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
import com.google.api.generator.engine.ast.AnonymousClassExpr;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.CastExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.InstanceofExpr;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.ThisObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Method.Stream;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.longrunning.Operation;
import com.google.protobuf.AbstractMessage;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class MockServiceImplClassComposer implements ClassComposer {
  private static final MockServiceImplClassComposer INSTANCE = new MockServiceImplClassComposer();
  private static final String MOCK_SERVICE_IMPL_NAME_PATTERN = "Mock%sImpl";
  private static final String IMPL_BASE_PATTERN = "%sImplBase";
  private static final Map<String, TypeNode> staticTypes = createStaticTypes();
  private static final VariableExpr requestsVarExpr =
      VariableExpr.withVariable(
          Variable.builder()
              .setName("requests")
              .setType(
                  TypeNode.withReference(
                      ConcreteReference.builder()
                          .setClazz(List.class)
                          .setGenerics(
                              Arrays.asList(staticTypes.get("AbstractMessage").reference()))
                          .build()))
              .build());
  private static final VariableExpr responsesVarExpr =
      VariableExpr.withVariable(
          Variable.builder()
              .setName("responses")
              .setType(
                  TypeNode.withReference(
                      ConcreteReference.builder()
                          .setClazz(Queue.class)
                          .setGenerics(Arrays.asList(ConcreteReference.withClazz(Object.class)))
                          .build()))
              .build());

  private MockServiceImplClassComposer() {}

  public static MockServiceImplClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(Service service, Map<String, Message> ignore) {
    Map<String, TypeNode> types = createDynamicTypes(service);
    String className = String.format(MOCK_SERVICE_IMPL_NAME_PATTERN, service.name());
    GapicClass.Kind kind = Kind.TEST;
    String pakkage = service.pakkage();

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(pakkage)
            .setAnnotations(createClassAnnotations())
            .setScope(ScopeNode.PUBLIC)
            .setName(className)
            .setExtendsType(types.get(String.format(IMPL_BASE_PATTERN, service.name())))
            .setStatements(createFieldDeclarations())
            .setMethods(createClassMethods(service, types))
            .build();
    return GapicClass.create(kind, classDef);
  }

  private static List<Statement> createFieldDeclarations() {
    return Arrays.asList(
        ExprStatement.withExpr(
            requestsVarExpr.toBuilder().setIsDecl(true).setScope(ScopeNode.PRIVATE).build()),
        ExprStatement.withExpr(
            responsesVarExpr.toBuilder().setIsDecl(true).setScope(ScopeNode.PRIVATE).build()));
  }

  private static List<AnnotationNode> createClassAnnotations() {
    return Arrays.asList(
        AnnotationNode.builder().setType(staticTypes.get("BetaApi")).build(),
        AnnotationNode.builder()
            .setType(staticTypes.get("Generated"))
            .setDescription("by gapic-generator-java")
            .build());
  }

  private static List<MethodDefinition> createClassMethods(
      Service service, Map<String, TypeNode> types) {
    List<MethodDefinition> javaMethods = new ArrayList<>();
    javaMethods.add(
        createConstructor(
            types.get(String.format(MOCK_SERVICE_IMPL_NAME_PATTERN, service.name()))));
    javaMethods.add(createGetRequestsMethod());
    javaMethods.add(createAddResponseMethod());
    javaMethods.add(createSetResponsesMethod(service));
    javaMethods.add(createAddExceptionMethod());
    javaMethods.add(createResetMethod());
    javaMethods.addAll(createProtoMethodOverrides(service));
    return javaMethods;
  }

  private static MethodDefinition createConstructor(TypeNode classType) {
    return MethodDefinition.constructorBuilder()
        .setScope(ScopeNode.PUBLIC)
        .setBody(createRequestResponseAssignStatements())
        .setReturnType(classType)
        .build();
  }

  private static MethodDefinition createGetRequestsMethod() {
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(requestsVarExpr.type())
        .setName("getRequests")
        .setReturnExpr(requestsVarExpr)
        .build();
  }

  private static MethodDefinition createAddResponseMethod() {
    VariableExpr responseArgExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("response")
                .setType(staticTypes.get("AbstractMessage"))
                .build());
    Expr methodInvocationExpr =
        MethodInvocationExpr.builder()
            .setMethodName("add")
            .setArguments(Arrays.asList(responseArgExpr))
            .setExprReferenceExpr(responsesVarExpr)
            .build();
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName("addResponse")
        .setArguments(Arrays.asList(responseArgExpr.toBuilder().setIsDecl(true).build()))
        .setBody(Arrays.asList(ExprStatement.withExpr(methodInvocationExpr)))
        .build();
  }

  private static MethodDefinition createSetResponsesMethod(Service service) {
    // TODO(miraleung): Re-instantiate the fields here.
    VariableExpr responsesArgVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("responses")
                .setType(
                    TypeNode.withReference(
                        ConcreteReference.builder()
                            .setClazz(List.class)
                            .setGenerics(
                                Arrays.asList(staticTypes.get("AbstractMessage").reference()))
                            .build()))
                .build());
    Expr responseAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(
                responsesVarExpr.toBuilder()
                    .setExprReferenceExpr(
                        ValueExpr.withValue(ThisObjectValue.withType(getThisClassType(service))))
                    .build())
            .setValueExpr(
                NewObjectExpr.builder()
                    .setType(
                        TypeNode.withReference(
                            ConcreteReference.builder()
                                .setClazz(LinkedList.class)
                                .setGenerics(
                                    Arrays.asList(ConcreteReference.withClazz(Object.class)))
                                .build()))
                    .setArguments(Arrays.asList(responsesArgVarExpr))
                    .build())
            .build();
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName("setResponses")
        .setArguments(Arrays.asList(responsesArgVarExpr.toBuilder().setIsDecl(true).build()))
        .setBody(Arrays.asList(ExprStatement.withExpr(responseAssignExpr)))
        .build();
  }

  private static MethodDefinition createAddExceptionMethod() {
    VariableExpr exceptionArgExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("exception")
                .setType(TypeNode.withReference(ConcreteReference.withClazz(Exception.class)))
                .build());
    Expr methodInvocationExpr =
        MethodInvocationExpr.builder()
            .setMethodName("add")
            .setArguments(Arrays.asList(exceptionArgExpr))
            .setExprReferenceExpr(responsesVarExpr)
            .build();
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName("addException")
        .setArguments(Arrays.asList(exceptionArgExpr.toBuilder().setIsDecl(true).build()))
        .setBody(Arrays.asList(ExprStatement.withExpr(methodInvocationExpr)))
        .build();
  }

  private static MethodDefinition createResetMethod() {
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName("reset")
        .setBody(createRequestResponseAssignStatements())
        .build();
  }

  private static List<MethodDefinition> createProtoMethodOverrides(Service service) {
    return service.methods().stream()
        .map(m -> createGenericProtoMethodOverride(m))
        .collect(Collectors.toList());
  }

  private static MethodDefinition createGenericProtoMethodOverride(Method protoMethod) {
    ConcreteReference streamObserverRef = ConcreteReference.withClazz(StreamObserver.class);
    TypeNode objectType = TypeNode.withReference(ConcreteReference.withClazz(Object.class));
    VariableExpr localResponseVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("response").setType(objectType).build());

    VariableExpr responseObserverVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("responseObserver")
                .setType(
                    TypeNode.withReference(
                        streamObserverRef.copyAndSetGenerics(
                            Arrays.asList(protoMethod.outputType().reference()))))
                .build());

    if (protoMethod.stream().equals(Stream.CLIENT) || protoMethod.stream().equals(Stream.BIDI)) {
      return createGenericClientStreamingProtoMethodOverride(
          protoMethod, responseObserverVarExpr, localResponseVarExpr);
    }

    VariableExpr requestArgVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("request").setType(protoMethod.inputType()).build());

    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName(JavaStyle.toLowerCamelCase(protoMethod.name()))
        .setArguments(
            Arrays.asList(
                requestArgVarExpr.toBuilder().setIsDecl(true).build(),
                responseObserverVarExpr.toBuilder().setIsDecl(true).build()))
        .setBody(
            Arrays.asList(
                ExprStatement.withExpr(
                    AssignmentExpr.builder()
                        .setVariableExpr(localResponseVarExpr.toBuilder().setIsDecl(true).build())
                        .setValueExpr(
                            MethodInvocationExpr.builder()
                                .setMethodName("remove")
                                .setExprReferenceExpr(responsesVarExpr)
                                .setReturnType(objectType)
                                .build())
                        .build()),
                createHandleObjectStatement(
                    protoMethod, requestArgVarExpr, responseObserverVarExpr, localResponseVarExpr)))
        .build();
  }

  private static MethodDefinition createGenericClientStreamingProtoMethodOverride(
      Method protoMethod, VariableExpr responseObserverVarExpr, VariableExpr localResponseVarExpr) {
    ConcreteReference streamObserverRef = ConcreteReference.withClazz(StreamObserver.class);

    TypeNode returnType =
        TypeNode.withReference(
            streamObserverRef.copyAndSetGenerics(
                Arrays.asList(protoMethod.inputType().reference())));
    VariableExpr requestObserverVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("requestObserver").setType(returnType).build());
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(returnType)
        .setName(JavaStyle.toLowerCamelCase(protoMethod.name()))
        .setArguments(
            Arrays.asList(
                responseObserverVarExpr.toBuilder().setIsFinal(true).setIsDecl(true).build()))
        .setBody(
            Arrays.asList(
                ExprStatement.withExpr(
                    AssignmentExpr.builder()
                        .setVariableExpr(requestObserverVarExpr.toBuilder().setIsDecl(true).build())
                        .setValueExpr(
                            createStreamObserverAnonymousClassExpr(
                                protoMethod,
                                returnType,
                                responseObserverVarExpr,
                                localResponseVarExpr))
                        .build())))
        .setReturnExpr(requestObserverVarExpr)
        .build();
  }

  private static AnonymousClassExpr createStreamObserverAnonymousClassExpr(
      Method protoMethod,
      TypeNode classType,
      VariableExpr responseObserverVarExpr,
      VariableExpr localResponseVarExpr) {
    return AnonymousClassExpr.builder()
        .setType(classType)
        .setMethods(
            Arrays.asList(
                createOnNextJavaMethod(protoMethod, responseObserverVarExpr, localResponseVarExpr),
                createOnErrorJavaMethod(responseObserverVarExpr),
                createOnCompletedJavaMethod(responseObserverVarExpr)))
        .build();
  }

  private static MethodDefinition createOnNextJavaMethod(
      Method protoMethod, VariableExpr responseObserverVarExpr, VariableExpr localResponseVarExpr) {
    VariableExpr valueVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("value").setType(protoMethod.inputType()).build());
    TypeNode objectType = TypeNode.withReference(ConcreteReference.withClazz(Object.class));

    Statement addValueToRequestsStatement =
        ExprStatement.withExpr(
            MethodInvocationExpr.builder()
                .setMethodName("add")
                .setArguments(Arrays.asList(valueVarExpr))
                .setExprReferenceExpr(requestsVarExpr)
                .build());
    Statement removeObjectStatement =
        ExprStatement.withExpr(
            AssignmentExpr.builder()
                .setVariableExpr(
                    localResponseVarExpr.toBuilder().setIsDecl(true).setIsFinal(true).build())
                .setValueExpr(
                    MethodInvocationExpr.builder()
                        .setMethodName("remove")
                        .setExprReferenceExpr(responsesVarExpr)
                        .setReturnType(objectType)
                        .build())
                .build());

    Statement handleObjectStatement =
        createHandleObjectStatement(
            protoMethod, null, responseObserverVarExpr, localResponseVarExpr);

    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName("onNext")
        .setArguments(Arrays.asList(valueVarExpr.toBuilder().setIsDecl(true).build()))
        .setBody(
            Arrays.asList(
                addValueToRequestsStatement, removeObjectStatement, handleObjectStatement))
        .build();
  }

  private static MethodDefinition createOnErrorJavaMethod(VariableExpr responseObserverVarExpr) {
    VariableExpr throwableVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("t")
                .setType(TypeNode.withReference(ConcreteReference.withClazz(Throwable.class)))
                .build());
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName("onError")
        .setArguments(Arrays.asList(throwableVarExpr.toBuilder().setIsDecl(true).build()))
        .setBody(
            Arrays.asList(
                ExprStatement.withExpr(
                    MethodInvocationExpr.builder()
                        .setMethodName("onError")
                        .setArguments(Arrays.asList(throwableVarExpr))
                        .setExprReferenceExpr(responseObserverVarExpr)
                        .build())))
        .build();
  }

  private static MethodDefinition createOnCompletedJavaMethod(
      VariableExpr responseObserverVarExpr) {
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName("onCompleted")
        .setBody(
            Arrays.asList(
                ExprStatement.withExpr(
                    MethodInvocationExpr.builder()
                        .setMethodName("onCompleted")
                        .setExprReferenceExpr(responseObserverVarExpr)
                        .build())))
        .build();
  }

  private static Statement createHandleObjectStatement(
      Method protoMethod,
      VariableExpr requestArgVarExpr,
      VariableExpr responseObserverVarExpr,
      VariableExpr localResponseVarExpr) {
    List<Expr> ifBodyExprs = new ArrayList<>();
    boolean isAnonymousClass = requestArgVarExpr == null;
    if (!isAnonymousClass) {
      ifBodyExprs.add(
          MethodInvocationExpr.builder()
              .setMethodName("add")
              .setArguments(Arrays.asList(requestArgVarExpr))
              .setExprReferenceExpr(requestsVarExpr)
              .build());
    }

    ifBodyExprs.add(
        MethodInvocationExpr.builder()
            .setMethodName("onNext")
            .setArguments(
                Arrays.asList(
                    CastExpr.builder()
                        .setType(protoMethod.outputType())
                        .setExpr(localResponseVarExpr)
                        .build()))
            .setExprReferenceExpr(responseObserverVarExpr)
            .build());

    if (!isAnonymousClass) {
      ifBodyExprs.add(
          MethodInvocationExpr.builder()
              .setMethodName("onCompleted")
              .setExprReferenceExpr(responseObserverVarExpr)
              .build());
    }

    TypeNode exceptionType = TypeNode.withReference(ConcreteReference.withClazz(Exception.class));

    return IfStatement.builder()
        .setConditionExpr(
            InstanceofExpr.builder()
                .setExpr(localResponseVarExpr)
                .setCheckType(protoMethod.outputType())
                .build())
        .setBody(
            ifBodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()))
        .addElseIf(
            InstanceofExpr.builder()
                .setExpr(localResponseVarExpr)
                .setCheckType(exceptionType)
                .build(),
            Arrays.asList(
                ExprStatement.withExpr(
                    MethodInvocationExpr.builder()
                        .setMethodName("onError")
                        .setArguments(
                            Arrays.asList(
                                CastExpr.builder()
                                    .setType(exceptionType)
                                    .setExpr(localResponseVarExpr)
                                    .build()))
                        .setExprReferenceExpr(responseObserverVarExpr)
                        .build())))
        .setElseBody(
            // TODO(miraleung): Pass a new IllegalArgumentException into
            // responseObserver.onError().
            Arrays.asList(
                ExprStatement.withExpr(
                    MethodInvocationExpr.builder()
                        .setMethodName("onError")
                        .setExprReferenceExpr(responseObserverVarExpr)
                        .build())))
        .build();
  }

  private static Map<String, TypeNode> createStaticTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            AbstractMessage.class,
            ArrayList.class,
            BetaApi.class,
            Generated.class,
            LinkedList.class,
            Operation.class,
            ServerServiceDefinition.class,
            StreamObserver.class);
    return concreteClazzes.stream()
        .collect(
            Collectors.toMap(
                c -> c.getSimpleName(),
                c -> TypeNode.withReference(ConcreteReference.withClazz(c))));
  }

  private static Map<String, TypeNode> createDynamicTypes(Service service) {
    Map<String, TypeNode> types = new HashMap<>();
    // Vapor dependency types.
    String implBase = String.format(IMPL_BASE_PATTERN, service.name());
    types.put(
        implBase,
        TypeNode.withReference(
            VaporReference.builder()
                .setName(implBase)
                // Hack: This should be a nested class and perhaps a static import or something.
                .setPakkage(String.format("%s.%sGrpc", service.pakkage(), service.name()))
                .build()));
    String className = String.format(MOCK_SERVICE_IMPL_NAME_PATTERN, service.name());
    types.put(
        className,
        TypeNode.withReference(
            VaporReference.builder()
                .setName(className)
                // Hack: This should be a nested class and perhaps a static import or something.
                .setPakkage(service.pakkage())
                .build()));
    return types;
  }

  private static TypeNode getThisClassType(Service service) {
    return TypeNode.withReference(
        VaporReference.builder()
            .setName(String.format(MOCK_SERVICE_IMPL_NAME_PATTERN, service.name()))
            .setPakkage(service.pakkage())
            .build());
  }

  private static List<Statement> createRequestResponseAssignStatements() {
    Expr assignRequestVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(requestsVarExpr)
            .setValueExpr(
                NewObjectExpr.builder()
                    .setType(TypeNode.withReference(ConcreteReference.withClazz(ArrayList.class)))
                    .setIsGeneric(true)
                    .build())
            .build();
    Expr assignResponsesVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(responsesVarExpr)
            .setValueExpr(
                NewObjectExpr.builder()
                    .setType(TypeNode.withReference(ConcreteReference.withClazz(LinkedList.class)))
                    .setIsGeneric(true)
                    .build())
            .build();
    return Arrays.asList(assignRequestVarExpr, assignResponsesVarExpr).stream()
        .map(e -> ExprStatement.withExpr(e))
        .collect(Collectors.toList());
  }
}
