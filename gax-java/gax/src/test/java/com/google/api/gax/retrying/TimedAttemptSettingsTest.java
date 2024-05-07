package com.google.api.gax.retrying;

import static com.google.api.gax.util.TimeConversionTestUtils.testDurationMethod;

import org.junit.Test;

public class TimedAttemptSettingsTest {

  private static final TimedAttemptSettings.Builder SETTINGS_BUILDER =
      TimedAttemptSettings.newBuilder()
          .setGlobalSettings(RetrySettings.newBuilder().build())
          .setRpcTimeout(java.time.Duration.ofMillis(5000l))
          .setRandomizedRetryDelay(java.time.Duration.ofMillis(5000l))
          .setAttemptCount(123)
          .setFirstAttemptStartTimeNanos(123l);

  @Test
  public void testRetryDelay() {
    testDurationMethod(
        123l,
        jt -> SETTINGS_BUILDER.setRetryDelay(jt).build(),
        tt -> SETTINGS_BUILDER.setRetryDelay(tt).build(),
        o -> o.getRetryDelayDuration(),
        o -> o.getRetryDelay());
  }

  @Test
  public void testRandomizedRetryDelay() {
    testDurationMethod(
        123l,
        jt -> SETTINGS_BUILDER.setRandomizedRetryDelay(jt).build(),
        tt -> SETTINGS_BUILDER.setRandomizedRetryDelay(tt).build(),
        o -> o.getRandomizedRetryDelayDuration(),
        o -> o.getRandomizedRetryDelay());
  }

  @Test
  public void testRpcTimeout() {
    testDurationMethod(
        123l,
        jt -> SETTINGS_BUILDER.setRpcTimeout(jt).build(),
        tt -> SETTINGS_BUILDER.setRpcTimeout(tt).build(),
        o -> o.getRpcTimeoutDuration(),
        o -> o.getRpcTimeout());
  }
}
