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
package com.google.cloud.iam.credentials.v1.samples;

// [START credentials_v1_generated_iamcredentialsclient_signjwt_signjwtrequest]
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import com.google.cloud.iam.credentials.v1.SignJwtRequest;
import com.google.cloud.iam.credentials.v1.SignJwtResponse;
import java.util.ArrayList;

public class SignJwtSignJwtRequest {

  public static void main(String[] args) throws Exception {
    signJwtSignJwtRequest();
  }

  public static void signJwtSignJwtRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      SignJwtRequest request =
          SignJwtRequest.newBuilder()
              .setName(ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]").toString())
              .addAllDelegates(new ArrayList<String>())
              .setPayload("payload-786701938")
              .build();
      SignJwtResponse response = iamCredentialsClient.signJwt(request);
    }
  }
}
// [END credentials_v1_generated_iamcredentialsclient_signjwt_signjwtrequest]