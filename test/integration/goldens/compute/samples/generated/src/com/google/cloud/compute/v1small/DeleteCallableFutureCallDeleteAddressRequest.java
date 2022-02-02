package com.google.cloud.compute.v1small.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.DeleteAddressRequest;
import com.google.longrunning.Operation;

public class DeleteCallableFutureCallDeleteAddressRequest {

  public static void main(String[] args) throws Exception {
    deleteCallableFutureCallDeleteAddressRequest();
  }

  public static void deleteCallableFutureCallDeleteAddressRequest() throws Exception {
    try (AddressesClient addressesClient = AddressesClient.create()) {
      DeleteAddressRequest request =
          DeleteAddressRequest.newBuilder()
              .setAddress("address-1147692044")
              .setProject("project-309310695")
              .setRegion("region-934795532")
              .setRequestId("requestId693933066")
              .build();
      ApiFuture<Operation> future = addressesClient.deleteCallable().futureCall(request);
      // Do something.
      com.google.cloud.compute.v1small.Operation response = future.get();
    }
  }
}
