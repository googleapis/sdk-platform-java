package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.UpdateCryptoKeyPrimaryVersionRequest;

public class UpdateCryptoKeyPrimaryVersionCallableFutureCallUpdateCryptoKeyPrimaryVersionRequest {

  public static void main(String[] args) throws Exception {
    updateCryptoKeyPrimaryVersionCallableFutureCallUpdateCryptoKeyPrimaryVersionRequest();
  }

  public static void
      updateCryptoKeyPrimaryVersionCallableFutureCallUpdateCryptoKeyPrimaryVersionRequest()
          throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      UpdateCryptoKeyPrimaryVersionRequest request =
          UpdateCryptoKeyPrimaryVersionRequest.newBuilder()
              .setName(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setCryptoKeyVersionId("cryptoKeyVersionId987674581")
              .build();
      ApiFuture<CryptoKey> future =
          keyManagementServiceClient.updateCryptoKeyPrimaryVersionCallable().futureCall(request);
      // Do something.
      CryptoKey response = future.get();
    }
  }
}
