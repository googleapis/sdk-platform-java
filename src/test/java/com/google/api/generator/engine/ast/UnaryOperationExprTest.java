package com.google.api.generator.engine.ast;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class UnaryOperationExprTest {
  /** =============================== Logic Not =============================== */
  @Test
  public void logicalNot_validBasic() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("x").setType(TypeNode.BOOLEAN).build());
    UnaryOperationExpr.logicalNotWithExpr(variableExpr);
    // No exception thrown, we're good.
  }

  @Test
  public void logicalNot_validBoxedType() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("x").setType(TypeNode.BOOLEAN_OBJECT).build());
    UnaryOperationExpr.logicalNotWithExpr(variableExpr);
    // No exception thrown, we're good.
  }

  @Test
  public void logicalNot_invalidNumericType() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(Variable.builder().setName("x").setType(TypeNode.INT).build());
    assertThrows(
        IllegalStateException.class, () -> UnaryOperationExpr.logicalNotWithExpr(variableExpr));
  }

  @Test
  public void logicalNot_invalidReferenceType() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(Variable.builder().setName("x").setType(TypeNode.STRING).build());
    assertThrows(
        IllegalStateException.class, () -> UnaryOperationExpr.logicalNotWithExpr(variableExpr));
  }

  /** =============================== Post Increment =============================== */
  @Test
  public void postIncrement_validBasic() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(Variable.builder().setName("x").setType(TypeNode.INT).build());
    UnaryOperationExpr.postfixIncrementWithExpr(variableExpr);
    // No exception thrown, we're good.
  }

  @Test
  public void postIncrement_validBoxedType() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("x").setType(TypeNode.FLOAT_OBJECT).build());
    UnaryOperationExpr.postfixIncrementWithExpr(variableExpr);
    // No exception thrown, we're good.
  }

  @Test
  public void postIncrement_invalidBooleanBoxedType() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(
            Variable.builder().setName("x").setType(TypeNode.BOOLEAN_OBJECT).build());
    assertThrows(
        IllegalStateException.class,
        () -> UnaryOperationExpr.postfixIncrementWithExpr(variableExpr));
  }

  @Test
  public void postIncrement_invalidReferenceType() {
    VariableExpr variableExpr =
        VariableExpr.withVariable(Variable.builder().setName("x").setType(TypeNode.STRING).build());
    assertThrows(
        IllegalStateException.class,
        () -> UnaryOperationExpr.postfixIncrementWithExpr(variableExpr));
  }
}
