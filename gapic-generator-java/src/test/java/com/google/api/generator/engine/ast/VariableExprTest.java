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

package com.google.api.generator.engine.ast;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class VariableExprTest {
  @Test
  public void createVariableExpr_basic() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    assertThat(variableExpr.variable()).isEqualTo(variable);
    assertThat(variableExpr.type()).isEqualTo(TypeNode.INT);
    assertThat(variableExpr.isDecl()).isFalse();
    assertThat(variableExpr.isFinal()).isFalse();
    assertThat(variableExpr.isStatic()).isFalse();
    assertThat(variableExpr.scope()).isEqualTo(ScopeNode.LOCAL);
  }

  @Test
  public void createVariableExpr_withFields() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING).build();
    VariableExpr variableExpr =
        VariableExpr.builder()
            .setVariable(variable)
            .setIsFinal(true)
            .setIsStatic(true)
            .setScope(ScopeNode.PRIVATE)
            .build();
    assertThat(variableExpr.variable()).isEqualTo(variable);
    assertThat(variableExpr.type()).isEqualTo(TypeNode.STRING);
    assertThat(variableExpr.isDecl()).isFalse();
    assertThat(variableExpr.isFinal()).isTrue();
    assertThat(variableExpr.isStatic()).isTrue();
    assertThat(variableExpr.scope()).isEqualTo(ScopeNode.PRIVATE);
  }

  @Test
  public void createVariableExpr_declaration() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.BOOLEAN).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();
    assertThat(variableExpr.variable()).isEqualTo(variable);
    assertThat(variableExpr.type()).isEqualTo(TypeNode.VOID);
    assertThat(variableExpr.isDecl()).isTrue();
  }
}
