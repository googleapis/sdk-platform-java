package com.google.api.gax.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class TimeConversionUtilsTest {

  final org.threeten.bp.Duration ttDuration = org.threeten.bp.Duration.ofMillis(123);
  final org.threeten.bp.Instant ttInstant = org.threeten.bp.Instant.ofEpochMilli(123);
  final java.time.Duration jtDuration = java.time.Duration.ofMillis(345);
  final java.time.Instant jtInstant = java.time.Instant.ofEpochMilli(345);

  @Test
  void testToJavaTimeDuration_validInput_succeeds() {
    assertEquals(
        ttDuration.toMillis(), TimeConversionUtils.toJavaTimeDuration(ttDuration).toMillis());
    assertNull(TimeConversionUtils.toJavaTimeDuration(null));
  }

  @Test
  void testToThreetenTimeDuration_validInput_succeeds() {
    assertEquals(
        jtDuration.toMillis(), TimeConversionUtils.toThreetenDuration(jtDuration).toMillis());
    assertNull(TimeConversionUtils.toThreetenDuration(null));
  }

  @Test
  void testToJavaTimeInstant_validInput_succeeds() {
    assertEquals(
        ttInstant.toEpochMilli(), TimeConversionUtils.toJavaTimeInstant(ttInstant).toEpochMilli());
    assertNull(TimeConversionUtils.toJavaTimeInstant(null));
  }

  @Test
  void testToThreetenTimeInstant_validInput_succeeds() {
    assertEquals(
        jtInstant.toEpochMilli(), TimeConversionUtils.toThreetenInstant(jtInstant).toEpochMilli());
    assertNull(TimeConversionUtils.toThreetenInstant(null));
  }
}
