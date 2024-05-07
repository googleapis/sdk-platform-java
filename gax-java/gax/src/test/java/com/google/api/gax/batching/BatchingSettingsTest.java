package com.google.api.gax.batching;

import static com.google.api.gax.util.TimeConversionTestUtils.testDurationMethod;

import org.junit.Test;

public class BatchingSettingsTest {

  private final BatchingSettings.Builder SETTINGS_BUILDER = BatchingSettings.newBuilder();

  @Test
  public void testDelayThreshold() {
    testDurationMethod(
        123l,
        jt -> SETTINGS_BUILDER.setDelayThreshold(jt).build(),
        tt -> SETTINGS_BUILDER.setDelayThreshold(tt).build(),
        o -> o.getDelayThresholdDuration(),
        o -> o.getDelayThreshold());
  }
}
