package com.google.cloud.kms.v1.samples;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.DecryptRequest;
import com.google.cloud.kms.v1.DecryptResponse;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;
import com.google.protobuf.Int64Value;

public class DecryptDecryptRequestRequest {

  public static void main(String[] args) throws Exception {
    decryptDecryptRequestRequest();
  }

  public static void decryptDecryptRequestRequest() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      DecryptRequest request =
          DecryptRequest.newBuilder()
              .setName(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setCiphertext(ByteString.EMPTY)
              .setAdditionalAuthenticatedData(ByteString.EMPTY)
              .setCiphertextCrc32C(Int64Value.newBuilder().build())
              .setAdditionalAuthenticatedDataCrc32C(Int64Value.newBuilder().build())
              .build();
      DecryptResponse response = keyManagementServiceClient.decrypt(request);
    }
  }
}
