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

package com.google.api.generator.spring;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.CastExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.LambdaExpr;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.RelationalOperationExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.ThisObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.composer.common.TestProtoLoader;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicServiceConfig;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.model.Transport;
import com.google.api.generator.gapic.protoparser.Parser;
import com.google.api.generator.gapic.protoparser.ServiceConfigParser;
import com.google.api.generator.spring.composer.SpringAutoConfigClassComposer;
import com.google.api.generator.spring.composer.SpringPropertiesClassComposer;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.showcase.v1beta1.EchoOuterClass;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class SpringAutoConfigClassComposerTest {
  private ServiceDescriptor echoService;
  private FileDescriptor echoFileDescriptor;

  @Before
  public void setUp() {
    echoFileDescriptor = EchoOuterClass.getDescriptor();
    echoService = echoFileDescriptor.getServices().get(0);
    assertEquals(echoService.getName(), "Echo");
  }

  @Test
  public void writeMethodDefinition_constructor() {
    // To be deleted, please ignore: practice AST grammar here.
    JavaWriterVisitor writerVisitor = new JavaWriterVisitor();

    // header
    List<CommentStatement> fileHeader =
        Arrays.asList(CommentStatement.withComment(BlockComment.withComment("Apache License")));
    // annotation types
    TypeNode beanAnnotationType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("Bean")
                .setPakkage("org.springframework.context.annotation")
                .build());
    TypeNode conditionalOnMissingBeanType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("ConditionalOnMissingBean")
                .setPakkage("org.springframework.boot.autoconfigure.condition")
                .build());
    TypeNode conditionalOnPropertyType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("ConditionalOnProperty")
                .setPakkage("org.springframework.boot.autoconfigure.condition")
                .build());
    // AnnotationNode description only accepts String for now. need to extend to params
    AnnotationNode conditionalOnPropertyNode =
        AnnotationNode.builder()
            .setType(conditionalOnPropertyType)
            .setDescription("value = \"spring.cloud.gcp.language.enabled\", matchIfMissing = false")
            .build();
    // client type
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("LanguageServiceClient")
                .setPakkage("com.google.cloud.language.v1")
                .build());
    // build expressions
    MethodInvocationExpr lhsExpr =
        MethodInvocationExpr.builder()
            .setMethodName("create")
            .setStaticReferenceType(clientType)
            .setReturnType(clientType)
            .build();

    VariableExpr variableExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setName("client").setType(clientType).build())
            .setIsDecl(true)
            .build();

    // VariableExpr returnArg = VariableExpr.builder()
    //     .setVariable(createVariable("client", clientType))
    //     .build();

    MethodInvocationExpr expr =
        MethodInvocationExpr.builder()
            .setMethodName("languageServiceClientCreate")
            .setReturnType(clientType)
            .build();

    // AssignmentExpr assignmentExpr = AssignmentExpr.builder().setVariableExpr(variableExpr)
    //     .setValueExpr(expr).build();

    MethodDefinition beanMethod =
        MethodDefinition.builder()
            .setName("languageServiceClient")
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(clientType)
            .setAnnotations(
                Arrays.asList(
                    AnnotationNode.withType(beanAnnotationType),
                    AnnotationNode.withType(conditionalOnMissingBeanType)))
            .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(IOException.class)))
            .setReturnExpr(lhsExpr)
            // .setBody(
            //     Arrays.asList(ExprStatement.withExpr(assignmentExpr)))
            .build();

    // members
    TypeNode credentialsProvider =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("CredentialsProvider")
                .setPakkage("com.google.api.gax.core")
                .build());
    TypeNode thisClassType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("LibraryServiceStub")
                .setPakkage("com.google.example.library.v1")
                .build());
    TypeNode clientProperties =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("LanguageProperties")
                .setPakkage("com.google.example.library.v1")
                .build());
    // import com.google.cloud.spring.core.GcpProjectIdProvider;

    TypeNode gcpProjectIdProvider =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("GcpProjectIdProvider")
                .setPakkage("com.google.cloud.spring.core")
                .build());
    // TypeNode thisClassType =
    // TypeNode thisClassType = typeStore.get(ClassNames.getServiceSettingsClassName(service));

    Variable variable =
        Variable.builder().setName("credentialsProvider").setType(credentialsProvider).build();
    VariableExpr varExpr =
        VariableExpr.builder()
            .setVariable(variable)
            .setScope(ScopeNode.PRIVATE)
            .setIsFinal(true)
            .setIsDecl(true)
            .build();
    ExprStatement statement1 = ExprStatement.withExpr(varExpr);

    // private final LanguageProperties clientProperties;
    Variable propertiesVar =
        Variable.builder().setName("clientProperties").setType(clientProperties).build();
    VariableExpr propertiesVarExpr =
        VariableExpr.builder()
            .setVariable(propertiesVar)
            .setScope(ScopeNode.PRIVATE)
            .setIsFinal(true)
            .setIsDecl(true)
            .build();
    ExprStatement statement2 = ExprStatement.withExpr(propertiesVarExpr);

    // private final GcpProjectIdProvider projectIdProvider;
    Variable projectIdProviderVar =
        Variable.builder().setName("projectIdProvider").setType(gcpProjectIdProvider).build();
    VariableExpr projectIdProviderVarExpr =
        VariableExpr.builder()
            .setVariable(projectIdProviderVar)
            .setScope(ScopeNode.PRIVATE)
            .setIsFinal(true)
            .setIsDecl(true)
            .build();
    ExprStatement statement3 = ExprStatement.withExpr(projectIdProviderVarExpr);

    /// constructor
    VariableExpr credentialsProviderBuilderVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("coreCredentialsProvider")
                .setType(credentialsProvider)
                .build());

    VariableExpr coreProjectIdProviderVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("coreProjectIdProvider")
                .setType(gcpProjectIdProvider)
                .build());

    List<Expr> ctorAssignmentExprs = new ArrayList<>();
    Expr thisExpr = ValueExpr.withValue(ThisObjectValue.withType(thisClassType));
    ctorAssignmentExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.withVariable(projectIdProviderVar)
                    .toBuilder()
                    .setExprReferenceExpr(thisExpr)
                    .build())
            .setValueExpr(coreProjectIdProviderVarExpr)
            .build());

    /**
     * if (clientProperties.getProjectId() != null) { this.projectIdProvider =
     * clientProperties::getProjectId; } else { this.projectIdProvider = coreProjectIdProvider; }
     */

    // expr: clientProperties.getProjectId() != null
    MethodInvocationExpr getProjectIdExpr =
        MethodInvocationExpr.builder()
            .setMethodName("getProjectId")
            .setExprReferenceExpr(VariableExpr.withVariable(propertiesVar).toBuilder().build())
            // .setStaticReferenceType(clientType)
            .setReturnType(credentialsProvider) // fake it
            .build();
    RelationalOperationExpr notEqualSentence =
        RelationalOperationExpr.notEqualToWithExprs(getProjectIdExpr, ValueExpr.createNullExpr());

    // this.projectIdProvider = () -> clientProperties.getProjectId();
    LambdaExpr lambdaExpr = LambdaExpr.builder().setReturnExpr(getProjectIdExpr).build();

    // this.projectIdProvider = () -> clientProperties.getProjectId();
    AssignmentExpr projectIdProviderAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.withVariable(projectIdProviderVar)
                    .toBuilder()
                    .setExprReferenceExpr(thisExpr)
                    .build())
            .setValueExpr(
                CastExpr.builder().setExpr(lambdaExpr).setType(gcpProjectIdProvider).build())
            .build();

    IfStatement ifStatement =
        IfStatement.builder()
            .setConditionExpr(notEqualSentence)
            .setBody(Arrays.asList(ExprStatement.withExpr(projectIdProviderAssignExpr)))
            .setElseBody(
                ctorAssignmentExprs.stream()
                    .map(e -> ExprStatement.withExpr(e))
                    .collect(Collectors.toList()))
            .build();

    MethodDefinition constructor =
        MethodDefinition.constructorBuilder()
            .setScope(ScopeNode.PROTECTED)
            .setReturnType(thisClassType)
            .setArguments(
                Arrays.asList(
                    credentialsProviderBuilderVarExpr.toBuilder().setIsDecl(true).build(),
                    coreProjectIdProviderVarExpr.toBuilder().setIsDecl(true).build()))
            // .setThrowsExceptions(Arrays.asList(FIXED_TYPESTORE.get("IOException")))
            .setBody(Arrays.asList(ifStatement))
            .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(IOException.class)))
            // ctorAssignmentExprs.stream()
            //     .map(e -> ExprStatement.withExpr(e))
            //     .collect(Collectors.toList()))
            .build();
    /// end constructor

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setFileHeader(fileHeader)
            .setPackageString("com.google.example.library.v1")
            .setName("LibraryServiceStub")
            .setScope(ScopeNode.PUBLIC)
            .setStatements(Arrays.asList(statement1, statement2, statement3))
            .setMethods(Arrays.asList(constructor, beanMethod))
            .setAnnotations(Arrays.asList(conditionalOnPropertyNode))
            .build();

    classDef.accept(writerVisitor);
    System.out.println("HERE STARTS OUTPUT: ");
    System.out.println(writerVisitor.write());
    // Assert.assertEquals("public LibrarySettings() {\n}\n\n", writerVisitor.write());
  }

  @Test
  public void generateAutoConfigClasses() {
    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);

    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();

    List<Service> services =
        Parser.parseService(
            echoFileDescriptor, messageTypes, resourceNames, Optional.empty(), outputResourceNames);

    String jsonFilename = "retrying_grpc_service_config.json";
    Path jsonPath = Paths.get(TestProtoLoader.instance().getTestFilesDirectory(), jsonFilename);
    Optional<GapicServiceConfig> serviceConfigOpt = ServiceConfigParser.parse(jsonPath.toString());
    assertTrue(serviceConfigOpt.isPresent());
    GapicServiceConfig serviceConfig = serviceConfigOpt.get();

    GapicContext context =
        GapicContext.builder()
            .setMessages(messageTypes)
            .setResourceNames(resourceNames)
            .setServices(services)
            .setHelperResourceNames(outputResourceNames)
            .setTransport(Transport.GRPC)
            .setServiceConfig(serviceConfig)
            .build();

    Service echoProtoService = services.get(0);
    GapicClass clazz = SpringAutoConfigClassComposer.instance().generate(context, echoProtoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    System.out.println(visitor.write());
    assertEquals(EXPECTED_CLASS_STRING, visitor.write());
  }

  @Test
  public void generatePropertiesTest() {

    Map<String, Message> messageTypes = Parser.parseMessages(echoFileDescriptor);

    Map<String, ResourceName> resourceNames = Parser.parseResourceNames(echoFileDescriptor);
    Set<ResourceName> outputResourceNames = new HashSet<>();

    List<Service> services =
        Parser.parseService(
            echoFileDescriptor, messageTypes, resourceNames, Optional.empty(), outputResourceNames);

    String jsonFilename = "showcase_grpc_service_config.json";
    Path jsonPath = Paths.get(TestProtoLoader.instance().getTestFilesDirectory(), jsonFilename);
    Optional<GapicServiceConfig> serviceConfigOpt = ServiceConfigParser.parse(jsonPath.toString());
    assertTrue(serviceConfigOpt.isPresent());
    GapicServiceConfig serviceConfig = serviceConfigOpt.get();

    GapicContext context =
        GapicContext.builder()
            .setMessages(messageTypes)
            .setResourceNames(resourceNames)
            .setServices(services)
            .setHelperResourceNames(outputResourceNames)
            .setTransport(Transport.GRPC)
            .setServiceConfig(serviceConfig)
            .build();

    Service echoProtoService = services.get(0);

    GapicClass clazz = SpringPropertiesClassComposer.instance().generate(context, echoProtoService);

    JavaWriterVisitor visitor = new JavaWriterVisitor();
    clazz.classDefinition().accept(visitor);
    // TODO: print out to check results for now. Need to convert to assert.
    System.out.println(visitor.write());
    assertEquals(EXPECTED_PROPERTY_CLASS_STRING, visitor.write());
  }

  protected static final String EXPECTED_CLASS_STRING =
      "package com.google.showcase.v1beta1.spring;\n"
          + "\n"
          + "import com.google.api.gax.core.CredentialsProvider;\n"
          + "import com.google.api.gax.core.ExecutorProvider;\n"
          + "import com.google.api.gax.httpjson.InstantiatingHttpJsonChannelProvider;\n"
          + "import com.google.api.gax.retrying.RetrySettings;\n"
          + "import com.google.api.gax.rpc.TransportChannelProvider;\n"
          // + "import com.google.cloud.spring.core.Credentials;\n"
          + "import com.google.cloud.spring.core.DefaultCredentialsProvider;\n"
          // + "import com.google.cloud.spring.core.GcpProjectIdProvider;\n"
          + "import com.google.common.collect.ImmutableMap;\n"
          + "import com.google.showcase.v1beta1.EchoClient;\n"
          + "import com.google.showcase.v1beta1.EchoSettings;\n"
          + "import java.io.IOException;\n"
          + "import javax.annotation.Generated;\n"
          + "import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;\n"
          + "import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;\n"
          + "import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;\n"
          + "import org.springframework.boot.context.properties.EnableConfigurationProperties;\n"
          + "import org.springframework.context.annotation.Bean;\n"
          + "import org.springframework.context.annotation.Configuration;\n"
          + "\n"
          + "@Generated(\"by gapic-generator-java\")\n"
          + "@Configuration(\"proxyBeanMethods = false\")\n"
          + "@ConditionalOnClass(\"value = EchoClient.class\")\n"
          + "@ConditionalOnProperty(\"value = \\\"spring.cloud.gcp.language.enabled\\\", matchIfMissing = false\")\n"
          + "@EnableConfigurationProperties(\"EchoSpringProperties.Class\")\n"
          + "public class EchoSpringAutoConfiguration {\n"
          // + "  private final CredentialsProvider credentialsProvider;\n"
          + "  private final EchoSpringProperties clientProperties;\n"
          // + "  private final GcpProjectIdProvider projectIdProvider;\n"
          + "  private static final ImmutableMap<String, RetrySettings> RETRY_PARAM_DEFINITIONS;\n"
          + "\n"
          + "  static {\n"
          + "    ImmutableMap.Builder<String, RetrySettings> definitions = ImmutableMap.builder();\n"
          + "    RetrySettings settings = null;\n"
          + "    settings = RetrySettings.newBuilder().setRpcTimeoutMultiplier(1.0).build();\n"
          + "    definitions.put(\"no_retry_params\", settings);\n"
          + "    RETRY_PARAM_DEFINITIONS = definitions.build();\n"
          + "  }\n"
          + "\n"
          + "  protected EchoSpringAutoConfiguration("
          // + "      CredentialsProvider coreCredentialsProvider,\n"
          // + "      GcpProjectIdProvider coreProjectIdProvider,\n"
          + "EchoSpringProperties clientProperties) {\n"
          // + "      throws IOException {\n"
          + "    this.clientProperties = clientProperties;\n"
          // + "    if (clientProperties.getCredentials().hasKey()) {\n"
          // + "      this.credentialsProvider =\n"
          // + "          ((CredentialsProvider) new
          // DefaultCredentialsProvider(clientProperties));\n"
          // + "    } else {\n"
          // + "      this.credentialsProvider = ((CredentialsProvider) coreProjectIdProvider);\n"
          // + "    }\n"
          // + "    if (clientProperties.getProjectId() != null) {\n"
          // + "      this.projectIdProvider = ((GcpProjectIdProvider) () ->
          // clientProperties.getProjectId());\n"
          // + "    } else {\n"
          // + "      this.projectIdProvider = coreProjectIdProvider;\n"
          // + "    }\n"
          + "  }\n"
          + "\n"
          + "  @Bean\n"
          + "  @ConditionalOnMissingBean\n"
          + "  public CredentialsProvider googleCredentials() throws IOException {\n"
          + "    return ((CredentialsProvider) new DefaultCredentialsProvider(this.clientProperties));\n"
          + "  }\n"
          + "\n"
          + "  @Bean\n"
          + "  @ConditionalOnMissingBean\n"
          + "  public TransportChannelProvider defaultEchoTransportChannelProvider() {\n"
          + "    return EchoSettings.defaultTransportChannelProvider();\n"
          + "  }\n"
          + "\n"
          + "  @Bean\n"
          + "  @ConditionalOnMissingBean\n"
          + "  public EchoClient echoClient(\n"
          + "      CredentialsProvider credentialsProvider,\n"
          + "      TransportChannelProvider defaultTransportChannelProvider)\n"
          + "      throws IOException {\n"
          + "    EchoSettings.Builder clientSettingsBuilder =\n"
          + "        EchoSettings.newBuilder()\n"
          + "            .setCredentialsProvider(credentialsProvider)\n"
          + "            .setTransportChannelProvider(defaultTransportChannelProvider)\n"
          + "            .setHeaderProvider();\n"
          + "    if (this.clientProperties.getQuotaProjectId() != null) {\n"
          + "      clientSettingsBuilder.setQuotaProjectId(this.clientProperties.getQuotaProjectId());\n"
          + "    }\n"
          + "    if (this.clientProperties.getExecutorThreadCount() != null) {\n"
          + "      ExecutorProvider executorProvider =\n"
          + "          EchoSettings.defaultExecutorProviderBuilder()\n"
          + "              .setExecutorThreadCount(this.clientProperties.getExecutorThreadCount())\n"
          + "              .build();\n"
          + "      clientSettingsBuilder.setBackgroundExecutorProvider(executorProvider);\n"
          + "    }\n"
          + "    if (this.clientProperties.getUseRest()) {\n"
          + "      clientSettingsBuilder.setTransportChannelProvider(\n"
          + "          EchoSettings.defaultHttpJsonTransportProviderBuilder().build());\n"
          + "    }\n"
          + "    return EchoClient.create(clientSettingsBuilder.build());\n"
          + "  }\n"
          + "}\n";

  protected static final String EXPECTED_PROPERTY_CLASS_STRING =
      "package com.google.showcase.v1beta1.spring;\n"
          + "\n"
          + "import com.google.cloud.spring.core.Credentials;\n"
          + "import com.google.cloud.spring.core.CredentialsSupplier;\n"
          + "import org.springframework.boot.context.properties.ConfigurationProperties;\n"
          + "import org.threeten.bp.Duration;\n"
          + "\n"
          + "@ConfigurationProperties(\"google.cloud.spring.autoconfig.echo\")\n"
          + "public class EchoSpringProperties implements CredentialsSupplier {\n"
          + "  private final Credentials credentials =\n"
          + "      new Credentials(\"https://www.googleapis.com/auth/cloud-platform\");\n"
          + "  private String quotaProjectId;\n"
          + "  private Integer executorThreadCount;\n"
          + "  private boolean useRest;\n"
          // retry settings per method, with default
          // (alternative not setting default here, but in autoconfig bean creation)
          + "  private Duration echoInitialRetryDelay = Duration.ofMillis(100L);\n"
          + "  private double echoRetryDelayMultiplier = 2.0;\n"
          + "  private Duration echoMaxRetryDelay = Duration.ofMillis(3000L);\n"
          + "  private Duration echoInitialRpcTimeout = Duration.ofMillis(10000L);\n"
          + "  private double echoRpcTimeoutMultiplier = 1.0;\n"
          + "  private Duration echoMaxRpcTimeout = Duration.ofMillis(10000L);\n"
          + "  private Duration echoTotalTimeout = Duration.ofMillis(10000L);\n"
          + "  private Duration expandInitialRetryDelay = Duration.ofMillis(100L);\n"
          + "  private double expandRetryDelayMultiplier = 2.0;\n"
          + "  private Duration expandMaxRetryDelay = Duration.ofMillis(3000L);\n"
          + "  private Duration expandInitialRpcTimeout = Duration.ofMillis(10000L);\n"
          + "  private double expandRpcTimeoutMultiplier = 1.0;\n"
          + "  private Duration expandMaxRpcTimeout = Duration.ofMillis(10000L);\n"
          + "  private Duration expandTotalTimeout = Duration.ofMillis(10000L);\n"
          + "  private Duration collectInitialRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private double collectRpcTimeoutMultiplier = 1.0;\n"
          + "  private Duration collectMaxRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private Duration collectTotalTimeout = Duration.ofMillis(5000L);\n"
          + "  private Duration chatInitialRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private double chatRpcTimeoutMultiplier = 1.0;\n"
          + "  private Duration chatMaxRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private Duration chatTotalTimeout = Duration.ofMillis(5000L);\n"
          + "  private Duration chatAgainInitialRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private double chatAgainRpcTimeoutMultiplier = 1.0;\n"
          + "  private Duration chatAgainMaxRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private Duration chatAgainTotalTimeout = Duration.ofMillis(5000L);\n"
          + "  private Duration pagedExpandInitialRetryDelay = Duration.ofMillis(100L);\n"
          + "  private double pagedExpandRetryDelayMultiplier = 2.0;\n"
          + "  private Duration pagedExpandMaxRetryDelay = Duration.ofMillis(3000L);\n"
          + "  private Duration pagedExpandInitialRpcTimeout = Duration.ofMillis(10000L);\n"
          + "  private double pagedExpandRpcTimeoutMultiplier = 1.0;\n"
          + "  private Duration pagedExpandMaxRpcTimeout = Duration.ofMillis(10000L);\n"
          + "  private Duration pagedExpandTotalTimeout = Duration.ofMillis(10000L);\n"
          + "  private Duration simplePagedExpandInitialRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private double simplePagedExpandRpcTimeoutMultiplier = 1.0;\n"
          + "  private Duration simplePagedExpandMaxRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private Duration simplePagedExpandTotalTimeout = Duration.ofMillis(5000L);\n"
          + "  private Duration waitInitialRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private double waitRpcTimeoutMultiplier = 1.0;\n"
          + "  private Duration waitMaxRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private Duration waitTotalTimeout = Duration.ofMillis(5000L);\n"
          + "  private Duration blockInitialRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private double blockRpcTimeoutMultiplier = 1.0;\n"
          + "  private Duration blockMaxRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private Duration blockTotalTimeout = Duration.ofMillis(5000L);\n"
          + "  private Duration collideNameInitialRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private double collideNameRpcTimeoutMultiplier = 1.0;\n"
          + "  private Duration collideNameMaxRpcTimeout = Duration.ofMillis(5000L);\n"
          + "  private Duration collideNameTotalTimeout = Duration.ofMillis(5000L);\n"
          + "\n"
          + "  @Override\n"
          + "  public Credentials getCredentials() {\n"
          + "    return this.credentials;\n"
          + "  }\n"
          + "\n"
          + "  public String getQuotaProjectId() {\n"
          + "    return this.quotaProjectId;\n"
          + "  }\n"
          + "\n"
          + "  public void setQuotaProjectId(String quotaProjectId) {\n"
          + "    this.quotaProjectId = quotaProjectId;\n"
          + "  }\n"
          + "\n"
          + "  public boolean getUseRest() {\n"
          + "    return this.useRest;\n"
          + "  }\n"
          + "\n"
          + "  public Integer getExecutorThreadCount() {\n"
          + "    return this.executorThreadCount;\n"
          + "  }\n"
          + "\n"
          + "  public void setExecutorThreadCount(Integer executorThreadCount) {\n"
          + "    this.executorThreadCount = executorThreadCount;\n"
          + "  }\n"
          + "\n"
          // getters and setters for retry settings.
          + "  public Duration getEchoInitialRetryDelay() {\n"
          + "    return this.echoInitialRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoInitialRetryDelay(Duration echoInitialRetryDelay) {\n"
          + "    this.echoInitialRetryDelay = echoInitialRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public double getEchoRetryDelayMultiplier() {\n"
          + "    return this.echoRetryDelayMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoRetryDelayMultiplier(double echoRetryDelayMultiplier) {\n"
          + "    this.echoRetryDelayMultiplier = echoRetryDelayMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getEchoMaxRetryDelay() {\n"
          + "    return this.echoMaxRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoMaxRetryDelay(Duration echoMaxRetryDelay) {\n"
          + "    this.echoMaxRetryDelay = echoMaxRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getEchoInitialRpcTimeout() {\n"
          + "    return this.echoInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoInitialRpcTimeout(Duration echoInitialRpcTimeout) {\n"
          + "    this.echoInitialRpcTimeout = echoInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getEchoRpcTimeoutMultiplier() {\n"
          + "    return this.echoRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoRpcTimeoutMultiplier(double echoRpcTimeoutMultiplier) {\n"
          + "    this.echoRpcTimeoutMultiplier = echoRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getEchoMaxRpcTimeout() {\n"
          + "    return this.echoMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoMaxRpcTimeout(Duration echoMaxRpcTimeout) {\n"
          + "    this.echoMaxRpcTimeout = echoMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getEchoTotalTimeout() {\n"
          + "    return this.echoTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setEchoTotalTimeout(Duration echoTotalTimeout) {\n"
          + "    this.echoTotalTimeout = echoTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getExpandInitialRetryDelay() {\n"
          + "    return this.expandInitialRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandInitialRetryDelay(Duration expandInitialRetryDelay) {\n"
          + "    this.expandInitialRetryDelay = expandInitialRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public double getExpandRetryDelayMultiplier() {\n"
          + "    return this.expandRetryDelayMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandRetryDelayMultiplier(double expandRetryDelayMultiplier) {\n"
          + "    this.expandRetryDelayMultiplier = expandRetryDelayMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getExpandMaxRetryDelay() {\n"
          + "    return this.expandMaxRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandMaxRetryDelay(Duration expandMaxRetryDelay) {\n"
          + "    this.expandMaxRetryDelay = expandMaxRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getExpandInitialRpcTimeout() {\n"
          + "    return this.expandInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandInitialRpcTimeout(Duration expandInitialRpcTimeout) {\n"
          + "    this.expandInitialRpcTimeout = expandInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getExpandRpcTimeoutMultiplier() {\n"
          + "    return this.expandRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandRpcTimeoutMultiplier(double expandRpcTimeoutMultiplier) {\n"
          + "    this.expandRpcTimeoutMultiplier = expandRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getExpandMaxRpcTimeout() {\n"
          + "    return this.expandMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandMaxRpcTimeout(Duration expandMaxRpcTimeout) {\n"
          + "    this.expandMaxRpcTimeout = expandMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getExpandTotalTimeout() {\n"
          + "    return this.expandTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setExpandTotalTimeout(Duration expandTotalTimeout) {\n"
          + "    this.expandTotalTimeout = expandTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getCollectInitialRpcTimeout() {\n"
          + "    return this.collectInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollectInitialRpcTimeout(Duration collectInitialRpcTimeout) {\n"
          + "    this.collectInitialRpcTimeout = collectInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getCollectRpcTimeoutMultiplier() {\n"
          + "    return this.collectRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollectRpcTimeoutMultiplier(double collectRpcTimeoutMultiplier) {\n"
          + "    this.collectRpcTimeoutMultiplier = collectRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getCollectMaxRpcTimeout() {\n"
          + "    return this.collectMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollectMaxRpcTimeout(Duration collectMaxRpcTimeout) {\n"
          + "    this.collectMaxRpcTimeout = collectMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getCollectTotalTimeout() {\n"
          + "    return this.collectTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollectTotalTimeout(Duration collectTotalTimeout) {\n"
          + "    this.collectTotalTimeout = collectTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getChatInitialRpcTimeout() {\n"
          + "    return this.chatInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatInitialRpcTimeout(Duration chatInitialRpcTimeout) {\n"
          + "    this.chatInitialRpcTimeout = chatInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getChatRpcTimeoutMultiplier() {\n"
          + "    return this.chatRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatRpcTimeoutMultiplier(double chatRpcTimeoutMultiplier) {\n"
          + "    this.chatRpcTimeoutMultiplier = chatRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getChatMaxRpcTimeout() {\n"
          + "    return this.chatMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatMaxRpcTimeout(Duration chatMaxRpcTimeout) {\n"
          + "    this.chatMaxRpcTimeout = chatMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getChatTotalTimeout() {\n"
          + "    return this.chatTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatTotalTimeout(Duration chatTotalTimeout) {\n"
          + "    this.chatTotalTimeout = chatTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getChatAgainInitialRpcTimeout() {\n"
          + "    return this.chatAgainInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatAgainInitialRpcTimeout(Duration chatAgainInitialRpcTimeout) {\n"
          + "    this.chatAgainInitialRpcTimeout = chatAgainInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getChatAgainRpcTimeoutMultiplier() {\n"
          + "    return this.chatAgainRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatAgainRpcTimeoutMultiplier(double chatAgainRpcTimeoutMultiplier) {\n"
          + "    this.chatAgainRpcTimeoutMultiplier = chatAgainRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getChatAgainMaxRpcTimeout() {\n"
          + "    return this.chatAgainMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatAgainMaxRpcTimeout(Duration chatAgainMaxRpcTimeout) {\n"
          + "    this.chatAgainMaxRpcTimeout = chatAgainMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getChatAgainTotalTimeout() {\n"
          + "    return this.chatAgainTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setChatAgainTotalTimeout(Duration chatAgainTotalTimeout) {\n"
          + "    this.chatAgainTotalTimeout = chatAgainTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getPagedExpandInitialRetryDelay() {\n"
          + "    return this.pagedExpandInitialRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandInitialRetryDelay(Duration pagedExpandInitialRetryDelay) {\n"
          + "    this.pagedExpandInitialRetryDelay = pagedExpandInitialRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public double getPagedExpandRetryDelayMultiplier() {\n"
          + "    return this.pagedExpandRetryDelayMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandRetryDelayMultiplier(double pagedExpandRetryDelayMultiplier) {\n"
          + "    this.pagedExpandRetryDelayMultiplier = pagedExpandRetryDelayMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getPagedExpandMaxRetryDelay() {\n"
          + "    return this.pagedExpandMaxRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandMaxRetryDelay(Duration pagedExpandMaxRetryDelay) {\n"
          + "    this.pagedExpandMaxRetryDelay = pagedExpandMaxRetryDelay;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getPagedExpandInitialRpcTimeout() {\n"
          + "    return this.pagedExpandInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandInitialRpcTimeout(Duration pagedExpandInitialRpcTimeout) {\n"
          + "    this.pagedExpandInitialRpcTimeout = pagedExpandInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getPagedExpandRpcTimeoutMultiplier() {\n"
          + "    return this.pagedExpandRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandRpcTimeoutMultiplier(double pagedExpandRpcTimeoutMultiplier) {\n"
          + "    this.pagedExpandRpcTimeoutMultiplier = pagedExpandRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getPagedExpandMaxRpcTimeout() {\n"
          + "    return this.pagedExpandMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandMaxRpcTimeout(Duration pagedExpandMaxRpcTimeout) {\n"
          + "    this.pagedExpandMaxRpcTimeout = pagedExpandMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getPagedExpandTotalTimeout() {\n"
          + "    return this.pagedExpandTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setPagedExpandTotalTimeout(Duration pagedExpandTotalTimeout) {\n"
          + "    this.pagedExpandTotalTimeout = pagedExpandTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getSimplePagedExpandInitialRpcTimeout() {\n"
          + "    return this.simplePagedExpandInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setSimplePagedExpandInitialRpcTimeout(Duration simplePagedExpandInitialRpcTimeout) {\n"
          + "    this.simplePagedExpandInitialRpcTimeout = simplePagedExpandInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getSimplePagedExpandRpcTimeoutMultiplier() {\n"
          + "    return this.simplePagedExpandRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setSimplePagedExpandRpcTimeoutMultiplier(\n"
          + "      double simplePagedExpandRpcTimeoutMultiplier) {\n"
          + "    this.simplePagedExpandRpcTimeoutMultiplier = simplePagedExpandRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getSimplePagedExpandMaxRpcTimeout() {\n"
          + "    return this.simplePagedExpandMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setSimplePagedExpandMaxRpcTimeout(Duration simplePagedExpandMaxRpcTimeout) {\n"
          + "    this.simplePagedExpandMaxRpcTimeout = simplePagedExpandMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getSimplePagedExpandTotalTimeout() {\n"
          + "    return this.simplePagedExpandTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setSimplePagedExpandTotalTimeout(Duration simplePagedExpandTotalTimeout) {\n"
          + "    this.simplePagedExpandTotalTimeout = simplePagedExpandTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getWaitInitialRpcTimeout() {\n"
          + "    return this.waitInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setWaitInitialRpcTimeout(Duration waitInitialRpcTimeout) {\n"
          + "    this.waitInitialRpcTimeout = waitInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getWaitRpcTimeoutMultiplier() {\n"
          + "    return this.waitRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setWaitRpcTimeoutMultiplier(double waitRpcTimeoutMultiplier) {\n"
          + "    this.waitRpcTimeoutMultiplier = waitRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getWaitMaxRpcTimeout() {\n"
          + "    return this.waitMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setWaitMaxRpcTimeout(Duration waitMaxRpcTimeout) {\n"
          + "    this.waitMaxRpcTimeout = waitMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getWaitTotalTimeout() {\n"
          + "    return this.waitTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setWaitTotalTimeout(Duration waitTotalTimeout) {\n"
          + "    this.waitTotalTimeout = waitTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getBlockInitialRpcTimeout() {\n"
          + "    return this.blockInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setBlockInitialRpcTimeout(Duration blockInitialRpcTimeout) {\n"
          + "    this.blockInitialRpcTimeout = blockInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getBlockRpcTimeoutMultiplier() {\n"
          + "    return this.blockRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setBlockRpcTimeoutMultiplier(double blockRpcTimeoutMultiplier) {\n"
          + "    this.blockRpcTimeoutMultiplier = blockRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getBlockMaxRpcTimeout() {\n"
          + "    return this.blockMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setBlockMaxRpcTimeout(Duration blockMaxRpcTimeout) {\n"
          + "    this.blockMaxRpcTimeout = blockMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getBlockTotalTimeout() {\n"
          + "    return this.blockTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setBlockTotalTimeout(Duration blockTotalTimeout) {\n"
          + "    this.blockTotalTimeout = blockTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getCollideNameInitialRpcTimeout() {\n"
          + "    return this.collideNameInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollideNameInitialRpcTimeout(Duration collideNameInitialRpcTimeout) {\n"
          + "    this.collideNameInitialRpcTimeout = collideNameInitialRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public double getCollideNameRpcTimeoutMultiplier() {\n"
          + "    return this.collideNameRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollideNameRpcTimeoutMultiplier(double collideNameRpcTimeoutMultiplier) {\n"
          + "    this.collideNameRpcTimeoutMultiplier = collideNameRpcTimeoutMultiplier;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getCollideNameMaxRpcTimeout() {\n"
          + "    return this.collideNameMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollideNameMaxRpcTimeout(Duration collideNameMaxRpcTimeout) {\n"
          + "    this.collideNameMaxRpcTimeout = collideNameMaxRpcTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public Duration getCollideNameTotalTimeout() {\n"
          + "    return this.collideNameTotalTimeout;\n"
          + "  }\n"
          + "\n"
          + "  public void setCollideNameTotalTimeout(Duration collideNameTotalTimeout) {\n"
          + "    this.collideNameTotalTimeout = collideNameTotalTimeout;\n"
          + "  }\n"
          + "}\n";
}
