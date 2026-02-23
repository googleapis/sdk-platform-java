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
import com.google.api.gax.core.GaxProperties;
import com.google.api.gax.rpc.AbstractGapicProperties;
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.gapic.composer.store.TypeStore;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;

public class GapicPropertiesClassComposer implements ClassComposer {
  private static final GapicPropertiesClassComposer INSTANCE = new GapicPropertiesClassComposer();

  private static final TypeStore FIXED_TYPESTORE = createStaticTypes();

  private GapicPropertiesClassComposer() {}

  public static GapicPropertiesClassComposer instance() {
    return INSTANCE;
  }

  @Override
  public GapicClass generate(GapicContext context, Service service) {
    String pakkage = service.pakkage();
    String className = "GapicProperties";

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(pakkage)
            .setAnnotations(createClassAnnotations())
            .setScope(ScopeNode.PUBLIC)
            .setName(className)
            .setExtendsType(FIXED_TYPESTORE.get("AbstractGapicProperties"))
            .setMethods(createClassMethods(context))
            .build();

    return GapicClass.create(GapicClass.Kind.MAIN, classDef);
  }

  private static List<AnnotationNode> createClassAnnotations() {
    return Arrays.asList(
        AnnotationNode.withType(FIXED_TYPESTORE.get("InternalApi")),
        AnnotationNode.builder()
            .setType(FIXED_TYPESTORE.get("Generated"))
            .setDescription("by gapic-generator-java")
            .build());
  }

  private List<MethodDefinition> createClassMethods(GapicContext context) {
    List<MethodDefinition> methods = new ArrayList<>();
    methods.add(createGetRepositoryMethod(context));
    return methods;
  }

  private MethodDefinition createGetRepositoryMethod(GapicContext context) {
    return MethodDefinition.builder()
        .setIsOverride(true)
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.STRING)
        .setName("getRepository")
        .setReturnExpr(
            ValueExpr.withValue(StringObjectValue.withValue(context.repo().orElse(null))))
        .build();
  }

  private static TypeStore createStaticTypes() {
    return new TypeStore(
        Arrays.asList(
            InternalApi.class,
            Generated.class,
            AbstractGapicProperties.class,
            GaxProperties.class));
  }
}
