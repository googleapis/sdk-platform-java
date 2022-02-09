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

// [START v2_storage_generated_storageclient_createhmackey_callablefuturecallcreatehmackeyrequest]
import com.google.api.core.ApiFuture;
import com.google.storage.v2.CommonRequestParams;
import com.google.storage.v2.CreateHmacKeyRequest;
import com.google.storage.v2.CreateHmacKeyResponse;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class CreateHmacKeyCallableFutureCallCreateHmacKeyRequest {

  public static void main(String[] args) throws Exception {
    createHmacKeyCallableFutureCallCreateHmacKeyRequest();
  }

  public static void createHmacKeyCallableFutureCallCreateHmacKeyRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (StorageClient storageClient = StorageClient.create()) {
      CreateHmacKeyRequest request =
          CreateHmacKeyRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setServiceAccountEmail("serviceAccountEmail1825953988")
              .setCommonRequestParams(CommonRequestParams.newBuilder().build())
              .build();
      ApiFuture<CreateHmacKeyResponse> future =
          storageClient.createHmacKeyCallable().futureCall(request);
      // Do something.
      CreateHmacKeyResponse response = future.get();
    }
  }
}
// [END v2_storage_generated_storageclient_createhmackey_callablefuturecallcreatehmackeyrequest]