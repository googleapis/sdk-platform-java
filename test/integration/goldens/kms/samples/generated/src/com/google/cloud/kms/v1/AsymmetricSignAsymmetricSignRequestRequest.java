package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.AsymmetricSignRequest;
import com.google.cloud.kms.v1.AsymmetricSignResponse;
import com.google.cloud.kms.v1.CryptoKeyVersionName;
import com.google.cloud.kms.v1.Digest;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.Int64Value;

public class AsymmetricSignAsymmetricSignRequestRequest {

  public static void main(String[] args) throws Exception {
    asymmetricSignAsymmetricSignRequestRequest();
  }

  public static void asymmetricSignAsymmetricSignRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      AsymmetricSignRequest request =
          AsymmetricSignRequest.newBuilder()
              .setName(
                  CryptoKeyVersionName.of(
                          "[PROJECT]",
                          "[LOCATION]",
                          "[KEY_RING]",
                          "[CRYPTO_KEY]",
                          "[CRYPTO_KEY_VERSION]")
                      .toString())
              .setDigest(Digest.newBuilder().build())
              .setDigestCrc32C(Int64Value.newBuilder().build())
              .build();
      AsymmetricSignResponse response = keyManagementServiceClient.asymmetricSign(request);
    }
  }
}
