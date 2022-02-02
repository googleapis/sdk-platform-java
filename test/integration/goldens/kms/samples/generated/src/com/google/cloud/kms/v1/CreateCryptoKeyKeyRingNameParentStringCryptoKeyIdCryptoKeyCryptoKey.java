package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;

public class CreateCryptoKeyKeyRingNameParentStringCryptoKeyIdCryptoKeyCryptoKey {

  public static void main(String[] args) throws Exception {
    createCryptoKeyKeyRingNameParentStringCryptoKeyIdCryptoKeyCryptoKey();
  }

  public static void createCryptoKeyKeyRingNameParentStringCryptoKeyIdCryptoKeyCryptoKey()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      KeyRingName parent = KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]");
      String cryptoKeyId = "cryptoKeyId-1643185255";
      CryptoKey cryptoKey = CryptoKey.newBuilder().build();
      CryptoKey response =
          keyManagementServiceClient.createCryptoKey(parent, cryptoKeyId, cryptoKey);
    }
  }
}
