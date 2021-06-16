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
import com.google.api.gax.httpjson.HttpJsonStubCallableFactory;
import com.google.api.gax.httpjson.ProtoMessageRequestFormatter;
import com.google.api.gax.httpjson.ProtoMessageResponseParser;
import com.google.api.gax.httpjson.ProtoRestSerializer;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AnonymousClassExpr;
import com.google.api.generator.engine.ast.AssignmentExpr;
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
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.common.AbstractServiceStubClassComposer;
import com.google.api.generator.gapic.composer.store.TypeStore;
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
                    createFieldsExtractorAnonClass(
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
                    createFieldsExtractorAnonClass(
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
                    createFieldsExtractorAnonClass(
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

  private Expr createFieldsExtractorAnonClass(
      Method method,
      TypeNode extractorReturnType,
      Set<String> httpBindingFieldNames,
      String serializerMethodName) {
    List<Expr> bodyExprs = new ArrayList<>();

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

      bodyExprs.add(fieldsAssignExpr);
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

      bodyExprs.add(serializerAssignExpr);
    }

    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(method.inputType()).setName("request").build());

    for (String httpBindingFieldName : httpBindingFieldNames) {
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

      ImmutableList.Builder<Expr> paramsPutArgs = ImmutableList.builder();
      if (fieldsVarExpr != null) {
        paramsPutArgs.add(fieldsVarExpr);
      }
      paramsPutArgs.add(
          ValueExpr.withValue(
              StringObjectValue.withValue(JavaStyle.toLowerCamelCase(httpBindingFieldName))));
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
        bodyExprs.add(paramsPutExpr);
      }
    }

    MethodDefinition extractMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(extractorReturnType)
            .setName("extract")
            .setArguments(requestVarExpr.toBuilder().setIsDecl(true).build())
            .setBody(bodyExprs.stream().map(ExprStatement::withExpr).collect(Collectors.toList()))
            .setReturnExpr(returnExpr)
            .build();

    TypeNode anonClassType =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(FieldsExtractor.class)
                .setGenerics(method.inputType().reference(), extractorReturnType.reference())
                .build());

    return AnonymousClassExpr.builder().setType(anonClassType).setMethods(extractMethod).build();
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
