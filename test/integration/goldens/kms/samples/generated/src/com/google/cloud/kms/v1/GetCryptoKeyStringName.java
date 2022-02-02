package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class GetCryptoKeyStringName {

  public static void main(String[] args) throws Exception {
    getCryptoKeyStringName();
  }

  public static void getCryptoKeyStringName() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String name =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]").toString();
      CryptoKey response = keyManagementServiceClient.getCryptoKey(name);
    }
  }
}
