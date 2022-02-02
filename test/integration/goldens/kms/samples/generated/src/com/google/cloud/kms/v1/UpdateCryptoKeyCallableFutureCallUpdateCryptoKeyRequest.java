package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.UpdateCryptoKeyRequest;
import com.google.protobuf.FieldMask;

public class UpdateCryptoKeyCallableFutureCallUpdateCryptoKeyRequest {

  public static void main(String[] args) throws Exception {
    updateCryptoKeyCallableFutureCallUpdateCryptoKeyRequest();
  }

  public static void updateCryptoKeyCallableFutureCallUpdateCryptoKeyRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      UpdateCryptoKeyRequest request =
          UpdateCryptoKeyRequest.newBuilder()
              .setCryptoKey(CryptoKey.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<CryptoKey> future =
          keyManagementServiceClient.updateCryptoKeyCallable().futureCall(request);
      // Do something.
      CryptoKey response = future.get();
    }
  }
}
