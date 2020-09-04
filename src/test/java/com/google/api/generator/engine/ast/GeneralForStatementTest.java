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

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

public class GeneralForStatementTest {

  @Test
  public void validGeneralForStatement_basicIsDecl() {
    Variable variable = Variable.builder().setName("i").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    MethodInvocationExpr maxSizeExpr =
        MethodInvocationExpr.builder().setMethodName("maxSize").build();

    GeneralForStatement.incrementWith(
        variableExpr, maxSizeExpr, Arrays.asList(createBodyStatement()));
  }

  @Test
  public void validGeneralForStatement_basicIsNotDecl() {
    Variable variable = Variable.builder().setName("i").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(false).build();

    MethodInvocationExpr maxSizeExpr =
        MethodInvocationExpr.builder().setMethodName("maxSize").build();

    GeneralForStatement.incrementWith(
        variableExpr, maxSizeExpr, Arrays.asList(createBodyStatement()));
  }

  @Test
  public void validGeneralForStatement_emptyBody() {
    Variable variable = Variable.builder().setName("i").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(false).build();

    MethodInvocationExpr maxSizeExpr =
        MethodInvocationExpr.builder().setMethodName("maxSize").build();

    GeneralForStatement.incrementWith(variableExpr, maxSizeExpr, Collections.emptyList());
  }

  @Test
  public void invalidForStatement() {
    Variable variable = Variable.builder().setName("str").setType(TypeNode.STRING).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setScope(ScopeNode.PRIVATE).build();
    MethodInvocationExpr maxSizeExpr =
        MethodInvocationExpr.builder().setMethodName("maxSize").build();

    assertThrows(
        IllegalStateException.class,
        () ->
            GeneralForStatement.incrementWith(variableExpr, maxSizeExpr, Collections.emptyList()));
  }

  private static Statement createBodyStatement() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Variable anotherVariable = Variable.builder().setName("y").setType(TypeNode.INT).build();
    Expr valueExpr = VariableExpr.builder().setVariable(anotherVariable).build();

    return ExprStatement.withExpr(
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build());
  }
}
