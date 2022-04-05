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

package com.google.storage.v2.samples;

// [START storage_v2_generated_storageclient_getiampolicy_resourcename_sync]
import com.google.api.resourcenames.ResourceName;
import com.google.iam.v1.Policy;
import com.google.storage.v2.CryptoKeyName;
import com.google.storage.v2.StorageClient;

public class SyncGetIamPolicyResourcename {

  public static void main(String[] args) throws Exception {
    syncGetIamPolicyResourcename();
  }

  public static void syncGetIamPolicyResourcename() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (StorageClient storageClient = StorageClient.create()) {
      ResourceName resource =
          CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]");
      Policy response = storageClient.getIamPolicy(resource);
    }
  }
}
// [END storage_v2_generated_storageclient_getiampolicy_resourcename_sync]
