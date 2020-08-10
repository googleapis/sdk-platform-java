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
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.ResourceName;
import com.google.api.generator.gapic.utils.JavaStyle;
import com.google.api.pathtemplate.PathTemplate;
import com.google.api.pathtemplate.ValidationException;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Generated;

public class ResourceNameHelperClassComposer {
  private static final String CLASS_NAME_PATTERN = "%sName";
  private static final String SLASH = "/";
  private static final String LEFT_BRACE = "{";
  private static final String RIGHT_BRACE = "}";

  private static final ResourceNameHelperClassComposer INSTANCE =
      new ResourceNameHelperClassComposer();

  private static final Map<String, TypeNode> STATIC_TYPES = createStaticTypes();
  private static final Map<String, VariableExpr> FIXED_CLASS_VARS =
      createFixedClassMemberVariables();

  private ResourceNameHelperClassComposer() {}

  public static ResourceNameHelperClassComposer instance() {
    return INSTANCE;
  }

  public GapicClass generate(ResourceName resourceName) {
    List<List<String>> tokenHierarchies = parseTokenHierarchy(resourceName.patterns());
    List<VariableExpr> templateFinalVarExprs = createTemplateClassMembers(tokenHierarchies);
    List<VariableExpr> patternTokenVarExprs = createPatternTokenClassMembers(tokenHierarchies);

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
            .setStatements(
                createClassStatements(
                    templateFinalVarExprs,
                    patternTokenVarExprs,
                    resourceName.patterns(),
                    tokenHierarchies))
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

  private static List<VariableExpr> createTemplateClassMembers(
      List<List<String>> tokenHierarchies) {
    return tokenHierarchies.stream()
        .map(
            ts ->
                VariableExpr.withVariable(
                    Variable.builder()
                        .setName(concatToUpperSnakeCaseName(ts))
                        .setType(STATIC_TYPES.get("PathTemplate"))
                        .build()))
        .collect(Collectors.toList());
  }

  private static List<VariableExpr> createPatternTokenClassMembers(
      List<List<String>> tokenHierarchies) {
    Set<String> tokenSet = getTokenSet(tokenHierarchies);
    return tokenSet.stream()
        .map(
            t ->
                VariableExpr.withVariable(
                    Variable.builder().setName(t).setType(TypeNode.STRING).build()))
        .collect(Collectors.toList());
  }

  private static List<Statement> createClassStatements(
      List<VariableExpr> templateFinalVarExprs,
      List<VariableExpr> patternTokenVarExprs,
      List<String> patterns,
      List<List<String>> tokenHierarchies) {
    List<Expr> memberVars = new ArrayList<>();
    Preconditions.checkState(
        templateFinalVarExprs.size() == patterns.size()
            && tokenHierarchies.size() == patterns.size(),
        "Cardinalities of patterns and associated variables do not match");
    // Pattern string variables.
    // Example:
    // private static final PathTemplate PROJECT_LOCATION_AUTOSCALING_POLICY_PATH_TEMPLATE =
    //     PathTemplate.createWithoutUrlEncoding(
    //         "projects/{project}/locations/{location}/autoscalingPolicies/{autoscaling_policy}");
    for (int i = 0; i < patterns.size(); i++) {
      VariableExpr varExpr =
          templateFinalVarExprs.get(i).toBuilder()
              .setIsDecl(true)
              .setScope(ScopeNode.PRIVATE)
              .setIsStatic(true)
              .setIsFinal(true)
              .build();
      Expr createWithoutUrlEncodingExpr =
          MethodInvocationExpr.builder()
              .setStaticReferenceType(STATIC_TYPES.get("PathTemplate"))
              .setMethodName("createWithoutUrlEncoding")
              .setArguments(
                  Arrays.asList(ValueExpr.withValue(StringObjectValue.withValue(patterns.get(i)))))
              .setReturnType(STATIC_TYPES.get("PathTemplate"))
              .build();
      memberVars.add(
          AssignmentExpr.builder()
              .setVariableExpr(varExpr)
              .setValueExpr(createWithoutUrlEncodingExpr)
              .build());
    }

    memberVars.add(
        FIXED_CLASS_VARS.get("fieldValuesMap").toBuilder()
            .setIsDecl(true)
            .setScope(ScopeNode.PRIVATE)
            .setIsVolatile(true)
            .build());
    Function<VariableExpr, VariableExpr> toDeclFn =
        v -> v.toBuilder().setIsDecl(true).setScope(ScopeNode.PRIVATE).build();
    memberVars.add(toDeclFn.apply(FIXED_CLASS_VARS.get("pathTemplate")));
    memberVars.add(toDeclFn.apply(FIXED_CLASS_VARS.get("fixedValue")));

    // Private per-token string variables.
    memberVars.addAll(
        patternTokenVarExprs.stream().map(v -> toDeclFn.apply(v)).collect(Collectors.toList()));
    return memberVars.stream().map(e -> ExprStatement.withExpr(e)).collect(Collectors.toList());
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

  private static Map<String, VariableExpr> createFixedClassMemberVariables() {
    Map<String, TypeNode> memberVars = new HashMap<>();
    Reference stringRef = ConcreteReference.withClazz(String.class);
    memberVars.put(
        "fieldValuesMap",
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(Map.class)
                .setGenerics(Arrays.asList(stringRef, stringRef))
                .build()));
    memberVars.put(
        "pathTemplate", TypeNode.withReference(ConcreteReference.withClazz(PathTemplate.class)));
    memberVars.put("fixedValue", TypeNode.STRING);
    return memberVars.entrySet().stream()
        .map(e -> Variable.builder().setName(e.getKey()).setType(e.getValue()).build())
        .collect(Collectors.toMap(v -> v.identifier().name(), v -> VariableExpr.withVariable(v)));
  }

  @VisibleForTesting
  static List<List<String>> parseTokenHierarchy(List<String> patterns) {
    List<List<String>> tokenHierachies = new ArrayList<>();
    // Assumes that resource definitions do not have non-slash separators.
    for (String pattern : patterns) {
      List<String> hierarchy = new ArrayList<>();
      Set<String> vars = PathTemplate.create(pattern).vars();
      String[] patternTokens = pattern.split(SLASH);
      for (String patternToken : patternTokens) {
        if (patternToken.startsWith(LEFT_BRACE) && patternToken.endsWith(RIGHT_BRACE)) {
          hierarchy.add(
              vars.stream()
                  .filter(v -> patternToken.contains(v))
                  .collect(Collectors.toList())
                  .get(0));
        }
      }
      tokenHierachies.add(hierarchy);
    }
    return tokenHierachies;
  }

  @VisibleForTesting
  static Set<String> getTokenSet(List<List<String>> tokenHierarchy) {
    return tokenHierarchy.stream().flatMap(tokens -> tokens.stream()).collect(Collectors.toSet());
  }

  @VisibleForTesting
  static String concatToUpperSnakeCaseName(List<String> tokens) {
    // Tokens are currently in lower_snake_case space.
    return JavaStyle.toUpperSnakeCase(tokens.stream().collect(Collectors.joining("_")));
  }

  @VisibleForTesting
  static String concatToUpperCamelCaseName(List<String> tokens) {
    // Tokens are currently in lower_snake_case space.
    return JavaStyle.toUpperCamelCase(tokens.stream().collect(Collectors.joining("_")));
  }
}
