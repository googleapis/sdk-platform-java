package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.RestoreCryptoKeyVersionRequest;

public class RestoreCryptoKeyVersionRestoreCryptoKeyVersionRequestRequest {

  public static void main(String[] args) throws Exception {
    restoreCryptoKeyVersionRestoreCryptoKeyVersionRequestRequest();
  }

  public static void restoreCryptoKeyVersionRestoreCryptoKeyVersionRequestRequest()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      RestoreCryptoKeyVersionRequest request =
          RestoreCryptoKeyVersionRequest.newBuilder()
              .setName(
                  CryptoKeyVersionName.of(
                          "[PROJECT]",
                          "[LOCATION]",
                          "[KEY_RING]",
                          "[CRYPTO_KEY]",
                          "[CRYPTO_KEY_VERSION]")
                      .toString())
              .build();
      CryptoKeyVersion response = keyManagementServiceClient.restoreCryptoKeyVersion(request);
    }
  }
}
