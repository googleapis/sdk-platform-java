// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.gapic.protoparser;

import com.google.api.AnnotationsProto;
import com.google.api.HttpRule;
import com.google.api.HttpRule.PatternCase;
import com.google.api.RoutingParameter;
import com.google.api.RoutingProto;
import com.google.api.RoutingRule;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.HttpBindings;
import com.google.api.generator.gapic.model.HttpBindings.HttpBinding;
import com.google.api.generator.gapic.model.Message;
import com.google.api.pathtemplate.PathTemplate;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Sets;
import com.google.protobuf.DescriptorProtos.MethodOptions;
import com.google.protobuf.Descriptors.MethodDescriptor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpRuleParser {
  private static final String ASTERISK = "*";

  public static HttpBindings parse(
      MethodDescriptor protoMethod, Message inputMessage, Map<String, Message> messageTypes) {
    MethodOptions methodOptions = protoMethod.getOptions();
    if (!methodOptions.hasExtension(AnnotationsProto.http)) {
      return null;
    }

    HttpRule httpRule = methodOptions.getExtension(AnnotationsProto.http);

    RoutingRule routingRule = null;
    if (methodOptions.hasExtension(RoutingProto.routing)) {
      routingRule = methodOptions.getExtension(RoutingProto.routing);
    }

    // Body validation.
    if (!Strings.isNullOrEmpty(httpRule.getBody()) && !httpRule.getBody().equals(ASTERISK)) {
      checkHttpFieldIsValid(httpRule.getBody(), inputMessage, true);
    }

    return parseHttpRuleHelper(httpRule, routingRule, Optional.of(inputMessage), messageTypes);
  }

  public static HttpBindings parseHttpRule(HttpRule httpRule) {
    return parseHttpRuleHelper(httpRule, null, Optional.empty(), Collections.emptyMap());
  }

  private static HttpBindings parseHttpRuleHelper(
      HttpRule httpRule,
      RoutingRule routingRule,
      Optional<Message> inputMessageOpt,
      Map<String, Message> messageTypes) {
    // Get pattern.
    String pattern = getHttpVerbPattern(httpRule);
    ImmutableSet.Builder<String> bindingsBuilder = getPatternBindings(pattern);
    if (httpRule.getAdditionalBindingsCount() > 0) {
      for (HttpRule additionalRule : httpRule.getAdditionalBindingsList()) {
        // TODO: save additional bindings path in HttpRuleBindings
        bindingsBuilder.addAll(getPatternBindings(getHttpVerbPattern(additionalRule)).build());
      }
    }

    Set<String> pathParamNames = bindingsBuilder.build();

    // TODO: support nested message fields bindings
    String body = httpRule.getBody();
    Set<String> bodyParamNames;
    Set<String> queryParamNames;
    if (!inputMessageOpt.isPresent()) {
      // Must be a mixin, do not support full HttpRuleBindings for now
      bodyParamNames = ImmutableSet.of();
      queryParamNames = ImmutableSet.of();
    } else if (Strings.isNullOrEmpty(body)) {
      bodyParamNames = ImmutableSet.of();
      queryParamNames = Sets.difference(inputMessageOpt.get().fieldMap().keySet(), pathParamNames);
    } else if (body.equals(ASTERISK)) {
      bodyParamNames = Sets.difference(inputMessageOpt.get().fieldMap().keySet(), pathParamNames);
      queryParamNames = ImmutableSet.of();
    } else {
      bodyParamNames = ImmutableSet.of(body);
      Set<String> bodyBinidngsUnion = Sets.union(bodyParamNames, pathParamNames);
      queryParamNames =
          Sets.difference(inputMessageOpt.get().fieldMap().keySet(), bodyBinidngsUnion);
    }

    Map<String, String> fieldToAliasMap = null;
    if (routingRule != null) {
      fieldToAliasMap = new HashMap<>();
      for (RoutingParameter routingParameter : routingRule.getRoutingParametersList()) {
        Set<String> params = getPatternBindings(routingParameter.getPathTemplate()).build();
        if (params.size() == 1) {
          fieldToAliasMap.put(routingParameter.getField(), params.iterator().next());
        }
      }
    }

    Message message = inputMessageOpt.orElse(null);
    return HttpBindings.builder()
        .setHttpVerb(HttpBindings.HttpVerb.valueOf(httpRule.getPatternCase().toString()))
        .setPattern(pattern)
        .setPathParameters(
            validateAndConstructHttpBindings(
                pathParamNames, fieldToAliasMap, message, messageTypes, true))
        .setQueryParameters(
            validateAndConstructHttpBindings(queryParamNames, null, message, messageTypes, false))
        .setBodyParameters(
            validateAndConstructHttpBindings(bodyParamNames, null, message, messageTypes, false))
        .setIsAsteriskBody(body.equals(ASTERISK))
        .build();
  }

  private static Set<HttpBinding> validateAndConstructHttpBindings(
      Set<String> paramNames,
      Map<String, String> fieldToAliasMap,
      Message inputMessage,
      Map<String, Message> messageTypes,
      boolean isPath) {

    if (fieldToAliasMap != null) {
      paramNames = fieldToAliasMap.keySet();
    }

    ImmutableSortedSet.Builder<HttpBinding> httpBindings = ImmutableSortedSet.naturalOrder();
    for (String paramName : paramNames) {
      // Handle foo.bar cases by descending into the subfields.
      String[] subFields = paramName.split("\\.");
      if (inputMessage == null) {
        httpBindings.add(
            HttpBinding.create(paramName, false, getAlias(paramName, fieldToAliasMap)));
        continue;
      }
      Message nestedMessage = inputMessage;
      for (int i = 0; i < subFields.length; i++) {
        String subFieldName = subFields[i];
        if (i < subFields.length - 1) {
          Field field = nestedMessage.fieldMap().get(subFieldName);
          nestedMessage = messageTypes.get(field.type().reference().fullName());
          Preconditions.checkNotNull(
              nestedMessage,
              String.format(
                  "No containing message found for field %s with type %s",
                  field.name(), field.type().reference().simpleName()));

        } else {
          if (isPath) {
            checkHttpFieldIsValid(subFieldName, nestedMessage, !isPath);
          }
          Field field = nestedMessage.fieldMap().get(subFieldName);
          httpBindings.add(
              HttpBinding.create(
                  paramName, field.isProto3Optional(), getAlias(paramName, fieldToAliasMap)));
        }
      }
    }
    return httpBindings.build();
  }

  private static String getAlias(String paramName, Map<String, String> fieldToAliasMap) {
    if (fieldToAliasMap != null && fieldToAliasMap.containsKey(paramName)) {
      return fieldToAliasMap.get(paramName);
    } else {
      return paramName;
    }
  }

  private static String getHttpVerbPattern(HttpRule httpRule) {
    PatternCase patternCase = httpRule.getPatternCase();
    switch (patternCase) {
      case GET:
        return httpRule.getGet();
      case PUT:
        return httpRule.getPut();
      case POST:
        return httpRule.getPost();
      case DELETE:
        return httpRule.getDelete();
      case PATCH:
        return httpRule.getPatch();
      case CUSTOM: // Invalid pattern.
        // Fall through.
      default:
        return "";
    }
  }

  private static ImmutableSortedSet.Builder<String> getPatternBindings(String pattern) {
    ImmutableSortedSet.Builder<String> bindings = ImmutableSortedSet.naturalOrder();
    if (pattern.isEmpty()) {
      return bindings;
    }

    PathTemplate template = PathTemplate.create(pattern);
    // Filter out any unbound variable like "$0, $1, etc.
    bindings.addAll(
        template.vars().stream().filter(s -> !s.contains("$")).collect(Collectors.toSet()));
    return bindings;
  }

  private static void checkHttpFieldIsValid(String binding, Message inputMessage, boolean isBody) {
    Preconditions.checkState(
        !Strings.isNullOrEmpty(binding),
        String.format("Null or empty binding for " + inputMessage.name()));
    Preconditions.checkState(
        inputMessage.fieldMap().containsKey(binding),
        String.format(
            "Expected message %s to contain field %s but none found",
            inputMessage.name(), binding));
    Field field = inputMessage.fieldMap().get(binding);
    boolean fieldCondition = !field.isRepeated();
    if (!isBody) {
      fieldCondition &= field.type().isProtoPrimitiveType() || field.isEnum();
    }
    String messageFormat =
        "Expected a non-repeated "
            + (isBody ? "" : "primitive ")
            + "type for field %s in message %s but got type %s";
    Preconditions.checkState(
        fieldCondition,
        String.format(messageFormat, field.name(), inputMessage.name(), field.type()));
  }
}
