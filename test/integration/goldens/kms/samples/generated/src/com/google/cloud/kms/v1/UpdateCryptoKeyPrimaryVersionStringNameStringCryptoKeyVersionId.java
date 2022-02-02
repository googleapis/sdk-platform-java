package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class UpdateCryptoKeyPrimaryVersionStringNameStringCryptoKeyVersionId {

  public static void main(String[] args) throws Exception {
    updateCryptoKeyPrimaryVersionStringNameStringCryptoKeyVersionId();
  }

  public static void updateCryptoKeyPrimaryVersionStringNameStringCryptoKeyVersionId()
      throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String name =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]").toString();
      String cryptoKeyVersionId = "cryptoKeyVersionId987674581";
      CryptoKey response =
          keyManagementServiceClient.updateCryptoKeyPrimaryVersion(name, cryptoKeyVersionId);
    }
  }
}
