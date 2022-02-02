package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.LocationName;

public class CreateKeyRingStringParentStringKeyRingIdKeyRingKeyRing {

  public static void main(String[] args) throws Exception {
    createKeyRingStringParentStringKeyRingIdKeyRingKeyRing();
  }

  public static void createKeyRingStringParentStringKeyRingIdKeyRingKeyRing() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String parent = LocationName.of("[PROJECT]", "[LOCATION]").toString();
      String keyRingId = "keyRingId-2027180374";
      KeyRing keyRing = KeyRing.newBuilder().build();
      KeyRing response = keyManagementServiceClient.createKeyRing(parent, keyRingId, keyRing);
    }
  }
}
