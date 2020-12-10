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

package com.google.api.generator.gapic.composer.samplecode;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.ForStatement;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.samplecode.SampleCodeWriter;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceClientSampleCodeComposer {

  public static String composeClassHeaderCredentialsSampleCode(
      // TODO(summerji): Add unit tests for composeClassHeaderCredentialsSampleCode.
      TypeNode clientType, TypeNode settingsType) {
    // Initialize clientSettings with builder() method.
    // e.g. EchoSettings echoSettings =
    // EchoSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create("myCredentials")).build();
    String settingsName = JavaStyle.toLowerCamelCase(settingsType.reference().name());
    String clientName = JavaStyle.toLowerCamelCase(clientType.reference().name());
    TypeNode myCredentialsType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("myCredentials")
                .setPakkage(clientType.reference().pakkage())
                .build());
    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName(settingsName).setType(settingsType).build());
    MethodInvocationExpr newBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(settingsType)
            .setMethodName("newBuilder")
            .build();
    TypeNode fixedCredentialProvideType =
        TypeNode.withReference(ConcreteReference.withClazz(FixedCredentialsProvider.class));
    MethodInvocationExpr credentialArgExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(fixedCredentialProvideType)
            .setArguments(
                VariableExpr.withVariable(
                    Variable.builder().setName("myCredentials").setType(myCredentialsType).build()))
            .setMethodName("create")
            .build();
    MethodInvocationExpr credentialsMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(newBuilderMethodExpr)
            .setArguments(credentialArgExpr)
            .setMethodName("setCredentialsProvider")
            .build();
    MethodInvocationExpr buildMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(credentialsMethodExpr)
            .setReturnType(settingsType)
            .setMethodName("build")
            .build();
    Expr initSettingsVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(buildMethodExpr)
            .build();

    // Initialized client with create() method.
    // e.g. EchoClient echoClient = EchoClient.create(echoSettings);
    VariableExpr clientVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName(clientName).setType(clientType).build());
    MethodInvocationExpr createMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(clientType)
            .setArguments(settingsVarExpr)
            .setMethodName("create")
            .setReturnType(clientType)
            .build();
    Expr initClientVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(createMethodExpr)
            .build();
    return SampleCodeWriter.write(
        Arrays.asList(
            ExprStatement.withExpr(initSettingsVarExpr),
            ExprStatement.withExpr(initClientVarExpr)));
  }

  public static String composeClassHeaderEndpointSampleCode(
      // TODO(summerji): Add unit tests for composeClassHeaderEndpointSampleCode.
      TypeNode clientType, TypeNode settingsType) {
    // Initialize client settings with builder() method.
    // e.g. EchoSettings echoSettings = EchoSettings.newBuilder().setEndpoint("myEndpoint").build();
    String settingsName = JavaStyle.toLowerCamelCase(settingsType.reference().name());
    String clientName = JavaStyle.toLowerCamelCase(clientType.reference().name());
    TypeNode myEndpointType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("myEndpoint")
                .setPakkage(clientType.reference().pakkage())
                .build());
    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName(settingsName).setType(settingsType).build());
    MethodInvocationExpr newBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(settingsType)
            .setMethodName("newBuilder")
            .build();
    MethodInvocationExpr credentialsMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(newBuilderMethodExpr)
            .setArguments(
                VariableExpr.withVariable(
                    Variable.builder().setName("myEndpoint").setType(myEndpointType).build()))
            .setMethodName("setEndpoint")
            .build();
    MethodInvocationExpr buildMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(credentialsMethodExpr)
            .setReturnType(settingsType)
            .setMethodName("build")
            .build();

    Expr initSettingsVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(settingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(buildMethodExpr)
            .build();

    // Initialize client with create() method.
    // e.g. EchoClient echoClient = EchoClient.create(echoSettings);
    VariableExpr clientVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName(clientName).setType(clientType).build());
    MethodInvocationExpr createMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(clientType)
            .setArguments(settingsVarExpr)
            .setMethodName("create")
            .setReturnType(clientType)
            .build();
    Expr initClientVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(createMethodExpr)
            .build();

    return SampleCodeWriter.write(
        Arrays.asList(
            ExprStatement.withExpr(initSettingsVarExpr),
            ExprStatement.withExpr(initClientVarExpr)));
  }

  public static String composeRpcMethodHeaderSampleCode(
      Method method,
      TypeNode clientType,
      List<MethodArgument> arguments,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes) {
    // TODO(summerji): Add other types RPC methods' sample code.
    if (method.isPaged()) {
      // Find the repeated field.
      Message methodOutputMessage = messageTypes.get(method.outputType().reference().simpleName());
      Field repeatedPagedResultsField = methodOutputMessage.findAndUnwrapFirstRepeatedField();
      Preconditions.checkNotNull(
          repeatedPagedResultsField,
          String.format(
              "No repeated field found on message %s for method %s",
              methodOutputMessage.name(), method.name()));

      TypeNode repeatedResponseType = repeatedPagedResultsField.type();
      return SampleCodeWriter.write(
          composeUnaryPagedRpcMethodSampleCode(
              method, clientType, repeatedResponseType, arguments, resourceNames));
    }
    if (method.hasLro()) {
      return SampleCodeWriter.write(
          composeUnaryLroRpcMethodSampleCode(method, clientType, arguments, resourceNames));
    }
    return SampleCodeWriter.write(
        composeUnaryRpcMethodSampleCode(method, clientType, arguments, resourceNames));
  }

  @VisibleForTesting
  static TryCatchStatement composeUnaryRpcMethodSampleCode(
      Method method,
      TypeNode clientType,
      List<MethodArgument> arguments,
      Map<String, ResourceName> resourceNames) {
    VariableExpr clientVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName(JavaStyle.toLowerCamelCase(clientType.reference().name()))
                .setType(clientType)
                .build());
    List<VariableExpr> rpcMethodArgVarExprs = createRpcMethodArgumentVariableExprs(arguments);
    List<Expr> rpcMethodArgDefaultValueExprs =
        createRpcMethodArgumentDefaultValueExprs(arguments, resourceNames);
    List<Expr> bodyExprs =
        createAssignmentsForVarExprsWithValueExprs(
            rpcMethodArgVarExprs, rpcMethodArgDefaultValueExprs);
    // Invoke current method based on return type.
    // e.g. if return void, echoClient.echo(..); or,
    // e.g. if return other type, EchoResponse response = echoClient.echo(...);
    boolean returnsVoid = isProtoEmptyType(method.outputType());
    if (returnsVoid) {
      bodyExprs.add(
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(clientVarExpr)
              .setMethodName(JavaStyle.toLowerCamelCase(method.name()))
              .setArguments(
                  rpcMethodArgVarExprs.stream().map(e -> (Expr) e).collect(Collectors.toList()))
              .setReturnType(clientType)
              .build());
    } else {
      VariableExpr responseVarExpr =
          VariableExpr.withVariable(
              Variable.builder().setName("response").setType(method.outputType()).build());
      MethodInvocationExpr clientMethodInvocationExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(clientVarExpr)
              .setMethodName(JavaStyle.toLowerCamelCase(method.name()))
              .setArguments(
                  rpcMethodArgVarExprs.stream().map(e -> (Expr) e).collect(Collectors.toList()))
              .setReturnType(responseVarExpr.variable().type())
              .build();
      bodyExprs.add(
          AssignmentExpr.builder()
              .setVariableExpr(responseVarExpr.toBuilder().setIsDecl(true).build())
              .setValueExpr(clientMethodInvocationExpr)
              .build());
    }

    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
        .setTryBody(
            bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()))
        .setIsSampleCode(true)
        .build();
  }

  @VisibleForTesting
  static TryCatchStatement composeUnaryPagedRpcMethodSampleCode(
      Method method,
      TypeNode clientType,
      TypeNode repeatedResponseType,
      List<MethodArgument> arguments,
      Map<String, ResourceName> resourceNames) {
    VariableExpr clientVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName(JavaStyle.toLowerCamelCase(clientType.reference().name()))
                .setType(clientType)
                .build());
    List<VariableExpr> rpcMethodArgVarExprs = createRpcMethodArgumentVariableExprs(arguments);
    List<Expr> rpcMethodArgDefaultValueExprs =
        createRpcMethodArgumentDefaultValueExprs(arguments, resourceNames);
    List<Expr> bodyExprs =
        createAssignmentsForVarExprsWithValueExprs(
            rpcMethodArgVarExprs, rpcMethodArgDefaultValueExprs);
    // For loop paged response item on iterateAll method.
    // e.g. for (LogEntry element : loggingServiceV2Client.ListLogs(parent).iterateAll()) {
    //          //doThingsWith(element);
    //      }
    MethodInvocationExpr clientMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(JavaStyle.toLowerCamelCase(method.name()))
            .setArguments(
                rpcMethodArgVarExprs.stream().map(e -> (Expr) e).collect(Collectors.toList()))
            .build();
    Expr clientMethodIteratorAllExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientMethodExpr)
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
            .setCollectionExpr(clientMethodIteratorAllExpr)
            .setBody(
                Arrays.asList(
                    CommentStatement.withComment(
                        LineComment.withComment("doThingsWith(element);"))))
            .build();

    List<Statement> bodyStatements =
        bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());
    bodyStatements.add(loopIteratorStatement);

    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
        .setTryBody(bodyStatements)
        .setIsSampleCode(true)
        .build();
  }

  @VisibleForTesting
  static TryCatchStatement composeUnaryLroRpcMethodSampleCode(
      Method method,
      TypeNode clientType,
      List<MethodArgument> arguments,
      Map<String, ResourceName> resourceNames) {
    VariableExpr clientVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName(JavaStyle.toLowerCamelCase(clientType.reference().name()))
                .setType(clientType)
                .build());
    List<VariableExpr> rpcMethodArgVarExprs = createRpcMethodArgumentVariableExprs(arguments);
    List<Expr> rpcMethodArgDefaultValueExprs =
        createRpcMethodArgumentDefaultValueExprs(arguments, resourceNames);
    List<Expr> bodyExprs =
        createAssignmentsForVarExprsWithValueExprs(
            rpcMethodArgVarExprs, rpcMethodArgDefaultValueExprs);
    // Assign response variable with invoking client's lro method.
    // e.g. if return void, echoClient.waitAsync(ttl).get(); or,
    // e.g. if return other type, WaitResponse response = echoClient.waitAsync(ttl).get();
    Expr invokeLroMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(String.format("%sAsync", JavaStyle.toLowerCamelCase(method.name())))
            .setArguments(
                rpcMethodArgVarExprs.stream().map(e -> (Expr) e).collect(Collectors.toList()))
            .build();
    Expr getResponseMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(invokeLroMethodExpr)
            .setMethodName("get")
            .setReturnType(method.lro().responseType())
            .build();
    boolean returnsVoid = isProtoEmptyType(method.lro().responseType());
    if (returnsVoid) {
      bodyExprs.add(getResponseMethodExpr);
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
              .setValueExpr(getResponseMethodExpr)
              .build());
    }

    return TryCatchStatement.builder()
        .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
        .setTryBody(
            bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()))
        .setIsSampleCode(true)
        .build();
  }

  // ==================================Helpers===================================================//

  // Create a list of RPC method arguments' variable expressions.
  private static List<VariableExpr> createRpcMethodArgumentVariableExprs(
      List<MethodArgument> arguments) {
    return arguments.stream()
        .map(
            arg ->
                VariableExpr.withVariable(
                    Variable.builder()
                        .setName(JavaStyle.toLowerCamelCase(arg.name()))
                        .setType(arg.type())
                        .build()))
        .collect(Collectors.toList());
  }

  // Create a list of RPC method arguments' default value expression.
  private static List<Expr> createRpcMethodArgumentDefaultValueExprs(
      List<MethodArgument> arguments, Map<String, ResourceName> resourceNames) {
    List<ResourceName> resourceNameList =
        resourceNames.values().stream().collect(Collectors.toList());
    Function<MethodArgument, MethodInvocationExpr> stringResourceNameDefaultValueExpr =
        arg ->
            MethodInvocationExpr.builder()
                .setExprReferenceExpr(
                    DefaultValueComposer.createDefaultValue(
                        resourceNames.get(arg.field().resourceReference().resourceTypeString()),
                        resourceNameList,
                        arg.field().name()))
                .setMethodName("toString")
                .setReturnType(TypeNode.STRING)
                .build();
    return arguments.stream()
        .map(
            arg ->
                !isStringTypedResourceName(arg, resourceNames)
                    ? DefaultValueComposer.createDefaultValue(arg, resourceNames)
                    : stringResourceNameDefaultValueExpr.apply(arg))
        .collect(Collectors.toList());
  }

  // Create a list of assignment expressions for variable expr with value expr.
  private static List<Expr> createAssignmentsForVarExprsWithValueExprs(
      List<VariableExpr> variableExprs, List<Expr> valueExprs) {
    Preconditions.checkState(
        variableExprs.size() == valueExprs.size(),
        "Expected the number of method arguments to match the number of default values.");
    return IntStream.range(0, variableExprs.size())
        .mapToObj(
            i ->
                AssignmentExpr.builder()
                    .setVariableExpr(variableExprs.get(i).toBuilder().setIsDecl(true).build())
                    .setValueExpr(valueExprs.get(i))
                    .build())
        .collect(Collectors.toList());
  }

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

  private static boolean isStringTypedResourceName(
      MethodArgument arg, Map<String, ResourceName> resourceNames) {
    return arg.type().equals(TypeNode.STRING)
        && arg.field().hasResourceReference()
        && resourceNames.containsKey(arg.field().resourceReference().resourceTypeString());
  }

  private static boolean isProtoEmptyType(TypeNode type) {
    return type.reference().pakkage().equals("com.google.protobuf")
        && type.reference().name().equals("Empty");
  }
}
