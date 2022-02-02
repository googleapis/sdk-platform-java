package com.google.cloud.compute.v1small.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.AddressesSettings;
import com.google.cloud.compute.v1small.myCredentials;

public class CreateAddressesSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createAddressesSettingsSetCredentialsProvider();
  }

  public static void createAddressesSettingsSetCredentialsProvider() throws Exception {
    AddressesSettings addressesSettings =
        AddressesSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    AddressesClient addressesClient = AddressesClient.create(addressesSettings);
  }
}
