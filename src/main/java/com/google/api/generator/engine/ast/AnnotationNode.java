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
public abstract class AnnotationNode implements AstNode {
  public static AnnotationNode OVERRIDE =
      AnnotationNode.builder().setType(annotationType(Override.class)).build();
  public static AnnotationNode DEPRECATED =
      AnnotationNode.builder().setType(annotationType(Deprecated.class)).build();

  private static TypeNode annotationType(Class clazz) {
    return TypeNode.withReference(ConcreteReference.withClazz(clazz));
  }

  public abstract TypeNode type();

  // TODO(unsupported): Any args that do not consist of a single string. However, this can easily be
  // extended to enable such support.
  @Nullable
  public abstract Expr descriptionExpr();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static AnnotationNode withTypeAndDescription(TypeNode type, String description) {
    return AnnotationNode.builder().setType(type).setDescription(description).build();
  }

  public static AnnotationNode withSuppressWarnings(String description) {
    return withTypeAndDescription(annotationType(SuppressWarnings.class), description);
  }

  public static AnnotationNode withType(TypeNode type) {
    return builder().setType(type).build();
  }

  public static Builder builder() {
    return new AutoValue_AnnotationNode.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setType(TypeNode type);

    public Builder setDescription(String description) {
      return setDescriptionExpr(ValueExpr.withValue(StringObjectValue.withValue(description)));
    }

    // This will never be anything other than a ValueExpr-wrapped StringObjectValue because
    // this setter is private, and called only by setDescription above.
    abstract Builder setDescriptionExpr(Expr descriptionExpr);

    abstract AnnotationNode autoBuild();

    public AnnotationNode build() {
      AnnotationNode annotation = autoBuild();
      Reference ref = annotation.type().reference();
      Preconditions.checkNotNull(ref, "Annotations must be an Object type");
      return annotation;
    }
  }
}
