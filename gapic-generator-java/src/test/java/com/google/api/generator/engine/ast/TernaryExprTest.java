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
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class TernaryExprTest {
  @Test
  public void validTernaryExpr_primitiveType() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();

    Variable conditionVariable =
        Variable.builder().setName("condition").setType(TypeNode.BOOLEAN).build();
    VariableExpr conditionExpr = VariableExpr.builder().setVariable(conditionVariable).build();

    Value value1 = PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build();
    Expr thenExpr = ValueExpr.builder().setValue(value1).build();
    Value value2 = PrimitiveValue.builder().setType(TypeNode.INT).setValue("4").build();
    Expr elseExpr = ValueExpr.builder().setValue(value2).build();

    TernaryExpr ternaryExpr =
        TernaryExpr.builder()
            .setConditionExpr(conditionExpr)
            .setThenExpr(thenExpr)
            .setElseExpr(elseExpr)
            .build();
    assertThat(ternaryExpr.conditionExpr().type()).isEqualTo(TypeNode.BOOLEAN);
    assertThat(ternaryExpr.thenExpr().type()).isEqualTo(ternaryExpr.elseExpr().type());
    assertThat(ternaryExpr.type()).isEqualTo(ternaryExpr.thenExpr().type());
  }

  @Test
  public void validTernaryExpr_ObjectType() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();

    Variable conditionVariable =
        Variable.builder().setName("condition").setType(TypeNode.BOOLEAN).build();
    VariableExpr conditionExpr = VariableExpr.builder().setVariable(conditionVariable).build();

    Value value1 = StringObjectValue.withValue("str1");
    Expr thenExpr = ValueExpr.builder().setValue(value1).build();
    Value value2 = StringObjectValue.withValue("str2");
    Expr elseExpr = ValueExpr.builder().setValue(value2).build();

    TernaryExpr ternaryExpr =
        TernaryExpr.builder()
            .setConditionExpr(conditionExpr)
            .setThenExpr(thenExpr)
            .setElseExpr(elseExpr)
            .build();
    assertThat(ternaryExpr.conditionExpr().type()).isEqualTo(TypeNode.BOOLEAN);
    assertThat(ternaryExpr.thenExpr().type()).isEqualTo(ternaryExpr.elseExpr().type());
    assertThat(ternaryExpr.type()).isEqualTo(ternaryExpr.thenExpr().type());
  }

  @Test
  public void invalidTernaryExpr() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();

    Variable conditionVariable =
        Variable.builder().setName("condition").setType(TypeNode.BOOLEAN).build();
    VariableExpr conditionExpr = VariableExpr.builder().setVariable(conditionVariable).build();

    Value value1 = PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build();
    Expr thenExpr = ValueExpr.builder().setValue(value1).build();
    Value value2 = PrimitiveValue.builder().setType(TypeNode.BOOLEAN).setValue("false").build();
    Expr elseExpr = ValueExpr.builder().setValue(value2).build();
    assertThrows(
        IllegalStateException.class,
        () -> {
          TernaryExpr.builder()
              .setConditionExpr(conditionExpr)
              .setThenExpr(thenExpr)
              .setElseExpr(elseExpr)
              .build();
        });
  }
}
