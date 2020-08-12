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
import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.api.pathtemplate.PathTemplate;
import com.google.api.pathtemplate.ValidationException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class ResourceNameHelperClassComposer {
  private static final String CLASS_NAME_PATTERN = "%sName";

  private static final ResourceNameHelperClassComposer INSTANCE =
      new ResourceNameHelperClassComposer();

  private static final Map<String, TypeNode> STATIC_TYPES = createStaticTypes();

  private ResourceNameHelperClassComposer() {}

  public static ResourceNameHelperClassComposer instance() {
    return INSTANCE;
  }

  public GapicClass generate(ResourceName resourceName) {
    String className =
        String.format(
            CLASS_NAME_PATTERN, JavaStyle.toUpperCamelCase(resourceName.resourceTypeName()));
    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString(resourceName.pakkage())
            .setAnnotations(createClassAnnotations())
            .setScope(ScopeNode.PUBLIC)
            .setName(className)
            .setImplementsTypes(createImplementsTypes())
            .build();
    return GapicClass.create(GapicClass.Kind.PROTO, classDef);
  }

  private static List<AnnotationNode> createClassAnnotations() {
    return Arrays.asList(
        AnnotationNode.builder()
            .setType(STATIC_TYPES.get("Generated"))
            .setDescription("by gapic-generator-java")
            .build());
  }

  private static List<TypeNode> createImplementsTypes() {
    return Arrays.asList(STATIC_TYPES.get("ResourceName"));
  }

  private static Map<String, TypeNode> createStaticTypes() {
    List<Class> concreteClazzes =
        Arrays.asList(
            ArrayList.class,
            BetaApi.class,
            Generated.class,
            ImmutableMap.class,
            List.class,
            Map.class,
            Objects.class,
            PathTemplate.class,
            Preconditions.class,
            com.google.api.resourcenames.ResourceName.class,
            ValidationException.class);
    return concreteClazzes.stream()
        .collect(
            Collectors.toMap(
                c -> c.getSimpleName(),
                c -> TypeNode.withReference(ConcreteReference.withClazz(c))));
  }
}
