package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.UpdateCryptoKeyVersionRequest;
import com.google.protobuf.FieldMask;

public class UpdateCryptoKeyVersionUpdateCryptoKeyVersionRequestRequest {

  public static void main(String[] args) throws Exception {
    updateCryptoKeyVersionUpdateCryptoKeyVersionRequestRequest();
  }

  public static void updateCryptoKeyVersionUpdateCryptoKeyVersionRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      UpdateCryptoKeyVersionRequest request =
          UpdateCryptoKeyVersionRequest.newBuilder()
              .setCryptoKeyVersion(CryptoKeyVersion.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      CryptoKeyVersion response = keyManagementServiceClient.updateCryptoKeyVersion(request);
    }
  }
}
