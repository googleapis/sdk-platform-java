package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class ListCryptoKeyVersionsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listCryptoKeyVersionsStringIterateAll();
  }

  public static void listCryptoKeyVersionsStringIterateAll() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String parent =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]").toString();
      for (CryptoKeyVersion element :
          keyManagementServiceClient.listCryptoKeyVersions(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
