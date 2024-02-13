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

// [START pubsub_v1_generated_Publisher_ListTopicSnapshots_sync]
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ListTopicSnapshotsRequest;
import com.google.pubsub.v1.TopicName;

public class SyncListTopicSnapshots {

  public static void main(String[] args) throws Exception {
    syncListTopicSnapshots();
  }

  public static void syncListTopicSnapshots() throws Exception {
    // This snippet has been automatically generated and should be regarded as a code template only.
    // It will require modifications to work:
    // - It may require correct/in-range values for request initialization.
    // - It may require specifying regional endpoints when creating the service client as shown in
    // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      ListTopicSnapshotsRequest request =
          ListTopicSnapshotsRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (String element : topicAdminClient.listTopicSnapshots(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
// [END pubsub_v1_generated_Publisher_ListTopicSnapshots_sync]
