package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.RestoreCryptoKeyVersionRequest;

public class RestoreCryptoKeyVersionCallableFutureCallRestoreCryptoKeyVersionRequest {

  public static void main(String[] args) throws Exception {
    restoreCryptoKeyVersionCallableFutureCallRestoreCryptoKeyVersionRequest();
  }

  public static void restoreCryptoKeyVersionCallableFutureCallRestoreCryptoKeyVersionRequest()
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
      ApiFuture<CryptoKeyVersion> future =
          keyManagementServiceClient.restoreCryptoKeyVersionCallable().futureCall(request);
      // Do something.
      CryptoKeyVersion response = future.get();
    }
  }
}
