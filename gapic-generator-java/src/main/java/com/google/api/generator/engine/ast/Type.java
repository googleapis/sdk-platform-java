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
import java.util.Objects;
import javax.annotation.Nullable;

@AutoValue
public abstract class Type implements AstNode {
  public enum TypeKind {
    BYTE,
    SHORT,
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    BOOLEAN,
    CHAR,
    OBJECT
  }

  public abstract TypeKind typeKind();

  public abstract boolean isArray();

  @Nullable
  public abstract Reference reference();

  public static Builder builder() {
    return new AutoValue_Type.Builder().setIsArray(false);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setTypeKind(TypeKind typeKind);

    public abstract Builder setIsArray(boolean isArray);

    public abstract Builder setReference(Reference reference);

    public abstract Type build();
  }

  // TODO(miraleung): More type creation helpers to come...
  public static Type createReferenceType(Reference reference) {
    return Type.builder().setTypeKind(TypeKind.OBJECT).setReference(reference).build();
  }

  public static Type createReferenceArrayType(Reference reference) {
    return Type.builder()
        .setTypeKind(TypeKind.OBJECT)
        .setReference(reference)
        .setIsArray(true)
        .build();
  }

  public static Type createIntType() {
    return createPrimitiveType(TypeKind.INT);
  }

  public static Type createByteArrayType() {
    return createPrimitiveArrayType(TypeKind.BYTE);
  }

  public boolean isPrimitiveType() {
    return isPrimitiveType(typeKind());
  }

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  // Java overrides.
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Type)) {
      return false;
    }

    Type type = (Type) o;
    return typeKind().equals(type.typeKind())
        && (isArray() == type.isArray())
        && Objects.equals(reference(), type.reference());
  }

  private static Type createPrimitiveType(TypeKind typeKind) {
    if (!isPrimitiveType(typeKind)) {
      throw new IllegalArgumentException("Object is not a primitive type.");
    }
    return Type.builder().setTypeKind(typeKind).build();
  }

  private static Type createPrimitiveArrayType(TypeKind typeKind) {
    if (typeKind.equals(TypeKind.OBJECT)) {
      throw new IllegalArgumentException("Object is not a primitive type.");
    }
    return Type.builder().setTypeKind(typeKind).setIsArray(true).build();
  }

  private static boolean isPrimitiveType(TypeKind typeKind) {
    return !typeKind.equals(TypeKind.OBJECT);
  }
}
