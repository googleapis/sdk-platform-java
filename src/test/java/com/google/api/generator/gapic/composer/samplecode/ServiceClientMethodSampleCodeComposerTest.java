// Copyright 2022 Google LLC
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

import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.LongrunningOperation;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.testutils.LineFormatter;
import com.google.protobuf.Descriptors;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class ServiceClientMethodSampleCodeComposerTest {
  private static final String SHOWCASE_PACKAGE_NAME = "com.google.showcase.v1beta1";
  private static final String LRO_PACKAGE_NAME = "com.google.longrunning";
  private static final String PROTO_PACKAGE_NAME = "com.google.protobuf";
  private static final String PAGINATED_FIELD_NAME = "page_size";

  @Test
  public void validComposeRpcMethodHeaderSampleCode_pagedRpcWithMultipleMethodArguments() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
                .setName("ListContentRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("ListContentResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode resourceNameType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(List.class)
                .setGenerics(ConcreteReference.withClazz(String.class))
                .build());
    List<MethodArgument> arguments =
        Arrays.asList(
            MethodArgument.builder()
                .setName("resourceName")
                .setType(resourceNameType)
                .setField(
                    Field.builder()
                        .setName("resourceName")
                        .setType(resourceNameType)
                        .setIsRepeated(true)
                        .build())
                .build(),
            MethodArgument.builder()
                .setName("filter")
                .setType(TypeNode.STRING)
                .setField(Field.builder().setName("filter").setType(TypeNode.STRING).build())
                .build());
    Method method =
        Method.builder()
            .setName("ListContent")
            .setMethodSignatures(Arrays.asList(arguments))
            .setInputType(inputType)
            .setOutputType(outputType)
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    Reference repeatedResponseReference =
        VaporReference.builder().setName("Content").setPakkage(SHOWCASE_PACKAGE_NAME).build();
    Field repeatedField =
        Field.builder()
            .setName("responses")
            .setType(
                TypeNode.withReference(
                    ConcreteReference.builder()
                        .setClazz(List.class)
                        .setGenerics(repeatedResponseReference)
                        .build()))
            .setIsMessage(true)
            .setIsRepeated(true)
            .build();
    Field nextPagedTokenField =
        Field.builder().setName("next_page_token").setType(TypeNode.STRING).build();
    Message listContentResponseMessage =
        Message.builder()
            .setName("ListContentResponse")
            .setFullProtoName("google.showcase.v1beta1.ListContentResponse")
            .setType(outputType)
            .setFields(Arrays.asList(repeatedField, nextPagedTokenField))
            .build();
    messageTypes.put("com.google.showcase.v1beta1.ListContentResponse", listContentResponseMessage);

    String results =
        ServiceClientMethodSampleComposer.composeRpcMethodHeaderSampleCode(
            method, clientType, arguments, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  List<String> resourceName = new ArrayList<>();\n",
            "  String filter = \"filter-1274492040\";\n",
            "  for (Content element : echoClient.listContent(resourceName, filter).iterateAll())"
                + " {\n",
            "    // doThingsWith(element);\n",
            "  }\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void validComposeRpcMethodHeaderSampleCode_pagedRpcWithNoMethodArguments() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
                .setName("ListContentRequest")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("ListContentResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    List<MethodArgument> arguments = Collections.emptyList();
    Method method =
        Method.builder()
            .setName("ListContent")
            .setMethodSignatures(Arrays.asList(arguments))
            .setInputType(inputType)
            .setOutputType(outputType)
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    Reference repeatedResponseReference =
        VaporReference.builder().setName("Content").setPakkage(SHOWCASE_PACKAGE_NAME).build();
    Field repeatedField =
        Field.builder()
            .setName("responses")
            .setType(
                TypeNode.withReference(
                    ConcreteReference.builder()
                        .setClazz(List.class)
                        .setGenerics(repeatedResponseReference)
                        .build()))
            .setIsMessage(true)
            .setIsRepeated(true)
            .build();
    Field nextPagedTokenField =
        Field.builder().setName("next_page_token").setType(TypeNode.STRING).build();
    Message listContentResponseMessage =
        Message.builder()
            .setName("ListContentResponse")
            .setFullProtoName("google.showcase.v1beta1.ListContentResponse")
            .setType(outputType)
            .setFields(Arrays.asList(repeatedField, nextPagedTokenField))
            .build();
    messageTypes.put("com.google.showcase.v1beta1.ListContentResponse", listContentResponseMessage);

    String results =
        ServiceClientMethodSampleComposer.composeRpcMethodHeaderSampleCode(
            method, clientType, arguments, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  for (Content element : echoClient.listContent().iterateAll()) {\n",
            "    // doThingsWith(element);\n",
            "  }\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void invalidComposeRpcMethodHeaderSampleCode_noMatchedRepeatedResponseTypeInPagedMethod() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
                .setName("PagedResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    List<MethodArgument> methodArguments = Collections.emptyList();
    Method method =
        Method.builder()
            .setName("simplePagedMethod")
            .setMethodSignatures(Arrays.asList(methodArguments))
            .setInputType(inputType)
            .setOutputType(outputType)
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientMethodSampleComposer.composeRpcMethodHeaderSampleCode(
                method, clientType, methodArguments, resourceNames, messageTypes));
  }

  @Test
  public void invalidComposeRpcMethodHeaderSampleCode_noRepeatedResponseTypeInPagedMethod() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
                .setName("PagedResponse")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    List<MethodArgument> methodArguments = Collections.emptyList();
    Method method =
        Method.builder()
            .setName("simplePagedMethod")
            .setMethodSignatures(Arrays.asList(methodArguments))
            .setInputType(inputType)
            .setOutputType(outputType)
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    Field responseField =
        Field.builder()
            .setName("response")
            .setType(
                TypeNode.withReference(
                    ConcreteReference.builder()
                        .setClazz(List.class)
                        .setGenerics(ConcreteReference.withClazz(String.class))
                        .build()))
            .setIsMessage(true)
            .setIsRepeated(false)
            .build();
    Field nextPageToken =
        Field.builder().setName("next_page_token").setType(TypeNode.STRING).build();
    Message noRepeatedFieldMessage =
        Message.builder()
            .setName("PagedResponse")
            .setFullProtoName("google.showcase.v1beta1.PagedResponse")
            .setType(outputType)
            .setFields(Arrays.asList(responseField, nextPageToken))
            .build();
    messageTypes.put("PagedResponse", noRepeatedFieldMessage);
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientMethodSampleComposer.composeRpcMethodHeaderSampleCode(
                method, clientType, methodArguments, resourceNames, messageTypes));
  }

  // ================================Unary RPC Default Method Sample Code ====================//
  @Test
  public void validComposeRpcDefaultMethodHeaderSampleCode_isPagedMethod() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
        ServiceClientMethodSampleComposer.composeRpcDefaultMethodHeaderSampleCode(
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
            "  for (EchoResponse element : echoClient.pagedExpand(request).iterateAll()) {\n",
            "    // doThingsWith(element);\n",
            "  }\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void invalidComposeRpcDefaultMethodHeaderSampleCode_isPagedMethod() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
            .setMethodSignatures(Collections.emptyList())
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientMethodSampleComposer.composeRpcDefaultMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
  }

  @Test
  public void validComposeRpcDefaultMethodHeaderSampleCode_hasLroMethodWithReturnResponse() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
            .setMethodSignatures(Collections.emptyList())
            .setLro(lro)
            .build();
    String results =
        ServiceClientMethodSampleComposer.composeRpcDefaultMethodHeaderSampleCode(
            method, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitRequest request = WaitRequest.newBuilder().build();\n",
            "  echoClient.waitAsync(request).get();\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void validComposeRpcDefaultMethodHeaderSampleCode_hasLroMethodWithReturnVoid() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
            .setMethodSignatures(Collections.emptyList())
            .setLro(lro)
            .build();
    String results =
        ServiceClientMethodSampleComposer.composeRpcDefaultMethodHeaderSampleCode(
            method, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitRequest request = WaitRequest.newBuilder().build();\n",
            "  WaitResponse response = echoClient.waitAsync(request).get();\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void validComposeRpcDefaultMethodHeaderSampleCode_pureUnaryReturnVoid() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
            VaporReference.builder().setName("Empty").setPakkage(PROTO_PACKAGE_NAME).build());
    Method method =
        Method.builder()
            .setName("Echo")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setMethodSignatures(Collections.emptyList())
            .build();
    String results =
        ServiceClientMethodSampleComposer.composeRpcDefaultMethodHeaderSampleCode(
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
            "  echoClient.echo(request);\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void validComposeRpcDefaultMethodHeaderSampleCode_pureUnaryReturnResponse() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
            .setName("Echo")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setMethodSignatures(Collections.emptyList())
            .build();
    String results =
        ServiceClientMethodSampleComposer.composeRpcDefaultMethodHeaderSampleCode(
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
            "  EchoResponse response = echoClient.echo(request);\n",
            "}");
    assertEquals(results, expected);
  }

  // ===================================Unary LRO RPC Method Sample Code ======================//
  @Test
  public void validComposeRpcMethodHeaderSampleCode_lroUnaryRpcWithNoMethodArgument() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
            .setMethodSignatures(Collections.emptyList())
            .build();

    String results =
        ServiceClientMethodSampleComposer.composeRpcMethodHeaderSampleCode(
            method, clientType, Collections.emptyList(), resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitResponse response = echoClient.waitAsync().get();\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void validComposeRpcMethodHeaderSampleCode_lroRpcWithReturnResponseType() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
    TypeNode ttlTypeNode =
        TypeNode.withReference(
            VaporReference.builder().setName("Duration").setPakkage(PROTO_PACKAGE_NAME).build());
    MethodArgument ttl =
        MethodArgument.builder()
            .setName("ttl")
            .setType(ttlTypeNode)
            .setField(
                Field.builder()
                    .setName("ttl")
                    .setType(ttlTypeNode)
                    .setIsMessage(true)
                    .setIsContainedInOneof(true)
                    .build())
            .build();
    List<MethodArgument> arguments = Arrays.asList(ttl);
    Method method =
        Method.builder()
            .setName("Wait")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setLro(lro)
            .setMethodSignatures(Arrays.asList(arguments))
            .build();

    String results =
        ServiceClientMethodSampleComposer.composeRpcMethodHeaderSampleCode(
            method, clientType, arguments, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  Duration ttl = Duration.newBuilder().build();\n",
            "  WaitResponse response = echoClient.waitAsync(ttl).get();\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void validComposeRpcMethodHeaderSampleCode_lroRpcWithReturnVoid() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
    TypeNode ttlTypeNode =
        TypeNode.withReference(
            VaporReference.builder().setName("Duration").setPakkage(PROTO_PACKAGE_NAME).build());
    MethodArgument ttl =
        MethodArgument.builder()
            .setName("ttl")
            .setType(ttlTypeNode)
            .setField(
                Field.builder()
                    .setName("ttl")
                    .setType(ttlTypeNode)
                    .setIsMessage(true)
                    .setIsContainedInOneof(true)
                    .build())
            .build();
    List<MethodArgument> arguments = Arrays.asList(ttl);
    Method method =
        Method.builder()
            .setName("Wait")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setLro(lro)
            .setMethodSignatures(Arrays.asList(arguments))
            .build();

    String results =
        ServiceClientMethodSampleComposer.composeRpcMethodHeaderSampleCode(
            method, clientType, arguments, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  Duration ttl = Duration.newBuilder().build();\n",
            "  echoClient.waitAsync(ttl).get();\n",
            "}");
    assertEquals(results, expected);
  }
}
