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

package com.google.cloud.kms.v1.samples;

// [START kms_v1_generated_keymanagementserviceclient_testiampermissions_async]
import com.google.api.core.ApiFuture;
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.iam.v1.TestIamPermissionsRequest;
import com.google.iam.v1.TestIamPermissionsResponse;
import java.util.ArrayList;

public class AsyncTestIamPermissions {

  public static void main(String[] args) throws Exception {
    asyncTestIamPermissions();
  }

  public static void asyncTestIamPermissions() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (KeyManagementServiceClient keyManagementServiceClient =
        KeyManagementServiceClient.create()) {
      TestIamPermissionsRequest request =
          TestIamPermissionsRequest.newBuilder()
              .setResource(
                  CryptoKeyName.of("[PROJECT]", "[LOCATION]", "[KEY_RING]", "[CRYPTO_KEY]")
                      .toString())
              .addAllPermissions(new ArrayList<String>())
              .build();
      ApiFuture<TestIamPermissionsResponse> future =
          keyManagementServiceClient.testIamPermissionsCallable().futureCall(request);
      // Do something.
      TestIamPermissionsResponse response = future.get();
    }
  }
}
// [END kms_v1_generated_keymanagementserviceclient_testiampermissions_async]
