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
import javax.annotation.Nullable;

@AutoValue
public abstract class ThrowExpr implements Expr {
  // TODO(miraleung): Refactor with StringObjectValue and possibly with NewObjectExpr.

  @Override
  public abstract TypeNode type();

  @Nullable
  public abstract String message();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static Builder builder() {
    return new AutoValue_ThrowExpr.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setType(TypeNode type);

    public abstract Builder setMessage(String message);

    // Private.
    abstract ThrowExpr autoBuild();

    public ThrowExpr build() {
      // TODO(miraleung): Escaping message() and the corresponding tests will be done when we switch
      // to StringObjectValue.
      ThrowExpr throwExpr = autoBuild();
      Preconditions.checkState(
          TypeNode.isExceptionType(throwExpr.type()),
          String.format("Type %s must be an exception type", throwExpr.type()));
      return throwExpr;
    }
  }
}
