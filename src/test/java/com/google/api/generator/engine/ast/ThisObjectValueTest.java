package com.google.api.generator.engine.ast;

import static org.junit.Assert.assertThrows;

import org.junit.Assert;
import org.junit.Test;

public class ThisObjectValueTest {
  @Test
  public void testThisObjectValueTest() {
    VaporReference ref =
        VaporReference.builder()
            .setName("Student")
            .setPakkage("com.google.example.examples.v1")
            .build();
    TypeNode typeNode = TypeNode.withReference(ref);
    ThisObjectValue thisObjectValue = ThisObjectValue.withType(TypeNode.withReference(ref));

    Assert.assertEquals(thisObjectValue.value(), "this");
    Assert.assertEquals(thisObjectValue.type(), typeNode);
  }

  @Test
  public void testThisObjectValueTest_invalid() {
    ClassDefinition classDefinition =
        ClassDefinition.builder()
            .setPackageString("com.google.example.library.v1.stub")
            .setName("LibraryServiceStub")
            .setScope(ScopeNode.PUBLIC)
            .build();
    ConcreteReference ref = ConcreteReference.withClazz(classDefinition.getClass());
    TypeNode typeNode = TypeNode.withReference(ref);
    ThisObjectValue thisObjectValue = ThisObjectValue.withType(TypeNode.withReference(ref));
    Assert.assertEquals(thisObjectValue.value(), "this");
    Assert.assertEquals(thisObjectValue.type(), typeNode);
  }

  @Test
  public void testThisObjectValueTest_invalid_type() {
    ConcreteReference ref = ConcreteReference.withClazz(Integer.class);
    assertThrows(
        IllegalStateException.class,
        () -> {
          ThisObjectValue.withType(TypeNode.withReference(ref));
        });
    assertThrows(
        IllegalStateException.class,
        () -> {
          ThisObjectValue.withType(TypeNode.BOOLEAN);
        });
  }
}
