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
  /** ============================== incrementWith ====================================== */
  @Test
  public void validGeneralForStatement_basicIsDecl() {
    Variable variable = Variable.builder().setName("i").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();
    ValueExpr initValue =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("0").setType(TypeNode.INT).build());

    MethodInvocationExpr maxSizeExpr =
        MethodInvocationExpr.builder().setMethodName("maxSize").setReturnType(TypeNode.INT).build();

    GeneralForStatement.incrementWith(
        variableExpr, initValue, maxSizeExpr, Arrays.asList(createBodyStatement()));
  }

  @Test
  public void validGeneralForStatement_basicIsNotDecl() {
    Variable variable = Variable.builder().setName("i").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(false).build();
    ValueExpr initValue =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("0").setType(TypeNode.INT).build());

    MethodInvocationExpr maxSizeExpr =
        MethodInvocationExpr.builder().setMethodName("maxSize").setReturnType(TypeNode.INT).build();

    GeneralForStatement.incrementWith(
        variableExpr, initValue, maxSizeExpr, Arrays.asList(createBodyStatement()));
  }

  @Test
  public void validGeneralForStatement_emptyBody() {
    Variable variable = Variable.builder().setName("i").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(false).build();
    ValueExpr initValue =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("0").setType(TypeNode.INT).build());

    MethodInvocationExpr maxSizeExpr =
        MethodInvocationExpr.builder().setMethodName("maxSize").setReturnType(TypeNode.INT).build();

    GeneralForStatement.incrementWith(
        variableExpr, initValue, maxSizeExpr, Collections.emptyList());
  }

  @Test
  public void invalidForStatement_privateVariable() {
    Variable variable = Variable.builder().setName("i").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setScope(ScopeNode.PRIVATE).build();
    ValueExpr initValue =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("0").setType(TypeNode.INT).build());
    MethodInvocationExpr maxSizeExpr =
        MethodInvocationExpr.builder().setMethodName("maxSize").setReturnType(TypeNode.INT).build();

    assertThrows(
        IllegalStateException.class,
        () ->
            GeneralForStatement.incrementWith(
                variableExpr, initValue, maxSizeExpr, Collections.emptyList()));
  }

  @Test
  public void invalidForStatement_staticAndFinalVariable() {
    Variable variable = Variable.builder().setName("i").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsFinal(true).setIsStatic(true).build();
    ValueExpr initValue =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("0").setType(TypeNode.INT).build());
    MethodInvocationExpr maxSizeExpr =
        MethodInvocationExpr.builder().setMethodName("maxSize").setReturnType(TypeNode.INT).build();

    assertThrows(
        IllegalStateException.class,
        () ->
            GeneralForStatement.incrementWith(
                variableExpr, initValue, maxSizeExpr, Collections.emptyList()));
  }

  /** ============================ Set Three Expressions ================================ */
  @Test
  public void validGeneralForState_buildExprs() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(Variable.builder().setName("i").setType(TypeNode.INT).build());
    ValueExpr initValueExpr =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("0").setType(TypeNode.INT).build());
    ValueExpr maxValueExpr =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("10").setType(TypeNode.INT).build());
    AssignmentExpr initializationExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(initValueExpr).build();
    RelationalOperationExpr terminationExpr =
        RelationalOperationExpr.lessThanWithExprs(variableExpr, maxValueExpr);
    UnaryOperationExpr incrementExpr = UnaryOperationExpr.postfixIncrementWithExpr(variableExpr);
    GeneralForStatement.builder()
        .setInitializationExpr(initializationExpr)
        .setTerminationExpr(terminationExpr)
        .setIncrementExpr(incrementExpr)
        .build();
  }

  @Test
  public void validGeneralForState_incrementExprIsMethodInvocationEpxr() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(Variable.builder().setName("i").setType(TypeNode.INT).build());
    ValueExpr initValueExpr =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("0").setType(TypeNode.INT).build());
    ValueExpr maxValueExpr =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("10").setType(TypeNode.INT).build());
    AssignmentExpr initializationExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(initValueExpr).build();
    RelationalOperationExpr terminationExpr =
        RelationalOperationExpr.lessThanWithExprs(variableExpr, maxValueExpr);
    MethodInvocationExpr incrementExpr =
        MethodInvocationExpr.builder()
            .setMethodName("doNothing")
            .setReturnType(TypeNode.INT)
            .build();
    GeneralForStatement.builder()
        .setInitializationExpr(initializationExpr)
        .setTerminationExpr(terminationExpr)
        .setIncrementExpr(incrementExpr)
        .build();
  }

  @Test
  public void validGeneralForState_incrementExprIsAssignmentOperationExpr() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(Variable.builder().setName("i").setType(TypeNode.INT).build());
    ValueExpr initValueExpr =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("0").setType(TypeNode.INT).build());
    ValueExpr maxValueExpr =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("10").setType(TypeNode.INT).build());
    AssignmentExpr initializationExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(initValueExpr).build();
    RelationalOperationExpr terminationExpr =
        RelationalOperationExpr.lessThanWithExprs(variableExpr, maxValueExpr);
    AssignmentOperationExpr incrementExpr =
        AssignmentOperationExpr.xorAssignmentWithExprs(
            variableExpr,
            ValueExpr.withValue(
                PrimitiveValue.builder().setValue("5").setType(TypeNode.INT).build()));
    GeneralForStatement.builder()
        .setInitializationExpr(initializationExpr)
        .setTerminationExpr(terminationExpr)
        .setIncrementExpr(incrementExpr)
        .build();
  }

  @Test
  public void validGeneralForState_incrementExprIsAssignmentExpr() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(Variable.builder().setName("i").setType(TypeNode.INT).build());
    ValueExpr initValueExpr =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("0").setType(TypeNode.INT).build());
    ValueExpr maxValueExpr =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("10").setType(TypeNode.INT).build());
    AssignmentExpr initializationExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(initValueExpr).build();
    RelationalOperationExpr terminationExpr =
        RelationalOperationExpr.lessThanWithExprs(variableExpr, maxValueExpr);
    AssignmentExpr incrementExpr =
        AssignmentExpr.builder()
            .setVariableExpr(variableExpr)
            .setValueExpr(
                ValueExpr.withValue(
                    PrimitiveValue.builder().setValue("5").setType(TypeNode.INT).build()))
            .build();
    GeneralForStatement.builder()
        .setInitializationExpr(initializationExpr)
        .setTerminationExpr(terminationExpr)
        .setIncrementExpr(incrementExpr)
        .build();
  }

  @Test
  public void invalidGeneralForState_buildTerminalExprNotBooleanType() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(Variable.builder().setName("i").setType(TypeNode.INT).build());
    ValueExpr initValueExpr =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("0").setType(TypeNode.INT).build());
    ValueExpr maxValueExpr =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("10").setType(TypeNode.INT).build());
    AssignmentExpr initializationExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(initValueExpr).build();
    AssignmentOperationExpr terminationExpr =
        AssignmentOperationExpr.multiplyAssignmentWithExprs(variableExpr, maxValueExpr);
    UnaryOperationExpr incrementExpr = UnaryOperationExpr.postfixIncrementWithExpr(variableExpr);
    assertThrows(
        IllegalStateException.class,
        () ->
            GeneralForStatement.builder()
                .setInitializationExpr(initializationExpr)
                .setTerminationExpr(terminationExpr)
                .setIncrementExpr(incrementExpr)
                .build());
  }

  @Test
  public void invalidGeneralForState_buildIncrementExprIsNotExprStatement() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(Variable.builder().setName("i").setType(TypeNode.INT).build());
    ValueExpr initValueExpr =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("0").setType(TypeNode.INT).build());
    ValueExpr maxValueExpr =
        ValueExpr.withValue(PrimitiveValue.builder().setValue("10").setType(TypeNode.INT).build());
    AssignmentExpr initializationExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(initValueExpr).build();
    RelationalOperationExpr terminationExpr =
        RelationalOperationExpr.lessThanWithExprs(variableExpr, maxValueExpr);
    RelationalOperationExpr incrementExpr =
        RelationalOperationExpr.equalToWithExprs(variableExpr, maxValueExpr);
    assertThrows(
        IllegalStateException.class,
        () ->
            GeneralForStatement.builder()
                .setInitializationExpr(initializationExpr)
                .setTerminationExpr(terminationExpr)
                .setIncrementExpr(incrementExpr)
                .build());
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
