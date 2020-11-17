package com.google.api.generator.gapic.composer;

import com.google.api.core.ApiFuture;
import com.google.api.gax.rpc.ApiStreamObserver;
import com.google.api.gax.rpc.ServerStream;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AnonymousClassExpr;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.EmptyLineStatement;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.ForStatement;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class SampleCodeHelperComposer {
  private static String ASYNC_NAME_PATTERN = "%sAsync";
  private static String UNARY_CALLABLE_NAME_PATTERN = "%sCallable";
  private static String OBSERVER_NAME_PATTERN = "%sObserver";
  private static String RESPONSE_VAR_NAME = "response";
  private static String REQUEST_VAR_NAME = "request";

  // ===========================================RPC==========================================//

  public static TryCatchStatement composeRpcMethodSampleCode(
      String clientName, TypeNode clientType, Method method, List<MethodArgument> arguments) {
    if (arguments.isEmpty()) {
      return composeUnaryRpcDefaultMethodSampleCode(clientName, clientType, method);
    }
    if (method.isPaged()) {
      return composePagedUnaryRpcMethodSampleCode(clientName, clientType, method, arguments);
    }
    if (method.hasLro()) {
      return composeLroUnaryRpcMethodSampleCode(clientName, clientType, method, arguments);
    }
    return composeUnaryRpcMethodSampleCode(clientName, clientType, method, arguments);
  }

  private static TryCatchStatement composeUnaryRpcMethodSampleCode(
      String clientName, TypeNode clientType, Method method, List<MethodArgument> arguments) {
    VariableExpr clientVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName(JavaStyle.toLowerCamelCase(clientName))
                .setType(clientType)
                .build());
    AssignmentExpr initClientWithCreateMethodExpr =
        assignClientVarWithCreateMethodExpr(clientType, JavaStyle.toLowerCamelCase(clientName));
    List<Statement> bodyStatements =
        arguments.stream()
            .map(methodArg -> ExprStatement.withExpr(assignArgumentWithDefaultValue(methodArg)))
            .collect(Collectors.toList());

    Expr invokeMethodExpr =
        method.outputType().equals(TypeNode.VOID)
            ? MethodInvocationExpr.builder()
                .setExprReferenceExpr(clientVarExpr)
                .setMethodName(method.name())
                .setReturnType(clientType)
                .build()
            : assignVarExpr(
                method.outputType(),
                RESPONSE_VAR_NAME,
                clientType,
                JavaStyle.toLowerCamelCase(clientName),
                arguments);
    bodyStatements.add(ExprStatement.withExpr(invokeMethodExpr));
    TryCatchStatement trySampleCodeExpr =
        TryCatchStatement.builder()
            .setTryResourceExpr(initClientWithCreateMethodExpr)
            .setTryBody(bodyStatements)
            .setIsSampleCode(true)
            .build();
    return trySampleCodeExpr;
  }

  private static TryCatchStatement composePagedUnaryRpcMethodSampleCode(
      String clientName, TypeNode clientType, Method method, List<MethodArgument> arguments) {
    VariableExpr clientVarExpr = createVariableExpr(clientName, clientType);
    AssignmentExpr initClientVarExpr =
        assignClientVarWithCreateMethodExpr(clientType, JavaStyle.toLowerCamelCase(clientName));

    List<Statement> bodyStatements =
        arguments.stream()
            .map(methodArg -> ExprStatement.withExpr(assignArgumentWithDefaultValue(methodArg)))
            .collect(Collectors.toList());

    ForStatement loopElementForStatement =
        ForStatement.builder()
            .setLocalVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
            .setCollectionExpr(createIteratorAllMethodExpr(method, clientType, arguments))
            .setBody(Arrays.asList(createLineCommentStatement("doThingsWith(element);")))
            .build();
    bodyStatements.add(loopElementForStatement);
    return TryCatchStatement.builder()
        .setTryResourceExpr(initClientVarExpr)
        .setTryBody(bodyStatements)
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composeLroUnaryRpcMethodSampleCode(
      String clientName, TypeNode clientType, Method method, List<MethodArgument> arguments) {
    List<Statement> bodyStatements =
        arguments.stream()
            .map(methodArg -> ExprStatement.withExpr(assignArgumentWithDefaultValue(methodArg)))
            .collect(Collectors.toList());
    Expr getResponseMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(clientType)
                    .setMethodName(getLroMethodName(method.name()))
                    .setArguments(mapMethodArgumentToExpr(arguments))
                    .build())
            .setMethodName("get")
            .setReturnType(method.outputType())
            .build();
    Expr responseAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(createVariableDeclExpr(RESPONSE_VAR_NAME, method.outputType()))
            .setValueExpr(getResponseMethodExpr)
            .build();
    bodyStatements.add(ExprStatement.withExpr(responseAssignmentExpr));
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVarWithCreateMethodExpr(clientType, clientName))
        .setTryBody(bodyStatements)
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composeUnaryRpcDefaultMethodSampleCode(
      String clientName, TypeNode clientType, Method method) {
    VariableExpr clientVarExpr = createVariableExpr(clientName, clientType);
    AssignmentExpr initClientWithCreateMethodExpr =
        assignClientVarWithCreateMethodExpr(clientType, JavaStyle.toLowerCamelCase(clientName));
    List<MethodArgument> arguments =
        !method.methodSignatures().isEmpty()
            ? method.methodSignatures().get(0)
            : Collections.emptyList();
    List<Statement> bodyStatements =
        arguments.stream()
            .map(methodArg -> ExprStatement.withExpr(assignArgumentWithDefaultValue(methodArg)))
            .collect(Collectors.toList());
    bodyStatements.add(
        ExprStatement.withExpr(createRequestBuilderExpr(method.inputType(), arguments)));
    VariableExpr responseVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(method.outputType()).setName(RESPONSE_VAR_NAME).build());

    if (method.isPaged()) {
      ForStatement loopResponseForStatement =
          ForStatement.builder()
              .setLocalVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
              .setCollectionExpr(createIteratorAllMethodExpr(method, clientType, arguments))
              .setBody(Arrays.asList(createLineCommentStatement("doThingsWith(element);")))
              .build();
      bodyStatements.add(loopResponseForStatement);
    } else if (method.hasLro()) {
      Expr getResponseMethodExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(
                  MethodInvocationExpr.builder()
                      .setStaticReferenceType(clientType)
                      .setMethodName(getLroMethodName(method.name()))
                      .setArguments(
                          Arrays.asList(createVariableExpr("request", method.inputType())))
                      .build())
              .setMethodName("get")
              .setReturnType(method.outputType())
              .build();
      Expr responseAssignmentExpr =
          AssignmentExpr.builder()
              .setVariableExpr(createVariableDeclExpr(RESPONSE_VAR_NAME, method.outputType()))
              .setValueExpr(getResponseMethodExpr)
              .build();
      bodyStatements.add(ExprStatement.withExpr(responseAssignmentExpr));
    } else {
      Expr assignResponseExpr =
          AssignmentExpr.builder()
              .setVariableExpr(responseVarExpr)
              .setValueExpr(
                  MethodInvocationExpr.builder()
                      .setStaticReferenceType(clientType)
                      .setMethodName(method.name())
                      .setArguments(createVariableExpr("request", method.inputType()))
                      .setReturnType(method.outputType())
                      .build())
              .build();
      bodyStatements.add(ExprStatement.withExpr(assignResponseExpr));
    }
    return TryCatchStatement.builder()
        .setTryResourceExpr(initClientWithCreateMethodExpr)
        .setTryBody(bodyStatements)
        .setIsSampleCode(true)
        .build();
  }

  // ========================================= PRC Callable ==================================//
  public static TryCatchStatement composeRpcCallableMethodSampleCode(
      String clientName, TypeNode clientType, Method method) {
    return composeUnaryRpcCallableMethodSampleCode(clientName, clientType, method);
  }

  public static TryCatchStatement composeStreamClientRpcCallableMethodSampleCode(
      String clientName, TypeNode clientType, Method method) {
    List<MethodArgument> arguments =
        method.methodSignatures().isEmpty()
            ? Collections.emptyList()
            : method.methodSignatures().get(0);
    MethodInvocationExpr onNextMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(createStreamObserverVarExpr(method, false))
            .setMethodName("onNext")
            .setArguments(createVariableExpr(REQUEST_VAR_NAME, method.inputType()))
            .build();

    List<Statement> bodyStatements = new ArrayList<>();
    bodyStatements.add(ExprStatement.withExpr(assignStreamObserverResponseExpr(method)));
    bodyStatements.add(ExprStatement.withExpr(assignStreamObserverRequestExpr(method, clientType)));
    bodyStatements.add(EmptyLineStatement.create());
    bodyStatements.addAll(
        arguments.stream()
            .map(methodArg -> ExprStatement.withExpr(assignArgumentWithDefaultValue(methodArg)))
            .collect(Collectors.toList()));
    bodyStatements.add(
        ExprStatement.withExpr(createRequestBuilderExpr(method.inputType(), arguments)));
    bodyStatements.add(ExprStatement.withExpr(onNextMethodExpr));
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVarWithCreateMethodExpr(clientType, clientName))
        .setTryBody(bodyStatements)
        .setIsSampleCode(true)
        .build();
  }

  public static TryCatchStatement composeStreamServerRpcCallableMethodSampleCode(
      String clientName, TypeNode clientType, Method method) {
    List<MethodArgument> arguments =
        method.methodSignatures().isEmpty()
            ? Collections.emptyList()
            : method.methodSignatures().get(0);
    List<Statement> bodyStatements =
        arguments.stream()
            .map(methodArg -> ExprStatement.withExpr(assignArgumentWithDefaultValue(methodArg)))
            .collect(Collectors.toList());
    ForStatement loopResponseForExpr =
        ForStatement.builder()
            .setLocalVariableExpr(
                createVariableExpr(RESPONSE_VAR_NAME, method.outputType())
                    .toBuilder()
                    .setIsDecl(true)
                    .build())
            .setCollectionExpr(createServerStreamVarExpr(method))
            .setBody(
                Arrays.asList(createLineCommentStatement("Do something when receive a response.")))
            .build();

    bodyStatements.add(
        ExprStatement.withExpr(createRequestBuilderExpr(method.inputType(), arguments)));
    bodyStatements.add(ExprStatement.withExpr(createServerStreamWithValueExpr(method, clientType)));
    bodyStatements.add(loopResponseForExpr);
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVarWithCreateMethodExpr(clientType, clientName))
        .setTryBody(bodyStatements)
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composeUnaryRpcCallableMethodSampleCode(
      String clientName, TypeNode clientType, Method method) {
    List<MethodArgument> arguments =
        method.methodSignatures().isEmpty()
            ? Collections.emptyList()
            : method.methodSignatures().get(0);
    List<Statement> bodyStatements =
        arguments.stream()
            .map(methodArg -> ExprStatement.withExpr(assignArgumentWithDefaultValue(methodArg)))
            .collect(Collectors.toList());
    bodyStatements.add(
        ExprStatement.withExpr(createRequestBuilderExpr(method.inputType(), arguments)));
    bodyStatements.add(
        ExprStatement.withExpr(createFutureResponseWithValueExpr(method, clientType)));
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVarWithCreateMethodExpr(clientType, clientName))
        .setTryBody(bodyStatements)
        .setIsSampleCode(true)
        .build();
  }
  // ===========================================Helper==========================================//
  private static Expr assignStreamObserverResponseExpr(Method method) {
    VariableExpr streamObserverResponseVarExpr = createStreamObserverVarExpr(method, true);
    MethodDefinition onNextMethod = MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setName("onNext")
        .setArguments(createVariableDeclExpr(RESPONSE_VAR_NAME, method.outputType()))
        .setBody(Arrays.asList(createLineCommentStatement("Do something when receive a response.")))
        .setReturnType(TypeNode.VOID)
        .build();
    MethodDefinition onErrorMethod = MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setName("onError")
        .setArguments(createVariableDeclExpr("t", TypeNode.withReference(ConcreteReference.withClazz(Throwable.class))))
        .setBody(Arrays.asList(createLineCommentStatement("Add error-handling")))
        .setReturnType(TypeNode.VOID)
        .build();
    MethodDefinition onCompletedMethod = MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setName("onCompleted")
        .setBody(Arrays.asList(createLineCommentStatement("Do something when complete.")))
        .setReturnType(TypeNode.VOID)
        .build();

    AnonymousClassExpr anonymousClassExpr = AnonymousClassExpr.builder()
        .setType(streamObserverResponseVarExpr.variable().type())
        .setMethods(onNextMethod, onErrorMethod, onCompletedMethod)
        .build();
    return AssignmentExpr.builder()
        .setVariableExpr(streamObserverResponseVarExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(anonymousClassExpr)
        .build();
  }

  private static Expr assignStreamObserverRequestExpr(Method method, TypeNode clientType) {
    VariableExpr streamObserverRequestExpr = createStreamObserverVarExpr(method, false);
    MethodInvocationExpr streamingCallMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(clientType)
                    .setMethodName(getCallableMethodName(method.name()))
                    .build())
            .setMethodName("clientStreamingCall")
            .setArguments(createStreamObserverVarExpr(method, true))
            .setReturnType(streamObserverRequestExpr.variable().type())
            .build();
    return AssignmentExpr.builder()
        .setVariableExpr(streamObserverRequestExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(streamingCallMethodExpr)
        .build();
  }

  private static VariableExpr createStreamObserverVarExpr(Method method, boolean isResponse) {
    return VariableExpr.withVariable(
        Variable.builder()
            .setName(
                isResponse
                    ? getObserverVariableName(RESPONSE_VAR_NAME)
                    : getObserverVariableName(REQUEST_VAR_NAME))
            .setType(
                TypeNode.withReference(
                    ConcreteReference.builder()
                        .setClazz(ApiStreamObserver.class)
                        .setGenerics(
                            Arrays.asList(
                                isResponse
                                    ? method.outputType().reference()
                                    : method.inputType().reference()))
                        .build()))
            .build());
  }

  private static Expr createServerStreamWithValueExpr(Method method, TypeNode clientType) {
    VariableExpr streamVarExpr = createServerStreamVarExpr(method);
    MethodInvocationExpr callMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(clientType)
                    .setMethodName(getCallableMethodName(method.name()))
                    .build())
            .setMethodName("call")
            .setArguments(createVariableExpr("request", method.inputType()))
            .setReturnType(streamVarExpr.variable().type())
            .build();
    return AssignmentExpr.builder()
        .setVariableExpr(streamVarExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(callMethodExpr)
        .build();
  }

  private static VariableExpr createServerStreamVarExpr(Method method) {
    return VariableExpr.withVariable(
        Variable.builder()
            .setName("stream")
            .setType(
                TypeNode.withReference(
                    ConcreteReference.builder()
                        .setClazz(ServerStream.class)
                        .setGenerics(Arrays.asList(method.outputType().reference()))
                        .build()))
            .build());
  }

  private static Expr createFutureResponseWithValueExpr(Method method, TypeNode clientType) {
    VariableExpr futureResponseVarExpr = createUnaryFutureResponseVarExpr(method);
    MethodInvocationExpr futureCallMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(
                MethodInvocationExpr.builder()
                    .setMethodName(getCallableMethodName(method.name()))
                    .setStaticReferenceType(clientType)
                    .build())
            .setMethodName("futureCall")
            .setArguments(createVariableExpr("request", method.inputType()))
            .setReturnType(futureResponseVarExpr.variable().type())
            .build();
    return AssignmentExpr.builder()
        .setVariableExpr(futureResponseVarExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(futureCallMethodExpr)
        .build();
  }

  private static VariableExpr createUnaryFutureResponseVarExpr(Method method) {
    return VariableExpr.withVariable(
        Variable.builder()
            .setName("futureResponse")
            .setType(
                TypeNode.withReference(
                    ConcreteReference.builder()
                        .setClazz(ApiFuture.class)
                        .setGenerics(Arrays.asList(method.outputType().reference()))
                        .build()))
            .build());
  }

  private static Expr createRequestBuilderExpr(
      TypeNode requestType, List<MethodArgument> arguments) {
    MethodInvocationExpr newBuilderExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(requestType)
            .setMethodName("newBuilder")
            .build();
    for (MethodArgument arg : arguments) {
      newBuilderExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(newBuilderExpr)
              .setMethodName(String.format("set%s", JavaStyle.toUpperCamelCase(arg.name())))
              .setArguments(
                  VariableExpr.withVariable(
                      Variable.builder().setName(arg.name()).setType(arg.type()).build()))
              .build();
    }
    MethodInvocationExpr requestBuildExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(newBuilderExpr)
            .setMethodName("build")
            .setReturnType(requestType)
            .build();
    return AssignmentExpr.builder()
        .setVariableExpr(createVariableDeclExpr("request", requestType))
        .setValueExpr(requestBuildExpr)
        .build();
  }

  private static Expr assignVarExpr(
      TypeNode varType,
      String varName,
      TypeNode classType,
      String methodName,
      List<MethodArgument> arguments) {
    VariableExpr varExpr =
        VariableExpr.withVariable(Variable.builder().setType(varType).setName(varName).build());
    List<Expr> argExprs = mapMethodArgumentToExpr(arguments);
    MethodInvocationExpr invokeMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(classType)
            .setMethodName(methodName)
            .setArguments(argExprs)
            .setReturnType(varType)
            .build();
    return AssignmentExpr.builder()
        .setVariableExpr(varExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(invokeMethodExpr)
        .build();
  }

  private static AssignmentExpr assignClientVarWithCreateMethodExpr(
      TypeNode clientType, String clientName) {
    return (AssignmentExpr)
        assignVarExpr(clientType, clientName, clientType, "create", Collections.emptyList());
  }

  private static Expr assignArgumentWithDefaultValue(MethodArgument argument) {
    // TODO: consider List<variable>
    // https://github.com/googleapis/java-video-intelligence/blob/master/google-cloud-video-intelligence/src/main/java/com/google/cloud/videointelligence/v1/VideoIntelligenceServiceClient.java#L183
    VariableExpr argVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName(argument.name()).setType(argument.field().type()).build());
    return AssignmentExpr.builder()
        .setVariableExpr(argVarExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(DefaultValueComposer.createDefaultValue(argument.field()))
        .build();
  }

  private static List<Expr> mapMethodArgumentToExpr(List<MethodArgument> methodArguments) {
    return methodArguments.stream()
        .map(methodArg -> createVariableExpr(methodArg.name(), methodArg.type()))
        .collect(Collectors.toList());
  }

  private static Expr createIteratorAllMethodExpr(
      Method method, TypeNode clientType, List<MethodArgument> arguments) {
    return MethodInvocationExpr.builder()
        .setExprReferenceExpr(
            MethodInvocationExpr.builder()
                .setStaticReferenceType(clientType)
                .setMethodName(method.name())
                .setArguments(
                    !arguments.isEmpty()
                        ? mapMethodArgumentToExpr(arguments)
                        : Arrays.asList(createVariableExpr("request", method.inputType())))
                .build())
        .setMethodName("iterateAll")
        .setReturnType(clientType)
        .build();
  }

  private static VariableExpr createVariableExpr(String variableName, TypeNode type) {
    return createVariableExpr(variableName, type, false);
  }

  private static VariableExpr createVariableDeclExpr(String variableName, TypeNode type) {
    return createVariableExpr(variableName, type, true);
  }

  private static VariableExpr createVariableExpr(
      String variableName, TypeNode type, boolean isDecl) {
    return VariableExpr.builder()
        .setVariable(createVariable(variableName, type))
        .setIsDecl(isDecl)
        .build();
  }

  private static Variable createVariable(String varName, TypeNode type) {
    return Variable.builder().setName(varName).setType(type).build();
  }

  private static CommentStatement createLineCommentStatement(String content) {
    return CommentStatement.withComment(LineComment.withComment(content));
  }

  private static String getLroMethodName(String methodName) {
    return JavaStyle.toLowerCamelCase(String.format(ASYNC_NAME_PATTERN, methodName));
  }

  private static String getCallableMethodName(String methodName) {
    return JavaStyle.toLowerCamelCase(String.format(UNARY_CALLABLE_NAME_PATTERN, methodName));
  }

  private static String getObserverVariableName(String variableName) {
    return JavaStyle.toLowerCamelCase(String.format(OBSERVER_NAME_PATTERN, variableName));
  }
}
