package com.google.cloud.compute.v1small.stub.samples;

import com.google.cloud.compute.v1small.stub.RegionOperationsStubSettings;
import java.time.Duration;

public class RegionOperationsStubSettingsGet {

  public static void main(String[] args) throws Exception {
    regionOperationsStubSettingsGet();
  }

  public static void regionOperationsStubSettingsGet() throws Exception {
    RegionOperationsStubSettings.Builder regionOperationsSettingsBuilder =
        RegionOperationsStubSettings.newBuilder();
    regionOperationsSettingsBuilder
        .getSettings()
        .setRetrySettings(
            regionOperationsSettingsBuilder
                .getSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    RegionOperationsStubSettings regionOperationsSettings = regionOperationsSettingsBuilder.build();
  }
}
