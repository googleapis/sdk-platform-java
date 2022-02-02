package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.DestroyCryptoKeyVersionRequest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class DestroyCryptoKeyVersionCallableFutureCallDestroyCryptoKeyVersionRequest {

  public static void main(String[] args) throws Exception {
    destroyCryptoKeyVersionCallableFutureCallDestroyCryptoKeyVersionRequest();
  }

  public static void destroyCryptoKeyVersionCallableFutureCallDestroyCryptoKeyVersionRequest()
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
      ApiFuture<CryptoKeyVersion> future =
          keyManagementServiceClient.destroyCryptoKeyVersionCallable().futureCall(request);
      // Do something.
      CryptoKeyVersion response = future.get();
    }
  }
}
