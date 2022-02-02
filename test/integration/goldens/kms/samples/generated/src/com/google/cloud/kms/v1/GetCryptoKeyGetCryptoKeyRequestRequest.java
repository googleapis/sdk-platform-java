package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.GetCryptoKeyRequest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class GetCryptoKeyGetCryptoKeyRequestRequest {

  public static void main(String[] args) throws Exception {
    getCryptoKeyGetCryptoKeyRequestRequest();
  }

  public static void getCryptoKeyGetCryptoKeyRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      GetCryptoKeyRequest request =
          GetCryptoKeyRequest.newBuilder()
              .setName(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .build();
      CryptoKey response = keyManagementServiceClient.getCryptoKey(request);
    }
  }
}
