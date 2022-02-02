package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.FieldMask;

public class UpdateCryptoKeyVersionCryptoKeyVersionCryptoKeyVersionFieldMaskUpdateMask {

  public static void main(String[] args) throws Exception {
    updateCryptoKeyVersionCryptoKeyVersionCryptoKeyVersionFieldMaskUpdateMask();
  }

  public static void updateCryptoKeyVersionCryptoKeyVersionCryptoKeyVersionFieldMaskUpdateMask()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CryptoKeyVersion cryptoKeyVersion = CryptoKeyVersion.newBuilder().build();
      FieldMask updateMask = FieldMask.newBuilder().build();
      CryptoKeyVersion response =
          keyManagementServiceClient.updateCryptoKeyVersion(cryptoKeyVersion, updateMask);
    }
  }
}
