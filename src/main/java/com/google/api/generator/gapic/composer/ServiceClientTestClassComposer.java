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

import com.google.api.gax.core.GoogleCredentialsProvider;
import com.google.api.gax.core.InstantiatingExecutorProvider;
import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GaxGrpcProperties;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.rpc.ApiStreamObserver;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientStreamingCallable;
import com.google.api.gax.rpc.InvalidArgumentException;
import com.google.api.gax.rpc.OperationCallSettings;
import com.google.api.gax.rpc.PagedCallSettings;
import com.google.api.gax.rpc.ServerStreamingCallSettings;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.StatusCode;
import com.google.api.gax.rpc.StreamingCallSettings;
import com.google.api.gax.rpc.UnaryCallSettings;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.CastExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.EnumRefExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.InstanceofExpr;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.longrunning.Operation;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Any;
import io.grpc.StatusRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Generated;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO(miraleung): Refactor classComposer.
public class ServiceClientTestClassComposer {
  private static final String CHANNEL_PROVIDER_VAR_NAME = "channelProvider";
  private static final String CLASS_NAME_PATTERN = "%sClientTest";
  private static final String CLIENT_VAR_NAME = "client";
  private static final String GRPC_TESTING_PACKAGE = "com.google.api.gax.grpc.testing";
  private static final String MOCK_SERVICE_CLASS_NAME_PATTERN = "Mock%s";
  private static final String MOCK_SERVICE_VAR_NAME_PATTERN = "mock%s";
  private static final String SERVICE_CLIENT_CLASS_NAME_PATTERN = "%sClient";
  private static final String SERVICE_HELPER_VAR_NAME = "mockServiceHelper";
  private static final String SERVICE_SETTINGS_CLASS_NAME_PATTERN = "%sSettings";
  private static final String STUB_SETTINGS_PATTERN = "%sSettings";
  private static final String PAGED_RESPONSE_TYPE_NAME_PATTERN = "%sPagedResponse";

  private static final ServiceClientTestClassComposer INSTANCE =
      new ServiceClientTestClassComposer();

  private static final Map<String, TypeNode> STATIC_TYPES = createStaticTypes();

  private static final AnnotationNode TEST_ANNOTATION =
      AnnotationNode.withType(STATIC_TYPES.get("Test"));
  // Avoid conflicting types with com.google.rpc.Status.
  private static final TypeNode GRPC_STATUS_TYPE =
      TypeNode.withReference(
          ConcreteReference.builder().setClazz(io.grpc.Status.class).setUseFullName(true).build());

  private ServiceClientTestClassComposer() {}

  public static ServiceClientTestClassComposer instance() {
    return INSTANCE;
  }

  public GapicClass generate(
      Service service, Map<String, ResourceName> resourceNames, Map<String, Message> messageTypes) {
    String pakkage = service.pakkage();
    Map<String, TypeNode> types = createDynamicTypes(service);
    String className = String.format("%sClientTest", service.name());
    GapicClass.Kind kind = Kind.MAIN;

    Map<String, VariableExpr> classMemberVarExprs = createClassMemberVarExprs(service, types);

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(pakkage)
            .setAnnotations(createClassAnnotations())
            .setScope(ScopeNode.PUBLIC)
            .setName(className)
            .setStatements(createClassMemberFieldDecls(classMemberVarExprs))
            .setMethods(
                createClassMethods(
                    service, classMemberVarExprs, types, resourceNames, messageTypes))
            .build();
    return GapicClass.create(kind, classDef);
  }

  private static List<AnnotationNode> createClassAnnotations() {
    return Arrays.asList(
        AnnotationNode.builder()
            .setType(STATIC_TYPES.get("Generated"))
            .setDescription("by gapic-generator-java")
            .build());
  }

  private static Map<String, VariableExpr> createClassMemberVarExprs(
      Service service, Map<String, TypeNode> types) {
    BiFunction<String, TypeNode, VariableExpr> varExprFn =
        (name, type) ->
            VariableExpr.withVariable(Variable.builder().setName(name).setType(type).build());
    Map<String, TypeNode> fields = new LinkedHashMap<>();
    fields.put(
        getMockServiceVarName(service.name()),
        types.get(String.format(MOCK_SERVICE_CLASS_NAME_PATTERN, service.name())));
    fields.put(SERVICE_HELPER_VAR_NAME, STATIC_TYPES.get("MockServiceHelper"));
    fields.put(CLIENT_VAR_NAME, types.get(getClientClassName(service.name())));
    fields.put(CHANNEL_PROVIDER_VAR_NAME, STATIC_TYPES.get("LocalChannelProvider"));
    return fields.entrySet().stream()
        .collect(Collectors.toMap(e -> e.getKey(), e -> varExprFn.apply(e.getKey(), e.getValue())));
  }

