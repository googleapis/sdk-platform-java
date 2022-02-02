package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.PublicKey;

public class GetPublicKeyCryptoKeyVersionNameName {

  public static void main(String[] args) throws Exception {
    getPublicKeyCryptoKeyVersionNameName();
  }

  public static void getPublicKeyCryptoKeyVersionNameName() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CryptoKeyVersionName name =
          CryptoKeyVersionName.of(
              "[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]", "[CRYPTO_KEY_VERSION]");
      PublicKey response = keyManagementServiceClient.getPublicKey(name);
    }
  }
}
