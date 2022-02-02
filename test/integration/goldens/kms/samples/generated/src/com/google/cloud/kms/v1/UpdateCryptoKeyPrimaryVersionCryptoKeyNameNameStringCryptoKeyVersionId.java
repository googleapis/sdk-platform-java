package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class UpdateCryptoKeyPrimaryVersionCryptoKeyNameNameStringCryptoKeyVersionId {

  public static void main(String[] args) throws Exception {
    updateCryptoKeyPrimaryVersionCryptoKeyNameNameStringCryptoKeyVersionId();
  }

  public static void updateCryptoKeyPrimaryVersionCryptoKeyNameNameStringCryptoKeyVersionId()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CryptoKeyName name =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]");
      String cryptoKeyVersionId = "cryptoKeyVersionId987674581";
      CryptoKey response =
          keyManagementServiceClient.updateCryptoKeyPrimaryVersion(name, cryptoKeyVersionId);
    }
  }
}
