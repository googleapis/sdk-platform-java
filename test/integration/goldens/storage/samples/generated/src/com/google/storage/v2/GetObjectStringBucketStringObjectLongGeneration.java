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

// [START REGION TAG]
import com.google.storage.v2.Object;
import com.google.storage.v2.StorageClient;

public class GetObjectStringBucketStringObjectLongGeneration {

  public static void main(String[] args) throws Exception {
    getObjectStringBucketStringObjectLongGeneration();
  }

  public static void getObjectStringBucketStringObjectLongGeneration() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String bucket = "bucket-1378203158";
      String object = "object-1023368385";
      long generation = 305703192;
      Object response = storageClient.getObject(bucket, object, generation);
    }
  }
}
// [END REGION TAG]