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

package com.google.api.generator.gapic.composer;

import com.google.api.gax.retrying.RetrySettings;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.BlockStatement;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.ThisObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.common.ClassComposer;
import com.google.api.generator.gapic.composer.common.RetrySettingsComposer;
import com.google.api.generator.gapic.composer.utils.ClassNames;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicServiceConfig;
import com.google.api.generator.gapic.model.Service;
import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class SpringAutoConfigClassComposer implements ClassComposer {
  private static final String CLASS_NAME_PATTERN = "%sSpringAutoConfiguration";
  private static final String OPERATIONS_STUB_MEMBER_NAME = "operationsStub";
  private static final String BACKGROUND_RESOURCES_MEMBER_NAME = "backgroundResources";
  private static final String RETRY_PARAM_DEFINITIONS_VAR_NAME = "RETRY_PARAM_DEFINITIONS";

  private static final SpringAutoConfigClassComposer INSTANCE = new SpringAutoConfigClassComposer();

  private static final Map<String, TypeNode> STATIC_TYPES = createStaticTypes();

  private static final VariableExpr NESTED_RETRY_PARAM_DEFINITIONS_VAR_EXPR =
      createNestedRetryParamDefinitionsVarExpr();

  private SpringAutoConfigClassComposer() {}

  public static SpringAutoConfigClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(GapicContext context, Service service) {
    String packageName = service.pakkage() + ".spring";
    Map<String, TypeNode> types = createDynamicTypes(service, packageName);
    String className = getThisClassName(service.name());
    GapicClass.Kind kind = Kind.MAIN;

    GapicServiceConfig gapicServiceConfig = context.serviceConfig();

    // WIP: retry settings.
    // for (Method method : service.methods()) {
    //   // do retry settings.
    //
    //   Method.Stream streamKind = method.stream();
    //   if (streamKind.equals(Method.Stream.CLIENT) || streamKind.equals(Method.Stream.BIDI)) {
    //     continue;
    //   }
    //   if (!Objects.isNull(gapicServiceConfig)
    //       && gapicServiceConfig.hasBatchingSetting(service, method)) {
    //     Optional<GapicBatchingSettings> batchingSettingOpt =
    //         gapicServiceConfig.getBatchingSetting(service, method);
    //     Preconditions.checkState(
    //         batchingSettingOpt.isPresent(),
    //         String.format(
    //             "No batching setting found for service %s, method %s",
    //             service.name(), method.name()));
    //     String settingsGetterMethodName =
    //         String.format("%sSettings", JavaStyle.toLowerCamelCase(method.name()));
    //     // bodyStatement.add();
    //
    //     String retryParamName = gapicServiceConfig.getRetryParamsName(service, method);
    //   }
    // }

    types.get("CredentialsProvider").isSupertypeOrEquals(types.get("DefaultCredentialsProvider"));

    // header -- not used, add later
    List<CommentStatement> fileHeader =
        Arrays.asList(CommentStatement.withComment(BlockComment.withComment("Apache License")));

    ClassDefinition classDef =
        ClassDefinition.builder()
            // .setFileHeader(fileHeader)
            .setPackageString(packageName)
            .setName(className)
            .setScope(ScopeNode.PUBLIC)
            .setStatements(createMemberVariables(service, packageName, types, gapicServiceConfig))
            .setMethods(
                Arrays.asList(
                    createConstructor(service.name(), className, types),
                    createBeanMethod(service, types)))
            .setAnnotations(createClassAnnotations(service, types))
            .build();
    return GapicClass.create(kind, classDef);
  }

  private static VariableExpr createNestedRetryParamDefinitionsVarExpr() {
    TypeNode varType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ImmutableMap.class)
                .setGenerics(
                    Arrays.asList(TypeNode.STRING, STATIC_TYPES.get("RetrySettings")).stream()
                        .map(t -> t.reference())
                        .collect(Collectors.toList()))
                .build());

    return VariableExpr.withVariable(
        Variable.builder().setType(varType).setName(RETRY_PARAM_DEFINITIONS_VAR_NAME).build());
  }

  private static List<Statement> createMemberVariables(
      Service service,
      String packageName,
      Map<String, TypeNode> types,
      GapicServiceConfig serviceConfig) {

    String serviceName = service.name();
    // TODO create CredentialsProvider bean instead.
    // // private final CredentialsProvider credentialsProvider;
    // Variable credentialsProviderVar =
    //     Variable.builder()
    //         .setName("credentialsProvider")
    //         .setType(types.get("CredentialsProvider"))
    //         .build();
    // VariableExpr credentialsProviderVarExpr =
    //     VariableExpr.builder()
    //         .setVariable(credentialsProviderVar)
    //         .setScope(ScopeNode.PRIVATE)
    //         .setIsFinal(true)
    //         .setIsDecl(true)
    //         .build();
    // ExprStatement credentialsProviderVarStatement =
    //     ExprStatement.withExpr(credentialsProviderVarExpr);

    // private final LanguageProperties clientProperties;
    Variable clientPropertiesVar =
        Variable.builder()
            .setName("clientProperties")
            .setType(types.get(serviceName + "Properties"))
            .build();
    VariableExpr clientPropertiesVarExpr =
        VariableExpr.builder()
            .setVariable(clientPropertiesVar)
            .setScope(ScopeNode.PRIVATE)
            .setIsFinal(true)
            .setIsDecl(true)
            .build();
    ExprStatement clientPropertiesStatement = ExprStatement.withExpr(clientPropertiesVarExpr);

    // // private final GcpProjectIdProvider projectIdProvider;
    // Variable projectIdProviderVar =
    //     Variable.builder()
    //         .setName("projectIdProvider")
    //         .setType(types.get("GcpProjectIdProvider"))
    //         .build();
    // VariableExpr projectIdProviderVarExpr =
    //     VariableExpr.builder()
    //         .setVariable(projectIdProviderVar)
    //         .setScope(ScopeNode.PRIVATE)
    //         .setIsFinal(true)
    //         .setIsDecl(true)
    //         .build();
    // ExprStatement projectIdProviderStatement = ExprStatement.withExpr(projectIdProviderVarExpr);

    // Declare the RETRY_PARAM_DEFINITIONS map.
    ExprStatement retryPramStatement =
        ExprStatement.withExpr(
            NESTED_RETRY_PARAM_DEFINITIONS_VAR_EXPR
                .toBuilder()
                .setIsDecl(true)
                .setScope(ScopeNode.PRIVATE)
                .setIsStatic(true)
                .setIsFinal(true)
                .build());

    BlockStatement retryParamDefinitionsBlock =
        RetrySettingsComposer.createRetryParamDefinitionsBlock(
            service, serviceConfig, NESTED_RETRY_PARAM_DEFINITIONS_VAR_EXPR);

    return Arrays.asList(clientPropertiesStatement, retryPramStatement, retryParamDefinitionsBlock);
  }

  private static MethodDefinition createConstructor(
      String serviceName, String className, Map<String, TypeNode> types) {
    /// constructor
    // VariableExpr credentialsProviderBuilderVarExpr =
    //     VariableExpr.withVariable(
    //         Variable.builder()
    //             .setName("coreCredentialsProvider")
    //             .setType(types.get("CredentialsProvider"))
    //             .build());
    //
    // VariableExpr coreProjectIdProviderVarExpr =
    //     VariableExpr.withVariable(
    //         Variable.builder()
    //             .setName("coreProjectIdProvider")
    //             .setType(types.get("GcpProjectIdProvider"))
    //             .build());

    VariableExpr propertiesVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("clientProperties")
                .setType(types.get(serviceName + "Properties"))
                .build());
    // Variable projectIdProviderVar =
    //     Variable.builder()
    //         .setName("projectIdProvider")
    //         .setType(types.get("GcpProjectIdProvider"))
    //         .build();
    Variable clientPropertiesVar =
        Variable.builder()
            .setName("clientProperties")
            .setType(types.get(serviceName + "Properties"))
            .build();

    Expr thisExpr = ValueExpr.withValue(ThisObjectValue.withType(types.get(className)));

    // this.clientProperties = clientProperties;
    AssignmentExpr thisPropertiesAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.withVariable(clientPropertiesVar)
                    .toBuilder()
                    .setExprReferenceExpr(thisExpr)
                    .build())
            .setValueExpr(propertiesVarExpr)
            .build();
    ExprStatement thisPropertiesAssignmentStatement =
        ExprStatement.withExpr(thisPropertiesAssignmentExpr);

    // /**
    //  * if (properties.getCredentials().hasKey()) { this.credentialsProvider = new
    //  * DefaultCredentialsProvider(properties); } else { this.credentialsProvider =
    //  * coreCredentialsProvider; }
    //  */
    //
    // // expr: properties.getCredentials().hasKey()
    //
    // MethodInvocationExpr getCredentialsExpr =
    //     MethodInvocationExpr.builder()
    //         .setMethodName("getCredentials")
    //         .setExprReferenceExpr(propertiesVarExpr)
    //         .setReturnType(types.get("Credentials"))
    //         .build();
    // MethodInvocationExpr hasKeyExpr =
    //     MethodInvocationExpr.builder()
    //         .setMethodName("hasKey")
    //         .setExprReferenceExpr(getCredentialsExpr)
    //         .setReturnType(TypeNode.BOOLEAN)
    //         .build();
    //
    // // if body: this.credentialsProvider = new DefaultCredentialsProvider(properties)
    // CastExpr castExpr =
    //     CastExpr.builder()
    //         .setExpr(
    //             NewObjectExpr.builder()
    //                 .setType(types.get("DefaultCredentialsProvider"))
    //                 .setArguments(propertiesVarExpr)
    //                 .build())
    //         .setType(types.get("CredentialsProvider"))
    //         .build();
    // Variable credentialsProviderVar =
    //     Variable.builder()
    //         .setName("credentialsProvider")
    //         .setType(types.get("CredentialsProvider"))
    //         .build();
    // AssignmentExpr credentialsProviderssignExpr =
    //     AssignmentExpr.builder()
    //         .setVariableExpr(
    //             VariableExpr.withVariable(credentialsProviderVar)
    //                 .toBuilder()
    //                 .setExprReferenceExpr(thisExpr)
    //                 .build())
    //         .setValueExpr(castExpr)
    //         .build();
    //
    // // else body: this.credentialsProvider = coreCredentialsProvider;
    // List<Expr> coreCredentialsProviderAssignmentExprs = new ArrayList<>();
    // coreCredentialsProviderAssignmentExprs.add(
    //     AssignmentExpr.builder()
    //         .setVariableExpr(
    //             VariableExpr.withVariable(credentialsProviderVar)
    //                 .toBuilder()
    //                 .setExprReferenceExpr(thisExpr)
    //                 .build())
    //         .setValueExpr(
    //             CastExpr.builder()
    //                 .setExpr(coreProjectIdProviderVarExpr)
    //                 .setType(types.get("CredentialsProvider"))
    //                 .build())
    //         .build());
    //
    // IfStatement credentialIfStatement =
    //     IfStatement.builder()
    //         .setConditionExpr(hasKeyExpr)
    //         .setBody(Arrays.asList(ExprStatement.withExpr(credentialsProviderssignExpr)))
    //         .setElseBody(
    //             coreCredentialsProviderAssignmentExprs.stream()
    //                 .map(e -> ExprStatement.withExpr(e))
    //                 .collect(Collectors.toList()))
    //         .build();
    //
    // /**
    //  * if (clientProperties.getProjectId() != null) { this.projectIdProvider =
    //  * clientProperties::getProjectId; } else { this.projectIdProvider = coreProjectIdProvider; }
    //  */
    // // else body: this.projectIdProvider = coreProjectIdProvider;
    // List<Expr> ctorAssignmentExprs = new ArrayList<>();
    // ctorAssignmentExprs.add(
    //     AssignmentExpr.builder()
    //         .setVariableExpr(
    //             VariableExpr.withVariable(projectIdProviderVar)
    //                 .toBuilder()
    //                 .setExprReferenceExpr(thisExpr)
    //                 .build())
    //         .setValueExpr(coreProjectIdProviderVarExpr)
    //         .build());
    //
    // // expr: clientProperties.getProjectId() != null
    // MethodInvocationExpr getProjectIdExpr =
    //     MethodInvocationExpr.builder()
    //         .setMethodName("getProjectId")
    //         .setExprReferenceExpr(
    //             VariableExpr.withVariable(clientPropertiesVar).toBuilder().build())
    //         // .setStaticReferenceType(clientType)
    //         .setReturnType(types.get("CredentialsProvider")) // fake it
    //         .build();
    // RelationalOperationExpr notEqualSentence =
    //     RelationalOperationExpr.notEqualToWithExprs(getProjectIdExpr,
    // ValueExpr.createNullExpr());
    //
    // // () -> clientProperties.getProjectId();
    // LambdaExpr lambdaExpr = LambdaExpr.builder().setReturnExpr(getProjectIdExpr).build();
    //
    // // this.projectIdProvider = () -> clientProperties.getProjectId();
    // AssignmentExpr projectIdProviderAssignExpr =
    //     AssignmentExpr.builder()
    //         .setVariableExpr(
    //             VariableExpr.withVariable(projectIdProviderVar)
    //                 .toBuilder()
    //                 .setExprReferenceExpr(thisExpr)
    //                 .build())
    //         .setValueExpr(
    //             CastExpr.builder()
    //                 .setExpr(lambdaExpr)
    //                 .setType(types.get("GcpProjectIdProvider"))
    //                 .build())
    //         .build();
    //
    // IfStatement projectIdProviderIfStatement =
    //     IfStatement.builder()
    //         .setConditionExpr(notEqualSentence)
    //         .setBody(Arrays.asList(ExprStatement.withExpr(projectIdProviderAssignExpr)))
    //         .setElseBody(
    //             ctorAssignmentExprs.stream()
    //                 .map(e -> ExprStatement.withExpr(e))
    //                 .collect(Collectors.toList()))
    //         .build();

    return MethodDefinition.constructorBuilder()
        .setScope(ScopeNode.PROTECTED)
        .setReturnType(types.get(className))
        .setArguments(
            Arrays.asList(
                // credentialsProviderBuilderVarExpr.toBuilder().setIsDecl(true).build(),
                // coreProjectIdProviderVarExpr.toBuilder().setIsDecl(true).build(),
                propertiesVarExpr.toBuilder().setIsDecl(true).build()))
        .setBody(Arrays.asList(thisPropertiesAssignmentStatement))
        // credentialIfStatement,
        // projectIdProviderIfStatement))
        // .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(IOException.class)))
        .build();
  }

  private static List<AnnotationNode> createClassAnnotations(
      Service service, Map<String, TypeNode> types) {
    // @Generated("by gapic-generator-java")
    // @Configuration(proxyBeanMethods = false)
    // @ConditionalOnClass(LanguageServiceClient.class)
    // @ConditionalOnProperty(value = "spring.cloud.gcp.language.enabled", matchIfMissing = true)
    // @EnableConfigurationProperties(LanguageProperties.class)

    // TODO: AnnotationNode description only accepts String for now. need to extend to params
    // and classes.
    AnnotationNode conditionalOnPropertyNode =
        AnnotationNode.builder()
            .setType(types.get("ConditionalOnProperty"))
            .setDescription("value = \"spring.cloud.gcp.language.enabled\", matchIfMissing = false")
            .build();
    AnnotationNode conditionalOnClassNode =
        AnnotationNode.builder()
            .setType(types.get("ConditionalOnClass"))
            .setDescription(
                "value = "
                    + ClassNames.getServiceClientClassName(service)
                    + ".class") // TODO: change after annotation feature merged. need to produce
            // XXX.class
            .build();
    AnnotationNode configurationNode =
        AnnotationNode.builder()
            .setType(types.get("Configuration"))
            .setDescription("proxyBeanMethods = false") // TODO: change to parameters
            .build();
    AnnotationNode enableConfigurationPropertiesNode =
        AnnotationNode.builder()
            .setType(types.get("EnableConfigurationProperties"))
            .setDescription(
                types.get(service.name() + "Properties").reference().name()
                    + ".Class") // TODO: change to parameters
            .build();
    return Arrays.asList(
        AnnotationNode.builder()
            .setType(STATIC_TYPES.get("Generated"))
            .setDescription("by gapic-generator-java")
            .build(),
        configurationNode,
        conditionalOnClassNode,
        conditionalOnPropertyNode,
        enableConfigurationPropertiesNode);
  }

  private static MethodDefinition createBeanMethod(Service service, Map<String, TypeNode> types) {
    // build expressions
    MethodInvocationExpr lhsExpr =
        MethodInvocationExpr.builder()
            // TODO: might need to use newBuilder().build() if options needed.
            // read more in client composer:
            // src/main/java/com/google/api/generator/gapic/composer/common/AbstractServiceClientClassComposer.java#L277-L292
            .setMethodName("create")
            .setStaticReferenceType(types.get("ServiceClient"))
            .setReturnType(types.get("ServiceClient"))
            .build();

    String methodName =
        CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, service.name()) + "Client";
    return MethodDefinition.builder()
        .setName(methodName)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(types.get("ServiceClient"))
        .setAnnotations(
            Arrays.asList(
                AnnotationNode.withType(types.get("Bean")),
                AnnotationNode.withType(types.get("ConditionalOnMissingBean"))))
        .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(IOException.class)))
        .setReturnExpr(lhsExpr)
        .build();
  }

  private static Map<String, TypeNode> createStaticTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            // BackgroundResource.class,
            // BackgroundResourceAggregation.class,
            // BidiStreamingCallable.class,
            // ClientContext.class,
            // ClientStreamingCallable.class,
            Generated.class,
            // GrpcCallSettings.class,
            // GrpcOperationsStub.class,
            // GrpcStubCallableFactory.class,
            // InterruptedException.class,
            // IOException.class,
            // MethodDescriptor.class,
            // Operation.class,
            // OperationCallable.class,
            // ProtoUtils.class,
            // ServerStreamingCallable.class,
            // TimeUnit.class,
            // UnaryCallable.class,
            RetrySettings.class);
    return concreteClazzes.stream()
        .collect(
            Collectors.toMap(
                c -> c.getSimpleName(),
                c -> TypeNode.withReference(ConcreteReference.withClazz(c))));
  }

  private static Map<String, TypeNode> createDynamicTypes(Service service, String packageName) {
    Map<String, TypeNode> typeMap =
        Arrays.asList(CLASS_NAME_PATTERN).stream()
            .collect(
                Collectors.toMap(
                    p -> String.format(p, service.name()),
                    p ->
                        TypeNode.withReference(
                            VaporReference.builder()
                                .setName(String.format(p, service.name()))
                                .setPakkage(packageName)
                                .build())));
    TypeNode credentialsProvider =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("CredentialsProvider")
                .setPakkage("com.google.api.gax.core")
                .build());
    TypeNode clientProperties =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(service.name() + "SpringProperties")
                .setPakkage(packageName)
                .build());

    TypeNode gcpProjectIdProvider =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("GcpProjectIdProvider")
                .setPakkage("com.google.cloud.spring.core")
                .build());

    // import com.google.cloud.spring.core.Credentials;
    TypeNode credentials =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("Credentials")
                .setPakkage("com.google.cloud.spring.core")
                .build());

    // import com.google.cloud.spring.core.DefaultCredentialsProvider;
    TypeNode defaultCredentialsProvider =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("DefaultCredentialsProvider")
                .setPakkage("com.google.cloud.spring.core")
                // TODO: this supre class info is not used, workaround by casting for now. look into
                // VaporReference.isSupertypeOrEquals()
                .setSupertypeReference(
                    VaporReference.builder()
                        .setName("CredentialsProvider")
                        .setPakkage("com.google.api.gax.core")
                        .build())
                .build());

    TypeNode serviceClient =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(ClassNames.getServiceClientClassName(service))
                .setPakkage(service.pakkage())
                .build());

    TypeNode bean =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("Bean")
                .setPakkage("org.springframework.context.annotation")
                .build());
    TypeNode configuration =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("Configuration")
                .setPakkage("org.springframework.context.annotation")
                .build());
    TypeNode enableConfigurationProperties =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("EnableConfigurationProperties")
                .setPakkage("org.springframework.boot.context.properties")
                .build());
    TypeNode conditionalOnMissingBean =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("ConditionalOnMissingBean")
                .setPakkage("org.springframework.boot.autoconfigure.condition")
                .build());

    TypeNode conditionalOnProperty =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("ConditionalOnProperty")
                .setPakkage("org.springframework.boot.autoconfigure.condition")
                .build());
    TypeNode conditionalOnClass =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("ConditionalOnClass")
                .setPakkage("org.springframework.boot.autoconfigure.condition")
                .build());

    typeMap.put("CredentialsProvider", credentialsProvider);
    typeMap.put(service.name() + "Properties", clientProperties);
    typeMap.put("GcpProjectIdProvider", gcpProjectIdProvider);
    typeMap.put("Credentials", credentials);
    typeMap.put("DefaultCredentialsProvider", defaultCredentialsProvider);
    typeMap.put("ServiceClient", serviceClient);
    typeMap.put("Bean", bean);
    typeMap.put("Configuration", configuration);
    typeMap.put("EnableConfigurationProperties", enableConfigurationProperties);
    typeMap.put("ConditionalOnMissingBean", conditionalOnMissingBean);
    typeMap.put("ConditionalOnProperty", conditionalOnProperty);
    typeMap.put("ConditionalOnClass", conditionalOnClass);

    return typeMap;
  }

  private static String getThisClassName(String serviceName) {
    return String.format(CLASS_NAME_PATTERN, serviceName);
  }
}
