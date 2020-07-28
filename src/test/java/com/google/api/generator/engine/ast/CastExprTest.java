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
  public void validCastExpr_basicPrimitiveSame() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.LONG).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    CastExpr.builder().setType(TypeNode.LONG).setExpr(variableExpr).build();
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validCastExpr_basicPrimitiveBoolean() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.BOOLEAN).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    CastExpr.builder().setType(TypeNode.BOOLEAN).setExpr(variableExpr).build();
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validCastExpr_basicPrimitiveNarrowing() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.LONG).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    CastExpr.builder().setType(TypeNode.INT).setExpr(variableExpr).build();
    // No exception thrown, so we succeeded.

    variable = Variable.builder().setName("x").setType(TypeNode.DOUBLE).build();
    variableExpr = VariableExpr.builder().setVariable(variable).build();
    CastExpr.builder().setType(TypeNode.FLOAT).setExpr(variableExpr).build();
    // No exception thrown, so we succeeded.

  }

  @Test
  public void validCastExpr_basicPrimitiveWidening() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    CastExpr.builder().setType(TypeNode.LONG).setExpr(variableExpr).build();
    // No exception thrown, so we succeeded.
    variable = Variable.builder().setName("x").setType(TypeNode.FLOAT).build();
    variableExpr = VariableExpr.builder().setVariable(variable).build();
    CastExpr.builder().setType(TypeNode.DOUBLE).setExpr(variableExpr).build();
    // No exception thrown, so we succeeded.

  }

  @Test
  public void validCastExpr_castBoxedTypeToPrimitive() {
    PrimitiveValue intValue = PrimitiveValue.builder().setValue("3").setType(TypeNode.INT).build();
    ValueExpr valueExpr = ValueExpr.withValue(intValue);
    CastExpr expr = CastExpr.builder().setType(TypeNode.INT_OBJECT).setExpr(valueExpr).build();
  }

  @Test
  public void validCastExpr_castPrimitiveToBoxedType() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.DOUBLE_OBJECT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    CastExpr expr = CastExpr.builder().setType(TypeNode.DOUBLE).setExpr(variableExpr).build();
  }

  @Test
  public void validCastExpr_nestedPrimitiveBoxedType() {
    // [Constructing] ((Object) ((Float) x))
    Variable variable = Variable.builder().setName("x").setType(TypeNode.FLOAT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    CastExpr expr = CastExpr.builder().setType(TypeNode.FLOAT_OBJECT).setExpr(variableExpr).build();
    CastExpr nestedExpr =
        CastExpr.builder()
            .setType(TypeNode.withReference(ConcreteReference.withClazz(Object.class)))
            .setExpr(expr)
            .build();
  }

  @Test
  public void invalidCastExpr_nestedPrimitiveBoxedType() {
    // [Constructing] ((Object) ((float) x))
    Variable variable = Variable.builder().setName("x").setType(TypeNode.FLOAT_OBJECT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    CastExpr expr = CastExpr.builder().setType(TypeNode.FLOAT).setExpr(variableExpr).build();
    assertThrows(
        IllegalStateException.class,
        () ->
            CastExpr.builder()
                .setType(TypeNode.withReference(ConcreteReference.withClazz(Object.class)))
                .setExpr(expr)
                .build());
  }

  @Test
  public void invalidCastExpr_castObjectToPrimitive() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    assertThrows(
        IllegalStateException.class,
        () -> CastExpr.builder().setType(TypeNode.INT).setExpr(variableExpr).build());
  }

  @Test
  public void invalidCastExpr_castPrimitiveToObject() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    assertThrows(
        IllegalStateException.class,
        () ->
            CastExpr.builder()
                .setType(TypeNode.withReference(ConcreteReference.withClazz(Object.class)))
                .setExpr(variableExpr)
                .build());
  }

  @Test
  public void invalidCastExpr_castBooleanToNumeric() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.BOOLEAN).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    assertThrows(
        IllegalStateException.class,
        () -> CastExpr.builder().setType(TypeNode.INT).setExpr(variableExpr).build());
  }

  @Test
  public void invalidCastExpr_castNumericToBoolean() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    assertThrows(
        IllegalStateException.class,
        () -> CastExpr.builder().setType(TypeNode.BOOLEAN).setExpr(variableExpr).build());
  }
}
