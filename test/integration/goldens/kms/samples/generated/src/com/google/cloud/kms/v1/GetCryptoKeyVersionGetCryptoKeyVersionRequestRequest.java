package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.GetCryptoKeyVersionRequest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class GetCryptoKeyVersionGetCryptoKeyVersionRequestRequest {

  public static void main(String[] args) throws Exception {
    getCryptoKeyVersionGetCryptoKeyVersionRequestRequest();
  }

  public static void getCryptoKeyVersionGetCryptoKeyVersionRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      GetCryptoKeyVersionRequest request =
          GetCryptoKeyVersionRequest.newBuilder()
              .setName(
                  CryptoKeyVersionName.of(
                          "[PROJECT]",
                          "[LOCATION]",
                          "[KEY_RING]",
                          "[CRYPTO_KEY]",
                          "[CRYPTO_KEY_VERSION]")
                      .toString())
              .build();
      CryptoKeyVersion response = keyManagementServiceClient.getCryptoKeyVersion(request);
    }
  }
}
