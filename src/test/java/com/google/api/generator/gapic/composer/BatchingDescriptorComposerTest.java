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

import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.GapicBatchingSettings;
import com.google.api.generator.gapic.model.GapicServiceConfig;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.protoparser.BatchingSettingsConfigParser;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.gapic.protoparser.ServiceConfigParser;
import com.google.logging.v2.LogEntryProto;
import com.google.logging.v2.LoggingConfigProto;
import com.google.logging.v2.LoggingMetricsProto;
import com.google.logging.v2.LoggingProto;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.pubsub.v1.PubsubProto;
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
import org.junit.Before;
import org.junit.Test;

public class BatchingDescriptorComposerTest {
  private static final String TESTFILES_DIRECTORY =
      "src/test/java/com/google/api/generator/gapic/testdata/";

  private JavaWriterVisitor writerVisitor;

  @Before
  public void setUp() {
    writerVisitor = new JavaWriterVisitor();
  }

  @Test
  public void batchingDescriptor_hasSubresponseField() {
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
            serviceFileDescriptor, messageTypes, resourceNames, outputResourceNames);

    String filename = "pubsub_gapic.yaml";
    Path path = Paths.get(TESTFILES_DIRECTORY, filename);
    Optional<List<GapicBatchingSettings>> batchingSettingsOpt =
        BatchingSettingsConfigParser.parse(Optional.of(path.toString()));
    assertTrue(batchingSettingsOpt.isPresent());

    String jsonFilename = "pubsub_grpc_service_config.json";
    Path jsonPath = Paths.get(TESTFILES_DIRECTORY, jsonFilename);
    Optional<GapicServiceConfig> configOpt =
        ServiceConfigParser.parse(jsonPath.toString(), batchingSettingsOpt);
    assertTrue(configOpt.isPresent());
    GapicServiceConfig config = configOpt.get();

    Service service = services.get(0);
    assertEquals("Publisher", service.name());
    Method method = findMethod(service, "Publish");

    GapicBatchingSettings batchingSetting = batchingSettingsOpt.get().get(0);
    assertEquals("Publish", batchingSetting.methodName());
    Expr batchingDescriptorExpr =
        BatchingDescriptorComposer.createBatchingDescriptorFieldDeclExpr(
            method, batchingSetting, messageTypes);

    batchingDescriptorExpr.accept(writerVisitor);
    String expected =
        createLines(
            "private static final BatchingDescriptor<PublishRequest, PublishResponse>"
                + " PUBLISH_BATCHING_DESC = new BatchingDescriptor<PublishRequest,"
                + " PublishResponse>() {\n",
            "@Override\n",
            "public PartitionKey getBatchPartitionKey(PublishRequest request) {\n",
            "return new PartitionKey(request.getTopic());\n",
            "}\n",
            "\n",
            "@Override\n",
            "public RequestBuilder<PublishRequest> getRequestBuilder() {\n",
            "return new RequestBuilder<PublishRequest>() {\n",
            "private PublishRequest.Builder builder;\n",
            "@Override\n",
            "public void appendRequest(PublishRequest request) {\n",
            "if (Objects.isNull(builder)) {\n",
            "builder = request.toBuilder();\n",
            "} else {\n",
            "builder.addAllMessages(request.getMessagesList());\n",
            "}\n",
            "}\n",
            "\n",
            "@Override\n",
            "public PublishRequest build() {\n",
            "return builder.build();\n",
            "}\n",
            "\n",
            "};\n",
            "}\n",
            "\n",
            "@Override\n",
            "public void splitResponse(PublishResponse batchResponse, Collection<? extends"
                + " BatchedRequestIssuer<PublishResponse>> batch) {\n",
            "int batchMessageIndex = 0;\n",
            "for (BatchedRequestIssuer<PublishResponse> responder : batch) {\n",
            "List<String> subresponseElements = new ArrayList<>();\n",
            "long subresponseCount = responder.getMessageCount();\n",
            "for (int i = 0; i < subresponseCount; i++) {\n",
            "subresponseElements.add(batchResponse.getMessageIds(batchMessageIndex++));\n",
            "}\n",
            "PublishResponse response ="
                + " PublishResponse.newBuilder().addAllMessageIds(subresponseElements).build();\n",
            "responder.setResponse(response);\n",
            "}\n",
            "}\n",
            "\n",
            "@Override\n",
            "public void splitException(Throwable throwable, Collection<? extends"
                + " BatchedRequestIssuer<PublishResponse>> batch) {\n",
            "for (BatchedRequestIssuer<PublishResponse> responder : batch) {\n",
            "responder.setException(throwable);\n",
            "}\n",
            "}\n",
            "\n",
            "@Override\n",
            "public long countElements(PublishRequest request) {\n",
            "return request.getMessagesCount();\n",
            "}\n",
            "\n",
            "@Override\n",
            "public long countBytes(PublishRequest request) {\n",
            "return request.getSerializedSize();\n",
            "}\n",
            "\n",
            "}");

