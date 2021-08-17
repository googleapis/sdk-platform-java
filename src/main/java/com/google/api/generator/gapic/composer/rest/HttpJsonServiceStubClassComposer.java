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

package com.google.api.generator.gapic.composer.rest;

import com.google.api.client.http.HttpMethods;
import com.google.api.core.InternalApi;
import com.google.api.gax.httpjson.ApiMethodDescriptor;
import com.google.api.gax.httpjson.FieldsExtractor;
import com.google.api.gax.httpjson.HttpJsonCallSettings;
import com.google.api.gax.httpjson.HttpJsonOperationSnapshot;
import com.google.api.gax.httpjson.HttpJsonStubCallableFactory;
import com.google.api.gax.httpjson.ProtoMessageRequestFormatter;
import com.google.api.gax.httpjson.ProtoMessageResponseParser;
import com.google.api.gax.httpjson.ProtoRestSerializer;
import com.google.api.gax.longrunning.OperationSnapshot;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.EnumRefExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.LambdaExpr;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.common.AbstractServiceStubClassComposer;
import com.google.api.generator.gapic.composer.store.TypeStore;
import com.google.api.generator.gapic.model.HttpBindings.HttpBinding;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HttpJsonServiceStubClassComposer extends AbstractServiceStubClassComposer {
  private static final HttpJsonServiceStubClassComposer INSTANCE =
      new HttpJsonServiceStubClassComposer();

  private static final TypeStore FIXED_REST_TYPESTORE = createStaticTypes();

  protected HttpJsonServiceStubClassComposer() {
    super(RestContext.instance());
  }

  public static HttpJsonServiceStubClassComposer instance() {
    return INSTANCE;
  }

  private static TypeStore createStaticTypes() {
    return new TypeStore(
        Arrays.asList(
            ApiMethodDescriptor.class,
            ArrayList.class,
            FieldsExtractor.class,
            InternalApi.class,
            HashMap.class,
            HttpJsonCallSettings.class,
            HttpJsonStubCallableFactory.class,
            Map.class,
            ProtoMessageRequestFormatter.class,
            ProtoMessageResponseParser.class,
            ProtoRestSerializer.class));
  }

  @Override
  protected Statement createMethodDescriptorVariableDecl(
      Service service, Method protoMethod, VariableExpr methodDescriptorVarExpr) {
    MethodInvocationExpr expr =
        MethodInvocationExpr.builder()
            .setMethodName("newBuilder")
            .setStaticReferenceType(
                FIXED_REST_TYPESTORE.get(ApiMethodDescriptor.class.getSimpleName()))
            .setGenerics(methodDescriptorVarExpr.variable().type().reference().generics())
            .build();

    BiFunction<String, List<Expr>, Function<MethodInvocationExpr, MethodInvocationExpr>>
        methodMaker = getMethodMaker();

    String codeMethodArgName = getProtoRpcFullMethodName(service, protoMethod);
    expr =
        methodMaker
            .apply(
                "setFullMethodName",
                Arrays.asList(ValueExpr.withValue(StringObjectValue.withValue(codeMethodArgName))))
            .apply(expr);

    expr = methodMaker.apply("setHttpMethod", getHttpMethodTypeExpr(protoMethod)).apply(expr);
    expr =
        methodMaker.apply("setRequestFormatter", getRequestFormatterExpr(protoMethod)).apply(expr);
    expr = methodMaker.apply("setResponseParser", setResponseParserExpr(protoMethod)).apply(expr);

    expr =
        methodMaker
            .apply("setOperationSnapshotFactory", setOperationSnapshotFactoryExpr(protoMethod))
            .apply(expr);
    expr =
        methodMaker
            .apply("setPollingRequestFactory", setPollingRequestFactoryExpr(protoMethod))
            .apply(expr);

    expr =
        MethodInvocationExpr.builder()
            .setMethodName("build")
            .setExprReferenceExpr(expr)
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
            .setValueExpr(expr)
            .build());
  }

  @Override
  protected List<MethodDefinition> createOperationsStubGetterMethod(
      VariableExpr operationsStubVarExpr) {
    return Collections.emptyList();
  }

  @Override
  protected Expr createTransportSettingsInitExpr(
      Method method, VariableExpr transportSettingsVarExpr, VariableExpr methodDescriptorVarExpr) {
    MethodInvocationExpr callSettingsBuilderExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(
                FIXED_REST_TYPESTORE.get(HttpJsonCallSettings.class.getSimpleName()))
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

  @Override
  protected List<AnnotationNode> createClassAnnotations(Service service) {
    List<AnnotationNode> annotations = super.createClassAnnotations(service);

    annotations.add(
        AnnotationNode.builder()
            .setType(FIXED_TYPESTORE.get("BetaApi"))
            .setDescription(
                "A restructuring of stub classes is planned, so this may break in the future")
            .build());
    return annotations;
  }

  @Override
  protected List<MethodDefinition> createGetMethodDescriptorsMethod(
      Service service,
      TypeStore typeStore,
      Map<String, VariableExpr> protoMethodNameToDescriptorVarExprs) {

    List<Expr> bodyExprs = new ArrayList<>();

    VariableExpr methodDescriptorsVarExpr =
        VariableExpr.withVariable(
            Variable.builder()
                .setType(
                    TypeNode.withReference(
                        ConcreteReference.builder()
                            .setClazz(List.class)
                            .setGenerics(
                                Arrays.asList(
                                    FIXED_REST_TYPESTORE.get("ApiMethodDescriptor").reference()))
                            .build()))
                .setName("methodDescriptors")
                .build());

    bodyExprs.add(
        AssignmentExpr.builder()
            .setVariableExpr(methodDescriptorsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(
                NewObjectExpr.builder()
                    .setType(FIXED_REST_TYPESTORE.get("ArrayList"))
                    .setIsGeneric(true)
                    .build())
            .build());

    for (VariableExpr methodDescriptorVarExpr : protoMethodNameToDescriptorVarExprs.values()) {
      bodyExprs.add(
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(methodDescriptorsVarExpr)
              .setMethodName("add")
              .setArguments(methodDescriptorVarExpr)
              .build());
    }

    return Arrays.asList(
        MethodDefinition.builder()
            .setScope(ScopeNode.PUBLIC)
            .setIsStatic(true)
            .setReturnType(methodDescriptorsVarExpr.type())
            .setReturnExpr(methodDescriptorsVarExpr)
            .setAnnotations(
                Arrays.asList(AnnotationNode.withType(FIXED_REST_TYPESTORE.get("InternalApi"))))
            .setName("getMethodDescriptors")
            .setBody(bodyExprs.stream().map(ExprStatement::withExpr).collect(Collectors.toList()))
            .build());
  }

  private BiFunction<String, List<Expr>, Function<MethodInvocationExpr, MethodInvocationExpr>>
      getMethodMaker() {
    return (mName, argExpr) ->
        (m) ->
            MethodInvocationExpr.builder()
                .setMethodName(mName)
                .setArguments(argExpr)
                .setExprReferenceExpr(m)
                .build();
  }

  private List<Expr> getRequestFormatterExpr(Method protoMethod) {
    BiFunction<String, List<Expr>, Function<MethodInvocationExpr, MethodInvocationExpr>>
        methodMaker = getMethodMaker();

    MethodInvocationExpr expr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(
                FIXED_REST_TYPESTORE.get(ProtoMessageRequestFormatter.class.getSimpleName()))
            .setMethodName("newBuilder")
            .setGenerics(Collections.singletonList(protoMethod.inputType().reference()))
            .build();

    TypeNode extractorVarType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(Map.class)
                .setGenerics(TypeNode.STRING.reference(), TypeNode.STRING.reference())
                .build());

    expr =
        methodMaker
            .apply(
                "setPath",
                Arrays.asList(
                    ValueExpr.withValue(
                        StringObjectValue.withValue(
                            protoMethod.httpBindings().patternLowerCamel())),
                    createFieldsExtractorClassInstance(
                        protoMethod,
                        extractorVarType,
                        protoMethod.httpBindings().pathParameters(),
                        "putPathParam")))
            .apply(expr);

    TypeNode fieldsVarGenericType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(List.class)
                .setGenerics(TypeNode.STRING.reference())
                .build());

    extractorVarType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(Map.class)
                .setGenerics(TypeNode.STRING.reference(), fieldsVarGenericType.reference())
                .build());

    expr =
        methodMaker
            .apply(
                "setQueryParamsExtractor",
                Arrays.asList(
                    createFieldsExtractorClassInstance(
                        protoMethod,
                        extractorVarType,
                        protoMethod.httpBindings().queryParameters(),
                        "putQueryParam")))
            .apply(expr);

    extractorVarType = TypeNode.STRING;
    expr =
        methodMaker
            .apply(
                "setRequestBodyExtractor",
                Arrays.asList(
                    createFieldsExtractorClassInstance(
                        protoMethod,
                        extractorVarType,
                        protoMethod.httpBindings().bodyParameters(),
                        "toBody")))
            .apply(expr);
    expr = methodMaker.apply("build", Collections.emptyList()).apply(expr);

    return Collections.singletonList(expr);
  }

  private List<Expr> setResponseParserExpr(Method protoMethod) {
    BiFunction<String, List<Expr>, Function<MethodInvocationExpr, MethodInvocationExpr>>
        methodMaker = getMethodMaker();

    MethodInvocationExpr expr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(
                FIXED_REST_TYPESTORE.get(ProtoMessageResponseParser.class.getSimpleName()))
            .setMethodName("newBuilder")
            .setGenerics(Collections.singletonList(protoMethod.outputType().reference()))
            // .setArguments(Arrays.asList(m))
            .build();

    expr =
        methodMaker
            .apply(
                "setDefaultInstance",
                Arrays.asList(
                    MethodInvocationExpr.builder()
                        .setStaticReferenceType(protoMethod.outputType())
                        .setMethodName("getDefaultInstance")
                        .setReturnType(protoMethod.outputType())
                        .build()))
            .apply(expr);
    expr = methodMaker.apply("build", Collections.emptyList()).apply(expr);

    return Collections.singletonList(expr);
  }

  private List<Expr> setOperationSnapshotFactoryExpr(Method protoMethod) {

    BiFunction<String, List<Expr>, Function<MethodInvocationExpr, MethodInvocationExpr>>
        methodMaker = getMethodMaker();

    // Generate input varibles for create()
    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(protoMethod.inputType()).setName("request").build());
    VariableExpr responseVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(protoMethod.outputType()).setName("response").build());

    List<Statement> createBody = new ArrayList<Statement>(4);

    // Generate opName
    // This will be replaced and edited based on annotations
    TypeNode stringBuilderType =
        TypeNode.withReference(ConcreteReference.withClazz(StringBuilder.class));
    VariableExpr opNameVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(stringBuilderType).setName("opName").build());
    MethodInvocationExpr getId =
        MethodInvocationExpr.builder()
            .setMethodName("getId")
            .setExprReferenceExpr(responseVarExpr)
            .build();
    Expr opNameObjectExpr =
        NewObjectExpr.builder().setType(stringBuilderType).setArguments(getId).build();
    AssignmentExpr opNameAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(opNameVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(opNameObjectExpr)
            .build();
    createBody.add(ExprStatement.withExpr(opNameAssignExpr));

    // Generate changes opName
    // This will be replaced and edited based on annotations
    MethodInvocationExpr requestGetProjectExpr =
        MethodInvocationExpr.builder()
            .setMethodName("getProject")
            .setExprReferenceExpr(requestVarExpr)
            .build();
    ValueExpr colonValueExpr =
        ValueExpr.builder().setValue(StringObjectValue.builder().setValue(":").build()).build();
    MethodInvocationExpr opNameAppendColonProjectExpr =
        MethodInvocationExpr.builder()
            .setMethodName("append")
            .setArguments(colonValueExpr)
            .setExprReferenceExpr(opNameVarExpr)
            .build();
    opNameAppendColonProjectExpr =
        methodMaker
            .apply("append", Collections.singletonList(requestGetProjectExpr))
            .apply(opNameAppendColonProjectExpr);
    createBody.add(ExprStatement.withExpr(opNameAppendColonProjectExpr));

    // Generate changes to opName
    MethodInvocationExpr requestGetRegionExpr =
        MethodInvocationExpr.builder()
            .setMethodName("getRegion")
            .setExprReferenceExpr(requestVarExpr)
            .build();
    MethodInvocationExpr opNameAppendColonRegionExpr =
        MethodInvocationExpr.builder()
            .setMethodName("append")
            .setArguments(colonValueExpr)
            .setExprReferenceExpr(opNameVarExpr)
            .build();
    opNameAppendColonRegionExpr =
        methodMaker
            .apply("append", Collections.singletonList(requestGetRegionExpr))
            .apply(opNameAppendColonRegionExpr);
    createBody.add(ExprStatement.withExpr(opNameAppendColonRegionExpr));

    // Generate check status expression
    // This will be replaced and edited based on annotations
    MethodInvocationExpr getStatusExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(responseVarExpr)
            .setMethodName("getStatus")
            .build();
    TypeNode statusType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("Status")
                .setPakkage("com.google.cloud.compute.v1")
                .setIsStaticImport(false)
                .build());
    VariableExpr statusDoneExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setName("DONE").setType(TypeNode.INT).build())
            .setStaticReferenceType(statusType)
            .build();
    MethodInvocationExpr statusEqualsExpr =
        methodMaker.apply("equals", Collections.singletonList(statusDoneExpr)).apply(getStatusExpr);

    // Generate return statement
    TypeNode httpJsonOperationSnapshotType =
        TypeNode.withReference(ConcreteReference.withClazz(HttpJsonOperationSnapshot.class));
    MethodInvocationExpr newBuilderExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(httpJsonOperationSnapshotType)
            .setMethodName("newBuilder")
            .build();
    MethodInvocationExpr opNameToStringExpr =
        MethodInvocationExpr.builder()
            .setMethodName("toString")
            .setExprReferenceExpr(opNameVarExpr)
            .build();
    // This will be replaced and edited based on annotations
    MethodInvocationExpr getHttpErrorStatusCodeExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(responseVarExpr)
            .setMethodName("getHttpErrorStatusCode")
            .build();
    // This will be replaced and edited based on annotations
    MethodInvocationExpr getHttpErrorMessageExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(responseVarExpr)
            .setMethodName("getHttpErrorMessage")
            .build();
    newBuilderExpr =
        methodMaker
            .apply("setName", Collections.singletonList(opNameToStringExpr))
            .apply(newBuilderExpr);
    newBuilderExpr =
        methodMaker
            .apply("setMetadata", Collections.singletonList(responseVarExpr))
            .apply(newBuilderExpr);
    newBuilderExpr =
        methodMaker
            .apply("setDone", Collections.singletonList(statusEqualsExpr))
            .apply(newBuilderExpr);
    newBuilderExpr =
        methodMaker
            .apply("setResponse", Collections.singletonList(responseVarExpr))
            .apply(newBuilderExpr);
    newBuilderExpr =
        methodMaker
            .apply("setError", Arrays.asList(getHttpErrorStatusCodeExpr, getHttpErrorMessageExpr))
            .apply(newBuilderExpr);
    TypeNode operationSnapshotType =
        TypeNode.withReference(
            ConcreteReference.builder().setClazz(OperationSnapshot.class).build());
    MethodInvocationExpr buildExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(newBuilderExpr)
            .setMethodName("build")
            .setReturnType(operationSnapshotType)
            .build();

    // Generate lambda anonymous class
    return Collections.singletonList(
        LambdaExpr.builder()
            .setArguments(
                requestVarExpr.toBuilder().setIsDecl(true).build(),
                responseVarExpr.toBuilder().setIsDecl(true).build())
            .setBody(createBody)
            .setReturnExpr(buildExpr)
            .build());
  }

  private List<Expr> setPollingRequestFactoryExpr(Method protoMethod) {

    BiFunction<String, List<Expr>, Function<MethodInvocationExpr, MethodInvocationExpr>>
        methodMaker = getMethodMaker();

    List<Statement> createBody = new ArrayList<Statement>(1);

    // Generate input variables for create
    VariableExpr compoundOperationIdVarExpr =
        VariableExpr.builder()
            .setVariable(
                Variable.builder().setType(TypeNode.STRING).setName("compoundOperationId").build())
            .build();

    // Generate idComponenets
    TypeNode listStringType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(List.class)
                .setGenerics(ConcreteReference.withClazz(String.class))
                .build());
    TypeNode arrayListStringType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(ArrayList.class)
                .setGenerics(ConcreteReference.withClazz(String.class))
                .build());
    TypeNode arraysType = TypeNode.withReference(ConcreteReference.withClazz(Arrays.class));
    VariableExpr idComponentsVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("idComponents").setType(listStringType).build());
    MethodInvocationExpr compoundOperationIdSplitExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(compoundOperationIdVarExpr)
            .setMethodName("split")
            .setArguments(ValueExpr.withValue(StringObjectValue.withValue(":")))
            .setReturnType(arrayListStringType)
            .build();
    MethodInvocationExpr asListExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(arraysType)
            .setMethodName("asList")
            .setArguments(compoundOperationIdSplitExpr)
            .setReturnType(arrayListStringType)
            .build();
    AssignmentExpr idComponentsAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(idComponentsVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(asListExpr)
            .build();
    createBody.add(ExprStatement.withExpr(idComponentsAssignExpr));

    // Generate return statement
    // This will be replaced and edited based on annotations
    TypeNode getRegionOperationRequestType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("GetRegionOperationRequest")
                .setPakkage("com.google.cloud.compute.v1")
                .setIsStaticImport(false)
                .build());
    MethodInvocationExpr newBuilderExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(getRegionOperationRequestType)
            .setMethodName("newBuilder")
            .build();
    newBuilderExpr =
        methodMaker
            .apply("setOperation", Collections.singletonList(getExpr(idComponentsVarExpr, "0")))
            .apply(newBuilderExpr);
    newBuilderExpr =
        methodMaker
            .apply("setProject", Collections.singletonList(getExpr(idComponentsVarExpr, "1")))
            .apply(newBuilderExpr);
    newBuilderExpr =
        methodMaker
            .apply("setRegion", Collections.singletonList(getExpr(idComponentsVarExpr, "2")))
            .apply(newBuilderExpr);
    MethodInvocationExpr buildExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(newBuilderExpr)
            .setMethodName("build")
            .setReturnType(getRegionOperationRequestType)
            .build();

    // Return lambda anonymous class
    return Collections.singletonList(
        LambdaExpr.builder()
            .setArguments(compoundOperationIdVarExpr.toBuilder().setIsDecl(true).build())
            .setBody(createBody)
            .setReturnExpr(buildExpr)
            .build());
  }

  // returns var.get(num);
  private MethodInvocationExpr getExpr(VariableExpr var, String num) {
    return MethodInvocationExpr.builder()
        .setExprReferenceExpr(var)
        .setMethodName("get")
        .setArguments(
            ValueExpr.builder()
                .setValue(PrimitiveValue.builder().setValue(num).setType(TypeNode.INT).build())
                .build())
        .build();
  }

  private Expr createFieldsExtractorClassInstance(
      Method method,
      TypeNode extractorReturnType,
      Set<HttpBinding> httpBindingFieldNames,
      String serializerMethodName) {
    List<Statement> bodyStatements = new ArrayList<>();

    Expr returnExpr = null;
    VariableExpr fieldsVarExpr = null;
    Expr serializerExpr = null;
    if (extractorReturnType.isProtoPrimitiveType()) {
      serializerExpr =
          MethodInvocationExpr.builder()
              .setMethodName("create")
              .setStaticReferenceType(
                  FIXED_REST_TYPESTORE.get(ProtoRestSerializer.class.getSimpleName()))
              .build();
      if (httpBindingFieldNames.isEmpty()) {
        returnExpr = ValueExpr.createNullExpr();
      }

    } else {
      fieldsVarExpr =
          VariableExpr.withVariable(
              Variable.builder().setName("fields").setType(extractorReturnType).build());
      Expr fieldsAssignExpr =
          AssignmentExpr.builder()
              .setVariableExpr(fieldsVarExpr.toBuilder().setIsDecl(true).build())
              .setValueExpr(
                  NewObjectExpr.builder()
                      .setType(FIXED_REST_TYPESTORE.get(HashMap.class.getSimpleName()))
                      .setIsGeneric(true)
                      .build())
              .build();

      bodyStatements.add(ExprStatement.withExpr(fieldsAssignExpr));
      returnExpr = fieldsVarExpr;

      TypeNode serializerVarType =
          TypeNode.withReference(
              ConcreteReference.builder()
                  .setClazz(ProtoRestSerializer.class)
                  .setGenerics(method.inputType().reference())
                  .build());

      VariableExpr serializerVarExpr =
          VariableExpr.withVariable(
              Variable.builder().setName("serializer").setType(serializerVarType).build());

      Expr serializerAssignExpr =
          AssignmentExpr.builder()
              .setVariableExpr(serializerVarExpr.toBuilder().setIsDecl(true).build())
              .setValueExpr(
                  MethodInvocationExpr.builder()
                      .setStaticReferenceType(
                          FIXED_REST_TYPESTORE.get(ProtoRestSerializer.class.getSimpleName()))
                      .setMethodName("create")
                      .setReturnType(serializerVarType)
                      .build())
              .build();

      serializerExpr = serializerVarExpr;

      bodyStatements.add(ExprStatement.withExpr(serializerAssignExpr));
    }

    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(method.inputType()).setName("request").build());

    for (HttpBinding httpBindingFieldName : httpBindingFieldNames) {
      // Handle foo.bar cases by descending into the subfields.
      MethodInvocationExpr.Builder requestFieldGetterExprBuilder =
          MethodInvocationExpr.builder().setExprReferenceExpr(requestVarExpr);
      MethodInvocationExpr.Builder requestFieldHasExprBuilder =
          MethodInvocationExpr.builder().setExprReferenceExpr(requestVarExpr);
      String[] descendantFields = httpBindingFieldName.name().split("\\.");
      for (int i = 0; i < descendantFields.length; i++) {
        String currFieldName = descendantFields[i];
        String bindingFieldMethodName =
            String.format("get%s", JavaStyle.toUpperCamelCase(currFieldName));
        requestFieldGetterExprBuilder =
            requestFieldGetterExprBuilder.setMethodName(bindingFieldMethodName);

        String bindingFieldHasMethodName =
            (i < descendantFields.length - 1)
                ? bindingFieldMethodName
                : String.format("has%s", JavaStyle.toUpperCamelCase(currFieldName));
        requestFieldHasExprBuilder =
            requestFieldHasExprBuilder
                .setMethodName(bindingFieldHasMethodName)
                .setReturnType(TypeNode.BOOLEAN);

        if (i < descendantFields.length - 1) {
          requestFieldGetterExprBuilder =
              MethodInvocationExpr.builder()
                  .setExprReferenceExpr(requestFieldGetterExprBuilder.build());
          requestFieldHasExprBuilder =
              MethodInvocationExpr.builder()
                  .setExprReferenceExpr(requestFieldHasExprBuilder.build());
        }
      }

      MethodInvocationExpr requestBuilderExpr = requestFieldGetterExprBuilder.build();
      MethodInvocationExpr requestHasExpr = requestFieldHasExprBuilder.build();

      ImmutableList.Builder<Expr> paramsPutArgs = ImmutableList.builder();
      if (fieldsVarExpr != null) {
        paramsPutArgs.add(fieldsVarExpr);
      }
      paramsPutArgs.add(
          ValueExpr.withValue(
              StringObjectValue.withValue(
                  JavaStyle.toLowerCamelCase(httpBindingFieldName.name()))));
      paramsPutArgs.add(requestBuilderExpr);

      Expr paramsPutExpr =
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(serializerExpr)
              .setMethodName(serializerMethodName)
              .setArguments(paramsPutArgs.build())
              .setReturnType(extractorReturnType)
              .build();

      if (fieldsVarExpr == null) {
        returnExpr = paramsPutExpr;
      } else {
        if (httpBindingFieldName.isOptional()) {
          bodyStatements.add(
              IfStatement.builder()
                  .setConditionExpr(requestHasExpr)
                  .setBody(Arrays.asList(ExprStatement.withExpr(paramsPutExpr)))
                  .build());
        } else {
          bodyStatements.add(ExprStatement.withExpr(paramsPutExpr));
        }
      }
    }

    // Overrides FieldsExtractor
    // (https://github.com/googleapis/gax-java/blob/12b18ee255d3fabe13bb3969df40753b29f830d5/gax-httpjson/src/main/java/com/google/api/gax/httpjson/FieldsExtractor.java).
    return LambdaExpr.builder()
        .setArguments(requestVarExpr.toBuilder().setIsDecl(true).build())
        .setBody(bodyStatements)
        .setReturnExpr(returnExpr)
        .build();
  }

  private List<Expr> getHttpMethodTypeExpr(Method protoMethod) {
    EnumRefExpr expr =
        EnumRefExpr.builder()
            .setName(protoMethod.httpBindings().httpVerb().toString())
            .setType(
                TypeNode.withReference(
                    ConcreteReference.builder().setClazz(HttpMethods.class).build()))
            .build();
    return Collections.singletonList(expr);
  }
}
