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
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.RoutingHeaders;
import com.google.api.generator.gapic.model.RoutingHeaders.RoutingHeader;
import com.google.api.pathtemplate.PathTemplate;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSortedSet;
import com.google.protobuf.DescriptorProtos.MethodOptions;
import com.google.protobuf.Descriptors.MethodDescriptor;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RoutingRuleParser {

  static final String WILDCARD_PATTERN = "{%s=**}";
  static final String PATH_TEMPLATE_WRONG_NUMBER_OF_NAMED_SEGMENT_ERROR_MESSAGE =
      "There needs to be one and only one named segment in path template %s";

  public static RoutingHeaders parse(
      MethodDescriptor protoMethod, Message inputMessage, Map<String, Message> messageTypes) {
    MethodOptions methodOptions = protoMethod.getOptions();

    RoutingHeaders.Builder builder = RoutingHeaders.builder();
    if (!methodOptions.hasExtension(RoutingProto.routing)) {
      return builder.build();
    }

    RoutingRule routingRule = methodOptions.getExtension(RoutingProto.routing);

    for (RoutingParameter routingParameter : routingRule.getRoutingParametersList()) {
      String pathTemplate = routingParameter.getPathTemplate();
      String field = routingParameter.getField();
      // validate if field exist in Message or nested Messages
      inputMessage.validateField(field, messageTypes);
      String name;
      // if specified, the pattern must contain one and only one named segment
      if (Strings.isNullOrEmpty(pathTemplate)) {
        name = field;
        pathTemplate = String.format(WILDCARD_PATTERN, name);
      } else {
        Set<String> params = getPatternBindings(pathTemplate).build();
        Preconditions.checkArgument(
            params.size() == 1,
            String.format(PATH_TEMPLATE_WRONG_NUMBER_OF_NAMED_SEGMENT_ERROR_MESSAGE, pathTemplate));
        name = params.iterator().next();
      }

      RoutingHeader routingHeader = RoutingHeader.create(field, name, pathTemplate);
      builder.addRoutingHeader(routingHeader);
    }

    return builder.build();
  }

  // TODO: duplicate of HttpRuleParser.getPatternBindings, move to a helper class
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
}
