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
import java.util.Objects;
import javax.annotation.Nullable;

@AutoValue
public abstract class TypeNode implements AstNode {
  static final Reference EXCEPTION_REFERENCE = ConcreteReference.withClazz(Exception.class);
  public static final Reference WILDCARD_REFERENCE =
      ConcreteReference.withClazz(ReferenceWildcard.class);

  public enum TypeKind {
    BYTE,
    SHORT,
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    BOOLEAN,
    CHAR,
    OBJECT,
    VOID
  }

  public static final TypeNode BOOLEAN = builder().setTypeKind(TypeKind.BOOLEAN).build();
  public static final TypeNode INT = builder().setTypeKind(TypeKind.INT).build();
  public static final TypeNode FLOAT = builder().setTypeKind(TypeKind.FLOAT).build();
  public static final TypeNode DOUBLE = builder().setTypeKind(TypeKind.DOUBLE).build();
  public static final TypeNode LONG = builder().setTypeKind(TypeKind.LONG).build();
  public static final TypeNode VOID = builder().setTypeKind(TypeKind.VOID).build();

  public static final TypeNode INTEGER = withReference(ConcreteReference.withClazz(Integer.class));
  public static final TypeNode NULL =
      withReference(ConcreteReference.withClazz(javax.lang.model.type.NullType.class));
  public static final TypeNode STRING = withReference(ConcreteReference.withClazz(String.class));
  public static final TypeNode VOID_OBJECT = withReference(ConcreteReference.withClazz(Void.class));

  public static final TypeNode STRING_ARRAY =
      builder()
          .setTypeKind(TypeKind.OBJECT)
          .setReference(ConcreteReference.withClazz(String.class))
          .setIsArray(true)
          .build();

  public abstract TypeKind typeKind();

  public abstract boolean isArray();

  @Nullable
  public abstract Reference reference();

  public static Builder builder() {
    return new AutoValue_TypeNode.Builder().setIsArray(false);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setTypeKind(TypeKind typeKind);

    public abstract Builder setIsArray(boolean isArray);

    public abstract Builder setReference(Reference reference);

    public abstract TypeNode build();
  }

  // TODO(miraleung): More type creation helpers to come...
  public static TypeNode withReference(Reference reference) {
    return TypeNode.builder().setTypeKind(TypeKind.OBJECT).setReference(reference).build();
  }

  public static TypeNode withExceptionClazz(Class clazz) {
    Preconditions.checkState(Exception.class.isAssignableFrom(clazz));
    return withReference(ConcreteReference.withClazz(clazz));
  }

  public static boolean isExceptionType(TypeNode type) {
    return isReferenceType(type) && EXCEPTION_REFERENCE.isAssignableFrom(type.reference());
  }

  public static boolean isReferenceType(TypeNode type) {
    return !isPrimitiveType(type.typeKind())
        && type.typeKind().equals(TypeKind.OBJECT)
        && type.reference() != null
        && !type.equals(TypeNode.NULL);
  }

  public boolean isPrimitiveType() {
    return isPrimitiveType(typeKind());
  }

  public static boolean isJavaLangObject(TypeNode type) {
    return type.reference().fullName().startsWith("java.lang");
  }

  public boolean isSupertypeOrEquals(TypeNode other) {
    return !isPrimitiveType()
        && !other.isPrimitiveType()
        && reference().isSupertypeOrEquals(other.reference());
  }

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  // Java overrides.
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof TypeNode)) {
      return false;
    }

    TypeNode type = (TypeNode) o;
    return typeKind().equals(type.typeKind())
        && (isArray() == type.isArray())
        && Objects.equals(reference(), type.reference());
  }

  @Override
  public int hashCode() {
    int hash = 17 * typeKind().hashCode() + 19 * (isArray() ? 1 : 3);
    if (reference() != null) {
      hash += 23 * reference().hashCode();
    }
    return hash;
  }

  private static TypeNode createPrimitiveType(TypeKind typeKind) {
    if (!isPrimitiveType(typeKind)) {
      throw new IllegalArgumentException("Object is not a primitive type.");
    }
    return TypeNode.builder().setTypeKind(typeKind).build();
  }

  private static TypeNode createPrimitiveArrayType(TypeKind typeKind) {
    if (typeKind.equals(TypeKind.OBJECT)) {
      throw new IllegalArgumentException("Object is not a primitive type.");
    }
    return TypeNode.builder().setTypeKind(typeKind).setIsArray(true).build();
  }

  private static boolean isPrimitiveType(TypeKind typeKind) {
    return !typeKind.equals(TypeKind.OBJECT);
  }
}
