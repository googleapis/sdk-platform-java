// Copyright 2021 Google LLC
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

package com.google.api.generator.test.protoloader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.api.generator.gapic.model.GapicBatchingSettings;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicServiceConfig;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.model.Transport;
import com.google.api.generator.gapic.protoparser.BatchingSettingsConfigParser;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.gapic.protoparser.ServiceConfigParser;
import com.google.api.generator.gapic.protoparser.ServiceYamlParser;
import com.google.auto.populate.field.AutoPopulateFieldTestingOuterClass;
import com.google.bookshop.v1beta1.BookshopProto;
import com.google.explicit.dynamic.routing.header.ExplicitDynamicRoutingHeaderTestingOuterClass;
import com.google.logging.v2.LogEntryProto;
import com.google.logging.v2.LoggingConfigProto;
import com.google.logging.v2.LoggingMetricsProto;
import com.google.logging.v2.LoggingProto;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.pubsub.v1.PubsubProto;
import com.google.showcase.v1beta1.EchoOuterClass;
import com.google.showcase.v1beta1.IdentityOuterClass;
import com.google.showcase.v1beta1.MessagingOuterClass;
import com.google.showcase.v1beta1.TestingOuterClass;
import com.google.testdata.v1.DeprecatedServiceOuterClass;
import com.google.testgapic.v1beta1.NestedMessageProto;
import com.google.types.testing.TypesTestingProto;
import google.cloud.CommonResources;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TestProtoLoader {

  private static final TestProtoLoader INSTANCE =
      new TestProtoLoader(Transport.GRPC, "src/test/resources/");
  private static final String ECHO_SERVICE_DESCRIPTION =
      "This service is used showcase the four main types of rpcs - unary, server\n"
          + " side streaming, client side streaming, and bidirectional streaming. This\n"
          + " service also exposes methods that explicitly implement server delay, and\n"
          + " paginated calls. Set the 'showcase-trailer' metadata key on any method\n"
          + " to have the values echoed in the response trailers.";
  private final String testFilesDirectory;
  private final Transport transport;

  protected TestProtoLoader(Transport transport, String testFilesDirectory) {
    this.testFilesDirectory = testFilesDirectory;
    this.transport = transport;
  }

  public static TestProtoLoader instance() {
    return INSTANCE;
  }

  public GapicContext parseDeprecatedService() {
    FileDescriptor fileDescriptor = DeprecatedServiceOuterClass.getDescriptor();
    ServiceDescriptor serviceDescriptor = fileDescriptor.getServices().get(0);
    assertEquals(serviceDescriptor.getName(), "DeprecatedService");

    Map<String, Message> messageTypes = Parser.parseMessages(fileDescriptor);
    Map<String, ResourceName> resourceNames = new HashMap<>();
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            fileDescriptor, messageTypes, resourceNames, Optional.empty(), outputResourceNames);

    String jsonFilename = "deprecated_service_grpc_service_config.json";
    Path jsonPath = Paths.get(testFilesDirectory, jsonFilename);
    Optional<GapicServiceConfig> configOpt = ServiceConfigParser.parse(jsonPath.toString());
    assertTrue(configOpt.isPresent());
    GapicServiceConfig config = configOpt.get();

    return GapicContext.builder()
        .setMessages(messageTypes)
        .setResourceNames(resourceNames)
        .setServices(services)
        .setServiceConfig(config)
        .setHelperResourceNames(outputResourceNames)
        .setTransport(transport)
        .build();
  }

  public GapicContext parseBookshopService() {
    FileDescriptor fileDescriptor = BookshopProto.getDescriptor();
    ServiceDescriptor serviceDescriptor = fileDescriptor.getServices().get(0);
    assertEquals(serviceDescriptor.getName(), "Bookshop");

    Map<String, Message> messageTypes = Parser.parseMessages(fileDescriptor);
    Map<String, ResourceName> resourceNames = new HashMap<>();
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            fileDescriptor, messageTypes, resourceNames, Optional.empty(), outputResourceNames);

    String jsonFilename = "bookshop_grpc_service_config.json";
    Path jsonPath = Paths.get(testFilesDirectory, jsonFilename);
    Optional<GapicServiceConfig> configOpt = ServiceConfigParser.parse(jsonPath.toString());
    assertTrue(configOpt.isPresent());
    GapicServiceConfig config = configOpt.get();

    return GapicContext.builder()
        .setMessages(messageTypes)
        .setResourceNames(resourceNames)
        .setServices(services)
        .setServiceConfig(config)
        .setHelperResourceNames(outputResourceNames)
        .setTransport(transport)
        .build();
  }

  public GapicContext parseNestedMessage() {
    FileDescriptor fileDescriptor = TypesTestingProto.getDescriptor();
    ServiceDescriptor serviceDescriptor = fileDescriptor.getServices().get(0);
    assertEquals(serviceDescriptor.getName(), "NestedMessageService");
    Map<String, Message> messageTypes = Parser.parseMessages(fileDescriptor);

    FileDescriptor messageFileDescriptor = NestedMessageProto.getDescriptor();
    messageTypes.putAll(Parser.parseMessages(messageFileDescriptor));

    Map<String, ResourceName> resourceNames = new HashMap<>();
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            fileDescriptor, messageTypes, resourceNames, Optional.empty(), outputResourceNames);

    return GapicContext.builder()
        .setMessages(messageTypes)
        .setResourceNames(resourceNames)
        .setServices(services)
        .setHelperResourceNames(outputResourceNames)
        .setTransport(transport)
        .build();
  }

  public GapicContext parseShowcaseEcho() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    ServiceDescriptor echoServiceDescriptor = echoFileDescriptor.getServices().get(0);
    assertEquals(echoServiceDescriptor.getName(), "Echo");

    String serviceYamlFilename = "echo_v1beta1.yaml";
    Path serviceYamlPath = Paths.get(testFilesDirectory, serviceYamlFilename);
    Optional<com.google.api.Service> serviceYamlOpt =
        ServiceYamlParser.parse(serviceYamlPath.toString());
    assertTrue(serviceYamlOpt.isPresent());

    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            echoFileDescriptor, messageTypes, resourceNames, serviceYamlOpt, outputResourceNames);

    // Explicitly adds service description, since this is not parsed from source code location
    // in test protos, as it would from a protoc CodeGeneratorRequest
    List<Service> servicesWithDescription =
        services.stream()
            .map(s -> s.toBuilder().setDescription(ECHO_SERVICE_DESCRIPTION).build())
            .collect(Collectors.toList());

    String jsonFilename = "showcase_grpc_service_config.json";
    Path jsonPath = Paths.get(testFilesDirectory, jsonFilename);
    Optional<GapicServiceConfig> configOpt = ServiceConfigParser.parse(jsonPath.toString());
    assertTrue(configOpt.isPresent());
    GapicServiceConfig config = configOpt.get();

    return GapicContext.builder()
        .setMessages(messageTypes)
        .setResourceNames(resourceNames)
        .setServices(servicesWithDescription)
        .setServiceConfig(config)
        .setHelperResourceNames(outputResourceNames)
        .setTransport(transport)
        .build();
  }

  public GapicContext parseShowcaseIdentity() {
    FileDescriptor fileDescriptor = IdentityOuterClass.getDescriptor();
    ServiceDescriptor identityService = fileDescriptor.getServices().get(0);
    assertEquals(identityService.getName(), "Identity");

    Map<String, Message> messageTypes = Parser.parseMessages(fileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(fileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            fileDescriptor, messageTypes, resourceNames, Optional.empty(), outputResourceNames);

    return GapicContext.builder()
        .setMessages(messageTypes)
        .setResourceNames(resourceNames)
        .setServices(services)
        .setHelperResourceNames(outputResourceNames)
        .setTransport(transport)
        .build();
  }

  public GapicContext parseShowcaseMessaging() {
    FileDescriptor fileDescriptor = MessagingOuterClass.getDescriptor();
    ServiceDescriptor messagingService = fileDescriptor.getServices().get(0);
    assertEquals(messagingService.getName(), "Messaging");

    Map<String, Message> messageTypes = Parser.parseMessages(fileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(fileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            fileDescriptor, messageTypes, resourceNames, Optional.empty(), outputResourceNames);

    return GapicContext.builder()
        .setMessages(messageTypes)
        .setResourceNames(resourceNames)
        .setServices(services)
        .setHelperResourceNames(outputResourceNames)
        .setTransport(transport)
        .build();
  }

  public GapicContext parseShowcaseTesting() {
    FileDescriptor testingFileDescriptor = TestingOuterClass.getDescriptor();
    ServiceDescriptor testingService = testingFileDescriptor.getServices().get(0);
    assertEquals(testingService.getName(), "Testing");

    Map<String, Message> messageTypes = Parser.parseMessages(testingFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(testingFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            testingFileDescriptor,
            messageTypes,
            resourceNames,
            Optional.empty(),
            outputResourceNames);

    return GapicContext.builder()
        .setMessages(messageTypes)
        .setResourceNames(resourceNames)
        .setServices(services)
        .setHelperResourceNames(outputResourceNames)
        .setTransport(transport)
        .build();
  }

  public GapicContext parseExplicitDynamicRoutingHeaderTesting() {
    FileDescriptor testingFileDescriptor =
        ExplicitDynamicRoutingHeaderTestingOuterClass.getDescriptor();
    ServiceDescriptor testingService = testingFileDescriptor.getServices().get(0);
    assertEquals(testingService.getName(), "ExplicitDynamicRoutingHeaderTesting");

    Map<String, Message> messageTypes = Parser.parseMessages(testingFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(testingFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            testingFileDescriptor,
            messageTypes,
            resourceNames,
            Optional.empty(),
            outputResourceNames);

    return GapicContext.builder()
        .setMessages(messageTypes)
        .setResourceNames(resourceNames)
        .setServices(services)
        .setHelperResourceNames(outputResourceNames)
        .setTransport(transport)
        .build();
  }

  public GapicContext parseAutoPopulateFieldTesting() {
    FileDescriptor autopopulationFileDescriptor =
        AutoPopulateFieldTestingOuterClass.getDescriptor();
    ServiceDescriptor autopopulationServiceDescriptor =
        autopopulationFileDescriptor.getServices().get(0);
    assertEquals(autopopulationServiceDescriptor.getName(), "AutoPopulateFieldTesting");

    String serviceYamlFilename = "auto_populate_field_testing.yaml";
    Path serviceYamlPath = Paths.get(testFilesDirectory, serviceYamlFilename);
    Optional<com.google.api.Service> serviceYamlOpt =
        ServiceYamlParser.parse(serviceYamlPath.toString());
    assertTrue(serviceYamlOpt.isPresent());

    Map<String, Message> messageTypes = Parser.parseMessages(autopopulationFileDescriptor);
    Map<String, ResourceName> resourceNames =
        Parser.parseResourceNames(autopopulationFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            autopopulationFileDescriptor,
            messageTypes,
            resourceNames,
            serviceYamlOpt,
            outputResourceNames);

    return GapicContext.builder()
        .setMessages(messageTypes)
        .setResourceNames(resourceNames)
        .setServices(services)
        .setHelperResourceNames(outputResourceNames)
        .setTransport(transport)
        .build();
  }

  public GapicContext parsePubSubPublisher() {
    FileDescriptor serviceFileDescriptor = PubsubProto.getDescriptor();
    FileDescriptor commonResourcesFileDescriptor = CommonResources.getDescriptor();
    ServiceDescriptor serviceDescriptor = serviceFileDescriptor.getServices().get(0);
    assertEquals("Publisher", serviceDescriptor.getName());

    Map<String, ResourceName> resourceNames = new HashMap<>();
    resourceNames.putAll(Parser.parseResourceNames(serviceFileDescriptor));
    resourceNames.putAll(Parser.parseResourceNames(commonResourcesFileDescriptor));
    Map<String, Message> messageTypes = Parser.parseMessages(serviceFileDescriptor);

    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            serviceFileDescriptor,
            messageTypes,
            resourceNames,
            Optional.empty(),
            outputResourceNames);

    String filename = "pubsub_gapic.yaml";
    Path path = Paths.get(getTestFilesDirectory(), filename);
    Optional<List<GapicBatchingSettings>> batchingSettingsOpt =
        BatchingSettingsConfigParser.parse(Optional.of(path.toString()));
    assertTrue(batchingSettingsOpt.isPresent());

    String jsonFilename = "pubsub_grpc_service_config.json";
    Path jsonPath = Paths.get(getTestFilesDirectory(), jsonFilename);
    Optional<GapicServiceConfig> configOpt = ServiceConfigParser.parse(jsonPath.toString());
    assertTrue(configOpt.isPresent());
    GapicServiceConfig config = configOpt.get();
    config.setBatchingSettings(batchingSettingsOpt);

    return GapicContext.builder()
        .setMessages(messageTypes)
        .setResourceNames(resourceNames)
        .setServices(services)
        .setServiceConfig(config)
        .setHelperResourceNames(outputResourceNames)
        .setTransport(transport)
        .build();
  }

  public GapicContext parseLogging() {
    FileDescriptor serviceFileDescriptor = LoggingProto.getDescriptor();
    ServiceDescriptor serviceDescriptor = serviceFileDescriptor.getServices().get(0);
    assertEquals(serviceDescriptor.getName(), "LoggingServiceV2");

    List<FileDescriptor> protoFiles =
        Arrays.asList(
            serviceFileDescriptor,
            LogEntryProto.getDescriptor(),
            LoggingConfigProto.getDescriptor(),
            LoggingMetricsProto.getDescriptor());

    Map<String, ResourceName> resourceNames = new HashMap<>();
    Map<String, Message> messageTypes = new HashMap<>();
    for (FileDescriptor fileDescriptor : protoFiles) {
      resourceNames.putAll(Parser.parseResourceNames(fileDescriptor));
      messageTypes.putAll(Parser.parseMessages(fileDescriptor));
    }

    // Additional resource names.
    FileDescriptor commonResourcesFileDescriptor = CommonResources.getDescriptor();
    resourceNames.putAll(Parser.parseResourceNames(commonResourcesFileDescriptor));

    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            serviceFileDescriptor,
            messageTypes,
            resourceNames,
            Optional.empty(),
            outputResourceNames);

    String filename = "logging_gapic.yaml";
    Path path = Paths.get(getTestFilesDirectory(), filename);
    Optional<List<GapicBatchingSettings>> batchingSettingsOpt =
        BatchingSettingsConfigParser.parse(Optional.of(path.toString()));
    assertTrue(batchingSettingsOpt.isPresent());

    String jsonFilename = "logging_grpc_service_config.json";
    Path jsonPath = Paths.get(getTestFilesDirectory(), jsonFilename);
    Optional<GapicServiceConfig> configOpt = ServiceConfigParser.parse(jsonPath.toString());
    assertTrue(configOpt.isPresent());
    GapicServiceConfig config = configOpt.get();
    config.setBatchingSettings(batchingSettingsOpt);

    return GapicContext.builder()
        .setMessages(messageTypes)
        .setResourceNames(resourceNames)
        .setServices(services)
        .setServiceConfig(config)
        .setHelperResourceNames(outputResourceNames)
        .setTransport(transport)
        .build();
  }

  public String getTestFilesDirectory() {
    return testFilesDirectory;
  }

  public Transport getTransport() {
    return transport;
  }
}
