package com.google.cloud.compute.v1small.samples;

import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.DeleteAddressRequest;
import com.google.cloud.compute.v1small.Operation;

public class DeleteOperationCallablefutureCallDeleteAddressRequest {

  public static void main(String[] args) throws Exception {
    deleteOperationCallablefutureCallDeleteAddressRequest();
  }

  public static void deleteOperationCallablefutureCallDeleteAddressRequest() throws Exception {
    try (AddressesClient addressesClient = AddressesClient.create()) {
      DeleteAddressRequest request =
          DeleteAddressRequest.newBuilder()
              .setAddress("address-1147692044")
              .setProject("project-309310695")
              .setRegion("region-934795532")
              .setRequestId("requestId693933066")
              .build();
      OperationFuture<Operation, Operation> future =
          addressesClient.deleteOperationCallable().futureCall(request);
      // Do something.
      Operation response = future.get();
    }
  }
}
