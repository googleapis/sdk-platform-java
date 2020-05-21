package com.google.api.generator.engine.basics;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class IdentifierTest {
  @Test
  public void foobarBasicTest() {
    assertThat(Identifier.foobar()).isEqualTo(3);
  }
}
