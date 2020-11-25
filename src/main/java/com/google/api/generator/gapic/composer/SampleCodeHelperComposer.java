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
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class SampleCodeHelperComposer {
  private static String RESPONSE = "response";

  public static TryCatchStatement composeRpcMethodSampleCode(
      Method method,
      List<MethodArgument> arguments,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames) {
    Preconditions.checkState(
        !arguments.isEmpty(),
        "If method %s's arguments is none, it should pass %s as argument.",
        method.name(),
        method.inputType().reference().name());
    // Paged Unary RPC method.
    if (method.isPaged()) {
      return composePagedUnaryRpcMethodSampleCode(method, arguments, clientType, resourceNames);
    }
    // Long-running operation Unary RPC method.
    if (method.hasLro()) {
      return composeLroUnaryRpcMethodSampleCode(method, arguments, clientType, resourceNames);
    }
    // Pure Unary RPC method.
    return composeUnaryRpcMethodSampleCode(method, arguments, clientType, resourceNames);
  }

  public static TryCatchStatement composeRpcDefaultMethodSampleCode(
      Method method, TypeNode clientType, Map<String, ResourceName> resourceNames) {
    // TODO(summerji): compose sample code for unary default rpc method.
    VariableExpr clientVarExpr = createVariableExpr(getClientName(clientType), clientType);
    String content =
        String.format(
            "Note: Not implemented yet, placeholder for unary %s rpc method sample code.",
            (!method.hasLro() && !method.isPaged()
                ? "default"
                : (method.hasLro() ? "lro default" : "paged default")));
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
        .setTryBody(Arrays.asList(createLineCommentStatement(content)))
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composeUnaryRpcMethodSampleCode(
      Method method,
      List<MethodArgument> arguments,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames) {
    VariableExpr clientVarExpr = createVariableExpr(getClientName(clientType), clientType);
    // Assign each method arguments with its default value.
    Map<String, VariableExpr> methodArgVarExprMap = mapMethodArgumentsToVariableExprs(arguments);
    List<Expr> methodArgumentsAssignmentExpr =
        assignMethodArgumentsWithDefaultValues(arguments, methodArgVarExprMap, resourceNames);
    List<Expr> methodVarExprs =
        arguments.stream()
            .map(arg -> methodArgVarExprMap.get(arg.name()))
            .collect(Collectors.toList());
    // Invoke current method based on return type.
    // e.g. if return void, echoClient.echo(..); or,
    // e.g. if return other type, EchoResponse response = echoClient.echo(...);
    boolean returnsVoid = isProtoEmptyType(method.outputType());
    Expr responseExpr = null;
    if (returnsVoid) {
      responseExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(clientVarExpr)
              .setMethodName(JavaStyle.toLowerCamelCase(method.name()))
              .setArguments(methodVarExprs)
              .setReturnType(clientType)
              .build();
    } else {
      responseExpr =
          createAssignExprForVariableWithClientMethod(
              createVariableExpr(RESPONSE, method.outputType()),
              clientVarExpr,
              JavaStyle.toLowerCamelCase(method.name()),
              methodVarExprs);
    }

    List<Expr> bodyExpr = new ArrayList<>();
    bodyExpr.addAll(methodArgumentsAssignmentExpr);
    bodyExpr.add(responseExpr);

    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
        .setTryBody(
            bodyExpr.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()))
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
    VariableExpr clientVarExpr = createVariableExpr(getClientName(clientType), clientType);
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
        .setTryBody(
            Arrays.asList(
                createLineCommentStatement(
                    "Note: Not implemented yet, placeholder for lro Unary rpc method sample code.")))
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composePagedUnaryRpcMethodSampleCode(
      Method method,
      List<MethodArgument> arguments,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames) {
    // TODO(summerji): compose sample code for unary paged rpc method.
    // TODO(summerji): Add unit tests.
    VariableExpr clientVarExpr = createVariableExpr(getClientName(clientType), clientType);
    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
        .setTryBody(
            Arrays.asList(
                createLineCommentStatement(
                    "Note: Not implemented yet, placeholder for paged unary rpc method sample code.")))
        .setIsSampleCode(true)
        .build();
  }
  // ==================================Helpers===================================================//

  // Assign client variable expr with create client.
  // e.g EchoClient echoClient = EchoClient.create()
  private static AssignmentExpr assignClientVariableWithCreateMethodExpr(
      VariableExpr clientVarExpr) {
    return AssignmentExpr.builder()
        .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(
            MethodInvocationExpr.builder()
                .setStaticReferenceType(clientVarExpr.variable().type())
                .setReturnType(clientVarExpr.variable().type())
                .setMethodName("create")
                .build())
        .build();
  }

  private static List<Expr> assignMethodArgumentsWithDefaultValues(
      List<MethodArgument> arguments,
      Map<String, VariableExpr> argVarExprs,
      Map<String, ResourceName> resourceNames) {
    return arguments.stream()
        .map(
            arg ->
                createAssignmentExpr(
                    argVarExprs.get(arg.name()),
                    DefaultValueComposer.createDefaultValue(arg, resourceNames)))
        .collect(Collectors.toList());
  }

  private static AssignmentExpr createAssignmentExpr(VariableExpr variableExpr, Expr valueExpr) {
    return AssignmentExpr.builder()
        .setVariableExpr(variableExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(valueExpr)
        .build();
  }

  private static Expr createAssignExprForVariableWithClientMethod(
      VariableExpr variableExpr,
      VariableExpr clientVarExpr,
      String methodName,
      List<Expr> argumentsVarExprs) {
    MethodInvocationExpr clientMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(JavaStyle.toLowerCamelCase(methodName))
            .setArguments(argumentsVarExprs)
            .setReturnType(variableExpr.variable().type())
            .build();
    return AssignmentExpr.builder()
        .setVariableExpr(variableExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(clientMethodInvocationExpr)
        .build();
  }

  private static Map<String, VariableExpr> mapMethodArgumentsToVariableExprs(
      List<MethodArgument> arguments) {
    return arguments.stream()
        .collect(
            Collectors.toMap(
                methodArg -> methodArg.name(),
                methodArg ->
                    createVariableExpr(
                        JavaStyle.toLowerCamelCase(methodArg.name()), methodArg.type())));
  }

  private static String getClientName(TypeNode clientType) {
    return JavaStyle.toLowerCamelCase(clientType.reference().name());
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
        .setVariable(Variable.builder().setName(variableName).setType(type).build())
        .setIsDecl(isDecl)
        .build();
  }

  private static boolean isProtoEmptyType(TypeNode type) {
    return type.reference().pakkage().equals("com.google.protobuf")
        && type.reference().name().equals("Empty");
  }
}
