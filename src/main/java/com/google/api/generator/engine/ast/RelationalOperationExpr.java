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
public abstract class RelationalOperationExpr implements OperationExpr {

  public abstract Expr lhsExpression();

  public abstract Expr rhsExpression();

  public abstract OperatorKind operatorKind();

  public TypeNode type() {
    return TypeNode.BOOLEAN;
  }

  public static RelationalOperationExpr equalToWithExpr(Expr expr1, Expr expr2) {
    return builder()
        .setLhsExpression(expr1)
        .setRhsExpression(expr2)
        .setOperatorKind(OperatorKind.RELATIONAL_EQUAL_TO)
        .build();
  }

  public static RelationalOperationExpr notEqualToWithExpr(Expr expr1, Expr expr2) {
    return builder()
        .setLhsExpression(expr1)
        .setRhsExpression(expr2)
        .setOperatorKind(OperatorKind.RELATIONAL_NOT_EQUAL_TO)
        .build();
  }

  private static Builder builder() {
    return new AutoValue_RelationalOperationExpr.Builder();
  }

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  @AutoValue.Builder
  public abstract static class Builder {

    // private, required
    abstract Builder setLhsExpression(Expr expr);

    // private, required
    abstract Builder setRhsExpression(Expr expr);

    // private
    abstract Builder setOperatorKind(OperatorKind operator);

    public abstract RelationalOperationExpr autoBuild();

    private RelationalOperationExpr build() {
      RelationalOperationExpr relationalOperationExpr = autoBuild();
      Expr lhsExpr = relationalOperationExpr.lhsExpression();
      Expr rhsExpr = relationalOperationExpr.rhsExpression();
      TypeNode lhsExprType =
          lhsExpr instanceof MethodInvocationExpr
              ? ((MethodInvocationExpr) lhsExpr).returnType()
              : lhsExpr.type();
      TypeNode rhsExprType =
          rhsExpr instanceof MethodInvocationExpr
              ? ((MethodInvocationExpr) rhsExpr).returnType()
              : rhsExpr.type();
      OperatorKind operator = relationalOperationExpr.operatorKind();
      if (operator.equals(OperatorKind.RELATIONAL_EQUAL_TO)
          || operator.equals(OperatorKind.RELATIONAL_NOT_EQUAL_TO)) {
        final String errorMsg =
            "Relational operator "
                + operator
                + " can not be applied on "
                + lhsExprType.toString()
                + ", "
                + rhsExprType.toString();
        Preconditions.checkState(
            !lhsExprType.equals(TypeNode.VOID)
                && !rhsExprType.equals(TypeNode.VOID)
                && (lhsExprType.isBoxedTypeEquals(rhsExprType)
                    || rhsExprType.isBoxedTypeEquals(lhsExprType)
                    || TypeNode.isNumberType(lhsExprType) && TypeNode.isNumberType(rhsExprType)
                    || lhsExprType.equals(rhsExprType)
                    || !TypeNode.isBoxedType(lhsExprType)
                        && !TypeNode.isBoxedType((rhsExprType))
                        && !lhsExprType.equals(TypeNode.STRING)
                        && !rhsExprType.equals(TypeNode.STRING)
                        && TypeNode.isReferenceType(lhsExprType)
                        && TypeNode.isReferenceType(rhsExprType)),
            errorMsg);
      }
      return relationalOperationExpr;
    }
  }
}
