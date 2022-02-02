package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class RestoreCryptoKeyVersionCryptoKeyVersionNameName {

  public static void main(String[] args) throws Exception {
    restoreCryptoKeyVersionCryptoKeyVersionNameName();
  }

  public static void restoreCryptoKeyVersionCryptoKeyVersionNameName() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CryptoKeyVersionName name =
          CryptoKeyVersionName.of(
              "[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]", "[CRYPTO_KEY_VERSION]");
      CryptoKeyVersion response = keyManagementServiceClient.restoreCryptoKeyVersion(name);
    }
  }
}
