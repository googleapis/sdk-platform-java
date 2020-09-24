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

package com.google.api.generator.gapic.composer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicBatchingSettings;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicServiceConfig;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.protoparser.BatchingSettingsConfigParser;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.gapic.protoparser.ServiceConfigParser;
import com.google.api.generator.test.framework.Assert;
import com.google.api.generator.test.framework.Utils;
import com.google.logging.v2.LogEntryProto;
import com.google.logging.v2.LoggingConfigProto;
import com.google.logging.v2.LoggingMetricsProto;
import com.google.logging.v2.LoggingProto;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.pubsub.v1.PubsubProto;
import com.google.showcase.v1beta1.EchoOuterClass;
import google.cloud.CommonResources;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.Test;

public class ServiceStubSettingsClassComposerTest {

  @Test
  public void generateServiceStubSettingsClasses_batchingWithEmptyResponses() {
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

    List<Service> services =
        parseServices(serviceFileDescriptor, serviceDescriptor, messageTypes, resourceNames);

    String filename = "logging_gapic.yaml";
    Path path = Paths.get(ComposerConstants.TESTFILES_DIRECTORY, filename);
    Optional<List<GapicBatchingSettings>> batchingSettingsOpt =
        BatchingSettingsConfigParser.parse(Optional.of(path.toString()));
    assertTrue(batchingSettingsOpt.isPresent());

    String jsonFilename = "logging_grpc_service_config.json";
    Path jsonPath = Paths.get(ComposerConstants.TESTFILES_DIRECTORY, jsonFilename);
    Optional<GapicServiceConfig> configOpt =
        ServiceConfigParser.parse(jsonPath.toString(), batchingSettingsOpt);
    assertTrue(configOpt.isPresent());
    GapicServiceConfig config = configOpt.get();

    Service protoService = services.get(0);
    GapicClass clazz =
        ServiceStubSettingsClassComposer.instance().generate(protoService, config, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(
        this.getClass(), "LoggingServiceV2StubSettings.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(ComposerConstants.GOLDENFILES_DIRECTORY, "LoggingServiceV2StubSettings.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateServiceStubSettingsClasses_batchingWithNonemptyResponses()
      throws IOException {
    FileDescriptor serviceFileDescriptor = PubsubProto.getDescriptor();
    FileDescriptor commonResourcesFileDescriptor = CommonResources.getDescriptor();
    ServiceDescriptor serviceDescriptor = serviceFileDescriptor.getServices().get(0);
    assertEquals("Publisher", serviceDescriptor.getName());

    Map<String, ResourceName> resourceNames = new HashMap<>();
    resourceNames.putAll(Parser.parseResourceNames(serviceFileDescriptor));
    resourceNames.putAll(Parser.parseResourceNames(commonResourcesFileDescriptor));

    Map<String, Message> messageTypes = Parser.parseMessages(serviceFileDescriptor);

    List<Service> services =
        parseServices(serviceFileDescriptor, serviceDescriptor, messageTypes, resourceNames);

    String filename = "pubsub_gapic.yaml";
    Path path = Paths.get(ComposerConstants.TESTFILES_DIRECTORY, filename);
    Optional<List<GapicBatchingSettings>> batchingSettingsOpt =
        BatchingSettingsConfigParser.parse(Optional.of(path.toString()));
    assertTrue(batchingSettingsOpt.isPresent());

    String jsonFilename = "pubsub_grpc_service_config.json";
    Path jsonPath = Paths.get(ComposerConstants.TESTFILES_DIRECTORY, jsonFilename);
    Optional<GapicServiceConfig> configOpt =
        ServiceConfigParser.parse(jsonPath.toString(), batchingSettingsOpt);
    assertTrue(configOpt.isPresent());
    GapicServiceConfig config = configOpt.get();

    Service protoService = services.get(0);
    assertEquals("Publisher", protoService.name());
    GapicClass clazz =
        ServiceStubSettingsClassComposer.instance().generate(protoService, config, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "PublisherStubSettings.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(ComposerConstants.GOLDENFILES_DIRECTORY, "PublisherStubSettings.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  @Test
  public void generateServiceStubSettingsClasses_basic() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    ServiceDescriptor echoServiceDescriptor = echoFileDescriptor.getServices().get(0);
    assertEquals(echoServiceDescriptor.getName(), "Echo");

    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    List<Service> services =
        parseServices(echoFileDescriptor, echoServiceDescriptor, messageTypes, resourceNames);

    String jsonFilename = "showcase_grpc_service_config.json";
    Path jsonPath = Paths.get(ComposerConstants.TESTFILES_DIRECTORY, jsonFilename);
    Optional<GapicServiceConfig> configOpt =
        ServiceConfigParser.parse(jsonPath.toString(), Optional.empty());
    assertTrue(configOpt.isPresent());
    GapicServiceConfig config = configOpt.get();

    Service echoProtoService = services.get(0);
    GapicClass clazz =
        ServiceStubSettingsClassComposer.instance()
            .generate(echoProtoService, config, messageTypes);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    Utils.saveCodegenToFile(this.getClass(), "EchoStubSettings.golden", visitor.write());
    Path goldenFilePath =
        Paths.get(ComposerConstants.GOLDENFILES_DIRECTORY, "EchoStubSettings.golden");
    Assert.assertCodeEquals(goldenFilePath, visitor.write());
  }

  private static List<Service> parseServices(
      FileDescriptor protoFileDescriptor,
      ServiceDescriptor serviceDescriptor,
      Map<String, Message> messageTypes,
      Map<String, ResourceName> resourceNames) {
    Set<ResourceName> outputResourceNames = new HashSet<>();
    return Parser.parseService(
        protoFileDescriptor, messageTypes, resourceNames, outputResourceNames);
  }
}
