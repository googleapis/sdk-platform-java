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

package com.google.api.generator.gapic.composer.samplecode;

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
import com.google.api.generator.gapic.composer.defaultvalue.DefaultValueComposer;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.Message;
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

public class ServiceClientMethodSampleComposer {
  public static String composeRpcMethodHeaderSampleCode(
      Method method,
      TypeNode clientType,
      List<MethodArgument> arguments,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes) {
    VariableExpr clientVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName(JavaStyle.toLowerCamelCase(clientType.reference().name()))
                .setType(clientType)
                .build());

    // Assign method's arguments variable with the default values.
    List<VariableExpr> rpcMethodArgVarExprs =
        ServiceClientSampleUtil.createRpcMethodArgumentVariableExprs(arguments);
    List<Expr> rpcMethodArgDefaultValueExprs =
        ServiceClientSampleUtil.createRpcMethodArgumentDefaultValueExprs(arguments, resourceNames);
    List<Expr> rpcMethodArgAssignmentExprs =
        ServiceClientSampleUtil.createAssignmentsForVarExprsWithValueExprs(
            rpcMethodArgVarExprs, rpcMethodArgDefaultValueExprs);

    List<Expr> bodyExprs = new ArrayList<>();
    bodyExprs.addAll(rpcMethodArgAssignmentExprs);

    List<Statement> bodyStatements = new ArrayList<>();
    if (method.isPaged()) {
      bodyStatements.addAll(
          composeUnaryPagedRpcMethodBodyStatements(
              method, clientVarExpr, rpcMethodArgVarExprs, bodyExprs, messageTypes));
    } else if (method.hasLro()) {
      bodyStatements.addAll(
          composeUnaryLroRpcMethodBodyStatements(
              method, clientVarExpr, rpcMethodArgVarExprs, bodyExprs));
    } else {
      bodyStatements.addAll(
          composeUnaryRpcMethodBodyStatements(
              method, clientVarExpr, rpcMethodArgVarExprs, bodyExprs));
    }

