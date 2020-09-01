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

import com.google.api.gax.batching.PartitionKey;
import com.google.api.gax.batching.RequestBuilder;
import com.google.api.gax.rpc.BatchedRequestIssuer;
import com.google.api.gax.rpc.BatchingDescriptor;
import com.google.api.generator.engine.ast.AnonymousClassExpr;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NewObjectExpr;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.GapicBatchingSettings;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BatchingDescriptorComposer {
  private static final String BATCHING_DESC_PATTERN = "%s_BATCHING_DESC";

  private static final Reference BATCHING_DESCRIPTOR_REF =
      ConcreteReference.withClazz(BatchingDescriptor.class);
  private static final Reference REQUEST_BUILDER_REF =
      ConcreteReference.withClazz(RequestBuilder.class);
  private static final Reference BATCHED_REQUEST_ISSUER_REF =
      ConcreteReference.withClazz(BatchedRequestIssuer.class);

  private static final TypeNode PARTITION_KEY_TYPE = toType(PartitionKey.class);

  private static final String ADD_ALL_METHOD_PATTERN = "addAll%s";
  private static final String GET_LIST_METHOD_PATTERN = "get%sList";

  public static Expr createBatchingDescriptorFieldDeclExpr(
      Method method, GapicBatchingSettings batchingSettings, Map<String, Message> messageTypes) {
    List<MethodDefinition> javaMethods = new ArrayList<>();
    javaMethods.add(createGetBatchPartitionKeyMethod(method, batchingSettings, messageTypes));
    javaMethods.add(createGetRequestBuilderMethod(method, batchingSettings));

    TypeNode batchingDescriptorType =
        toType(BATCHING_DESCRIPTOR_REF, method.inputType(), method.outputType());
    AnonymousClassExpr batchingDescriptorClassExpr =
        AnonymousClassExpr.builder()
            .setType(batchingDescriptorType)
            .setMethods(javaMethods)
            .build();

    String varName =
        String.format(BATCHING_DESC_PATTERN, JavaStyle.toUpperSnakeCase(method.name()));
    return AssignmentExpr.builder()
        .setVariableExpr(
            VariableExpr.builder()
                .setVariable(
                    Variable.builder().setType(batchingDescriptorType).setName(varName).build())
                .setIsDecl(true)
                .setScope(ScopeNode.PRIVATE)
                .setIsStatic(true)
                .setIsFinal(true)
                .build())
        .setValueExpr(batchingDescriptorClassExpr)
        .build();
  }

  private static MethodDefinition createGetBatchPartitionKeyMethod(
      Method method, GapicBatchingSettings batchingSettings, Map<String, Message> messageTypes) {
    String methodInputTypeName = method.inputType().reference().name();
    Message inputMessage = messageTypes.get(methodInputTypeName);
    Preconditions.checkNotNull(
        inputMessage,
        String.format(
            "Message %s not found for RPC method %s", methodInputTypeName, method.name()));

    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(method.inputType()).setName("request").build());

    List<Expr> partitionKeyArgExprs = new ArrayList<>();
    for (String discriminatorFieldName : batchingSettings.discriminatorFieldNames()) {
      Preconditions.checkNotNull(
          inputMessage.fieldMap().get(discriminatorFieldName),
          String.format(
              "Batching discriminator field %s not found in message %s",
              discriminatorFieldName, inputMessage.name()));
      String getterMethodName =
          String.format("get%s", JavaStyle.toUpperCamelCase(discriminatorFieldName));
      partitionKeyArgExprs.add(
          MethodInvocationExpr.builder()
              .setExprReferenceExpr(requestVarExpr)
              .setMethodName(getterMethodName)
              .build());
    }
    Expr returnExpr =
        NewObjectExpr.builder()
            .setType(PARTITION_KEY_TYPE)
            .setArguments(partitionKeyArgExprs)
            .build();

    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(PARTITION_KEY_TYPE)
        .setName("getBatchPartitionKey")
        .setArguments(requestVarExpr.toBuilder().setIsDecl(true).build())
        .setReturnExpr(returnExpr)
        .build();
  }

  private static MethodDefinition createGetRequestBuilderMethod(
      Method method, GapicBatchingSettings batchingSettings) {
    TypeNode builderType = toType(REQUEST_BUILDER_REF, method.inputType());
    VariableExpr builderVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(builderType).setName("builder").build());
    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(method.inputType()).setName("request").build());

    Expr toBuilderExpr =
        AssignmentExpr.builder()
            .setVariableExpr(builderVarExpr)
            .setValueExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(requestVarExpr)
                    .setMethodName("toBuilder")
                    .setReturnType(builderType)
                    .build())
            .build();

    String upperBatchedFieldName = JavaStyle.toUpperCamelCase(batchingSettings.batchedFieldName());
    String getFooListMethodName = String.format(GET_LIST_METHOD_PATTERN, upperBatchedFieldName);
    Expr getFooListExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(requestVarExpr)
            .setMethodName(getFooListMethodName)
            .build();

    String addAllMethodName = String.format(ADD_ALL_METHOD_PATTERN, upperBatchedFieldName);
    Expr addAllExpr =
        MethodInvocationExpr.builder()
            .setExprReferenceExpr(builderVarExpr)
            .setMethodName(addAllMethodName)
            .setArguments(getFooListExpr)
            .build();

    MethodDefinition appendRequestMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .setName("appendRequest")
            .setArguments(requestVarExpr.toBuilder().setIsDecl(true).build())
            .setBody(
                Arrays.asList(
                    IfStatement.builder()
                        .setConditionExpr(
                            MethodInvocationExpr.builder()
                                .setStaticReferenceType(toType(Objects.class))
                                .setMethodName("isNull")
                                .setArguments(builderVarExpr)
                                .setReturnType(TypeNode.BOOLEAN)
                                .build())
                        .setBody(Arrays.asList(ExprStatement.withExpr(toBuilderExpr)))
                        .setElseBody(Arrays.asList(ExprStatement.withExpr(addAllExpr)))
                        .build()))
            .build();

    MethodDefinition buildMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(method.inputType())
            .setName("build")
            .setReturnExpr(
                MethodInvocationExpr.builder()
                    .setExprReferenceExpr(builderVarExpr)
                    .setMethodName("build")
                    .setReturnType(method.inputType())
                    .build())
            .build();

    AnonymousClassExpr requestBuilderAnonClassExpr =
        AnonymousClassExpr.builder()
            .setType(builderType)
            .setStatements(
                Arrays.asList(
                    ExprStatement.withExpr(
                        builderVarExpr
                            .toBuilder()
                            .setIsDecl(true)
                            .setScope(ScopeNode.PRIVATE)
                            .build())))
            .setMethods(Arrays.asList(appendRequestMethod, buildMethod))
            .build();

    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(builderType)
        .setName("getRequestBuilder")
        .setReturnExpr(requestBuilderAnonClassExpr)
        .build();
  }

  private static TypeNode toType(Class clazz) {
    return TypeNode.withReference(ConcreteReference.withClazz(clazz));
  }

  private static TypeNode toType(Reference reference, TypeNode... types) {
    return TypeNode.withReference(
        reference.copyAndSetGenerics(
            Arrays.asList(types).stream().map(t -> t.reference()).collect(Collectors.toList())));
  }
}
