package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.Operation;

public class DeleteAsyncStringStringStringGet {

  public static void main(String[] args) throws Exception {
    deleteAsyncStringStringStringGet();
  }

  public static void deleteAsyncStringStringStringGet() throws Exception {
    try (AddressesClient addressesClient = AddressesClient.create()) {
      String project = "project-309310695";
      String region = "region-934795532";
      String address = "address-1147692044";
      Operation response = addressesClient.deleteAsync(project, region, address).get();
    }
  }
}