  private static List<Statement> createClassMemberFieldDecls(
      Map<String, VariableExpr> classMemberVarExprs) {
    return classMemberVarExprs.values().stream()
        .map(
            v ->
                ExprStatement.withExpr(
                    v.toBuilder()
                        .setIsDecl(true)
                        .setScope(ScopeNode.PUBLIC)
                        .setIsStatic(v.type().reference().name().startsWith("Mock"))
                        .build()))
        .collect(Collectors.toList());
  }

  private static List<MethodDefinition> createClassMethods(
      Service service,
      Map<String, VariableExpr> classMemberVarExprs,
      Map<String, TypeNode> types,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes) {
    List<MethodDefinition> javaMethods = new ArrayList<>();
    javaMethods.addAll(createTestAdminMethods(service, classMemberVarExprs, types));
    javaMethods.addAll(
        createTestMethods(service, classMemberVarExprs, resourceNames, messageTypes));
    return javaMethods;
  }

  private static List<MethodDefinition> createTestAdminMethods(
      Service service, Map<String, VariableExpr> classMemberVarExprs, Map<String, TypeNode> types) {
    List<MethodDefinition> javaMethods = new ArrayList<>();
    javaMethods.add(createStartStaticServerMethod(service, classMemberVarExprs));
    javaMethods.add(createStopServerMethod(service, classMemberVarExprs));
    javaMethods.add(createSetUpMethod(service, classMemberVarExprs, types));
    javaMethods.add(createTearDownMethod(service, classMemberVarExprs));
    return javaMethods;
  }

  private static MethodDefinition createStartStaticServerMethod(
      Service service, Map<String, VariableExpr> classMemberVarExprs) {
    VariableExpr mockServiceVarExpr =
        classMemberVarExprs.get(getMockServiceVarName(service.name()));
    VariableExpr serviceHelperVarExpr = classMemberVarExprs.get(SERVICE_HELPER_VAR_NAME);
    Expr initMockServiceExpr =
        AssignmentExpr.builder()
            .setVariableExpr(mockServiceVarExpr)
            .setValueExpr(NewObjectExpr.builder().setType(mockServiceVarExpr.type()).build())
            .build();

    MethodInvocationExpr firstArg =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(STATIC_TYPES.get("UUID"))
            .setMethodName("randomUUID")
            .build();
    firstArg =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(firstArg)
            .setMethodName("toString")
            .build();

    MethodInvocationExpr secondArg =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(STATIC_TYPES.get("Arrays"))
            .setGenerics(Arrays.asList(STATIC_TYPES.get("MockGrpcService").reference()))
            .setMethodName("asList")
            .setArguments(Arrays.asList(mockServiceVarExpr))
            .build();

    Expr initServiceHelperExpr =
        AssignmentExpr.builder()
            .setVariableExpr(serviceHelperVarExpr)
            .setValueExpr(
                NewObjectExpr.builder()
                    .setType(serviceHelperVarExpr.type())
                    .setArguments(Arrays.asList(firstArg, secondArg))
                    .build())
            .build();

    Expr startServiceHelperExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(serviceHelperVarExpr)
            .setMethodName("start")
            .build();

    return MethodDefinition.builder()
        .setAnnotations(Arrays.asList(AnnotationNode.withType(STATIC_TYPES.get("BeforeClass"))))
        .setScope(ScopeNode.PUBLIC)
        .setIsStatic(true)
        .setReturnType(TypeNode.VOID)
        .setName("startStaticServer")
        .setBody(
            Arrays.asList(initMockServiceExpr, initServiceHelperExpr, startServiceHelperExpr)
                .stream()
                .map(e -> ExprStatement.withExpr(e))
                .collect(Collectors.toList()))
        .build();
  }

  private static MethodDefinition createStopServerMethod(
      Service service, Map<String, VariableExpr> classMemberVarExprs) {
    return MethodDefinition.builder()
        .setAnnotations(Arrays.asList(AnnotationNode.withType(STATIC_TYPES.get("AfterClass"))))
        .setScope(ScopeNode.PUBLIC)
        .setIsStatic(true)
        .setReturnType(TypeNode.VOID)
        .setName("stopServer")
        .setBody(
            Arrays.asList(
                ExprStatement.withExpr(
                    MethodInvocationExpr.builder()
                        .setExprReferenceExpr(classMemberVarExprs.get(SERVICE_HELPER_VAR_NAME))
                        .setMethodName("stop")
                        .build())))
        .build();
  }

