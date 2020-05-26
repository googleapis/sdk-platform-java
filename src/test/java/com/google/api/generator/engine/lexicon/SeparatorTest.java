package com.google.api.generator.engine.lexicon;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class SeparatorTest {
  @Test
  public void separatorTest() {
    assertThat(Separator.containsSeparator("foo")).isFalse();

    assertThat(Separator.containsSeparator("foo.foo")).isTrue();
    assertThat(Separator.containsSeparator("foo}foo")).isTrue();
    assertThat(Separator.containsSeparator("foo{foo")).isTrue();
    assertThat(Separator.containsSeparator("as,df;a.{sdf")).isTrue();
    assertThat(Separator.containsSeparator("]foo")).isTrue();
    assertThat(Separator.containsSeparator("foo][[[]]")).isTrue();
  }
}
