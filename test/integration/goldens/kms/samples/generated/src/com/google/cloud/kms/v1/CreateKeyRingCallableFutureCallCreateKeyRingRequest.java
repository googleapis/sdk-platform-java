package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CreateKeyRingRequest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.LocationName;

public class CreateKeyRingCallableFutureCallCreateKeyRingRequest {

  public static void main(String[] args) throws Exception {
    createKeyRingCallableFutureCallCreateKeyRingRequest();
  }

  public static void createKeyRingCallableFutureCallCreateKeyRingRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CreateKeyRingRequest request =
          CreateKeyRingRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setKeyRingId("keyRingId-2027180374")
              .setKeyRing(KeyRing.newBuilder().build())
              .build();
      ApiFuture<KeyRing> future =
          keyManagementServiceClient.createKeyRingCallable().futureCall(request);
      // Do something.
      KeyRing response = future.get();
    }
  }
}
