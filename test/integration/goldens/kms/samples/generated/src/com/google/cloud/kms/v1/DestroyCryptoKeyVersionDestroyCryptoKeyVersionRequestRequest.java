package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.DestroyCryptoKeyVersionRequest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class DestroyCryptoKeyVersionDestroyCryptoKeyVersionRequestRequest {

  public static void main(String[] args) throws Exception {
    destroyCryptoKeyVersionDestroyCryptoKeyVersionRequestRequest();
  }

  public static void destroyCryptoKeyVersionDestroyCryptoKeyVersionRequestRequest()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      DestroyCryptoKeyVersionRequest request =
          DestroyCryptoKeyVersionRequest.newBuilder()
              .setName(
                  CryptoKeyVersionName.of(
                          "[PROJECT]",
                          "[LOCATION]",
                          "[KEY_RING]",
                          "[CRYPTO_KEY]",
                          "[CRYPTO_KEY_VERSION]")
                      .toString())
              .build();
      CryptoKeyVersion response = keyManagementServiceClient.destroyCryptoKeyVersion(request);
    }
  }
}
