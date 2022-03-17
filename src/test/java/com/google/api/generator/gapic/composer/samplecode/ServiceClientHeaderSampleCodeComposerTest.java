package com.google.api.generator.gapic.composer.samplecode;

import static org.junit.Assert.assertEquals;

import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.LongrunningOperation;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.testutils.LineFormatter;
import com.google.protobuf.Descriptors;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.Test;

public class ServiceClientHeaderSampleCodeComposerTest {
  private static final String SHOWCASE_PACKAGE_NAME = "com.google.showcase.v1beta1";
  private static final String LRO_PACKAGE_NAME = "com.google.longrunning";
  private static final String PROTO_PACKAGE_NAME = "com.google.protobuf";
  private static final String PAGINATED_FIELD_NAME = "page_size";

  // =============================== Class Header Sample Code ===============================//
  @Test
  public void composeClassHeaderMethodSampleCode_unaryRpc() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
    String results =
        ServiceClientHeaderSampleComposer.composeClassHeaderMethodSampleCode(
            echoProtoService, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  EchoResponse response = echoClient.echo();\n",
            "}");
    assertEquals(expected, results);
  }

  @Test
  public void composeClassHeaderMethodSampleCode_firstMethodIsNotUnaryRpc() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
        ServiceClientHeaderSampleComposer.composeClassHeaderMethodSampleCode(
            service, clientType, resourceNames, messageTypes);
    String expected =
        LineFormatter.lines(
            "try (EchoClient echoClient = EchoClient.create()) {\n",
            "  Duration ttl = Duration.newBuilder().build();\n",
            "  WaitResponse response = echoClient.waitAsync(ttl).get();\n",
            "}");
    assertEquals(results, expected);
  }

  @Test
  public void composeClassHeaderMethodSampleCode_firstMethodHasNoSignatures() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
        ServiceClientHeaderSampleComposer.composeClassHeaderMethodSampleCode(
            service, clientType, resourceNames, messageTypes);
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

  @Test
  public void composeClassHeaderMethodSampleCode_firstMethodIsStream() {
    Descriptors.FileDescriptor echoFileDescriptor = EchoOuterClass.getDescriptor();
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
            .setStream(Method.Stream.SERVER)
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
        ServiceClientHeaderSampleComposer.composeClassHeaderMethodSampleCode(
            service, clientType, resourceNames, messageTypes);
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
        ServiceClientHeaderSampleComposer.composeClassHeaderCredentialsSampleCode(
            clientType, settingsType);
    String expected =
        LineFormatter.lines(
            "EchoSettings echoSettings =\n",
            "    EchoSettings.newBuilder()\n",
            "        .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))\n",
            "        .build();\n",
            "EchoClient echoClient = EchoClient.create(echoSettings);");
    assertEquals(expected, results);
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
        ServiceClientHeaderSampleComposer.composeClassHeaderEndpointSampleCode(
            clientType, settingsType);
    String expected =
        LineFormatter.lines(
            "EchoSettings echoSettings ="
                + " EchoSettings.newBuilder().setEndpoint(myEndpoint).build();\n",
            "EchoClient echoClient = EchoClient.create(echoSettings);");
    assertEquals(expected, results);
  }
}
