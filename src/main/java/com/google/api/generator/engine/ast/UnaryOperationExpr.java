// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.engine.ast;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;

@AutoValue
public abstract class UnaryOperationExpr implements OperationExpr {

  public abstract Expr expression();

  public abstract OperatorKind operatorKind();

  public abstract TypeNode type();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static UnaryOperationExpr postfixIncrementWithExpr(Expr expr) {
    return builder()
        .setExpression(expr)
        .setOperatorKind(OperatorKind.UNARY_POST_INCREMENT)
        .setType(expr.type())
        .build();
  }

  public static UnaryOperationExpr logicalNotWithExpr(Expr expr) {
    return builder()
        .setOperatorKind(OperatorKind.LOGICAL_NOT)
        .setExpression(expr)
        .setType(TypeNode.BOOLEAN)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_UnaryOperationExpr.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    // private
    abstract Builder setExpression(Expr expr);

    // private.
    abstract Builder setOperatorKind(OperatorKind operator);

    // private.
    abstract Builder setType(TypeNode type);

    abstract UnaryOperationExpr autoBuild();

    private UnaryOperationExpr build() {
      UnaryOperationExpr unaryOperationExpr = autoBuild();
      Expr expr = unaryOperationExpr.expression();
      TypeNode exprType = expr.type();
      OperatorKind operator = unaryOperationExpr.operatorKind();
      final String errorMsg =
          "Unary operator " + operator + " can not be applied to " + exprType.toString();

      // Operators can not be applied on Void type
      Preconditions.checkState(
          !exprType.equals(TypeNode.VOID) && !exprType.equals(TypeNode.NULL), errorMsg);
      if (operator.equals(OperatorKind.LOGICAL_NOT)) {
        // Logical not (!) can only be applied on boolean/Boolean type
        Preconditions.checkState(
            exprType.equals(TypeNode.BOOLEAN) || exprType.equals(TypeNode.BOOLEAN_OBJECT),
            errorMsg);
      }
      if (operator.equals(OperatorKind.UNARY_POST_INCREMENT)) {
        // Post-Increment (++) can be applied on Numeric Type(int, double, float, long, short, char)
        // and its boxed type (exclude Boolean)
        Preconditions.checkState(
            exprType.isNumericType()
                || (TypeNode.isBoxedType(exprType) && !exprType.equals(TypeNode.BOOLEAN_OBJECT)),
            errorMsg);
      }
      return unaryOperationExpr;
    }
  }
}
