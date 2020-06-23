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

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class AssignmentExprTest {
  @Test
  public void assignMatchingValue() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Value value = PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build();
    Expr valueExpr = ValueExpr.builder().setValue(value).build();

    assertValidAssignmentExpr(variableExpr, valueExpr);
  }

  @Test
  public void assignMismatchedValue() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.BOOLEAN).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Value value = PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build();
    Expr valueExpr = ValueExpr.builder().setValue(value).build();

    assertInvalidAssignmentExpr(variableExpr, valueExpr);
  }

  @Test
  public void assignSubtypeValue() {
    Variable variable =
        Variable.builder()
            .setName("x")
            .setType(TypeNode.withReference(Reference.withClazz(List.class)))
            .build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    MethodInvocationExpr valueExpr =
        MethodInvocationExpr.builder()
            .setMethodName("getAList")
            .setReturnType(TypeNode.withReference(Reference.withClazz(ArrayList.class)))
            .build();

    assertValidAssignmentExpr(variableExpr, valueExpr);
  }

  @Test
  public void assignMatchingVariable() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Variable anotherVariable = Variable.builder().setName("y").setType(TypeNode.INT).build();
    Expr valueExpr = VariableExpr.builder().setVariable(anotherVariable).build();

    assertValidAssignmentExpr(variableExpr, valueExpr);
  }

  private static void assertInvalidAssignmentExpr(VariableExpr variableExpr, Expr valueExpr) {
    assertThrows(
        TypeMismatchException.class,
        () -> {
          AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();
        });
  }

  private static void assertValidAssignmentExpr(VariableExpr variableExpr, Expr valueExpr) {
    AssignmentExpr assignMentExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();
    // No exception, we succeeded.
    assertThat(assignMentExpr.variableExpr()).isEqualTo(variableExpr);
    assertThat(assignMentExpr.valueExpr()).isEqualTo(valueExpr);
  }
}
