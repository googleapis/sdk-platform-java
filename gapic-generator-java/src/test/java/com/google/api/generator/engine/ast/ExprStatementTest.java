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

public class ExprStatementTest {

  @Test
  public void validMethodExprStatement() {
    MethodInvocationExpr methodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("foobar")
            .setStaticReferenceName("SomeClass")
            .build();
    assertValidExprStatement(methodExpr);
  }

  @Test
  public void validVariableExprStatement() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    assertValidExprStatement(VariableExpr.builder().setVariable(variable).setIsDecl(true).build());
  }

  @Test
  public void validAssignmentExprStatement() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Value value = PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build();
    Expr valueExpr = ValueExpr.builder().setValue(value).build();

    AssignmentExpr assignmentExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();
    assertValidExprStatement(assignmentExpr);
  }

  @Test
  public void invalidVariableExprStatement() {
    Variable variable = Variable.builder().setType(TypeNode.INT).setName("libraryClient").build();
    VariableExpr varExpr = VariableExpr.builder().setVariable(variable).build();
    assertInvalidExprStatement(varExpr);
  }

  @Test
  public void invalidValueExprStatement() {
    Value value = PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build();
    ValueExpr valueExpr = ValueExpr.builder().setValue(value).build();
    assertInvalidExprStatement(valueExpr);
  }

  private static void assertInvalidExprStatement(Expr expr) {
    assertThrows(
        IllegalStateException.class,
        () -> {
          ExprStatement.withExpr(expr);
        });
  }

  private static void assertValidExprStatement(Expr expr) {
    assertThat(ExprStatement.withExpr(expr).expression()).isEqualTo(expr);
  }
}
