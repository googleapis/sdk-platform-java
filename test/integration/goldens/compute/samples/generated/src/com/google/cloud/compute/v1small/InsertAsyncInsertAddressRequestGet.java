package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.Address;
import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.InsertAddressRequest;
import com.google.cloud.compute.v1small.Operation;

public class InsertAsyncInsertAddressRequestGet {

  public static void main(String[] args) throws Exception {
    insertAsyncInsertAddressRequestGet();
  }

  public static void insertAsyncInsertAddressRequestGet() throws Exception {
    try (AddressesClient addressesClient = AddressesClient.create()) {
      InsertAddressRequest request =
          InsertAddressRequest.newBuilder()
              .setAddressResource(Address.newBuilder().build())
              .setProject("project-309310695")
              .setRegion("region-934795532")
              .setRequestId("requestId693933066")
              .build();
      Operation response = addressesClient.insertAsync(request).get();
    }
  }
}
