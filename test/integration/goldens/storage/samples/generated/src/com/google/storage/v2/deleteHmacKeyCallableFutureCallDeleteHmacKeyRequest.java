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

// [START v2_storage_generated_storageclient_deletehmackey_callablefuturecalldeletehmackeyrequest]
import com.google.api.core.ApiFuture;
import com.google.protobuf.Empty;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.DeleteHmacKeyRequest;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class DeleteHmacKeyCallableFutureCallDeleteHmacKeyRequest {

  public static void main(String[] args) throws Exception {
    deleteHmacKeyCallableFutureCallDeleteHmacKeyRequest();
  }

  public static void deleteHmacKeyCallableFutureCallDeleteHmacKeyRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (StorageClient storageClient = StorageClient.create()) {
      DeleteHmacKeyRequest request =
          DeleteHmacKeyRequest.newBuilder()
              .setAccessId("accessId-2146437729")
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<Empty> future = storageClient.deleteHmacKeyCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
// [END v2_storage_generated_storageclient_deletehmackey_callablefuturecalldeletehmackeyrequest]