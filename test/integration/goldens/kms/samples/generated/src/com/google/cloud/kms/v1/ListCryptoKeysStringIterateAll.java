package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;

public class ListCryptoKeysStringIterateAll {

  public static void main(String[] args) throws Exception {
    listCryptoKeysStringIterateAll();
  }

  public static void listCryptoKeysStringIterateAll() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String parent = KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString();
      for (CryptoKey element : keyManagementServiceClient.listCryptoKeys(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
