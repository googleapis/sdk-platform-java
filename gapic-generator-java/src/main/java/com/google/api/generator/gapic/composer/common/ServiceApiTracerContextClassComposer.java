/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.api.generator.gapic.composer.common;

import com.google.api.core.InternalApi;
import com.google.api.gax.tracing.ApiTracerContext;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.ThisObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.composer.store.TypeStore;
import com.google.api.generator.gapic.composer.utils.ClassNames;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import javax.annotation.Nullable;

public class ServiceApiTracerContextClassComposer implements ClassComposer {
  private static final ServiceApiTracerContextClassComposer INSTANCE =
      new ServiceApiTracerContextClassComposer();

  private static final TypeStore FIXED_TYPESTORE = createStaticTypes();

  private ServiceApiTracerContextClassComposer() {}

  public static ServiceApiTracerContextClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(GapicContext context, Service service) {
    String pakkage = String.format("%s.stub", service.pakkage());
    String className = ClassNames.getServiceApiTracerContextClassName(service);
    TypeStore typeStore = createDynamicTypes(service, pakkage);

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(pakkage)
            .setAnnotations(createClassAnnotations())
            .setScope(ScopeNode.PUBLIC)
            .setName(className)
            .setExtendsType(FIXED_TYPESTORE.get("ApiTracerContext"))
            .setStatements(createClassStatements())
            .setMethods(createClassMethods(service, context, typeStore))
            .build();

    return GapicClass.create(GapicClass.Kind.STUB, classDef);
  }

  private static List<AnnotationNode> createClassAnnotations() {
    return Arrays.asList(
        AnnotationNode.withType(FIXED_TYPESTORE.get("InternalApi")),
        AnnotationNode.builder()
            .setType(FIXED_TYPESTORE.get("Generated"))
            .setDescription("by gapic-generator-java")
            .build());
  }

  private static List<Statement> createClassStatements() {
    VariableExpr serverAddressVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.STRING).setName("serverAddress").build());
    return Arrays.asList(
        ExprStatement.withExpr(
            serverAddressVarExpr.toBuilder()
                .setIsDecl(true)
                .setScope(ScopeNode.PRIVATE)
                .setIsFinal(true)
                .build()));
  }

  private static List<MethodDefinition> createClassMethods(
      Service service, GapicContext context, TypeStore typeStore) {
    List<MethodDefinition> methods = new ArrayList<>();
    methods.add(createConstructor(service, typeStore));
    methods.add(createCreateMethod(service, typeStore));
    methods.add(createGetServerAddressMethod());
    methods.add(createGetRepoMethod(context));
    return methods;
  }

  private static MethodDefinition createConstructor(Service service, TypeStore typeStore) {
    TypeNode thisType = typeStore.get(ClassNames.getServiceApiTracerContextClassName(service));
    VariableExpr serverAddressVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.STRING).setName("serverAddress").build());

    VariableExpr thisServerAddressVarExpr =
        VariableExpr.builder()
            .setVariable(serverAddressVarExpr.variable())
            .setExprReferenceExpr(ValueExpr.withValue(ThisObjectValue.withType(thisType)))
            .build();

    return MethodDefinition.constructorBuilder()
        .setScope(ScopeNode.PRIVATE)
        .setReturnType(thisType)
        .setArguments(serverAddressVarExpr.toBuilder().setIsDecl(true).build())
        .setBody(
            Arrays.asList(
                ExprStatement.withExpr(
                    AssignmentExpr.builder()
                        .setVariableExpr(thisServerAddressVarExpr)
                        .setValueExpr(serverAddressVarExpr)
                        .build())))
        .build();
  }

  private static MethodDefinition createCreateMethod(Service service, TypeStore typeStore) {
    TypeNode thisType = typeStore.get(ClassNames.getServiceApiTracerContextClassName(service));
    VariableExpr serverAddressVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.STRING).setName("serverAddress").build());

    return MethodDefinition.builder()
        .setScope(ScopeNode.PUBLIC)
        .setIsStatic(true)
        .setReturnType(thisType)
        .setName("create")
        .setArguments(serverAddressVarExpr.toBuilder().setIsDecl(true).build())
        .setReturnExpr(
            com.google.api.generator.engine.ast.NewObjectExpr.builder()
                .setType(thisType)
                .setArguments(serverAddressVarExpr)
                .build())
        .build();
  }

  private static MethodDefinition createGetServerAddressMethod() {
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.STRING)
        .setName("getServerAddress")
        .setReturnExpr(
            VariableExpr.withVariable(
                Variable.builder().setType(TypeNode.STRING).setName("serverAddress").build()))
        .build();
  }

  private static MethodDefinition createGetRepoMethod(GapicContext context) {
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.STRING)
        .setName("getRepo")
        .setReturnExpr(
            context.repo().isPresent()
                ? ValueExpr.withValue(StringObjectValue.withValue(context.repo().get()))
                : ValueExpr.createNullExpr())
        .build();
  }

  private static TypeStore createStaticTypes() {
    return new TypeStore(
        Arrays.asList(InternalApi.class, Generated.class, ApiTracerContext.class, Nullable.class));
  }

  private TypeStore createDynamicTypes(Service service, String pakkage) {
    TypeStore typeStore = new TypeStore();
    typeStore.put(pakkage, ClassNames.getServiceApiTracerContextClassName(service));
    return typeStore;
  }
}
