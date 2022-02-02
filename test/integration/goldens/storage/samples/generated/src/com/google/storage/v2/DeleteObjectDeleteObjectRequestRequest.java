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
import com.google.protobuf.Empty;
import com.google.storage.v2.CommonObjectRequestParams;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.DeleteObjectRequest;
import com.google.storage.v2.StorageClient;

public class DeleteObjectDeleteObjectRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteObjectDeleteObjectRequestRequest();
  }

  public static void deleteObjectDeleteObjectRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      DeleteObjectRequest request =
          DeleteObjectRequest.newBuilder()
              .setBucket("bucket-1378203158")
              .setObject("object-1023368385")
              .setUploadId("uploadId1563990780")
              .setGeneration(305703192)
              .setIfGenerationMatch(-1086241088)
              .setIfGenerationNotMatch(1475720404)
              .setIfMetagenerationMatch(1043427781)
              .setIfMetagenerationNotMatch(1025430873)
              .setCommonObjectRequestParams(CommonObjectRequestParams.newBuilder().build())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      storageClient.deleteObject(request);
    }
  }
}
// [END REGION TAG]