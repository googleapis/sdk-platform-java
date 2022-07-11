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

import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.core.BackgroundResourceAggregation;
import com.google.api.gax.grpc.GrpcCallSettings;
import com.google.api.gax.grpc.GrpcStubCallableFactory;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.ClientStreamingCallable;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.CastExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.LambdaExpr;
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
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import com.google.common.base.CaseFormat;
import com.google.longrunning.Operation;
import com.google.longrunning.stub.GrpcOperationsStub;
import io.grpc.MethodDescriptor;
import io.grpc.protobuf.ProtoUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class SpringAutoConfigClassComposer implements ClassComposer {
  private static final String CLASS_NAME_PATTERN = "%sSpringAutoConfiguration";
  private static final String OPERATIONS_STUB_MEMBER_NAME = "operationsStub";
  private static final String BACKGROUND_RESOURCES_MEMBER_NAME = "backgroundResources";

  private static final SpringAutoConfigClassComposer INSTANCE = new SpringAutoConfigClassComposer();

  private static final Map<String, TypeNode> staticTypes = createStaticTypes();

  private SpringAutoConfigClassComposer() {}

  public static SpringAutoConfigClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(GapicContext ignore, Service service) {
    String packageName = service.pakkage() + ".spring";
    Map<String, TypeNode> types = createDynamicTypes(service, packageName);
    String className = getThisClassName(service.name());
    GapicClass.Kind kind = Kind.MAIN;

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
            .setStatements(createMemberVariables(service.name(), packageName, types))
            .setMethods(
                Arrays.asList(
                    createConstructor(service.name(), className, types), createBeanMethod(service)))
            .setAnnotations(createClassAnnotations(service.name()))
            .build();
    return GapicClass.create(kind, classDef);
  }

  private static List<Statement> createMemberVariables(
      String serviceName, String packageName, Map<String, TypeNode> types) {

    // private final CredentialsProvider credentialsProvider;
    Variable credentialsProviderVar =
        Variable.builder()
            .setName("credentialsProvider")
            .setType(types.get("CredentialsProvider"))
            .build();
    VariableExpr credentialsProviderVarExpr =
        VariableExpr.builder()
            .setVariable(credentialsProviderVar)
            .setScope(ScopeNode.PRIVATE)
            .setIsFinal(true)
            .setIsDecl(true)
            .build();
    ExprStatement credentialsProviderVarStatement =
        ExprStatement.withExpr(credentialsProviderVarExpr);

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

    // private final GcpProjectIdProvider projectIdProvider;
    Variable projectIdProviderVar =
        Variable.builder()
            .setName("projectIdProvider")
            .setType(types.get("GcpProjectIdProvider"))
            .build();
    VariableExpr projectIdProviderVarExpr =
        VariableExpr.builder()
            .setVariable(projectIdProviderVar)
            .setScope(ScopeNode.PRIVATE)
            .setIsFinal(true)
            .setIsDecl(true)
            .build();
    ExprStatement projectIdProviderStatement = ExprStatement.withExpr(projectIdProviderVarExpr);

    return Arrays.asList(
        credentialsProviderVarStatement, clientPropertiesStatement, projectIdProviderStatement);
  }

  private static MethodDefinition createConstructor(
      String serviceName, String className, Map<String, TypeNode> types) {
    /// constructor
    VariableExpr credentialsProviderBuilderVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("coreCredentialsProvider")
                .setType(types.get("CredentialsProvider"))
                .build());

    VariableExpr coreProjectIdProviderVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("coreProjectIdProvider")
                .setType(types.get("GcpProjectIdProvider"))
                .build());

    VariableExpr propertiesVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("clientProperties")
                .setType(types.get(serviceName + "Properties"))
                .build());
    Variable projectIdProviderVar =
        Variable.builder()
            .setName("projectIdProvider")
            .setType(types.get("GcpProjectIdProvider"))
            .build();
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

    /**
     * if (properties.getCredentials().hasKey()) { this.credentialsProvider = new
     * DefaultCredentialsProvider(properties); } else { this.credentialsProvider =
     * coreCredentialsProvider; }
     */

    // expr: properties.getCredentials().hasKey()

    MethodInvocationExpr getCredentialsExpr =
        MethodInvocationExpr.builder()
            .setMethodName("getCredentials")
            .setExprReferenceExpr(propertiesVarExpr)
            .setReturnType(types.get("Credentials"))
            .build();
    MethodInvocationExpr hasKeyExpr =
        MethodInvocationExpr.builder()
            .setMethodName("hasKey")
            .setExprReferenceExpr(getCredentialsExpr)
            .setReturnType(TypeNode.BOOLEAN)
            .build();

    // if body: this.credentialsProvider = new DefaultCredentialsProvider(properties)
    CastExpr castExpr =
        CastExpr.builder()
            .setExpr(
                NewObjectExpr.builder()
                    .setType(types.get("DefaultCredentialsProvider"))
                    .setArguments(propertiesVarExpr)
                    .build())
            .setType(types.get("CredentialsProvider"))
            .build();
    Variable credentialsProviderVar =
        Variable.builder()
            .setName("credentialsProvider")
            .setType(types.get("CredentialsProvider"))
            .build();
    AssignmentExpr credentialsProviderssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.withVariable(credentialsProviderVar)
                    .toBuilder()
                    .setExprReferenceExpr(thisExpr)
                    .build())
            .setValueExpr(castExpr)
            .build();

    // else body: this.credentialsProvider = coreCredentialsProvider;
    List<Expr> coreCredentialsProviderAssignmentExprs = new ArrayList<>();
    coreCredentialsProviderAssignmentExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.withVariable(credentialsProviderVar)
                    .toBuilder()
                    .setExprReferenceExpr(thisExpr)
                    .build())
            .setValueExpr(
                CastExpr.builder()
                    .setExpr(coreProjectIdProviderVarExpr)
                    .setType(types.get("CredentialsProvider"))
                    .build())
            .build());

    IfStatement credentialIfStatement =
        IfStatement.builder()
            .setConditionExpr(hasKeyExpr)
            .setBody(Arrays.asList(ExprStatement.withExpr(credentialsProviderssignExpr)))
            .setElseBody(
                coreCredentialsProviderAssignmentExprs.stream()
                    .map(e -> ExprStatement.withExpr(e))
                    .collect(Collectors.toList()))
            .build();

    /**
     * if (clientProperties.getProjectId() != null) { this.projectIdProvider =
     * clientProperties::getProjectId; } else { this.projectIdProvider = coreProjectIdProvider; }
     */
    // else body: this.projectIdProvider = coreProjectIdProvider;
    List<Expr> ctorAssignmentExprs = new ArrayList<>();
    ctorAssignmentExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(
                VariableExpr.withVariable(projectIdProviderVar)
                    .toBuilder()
                    .setExprReferenceExpr(thisExpr)
                    .build())
            .setValueExpr(coreProjectIdProviderVarExpr)
            .build());

    // expr: clientProperties.getProjectId() != null
    MethodInvocationExpr getProjectIdExpr =
        MethodInvocationExpr.builder()
            .setMethodName("getProjectId")
            .setExprReferenceExpr(
                VariableExpr.withVariable(clientPropertiesVar).toBuilder().build())
            // .setStaticReferenceType(clientType)
            .setReturnType(types.get("CredentialsProvider")) // fake it
            .build();
    RelationalOperationExpr notEqualSentence =
        RelationalOperationExpr.notEqualToWithExprs(getProjectIdExpr, ValueExpr.createNullExpr());

    // () -> clientProperties.getProjectId();
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
                CastExpr.builder()
                    .setExpr(lambdaExpr)
                    .setType(types.get("GcpProjectIdProvider"))
                    .build())
            .build();

    IfStatement projectIdProviderIfStatement =
        IfStatement.builder()
            .setConditionExpr(notEqualSentence)
            .setBody(Arrays.asList(ExprStatement.withExpr(projectIdProviderAssignExpr)))
            .setElseBody(
                ctorAssignmentExprs.stream()
                    .map(e -> ExprStatement.withExpr(e))
                    .collect(Collectors.toList()))
            .build();

    return MethodDefinition.constructorBuilder()
        .setScope(ScopeNode.PROTECTED)
        .setReturnType(types.get(className))
        .setArguments(
            Arrays.asList(
                credentialsProviderBuilderVarExpr.toBuilder().setIsDecl(true).build(),
                coreProjectIdProviderVarExpr.toBuilder().setIsDecl(true).build(),
                propertiesVarExpr.toBuilder().setIsDecl(true).build()))
        .setBody(
            Arrays.asList(
                thisPropertiesAssignmentStatement,
                credentialIfStatement,
                projectIdProviderIfStatement))
        .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(IOException.class)))
        .build();
  }

  private static List<AnnotationNode> createClassAnnotations(String serviceName) {
    TypeNode conditionalOnPropertyType =
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

    // TODO: AnnotationNode description only accepts String for now. need to extend to params
    // and classes.
    AnnotationNode conditionalOnPropertyNode =
        AnnotationNode.builder()
            .setType(conditionalOnPropertyType)
            .setDescription("value = \"spring.cloud.gcp.language.enabled\", matchIfMissing = false")
            .build();
    AnnotationNode conditionalOnClassNode =
        AnnotationNode.builder()
            .setType(conditionalOnClass)
            .setDescription("value = " + serviceName) // need to produce XXX.class
            .build();
    return Arrays.asList(
        AnnotationNode.builder()
            .setType(staticTypes.get("Generated"))
            .setDescription("by gapic-generator-java")
            .build(),
        conditionalOnPropertyNode,
        conditionalOnClassNode);
  }

  private static MethodDefinition createBeanMethod(Service service) {
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
    // client type
    TypeNode clientType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(service.name() + "Client")
                .setPakkage(service.pakkage())
                .build());
    // build expressions
    MethodInvocationExpr lhsExpr =
        MethodInvocationExpr.builder()
            // TODO: might need to use newBuilder().build() if options needed.
            // read more in client composer:
            // src/main/java/com/google/api/generator/gapic/composer/common/AbstractServiceClientClassComposer.java#L277-L292
            .setMethodName("create")
            .setStaticReferenceType(clientType)
            .setReturnType(clientType)
            .build();

    String methodName =
        CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, service.name()) + "Client";
    return MethodDefinition.builder()
        .setName(methodName)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(clientType)
        .setAnnotations(
            Arrays.asList(
                AnnotationNode.withType(beanAnnotationType),
                AnnotationNode.withType(conditionalOnMissingBeanType)))
        .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(IOException.class)))
        .setReturnExpr(lhsExpr)
        .build();
  }

  private static Map<String, TypeNode> createStaticTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            BackgroundResource.class,
            BackgroundResourceAggregation.class,
            BidiStreamingCallable.class,
            ClientContext.class,
            ClientStreamingCallable.class,
            Generated.class,
            GrpcCallSettings.class,
            GrpcOperationsStub.class,
            GrpcStubCallableFactory.class,
            InterruptedException.class,
            IOException.class,
            MethodDescriptor.class,
            Operation.class,
            OperationCallable.class,
            ProtoUtils.class,
            ServerStreamingCallable.class,
            TimeUnit.class,
            UnaryCallable.class);
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
                .setName(service.name() + "Properties")
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

    typeMap.put("CredentialsProvider", credentialsProvider);
    typeMap.put(service.name() + "Properties", clientProperties);
    typeMap.put("GcpProjectIdProvider", gcpProjectIdProvider);
    typeMap.put("Credentials", credentials);
    typeMap.put("DefaultCredentialsProvider", defaultCredentialsProvider);

    return typeMap;
  }

  private static String getThisClassName(String serviceName) {
    return String.format(CLASS_NAME_PATTERN, serviceName);
  }
}
