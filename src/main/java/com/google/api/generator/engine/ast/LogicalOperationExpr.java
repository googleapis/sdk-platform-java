package com.google.api.generator.engine.ast;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;

@AutoValue
public abstract class LogicalOperationExpr implements OperationExpr {

  public abstract Expr lhsExpr();

  public abstract Expr rhsExpr();

  public abstract OperatorKind operatorKind();

  @Override
  public TypeNode type() {
    return TypeNode.BOOLEAN;
  }

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  // Convenience wrapper.
  public static LogicalOperationExpr logicalAndWithExprs(Expr lhsExpr, Expr rhsExpr) {
    return builder()
        .setLhsExpr(lhsExpr)
        .setRhsExpr(rhsExpr)
        .setOperatorKind(OperatorKind.LOGICAL_AND)
        .build();
  }

  // Convenience wrapper.
  public static LogicalOperationExpr logicalOrWithExprs(Expr lhsExpr, Expr rhsExpr) {
    return builder()
        .setLhsExpr(lhsExpr)
        .setRhsExpr(rhsExpr)
        .setOperatorKind(OperatorKind.LOGICAL_OR)
        .build();
  }

  private static Builder builder() {
    return new AutoValue_LogicalOperationExpr.Builder();
  }

  @AutoValue.Builder
  abstract static class Builder {
    // Private setter.
    abstract Builder setLhsExpr(Expr expr);

    // Private setter.
    abstract Builder setRhsExpr(Expr expr);

    // Private setter.
    abstract Builder setOperatorKind(OperatorKind operator);

    abstract LogicalOperationExpr autoBuild();

    private LogicalOperationExpr build() {
      LogicalOperationExpr logicalOperationExpr = autoBuild();
      TypeNode lhsExprType = logicalOperationExpr.lhsExpr().type();
      TypeNode rhsExprType = logicalOperationExpr.rhsExpr().type();
      OperatorKind operator = logicalOperationExpr.operatorKind();
      final String errorMsg =
          String.format(
              "Logical operator %s can not be applied to %s, %s.",
              operator, lhsExprType.toString(), rhsExprType.toString());
      Preconditions.checkState(isValidLogicalType(lhsExprType, rhsExprType), errorMsg);
      return logicalOperationExpr;
    }

    private boolean isValidLogicalType(TypeNode lhsType, TypeNode rhsType) {
      return lhsType.equals(TypeNode.BOOLEAN) && rhsType.equals(TypeNode.BOOLEAN);
    }
  }
}
