package com.google.api.generator.engine.basics;

import com.google.api.generator.engine.ast.AstNode;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public class Type implements AstNode {
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

  private final TypeKind typeKind;
  private final boolean isArray;
  @Nullable private final Reference reference;

  private Type(@NotNull TypeKind typeKind, @Nullable Reference reference, boolean isArray) {
    this.typeKind = typeKind;
    this.reference = reference;
    this.isArray = isArray;
  }

  // TODO(miraleung): More type creation helpers to come...
  public static Type createReferenceType(Reference reference) {
    return new Type(TypeKind.OBJECT, reference, false);
  }

  public static Type createReferenceArrayType(Reference reference) {
    return new Type(TypeKind.OBJECT, reference, true);
  }

  public static Type createIntType() {
    return createPrimitiveType(TypeKind.INT);
  }

  public static Type createByteArrayType() {
    return createPrimitiveArrayType(TypeKind.BYTE);
  }

  public TypeKind typeKind() {
    return typeKind;
  }

  // AstNode overrides.
  @Override
  public String write() {
    StringBuilder generatedCodeBuilder = new StringBuilder();
    if (isPrimitiveType(typeKind)) {
      generatedCodeBuilder.append(typeKind.toString().toLowerCase());
    } else {
      // A null pointer exception will be thrown if reference is null, which is WAI.
      generatedCodeBuilder.append(reference.write());
    }

    if (isArray) {
      generatedCodeBuilder.append("[]");
    }
    return generatedCodeBuilder.toString();
  }

  // Java overrides.
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Type)) {
      return false;
    }

    Type type = (Type) o;
    return typeKind.equals(type.typeKind)
        && (isArray == type.isArray)
        && Objects.equals(reference, type.reference);
  }

  private static Type createPrimitiveType(TypeKind typeKind) {
    if (!isPrimitiveType(typeKind)) {
      throw new IllegalArgumentException("Object is not a primitive type.");
    }
    return new Type(typeKind, null, false);
  }

  private static Type createPrimitiveArrayType(TypeKind typeKind) {
    if (typeKind.equals(TypeKind.OBJECT)) {
      throw new IllegalArgumentException("Object is not a primitive type.");
    }
    return new Type(typeKind, null, true);
  }

  private static boolean isPrimitiveType(TypeKind typeKind) {
    return !typeKind.equals(TypeKind.OBJECT);
  }
}
