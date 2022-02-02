package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.KeyRingName;

public class GetKeyRingStringName {

  public static void main(String[] args) throws Exception {
    getKeyRingStringName();
  }

  public static void getKeyRingStringName() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String name = KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString();
      KeyRing response = keyManagementServiceClient.getKeyRing(name);
    }
  }
}
