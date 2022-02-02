package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.RegionOperationsSettings;
import java.time.Duration;

public class RegionOperationsSettingsGet {

  public static void main(String[] args) throws Exception {
    regionOperationsSettingsGet();
  }

  public static void regionOperationsSettingsGet() throws Exception {
    RegionOperationsSettings.Builder regionOperationsSettingsBuilder =
        RegionOperationsSettings.newBuilder();
    regionOperationsSettingsBuilder
        .getSettings()
        .setRetrySettings(
            regionOperationsSettingsBuilder
                .getSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    RegionOperationsSettings regionOperationsSettings = regionOperationsSettingsBuilder.build();
  }
}
