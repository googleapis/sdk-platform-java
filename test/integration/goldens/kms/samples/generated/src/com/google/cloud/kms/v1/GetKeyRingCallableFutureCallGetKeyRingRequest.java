package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.GetKeyRingRequest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.KeyRingName;

public class GetKeyRingCallableFutureCallGetKeyRingRequest {

  public static void main(String[] args) throws Exception {
    getKeyRingCallableFutureCallGetKeyRingRequest();
  }

  public static void getKeyRingCallableFutureCallGetKeyRingRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      GetKeyRingRequest request =
          GetKeyRingRequest.newBuilder()
              .setName(KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString())
              .build();
      ApiFuture<KeyRing> future =
          keyManagementServiceClient.getKeyRingCallable().futureCall(request);
      // Do something.
      KeyRing response = future.get();
    }
  }
}
