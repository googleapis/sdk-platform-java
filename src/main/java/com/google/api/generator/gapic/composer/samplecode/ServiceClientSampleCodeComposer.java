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

import com.google.api.core.ApiFuture;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.rpc.ApiStreamObserver;
import com.google.api.gax.rpc.BidiStream;
import com.google.api.gax.rpc.ServerStream;
import com.google.api.generator.engine.ast.AnonymousClassExpr;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.BreakStatement;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.ForStatement;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.UnaryOperationExpr;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.ast.WhileStatement;
import com.google.api.generator.gapic.composer.defaultvalue.DefaultValueComposer;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Method.Stream;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.longrunning.Operation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceClientSampleCodeComposer {

  public static String composeClassHeaderMethodSampleCode(
      Service service,
      TypeNode clientType,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes) {
    // Use the first pure unary RPC method's sample code as showcase, if no such method exists, use
    // the first method in the service's methods list.
    Method method =
        service.methods().stream()
            .filter(m -> m.stream() == Stream.NONE && !m.hasLro() && !m.isPaged())
            .findFirst()
            .orElse(service.methods().get(0));
    if (method.stream() == Stream.NONE) {
      if (method.methodSignatures().isEmpty()) {
        return composeRpcDefaultMethodHeaderSampleCode(
            method, clientType, resourceNames, messageTypes);
      }
      return composeRpcMethodHeaderSampleCode(
          method, clientType, method.methodSignatures().get(0), resourceNames, messageTypes);
    }
    return composeStreamCallableMethodHeaderSampleCode(
        method, clientType, resourceNames, messageTypes);
  }

  public static String composeClassHeaderCredentialsSampleCode(
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
    VariableExpr clientVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName(JavaStyle.toLowerCamelCase(clientType.reference().name()))
                .setType(clientType)
                .build());

    // Assign method's arguments variable with the default values.
    List<VariableExpr> rpcMethodArgVarExprs = createRpcMethodArgumentVariableExprs(arguments);
    List<Expr> rpcMethodArgDefaultValueExprs =
        createRpcMethodArgumentDefaultValueExprs(arguments, resourceNames);
    List<Expr> rpcMethodArgAssignmentExprs =
        createAssignmentsForVarExprsWithValueExprs(
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
            .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
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
        DefaultValueComposer.createSimpleMessageBuilderExpr(
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
            .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
            .setTryBody(bodyStatements)
            .setIsSampleCode(true)
            .build());
  }

  // Compose sample code for the method where it is CallableMethodKind.LRO.
  public static String composeLroCallableMethodHeaderSampleCode(
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
    // Assign method's request variable with the default value.
    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("request").setType(method.inputType()).build());
    Message requestMessage = messageTypes.get(method.inputType().reference().fullName());
    Preconditions.checkNotNull(
        requestMessage,
        String.format(
            "Could not find the message type %s.", method.inputType().reference().fullName()));
    Expr requestBuilderExpr =
        DefaultValueComposer.createSimpleMessageBuilderExpr(
            requestMessage, resourceNames, messageTypes);
    AssignmentExpr requestAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(requestVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(requestBuilderExpr)
            .build();

    List<Expr> bodyExprs = new ArrayList<>();
    bodyExprs.add(requestAssignmentExpr);

    // Create OperationFuture variable expr with invoking client's LRO callable method.
    // e.g. OperationFuture<Empty, WaitMetadata> future =
    //          echoClient.waitOperationCallable().futureCall(request);
    TypeNode operationFutureType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(OperationFuture.class)
                .setGenerics(
                    method.lro().responseType().reference(),
                    method.lro().metadataType().reference())
                .build());
    VariableExpr operationFutureVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("future").setType(operationFutureType).build());
    MethodInvocationExpr rpcMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(
                String.format("%sOperationCallable", JavaStyle.toLowerCamelCase(method.name())))
            .build();
    rpcMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(rpcMethodInvocationExpr)
            .setMethodName("futureCall")
            .setArguments(requestVarExpr)
            .setReturnType(operationFutureType)
            .build();
    bodyExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(operationFutureVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(rpcMethodInvocationExpr)
            .build());

    List<Statement> bodyStatements =
        bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());
    bodyExprs.clear();
    // Add a line comment
    bodyStatements.add(CommentStatement.withComment(LineComment.withComment("Do something.")));

    // Assign response variable with invoking client's LRO method.
    // e.g. if return void, future.get(); or,
    // e.g. if return other type, WaitResponse response = future.get();
    MethodInvocationExpr futureGetMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(operationFutureVarExpr)
            .setMethodName("get")
            .setReturnType(method.lro().responseType())
            .build();
    boolean returnsVoid = isProtoEmptyType(method.lro().responseType());
    if (returnsVoid) {
      bodyExprs.add(futureGetMethodExpr);
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
              .setValueExpr(futureGetMethodExpr)
              .build());
    }
    bodyStatements.addAll(
        bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()));
    bodyExprs.clear();

    return SampleCodeWriter.write(
        TryCatchStatement.builder()
            .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
            .setTryBody(bodyStatements)
            .setIsSampleCode(true)
            .build());
  }

  // Compose sample code for the method where it is CallableMethodKind.PAGED.
  public static String composePagedCallableMethodHeaderSampleCode(
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
    // Assign method's request variable with the default value.
    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("request").setType(method.inputType()).build());
    Message requestMessage = messageTypes.get(method.inputType().reference().fullName());
    Preconditions.checkNotNull(
        requestMessage,
        String.format(
            "Could not find the message type %s.", method.inputType().reference().fullName()));
    Expr requestBuilderExpr =
        DefaultValueComposer.createSimpleMessageBuilderExpr(
            requestMessage, resourceNames, messageTypes);
    AssignmentExpr requestAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(requestVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(requestBuilderExpr)
            .build();

    List<Expr> bodyExprs = new ArrayList<>();
    bodyExprs.add(requestAssignmentExpr);

    // Find the repeated field.
    Message methodOutputMessage = messageTypes.get(method.outputType().reference().fullName());
    Field repeatedPagedResultsField = methodOutputMessage.findAndUnwrapPaginatedRepeatedField();
    Preconditions.checkNotNull(
        repeatedPagedResultsField,
        String.format(
            "No repeated field found on message %s for method %s",
            methodOutputMessage.name(), method.name()));
    TypeNode repeatedResponseType = repeatedPagedResultsField.type();

    // Create ApiFuture Variable Expression with assign value by invoking client paged callable
    // method.
    // e.g. ApiFuture<ListExclusionsPagedResponse> future =
    //          configServiceV2Client.listExclusionsPagedCallable().futureCall(request);
    TypeNode apiFutureType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ApiFuture.class)
                .setGenerics(repeatedResponseType.reference())
                .build());
    VariableExpr apiFutureVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("future").setType(apiFutureType).build());
    MethodInvocationExpr pagedCallableFutureMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(
                String.format("%sPagedCallable", JavaStyle.toLowerCamelCase(method.name())))
            .build();
    pagedCallableFutureMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(pagedCallableFutureMethodExpr)
            .setMethodName("futureCall")
            .setArguments(requestVarExpr)
            .setReturnType(apiFutureType)
            .build();
    AssignmentExpr apiFutureAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(apiFutureVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(pagedCallableFutureMethodExpr)
            .build();
    bodyExprs.add(apiFutureAssignmentExpr);

    List<Statement> bodyStatements =
        bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());
    bodyExprs.clear();

    // Add line comment
    bodyStatements.add(CommentStatement.withComment(LineComment.withComment("Do something.")));

    // For-loop on repeated response element
    // e.g. for (ListExclusionsResponse element : future.get().iterateAll()) {
    //        // doThingsWith(element);
    //      }
    VariableExpr repeatedResponseVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("element").setType(repeatedResponseType).build());
    MethodInvocationExpr futureGetIterateAllMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(apiFutureVarExpr)
            .setMethodName("get")
            .build();
    futureGetIterateAllMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(futureGetIterateAllMethodExpr)
            .setMethodName("iterateAll")
            .setReturnType(repeatedResponseType)
            .build();
    CommentStatement lineCommentStatement =
        CommentStatement.withComment(LineComment.withComment("doThingsWith(element);"));
    ForStatement repeatedResponseForStatement =
        ForStatement.builder()
            .setLocalVariableExpr(repeatedResponseVarExpr.toBuilder().setIsDecl(true).build())
            .setCollectionExpr(futureGetIterateAllMethodExpr)
            .setBody(Arrays.asList(lineCommentStatement))
            .build();
    bodyStatements.add(repeatedResponseForStatement);

    return SampleCodeWriter.write(
        TryCatchStatement.builder()
            .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
            .setTryBody(bodyStatements)
            .setIsSampleCode(true)
            .build());
  }

  // Compose sample code for the method where it is CallableMethodKind.REGULAR.
  public static String composeRegularCallableMethodHeaderSampleCode(
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

    // Assign method's request variable with the default value.
    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("request").setType(method.inputType()).build());
    Message requestMessage = messageTypes.get(method.inputType().reference().fullName());
    Preconditions.checkNotNull(
        requestMessage,
        String.format(
            "Could not find the message type %s.", method.inputType().reference().fullName()));
    Expr requestBuilderExpr =
        DefaultValueComposer.createSimpleMessageBuilderExpr(
            requestMessage, resourceNames, messageTypes);
    AssignmentExpr requestAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(requestVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(requestBuilderExpr)
            .build();

    List<Statement> bodyStatements = new ArrayList<>();
    bodyStatements.add(ExprStatement.withExpr(requestAssignmentExpr));

    if (method.isPaged()) {
      bodyStatements.addAll(
          composePagedCallableBodyStatements(method, clientVarExpr, requestVarExpr, messageTypes));
    } else {
      bodyStatements.addAll(
          composeUnaryOrLroCallableBodyStatements(method, clientVarExpr, requestVarExpr));
    }

    return SampleCodeWriter.write(
        TryCatchStatement.builder()
            .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
            .setTryBody(bodyStatements)
            .setIsSampleCode(true)
            .build());
  }

  public static String composeStreamCallableMethodHeaderSampleCode(
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
    // Assign method's request variable with the default value.
    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("request").setType(method.inputType()).build());
    Message requestMessage = messageTypes.get(method.inputType().reference().fullName());
    Preconditions.checkNotNull(
        requestMessage,
        String.format(
            "Could not find the message type %s.", method.inputType().reference().fullName()));
    Expr requestBuilderExpr =
        DefaultValueComposer.createSimpleMessageBuilderExpr(
            requestMessage, resourceNames, messageTypes);
    AssignmentExpr requestAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(requestVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(requestBuilderExpr)
            .build();

    List<Statement> bodyStatements = new ArrayList<>();
    if (method.stream().equals(Stream.SERVER)) {
      bodyStatements.addAll(
          composeStreamServerBodyStatements(method, clientVarExpr, requestAssignmentExpr));
    } else if (method.stream().equals(Stream.BIDI)) {
      bodyStatements.addAll(
          composeStreamBidiBodyStatements(method, clientVarExpr, requestAssignmentExpr));
    } else if (method.stream().equals(Stream.CLIENT)) {
      bodyStatements.addAll(
          composeStreamClientBodyStatements(method, clientVarExpr, requestAssignmentExpr));
    }

    return SampleCodeWriter.write(
        TryCatchStatement.builder()
            .setTryResourceExpr(assignClientVariableWithCreateMethodExpr(clientVarExpr))
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
    boolean returnsVoid = isProtoEmptyType(method.outputType());
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
    boolean returnsVoid = isProtoEmptyType(method.lro().responseType());
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

  private static List<Statement> composeStreamServerBodyStatements(
      Method method, VariableExpr clientVarExpr, AssignmentExpr requestAssignmentExpr) {
    List<Expr> bodyExprs = new ArrayList<>();
    bodyExprs.add(requestAssignmentExpr);

    // Create server stream variable expression, and assign it a value by invoking server stream
    // method.
    // e.g. ServerStream<EchoResponse> stream = echoClient.expandCallable().call(request)
    TypeNode serverStreamType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ServerStream.class)
                .setGenerics(method.outputType().reference())
                .build());
    VariableExpr serverStreamVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("stream").setType(serverStreamType).build());
    MethodInvocationExpr clientStreamCallMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(JavaStyle.toLowerCamelCase(String.format("%sCallable", method.name())))
            .build();
    clientStreamCallMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientStreamCallMethodInvocationExpr)
            .setMethodName("call")
            .setArguments(requestAssignmentExpr.variableExpr().toBuilder().setIsDecl(false).build())
            .setReturnType(serverStreamType)
            .build();
    AssignmentExpr streamAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(serverStreamVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(clientStreamCallMethodInvocationExpr)
            .build();
    bodyExprs.add(streamAssignmentExpr);

    List<Statement> bodyStatements =
        bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());

    // For-loop on server stream variable expresion.
    // e.g. for (EchoResponse response : stream) {
    //        // Do something when a response is received.
    //      }
    VariableExpr responseVarExpr =
        VariableExpr.builder()
            .setVariable(
                Variable.builder().setName("response").setType(method.outputType()).build())
            .setIsDecl(true)
            .build();
    ForStatement forStatement =
        ForStatement.builder()
            .setLocalVariableExpr(responseVarExpr)
            .setCollectionExpr(serverStreamVarExpr)
            .setBody(
                Arrays.asList(
                    CommentStatement.withComment(
                        LineComment.withComment("Do something when a response is received."))))
            .build();
    bodyStatements.add(forStatement);

    return bodyStatements;
  }

  private static List<Statement> composeStreamBidiBodyStatements(
      Method method, VariableExpr clientVarExpr, AssignmentExpr requestAssignmentExpr) {
    List<Expr> bodyExprs = new ArrayList<>();

    // Create bidi stream variable expression and assign it with invoking client's bidi stream
    // method.
    // e.g. BidiStream<EchoRequest, EchoResponse> bidiStream = echoClient.chatCallable().call();
    TypeNode bidiStreamType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(BidiStream.class)
                .setGenerics(method.inputType().reference(), method.outputType().reference())
                .build());
    VariableExpr bidiStreamVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("bidiStream").setType(bidiStreamType).build());
    MethodInvocationExpr clientBidiStreamCallMethodInvoationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(JavaStyle.toLowerCamelCase(String.format("%sCallable", method.name())))
            .build();
    clientBidiStreamCallMethodInvoationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientBidiStreamCallMethodInvoationExpr)
            .setMethodName("call")
            .setReturnType(bidiStreamType)
            .build();
    AssignmentExpr bidiStreamAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(bidiStreamVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(clientBidiStreamCallMethodInvoationExpr)
            .build();
    bodyExprs.add(bidiStreamAssignmentExpr);

    // Add request with default value expression.
    bodyExprs.add(requestAssignmentExpr);

    // Invoke send method with argument request.
    // e.g. bidiStream.send(request);
    MethodInvocationExpr sendMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(bidiStreamVarExpr)
            .setArguments(requestAssignmentExpr.variableExpr().toBuilder().setIsDecl(false).build())
            .setMethodName("send")
            .build();
    bodyExprs.add(sendMethodInvocationExpr);

    List<Statement> bodyStatements =
        bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());

    // For-loop on bidi stream variable.
    // e.g. for (EchoResponse response : bidiStream) {
    //        // Do something when reveive a response.
    //      }
    ForStatement forStatement =
        ForStatement.builder()
            .setLocalVariableExpr(
                VariableExpr.builder()
                    .setVariable(
                        Variable.builder().setType(method.outputType()).setName("response").build())
                    .setIsDecl(true)
                    .build())
            .setCollectionExpr(bidiStreamVarExpr)
            .setBody(
                Arrays.asList(
                    CommentStatement.withComment(
                        LineComment.withComment("Do something when a response is received."))))
            .build();
    bodyStatements.add(forStatement);

    return bodyStatements;
  }

  private static List<Statement> composeStreamClientBodyStatements(
      Method method, VariableExpr clientVarExpr, AssignmentExpr requestAssignmentExpr) {
    List<Expr> bodyExprs = new ArrayList<>();

    // Create responseObserver variable expression.
    // e.g. ApiStream<EchoResponse> responseObserver
    TypeNode responseObserverType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ApiStreamObserver.class)
                .setGenerics(method.inputType().reference())
                .build());
    VariableExpr responseObserverVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("responseObserver").setType(responseObserverType).build());
    // Create an anonymous class for ApiStreamObserver that contains the methods onNext, onError,
    // and onCompleted.
    MethodDefinition onNextMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setName("onNext")
            .setArguments(
                VariableExpr.builder()
                    .setVariable(
                        Variable.builder().setName("response").setType(method.outputType()).build())
                    .setIsDecl(true)
                    .build())
            .setBody(
                Arrays.asList(
                    CommentStatement.withComment(
                        LineComment.withComment("Do something when a response is received."))))
            .setReturnType(TypeNode.VOID)
            .build();
    MethodDefinition onErrorMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setName("onError")
            .setArguments(
                VariableExpr.builder()
                    .setVariable(
                        Variable.builder()
                            .setName("t")
                            .setType(
                                TypeNode.withReference(
                                    ConcreteReference.withClazz(Throwable.class)))
                            .build())
                    .setIsDecl(true)
                    .build())
            .setBody(
                Arrays.asList(
                    CommentStatement.withComment(LineComment.withComment("Add error-handling"))))
            .setReturnType(TypeNode.VOID)
            .build();
    MethodDefinition onCompletedMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setName("onCompleted")
            .setBody(
                Arrays.asList(
                    CommentStatement.withComment(
                        LineComment.withComment("Do something when complete."))))
            .setReturnType(TypeNode.VOID)
            .build();
    AnonymousClassExpr anonymousClassExpr =
        AnonymousClassExpr.builder()
            .setType(responseObserverType)
            .setMethods(onNextMethod, onErrorMethod, onCompletedMethod)
            .build();
    AssignmentExpr responseObserverAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(responseObserverVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(anonymousClassExpr)
            .build();
    bodyExprs.add(responseObserverAssignmentExpr);

    // Create an assignment expression for request observer variable expression when invoking the
    // client's streaming call.
    // e.g. ApiStreamObserver<EchoRequest> requestObserver =
    //          echoClient.collectCallable().clientStreamingCall(responseObserver);
    TypeNode requestObserverType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ApiStreamObserver.class)
                .setGenerics(method.inputType().reference())
                .build());
    VariableExpr requestObserverVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("requestObserver").setType(responseObserverType).build());
    MethodInvocationExpr clientStreamCallMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(JavaStyle.toLowerCamelCase(method.name()))
            .build();
    clientStreamCallMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientStreamCallMethodInvocationExpr)
            .setArguments(responseObserverVarExpr)
            .setMethodName("clientStreamingCall")
            .setReturnType(requestObserverType)
            .build();
    AssignmentExpr requestObserverAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(requestObserverVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(clientStreamCallMethodInvocationExpr)
            .build();
    bodyExprs.add(requestObserverAssignmentExpr);

    // Add assignment expression of request with its default value.
    bodyExprs.add(requestAssignmentExpr);

    // Invoke onNext method with argument of request variable.
    // e.g. requestObserver.onNext(request)
    MethodInvocationExpr onNextMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(requestObserverVarExpr)
            .setMethodName("onNext")
            .setArguments(requestAssignmentExpr.variableExpr().toBuilder().setIsDecl(false).build())
            .build();
    bodyExprs.add(onNextMethodExpr);

    return bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());
  }

  private static List<Statement> composeUnaryOrLroCallableBodyStatements(
      Method method, VariableExpr clientVarExpr, VariableExpr requestVarExpr) {
    List<Statement> bodyStatements = new ArrayList<>();
    // Create api future variable expression, and assign it with a value by invoking callable
    // method.
    // e.g. ApiFuture<EchoResponse> future = echoClient.echoCallable().futureCall(request);
    TypeNode apiFutureType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ApiFuture.class)
                .setGenerics(
                    method.hasLro()
                        ? ConcreteReference.withClazz(Operation.class)
                        : method.outputType().reference())
                .build());
    VariableExpr apiFutureVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("future").setType(apiFutureType).build());
    MethodInvocationExpr callableMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(JavaStyle.toLowerCamelCase(String.format("%sCallable", method.name())))
            .build();
    callableMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(callableMethodInvocationExpr)
            .setMethodName("futureCall")
            .setArguments(requestVarExpr)
            .setReturnType(apiFutureType)
            .build();
    AssignmentExpr futureAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(apiFutureVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(callableMethodInvocationExpr)
            .build();
    bodyStatements.add(ExprStatement.withExpr(futureAssignmentExpr));
    bodyStatements.add(CommentStatement.withComment(LineComment.withComment("Do something.")));

    MethodInvocationExpr getMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(apiFutureVarExpr)
            .setMethodName("get")
            .setReturnType(method.outputType())
            .build();
    TypeNode methodOutputType = method.hasLro() ? method.lro().responseType() : method.outputType();
    boolean returnsVoid = isProtoEmptyType(methodOutputType);
    if (returnsVoid) {
      bodyStatements.add(ExprStatement.withExpr(getMethodInvocationExpr));
    } else {
      VariableExpr responseVarExpr =
          VariableExpr.builder()
              .setVariable(
                  Variable.builder().setType(method.outputType()).setName("response").build())
              .setIsDecl(true)
              .build();
      AssignmentExpr responseAssignmentExpr =
          AssignmentExpr.builder()
              .setVariableExpr(responseVarExpr)
              .setValueExpr(getMethodInvocationExpr)
              .build();
      bodyStatements.add(ExprStatement.withExpr(responseAssignmentExpr));
    }
    return bodyStatements;
  }

  private static List<Statement> composePagedCallableBodyStatements(
      Method method,
      VariableExpr clientVarExpr,
      VariableExpr requestVarExpr,
      Map<String, Message> messageTypes) {
    // Find the repeated field.
    Message methodOutputMessage = messageTypes.get(method.outputType().reference().fullName());
    Field repeatedPagedResultsField = methodOutputMessage.findAndUnwrapPaginatedRepeatedField();
    Preconditions.checkNotNull(
        repeatedPagedResultsField,
        String.format(
            "No repeated field found on message %s for method %s",
            methodOutputMessage.name(), method.name()));
    TypeNode repeatedResponseType = repeatedPagedResultsField.type();

    // Assign future variable by invoking paged callable method.
    // e.g. ApiFuture<PagedExpandPagedResponse> future =
    // echoClient.pagedExpandCallable().futureCall(request);
    VariableExpr responseVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("response").setType(method.outputType()).build());
    MethodInvocationExpr pagedCallableMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientVarExpr)
            .setMethodName(JavaStyle.toLowerCamelCase(String.format("%sCallable", method.name())))
            .build();
    pagedCallableMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(pagedCallableMethodInvocationExpr)
            .setMethodName("call")
            .setArguments(requestVarExpr)
            .setReturnType(method.outputType())
            .build();
    AssignmentExpr responseAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(responseVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(pagedCallableMethodInvocationExpr)
            .build();
    List<Statement> whileBodyStatements = new ArrayList<>();
    whileBodyStatements.add(ExprStatement.withExpr(responseAssignmentExpr));

    // For-loop on repeated response elements.
    // e.g. for (EchoResponse element : response.getResponsesList()) {
    //        // doThingsWith(element);
    //      }
    VariableExpr repeatedResponseVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("element").setType(repeatedResponseType).build());
    MethodInvocationExpr getResponseListMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(responseVarExpr)
            .setMethodName("getResponsesList")
            .build();
    ForStatement responseForStatements =
        ForStatement.builder()
            .setLocalVariableExpr(repeatedResponseVarExpr.toBuilder().setIsDecl(true).build())
            .setCollectionExpr(getResponseListMethodInvocationExpr)
            .setBody(
                Arrays.asList(
                    CommentStatement.withComment(
                        LineComment.withComment("doThingsWith(element);"))))
            .build();
    whileBodyStatements.add(responseForStatements);

    // Create nextPageToken variable expression and assign it with a value by invoking
    // getNextPageToken method.
    // e.g. String nextPageToken = response.getNextPageToken();
    VariableExpr nextPageTokenVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("nextPageToken").setType(TypeNode.STRING).build());
    MethodInvocationExpr getNextPageTokenMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(responseVarExpr)
            .setMethodName("getNextPageToken")
            .setReturnType(TypeNode.STRING)
            .build();
    AssignmentExpr nextPageTokenAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(nextPageTokenVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(getNextPageTokenMethodInvocationExpr)
            .build();
    whileBodyStatements.add(ExprStatement.withExpr(nextPageTokenAssignmentExpr));

    // If nextPageToken variable expression is not null or empty, assign request variable with a
    // value by invoking setPageToken method.
    // if (!Strings.isNullOrEmpty(nextPageToken)) {
    //   request =  request.toBuilder().setPageToken(nextPageToken).build();
    // } else {
    //   break;
    // }
    Expr conditionExpr =
        UnaryOperationExpr.logicalNotWithExpr(
            MethodInvocationExpr.builder()
                .setStaticReferenceType(
                    TypeNode.withReference(ConcreteReference.withClazz(Strings.class)))
                .setMethodName("isNullOrEmpty")
                .setArguments(nextPageTokenVarExpr)
                .setReturnType(TypeNode.BOOLEAN)
                .build());
    MethodInvocationExpr setPageTokenMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(requestVarExpr)
            .setMethodName("toBuilder")
            .build();
    setPageTokenMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(setPageTokenMethodInvocationExpr)
            .setMethodName("setPageToken")
            .setArguments(nextPageTokenVarExpr)
            .build();
    setPageTokenMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(setPageTokenMethodInvocationExpr)
            .setMethodName("build")
            .setReturnType(method.inputType())
            .build();
    AssignmentExpr requestReAssignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(requestVarExpr)
            .setValueExpr(setPageTokenMethodInvocationExpr)
            .build();
    IfStatement nextPageTokenIfStatement =
        IfStatement.builder()
            .setConditionExpr(conditionExpr)
            .setBody(Arrays.asList(ExprStatement.withExpr(requestReAssignmentExpr)))
            .setElseBody(Arrays.asList(BreakStatement.create()))
            .build();
    whileBodyStatements.add(nextPageTokenIfStatement);

    WhileStatement pagedWhileStatement =
        WhileStatement.builder()
            .setConditionExpr(
                ValueExpr.withValue(
                    PrimitiveValue.builder().setValue("true").setType(TypeNode.BOOLEAN).build()))
            .setBody(whileBodyStatements)
            .build();
    return Arrays.asList(pagedWhileStatement);
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
