package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class CreateCryptoKeyVersionStringParentCryptoKeyVersionCryptoKeyVersion {

  public static void main(String[] args) throws Exception {
    createCryptoKeyVersionStringParentCryptoKeyVersionCryptoKeyVersion();
  }

  public static void createCryptoKeyVersionStringParentCryptoKeyVersionCryptoKeyVersion()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String parent =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]").toString();
      CryptoKeyVersion cryptoKeyVersion = CryptoKeyVersion.newBuilder().build();
      CryptoKeyVersion response =
          keyManagementServiceClient.createCryptoKeyVersion(parent, cryptoKeyVersion);
    }
  }
}
