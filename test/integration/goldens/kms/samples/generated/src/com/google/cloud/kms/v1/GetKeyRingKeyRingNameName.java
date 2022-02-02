package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.KeyRingName;

public class GetKeyRingKeyRingNameName {

  public static void main(String[] args) throws Exception {
    getKeyRingKeyRingNameName();
  }

  public static void getKeyRingKeyRingNameName() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      KeyRingName name = KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]");
      KeyRing response = keyManagementServiceClient.getKeyRing(name);
    }
  }
}
