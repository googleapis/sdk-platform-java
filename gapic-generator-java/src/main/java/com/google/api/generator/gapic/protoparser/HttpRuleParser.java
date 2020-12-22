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
import com.google.api.generator.gapic.model.Message;
import com.google.api.pathtemplate.PathTemplate;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.protobuf.DescriptorProtos.MethodOptions;
import com.google.protobuf.Descriptors.MethodDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpRuleParser {
  private static final String ASTERISK = "*";

  public static Optional<List<String>> parseHttpBindings(
      MethodDescriptor protoMethod, Message inputMessage, Map<String, Message> messageTypes) {
    MethodOptions methodOptions = protoMethod.getOptions();
    if (!methodOptions.hasExtension(AnnotationsProto.http)) {
      return Optional.empty();
    }

    HttpRule httpRule = methodOptions.getExtension(AnnotationsProto.http);

    // Body validation.
    if (!Strings.isNullOrEmpty(httpRule.getBody()) && !httpRule.getBody().equals(ASTERISK)) {
      checkHttpFieldIsValid(httpRule.getBody(), inputMessage, true);
    }

    // Get pattern.
    Set<String> uniqueBindings = getPatternBindings(httpRule);
    if (uniqueBindings.isEmpty()) {
      return Optional.empty();
    }

    if (httpRule.getAdditionalBindingsCount() > 0) {
      for (HttpRule additionalRule : httpRule.getAdditionalBindingsList()) {
        uniqueBindings.addAll(getPatternBindings(additionalRule));
      }
    }

    List<String> bindings = new ArrayList<>(uniqueBindings);
    Collections.sort(bindings);

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

    return Optional.of(bindings);
  }

  private static Set<String> getPatternBindings(HttpRule httpRule) {
    String pattern = null;
    // Assign a temp variable to prevent the formatter from removing the import.
    PatternCase patternCase = httpRule.getPatternCase();
    switch (patternCase) {
      case GET:
        pattern = httpRule.getGet();
        break;
      case PUT:
        pattern = httpRule.getPut();
        break;
      case POST:
        pattern = httpRule.getPost();
        break;
      case DELETE:
        pattern = httpRule.getDelete();
        break;
      case PATCH:
        pattern = httpRule.getPatch();
        break;
      case CUSTOM: // Invalid pattern.
        // Fall through.
      default:
        return Collections.emptySet();
    }

    PathTemplate template = PathTemplate.create(pattern);
    Set<String> bindings =
        new HashSet<String>(
            // Filter out any unbound variable like "$0, $1, etc.
            template.vars().stream().filter(s -> !s.contains("$")).collect(Collectors.toList()));
    return bindings;
  }

  private static void checkHttpFieldIsValid(String binding, Message inputMessage, boolean isBody) {
    Preconditions.checkState(
        !Strings.isNullOrEmpty(binding),
        String.format("Null or empty binding for " + inputMessage.name()));
    Preconditions.checkState(
        inputMessage.fieldMap().containsKey(binding),
        String.format(
            "Expected message %s to contain field %s but none found"
                + ", DEL: "
                + inputMessage.fieldMap().keySet(),
            inputMessage.name(),
            binding));
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
