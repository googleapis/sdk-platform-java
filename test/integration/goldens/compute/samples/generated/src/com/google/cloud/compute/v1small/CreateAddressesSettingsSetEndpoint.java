package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.AddressesSettings;
import com.google.cloud.compute.v1small.myEndpoint;

public class CreateAddressesSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createAddressesSettingsSetEndpoint();
  }

  public static void createAddressesSettingsSetEndpoint() throws Exception {
    AddressesSettings addressesSettings =
        AddressesSettings.newBuilder().setEndpoint(myEndpoint).build();
    AddressesClient addressesClient = AddressesClient.create(addressesSettings);
  }
}
