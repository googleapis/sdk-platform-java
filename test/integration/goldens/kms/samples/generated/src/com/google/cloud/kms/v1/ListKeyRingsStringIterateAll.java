package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.LocationName;

public class ListKeyRingsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listKeyRingsStringIterateAll();
  }

  public static void listKeyRingsStringIterateAll() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String parent = LocationName.of("[PROJECT]", "[LOCATION]").toString();
      for (KeyRing element : keyManagementServiceClient.listKeyRings(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
