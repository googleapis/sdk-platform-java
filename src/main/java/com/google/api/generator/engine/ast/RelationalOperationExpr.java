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

<<<<<<< HEAD
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
=======
import com.google.api.generator.engine.lexicon.OperatorKind;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
>>>>>>> 7bb0f2b43a3139b8cf31c4f55287d441f597e2aa

@AutoValue
public abstract class RelationalOperationExpr implements OperationExpr {

<<<<<<< HEAD
  public abstract Expr lhsExpression();

  public abstract Expr rhsExpression();
=======
  public abstract Expr firstExpression();

  public abstract Expr secondExpression();
>>>>>>> 7bb0f2b43a3139b8cf31c4f55287d441f597e2aa

  public abstract OperatorKind operatorKind();

  public TypeNode type() {
    return TypeNode.BOOLEAN;
  }

  public static RelationalOperationExpr equalToWithExpr(Expr expr1, Expr expr2) {
    return builder()
<<<<<<< HEAD
        .setLhsExpression(expr1)
        .setRhsExpression(expr2)
=======
        .setFirstExpression(expr1)
        .setSecondExpression(expr2)
>>>>>>> 7bb0f2b43a3139b8cf31c4f55287d441f597e2aa
        .setOperatorKind(OperatorKind.RELATIONAL_EQUAL_TO)
        .build();
  }

  public static RelationalOperationExpr notEqualToWithExpr(Expr expr1, Expr expr2) {
    return builder()
<<<<<<< HEAD
        .setLhsExpression(expr1)
        .setRhsExpression(expr2)
=======
        .setFirstExpression(expr1)
        .setSecondExpression(expr2)
>>>>>>> 7bb0f2b43a3139b8cf31c4f55287d441f597e2aa
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
<<<<<<< HEAD
    abstract Builder setLhsExpression(Expr expr);

    // private, required
    abstract Builder setRhsExpression(Expr expr);
=======
    abstract Builder setFirstExpression(Expr expr);

    // private, required
    abstract Builder setSecondExpression(Expr expr);
>>>>>>> 7bb0f2b43a3139b8cf31c4f55287d441f597e2aa

    // private
    abstract Builder setOperatorKind(OperatorKind operator);

    public abstract RelationalOperationExpr autoBuild();

    private RelationalOperationExpr build() {
<<<<<<< HEAD
      RelationalOperationExpr relationalOperationExpr = autoBuild();
      Expr lhsExpr = relationalOperationExpr.lhsExpression();
      Expr rhsExpr = relationalOperationExpr.rhsExpression();
      TypeNode lhsExprType = lhsExpr.type();
      TypeNode rhsExprType = rhsExpr.type();
      OperatorKind operator = relationalOperationExpr.operatorKind();
      final String errorMsg =
          "Relational operator "
              + operator
              + " can not be applied on "
              + lhsExprType.toString()
              + ", "
              + rhsExprType.toString();
      // operators can not be applied on void type
      Preconditions.checkState(
          !lhsExprType.equals(TypeNode.VOID)
              && !rhsExprType.equals(TypeNode.VOID), errorMsg);
      // type check for equal to (==) and not equal to (!=)
      if (operator.equals(OperatorKind.RELATIONAL_EQUAL_TO)
          || operator.equals(OperatorKind.RELATIONAL_NOT_EQUAL_TO)) {
        // lhsExpr type is array, rhsExpr type should be array and matched type or null
        if (lhsExprType.isArray()) {
          Preconditions.checkState((rhsExprType.isArray() && lhsExprType.typeKind().equals(rhsExprType.typeKind())) || rhsExprType.equals(TypeNode.NULL), errorMsg);
        }
        // lhsExpr type is numerical type, rhsExpr type should be any numerical or any Boxed numerical
        if (!lhsExprType.isArray() && lhsExprType.isNumericType()) {
          Preconditions.checkState(!rhsExprType.isArray() && (rhsExprType.isNumericType() || TypeNode.isNumericBoxedType(rhsExprType)), errorMsg);
        }
        // lhsExpr type is boolean type, rhsExpr type should be boolean or its boxed type
        if (!lhsExprType.isArray() && lhsExprType.equals(TypeNode.BOOLEAN)) {
          Preconditions.checkState(!rhsExprType.isArray() && rhsExprType.equals(lhsExprType), errorMsg);
        }
        // lhsExpr type is reference type, rhsExpr type should be matched referenced type or null or Object
        if (!lhsExprType.isArray() && TypeNode.isReferenceType(lhsExprType) && !lhsExprType.equals(TypeNode.OBJECT)) {
          Preconditions.checkState(!rhsExprType.isArray() && (rhsExprType.equals(lhsExprType) || rhsExprType.equals(TypeNode.NULL) || rhsExprType.equals(TypeNode.OBJECT)),errorMsg);
        }
        // lhsExpr type is Object or null type, rhsExpr type should be any reference type or null or any Boxed type
        if (!lhsExprType.isArray() && (lhsExprType.equals(TypeNode.OBJECT) || lhsExprType.equals(TypeNode.NULL))) {
          Preconditions.checkState((!rhsExprType.isArray() && (TypeNode.isReferenceType(rhsExprType) || TypeNode.isBoxedType(lhsExprType))) || rhsExprType.equals(TypeNode.NULL), errorMsg);
        }
        // lhsExpr type is boxed type, rhsExpr type should be matched boxed type or its unboxing type or null
        if (!lhsExprType.isArray() && (TypeNode.isBoxedType(lhsExprType))) {
          Preconditions.checkState((!rhsExprType.isArray() && lhsExprType.equals(rhsExprType)) || rhsExprType.equals(TypeNode.NULL), errorMsg);
        }
=======
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
>>>>>>> 7bb0f2b43a3139b8cf31c4f55287d441f597e2aa
      }
      return relationalOperationExpr;
    }
  }
}
