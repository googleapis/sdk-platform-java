package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CreateCryptoKeyRequest;
import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;

public class CreateCryptoKeyCreateCryptoKeyRequestRequest {

  public static void main(String[] args) throws Exception {
    createCryptoKeyCreateCryptoKeyRequestRequest();
  }

  public static void createCryptoKeyCreateCryptoKeyRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CreateCryptoKeyRequest request =
          CreateCryptoKeyRequest.newBuilder()
              .setParent(KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString())
              .setCryptoKeyId("cryptoKeyId-1643185255")
              .setCryptoKey(CryptoKey.newBuilder().build())
              .setSkipInitialVersionCreation(true)
              .build();
      CryptoKey response = keyManagementServiceClient.createCryptoKey(request);
    }
  }
}
