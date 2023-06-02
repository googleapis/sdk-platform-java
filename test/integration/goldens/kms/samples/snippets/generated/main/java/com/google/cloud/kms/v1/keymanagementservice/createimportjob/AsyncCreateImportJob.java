/*
 * Copyright 2023 Google LLC
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

// [START cloudkms_v1_generated_KeyManagementService_CreateImportJob_async]
import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CreateImportJobRequest;
import com.google.cloud.kms.v1.ImportJob;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.cloud.kms.v1.KeyRingName;

public class AsyncCreateImportJob {

  public static void main(String[] args) throws Exception {
    asyncCreateImportJob();
  }

  public static void asyncCreateImportJob() throws Exception {
    // This snippet has been automatically generated and should be regarded as a code template only.
    // It will require modifications to work:
    // - It may require correct/in-range values for request initialization.
    // - It may require specifying regional endpoints when creating the service client as shown in
    // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      CreateImportJobRequest request =
          CreateImportJobRequest.newBuilder()
              .setParent(KeyRingName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]").toString())
              .setImportJobId("importJobId1449444627")
              .setImportJob(ImportJob.newBuilder().build())
              .build();
      ApiFuture<ImportJob> future =
          keyManagementServiceClient.createImportJobCallable().futureCall(request);
      // Do something.
      ImportJob response = future.get();
    }
  }
}
// [END cloudkms_v1_generated_KeyManagementService_CreateImportJob_async]
