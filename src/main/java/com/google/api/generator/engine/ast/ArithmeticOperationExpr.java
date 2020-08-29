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
public abstract class ArithmeticOperationExpr implements OperationExpr {

  public abstract Expr lhsExpr();

  public abstract Expr rhsExpr();

  public abstract OperatorKind operatorKind();

  public abstract TypeNode type();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static ArithmeticOperationExpr concatWithExprs(Expr expr1, Expr expr2) {
    return builder()
        .setLhsExpr(expr1)
        .setRhsExpr(expr2)
        .setOperatorKind(OperatorKind.ARITHMETIC_ADDITION)
        .setType(TypeNode.STRING)
        .build();
  }

  private static Builder builder() {
    return new AutoValue_ArithmeticOperationExpr.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    // private, required
    abstract Builder setLhsExpr(Expr expr);
    // private, required
    abstract Builder setRhsExpr(Expr expr);
    // private.
    abstract Builder setOperatorKind(OperatorKind operator);
    // private.
    abstract Builder setType(TypeNode type);

    abstract ArithmeticOperationExpr autoBuild();

    private ArithmeticOperationExpr build() {
      ArithmeticOperationExpr arithmeticOperationExpr = autoBuild();
      Expr lhsExpr = arithmeticOperationExpr.lhsExpr();
      Expr rhsExpr = arithmeticOperationExpr.rhsExpr();
      TypeNode lhsExprType = lhsExpr.type();
      TypeNode rhsExprType = rhsExpr.type();
      OperatorKind operator = arithmeticOperationExpr.operatorKind();
      final String errorMsg =
          "Arithmetic operator "
              + operator
              + " can not be applied to "
              + lhsExprType.toString()
              + ", "
              + rhsExprType.toString();

      // None of expression should be void type
      Preconditions.checkState(
          !lhsExprType.equals(TypeNode.VOID) && !rhsExprType.equals(TypeNode.VOID), errorMsg);

      // Concat operator type checks
      if (operator.equals(OperatorKind.ARITHMETIC_ADDITION)) {
        // concat require at least one expression type is String
        Preconditions.checkState(
            lhsExprType.equals(TypeNode.STRING) || rhsExprType.equals(TypeNode.STRING), errorMsg);
      }
      return arithmeticOperationExpr;
    }
  }
}
