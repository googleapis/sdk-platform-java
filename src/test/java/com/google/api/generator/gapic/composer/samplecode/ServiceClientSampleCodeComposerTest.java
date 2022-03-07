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

import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.LongrunningOperation;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Method.Stream;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.testutils.LineFormatter;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class ServiceClientSampleCodeComposerTest {
  private static final String SHOWCASE_PACKAGE_NAME = "com.google.showcase.v1beta1";
  private static final String LRO_PACKAGE_NAME = "com.google.longrunning";
  private static final String PROTO_PACKAGE_NAME = "com.google.protobuf";
  private static final String PAGINATED_FIELD_NAME = "page_size";

  // =============================== Class Header Sample Code ===============================//
  @Test
  public void composeClassHeaderMethodSampleCode_unaryRpc() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Service> services =
        Parser.parseService(
            echoFileDescriptor, messageTypes, resourceNames, Optional.empty(), outputResourceNames);
    Service echoProtoService = services.get(0);
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    Sample sample =
        ServiceClientSampleCodeComposer.composeClassHeaderMethodSampleCode(
            echoProtoService, clientType, resourceNames, messageTypes);
    String results = writeStatements(sample);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  EchoResponse response = echoClient.echo();\n",
            "}");
    Assert.assertEquals(expected, results);
  }

  @Test
  public void composeClassHeaderMethodSampleCode_firstMethodIsNotUnaryRpc() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
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
    Method method =
        Method.builder()
            .setName("Wait")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setLro(lro)
            .setMethodSignatures(Arrays.asList(Arrays.asList(ttl)))
            .build();
    Service service =
        Service.builder()
            .setName("Echo")
            .setDefaultHost("localhost:7469")
            .setOauthScopes(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"))
            .setPakkage(SHOWCASE_PACKAGE_NAME)
            .setProtoPakkage(SHOWCASE_PACKAGE_NAME)
            .setOriginalJavaPackage(SHOWCASE_PACKAGE_NAME)
            .setOverriddenName("Echo")
            .setMethods(Arrays.asList(method))
            .build();
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    String results =
        writeStatements(
            ServiceClientSampleCodeComposer.composeClassHeaderMethodSampleCode(
                service, clientType, resourceNames, messageTypes));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  Duration ttl = Duration.newBuilder().build();\n",
            "  WaitResponse response = echoClient.waitAsync(ttl).get();\n",
            "}");
    Assert.assertEquals(results, expected);
  }

  @Test
  public void composeClassHeaderMethodSampleCode_firstMethodHasNoSignatures() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
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
    Service service =
        Service.builder()
            .setName("Echo")
            .setDefaultHost("localhost:7469")
            .setOauthScopes(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"))
            .setPakkage(SHOWCASE_PACKAGE_NAME)
            .setProtoPakkage(SHOWCASE_PACKAGE_NAME)
            .setOriginalJavaPackage(SHOWCASE_PACKAGE_NAME)
            .setOverriddenName("Echo")
            .setMethods(Arrays.asList(method))
            .build();
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    String results =
        writeStatements(
            ServiceClientSampleCodeComposer.composeClassHeaderMethodSampleCode(
                service, clientType, resourceNames, messageTypes));
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
    Assert.assertEquals(results, expected);
  }

  @Test
  public void composeClassHeaderMethodSampleCode_firstMethodIsStream() {
    FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
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
    Service service =
        Service.builder()
            .setName("Echo")
            .setDefaultHost("localhost:7469")
            .setOauthScopes(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"))
            .setPakkage(SHOWCASE_PACKAGE_NAME)
            .setProtoPakkage(SHOWCASE_PACKAGE_NAME)
            .setOriginalJavaPackage(SHOWCASE_PACKAGE_NAME)
            .setOverriddenName("Echo")
            .setMethods(Arrays.asList(method))
            .build();
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    String results =
        writeStatements(
            ServiceClientSampleCodeComposer.composeClassHeaderMethodSampleCode(
                service, clientType, resourceNames, messageTypes));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  ExpandRequest request =\n",
            "      ExpandRequest.newBuilder().setContent(\"content951530617\").setInfo(\"info3237038\").build();\n",
            "  ServerStream<EchoResponse> stream = echoClient.expandCallable().call(request);\n",
            "  for (EchoResponse response : stream) {\n",
            "    // Do something when a response is received.\n",
            "  }\n",
            "}");
    Assert.assertEquals(results, expected);
  }

  @Test
  public void composeClassHeaderCredentialsSampleCode() {
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode settingsType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoSettings")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    String results =
        writeStatements(
            ServiceClientSampleCodeComposer.composeClassHeaderCredentialsSampleCode(
                clientType, settingsType));
    String expected =
        LineFormatter.lines(
            "EchoSettings echoSettings =\n",
            "    EchoSettings.newBuilder()\n",
            "        .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))\n",
            "        .build();\n",
            "EchoClient echoClient = EchoClient.create(echoSettings);");
    Assert.assertEquals(expected, results);
  }

  @Test
  public void composeClassHeaderEndpointSampleCode() {
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoClient")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    TypeNode settingsType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EchoSettings")
                .setPakkage(SHOWCASE_PACKAGE_NAME)
                .build());
    String results =
        writeStatements(
            ServiceClientSampleCodeComposer.composeClassHeaderEndpointSampleCode(
                clientType, settingsType));
    String expected =
        LineFormatter.lines(
            "EchoSettings echoSettings ="
                + " EchoSettings.newBuilder().setEndpoint(myEndpoint).build();\n",
            "EchoClient echoClient = EchoClient.create(echoSettings);");
    Assert.assertEquals(expected, results);
  }

  // =======================================Unary RPC Method Sample Code=======================//
  /*
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
    Assert.assertEquals(expected, results);
  }
  @Test
  public void validComposeRpcMethodHeaderSampleCode_pureUnaryRpcWithResourceNameMethodArgument() {
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
    Method method =
        Method.builder()
            .setName("echo")
            .setMethodSignatures(signatures)
            .setInputType(inputType)
            .setOutputType(outputType)
            .build();
    String results =
        ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
            method, clientType, signatures.get(0), resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  ResourceName parent = FoobarName.ofProjectFoobarName(\"[PROJECT]\","
                + " \"[FOOBAR]\");\n",
            "  EchoResponse response = echoClient.echo(parent);\n",
            "}");
    Assert.assertEquals(expected, results);
  }
  @Test
  public void
      validComposeRpcMethodHeaderSampleCode_pureUnaryRpcWithSuperReferenceIsResourceNameMethodArgument() {
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
        ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
            unaryMethod, clientType, signatures.get(0), resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  FoobarName name = FoobarName.ofProjectFoobarName(\"[PROJECT]\", \"[FOOBAR]\");\n",
            "  EchoResponse response = echoClient.echo(name);\n",
            "}");
    Assert.assertEquals(expected, results);
  }
  @Test
  public void
      validComposeRpcMethodHeaderSampleCode_pureUnaryRpcWithStringWithResourceReferenceMethodArgument() {
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
        ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
            unaryMethod, clientType, signatures.get(0), resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  String name = FoobarName.ofProjectFoobarName(\"[PROJECT]\","
                + " \"[FOOBAR]\").toString();\n",
            "  EchoResponse response = echoClient.echo(name);\n",
            "}");
    Assert.assertEquals(expected, results);
  }
  @Test
  public void
      validComposeRpcMethodHeaderSampleCode_pureUnaryRpcWithStringWithParentResourceReferenceMethodArgument() {
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
        ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
            unaryMethod, clientType, signatures.get(0), resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  String parent = FoobarName.ofProjectFoobarName(\"[PROJECT]\","
                + " \"[FOOBAR]\").toString();\n",
            "  EchoResponse response = echoClient.echo(parent);\n",
            "}");
    Assert.assertEquals(expected, results);
  }
  @Test
  public void validComposeRpcMethodHeaderSampleCode_pureUnaryRpcWithIsMessageMethodArgument() {
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
        ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
            unaryMethod, clientType, signatures.get(0), resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  Status error = Status.newBuilder().build();\n",
            "  EchoResponse response = echoClient.echo(error);\n",
            "}");
    Assert.assertEquals(expected, results);
  }
  @Test
  public void
      validComposeRpcMethodHeaderSampleCode_pureUnaryRpcWithMultipleWordNameMethodArgument() {
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
        ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
            unaryMethod, clientType, signatures.get(0), resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  String displayName = FoobarName.ofProjectFoobarName(\"[PROJECT]\","
                + " \"[FOOBAR]\").toString();\n",
            "  String otherName = \"otherName-1946065477\";\n",
            "  EchoResponse response = echoClient.echo(displayName, otherName);\n",
            "}");
    Assert.assertEquals(expected, results);
  }
  @Test
  public void
      validComposeRpcMethodHeaderSampleCode_pureUnaryRpcWithStringIsContainedInOneOfMethodArgument() {
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
        ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
            unaryMethod, clientType, signatures.get(0), resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  String content = \"content951530617\";\n",
            "  EchoResponse response = echoClient.echo(content);\n",
            "}");
    Assert.assertEquals(expected, results);
  }
  @Test
  public void validComposeRpcMethodHeaderSampleCode_pureUnaryRpcWithMultipleMethodArguments() {
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
        ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
            unaryMethod, clientType, signatures.get(0), resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  String content = \"content951530617\";\n",
            "  Severity severity = Severity.forNumber(0);\n",
            "  EchoResponse response = echoClient.echo(content, severity);\n",
            "}");
    Assert.assertEquals(expected, results);
  }
  @Test
  public void validComposeRpcMethodHeaderSampleCode_pureUnaryRpcWithNoMethodArguments() {
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
    List<List<MethodArgument>> signatures = Arrays.asList(Collections.emptyList());
    Method unaryMethod =
        Method.builder()
            .setName("echo")
            .setMethodSignatures(signatures)
            .setInputType(inputType)
            .setOutputType(outputType)
            .build();
    String results =
        ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
            unaryMethod, clientType, signatures.get(0), resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  EchoResponse response = echoClient.echo();\n",
            "}");
    Assert.assertEquals(expected, results);
  }
  @Test
  public void validComposeRpcMethodHeaderSampleCode_pureUnaryRpcWithMethodReturnVoid() {
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
                .setName("DeleteUserRequest")
                .setPakkage("com.google.showcase.v1beta1")
                .build());
    TypeNode outputType =
        TypeNode.withReference(
            VaporReference.builder().setName("Empty").setPakkage(PROTO_PACKAGE_NAME).build());
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
        ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
            unaryMethod, clientType, methodSignatures.get(0), resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  String name = \"name3373707\";\n",
            "  echoClient.delete(name);\n",
            "}");
    Assert.assertEquals(results, expected);
  }
  */

  // ===================================Unary Paged RPC Method Sample Code ======================//
  @Test
  public void validComposeRpcMethodHeaderSampleCode_pagedRpcWithMultipleMethodArguments() {
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
        writeStatements(
            ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
                method, clientType, arguments, resourceNames, messageTypes));
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
    Assert.assertEquals(results, expected);
  }

  @Test
  public void validComposeRpcMethodHeaderSampleCode_pagedRpcWithNoMethodArguments() {
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
        writeStatements(
            ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
                method, clientType, arguments, resourceNames, messageTypes));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  for (Content element : echoClient.listContent().iterateAll()) {\n",
            "    // doThingsWith(element);\n",
            "  }\n",
            "}");
    Assert.assertEquals(results, expected);
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
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    Assert.assertThrows(
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
    Assert.assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
                method, clientType, methodArguments, resourceNames, messageTypes));
  }

  // ===================================Unary LRO RPC Method Sample Code ======================//
  @Test
  public void validComposeRpcMethodHeaderSampleCode_lroUnaryRpcWithNoMethodArgument() {
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
            .setMethodSignatures(Collections.emptyList())
            .build();

    String results =
        writeStatements(
            ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
                method, clientType, Collections.emptyList(), resourceNames, messageTypes));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitResponse response = echoClient.waitAsync().get();\n",
            "}");
    Assert.assertEquals(results, expected);
  }

  @Test
  public void validComposeRpcMethodHeaderSampleCode_lroRpcWithReturnResponseType() {
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
        writeStatements(
            ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
                method, clientType, arguments, resourceNames, messageTypes));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  Duration ttl = Duration.newBuilder().build();\n",
            "  WaitResponse response = echoClient.waitAsync(ttl).get();\n",
            "}");
    Assert.assertEquals(results, expected);
  }

  @Test
  public void validComposeRpcMethodHeaderSampleCode_lroRpcWithReturnVoid() {
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
        writeStatements(
            ServiceClientSampleCodeComposer.composeRpcMethodHeaderSampleCode(
                method, clientType, arguments, resourceNames, messageTypes));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  Duration ttl = Duration.newBuilder().build();\n",
            "  echoClient.waitAsync(ttl).get();\n",
            "}");
    Assert.assertEquals(results, expected);
  }

  // ================================Unary RPC Default Method Sample Code ====================//
  @Test
  public void validComposeRpcDefaultMethodHeaderSampleCode_isPagedMethod() {
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
        writeStatements(
            ServiceClientSampleCodeComposer.composeRpcDefaultMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
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
    Assert.assertEquals(results, expected);
  }

  @Test
  public void invalidComposeRpcDefaultMethodHeaderSampleCode_isPagedMethod() {
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
            .setMethodSignatures(Collections.emptyList())
            .setPageSizeFieldName(PAGINATED_FIELD_NAME)
            .build();
    Assert.assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientSampleCodeComposer.composeRpcDefaultMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
  }

  @Test
  public void validComposeRpcDefaultMethodHeaderSampleCode_hasLroMethodWithReturnResponse() {
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
            .setMethodSignatures(Collections.emptyList())
            .setLro(lro)
            .build();
    String results =
        writeStatements(
            ServiceClientSampleCodeComposer.composeRpcDefaultMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitRequest request = WaitRequest.newBuilder().build();\n",
            "  echoClient.waitAsync(request).get();\n",
            "}");
    Assert.assertEquals(results, expected);
  }

  @Test
  public void validComposeRpcDefaultMethodHeaderSampleCode_hasLroMethodWithReturnVoid() {
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
            .setMethodSignatures(Collections.emptyList())
            .setLro(lro)
            .build();
    String results =
        writeStatements(
            ServiceClientSampleCodeComposer.composeRpcDefaultMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitRequest request = WaitRequest.newBuilder().build();\n",
            "  WaitResponse response = echoClient.waitAsync(request).get();\n",
            "}");
    Assert.assertEquals(results, expected);
  }

  @Test
  public void validComposeRpcDefaultMethodHeaderSampleCode_pureUnaryReturnVoid() {
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
            VaporReference.builder().setName("Empty").setPakkage(PROTO_PACKAGE_NAME).build());
    Method method =
        Method.builder()
            .setName("Echo")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setMethodSignatures(Collections.emptyList())
            .build();
    String results =
        writeStatements(
            ServiceClientSampleCodeComposer.composeRpcDefaultMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
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
    Assert.assertEquals(results, expected);
  }

  @Test
  public void validComposeRpcDefaultMethodHeaderSampleCode_pureUnaryReturnResponse() {
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
            .setName("Echo")
            .setInputType(inputType)
            .setOutputType(outputType)
            .setMethodSignatures(Collections.emptyList())
            .build();
    String results =
        writeStatements(
            ServiceClientSampleCodeComposer.composeRpcDefaultMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
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
    Assert.assertEquals(results, expected);
  }

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
        writeStatements(
            ServiceClientSampleCodeComposer.composeLroCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitRequest request = WaitRequest.newBuilder().build();\n",
            "  OperationFuture<WaitResponse, WaitMetadata> future =\n",
            "      echoClient.waitOperationCallable().futureCall(request);\n",
            "  // Do something.\n",
            "  WaitResponse response = future.get();\n",
            "}");
    Assert.assertEquals(results, expected);
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
        writeStatements(
            ServiceClientSampleCodeComposer.composeLroCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitRequest request = WaitRequest.newBuilder().build();\n",
            "  OperationFuture<Empty, WaitMetadata> future =\n",
            "      echoClient.waitOperationCallable().futureCall(request);\n",
            "  // Do something.\n",
            "  future.get();\n",
            "}");
    Assert.assertEquals(results, expected);
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
        writeStatements(
            ServiceClientSampleCodeComposer.composePagedCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
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
    Assert.assertEquals(results, expected);
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
    Assert.assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientSampleCodeComposer.composePagedCallableMethodHeaderSampleCode(
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
    Assert.assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientSampleCodeComposer.composePagedCallableMethodHeaderSampleCode(
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
    Assert.assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientSampleCodeComposer.composePagedCallableMethodHeaderSampleCode(
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
        writeStatements(
            ServiceClientSampleCodeComposer.composeStreamCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  ExpandRequest request =\n",
            "      ExpandRequest.newBuilder().setContent(\"content951530617\").setInfo(\"info3237038\").build();\n",
            "  ServerStream<EchoResponse> stream = echoClient.expandCallable().call(request);\n",
            "  for (EchoResponse response : stream) {\n",
            "    // Do something when a response is received.\n",
            "  }\n",
            "}");
    Assert.assertEquals(results, expected);
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
    Assert.assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientSampleCodeComposer.composeStreamCallableMethodHeaderSampleCode(
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
        writeStatements(
            ServiceClientSampleCodeComposer.composeStreamCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
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
    Assert.assertEquals(results, expected);
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
    Assert.assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientSampleCodeComposer.composeStreamCallableMethodHeaderSampleCode(
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
        writeStatements(
            ServiceClientSampleCodeComposer.composeStreamCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
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
    Assert.assertEquals(results, expected);
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
    Assert.assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientSampleCodeComposer.composeStreamCallableMethodHeaderSampleCode(
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
        writeStatements(
            ServiceClientSampleCodeComposer.composeRegularCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
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
    Assert.assertEquals(results, expected);
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
        writeStatements(
            ServiceClientSampleCodeComposer.composeRegularCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitRequest request = WaitRequest.newBuilder().build();\n",
            "  ApiFuture<Operation> future = echoClient.waitCallable().futureCall(request);\n",
            "  // Do something.\n",
            "  Operation response = future.get();\n",
            "}");
    Assert.assertEquals(results, expected);
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
        writeStatements(
            ServiceClientSampleCodeComposer.composeRegularCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  WaitRequest request = WaitRequest.newBuilder().build();\n",
            "  ApiFuture<Operation> future = echoClient.waitCallable().futureCall(request);\n",
            "  // Do something.\n",
            "  future.get();\n",
            "}");
    Assert.assertEquals(results, expected);
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
        writeStatements(
            ServiceClientSampleCodeComposer.composeRegularCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
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
    Assert.assertEquals(results, expected);
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
    Assert.assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientSampleCodeComposer.composeRegularCallableMethodHeaderSampleCode(
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
    Assert.assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientSampleCodeComposer.composeRegularCallableMethodHeaderSampleCode(
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
    Assert.assertThrows(
        NullPointerException.class,
        () ->
            ServiceClientSampleCodeComposer.composeRegularCallableMethodHeaderSampleCode(
                method, clientType, resourceNames, messageTypes));
  }

  private String writeStatements(Sample sample) {
    return SampleCodeWriter.write(sample.body());
  }
}
