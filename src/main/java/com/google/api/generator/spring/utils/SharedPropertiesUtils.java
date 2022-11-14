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

import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import java.util.Map;

public class SharedPropertiesUtils {
  public static final String SHARED_PROPERTIES_CLAZZ_NAME = "SharedProperties";
  public static final String SHARED_PROPERTIES_PAKKAGE_NAME = "com.google.cloud.spring.shared";

  public static TypeNode getSharedPropertiesType() {
    TypeNode loggerType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName(SHARED_PROPERTIES_CLAZZ_NAME)
                .setPakkage(SHARED_PROPERTIES_PAKKAGE_NAME)
                .build());
    return loggerType;
  }

  public static Statement getSharedPropertiesDeclaration(Map<String, TypeNode> types) {
    Variable sharedPropertiesVar = Variable.builder()
        .setName("sharedProperties")
        .setType(types.get("SharedProperties"))
        .build();
    return ExprStatement.withExpr(VariableExpr.builder()
        .setVariable(sharedPropertiesVar)
        .setScope(ScopeNode.PRIVATE)
        .setIsStatic(false)
        .setIsFinal(true)
        .setIsDecl(true)
        .build());
  }
}
