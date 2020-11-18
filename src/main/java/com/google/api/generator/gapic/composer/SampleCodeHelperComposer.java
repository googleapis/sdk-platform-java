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

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.ForStatement;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class SampleCodeHelperComposer {
  private static String RESPONSE_VAR_NAME = "response";
  private static String REQUEST_VAR_NAME = "request";
  private static String ASYNC_NAME_PATTERN = "%sAsync";

  public static TryCatchStatement composeRpcMethodSampleCode(
      Method method,
      List<MethodArgument> arguments,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames) {
    // Default Unary RPC method.
    if (arguments.isEmpty()) {
      return composeUnaryRpcDefaultMethodSampleCode(method, clientType);
    }
    // Paged Unary RPC method.
    if (method.isPaged()) {
      return composePagedUnaryRpcMethodSampleCode(method, arguments, clientType);
    }
    // Long run operation Unary RPC method.
    if (method.hasLro()) {
      return composeLroUnaryRpcMethodSampleCode(method, arguments, clientType);
    }
    // Pure Unary RPC method.
    return composeUnaryRpcMethodSampleCode(method, arguments, clientType, resourceNames);
  }

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
      Method method, List<MethodArgument> arguments, TypeNode clientType) {
    // TODO(summerji): compose sample code for unary lro rpc method.
    // TODO(summerji): Add unit tests.
    // Assign each method arguments with default value.
    List<Statement> bodyStatements =
        arguments.stream()
            .map(
                methodArg ->
                    ExprStatement.withExpr(assignMethodArgumentWithDefaultValue(methodArg)))
            .collect(Collectors.toList());
    // Assign request with set attributes.
    // e.g. EchoRequest request = echoClient.newBuilder().setName(name).build();
    bodyStatements.add(
        ExprStatement.withExpr(createRequestBuilderExpr(method.inputType(), arguments)));
    // Assign response variable with get method.
    // e.g EchoResponse response = echoClient.waitAsync().get();
    Expr getResponseMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(clientType)
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
      Method method, List<MethodArgument> arguments, TypeNode clientType) {
    // TODO(summerji): Add unit test.
    // Assign each method arguments with default value.
    List<Statement> bodyStatements =
        arguments.stream()
            .map(
                methodArg ->
                    ExprStatement.withExpr(assignMethodArgumentWithDefaultValue(methodArg)))
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
      Method method, TypeNode clientType) {
    // TODO(summerji): compose sample code for unary default rpc method.
    // TODO(summerji): Add unit tests.
    String content =
        String.format(
            "Note: Not Implement yet, placeholder for unary %s rpc method sample code.",
            (!method.hasLro() && !method.isPaged()
                ? "default"
                : (method.hasLro() ? "lro default" : "paged default")));
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientType))
        .setTryBody(Arrays.asList(createLineCommentStatement(content)))
        .setIsSampleCode(true)
        .build();
  }

  // ==================================Helpers===================================================//

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

  private static Expr assignMethodArgumentWithDefaultValue(MethodArgument argument) {
    return AssignmentExpr.builder()
        .setVariableExpr(createVariableDeclExpr(argument.name(), argument.field().type()))
        .setValueExpr(DefaultValueComposer.createDefaultValue(argument.field()))
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

  private static String getClientName(TypeNode clientType) {
    return JavaStyle.toLowerCamelCase(clientType.reference().name());
  }

  private static String getLroMethodName(String methodName) {
    return JavaStyle.toLowerCamelCase(String.format(ASYNC_NAME_PATTERN, methodName));
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
