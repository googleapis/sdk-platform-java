package com.google.cloud.compute.v1small.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.compute.v1small.Address;
import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.InsertAddressRequest;
import com.google.longrunning.Operation;

public class InsertCallableFutureCallInsertAddressRequest {

  public static void main(String[] args) throws Exception {
    insertCallableFutureCallInsertAddressRequest();
  }

  public static void insertCallableFutureCallInsertAddressRequest() throws Exception {
    try (AddressesClient addressesClient = AddressesClient.create()) {
      InsertAddressRequest request =
          InsertAddressRequest.newBuilder()
              .setAddressResource(Address.newBuilder().build())
              .setProject("project-309310695")
              .setRegion("region-934795532")
              .setRequestId("requestId693933066")
              .build();
      ApiFuture<Operation> future = addressesClient.insertCallable().futureCall(request);
      // Do something.
      com.google.cloud.compute.v1small.Operation response = future.get();
    }
  }
}
