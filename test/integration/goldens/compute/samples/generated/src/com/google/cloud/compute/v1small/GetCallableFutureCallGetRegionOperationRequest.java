package com.google.cloud.compute.v1small.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.compute.v1small.GetRegionOperationRequest;
import com.google.cloud.compute.v1small.Operation;
import com.google.cloud.compute.v1small.RegionOperationsClient;

public class GetCallableFutureCallGetRegionOperationRequest {

  public static void main(String[] args) throws Exception {
    getCallableFutureCallGetRegionOperationRequest();
  }

  public static void getCallableFutureCallGetRegionOperationRequest() throws Exception {
    try (RegionOperationsClient regionOperationsClient = RegionOperationsClient.create()) {
      GetRegionOperationRequest request =
          GetRegionOperationRequest.newBuilder()
              .setOperation("operation1662702951")
              .setProject("project-309310695")
              .setRegion("region-934795532")
              .build();
      ApiFuture<Operation> future = regionOperationsClient.getCallable().futureCall(request);
      // Do something.
      Operation response = future.get();
    }
  }
}
