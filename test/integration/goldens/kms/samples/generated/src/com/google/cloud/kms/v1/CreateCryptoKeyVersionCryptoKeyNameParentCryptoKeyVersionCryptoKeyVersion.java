package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class CreateCryptoKeyVersionCryptoKeyNameParentCryptoKeyVersionCryptoKeyVersion {

  public static void main(String[] args) throws Exception {
    createCryptoKeyVersionCryptoKeyNameParentCryptoKeyVersionCryptoKeyVersion();
  }

  public static void createCryptoKeyVersionCryptoKeyNameParentCryptoKeyVersionCryptoKeyVersion()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CryptoKeyName parent =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]");
      CryptoKeyVersion cryptoKeyVersion = CryptoKeyVersion.newBuilder().build();
      CryptoKeyVersion response =
          keyManagementServiceClient.createCryptoKeyVersion(parent, cryptoKeyVersion);
    }
  }
}
