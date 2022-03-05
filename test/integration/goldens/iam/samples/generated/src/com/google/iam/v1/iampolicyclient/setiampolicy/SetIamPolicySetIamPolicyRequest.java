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

package com.google.iam.v1.samples;

// [START iam_v1_generated_iampolicyclient_setiampolicy_setiampolicyrequest]
import com.google.iam.v1.IAMPolicyClient;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;

public class SetIamPolicySetIamPolicyRequest {

  public static void main(String[] args) throws Exception {
    setIamPolicySetIamPolicyRequest();
  }

  public static void setIamPolicySetIamPolicyRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
      SetIamPolicyRequest request =
          SetIamPolicyRequest.newBuilder()
              .setResource("SetIamPolicyRequest1223629066".toString())
              .setPolicy(Policy.newBuilder().build())
              .build();
      Policy response = iAMPolicyClient.setIamPolicy(request);
    }
  }
}
// [END iam_v1_generated_iampolicyclient_setiampolicy_setiampolicyrequest]
