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

import com.google.api.gax.rpc.ApiStreamObserver;
import com.google.api.gax.rpc.ServerStream;
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
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Method.Stream;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class SampleCodeHelperComposer {
  private static String RESPONSE_VAR_NAME = "response";
  private static String REQUEST_VAR_NAME = "request";
  private static String ASYNC_NAME_PATTERN = "%sAsync";
  private static String OBSERVER_NAME_PATTERN = "%sObserver";
  private static String UNARY_CALLABLE_NAME_PATTERN = "%sCallable";

  public static TryCatchStatement composeRpcMethodSampleCode(
      Method method,
      List<MethodArgument> arguments,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames) {
    // Default Unary RPC method.
    if (arguments.isEmpty()) {
      return composeUnaryRpcDefaultMethodSampleCode(method, clientType, resourceNames);
    }
    // Paged Unary RPC method.
    if (method.isPaged()) {
      return composePagedUnaryRpcMethodSampleCode(method, arguments, clientType, resourceNames);
    }
    // Long run operation Unary RPC method.
    if (method.hasLro()) {
      return composeLroUnaryRpcMethodSampleCode(method, arguments, clientType, resourceNames);
    }
    // Pure Unary RPC method.
    return composeUnaryRpcMethodSampleCode(method, arguments, clientType, resourceNames);
  }

  public static TryCatchStatement composeRpcCallableMethodSampleCode(
      Method method,
      TypeNode clientType,
      TypeNode returnType,
      Map<String, ResourceName> resourceNames) {
    if (method.stream() != Stream.NONE) {
      return composeStreamRpcCallableMethodSampleCode(method, clientType, resourceNames);
    }
    if (method.isPaged()) {
      return composePagedRpcCallableMethodSampleCode(method, clientType, returnType, resourceNames);
    }
    if (method.hasLro()) {
      return composeLroRpcCallableMethodSampleCode(method, clientType, returnType, resourceNames);
    }
    return composeUnaryRpcCallableMethodSampleCode(method, clientType, resourceNames);
  }

  // ============================================ PRC  =========================================//

  private static TryCatchStatement composeUnaryRpcMethodSampleCode(
      Method method,
      List<MethodArgument> arguments,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames) {
    // TODO(summerji): Add unit tests.
    // Assign each method arguments with default value.
    List<Statement> bodyStatements =
        arguments.stream()
            .map(
                methodArg ->
                    ExprStatement.withExpr(
                        assignMethodArgumentWithDefaultValue(methodArg, resourceNames)))
            .collect(Collectors.toList());
    // Invoke current method based on return type.
    // e.g. if return void, echoClient.echo(..); or,
    // e.g. if return other type, EchoResponse response = echoClient.echo(...);
    if (method.outputType().equals(TypeNode.VOID)) {
      bodyStatements.add(
          ExprStatement.withExpr(
              MethodInvocationExpr.builder()
                  .setExprReferenceExpr(
                      createVariableDeclExpr(getClientName(clientType), clientType))
                  .setMethodName(method.name())
                  .setReturnType(clientType)
                  .build()));
    } else {
      bodyStatements.add(
          ExprStatement.withExpr(
              createAssignExprForVariableWithClientMethod(
                  RESPONSE_VAR_NAME, method.outputType(), clientType, method.name(), arguments)));
    }

    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientType))
        .setTryBody(bodyStatements)
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composeLroUnaryRpcMethodSampleCode(
      Method method,
      List<MethodArgument> arguments,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames) {
    // TODO(summerji): compose sample code for unary lro rpc method.
    // TODO(summerji): Add unit tests.
    // Assign each method arguments with default value.
    List<Statement> bodyStatements =
        arguments.stream()
            .map(
                methodArg ->
                    ExprStatement.withExpr(
                        assignMethodArgumentWithDefaultValue(methodArg, resourceNames)))
            .collect(Collectors.toList());
    // Assign response variable with get method.
    // e.g EchoResponse response = echoClient.waitAsync().get();
    Expr getResponseMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(createVariableExpr(getClientName(clientType), clientType))
                    .setMethodName(getLroMethodName(method.name()))
                    .setArguments(mapMethodArgumentsToVariableExprs(arguments))
                    .build())
            .setMethodName("get")
            .setReturnType(method.outputType())
            .build();
    bodyStatements.add(
        ExprStatement.withExpr(
            AssignmentExpr.builder()
                .setVariableExpr(createVariableDeclExpr(RESPONSE_VAR_NAME, method.outputType()))
                .setValueExpr(getResponseMethodExpr)
                .build()));
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientType))
        .setTryBody(bodyStatements)
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composePagedUnaryRpcMethodSampleCode(
      Method method,
      List<MethodArgument> arguments,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames) {
    // TODO(summerji): Add unit test.
    // Assign each method arguments with default value.
    List<Statement> bodyStatements =
        arguments.stream()
            .map(
                methodArg ->
                    ExprStatement.withExpr(
                        assignMethodArgumentWithDefaultValue(methodArg, resourceNames)))
            .collect(Collectors.toList());
    // For loop client on iterateAll method.
    // e.g. for (LoggingServiceV2Client loggingServiceV2Client :
    // loggingServiceV2Client.ListLogs(parent).iterateAll()) {
    // //doThingsWith(element);}
    bodyStatements.add(
        ForStatement.builder()
            .setLocalVariableExpr(createVariableDeclExpr(getClientName(clientType), clientType))
            .setCollectionExpr(createIteratorAllMethodExpr(method, clientType, arguments))
            .setBody(Arrays.asList(createLineCommentStatement("doThingsWith(element);")))
            .build());
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientType))
        .setTryBody(bodyStatements)
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composeUnaryRpcDefaultMethodSampleCode(
      Method method, TypeNode clientType, Map<String, ResourceName> resourceNames) {
    // TODO(summerji): compose sample code for unary default rpc method.
    // TODO(summerji): Add unit tests.
    // If variant method signatures exists, use the first one.
    List<MethodArgument> arguments =
        !method.methodSignatures().isEmpty()
            ? method.methodSignatures().get(0)
            : Collections.emptyList();
    // Assign each method arguments with default value.
    List<Statement> bodyStatements =
        arguments.stream()
            .map(
                methodArg ->
                    ExprStatement.withExpr(
                        assignMethodArgumentWithDefaultValue(methodArg, resourceNames)))
            .collect(Collectors.toList());
    // Assign request variables with set argument attributes.
    // e.g EchoRequest
    bodyStatements.add(
        ExprStatement.withExpr(createRequestBuilderExpr(method.inputType(), arguments)));

    if (method.isPaged()) {
      // For loop on invoke client method's iterator with comment.
      // e.g. for (EchoClient echoClient : echoClient.PagedExpand(request).iterateAll()) {//..}
      bodyStatements.add(
          ForStatement.builder()
              .setLocalVariableExpr(createVariableDeclExpr(getClientName(clientType), clientType))
              .setCollectionExpr(createIteratorAllMethodExpr(method, clientType, arguments))
              .setBody(Arrays.asList(createLineCommentStatement("doThingsWith(element);")))
              .build());
    } else if (method.hasLro()) {
      // Create response variable by invoke client's async method.
      // e.g Operation response = EchoClient.waitAsync(request).get();
      Expr getResponseMethodExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(
                  MethodInvocationExpr.builder()
                      .setStaticReferenceType(clientType)
                      .setMethodName(getLroMethodName(method.name()))
                      .setArguments(
                          Arrays.asList(createVariableExpr(REQUEST_VAR_NAME, method.inputType())))
                      .build())
              .setMethodName("get")
              .setReturnType(method.outputType())
              .build();
      bodyStatements.add(
          ExprStatement.withExpr(
              AssignmentExpr.builder()
                  .setVariableExpr(createVariableDeclExpr(RESPONSE_VAR_NAME, method.outputType()))
                  .setValueExpr(getResponseMethodExpr)
                  .build()));
    } else {
      // Create response variable by invoke client's method by passing request.
      // e.g. EchoResponse response = echoClient.Echo(request);
      Expr invokeMethodExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(createVariableExpr(getClientName(clientType), clientType))
              .setMethodName(method.name())
              .setArguments(createVariableExpr("request", method.inputType()))
              .setReturnType(method.outputType())
              .build();
      bodyStatements.add(
          ExprStatement.withExpr(
              AssignmentExpr.builder()
                  .setVariableExpr(createVariableDeclExpr(RESPONSE_VAR_NAME, method.outputType()))
                  .setValueExpr(invokeMethodExpr)
                  .build()));
    }

    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientType))
        .setTryBody(bodyStatements)
        .setIsSampleCode(true)
        .build();
  }

  // ======================================== PRC Callable =====================================//
  private static TryCatchStatement composeStreamRpcCallableMethodSampleCode(
      Method method, TypeNode clientType, Map<String, ResourceName> resourceNames) {
    if (method.stream() == Stream.CLIENT) {
      return composeStreamClientRpcCallableMethodSampleCode(method, clientType, resourceNames);
    }
    if (method.stream() == Stream.SERVER) {
      return composeStreamServerRpcCallableMethodSampleCode(method, clientType, resourceNames);
    }
    return composeStreamBiDiRpcCallableMethodSampleCode(method, clientType);
  }

  private static TryCatchStatement composeStreamClientRpcCallableMethodSampleCode(
      Method method, TypeNode clientType, Map<String, ResourceName> resourceNames) {
    // TODO(summerji): Add unit tests
    // TODO(summerji): Add customize escape for @ character in <pre>{@code..}</pre>
    // If variant method signatures exists, use the first one.
    List<MethodArgument> arguments =
        method.methodSignatures().isEmpty()
            ? Collections.emptyList()
            : method.methodSignatures().get(0);
    MethodInvocationExpr onNextRequestMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(createStreamObserverVarExpr(method, false))
            .setMethodName("onNext")
            .setArguments(createVariableExpr(REQUEST_VAR_NAME, method.inputType()))
            .build();

    List<Statement> bodyStatements = new ArrayList<>();
    // Create StreamObserver response variable with anonymous class.
    bodyStatements.add(
        ExprStatement.withExpr(createAssignStreamObserverResponseAnonymousClassExpr(method)));
    // Create StreamObserver request variable with invoke chain method from client method.
    bodyStatements.add(
        ExprStatement.withExpr(createAssignStreamObserverRequestExpr(method, clientType)));
    // Assign each method argument with default value.
    bodyStatements.add(EmptyLineStatement.create());
    bodyStatements.addAll(
        arguments.stream()
            .map(
                methodArg ->
                    ExprStatement.withExpr(
                        assignMethodArgumentWithDefaultValue(methodArg, resourceNames)))
            .collect(Collectors.toList()));
    // Create request variable by set attributes base on method arguments.
    bodyStatements.add(
        ExprStatement.withExpr(createRequestBuilderExpr(method.inputType(), arguments)));
    // Invoke onNext method on request varaible.
    bodyStatements.add(ExprStatement.withExpr(onNextRequestMethodExpr));

    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientType))
        .setTryBody(bodyStatements)
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composeStreamServerRpcCallableMethodSampleCode(
      Method method, TypeNode clientType, Map<String, ResourceName> resourceNames) {
    // TODO(summerji): Add unit tests
    // If variant method signatures exists, use the first one.
    List<MethodArgument> arguments =
        method.methodSignatures().isEmpty()
            ? Collections.emptyList()
            : method.methodSignatures().get(0);
    // Assign each method arguments with default value.
    List<Statement> bodyStatements =
        arguments.stream()
            .map(
                methodArg ->
                    ExprStatement.withExpr(
                        assignMethodArgumentWithDefaultValue(methodArg, resourceNames)))
            .collect(Collectors.toList());
    TypeNode serverStreamType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ServerStream.class)
                .setGenerics(Arrays.asList(method.outputType().reference()))
                .build());
    VariableExpr serverStreamVarExpr = createVariableExpr("stream", serverStreamType);
    // Assign request variable with attributes based on method arguments.
    bodyStatements.add(
        ExprStatement.withExpr(createRequestBuilderExpr(method.inputType(), arguments)));
    // Create server stream variable with invoke client methods and call method.
    // e.g ServerStream<EchoResponse> stream = EchoClient.expandCallable().call(request);
    bodyStatements.add(
        ExprStatement.withExpr(
            createServerStreamWithValueExpr(method, clientType, serverStreamVarExpr)));
    // For loop on stream response variable with comment as body.
    bodyStatements.add(
        ForStatement.builder()
            .setLocalVariableExpr(createVariableDeclExpr(RESPONSE_VAR_NAME, method.outputType()))
            .setCollectionExpr(serverStreamVarExpr)
            .setBody(
                Arrays.asList(createLineCommentStatement("Do something when receive a response.")))
            .build());
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientType))
        .setTryBody(bodyStatements)
        .setIsSampleCode(true)
        .build();
  }

  private static Expr createServerStreamWithValueExpr(
      Method method, TypeNode clientType, VariableExpr serverStreamVarExpr) {
    MethodInvocationExpr callMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(createVariableExpr(getClientName(clientType), clientType))
                    .setMethodName(getCallableMethodName(method.name()))
                    .build())
            .setMethodName("call")
            .setArguments(createVariableExpr("request", method.inputType()))
            .setReturnType(serverStreamVarExpr.variable().type())
            .build();
    return AssignmentExpr.builder()
        .setVariableExpr(serverStreamVarExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(callMethodExpr)
        .build();
  }

  private static TryCatchStatement composeStreamBiDiRpcCallableMethodSampleCode(
      Method method, TypeNode clientType) {
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientType))
        .setTryBody(
            Arrays.asList(
                createLineCommentStatement(
                    "Note: Not implement yet, placeholder for Stream.BiDi Rpc callable methods' sample code.")))
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composePagedRpcCallableMethodSampleCode(
      Method method,
      TypeNode clientType,
      TypeNode returnType,
      Map<String, ResourceName> resourceNames) {
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientType))
        .setTryBody(
            Arrays.asList(
                createLineCommentStatement(
                    "Note: Not implement yet, placeholder for Paged Rpc callable methods' sample code.")))
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composeLroRpcCallableMethodSampleCode(
      Method method,
      TypeNode clientType,
      TypeNode returnType,
      Map<String, ResourceName> resourceNames) {
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientType))
        .setTryBody(
            Arrays.asList(
                createLineCommentStatement(
                    "Note: Not implement yet, placeholder for Lro Rpc callable methods' sample code.")))
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composeUnaryRpcCallableMethodSampleCode(
      Method method, TypeNode clientType, Map<String, ResourceName> resourceNames) {
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientType))
        .setTryBody(
            Arrays.asList(
                createLineCommentStatement(
                    "Note: Not implement yet, placeholder for Unary Rpc callable methods' sample code.")))
        .setIsSampleCode(true)
        .build();
  }

  // ======================================== Helpers ===========================================//

  // Assign client variable expr with create client.
  // e.g EchoClient echoClient = EchoClient.create()
  private static AssignmentExpr assignClientVariableWithCreateMethodExpr(TypeNode clientType) {
    return AssignmentExpr.builder()
        .setVariableExpr(createVariableDeclExpr(getClientName(clientType), clientType))
        .setValueExpr(
            MethodInvocationExpr.builder()
                .setStaticReferenceType(clientType)
                .setReturnType(clientType)
                .setMethodName("create")
                .build())
        .build();
  }

  // Create request variable by set attributes.
  // e.g. EchoRequest request = EchoRequest.newBuilder().setParent(parent).build();
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

  private static Expr assignMethodArgumentWithDefaultValue(
      MethodArgument argument, Map<String, ResourceName> resourceNames) {
    return AssignmentExpr.builder()
        .setVariableExpr(createVariableDeclExpr(argument.name(), argument.type()))
        .setValueExpr(DefaultValueComposer.createDefaultValue(argument, resourceNames))
        .build();
  }

  private static Expr createAssignExprForVariableWithClientMethod(
      String variableName,
      TypeNode variableType,
      TypeNode clientType,
      String methodName,
      List<MethodArgument> arguments) {
    VariableExpr varExpr = createVariableExpr(variableName, variableType);
    MethodInvocationExpr clientMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(createVariableExpr(getClientName(clientType), clientType))
            .setMethodName(methodName)
            .setArguments(mapMethodArgumentsToVariableExprs(arguments))
            .setReturnType(variableType)
            .build();
    return AssignmentExpr.builder()
        .setVariableExpr(varExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(clientMethodInvocationExpr)
        .build();
  }

  private static List<Expr> mapMethodArgumentsToVariableExprs(List<MethodArgument> arguments) {
    return arguments.stream()
        .map(arg -> createVariableExpr(arg.name(), arg.type()))
        .collect(Collectors.toList());
  }

  private static Expr createIteratorAllMethodExpr(
      Method method, TypeNode clientType, List<MethodArgument> arguments) {
    // e.g echoClient.echo(name).iterateAll()
    return MethodInvocationExpr.builder()
        .setExprReferenceExpr(
            MethodInvocationExpr.builder()
                .setExprReferenceExpr(createVariableExpr(getClientName(clientType), clientType))
                .setMethodName(method.name())
                .setArguments(
                    !arguments.isEmpty()
                        ? mapMethodArgumentsToVariableExprs(arguments)
                        : Arrays.asList(createVariableExpr("request", method.inputType())))
                .build())
        .setMethodName("iterateAll")
        .setReturnType(clientType)
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

  private static Expr createAssignStreamObserverResponseAnonymousClassExpr(Method method) {
    VariableExpr streamObserverResponseVarExpr = createStreamObserverVarExpr(method, true);
    MethodDefinition onNextMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setName("onNext")
            .setArguments(createVariableDeclExpr(RESPONSE_VAR_NAME, method.outputType()))
            .setBody(
                Arrays.asList(createLineCommentStatement("Do something when receive a response.")))
            .setReturnType(TypeNode.VOID)
            .build();
    MethodDefinition onErrorMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setName("onError")
            .setArguments(
                createVariableDeclExpr(
                    "t", TypeNode.withReference(ConcreteReference.withClazz(Throwable.class))))
            .setBody(Arrays.asList(createLineCommentStatement("Add error-handling")))
            .setReturnType(TypeNode.VOID)
            .build();
    MethodDefinition onCompletedMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setName("onCompleted")
            .setBody(Arrays.asList(createLineCommentStatement("Do something when complete.")))
            .setReturnType(TypeNode.VOID)
            .build();

    AnonymousClassExpr anonymousClassExpr =
        AnonymousClassExpr.builder()
            .setType(streamObserverResponseVarExpr.variable().type())
            .setMethods(onNextMethod, onErrorMethod, onCompletedMethod)
            .build();
    return AssignmentExpr.builder()
        .setVariableExpr(streamObserverResponseVarExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(anonymousClassExpr)
        .build();
  }

  private static Expr createAssignStreamObserverRequestExpr(Method method, TypeNode clientType) {
    VariableExpr streamObserverRequestExpr = createStreamObserverVarExpr(method, false);
    MethodInvocationExpr streamingCallMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(createVariableExpr(getClientName(clientType), clientType))
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

  private static String getClientName(TypeNode clientType) {
    return JavaStyle.toLowerCamelCase(clientType.reference().name());
  }

  private static String getLroMethodName(String methodName) {
    return JavaStyle.toLowerCamelCase(String.format(ASYNC_NAME_PATTERN, methodName));
  }

  private static String getObserverVariableName(String variableName) {
    return JavaStyle.toLowerCamelCase(String.format(OBSERVER_NAME_PATTERN, variableName));
  }

  private static String getCallableMethodName(String methodName) {
    return JavaStyle.toLowerCamelCase(String.format(UNARY_CALLABLE_NAME_PATTERN, methodName));
  }

  private static CommentStatement createLineCommentStatement(String content) {
    return CommentStatement.withComment(LineComment.withComment(content));
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
}
