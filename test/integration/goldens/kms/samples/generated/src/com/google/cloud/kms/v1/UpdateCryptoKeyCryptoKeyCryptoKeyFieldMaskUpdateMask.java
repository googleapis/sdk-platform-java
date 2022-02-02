package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.FieldMask;

public class UpdateCryptoKeyCryptoKeyCryptoKeyFieldMaskUpdateMask {

  public static void main(String[] args) throws Exception {
    updateCryptoKeyCryptoKeyCryptoKeyFieldMaskUpdateMask();
  }

  public static void updateCryptoKeyCryptoKeyCryptoKeyFieldMaskUpdateMask() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CryptoKey cryptoKey = CryptoKey.newBuilder().build();
      FieldMask updateMask = FieldMask.newBuilder().build();
      CryptoKey response = keyManagementServiceClient.updateCryptoKey(cryptoKey, updateMask);
    }
  }
}
