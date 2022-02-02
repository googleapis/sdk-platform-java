package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.location.GetLocationRequest;
import com.google.cloud.location.Location;

public class GetLocationCallableFutureCallGetLocationRequest {

  public static void main(String[] args) throws Exception {
    getLocationCallableFutureCallGetLocationRequest();
  }

  public static void getLocationCallableFutureCallGetLocationRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      GetLocationRequest request = GetLocationRequest.newBuilder().setName("name3373707").build();
      ApiFuture<Location> future =
          keyManagementServiceClient.getLocationCallable().futureCall(request);
      // Do something.
      Location response = future.get();
    }
  }
}
