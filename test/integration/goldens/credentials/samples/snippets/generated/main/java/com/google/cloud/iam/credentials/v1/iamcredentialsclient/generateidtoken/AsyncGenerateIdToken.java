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

package com.google.cloud.iam.credentials.v1.samples;

// [START credentials_v1_generated_iamcredentialsclient_generateidtoken_async]
import com.google.api.core.ApiFuture;
import com.google.cloud.iam.credentials.v1.GenerateIdTokenRequest;
import com.google.cloud.iam.credentials.v1.GenerateIdTokenResponse;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import java.util.ArrayList;

public class AsyncGenerateIdToken {

  public static void main(String[] args) throws Exception {
    asyncGenerateIdToken();
  }

  public static void asyncGenerateIdToken() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      GenerateIdTokenRequest request =
          GenerateIdTokenRequest.newBuilder()
              .setName(ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]").toString())
              .addAllDelegates(new ArrayList<String>())
              .setAudience("audience975628804")
              .setIncludeEmail(true)
              .build();
      ApiFuture<GenerateIdTokenResponse> future =
          iamCredentialsClient.generateIdTokenCallable().futureCall(request);
      // Do something.
      GenerateIdTokenResponse response = future.get();
    }
  }
}
// [END credentials_v1_generated_iamcredentialsclient_generateidtoken_async]
