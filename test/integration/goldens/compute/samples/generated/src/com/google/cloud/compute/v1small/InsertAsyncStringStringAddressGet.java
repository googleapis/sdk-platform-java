package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.Address;
import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.Operation;

public class InsertAsyncStringStringAddressGet {

  public static void main(String[] args) throws Exception {
    insertAsyncStringStringAddressGet();
  }

  public static void insertAsyncStringStringAddressGet() throws Exception {
    try (AddressesClient addressesClient = AddressesClient.create()) {
      String project = "project-309310695";
      String region = "region-934795532";
      Address addressResource = Address.newBuilder().build();
      Operation response = addressesClient.insertAsync(project, region, addressResource).get();
    }
  }
}
