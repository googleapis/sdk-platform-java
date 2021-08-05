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
import com.google.api.DocumentationRule;
import com.google.api.HttpRule;
import com.google.api.ResourceDescriptor;
import com.google.api.ResourceProto;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.GapicBatchingSettings;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicLanguageSettings;
import com.google.api.generator.gapic.model.GapicLroRetrySettings;
import com.google.api.generator.gapic.model.GapicServiceConfig;
import com.google.api.generator.gapic.model.HttpBindings;
import com.google.api.generator.gapic.model.LongrunningOperation;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.ResourceReference;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.model.SourceCodeInfoLocation;
import com.google.api.generator.gapic.model.Transport;
import com.google.api.generator.gapic.utils.ResourceNameConstants;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
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
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
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
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Parser {
  private static final String COMMA = ",";
  private static final String COLON = ":";
  private static final String DEFAULT_PORT = "443";
  private static final String DOT = ".";
  private static final String SLASH = "/";

  private static final ResourceName WILDCARD_RESOURCE_NAME =
      ResourceName.createWildcard("*", "com.google.api.wildcard.placeholder");

  // Mirrors the sanitizer allowlist.
  private static final Set<String> MIXIN_ALLOWLIST =
      ImmutableSet.of(
          "google.iam.v1.IAMPolicy",
          "google.longrunning.Operations",
          "google.cloud.location.Locations");
  // These must be kept in sync with the above protos' java_package options.
  private static final Set<String> MIXIN_JAVA_PACKAGE_ALLOWLIST =
      ImmutableSet.of("com.google.iam.v1", "com.google.longrunning", "com.google.cloud.location");

  // Allow other parsers to access this.
  protected static final SourceCodeInfoParser SOURCE_CODE_INFO_PARSER = new SourceCodeInfoParser();

  static class GapicParserException extends RuntimeException {
    public GapicParserException(String errorMessage) {
      super(errorMessage);
    }
  }

  public static GapicContext parse(CodeGeneratorRequest request) {
    Optional<String> gapicYamlConfigPathOpt =
        PluginArgumentParser.parseGapicYamlConfigPath(request);
    Optional<List<GapicBatchingSettings>> batchingSettingsOpt =
        BatchingSettingsConfigParser.parse(gapicYamlConfigPathOpt);
    Optional<List<GapicLroRetrySettings>> lroRetrySettingsOpt =
        GapicLroRetrySettingsParser.parse(gapicYamlConfigPathOpt);
    Optional<GapicLanguageSettings> languageSettingsOpt =
        GapicLanguageSettingsParser.parse(gapicYamlConfigPathOpt);
    Optional<String> transportOpt = PluginArgumentParser.parseTransport(request);

    boolean willGenerateMetadata = PluginArgumentParser.hasMetadataFlag(request);

    Optional<String> serviceConfigPathOpt = PluginArgumentParser.parseJsonConfigPath(request);
    String serviceConfigPath = serviceConfigPathOpt.isPresent() ? serviceConfigPathOpt.get() : null;
    Optional<GapicServiceConfig> serviceConfigOpt = ServiceConfigParser.parse(serviceConfigPath);
    if (serviceConfigOpt.isPresent()) {
      GapicServiceConfig serviceConfig = serviceConfigOpt.get();
      serviceConfig.setLroRetrySettings(lroRetrySettingsOpt);
      serviceConfig.setBatchingSettings(batchingSettingsOpt);
      serviceConfig.setLanguageSettings(languageSettingsOpt);
      serviceConfigOpt = Optional.of(serviceConfig);
    }

    Optional<String> serviceYamlConfigPathOpt =
        PluginArgumentParser.parseServiceYamlConfigPath(request);
    Optional<com.google.api.Service> serviceYamlProtoOpt =
        serviceYamlConfigPathOpt.isPresent()
            ? ServiceYamlParser.parse(serviceYamlConfigPathOpt.get())
            : Optional.empty();

    // Collect the resource references seen in messages.
    Set<ResourceReference> outputResourceReferencesSeen = new HashSet<>();
    // Keep message and resource name parsing separate for cleaner logic.
    // While this takes an extra pass through the protobufs, the extra time is relatively trivial
    // and is worth the larger reduced maintenance cost.
    Map<String, Message> messages = parseMessages(request, outputResourceReferencesSeen);

    Map<String, ResourceName> resourceNames = parseResourceNames(request);
    messages = updateResourceNamesInMessages(messages, resourceNames.values());

    // Contains only resource names that are actually used. That is, resource name definitions
    // or references that are simply defined, but not used, will not have corresponding Java helper
    // classes generated.
    Set<ResourceName> outputArgResourceNames = new HashSet<>();
    List<Service> mixinServices = new ArrayList<>();
    Transport transport = Transport.parse(transportOpt.orElse(Transport.GRPC.toString()));
    List<Service> services =
        parseServices(
            request,
            messages,
            resourceNames,
            outputArgResourceNames,
            serviceYamlProtoOpt,
            serviceConfigOpt,
            mixinServices,
            transport);

    Preconditions.checkState(!services.isEmpty(), "No services found to generate");
    return GapicContext.builder()
        .setServices(services)
        .setMixinServices(mixinServices)
        .setMessages(messages)
        .setResourceNames(resourceNames)
        .setHelperResourceNames(outputArgResourceNames)
        .setServiceConfig(serviceConfigOpt.isPresent() ? serviceConfigOpt.get() : null)
        .setGapicMetadataEnabled(willGenerateMetadata)
        .setServiceYamlProto(serviceYamlProtoOpt.isPresent() ? serviceYamlProtoOpt.get() : null)
        .setTransport(transport)
        .build();
  }

  public static List<Service> parseServices(
      CodeGeneratorRequest request,
      Map<String, Message> messageTypes,
      Map<String, ResourceName> resourceNames,
      Set<ResourceName> outputArgResourceNames,
      Optional<com.google.api.Service> serviceYamlProtoOpt,
      Optional<GapicServiceConfig> serviceConfigOpt,
      List<Service> outputMixinServices,
      Transport transport) {
    Map<String, FileDescriptor> fileDescriptors = getFilesToGenerate(request);

    List<Service> services = new ArrayList<>();
    for (String fileToGenerate : request.getFileToGenerateList()) {
      FileDescriptor fileDescriptor =
          Preconditions.checkNotNull(
              fileDescriptors.get(fileToGenerate),
              "Missing file descriptor for [%s]",
              fileToGenerate);

      services.addAll(
          parseService(
              fileDescriptor,
              messageTypes,
              resourceNames,
              serviceYamlProtoOpt,
              serviceConfigOpt,
              outputArgResourceNames,
              transport));
    }

    // Prevent codegen for mixed-in services if there are other services present, since that is an
    // indicator that we are not generating a GAPIC client for the mixed-in service on its own.
    Function<Service, String> serviceFullNameFn =
        s -> String.format("%s.%s", s.protoPakkage(), s.name());
    Set<Service> blockedCodegenMixinApis = new HashSet<>();
    Set<Service> definedServices = new HashSet<>();
    for (Service s : services) {
      if (MIXIN_ALLOWLIST.contains(serviceFullNameFn.apply(s))) {
        blockedCodegenMixinApis.add(s);
      } else {
        definedServices.add(s);
      }
    }
    // It's very unlikely the blocklisted APIs will contain the other, or any other service.
    boolean servicesContainBlocklistedApi =
        !blockedCodegenMixinApis.isEmpty() && !definedServices.isEmpty();
    // Service names that are stated in the YAML file (as mixins). Used to filter
    // blockedCodegenMixinApis.
    Set<String> mixedInApis =
        !serviceYamlProtoOpt.isPresent()
            ? Collections.emptySet()
            : serviceYamlProtoOpt.get().getApisList().stream()
                .filter(a -> MIXIN_ALLOWLIST.contains(a.getName()))
                .map(a -> a.getName())
                .collect(Collectors.toSet());
    // Holds the methods to be mixed in.
    // Key: proto_package.ServiceName.RpcName.
    // Value: HTTP rules, which clobber those in the proto.
    // Assumes that http.rules.selector always specifies RPC names in the above format.
    Map<String, HttpBindings> mixedInMethodsToHttpRules = new HashMap<>();
    Map<String, String> mixedInMethodsToDocs = new HashMap<>();
    // Parse HTTP rules and documentation, which will override the proto.
    if (serviceYamlProtoOpt.isPresent()) {
      for (HttpRule httpRule : serviceYamlProtoOpt.get().getHttp().getRulesList()) {
        HttpBindings httpBindings = HttpRuleParser.parseHttpRule(httpRule);
        if (httpBindings == null) {
          continue;
        }
        for (String rpcFullNameRaw : httpRule.getSelector().split(",")) {
          String rpcFullName = rpcFullNameRaw.trim();
          mixedInMethodsToHttpRules.put(rpcFullName, httpBindings);
        }
      }
      for (DocumentationRule docRule :
          serviceYamlProtoOpt.get().getDocumentation().getRulesList()) {
        for (String rpcFullNameRaw : docRule.getSelector().split(",")) {
          String rpcFullName = rpcFullNameRaw.trim();
          mixedInMethodsToDocs.put(rpcFullName, docRule.getDescription());
        }
      }
    }

    // Sort potential mixin services alphabetically.
    List<Service> orderedBlockedCodegenMixinApis =
        blockedCodegenMixinApis.stream()
            .sorted((s1, s2) -> s2.name().compareTo(s1.name()))
            .collect(Collectors.toList());

    Set<String> apiDefinedRpcs = new HashSet<>();
    for (Service service : services) {
      if (orderedBlockedCodegenMixinApis.contains(service)) {
        continue;
      }
      apiDefinedRpcs.addAll(
          service.methods().stream().map(m -> m.name()).collect(Collectors.toSet()));
    }
    // Mix-in APIs only if the protos are present and they're defined in the service.yaml file.
    Set<Service> outputMixinServiceSet = new HashSet<>();
    if (servicesContainBlocklistedApi && !mixedInApis.isEmpty()) {
      for (int i = 0; i < services.size(); i++) {
        Service originalService = services.get(i);
        List<Method> updatedOriginalServiceMethods = new ArrayList<>(originalService.methods());
        // If mixin APIs are present, add the methods to all other services.
        for (Service mixinService : orderedBlockedCodegenMixinApis) {
          final String mixinServiceFullName = serviceFullNameFn.apply(mixinService);
          if (!mixedInApis.contains(mixinServiceFullName)) {
            continue;
          }
          Function<Method, String> methodToFullProtoNameFn =
              m -> String.format("%s.%s", mixinServiceFullName, m.name());
          // Filter mixed-in methods based on those listed in the HTTP rules section of
          // service.yaml.
          List<Method> updatedMixinMethods =
              mixinService.methods().stream()
                  // Mixin method inclusion is based on the HTTP rules list in service.yaml.
                  .filter(
                      m -> mixedInMethodsToHttpRules.containsKey(methodToFullProtoNameFn.apply(m)))
                  .map(
                      m -> {
                        // HTTP rules and RPC documentation in the service.yaml file take
                        // precedence.
                        String fullMethodName = methodToFullProtoNameFn.apply(m);
                        HttpBindings httpBindings =
                            mixedInMethodsToHttpRules.containsKey(fullMethodName)
                                ? mixedInMethodsToHttpRules.get(fullMethodName)
                                : m.httpBindings();
                        String docs =
                            mixedInMethodsToDocs.containsKey(fullMethodName)
                                ? mixedInMethodsToDocs.get(fullMethodName)
                                : m.description();
                        return m.toBuilder()
                            .setHttpBindings(httpBindings)
                            .setDescription(docs)
                            .build();
                      })
                  .collect(Collectors.toList());
          // Overridden RPCs defined in the protos take precedence.
          updatedMixinMethods.stream()
              .filter(m -> !apiDefinedRpcs.contains(m.name()))
              .forEach(
                  m ->
                      updatedOriginalServiceMethods.add(
                          m.toBuilder()
                              .setMixedInApiName(serviceFullNameFn.apply(mixinService))
                              .build()));
          // Sort by method name, to ensure a deterministic method ordering (for tests).
          updatedMixinMethods =
              updatedMixinMethods.stream()
                  .sorted((m1, m2) -> m2.name().compareTo(m1.name()))
                  .collect(Collectors.toList());
          outputMixinServiceSet.add(
              mixinService.toBuilder().setMethods(updatedMixinMethods).build());
        }
        services.set(
            i, originalService.toBuilder().setMethods(updatedOriginalServiceMethods).build());
      }
    }

    if (servicesContainBlocklistedApi) {
      services =
          services.stream()
              .filter(s -> !MIXIN_ALLOWLIST.contains(serviceFullNameFn.apply(s)))
              .collect(Collectors.toList());
    }

    // Use a list to ensure ordering for deterministic tests.
    outputMixinServices.addAll(
        outputMixinServiceSet.stream()
            .sorted((s1, s2) -> s2.name().compareTo(s1.name()))
            .collect(Collectors.toSet()));
    return services;
  }

  @VisibleForTesting
  public static List<Service> parseService(
      FileDescriptor fileDescriptor,
      Map<String, Message> messageTypes,
      Map<String, ResourceName> resourceNames,
      Optional<com.google.api.Service> serviceYamlProtoOpt,
      Set<ResourceName> outputArgResourceNames) {
    return parseService(
        fileDescriptor,
        messageTypes,
        resourceNames,
        serviceYamlProtoOpt,
        Optional.empty(),
        outputArgResourceNames,
        Transport.GRPC);
  }

  public static List<Service> parseService(
      FileDescriptor fileDescriptor,
      Map<String, Message> messageTypes,
      Map<String, ResourceName> resourceNames,
      Optional<com.google.api.Service> serviceYamlProtoOpt,
      Optional<GapicServiceConfig> serviceConfigOpt,
      Set<ResourceName> outputArgResourceNames,
      Transport transport) {

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
              } else if (serviceYamlProtoOpt.isPresent()) {
                // Fall back to the DNS name supplied in the service .yaml config.
                defaultHost = serviceYamlProtoOpt.get().getName();
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

              boolean isDeprecated = false;
              if (serviceOptions.hasDeprecated()) {
                isDeprecated = serviceOptions.getDeprecated();
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

              String serviceName = s.getName();
              String overriddenServiceName = serviceName;
              String pakkage = TypeParser.getPackage(fileDescriptor);
              String originalJavaPackage = pakkage;
              // Override Java package with that specified in gapic.yaml.
              if (serviceConfigOpt.isPresent()
                  && serviceConfigOpt.get().getLanguageSettingsOpt().isPresent()) {
                GapicLanguageSettings languageSettings =
                    serviceConfigOpt.get().getLanguageSettingsOpt().get();
                pakkage = languageSettings.pakkage();
                overriddenServiceName =
                    languageSettings.getJavaServiceName(fileDescriptor.getPackage(), s.getName());
              }
              return serviceBuilder
                  .setName(serviceName)
                  .setOverriddenName(overriddenServiceName)
                  .setDefaultHost(defaultHost)
                  .setOauthScopes(oauthScopes)
                  .setPakkage(pakkage)
                  .setOriginalJavaPackage(originalJavaPackage)
                  .setProtoPakkage(fileDescriptor.getPackage())
                  .setIsDeprecated(isDeprecated)
                  .setMethods(
                      parseMethods(
                          s,
                          pakkage,
                          messageTypes,
                          resourceNames,
                          serviceConfigOpt,
                          outputArgResourceNames,
                          transport))
                  .build();
            })
        .collect(Collectors.toList());
  }

  public static Map<String, Message> parseMessages(
      CodeGeneratorRequest request, Set<ResourceReference> outputResourceReferencesSeen) {
    Map<String, FileDescriptor> fileDescriptors = getFilesToGenerate(request);
    Map<String, Message> messages = new HashMap<>();
    // Look for message types amongst all the protos, not just the ones to generate. This will
    // ensure we track commonly-used protos like Empty.
    for (FileDescriptor fileDescriptor : fileDescriptors.values()) {
      messages.putAll(parseMessages(fileDescriptor, outputResourceReferencesSeen));
    }

    return messages;
  }

  // TODO(miraleung): Propagate the internal method to all tests, and remove this wrapper.
  public static Map<String, Message> parseMessages(FileDescriptor fileDescriptor) {
    return parseMessages(fileDescriptor, new HashSet<>());
  }

  public static Map<String, Message> parseMessages(
      FileDescriptor fileDescriptor, Set<ResourceReference> outputResourceReferencesSeen) {
    // TODO(miraleung): Preserve nested type and package data in the type key.
    Map<String, Message> messages = new HashMap<>();
    for (Descriptor messageDescriptor : fileDescriptor.getMessageTypes()) {
      messages.putAll(parseMessages(messageDescriptor, outputResourceReferencesSeen));
    }
    // We treat enums as messages since we primarily care only about the type representation.
    for (EnumDescriptor enumDescriptor : fileDescriptor.getEnumTypes()) {
      String name = enumDescriptor.getName();
      List<EnumValueDescriptor> valueDescriptors = enumDescriptor.getValues();
      TypeNode enumType = TypeParser.parseType(enumDescriptor);
      messages.put(
          enumType.reference().fullName(),
          Message.builder()
              .setType(enumType)
              .setName(name)
              .setFullProtoName(enumDescriptor.getFullName())
              .setEnumValues(
                  valueDescriptors.stream().map(v -> v.getName()).collect(Collectors.toList()),
                  valueDescriptors.stream().map(v -> v.getNumber()).collect(Collectors.toList()))
              .build());
    }
    return messages;
  }

  private static Map<String, Message> parseMessages(
      Descriptor messageDescriptor, Set<ResourceReference> outputResourceReferencesSeen) {
    return parseMessages(messageDescriptor, outputResourceReferencesSeen, new ArrayList<String>());
  }

  private static Map<String, Message> parseMessages(
      Descriptor messageDescriptor,
      Set<ResourceReference> outputResourceReferencesSeen,
      List<String> outerNestedTypes) {
    Map<String, Message> messages = new HashMap<>();
    String messageName = messageDescriptor.getName();
    if (!messageDescriptor.getNestedTypes().isEmpty()) {
      for (Descriptor nestedMessage : messageDescriptor.getNestedTypes()) {
        if (isMapType(nestedMessage)) {
          continue;
        }
        List<String> currentNestedTypes = new ArrayList<>(outerNestedTypes);
        currentNestedTypes.add(messageName);
        messages.putAll(
            parseMessages(nestedMessage, outputResourceReferencesSeen, currentNestedTypes));
      }
    }
    TypeNode messageType = TypeParser.parseType(messageDescriptor);
    messages.put(
        messageType.reference().fullName(),
        Message.builder()
            .setType(messageType)
            .setName(messageName)
            .setFullProtoName(messageDescriptor.getFullName())
            .setFields(parseFields(messageDescriptor, outputResourceReferencesSeen))
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
   * @param resources A list of ResourceName POJOs.
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
    String javaPackage = parseServiceJavaPackage(request);
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
      Optional<GapicServiceConfig> serviceConfigOpt,
      Set<ResourceName> outputArgResourceNames,
      Transport transport) {
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

      boolean isDeprecated = false;
      if (protoMethod.getOptions().hasDeprecated()) {
        isDeprecated = protoMethod.getOptions().getDeprecated();
      }

      Message inputMessage = messageTypes.get(inputType.reference().fullName());
      Preconditions.checkNotNull(
          inputMessage, String.format("No message found for %s", inputType.reference().fullName()));
      HttpBindings httpBindings = HttpRuleParser.parse(protoMethod, inputMessage, messageTypes);
      boolean isBatching =
          !serviceConfigOpt.isPresent()
              ? false
              : serviceConfigOpt
                  .get()
                  .hasBatchingSetting(
                      /* protoPakkage */ protoMethod.getFile().getPackage(),
                      serviceDescriptor.getName(),
                      protoMethod.getName());

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
              .setIsBatching(isBatching)
              .setPageSizeFieldName(parsePageSizeFieldName(protoMethod, messageTypes, transport))
              .setIsDeprecated(isDeprecated)
              .build());

      // Any input type that has a resource reference will need a resource name helper class.
      for (Field field : inputMessage.fields()) {
        if (field.hasResourceReference()) {
          String resourceTypeString = field.resourceReference().resourceTypeString();
          ResourceName resourceName = null;
          // Support older resource_references that specify only the final typename, e.g. FooBar
          // versus example.com/FooBar.
          if (resourceTypeString.indexOf(SLASH) < 0) {
            Optional<String> actualResourceTypeNameOpt =
                resourceNames.keySet().stream()
                    .filter(k -> k.substring(k.lastIndexOf(SLASH) + 1).equals(resourceTypeString))
                    .findFirst();
            if (actualResourceTypeNameOpt.isPresent()) {
              resourceName = resourceNames.get(actualResourceTypeNameOpt.get());
            }
          } else {
            resourceName = resourceNames.get(resourceTypeString);
          }

          if (ResourceNameConstants.WILDCARD_PATTERN.equals(resourceTypeString)) {
            resourceName = WILDCARD_RESOURCE_NAME;
          } else {
            Preconditions.checkNotNull(
                resourceName,
                String.format(
                    "Resource name %s not found; parsing field %s in message %s in method %s",
                    resourceTypeString, field.name(), inputMessage.name(), protoMethod.getName()));
          }

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

    // These can be short names (e.g. FooMessage) or fully-qualified names with the *proto* package.
    String responseTypeName = lroInfo.getResponseType();
    String metadataTypeName = lroInfo.getMetadataType();

    int lastDotIndex = responseTypeName.lastIndexOf('.');
    boolean isResponseTypeNameShortOnly = lastDotIndex < 0;
    String responseTypeShortName =
        lastDotIndex >= 0 ? responseTypeName.substring(lastDotIndex + 1) : responseTypeName;

    lastDotIndex = metadataTypeName.lastIndexOf('.');
    boolean isMetadataTypeNameShortOnly = lastDotIndex < 0;
    String metadataTypeShortName =
        lastDotIndex >= 0 ? metadataTypeName.substring(lastDotIndex + 1) : metadataTypeName;

    Message responseMessage = null;
    Message metadataMessage = null;

    // The messageTypes map keys to the Java fully-qualified name.
    for (Map.Entry<String, Message> messageEntry : messageTypes.entrySet()) {
      String messageKey = messageEntry.getKey();
      int messageLastDotIndex = messageEntry.getKey().lastIndexOf('.');
      String messageShortName =
          messageLastDotIndex >= 0 ? messageKey.substring(messageLastDotIndex + 1) : messageKey;
      if (responseMessage == null) {
        if (isResponseTypeNameShortOnly && responseTypeName.equals(messageShortName)) {
          responseMessage = messageEntry.getValue();
        } else if (!isResponseTypeNameShortOnly && responseTypeShortName.equals(messageShortName)) {
          // Ensure that the full proto name matches.
          Message candidateMessage = messageEntry.getValue();
          if (candidateMessage.fullProtoName().equals(responseTypeName)) {
            responseMessage = candidateMessage;
          }
        }
      }
      if (metadataMessage == null) {
        if (isMetadataTypeNameShortOnly && metadataTypeName.equals(messageShortName)) {
          metadataMessage = messageEntry.getValue();
        } else if (!isMetadataTypeNameShortOnly && metadataTypeShortName.equals(messageShortName)) {
          // Ensure that the full proto name matches.
          Message candidateMessage = messageEntry.getValue();
          if (candidateMessage.fullProtoName().equals(metadataTypeName)) {
            metadataMessage = candidateMessage;
          }
        }
      }
    }

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
  static String parsePageSizeFieldName(
      MethodDescriptor methodDescriptor, Map<String, Message> messageTypes, Transport transport) {
    TypeNode inputMessageType = TypeParser.parseType(methodDescriptor.getInputType());
    TypeNode outputMessageType = TypeParser.parseType(methodDescriptor.getOutputType());
    Message inputMessage = messageTypes.get(inputMessageType.reference().fullName());
    Message outputMessage = messageTypes.get(outputMessageType.reference().fullName());

    // This should technically handle the absence of either of these fields (aip.dev/158), but we
    // gate on their collective presence to ensure the generated surface is backawrds-compatible
    // with monolith-gnerated libraries.
    String pagedFieldName = null;

    if (inputMessage.fieldMap().containsKey("page_token")
        && outputMessage.fieldMap().containsKey("next_page_token")) {
      // List of potential field names representing page size.
      // page_size gets priority over max_results if both are present
      List<String> fieldNames = new ArrayList<>();
      fieldNames.add("page_size");
      if (transport == Transport.REST) {
        fieldNames.add("max_results");
      }
      for (String fieldName : fieldNames) {
        if (pagedFieldName == null && inputMessage.fieldMap().containsKey(fieldName)) {
          pagedFieldName = fieldName;
        }
      }
    }
    return pagedFieldName;
  }

  @VisibleForTesting
  static String sanitizeDefaultHost(String rawDefaultHost) {
    if (rawDefaultHost.indexOf(COLON) >= 0) {
      // A port is already present, just return the existing string.
      return rawDefaultHost;
    }
    return String.format("%s:%s", rawDefaultHost, DEFAULT_PORT);
  }

  private static List<Field> parseFields(
      Descriptor messageDescriptor, Set<ResourceReference> outputResourceReferencesSeen) {
    List<FieldDescriptor> fields = new ArrayList<>(messageDescriptor.getFields());
    // Sort by ascending field index order. This is important for paged responses, where the first
    // repeated type is taken.
    fields.sort((f1, f2) -> f1.getIndex() - f2.getIndex());

    // Mirror protoc's name conflict resolution behavior for fields.
    // If a singular field's name equals that of a repeated field with "Count" or "List" suffixed,
    // append the protobuf's field number to both fields' names.
    // See:
    // https://github.com/protocolbuffers/protobuf/blob/9df42757f97da9f748a464deeda96427a8f7ade0/src/google/protobuf/compiler/java/java_context.cc#L60
    Map<String, Integer> repeatedFieldNamesToNumber =
        fields.stream()
            .filter(f -> f.isRepeated())
            .collect(Collectors.toMap(f -> f.getName(), f -> f.getNumber()));
    Set<Integer> fieldNumbersWithConflicts = new HashSet<>();
    for (FieldDescriptor field : fields) {
      Set<String> conflictingRepeatedFieldNames =
          repeatedFieldNamesToNumber.keySet().stream()
              .filter(
                  n -> field.getName().equals(n + "_count") || field.getName().equals(n + "_list"))
              .collect(Collectors.toSet());
      if (!conflictingRepeatedFieldNames.isEmpty()) {
        fieldNumbersWithConflicts.addAll(
            conflictingRepeatedFieldNames.stream()
                .map(n -> repeatedFieldNamesToNumber.get(n))
                .collect(Collectors.toSet()));
        fieldNumbersWithConflicts.add(field.getNumber());
      }
    }

    return fields.stream()
        .map(
            f ->
                parseField(
                    f,
                    messageDescriptor,
                    fieldNumbersWithConflicts.contains(f.getNumber()),
                    outputResourceReferencesSeen))
        .collect(Collectors.toList());
  }

  private static Field parseField(
      FieldDescriptor fieldDescriptor,
      Descriptor messageDescriptor,
      boolean hasFieldNameConflict,
      Set<ResourceReference> outputResourceReferencesSeen) {
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
      outputResourceReferencesSeen.add(resourceReference);

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

    // Mirror protoc's name conflict resolution behavior for fields.
    // For more context, trace hasFieldNameConflict back to where it gets passed in above.
    String actualFieldName =
        hasFieldNameConflict
            ? fieldDescriptor.getName() + fieldDescriptor.getNumber()
            : fieldDescriptor.getName();

    return fieldBuilder
        .setName(actualFieldName)
        .setOriginalName(fieldDescriptor.getName())
        .setType(TypeParser.parseType(fieldDescriptor))
        .setIsMessage(fieldDescriptor.getJavaType() == FieldDescriptor.JavaType.MESSAGE)
        .setIsEnum(fieldDescriptor.getJavaType() == FieldDescriptor.JavaType.ENUM)
        .setIsContainedInOneof(
            fieldDescriptor.getContainingOneof() != null
                && !fieldDescriptor.getContainingOneof().isSynthetic())
        .setIsProto3Optional(
            fieldDescriptor.getContainingOneof() != null
                && fieldDescriptor.getContainingOneof().isSynthetic())
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

    // Filter out mixin packages.
    Map<String, Integer> processedJavaPackageCount =
        javaPackageCount.entrySet().stream()
            .filter(e -> !MIXIN_JAVA_PACKAGE_ALLOWLIST.contains(e.getKey()))
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

    // An empty map indicates that only mixin packages were present, which means that we're
    // generating a standalone client for a mixin.
    if (processedJavaPackageCount.isEmpty()) {
      processedJavaPackageCount = javaPackageCount;
    }

    String finalJavaPackage =
        processedJavaPackageCount.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .get()
            .getKey();
    Preconditions.checkState(
        !Strings.isNullOrEmpty(finalJavaPackage), "No service Java package found");
    return finalJavaPackage;
  }

  /**
   * Retrieves the nested type name from a fully-qualified protobuf type name. Example:
   * google.ads.googleads.v3.resources.MutateJob.MutateJobMetadata > MutateJob.MutateJobMetadata.
   */
  @VisibleForTesting
  static String parseNestedProtoTypeName(String fullyQualifiedName) {
    if (!fullyQualifiedName.contains(DOT)) {
      return fullyQualifiedName;
    }
    // Find the first component in CapitalCamelCase. Assumes that proto package
    // components must be in all lowercase and type names are in CapitalCamelCase.
    String[] components = fullyQualifiedName.split("\\.");
    List<String> nestedTypeComponents =
        IntStream.range(0, components.length)
            .filter(i -> Character.isUpperCase(components[i].charAt(0)))
            .mapToObj(i -> components[i])
            .collect(Collectors.toList());
    return String.join(".", nestedTypeComponents);
  }
}
