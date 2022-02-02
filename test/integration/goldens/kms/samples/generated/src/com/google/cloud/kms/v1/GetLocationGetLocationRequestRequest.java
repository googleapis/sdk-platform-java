package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.location.GetLocationRequest;
import com.google.cloud.location.Location;

public class GetLocationGetLocationRequestRequest {

  public static void main(String[] args) throws Exception {
    getLocationGetLocationRequestRequest();
  }

  public static void getLocationGetLocationRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      GetLocationRequest request = GetLocationRequest.newBuilder().setName("name3373707").build();
      Location response = keyManagementServiceClient.getLocation(request);
    }
  }
}
