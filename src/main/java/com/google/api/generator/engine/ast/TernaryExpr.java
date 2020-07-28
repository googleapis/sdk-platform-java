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
public abstract class TernaryExpr implements Expr {
  public abstract Expr conditionExpr();

  public abstract Expr thenExpr();

  public abstract Expr elseExpr();

  @Override
  public TypeNode type() {
    return thenExpr().type();
  }
  // This method is added because if thenExpr and elseExpr are boxed/primitive type equals,
  // for example `condition ? intVar : integerVar` thenExpr and elseExpr will have different types.
  // Boxed type can be castable/assigned to primitive type and reference type, and primitive type
  // is castable/assigned to boxed type, or other numeric primitive type.
  public TypeNode elseType() {
    return elseExpr().type();
  }

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static Builder builder() {
    return new AutoValue_TernaryExpr.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setConditionExpr(Expr conditionExpr);

    public abstract Builder setThenExpr(Expr thenExpression);

    public abstract Builder setElseExpr(Expr elseExpression);

    abstract TernaryExpr autoBuild();

    public TernaryExpr build() {
      TernaryExpr ternaryExpr = autoBuild();
      TypeNode thenType = ternaryExpr.thenExpr().type();
      TypeNode elseType = ternaryExpr.elseExpr().type();
      Preconditions.checkState(
          thenType.equals(elseType) || TypeNode.isBoxedTypeEquals(thenType, elseType),
          "Second and third expressions should have the same type.");
      Preconditions.checkState(
          ternaryExpr.conditionExpr().type().equals(TypeNode.BOOLEAN),
          "Ternary condition must be a boolean expression.");
      return ternaryExpr;
    }
  }
}
