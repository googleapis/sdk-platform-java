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
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.utils.JavaStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MethodSampleCodeHelperComposer {
  private static String RESPONSE = "response";

  public static TryCatchStatement composeUnaryRpcMethodSampleCode(
      Method method,
      List<MethodArgument> arguments,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames) {
    VariableExpr clientVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName(JavaStyle.toLowerCamelCase(clientType.reference().name()))
                .setType(clientType)
                .build());
    // Assign each method arguments with its default value.
    List<Expr> methodArgExprs = createMethodArgVarExprs(arguments, resourceNames);
    List<Expr> bodyExpr =
        assignMethodArgumentsWithDefaultValues(arguments, methodArgExprs, resourceNames);
    // Invoke current method based on return type.
    // e.g. if return void, echoClient.echo(..); or,
    // e.g. if return other type, EchoResponse response = echoClient.echo(...);
    boolean returnsVoid = isProtoEmptyType(method.outputType());
    if (returnsVoid) {
      bodyExpr.add(
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(clientVarExpr)
              .setMethodName(JavaStyle.toLowerCamelCase(method.name()))
              .setArguments(methodArgExprs)
              .setReturnType(clientType)
              .build());
    } else {
      VariableExpr responseVarExpr =
          VariableExpr.withVariable(
              Variable.builder().setName(RESPONSE).setType(method.outputType()).build());
      bodyExpr.add(
          createAssignExprForVariableWithClientMethod(
              responseVarExpr,
              clientVarExpr,
              JavaStyle.toLowerCamelCase(method.name()),
              methodArgExprs));
    }

    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
        .setTryBody(
            bodyExpr.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()))
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

  // Create a list of Expr for method argument variable expression.
  private static List<Expr> createMethodArgVarExprs(
      List<MethodArgument> arguments, Map<String, ResourceName> resourceNames) {
    return arguments.stream()
        .map(
            arg -> {
              VariableExpr argVarExpr =
                  VariableExpr.withVariable(
                      Variable.builder()
                          .setName(JavaStyle.toLowerCamelCase(arg.name()))
                          .setType(arg.type())
                          .build());
              if (arg.type().equals(TypeNode.STRING)
                  && arg.field().hasResourceReference()
                  && resourceNames.containsKey(
                      arg.field().resourceReference().resourceTypeString())) {
                return MethodInvocationExpr.builder()
                    .setExprReferenceExpr(argVarExpr)
                    .setMethodName("toString")
                    .build();
              }
              return argVarExpr;
            })
        .collect(Collectors.toList());
  }

  // Return a list of AssignmentExpr for method argument with its default value.
  private static List<Expr> assignMethodArgumentsWithDefaultValues(
      List<MethodArgument> arguments,
      List<Expr> argVarExprs,
      Map<String, ResourceName> resourceNames) {
    List<ResourceName> resourceNameList =
        resourceNames.values().stream().collect(Collectors.toList());
    List<Expr> assignmentExprs = new ArrayList<>();
    for (int i = 0; i < arguments.size(); i++) {
      MethodArgument arg = arguments.get(i);
      VariableExpr argVarExpr =
          (argVarExprs.get(i) instanceof VariableExpr) ? (VariableExpr) argVarExprs.get(i) : null;
      Expr defaultValueExpr = DefaultValueComposer.createDefaultValue(arg, resourceNames);
      if (arg.type().equals(TypeNode.STRING)
          && arg.field().hasResourceReference()
          && resourceNames.containsKey(arg.field().resourceReference().resourceTypeString())) {
        ResourceName resourceName =
            resourceNames.get(arg.field().resourceReference().resourceTypeString());
        defaultValueExpr =
            DefaultValueComposer.createDefaultValue(
                resourceName, resourceNameList, arg.field().name());
        TypeNode resourceReferenceType =
            arg.field().resourceReference().isChildType()
                ? TypeNode.withReference(
                    ConcreteReference.withClazz(com.google.api.resourcenames.ResourceName.class))
                : defaultValueExpr.type();
        argVarExpr =
            VariableExpr.withVariable(
                Variable.builder()
                    .setName(JavaStyle.toLowerCamelCase(arg.name()))
                    .setType(resourceReferenceType)
                    .build());
      }
      assignmentExprs.add(
          AssignmentExpr.builder()
              .setVariableExpr(argVarExpr.toBuilder().setIsDecl(true).build())
              .setValueExpr(defaultValueExpr)
              .build());
    }
    return assignmentExprs;
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

  private static String getClientName(TypeNode clientType) {
    return JavaStyle.toLowerCamelCase(clientType.reference().name());
  }

  private static boolean isProtoEmptyType(TypeNode type) {
    return type.reference().pakkage().equals("com.google.protobuf")
        && type.reference().name().equals("Empty");
  }
}
