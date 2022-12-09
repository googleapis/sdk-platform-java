// Copyright 2022 Google LLC
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

package com.google.api.generator.spring.utils;

import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GlobalPropertiesUtils {
  public static final String GLOBAL_PROPERTIES_CLAZZ_NAME = "GlobalProperties";
  public static final String GLOBAL_PROPERTIES_PAKKAGE_NAME = "com.google.cloud.spring.global";

  public static TypeNode getGlobalPropertiesType() {
    TypeNode loggerType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(GLOBAL_PROPERTIES_CLAZZ_NAME)
                .setPakkage(GLOBAL_PROPERTIES_PAKKAGE_NAME)
                .build());
    return loggerType;
  }

  public static Statement getGlobalPropertiesDeclaration(Map<String, TypeNode> types) {
    Variable globalPropertiesVar =
        Variable.builder()
            .setName("globalProperties")
            .setType(types.get("GlobalProperties"))
            .build();
    return ExprStatement.withExpr(
        VariableExpr.builder()
            .setVariable(globalPropertiesVar)
            .setScope(ScopeNode.PRIVATE)
            .setIsStatic(false)
            .setIsFinal(true)
            .setIsDecl(true)
            .build());
  }

  public static Statement getPrivateVarDeclaration(String varName, TypeNode type, boolean isFinal) {
    Variable variable = Variable.builder().setName(varName).setType(type).build();
    return ExprStatement.withExpr(
        VariableExpr.builder()
            .setVariable(variable)
            .setScope(ScopeNode.PRIVATE)
            .setIsFinal(true)
            .setIsDecl(true)
            .build());
  }

  public static ExprStatement createMemberVarStatement(
      String varName,
      TypeNode varType,
      boolean isFinal,
      Expr defaultVal,
      List<AnnotationNode> annotationNodes) {
    Variable memberVar = Variable.builder().setName(varName).setType(varType).build();
    VariableExpr memberVarExpr =
        VariableExpr.builder()
            .setVariable(memberVar)
            .setScope(ScopeNode.PRIVATE)
            .setAnnotations(annotationNodes == null ? Collections.emptyList() : annotationNodes)
            .setIsDecl(true)
            .setIsFinal(isFinal)
            .build();

    if (defaultVal == null) {
      return ExprStatement.withExpr(memberVarExpr);
    }
    AssignmentExpr assignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(memberVarExpr.toBuilder().setIsDecl(true).build())
            .setValueExpr(defaultVal)
            .build();
    ExprStatement memberVarStatement = ExprStatement.withExpr(assignmentExpr);

    return memberVarStatement;
  }
}