    return SampleCodeWriter.write(
        TryCatchStatement.builder()
            .setTryResourceExpr(
                ServiceClientSampleUtil.assignClientVariableWithCreateMethodExpr(clientVarExpr))
            .setTryBody(bodyStatements)
            .setIsSampleCode(true)
            .build());
  }

  public static String composeRpcDefaultMethodHeaderSampleCode(
      Method method,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes) {
    VariableExpr clientVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName(JavaStyle.toLowerCamelCase(clientType.reference().name()))
                .setType(clientType)
                .build());

    // Create request variable expression and assign with its default value.
    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("request").setType(method.inputType()).build());
    List<VariableExpr> rpcMethodArgVarExprs = Arrays.asList(requestVarExpr);
    Message requestMessage = messageTypes.get(method.inputType().reference().fullName());
    Preconditions.checkNotNull(
        requestMessage,
        String.format(
            "Could not find the message type %s.", method.inputType().reference().fullName()));
    Expr requestBuilderExpr =
        DefaultValueComposer.createSimpleMessageBuilderValue(
            requestMessage, resourceNames, messageTypes);
    AssignmentExpr requestAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(requestVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(requestBuilderExpr)
            .build();

    List<Expr> bodyExprs = new ArrayList<>();
    bodyExprs.add(requestAssignmentExpr);

    List<Statement> bodyStatements = new ArrayList<>();
    if (method.isPaged()) {
      bodyStatements.addAll(
          composeUnaryPagedRpcMethodBodyStatements(
              method, clientVarExpr, rpcMethodArgVarExprs, bodyExprs, messageTypes));
    } else if (method.hasLro()) {
      bodyStatements.addAll(
          composeUnaryLroRpcMethodBodyStatements(
              method, clientVarExpr, rpcMethodArgVarExprs, bodyExprs));
    } else {
      bodyStatements.addAll(
          composeUnaryRpcMethodBodyStatements(
              method, clientVarExpr, rpcMethodArgVarExprs, bodyExprs));
    }

    return SampleCodeWriter.write(
        TryCatchStatement.builder()
            .setTryResourceExpr(
                ServiceClientSampleUtil.assignClientVariableWithCreateMethodExpr(clientVarExpr))
            .setTryBody(bodyStatements)
            .setIsSampleCode(true)
            .build());
  }

  private static List<Statement> composeUnaryRpcMethodBodyStatements(
      Method method,
      VariableExpr clientVarExpr,
      List<VariableExpr> rpcMethodArgVarExprs,
      List<Expr> bodyExprs) {

    // Invoke current method based on return type.
    // e.g. if return void, echoClient.echo(..); or,
    // e.g. if return other type, EchoResponse response = echoClient.echo(...);
    boolean returnsVoid = ServiceClientSampleUtil.isProtoEmptyType(method.outputType());
    MethodInvocationExpr clientRpcMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(JavaStyle.toLowerCamelCase(method.name()))
            .setArguments(
                rpcMethodArgVarExprs.stream().map(e -> (Expr) e).collect(Collectors.toList()))
            .setReturnType(method.outputType())
            .build();
    if (returnsVoid) {
      bodyExprs.add(clientRpcMethodInvocationExpr);
    } else {
      VariableExpr responseVarExpr =
          VariableExpr.withVariable(
              Variable.builder().setName("response").setType(method.outputType()).build());
      bodyExprs.add(
          AssignmentExpr.builder()
              .setVariableExpr(responseVarExpr.toBuilder().setIsDecl(true).build())
              .setValueExpr(clientRpcMethodInvocationExpr)
              .build());
    }

    return bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());
  }

  private static List<Statement> composeUnaryPagedRpcMethodBodyStatements(
      Method method,
      VariableExpr clientVarExpr,
      List<VariableExpr> rpcMethodArgVarExprs,
      List<Expr> bodyExprs,
      Map<String, Message> messageTypes) {

    // Find the repeated field.
    Message methodOutputMessage = messageTypes.get(method.outputType().reference().fullName());
    Preconditions.checkNotNull(
        methodOutputMessage,
        "Output message %s not found, keys: ",
        method.outputType().reference().fullName(),
        messageTypes.keySet().toString());
    Field repeatedPagedResultsField = methodOutputMessage.findAndUnwrapPaginatedRepeatedField();
    Preconditions.checkNotNull(
        repeatedPagedResultsField,
        String.format(
            "No repeated field found on message %s for method %s",
            methodOutputMessage.name(), method.name()));
    TypeNode repeatedResponseType = repeatedPagedResultsField.type();

    // For loop paged response item on iterateAll method.
    // e.g. for (LogEntry element : loggingServiceV2Client.ListLogs(parent).iterateAll()) {
    //          //doThingsWith(element);
    //      }
    MethodInvocationExpr clientMethodIterateAllExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(JavaStyle.toLowerCamelCase(method.name()))
            .setArguments(
                rpcMethodArgVarExprs.stream().map(e -> (Expr) e).collect(Collectors.toList()))
            .build();
    clientMethodIterateAllExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientMethodIterateAllExpr)
            .setMethodName("iterateAll")
            .setReturnType(repeatedResponseType)
            .build();
    ForStatement loopIteratorStatement =
        ForStatement.builder()
            .setLocalVariableExpr(
                VariableExpr.builder()
                    .setVariable(
                        Variable.builder().setName("element").setType(repeatedResponseType).build())
                    .setIsDecl(true)
                    .build())
            .setCollectionExpr(clientMethodIterateAllExpr)
            .setBody(
                Arrays.asList(
                    CommentStatement.withComment(
                        LineComment.withComment("doThingsWith(element);"))))
            .build();

    List<Statement> bodyStatements =
        bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());
    bodyExprs.clear();
    bodyStatements.add(loopIteratorStatement);

    return bodyStatements;
  }

  private static List<Statement> composeUnaryLroRpcMethodBodyStatements(
      Method method,
      VariableExpr clientVarExpr,
      List<VariableExpr> rpcMethodArgVarExprs,
      List<Expr> bodyExprs) {
    // Assign response variable with invoking client's LRO method.
    // e.g. if return void, echoClient.waitAsync(ttl).get(); or,
    // e.g. if return other type, WaitResponse response = echoClient.waitAsync(ttl).get();
    Expr invokeLroGetMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(String.format("%sAsync", JavaStyle.toLowerCamelCase(method.name())))
            .setArguments(
                rpcMethodArgVarExprs.stream().map(e -> (Expr) e).collect(Collectors.toList()))
            .build();
    invokeLroGetMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(invokeLroGetMethodExpr)
            .setMethodName("get")
            .setReturnType(method.lro().responseType())
            .build();
    boolean returnsVoid = ServiceClientSampleUtil.isProtoEmptyType(method.lro().responseType());
    if (returnsVoid) {
      bodyExprs.add(invokeLroGetMethodExpr);
    } else {
      VariableExpr responseVarExpr =
          VariableExpr.builder()
              .setVariable(
                  Variable.builder()
                      .setName("response")
                      .setType(method.lro().responseType())
                      .build())
              .setIsDecl(true)
              .build();
      bodyExprs.add(
          AssignmentExpr.builder()
              .setVariableExpr(responseVarExpr)
              .setValueExpr(invokeLroGetMethodExpr)
              .build());
    }

    return bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());
  }
}
