package com.google.api.generator.engine.ast;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class AssignmentOperationExprTest {
  /** ========================= Multiply And Assignment Operators =============================== */
  /** =========== Multiply And Assignment Operators: LHS data type is numeric ================= */
  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericMatched() {
    // int a = 1;
    // double b = 2;
    // char c = 'e';
    // float f = 5.99f;
    // short s = 5000;
    // long l = 15000000000L;
    // boolean bool = false;
    // Integer ao = new Integer(4);
    // Float fo = new Float(f);
    Boolean bo = new Boolean(false);
    // int[] ar = new int[3];
    // String word = "avc";
    // boolean we = a < word;
    // No need swap test case.
    // boolean c = bo < new Object();
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericUnmatched() {
    // No need swap test case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericMatchedBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validNumericBoxedWithMatchType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericUnmatchedBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validNumericBoxedWithUnmatchedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidBooleanBoxedWithNumericType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNumericWithReferenceType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidReferencedWithNumericType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNumericWithNewObject() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNewObjectWithNumeric".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ==== Multiply And Assignment Operators: LHS data type is boolean and its boxed type ===== */
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidBooleanWithNumericType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidBooleanBoxedWithNumericType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Numeric Box Type ============ */
  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericBoxedWithMatchType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validNumericMatchedBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericBoxedWithUnmatchedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validNumericUnmatchedBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.SHORT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Reference Type ============ */
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidReferencedWithNumericType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithReferenceType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }
  /** ======== Multiply And Assignment Operators: LHS data type is Object Type ============ */
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNewObjectWithNumeric() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithNewObject".
    NewObjectExpr lhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "x");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }
  /** ======== Multiply And Assignment Operators: LHS data type is Null Type ============ */
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNullWithNumericType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithNullType".
    ValueExpr lhsExpr = ValueExpr.withValue(NullObjectValue.create());
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  private VariableExpr createVariableExpr(TypeNode type, String name) {
    Variable variable = Variable.builder().setName(name).setType(type).build();
    VariableExpr variableExpr = VariableExpr.withVariable(variable);
    return variableExpr;
  }
}
