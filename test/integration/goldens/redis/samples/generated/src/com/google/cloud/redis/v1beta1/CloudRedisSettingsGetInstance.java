package com.google.cloud.redis.v1beta1.samples;

import com.google.cloud.redis.v1beta1.CloudRedisSettings;
import java.time.Duration;

public class CloudRedisSettingsGetInstance {

  public static void main(String[] args) throws Exception {
    cloudRedisSettingsGetInstance();
  }

  public static void cloudRedisSettingsGetInstance() throws Exception {
    CloudRedisSettings.Builder cloudRedisSettingsBuilder = CloudRedisSettings.newBuilder();
    cloudRedisSettingsBuilder
        .getInstanceSettings()
        .setRetrySettings(
            cloudRedisSettingsBuilder
                .getInstanceSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    CloudRedisSettings cloudRedisSettings = cloudRedisSettingsBuilder.build();
  }
}
