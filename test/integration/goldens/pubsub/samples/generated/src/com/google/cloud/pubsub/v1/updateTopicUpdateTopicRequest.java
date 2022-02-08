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

// [START 10_10_generated_topicAdminClient_updateTopic_updateTopicRequest]
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.protobuf.FieldMask;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.UpdateTopicRequest;

public class UpdateTopicUpdateTopicRequest {

  public static void main(String[] args) throws Exception {
    updateTopicUpdateTopicRequest();
  }

  public static void updateTopicUpdateTopicRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      UpdateTopicRequest request =
          UpdateTopicRequest.newBuilder()
              .setTopic(Topic.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      Topic response = topicAdminClient.updateTopic(request);
    }
  }
}
// [END 10_10_generated_topicAdminClient_updateTopic_updateTopicRequest]