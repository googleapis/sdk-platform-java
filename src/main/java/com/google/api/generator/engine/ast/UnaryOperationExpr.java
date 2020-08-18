package com.google.api.generator.engine.ast;

import com.google.api.generator.engine.lexicon.OperatorKind;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;

@AutoValue
public abstract class UnaryOperationExpr implements OperationExpr {

  public abstract Expr firstExpression();

  public abstract OperatorKind operatorKind();

  public abstract boolean isPostfixOperator();

  public abstract TypeNode type();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static UnaryOperationExpr postfixIncrementWithExpr(Expr expr) {
    return builder()
        .setFirstExpression(expr)
        .setOperatorKind(OperatorKind.UNARY_INCREMENT)
        .setType(expr.type())
        .setIsPostfixOperator(true)
        .build();
  }

  public static UnaryOperationExpr logicalNotWithExpr(Expr expr) {
    return builder()
        .setOperatorKind(OperatorKind.LOGICAL_NOT)
        .setFirstExpression(expr)
        .setType(TypeNode.BOOLEAN)
        .setIsPostfixOperator(false)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_UnaryOperationExpr.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    // private
    abstract Builder setFirstExpression(Expr expr);

    // private
    abstract Builder setSecondExpression(Expr expr);

    // private.
    abstract Builder setOperatorKind(OperatorKind operator);

    // private.
    abstract Builder setType(TypeNode type);

    // private
    abstract Builder setIsPostfixOperator(boolean isPostfix);

    abstract UnaryOperationExpr autoBuild();

    private UnaryOperationExpr build() {
      UnaryOperationExpr unaryOperationExpr = autoBuild();
      Expr expr = unaryOperationExpr.firstExpression();
      TypeNode exprType =
          expr instanceof MethodInvocationExpr
              ? ((MethodInvocationExpr) expr).returnType()
              : expr.type();
      OperatorKind operator = unaryOperationExpr.operatorKind();
      final String errorMsg =
          "Unary operator " + operator + " can not be applied to " + exprType.toString();
      if (operator.equals(OperatorKind.LOGICAL_NOT)) {
        Preconditions.checkState(exprType.equals(TypeNode.BOOLEAN), errorMsg);
      }
      // Note: the following check also apply for Decrement, if we support for future.
      if (operator.equals(OperatorKind.UNARY_INCREMENT)) {
        Preconditions.checkState(TypeNode.isNumberType(exprType), errorMsg);
      }
      return unaryOperationExpr;
    }
  }
}
