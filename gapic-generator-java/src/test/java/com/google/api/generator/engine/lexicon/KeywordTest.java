package com.google.api.generator.engine.lexicon;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class KeywordTest {
  @Test
  public void keywordTest() {
    // Modifiers.
    assertThat(Keyword.isKeyword("static")).isTrue();
    assertThat(Keyword.isKeyword("private")).isTrue();

    // Other semantic blocks..
    assertThat(Keyword.isKeyword("throws")).isTrue();
    assertThat(Keyword.isKeyword("try")).isTrue();
    assertThat(Keyword.isKeyword("for")).isTrue();

    // Primitive types.
    assertThat(Keyword.isKeyword("int")).isTrue();
    assertThat(Keyword.isKeyword("char")).isTrue();

    // Literal values.
    assertThat(Keyword.isKeyword("false")).isFalse();
    assertThat(Keyword.isKeyword("null")).isFalse();
    assertThat(Keyword.isKeyword("asdf")).isFalse();
    assertThat(Keyword.isKeyword("12345")).isFalse();
    assertThat(3).isEqualTo(2);
  }
}
