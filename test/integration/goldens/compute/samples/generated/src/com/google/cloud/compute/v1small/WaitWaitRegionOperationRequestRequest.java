package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.Operation;
import com.google.cloud.compute.v1small.RegionOperationsClient;
import com.google.cloud.compute.v1small.WaitRegionOperationRequest;

public class WaitWaitRegionOperationRequestRequest {

  public static void main(String[] args) throws Exception {
    waitWaitRegionOperationRequestRequest();
  }

  public static void waitWaitRegionOperationRequestRequest() throws Exception {
    try (RegionOperationsClient regionOperationsClient = RegionOperationsClient.create()) {
      WaitRegionOperationRequest request =
          WaitRegionOperationRequest.newBuilder()
              .setOperation("operation1662702951")
              .setProject("project-309310695")
              .setRegion("region-934795532")
              .build();
      Operation response = regionOperationsClient.wait(request);
    }
  }
}
