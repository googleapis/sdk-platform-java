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

import com.google.api.RoutingParameter;
import com.google.api.RoutingProto;
import com.google.api.RoutingRule;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.RoutingHeaders;
import com.google.api.generator.gapic.model.RoutingHeaders.RoutingHeader;
import com.google.api.pathtemplate.PathTemplate;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSortedSet;
import com.google.protobuf.DescriptorProtos.MethodOptions;
import com.google.protobuf.Descriptors.MethodDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RoutingRuleParser {

  private static final String ASTERISK = "**";

  public static RoutingHeaders parse(
      MethodDescriptor protoMethod, Message inputMessage, Map<String, Message> messageTypes) {
    MethodOptions methodOptions = protoMethod.getOptions();

    if (!methodOptions.hasExtension(RoutingProto.routing)) {
      return RoutingHeaders.create(Collections.emptyList());
    }

    RoutingRule routingRule = methodOptions.getExtension(RoutingProto.routing);

    return parseHttpRuleHelper(routingRule, Optional.of(inputMessage), messageTypes);
  }

  private static RoutingHeaders parseHttpRuleHelper(
      RoutingRule routingRule,
      Optional<Message> inputMessageOpt,
      Map<String, Message> messageTypes) {

    // one field may map to multiple headers, Example 6
    // multiple fields may map to one header as well, Example 8, last wins
    // one combination of field/name may have multiple patterns, the one matches win, Example 3c.
    List<RoutingHeader> routingHeaderSet = new ArrayList<>();
    for (RoutingParameter routingParameter : routingRule.getRoutingParametersList()) {
      String pathTemplate = routingParameter.getPathTemplate();
      String field = routingParameter.getField();
      // TODO: Validate if field exist in Message or nested Messages
      // If failed, stop or ignore? stop?
      checkHttpFieldIsValid(field, inputMessageOpt.get(), false);

      // TODO: Validate the pattern, if specified and not **, the pattern must contain one and
      // only one named segment
      // If failed, stop or ignore? stop?
      Set<String> params = getPatternBindings(pathTemplate).build();
      String name = field;
      // set name to field if empty, Example 1
      if (!params.isEmpty()) {
        name = params.iterator().next();
      }

      // set path to ** if empty, Example 1
      if (pathTemplate.isEmpty()) {
        pathTemplate = ASTERISK;
      }

      RoutingHeader routingHeader = RoutingHeader.create(field, name, pathTemplate);
      routingHeaderSet.add(routingHeader);
    }

    return RoutingHeaders.create(routingHeaderSet);
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

  // TODO:Move to Message.java, also need to handle nested fields. The field has to be of type
  // String
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
