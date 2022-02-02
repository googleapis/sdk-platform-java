package com.google.cloud.redis.v1beta1.stub.samples;

import com.google.cloud.redis.v1beta1.stub.CloudRedisStubSettings;
import java.time.Duration;

public class CloudRedisStubSettingsGetInstance {

  public static void main(String[] args) throws Exception {
    cloudRedisStubSettingsGetInstance();
  }

  public static void cloudRedisStubSettingsGetInstance() throws Exception {
    CloudRedisStubSettings.Builder cloudRedisSettingsBuilder = CloudRedisStubSettings.newBuilder();
    cloudRedisSettingsBuilder
        .getInstanceSettings()
        .setRetrySettings(
            cloudRedisSettingsBuilder
                .getInstanceSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    CloudRedisStubSettings cloudRedisSettings = cloudRedisSettingsBuilder.build();
  }
}
