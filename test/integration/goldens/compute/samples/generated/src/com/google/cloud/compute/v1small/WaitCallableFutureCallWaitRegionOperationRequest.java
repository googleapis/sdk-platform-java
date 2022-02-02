package com.google.cloud.compute.v1small.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.compute.v1small.Operation;
import com.google.cloud.compute.v1small.RegionOperationsClient;
import com.google.cloud.compute.v1small.WaitRegionOperationRequest;

public class WaitCallableFutureCallWaitRegionOperationRequest {

  public static void main(String[] args) throws Exception {
    waitCallableFutureCallWaitRegionOperationRequest();
  }

  public static void waitCallableFutureCallWaitRegionOperationRequest() throws Exception {
    try (RegionOperationsClient regionOperationsClient = RegionOperationsClient.create()) {
      WaitRegionOperationRequest request =
          WaitRegionOperationRequest.newBuilder()
              .setOperation("operation1662702951")
              .setProject("project-309310695")
              .setRegion("region-934795532")
              .build();
      ApiFuture<Operation> future = regionOperationsClient.waitCallable().futureCall(request);
      // Do something.
      Operation response = future.get();
    }
  }
}
