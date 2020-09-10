package com.google.api.generator.engine.ast;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class LogicalOperationExprTest {
  /** =============================== Logic And Operation Expr =============================== */
  @Test
  public void logicalAnd_validBasic() {
    VariableExpr lhsExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.BOOLEAN).setName("isGood").build());
    VariableExpr rhsExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.BOOLEAN).setName("isValid").build());
    LogicalOperationExpr.logicalAndWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void logicalAnd_validBoxedBoolean() {
    VariableExpr lhsExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.BOOLEAN).setName("isGood").build());
    VariableExpr rhsExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.BOOLEAN_OBJECT).setName("isValid").build());
    LogicalOperationExpr.logicalAndWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void logicalAnd_invalidNumericType() {
    VariableExpr lhsExpr =
        VariableExpr.withVariable(Variable.builder().setType(TypeNode.INT).setName("x").build());
    VariableExpr rhsExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.BOOLEAN).setName("isValid").build());
    assertThrows(
        IllegalStateException.class,
        () -> LogicalOperationExpr.logicalAndWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void logicalAnd_invalidStringType() {
    VariableExpr lhsExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.BOOLEAN_OBJECT).setName("x").build());
    VariableExpr rhsExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.STRING).setName("isValid").build());
    assertThrows(
        IllegalStateException.class,
        () -> LogicalOperationExpr.logicalAndWithExprs(lhsExpr, rhsExpr));
  }

  /** =============================== Logic Or Operation Expr =============================== */
  @Test
  public void logicalOr_validBoxedBoolean() {
    VariableExpr lhsExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.BOOLEAN_OBJECT).setName("isGood").build());
    VariableExpr rhsExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.BOOLEAN_OBJECT).setName("isValid").build());
    LogicalOperationExpr.logicalOrWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void logicalOr_invalidVoidType() {
    VariableExpr lhsExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.BOOLEAN).setName("x").build());
    MethodInvocationExpr rhsExpr =
        MethodInvocationExpr.builder().setMethodName("doNothing").build();
    assertThrows(
        IllegalStateException.class,
        () -> LogicalOperationExpr.logicalOrWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void logicalOr_invalidNullType() {
    VariableExpr lhsExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(TypeNode.BOOLEAN).setName("x").build());
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    assertThrows(
        IllegalStateException.class,
        () -> LogicalOperationExpr.logicalOrWithExprs(lhsExpr, rhsExpr));
  }
}
