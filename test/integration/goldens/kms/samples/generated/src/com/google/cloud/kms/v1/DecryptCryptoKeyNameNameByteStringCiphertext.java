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

// [START REGION TAG]
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.DecryptResponse;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;

public class DecryptCryptoKeyNameNameByteStringCiphertext {

  public static void main(String[] args) throws Exception {
    decryptCryptoKeyNameNameByteStringCiphertext();
  }

  public static void decryptCryptoKeyNameNameByteStringCiphertext() throws Exception {
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CryptoKeyName name =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]");
      ByteString ciphertext = ByteString.EMPTY;
      DecryptResponse response = keyManagementServiceClient.decrypt(name, ciphertext);
    }
  }
}
// [END REGION TAG]