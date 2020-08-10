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

import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.core.BackgroundResourceAggregation;
import com.google.api.gax.grpc.GrpcCallSettings;
import com.google.api.gax.grpc.GrpcStubCallableFactory;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.ClientStreamingCallable;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.EnumRefExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
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
import com.google.longrunning.Operation;
import com.google.longrunning.stub.GrpcOperationsStub;
import io.grpc.MethodDescriptor;
import io.grpc.protobuf.ProtoUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class GrpcServiceStubClassComposer implements ClassComposer {
  private static final String CLASS_NAME_PATTERN = "Grpc%sStub";
  private static final String GRPC_SERVICE_CALLABLE_FACTORY_PATTERN = "Grpc%sCallableFactory";
  private static final String METHOD_DESCRIPTOR_NAME_PATTERN = "%sMethodDescriptor";
  private static final String PAGED_RESPONSE_TYPE_NAME_PATTERN = "%sPagedResponse";
  private static final String PAGED_CALLABLE_CLASS_MEMBER_PATTERN = "%sPagedCallable";
  private static final String STUB_SETTINGS_PATTERN = "%sStubSettings";
  private static final String STUB_PATTERN = "%sStub";

  private static final String BACKGROUND_RESOURCES_MEMBER_NAME = "backgroundResources";
  private static final String CALLABLE_NAME = "Callable";
  private static final String CALLABLE_FACTORY_MEMBER_NAME = "callableFactory";
  private static final String CALLABLE_CLASS_MEMBER_PATTERN = "%sCallable";
  private static final String OPERATION_CALLABLE_CLASS_MEMBER_PATTERN = "%sOperationCallable";
  private static final String OPERATION_CALLABLE_NAME = "OperationCallable";
  private static final String OPERATIONS_STUB_MEMBER_NAME = "operationsStub";
  private static final String PAGED_CALLABLE_NAME = "PagedCallable";

  private static final GrpcServiceStubClassComposer INSTANCE = new GrpcServiceStubClassComposer();

  private static final Map<String, TypeNode> staticTypes = createStaticTypes();

  private GrpcServiceStubClassComposer() {}

  public static GrpcServiceStubClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(Service service, Map<String, Message> ignore) {
    String pakkage = service.pakkage() + ".stub";
    Map<String, TypeNode> types = createDynamicTypes(service, pakkage);
    String className = getThisClassName(service.name());
    GapicClass.Kind kind = Kind.STUB;

    Map<String, VariableExpr> protoMethodNameToDescriptorVarExprs =
        createProtoMethodNameToDescriptorClassMembers(service);

    Map<String, VariableExpr> callableClassMemberVarExprs =
        createCallableClassMembers(service, types);

    Map<String, VariableExpr> classMemberVarExprs = new LinkedHashMap<>();
    classMemberVarExprs.put(
        BACKGROUND_RESOURCES_MEMBER_NAME,
        VariableExpr.withVariable(
            Variable.builder()
                .setName(BACKGROUND_RESOURCES_MEMBER_NAME)
                .setType(staticTypes.get("BackgroundResource"))
                .build()));
    classMemberVarExprs.put(
        OPERATIONS_STUB_MEMBER_NAME,
        VariableExpr.withVariable(
            Variable.builder()
                .setName(OPERATIONS_STUB_MEMBER_NAME)
                .setType(staticTypes.get("GrpcOperationsStub"))
                .build()));
    classMemberVarExprs.put(
        CALLABLE_FACTORY_MEMBER_NAME,
        VariableExpr.withVariable(
            Variable.builder()
                .setName(CALLABLE_FACTORY_MEMBER_NAME)
                .setType(staticTypes.get("GrpcStubCallableFactory"))
                .build()));

    List<Statement> classStatements = new ArrayList<>();
    classStatements.addAll(
        createMethodDescriptorVariableDecls(service, protoMethodNameToDescriptorVarExprs));
    classStatements.addAll(createClassMemberFieldDeclarations(callableClassMemberVarExprs));
    classStatements.addAll(createClassMemberFieldDeclarations(classMemberVarExprs));

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(pakkage)
            .setAnnotations(createClassAnnotations())
            .setScope(ScopeNode.PUBLIC)
            .setName(className)
            .setExtendsType(types.get(String.format(STUB_PATTERN, service.name())))
            .setStatements(classStatements)
            .setMethods(
                createClassMethods(
                    service,
                    types,
                    classMemberVarExprs,
                    callableClassMemberVarExprs,
                    protoMethodNameToDescriptorVarExprs))
            .build();
    return GapicClass.create(kind, classDef);
  }

  private static List<Statement> createMethodDescriptorVariableDecls(
      Service service, Map<String, VariableExpr> protoMethodNameToDescriptorVarExprs) {
    return service.methods().stream()
        .map(
            m ->
                createMethodDescriptorVariableDecl(
                    service, m, protoMethodNameToDescriptorVarExprs.get(m.name())))
        .collect(Collectors.toList());
  }

  private static Statement createMethodDescriptorVariableDecl(
      Service service, Method protoMethod, VariableExpr methodDescriptorVarExpr) {
    MethodInvocationExpr methodDescriptorMaker =
        MethodInvocationExpr.builder()
            .setMethodName("newBuilder")
            .setStaticReferenceType(staticTypes.get("MethodDescriptor"))
            .setGenerics(methodDescriptorVarExpr.variable().type().reference().generics())
            .build();

    BiFunction<String, Expr, Function<MethodInvocationExpr, MethodInvocationExpr>> methodMakerFn =
        (mName, argExpr) ->
            m ->
                MethodInvocationExpr.builder()
                    .setMethodName(mName)
                    .setArguments(Arrays.asList(argExpr))
                    .setExprReferenceExpr(m)
                    .build();

    methodDescriptorMaker =
        methodMakerFn
            .apply("setType", getMethodDescriptorMethodTypeExpr(protoMethod))
            .apply(methodDescriptorMaker);

    String codeMethodNameArg =
        String.format("%s.%s/%s", service.protoPakkage(), service.name(), protoMethod.name());
    methodDescriptorMaker =
        methodMakerFn
            .apply(
                "setFullMethodName",
                ValueExpr.withValue(StringObjectValue.withValue(codeMethodNameArg)))
            .apply(methodDescriptorMaker);

    Function<MethodInvocationExpr, MethodInvocationExpr> protoUtilsMarshallerFn =
        m ->
            MethodInvocationExpr.builder()
                .setStaticReferenceType(staticTypes.get("ProtoUtils"))
                .setMethodName("marshaller")
                .setArguments(Arrays.asList(m))
                .build();
    MethodInvocationExpr methodInvocationArg =
        MethodInvocationExpr.builder()
            .setMethodName("getDefaultInstance")
            .setStaticReferenceType(protoMethod.inputType())
            .build();

    methodDescriptorMaker =
        methodMakerFn
            .apply("setRequestMarshaller", protoUtilsMarshallerFn.apply(methodInvocationArg))
            .apply(methodDescriptorMaker);

    methodInvocationArg =
        MethodInvocationExpr.builder()
            .setMethodName("getDefaultInstance")
            .setStaticReferenceType(protoMethod.outputType())
            .build();
    methodDescriptorMaker =
        methodMakerFn
            .apply("setResponseMarshaller", protoUtilsMarshallerFn.apply(methodInvocationArg))
            .apply(methodDescriptorMaker);

    methodDescriptorMaker =
        MethodInvocationExpr.builder()
            .setMethodName("build")
            .setExprReferenceExpr(methodDescriptorMaker)
            .setReturnType(methodDescriptorVarExpr.type())
            .build();

    return ExprStatement.withExpr(
        AssignmentExpr.builder()
            .setVariableExpr(
                methodDescriptorVarExpr.toBuilder()
                    .setIsDecl(true)
                    .setScope(ScopeNode.PRIVATE)
                    .setIsStatic(true)
                    .setIsFinal(true)
                    .build())
            .setValueExpr(methodDescriptorMaker)
            .build());
  }

  private static List<Statement> createClassMemberFieldDeclarations(
      Map<String, VariableExpr> fieldNameToVarExprs) {
    return fieldNameToVarExprs.values().stream()
        .map(
            v ->
                ExprStatement.withExpr(
                    v.toBuilder()
                        .setIsDecl(true)
                        .setScope(ScopeNode.PRIVATE)
                        .setIsFinal(true)
                        .build()))
        .collect(Collectors.toList());
  }

  private static Map<String, VariableExpr> createProtoMethodNameToDescriptorClassMembers(
      Service service) {
    return service.methods().stream()
        .collect(
            Collectors.toMap(
                m -> m.name(),
                m ->
                    VariableExpr.withVariable(
                        Variable.builder()
                            .setName(
                                String.format(
                                    METHOD_DESCRIPTOR_NAME_PATTERN,
                                    JavaStyle.toLowerCamelCase(m.name())))
                            .setType(
                                TypeNode.withReference(
                                    ConcreteReference.builder()
                                        .setClazz(MethodDescriptor.class)
                                        .setGenerics(
                                            Arrays.asList(
                                                m.inputType().reference(),
                                                m.outputType().reference()))
                                        .build()))
                            .build())));
  }

  private static Map<String, VariableExpr> createCallableClassMembers(
      Service service, Map<String, TypeNode> types) {
    Map<String, VariableExpr> callableClassMembers = new LinkedHashMap<>();
    // Using a for-loop because the output cardinality is not a 1:1 mapping to the input set.
    for (Method protoMethod : service.methods()) {
      String javaStyleProtoMethodName = JavaStyle.toLowerCamelCase(protoMethod.name());
      String callableName = String.format(CALLABLE_CLASS_MEMBER_PATTERN, javaStyleProtoMethodName);
      callableClassMembers.put(
          callableName,
          VariableExpr.withVariable(
              Variable.builder()
                  .setName(callableName)
                  .setType(getCallableType(protoMethod))
                  .build()));
      if (protoMethod.hasLro()) {
        callableName =
            String.format(OPERATION_CALLABLE_CLASS_MEMBER_PATTERN, javaStyleProtoMethodName);
        callableClassMembers.put(
            callableName,
            VariableExpr.withVariable(
                Variable.builder()
                    .setName(callableName)
                    .setType(
                        TypeNode.withReference(
                            ConcreteReference.builder()
                                .setClazz(OperationCallable.class)
                                .setGenerics(
                                    Arrays.asList(
                                        protoMethod.inputType().reference(),
                                        protoMethod.lro().responseType().reference(),
                                        protoMethod.lro().metadataType().reference()))
                                .build()))
                    .build()));
      }
      if (protoMethod.isPaged()) {
        callableName = String.format(PAGED_CALLABLE_CLASS_MEMBER_PATTERN, javaStyleProtoMethodName);
        callableClassMembers.put(
            callableName,
            VariableExpr.withVariable(
                Variable.builder()
                    .setName(callableName)
                    .setType(
                        TypeNode.withReference(
                            getCallableType(protoMethod)
                                .reference()
                                .copyAndSetGenerics(
                                    Arrays.asList(
                                        protoMethod.inputType().reference(),
                                        types
                                            .get(
                                                String.format(
                                                    PAGED_RESPONSE_TYPE_NAME_PATTERN,
                                                    protoMethod.name()))
                                            .reference()))))
                    .build()));
      }
    }
    return callableClassMembers;
  }

  private static List<AnnotationNode> createClassAnnotations() {
    return Arrays.asList(
        AnnotationNode.builder()
            .setType(staticTypes.get("Generated"))
            .setDescription("by gapic-generator-java")
            .build());
  }

  private static List<MethodDefinition> createClassMethods(
      Service service,
      Map<String, TypeNode> types,
      Map<String, VariableExpr> classMemberVarExprs,
      Map<String, VariableExpr> callableClassMemberVarExprs,
      Map<String, VariableExpr> protoMethodNameToDescriptorVarExprs) {
    List<MethodDefinition> javaMethods = new ArrayList<>();
    javaMethods.addAll(createStaticCreatorMethods(service.name(), types));
    javaMethods.addAll(
        createConstructorMethods(
            service,
            types,
            classMemberVarExprs,
            callableClassMemberVarExprs,
            protoMethodNameToDescriptorVarExprs));
    javaMethods.add(
        createOperationsStubGetterMethod(classMemberVarExprs.get(OPERATIONS_STUB_MEMBER_NAME)));
    javaMethods.addAll(createCallableGetterMethods(callableClassMemberVarExprs));
    javaMethods.addAll(
        createStubOverrideMethods(classMemberVarExprs.get(BACKGROUND_RESOURCES_MEMBER_NAME)));
    return javaMethods;
  }

  private static List<MethodDefinition> createStaticCreatorMethods(
      String serviceName, Map<String, TypeNode> types) {
    TypeNode creatorMethodReturnType = types.get(getThisClassName(serviceName));
    Function<List<VariableExpr>, MethodDefinition.Builder> creatorMethodStarterFn =
        argList ->
            MethodDefinition.builder()
                .setScope(ScopeNode.PUBLIC)
                .setIsStatic(true)
                .setIsFinal(true)
                .setReturnType(creatorMethodReturnType)
                .setName("create")
                .setArguments(
                    argList.stream()
                        .map(v -> v.toBuilder().setIsDecl(true).build())
                        .collect(Collectors.toList()))
                .setThrowsExceptions(
                    Arrays.asList(
                        TypeNode.withReference(ConcreteReference.withClazz(IOException.class))));

    Function<List<Expr>, Expr> instantiatorExprFn =
        argList ->
            NewObjectExpr.builder().setType(creatorMethodReturnType).setArguments(argList).build();

    TypeNode stubSettingsType = types.get(String.format(STUB_SETTINGS_PATTERN, serviceName));
    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("settings").setType(stubSettingsType).build());

    TypeNode clientContextType = staticTypes.get("ClientContext");
    VariableExpr clientContextVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("clientContext").setType(clientContextType).build());

    VariableExpr callableFactoryVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("callableFactory")
                .setType(staticTypes.get("GrpcStubCallableFactory"))
                .build());

    MethodInvocationExpr clientContextCreateMethodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("create")
            .setStaticReferenceType(clientContextType)
            .setArguments(Arrays.asList(settingsVarExpr))
            .build();
    MethodInvocationExpr settingsBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("newBuilder")
            .setStaticReferenceType(stubSettingsType)
            .build();
    settingsBuilderMethodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("build")
            .setExprReferenceExpr(settingsBuilderMethodExpr)
            .build();

    return Arrays.asList(
        creatorMethodStarterFn
            .apply(Arrays.asList(settingsVarExpr))
            .setReturnExpr(
                instantiatorExprFn.apply(
                    Arrays.asList(settingsVarExpr, clientContextCreateMethodExpr)))
            .build(),
        creatorMethodStarterFn
            .apply(Arrays.asList(clientContextVarExpr))
            .setReturnExpr(
                instantiatorExprFn.apply(
                    Arrays.asList(settingsBuilderMethodExpr, clientContextVarExpr)))
            .build(),
        creatorMethodStarterFn
            .apply(Arrays.asList(clientContextVarExpr, callableFactoryVarExpr))
            .setReturnExpr(
                instantiatorExprFn.apply(
                    Arrays.asList(
                        settingsBuilderMethodExpr, clientContextVarExpr, callableFactoryVarExpr)))
            .build());
  }

  private static List<MethodDefinition> createConstructorMethods(
      Service service,
      Map<String, TypeNode> types,
      Map<String, VariableExpr> classMemberVarExprs,
      Map<String, VariableExpr> callableClassMemberVarExprs,
      Map<String, VariableExpr> protoMethodNameToDescriptorVarExprs) {
    TypeNode stubSettingsType = types.get(String.format(STUB_SETTINGS_PATTERN, service.name()));
    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("settings").setType(stubSettingsType).build());

    TypeNode clientContextType = staticTypes.get("ClientContext");
    VariableExpr clientContextVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("clientContext").setType(clientContextType).build());

    // TODO(miraleung): Change the name of this var to callableFactory.
    VariableExpr callableFactoryVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("callableFactori")
                .setType(staticTypes.get("GrpcStubCallableFactory"))
                .build());

    TypeNode thisClassType = types.get(getThisClassName(service.name()));
    TypeNode ioExceptionType =
        TypeNode.withReference(ConcreteReference.withClazz(IOException.class));

    BiFunction<List<VariableExpr>, List<Statement>, MethodDefinition> ctorMakerFn =
        (args, body) ->
            MethodDefinition.constructorBuilder()
                .setScope(ScopeNode.PROTECTED)
                .setReturnType(thisClassType)
                .setArguments(
                    args.stream()
                        .map(v -> v.toBuilder().setIsDecl(true).build())
                        .collect(Collectors.toList()))
                .setThrowsExceptions(Arrays.asList(ioExceptionType))
                .setBody(body)
                .build();

    // First constructor method.
    MethodDefinition firstCtor =
        ctorMakerFn.apply(
            Arrays.asList(settingsVarExpr, clientContextVarExpr),
            Arrays.asList(
                ExprStatement.withExpr(
                    MethodInvocationExpr.builder()
                        // TODO(miraleung): Actually instantiate this class.
                        .setMethodName("thiis")
                        .setArguments(
                            Arrays.asList(
                                settingsVarExpr,
                                clientContextVarExpr,
                                NewObjectExpr.builder()
                                    .setType(
                                        types.get(
                                            String.format(
                                                GRPC_SERVICE_CALLABLE_FACTORY_PATTERN,
                                                service.name())))
                                    .build()))
                        .setReturnType(thisClassType)
                        .build())));

    // Body of the second constructor method.
    List<Expr> secondCtorExprs = new ArrayList<>();
    secondCtorExprs.add(
        AssignmentExpr.builder()
            // TODO(miraleung): this.callableFactory.
            .setVariableExpr(classMemberVarExprs.get("callableFactory"))
            .setValueExpr(callableFactoryVarExpr)
            .build());
    // TODO(miraleung): this.operationsStub.
    VariableExpr operationsStubClassVarExpr = classMemberVarExprs.get("operationsStub");
    secondCtorExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(operationsStubClassVarExpr)
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(staticTypes.get("GrpcOperationsStub"))
                    .setMethodName("create")
                    .setArguments(Arrays.asList(clientContextVarExpr, callableFactoryVarExpr))
                    .setReturnType(operationsStubClassVarExpr.type())
                    .build())
            .build());

    // Transport settings local variables.
    Map<String, VariableExpr> javaStyleMethodNameToTransportSettingsVarExprs =
        service.methods().stream()
            .collect(
                Collectors.toMap(
                    m -> JavaStyle.toLowerCamelCase(m.name()),
                    m ->
                        VariableExpr.withVariable(
                            Variable.builder()
                                .setName(
                                    String.format(
                                        "%sTransportSettings",
                                        JavaStyle.toLowerCamelCase(m.name())))
                                .setType(
                                    TypeNode.withReference(
                                        ConcreteReference.builder()
                                            .setClazz(GrpcCallSettings.class)
                                            .setGenerics(
                                                Arrays.asList(
                                                    m.inputType().reference(),
                                                    m.outputType().reference()))
                                            .build()))
                                .build())));

    secondCtorExprs.addAll(
        service.methods().stream()
            .map(
                m ->
                    createTransportSettingsInitExpr(
                        javaStyleMethodNameToTransportSettingsVarExprs.get(
                            JavaStyle.toLowerCamelCase(m.name())),
                        protoMethodNameToDescriptorVarExprs.get(m.name())))
            .collect(Collectors.toList()));

    // Initialize <method>Callable variables.
    secondCtorExprs.addAll(
        callableClassMemberVarExprs.entrySet().stream()
            .map(
                e ->
                    createCallableInitExpr(
                        e.getKey(),
                        e.getValue(),
                        callableFactoryVarExpr,
                        settingsVarExpr,
                        clientContextVarExpr,
                        operationsStubClassVarExpr,
                        javaStyleMethodNameToTransportSettingsVarExprs))
            .collect(Collectors.toList()));

    // Instantiate backgroundResources.
    MethodInvocationExpr getBackgroundResourcesMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientContextVarExpr)
            .setMethodName("getBackgroundResources")
            .build();
    VariableExpr backgroundResourcesVarExpr = classMemberVarExprs.get("backgroundResources");
    secondCtorExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(backgroundResourcesVarExpr)
            .setValueExpr(
                NewObjectExpr.builder()
                    .setType(staticTypes.get("BackgroundResourceAggregation"))
                    .setArguments(Arrays.asList(getBackgroundResourcesMethodExpr))
                    .build())
            .build());

    // Second constructor method.
    MethodDefinition secondCtor =
        ctorMakerFn.apply(
            Arrays.asList(settingsVarExpr, clientContextVarExpr, callableFactoryVarExpr),
            secondCtorExprs.stream()
                .map(e -> ExprStatement.withExpr(e))
                .collect(Collectors.toList()));

    return Arrays.asList(firstCtor, secondCtor);
  }

  private static Expr createTransportSettingsInitExpr(
      VariableExpr transportSettingsVarExpr, VariableExpr methodDescriptorVarExpr) {
    MethodInvocationExpr callSettingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(staticTypes.get("GrpcCallSettings"))
            .setGenerics(transportSettingsVarExpr.type().reference().generics())
            .setMethodName("newBuilder")
            .build();
    callSettingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(callSettingsBuilderExpr)
            .setMethodName("setMethodDescriptor")
            .setArguments(Arrays.asList(methodDescriptorVarExpr))
            .build();
    callSettingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(callSettingsBuilderExpr)
            .setMethodName("build")
            .setReturnType(transportSettingsVarExpr.type())
            .build();
    return AssignmentExpr.builder()
        .setVariableExpr(transportSettingsVarExpr.toBuilder().setIsDecl(true).build())
        .setValueExpr(callSettingsBuilderExpr)
        .build();
  }

  private static Expr createCallableInitExpr(
      String callableVarName,
      VariableExpr callableVarExpr,
      VariableExpr callableFactoryVarExpr,
      VariableExpr settingsVarExpr,
      VariableExpr clientContextVarExpr,
      VariableExpr operationsStubClassVarExpr,
      Map<String, VariableExpr> javaStyleMethodNameToTransportSettingsVarExprs) {
    boolean isOperation = callableVarName.endsWith(OPERATION_CALLABLE_NAME);
    boolean isPaged = callableVarName.endsWith(PAGED_CALLABLE_NAME);
    int sublength = 0;
    if (isOperation) {
      sublength = OPERATION_CALLABLE_NAME.length();
    } else if (isPaged) {
      sublength = PAGED_CALLABLE_NAME.length();
    } else {
      sublength = CALLABLE_NAME.length();
    }
    String javaStyleMethodName = callableVarName.substring(0, callableVarName.length() - sublength);
    List<Expr> creatorMethodArgVarExprs = null;
    if (isOperation) {
      creatorMethodArgVarExprs =
          Arrays.asList(
              javaStyleMethodNameToTransportSettingsVarExprs.get(javaStyleMethodName),
              MethodInvocationExpr.builder()
                  .setExprReferenceExpr(settingsVarExpr)
                  .setMethodName(String.format("%sOperationSettings", javaStyleMethodName))
                  .build(),
              clientContextVarExpr,
              operationsStubClassVarExpr);
    } else {
      creatorMethodArgVarExprs =
          Arrays.asList(
              javaStyleMethodNameToTransportSettingsVarExprs.get(javaStyleMethodName),
              MethodInvocationExpr.builder()
                  .setExprReferenceExpr(settingsVarExpr)
                  .setMethodName(String.format("%sSettings", javaStyleMethodName))
                  .build(),
              clientContextVarExpr);
    }

    String callableCreatorMethodName = getCallableCreatorMethodName(callableVarExpr.type());
    return AssignmentExpr.builder()
        // TODO(miraleung): Reference this.
        .setVariableExpr(callableVarExpr)
        .setValueExpr(
            MethodInvocationExpr.builder()
                .setExprReferenceExpr(callableFactoryVarExpr)
                .setMethodName(callableCreatorMethodName)
                .setArguments(creatorMethodArgVarExprs)
                .setReturnType(callableVarExpr.type())
                .build())
        .build();
  }

  private static String getCallableCreatorMethodName(TypeNode callableVarExprType) {
    final String typeName = callableVarExprType.reference().name();
    String streamName = "Unary";

    // Special handling for pagination methods.
    if (callableVarExprType.reference().generics().size() == 2
        && callableVarExprType.reference().generics().get(0).name().endsWith("PagedResponse")) {
      streamName = "Paged";
    } else {
      if (typeName.startsWith("Client")) {
        streamName = "ClientStreaming";
      } else if (typeName.startsWith("Server")) {
        streamName = "ServerStreaming";
      } else if (typeName.startsWith("Bidi")) {
        streamName = "BidiStreaming";
      } else if (typeName.startsWith("Operation")) {
        streamName = "Operation";
      }
    }
    return String.format("create%sCallable", streamName);
  }

  private static MethodDefinition createOperationsStubGetterMethod(
      VariableExpr operationsStubVarExpr) {
    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(operationsStubVarExpr.type())
        .setName("getOperationsStub")
        .setReturnExpr(operationsStubVarExpr)
        .build();
  }

  private static List<MethodDefinition> createCallableGetterMethods(
      Map<String, VariableExpr> callableClassMemberVarExprs) {
    return callableClassMemberVarExprs.entrySet().stream()
        .map(
            e ->
                MethodDefinition.builder()
                    .setScope(ScopeNode.PUBLIC)
                    .setReturnType(e.getValue().type())
                    .setName(e.getKey())
                    .setReturnExpr(e.getValue())
                    .build())
        .collect(Collectors.toList());
  }

  private static List<MethodDefinition> createStubOverrideMethods(
      VariableExpr backgroundResourcesVarExpr) {
    Function<String, MethodDefinition.Builder> methodMakerStarterFn =
        methodName ->
            MethodDefinition.builder()
                .setIsOverride(true)
                .setScope(ScopeNode.PUBLIC)
                .setName(methodName);

    Function<String, MethodDefinition> voidMethodMakerFn =
        methodName ->
            methodMakerStarterFn
                .apply(methodName)
                .setReturnType(TypeNode.VOID)
                .setBody(
                    Arrays.asList(
                        ExprStatement.withExpr(
                            MethodInvocationExpr.builder()
                                .setExprReferenceExpr(backgroundResourcesVarExpr)
                                .setMethodName(methodName)
                                .build())))
                .build();

    Function<String, MethodDefinition> booleanMethodMakerFn =
        methodName ->
            methodMakerStarterFn
                .apply(methodName)
                .setReturnType(TypeNode.BOOLEAN)
                .setReturnExpr(
                    MethodInvocationExpr.builder()
                        .setExprReferenceExpr(backgroundResourcesVarExpr)
                        .setMethodName(methodName)
                        .setReturnType(TypeNode.BOOLEAN)
                        .build())
                .build();

    List<MethodDefinition> javaMethods = new ArrayList<>();
    javaMethods.add(
        methodMakerStarterFn
            .apply("close")
            .setIsFinal(true)
            .setReturnType(TypeNode.VOID)
            .setBody(
                Arrays.asList(
                    ExprStatement.withExpr(
                        MethodInvocationExpr.builder().setMethodName("shutdown").build())))
            .build());
    javaMethods.add(voidMethodMakerFn.apply("shutdown"));
    javaMethods.add(booleanMethodMakerFn.apply("isShutdown"));
    javaMethods.add(booleanMethodMakerFn.apply("isTerminated"));
    javaMethods.add(voidMethodMakerFn.apply("shutdownNow"));

    List<VariableExpr> awaitTerminationArgs =
        Arrays.asList(
            VariableExpr.withVariable(
                Variable.builder().setName("duration").setType(TypeNode.LONG).build()),
            VariableExpr.withVariable(
                Variable.builder().setName("unit").setType(staticTypes.get("TimeUnit")).build()));
    javaMethods.add(
        methodMakerStarterFn
            .apply("awaitTermination")
            .setReturnType(TypeNode.BOOLEAN)
            .setArguments(
                awaitTerminationArgs.stream()
                    .map(v -> v.toBuilder().setIsDecl(true).build())
                    .collect(Collectors.toList()))
            .setThrowsExceptions(Arrays.asList(staticTypes.get("InterruptedException")))
            .setReturnExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(backgroundResourcesVarExpr)
                    .setMethodName("awaitTermination")
                    .setArguments(
                        awaitTerminationArgs.stream()
                            .map(v -> (Expr) v)
                            .collect(Collectors.toList()))
                    .setReturnType(TypeNode.BOOLEAN)
                    .build())
            .build());
    return javaMethods;
  }

  private static Map<String, TypeNode> createStaticTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            BackgroundResource.class,
            BackgroundResourceAggregation.class,
            BidiStreamingCallable.class,
            ClientContext.class,
            ClientStreamingCallable.class,
            Generated.class,
            GrpcCallSettings.class,
            GrpcOperationsStub.class,
            GrpcStubCallableFactory.class,
            InterruptedException.class,
            IOException.class,
            MethodDescriptor.class,
            Operation.class,
            OperationCallable.class,
            ProtoUtils.class,
            ServerStreamingCallable.class,
            TimeUnit.class,
            UnaryCallable.class);
    return concreteClazzes.stream()
        .collect(
            Collectors.toMap(
                c -> c.getSimpleName(),
                c -> TypeNode.withReference(ConcreteReference.withClazz(c))));
  }

  private static Map<String, TypeNode> createDynamicTypes(Service service, String stubPakkage) {
    Map<String, TypeNode> types = new HashMap<>();
    types.putAll(
        Arrays.asList(
                CLASS_NAME_PATTERN,
                STUB_SETTINGS_PATTERN,
                STUB_PATTERN,
                GRPC_SERVICE_CALLABLE_FACTORY_PATTERN)
            .stream()
            .collect(
                Collectors.toMap(
                    p -> String.format(p, service.name()),
                    p ->
                        TypeNode.withReference(
                            VaporReference.builder()
                                .setName(String.format(p, service.name()))
                                .setPakkage(stubPakkage)
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
                                .setEnclosingClassName(String.format("%sClient", service.name()))
                                .setIsStaticImport(true)
                                .build()))));
    return types;
  }

  private static TypeNode getCallableType(Method protoMethod) {
    TypeNode callableType = staticTypes.get("UnaryCallable");
    switch (protoMethod.stream()) {
      case CLIENT:
        callableType = staticTypes.get("ClientStreamingCallable");
        break;
      case SERVER:
        callableType = staticTypes.get("ServerStreamingCallable");
        break;
      case BIDI:
        callableType = staticTypes.get("BidiStreamingCallable");
        break;
      case NONE:
        // Fall through
      default:
        // Fall through
    }

    return TypeNode.withReference(
        callableType
            .reference()
            .copyAndSetGenerics(
                Arrays.asList(
                    protoMethod.inputType().reference(), protoMethod.outputType().reference())));
  }

  private static EnumRefExpr getMethodDescriptorMethodTypeExpr(Method protoMethod) {
    String enumName = "";
    switch (protoMethod.stream()) {
      case CLIENT:
        enumName = "CLIENT_STREAMING";
        break;
      case SERVER:
        enumName = "SERVER_STREAMING";
        break;
      case BIDI:
        enumName = "BIDI_STREAMING";
        break;
      case NONE:
        // Fall through.
      default:
        enumName = "UNARY";
    }
    return EnumRefExpr.builder()
        .setName(enumName)
        .setType(
            TypeNode.withReference(
                ConcreteReference.builder().setClazz(MethodDescriptor.MethodType.class).build()))
        .build();
  }

  private static String getThisClassName(String serviceName) {
    return String.format(CLASS_NAME_PATTERN, serviceName);
  }
}
