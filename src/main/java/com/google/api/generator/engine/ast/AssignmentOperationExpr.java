package com.google.api.generator.engine.ast;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;

@AutoValue
public abstract class AssignmentOperationExpr implements OperationExpr {
  public abstract Expr lhsExpr();

  public abstract Expr rhsExpr();

  public abstract OperatorKind operatorKind();

  public abstract TypeNode type();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static AssignmentOperationExpr bitwiseExclusiveOrAndAssignmentWithExprs(
      Expr lhsExpr, Expr rhsExpr) {
    return builder()
        .setLhsExpr(lhsExpr)
        .setRhsExpr(rhsExpr)
        .setOperatorKind(OperatorKind.ASSIGNMENT_BITWISE_EXCLUSIVE_OR_AND_ASSIGNMENT)
        .setType(lhsExpr.type())
        .build();
  }

  public static AssignmentOperationExpr multiplyAndAssignmentWithExprs(Expr lhsExpr, Expr rhsExpr) {
    return builder()
        .setLhsExpr(lhsExpr)
        .setRhsExpr(rhsExpr)
        .setOperatorKind(OperatorKind.ASSIGNMENT_MULTIPLY_AND_ASSIGNMENT)
        .setType(lhsExpr.type())
        .build();
  }

  private static Builder builder() {
    return new AutoValue_AssignmentOperationExpr.Builder();
  }

  @AutoValue.Builder
  abstract static class Builder {
    // Private setter.
    abstract Builder setLhsExpr(Expr expr);

    // Private setter.
    abstract Builder setRhsExpr(Expr expr);

    // Private setter.
    abstract Builder setOperatorKind(OperatorKind operator);

    // Private setter.
    abstract Builder setType(TypeNode type);

    abstract AssignmentOperationExpr autoBuild();

    private AssignmentOperationExpr build() {
      AssignmentOperationExpr assignmentOperationExpr = autoBuild();
      TypeNode lhsExprType = assignmentOperationExpr.lhsExpr().type();
      TypeNode rhsExprType = assignmentOperationExpr.rhsExpr().type();
      OperatorKind operator = assignmentOperationExpr.operatorKind();
      final String errorMsg =
          String.format(
              "Assignment operator %s can not be applied to %s, %s.",
              operator, lhsExprType.toString(), rhsExprType.toString());
      if (operator.equals(OperatorKind.ASSIGNMENT_MULTIPLY_AND_ASSIGNMENT)) {
        Preconditions.checkState(
            isValidMultiplyAndAssignmentType(lhsExprType, rhsExprType), errorMsg);
      }
      return assignmentOperationExpr;
    }

    private boolean isValidMultiplyAndAssignmentType(TypeNode lhsType, TypeNode rhsType) {
      if (lhsType.equals(rhsType)) {
        return true;
      }
      if (TypeNode.isNumericType(lhsType)) {
        return TypeNode.isNumericType(rhsType);
      }
      if (TypeNode.isBoxedType(lhsType)) {
        return rhsType.equals(TypeNode.NULL);
      }
      return false;
    }
  }
}
