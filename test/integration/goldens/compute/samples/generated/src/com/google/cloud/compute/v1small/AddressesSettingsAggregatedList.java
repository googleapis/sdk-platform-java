package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.AddressesSettings;
import java.time.Duration;

public class AddressesSettingsAggregatedList {

  public static void main(String[] args) throws Exception {
    addressesSettingsAggregatedList();
  }

  public static void addressesSettingsAggregatedList() throws Exception {
    AddressesSettings.Builder addressesSettingsBuilder = AddressesSettings.newBuilder();
    addressesSettingsBuilder
        .aggregatedListSettings()
        .setRetrySettings(
            addressesSettingsBuilder
                .aggregatedListSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    AddressesSettings addressesSettings = addressesSettingsBuilder.build();
  }
}
