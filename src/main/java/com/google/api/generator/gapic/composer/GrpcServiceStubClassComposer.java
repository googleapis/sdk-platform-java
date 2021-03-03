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

import com.google.api.core.BetaApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.core.BackgroundResourceAggregation;
import com.google.api.gax.grpc.GrpcCallSettings;
import com.google.api.gax.grpc.GrpcStubCallableFactory;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientContext;
import com.google.api.gax.rpc.ClientStreamingCallable;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.RequestParamsExtractor;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AnonymousClassExpr;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.CommentStatement;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.EmptyLineStatement;
import com.google.api.generator.engine.ast.EnumRefExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.JavaDocComment;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.ReferenceConstructorExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.ThisObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.comment.StubCommentComposer;
import com.google.api.generator.gapic.composer.store.TypeStore;
import com.google.api.generator.gapic.composer.utils.ClassNames;
import com.google.api.generator.gapic.composer.utils.PackageChecker;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.longrunning.Operation;
import com.google.longrunning.stub.GrpcOperationsStub;
import io.grpc.MethodDescriptor;
import io.grpc.protobuf.ProtoUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class GrpcServiceStubClassComposer implements ClassComposer {
  private static final Statement EMPTY_LINE_STATEMENT = EmptyLineStatement.create();

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

  private static final TypeStore FIXED_TYPESTORE = createStaticTypes();

  // Legacy support for the original reroute_to_grpc_interface option in gapic.yaml. These two APIs
  // predate the modern way, which is to add the RPCs directly into the proto.
  private static final Set<String> REROUTE_TO_GRPC_INTERFACE_SERVICE_ALLOWLIST =
      new HashSet<>(Arrays.asList("google.pubsub.v1"));
  private static final Set<String> REROUTE_TO_GRPC_INTERFACE_IAM_METHOD_ALLOWLIST =
      new HashSet<>(Arrays.asList("SetIamPolicy", "GetIamPolicy", "TestIamPermissions"));

  private GrpcServiceStubClassComposer() {}

  public static GrpcServiceStubClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(GapicContext ignored, Service service) {
    String pakkage = service.pakkage() + ".stub";
    TypeStore typeStore = createDynamicTypes(service, pakkage);
    String className = ClassNames.getGrpcServiceStubClassName(service);
    GapicClass.Kind kind = Kind.STUB;

    Map<String, VariableExpr> protoMethodNameToDescriptorVarExprs =
        createProtoMethodNameToDescriptorClassMembers(service);

    Map<String, VariableExpr> callableClassMemberVarExprs =
        createCallableClassMembers(service, typeStore);

    Map<String, VariableExpr> classMemberVarExprs = new LinkedHashMap<>();
    classMemberVarExprs.put(
        BACKGROUND_RESOURCES_MEMBER_NAME,
        VariableExpr.withVariable(
            Variable.builder()
                .setName(BACKGROUND_RESOURCES_MEMBER_NAME)
                .setType(FIXED_TYPESTORE.get("BackgroundResource"))
                .build()));
    classMemberVarExprs.put(
        OPERATIONS_STUB_MEMBER_NAME,
        VariableExpr.withVariable(
            Variable.builder()
                .setName(OPERATIONS_STUB_MEMBER_NAME)
                .setType(FIXED_TYPESTORE.get("GrpcOperationsStub"))
                .build()));
    classMemberVarExprs.put(
        CALLABLE_FACTORY_MEMBER_NAME,
        VariableExpr.withVariable(
            Variable.builder()
                .setName(CALLABLE_FACTORY_MEMBER_NAME)
                .setType(FIXED_TYPESTORE.get("GrpcStubCallableFactory"))
                .build()));

    List<Statement> classStatements =
        createClassStatements(
            service,
            protoMethodNameToDescriptorVarExprs,
            callableClassMemberVarExprs,
            classMemberVarExprs);

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(pakkage)
            .setHeaderCommentStatements(
                StubCommentComposer.createGrpcServiceStubClassHeaderComments(service.name()))
            .setAnnotations(createClassAnnotations(service.pakkage()))
            .setScope(ScopeNode.PUBLIC)
            .setName(className)
            .setExtendsType(typeStore.get(ClassNames.getServiceStubClassName(service)))
            .setStatements(classStatements)
            .setMethods(
                createClassMethods(
                    service,
                    typeStore,
                    classMemberVarExprs,
                    callableClassMemberVarExprs,
                    protoMethodNameToDescriptorVarExprs))
            .build();
    return GapicClass.create(kind, classDef);
  }

  private static List<Statement> createClassStatements(
      Service service,
      Map<String, VariableExpr> protoMethodNameToDescriptorVarExprs,
      Map<String, VariableExpr> callableClassMemberVarExprs,
      Map<String, VariableExpr> classMemberVarExprs) {
    List<Statement> classStatements = new ArrayList<>();
    for (Statement statement :
        createMethodDescriptorVariableDecls(service, protoMethodNameToDescriptorVarExprs)) {
      classStatements.add(statement);
      classStatements.add(EMPTY_LINE_STATEMENT);
    }

    classStatements.addAll(createClassMemberFieldDeclarations(callableClassMemberVarExprs));
    classStatements.add(EMPTY_LINE_STATEMENT);

    classStatements.addAll(createClassMemberFieldDeclarations(classMemberVarExprs));
    return classStatements;
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
            .setStaticReferenceType(FIXED_TYPESTORE.get("MethodDescriptor"))
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

    String codeMethodNameArg = getProtoRpcFullMethodName(service, protoMethod);
    methodDescriptorMaker =
        methodMakerFn
            .apply(
                "setFullMethodName",
                ValueExpr.withValue(StringObjectValue.withValue(codeMethodNameArg)))
            .apply(methodDescriptorMaker);

    Function<MethodInvocationExpr, MethodInvocationExpr> protoUtilsMarshallerFn =
        m ->
            MethodInvocationExpr.builder()
                .setStaticReferenceType(FIXED_TYPESTORE.get("ProtoUtils"))
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
                methodDescriptorVarExpr
                    .toBuilder()
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
      Service service, TypeStore typeStore) {
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
                                        typeStore
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

  private static List<AnnotationNode> createClassAnnotations(String pakkage) {
    List<AnnotationNode> annotations = new ArrayList<>();
    if (!PackageChecker.isGaApi(pakkage)) {
      annotations.add(AnnotationNode.withType(FIXED_TYPESTORE.get("BetaApi")));
    }
    annotations.add(
        AnnotationNode.builder()
            .setType(FIXED_TYPESTORE.get("Generated"))
            .setDescription("by gapic-generator-java")
            .build());
    return annotations;
  }

  private static List<MethodDefinition> createClassMethods(
      Service service,
      TypeStore typeStore,
      Map<String, VariableExpr> classMemberVarExprs,
      Map<String, VariableExpr> callableClassMemberVarExprs,
      Map<String, VariableExpr> protoMethodNameToDescriptorVarExprs) {
    List<MethodDefinition> javaMethods = new ArrayList<>();
    javaMethods.addAll(createStaticCreatorMethods(service, typeStore));
    javaMethods.addAll(
        createConstructorMethods(
            service,
            typeStore,
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
      Service service, TypeStore typeStore) {
    TypeNode creatorMethodReturnType =
        typeStore.get(ClassNames.getGrpcServiceStubClassName(service));
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

    TypeNode stubSettingsType = typeStore.get(ClassNames.getServiceStubSettingsClassName(service));
    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("settings").setType(stubSettingsType).build());

    TypeNode clientContextType = FIXED_TYPESTORE.get("ClientContext");
    VariableExpr clientContextVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("clientContext").setType(clientContextType).build());

    VariableExpr callableFactoryVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("callableFactory")
                .setType(FIXED_TYPESTORE.get("GrpcStubCallableFactory"))
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
      TypeStore typeStore,
      Map<String, VariableExpr> classMemberVarExprs,
      Map<String, VariableExpr> callableClassMemberVarExprs,
      Map<String, VariableExpr> protoMethodNameToDescriptorVarExprs) {
    TypeNode stubSettingsType = typeStore.get(ClassNames.getServiceStubSettingsClassName(service));
    VariableExpr settingsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("settings").setType(stubSettingsType).build());

    TypeNode clientContextType = FIXED_TYPESTORE.get("ClientContext");
    VariableExpr clientContextVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("clientContext").setType(clientContextType).build());

    VariableExpr callableFactoryVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("callableFactory")
                .setType(FIXED_TYPESTORE.get("GrpcStubCallableFactory"))
                .build());

    TypeNode thisClassType = typeStore.get(ClassNames.getGrpcServiceStubClassName(service));
    TypeNode ioExceptionType =
        TypeNode.withReference(ConcreteReference.withClazz(IOException.class));

    BiFunction<List<VariableExpr>, List<Statement>, MethodDefinition> ctorMakerFn =
        (args, body) ->
            MethodDefinition.constructorBuilder()
                .setScope(ScopeNode.PROTECTED)
                .setReturnType(thisClassType)
                .setHeaderCommentStatements(Arrays.asList(createProtectedCtorComment(service)))
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
                    ReferenceConstructorExpr.thisBuilder()
                        .setType(thisClassType)
                        .setArguments(
                            settingsVarExpr,
                            clientContextVarExpr,
                            NewObjectExpr.builder()
                                .setType(
                                    typeStore.get(
                                        ClassNames.getGrpcServiceCallableFactoryClassName(service)))
                                .build())
                        .build())));

    Expr thisExpr =
        ValueExpr.withValue(
            ThisObjectValue.withType(
                typeStore.get(ClassNames.getGrpcServiceStubClassName(service))));
    // Body of the second constructor method.
    List<Statement> secondCtorStatements = new ArrayList<>();
    List<Expr> secondCtorExprs = new ArrayList<>();
    secondCtorExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(
                classMemberVarExprs
                    .get("callableFactory")
                    .toBuilder()
                    .setExprReferenceExpr(thisExpr)
                    .build())
            .setValueExpr(callableFactoryVarExpr)
            .build());
    VariableExpr operationsStubClassVarExpr = classMemberVarExprs.get("operationsStub");
    secondCtorExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(
                operationsStubClassVarExpr.toBuilder().setExprReferenceExpr(thisExpr).build())
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(FIXED_TYPESTORE.get("GrpcOperationsStub"))
                    .setMethodName("create")
                    .setArguments(Arrays.asList(clientContextVarExpr, callableFactoryVarExpr))
                    .setReturnType(operationsStubClassVarExpr.type())
                    .build())
            .build());
    secondCtorStatements.addAll(
        secondCtorExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()));
    secondCtorExprs.clear();
    secondCtorStatements.add(EMPTY_LINE_STATEMENT);

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
                        m,
                        javaStyleMethodNameToTransportSettingsVarExprs.get(
                            JavaStyle.toLowerCamelCase(m.name())),
                        protoMethodNameToDescriptorVarExprs.get(m.name())))
            .collect(Collectors.toList()));
    secondCtorStatements.addAll(
        secondCtorExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()));
    secondCtorExprs.clear();
    secondCtorStatements.add(EMPTY_LINE_STATEMENT);

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
                        thisExpr,
                        javaStyleMethodNameToTransportSettingsVarExprs))
            .collect(Collectors.toList()));
    secondCtorStatements.addAll(
        secondCtorExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()));
    secondCtorExprs.clear();
    secondCtorStatements.add(EMPTY_LINE_STATEMENT);

    // Instantiate backgroundResources.
    MethodInvocationExpr getBackgroundResourcesMethodExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(clientContextVarExpr)
            .setMethodName("getBackgroundResources")
            .build();
    VariableExpr backgroundResourcesVarExpr = classMemberVarExprs.get("backgroundResources");
    secondCtorExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(
                backgroundResourcesVarExpr.toBuilder().setExprReferenceExpr(thisExpr).build())
            .setValueExpr(
                NewObjectExpr.builder()
                    .setType(FIXED_TYPESTORE.get("BackgroundResourceAggregation"))
                    .setArguments(Arrays.asList(getBackgroundResourcesMethodExpr))
                    .build())
            .build());
    secondCtorStatements.addAll(
        secondCtorExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()));
    secondCtorExprs.clear();

    // Second constructor method.
    MethodDefinition secondCtor =
        ctorMakerFn.apply(
            Arrays.asList(settingsVarExpr, clientContextVarExpr, callableFactoryVarExpr),
            secondCtorStatements);

    return Arrays.asList(firstCtor, secondCtor);
  }

  private static Expr createTransportSettingsInitExpr(
      Method method, VariableExpr transportSettingsVarExpr, VariableExpr methodDescriptorVarExpr) {
    MethodInvocationExpr callSettingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(FIXED_TYPESTORE.get("GrpcCallSettings"))
            .setGenerics(transportSettingsVarExpr.type().reference().generics())
            .setMethodName("newBuilder")
            .build();
    callSettingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(callSettingsBuilderExpr)
            .setMethodName("setMethodDescriptor")
            .setArguments(Arrays.asList(methodDescriptorVarExpr))
            .build();

    if (method.hasHttpBindings()) {
      callSettingsBuilderExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(callSettingsBuilderExpr)
              .setMethodName("setParamsExtractor")
              .setArguments(createRequestParamsExtractorAnonClass(method))
              .build();
    }

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

  private static AnonymousClassExpr createRequestParamsExtractorAnonClass(Method method) {
    Preconditions.checkState(
        method.hasHttpBindings(), String.format("Method %s has no HTTP binding", method.name()));

    TypeNode paramsVarType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ImmutableMap.Builder.class)
                .setGenerics(TypeNode.STRING.reference(), TypeNode.STRING.reference())
                .build());
    VariableExpr paramsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("params").setType(paramsVarType).build());
    VariableExpr reqeustVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("request").setType(method.inputType()).build());

    Expr paramsAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(paramsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setStaticReferenceType(FIXED_TYPESTORE.get("ImmutableMap"))
                    .setMethodName("builder")
                    .setReturnType(paramsVarType)
                    .build())
            .build();
    List<Expr> bodyExprs = new ArrayList<>();
    bodyExprs.add(paramsAssignExpr);

    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(method.inputType()).setName("request").build());

    for (String httpBindingFieldName : method.httpBindings()) {
      // Handle foo.bar cases by descending into the subfields.
      MethodInvocationExpr.Builder requestFieldGetterExprBuilder =
          MethodInvocationExpr.builder().setExprReferenceExpr(requestVarExpr);
      String[] descendantFields = httpBindingFieldName.split("\\.");
      for (int i = 0; i < descendantFields.length; i++) {
        String currFieldName = descendantFields[i];
        String bindingFieldMethodName =
            String.format("get%s", JavaStyle.toUpperCamelCase(currFieldName));
        requestFieldGetterExprBuilder =
            requestFieldGetterExprBuilder.setMethodName(bindingFieldMethodName);
        if (i < descendantFields.length - 1) {
          requestFieldGetterExprBuilder =
              MethodInvocationExpr.builder()
                  .setExprReferenceExpr(requestFieldGetterExprBuilder.build());
        }
      }

      MethodInvocationExpr requestBuilderExpr = requestFieldGetterExprBuilder.build();
      Expr valueOfExpr =
          MethodInvocationExpr.builder()
              .setStaticReferenceType(TypeNode.STRING)
              .setMethodName("valueOf")
              .setArguments(requestBuilderExpr)
              .build();

      Expr paramsPutExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(paramsVarExpr)
              .setMethodName("put")
              .setArguments(
                  ValueExpr.withValue(StringObjectValue.withValue(httpBindingFieldName)),
                  valueOfExpr)
              .build();
      bodyExprs.add(paramsPutExpr);
    }

    TypeNode returnType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(Map.class)
                .setGenerics(TypeNode.STRING.reference(), TypeNode.STRING.reference())
                .build());
    Expr returnExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(paramsVarExpr)
            .setMethodName("build")
            .setReturnType(returnType)
            .build();

    MethodDefinition extractMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(returnType)
            .setName("extract")
            .setArguments(requestVarExpr.toBuilder().setIsDecl(true).build())
            .setBody(
                bodyExprs.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList()))
            .setReturnExpr(returnExpr)
            .build();

    TypeNode anonClassType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(RequestParamsExtractor.class)
                .setGenerics(method.inputType().reference())
                .build());
    return AnonymousClassExpr.builder().setType(anonClassType).setMethods(extractMethod).build();
  }

  private static Expr createCallableInitExpr(
      String callableVarName,
      VariableExpr callableVarExpr,
      VariableExpr callableFactoryVarExpr,
      VariableExpr settingsVarExpr,
      VariableExpr clientContextVarExpr,
      VariableExpr operationsStubClassVarExpr,
      Expr thisExpr,
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
    Expr transportSettingsVarExpr =
        javaStyleMethodNameToTransportSettingsVarExprs.get(javaStyleMethodName);
    if (transportSettingsVarExpr == null && isOperation) {
      // Try again, in case the name dtection above was inaccurate.
      isOperation = false;
      sublength = CALLABLE_NAME.length();
      javaStyleMethodName = callableVarName.substring(0, callableVarName.length() - sublength);
      transportSettingsVarExpr =
          javaStyleMethodNameToTransportSettingsVarExprs.get(javaStyleMethodName);
    }
    Preconditions.checkNotNull(
        transportSettingsVarExpr,
        String.format(
            "No transport settings variable found for method name %s", javaStyleMethodName));
    if (isOperation) {
      creatorMethodArgVarExprs =
          Arrays.asList(
              transportSettingsVarExpr,
              MethodInvocationExpr.builder()
                  .setExprReferenceExpr(settingsVarExpr)
                  .setMethodName(String.format("%sOperationSettings", javaStyleMethodName))
                  .build(),
              clientContextVarExpr,
              operationsStubClassVarExpr);
    } else {
      creatorMethodArgVarExprs =
          Arrays.asList(
              transportSettingsVarExpr,
              MethodInvocationExpr.builder()
                  .setExprReferenceExpr(settingsVarExpr)
                  .setMethodName(String.format("%sSettings", javaStyleMethodName))
                  .build(),
              clientContextVarExpr);
    }

    String callableCreatorMethodName = getCallableCreatorMethodName(callableVarExpr.type());
    return AssignmentExpr.builder()
        .setVariableExpr(callableVarExpr.toBuilder().setExprReferenceExpr(thisExpr).build())
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
        && callableVarExprType.reference().generics().get(1).name().endsWith("PagedResponse")) {
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
                    .setIsOverride(true)
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
                Variable.builder()
                    .setName("unit")
                    .setType(FIXED_TYPESTORE.get("TimeUnit"))
                    .build()));
    javaMethods.add(
        methodMakerStarterFn
            .apply("awaitTermination")
            .setReturnType(TypeNode.BOOLEAN)
            .setArguments(
                awaitTerminationArgs.stream()
                    .map(v -> v.toBuilder().setIsDecl(true).build())
                    .collect(Collectors.toList()))
            .setThrowsExceptions(Arrays.asList(FIXED_TYPESTORE.get("InterruptedException")))
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

  private static TypeStore createStaticTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            BackgroundResource.class,
            BackgroundResourceAggregation.class,
            BetaApi.class,
            BidiStreamingCallable.class,
            ClientContext.class,
            ClientStreamingCallable.class,
            Generated.class,
            GrpcCallSettings.class,
            GrpcOperationsStub.class,
            GrpcStubCallableFactory.class,
            ImmutableMap.class,
            InterruptedException.class,
            IOException.class,
            MethodDescriptor.class,
            Operation.class,
            OperationCallable.class,
            ProtoUtils.class,
            RequestParamsExtractor.class,
            ServerStreamingCallable.class,
            TimeUnit.class,
            UnaryCallable.class);
    return new TypeStore(concreteClazzes);
  }

  private static TypeStore createDynamicTypes(Service service, String stubPakkage) {
    TypeStore typeStore = new TypeStore();
    typeStore.putAll(
        stubPakkage,
        Arrays.asList(
            ClassNames.getGrpcServiceStubClassName(service),
            ClassNames.getServiceStubSettingsClassName(service),
            ClassNames.getServiceStubClassName(service),
            ClassNames.getGrpcServiceCallableFactoryClassName(service)));
    // Pagination types.
    typeStore.putAll(
        service.pakkage(),
        service.methods().stream()
            .filter(m -> m.isPaged())
            .map(m -> String.format(PAGED_RESPONSE_TYPE_NAME_PATTERN, m.name()))
            .collect(Collectors.toList()),
        true,
        ClassNames.getServiceClientClassName(service));
    return typeStore;
  }

  private static TypeNode getCallableType(Method protoMethod) {
    TypeNode callableType = FIXED_TYPESTORE.get("UnaryCallable");
    switch (protoMethod.stream()) {
      case CLIENT:
        callableType = FIXED_TYPESTORE.get("ClientStreamingCallable");
        break;
      case SERVER:
        callableType = FIXED_TYPESTORE.get("ServerStreamingCallable");
        break;
      case BIDI:
        callableType = FIXED_TYPESTORE.get("BidiStreamingCallable");
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

  private static String getProtoRpcFullMethodName(Service protoService, Method protoMethod) {
    if (protoMethod.isMixin()) {
      return String.format("%s/%s", protoMethod.mixedInApiName(), protoMethod.name());
    }

    if (!REROUTE_TO_GRPC_INTERFACE_SERVICE_ALLOWLIST.contains(protoService.protoPakkage())
        || !REROUTE_TO_GRPC_INTERFACE_IAM_METHOD_ALLOWLIST.contains(protoMethod.name())) {
      return String.format(
          "%s.%s/%s", protoService.protoPakkage(), protoService.name(), protoMethod.name());
    }
    // This is meant to be a temporary workaround until the allow-listed services come up with a
    // long-term solution.
    return String.format("google.iam.v1.IAMPolicy/%s", protoMethod.name());
  }

  private static CommentStatement createProtectedCtorComment(Service service) {
    return CommentStatement.withComment(
        JavaDocComment.withComment(
            String.format(
                "Constructs an instance of %s, using the given settings. This is protected so that"
                    + " it is easy to make a subclass, but otherwise, the static factory methods"
                    + " should be  preferred.",
                ClassNames.getGrpcServiceStubClassName(service))));
  }
}
