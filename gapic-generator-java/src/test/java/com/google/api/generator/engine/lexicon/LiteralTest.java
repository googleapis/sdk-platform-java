package com.google.api.generator.engine.lexicon;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class LiteralTest {
  @Test
  public void booleanDetected() {
    assertThat(Literal.isBooleanLiteral("True")).isFalse();
    assertThat(Literal.isBooleanLiteral("true")).isTrue();
  }

  @Test
  public void nullLDetected() {
    assertThat(Literal.isNullLiteral("NULL")).isFalse();
    assertThat(Literal.isNullLiteral("null")).isTrue();
    assertThat(Literal.isNullLiteral("null_asdf")).isFalse();
    assertThat(Literal.isNullLiteral("1null")).isFalse();
  }

  @Test
  public void integerDetected() {
    assertThat(Literal.isIntegerLiteral("a123")).isFalse();
    assertThat(Literal.isIntegerLiteral("123")).isTrue();
    assertThat(Literal.isIntegerLiteral("123L")).isFalse();
    assertThat(Literal.isIntegerLiteral("123r")).isFalse();
    assertThat(Literal.isIntegerLiteral("123e2")).isFalse();
  }

  @Test
  public void longDetected() {
    assertThat(Literal.isLongLiteral("123")).isTrue();
    assertThat(Literal.isLongLiteral("123L")).isTrue();
    assertThat(Literal.isLongLiteral("123l")).isTrue();
    assertThat(Literal.isLongLiteral("123e")).isFalse();
  }

  @Test
  public void floatDetected() {
    assertThat(Literal.isFloatLiteral("123")).isTrue();
    assertThat(Literal.isFloatLiteral("123f")).isTrue();
    assertThat(Literal.isFloatLiteral("123.")).isFalse();
    assertThat(Literal.isFloatLiteral("123.f")).isTrue();
    assertThat(Literal.isFloatLiteral("123.F")).isTrue();
    assertThat(Literal.isFloatLiteral("123.234F")).isTrue();
    assertThat(Literal.isFloatLiteral("123.234Fe-3")).isFalse();
    assertThat(Literal.isFloatLiteral("123E2")).isFalse();
  }

  @Test
  public void doubleDetected() {
    assertThat(Literal.isDoubleLiteral("123")).isTrue();
    assertThat(Literal.isDoubleLiteral("123f")).isTrue();
    assertThat(Literal.isDoubleLiteral("123E-2")).isTrue();
    assertThat(Literal.isDoubleLiteral("123.134E-2")).isTrue();
    assertThat(Literal.isDoubleLiteral("123.E-2")).isTrue();
    assertThat(Literal.isDoubleLiteral("123e2")).isTrue();
    assertThat(Literal.isDoubleLiteral("123e")).isFalse();
    assertThat(Literal.isDoubleLiteral("123E-")).isFalse();
  }

  @Test
  public void literalDetected() {
    assertThat(Literal.isLiteral("False")).isFalse();
    assertThat(Literal.isLiteral("asdf")).isFalse();
    assertThat(Literal.isLiteral("asdf12345")).isFalse();
    assertThat(Literal.isLiteral("asdf1f")).isFalse();

    assertThat(Literal.isLiteral("false")).isTrue();
    assertThat(Literal.isLiteral("null")).isTrue();
    assertThat(Literal.isLiteral("123")).isTrue();
    assertThat(Literal.isLiteral("123E-2")).isTrue();
    assertThat(Literal.isLiteral("123.f")).isTrue();
  }
}
