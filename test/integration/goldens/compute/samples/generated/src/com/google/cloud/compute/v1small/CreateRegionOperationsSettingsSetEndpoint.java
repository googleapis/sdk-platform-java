package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.RegionOperationsClient;
import com.google.cloud.compute.v1small.RegionOperationsSettings;
import com.google.cloud.compute.v1small.myEndpoint;

public class CreateRegionOperationsSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createRegionOperationsSettingsSetEndpoint();
  }

  public static void createRegionOperationsSettingsSetEndpoint() throws Exception {
    RegionOperationsSettings regionOperationsSettings =
        RegionOperationsSettings.newBuilder().setEndpoint(myEndpoint).build();
    RegionOperationsClient regionOperationsClient =
        RegionOperationsClient.create(regionOperationsSettings);
  }
}
