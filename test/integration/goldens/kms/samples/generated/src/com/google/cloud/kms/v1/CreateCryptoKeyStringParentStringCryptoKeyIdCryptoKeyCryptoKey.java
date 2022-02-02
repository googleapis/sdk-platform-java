package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;

public class CreateCryptoKeyStringParentStringCryptoKeyIdCryptoKeyCryptoKey {

  public static void main(String[] args) throws Exception {
    createCryptoKeyStringParentStringCryptoKeyIdCryptoKeyCryptoKey();
  }

  public static void createCryptoKeyStringParentStringCryptoKeyIdCryptoKeyCryptoKey()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String parent = KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString();
      String cryptoKeyId = "cryptoKeyId-1643185255";
      CryptoKey cryptoKey = CryptoKey.newBuilder().build();
      CryptoKey response =
          keyManagementServiceClient.createCryptoKey(parent, cryptoKeyId, cryptoKey);
    }
  }
}
