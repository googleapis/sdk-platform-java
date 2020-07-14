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

import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class CastExprTest {
  @Test
  public void validCastExpr_basic() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    CastExpr.builder()
        .setType(TypeNode.withReference(ConcreteReference.withClazz(Object.class)))
        .setExpr(variableExpr)
        .build();
    // No exception thrown, so we succeeded.
  }

  @Test
  public void invalidCastExpr_castToPrimitive() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    assertThrows(
        IllegalStateException.class,
        () -> {
          CastExpr.builder().setType(TypeNode.INT).setExpr(variableExpr).build();
        });
  }

  @Test
  public void invalidCastExpr_castPrimitiveToObject() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    assertThrows(
        IllegalStateException.class,
        () -> {
          CastExpr.builder()
              .setType(TypeNode.withReference(ConcreteReference.withClazz(Object.class)))
              .setExpr(variableExpr)
              .build();
        });
  }
}
