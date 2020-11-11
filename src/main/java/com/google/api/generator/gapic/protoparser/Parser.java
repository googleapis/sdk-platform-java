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
import com.google.api.ResourceDescriptor;
import com.google.api.ResourceProto;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicServiceConfig;
import com.google.api.generator.gapic.model.LongrunningOperation;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.ResourceReference;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.model.SourceCodeInfoLocation;
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
import com.google.protobuf.DescriptorProtos.ServiceOptions;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.DescriptorValidationException;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Parser {
  private static final String COMMA = ",";
  private static final String COLON = ":";
  private static final String DEFAULT_PORT = "443";
  private static final String DOT = ".";

  // Allow other parsers to access this.
  protected static final SourceCodeInfoParser SOURCE_CODE_INFO_PARSER = new SourceCodeInfoParser();

  static class GapicParserException extends RuntimeException {
    public GapicParserException(String errorMessage) {
      super(errorMessage);
    }
  }

  public static GapicContext parse(CodeGeneratorRequest request) {
    Optional<String> serviceConfigPathOpt = PluginArgumentParser.parseJsonConfigPath(request);
    String serviceConfigPath = serviceConfigPathOpt.isPresent() ? serviceConfigPathOpt.get() : null;
    Optional<GapicServiceConfig> serviceConfigOpt = ServiceConfigParser.parse(serviceConfigPath);

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
        .setServiceConfig(serviceConfigOpt.isPresent() ? serviceConfigOpt.get() : null)
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
            s -> {
              // Workaround for a missing default_host and oauth_scopes annotation from a service
              // definition. This can happen for protos that bypass the publishing process.
              // TODO(miraleung): Remove this workaround later?
              ServiceOptions serviceOptions = s.getOptions();
              String defaultHost = null;
              if (serviceOptions.hasExtension(ClientProto.defaultHost)) {
                defaultHost =
                    sanitizeDefaultHost(serviceOptions.getExtension(ClientProto.defaultHost));
              }
              Preconditions.checkState(
                  !Strings.isNullOrEmpty(defaultHost),
                  String.format(
                      "Default host not found in service YAML config file or annotation for %s",
                      s.getName()));

              List<String> oauthScopes = Collections.emptyList();
              if (serviceOptions.hasExtension(ClientProto.oauthScopes)) {
                oauthScopes =
                    Arrays.asList(
                        serviceOptions.getExtension(ClientProto.oauthScopes).split(COMMA));
              }

              Service.Builder serviceBuilder = Service.builder();
              if (fileDescriptor.toProto().hasSourceCodeInfo()) {
                SourceCodeInfoLocation protoServiceLocation =
                    SOURCE_CODE_INFO_PARSER.getLocation(s);
                if (!Objects.isNull(protoServiceLocation)
                    && !Strings.isNullOrEmpty(protoServiceLocation.getLeadingComments())) {
                  serviceBuilder.setDescription(protoServiceLocation.getLeadingComments());
                }
              }

              return serviceBuilder
                  .setName(s.getName())
                  .setDefaultHost(defaultHost)
                  .setOauthScopes(oauthScopes)
                  .setPakkage(pakkage)
                  .setProtoPakkage(fileDescriptor.getPackage())
                  .setMethods(
                      parseMethods(s, pakkage, messageTypes, resourceNames, outputArgResourceNames))
                  .build();
            })
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
    // TODO(miraleung): Preserve nested type and package data in the type key.
    Map<String, Message> messages = new HashMap<>();
    for (Descriptor messageDescriptor : fileDescriptor.getMessageTypes()) {
      messages.putAll(parseMessages(messageDescriptor));
    }
    return messages;
  }

  private static Map<String, Message> parseMessages(Descriptor messageDescriptor) {
    return parseMessages(messageDescriptor, new ArrayList<String>());
  }

  private static Map<String, Message> parseMessages(
      Descriptor messageDescriptor, List<String> outerNestedTypes) {
    Map<String, Message> messages = new HashMap<>();
    String messageName = messageDescriptor.getName();
    if (!messageDescriptor.getNestedTypes().isEmpty()) {
      for (Descriptor nestedMessage : messageDescriptor.getNestedTypes()) {
        if (isMapType(nestedMessage)) {
          continue;
        }
        outerNestedTypes.add(messageName);
        messages.putAll(parseMessages(nestedMessage, outerNestedTypes));
      }
    }
    String pakkage = TypeParser.getPackage(messageDescriptor.getFile());
    messages.put(
        messageName,
        Message.builder()
            .setType(TypeParser.parseType(messageDescriptor))
            .setName(messageName)
            .setFields(parseFields(messageDescriptor))
            .setOuterNestedTypes(outerNestedTypes)
            .build());
    return messages;
  }

  private static boolean isMapType(Descriptor messageDescriptor) {
    List<String> fieldNames =
        messageDescriptor.getFields().stream().map(f -> f.getName()).collect(Collectors.toList());
    // Ends in "Entry" and has exactly two fields, named "key" and "value".
    return messageDescriptor.getName().endsWith("Entry")
        && fieldNames.size() == 2
        && fieldNames.get(0).equals("key")
        && fieldNames.get(1).equals("value");
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
    @VisibleForTesting String javaPackage = parseServiceJavaPackage(request);
    Map<String, FileDescriptor> fileDescriptors = getFilesToGenerate(request);
    Map<String, ResourceName> resourceNames = new HashMap<>();
    for (String fileToGenerate : request.getFileToGenerateList()) {
      FileDescriptor fileDescriptor =
          Preconditions.checkNotNull(
              fileDescriptors.get(fileToGenerate),
              "Missing file descriptor for [%s]",
              fileToGenerate);
      resourceNames.putAll(parseResourceNames(fileDescriptor, javaPackage));
    }
    return resourceNames;
  }

  // Convenience wrapper for package-external unit tests. DO NOT ADD NEW FUNCTIONALITY HERE.
  public static Map<String, ResourceName> parseResourceNames(FileDescriptor fileDescriptor) {
    return parseResourceNames(fileDescriptor, TypeParser.getPackage(fileDescriptor));
  }

  public static Map<String, ResourceName> parseResourceNames(
      FileDescriptor fileDescriptor, String javaPackage) {
    return ResourceNameParser.parseResourceNames(fileDescriptor, javaPackage);
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
      Method.Builder methodBuilder = Method.builder();
      if (protoMethod.getFile().toProto().hasSourceCodeInfo()) {
        SourceCodeInfoLocation protoMethodLocation =
            SOURCE_CODE_INFO_PARSER.getLocation(protoMethod);
        if (!Objects.isNull(protoMethodLocation)
            && !Strings.isNullOrEmpty(protoMethodLocation.getLeadingComments())) {
          methodBuilder.setDescription(protoMethodLocation.getLeadingComments());
        }
      }

      Optional<List<String>> httpBindingsOpt =
          HttpRuleParser.parseHttpBindings(
              protoMethod, messageTypes.get(inputType.reference().name()), messageTypes);
      List<String> httpBindings =
          httpBindingsOpt.isPresent() ? httpBindingsOpt.get() : Collections.emptyList();

      methods.add(
          methodBuilder
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
              .setHttpBindings(httpBindings)
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
    String responseTypePackage = "";
    if (responseTypeName.contains(DOT)) {
      responseTypeName = responseTypeName.substring(responseTypeName.lastIndexOf(DOT) + 1);
    }

    String metadataTypeName = lroInfo.getMetadataType();
    if (metadataTypeName.contains(DOT)) {
      metadataTypeName = metadataTypeName.substring(metadataTypeName.lastIndexOf(DOT) + 1);
    }

    Message responseMessage = messageTypes.get(responseTypeName);
    Message metadataMessage = messageTypes.get(metadataTypeName);
    Preconditions.checkNotNull(
        responseMessage,
        String.format(
            "LRO response message %s not found on method %s",
            responseTypeName, methodDescriptor.getName()));
    // TODO(miraleung): Check that the packages are equal if those strings are not empty.
    Preconditions.checkNotNull(
        metadataMessage,
        String.format(
            "LRO metadata message %s not found in method %s",
            metadataTypeName, methodDescriptor.getName()));

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

  @VisibleForTesting
  static String sanitizeDefaultHost(String rawDefaultHost) {
    if (rawDefaultHost.indexOf(COLON) >= 0) {
      // A port is already present, just return the existing string.
      return rawDefaultHost;
    }
    return String.format("%s:%s", rawDefaultHost, DEFAULT_PORT);
  }

  private static List<Field> parseFields(Descriptor messageDescriptor) {
    List<FieldDescriptor> fields = new ArrayList<>(messageDescriptor.getFields());
    // Sort by ascending field index order. This is important for paged responses, where the first
    // repeated type is taken.
    fields.sort((f1, f2) -> f1.getIndex() - f2.getIndex());
    return fields.stream().map(f -> parseField(f, messageDescriptor)).collect(Collectors.toList());
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
    } else if (messageOptions.hasExtension(ResourceProto.resource)) {
      ResourceDescriptor protoResource = messageOptions.getExtension(ResourceProto.resource);
      // aip.dev/4231.
      String resourceFieldNameValue = ResourceNameConstants.NAME_FIELD_NAME;
      if (!Strings.isNullOrEmpty(protoResource.getNameField())) {
        resourceFieldNameValue = protoResource.getNameField();
      }
      if (fieldDescriptor.getName().equals(resourceFieldNameValue)) {
        resourceReference = ResourceReference.withType(protoResource.getType());
      }
    }

    Field.Builder fieldBuilder = Field.builder();
    if (fieldDescriptor.getFile().toProto().hasSourceCodeInfo()) {
      SourceCodeInfoLocation protoFieldLocation =
          SOURCE_CODE_INFO_PARSER.getLocation(fieldDescriptor);
      if (!Objects.isNull(protoFieldLocation)
          && !Strings.isNullOrEmpty(protoFieldLocation.getLeadingComments())) {
        fieldBuilder.setDescription(protoFieldLocation.getLeadingComments());
      }
    }

    return fieldBuilder
        .setName(fieldDescriptor.getName())
        .setType(TypeParser.parseType(fieldDescriptor))
        .setIsMessage(fieldDescriptor.getJavaType() == FieldDescriptor.JavaType.MESSAGE)
        .setIsEnum(fieldDescriptor.getJavaType() == FieldDescriptor.JavaType.ENUM)
        .setIsContainedInOneof(fieldDescriptor.getContainingOneof() != null)
        .setIsRepeated(fieldDescriptor.isRepeated())
        .setIsMap(fieldDescriptor.isMapField())
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

  private static String parseServiceJavaPackage(CodeGeneratorRequest request) {
    Map<String, Integer> javaPackageCount = new HashMap<>();
    Map<String, FileDescriptor> fileDescriptors = getFilesToGenerate(request);
    for (String fileToGenerate : request.getFileToGenerateList()) {
      FileDescriptor fileDescriptor =
          Preconditions.checkNotNull(
              fileDescriptors.get(fileToGenerate),
              "Missing file descriptor for [%s]",
              fileToGenerate);

      String javaPackage = fileDescriptor.getOptions().getJavaPackage();
      if (Strings.isNullOrEmpty(javaPackage)) {
        continue;
      }
      if (javaPackageCount.containsKey(javaPackage)) {
        javaPackageCount.put(javaPackage, javaPackageCount.get(javaPackage) + 1);
      } else {
        javaPackageCount.put(javaPackage, 1);
      }
    }

    String finalJavaPackage =
        javaPackageCount.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    Preconditions.checkState(
        !Strings.isNullOrEmpty(finalJavaPackage), "No service Java package found");
    return finalJavaPackage;
  }
}
