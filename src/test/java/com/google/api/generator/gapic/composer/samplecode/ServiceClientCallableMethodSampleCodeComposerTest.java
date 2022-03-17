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

package com.google.api.generator.gapic.composer.samplecode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.LongrunningOperation;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Method.Stream;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.testutils.LineFormatter;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;

public class ServiceClientCallableMethodSampleCodeComposerTest {
  private static final String SHOWCASE_PACKAGE_NAME = "com.google.showcase.v1beta1";
  private static final String LRO_PACKAGE_NAME = "com.google.longrunning";
  private static final String PROTO_PACKAGE_NAME = "com.google.protobuf";
  private static final String PAGINATED_FIELD_NAME = "page_size";

  // ================================LRO Callable Method Sample Code ====================//
  @Test
  public void validComposeLroCallableMethodHeaderSampleCode_withReturnResponse() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("WaitRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder().setName("Operation").setPakkage(LRO_PACKAGE_NAME).build());
    TypeNode responseType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("WaitResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode metadataType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("WaitMetadata")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    LongrunningOperation lro =
        LongrunningOperation.builder()
            .setResponseType(responseType)
            .setMetadataType(metadataType)
            .build();
    Method method =
        Method.builder()
            .setName("Wait")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setLro(lro)
            .build();
    String results =
        ServiceClientCallableSampleComposer.composeLroCallableMethodHeaderSampleCode(
            method, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitRequest request = WaitRequest.newBuilder().build();\n",
            "  OperationFuture<WaitResponse, WaitMetadata> future =\n",
            "      echoClient.waitOperationCallable().futureCall(request);\n",
            "  // Do something.\n",
            "  WaitResponse response = future.get();\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void validComposeLroCallableMethodHeaderSampleCode_withReturnVoid() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("WaitRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder().setName("Operation").setPakkage(LRO_PACKAGE_NAME).build());
    TypeNode responseType =
        TypeNode.withReference(
            VaporReference.builder().setName("Empty").setPakkage(PROTO_PACKAGE_NAME).build());
    TypeNode metadataType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("WaitMetadata")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    LongrunningOperation lro =
        LongrunningOperation.builder()
            .setResponseType(responseType)
            .setMetadataType(metadataType)
            .build();
    Method method =
        Method.builder()
            .setName("Wait")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setLro(lro)
            .build();
    String results =
        ServiceClientCallableSampleComposer.composeLroCallableMethodHeaderSampleCode(
            method, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitRequest request = WaitRequest.newBuilder().build();\n",
            "  OperationFuture<Empty, WaitMetadata> future =\n",
            "      echoClient.waitOperationCallable().futureCall(request);\n",
            "  // Do something.\n",
            "  future.get();\n",
            "}");
    assertEquals(results, expected);
  }

  // ================================Paged Callable Method Sample Code ====================//
  @Test
  public void validComposePagedCallableMethodHeaderSampleCode() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("PagedExpandRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("PagedExpandResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder()
            .setName("PagedExpand")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    String results =
        ServiceClientCallableSampleComposer.composePagedCallableMethodHeaderSampleCode(
            method, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  PagedExpandRequest request =\n",
            "      PagedExpandRequest.newBuilder()\n",
            "          .setContent(\"content951530617\")\n",
            "          .setPageSize(883849137)\n",
            "          .setPageToken(\"pageToken873572522\")\n",
            "          .build();\n",
            "  ApiFuture<EchoResponse> future ="
                + " echoClient.pagedExpandPagedCallable().futureCall(request);\n",
            "  // Do something.\n",
            "  for (EchoResponse element : future.get().iterateAll()) {\n",
            "    // doThingsWith(element);\n",
            "  }\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void invalidComposePagedCallableMethodHeaderSampleCode_inputTypeNotExistInMessage() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("NotExistRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("PagedExpandResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder()
            .setName("PagedExpand")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientCallableSampleComposer.composePagedCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
  }

  @Test
  public void invalidComposePagedCallableMethodHeaderSampleCode_noExistMethodResponse() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("NoExistResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder()
            .setName("PagedExpand")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientCallableSampleComposer.composePagedCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
  }

  @Test
  public void invalidComposePagedCallableMethodHeaderSampleCode_noRepeatedResponse() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("NoRepeatedResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder()
            .setName("PagedExpand")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    Field responseField = Field.builder().setName("response").setType(TypeNode.STRING).build();
    Message noRepeatedResponseMessage =
        Message.builder()
            .setName("NoRepeatedResponse")
            .setFullProtoName("google.showcase.v1beta1.NoRepeatedResponse")
            .setType(
                TypeNode.withReference(
                    VaporReference.builder()
                        .setName("NoRepeatedResponse")
                        .setPakkage(SHOWCASE_PACKAGE_NAME)
                        .build()))
            .setFields(Arrays.asList(responseField))
            .build();
    messageTypes.put("NoRepeatedResponse", noRepeatedResponseMessage);
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientCallableSampleComposer.composePagedCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
  }

  // ==============================Stream Callable Method Sample Code ====================//
  @Test
  public void validComposeStreamCallableMethodHeaderSampleCode_serverStream() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("ExpandRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder()
            .setName("Expand")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setStream(Stream.SERVER)
            .build();
    String results =
        ServiceClientCallableSampleComposer.composeStreamCallableMethodHeaderSampleCode(
            method, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  ExpandRequest request =\n",
            "     "
                + " ExpandRequest.newBuilder().setContent(\"content951530617\").setInfo(\"info3237038\").build();\n",
            "  ServerStream<EchoResponse> stream = echoClient.expandCallable().call(request);\n",
            "  for (EchoResponse response : stream) {\n",
            "    // Do something when a response is received.\n",
            "  }\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void invalidComposeStreamCallableMethodHeaderSampleCode_serverStreamNotExistRequest() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("NotExistRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder()
            .setName("Expand")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setStream(Stream.SERVER)
            .build();
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientCallableSampleComposer.composeStreamCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
  }

  @Test
  public void validComposeStreamCallableMethodHeaderSampleCode_bidiStream() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder()
            .setName("chat")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setStream(Stream.BIDI)
            .build();
    String results =
        ServiceClientCallableSampleComposer.composeStreamCallableMethodHeaderSampleCode(
            method, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  BidiStream<EchoRequest, EchoResponse> bidiStream ="
                + " echoClient.chatCallable().call();\n",
            "  EchoRequest request =\n",
            "      EchoRequest.newBuilder()\n",
            "          .setName(FoobarName.ofProjectFoobarName(\"[PROJECT]\","
                + " \"[FOOBAR]\").toString())\n",
            "          .setParent(FoobarName.ofProjectFoobarName(\"[PROJECT]\","
                + " \"[FOOBAR]\").toString())\n",
            "          .setSeverity(Severity.forNumber(0))\n",
            "          .setFoobar(Foobar.newBuilder().build())\n",
            "          .build();\n",
            "  bidiStream.send(request);\n",
            "  for (EchoResponse response : bidiStream) {\n",
            "    // Do something when a response is received.\n",
            "  }\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void invalidComposeStreamCallableMethodHeaderSampleCode_bidiStreamNotExistRequest() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("NotExistRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder()
            .setName("chat")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setStream(Stream.BIDI)
            .build();
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientCallableSampleComposer.composeStreamCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
  }

  @Test
  public void validComposeStreamCallableMethodHeaderSampleCode_clientStream() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder()
            .setName("Collect")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setStream(Stream.CLIENT)
            .build();
    String results =
        ServiceClientCallableSampleComposer.composeStreamCallableMethodHeaderSampleCode(
            method, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  ApiStreamObserver<EchoRequest> responseObserver =\n",
            "      new ApiStreamObserver<EchoRequest>() {\n",
            "        {@literal @}Override\n",
            "        public void onNext(EchoResponse response) {\n",
            "          // Do something when a response is received.\n",
            "        }\n",
            "\n",
            "        {@literal @}Override\n",
            "        public void onError(Throwable t) {\n",
            "          // Add error-handling\n",
            "        }\n",
            "\n",
            "        {@literal @}Override\n",
            "        public void onCompleted() {\n",
            "          // Do something when complete.\n",
            "        }\n",
            "      };\n",
            "  ApiStreamObserver<EchoRequest> requestObserver =\n",
            "      echoClient.collect().clientStreamingCall(responseObserver);\n",
            "  EchoRequest request =\n",
            "      EchoRequest.newBuilder()\n",
            "          .setName(FoobarName.ofProjectFoobarName(\"[PROJECT]\","
                + " \"[FOOBAR]\").toString())\n",
            "          .setParent(FoobarName.ofProjectFoobarName(\"[PROJECT]\","
                + " \"[FOOBAR]\").toString())\n",
            "          .setSeverity(Severity.forNumber(0))\n",
            "          .setFoobar(Foobar.newBuilder().build())\n",
            "          .build();\n",
            "  requestObserver.onNext(request);\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void invalidComposeStreamCallableMethodHeaderSampleCode_clientStreamNotExistRequest() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("NotExistRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder()
            .setName("Collect")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setStream(Stream.CLIENT)
            .build();
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientCallableSampleComposer.composeStreamCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
  }

  // ================================Regular Callable Method Sample Code ====================//
  @Test
  public void validComposeRegularCallableMethodHeaderSampleCode_unaryRpc() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder().setName("Echo").setInputType(inputType).setOutputType(outputType).build();
    String results =
        ServiceClientCallableSampleComposer.composeRegularCallableMethodHeaderSampleCode(
            method, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  EchoRequest request =\n",
            "      EchoRequest.newBuilder()\n",
            "          .setName(FoobarName.ofProjectFoobarName(\"[PROJECT]\","
                + " \"[FOOBAR]\").toString())\n",
            "          .setParent(FoobarName.ofProjectFoobarName(\"[PROJECT]\","
                + " \"[FOOBAR]\").toString())\n",
            "          .setSeverity(Severity.forNumber(0))\n",
            "          .setFoobar(Foobar.newBuilder().build())\n",
            "          .build();\n",
            "  ApiFuture<EchoResponse> future = echoClient.echoCallable().futureCall(request);\n",
            "  // Do something.\n",
            "  EchoResponse response = future.get();\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void validComposeRegularCallableMethodHeaderSampleCode_lroRpc() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("WaitRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder().setName("Operation").setPakkage(LRO_PACKAGE_NAME).build());
    TypeNode responseType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("WaitResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode metadataType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("WaitMetadata")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    LongrunningOperation lro =
        LongrunningOperation.builder()
            .setResponseType(responseType)
            .setMetadataType(metadataType)
            .build();
    Method method =
        Method.builder()
            .setName("Wait")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setLro(lro)
            .build();
    String results =
        ServiceClientCallableSampleComposer.composeRegularCallableMethodHeaderSampleCode(
            method, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitRequest request = WaitRequest.newBuilder().build();\n",
            "  ApiFuture<Operation> future = echoClient.waitCallable().futureCall(request);\n",
            "  // Do something.\n",
            "  Operation response = future.get();\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void validComposeRegularCallableMethodHeaderSampleCode_lroRpcWithReturnVoid() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("WaitRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder().setName("Operation").setPakkage(LRO_PACKAGE_NAME).build());
    TypeNode responseType =
        TypeNode.withReference(
            VaporReference.builder().setName("Empty").setPakkage(PROTO_PACKAGE_NAME).build());
    TypeNode metadataType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("WaitMetadata")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    LongrunningOperation lro =
        LongrunningOperation.builder()
            .setResponseType(responseType)
            .setMetadataType(metadataType)
            .build();
    Method method =
        Method.builder()
            .setName("Wait")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setLro(lro)
            .build();
    String results =
        ServiceClientCallableSampleComposer.composeRegularCallableMethodHeaderSampleCode(
            method, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitRequest request = WaitRequest.newBuilder().build();\n",
            "  ApiFuture<Operation> future = echoClient.waitCallable().futureCall(request);\n",
            "  // Do something.\n",
            "  future.get();\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void validComposeRegularCallableMethodHeaderSampleCode_pageRpc() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("PagedExpandRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("PagedExpandResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder()
            .setName("PagedExpand")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setMethodSignatures(Collections.emptyList())
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    String results =
        ServiceClientCallableSampleComposer.composeRegularCallableMethodHeaderSampleCode(
            method, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  PagedExpandRequest request =\n",
            "      PagedExpandRequest.newBuilder()\n",
            "          .setContent(\"content951530617\")\n",
            "          .setPageSize(883849137)\n",
            "          .setPageToken(\"pageToken873572522\")\n",
            "          .build();\n",
            "  while (true) {\n",
            "    PagedExpandResponse response = echoClient.pagedExpandCallable().call(request);\n",
            "    for (EchoResponse element : response.getResponsesList()) {\n",
            "      // doThingsWith(element);\n",
            "    }\n",
            "    String nextPageToken = response.getNextPageToken();\n",
            "    if (!Strings.isNullOrEmpty(nextPageToken)) {\n",
            "      request = request.toBuilder().setPageToken(nextPageToken).build();\n",
            "    } else {\n",
            "      break;\n",
            "    }\n",
            "  }\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void invalidComposeRegularCallableMethodHeaderSampleCode_noExistMethodRequest() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("NoExistRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder().setName("Echo").setInputType(inputType).setOutputType(outputType).build();
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientCallableSampleComposer.composeRegularCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
  }

  @Test
  public void invalidComposeRegularCallableMethodHeaderSampleCode_noExistMethodResponsePagedRpc() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("NoExistResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder()
            .setName("PagedExpand")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientCallableSampleComposer.composeRegularCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
  }

  @Test
  public void invalidComposeRegularCallableMethodHeaderSampleCode_noRepeatedResponsePagedRpc() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("NoRepeatedResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Method method =
        Method.builder()
            .setName("PagedExpand")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    Field responseField = Field.builder().setName("response").setType(TypeNode.STRING).build();
    Message noRepeatedResponseMessage =
        Message.builder()
            .setName("NoRepeatedResponse")
            .setFullProtoName("google.showcase.v1beta1.NoRepeatedResponse")
            .setType(
                TypeNode.withReference(
                    VaporReference.builder()
                        .setName("NoRepeatedResponse")
                        .setPakkage(SHOWCASE_PACKAGE_NAME)
                        .build()))
            .setFields(Arrays.asList(responseField))
            .build();
    messageTypes.put("NoRepeatedResponse", noRepeatedResponseMessage);
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientCallableSampleComposer.composeRegularCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
  }
}
