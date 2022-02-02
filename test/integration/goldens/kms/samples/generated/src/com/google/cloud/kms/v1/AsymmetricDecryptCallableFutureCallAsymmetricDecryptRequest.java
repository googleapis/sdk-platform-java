package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.AsymmetricDecryptRequest;
import com.google.cloud.kms.v1.AsymmetricDecryptResponse;
import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;
import com.google.protobuf.Int64Value;

public class AsymmetricDecryptCallableFutureCallAsymmetricDecryptRequest {

  public static void main(String[] args) throws Exception {
    asymmetricDecryptCallableFutureCallAsymmetricDecryptRequest();
  }

  public static void asymmetricDecryptCallableFutureCallAsymmetricDecryptRequest()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      AsymmetricDecryptRequest request =
          AsymmetricDecryptRequest.newBuilder()
              .setName(
                  CryptoKeyVersionName.of(
                          "[PROJECT]",
                          "[LOCATION]",
                          "[KEY_RING]",
                          "[CRYPTO_KEY]",
                          "[CRYPTO_KEY_VERSION]")
                      .toString())
              .setCiphertext(ByteString.EMPTY)
              .setCiphertextCrc32C(Int64Value.newBuilder().build())
              .build();
      ApiFuture<AsymmetricDecryptResponse> future =
          keyManagementServiceClient.asymmetricDecryptCallable().futureCall(request);
      // Do something.
      AsymmetricDecryptResponse response = future.get();
    }
  }
}
