package com.google.api.generator.engine.ast;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AutoValue
public abstract class OperationExpr implements Expr {
  public abstract ImmutableList<Expr> expressions();

  public abstract OperatorNode operator();

  public abstract TypeNode type();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static Builder builder() {
    return new AutoValue_OperationExpr.Builder().setType(TypeNode.VOID);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setExpressions(List<Expr> exprs);

    public abstract Builder setOperator(OperatorNode operatorNode);

    // private.
    abstract Builder setType(TypeNode type);

    abstract OperationExpr autoBuild();

    public OperationExpr build() {
      OperationExpr operationExpr = autoBuild();
      List<Expr> exprs = operationExpr.expressions();
      OperatorNode operatorNode = operationExpr.operator();

      Set<TypeNode> types = exprs.stream().map(expr -> expr.type()).collect(Collectors.toSet());
      if (!OperatorNode.isArithmeticOperator(operatorNode) && types.size() > 1) {
        throw new IllegalArgumentException(
            "Operator " + operatorNode.toString() + " cannot be applied to unmatched types.");
      }
      if (OperatorNode.isArithmeticOperator(operatorNode)
          && !types.stream()
              .filter(type -> !TypeNode.STRING.equals(type))
              .allMatch(type -> TypeNode.isNumberType(type))) {
        throw new IllegalArgumentException(
            "Arithmetic Operator "
                + operatorNode.toString()
                + " can only be applied to number types.");
      }

      TypeNode operationExprType = types.stream().findFirst().get();
      // Addition as concat string operator
      if (operatorNode.equals(OperatorNode.ADDITION)
          && types.size() == 2
          && types.contains(TypeNode.STRING)) {
        operationExprType = TypeNode.STRING;
      }
      // TODO(summerji): Support mixed-type in arithmetic rules if need later.
      // Currently, we only support same number type in arithmetic operator (e.g 2.2 + 1.0)
      // And concat String in Addition operator (e.g 5 + "someWord")
      setType(operationExprType);

      // Expressions Check
      if (OperatorNode.isUnaryOperator(operatorNode) && exprs.size() != 1) {
        throw new IllegalArgumentException(
            "Unary Operator " + operatorNode.toString() + " requires one expression.");
      }
      if (!OperatorNode.isUnaryOperator(operatorNode) && exprs.size() < 2) {
        throw new IllegalArgumentException(
            "Operator " + operatorNode.toString() + " requires at least two expressions.");
      }
      if (OperatorNode.isRelationalOperator(operatorNode) && exprs.size() != 2) {
        throw new IllegalArgumentException(
            "Relational Operator " + operatorNode.toString() + " needs exactly two expressions.");
      }
      if (OperatorNode.isBitwiseOperator(operatorNode)) {
        for (Expr expr : exprs) {
          if (!TypeNode.INT.equals(expr.type()) && !TypeNode.INTEGER.equals(expr.type())) {
            throw new IllegalArgumentException(
                "Bitwise Operator " + operatorNode.toString() + "only support int or Integer type");
          }
        }
      }
      return autoBuild();
    }
  }
}
