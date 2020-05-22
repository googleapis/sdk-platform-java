package com.google.api.generator.engine.lexicon;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class OperatorTest {

  @Test
  public void separatorTest() {
    assertThat(Operator.containsOperator("foo")).isFalse();

    assertThat(Operator.containsOperator("foo+foo")).isTrue();
    assertThat(Operator.containsOperator("fo:of-oo")).isTrue();
    assertThat(Operator.containsOperator("foo?foo")).isTrue();
    assertThat(Operator.containsOperator("as>df<a|{sdf")).isTrue();
    assertThat(Operator.containsOperator("!=foo")).isTrue();
    assertThat(Operator.containsOperator("fo%o")).isTrue();
    assertThat(Operator.containsOperator("foo|&")).isTrue();
  }
}