    assertEquals(expected, writerVisitor.write());
  }

  @Test
  public void batchingDescriptor_noSubresponseField() {
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

    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            serviceFileDescriptor, messageTypes, resourceNames, outputResourceNames);

    String filename = "logging_gapic.yaml";
    Path path = Paths.get(TESTFILES_DIRECTORY, filename);
    Optional<List<GapicBatchingSettings>> batchingSettingsOpt =
        BatchingSettingsConfigParser.parse(Optional.of(path.toString()));
    assertTrue(batchingSettingsOpt.isPresent());

    String jsonFilename = "logging_grpc_service_config.json";
    Path jsonPath = Paths.get(TESTFILES_DIRECTORY, jsonFilename);
    Optional<GapicServiceConfig> configOpt =
        ServiceConfigParser.parse(jsonPath.toString(), batchingSettingsOpt);
    assertTrue(configOpt.isPresent());
    GapicServiceConfig config = configOpt.get();

    Service service = services.get(0);
    assertEquals("LoggingServiceV2", service.name());
    Method method = findMethod(service, "WriteLogEntries");

    GapicBatchingSettings batchingSetting = batchingSettingsOpt.get().get(0);
    assertEquals("WriteLogEntries", batchingSetting.methodName());
    Expr batchingDescriptorExpr =
        BatchingDescriptorComposer.createBatchingDescriptorFieldDeclExpr(
            method, batchingSetting, messageTypes);

    batchingDescriptorExpr.accept(writerVisitor);
    String expected =
        createLines(
            "private static final BatchingDescriptor<WriteLogEntriesRequest,"
                + " WriteLogEntriesResponse> WRITE_LOG_ENTRIES_BATCHING_DESC = new"
                + " BatchingDescriptor<WriteLogEntriesRequest, WriteLogEntriesResponse>() {\n",
            "@Override\n",
            "public PartitionKey getBatchPartitionKey(WriteLogEntriesRequest request) {\n",
            "return new PartitionKey(request.getLogName(), request.getResource(),"
                + " request.getLabels());\n",
            "}\n",
            "\n",
            "@Override\n",
            "public RequestBuilder<WriteLogEntriesRequest> getRequestBuilder() {\n",
            "return new RequestBuilder<WriteLogEntriesRequest>() {\n",
            "private WriteLogEntriesRequest.Builder builder;\n",
            "@Override\n",
            "public void appendRequest(WriteLogEntriesRequest request) {\n",
            "if (Objects.isNull(builder)) {\n",
            "builder = request.toBuilder();\n",
            "} else {\n",
            "builder.addAllEntries(request.getEntriesList());\n",
            "}\n",
            "}\n",
            "\n",
            "@Override\n",
            "public WriteLogEntriesRequest build() {\n",
            "return builder.build();\n",
            "}\n",
            "\n",
            "};\n",
            "}\n",
            "\n",
            "@Override\n",
            "public void splitResponse(WriteLogEntriesResponse batchResponse, Collection<? extends"
                + " BatchedRequestIssuer<WriteLogEntriesResponse>> batch) {\n",
            "for (BatchedRequestIssuer<WriteLogEntriesResponse> responder : batch) {\n",
            "WriteLogEntriesResponse response = WriteLogEntriesResponse.newBuilder().build();\n",
            "responder.setResponse(response);\n",
            "}\n",
            "}\n",
            "\n",
            "@Override\n",
            "public void splitException(Throwable throwable, Collection<? extends"
                + " BatchedRequestIssuer<WriteLogEntriesResponse>> batch) {\n",
            "for (BatchedRequestIssuer<WriteLogEntriesResponse> responder : batch) {\n",
            "responder.setException(throwable);\n",
            "}\n",
            "}\n",
            "\n",
            "@Override\n",
            "public long countElements(WriteLogEntriesRequest request) {\n",
            "return request.getEntriesCount();\n",
            "}\n",
            "\n",
            "@Override\n",
            "public long countBytes(WriteLogEntriesRequest request) {\n",
            "return request.getSerializedSize();\n",
            "}\n",
            "\n",
            "}");

    assertEquals(expected, writerVisitor.write());
  }

  private static Method findMethod(Service service, String methodName) {
    for (Method m : service.methods()) {
      if (m.name().equals(methodName)) {
        return m;
      }
    }
    return null;
  }

  private static String createLines(String... lines) {
    // Cast to get rid of warnings.
    return String.format(new String(new char[lines.length]).replace("\0", "%s"), (Object[]) lines);
  }
}
