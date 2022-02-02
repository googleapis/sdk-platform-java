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

// [START REGION TAG]
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.GetPolicyOptions;
import com.google.iam.v1.Policy;
import com.google.pubsub.v1.ProjectName;

public class GetIamPolicyGetIamPolicyRequestRequest {

  public static void main(String[] args) throws Exception {
    getIamPolicyGetIamPolicyRequestRequest();
  }

  public static void getIamPolicyGetIamPolicyRequestRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      GetIamPolicyRequest request =
          GetIamPolicyRequest.newBuilder()
              .setResource(ProjectName.of("[PROJECT]").toString())
              .setOptions(GetPolicyOptions.newBuilder().build())
              .build();
      Policy response = topicAdminClient.getIamPolicy(request);
    }
  }
}
// [END REGION TAG]