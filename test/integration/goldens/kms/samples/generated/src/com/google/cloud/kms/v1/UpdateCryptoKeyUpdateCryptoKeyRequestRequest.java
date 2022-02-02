package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.UpdateCryptoKeyRequest;
import com.google.protobuf.FieldMask;

public class UpdateCryptoKeyUpdateCryptoKeyRequestRequest {

  public static void main(String[] args) throws Exception {
    updateCryptoKeyUpdateCryptoKeyRequestRequest();
  }

  public static void updateCryptoKeyUpdateCryptoKeyRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      UpdateCryptoKeyRequest request =
          UpdateCryptoKeyRequest.newBuilder()
              .setCryptoKey(CryptoKey.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      CryptoKey response = keyManagementServiceClient.updateCryptoKey(request);
    }
  }
}
