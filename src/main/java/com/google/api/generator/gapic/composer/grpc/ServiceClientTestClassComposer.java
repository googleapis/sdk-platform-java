// Copyright 2021 Google LLC
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

package com.google.api.generator.gapic.composer.grpc;

import com.google.api.gax.grpc.GaxGrpcProperties;
import com.google.api.gax.grpc.testing.LocalChannelProvider;
import com.google.api.gax.grpc.testing.MockGrpcService;
import com.google.api.gax.grpc.testing.MockServiceHelper;
import com.google.api.gax.grpc.testing.MockStreamObserver;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.CastExpr;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.EnumRefExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.common.AbstractServiceClientTestClassComposer;
import com.google.api.generator.gapic.composer.store.TypeStore;
import com.google.api.generator.gapic.composer.utils.ClassNames;
import com.google.api.generator.gapic.model.Field;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.MethodArgument;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.base.Preconditions;
import com.google.protobuf.AbstractMessage;
import io.grpc.StatusRuntimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ServiceClientTestClassComposer extends AbstractServiceClientTestClassComposer {
  private static final String SERVICE_HELPER_VAR_NAME = "mockServiceHelper";
  private static final String CHANNEL_PROVIDER_VAR_NAME = "channelProvider";

  private static final TypeNode LIST_TYPE =
      TypeNode.withReference(ConcreteReference.withClazz(List.class));
  private static final TypeNode MAP_TYPE =
      TypeNode.withReference(ConcreteReference.withClazz(Map.class));
  private static final TypeNode RESOURCE_NAME_TYPE =
      TypeNode.withReference(
          ConcreteReference.withClazz(com.google.api.resourcenames.ResourceName.class));

  // Avoid conflicting types with com.google.rpc.Status.
  private static final TypeNode GRPC_STATUS_TYPE =
      TypeNode.withReference(
          ConcreteReference.builder().setClazz(io.grpc.Status.class).setUseFullName(true).build());

  protected ServiceClientTestClassComposer() {
    super(createTypes(), new ClassNames("Grpc"));
  }

  private static final ServiceClientTestClassComposer INSTANCE =
      new ServiceClientTestClassComposer();

  public static AbstractServiceClientTestClassComposer instance() {
    return INSTANCE;
  }

  protected static TypeStore createTypes() {
    TypeStore typeStore = createCommonTypes();
    typeStore.putAll(
        Arrays.asList(
            GaxGrpcProperties.class,
            LocalChannelProvider.class,
            MockGrpcService.class,
            MockServiceHelper.class,
            MockStreamObserver.class,
            StatusRuntimeException.class));

    return typeStore;
  }

  @Override
  protected Map<String, VariableExpr> createClassMemberVarExprs(
      Service service, GapicContext context, TypeStore typeStore) {
    BiFunction<String, TypeNode, VariableExpr> varExprFn =
        (name, type) ->
            VariableExpr.withVariable(Variable.builder().setName(name).setType(type).build());
    Map<String, TypeNode> fields = new LinkedHashMap<>();
    fields.put(
        getMockServiceVarName(service), typeStore.get(ClassNames.getMockServiceClassName(service)));
    for (Service mixinService : context.mixinServices()) {
      fields.put(
          getMockServiceVarName(mixinService),
          typeStore.get(ClassNames.getMockServiceClassName(mixinService)));
    }
    fields.put(SERVICE_HELPER_VAR_NAME, getFixedTypeStore().get("MockServiceHelper"));
    fields.put(CLIENT_VAR_NAME, typeStore.get(ClassNames.getServiceClientClassName(service)));
    fields.put(CHANNEL_PROVIDER_VAR_NAME, getFixedTypeStore().get("LocalChannelProvider"));
    return fields.entrySet().stream()
        .collect(Collectors.toMap(e -> e.getKey(), e -> varExprFn.apply(e.getKey(), e.getValue())));
  }

  @Override
  protected MethodDefinition createStartStaticServerMethod(
      Service service,
      GapicContext context,
      Map<String, VariableExpr> classMemberVarExprs,
      TypeStore typeStore) {
    VariableExpr serviceHelperVarExpr = classMemberVarExprs.get(SERVICE_HELPER_VAR_NAME);
    Function<Service, VariableExpr> serviceToVarExprFn =
        s -> classMemberVarExprs.get(getMockServiceVarName(s));
    Function<Service, Expr> serviceToVarInitExprFn =
        s -> {
          VariableExpr mockServiceVarExpr = serviceToVarExprFn.apply(s);
          return AssignmentExpr.builder()
              .setVariableExpr(mockServiceVarExpr)
              .setValueExpr(NewObjectExpr.builder().setType(mockServiceVarExpr.type()).build())
              .build();
        };
    List<Expr> varInitExprs = new ArrayList<>();
    List<Expr> mockServiceVarExprs = new ArrayList<>();
    varInitExprs.add(serviceToVarInitExprFn.apply(service));
    mockServiceVarExprs.add(serviceToVarExprFn.apply(service));
    for (Service mixinService : context.mixinServices()) {
      varInitExprs.add(serviceToVarInitExprFn.apply(mixinService));
      mockServiceVarExprs.add(serviceToVarExprFn.apply(mixinService));
    }

    MethodInvocationExpr firstArg =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(getFixedTypeStore().get("UUID"))
            .setMethodName("randomUUID")
            .build();
    firstArg =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(firstArg)
            .setMethodName("toString")
            .build();

    MethodInvocationExpr secondArg =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(getFixedTypeStore().get("Arrays"))
            .setGenerics(Arrays.asList(getFixedTypeStore().get("MockGrpcService").reference()))
            .setMethodName("asList")
            .setArguments(mockServiceVarExprs)
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
    varInitExprs.add(initServiceHelperExpr);
    varInitExprs.add(startServiceHelperExpr);

    return MethodDefinition.builder()
        .setAnnotations(
            Arrays.asList(AnnotationNode.withType(getFixedTypeStore().get("BeforeClass"))))
        .setScope(ScopeNode.PUBLIC)
        .setIsStatic(true)
        .setReturnType(TypeNode.VOID)
        .setName("startStaticServer")
        .setBody(
            varInitExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()))
        .build();
  }

  @Override
  protected MethodDefinition createStopServerMethod(
      Service service, Map<String, VariableExpr> classMemberVarExprs) {
    return MethodDefinition.builder()
        .setAnnotations(
            Arrays.asList(AnnotationNode.withType(getFixedTypeStore().get("AfterClass"))))
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

  @Override
  protected MethodDefinition createSetUpMethod(
      Service service, Map<String, VariableExpr> classMemberVarExprs, TypeStore typeStore) {
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

    TypeNode settingsType = typeStore.get(ClassNames.getServiceSettingsClassName(service));
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
                    .setStaticReferenceType(getFixedTypeStore().get("NoCredentialsProvider"))
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
                    .setStaticReferenceType(
                        typeStore.get(ClassNames.getServiceClientClassName(service)))
                    .setMethodName("create")
                    .setArguments(Arrays.asList(localSettingsVarExpr))
                    .setReturnType(clientVarExpr.type())
                    .build())
            .build();

    return MethodDefinition.builder()
        .setAnnotations(Arrays.asList(AnnotationNode.withType(getFixedTypeStore().get("Before"))))
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName("setUp")
        .setThrowsExceptions(Arrays.asList(getFixedTypeStore().get("IOException")))
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

  @Override
  protected MethodDefinition createTearDownMethod(
      Service service, Map<String, VariableExpr> classMemberVarExprs) {
    return MethodDefinition.builder()
        .setAnnotations(Arrays.asList(AnnotationNode.withType(getFixedTypeStore().get("After"))))
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

  @Override
  protected List<Statement> constructRpcTestCheckerLogic(
      Method method,
      Service service,
      boolean isRequestArg,
      Map<String, VariableExpr> classMemberVarExprs,
      VariableExpr requestVarExpr,
      Message requestMessage,
      List<VariableExpr> argExprs) {
    List<Expr> methodExprs = new ArrayList<>();
    List<Statement> methodStatements = new ArrayList<>();

    // Construct the request checker logic.
    VariableExpr actualRequestsVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setType(
                    TypeNode.withReference(
                        ConcreteReference.builder()
                            .setClazz(List.class)
                            .setGenerics(
                                Arrays.asList(ConcreteReference.withClazz(AbstractMessage.class)))
                            .build()))
                .setName("actualRequests")
                .build());
    methodExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(actualRequestsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(classMemberVarExprs.get(getMockServiceVarName(service)))
                    .setMethodName("getRequests")
                    .setReturnType(actualRequestsVarExpr.type())
                    .build())
            .build());

    methodExprs.add(
        MethodInvocationExpr.builder()
            .setStaticReferenceType(getFixedTypeStore().get("Assert"))
            .setMethodName("assertEquals")
            .setArguments(
                ValueExpr.withValue(
                    PrimitiveValue.builder().setType(TypeNode.INT).setValue("1").build()),
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(actualRequestsVarExpr)
                    .setMethodName("size")
                    .build())
            .build());

    VariableExpr actualRequestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(method.inputType()).setName("actualRequest").build());
    Expr getFirstRequestExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(actualRequestsVarExpr)
            .setMethodName("get")
            .setArguments(
                ValueExpr.withValue(
                    PrimitiveValue.builder().setType(TypeNode.INT).setValue("0").build()))
            .setReturnType(getFixedTypeStore().get("AbstractMessage"))
            .build();
    getFirstRequestExpr =
        CastExpr.builder().setType(method.inputType()).setExpr(getFirstRequestExpr).build();
    methodExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(actualRequestVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(getFirstRequestExpr)
            .build());
    methodStatements.addAll(
        methodExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()));
    methodExprs.clear();
    methodStatements.add(EMPTY_LINE_STATEMENT);

    // Assert field equality.
    if (isRequestArg) {
      // TODO(miraleung): Replace these with a simple request object equals?
      Preconditions.checkNotNull(requestVarExpr);
      Preconditions.checkNotNull(requestMessage);
      for (Field field : requestMessage.fields()) {
        String fieldGetterMethodNamePatternTemp = "get%s";
        if (field.isRepeated()) {
          fieldGetterMethodNamePatternTemp = field.isMap() ? "get%sMap" : "get%sList";
        }
        final String fieldGetterMethodNamePattern = fieldGetterMethodNamePatternTemp;
        Function<VariableExpr, Expr> checkExprFn =
            v ->
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(v)
                    .setMethodName(
                        String.format(
                            fieldGetterMethodNamePattern, JavaStyle.toUpperCamelCase(field.name())))
                    .build();
        Expr expectedFieldExpr = checkExprFn.apply(requestVarExpr);
        Expr actualFieldExpr = checkExprFn.apply(actualRequestVarExpr);
        List<Expr> assertEqualsArguments = new ArrayList<>();
        assertEqualsArguments.add(expectedFieldExpr);
        assertEqualsArguments.add(actualFieldExpr);
        if (TypeNode.isFloatingPointType(field.type())) {
          boolean isFloat = field.type().equals(TypeNode.FLOAT);
          assertEqualsArguments.add(
              ValueExpr.withValue(
                  PrimitiveValue.builder()
                      .setType(isFloat ? TypeNode.FLOAT : TypeNode.DOUBLE)
                      .setValue(String.format("0.0001%s", isFloat ? "f" : ""))
                      .build()));
        }
        methodExprs.add(
            MethodInvocationExpr.builder()
                .setStaticReferenceType(getFixedTypeStore().get("Assert"))
                .setMethodName("assertEquals")
                .setArguments(assertEqualsArguments)
                .build());
      }
    } else {
      for (VariableExpr argVarExpr : argExprs) {
        Variable variable = argVarExpr.variable();
        String fieldGetterMethodNamePattern = "get%s";
        if (LIST_TYPE.isSupertypeOrEquals(variable.type())) {
          fieldGetterMethodNamePattern = "get%sList";
        } else if (MAP_TYPE.isSupertypeOrEquals(variable.type())) {
          fieldGetterMethodNamePattern = "get%sMap";
        }
        Expr actualFieldExpr =
            MethodInvocationExpr.builder()
                .setExprReferenceExpr(actualRequestVarExpr)
                .setMethodName(
                    String.format(
                        fieldGetterMethodNamePattern,
                        JavaStyle.toUpperCamelCase(variable.identifier().name())))
                .build();
        Expr expectedFieldExpr = argVarExpr;
        if (RESOURCE_NAME_TYPE.isSupertypeOrEquals(argVarExpr.type())) {
          expectedFieldExpr =
              MethodInvocationExpr.builder()
                  .setExprReferenceExpr(argVarExpr)
                  .setMethodName("toString")
                  .build();
        }
        methodExprs.add(
            MethodInvocationExpr.builder()
                .setStaticReferenceType(getFixedTypeStore().get("Assert"))
                .setMethodName("assertEquals")
                .setArguments(expectedFieldExpr, actualFieldExpr)
                .build());
      }
    }

    // Assert header equality.
    Expr headerKeyExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(getFixedTypeStore().get("ApiClientHeaderProvider"))
            .setMethodName("getDefaultApiClientHeaderKey")
            .build();
    Expr headerPatternExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(getFixedTypeStore().get("GaxGrpcProperties"))
            .setMethodName("getDefaultApiClientHeaderPattern")
            .build();
    Expr headerSentExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(classMemberVarExprs.get("channelProvider"))
            .setMethodName("isHeaderSent")
            .setArguments(headerKeyExpr, headerPatternExpr)
            .build();
    methodExprs.add(
        MethodInvocationExpr.builder()
            .setStaticReferenceType(getFixedTypeStore().get("Assert"))
            .setMethodName("assertTrue")
            .setArguments(headerSentExpr)
            .build());
    methodStatements.addAll(
        methodExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()));
    methodExprs.clear();

    return methodStatements;
  }

  @Override
  protected MethodDefinition createRpcExceptionTestMethod(
      Method method,
      Service service,
      List<MethodArgument> methodSignature,
      int variantIndex,
      Map<String, VariableExpr> classMemberVarExprs,
      Map<String, ResourceName> resourceNames,
      Map<String, Message> messageTypes) {
    VariableExpr exceptionVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setType(getFixedTypeStore().get("StatusRuntimeException"))
                .setName("exception")
                .build());

    // First two assignment lines.
    Expr exceptionAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(exceptionVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(
                NewObjectExpr.builder()
                    .setType(getFixedTypeStore().get("StatusRuntimeException"))
                    .setArguments(
                        EnumRefExpr.builder()
                            .setType(GRPC_STATUS_TYPE)
                            .setName("INVALID_ARGUMENT")
                            .build())
                    .build())
            .build();
    Expr addExceptionExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(classMemberVarExprs.get(getMockServiceVarName(service)))
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
          createStreamingRpcExceptionTestStatements(
              method, classMemberVarExprs, resourceNames, messageTypes));
    } else {
      methodBody.addAll(
          createRpcExceptionTestStatements(
              method, methodSignature, classMemberVarExprs, resourceNames, messageTypes));
    }

    return MethodDefinition.builder()
        .setAnnotations(Arrays.asList(getTestAnnotation()))
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName(exceptionTestMethodName)
        .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(Exception.class)))
        .setBody(methodBody)
        .build();
  }
}
