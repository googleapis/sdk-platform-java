package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.GetPublicKeyRequest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.PublicKey;

public class GetPublicKeyGetPublicKeyRequestRequest {

  public static void main(String[] args) throws Exception {
    getPublicKeyGetPublicKeyRequestRequest();
  }

  public static void getPublicKeyGetPublicKeyRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      GetPublicKeyRequest request =
          GetPublicKeyRequest.newBuilder()
              .setName(
                  CryptoKeyVersionName.of(
                          "[PROJECT]",
                          "[LOCATION]",
                          "[KEY_RING]",
                          "[CRYPTO_KEY]",
                          "[CRYPTO_KEY_VERSION]")
                      .toString())
              .build();
      PublicKey response = keyManagementServiceClient.getPublicKey(request);
    }
  }
}
