package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.GetKeyRingRequest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.KeyRingName;

public class GetKeyRingGetKeyRingRequestRequest {

  public static void main(String[] args) throws Exception {
    getKeyRingGetKeyRingRequestRequest();
  }

  public static void getKeyRingGetKeyRingRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      GetKeyRingRequest request =
          GetKeyRingRequest.newBuilder()
              .setName(KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString())
              .build();
      KeyRing response = keyManagementServiceClient.getKeyRing(request);
    }
  }
}
