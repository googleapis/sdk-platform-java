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

import com.google.api.ClientProto;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.common.base.Preconditions;
import com.google.protobuf.Descriptors.MethodDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// TODO(miraleung): Add tests for this class. Currently exercised in integration tests.
public class MethodSignatureParser {
  private static final String DOT = ".";
  private static final String METHOD_SIGNATURE_DELIMITER = "\\s*,\\s*";

  /** Parses a list of method signature annotations out of an RPC. */
  public static List<List<MethodArgument>> parseMethodSignatures(
      MethodDescriptor methodDescriptor,
      String servicePackage,
      TypeNode methodInputType,
      Map<String, Message> messageTypes,
      Map<String, ResourceName> resourceNames,
      Set<ResourceName> outputArgResourceNames) {
    List<String> stringSigs =
        methodDescriptor.getOptions().getExtension(ClientProto.methodSignature);

    List<List<MethodArgument>> signatures = new ArrayList<>();
    if (stringSigs.isEmpty()) {
      return signatures;
    }

    Map<String, ResourceName> patternsToResourceNames = createPatternResourceNameMap(resourceNames);
    String methodInputTypeName = methodInputType.reference().name();
    Message inputMessage = messageTypes.get(methodInputTypeName);

    // Example from Expand in echo.proto:
    // Input: ["content,error", "content,error,info"].
    // Output: [["content", "error"], ["content", "error", "info"]].
    for (String stringSig : stringSigs) {
      List<MethodArgument> arguments = new ArrayList<>();
      for (String argumentName : stringSig.split(METHOD_SIGNATURE_DELIMITER)) {
        List<TypeNode> argumentTypePath =
            new ArrayList<>(
                parseTypeFromArgumentName(
                    argumentName,
                    servicePackage,
                    inputMessage,
                    messageTypes,
                    resourceNames,
                    patternsToResourceNames,
                    outputArgResourceNames));

        int dotLastIndex = argumentName.lastIndexOf(DOT);
        String actualArgumentName =
            dotLastIndex < 0 ? argumentName : argumentName.substring(dotLastIndex + 1);

        int typeLastIndex = argumentTypePath.size() - 1;
        TypeNode argumentType = argumentTypePath.get(typeLastIndex);
        argumentTypePath.remove(typeLastIndex);

        arguments.add(
            MethodArgument.builder()
                .setName(actualArgumentName)
                .setType(argumentType)
                .setNestedTypes(argumentTypePath)
                .build());
      }
      signatures.add(arguments);
    }
    return signatures;
  }

  private static List<TypeNode> parseTypeFromArgumentName(
      String argumentName,
      String servicePackage,
      Message inputMessage,
      Map<String, Message> messageTypes,
      Map<String, ResourceName> resourceNames,
      Map<String, ResourceName> patternsToResourceNames,
      Set<ResourceName> outputArgResourceNames) {
    return parseTypeFromArgumentName(
        argumentName,
        servicePackage,
        inputMessage,
        messageTypes,
        resourceNames,
        patternsToResourceNames,
        outputArgResourceNames,
        new ArrayList<>());
  }

  private static List<TypeNode> parseTypeFromArgumentName(
      String argumentName,
      String servicePackage,
      Message inputMessage,
      Map<String, Message> messageTypes,
      Map<String, ResourceName> resourceNames,
      Map<String, ResourceName> patternsToResourceNames,
      Set<ResourceName> outputArgResourceNames,
      List<TypeNode> typeAcc) {
    int dotIndex = argumentName.indexOf(DOT);
    // TODO(miraleung): Fake out resource names here.
    if (dotIndex < 1) {
      Field field = inputMessage.fieldMap().get(argumentName);
      Preconditions.checkNotNull(
          field, String.format("Field %s not found, %s", argumentName, inputMessage.fieldMap()));
      return Arrays.asList(field.type());
    }
    Preconditions.checkState(
        dotIndex < argumentName.length() - 1,
        String.format(
            "Invalid argument name found: dot cannot be at the end of name %s", argumentName));
    String firstFieldName = argumentName.substring(0, dotIndex);
    String remainingArgumentName = argumentName.substring(dotIndex + 1);

    // Must be a sub-message for a type's subfield to be valid.
    Field firstField = inputMessage.fieldMap().get(firstFieldName);
    if (firstField.hasResourceReference()) {
      List<ResourceName> resourceNameArgs =
          ResourceReferenceParser.parseResourceNames(
              firstField.resourceReference(),
              servicePackage,
              resourceNames,
              patternsToResourceNames);
      outputArgResourceNames.addAll(resourceNameArgs);
      return resourceNameArgs.stream().map(r -> r.type()).collect(Collectors.toList());
    }

    Preconditions.checkState(
        !firstField.isRepeated(),
        String.format("Cannot descend into repeated field %s", firstField.name()));

    TypeNode firstFieldType = firstField.type();
    Preconditions.checkState(
        TypeNode.isReferenceType(firstFieldType) && !firstFieldType.equals(TypeNode.STRING),
        String.format("Field reference on %s cannot be a primitive type", firstFieldName));

    String firstFieldTypeName = firstFieldType.reference().name();
    Message firstFieldMessage = messageTypes.get(firstFieldTypeName);
    Preconditions.checkNotNull(
        firstFieldMessage,
        String.format(
            "Message type %s for field reference %s invalid", firstFieldTypeName, firstFieldName));

    List<TypeNode> newAcc = new ArrayList<>(typeAcc);
    newAcc.add(firstFieldType);
    return parseTypeFromArgumentName(
        remainingArgumentName,
        servicePackage,
        firstFieldMessage,
        messageTypes,
        resourceNames,
        patternsToResourceNames,
        outputArgResourceNames,
        newAcc);
  }

  private static Map<String, ResourceName> createPatternResourceNameMap(
      Map<String, ResourceName> resourceNames) {
    Map<String, ResourceName> patternsToResourceNames = new HashMap<>();
    for (ResourceName resourceName : resourceNames.values()) {
      for (String pattern : resourceName.patterns()) {
        patternsToResourceNames.put(pattern, resourceName);
      }
    }
    return patternsToResourceNames;
  }
}
