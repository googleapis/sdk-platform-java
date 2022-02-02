package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigSettings;
import java.time.Duration;

public class ConfigSettingsGetBucket {

  public static void main(String[] args) throws Exception {
    configSettingsGetBucket();
  }

  public static void configSettingsGetBucket() throws Exception {
    ConfigSettings.Builder configSettingsBuilder = ConfigSettings.newBuilder();
    configSettingsBuilder
        .getBucketSettings()
        .setRetrySettings(
            configSettingsBuilder
                .getBucketSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    ConfigSettings configSettings = configSettingsBuilder.build();
  }
}
