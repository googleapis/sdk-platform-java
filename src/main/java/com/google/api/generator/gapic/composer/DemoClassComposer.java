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
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Service;
import com.google.longrunning.Operation;
import com.google.longrunning.stub.GrpcOperationsStub;
import io.grpc.MethodDescriptor;
import io.grpc.protobuf.ProtoUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class DemoClassComposer implements ClassComposer {
  private static final String CLASS_NAME_PATTERN = "%sDemo";
  private static final String OPERATIONS_STUB_MEMBER_NAME = "operationsStub";
  private static final String BACKGROUND_RESOURCES_MEMBER_NAME = "backgroundResources";

  private static final DemoClassComposer INSTANCE = new DemoClassComposer();

  private static final Map<String, TypeNode> staticTypes = createStaticTypes();

  private DemoClassComposer() {}

  public static DemoClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(Service service, Map<String, Message> ignore) {
    String pakkage = service.pakkage() + ".demo";
    Map<String, TypeNode> types = createDynamicTypes(service, pakkage);
    String className = getThisClassName(service.name());
    GapicClass.Kind kind = Kind.MAIN;

    Map<String, VariableExpr> classMemberVarExprs = new LinkedHashMap<>();
    classMemberVarExprs.put(
        BACKGROUND_RESOURCES_MEMBER_NAME,
        VariableExpr.withVariable(
            Variable.builder()
                .setName(BACKGROUND_RESOURCES_MEMBER_NAME)
                .setType(staticTypes.get("BackgroundResource"))
                .build()));
    classMemberVarExprs.put(
        OPERATIONS_STUB_MEMBER_NAME,
        VariableExpr.withVariable(
            Variable.builder()
                .setName(OPERATIONS_STUB_MEMBER_NAME)
                .setType(staticTypes.get("GrpcOperationsStub"))
                .build()));

    List<Statement> classStatements =
        classMemberVarExprs.values().stream()
            .map(
                v ->
                    ExprStatement.withExpr(
                        v.toBuilder()
                            .setIsDecl(true)
                            .setScope(ScopeNode.PRIVATE)
                            .setIsFinal(true)
                            .build()))
            .collect(Collectors.toList());

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(pakkage)
            .setAnnotations(createClassAnnotations())
            .setScope(ScopeNode.PUBLIC)
            .setName(className)
            .setStatements(classStatements)
            .setMethods(createClassMethods(service, types, classMemberVarExprs))
            .build();
    return GapicClass.create(kind, classDef);
  }

  private static List<AnnotationNode> createClassAnnotations() {
    return Arrays.asList(
        AnnotationNode.builder()
            .setType(staticTypes.get("Generated"))
            .setDescription("by gapic-generator-java")
            .build());
  }

  private static List<MethodDefinition> createClassMethods(
      Service service, Map<String, TypeNode> types, Map<String, VariableExpr> classMemberVarExprs) {
    List<MethodDefinition> javaMethods = new ArrayList<>();
    javaMethods.add(createFoobarMethod());
    return javaMethods;
  }

  private static MethodDefinition createFoobarMethod() {

    VariableExpr xVarExpr =
        VariableExpr.withVariable(Variable.builder().setName("x").setType(TypeNode.INT).build());

    VariableExpr xDeclVarExpr = xVarExpr.toBuilder().setIsDecl(true).build();

    TypeNode threadLocalRandomType =
        TypeNode.withReference(ConcreteReference.withClazz(ThreadLocalRandom.class));

    // nextInt method arguments.
    Expr zeroExpr =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("0").setType(TypeNode.INT).build());
    VariableExpr maxVarExpr =
        VariableExpr.withVariable(Variable.builder().setName("max").setType(TypeNode.INT).build());

    List<Expr> methodArguments = Arrays.asList(zeroExpr, maxVarExpr);

    MethodInvocationExpr methodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(threadLocalRandomType)
            .setMethodName("current")
            .build();
    methodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(methodExpr)
            .setMethodName("nextInt")
            .setArguments(methodArguments)
            .setReturnType(TypeNode.INT)
            .build();

    // Building "int x = ThreadLocalRandom.current().nextInt(mn, mx);"
    Expr assignExpr =
        AssignmentExpr.builder().setVariableExpr(xDeclVarExpr).setValueExpr(methodExpr).build();
    List<Statement> body = Arrays.asList(ExprStatement.withExpr(assignExpr));

    VariableExpr maxArgExpr = maxVarExpr.toBuilder().setIsDecl(true).build();
    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.INT)
        .setName("foobar")
        .setArguments(Arrays.asList(maxArgExpr))
        .setBody(body)
        .setReturnExpr(xVarExpr)
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

  private static Map<String, TypeNode> createDynamicTypes(Service service, String stubPakkage) {
    return Arrays.asList(CLASS_NAME_PATTERN).stream()
        .collect(
            Collectors.toMap(
                p -> String.format(p, service.name()),
                p ->
                    TypeNode.withReference(
                        VaporReference.builder()
                            .setName(String.format(p, service.name()))
                            .setPakkage(stubPakkage)
                            .build())));
  }

  private static String getThisClassName(String serviceName) {
    return String.format(CLASS_NAME_PATTERN, serviceName);
  }
}
