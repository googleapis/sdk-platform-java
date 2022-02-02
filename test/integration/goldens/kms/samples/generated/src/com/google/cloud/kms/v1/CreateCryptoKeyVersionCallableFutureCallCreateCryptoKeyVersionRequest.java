package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CreateCryptoKeyVersionRequest;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class CreateCryptoKeyVersionCallableFutureCallCreateCryptoKeyVersionRequest {

  public static void main(String[] args) throws Exception {
    createCryptoKeyVersionCallableFutureCallCreateCryptoKeyVersionRequest();
  }

  public static void createCryptoKeyVersionCallableFutureCallCreateCryptoKeyVersionRequest()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CreateCryptoKeyVersionRequest request =
          CreateCryptoKeyVersionRequest.newBuilder()
              .setParent(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setCryptoKeyVersion(CryptoKeyVersion.newBuilder().build())
              .build();
      ApiFuture<CryptoKeyVersion> future =
          keyManagementServiceClient.createCryptoKeyVersionCallable().futureCall(request);
      // Do something.
      CryptoKeyVersion response = future.get();
    }
  }
}
