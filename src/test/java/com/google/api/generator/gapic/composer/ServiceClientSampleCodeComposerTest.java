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
import static org.junit.Assert.assertThrows;

import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.gapic.composer.samplecode.SampleCodeWriter;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.ResourceReference;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.testutils.LineFormatter;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class ServiceClientSampleCodeComposerTest {
  private static final String SHOWCASE_PACKAGE_NAME = "com.google.showcase.v1beta1";

  // =======================================RPC Method Header Sample Code=======================//
  @Test
  public void validComposeRpcMethodHeaderSampleCode_pureUnaryRpc() {
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
    List<MethodArgument> methodArguments = Collections.emptyList();
    Method method =
        Method.builder()
            .setName("echo")
            .setMethodSignatures(Arrays.asList(methodArguments))
            .setInputType(inputType)
            .setOutputType(outputType)
            .build();
    String results =
        ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
            method, clientType, methodArguments, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  EchoResponse response = echoClient.echo();\n",
            "}");
    assertEquals(expected, results);
  }

  @Test
  public void validComposeRpcMethodHeaderSampleCode_pagedUnaryRpc() {
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
            .setName("SimplePagedExpand")
            .setMethodSignatures(Collections.emptyList())
            .setInputType(inputType)
            .setOutputType(outputType)
            .setIsPaged(true)
            .build();
    String results =
        ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
            method, clientType, Collections.emptyList(), resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  for (EchoResponse element : echoClient.simplePagedExpand().iterateAll()) {\n",
            "    // doThingsWith(element);\n",
            "  }\n",
            "}");
    assertEquals(expected, results);
  }

  @Test
  public void invalidComposeRpcMethodHeaderSampleCode_noMatchedRepeatedResponseTypeInPagedMethod() {
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
            .setIsPaged(true)
            .build();
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
                method, clientType, methodArguments, resourceNames, messageTypes));
  }

  @Test
  public void invalidComposeRpcMethodHeaderSampleCode_noRepeatedResponseTypeInPagedMethod() {
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
            .setIsPaged(true)
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
            .setType(outputType)
            .setFields(Arrays.asList(responseField, nextPageToken))
            .build();
    messageTypes.put("PagedResponse", noRepeatedFieldMessage);
    assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
                method, clientType, methodArguments, resourceNames, messageTypes));
  }

  // =======================================Unary RPC Method Sample Code=======================//
  @Test
  public void composeUnaryRpcMethodSampleCode_resourceNameMethodArgument() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
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
    TypeNode resourceNameType =
        TypeNode.withReference(
            ConcreteReference.withClazz(com.google.api.resourcenames.ResourceName.class));
    MethodArgument arg =
        MethodArgument.builder()
            .setName("parent")
            .setType(resourceNameType)
            .setField(
                Field.builder()
                    .setName("parent")
                    .setType(TypeNode.STRING)
                    .setResourceReference(
                        ResourceReference.withType("showcase.googleapis.com/AnythingGoes"))
                    .build())
            .setIsResourceNameHelper(true)
            .build();
    List<List<MethodArgument>> signatures = Arrays.asList(Arrays.asList(arg));
    Method unaryMethod =
        Method.builder()
            .setName("echo")
            .setMethodSignatures(signatures)
            .setInputType(inputType)
            .setOutputType(outputType)
            .build();
    String results =
        SampleCodeWriter.write(
            ServiceClientSampleCodeComposer.composeUnaryRpcMethodSampleCode(
                unaryMethod, clientType, signatures.get(0), resourceNames));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  ResourceName parent = FoobarName.ofProjectFoobarName(\"[PROJECT]\", \"[FOOBAR]\");\n",
            "  EchoResponse response = echoClient.echo(parent);\n",
            "}");
    assertEquals(expected, results);
  }

  @Test
  public void composeUnaryRpcMethodSampleCode_superReferenceIsResourceNameMethodArgument() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
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
    TypeNode methodArgType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("FoobarName")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .setSupertypeReference(
                    ConcreteReference.withClazz(com.google.api.resourcenames.ResourceName.class))
                .build());
    Field methodArgField =
        Field.builder()
            .setName("name")
            .setType(TypeNode.STRING)
            .setResourceReference(ResourceReference.withType("showcase.googleapis.com/Foobar"))
            .build();
    MethodArgument arg =
        MethodArgument.builder()
            .setName("name")
            .setType(methodArgType)
            .setField(methodArgField)
            .setIsResourceNameHelper(true)
            .build();
    List<List<MethodArgument>> signatures = Arrays.asList(Arrays.asList(arg));
    Method unaryMethod =
        Method.builder()
            .setName("echo")
            .setMethodSignatures(signatures)
            .setInputType(inputType)
            .setOutputType(outputType)
            .build();
    String results =
        SampleCodeWriter.write(
            ServiceClientSampleCodeComposer.composeUnaryRpcMethodSampleCode(
                unaryMethod, clientType, signatures.get(0), resourceNames));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  FoobarName name = FoobarName.ofProjectFoobarName(\"[PROJECT]\", \"[FOOBAR]\");\n",
            "  EchoResponse response = echoClient.echo(name);\n",
            "}");
    assertEquals(expected, results);
  }

  @Test
  public void composeUnaryRpcMethodSampleCode_stringWithResourceReferenceMethodArgument() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
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
    Field methodArgField =
        Field.builder()
            .setName("name")
            .setType(TypeNode.STRING)
            .setResourceReference(ResourceReference.withType("showcase.googleapis.com/Foobar"))
            .build();
    MethodArgument arg =
        MethodArgument.builder()
            .setName("name")
            .setType(TypeNode.STRING)
            .setField(methodArgField)
            .build();
    List<List<MethodArgument>> signatures = Arrays.asList(Arrays.asList(arg));
    Method unaryMethod =
        Method.builder()
            .setName("echo")
            .setMethodSignatures(signatures)
            .setInputType(inputType)
            .setOutputType(outputType)
            .build();
    String results =
        SampleCodeWriter.write(
            ServiceClientSampleCodeComposer.composeUnaryRpcMethodSampleCode(
                unaryMethod, clientType, signatures.get(0), resourceNames));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  String name = FoobarName.ofProjectFoobarName(\"[PROJECT]\", \"[FOOBAR]\").toString();\n",
            "  EchoResponse response = echoClient.echo(name);\n",
            "}");
    assertEquals(expected, results);
  }

  @Test
  public void composeUnaryRpcMethodSampleCode_stringWithParentResourceReferenceMethodArgument() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
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
    Field methodArgField =
        Field.builder()
            .setName("parent")
            .setType(TypeNode.STRING)
            .setResourceReference(
                ResourceReference.withChildType("showcase.googleapis.com/AnythingGoes"))
            .build();
    MethodArgument arg =
        MethodArgument.builder()
            .setName("parent")
            .setType(TypeNode.STRING)
            .setField(methodArgField)
            .build();
    List<List<MethodArgument>> signatures = Arrays.asList(Arrays.asList(arg));
    Method unaryMethod =
        Method.builder()
            .setName("echo")
            .setMethodSignatures(signatures)
            .setInputType(inputType)
            .setOutputType(outputType)
            .build();
    String results =
        SampleCodeWriter.write(
            ServiceClientSampleCodeComposer.composeUnaryRpcMethodSampleCode(
                unaryMethod, clientType, signatures.get(0), resourceNames));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  String parent = FoobarName.ofProjectFoobarName(\"[PROJECT]\", \"[FOOBAR]\").toString();\n",
            "  EchoResponse response = echoClient.echo(parent);\n",
            "}");
    assertEquals(expected, results);
  }

  @Test
  public void composeUnaryRpcMethodSampleCode_isMessageMethodArgument() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
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
    TypeNode methodArgType =
        TypeNode.withReference(
            VaporReference.builder().setName("Status").setPakkage("com.google.rpc").build());
    Field methodArgField =
        Field.builder()
            .setName("error")
            .setType(methodArgType)
            .setIsMessage(true)
            .setIsContainedInOneof(true)
            .build();
    MethodArgument arg =
        MethodArgument.builder()
            .setName("error")
            .setType(methodArgType)
            .setField(methodArgField)
            .build();
    List<List<MethodArgument>> signatures = Arrays.asList(Arrays.asList(arg));
    Method unaryMethod =
        Method.builder()
            .setName("echo")
            .setMethodSignatures(signatures)
            .setInputType(inputType)
            .setOutputType(outputType)
            .build();
    String results =
        SampleCodeWriter.write(
            ServiceClientSampleCodeComposer.composeUnaryRpcMethodSampleCode(
                unaryMethod, clientType, signatures.get(0), resourceNames));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  Status error = Status.newBuilder().build();\n",
            "  EchoResponse response = echoClient.echo(error);\n",
            "}");
    assertEquals(expected, results);
  }

  @Test
  public void composeUnaryRpcMethodSampleCode_multipleWordNameMethodArgument() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
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
    Field methodArgField =
        Field.builder()
            .setName("display_name")
            .setType(TypeNode.STRING)
            .setResourceReference(
                ResourceReference.withChildType("showcase.googleapis.com/AnythingGoes"))
            .build();
    Reference userRef =
        VaporReference.builder().setName("User").setPakkage(SHOWCASE_PACKAGE_NAME).build();
    Field nestFiled =
        Field.builder()
            .setName("user")
            .setType(TypeNode.withReference(userRef))
            .setIsMessage(true)
            .build();
    MethodArgument argDisplayName =
        MethodArgument.builder()
            .setName("display_name")
            .setType(TypeNode.STRING)
            .setField(methodArgField)
            .setNestedFields(Arrays.asList(nestFiled))
            .build();
    MethodArgument argOtherName =
        MethodArgument.builder()
            .setName("other_name")
            .setType(TypeNode.STRING)
            .setField(Field.builder().setName("other_name").setType(TypeNode.STRING).build())
            .setNestedFields(Arrays.asList(nestFiled))
            .build();
    List<List<MethodArgument>> signatures =
        Arrays.asList(Arrays.asList(argDisplayName, argOtherName));
    Method unaryMethod =
        Method.builder()
            .setName("echo")
            .setMethodSignatures(signatures)
            .setInputType(inputType)
            .setOutputType(outputType)
            .build();
    String results =
        SampleCodeWriter.write(
            ServiceClientSampleCodeComposer.composeUnaryRpcMethodSampleCode(
                unaryMethod, clientType, signatures.get(0), resourceNames));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  String displayName = FoobarName.ofProjectFoobarName(\"[PROJECT]\", \"[FOOBAR]\").toString();\n",
            "  String otherName = \"otherName-1946065477\";\n",
            "  EchoResponse response = echoClient.echo(displayName, otherName);\n",
            "}");
    assertEquals(expected, results);
  }

  @Test
  public void composeUnaryRpcMethodSampleCode_stringIsContainedInOneOfMethodArgument() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
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
    Field methodArgField =
        Field.builder()
            .setName("content")
            .setType(TypeNode.STRING)
            .setIsContainedInOneof(true)
            .build();
    MethodArgument arg =
        MethodArgument.builder()
            .setName("content")
            .setType(TypeNode.STRING)
            .setField(methodArgField)
            .build();
    List<List<MethodArgument>> signatures = Arrays.asList(Arrays.asList(arg));
    Method unaryMethod =
        Method.builder()
            .setName("echo")
            .setMethodSignatures(signatures)
            .setInputType(inputType)
            .setOutputType(outputType)
            .build();
    String results =
        SampleCodeWriter.write(
            ServiceClientSampleCodeComposer.composeUnaryRpcMethodSampleCode(
                unaryMethod, clientType, signatures.get(0), resourceNames));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  String content = \"content951530617\";\n",
            "  EchoResponse response = echoClient.echo(content);\n",
            "}");
    assertEquals(expected, results);
  }

  @Test
  public void composeUnaryRpcMethodSampleCode_multipleMethodArguments() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
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
    MethodArgument arg1 =
        MethodArgument.builder()
            .setName("content")
            .setType(TypeNode.STRING)
            .setField(Field.builder().setName("content").setType(TypeNode.STRING).build())
            .build();
    TypeNode severityType =
        TypeNode.withReference(
            VaporReference.builder().setName("Severity").setPakkage(SHOWCASE_PACKAGE_NAME).build());
    MethodArgument arg2 =
        MethodArgument.builder()
            .setName("severity")
            .setType(severityType)
            .setField(
                Field.builder().setName("severity").setType(severityType).setIsEnum(true).build())
            .build();
    List<List<MethodArgument>> signatures = Arrays.asList(Arrays.asList(arg1, arg2));
    Method unaryMethod =
        Method.builder()
            .setName("echo")
            .setMethodSignatures(signatures)
            .setInputType(inputType)
            .setOutputType(outputType)
            .build();
    String results =
        SampleCodeWriter.write(
            ServiceClientSampleCodeComposer.composeUnaryRpcMethodSampleCode(
                unaryMethod, clientType, signatures.get(0), resourceNames));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  String content = \"content951530617\";\n",
            "  Severity severity = Severity.forNumber(0);\n",
            "  EchoResponse response = echoClient.echo(content, severity);\n",
            "}");
    assertEquals(expected, results);
  }

  @Test
  public void composeUnaryRpcMethodSampleCode_noMethodArguments() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
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
    List<List<MethodArgument>> signatures = Arrays.asList(Collections.emptyList());
    Method unaryMethod =
        Method.builder()
            .setName("echo")
            .setMethodSignatures(signatures)
            .setInputType(inputType)
            .setOutputType(outputType)
            .build();
    String results =
        SampleCodeWriter.write(
            ServiceClientSampleCodeComposer.composeUnaryRpcMethodSampleCode(
                unaryMethod, clientType, signatures.get(0), resourceNames));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  EchoResponse response = echoClient.echo();\n",
            "}");
    assertEquals(expected, results);
  }

  @Test
  public void composeUnaryRpcMethodSampleCode_methodReturnVoid() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode inputType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("DeleteUserRequest")
                .setPakkage("com.google.showcase.v1beta1")
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder().setName("Empty").setPakkage("com.google.protobuf").build());
    List<List<MethodArgument>> methodSignatures =
        Arrays.asList(
            Arrays.asList(
                MethodArgument.builder()
                    .setName("name")
                    .setType(TypeNode.STRING)
                    .setField(Field.builder().setName("name").setType(TypeNode.STRING).build())
                    .build()));
    Method unaryMethod =
        Method.builder()
            .setName("delete")
            .setMethodSignatures(methodSignatures)
            .setInputType(inputType)
            .setOutputType(outputType)
            .build();
    String results =
        SampleCodeWriter.write(
            ServiceClientSampleCodeComposer.composeUnaryRpcMethodSampleCode(
                unaryMethod, clientType, methodSignatures.get(0), resourceNames));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  String name = \"name3373707\";\n",
            "  echoClient.delete(name);\n",
            "}");
    assertEquals(results, expected);
  }

  // ===================================Unary Paged RPC Method Sample Code ======================//
  @Test
  public void composeUnaryPagedRpcMethodSampleCode_multipleMethodArguments() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
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
            .setIsPaged(true)
            .build();
    TypeNode repeatedResponseType =
        TypeNode.withReference(
            VaporReference.builder().setName("Content").setPakkage(SHOWCASE_PACKAGE_NAME).build());

    String results =
        SampleCodeWriter.write(
            ServiceClientSampleCodeComposer.composeUnaryPagedRpcMethodSampleCode(
                method, arguments, clientType, resourceNames, repeatedResponseType));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  List<String> resourceName = new ArrayList<>();\n",
            "  String filter = \"filter-1274492040\";\n",
            "  for (Content element : echoClient.listContent(resourceName, filter).iterateAll()) {\n",
            "    // doThingsWith(element);\n",
            "  }\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void composeUnaryPagedRpcMethodSampleCode_noMethodArguments() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
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
            .setIsPaged(true)
            .build();
    TypeNode repeatedResponseType =
        TypeNode.withReference(
            VaporReference.builder().setName("Content").setPakkage(SHOWCASE_PACKAGE_NAME).build());

    String results =
        SampleCodeWriter.write(
            ServiceClientSampleCodeComposer.composeUnaryPagedRpcMethodSampleCode(
                method, arguments, clientType, resourceNames, repeatedResponseType));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  for (Content element : echoClient.listContent().iterateAll()) {\n",
            "    // doThingsWith(element);\n",
            "  }\n",
            "}");
    assertEquals(results, expected);
  }
}
