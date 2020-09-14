package com.google.api.generator.engine.ast;

import org.junit.Test;

public class AssignmentOperationExprTest {
  /** ==================== Multiply And Assignment Operators: LHS data type is numeric ======================= */
  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericMatched() {
    int a = 1;
    double b = 2;
    char c = 'e';
    float f = 5.99f;
    short s = 5000;
    long l = 15000000000L;
    boolean bool = false;
    a *= new Integer(4);
    int we = a;
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericUnmatched() {
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericMatchedBoxedType() {
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericUnmatchedBoxedType() {
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }


  private VariableExpr createVariableExpr(TypeNode type, String name) {
    Variable variable = Variable.builder().setName(name).setType(type).build();
    VariableExpr variableExpr = VariableExpr.withVariable(variable);
    return variableExpr;
  }

}
