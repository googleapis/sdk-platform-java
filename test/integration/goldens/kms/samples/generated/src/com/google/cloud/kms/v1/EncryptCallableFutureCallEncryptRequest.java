package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.EncryptRequest;
import com.google.cloud.kms.v1.EncryptResponse;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;
import com.google.protobuf.Int64Value;

public class EncryptCallableFutureCallEncryptRequest {

  public static void main(String[] args) throws Exception {
    encryptCallableFutureCallEncryptRequest();
  }

  public static void encryptCallableFutureCallEncryptRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      EncryptRequest request =
          EncryptRequest.newBuilder()
              .setName(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setPlaintext(ByteString.EMPTY)
              .setAdditionalAuthenticatedData(ByteString.EMPTY)
              .setPlaintextCrc32C(Int64Value.newBuilder().build())
              .setAdditionalAuthenticatedDataCrc32C(Int64Value.newBuilder().build())
              .build();
      ApiFuture<EncryptResponse> future =
          keyManagementServiceClient.encryptCallable().futureCall(request);
      // Do something.
      EncryptResponse response = future.get();
    }
  }
}
