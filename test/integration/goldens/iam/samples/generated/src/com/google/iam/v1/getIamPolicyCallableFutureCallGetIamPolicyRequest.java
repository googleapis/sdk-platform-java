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

// [START v1_iam_generated_iampolicyclient_getiampolicy_callablefuturecallgetiampolicyrequest]
import com.google.api.core.ApiFuture;
import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.GetPolicyOptions;
import com.google.iam.v1.IAMPolicyClient;
import com.google.iam.v1.Policy;

public class GetIamPolicyCallableFutureCallGetIamPolicyRequest {

  public static void main(String[] args) throws Exception {
    getIamPolicyCallableFutureCallGetIamPolicyRequest();
  }

  public static void getIamPolicyCallableFutureCallGetIamPolicyRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (IAMPolicyClient iAMPolicyClient = IAMPolicyClient.create()) {
      GetIamPolicyRequest request =
          GetIamPolicyRequest.newBuilder()
              .setResource("GetIamPolicyRequest-1527610370".toString())
              .setOptions(GetPolicyOptions.newBuilder().build())
              .build();
      ApiFuture<Policy> future = iAMPolicyClient.getIamPolicyCallable().futureCall(request);
      // Do something.
      Policy response = future.get();
    }
  }
}
// [END v1_iam_generated_iampolicyclient_getiampolicy_callablefuturecallgetiampolicyrequest]