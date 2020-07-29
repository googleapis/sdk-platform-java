package com.google.api.generator.engine.ast;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class ReferenceConstructorExprTest {

  @Test
  public void validReferenceConstructorExpr_thisConstructorBasic() {
    VaporReference ref =
        VaporReference.builder()
            .setName("Student")
            .setPakkage("com.google.example.examples.v1")
            .build();
    TypeNode typeNode = TypeNode.withReference(ref);
    ReferenceConstructorExpr.thisBuilder().setType(typeNode).build();
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validReferenceConstructorExpr_superConstructorBasic() {
    VaporReference ref =
        VaporReference.builder()
            .setName("Student")
            .setPakkage("com.google.example.examples.v1")
            .build();
    TypeNode typeNode = TypeNode.withReference(ref);
    ReferenceConstructorExpr.superBuilder().setType(typeNode).build();
    // No exception thrown, so we succeeded.
  }

  @Test
  public void invalidReferenceConstructorExpr_nonReferenceType() {
    assertThrows(
        IllegalStateException.class,
        () -> {
          ReferenceConstructorExpr.thisBuilder().setType(TypeNode.BOOLEAN).build();
        });
  }

  @Test
  public void invalidReferenceConstructorExpr_NullType() {
    assertThrows(
        IllegalStateException.class,
        () -> {
          ReferenceConstructorExpr.superBuilder().setType(TypeNode.NULL).build();
        });
  }
}
