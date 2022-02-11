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

// [START 1.0_10_generated_keymanagementserviceclient_updatecryptokeyprimaryversion_updatecryptokeyprimaryversionrequest]
import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.UpdateCryptoKeyPrimaryVersionRequest;

public class UpdateCryptoKeyPrimaryVersionUpdateCryptoKeyPrimaryVersionRequest {

  public static void main(String[] args) throws Exception {
    updateCryptoKeyPrimaryVersionUpdateCryptoKeyPrimaryVersionRequest();
  }

  public static void updateCryptoKeyPrimaryVersionUpdateCryptoKeyPrimaryVersionRequest()
      throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      UpdateCryptoKeyPrimaryVersionRequest request =
          UpdateCryptoKeyPrimaryVersionRequest.newBuilder()
              .setName(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .setCryptoKeyVersionId("cryptoKeyVersionId987674581")
              .build();
      CryptoKey response = keyManagementServiceClient.updateCryptoKeyPrimaryVersion(request);
    }
  }
}
// [END 1.0_10_generated_keymanagementserviceclient_updatecryptokeyprimaryversion_updatecryptokeyprimaryversionrequest]