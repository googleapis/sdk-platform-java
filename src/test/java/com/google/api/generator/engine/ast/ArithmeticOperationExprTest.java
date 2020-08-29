package com.google.api.generator.engine.ast;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class ArithmeticOperationExprTest {
  @Test
  public void concatString_validBasic() {
    VariableExpr lhsExpr =
        VariableExpr.withVariable(Variable.builder().setType(TypeNode.STRING).setName("x").build());
    MethodInvocationExpr rhsExpr =
        MethodInvocationExpr.builder()
            .setReturnType(TypeNode.STRING)
            .setMethodName("getSomeString")
            .build();
    ArithmeticOperationExpr.concatWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void concatString_invalidVoidType() {
    VariableExpr lhsExpr =
        VariableExpr.withVariable(Variable.builder().setType(TypeNode.STRING).setName("x").build());
    MethodInvocationExpr rhsExpr =
        MethodInvocationExpr.builder()
            .setReturnType(TypeNode.VOID)
            .setMethodName("getSomething")
            .build();
    assertThrows(
        IllegalStateException.class,
        () -> ArithmeticOperationExpr.concatWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void concatString_invalidNonStringType() {
    VariableExpr lhsExpr =
        VariableExpr.withVariable(Variable.builder().setType(TypeNode.INT).setName("x").build());
    MethodInvocationExpr rhsExpr =
        MethodInvocationExpr.builder()
            .setReturnType(TypeNode.DOUBLE)
            .setMethodName("getSomething")
            .build();
    assertThrows(
        IllegalStateException.class,
        () -> ArithmeticOperationExpr.concatWithExprs(lhsExpr, rhsExpr));
  }
}
