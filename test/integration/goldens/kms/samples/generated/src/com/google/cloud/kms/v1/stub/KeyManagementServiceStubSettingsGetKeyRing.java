package com.google.cloud.kms.v1.stub.samples;

import com.google.cloud.kms.v1.stub.KeyManagementServiceStubSettings;
import java.time.Duration;

public class KeyManagementServiceStubSettingsGetKeyRing {

  public static void main(String[] args) throws Exception {
    keyManagementServiceStubSettingsGetKeyRing();
  }

  public static void keyManagementServiceStubSettingsGetKeyRing() throws Exception {
    KeyManagementServiceStubSettings.Builder keyManagementServiceSettingsBuilder =
        KeyManagementServiceStubSettings.newBuilder();
    keyManagementServiceSettingsBuilder
        .getKeyRingSettings()
        .setRetrySettings(
            keyManagementServiceSettingsBuilder
                .getKeyRingSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    KeyManagementServiceStubSettings keyManagementServiceSettings =
        keyManagementServiceSettingsBuilder.build();
  }
}
