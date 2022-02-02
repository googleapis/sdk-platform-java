package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;

public class ListCryptoKeysKeyRingNameIterateAll {

  public static void main(String[] args) throws Exception {
    listCryptoKeysKeyRingNameIterateAll();
  }

  public static void listCryptoKeysKeyRingNameIterateAll() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      KeyRingName parent = KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]");
      for (CryptoKey element : keyManagementServiceClient.listCryptoKeys(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
