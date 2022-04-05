/*
 * Copyright 2022 Google LLC
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

// [START kms_v1_generated_keymanagementserviceclient_updatecryptokeyprimaryversion_stringstring_sync]
import com.google.cloud.kms.v1.CryptoKey;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

public class SyncUpdateCryptoKeyPrimaryVersionStringString {

  public static void main(String[] args) throws Exception {
    syncUpdateCryptoKeyPrimaryVersionStringString();
  }

  public static void syncUpdateCryptoKeyPrimaryVersionStringString() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String name =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]").toString();
      String cryptoKeyVersionId = "cryptoKeyVersionId987674581";
      CryptoKey response =
          keyManagementServiceClient.updateCryptoKeyPrimaryVersion(name, cryptoKeyVersionId);
    }
  }
}
// [END kms_v1_generated_keymanagementserviceclient_updatecryptokeyprimaryversion_stringstring_sync]
