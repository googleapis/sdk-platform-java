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

package com.google.api.generator.spring.composer;

import com.google.api.gax.core.ExecutorProvider;
import com.google.api.gax.httpjson.InstantiatingHttpJsonChannelProvider;
import com.google.api.gax.retrying.RetrySettings;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.BlockStatement;
import com.google.api.generator.engine.ast.CastExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.RelationalOperationExpr;
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
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.util.ArrayList;
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
    String libName = Utils.getLibName(context);
    GapicClass.Kind kind = Kind.MAIN;

    GapicServiceConfig gapicServiceConfig = context.serviceConfig();

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
                    createCredentialsProviderBeanMethod(service, className, types),
                    createTransportChannelProviderBeanMethod(service, types),
                    createClientBeanMethod(service, className, types, gapicServiceConfig)))
            .setAnnotations(createClassAnnotations(service, types, libName))
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
      Service service, Map<String, TypeNode> types, String libName) {
    // @Generated("by gapic-generator-java")
    // @AutoConfiguration
    // @ConditionalOnClass(LanguageServiceClient.class)
    // @ConditionalOnProperty(value = "spring.cloud.gcp.language.enabled", matchIfMissing = true)
    // @EnableConfigurationProperties(LanguageProperties.class)

    // TODO: AnnotationNode description only accepts String for now. need to extend to params
    // and classes.
    AnnotationNode conditionalOnPropertyNode =
        AnnotationNode.builder()
            .setType(types.get("ConditionalOnProperty"))
            .setDescription(
                "value = \""
                    + Utils.springPropertyPrefix(libName, service.name())
                    + ".enabled\", matchIfMissing = false")
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
        AnnotationNode.builder().setType(types.get("AutoConfiguration")).build();
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

  private static MethodDefinition createCredentialsProviderBeanMethod(
      Service service, String className, Map<String, TypeNode> types) {
    // @Bean
    // @ConditionalOnMissingBean
    // public CredentialsProvider googleCredentials() throws IOException {
    //   return new DefaultCredentialsProvider(this.clientProperties);
    // }

    Variable clientPropertiesVar =
        Variable.builder()
            .setName("clientProperties")
            .setType(types.get(service.name() + "Properties"))
            .build();

    Expr thisExpr = ValueExpr.withValue(ThisObjectValue.withType(types.get(className)));
    VariableExpr thisClientProperties =
        VariableExpr.withVariable(clientPropertiesVar)
            .toBuilder()
            .setExprReferenceExpr(thisExpr)
            .build();
    CastExpr castExpr =
        CastExpr.builder()
            .setExpr(
                NewObjectExpr.builder()
                    .setType(types.get("DefaultCredentialsProvider"))
                    .setArguments(thisClientProperties)
                    .build())
            .setType(types.get("CredentialsProvider"))
            .build();

    String methodName = "googleCredentials";
    return MethodDefinition.builder()
        .setName(methodName)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(types.get("CredentialsProvider"))
        .setAnnotations(
            Arrays.asList(
                AnnotationNode.withType(types.get("Bean")),
                AnnotationNode.withType(types.get("ConditionalOnMissingBean"))))
        .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(IOException.class)))
        .setReturnExpr(castExpr)
        .build();
  }

  private static MethodDefinition createTransportChannelProviderBeanMethod(
      Service service, Map<String, TypeNode> types) {

    //   @Bean
    //   @ConditionalOnMissingBean
    //   public TransportChannelProvider defaultLanguageTransportChannelProvider() {
    //     return LanguageServiceSettings.defaultTransportChannelProvider();
    //   }
    // build expressions
    MethodInvocationExpr returnExpr =
        MethodInvocationExpr.builder()
            .setMethodName("defaultTransportChannelProvider")
            .setStaticReferenceType(types.get("ServiceSettings"))
            .setReturnType(STATIC_TYPES.get("TransportChannelProvider"))
            .build();

    String methodName = "default" + service.name() + "TransportChannelProvider";
    return MethodDefinition.builder()
        .setName(methodName)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(STATIC_TYPES.get("TransportChannelProvider"))
        .setAnnotations(
            Arrays.asList(
                AnnotationNode.withType(types.get("Bean")),
                AnnotationNode.withType(types.get("ConditionalOnMissingBean"))))
        .setReturnExpr(returnExpr)
        .build();
  }

  private static IfStatement createIfStatement(
      Expr conditionExpr, List<Statement> ifBody, List<Statement> elseBody) {
    IfStatement.Builder credentialIfStatement =
        IfStatement.builder().setConditionExpr(conditionExpr).setBody(ifBody);
    if (elseBody != null) {
      credentialIfStatement.setElseBody(elseBody);
    }
    return credentialIfStatement.build();
  }

  private static Statement createRetrySettingBuilderForMethod(
      String methodName, Variable settingBuilderVariable) {
    // RetrySettings.Builder annotateTextRetrySettingsBuilder =
    // clientSettingsBuilder.annotateTextSettings()
    //     .getRetrySettings()
    //     .toBuilder();
    Variable retrySettingBuilderVariable =
        Variable.builder()
            .setName(String.format("%sRetrySettingBuilder", methodName))
            .setType(STATIC_TYPES.get("Builder"))
            .build();
    VariableExpr retrySettingsVarExpr =
        VariableExpr.withVariable(retrySettingBuilderVariable).toBuilder().setIsDecl(true).build();
    MethodInvocationExpr clientSettingBuilderChain =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(VariableExpr.withVariable(settingBuilderVariable))
            .setMethodName(String.format("%sSettings", methodName))
            .build();
    clientSettingBuilderChain =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientSettingBuilderChain)
            .setMethodName("getRetrySettings")
            .build();
    clientSettingBuilderChain =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientSettingBuilderChain)
            .setMethodName("toBuilder")
            .setReturnType(STATIC_TYPES.get("Builder"))
            .build();
    AssignmentExpr retrySettingCreateExpr =
        AssignmentExpr.builder()
            .setVariableExpr(retrySettingsVarExpr)
            .setValueExpr(clientSettingBuilderChain)
            .build();

    return ExprStatement.withExpr(retrySettingCreateExpr);
  }

  private static Statement setRetrySettingsForMethod(
      String methodName, Variable settingBuilderVariable) {
    //     clientSettingsBuilder.annotateTextSettings()
    //         .setRetrySettings(annotateTextRetrySettingsBuilder.build());

    MethodInvocationExpr clientSettingBuilderChain =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(VariableExpr.withVariable(settingBuilderVariable))
            .setMethodName(String.format("%sSettings", methodName))
            .build();

    // annotateTextRetrySettingsBuilder.build()
    Variable retrySettingBuilderVariable =
        Variable.builder()
            .setName(String.format("%sRetrySettingBuilder", methodName)) // extract method name
            .setType(STATIC_TYPES.get("Builder"))
            .build();
    MethodInvocationExpr retrySettingsBuilderChain =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(VariableExpr.withVariable(retrySettingBuilderVariable))
            .setMethodName("build")
            .build();

    clientSettingBuilderChain =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientSettingBuilderChain)
            .setMethodName("setRetrySettings")
            .setArguments(retrySettingsBuilderChain)
            .build();

    return ExprStatement.withExpr(clientSettingBuilderChain);
  }

  private static MethodDefinition createClientBeanMethod(
      Service service,
      String className,
      Map<String, TypeNode> types,
      GapicServiceConfig gapicServiceConfig) {

    // argument variables:
    VariableExpr credentialsProviderVariableExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("credentialsProvider")
                .setType(types.get("CredentialsProvider"))
                .build());
    VariableExpr transportChannelProviderVariableExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("defaultTransportChannelProvider")
                .setType(STATIC_TYPES.get("TransportChannelProvider"))
                .build());

    List<Statement> bodyStatements = new ArrayList<>();
    //   LanguageServiceSettings.Builder clientSettingsBuilder =
    //       LanguageServiceSettings.newBuilder()
    //           .setCredentialsProvider(credentialsProvider)
    //           .setTransportChannelProvider(defaultTransportChannelProvider)
    //           .setHeaderProvider(
    //               new UserAgentHeaderProvider(this.getClass()));
    Variable settingBuilderVariable =
        Variable.builder()
            .setName("clientSettingsBuilder")
            .setType(types.get("ServiceSettingsBuilder"))
            .build();
    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(settingBuilderVariable).toBuilder().setIsDecl(true).build();
    Expr settingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(types.get("ServiceSettings"))
            .setMethodName("newBuilder")
            .build();

    settingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(settingsBuilderExpr)
            .setMethodName("setCredentialsProvider")
            .setArguments(credentialsProviderVariableExpr)
            .build();
    //           .setTransportChannelProvider(defaultTransportChannelProvider)
    settingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(settingsBuilderExpr)
            .setMethodName("setTransportChannelProvider")
            .setArguments(transportChannelProviderVariableExpr)
            .build();
    //           .setHeaderProvider(
    //               new UserAgentHeaderProvider(this.getClass()));
    settingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(settingsBuilderExpr)
            .setMethodName("setHeaderProvider")
            // .setArguments() //TODO add augument here to create new obj. Caveat: decide where to
            // UserAgentHeaderProvider class first.
            .setReturnType(settingBuilderVariable.type())
            .build();
    AssignmentExpr settingCreateExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr)
            .setValueExpr(settingsBuilderExpr)
            .build();

    bodyStatements.add(ExprStatement.withExpr(settingCreateExpr));

    //   if (this.clientProperties.getQuotaProjectId() != null) {
    //     clientSettingsBuilder.setQuotaProjectId(clientProperties.getQuotaProjectId());
    //     LOGGER.info("Quota project id set to: " + clientProperties.getQuotaProjectId()
    //         + ", this overrides project id from credentials.");
    //   }
    Expr thisExpr = ValueExpr.withValue(ThisObjectValue.withType(types.get(className)));
    Variable clientPropertiesVar =
        Variable.builder()
            .setName("clientProperties")
            .setType(types.get(service.name() + "Properties"))
            .build();
    VariableExpr thisClientPropertiesVarExpr =
        VariableExpr.withVariable(clientPropertiesVar)
            .toBuilder()
            .setExprReferenceExpr(thisExpr)
            .build();

    MethodInvocationExpr getQuotaProjectId =
        MethodInvocationExpr.builder()
            .setMethodName("getQuotaProjectId")
            .setReturnType(TypeNode.STRING)
            .setExprReferenceExpr(thisClientPropertiesVarExpr)
            .build();
    RelationalOperationExpr projectIdIsNull =
        RelationalOperationExpr.notEqualToWithExprs(getQuotaProjectId, ValueExpr.createNullExpr());

    //     clientSettingsBuilder.setQuotaProjectId(clientProperties.getQuotaProjectId());
    MethodInvocationExpr setQuotaProjectId =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(VariableExpr.withVariable(settingBuilderVariable))
            .setMethodName("setQuotaProjectId")
            .setArguments(getQuotaProjectId)
            .build();

    IfStatement setQuotaProjectIdStatement =
        createIfStatement(
            projectIdIsNull,
            Arrays.asList(ExprStatement.withExpr(setQuotaProjectId)), // TODO add logger info
            null);

    bodyStatements.add(setQuotaProjectIdStatement);

    //   if (this.clientProperties.getExecutorThreadCount() != null) {
    //     ExecutorProvider executorProvider =
    // LanguageServiceSettings.defaultExecutorProviderBuilder()
    //         .setExecutorThreadCount(clientProperties.getExecutorThreadCount()).build();
    //     clientSettingsBuilder
    //         .setBackgroundExecutorProvider(executorProvider);
    //   }

    MethodInvocationExpr getExecutorThreadCount =
        MethodInvocationExpr.builder()
            .setMethodName("getExecutorThreadCount")
            .setReturnType(TypeNode.INT_OBJECT)
            .setExprReferenceExpr(thisClientPropertiesVarExpr)
            .build();
    RelationalOperationExpr executorThreadCountIsNull =
        RelationalOperationExpr.notEqualToWithExprs(
            getExecutorThreadCount, ValueExpr.createNullExpr());

    VariableExpr executorProviderVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setType(STATIC_TYPES.get("ExecutorProvider"))
                .setName("executorProvider")
                .build());

    MethodInvocationExpr chainedMethodToSetExecutorProvider =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(types.get("ServiceSettings"))
            .setMethodName("defaultExecutorProviderBuilder")
            .build();
    chainedMethodToSetExecutorProvider =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(chainedMethodToSetExecutorProvider)
            .setMethodName("setExecutorThreadCount")
            .setArguments(getExecutorThreadCount)
            .build();
    chainedMethodToSetExecutorProvider =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(chainedMethodToSetExecutorProvider)
            .setMethodName("build")
            .setReturnType(STATIC_TYPES.get("ExecutorProvider"))
            .build();
    AssignmentExpr executorProviderAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(executorProviderVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(chainedMethodToSetExecutorProvider)
            .build();
    MethodInvocationExpr setBackgroundExecutorProvider =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(VariableExpr.withVariable(settingBuilderVariable))
            .setMethodName("setBackgroundExecutorProvider")
            .setArguments(executorProviderVarExpr)
            .build();

    IfStatement setBackgroundExecutorProviderStatement =
        createIfStatement(
            executorThreadCountIsNull,
            Arrays.asList(
                ExprStatement.withExpr(executorProviderAssignExpr),
                ExprStatement.withExpr(setBackgroundExecutorProvider)), // TODO add logger info
            null);

    bodyStatements.add(setBackgroundExecutorProviderStatement);

    //   if (clientProperties.getUseRest()) {
    //     clientSettingsBuilder.setTransportChannelProvider(
    //         LanguageServiceSettings.defaultHttpJsonTransportProviderBuilder().build());
    //   }

    MethodInvocationExpr getUseRest =
        MethodInvocationExpr.builder()
            .setMethodName("getUseRest")
            .setReturnType(TypeNode.BOOLEAN)
            .setExprReferenceExpr(thisClientPropertiesVarExpr)
            .build();

    // LanguageServiceSettings.defaultHttpJsonTransportProviderBuilder().build()
    Expr defaultTransportProviderBuider =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(types.get("ServiceSettings"))
            .setMethodName("defaultHttpJsonTransportProviderBuilder")
            .build();
    defaultTransportProviderBuider =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(defaultTransportProviderBuider)
            .setMethodName("build")
            .setReturnType(STATIC_TYPES.get("InstantiatingHttpJsonChannelProvider"))
            .build();

    MethodInvocationExpr setTransportProvider =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(VariableExpr.withVariable(settingBuilderVariable))
            .setMethodName("setTransportChannelProvider")
            .setArguments(defaultTransportProviderBuider)
            .build();
    IfStatement setTransportChannelProviderStatement =
        createIfStatement(
            getUseRest, Arrays.asList(ExprStatement.withExpr(setTransportProvider)), null);

    bodyStatements.add(setTransportChannelProviderStatement);

    // retry settings for each method
    TypeNode thisClassType = types.get(service.name() + "AutoConfig");
    List retrySettings =
        Utils.processRetrySettings(
            service,
            gapicServiceConfig,
            thisClassType,
            (String methodName) -> {
              List<Statement> statements = new ArrayList<>();
              statements.add(
                  createRetrySettingBuilderForMethod(methodName, settingBuilderVariable));
              return statements;
            },
            (List<String> methodAndPropertyName, Expr defaultVal) -> {
              // return new ArrayList<>();
              List<Statement> statements = new ArrayList<>();
              String methodName = methodAndPropertyName.get(0);
              String settingName = methodAndPropertyName.get(1); // safe guard??
              String propertyName =
                  CaseFormat.LOWER_CAMEL.to(
                      CaseFormat.UPPER_CAMEL, Joiner.on("").join(methodAndPropertyName));

              TypeNode propertyType = defaultVal.type();
              if (propertyType.equals(TypeNode.DOUBLE)) {
                propertyType = TypeNode.DOUBLE_OBJECT;
              }
              MethodInvocationExpr currentRetrySettingProperty =
                  MethodInvocationExpr.builder()
                      .setMethodName(String.format("get%s", propertyName))
                      .setReturnType(propertyType)
                      .setExprReferenceExpr(thisClientPropertiesVarExpr)
                      .build();
              RelationalOperationExpr currentRetrySettingPropertyIsNull =
                  RelationalOperationExpr.notEqualToWithExprs(
                      currentRetrySettingProperty, ValueExpr.createNullExpr());

              // annotateTextRetrySettingsBuilder.setInitialRetryDelay(this.clientProperties.getAnnotateTextInitialRetryDelay());
              Variable retrySettingBuilderVariable =
                  Variable.builder()
                      .setName(
                          String.format("%sRetrySettingBuilder", methodName)) // extract method name
                      .setType(STATIC_TYPES.get("Builder"))
                      .build();
              MethodInvocationExpr retrySettingsBuilderChain =
                  MethodInvocationExpr.builder()
                      .setExprReferenceExpr(VariableExpr.withVariable(retrySettingBuilderVariable))
                      .setMethodName(String.format("set%s", settingName))
                      .setArguments(currentRetrySettingProperty)
                      .build();

              IfStatement currentRetrySettingPropertyIfStatement =
                  createIfStatement(
                      currentRetrySettingPropertyIsNull,
                      Arrays.asList(ExprStatement.withExpr(retrySettingsBuilderChain)),
                      null);
              statements.add(currentRetrySettingPropertyIfStatement);
              return statements;
            },
            (String methodName) -> {
              List<Statement> statements = new ArrayList<>();
              statements.add(setRetrySettingsForMethod(methodName, settingBuilderVariable));
              return statements;
            });
    bodyStatements.addAll(retrySettings);

    // return expressions
    MethodInvocationExpr serviceSettingsBuilt =
        MethodInvocationExpr.builder()
            .setMethodName("build")
            .setExprReferenceExpr(settingsVarExpr.toBuilder().setIsDecl(false).build())
            .setReturnType(types.get("ServiceSettings"))
            .build();
    MethodInvocationExpr returnExpr =
        MethodInvocationExpr.builder()
            // read more in client composer:
            // src/main/java/com/google/api/generator/gapic/composer/common/AbstractServiceClientClassComposer.java#L277-L292
            .setMethodName("create")
            .setStaticReferenceType(types.get("ServiceClient"))
            .setReturnType(types.get("ServiceClient"))
            .setArguments(serviceSettingsBuilt)
            .build();

    String methodName =
        CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, service.name()) + "Client";

    return MethodDefinition.builder()
        .setName(methodName)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(types.get("ServiceClient"))
        .setArguments(
            Arrays.asList(
                credentialsProviderVariableExpr.toBuilder().setIsDecl(true).build(),
                transportChannelProviderVariableExpr.toBuilder().setIsDecl(true).build()))
        .setAnnotations(
            Arrays.asList(
                AnnotationNode.withType(types.get("Bean")),
                AnnotationNode.withType(types.get("ConditionalOnMissingBean"))))
        .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(IOException.class)))
        .setReturnExpr(returnExpr)
        .setBody(bodyStatements)
        .build();
  }

  private static Map<String, TypeNode> createStaticTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            Generated.class,
            RetrySettings.class,
            RetrySettings.Builder
                .class, // name will be just Builder. consider change of more than one builder here.
            TransportChannelProvider.class,
            // import com.google.api.gax.httpjson.InstantiatingHttpJsonChannelProvider;
            InstantiatingHttpJsonChannelProvider.class,
            ExecutorProvider.class);
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

    TypeNode clientAutoconfig =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(service.name() + "SpringAutoConfig")
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
    TypeNode serviceSettings =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(ClassNames.getServiceSettingsClassName(service))
                .setPakkage(service.pakkage())
                .build());
    TypeNode serviceSettingsBuilder =
        TypeNode.withReference(
            VaporReference.builder()
                .setPakkage(service.pakkage())
                .setName("Builder")
                .setEnclosingClassNames(ClassNames.getServiceSettingsClassName(service))
                .build());

    TypeNode bean =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("Bean")
                .setPakkage("org.springframework.context.annotation")
                .build());
    TypeNode autoConfiguration =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("AutoConfiguration")
                .setPakkage("org.springframework.boot.autoconfigure")
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
    typeMap.put(service.name() + "AutoConfig", clientAutoconfig);
    typeMap.put("GcpProjectIdProvider", gcpProjectIdProvider);
    typeMap.put("Credentials", credentials);
    typeMap.put("DefaultCredentialsProvider", defaultCredentialsProvider);
    typeMap.put("ServiceClient", serviceClient);
    typeMap.put("ServiceSettings", serviceSettings);
    typeMap.put("ServiceSettingsBuilder", serviceSettingsBuilder);
    typeMap.put("Bean", bean);
    typeMap.put("AutoConfiguration", autoConfiguration);
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
