package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.GetCryptoKeyVersionRequest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class GetCryptoKeyVersionCallableFutureCallGetCryptoKeyVersionRequest {

  public static void main(String[] args) throws Exception {
    getCryptoKeyVersionCallableFutureCallGetCryptoKeyVersionRequest();
  }

  public static void getCryptoKeyVersionCallableFutureCallGetCryptoKeyVersionRequest()
      throws Exception {
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
      ApiFuture<CryptoKeyVersion> future =
          keyManagementServiceClient.getCryptoKeyVersionCallable().futureCall(request);
      // Do something.
      CryptoKeyVersion response = future.get();
    }
  }
}
