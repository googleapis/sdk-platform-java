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

import com.google.api.ResourceDescriptor;
import com.google.api.ResourceProto;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.LongrunningOperation;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.ResourceReference;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.ResourceNameConstants;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.longrunning.OperationInfo;
import com.google.longrunning.OperationsProto;
import com.google.protobuf.DescriptorProtos.FieldOptions;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.MessageOptions;
import com.google.protobuf.DescriptorProtos.MethodOptions;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.DescriptorValidationException;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Parser {
  static class GapicParserException extends RuntimeException {
    public GapicParserException(String errorMessage) {
      super(errorMessage);
    }
  }

  public static GapicContext parse(CodeGeneratorRequest request) {
    // Keep message and resource name parsing separate for cleaner logic.
    // While this takes an extra pass through the protobufs, the extra time is relatively trivial
    // and is worth the larger reduced maintenance cost.
    Map<String, Message> messages = parseMessages(request);
    Map<String, ResourceName> resourceNames = parseResourceNames(request);
    messages = updateResourceNamesInMessages(messages, resourceNames.values());
    Set<ResourceName> outputArgResourceNames = new HashSet<>();
    List<Service> services =
        parseServices(request, messages, resourceNames, outputArgResourceNames);
    return GapicContext.builder()
        .setServices(services)
        .setMessages(messages)
        .setResourceNames(resourceNames)
        .setHelperResourceNames(outputArgResourceNames)
        .build();
  }

  public static List<Service> parseServices(
      CodeGeneratorRequest request,
      Map<String, Message> messageTypes,
      Map<String, ResourceName> resourceNames,
      Set<ResourceName> outputArgResourceNames) {
    Map<String, FileDescriptor> fileDescriptors = getFilesToGenerate(request);
    List<Service> services = new ArrayList<>();
    for (String fileToGenerate : request.getFileToGenerateList()) {
      FileDescriptor fileDescriptor =
          Preconditions.checkNotNull(
              fileDescriptors.get(fileToGenerate),
              "Missing file descriptor for [%s]",
              fileToGenerate);

      services.addAll(
          parseService(fileDescriptor, messageTypes, resourceNames, outputArgResourceNames));
    }

    return services;
  }

  public static List<Service> parseService(
      FileDescriptor fileDescriptor,
      Map<String, Message> messageTypes,
      Map<String, ResourceName> resourceNames,
      Set<ResourceName> outputArgResourceNames) {
    String pakkage = TypeParser.getPackage(fileDescriptor);
    return fileDescriptor.getServices().stream()
        .map(
            s ->
                Service.builder()
                    .setName(s.getName())
                    .setPakkage(pakkage)
                    .setProtoPakkage(fileDescriptor.getPackage())
                    .setMethods(
                        parseMethods(
                            s, pakkage, messageTypes, resourceNames, outputArgResourceNames))
                    .build())
        .collect(Collectors.toList());
  }

  public static Map<String, Message> parseMessages(CodeGeneratorRequest request) {
    Map<String, FileDescriptor> fileDescriptors = getFilesToGenerate(request);
    Map<String, Message> messages = new HashMap<>();
    for (String fileToGenerate : request.getFileToGenerateList()) {
      FileDescriptor fileDescriptor =
          Preconditions.checkNotNull(
              fileDescriptors.get(fileToGenerate),
              "Missing file descriptor for [%s]",
              fileToGenerate);
      messages.putAll(parseMessages(fileDescriptor));
    }
    return messages;
  }

  public static Map<String, Message> parseMessages(FileDescriptor fileDescriptor) {
    String pakkage = TypeParser.getPackage(fileDescriptor);
    return fileDescriptor.getMessageTypes().stream()
        .collect(
            Collectors.toMap(
                md -> md.getName(),
                md ->
                    Message.builder()
                        .setType(
                            TypeNode.withReference(
                                VaporReference.builder()
                                    .setName(md.getName())
                                    .setPakkage(pakkage)
                                    .build()))
                        .setName(md.getName())
                        .setFields(parseFields(md))
                        .build()));
  }

  /**
   * Populates ResourceName objects in Message POJOs.
   *
   * @param messageTypes A map of the message type name (as in the protobuf) to Message POJOs.
   * @param resourceNames A list of ResourceName POJOs.
   * @return The updated messageTypes map.
   */
  public static Map<String, Message> updateResourceNamesInMessages(
      Map<String, Message> messageTypes, Collection<ResourceName> resources) {
    Map<String, Message> updatedMessages = new HashMap<>(messageTypes);
    for (ResourceName resource : resources) {
      if (!resource.hasParentMessageName()) {
        continue;
      }
      String messageKey = resource.parentMessageName();
      Message messageToUpdate = updatedMessages.get(messageKey);
      updatedMessages.put(messageKey, messageToUpdate.toBuilder().setResource(resource).build());
    }
    return updatedMessages;
  }

  public static Map<String, ResourceName> parseResourceNames(CodeGeneratorRequest request) {
    Map<String, FileDescriptor> fileDescriptors = getFilesToGenerate(request);
    Map<String, ResourceName> resourceNames = new HashMap<>();
    for (String fileToGenerate : request.getFileToGenerateList()) {
      FileDescriptor fileDescriptor =
          Preconditions.checkNotNull(
              fileDescriptors.get(fileToGenerate),
              "Missing file descriptor for [%s]",
              fileToGenerate);
      resourceNames.putAll(parseResourceNames(fileDescriptor));
    }
    return resourceNames;
  }

  // Convenience wrapper for package-external unit tests.
  public static Map<String, ResourceName> parseResourceNames(FileDescriptor fileDescriptor) {
    return ResourceNameParser.parseResourceNames(fileDescriptor);
  }

  @VisibleForTesting
  static List<Method> parseMethods(
      ServiceDescriptor serviceDescriptor,
      String servicePackage,
      Map<String, Message> messageTypes,
      Map<String, ResourceName> resourceNames,
      Set<ResourceName> outputArgResourceNames) {
    List<Method> methods = new ArrayList<>();
    for (MethodDescriptor protoMethod : serviceDescriptor.getMethods()) {
      // Parse the method.
      TypeNode inputType = TypeParser.parseType(protoMethod.getInputType());
      methods.add(
          Method.builder()
              .setName(protoMethod.getName())
              .setInputType(inputType)
              .setOutputType(TypeParser.parseType(protoMethod.getOutputType()))
              .setStream(
                  Method.toStream(protoMethod.isClientStreaming(), protoMethod.isServerStreaming()))
              .setLro(parseLro(protoMethod, messageTypes))
              .setMethodSignatures(
                  MethodSignatureParser.parseMethodSignatures(
                      protoMethod,
                      servicePackage,
                      inputType,
                      messageTypes,
                      resourceNames,
                      outputArgResourceNames))
              .setIsPaged(parseIsPaged(protoMethod, messageTypes))
              .build());

      // Any input type that has a resource reference will need a resource name helper calss.
      Message inputMessage = messageTypes.get(inputType.reference().name());
      for (Field field : inputMessage.fields()) {
        if (field.hasResourceReference()) {
          String resourceTypeString = field.resourceReference().resourceTypeString();
          ResourceName resourceName = resourceNames.get(resourceTypeString);
          Preconditions.checkNotNull(
              resourceName, String.format("Resource name %s not found", resourceTypeString));
          System.out.println("DEL: ADDING " + resourceTypeString);
          outputArgResourceNames.add(resourceName);
        }
      }
    }

    return methods;
  }

  @VisibleForTesting
  static LongrunningOperation parseLro(
      MethodDescriptor methodDescriptor, Map<String, Message> messageTypes) {
    MethodOptions methodOptions = methodDescriptor.getOptions();
    if (!methodOptions.hasExtension(OperationsProto.operationInfo)) {
      return null;
    }

    OperationInfo lroInfo =
        methodDescriptor.getOptions().getExtension(OperationsProto.operationInfo);
    String responseTypeName = lroInfo.getResponseType();
    String metadataTypeName = lroInfo.getMetadataType();
    Message responseMessage = messageTypes.get(responseTypeName);
    Message metadataMessage = messageTypes.get(metadataTypeName);
    Preconditions.checkNotNull(
        responseMessage, String.format("LRO response message %s not found", responseTypeName));
    Preconditions.checkNotNull(
        metadataMessage, String.format("LRO metadata message %s not found", metadataTypeName));

    return LongrunningOperation.withTypes(responseMessage.type(), metadataMessage.type());
  }

  @VisibleForTesting
  static boolean parseIsPaged(
      MethodDescriptor methodDescriptor, Map<String, Message> messageTypes) {
    Message inputMessage = messageTypes.get(methodDescriptor.getInputType().getName());
    Message outputMessage = messageTypes.get(methodDescriptor.getInputType().getName());
    return inputMessage.fieldMap().containsKey("page_size")
        || inputMessage.fieldMap().containsKey("page_token")
        || outputMessage.fieldMap().containsKey("next_page_token");
  }

  private static List<Field> parseFields(Descriptor messageDescriptor) {
    return messageDescriptor.getFields().stream()
        .map(f -> parseField(f, messageDescriptor))
        .collect(Collectors.toList());
  }

  private static Field parseField(FieldDescriptor fieldDescriptor, Descriptor messageDescriptor) {
    FieldOptions fieldOptions = fieldDescriptor.getOptions();
    MessageOptions messageOptions = messageDescriptor.getOptions();
    ResourceReference resourceReference = null;
    if (fieldOptions.hasExtension(ResourceProto.resourceReference)) {
      com.google.api.ResourceReference protoResourceReference =
          fieldOptions.getExtension(ResourceProto.resourceReference);
      // Assumes only one of type or child_type is set.
      String typeString = protoResourceReference.getType();
      String childTypeString = protoResourceReference.getChildType();
      Preconditions.checkState(
          !Strings.isNullOrEmpty(typeString) ^ !Strings.isNullOrEmpty(childTypeString),
          String.format(
              "Exactly one of type or child_type must be set for resource_reference in field %s",
              fieldDescriptor.getName()));
      boolean isChildType = !Strings.isNullOrEmpty(childTypeString);
      resourceReference =
          isChildType
              ? ResourceReference.withChildType(childTypeString)
              : ResourceReference.withType(typeString);
    } else if (fieldDescriptor.getName().equals(ResourceNameConstants.NAME_FIELD_NAME)
        && messageOptions.hasExtension(ResourceProto.resource)) {
      ResourceDescriptor protoResource = messageOptions.getExtension(ResourceProto.resource);
      resourceReference = ResourceReference.withType(protoResource.getType());
    }

    return Field.builder()
        .setName(fieldDescriptor.getName())
        .setType(TypeParser.parseType(fieldDescriptor))
        .setIsRepeated(fieldDescriptor.isRepeated())
        .setResourceReference(resourceReference)
        .build();
  }

  private static Map<String, FileDescriptor> getFilesToGenerate(CodeGeneratorRequest request) {
    // Build the fileDescriptors map so that we can create the FDs for the filesToGenerate.
    Map<String, FileDescriptor> fileDescriptors = Maps.newHashMap();
    for (FileDescriptorProto fileDescriptorProto : request.getProtoFileList()) {
      // Look up the imported files from previous file descriptors.  It is sufficient to look at
      // only previous file descriptors because CodeGeneratorRequest guarantees that the files
      // are sorted in topological order.
      FileDescriptor[] deps = new FileDescriptor[fileDescriptorProto.getDependencyCount()];
      for (int i = 0; i < fileDescriptorProto.getDependencyCount(); i++) {
        String name = fileDescriptorProto.getDependency(i);
        deps[i] =
            Preconditions.checkNotNull(
                fileDescriptors.get(name), "Missing file descriptor for [%s]", name);
      }

      FileDescriptor fileDescriptor = null;
      try {
        fileDescriptor = FileDescriptor.buildFrom(fileDescriptorProto, deps);
      } catch (DescriptorValidationException e) {
        throw new GapicParserException(e.getMessage());
      }

      fileDescriptors.put(fileDescriptor.getName(), fileDescriptor);
    }
    return fileDescriptors;
  }
}
