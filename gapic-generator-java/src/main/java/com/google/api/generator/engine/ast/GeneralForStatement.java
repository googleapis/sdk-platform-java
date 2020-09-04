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
import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class GeneralForStatement implements Statement {
  public abstract Expr initializationExpr();

  // TODO(summerji): Integrate OperationExpr here. Start by uncommenting the following section to
  // replace the localVariableExpr and maxSizeExpr getters after it.
  /*
  // Uses the same terminology as https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html.
  public abstract Expr terminationExpr();
  public abstract Expr incrementExpr();
  */

  public abstract VariableExpr localVariableExpr();

  public abstract Expr maxSizeExpr();

  public abstract ImmutableList<Statement> body();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  // Convenience wrapper.
  public static GeneralForStatement incrementWith(
      VariableExpr variableExpr, Expr maxSizeExpr, List<Statement> body) {
    // TODO(summerji): Do some integration here, in JavaWriterVisitor, in ImportWriterVisitor, and
    // add more tests.
    return builder()
        .setLocalVariableExpr(variableExpr.toBuilder().setIsDecl(false).build())
        .setMaxSizeExpr(maxSizeExpr)
        .setBody(body)
        .build();
  }

  public static Builder builder() {
    return new AutoValue_GeneralForStatement.Builder().setBody(Collections.emptyList());
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setInitializationExpr(Expr initializationExpr);

    public abstract Builder setBody(List<Statement> body);

    // Private.
    abstract Builder setLocalVariableExpr(VariableExpr variableExpr);

    abstract Builder setMaxSizeExpr(Expr maxSizeExpr);

    abstract VariableExpr localVariableExpr();

    abstract GeneralForStatement autoBuild();

    // Type-checking will be done in the sub-expressions.
    public GeneralForStatement build() {
      VariableExpr varExpr = localVariableExpr();
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
      setInitializationExpr(
          AssignmentExpr.builder()
              .setVariableExpr(varExpr.toBuilder().setIsDecl(true).build())
              .setValueExpr(
                  ValueExpr.withValue(
                      PrimitiveValue.builder().setValue("0").setType(TypeNode.INT).build()))
              .build());
      // TODO(summerji): Remove the following two lines.
      // This temporary workaround will be removed soon, so it doesn't need a test.
      setLocalVariableExpr(varExpr.toBuilder().setIsDecl(false).build());
      return autoBuild();
    }
  }
}
