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
import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class NewObjectExpr implements Expr {
  public abstract TypeNode type();

  public abstract List<Expr> arguments();

  public abstract boolean isGeneric();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static Builder builder() {
    return new AutoValue_NewObjectExpr.Builder()
        .setArguments(Collections.emptyList())
        .setIsGeneric(false);
  }

  public static Builder genericBuilder() {
    return new AutoValue_NewObjectExpr.Builder()
        .setArguments(Collections.emptyList())
        .setIsGeneric(true);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setType(TypeNode type);

    public abstract Builder setArguments(List<Expr> arguments);

    // Private.
    abstract Builder setIsGeneric(boolean isGeneric);

    abstract NewObjectExpr autoBuild();

    public NewObjectExpr build() {
      NewObjectExpr newObjectExpr = autoBuild();
      // Check the object is reference type.
      Preconditions.checkState(
          TypeNode.isReferenceType(newObjectExpr.type()), "New Object must be reference types.");
      // Check if there is a conflict between isGeneric() setting and generics() setting.
      boolean noGenerics = newObjectExpr.type().reference().generics().isEmpty();
      Preconditions.checkState(
          (newObjectExpr.isGeneric() || noGenerics),
          "Please call genericBuilder() if the new object is generic, else call builder() to build"
              + " the object.");
      return newObjectExpr;
    }
  }
}
