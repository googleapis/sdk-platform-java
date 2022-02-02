package com.google.cloud.compute.v1small.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.compute.v1small.RegionOperationsClient;
import com.google.cloud.compute.v1small.RegionOperationsSettings;
import com.google.cloud.compute.v1small.myCredentials;

public class CreateRegionOperationsSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createRegionOperationsSettingsSetCredentialsProvider();
  }

  public static void createRegionOperationsSettingsSetCredentialsProvider() throws Exception {
    RegionOperationsSettings regionOperationsSettings =
        RegionOperationsSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    RegionOperationsClient regionOperationsClient =
        RegionOperationsClient.create(regionOperationsSettings);
  }
}
