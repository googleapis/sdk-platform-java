package com.google.api.gax.retrying;

import com.google.api.gax.util.ThreetenTestAutoValueOverrideClass;

@ThreetenTestAutoValueOverrideClass
public abstract class RetrySettingsTestAutoValue extends RetrySettings {

  public static RetrySettings.Builder newBuilder() {
    return RetrySettings.newBuilder()
        .setMaxRpcTimeout(java.time.Duration.ofMillis(999999l))
        .setMaxRetryDelay(java.time.Duration.ofMillis(999999l));
  }
}
