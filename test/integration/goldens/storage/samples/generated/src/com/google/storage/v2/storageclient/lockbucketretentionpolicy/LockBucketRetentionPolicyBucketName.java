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

// [START storage_v2_generated_storageclient_lockbucketretentionpolicy_bucketname_sync]
import com.google.storage.v2.Bucket;
import com.google.storage.v2.BucketName;
import com.google.storage.v2.StorageClient;

public class LockBucketRetentionPolicyBucketName {

  public static void main(String[] args) throws Exception {
    lockBucketRetentionPolicyBucketName();
  }

  public static void lockBucketRetentionPolicyBucketName() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (StorageClient storageClient = StorageClient.create()) {
      BucketName bucket = BucketName.of("[PROJECT]", "[BUCKET]");
      Bucket response = storageClient.lockBucketRetentionPolicy(bucket);
    }
  }
}
// [END storage_v2_generated_storageclient_lockbucketretentionpolicy_bucketname_sync]
