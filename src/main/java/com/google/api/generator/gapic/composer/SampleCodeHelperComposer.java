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
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.util.Arrays;
import java.util.List;

public final class SampleCodeHelperComposer {

  public static TryCatchStatement composeRpcMethodSampleCode(
      Method method, List<MethodArgument> arguments, TypeNode clientType) {
    // Default Unary RPC method.
    if (arguments.isEmpty()) {
      return composeUnaryRpcDefaultMethodSampleCode(method, clientType);
    }
    // Paged Unary RPC method.
    if (method.isPaged()) {
      return composePagedUnaryRpcMethodSampleCode(method, arguments, clientType);
    }
    // Long-running operation Unary RPC method.
    if (method.hasLro()) {
      return composeLroUnaryRpcMethodSampleCode(method, arguments, clientType);
    }
    // Pure Unary RPC method.
    return composeUnaryRpcMethodSampleCode(method, arguments, clientType);
  }

  private static TryCatchStatement composeUnaryRpcMethodSampleCode(
      Method method, List<MethodArgument> arguments, TypeNode clientType) {
    // TODO(summerji): compose sample code for unary rpc method.
    return TryCatchStatement.builder()
        .setTryResourceExpr(
            assignClientVariableWithCreateMethodExpr(
                createVariableExpr(getClientName(clientType), clientType)))
        .setTryBody(
            Arrays.asList(
                createLineCommentStatement(
                    "Note: Not Implement yet, placeholder for pure unary rpc method sample code.")))
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composeLroUnaryRpcMethodSampleCode(
      Method method, List<MethodArgument> arguments, TypeNode clientType) {
    // TODO(summerji): compose sample code for unary lro rpc method.
    return TryCatchStatement.builder()
        .setTryResourceExpr(
            assignClientVariableWithCreateMethodExpr(
                createVariableExpr(getClientName(clientType), clientType)))
        .setTryBody(
            Arrays.asList(
                createLineCommentStatement(
                    "Note: Not Implement yet, placeholder for lro Unary rpc method sample code.")))
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composePagedUnaryRpcMethodSampleCode(
      Method method, List<MethodArgument> arguments, TypeNode clientType) {
    // TODO(summerji): compose sample code for unary paged rpc method.
    return TryCatchStatement.builder()
        .setTryResourceExpr(
            assignClientVariableWithCreateMethodExpr(
                createVariableExpr(getClientName(clientType), clientType)))
        .setTryBody(
            Arrays.asList(
                createLineCommentStatement(
                    "Note: Not Implement yet, placeholder for paged unary rpc method sample code.")))
        .setIsSampleCode(true)
        .build();
  }

  private static TryCatchStatement composeUnaryRpcDefaultMethodSampleCode(
      Method method, TypeNode clientType) {
    // TODO(summerji): compose sample code for unary default rpc method.
    String content =
        String.format(
            "Note: Not Implement yet, placeholder for unary %s rpc method sample code.",
            (!method.hasLro() && !method.isPaged()
                ? "default"
                : (method.hasLro() ? "lro default" : "paged default")));
    return TryCatchStatement.builder()
        .setTryResourceExpr(
            assignClientVariableWithCreateMethodExpr(
                createVariableExpr(getClientName(clientType), clientType)))
        .setTryBody(Arrays.asList(createLineCommentStatement(content)))
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
}
