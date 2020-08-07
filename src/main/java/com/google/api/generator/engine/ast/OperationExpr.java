package com.google.api.generator.engine.ast;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.List;

@AutoValue
public abstract class OperationExpr implements Expr {
  public abstract ImmutableList<Expr> expressions();

  public abstract OperatorNode operator();

  public TypeNode type() {
    TypeNode typeNode = expressions().get(0).type();
    if (OperatorNode.ADDITION.equals(operator())) {
      System.out.println();
    }
    // TODO: consider multiple types
    // int + String
    // int + double + number...
    for (Expr expr : expressions()) {
      if (!typeNode.equals(expr.type())) {
        throw new TypeMismatchException("Each expression type should match the same.");
      }
    }
    return typeNode;
  }

  @Override
  public void accept(AstNodeVisitor visitor) { visitor.visit(this);}

  public static Builder builder() {
    return new AutoValue_OperationExpr.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setExpressions(List<Expr> exprs);

    public abstract Builder setOperator(OperatorNode operatorNode);

    abstract OperationExpr autoBuild();

    public OperationExpr build() {
      OperationExpr operationExpr = autoBuild();
      List<Expr> exprs = operationExpr.expressions();
      OperatorNode operatorNode = operationExpr.operator();
      // Expressions Check
      if (OperatorNode.isUnaryOperator(operatorNode) && exprs.size() != 1) {
        throw new IllegalArgumentException(
            "Unary Operator " + operatorNode.toString() + " requires one expression."
        );
      }
      if (!OperatorNode.isUnaryOperator(operatorNode) && exprs.size() < 2) {
        throw new IllegalArgumentException(
            "Operator " + operatorNode.toString() + " requires at least two expressions."
        );
      }
      if (OperatorNode.isRelationalOperator(operatorNode) && exprs.size() != 2) {
        throw new IllegalArgumentException(
            "Relational Operator "
                + operatorNode.toString()
                + " needs exactly two expressions.");
      }
      if (OperatorNode.isBitwiseOperator(operatorNode)) {
        for (Expr expr : exprs) {
          if (!TypeNode.INT.equals(expr.type()) && !TypeNode.INTEGER.equals(expr.type())) {
            throw new IllegalArgumentException(
                "Bitwise Operator " + operatorNode.toString() + "only support int or Integer type"
            );
          }
        }
      }
      return operationExpr;
    }
  }
}
