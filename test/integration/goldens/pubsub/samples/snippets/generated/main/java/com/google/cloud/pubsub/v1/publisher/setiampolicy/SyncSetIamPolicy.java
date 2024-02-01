/*
 * Copyright 2024 Google LLC
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

// [START pubsub_v1_generated_Publisher_SetIamPolicy_sync]
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;
import com.google.protobuf.FieldMask;
import com.google.pubsub.v1.SnapshotName;

public class SyncSetIamPolicy {

  public static void main(String[] args) throws Exception {
    syncSetIamPolicy();
  }

  public static void syncSetIamPolicy() throws Exception {
    // This snippet has been automatically generated and should be regarded as a code template only.
    // It will require modifications to work:
    // - It may require correct/in-range values for request initialization.
    // - It may require specifying regional endpoints when creating the service client as shown in
    // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      SetIamPolicyRequest request =
          SetIamPolicyRequest.newBuilder()
              .setResource(SnapshotName.of("[PROJECT]", "[SNAPSHOT]").toString())
              .setPolicy(Policy.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      Policy response = topicAdminClient.setIamPolicy(request);
    }
  }
}
// [END pubsub_v1_generated_Publisher_SetIamPolicy_sync]
