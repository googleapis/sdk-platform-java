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
package com.google.storage.v2.samples;

// [START 10_10_generated_storageClient_createBucket_createBucketRequest]
import com.google.storage.v2.Bucket;
import com.google.storage.v2.CreateBucketRequest;
import com.google.storage.v2.PredefinedBucketAcl;
import com.google.storage.v2.PredefinedObjectAcl;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class CreateBucketCreateBucketRequest {

  public static void main(String[] args) throws Exception {
    createBucketCreateBucketRequest();
  }

  public static void createBucketCreateBucketRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (StorageClient storageClient = StorageClient.create()) {
      CreateBucketRequest request =
          CreateBucketRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setBucket(Bucket.newBuilder().build())
              .setBucketId("bucketId-1603305307")
              .setPredefinedAcl(PredefinedBucketAcl.forNumber(0))
              .setPredefinedDefaultObjectAcl(PredefinedObjectAcl.forNumber(0))
              .build();
      Bucket response = storageClient.createBucket(request);
    }
  }
}
// [END 10_10_generated_storageClient_createBucket_createBucketRequest]