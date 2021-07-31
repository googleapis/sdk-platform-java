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
  public abstract Expr messageExpr();

  @Nullable
  public abstract Expr causeExpr();

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

    public Builder setMessageExpr(String message) {
      return setMessageExpr(ValueExpr.withValue(StringObjectValue.withValue(message)));
    }

    public abstract Builder setMessageExpr(Expr expr);

    public abstract Builder setCauseExpr(Expr expr);

    // Private.
    abstract TypeNode type();

    abstract Expr messageExpr();

    abstract Expr causeExpr();

    abstract ThrowExpr autoBuild();

    public ThrowExpr build() {
      Preconditions.checkState(
          TypeNode.isExceptionType(type()),
          String.format("Type %s must be an exception type", type()));

      if (messageExpr() != null) {
        Preconditions.checkState(
            messageExpr().type().equals(TypeNode.STRING),
            String.format("Message expression type must be a string for exception %s", type()));
      }

      if (causeExpr() != null) {
        Preconditions.checkState(
            TypeNode.THROWABLE.reference().isSupertypeOrEquals(causeExpr().type().reference()),
            String.format(
                "Cause expression type must be a subclass of Throwable, but found %s",
                causeExpr().type()));
      }

      return autoBuild();
    }
  }
}
