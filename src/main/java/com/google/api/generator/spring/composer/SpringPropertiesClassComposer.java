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

import static com.google.api.generator.engine.ast.NewObjectExpr.*;

import com.google.api.gax.retrying.RetrySettings;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.AstNode;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.ThisObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.common.ClassComposer;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicServiceConfig;
import com.google.api.generator.gapic.model.Service;
import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

public class SpringPropertiesClassComposer implements ClassComposer {
  private static final String CLASS_NAME_PATTERN = "%sSpringProperties";

  private static final Map<String, TypeNode> STATIC_TYPES = createStaticTypes();
  private static final String RETRY_PARAM_DEFINITIONS_VAR_NAME = "RETRY_PARAM_DEFINITIONS";

  private static final SpringPropertiesClassComposer INSTANCE = new SpringPropertiesClassComposer();

  public static SpringPropertiesClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(GapicContext context, Service service) {
    String packageName = service.pakkage() + ".spring";
    String className = String.format(CLASS_NAME_PATTERN, service.name());
    GapicServiceConfig gapicServiceConfig = context.serviceConfig();
    Map<String, TypeNode> dynamicTypes = createDynamicTypes(service, packageName);

    // TODO: this is the prefix user will use to set properties, may need to change depending on
    // branding.
    AnnotationNode classAnnotationNode =
        AnnotationNode.builder()
            .setType(STATIC_TYPES.get("ConfigurationProperties"))
            .setDescription(
                "google.cloud.spring.autoconfig."
                    + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, service.name()))
            .build();

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(packageName)
            .setName(className)
            .setScope(ScopeNode.PUBLIC)
            .setStatements(
                createMemberVariables(service, packageName, dynamicTypes, gapicServiceConfig))
            .setMethods(createGetterSetters(service, dynamicTypes, gapicServiceConfig))
            .setAnnotations(Arrays.asList(classAnnotationNode))
            .setImplementsTypes(Arrays.asList(dynamicTypes.get("CredentialsSupplier")))
            .build();
    return GapicClass.create(Kind.MAIN, classDef);
    // return null;
  }

  private static ExprStatement createMemberVarStatement(
      String varName, TypeNode varType, boolean isFinal, Expr defaultVal) {
    Variable memberVar = Variable.builder().setName(varName).setType(varType).build();
    VariableExpr memberVarExpr =
        VariableExpr.builder()
            .setVariable(memberVar)
            .setScope(ScopeNode.PRIVATE)
            .setIsDecl(true)
            .setIsFinal(isFinal)
            .build();

    if (defaultVal == null) {
      return ExprStatement.withExpr(memberVarExpr);
    }
    AssignmentExpr assignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(memberVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(defaultVal)
            .build();
    ExprStatement memberVarStatement = ExprStatement.withExpr(assignmentExpr);

    return memberVarStatement;
  }

  private static List<Statement> createMemberVariables(
      Service service,
      String packageName,
      Map<String, TypeNode> types,
      GapicServiceConfig serviceConfig) {

    String serviceName = service.name();
    //   @NestedConfigurationProperty
    //   private final Credentials credentials = new
    // Credentials("https://www.googleapis.com/auth/cloud-language");
    NewObjectExpr defaultCredentialScopes =
        builder()
            .setType(types.get("Credentials"))
            .setArguments(
                service.oauthScopes().stream()
                    .map(x -> ValueExpr.withValue(StringObjectValue.withValue(x)))
                    .collect(Collectors.toList()))
            .build();
    // TODO: credentials field needs annotation.
    ExprStatement credentialsStatement =
        createMemberVarStatement(
            "credentials", types.get("Credentials"), true, defaultCredentialScopes);

    //   private String quotaProjectId;
    ExprStatement quotaProjectIdVarStatement =
        createMemberVarStatement("quotaProjectId", TypeNode.STRING, false, null);

    //   private Integer executorThreadCount;
    ExprStatement executorThreadCountVarStatement =
        createMemberVarStatement("executorThreadCount", TypeNode.INT_OBJECT, false, null);

    //   private boolean useRest = false;
    ExprStatement useRestVarStatement =
        createMemberVarStatement("useRest", TypeNode.BOOLEAN, false, null);

    //
    //   private static final ImmutableMap<String, RetrySettings> RETRY_PARAM_DEFINITIONS;

    // declare each retry settings with its default value. use defaults from serviceConfig
    TypeNode thisClassType = types.get(service.name() + "Properties");
    List<? extends AstNode> retrySettings =
        Utils.processRetrySettings(
            service,
            serviceConfig,
            thisClassType,
            (String propertyName) -> new ArrayList<>(),
            (List<String> methodAndPropertyName, Expr defaultVal) -> {
              List<Statement> getterAndSetter = new ArrayList<>();
              TypeNode propertyType = defaultVal.type();
              // TODO: safer cast?
              if (propertyType.equals(TypeNode.DOUBLE)) {
                propertyType = TypeNode.DOUBLE_OBJECT;
              }
              String propertyName = Joiner.on("").join(methodAndPropertyName);
              ExprStatement retrySettingsStatement =
                  createMemberVarStatement(propertyName, propertyType, false, null);
              getterAndSetter.add(retrySettingsStatement);
              return getterAndSetter;
            },
            (String propertyName) -> new ArrayList<>());

    List<Statement> statements =
        retrySettings.stream().map(x -> (Statement) x).collect(Collectors.toList());

    statements.add(0, useRestVarStatement);
    statements.add(0, executorThreadCountVarStatement);
    statements.add(0, quotaProjectIdVarStatement);
    statements.add(0, credentialsStatement);
    return statements;
  }

  private static List<MethodDefinition> createGetterSetters(
      Service service, Map<String, TypeNode> types, GapicServiceConfig gapicServiceConfig) {

    TypeNode thisClassType = types.get(service.name() + "Properties");
    List<MethodDefinition> methodDefinitions = new ArrayList<>();

    methodDefinitions.add(
        createGetterMethod(
            thisClassType,
            "credentials",
            types.get("Credentials"),
            Arrays.asList(AnnotationNode.OVERRIDE)));
    methodDefinitions.add(
        createGetterMethod(thisClassType, "quotaProjectId", TypeNode.STRING, null));
    methodDefinitions.add(createSetterMethod(thisClassType, "quotaProjectId", TypeNode.STRING));
    methodDefinitions.add(createGetterMethod(thisClassType, "useRest", TypeNode.BOOLEAN, null));
    methodDefinitions.add(
        createGetterMethod(thisClassType, "executorThreadCount", TypeNode.INT_OBJECT, null));
    methodDefinitions.add(
        createSetterMethod(thisClassType, "executorThreadCount", TypeNode.INT_OBJECT));

    List retrySettings =
        Utils.processRetrySettings(
            service,
            gapicServiceConfig,
            thisClassType,
            (String propertyName) -> new ArrayList<>(),
            (List<String> methodAndPropertyName, Expr defaultVal) -> {
              List<MethodDefinition> getterAndSetter = new ArrayList<>();
              TypeNode propertyType = defaultVal.type();
              if (propertyType.equals(TypeNode.DOUBLE)) {
                propertyType = TypeNode.DOUBLE_OBJECT;
              }
              String propertyName = Joiner.on("").join(methodAndPropertyName);
              getterAndSetter.add(
                  createGetterMethod(thisClassType, propertyName, propertyType, null));
              getterAndSetter.add(createSetterMethod(thisClassType, propertyName, propertyType));
              return getterAndSetter;
            },
            (String propertyName) -> new ArrayList<>());

    methodDefinitions.addAll(retrySettings);
    // TODO: This can be for future stages. for long running operations:
    // for (Method method : service.methods()) {
    //   if (!method.hasLro()) {
    //     continue;
    //   }
    //   //
    // com.google.api.generator.gapic.composer.common.RetrySettingsComposer.createLroSettingsBuilderExpr
    //   //  %sOperationSettings
    // }

    return methodDefinitions;
  }

  private static MethodDefinition createGetterMethod(
      TypeNode thisClassType,
      String propertyName,
      TypeNode returnType,
      List<AnnotationNode> annotationNodes) {

    Variable propertyVar = Variable.builder().setName(propertyName).setType(returnType).build();
    Expr thisExpr = ValueExpr.withValue(ThisObjectValue.withType(thisClassType));

    VariableExpr propertyVariableExpr =
        VariableExpr.withVariable(propertyVar).toBuilder().setExprReferenceExpr(thisExpr).build();

    String methodName = "get" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, propertyName);

    return MethodDefinition.builder()
        .setName(methodName)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(returnType)
        .setAnnotations(annotationNodes == null ? Collections.emptyList() : annotationNodes)
        .setReturnExpr(propertyVariableExpr)
        .build();
  }

  private static MethodDefinition createSetterMethod(
      TypeNode thisClassType, String propertyName, TypeNode returnType) {
    Variable propertyVar = Variable.builder().setName(propertyName).setType(returnType).build();
    Expr thisExpr = ValueExpr.withValue(ThisObjectValue.withType(thisClassType));

    AssignmentExpr propertyVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.withVariable(propertyVar)
                    .toBuilder()
                    .setExprReferenceExpr(thisExpr)
                    .build())
            .setValueExpr(VariableExpr.withVariable(propertyVar))
            .build();

    String methodName = "set" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, propertyName);

    return MethodDefinition.builder()
        .setName(methodName)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setArguments(VariableExpr.builder().setVariable(propertyVar).setIsDecl(true).build())
        .setBody(Arrays.asList(ExprStatement.withExpr(propertyVarExpr)))
        .build();
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
    TypeNode clientProperties =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(service.name() + "SpringProperties")
                .setPakkage(packageName)
                .build());

    // import com.google.cloud.spring.core.Credentials;
    TypeNode credentials =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("Credentials")
                .setPakkage("com.google.cloud.spring.core")
                .build());

    // import com.google.cloud.spring.core.CredentialsSupplier;
    TypeNode credentialsSupplier =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("CredentialsSupplier")
                .setPakkage("com.google.cloud.spring.core")
                .build());

    typeMap.put(service.name() + "Properties", clientProperties);
    typeMap.put("Credentials", credentials);
    typeMap.put("CredentialsSupplier", credentialsSupplier);

    return typeMap;
  }

  private static Map<String, TypeNode> createStaticTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            RetrySettings.class,
            org.threeten.bp.Duration.class,
            ConfigurationProperties.class,
            NestedConfigurationProperty.class);
    return concreteClazzes.stream()
        .collect(
            Collectors.toMap(
                c -> c.getSimpleName(),
                c -> TypeNode.withReference(ConcreteReference.withClazz(c))));
  }
}
