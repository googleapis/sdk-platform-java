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

@AutoValue
public abstract class VariableExpr implements Expr {
  public abstract Variable variable();

  /** Variable declaration fields. */
  public abstract boolean isDecl();

  public abstract ScopeNode scope();

  public abstract boolean isStatic();

  public abstract boolean isFinal();

  public static Builder builder() {
    return new AutoValue_VariableExpr.Builder()
        .setIsDecl(false)
        .setIsFinal(false)
        .setIsStatic(false)
        .setScope(ScopeNode.LOCAL);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setVariable(Variable variable);

    public abstract Builder setIsDecl(boolean isDecl);

    public abstract Builder setScope(ScopeNode scope);

    public abstract Builder setIsStatic(boolean isStatic);

    public abstract Builder setIsFinal(boolean isFinal);

    public abstract VariableExpr build();
  }

  @Override
  public TypeNode type() {
    return TypeNode.VOID;
  }

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }
}
