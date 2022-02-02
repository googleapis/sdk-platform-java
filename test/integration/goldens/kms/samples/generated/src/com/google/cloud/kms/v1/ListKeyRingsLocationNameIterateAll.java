package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.LocationName;

public class ListKeyRingsLocationNameIterateAll {

  public static void main(String[] args) throws Exception {
    listKeyRingsLocationNameIterateAll();
  }

  public static void listKeyRingsLocationNameIterateAll() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      LocationName parent = LocationName.of("[PROJECT]", "[LOCATION]");
      for (KeyRing element : keyManagementServiceClient.listKeyRings(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
