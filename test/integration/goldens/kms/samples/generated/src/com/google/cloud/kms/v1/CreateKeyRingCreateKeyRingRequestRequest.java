package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CreateKeyRingRequest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.LocationName;

public class CreateKeyRingCreateKeyRingRequestRequest {

  public static void main(String[] args) throws Exception {
    createKeyRingCreateKeyRingRequestRequest();
  }

  public static void createKeyRingCreateKeyRingRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CreateKeyRingRequest request =
          CreateKeyRingRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setKeyRingId("keyRingId-2027180374")
              .setKeyRing(KeyRing.newBuilder().build())
              .build();
      KeyRing response = keyManagementServiceClient.createKeyRing(request);
    }
  }
}
