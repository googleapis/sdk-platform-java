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
import java.util.ArrayList;
import java.util.List;

@AutoValue
public abstract class RelationalOperationExpr implements OperationExpr {

  public abstract Expr firstExpression();

  public abstract Expr secondExpression();

  public abstract OperatorKind operatorKind();

  public TypeNode type() {
    return TypeNode.BOOLEAN;
  }

  public static RelationalOperationExpr equalToWithExpr(Expr expr1, Expr expr2) {
    return builder()
        .setFirstExpression(expr1)
        .setSecondExpression(expr2)
        .setOperatorKind(OperatorKind.RELATIONAL_EQUAL_TO)
        .build();
  }

  public static RelationalOperationExpr notEqualToWithExpr(Expr expr1, Expr expr2) {
    return builder()
        .setFirstExpression(expr1)
        .setSecondExpression(expr2)
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
    abstract Builder setFirstExpression(Expr expr);

    // private, required
    abstract Builder setSecondExpression(Expr expr);

    // private
    abstract Builder setOperatorKind(OperatorKind operator);

    public abstract RelationalOperationExpr autoBuild();

    private RelationalOperationExpr build() {
      String a = "5";
      Character b = 'a';
      // boolean c = a != b;
      RelationalOperationExpr relationalOperationExpr = autoBuild();
      List h = new ArrayList();
      boolean c = relationalOperationExpr != h;
      Expr firstExpr = relationalOperationExpr.firstExpression();
      Expr secondExpr = relationalOperationExpr.secondExpression();
      TypeNode firstExprType =
          firstExpr instanceof MethodInvocationExpr
              ? ((MethodInvocationExpr) firstExpr).returnType()
              : firstExpr.type();
      TypeNode secondExprType =
          secondExpr instanceof MethodInvocationExpr
              ? ((MethodInvocationExpr) secondExpr).returnType()
              : secondExpr.type();
      OperatorKind operator = relationalOperationExpr.operatorKind();
      if (operator.equals(OperatorKind.RELATIONAL_EQUAL_TO)
          || operator.equals(OperatorKind.RELATIONAL_NOT_EQUAL_TO)) {
        final String errorMsg =
            "Relational operator "
                + operator
                + " can not be applied on "
                + firstExprType.toString()
                + ", "
                + secondExprType.toString();
        Preconditions.checkState(
            !firstExprType.equals(TypeNode.VOID)
                && !secondExprType.equals(TypeNode.VOID)
                && (firstExprType.isBoxedTypeEquals(secondExprType)
                    || secondExprType.isBoxedTypeEquals(firstExprType)
                    || TypeNode.isNumberType(firstExprType) && TypeNode.isNumberType(secondExprType)
                    || firstExprType.equals(secondExprType)
                    || !TypeNode.isBoxedType(firstExprType)
                        && !TypeNode.isBoxedType((secondExprType))
                        && !firstExprType.equals(TypeNode.STRING)
                        && !secondExprType.equals(TypeNode.STRING)
                        && TypeNode.isReferenceType(firstExprType)
                        && TypeNode.isReferenceType(secondExprType)),
            errorMsg);
      }
      return relationalOperationExpr;
    }
  }
}
