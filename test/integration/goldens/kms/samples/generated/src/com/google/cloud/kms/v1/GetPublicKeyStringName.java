package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.PublicKey;

public class GetPublicKeyStringName {

  public static void main(String[] args) throws Exception {
    getPublicKeyStringName();
  }

  public static void getPublicKeyStringName() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String name =
          CryptoKeyVersionName.of(
                  "[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]", "[CRYPTO_KEY_VERSION]")
              .toString();
      PublicKey response = keyManagementServiceClient.getPublicKey(name);
    }
  }
}
