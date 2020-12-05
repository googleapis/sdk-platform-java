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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    VariableExpr myCredentialVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("myCredentials").setType(myCredentialsType).build());
    MethodInvocationExpr credentialArgExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(fixedCredentialProvideType)
            .setArguments(myCredentialVarExpr)
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
    VariableExpr myEndpointVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("myEndpoint").setType(myEndpointType).build());
    MethodInvocationExpr credentialsMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(newBuilderMethodExpr)
            .setArguments(myEndpointVarExpr)
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
      List<MethodArgument> arguments,
      TypeNode clientType,
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
              method, arguments, clientType, resourceNames, repeatedResponseType));
    }
    return SampleCodeWriter.write(
        composeUnaryRpcMethodSampleCode(method, arguments, clientType, resourceNames));
  }

  @VisibleForTesting
  static TryCatchStatement composeUnaryRpcMethodSampleCode(
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
    // List of rpc method arguments' variable expressions.
    List<Expr> rpcMethodArgVarExprs =
        arguments.stream()
            .map(
                arg ->
                    VariableExpr.withVariable(
                        Variable.builder()
                            .setName(JavaStyle.toLowerCamelCase(arg.name()))
                            .setType(arg.type())
                            .build()))
            .collect(Collectors.toList());
    // List of rpc method arguments' default value expression.
    List<ResourceName> resourceNameList =
        resourceNames.values().stream().collect(Collectors.toList());
    List<Expr> rpcMethodArgDefaultValueExprs =
        arguments.stream()
            .map(
                arg ->
                    !isStringTypedResourceName(arg, resourceNames)
                        ? DefaultValueComposer.createDefaultValue(arg, resourceNames)
                        : MethodInvocationExpr.builder()
                            .setExprReferenceExpr(
                                DefaultValueComposer.createDefaultValue(
                                    resourceNames.get(
                                        arg.field().resourceReference().resourceTypeString()),
                                    resourceNameList,
                                    arg.field().name()))
                            .setMethodName("toString")
                            .setReturnType(TypeNode.STRING)
                            .build())
            .collect(Collectors.toList());

    List<Expr> bodyExprs = new ArrayList<>();
    Preconditions.checkState(
        rpcMethodArgVarExprs.size() == rpcMethodArgDefaultValueExprs.size(),
        "The method arguments' the number of variable expressions should equal to the number of default value expressions.");
    bodyExprs.addAll(
        IntStream.range(0, rpcMethodArgVarExprs.size())
            .mapToObj(
                i ->
                    AssignmentExpr.builder()
                        .setVariableExpr(
                            ((VariableExpr) rpcMethodArgVarExprs.get(i))
                                .toBuilder()
                                .setIsDecl(true)
                                .build())
                        .setValueExpr(rpcMethodArgDefaultValueExprs.get(i))
                        .build())
            .collect(Collectors.toList()));
    // Invoke current method based on return type.
    // e.g. if return void, echoClient.echo(..); or,
    // e.g. if return other type, EchoResponse response = echoClient.echo(...);
    boolean returnsVoid = isProtoEmptyType(method.outputType());
    if (returnsVoid) {
      bodyExprs.add(
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(clientVarExpr)
              .setMethodName(JavaStyle.toLowerCamelCase(method.name()))
              .setArguments(rpcMethodArgVarExprs)
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
              .setArguments(rpcMethodArgVarExprs)
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

  public static TryCatchStatement composeUnaryPagedRpcMethodSampleCode(
      Method method,
      List<MethodArgument> arguments,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames,
      TypeNode repeatedResponseType) {
    VariableExpr clientVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName(JavaStyle.toLowerCamelCase(clientType.reference().name()))
                .setType(clientType)
                .build());
    // List of rpc method arguments' variable expressions.
    List<Expr> rpcMethodArgVarExprs =
        arguments.stream()
            .map(
                arg ->
                    VariableExpr.withVariable(
                        Variable.builder()
                            .setName(JavaStyle.toLowerCamelCase(arg.name()))
                            .setType(arg.type())
                            .build()))
            .collect(Collectors.toList());
    // List of rpc method arguments' default value expression.
    List<ResourceName> resourceNameList =
        resourceNames.values().stream().collect(Collectors.toList());
    List<Expr> rpcMethodArgDefaultValueExprs =
        arguments.stream()
            .map(
                arg ->
                    !isStringTypedResourceName(arg, resourceNames)
                        ? DefaultValueComposer.createDefaultValue(arg, resourceNames)
                        : MethodInvocationExpr.builder()
                            .setExprReferenceExpr(
                                DefaultValueComposer.createDefaultValue(
                                    resourceNames.get(
                                        arg.field().resourceReference().resourceTypeString()),
                                    resourceNameList,
                                    arg.field().name()))
                            .setMethodName("toString")
                            .setReturnType(TypeNode.STRING)
                            .build())
            .collect(Collectors.toList());

    List<Expr> bodyExprs = new ArrayList<>();
    Preconditions.checkState(
        rpcMethodArgVarExprs.size() == rpcMethodArgDefaultValueExprs.size(),
        "The method arguments' the number of variable expressions should equal to the number of default value expressions.");
    bodyExprs.addAll(
        IntStream.range(0, rpcMethodArgVarExprs.size())
            .mapToObj(
                i ->
                    AssignmentExpr.builder()
                        .setVariableExpr(
                            ((VariableExpr) rpcMethodArgVarExprs.get(i))
                                .toBuilder()
                                .setIsDecl(true)
                                .build())
                        .setValueExpr(rpcMethodArgDefaultValueExprs.get(i))
                        .build())
            .collect(Collectors.toList()));
    // For loop paged response item on iterateAll method.
    // e.g. for (LogEntry element : loggingServiceV2Client.ListLogs(parent).iterateAll()) {
    //          //doThingsWith(element);
    //      }
    MethodInvocationExpr clientMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(JavaStyle.toLowerCamelCase(method.name()))
            .setArguments(rpcMethodArgVarExprs)
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
            .setBody(Arrays.asList(createLineCommentStatement("doThingsWith(element);")))
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

  private static CommentStatement createLineCommentStatement(String content) {
    return CommentStatement.withComment(LineComment.withComment(content));
  }
}
