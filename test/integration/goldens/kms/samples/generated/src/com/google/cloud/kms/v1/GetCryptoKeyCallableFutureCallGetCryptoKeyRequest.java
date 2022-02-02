package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.GetCryptoKeyRequest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class GetCryptoKeyCallableFutureCallGetCryptoKeyRequest {

  public static void main(String[] args) throws Exception {
    getCryptoKeyCallableFutureCallGetCryptoKeyRequest();
  }

  public static void getCryptoKeyCallableFutureCallGetCryptoKeyRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      GetCryptoKeyRequest request =
          GetCryptoKeyRequest.newBuilder()
              .setName(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .build();
      ApiFuture<CryptoKey> future =
          keyManagementServiceClient.getCryptoKeyCallable().futureCall(request);
      // Do something.
      CryptoKey response = future.get();
    }
  }
}
