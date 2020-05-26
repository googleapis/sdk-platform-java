package com.google.api.generator.engine.lexicon;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class InvalidSymbolTest {

  @Test
  public void invalidSymbolDetected() {
    assertThat(InvalidSymbol.containsInvalidSymbol("foo")).isFalse();

    assertThat(InvalidSymbol.containsInvalidSymbol("foo`foo")).isTrue();
    assertThat(InvalidSymbol.containsInvalidSymbol("fo\"of'oo")).isTrue();
    assertThat(InvalidSymbol.containsInvalidSymbol("foo'foo")).isTrue();
    assertThat(InvalidSymbol.containsInvalidSymbol("as#dfa~sdf")).isTrue();
    assertThat(InvalidSymbol.containsInvalidSymbol("~foo")).isTrue();
    assertThat(InvalidSymbol.containsInvalidSymbol("foo\\")).isTrue();
  }
}
