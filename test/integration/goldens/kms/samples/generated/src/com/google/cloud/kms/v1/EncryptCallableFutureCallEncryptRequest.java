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

// [START 10_10_generated_keyManagementServiceClient_encryptCallable_futureCallEncryptRequest]
import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.EncryptRequest;
import com.google.cloud.kms.v1.EncryptResponse;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;
import com.google.protobuf.Int64Value;

public class EncryptCallableFutureCallEncryptRequest {

  public static void main(String[] args) throws Exception {
    encryptCallableFutureCallEncryptRequest();
  }

  public static void encryptCallableFutureCallEncryptRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      EncryptRequest request =
          EncryptRequest.newBuilder()
              .setName(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setPlaintext(ByteString.EMPTY)
              .setAdditionalAuthenticatedData(ByteString.EMPTY)
              .setPlaintextCrc32C(Int64Value.newBuilder().build())
              .setAdditionalAuthenticatedDataCrc32C(Int64Value.newBuilder().build())
              .build();
      ApiFuture<EncryptResponse> future =
          keyManagementServiceClient.encryptCallable().futureCall(request);
      // Do something.
      EncryptResponse response = future.get();
    }
  }
}
// [END 10_10_generated_keyManagementServiceClient_encryptCallable_futureCallEncryptRequest]