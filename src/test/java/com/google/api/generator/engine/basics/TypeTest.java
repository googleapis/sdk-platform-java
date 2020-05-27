package com.google.api.generator.engine.basics;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class TypeTest {
  @Test
  public void writePrimitiveType() {
    Type intType = Type.createIntType();
    assertThat(intType).isNotNull();
    assertThat(intType.write()).isEqualTo("int");
  }

  @Test
  public void writePrimitiveArrayType() {
    assertThat(Type.createByteArrayType().write()).isEqualTo("byte[]");
  }
}
