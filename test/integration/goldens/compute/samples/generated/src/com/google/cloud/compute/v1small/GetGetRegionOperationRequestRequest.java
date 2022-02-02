package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.GetRegionOperationRequest;
import com.google.cloud.compute.v1small.Operation;
import com.google.cloud.compute.v1small.RegionOperationsClient;

public class GetGetRegionOperationRequestRequest {

  public static void main(String[] args) throws Exception {
    getGetRegionOperationRequestRequest();
  }

  public static void getGetRegionOperationRequestRequest() throws Exception {
    try (RegionOperationsClient regionOperationsClient = RegionOperationsClient.create()) {
      GetRegionOperationRequest request =
          GetRegionOperationRequest.newBuilder()
              .setOperation("operation1662702951")
              .setProject("project-309310695")
              .setRegion("region-934795532")
              .build();
      Operation response = regionOperationsClient.get(request);
    }
  }
}
