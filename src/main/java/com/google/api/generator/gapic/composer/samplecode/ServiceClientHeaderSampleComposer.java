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

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.defaultvalue.DefaultValueComposer;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.RegionTag;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Sample;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceClientHeaderSampleComposer {
  public static Sample composeClassHeaderSample(
      Service service,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes) {
    if (service.methods().isEmpty()) {
      return ServiceClientMethodSampleComposer.composeEmptyServiceSample(clientType);
    }

    // Use the first pure unary RPC method's sample code as showcase, if no such method exists, use
    // the first method in the service's methods list.
    Method method =
        service.methods().stream()
            .filter(m -> m.stream() == Method.Stream.NONE && !m.hasLro() && !m.isPaged())
            .findFirst()
            .orElse(service.methods().get(0));

    if (method.stream() == Method.Stream.NONE) {
      if (method.methodSignatures().isEmpty()) {
        return ServiceClientMethodSampleComposer.composeCanonicalSample(
            method, clientType, resourceNames, messageTypes);
      }
      return composeShowcaseMethodSample(
          method, clientType, method.methodSignatures().get(0), resourceNames, messageTypes);
    }
    return ServiceClientCallableMethodSampleComposer.composeStreamCallableMethod(
        method, clientType, resourceNames, messageTypes);
  }

  public static Sample composeShowcaseMethodSample(
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
    List<VariableExpr> rpcMethodArgVarExprs = createArgumentVariableExprs(arguments);
    List<Expr> rpcMethodArgDefaultValueExprs =
        createArgumentDefaultValueExprs(arguments, resourceNames);
    List<Expr> rpcMethodArgAssignmentExprs =
        createAssignmentsForVarExprsWithValueExprs(
            rpcMethodArgVarExprs, rpcMethodArgDefaultValueExprs);

    List<Expr> bodyExprs = new ArrayList<>();
    bodyExprs.addAll(rpcMethodArgAssignmentExprs);

    List<Statement> bodyStatements = new ArrayList<>();
    RegionTag regionTag;
    if (method.isPaged()) {
      Sample unaryPagedRpc =
          ServiceClientMethodSampleComposer.composePagedSample(
              method, clientVarExpr, rpcMethodArgVarExprs, bodyExprs, messageTypes);
      bodyStatements.addAll(unaryPagedRpc.body());
      regionTag = unaryPagedRpc.regionTag();
    } else if (method.hasLro()) {
      Sample unaryLroRpc =
          ServiceClientMethodSampleComposer.composeLroSample(
              method, clientVarExpr, rpcMethodArgVarExprs, bodyExprs);
      bodyStatements.addAll(unaryLroRpc.body());
      regionTag = unaryLroRpc.regionTag();
    } else {
      //  e.g. echoClient.echo(), echoClient.echo(...)
      Sample unaryRpc =
          ServiceClientMethodSampleComposer.composeSample(
              method, clientVarExpr, rpcMethodArgVarExprs, bodyExprs);
      bodyStatements.addAll(unaryRpc.body());
      regionTag = unaryRpc.regionTag();
    }

    List<Statement> body =
        Arrays.asList(
            TryCatchStatement.builder()
                .setTryResourceExpr(
                    SampleComposerUtil.assignClientVariableWithCreateMethodExpr(clientVarExpr))
                .setTryBody(bodyStatements)
                .setIsSampleCode(true)
                .build());
    return Sample.builder().setBody(body).setRegionTag(regionTag).build();
  }

  public static Sample composeSetCredentialsSample(TypeNode clientType, TypeNode settingsType) {
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
    String rpcName = createMethodExpr.methodIdentifier().name();
    Expr initClientVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(createMethodExpr)
            .build();

    List<Statement> sampleBody =
        Arrays.asList(
            ExprStatement.withExpr(initSettingsVarExpr), ExprStatement.withExpr(initClientVarExpr));
    RegionTag regionTag =
        RegionTag.builder()
            .setServiceName(clientName)
            .setRpcName(rpcName)
            .setOverloadDisambiguation("setCredentialsProvider")
            .build();
    return Sample.builder().setBody(sampleBody).setRegionTag(regionTag).build();
  }

  public static Sample composeSetEndpointSample(TypeNode clientType, TypeNode settingsType) {
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
    String rpcName = createMethodExpr.methodIdentifier().name();
    Expr initClientVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(createMethodExpr)
            .build();
    RegionTag regionTag =
        RegionTag.builder()
            .setServiceName(clientName)
            .setRpcName(rpcName)
            .setOverloadDisambiguation("setEndpoint")
            .build();
    List<Statement> sampleBody =
        Arrays.asList(
            ExprStatement.withExpr(initSettingsVarExpr), ExprStatement.withExpr(initClientVarExpr));
    return Sample.builder().setBody(sampleBody).setRegionTag(regionTag).build();
  }

  public static Sample composeTransportSample(
      TypeNode clientType, TypeNode settingsType, String transportProviderMethod) {
    String settingsName = JavaStyle.toLowerCamelCase(settingsType.reference().name());
    String clientName = JavaStyle.toLowerCamelCase(clientType.reference().name());
    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName(settingsName).setType(settingsType).build());
    MethodInvocationExpr newBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(settingsType)
            .setMethodName("newBuilder")
            .build();
    MethodInvocationExpr transportChannelProviderArg =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(settingsType)
                    .setMethodName(transportProviderMethod)
                    .build())
            .setMethodName("build")
            .build();
    MethodInvocationExpr credentialsMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(newBuilderMethodExpr)
            .setArguments(transportChannelProviderArg)
            .setMethodName("setTransportChannelProvider")
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
    String rpcName = createMethodExpr.methodIdentifier().name();
    Expr initClientVarExpr =
        AssignmentExpr.builder()
            .setVariableExpr(clientVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(createMethodExpr)
            .build();

    List<Statement> sampleBody =
        Arrays.asList(
            ExprStatement.withExpr(initSettingsVarExpr), ExprStatement.withExpr(initClientVarExpr));
    RegionTag regionTag =
        RegionTag.builder()
            .setServiceName(clientName)
            .setRpcName(rpcName)
            .setOverloadDisambiguation("setCredentialsProvider")
            .build();
    return Sample.builder().setBody(sampleBody).setRegionTag(regionTag).build();
  }

  // Create a list of RPC method arguments' variable expressions.
  private static List<VariableExpr> createArgumentVariableExprs(List<MethodArgument> arguments) {
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
  private static List<Expr> createArgumentDefaultValueExprs(
      List<MethodArgument> arguments, Map<String, ResourceName> resourceNames) {
    List<ResourceName> resourceNameList =
        resourceNames.values().stream().collect(Collectors.toList());
    Function<MethodArgument, MethodInvocationExpr> stringResourceNameDefaultValueExpr =
        arg ->
            MethodInvocationExpr.builder()
                .setExprReferenceExpr(
                    DefaultValueComposer.createResourceHelperValue(
                        resourceNames.get(arg.field().resourceReference().resourceTypeString()),
                        arg.field().resourceReference().isChildType(),
                        resourceNameList,
                        arg.field().name()))
                .setMethodName("toString")
                .setReturnType(TypeNode.STRING)
                .build();
    return arguments.stream()
        .map(
            arg ->
                !SampleComposerUtil.isStringTypedResourceName(arg, resourceNames)
                    ? DefaultValueComposer.createMethodArgValue(
                        arg, resourceNames, Collections.emptyMap(), Collections.emptyMap())
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
}
