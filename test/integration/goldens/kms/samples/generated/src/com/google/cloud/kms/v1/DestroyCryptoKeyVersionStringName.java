package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyVersion;
import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class DestroyCryptoKeyVersionStringName {

  public static void main(String[] args) throws Exception {
    destroyCryptoKeyVersionStringName();
  }

  public static void destroyCryptoKeyVersionStringName() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String name =
          CryptoKeyVersionName.of(
                  "[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]", "[CRYPTO_KEY_VERSION]")
              .toString();
      CryptoKeyVersion response = keyManagementServiceClient.destroyCryptoKeyVersion(name);
    }
  }
}
