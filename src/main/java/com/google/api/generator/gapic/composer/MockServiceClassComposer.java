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

import com.google.api.core.BetaApi;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Service;
import com.google.protobuf.AbstractMessage;
import io.grpc.ServerServiceDefinition;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class MockServiceClassComposer implements ClassComposer {
  private static final MockServiceClassComposer INSTANCE = new MockServiceClassComposer();
  private static final String MOCK_SERVICE_NAME_PATTERN = "Mock%s";
  private static final String MOCK_IMPL_NAME_PATTERN = "Mock%sImpl";
  private static final String SERVICE_IMPL_VAR_NAME = "serviceImpl";

  private MockServiceClassComposer() {}

  public static MockServiceClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(Service service, Map<String, Message> ignore) {
    Map<String, TypeNode> types = createTypes(service);
    String className = String.format(MOCK_SERVICE_NAME_PATTERN, service.name());
    GapicClass.Kind kind = Kind.TEST;
    String pakkage = service.pakkage();

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setFileHeader(FileHeader.createApacheLicense())
            .setPackageString(pakkage)
            .setAnnotations(createClassAnnotations(types))
            .setScope(ScopeNode.PUBLIC)
            .setName(className)
            .setImplementsTypes(createClassImplements(types))
            .setStatements(createFieldDeclarations(service.name(), types))
            .setMethods(createClassMethods(service, types))
            .build();
    return GapicClass.create(kind, classDef);
  }

  private static List<Statement> createFieldDeclarations(
      String serviceName, Map<String, TypeNode> types) {
    return Arrays.asList(
        ExprStatement.withExpr(
            VariableExpr.builder()
                .setVariable(getServiceImplVariable(serviceName, types))
                .setScope(ScopeNode.PRIVATE)
                .setIsFinal(true)
                .setIsDecl(true)
                .build()));
  }

  private static List<AnnotationNode> createClassAnnotations(Map<String, TypeNode> types) {
    return Arrays.asList(
        AnnotationNode.builder().setType(types.get("BetaApi")).build(),
        AnnotationNode.builder()
            .setType(types.get("Generated"))
            .setDescription("by gapic-generator-java")
            .build());
  }

  private static List<TypeNode> createClassImplements(Map<String, TypeNode> types) {
    return Arrays.asList(types.get("MockGrpcService"));
  }

  private static List<MethodDefinition> createClassMethods(
      Service service, Map<String, TypeNode> types) {
    VariableExpr serviceImplVarExpr =
        VariableExpr.withVariable(getServiceImplVariable(service.name(), types));
    return Arrays.asList(
        createConstructor(service.name(), serviceImplVarExpr, types),
        createGetRequestsMethod(serviceImplVarExpr, types),
        createAddResponseMethod(serviceImplVarExpr, types),
        createAddExceptionMethod(serviceImplVarExpr),
        createGetServiceDefinitionMethod(serviceImplVarExpr, types),
        createResetMethod(serviceImplVarExpr));
  }

  private static MethodDefinition createConstructor(
      String serviceName, VariableExpr serviceImplVarExpr, Map<String, TypeNode> types) {
    // TODO(miraleung): Instantiate fields here.
    return MethodDefinition.constructorBuilder()
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(types.get(String.format(MOCK_SERVICE_NAME_PATTERN, serviceName)))
        .build();
  }

  private static MethodDefinition createGetRequestsMethod(
      VariableExpr serviceImplVarExpr, Map<String, TypeNode> types) {
    TypeNode returnType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(List.class)
                .setGenerics(Arrays.asList(types.get("AbstractMessage").reference()))
                .build());
    String methodName = "getRequests";
    Expr returnExpr =
        MethodInvocationExpr.builder()
            .setMethodName(methodName)
            .setReturnType(returnType)
            .setExprReferenceExpr(serviceImplVarExpr)
            .build();
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(returnType)
        .setName(methodName)
        .setReturnExpr(returnExpr)
        .build();
  }

  private static MethodDefinition createAddResponseMethod(
      VariableExpr serviceImplVarExpr, Map<String, TypeNode> types) {
    String methodName = "addResponse";
    VariableExpr responseArgExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("response").setType(types.get("AbstractMessage")).build());

    Expr methodInvocationExpr =
        MethodInvocationExpr.builder()
            .setMethodName(methodName)
            .setArguments(Arrays.asList(responseArgExpr))
            .setExprReferenceExpr(serviceImplVarExpr)
            .build();
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName(methodName)
        .setArguments(Arrays.asList(responseArgExpr.toBuilder().setIsDecl(true).build()))
        .setBody(Arrays.asList(ExprStatement.withExpr(methodInvocationExpr)))
        .build();
  }

  private static MethodDefinition createAddExceptionMethod(VariableExpr serviceImplVarExpr) {
    String methodName = "addException";
    VariableExpr exceptionArgExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("exception")
                .setType(TypeNode.withReference(ConcreteReference.withClazz(Exception.class)))
                .build());
    Expr methodInvocationExpr =
        MethodInvocationExpr.builder()
            .setMethodName(methodName)
            .setArguments(Arrays.asList(exceptionArgExpr))
            .setExprReferenceExpr(serviceImplVarExpr)
            .build();
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName(methodName)
        .setArguments(Arrays.asList(exceptionArgExpr.toBuilder().setIsDecl(true).build()))
        .setBody(Arrays.asList(ExprStatement.withExpr(methodInvocationExpr)))
        .build();
  }

  private static MethodDefinition createGetServiceDefinitionMethod(
      VariableExpr serviceImplVarExpr, Map<String, TypeNode> types) {
    TypeNode returnType = types.get("ServerServiceDefinition");
    Expr methodInvocationExpr =
        MethodInvocationExpr.builder()
            .setMethodName("bindService")
            .setExprReferenceExpr(serviceImplVarExpr)
            .setReturnType(returnType)
            .build();
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(returnType)
        .setName("getServiceDefinition")
        .setReturnExpr(methodInvocationExpr)
        .build();
  }

  private static MethodDefinition createResetMethod(VariableExpr serviceImplVarExpr) {
    String methodName = "reset";
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName(methodName)
        .setBody(
            Arrays.asList(
                ExprStatement.withExpr(
                    MethodInvocationExpr.builder()
                        .setMethodName(methodName)
                        .setExprReferenceExpr(serviceImplVarExpr)
                        .build())))
        .build();
  }

  private static Map<String, TypeNode> createTypes(Service service) {
    List<Class> concreteClazzes =
        Arrays.asList(
            AbstractMessage.class, BetaApi.class, Generated.class, ServerServiceDefinition.class);
    Map<String, TypeNode> types =
        concreteClazzes.stream()
            .collect(
                Collectors.toMap(
                    c -> c.getSimpleName(),
                    c -> TypeNode.withReference(ConcreteReference.withClazz(c))));

    // Vapor dependency types.
    String mockGrpcServiceName = "MockGrpcService";
    types.put(
        mockGrpcServiceName,
        TypeNode.withReference(
            VaporReference.builder()
                .setName(mockGrpcServiceName)
                .setPakkage("com.google.api.gax.grpc.testing")
                .build()));
    // Vapor same-package deps.
    String mockImplName = String.format(MOCK_IMPL_NAME_PATTERN, service.name());
    types.put(
        mockImplName,
        TypeNode.withReference(
            VaporReference.builder().setName(mockImplName).setPakkage(service.pakkage()).build()));

    String className = String.format(MOCK_SERVICE_NAME_PATTERN, service.name());
    types.put(
        className,
        TypeNode.withReference(
            VaporReference.builder().setName(className).setPakkage(service.pakkage()).build()));
    return types;
  }

  private static Variable getServiceImplVariable(String serviceName, Map<String, TypeNode> types) {
    return Variable.builder()
        .setName(SERVICE_IMPL_VAR_NAME)
        .setType(types.get(String.format(MOCK_IMPL_NAME_PATTERN, serviceName)))
        .build();
  }
}