  private static MethodDefinition createSetUpMethod(
      Service service, Map<String, VariableExpr> classMemberVarExprs, Map<String, TypeNode> types) {
    VariableExpr clientVarExpr = classMemberVarExprs.get(CLIENT_VAR_NAME);
    VariableExpr serviceHelperVarExpr = classMemberVarExprs.get(SERVICE_HELPER_VAR_NAME);
    VariableExpr channelProviderVarExpr = classMemberVarExprs.get(CHANNEL_PROVIDER_VAR_NAME);

    Expr resetServiceHelperExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(serviceHelperVarExpr)
            .setMethodName("reset")
            .build();
    Expr channelProviderInitExpr =
        AssignmentExpr.builder()
            .setVariableExpr(channelProviderVarExpr)
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(serviceHelperVarExpr)
                    .setMethodName("createChannelProvider")
                    .setReturnType(channelProviderVarExpr.type())
                    .build())
            .build();

    TypeNode settingsType = types.get(getServiceSettingsClassName(service.name()));
    VariableExpr localSettingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("settings").setType(settingsType).build());

    Expr settingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(settingsType)
            .setMethodName("newBuilder")
            .build();
    Function<Expr, BiFunction<String, Expr, MethodInvocationExpr>> methodBuilderFn =
        methodExpr ->
            (mName, argExpr) ->
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(methodExpr)
                    .setMethodName(mName)
                    .setArguments(Arrays.asList(argExpr))
                    .build();
    settingsBuilderExpr =
        methodBuilderFn
            .apply(settingsBuilderExpr)
            .apply(
                "setTransportChannelProvider", classMemberVarExprs.get(CHANNEL_PROVIDER_VAR_NAME));
    settingsBuilderExpr =
        methodBuilderFn
            .apply(settingsBuilderExpr)
            .apply(
                "setCredentialsProvider",
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(STATIC_TYPES.get("NoCredentialsProvider"))
                    .setMethodName("create")
                    .build());
    settingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(settingsBuilderExpr)
            .setMethodName("build")
            .setReturnType(localSettingsVarExpr.type())
            .build();

    Expr initLocalSettingsExpr =
        AssignmentExpr.builder()
            .setVariableExpr(localSettingsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(settingsBuilderExpr)
            .build();

    Expr initClientExpr =
        AssignmentExpr.builder()
            .setVariableExpr(clientVarExpr)
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(types.get(getClientClassName(service.name())))
                    .setMethodName("create")
                    .setArguments(Arrays.asList(localSettingsVarExpr))
                    .setReturnType(clientVarExpr.type())
                    .build())
            .build();

    return MethodDefinition.builder()
        .setAnnotations(Arrays.asList(AnnotationNode.withType(STATIC_TYPES.get("Before"))))
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName("setUp")
        .setThrowsExceptions(Arrays.asList(STATIC_TYPES.get("IOException")))
        .setBody(
            Arrays.asList(
                    resetServiceHelperExpr,
                    channelProviderInitExpr,
                    initLocalSettingsExpr,
                    initClientExpr)
                .stream()
                .map(e -> ExprStatement.withExpr(e))
                .collect(Collectors.toList()))
        .build();
  }

  private static MethodDefinition createTearDownMethod(
      Service service, Map<String, VariableExpr> classMemberVarExprs) {
    return MethodDefinition.builder()
        .setAnnotations(Arrays.asList(AnnotationNode.withType(STATIC_TYPES.get("After"))))
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName("tearDown")
        .setThrowsExceptions(
            Arrays.asList(TypeNode.withReference(ConcreteReference.withClazz(Exception.class))))
        .setBody(
            Arrays.asList(
                ExprStatement.withExpr(
                    MethodInvocationExpr.builder()
                        .setExprReferenceExpr(classMemberVarExprs.get(CLIENT_VAR_NAME))
                        .setMethodName("close")
                        .build())))
        .build();
  }

  private static List<MethodDefinition> createTestMethods(
      Service service,
      Map<String, VariableExpr> classMemberVarExprs,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes) {
    List<MethodDefinition> javaMethods = new ArrayList<>();
    for (Method method : service.methods()) {
      if (method.methodSignatures().isEmpty()) {
        javaMethods.add(
            createRpcExceptionTestMethod(
                method,
                Collections.emptyList(),
                0,
                service.name(),
                classMemberVarExprs,
                resourceNames,
                messageTypes));
      } else {
        // Make the method signature order deterministic, which helps with unit testing and
        // per-version
        // diffs.
        List<List<MethodArgument>> sortedMethodSignatures =
            method.methodSignatures().stream()
                .sorted(
                    (s1, s2) -> {
                      if (s1.size() != s2.size()) {
                        return s1.size() - s2.size();
                      }
                      for (int i = 0; i < s1.size(); i++) {
                        int compareVal = s1.get(i).compareTo(s2.get(i));
                        if (compareVal != 0) {
                          return compareVal;
                        }
                      }
                      return 0;
                    })
                .collect(Collectors.toList());

        for (int i = 0; i < sortedMethodSignatures.size(); i++) {
          javaMethods.add(
              createRpcExceptionTestMethod(
                  method,
                  sortedMethodSignatures.get(i),
                  i,
                  service.name(),
                  classMemberVarExprs,
                  resourceNames,
                  messageTypes));
        }
      }
    }
    return javaMethods;
  }

  private static MethodDefinition createRpcExceptionTestMethod(
      Method method,
      List<MethodArgument> methodSignature,
      int variantIndex,
      String serviceName,
      Map<String, VariableExpr> classMemberVarExprs,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes) {
    VariableExpr exceptionVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setType(STATIC_TYPES.get("StatusRuntimeException"))
                .setName("exception")
                .build());

    // First two assignment lines.
    Expr exceptionAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(exceptionVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(
                NewObjectExpr.builder()
                    .setType(STATIC_TYPES.get("StatusRuntimeException"))
                    .setArguments(
                        EnumRefExpr.builder()
                            .setType(GRPC_STATUS_TYPE)
                            .setName("INVALID_ARGUMENT")
                            .build())
                    .build())
            .build();
    Expr addExceptionExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(classMemberVarExprs.get(getMockServiceVarName(serviceName)))
            .setMethodName("addException")
            .setArguments(exceptionVarExpr)
            .build();

    // Try-catch block. Build the method call.
    String exceptionTestMethodName =
        String.format(
            "%sExceptionTest%s",
            JavaStyle.toLowerCamelCase(method.name()), variantIndex > 0 ? variantIndex + 1 : "");

    boolean isStreaming = !method.stream().equals(Method.Stream.NONE);
    List<Statement> methodBody = new ArrayList<>();
    methodBody.add(ExprStatement.withExpr(exceptionAssignExpr));
    methodBody.add(ExprStatement.withExpr(addExceptionExpr));
    if (isStreaming) {
      methodBody.addAll(
          createRpcExceptionTestStreamingStatements(
              method, classMemberVarExprs, resourceNames, messageTypes));
    } else {
      methodBody.addAll(
          createRpcExceptionTestNonStreamingStatements(
              method, methodSignature, classMemberVarExprs, resourceNames, messageTypes));
    }

    return MethodDefinition.builder()
        .setAnnotations(Arrays.asList(TEST_ANNOTATION))
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName(exceptionTestMethodName)
        .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(Exception.class)))
        .setBody(methodBody)
        .build();
  }

  private static List<Statement> createRpcExceptionTestStreamingStatements(
      Method method,
      Map<String, VariableExpr> classMemberVarExprs,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes) {
    // Build the request variable and assign it.
    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(method.inputType()).setName("request").build());
    Message requestMessage = messageTypes.get(method.inputType().reference().name());
    Preconditions.checkNotNull(requestMessage);
    Expr valExpr =
        DefaultValueComposer.createSimpleMessageBuilderExpr(
            requestMessage, resourceNames, messageTypes);

    List<Expr> exprs = new ArrayList<>();
    exprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(requestVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(valExpr)
            .build());

    // Build the responseObserver variable.
    VariableExpr responseObserverVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setType(
                    TypeNode.withReference(
                        STATIC_TYPES
                            .get("MockStreamObserver")
                            .reference()
                            .copyAndSetGenerics(Arrays.asList(method.outputType().reference()))))
                .setName("responseObserver")
                .build());

    exprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(responseObserverVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(
                NewObjectExpr.builder()
                    .setType(STATIC_TYPES.get("MockStreamObserver"))
                    .setIsGeneric(true)
                    .build())
            .build());

    // Build the callable variable and assign it.
    VariableExpr callableVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(getCallableType(method)).setName("callable").build());
    Expr streamingCallExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(classMemberVarExprs.get("client"))
            .setMethodName(String.format("%sCallable", JavaStyle.toLowerCamelCase(method.name())))
            .setReturnType(callableVarExpr.type())
            .build();
    if (method.stream().equals(Method.Stream.SERVER)) {
      exprs.add(streamingCallExpr);
    } else {
      exprs.add(
          AssignmentExpr.builder()
              .setVariableExpr(callableVarExpr.toBuilder().setIsDecl(true).build())
              .setValueExpr(streamingCallExpr)
              .build());
    }

    if (!method.stream().equals(Method.Stream.SERVER)) {
      // Call the streaming-variant callable method.
      VariableExpr requestObserverVarExpr =
          VariableExpr.withVariable(
              Variable.builder()
                  .setType(
                      TypeNode.withReference(
                          STATIC_TYPES
                              .get("ApiStreamObserver")
                              .reference()
                              .copyAndSetGenerics(Arrays.asList(method.inputType().reference()))))
                  .setName("requestObserver")
                  .build());
      exprs.add(
          AssignmentExpr.builder()
              .setVariableExpr(requestObserverVarExpr.toBuilder().setIsDecl(true).build())
              .setValueExpr(
                  MethodInvocationExpr.builder()
                      .setExprReferenceExpr(callableVarExpr)
                      .setMethodName(getCallableMethodName(method))
                      .setArguments(requestVarExpr, responseObserverVarExpr)
                      .setReturnType(requestObserverVarExpr.type())
                      .build())
              .build());

      exprs.add(
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(requestObserverVarExpr)
              .setMethodName("onNext")
              .setArguments(requestVarExpr)
              .build());
    }

    List<Expr> tryBodyExprs = new ArrayList<>();
    // TODO(v2): This variable is unused in the generated test, it can be deleted.
    VariableExpr actualResponsesVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setType(
                    TypeNode.withReference(
                        ConcreteReference.builder()
                            .setClazz(List.class)
                            .setGenerics(Arrays.asList(method.outputType().reference()))
                            .build()))
                .setName("actualResponses")
                .build());

    Expr getFutureResponseExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(responseObserverVarExpr)
            .setMethodName("future")
            .build();
    getFutureResponseExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(getFutureResponseExpr)
            .setMethodName("get")
            .setReturnType(actualResponsesVarExpr.type())
            .build();
    tryBodyExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(actualResponsesVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(getFutureResponseExpr)
            .build());
    // Assert a failure if no exception was raised.
    tryBodyExprs.add(
        MethodInvocationExpr.builder()
            .setStaticReferenceType(STATIC_TYPES.get("Assert"))
            .setMethodName("fail")
            .setArguments(ValueExpr.withValue(StringObjectValue.withValue("No exception thrown")))
            .build());

    VariableExpr catchExceptionVarExpr =
        VariableExpr.builder()
            .setVariable(
                Variable.builder()
                    .setType(TypeNode.withExceptionClazz(ExecutionException.class))
                    .setName("e")
                    .build())
            .build();

    TryCatchStatement tryCatchBlock =
        TryCatchStatement.builder()
            .setTryBody(
                tryBodyExprs.stream()
                    .map(e -> ExprStatement.withExpr(e))
                    .collect(Collectors.toList()))
            .setCatchVariableExpr(catchExceptionVarExpr.toBuilder().setIsDecl(true).build())
            .setCatchBody(createRpcLroExceptionTestCatchBody(catchExceptionVarExpr, true))
            .build();

    List<Statement> statements = new ArrayList<>();
    statements.addAll(
        exprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()));
    statements.add(tryCatchBlock);
    return statements;
  }

  private static List<Statement> createRpcExceptionTestNonStreamingStatements(
      Method method,
      List<MethodArgument> methodSignature,
      Map<String, VariableExpr> classMemberVarExprs,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes) {
    List<VariableExpr> argVarExprs = new ArrayList<>();
    List<Expr> tryBodyExprs = new ArrayList<>();
    if (methodSignature.isEmpty()) {
      // Construct the actual request.
      VariableExpr varExpr =
          VariableExpr.withVariable(
              Variable.builder().setType(method.inputType()).setName("request").build());
      argVarExprs.add(varExpr);
      Message requestMessage = messageTypes.get(method.inputType().reference().name());
      Preconditions.checkNotNull(requestMessage);
      Expr valExpr =
          DefaultValueComposer.createSimpleMessageBuilderExpr(
              requestMessage, resourceNames, messageTypes);
      tryBodyExprs.add(
          AssignmentExpr.builder()
              .setVariableExpr(varExpr.toBuilder().setIsDecl(true).build())
              .setValueExpr(valExpr)
              .build());
    } else {
      for (MethodArgument methodArg : methodSignature) {
        VariableExpr varExpr =
            VariableExpr.withVariable(
                Variable.builder().setType(methodArg.type()).setName(methodArg.name()).build());
        argVarExprs.add(varExpr);
        Expr valExpr = DefaultValueComposer.createDefaultValue(methodArg, resourceNames);
        tryBodyExprs.add(
            AssignmentExpr.builder()
                .setVariableExpr(varExpr.toBuilder().setIsDecl(true).build())
                .setValueExpr(valExpr)
                .build());
        // TODO(miraleung): Empty line here.
      }
    }
    String rpcJavaName = JavaStyle.toLowerCamelCase(method.name());
    if (method.hasLro()) {
      rpcJavaName += "Async";
    }
    MethodInvocationExpr rpcJavaMethodInvocationExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(classMemberVarExprs.get("client"))
            .setMethodName(rpcJavaName)
            .setArguments(argVarExprs.stream().map(e -> (Expr) e).collect(Collectors.toList()))
            .build();
    if (method.hasLro()) {
      rpcJavaMethodInvocationExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(rpcJavaMethodInvocationExpr)
              .setMethodName("get")
              .build();
    }
    tryBodyExprs.add(rpcJavaMethodInvocationExpr);

    VariableExpr catchExceptionVarExpr =
        VariableExpr.builder()
            .setVariable(
                Variable.builder()
                    .setType(
                        TypeNode.withExceptionClazz(
                            method.hasLro()
                                ? ExecutionException.class
                                : InvalidArgumentException.class))
                    .setName("e")
                    .build())
            .build();

    List<Statement> catchBody =
        method.hasLro()
            ? createRpcLroExceptionTestCatchBody(catchExceptionVarExpr, false)
            : Arrays.asList(
                CommentStatement.withComment(LineComment.withComment("Expected exception.")));
    // Assert a failure if no exception was raised.
    tryBodyExprs.add(
        MethodInvocationExpr.builder()
            .setStaticReferenceType(STATIC_TYPES.get("Assert"))
            .setMethodName("fail")
            .setArguments(ValueExpr.withValue(StringObjectValue.withValue("No exception raised")))
            .build());

    TryCatchStatement tryCatchBlock =
        TryCatchStatement.builder()
            .setTryBody(
                tryBodyExprs.stream()
                    .map(e -> ExprStatement.withExpr(e))
                    .collect(Collectors.toList()))
            .setCatchVariableExpr(catchExceptionVarExpr.toBuilder().setIsDecl(true).build())
            .setCatchBody(catchBody)
            .build();

    return Arrays.asList(tryCatchBlock);
  }

  private static List<Statement> createRpcLroExceptionTestCatchBody(
      VariableExpr exceptionExpr, boolean isStreaming) {
    List<Expr> catchBodyExprs = new ArrayList<>();

    Expr testExpectedValueExpr =
        VariableExpr.builder()
            .setVariable(
                Variable.builder()
                    .setType(TypeNode.withReference(ConcreteReference.withClazz(Class.class)))
                    .setName("class")
                    .build())
            .setStaticReferenceType(STATIC_TYPES.get("InvalidArgumentException"))
            .build();
    Expr getCauseExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(exceptionExpr)
            .setMethodName("getCause")
            .setReturnType(TypeNode.withReference(ConcreteReference.withClazz(Throwable.class)))
            .build();
    Expr testActualValueExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(getCauseExpr)
            .setMethodName("getClass")
            .build();

    if (isStreaming) {
      InstanceofExpr checkInstanceExpr =
          InstanceofExpr.builder()
              .setExpr(getCauseExpr)
              .setCheckType(STATIC_TYPES.get("InvalidArgumentException"))
              .build();
      catchBodyExprs.add(
          MethodInvocationExpr.builder()
              .setStaticReferenceType(STATIC_TYPES.get("Assert"))
              .setMethodName("assertTrue")
              .setArguments(checkInstanceExpr)
              .build());
    } else {
      // Constructs `Assert.assertEquals(InvalidArgumentException.class, e.getCaus().getClass());`.
      catchBodyExprs.add(
          MethodInvocationExpr.builder()
              .setStaticReferenceType(STATIC_TYPES.get("Assert"))
              .setMethodName("assertEquals")
              .setArguments(testExpectedValueExpr, testActualValueExpr)
              .build());
    }

    // Construct the apiException variable.
    VariableExpr apiExceptionVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setType(STATIC_TYPES.get("InvalidArgumentException"))
                .setName("apiException")
                .build());
    Expr castedCauseExpr =
        CastExpr.builder()
            .setType(STATIC_TYPES.get("InvalidArgumentException"))
            .setExpr(getCauseExpr)
            .build();
    catchBodyExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(apiExceptionVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(castedCauseExpr)
            .build());

    // Construct the last assert statement.
    testExpectedValueExpr =
        EnumRefExpr.builder()
            .setType(
                TypeNode.withReference(
                    ConcreteReference.builder()
                        .setClazz(StatusCode.Code.class)
                        .setIsStaticImport(false)
                        .build()))
            .setName("INVALID_ARGUMENT")
            .build();
    testActualValueExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(apiExceptionVarExpr)
            .setMethodName("getStatusCode")
            .build();
    testActualValueExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(testActualValueExpr)
            .setMethodName("getCode")
            .build();
    catchBodyExprs.add(
        MethodInvocationExpr.builder()
            .setStaticReferenceType(STATIC_TYPES.get("Assert"))
            .setMethodName("assertEquals")
            .setArguments(testExpectedValueExpr, testActualValueExpr)
            .build());

    return catchBodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());
  }

  /* =========================================
   * Type creator methods.
   * =========================================
   */

  private static Map<String, TypeNode> createStaticTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            AbstractMessage.class,
            After.class,
            AfterClass.class,
            Any.class,
            ApiClientHeaderProvider.class,
            ApiStreamObserver.class,
            Arrays.class,
            Assert.class,
            Before.class,
            BeforeClass.class,
            BidiStreamingCallable.class,
            ClientStreamingCallable.class,
            ExecutionException.class,
            GaxGrpcProperties.class,
            Generated.class,
            IOException.class,
            InvalidArgumentException.class,
            List.class,
            Lists.class,
            NoCredentialsProvider.class,
            Operation.class,
            ServerStreamingCallable.class,
            StatusCode.class,
            StatusRuntimeException.class,
            Test.class,
            UUID.class);
    Map<String, TypeNode> STATIC_TYPES =
        concreteClazzes.stream()
            .collect(
                Collectors.toMap(
                    c -> c.getSimpleName(),
                    c -> TypeNode.withReference(ConcreteReference.withClazz(c))));

    STATIC_TYPES.putAll(
        Arrays.asList(
                "LocalChannelProvider",
                "MockGrpcService",
                "MockServiceHelper",
                "MockStreamObserver")
            .stream()
            .collect(
                Collectors.toMap(
                    n -> n,
                    n ->
                        TypeNode.withReference(
                            VaporReference.builder()
                                .setName(n)
                                .setPakkage(GRPC_TESTING_PACKAGE)
                                .build()))));
    return STATIC_TYPES;
  }

  private static Map<String, TypeNode> createDefaultMethodNamesToTypes() {
    Function<Class, TypeNode> typeMakerFn =
        c -> TypeNode.withReference(ConcreteReference.withClazz(c));
    Map<String, TypeNode> javaMethodNameToReturnType = new LinkedHashMap<>();
    javaMethodNameToReturnType.put(
        "defaultExecutorProviderBuilder",
        typeMakerFn.apply(InstantiatingExecutorProvider.Builder.class));
    javaMethodNameToReturnType.put("getDefaultEndpoint", TypeNode.STRING);
    javaMethodNameToReturnType.put(
        "getDefaultServiceScopes",
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(List.class)
                .setGenerics(Arrays.asList(TypeNode.STRING.reference()))
                .build()));
    javaMethodNameToReturnType.put(
        "defaultCredentialsProviderBuilder",
        typeMakerFn.apply(GoogleCredentialsProvider.Builder.class));
    javaMethodNameToReturnType.put(
        "defaultGrpcTransportProviderBuilder",
        typeMakerFn.apply(InstantiatingGrpcChannelProvider.Builder.class));
    javaMethodNameToReturnType.put(
        "defaultTransportChannelProvider", STATIC_TYPES.get("TransportChannelProvider"));
    return javaMethodNameToReturnType;
  }

  private static Map<String, TypeNode> createDynamicTypes(Service service) {
    Map<String, TypeNode> types = new HashMap<>();

    // ServiceStubSettings class.
    String stubSettingsClassName = String.format(STUB_SETTINGS_PATTERN, service.name());
    types.put(
        stubSettingsClassName,
        TypeNode.withReference(
            VaporReference.builder()
                .setName(stubSettingsClassName)
                .setPakkage(String.format("%s.stub", service.pakkage()))
                .build()));

    // Classes in the same package.
    types.putAll(
        Arrays.asList(
                SERVICE_CLIENT_CLASS_NAME_PATTERN,
                SERVICE_SETTINGS_CLASS_NAME_PATTERN,
                MOCK_SERVICE_CLASS_NAME_PATTERN)
            .stream()
            .collect(
                Collectors.toMap(
                    p -> String.format(p, service.name()),
                    p ->
                        TypeNode.withReference(
                            VaporReference.builder()
                                .setName(String.format(p, service.name()))
                                .setPakkage(service.pakkage())
                                .build()))));

    // Pagination types.
    types.putAll(
        service.methods().stream()
            .filter(m -> m.isPaged())
            .collect(
                Collectors.toMap(
                    m -> String.format(PAGED_RESPONSE_TYPE_NAME_PATTERN, m.name()),
                    m ->
                        TypeNode.withReference(
                            VaporReference.builder()
                                .setName(String.format(PAGED_RESPONSE_TYPE_NAME_PATTERN, m.name()))
                                .setPakkage(service.pakkage())
                                .setEnclosingClassName(getClientClassName(service.name()))
                                .setIsStaticImport(true)
                                .build()))));
    return types;
  }

  private static TypeNode getOperationCallSettingsType(Method protoMethod) {
    return getOperationCallSettingsTypeHelper(protoMethod, false);
  }

  private static TypeNode getOperationCallSettingsBuilderType(Method protoMethod) {
    return getOperationCallSettingsTypeHelper(protoMethod, true);
  }

  private static TypeNode getOperationCallSettingsTypeHelper(
      Method protoMethod, boolean isBuilder) {
    Preconditions.checkState(
        protoMethod.hasLro(),
        String.format("Cannot get OperationCallSettings on non-LRO method %s", protoMethod.name()));
    Class callSettingsClazz =
        isBuilder ? OperationCallSettings.Builder.class : OperationCallSettings.class;
    return TypeNode.withReference(
        ConcreteReference.builder()
            .setClazz(callSettingsClazz)
            .setGenerics(
                Arrays.asList(
                    protoMethod.inputType().reference(),
                    protoMethod.lro().responseType().reference(),
                    protoMethod.lro().metadataType().reference()))
            .build());
  }

  private static TypeNode getCallSettingsType(Method protoMethod, Map<String, TypeNode> types) {
    return getCallSettingsTypeHelper(protoMethod, types, false);
  }

  private static TypeNode getCallSettingsBuilderType(
      Method protoMethod, Map<String, TypeNode> types) {
    return getCallSettingsTypeHelper(protoMethod, types, true);
  }

  private static TypeNode getCallSettingsTypeHelper(
      Method protoMethod, Map<String, TypeNode> types, boolean isBuilder) {
    Class callSettingsClazz = isBuilder ? UnaryCallSettings.Builder.class : UnaryCallSettings.class;
    if (protoMethod.isPaged()) {
      callSettingsClazz = isBuilder ? PagedCallSettings.Builder.class : PagedCallSettings.class;
    } else {
      switch (protoMethod.stream()) {
        case CLIENT:
          // Fall through.
        case BIDI:
          callSettingsClazz =
              isBuilder ? StreamingCallSettings.Builder.class : StreamingCallSettings.class;
          break;
        case SERVER:
          callSettingsClazz =
              isBuilder
                  ? ServerStreamingCallSettings.Builder.class
                  : ServerStreamingCallSettings.class;
          break;
        case NONE:
          // Fall through
        default:
          // Fall through
      }
    }

    List<Reference> generics = new ArrayList<>();
    generics.add(protoMethod.inputType().reference());
    generics.add(protoMethod.outputType().reference());
    if (protoMethod.isPaged()) {
      generics.add(
          types
              .get(String.format(PAGED_RESPONSE_TYPE_NAME_PATTERN, protoMethod.name()))
              .reference());
    }

    return TypeNode.withReference(
        ConcreteReference.builder().setClazz(callSettingsClazz).setGenerics(generics).build());
  }

  private static TypeNode getCallableType(Method protoMethod) {
    Preconditions.checkState(
        !protoMethod.stream().equals(Method.Stream.NONE),
        "No callable type exists for non-streaming methods.");

    Class callableClazz = ClientStreamingCallable.class;
    switch (protoMethod.stream()) {
      case BIDI:
        callableClazz = BidiStreamingCallable.class;
        break;
      case SERVER:
        callableClazz = ServerStreamingCallable.class;
        break;
      case CLIENT:
        // Fall through.
      case NONE:
        // Fall through
      default:
        // Fall through
    }

    List<Reference> generics = new ArrayList<>();
    generics.add(protoMethod.inputType().reference());
    generics.add(protoMethod.outputType().reference());

    return TypeNode.withReference(
        ConcreteReference.builder().setClazz(callableClazz).setGenerics(generics).build());
  }

  private static String getCallableMethodName(Method protoMethod) {
    Preconditions.checkState(
        !protoMethod.stream().equals(Method.Stream.NONE),
        "No callable type exists for non-streaming methods.");

    switch (protoMethod.stream()) {
      case BIDI:
        return "bidiStreamingCall";
      case SERVER:
        return "serverStreamingCall";
      case CLIENT:
        // Fall through.
      case NONE:
        // Fall through
      default:
        return "clientStreamingCall";
    }
  }

  private static String getClientClassName(String serviceName) {
    return String.format(SERVICE_CLIENT_CLASS_NAME_PATTERN, serviceName);
  }

  private static String getServiceSettingsClassName(String serviceName) {
    return String.format(SERVICE_SETTINGS_CLASS_NAME_PATTERN, serviceName);
  }

  private static String getMockServiceVarName(String serviceName) {
    return String.format(MOCK_SERVICE_VAR_NAME_PATTERN, serviceName);
  }
}
