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
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.HttpRuleBindings;
import com.google.api.generator.gapic.model.Message;
import com.google.api.pathtemplate.PathTemplate;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Sets;
import com.google.protobuf.DescriptorProtos.MethodOptions;
import com.google.protobuf.Descriptors.MethodDescriptor;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpRuleParser {
  private static final String ASTERISK = "*";

  public static HttpRuleBindings parse(
      MethodDescriptor protoMethod, Message inputMessage, Map<String, Message> messageTypes) {
    MethodOptions methodOptions = protoMethod.getOptions();
    HttpRuleBindings.Builder httpBindings = HttpRuleBindings.builder();
    if (!methodOptions.hasExtension(AnnotationsProto.http)) {
      return httpBindings.build();
    }

    HttpRule httpRule = methodOptions.getExtension(AnnotationsProto.http);

    // Body validation.
    if (!Strings.isNullOrEmpty(httpRule.getBody()) && !httpRule.getBody().equals(ASTERISK)) {
      checkHttpFieldIsValid(httpRule.getBody(), inputMessage, true);
    }

    // Get pattern.
    String pattern = getPattern(httpRule);
    ImmutableSet.Builder<String> bindingsBuilder = getPatternBindings(pattern);
    if (httpRule.getAdditionalBindingsCount() > 0) {
      for (HttpRule additionalRule : httpRule.getAdditionalBindingsList()) {
        // TODO: save additional bindings path in HttpRuleBindings
        bindingsBuilder.addAll(getPatternBindings(getPattern(additionalRule)).build());
      }
    }

    Set<String> bindings = bindingsBuilder.build();

    // Binding validation.
    for (String binding : bindings) {
      // Handle foo.bar cases by descending into the subfields.
      String[] descendantBindings = binding.split("\\.");
      Message containingMessage = inputMessage;
      for (int i = 0; i < descendantBindings.length; i++) {
        String subField = descendantBindings[i];
        if (i < descendantBindings.length - 1) {
          Field field = containingMessage.fieldMap().get(subField);
          containingMessage = messageTypes.get(field.type().reference().simpleName());
          Preconditions.checkNotNull(
              containingMessage,
              String.format(
                  "No containing message found for field %s with type %s",
                  field.name(), field.type().reference().simpleName()));
        } else {
          checkHttpFieldIsValid(subField, containingMessage, false);
        }
      }
    }

    String body = httpRule.getBody();
    Set<String> bodyParameters;
    Set<String> queryParameters;
    if (Strings.isNullOrEmpty(body)) {
      bodyParameters = ImmutableSet.of();
      queryParameters = Sets.difference(inputMessage.fieldMap().keySet(), bindings);
    } else if (body.equals(ASTERISK)) {
      bodyParameters = Sets.difference(inputMessage.fieldMap().keySet(), bindings);
      queryParameters = ImmutableSet.of();
    } else {
      bodyParameters = ImmutableSet.of(body);
      Set<String> bodyBinidngsUnion = Sets.union(bodyParameters, bindings);
      queryParameters = Sets.difference(inputMessage.fieldMap().keySet(), bodyBinidngsUnion);
    }

    httpBindings.setHttpVerb(httpRule.getPatternCase().toString());
    httpBindings.setPattern(pattern);
    httpBindings.setPathParameters(ImmutableSortedSet.copyOf(bindings));
    httpBindings.setQueryParameters(ImmutableSortedSet.copyOf(queryParameters));
    httpBindings.setBodyParameters(ImmutableSortedSet.copyOf(bodyParameters));

    return httpBindings.build();
  }

  private static String getPattern(HttpRule httpRule) {
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
      fieldCondition &= field.type().isProtoPrimitiveType();
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
