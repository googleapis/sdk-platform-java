package com.google.cloud.compute.v1small.stub.samples;

import com.google.cloud.compute.v1small.stub.AddressesStubSettings;
import java.time.Duration;

public class AddressesStubSettingsAggregatedList {

  public static void main(String[] args) throws Exception {
    addressesStubSettingsAggregatedList();
  }

  public static void addressesStubSettingsAggregatedList() throws Exception {
    AddressesStubSettings.Builder addressesSettingsBuilder = AddressesStubSettings.newBuilder();
    addressesSettingsBuilder
        .aggregatedListSettings()
        .setRetrySettings(
            addressesSettingsBuilder
                .aggregatedListSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    AddressesStubSettings addressesSettings = addressesSettingsBuilder.build();
  }
}
