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
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
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
    // stringSigs: ["content,error", "content,error,info"].
    for (String stringSig : stringSigs) {
      List<String> argumentNames = new ArrayList<>();
      Map<String, List<MethodArgument>> argumentNameToOverloads = new HashMap<>();

      // stringSig.split: ["content", "error"].
      for (String argumentName : stringSig.split(METHOD_SIGNATURE_DELIMITER)) {
        // For resource names, this will be empty.
        List<TypeNode> argumentTypePathAcc = new ArrayList<>();
        // There should be more than one type returned only when we encounter a reousrce name.
        List<TypeNode> argumentTypes =
            parseTypeFromArgumentName(
                argumentName,
                servicePackage,
                inputMessage,
                messageTypes,
                resourceNames,
                patternsToResourceNames,
                argumentTypePathAcc,
                outputArgResourceNames);
        int dotLastIndex = argumentName.lastIndexOf(DOT);
        String actualArgumentName =
            dotLastIndex < 0 ? argumentName : argumentName.substring(dotLastIndex + 1);
        argumentNames.add(actualArgumentName);
        argumentNameToOverloads.put(
            actualArgumentName,
            argumentTypes.stream()
                .map(
                    type ->
                        MethodArgument.builder()
                            .setName(actualArgumentName)
                            .setType(type)
                            .setIsResourceNameHelper(
                                argumentTypes.size() > 1 && !type.equals(TypeNode.STRING))
                            .setNestedTypes(argumentTypePathAcc)
                            .build())
                .collect(Collectors.toList()));
      }
      signatures.addAll(flattenMethodSignatureVariants(argumentNames, argumentNameToOverloads));
    }
    return signatures;
  }

  @VisibleForTesting
  static List<List<MethodArgument>> flattenMethodSignatureVariants(
      List<String> argumentNames, Map<String, List<MethodArgument>> argumentNameToOverloads) {
    Preconditions.checkState(
        argumentNames.size() == argumentNameToOverloads.size(),
        String.format(
            "Cardinality of argument names %s do not match that of overloaded types %s",
            argumentNames, argumentNameToOverloads));
    for (String name : argumentNames) {
      Preconditions.checkNotNull(
          argumentNameToOverloads.get(name),
          String.format("No corresponding overload types found for argument %s", name));
    }
    return flattenMethodSignatureVariants(argumentNames, argumentNameToOverloads, 0);
  }

  private static List<List<MethodArgument>> flattenMethodSignatureVariants(
      List<String> argumentNames,
      Map<String, List<MethodArgument>> argumentNameToOverloads,
      int depth) {
    List<List<MethodArgument>> methodArgs = new ArrayList<>();
    if (depth >= argumentNames.size() - 1) {
      for (MethodArgument methodArg : argumentNameToOverloads.get(argumentNames.get(depth))) {
        methodArgs.add(Lists.newArrayList(methodArg));
      }
      return methodArgs;
    }

    List<List<MethodArgument>> subsequentArgs =
        flattenMethodSignatureVariants(argumentNames, argumentNameToOverloads, depth + 1);
    for (MethodArgument methodArg : argumentNameToOverloads.get(argumentNames.get(depth))) {
      for (List<MethodArgument> subsequentArg : subsequentArgs) {
        // Use a new list to avoid appending all subsequent elements (in upcoming loop iterations)
        // to the same list.
        List<MethodArgument> appendedArgs = new ArrayList<>(subsequentArg);
        appendedArgs.add(0, methodArg);
        methodArgs.add(appendedArgs);
      }
    }
    return methodArgs;
  }

  private static List<TypeNode> parseTypeFromArgumentName(
      String argumentName,
      String servicePackage,
      Message inputMessage,
      Map<String, Message> messageTypes,
      Map<String, ResourceName> resourceNames,
      Map<String, ResourceName> patternsToResourceNames,
      List<TypeNode> argumentTypePathAcc,
      Set<ResourceName> outputArgResourceNames) {

    int dotIndex = argumentName.indexOf(DOT);
    if (dotIndex < 1) {
      Field field = inputMessage.fieldMap().get(argumentName);
      Preconditions.checkNotNull(
          field,
          String.format(
              "Field %s not found from input message %s values %s",
              argumentName, inputMessage.name(), inputMessage.fieldMap().keySet()));
      if (!field.hasResourceReference()) {
        return Arrays.asList(field.type());
      }

      // Parse the resource name tyeps.
      List<ResourceName> resourceNameArgs =
          ResourceReferenceParser.parseResourceNames(
              field.resourceReference(), servicePackage, resourceNames, patternsToResourceNames);
      outputArgResourceNames.addAll(resourceNameArgs);
      List<TypeNode> allFieldTypes = new ArrayList<>();
      allFieldTypes.add(TypeNode.STRING);
      allFieldTypes.addAll(
          resourceNameArgs.stream().map(r -> r.type()).collect(Collectors.toList()));
      return allFieldTypes;
    }

    Preconditions.checkState(
        dotIndex < argumentName.length() - 1,
        String.format(
            "Invalid argument name found: dot cannot be at the end of name %s", argumentName));
    String firstFieldName = argumentName.substring(0, dotIndex);
    String remainingArgumentName = argumentName.substring(dotIndex + 1);

    // Must be a sub-message for a type's subfield to be valid.
    Field firstField = inputMessage.fieldMap().get(firstFieldName);

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

    argumentTypePathAcc.add(firstFieldType);
    return parseTypeFromArgumentName(
        remainingArgumentName,
        servicePackage,
        firstFieldMessage,
        messageTypes,
        resourceNames,
        patternsToResourceNames,
        argumentTypePathAcc,
        outputArgResourceNames);
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
