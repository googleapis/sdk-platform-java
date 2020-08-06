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
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Service;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.longrunning.Operation;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Any;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class ServiceClientTestClassComposer implements ClassComposer {
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

  private static final Map<String, TypeNode> staticTypes = createStaticTypes();

  private ServiceClientTestClassComposer() {}

  public static ServiceClientTestClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(Service service, Map<String, Message> ignore) {
    String pakkage = service.pakkage();
    Map<String, TypeNode> types = createDynamicTypes(service);
    String className = String.format("%sClientTest", service.name());
    GapicClass.Kind kind = Kind.MAIN;

    Map<String, VariableExpr> classMemberVarExprs = createClassMemberVarExprs(service, types);

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setFileHeader(FileHeader.createApacheLicense())
            .setPackageString(pakkage)
            .setAnnotations(createClassAnnotations())
            .setScope(ScopeNode.PUBLIC)
            .setName(className)
            .setStatements(createClassMemberFieldDecls(classMemberVarExprs))
            .setMethods(createClassMethods(service, classMemberVarExprs, types))
            .build();
    return GapicClass.create(kind, classDef);
  }

  private static List<AnnotationNode> createClassAnnotations() {
    return Arrays.asList(
        AnnotationNode.builder()
            .setType(staticTypes.get("Generated"))
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
    fields.put(SERVICE_HELPER_VAR_NAME, staticTypes.get("MockServiceHelper"));
    fields.put(CLIENT_VAR_NAME, types.get(getClientClassName(service.name())));
    fields.put(CHANNEL_PROVIDER_VAR_NAME, staticTypes.get("LocalChannelProvider"));
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
      Service service, Map<String, VariableExpr> classMemberVarExprs, Map<String, TypeNode> types) {
    List<MethodDefinition> javaMethods = new ArrayList<>();
    javaMethods.addAll(createTestAdminMethods(service, classMemberVarExprs, types));
    // TODO(miraleung): FIll this in.
    /*
    for (Method protoMethod : service.methods()) {
      javaMethods.add(createTestMethod(protoMethod, classMemberVarExprs, types));
      javaMethods.add(createExceptionTestMethod(protoMethod, classMemberVarExprs, types));
    }
    */
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
    // TODO(miraleung): Actually intantiate this.
    Expr initMockServiceExpr =
        AssignmentExpr.builder()
            .setVariableExpr(mockServiceVarExpr)
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setMethodName("newMockTodo")
                    .setReturnType(mockServiceVarExpr.type())
                    .build())
            .build();

    MethodInvocationExpr firstArg =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(staticTypes.get("UUID"))
            .setMethodName("randomUUID")
            .build();
    firstArg =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(firstArg)
            .setMethodName("toString")
            .build();

    MethodInvocationExpr secondArg =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(staticTypes.get("Arrays"))
            .setGenerics(Arrays.asList(staticTypes.get("MockGrpcService").reference()))
            .setMethodName("asList")
            .setArguments(Arrays.asList(mockServiceVarExpr))
            .build();
    // TODO(miraleung): Actually intantiate this.
    Expr initServiceHelperExpr =
        AssignmentExpr.builder()
            .setVariableExpr(serviceHelperVarExpr)
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setMethodName("newMockServiceHelperTodo")
                    .setArguments(Arrays.asList(firstArg, secondArg))
                    .setReturnType(serviceHelperVarExpr.type())
                    .build())
            .build();

    Expr startServiceHelperExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(serviceHelperVarExpr)
            .setMethodName("start")
            .build();

    return MethodDefinition.builder()
        .setAnnotations(Arrays.asList(AnnotationNode.withType(staticTypes.get("BeforeClass"))))
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
        .setAnnotations(Arrays.asList(AnnotationNode.withType(staticTypes.get("AfterClass"))))
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
                    .setStaticReferenceType(staticTypes.get("NoCredentialsProvider"))
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
            .setVariableExpr(localSettingsVarExpr)
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
        .setAnnotations(Arrays.asList(AnnotationNode.withType(staticTypes.get("Before"))))
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setName("setUp")
        .setThrowsExceptions(Arrays.asList(staticTypes.get("IOException")))
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
        .setAnnotations(Arrays.asList(AnnotationNode.withType(staticTypes.get("After"))))
        .setScope(ScopeNode.PUBLIC)
        .setIsStatic(true)
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

  private static void createTestMethod(
      Method protoMethod,
      Map<String, VariableExpr> classMemberVarExprs,
      Map<String, TypeNode> types) {
    // TODO(miraleung): Fill this in.
  }

  private static void createExceptionTestMethod(
      Method protoMethod,
      Map<String, VariableExpr> classMemberVarExprs,
      Map<String, TypeNode> types) {
    // TODO(miraleung): Fill this in.
  }

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
            Status.class,
            StatusCode.class,
            StatusRuntimeException.class,
            Test.class,
            UUID.class);
    Map<String, TypeNode> staticTypes =
        concreteClazzes.stream()
            .collect(
                Collectors.toMap(
                    c -> c.getSimpleName(),
                    c -> TypeNode.withReference(ConcreteReference.withClazz(c))));

    staticTypes.putAll(
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
    return staticTypes;
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
        "defaultTransportChannelProvider", staticTypes.get("TransportChannelProvider"));
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
