package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.LocationName;

public class CreateKeyRingLocationNameParentStringKeyRingIdKeyRingKeyRing {

  public static void main(String[] args) throws Exception {
    createKeyRingLocationNameParentStringKeyRingIdKeyRingKeyRing();
  }

  public static void createKeyRingLocationNameParentStringKeyRingIdKeyRingKeyRing()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      LocationName parent = LocationName.of("[PROJECT]", "[LOCATION]");
      String keyRingId = "keyRingId-2027180374";
      KeyRing keyRing = KeyRing.newBuilder().build();
      KeyRing response = keyManagementServiceClient.createKeyRing(parent, keyRingId, keyRing);
    }
  }
}
