/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.kms.v1.samples;

// [START 1.0_10_generated_keymanagementserviceclient_decrypt_decryptrequest]
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.DecryptRequest;
import com.google.cloud.kms.v1.DecryptResponse;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;
import com.google.protobuf.Int64Value;

public class DecryptDecryptRequest {

  public static void main(String[] args) throws Exception {
    decryptDecryptRequest();
  }

  public static void decryptDecryptRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
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
// [END 1.0_10_generated_keymanagementserviceclient_decrypt_decryptrequest]