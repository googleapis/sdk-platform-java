package com.google.api.gax.retrying;


import com.google.api.gax.util.ThreetenTestAutoValueOverrideClass;

@ThreetenTestAutoValueOverrideClass
public abstract class TimedAttemptSettingsTestAutoValue extends TimedAttemptSettings {

  /**
   * Custom builder with randon values used to confirm threeten overloaded methods
   */
  public static TimedAttemptSettings.Builder newBuilder() {
    return TimedAttemptSettings.newBuilder()
        .setGlobalSettings(RetrySettings.newBuilder().build())
        .setRetryDelay(java.time.Duration.ofMillis(123l))
        .setRpcTimeout(java.time.Duration.ofMillis(5000l))
        .setAttemptCount(1)
        .setFirstAttemptStartTimeNanos(123l)
        .setRandomizedRetryDelay(java.time.Duration.ofMillis(5000l));
  }
}
