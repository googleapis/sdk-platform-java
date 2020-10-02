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

import autovalue.shaded.com.google$.common.annotations.$VisibleForTesting;
import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class GeneralForStatement implements Statement {
  private int i  = 0;
  for (i = 0; i < 10; i++) {}

  void foobar() {
    for (i = 0; i < 10; i++) {
      System.out.println("i = " + i);
    }
  }
  public abstract AssignmentExpr initializationExpr();

  public abstract Expr terminationExpr();

  public abstract Expr incrementExpr();

  public abstract ImmutableList<Statement> body();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  // incrementWith is convenience wrapper to generate index-base for-loop with lower and upper bound
  // and post increment on variable, like code in ```for (int i = 0; i < getMax(); i++) {..}```
  // TODO (unsupported): Add more convenience wrapper for the future generation needs.
  public static GeneralForStatement incrementWith(
      VariableExpr localVariableExpr,
      ValueExpr initialValueExpr,
      Expr maxSizeExpr,
      List<Statement> body) {
    return builder()
        .setInitializationExpr(
            AssignmentExpr.builder()
                .setVariableExpr(localVariableExpr)
                .setValueExpr(initialValueExpr)
                .build())
        .setTerminationExpr(
            RelationalOperationExpr.lessThanWithExprs(
                localVariableExpr.toBuilder().setIsDecl(false).build(), maxSizeExpr))
        .setIncrementExpr(
            UnaryOperationExpr.postfixIncrementWithExpr(
                localVariableExpr.toBuilder().setIsDecl(false).build()))
        .setBody(body)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_GeneralForStatement.Builder().setBody(Collections.emptyList());
  }

  @AutoValue.Builder
  abstract static class Builder {
    // Private setter.
    @$VisibleForTesting
    abstract Builder setInitializationExpr(AssignmentExpr initializationExpr);
    // Private setter.
    @$VisibleForTesting
    abstract Builder setTerminationExpr(Expr terminationExpr);
    // Private setter.
    @$VisibleForTesting
    abstract Builder setIncrementExpr(Expr incrementExpr);
    // Private setter.
    @$VisibleForTesting
    abstract Builder setBody(List<Statement> body);

    abstract GeneralForStatement autoBuild();

    // Type-checking will be done in the sub-expressions.
    @$VisibleForTesting
    public GeneralForStatement build() {
      GeneralForStatement generalForStatement = autoBuild();
      AssignmentExpr initializationExpr = generalForStatement.initializationExpr();
      Expr terminationExpr = generalForStatement.terminationExpr();
      Expr incrementExpr = generalForStatement.incrementExpr();
      VariableExpr varExpr = initializationExpr.variableExpr();
      Preconditions.checkState(
          varExpr.scope().equals(ScopeNode.LOCAL),
          String.format(
              "Variable %s in a general for-loop cannot have a non-local scope",
              varExpr.variable().identifier().name()));
      Preconditions.checkState(
          !varExpr.isStatic() && !varExpr.isFinal(),
          String.format(
              "Variable %s in a general for-loop cannot be static or final",
              varExpr.variable().identifier().name()));
      Preconditions.checkState(
          terminationExpr.type().equals(TypeNode.BOOLEAN),
          "Terminal expression %s must be boolean-type expression.");
      Preconditions.checkState(
          (incrementExpr instanceof MethodInvocationExpr)
              || (incrementExpr instanceof AssignmentExpr)
              || (incrementExpr instanceof AssignmentOperationExpr)
              // TODO(unsupported): Currently we only support postIncrement (i++), please add
              // postDecrement, prefixIncrement, prefixIncrement if needed.
              || (incrementExpr instanceof UnaryOperationExpr
                  && ((UnaryOperationExpr) incrementExpr).isPostfixIncrement()),
          "Increment expression %s must be either a method invocation, assignment, or unary post-fix operation expression.");
      return autoBuild();
    }
  }
}
