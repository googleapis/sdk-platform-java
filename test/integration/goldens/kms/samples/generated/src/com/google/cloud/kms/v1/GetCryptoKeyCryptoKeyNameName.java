package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class GetCryptoKeyCryptoKeyNameName {

  public static void main(String[] args) throws Exception {
    getCryptoKeyCryptoKeyNameName();
  }

  public static void getCryptoKeyCryptoKeyNameName() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CryptoKeyName name =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]");
      CryptoKey response = keyManagementServiceClient.getCryptoKey(name);
    }
  }
}
