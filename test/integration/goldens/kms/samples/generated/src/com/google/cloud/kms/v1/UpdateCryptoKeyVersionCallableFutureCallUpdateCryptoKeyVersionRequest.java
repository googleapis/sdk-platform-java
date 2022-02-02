package com.google.cloud.kms.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.UpdateCryptoKeyVersionRequest;
import com.google.protobuf.FieldMask;

public class UpdateCryptoKeyVersionCallableFutureCallUpdateCryptoKeyVersionRequest {

  public static void main(String[] args) throws Exception {
    updateCryptoKeyVersionCallableFutureCallUpdateCryptoKeyVersionRequest();
  }

  public static void updateCryptoKeyVersionCallableFutureCallUpdateCryptoKeyVersionRequest()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      UpdateCryptoKeyVersionRequest request =
          UpdateCryptoKeyVersionRequest.newBuilder()
              .setCryptoKeyVersion(CryptoKeyVersion.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<CryptoKeyVersion> future =
          keyManagementServiceClient.updateCryptoKeyVersionCallable().futureCall(request);
      // Do something.
      CryptoKeyVersion response = future.get();
    }
  }
}
