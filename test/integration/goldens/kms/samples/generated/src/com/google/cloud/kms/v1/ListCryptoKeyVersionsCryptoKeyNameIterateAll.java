package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class ListCryptoKeyVersionsCryptoKeyNameIterateAll {

  public static void main(String[] args) throws Exception {
    listCryptoKeyVersionsCryptoKeyNameIterateAll();
  }

  public static void listCryptoKeyVersionsCryptoKeyNameIterateAll() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CryptoKeyName parent =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]");
      for (CryptoKeyVersion element :
          keyManagementServiceClient.listCryptoKeyVersions(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
