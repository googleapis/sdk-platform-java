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
import com.google.api.gax.httpjson.HttpJsonLongRunningClient;
import com.google.api.gax.httpjson.HttpJsonOperationSnapshot;
import com.google.api.gax.httpjson.HttpJsonStubCallableFactory;
import com.google.api.gax.httpjson.ProtoMessageRequestFormatter;
import com.google.api.gax.httpjson.ProtoMessageResponseParser;
import com.google.api.gax.httpjson.ProtoRestSerializer;
import com.google.api.gax.longrunning.OperationSnapshot;
import com.google.api.gax.rpc.LongRunningClient;
import com.google.api.gax.rpc.UnaryCallable;
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
import com.google.api.generator.engine.ast.ThisObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.common.AbstractServiceStubClassComposer;
import com.google.api.generator.gapic.composer.common.TransportContext;
import com.google.api.generator.gapic.composer.store.TypeStore;
import com.google.api.generator.gapic.model.HttpBindings.HttpBinding;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.model.OperationResponse;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.collect.BiMap;
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
      Service service,
      Method protoMethod,
      VariableExpr methodDescriptorVarExpr,
      Map<String, Message> messageTypes) {
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

    if (protoMethod.isOperationPollingMethod() || protoMethod.hasLro()) {
      expr =
          methodMaker
              .apply(
                  "setOperationSnapshotFactory",
                  setOperationSnapshotFactoryExpr(protoMethod, messageTypes))
              .apply(expr);
    }

    if (protoMethod.isOperationPollingMethod()) {
      expr =
          methodMaker
              .apply(
                  "setPollingRequestFactory",
                  setPollingRequestFactoryExpr(protoMethod, messageTypes))
              .apply(expr);
    }

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

  public HttpJsonServiceStubClassComposer(TransportContext transportContext) {
    super(transportContext);
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

  // Generates get[camelCase(fieldName)]
  private String getMethodFormat(String fieldName) {
    return "get" + JavaStyle.toUpperCamelCase(fieldName);
  }

  // Generates set[camelCase(fieldName)]
  private String setMethodFormat(String fieldName) {
    return "set" + JavaStyle.toUpperCamelCase(fieldName);
  }

  // Generates: [nameVar].append(":").append([requestVar].get[FieldName]());
  private ExprStatement appendField(
      VariableExpr nameVar, VariableExpr requestVar, String fieldName) {
    BiFunction<String, List<Expr>, Function<MethodInvocationExpr, MethodInvocationExpr>>
        methodMaker = getMethodMaker();
    ValueExpr colonValueExpr =
        ValueExpr.builder().setValue(StringObjectValue.builder().setValue(":").build()).build();
    MethodInvocationExpr opNameAppendColonExpr =
        MethodInvocationExpr.builder()
            .setMethodName("append")
            .setArguments(colonValueExpr)
            .setExprReferenceExpr(nameVar)
            .build();
    MethodInvocationExpr getField =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(requestVar)
            .setMethodName(getMethodFormat(fieldName))
            .build();
    opNameAppendColonExpr =
        methodMaker
            .apply("append", Collections.singletonList(getField))
            .apply(opNameAppendColonExpr);
    return ExprStatement.withExpr(opNameAppendColonExpr);
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

  private List<Expr> setOperationSnapshotFactoryExpr(
      Method protoMethod, Map<String, Message> messageTypes) {

    Message inputOperationMessage =
        messageTypes.get(protoMethod.inputType().reference().fullName());
    Message outputOperationMessage =
        messageTypes.get(protoMethod.outputType().reference().fullName());
    OperationResponse operationResponse = outputOperationMessage.operationResponse();

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
    TypeNode stringBuilderType =
        TypeNode.withReference(ConcreteReference.withClazz(StringBuilder.class));
    VariableExpr opNameVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(stringBuilderType).setName("opName").build());
    MethodInvocationExpr getId =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(responseVarExpr)
            .setMethodName(getMethodFormat(operationResponse.nameFieldName()))
            .build();
    Expr opNameObjectExpr =
        NewObjectExpr.builder().setType(stringBuilderType).setArguments(getId).build();
    AssignmentExpr opNameAssignExpr =
        AssignmentExpr.builder()
            .setVariableExpr(opNameVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(opNameObjectExpr)
            .build();
    createBody.add(ExprStatement.withExpr(opNameAssignExpr));

    // Generate compound operation name
    if (!protoMethod.isOperationPollingMethod()) {
      // TODO: Change to ordered map
      Map<String, String> requestFields = inputOperationMessage.operationRequestFields();
      List<String> fieldAnnotationNames = new ArrayList<String>(requestFields.keySet());
      Collections.sort(fieldAnnotationNames);
      for (String fieldName : fieldAnnotationNames) {
        createBody.add(appendField(opNameVarExpr, requestVarExpr, requestFields.get(fieldName)));
      }
    }

    // Generate check for status == done
    MethodInvocationExpr getStatusExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(responseVarExpr)
            .setMethodName(getMethodFormat(operationResponse.statusFieldName()))
            .build();

    String statusTypeName = operationResponse.statusFieldTypeName();
    String statusClassName = statusTypeName.substring(statusTypeName.lastIndexOf('.') + 1);

    TypeNode statusType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(statusClassName)
                .setPakkage(protoMethod.outputType().reference().fullName())
                .setIsStaticImport(false)
                .build());
    VariableExpr statusDoneExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setName("DONE").setType(TypeNode.INT).build())
            .setStaticReferenceType(statusType)
            .build();
    MethodInvocationExpr statusEqualsExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(statusDoneExpr)
            .setMethodName("equals")
            .setArguments(getStatusExpr)
            .build();

    // Generate return statement
    TypeNode httpJsonOperationSnapshotType =
        TypeNode.withReference(ConcreteReference.withClazz(HttpJsonOperationSnapshot.class));

    // Generate getter methods from annotations
    MethodInvocationExpr opNameToStringExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(opNameVarExpr)
            .setMethodName("toString")
            .build();
    MethodInvocationExpr getHttpErrorStatusCodeExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(responseVarExpr)
            .setMethodName(getMethodFormat(operationResponse.errorCodeFieldName()))
            .build();
    MethodInvocationExpr getHttpErrorMessageExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(responseVarExpr)
            .setMethodName(getMethodFormat(operationResponse.errorMessageFieldName()))
            .build();
    MethodInvocationExpr newBuilderExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(httpJsonOperationSnapshotType)
            .setMethodName("newBuilder")
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

  private List<Expr> setPollingRequestFactoryExpr(
      Method protoMethod, Map<String, Message> messageTypes) {
    BiFunction<String, List<Expr>, Function<MethodInvocationExpr, MethodInvocationExpr>>
        methodMaker = getMethodMaker();

    Message inputOperationMessage =
        messageTypes.get(protoMethod.inputType().reference().fullName());

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
    TypeNode getOperationRequestType = TypeNode.withReference(protoMethod.inputType().reference());
    MethodInvocationExpr newBuilderExpr =
        MethodInvocationExpr.builder()
            .setStaticReferenceType(getOperationRequestType)
            .setMethodName("newBuilder")
            .build();
    BiMap<String, String> responseFieldsMap = inputOperationMessage.operationResponseFields();
    List<String> responseFieldAnnotationNames = new ArrayList<String>(responseFieldsMap.keySet());
    Collections.sort(responseFieldAnnotationNames);
    Set<String> responseFieldsNames = responseFieldsMap.inverse().keySet();
    Set<String> allFieldsNames = inputOperationMessage.fieldMap().keySet();
    ArrayList<String> nonResponseFieldsNames = new ArrayList<String>();
    for (String fieldName : allFieldsNames) {
      if (!responseFieldsNames.contains(fieldName)) {
        nonResponseFieldsNames.add(fieldName);
      }
    }
    Collections.sort(nonResponseFieldsNames);
    int index = 0;
    for (String fieldAnnotationName : responseFieldAnnotationNames) {
      newBuilderExpr =
          methodMaker
              .apply(
                  setMethodFormat(responseFieldsMap.get(fieldAnnotationName)),
                  Collections.singletonList(getExpr(idComponentsVarExpr, Integer.toString(index))))
              .apply(newBuilderExpr);
      index++;
    }
    for (String fieldName : nonResponseFieldsNames) {
      newBuilderExpr =
          methodMaker
              .apply(
                  setMethodFormat(fieldName),
                  Collections.singletonList(getExpr(idComponentsVarExpr, Integer.toString(index))))
              .apply(newBuilderExpr);
      index++;
    }

    MethodInvocationExpr buildExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(newBuilderExpr)
            .setMethodName("build")
            .setReturnType(getOperationRequestType)
            .build();

    // Return lambda anonymous class
    return Collections.singletonList(
        LambdaExpr.builder()
            .setArguments(compoundOperationIdVarExpr.toBuilder().setIsDecl(true).build())
            .setBody(createBody)
            .setReturnExpr(buildExpr)
            .build());
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

  @Override
  protected List<Statement> createLongRunningClient(Service service, TypeStore typeStore) {
    Method pollingMethod = service.operationPollingMethod();
    if (pollingMethod != null) {
      Expr thisExpr =
          ValueExpr.withValue(
              ThisObjectValue.withType(
                  typeStore.get(
                      getTransportContext()
                          .classNames()
                          .getTransportServiceStubClassName(service))));

      VariableExpr callable =
          VariableExpr.withVariable(
              Variable.builder()
                  .setName(pollingMethod.name().toLowerCase() + "Callable")
                  .setType(TypeNode.withReference(ConcreteReference.withClazz(UnaryCallable.class)))
                  .build());
      VariableExpr methodDescriptor =
          VariableExpr.withVariable(
              Variable.builder()
                  .setName(pollingMethod.name().toLowerCase() + "MethodDescriptor")
                  .setType(
                      TypeNode.withReference(
                          ConcreteReference.withClazz(ApiMethodDescriptor.class)))
                  .build());

      TypeNode httpJsonLongRunningClientType =
          TypeNode.withReference(
              ConcreteReference.builder()
                  .setClazz(HttpJsonLongRunningClient.class)
                  .setGenerics(
                      Arrays.asList(
                          pollingMethod.inputType().reference(),
                          pollingMethod.outputType().reference()))
                  .build());

      NewObjectExpr HttpJsonLongRunningClient =
          NewObjectExpr.builder()
              .setType(httpJsonLongRunningClientType)
              .setArguments(
                  Arrays.asList(
                      callable,
                      MethodInvocationExpr.builder()
                          .setExprReferenceExpr(methodDescriptor)
                          .setMethodName("getOperationSnapshotFactory")
                          .build(),
                      MethodInvocationExpr.builder()
                          .setExprReferenceExpr(methodDescriptor)
                          .setMethodName("getPollingRequestFactory")
                          .build()))
              .build();

      AssignmentExpr assignLongRunningClient =
          AssignmentExpr.builder()
              .setVariableExpr(
                  VariableExpr.builder()
                      .setExprReferenceExpr(thisExpr)
                      .setVariable(
                          Variable.builder()
                              .setName("longRunningClient")
                              .setType(
                                  TypeNode.withReference(
                                      ConcreteReference.withClazz(LongRunningClient.class)))
                              .build())
                      .build())
              .setValueExpr(HttpJsonLongRunningClient)
              .build();

      return Arrays.asList(ExprStatement.withExpr(assignLongRunningClient));
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  protected VariableExpr declareLongRunningClient() {
    return VariableExpr.withVariable(
        Variable.builder()
            .setName("longRunningClient")
            .setType(TypeNode.withReference(ConcreteReference.withClazz(LongRunningClient.class)))
            .build());
  }

  @Override
  protected List<MethodDefinition> createLongRunningClientGetter() {
    VariableExpr longRunningClient =
        VariableExpr.withVariable(
            Variable.builder()
                .setName("longRunningClient")
                .setType(
                    TypeNode.withReference(ConcreteReference.withClazz(LongRunningClient.class)))
                .build());

    return ImmutableList.of(
        MethodDefinition.builder()
            .setName("longRunningClient")
            .setScope(ScopeNode.PUBLIC)
            .setIsOverride(true)
            .setReturnType(
                TypeNode.withReference(ConcreteReference.withClazz(LongRunningClient.class)))
            .setReturnExpr(longRunningClient)
            .build());
  }
}
