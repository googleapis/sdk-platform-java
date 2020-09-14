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
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;

@AutoValue
public abstract class TypeNode implements AstNode {
  static final Reference EXCEPTION_REFERENCE = ConcreteReference.withClazz(Exception.class);
  public static final Reference WILDCARD_REFERENCE = ConcreteReference.wildcard();

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
  public static final TypeNode BYTE = builder().setTypeKind(TypeKind.BYTE).build();
  public static final TypeNode CHAR = builder().setTypeKind(TypeKind.CHAR).build();
  public static final TypeNode DOUBLE = builder().setTypeKind(TypeKind.DOUBLE).build();
  public static final TypeNode FLOAT = builder().setTypeKind(TypeKind.FLOAT).build();
  public static final TypeNode INT = builder().setTypeKind(TypeKind.INT).build();
  public static final TypeNode LONG = builder().setTypeKind(TypeKind.LONG).build();
  public static final TypeNode SHORT = builder().setTypeKind(TypeKind.SHORT).build();

  public static final TypeNode BOOLEAN_OBJECT =
      withReference(ConcreteReference.withClazz(Boolean.class));
  public static final TypeNode BYTE_OBJECT = withReference(ConcreteReference.withClazz(Byte.class));
  public static final TypeNode CHAR_OBJECT =
      withReference(ConcreteReference.withClazz(Character.class));
  public static final TypeNode DOUBLE_OBJECT =
      withReference(ConcreteReference.withClazz(Double.class));
  public static final TypeNode FLOAT_OBJECT =
      withReference(ConcreteReference.withClazz(Float.class));
  public static final TypeNode INT_OBJECT =
      withReference(ConcreteReference.withClazz(Integer.class));
  public static final TypeNode LONG_OBJECT = withReference(ConcreteReference.withClazz(Long.class));
  public static final TypeNode SHORT_OBJECT =
      withReference(ConcreteReference.withClazz(Short.class));

  private static final Map<TypeNode, TypeNode> BOXED_TYPE_MAP = createBoxedTypeMap();

  public static final TypeNode VOID = builder().setTypeKind(TypeKind.VOID).build();

  public static final TypeNode NULL =
      withReference(ConcreteReference.withClazz(javax.lang.model.type.NullType.class));
  public static final TypeNode OBJECT = withReference(ConcreteReference.withClazz(Object.class));
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

    // Private.
    abstract Reference reference();

    abstract TypeNode autoBuild();

    public TypeNode build() {
      if (reference() != null) {
        // Disallow top-level wildcard references.
        Preconditions.checkState(
            !reference().isWildcard(),
            String.format(
                "The top-level referenece in a type cannot be a wildcard, found %s",
                reference().name()));
      }
      return autoBuild();
    }
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

  public static boolean isNumericType(TypeNode type) {
    return type.equals(TypeNode.INT)
        || type.equals(TypeNode.LONG)
        || type.equals(TypeNode.DOUBLE)
        || type.equals(TypeNode.SHORT)
        || type.equals(TypeNode.FLOAT)
        || type.equals(TypeNode.CHAR);
  }

  public static boolean isBoxedType(TypeNode type) {
    return isReferenceType(type) && BOXED_TYPE_MAP.containsValue(type);
  }

  public boolean isPrimitiveType() {
    return isPrimitiveType(typeKind());
  }

  public boolean isSupertypeOrEquals(TypeNode other) {
    boolean oneTypeIsNull = this.equals(TypeNode.NULL) ^ other.equals(TypeNode.NULL);
    return !isPrimitiveType()
        && !other.isPrimitiveType()
        && isArray() == other.isArray()
        && (reference().isSupertypeOrEquals(other.reference()) || oneTypeIsNull);
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
    return strictEquals(type) || isBoxedTypeEquals(type);
  }

  @Override
  public int hashCode() {
    int hash = 17 * typeKind().hashCode() + 19 * (isArray() ? 1 : 3);
    if (reference() != null) {
      hash += 23 * reference().hashCode();
    }
    return hash;
  }

  @VisibleForTesting
  boolean strictEquals(TypeNode other) {
    return typeKind().equals(other.typeKind())
        && (isArray() == other.isArray())
        && Objects.equals(reference(), other.reference());
  }

  @VisibleForTesting
  boolean isBoxedTypeEquals(TypeNode other) {
    // If both types are primitive/reference type, return false.
    // Array of boxed/primitive type is not considered equal.
    if (isArray() || other.isArray()) {
      return false;
    }
    if (other.isPrimitiveType()) {
      return Objects.equals(this, BOXED_TYPE_MAP.get(other));
    }
    return Objects.equals(other, BOXED_TYPE_MAP.get(this));
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

  private static Map<TypeNode, TypeNode> createBoxedTypeMap() {
    Map<TypeNode, TypeNode> map = new HashMap<>();
    map.put(TypeNode.INT, TypeNode.INT_OBJECT);
    map.put(TypeNode.BOOLEAN, TypeNode.BOOLEAN_OBJECT);
    map.put(TypeNode.BYTE, TypeNode.BYTE_OBJECT);
    map.put(TypeNode.CHAR, TypeNode.CHAR_OBJECT);
    map.put(TypeNode.FLOAT, TypeNode.FLOAT_OBJECT);
    map.put(TypeNode.LONG, TypeNode.LONG_OBJECT);
    map.put(TypeNode.SHORT, TypeNode.SHORT_OBJECT);
    map.put(TypeNode.DOUBLE, TypeNode.DOUBLE_OBJECT);
    return map;
  }
}
