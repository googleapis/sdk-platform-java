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
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicClass.Kind;
import com.google.api.generator.gapic.model.Message;
import com.google.api.generator.gapic.model.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

public class ServiceClientClassComposer implements ClassComposer {
  private static ServiceClientClassComposer INSTANCE = new ServiceClientClassComposer();

  private final Map<String, TypeNode> types;

  private ServiceClientClassComposer() {
    types = createTypes();
  }

  public static ServiceClientClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(Service service, Map<String, Message> messageTypes) {
    String className = String.format("%sClient", service.name());
    GapicClass.Kind kind = Kind.MAIN;
    String pakkage = service.pakkage();

    // TODO(miraleung): Comments, methods, etc.

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(pakkage)
            .setAnnotations(createClassAnnotations())
            .setImplementsTypes(createClassImplements())
            .setName(className)
            .setMethods(createClassMethods())
            .setScope(ScopeNode.PUBLIC)
            .build();
    return GapicClass.create(kind, classDef);
  }

  private List<AnnotationNode> createClassAnnotations() {
    return Arrays.asList(
        AnnotationNode.builder()
            .setType(types.get("Generated"))
            .setDescription("by gapic-generator")
            .build(),
        AnnotationNode.builder().setType(types.get("BetaApi")).build());
  }

  private List<TypeNode> createClassImplements() {
    return Arrays.asList(types.get("BackgroundResource"));
  }

  private List<MethodDefinition> createClassMethods() {
    List<MethodDefinition> methods = new ArrayList<>();
    // TODO(miraleung): More methods.
    methods.addAll(createBackgroundResourceMethods());
    return methods;
  }

  private List<MethodDefinition> createBackgroundResourceMethods() {
    List<MethodDefinition> methods = new ArrayList<>();

    // TODO(miraleung): Fill out the body.
    MethodDefinition closeMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .setName("close")
            .build();
    methods.add(closeMethod);

    // TODO(miraleung): Fill out the body.
    MethodDefinition shutdownMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .setName("shutdown")
            .build();
    methods.add(shutdownMethod);

    // TODO(miraleung): Fill out the body.
    Expr placeholderReturnExpr =
        ValueExpr.withValue(
            PrimitiveValue.builder().setType(TypeNode.BOOLEAN).setValue("false").build());
    MethodDefinition isShutdownMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.BOOLEAN)
            .setName("isShutdown")
            .setReturnExpr(placeholderReturnExpr)
            .build();
    methods.add(isShutdownMethod);

    // TODO(miraleung): Fill out the body.
    MethodDefinition isTerminatedMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.BOOLEAN)
            .setName("isTerminated")
            .setReturnExpr(placeholderReturnExpr)
            .build();
    methods.add(isTerminatedMethod);

    // TODO(miraleung): Fill out the body.
    MethodDefinition shutdownNowMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .setName("shutdownNow")
            .build();
    methods.add(shutdownNowMethod);

    // TODO(miraleung): Fill out the body.
    List<VariableExpr> arguments =
        Arrays.asList(
            VariableExpr.builder()
                .setVariable(createVariable("duration", TypeNode.LONG))
                .setIsDecl(true)
                .build(),
            VariableExpr.builder()
                .setVariable(createVariable("unit", types.get("TimeUnit")))
                .setIsDecl(true)
                .build());

    MethodDefinition awaitTerminationMethod =
        MethodDefinition.builder()
            .setIsOverride(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.BOOLEAN)
            .setName("awaitTermination")
            .setReturnExpr(placeholderReturnExpr)
            .setArguments(arguments)
            .setThrowsExceptions(Arrays.asList(types.get("InterruptedException")))
            .build();
    methods.add(awaitTerminationMethod);

    return methods;
  }

  private static Map<String, TypeNode> createTypes() {
    Map<String, TypeNode> types = new HashMap<>();
    List<Class> concreteClazzes =
        Arrays.asList(
            Generated.class,
            BetaApi.class,
            BackgroundResource.class,
            TimeUnit.class,
            InterruptedException.class);
    for (Class clazz : concreteClazzes) {
      types.put(clazz.getSimpleName(), TypeNode.withReference(ConcreteReference.withClazz(clazz)));
    }
    return types;
  }

  private static Variable createVariable(String name, TypeNode type) {
    return Variable.builder().setName(name).setType(type).build();
  }
}
