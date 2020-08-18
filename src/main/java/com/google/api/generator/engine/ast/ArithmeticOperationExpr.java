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

import com.google.api.generator.engine.lexicon.OperatorKind;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;

@AutoValue
public abstract class ArithmeticOperationExpr implements OperationExpr {

  public abstract Expr firstExpression();

  public abstract Expr secondExpression();

  public abstract OperatorKind operatorKind();

  public abstract TypeNode type();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static ArithmeticOperationExpr concatWithExprs(Expr expr1, Expr expr2) {
    return builder()
        .setFirstExpression(expr1)
        .setSecondExpression(expr2)
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
    abstract Builder setFirstExpression(Expr expr);
    // private, required
    abstract Builder setSecondExpression(Expr expr);
    // private.
    abstract Builder setOperatorKind(OperatorKind operator);
    // private.
    abstract Builder setType(TypeNode type);

    abstract ArithmeticOperationExpr autoBuild();

    private ArithmeticOperationExpr build() {
      ArithmeticOperationExpr arithmeticOperationExpr = autoBuild();
      Expr lhsExpr = arithmeticOperationExpr.firstExpression();
      Expr rhsExpr = arithmeticOperationExpr.secondExpression();
      TypeNode lhsExprType =
          lhsExpr instanceof MethodInvocationExpr
              ? ((MethodInvocationExpr) lhsExpr).returnType()
              : lhsExpr.type();
      TypeNode rhsExprType =
          rhsExpr instanceof MethodInvocationExpr
              ? ((MethodInvocationExpr) rhsExpr).returnType()
              : rhsExpr.type();
      OperatorKind operator = arithmeticOperationExpr.operatorKind();
      // Concat operator type checks
      if (operator.equals(OperatorKind.ARITHMETIC_ADDITION)
          && arithmeticOperationExpr.type().equals(TypeNode.STRING)) {
        final String errorMsg =
            "Arithmetic operator "
                + operator
                + " can not be applied to "
                + lhsExprType.toString()
                + ", "
                + rhsExprType.toString();
        Preconditions.checkState(
            lhsExprType.equals(TypeNode.STRING) || rhsExprType.equals(TypeNode.STRING), errorMsg);
        Preconditions.checkState(
            !lhsExprType.equals(TypeNode.VOID) && !rhsExprType.equals(TypeNode.VOID), errorMsg);
      }
      return arithmeticOperationExpr;
    }
  }
}
