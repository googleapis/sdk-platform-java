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

import com.google.api.core.ApiFunction;
import com.google.api.core.BetaApi;
import com.google.api.gax.core.GoogleCredentialsProvider;
import com.google.api.gax.core.InstantiatingExecutorProvider;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.api.gax.rpc.ApiClientHeaderProvider;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.ClientSettings;
import com.google.api.gax.rpc.OperationCallSettings;
import com.google.api.gax.rpc.PagedCallSettings;
import com.google.api.gax.rpc.ServerStreamingCallSettings;
import com.google.api.gax.rpc.StreamingCallSettings;
import com.google.api.gax.rpc.StubSettings;
import com.google.api.gax.rpc.TransportChannelProvider;
import com.google.api.gax.rpc.UnaryCallSettings;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.CastExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NullObjectValue;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.base.Preconditions;
import com.google.longrunning.Operation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class ServiceSettingsClassComposer implements ClassComposer {
  private static final String BUILDER_CLASS_NAME = "Builder";
  private static final String CLASS_NAME_PATTERN = "%sSettings";
  private static final String CALL_SETTINGS_TYPE_NAME_PATTERN = "%sCallSettings";
  private static final String PAGED_RESPONSE_TYPE_NAME_PATTERN = "%sPagedResponse";
  private static final String STUB_SETTINGS_PATTERN = "%sStubSettings";

  private static final ServiceSettingsClassComposer INSTANCE = new ServiceSettingsClassComposer();

  private static final Map<String, TypeNode> staticTypes = createStaticTypes();

  private ServiceSettingsClassComposer() {}

  public static ServiceSettingsClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(Service service, Map<String, Message> ignore) {
    String pakkage = service.pakkage();
    Map<String, TypeNode> types = createDynamicTypes(service);
    String className = getThisClassName(service.name());
    GapicClass.Kind kind = Kind.MAIN;

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setFileHeader(FileHeader.createApacheLicense())
            .setPackageString(pakkage)
            .setAnnotations(createClassAnnotations())
            .setScope(ScopeNode.PUBLIC)
            .setName(className)
            .setExtendsType(
                TypeNode.withReference(
                    staticTypes
                        .get("ClientSettings")
                        .reference()
                        .copyAndSetGenerics(
                            Arrays.asList(
                                types.get(getThisClassName(service.name())).reference()))))
            .setMethods(createClassMethods(service, types))
            .setNestedClasses(Arrays.asList(createNestedBuilderClass(service, types)))
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

  private static List<MethodDefinition> createClassMethods(
      Service service, Map<String, TypeNode> types) {
    List<MethodDefinition> javaMethods = new ArrayList<>();
    javaMethods.addAll(createSettingsGetterMethods(service, types));
    javaMethods.add(createCreatorMethod(service, types));
    javaMethods.addAll(createDefaultGetterMethods(service, types));
    javaMethods.addAll(createBuilderHelperMethods(service, types));
    javaMethods.add(createConstructorMethod(service, types));
    return javaMethods;
  }

  private static MethodDefinition createConstructorMethod(
      Service service, Map<String, TypeNode> types) {
    VariableExpr settingsBuilderVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("settingsBuilder")
                .setType(types.get(BUILDER_CLASS_NAME))
                .build());
    TypeNode thisClassType = types.get(getThisClassName(service.name()));
    // TODO(miraleung): Use super.
    return MethodDefinition.constructorBuilder()
        .setScope(ScopeNode.PROTECTED)
        .setReturnType(thisClassType)
        .setArguments(Arrays.asList(settingsBuilderVarExpr.toBuilder().setIsDecl(true).build()))
        .setThrowsExceptions(Arrays.asList(staticTypes.get("IOException")))
        .setBody(
            Arrays.asList(
                ExprStatement.withExpr(
                    MethodInvocationExpr.builder()
                        .setMethodName("suuper")
                        .setArguments(Arrays.asList(settingsBuilderVarExpr))
                        .setReturnType(thisClassType)
                        .build())))
        .build();
  }

  private static List<MethodDefinition> createSettingsGetterMethods(
      Service service, Map<String, TypeNode> types) {
    TypeNode stubSettingsType = types.get(getStubSettingsClassName(service.name()));
    BiFunction<TypeNode, String, MethodDefinition> methodMakerFn =
        (retType, methodName) ->
            MethodDefinition.builder()
                .setScope(ScopeNode.PUBLIC)
                .setReturnType(retType)
                .setName(methodName)
                .setReturnExpr(
                    MethodInvocationExpr.builder()
                        .setExprReferenceExpr(
                            CastExpr.builder()
                                .setType(stubSettingsType)
                                .setExpr(
                                    MethodInvocationExpr.builder()
                                        .setMethodName("getStubSettings")
                                        .setReturnType(staticTypes.get("StubSettings"))
                                        .build())
                                .build())
                        .setMethodName(methodName)
                        .setReturnType(retType)
                        .build())
                .build();
    List<MethodDefinition> javaMethods = new ArrayList<>();
    for (Method protoMethod : service.methods()) {
      String javaStyleName = JavaStyle.toLowerCamelCase(protoMethod.name());
      javaMethods.add(
          methodMakerFn.apply(
              getCallSettingsType(protoMethod, types), String.format("%sSettings", javaStyleName)));
      if (protoMethod.hasLro()) {
        javaMethods.add(
            methodMakerFn.apply(
                getOperationCallSettingsType(protoMethod),
                String.format("%sOperationSettings", javaStyleName)));
      }
    }
    return javaMethods;
  }

  private static MethodDefinition createCreatorMethod(
      Service service, Map<String, TypeNode> types) {
    TypeNode stubClassType = types.get(getStubSettingsClassName(service.name()));
    VariableExpr stubVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("stub").setType(stubClassType).build());

    TypeNode thisClassType = types.get(getThisClassName(service.name()));
    MethodInvocationExpr stubBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(stubVarExpr)
            .setMethodName("toBuilder")
            .build();
    // TODO(miraleung): Actually instantiate the builder instaead of newTodoBuilder.
    MethodInvocationExpr returnMethodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("newTodoBuilder")
            .setArguments(Arrays.asList(stubBuilderMethodExpr))
            .build();
    returnMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(returnMethodExpr)
            .setMethodName("build")
            .setReturnType(thisClassType)
            .build();
    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setIsStatic(true)
        .setIsFinal(true)
        .setReturnType(thisClassType)
        .setName("create")
        .setArguments(Arrays.asList(stubVarExpr.toBuilder().setIsDecl(true).build()))
        .setThrowsExceptions(Arrays.asList(staticTypes.get("IOException")))
        .setReturnExpr(returnMethodExpr)
        .build();
  }

  private static List<MethodDefinition> createDefaultGetterMethods(
      Service service, Map<String, TypeNode> types) {
    Map<String, TypeNode> javaMethodNameToReturnType = createDefaultMethodNamesToTypes();

    BiFunction<String, TypeNode, MethodDefinition> methodMakerFn =
        (mName, retType) ->
            MethodDefinition.builder()
                .setScope(ScopeNode.PUBLIC)
                .setIsStatic(true)
                .setReturnType(retType)
                .setName(mName)
                .setReturnExpr(
                    MethodInvocationExpr.builder()
                        .setStaticReferenceType(types.get(getStubSettingsClassName(service.name())))
                        .setMethodName(mName)
                        .setReturnType(retType)
                        .build())
                .build();
    List<MethodDefinition> javaMethods = new ArrayList<>();
    javaMethods.addAll(
        javaMethodNameToReturnType.entrySet().stream()
            .map(e -> methodMakerFn.apply(e.getKey(), e.getValue()))
            .collect(Collectors.toList()));
    javaMethods.add(
        methodMakerFn
            .apply(
                "defaultApiClientHeaderProviderBuilder",
                TypeNode.withReference(
                    ConcreteReference.withClazz(ApiClientHeaderProvider.Builder.class)))
            .toBuilder()
            .setAnnotations(
                Arrays.asList(
                    AnnotationNode.builder()
                        .setType(staticTypes.get("BetaApi"))
                        .setDescription(
                            "The surface for customizing headers is not stable yet and may"
                                + " change in the future.")
                        .build()))
            .build());
    return javaMethods;
  }

  private static List<MethodDefinition> createBuilderHelperMethods(
      Service service, Map<String, TypeNode> types) {
    TypeNode builderType = types.get(BUILDER_CLASS_NAME);
    MethodDefinition newBuilderMethodOne =
        MethodDefinition.builder()
            .setScope(ScopeNode.PUBLIC)
            .setIsStatic(true)
            .setReturnType(builderType)
            .setName("newBuilder")
            .setReturnExpr(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(builderType)
                    .setMethodName("createDefault")
                    .setReturnType(builderType)
                    .build())
            .build();

    VariableExpr clientContextVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("clientContext")
                .setType(staticTypes.get("ClientContext"))
                .build());
    // TODO(miraleung): Actually instantiate thie builder.
    MethodDefinition newBuilderMethodTwo =
        MethodDefinition.builder()
            .setScope(ScopeNode.PUBLIC)
            .setIsStatic(true)
            .setReturnType(builderType)
            .setName("newBuilder")
            .setArguments(Arrays.asList(clientContextVarExpr.toBuilder().setIsDecl(true).build()))
            .setReturnExpr(
                MethodInvocationExpr.builder()
                    .setMethodName("newBuilder")
                    .setArguments(Arrays.asList(clientContextVarExpr))
                    .setReturnType(builderType)
                    .build())
            .build();

    // TODO(miraleung): Use this and actually instantiate the builder.
    MethodDefinition toBuilderMethod =
        MethodDefinition.builder()
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(builderType)
            .setName("toBuilder")
            .setReturnExpr(
                MethodInvocationExpr.builder()
                    .setMethodName("newBuilder")
                    .setArguments(
                        Arrays.asList(
                            VariableExpr.withVariable(
                                Variable.builder()
                                    .setName("thiis")
                                    .setType(types.get(getThisClassName(service.name())))
                                    .build())))
                    .setReturnType(builderType)
                    .build())
            .build();

    return Arrays.asList(newBuilderMethodOne, newBuilderMethodTwo, toBuilderMethod);
  }

  private static ClassDefinition createNestedBuilderClass(
      Service service, Map<String, TypeNode> types) {
    return ClassDefinition.builder()
        .setIsNested(true)
        .setScope(ScopeNode.PUBLIC)
        .setIsStatic(true)
        .setName(BUILDER_CLASS_NAME)
        .setExtendsType(
            TypeNode.withReference(
                ConcreteReference.builder()
                    .setClazz(ClientSettings.Builder.class)
                    .setGenerics(
                        Arrays.asList(
                            types.get(getThisClassName(service.name())).reference(),
                            types.get(BUILDER_CLASS_NAME).reference()))
                    .build()))
        .setMethods(createNestedBuilderClassMethods(service, types))
        .build();
  }

  private static List<MethodDefinition> createNestedBuilderClassMethods(
      Service service, Map<String, TypeNode> types) {
    List<MethodDefinition> javaMethods = new ArrayList<>();
    javaMethods.addAll(createNestedBuilderConstructorMethods(service, types));
    javaMethods.add(createNestedBuilderCreatorMethod(service, types));
    javaMethods.add(createNestedBuilderStubSettingsBuilderMethod(service, types));
    javaMethods.add(createNestedBuilderApplyToAllUnaryMethod(service, types));
    javaMethods.addAll(createNestedBuilderSettingsGetterMethods(service, types));
    javaMethods.add(createNestedBuilderClassBuildMethod(service, types));
    return javaMethods;
  }

  private static List<MethodDefinition> createNestedBuilderConstructorMethods(
      Service service, Map<String, TypeNode> types) {
    TypeNode builderType = types.get(BUILDER_CLASS_NAME);
    // TODO(miraleung): Use this.
    MethodDefinition noArgCtor =
        MethodDefinition.constructorBuilder()
            .setScope(ScopeNode.PROTECTED)
            .setReturnType(builderType)
            .setThrowsExceptions(Arrays.asList(staticTypes.get("IOException")))
            .setBody(
                Arrays.asList(
                    ExprStatement.withExpr(
                        MethodInvocationExpr.builder()
                            .setMethodName("thiis")
                            .setArguments(
                                Arrays.asList(
                                    CastExpr.builder()
                                        .setType(staticTypes.get("ClientContext"))
                                        .setExpr(ValueExpr.withValue(NullObjectValue.create()))
                                        .build()))
                            .setReturnType(builderType)
                            .build())))
            .build();

    // TODO(miraleung): Use super.
    BiFunction<VariableExpr, Expr, MethodDefinition> ctorMakerFn =
        (ctorArg, superArg) ->
            MethodDefinition.constructorBuilder()
                .setScope(ScopeNode.PROTECTED)
                .setReturnType(builderType)
                .setArguments(Arrays.asList(ctorArg.toBuilder().setIsDecl(true).build()))
                .setBody(
                    Arrays.asList(
                        ExprStatement.withExpr(
                            MethodInvocationExpr.builder()
                                .setMethodName("suuper")
                                .setArguments(Arrays.asList(superArg))
                                .setReturnType(builderType)
                                .build())))
                .build();

    VariableExpr clientContextVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("clientContext")
                .setType(staticTypes.get("ClientContext"))
                .build());
    MethodDefinition clientContextCtor =
        ctorMakerFn.apply(
            clientContextVarExpr,
            MethodInvocationExpr.builder()
                .setStaticReferenceType(types.get(getStubSettingsClassName(service.name())))
                .setMethodName("newBuilder")
                .setArguments(Arrays.asList(clientContextVarExpr))
                .build());

    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("settings")
                .setType(types.get(getThisClassName(service.name())))
                .build());
    MethodInvocationExpr settingsBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(settingsVarExpr)
            .setMethodName("getStubSettings")
            .build();
    settingsBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(settingsBuilderMethodExpr)
            .setMethodName("toBuilder")
            .build();
    MethodDefinition settingsCtor = ctorMakerFn.apply(settingsVarExpr, settingsBuilderMethodExpr);

    TypeNode stubSettingsBuilderType = getStubSettingsBuilderType(service);
    VariableExpr stubSettingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("stubSettings").setType(stubSettingsBuilderType).build());
    MethodDefinition stubSettingsCtor = ctorMakerFn.apply(stubSettingsVarExpr, stubSettingsVarExpr);

    return Arrays.asList(noArgCtor, clientContextCtor, settingsCtor, stubSettingsCtor);
  }

  private static MethodDefinition createNestedBuilderCreatorMethod(
      Service service, Map<String, TypeNode> types) {
    MethodInvocationExpr ctorArg =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(types.get(getStubSettingsClassName(service.name())))
            .setMethodName("newBuilder")
            .build();

    TypeNode builderType = types.get(BUILDER_CLASS_NAME);
    // TODO(miraleung): Actually instantiate the Builder instead of newBuilderTodo.
    return MethodDefinition.builder()
        .setScope(ScopeNode.PRIVATE)
        .setIsStatic(true)
        .setReturnType(builderType)
        .setName("createDefault")
        .setReturnExpr(
            MethodInvocationExpr.builder()
                .setMethodName("newBuilderTodo")
                .setArguments(Arrays.asList(ctorArg))
                .setReturnType(builderType)
                .build())
        .build();
  }

  private static MethodDefinition createNestedBuilderStubSettingsBuilderMethod(
      Service service, Map<String, TypeNode> types) {
    TypeNode retType = getStubSettingsBuilderType(service);
    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(retType)
        .setName("getStubSettingsBuilder")
        .setReturnExpr(
            CastExpr.builder()
                .setType(retType)
                .setExpr(
                    MethodInvocationExpr.builder()
                        .setMethodName("getStubSettings")
                        .setReturnType(staticTypes.get("StubSettings"))
                        .build())
                .build())
        .build();
  }

  private static MethodDefinition createNestedBuilderApplyToAllUnaryMethod(
      Service service, Map<String, TypeNode> types) {
    TypeNode builderType = types.get(BUILDER_CLASS_NAME);
    String javaMethodName = "applyToAllUnaryMethods";
    // TODO(miraleung): Use super.
    VariableExpr superExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("suuper").setType(staticTypes.get("StubSettings")).build());

    TypeNode unaryCallSettingsType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(UnaryCallSettings.Builder.class)
                .setGenerics(
                    Arrays.asList(TypeNode.WILDCARD_REFERENCE, TypeNode.WILDCARD_REFERENCE))
                .build());
    VariableExpr settingsUpdaterVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("settingsUpdater")
                .setType(
                    TypeNode.withReference(
                        staticTypes
                            .get("ApiFunction")
                            .reference()
                            .copyAndSetGenerics(
                                Arrays.asList(
                                    unaryCallSettingsType.reference(),
                                    TypeNode.VOID_OBJECT.reference()))))
                .build());

    MethodInvocationExpr builderMethodExpr =
        MethodInvocationExpr.builder().setMethodName("getStubSettingsBuilder").build();
    builderMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(builderMethodExpr)
            .setMethodName("unaryMethodSettingsBuilders")
            .build();

    MethodInvocationExpr applyMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(superExpr)
            .setMethodName(javaMethodName)
            .setArguments(Arrays.asList(builderMethodExpr, settingsUpdaterVarExpr))
            .build();

    // TODO(miraleung): Use this.
    VariableExpr returnExpr =
        VariableExpr.withVariable(Variable.builder().setName("thiis").setType(builderType).build());

    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(builderType)
        .setName(javaMethodName)
        .setArguments(Arrays.asList(settingsUpdaterVarExpr.toBuilder().setIsDecl(true).build()))
        .setThrowsExceptions(
            Arrays.asList(TypeNode.withReference(ConcreteReference.withClazz(Exception.class))))
        .setBody(Arrays.asList(ExprStatement.withExpr(applyMethodExpr)))
        .setReturnExpr(returnExpr)
        .build();
  }

  private static List<MethodDefinition> createNestedBuilderSettingsGetterMethods(
      Service service, Map<String, TypeNode> types) {
    BiFunction<TypeNode, String, MethodDefinition> methodMakerFn =
        (retType, methodName) ->
            MethodDefinition.builder()
                .setScope(ScopeNode.PUBLIC)
                .setReturnType(retType)
                .setName(methodName)
                .setReturnExpr(
                    MethodInvocationExpr.builder()
                        .setExprReferenceExpr(
                            MethodInvocationExpr.builder()
                                .setMethodName("getStubSettingsBuilder")
                                .build())
                        .setMethodName(methodName)
                        .setReturnType(retType)
                        .build())
                .build();
    List<MethodDefinition> javaMethods = new ArrayList<>();
    for (Method protoMethod : service.methods()) {
      String javaStyleName = JavaStyle.toLowerCamelCase(protoMethod.name());
      javaMethods.add(
          methodMakerFn.apply(
              getCallSettingsBuilderType(protoMethod, types),
              String.format("%sSettings", javaStyleName)));
      if (protoMethod.hasLro()) {
        javaMethods.add(
            methodMakerFn.apply(
                getOperationCallSettingsBuilderType(protoMethod),
                String.format("%sOperationSettings", javaStyleName)));
      }
    }
    return javaMethods;
  }

  private static MethodDefinition createNestedBuilderClassBuildMethod(
      Service service, Map<String, TypeNode> types) {
    VariableExpr thisExpr =
        VariableExpr.withVariable(
            // TODO(miraleung): Actually use this.
            Variable.builder().setName("thiis").setType(types.get(BUILDER_CLASS_NAME)).build());
    TypeNode returnType = types.get(getThisClassName(service.name()));
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(returnType)
        .setName("build")
        .setThrowsExceptions(Arrays.asList(staticTypes.get("IOException")))
        .setReturnExpr(
            MethodInvocationExpr.builder()
                // TODO(miraleung): Actually instantiate ServiceSettings.
                .setMethodName("newServiceSettings")
                .setArguments(Arrays.asList(thisExpr))
                .setReturnType(returnType)
                .build())
        .build();
  }

  private static Map<String, TypeNode> createStaticTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            ApiClientHeaderProvider.class,
            ApiFunction.class,
            BetaApi.class,
            ClientContext.class,
            ClientSettings.class,
            Generated.class,
            GoogleCredentialsProvider.class,
            InstantiatingExecutorProvider.class,
            InstantiatingGrpcChannelProvider.class,
            IOException.class,
            Operation.class,
            OperationCallSettings.class,
            PagedCallSettings.class,
            ServerStreamingCallSettings.class,
            StreamingCallSettings.class,
            StubSettings.class,
            TransportChannelProvider.class,
            UnaryCallSettings.class);
    return concreteClazzes.stream()
        .collect(
            Collectors.toMap(
                c -> c.getSimpleName(),
                c -> TypeNode.withReference(ConcreteReference.withClazz(c))));
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
    // This class.
    types.put(
        getThisClassName(service.name()),
        TypeNode.withReference(
            VaporReference.builder()
                .setName(getThisClassName(service.name()))
                .setPakkage(service.pakkage())
                .build()));
    // Nested Builder class.
    types.put(
        BUILDER_CLASS_NAME,
        TypeNode.withReference(
            VaporReference.builder()
                .setName(BUILDER_CLASS_NAME)
                .setEnclosingClassName(getThisClassName(service.name()))
                .setPakkage(service.pakkage())
                .setIsStaticImport(true)
                .build()));

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
                                .setEnclosingClassName(String.format("%sClient", service.name()))
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

  private static String getThisClassName(String serviceName) {
    return String.format(CLASS_NAME_PATTERN, serviceName);
  }

  private static String getStubSettingsClassName(String serviceName) {
    return String.format(STUB_SETTINGS_PATTERN, serviceName);
  }

  private static TypeNode getStubSettingsBuilderType(Service service) {
    return TypeNode.withReference(
        VaporReference.builder()
            .setPakkage(service.pakkage())
            .setName(BUILDER_CLASS_NAME)
            .setEnclosingClassName(getStubSettingsClassName(service.name()))
            .build());
  }
}
