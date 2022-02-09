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

// [START v1_kms_generated_keymanagementserviceclient_getkeyring_string]
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRing;
import com.google.cloud.kms.v1.KeyRingName;

public class GetKeyRingString {

  public static void main(String[] args) throws Exception {
    getKeyRingString();
  }

  public static void getKeyRingString() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      String name = KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString();
      KeyRing response = keyManagementServiceClient.getKeyRing(name);
    }
  }
}
// [END v1_kms_generated_keymanagementserviceclient_getkeyring_string]