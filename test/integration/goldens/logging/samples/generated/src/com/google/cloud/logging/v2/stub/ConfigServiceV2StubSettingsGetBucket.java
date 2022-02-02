package com.google.cloud.logging.v2.stub.samples;

import com.google.cloud.logging.v2.stub.ConfigServiceV2StubSettings;
import java.time.Duration;

public class ConfigServiceV2StubSettingsGetBucket {

  public static void main(String[] args) throws Exception {
    configServiceV2StubSettingsGetBucket();
  }

  public static void configServiceV2StubSettingsGetBucket() throws Exception {
    ConfigServiceV2StubSettings.Builder configSettingsBuilder =
        ConfigServiceV2StubSettings.newBuilder();
    configSettingsBuilder
        .getBucketSettings()
        .setRetrySettings(
            configSettingsBuilder
                .getBucketSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    ConfigServiceV2StubSettings configSettings = configSettingsBuilder.build();
  }
}
