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

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import com.google.api.AnnotationsProto;
import com.google.api.ClientProto;
import com.google.api.FieldBehaviorProto;
import com.google.api.HttpProto;
import com.google.api.LaunchStageProto;
import com.google.api.ResourceProto;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.ResourceReference;
import com.google.api.generator.gapic.model.Transport;
import com.google.bookshop.v1beta1.BookshopProto;
import com.google.cloud.location.LocationsProto;
import com.google.common.collect.ImmutableList;
import com.google.iam.v1.IamPolicyProto;
import com.google.iam.v1.OptionsProto;
import com.google.iam.v1.PolicyProto;
import com.google.longrunning.OperationsProto;
import com.google.protobuf.AnyProto;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.DurationProto;
import com.google.protobuf.EmptyProto;
import com.google.protobuf.FieldMaskProto;
import com.google.protobuf.TimestampProto;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorRequest;
import com.google.rpc.StatusProto;
import com.google.showcase.v1beta1.EchoOuterClass;
import com.google.showcase.v1beta1.TestingOuterClass;
import com.google.testgapic.v1beta1.LockerProto;
import com.google.type.ExprProto;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class ParserTest {
  private static final String ECHO_PACKAGE = "com.google.showcase.v1beta1";
  // TODO(miraleung): Backfill with more tests (e.g. field, message, methods) for Parser.java.
  private ServiceDescriptor echoService;
  private FileDescriptor echoFileDescriptor;

  @Before
  public void setUp() {
    echoFileDescriptor = EchoOuterClass.getDescriptor();
    echoService = echoFileDescriptor.getServices().get(0);
    assertEquals("Echo", echoService.getName());
  }

  @Test
  public void parseMessages_basic() {
    // TODO(miraleung): Add more tests for oneofs and other message-parsing edge cases.
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);

    Message echoRequestMessage = messageTypes.get("com.google.showcase.v1beta1.EchoRequest");
    Field echoRequestNameField = echoRequestMessage.fieldMap().get("name");
    assertTrue(echoRequestNameField.hasResourceReference());

    String echoResponseName = "EchoResponse";
    Field echoResponseContentField =
        Field.builder().setName("content").setType(TypeNode.STRING).build();
    Field echoResponseSeverityField =
        Field.builder()
            .setName("severity")
            .setType(
                TypeNode.withReference(
                    VaporReference.builder().setName("Severity").setPakkage(ECHO_PACKAGE).build()))
            .setIsEnum(true)
            .build();
    TypeNode echoResponseType =
        TypeNode.withReference(
            VaporReference.builder().setName(echoResponseName).setPakkage(ECHO_PACKAGE).build());

    Message echoResponseMessage =
        Message.builder()
            .setType(echoResponseType)
            .setName(echoResponseName)
            .setFullProtoName("google.showcase.v1beta1." + echoResponseName)
            .setFields(Arrays.asList(echoResponseContentField, echoResponseSeverityField))
            .build();

    assertEquals(
        echoResponseMessage, messageTypes.get("com.google.showcase.v1beta1." + echoResponseName));
  }

  @Test
  public void parseMessages_fieldNameConflicts() {
    FileDescriptor bookshopFileDescriptor = BookshopProto.getDescriptor();
    Map<String, Message> messageTypes = Parser.parseMessages(bookshopFileDescriptor);
    Message requestMessage = messageTypes.get("com.google.bookshop.v1beta1.GetBookRequest");
    // Check that field names have been changed.
    assertThat(requestMessage.fieldMap()).containsKey("books_count1");
    assertThat(requestMessage.fieldMap()).containsKey("books_list2");
    assertThat(requestMessage.fieldMap()).containsKey("books3");
  }

  @Test
  public void parseMethods_basic() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Method> methods =
        Parser.parseMethods(
            echoService,
            ECHO_PACKAGE,
            messageTypes,
            resourceNames,
            Optional.empty(),
            outputResourceNames,
            Transport.GRPC);

    assertEquals(10, methods.size());

    // Methods should appear in the same order as in the protobuf file.
    Method echoMethod = methods.get(0);
    assertEquals(echoMethod.name(), "Echo");
    assertEquals(echoMethod.stream(), Method.Stream.NONE);

    // Detailed method signature parsing tests are in a separate unit test.
    List<List<MethodArgument>> methodSignatures = echoMethod.methodSignatures();
    assertEquals(8, methodSignatures.size());

    Method expandMethod = methods.get(1);
    assertEquals("Expand", expandMethod.name());
    assertEquals(
        TypeNode.withReference(
            VaporReference.builder().setName("ExpandRequest").setPakkage(ECHO_PACKAGE).build()),
        expandMethod.inputType());
    assertEquals(
        TypeNode.withReference(
            VaporReference.builder().setName("EchoResponse").setPakkage(ECHO_PACKAGE).build()),
        expandMethod.outputType());
    assertEquals(Method.Stream.SERVER, expandMethod.stream());
    assertEquals(1, expandMethod.methodSignatures().size());
    assertMethodArgumentEquals(
        "content",
        TypeNode.STRING,
        ImmutableList.of(),
        expandMethod.methodSignatures().get(0).get(0));
    assertMethodArgumentEquals(
        "error",
        TypeNode.withReference(createStatusReference()),
        ImmutableList.of(),
        expandMethod.methodSignatures().get(0).get(1));

    Method collectMethod = methods.get(2);
    assertEquals("Collect", collectMethod.name());
    assertEquals(Method.Stream.CLIENT, collectMethod.stream());

    Method chatMethod = methods.get(3);
    assertEquals("Chat", chatMethod.name());
    assertEquals(Method.Stream.BIDI, chatMethod.stream());
  }

  @Test
  public void parseMethods_basicLro() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    List<Method> methods =
        Parser.parseMethods(
            echoService,
            ECHO_PACKAGE,
            messageTypes,
            resourceNames,
            Optional.empty(),
            outputResourceNames,
            Transport.GRPC);

    assertEquals(10, methods.size());

    // Methods should appear in the same order as in the protobuf file.
    Method waitMethod = methods.get(7);
    assertEquals("Wait", waitMethod.name());
    assertTrue(waitMethod.hasLro());
    TypeNode waitResponseType = messageTypes.get("com.google.showcase.v1beta1.WaitResponse").type();
    TypeNode waitMetadataType = messageTypes.get("com.google.showcase.v1beta1.WaitMetadata").type();
    assertThat(waitMethod.lro().responseType()).isEqualTo(waitResponseType);
    assertThat(waitMethod.lro().metadataType()).isEqualTo(waitMetadataType);
  }

  @Test
  public void parseLro_missingResponseType() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    MethodDescriptor waitMethodDescriptor = echoService.getMethods().get(7);
    assertEquals("Wait", waitMethodDescriptor.getName());
    messageTypes.remove("com.google.showcase.v1beta1.WaitResponse");
    assertThrows(
        NullPointerException.class, () -> Parser.parseLro("", waitMethodDescriptor, messageTypes));
  }

  @Test
  public void parseLro_missingMetadataType() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    MethodDescriptor waitMethodDescriptor = echoService.getMethods().get(7);
    assertEquals("Wait", waitMethodDescriptor.getName());
    messageTypes.remove("com.google.showcase.v1beta1.WaitMetadata");
    assertThrows(
        NullPointerException.class, () -> Parser.parseLro("", waitMethodDescriptor, messageTypes));
  }

  @Test
  public void parseMethodSignatures_empty() {
    // TODO(miraleung): Move this to MethodSignatureParserTest.
    MethodDescriptor methodDescriptor = echoService.getMethods().get(5);
    assertEquals("PagedExpand", methodDescriptor.getName());
    TypeNode inputType = TypeParser.parseType(methodDescriptor.getInputType());
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();

    assertThat(
            MethodSignatureParser.parseMethodSignatures(
                methodDescriptor,
                ECHO_PACKAGE,
                inputType,
                messageTypes,
                resourceNames,
                outputResourceNames))
        .isEmpty();
  }

  @Test
  public void parseMethodSignatures_validArgstAndEmptyString() {
    // TODO(miraleung): Move this to MethodSignatureParserTest.
    MethodDescriptor methodDescriptor = echoService.getMethods().get(0);
    assertEquals("Echo", methodDescriptor.getName());
    TypeNode inputType = TypeParser.parseType(methodDescriptor.getInputType());
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();

    List<List<MethodArgument>> methodArgs =
        MethodSignatureParser.parseMethodSignatures(
            methodDescriptor,
            ECHO_PACKAGE,
            inputType,
            messageTypes,
            resourceNames,
            outputResourceNames);
    assertEquals(Collections.emptyList(), methodArgs.get(0));
    assertEquals(1, methodArgs.get(1).size());
    assertEquals("parent", methodArgs.get(1).get(0).name());
  }

  @Test
  public void parseMethodSignatures_basic() {
    MethodDescriptor echoMethodDescriptor = echoService.getMethods().get(0);
    TypeNode inputType = TypeParser.parseType(echoMethodDescriptor.getInputType());
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();

    List<List<MethodArgument>> methodSignatures =
        MethodSignatureParser.parseMethodSignatures(
            echoMethodDescriptor,
            ECHO_PACKAGE,
            inputType,
            messageTypes,
            resourceNames,
            outputResourceNames);
    assertEquals(8, methodSignatures.size());

    // Signature contents: [].
    List<MethodArgument> methodArgs = methodSignatures.get(0);
    assertEquals(Collections.emptyList(), methodArgs);

    // Signature contents: ["parent"].
    methodArgs = methodSignatures.get(1);
    assertEquals(1, methodArgs.size());
    MethodArgument argument = methodArgs.get(0);
    TypeNode resourceNameType =
        TypeNode.withReference(
            ConcreteReference.withClazz(com.google.api.resourcenames.ResourceName.class));
    assertMethodArgumentEquals("parent", resourceNameType, ImmutableList.of(), argument);

    // Signature contents: ["error"].
    methodArgs = methodSignatures.get(2);
    assertEquals(1, methodArgs.size());
    argument = methodArgs.get(0);
    assertMethodArgumentEquals(
        "error", TypeNode.withReference(createStatusReference()), ImmutableList.of(), argument);

    // Signature contents: ["name"], resource helper variant.
    methodArgs = methodSignatures.get(3);
    assertEquals(1, methodArgs.size());
    argument = methodArgs.get(0);
    TypeNode foobarNameType =
        TypeNode.withReference(
            VaporReference.builder().setName("FoobarName").setPakkage(ECHO_PACKAGE).build());
    assertMethodArgumentEquals("name", foobarNameType, ImmutableList.of(), argument);

    // Signature contents: ["content"].
    methodArgs = methodSignatures.get(4);
    assertEquals(1, methodArgs.size());
    argument = methodArgs.get(0);
    assertMethodArgumentEquals("content", TypeNode.STRING, ImmutableList.of(), argument);

    // Signature contents: ["name"], String variant.
    methodArgs = methodSignatures.get(5);
    assertEquals(1, methodArgs.size());
    argument = methodArgs.get(0);
    assertMethodArgumentEquals("name", TypeNode.STRING, ImmutableList.of(), argument);

    // Signature contents: ["parent"], String variant.
    methodArgs = methodSignatures.get(6);
    assertEquals(1, methodArgs.size());
    argument = methodArgs.get(0);
    assertMethodArgumentEquals("parent", TypeNode.STRING, ImmutableList.of(), argument);

    // Signature contents: ["content", "severity"].
    methodArgs = methodSignatures.get(7);
    assertEquals(2, methodArgs.size());
    argument = methodArgs.get(0);
    assertMethodArgumentEquals("content", TypeNode.STRING, ImmutableList.of(), argument);
    argument = methodArgs.get(1);
    assertMethodArgumentEquals(
        "severity",
        TypeNode.withReference(
            VaporReference.builder().setName("Severity").setPakkage(ECHO_PACKAGE).build()),
        ImmutableList.of(),
        argument);
  }

  @Test
  public void parseMessagesAndResourceNames_update() {
    FileDescriptor lockerServiceFileDescriptor = LockerProto.getDescriptor();
    Map<String, Message> messageTypes = Parser.parseMessages(lockerServiceFileDescriptor);

    Message documentMessage = messageTypes.get("com.google.testgapic.v1beta1.Document");
    assertFalse(documentMessage.hasResource());
    Message folderMessage = messageTypes.get("com.google.testgapic.v1beta1.Folder");
    assertFalse(folderMessage.hasResource());

    Map<String, ResourceName> resourceNames =
        ResourceNameParser.parseResourceNames(lockerServiceFileDescriptor);
    messageTypes = Parser.updateResourceNamesInMessages(messageTypes, resourceNames.values());

    documentMessage = messageTypes.get("com.google.testgapic.v1beta1.Document");
    assertTrue(documentMessage.hasResource());
    folderMessage = messageTypes.get("com.google.testgapic.v1beta1.Folder");
    assertFalse(folderMessage.hasResource());
  }

  @Test
  public void parseMessages_fieldsHaveResourceReferences() {
    FileDescriptor lockerServiceFileDescriptor = LockerProto.getDescriptor();
    Map<String, Message> messageTypes = Parser.parseMessages(lockerServiceFileDescriptor);

    // Child type.
    Message message = messageTypes.get("com.google.testgapic.v1beta1.CreateFolderRequest");
    Field field = message.fieldMap().get("parent");
    assertTrue(field.hasResourceReference());
    ResourceReference resourceReference = field.resourceReference();
    assertEquals(
        "cloudresourcemanager.googleapis.com/Folder", resourceReference.resourceTypeString());
    assertTrue(resourceReference.isChildType());

    // Type.
    message = messageTypes.get("com.google.testgapic.v1beta1.GetFolderRequest");
    field = message.fieldMap().get("name");
    assertTrue(field.hasResourceReference());
    resourceReference = field.resourceReference();
    assertEquals(
        "cloudresourcemanager.googleapis.com/Folder", resourceReference.resourceTypeString());
    assertFalse(resourceReference.isChildType());

    // Non-RPC-specific message.
    message = messageTypes.get("com.google.testgapic.v1beta1.Folder");
    field = message.fieldMap().get("name");
    assertTrue(field.hasResourceReference());
    resourceReference = field.resourceReference();
    assertEquals(
        "cloudresourcemanager.googleapis.com/Folder", resourceReference.resourceTypeString());
    assertFalse(resourceReference.isChildType());

    // No explicit resource_reference annotation on the field, and the resource annotation is in the
    // message.
    message = messageTypes.get("com.google.testgapic.v1beta1.Document");
    field = message.fieldMap().get("name");
    assertTrue(field.hasResourceReference());
    resourceReference = field.resourceReference();
    assertEquals("testgapic.googleapis.com/Document", resourceReference.resourceTypeString());
    assertFalse(resourceReference.isChildType());
  }

  @Test
  public void parseFields_mapType() {
    FileDescriptor testingFileDescriptor = TestingOuterClass.getDescriptor();
    ServiceDescriptor testingService = testingFileDescriptor.getServices().get(0);
    assertEquals(testingService.getName(), "Testing");

    Map<String, Message> messageTypes = Parser.parseMessages(testingFileDescriptor);
    Message message = messageTypes.get("com.google.showcase.v1beta1.Session");
    Field field = message.fieldMap().get("session_ids_to_descriptor");
    assertEquals(
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(Map.class)
                .setGenerics(
                    Arrays.asList(TypeNode.INT_OBJECT.reference(), TypeNode.STRING.reference()))
                .build()),
        field.type());
  }

  @Test
  public void parseResourceNames_inputTypeHasReferenceNotInMethodSignature() {
    FileDescriptor testingFileDescriptor = TestingOuterClass.getDescriptor();
    ServiceDescriptor testingService = testingFileDescriptor.getServices().get(0);
    assertEquals(testingService.getName(), "Testing");

    Map<String, Message> messageTypes = Parser.parseMessages(testingFileDescriptor);
    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(testingFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();
    Parser.parseService(
        testingFileDescriptor, messageTypes, resourceNames, Optional.empty(), outputResourceNames);

    assertEquals(2, outputResourceNames.size());

    ResourceName resname = resourceNames.get("showcase.googleapis.com/Session");
    assertThat(outputResourceNames).contains(resname);

    resname = resourceNames.get("showcase.googleapis.com/Test");
    assertThat(outputResourceNames).contains(resname);
  }

  @Test
  public void sanitizeDefaultHost_basic() {
    String defaultHost = "localhost:1234";
    assertEquals(defaultHost, Parser.sanitizeDefaultHost(defaultHost));

    defaultHost = "logging.googleapis.com";
    assertEquals(String.format("%s:443", defaultHost), Parser.sanitizeDefaultHost(defaultHost));
  }

  @Test
  public void parseWithoutMixinProtos() {
    CodeGeneratorRequest request = echoBuilder().build();

    GapicContext context = Parser.parse(request);
    assertEquals(1, context.services().size());
    assertThat(
            context.services().get(0).methods().stream()
                .map(Method::name)
                .collect(Collectors.toList()))
        .containsAnyIn(
            ImmutableList.of(
                "ListLocations",
                "GetLocation",
                "SetIamPolicy",
                "GetIamPolicy",
                "TestIamPermissions"));
  }

  @Test
  public void parseWithMixinProtos() {
    CodeGeneratorRequest request =
        echoBuilder()
            .addProtoFile(ExprProto.getDescriptor().toProto())
            .addProtoFile(FieldMaskProto.getDescriptor().toProto())
            .addProtoFile(OptionsProto.getDescriptor().toProto())
            .addProtoFile(PolicyProto.getDescriptor().toProto())
            .addProtoFile(IamPolicyProto.getDescriptor().toProto())
            .addProtoFile(LocationsProto.getDescriptor().toProto())
            .addFileToGenerate("google/iam/v1/iam_policy.proto")
            .addFileToGenerate("google/cloud/location/locations.proto")
            .build();

    GapicContext context = Parser.parse(request);
    assertEquals(1, context.services().size());
    assertThat(
        context.services().get(0).methods().stream()
            .map(Method::name)
            .collect(Collectors.toList()))
        .containsAnyIn(
            ImmutableList.of(
                "ListLocations",
                "GetLocation",
                "SetIamPolicy",
                "GetIamPolicy",
                "TestIamPermissions"));
  }

  @Test
  public void parseNestedProtoTypeName() {
    assertEquals("MutateJobMetadata", Parser.parseNestedProtoTypeName("MutateJobMetadata"));
    assertEquals(
        "MutateJob.MutateJobMetadata",
        Parser.parseNestedProtoTypeName("MutateJob.MutateJobMetadata"));
    assertEquals(
        "MutateJob.MutateJobMetadata",
        Parser.parseNestedProtoTypeName(
            "google.ads.googleads.v3.resources.MutateJob.MutateJobMetadata"));
  }

  private void assertMethodArgumentEquals(
      String name, TypeNode type, List<TypeNode> nestedFields, MethodArgument argument) {
    assertEquals(name, argument.name());
    assertEquals(type, argument.type());
    assertEquals(nestedFields, argument.nestedFields());
  }

  private static Reference createStatusReference() {
    return VaporReference.builder().setName("Status").setPakkage("com.google.rpc").build();
  }

  private CodeGeneratorRequest.Builder echoBuilder() {
    return CodeGeneratorRequest.newBuilder()
        .setParameter(
            "metadata,transport=grpc,rest-numeric-enums,api-service-config=src/test/resources/echo_v1beta1.yaml")
        .addProtoFile(HttpProto.getDescriptor().toProto())
        .addProtoFile(LaunchStageProto.getDescriptor().toProto())
        .addProtoFile(AnyProto.getDescriptor().toProto())
        .addProtoFile(DescriptorProtos.getDescriptor().toProto())
        .addProtoFile(DurationProto.getDescriptor().toProto())
        .addProtoFile(EmptyProto.getDescriptor().toProto())
        .addProtoFile(StatusProto.getDescriptor().toProto())
        .addProtoFile(AnnotationsProto.getDescriptor().toProto())
        .addProtoFile(ClientProto.getDescriptor().toProto())
        .addProtoFile(FieldBehaviorProto.getDescriptor().toProto())
        .addProtoFile(ResourceProto.getDescriptor().toProto())
        .addProtoFile(OperationsProto.getDescriptor().toProto())
        .addProtoFile(TimestampProto.getDescriptor().toProto())
        .addProtoFile(EchoOuterClass.getDescriptor().toProto())
        .addFileToGenerate("echo.proto");
  }
}
