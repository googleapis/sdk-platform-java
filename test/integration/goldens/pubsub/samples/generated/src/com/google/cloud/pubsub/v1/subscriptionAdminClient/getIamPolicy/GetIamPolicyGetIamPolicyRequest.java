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
package com.google.cloud.pubsub.v1.samples;

// [START pubsub_v1_generated_subscriptionadminclient_getiampolicy_getiampolicyrequest]
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.GetPolicyOptions;
import com.google.iam.v1.Policy;
import com.google.pubsub.v1.ProjectName;

public class GetIamPolicyGetIamPolicyRequest {

  public static void main(String[] args) throws Exception {
    getIamPolicyGetIamPolicyRequest();
  }

  public static void getIamPolicyGetIamPolicyRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      GetIamPolicyRequest request =
          GetIamPolicyRequest.newBuilder()
              .setResource(ProjectName.of("[PROJECT]").toString())
              .setOptions(GetPolicyOptions.newBuilder().build())
              .build();
      Policy response = subscriptionAdminClient.getIamPolicy(request);
    }
  }
}
// [END pubsub_v1_generated_subscriptionadminclient_getiampolicy_getiampolicyrequest]