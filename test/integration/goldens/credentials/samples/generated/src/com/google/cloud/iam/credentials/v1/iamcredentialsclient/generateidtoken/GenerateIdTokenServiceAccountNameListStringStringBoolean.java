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

// [START credentials_v1_generated_iamcredentialsclient_generateidtoken_serviceaccountnameliststringstringboolean]
import com.google.cloud.iam.credentials.v1.GenerateIdTokenResponse;
import com.google.cloud.iam.credentials.v1.IamCredentialsClient;
import com.google.cloud.iam.credentials.v1.ServiceAccountName;
import java.util.ArrayList;
import java.util.List;

public class GenerateIdTokenServiceAccountNameListStringStringBoolean {

  public static void main(String[] args) throws Exception {
    generateIdTokenServiceAccountNameListStringStringBoolean();
  }

  public static void generateIdTokenServiceAccountNameListStringStringBoolean() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (IamCredentialsClient iamCredentialsClient = IamCredentialsClient.create()) {
      ServiceAccountName name = ServiceAccountName.of("[PROJECT]", "[SERVICE_ACCOUNT]");
      List<String> delegates = new ArrayList<>();
      String audience = "audience975628804";
      boolean includeEmail = true;
      GenerateIdTokenResponse response =
          iamCredentialsClient.generateIdToken(name, delegates, audience, includeEmail);
    }
  }
}
// [END credentials_v1_generated_iamcredentialsclient_generateidtoken_serviceaccountnameliststringstringboolean]
