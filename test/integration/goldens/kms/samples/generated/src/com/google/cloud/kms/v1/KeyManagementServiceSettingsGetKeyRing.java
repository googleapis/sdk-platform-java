package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.KeyManagementServiceSettings;
import java.time.Duration;

public class KeyManagementServiceSettingsGetKeyRing {

  public static void main(String[] args) throws Exception {
    keyManagementServiceSettingsGetKeyRing();
  }

  public static void keyManagementServiceSettingsGetKeyRing() throws Exception {
    KeyManagementServiceSettings.Builder keyManagementServiceSettingsBuilder =
        KeyManagementServiceSettings.newBuilder();
    keyManagementServiceSettingsBuilder
        .getKeyRingSettings()
        .setRetrySettings(
            keyManagementServiceSettingsBuilder
                .getKeyRingSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    KeyManagementServiceSettings keyManagementServiceSettings =
        keyManagementServiceSettingsBuilder.build();
  }
}
