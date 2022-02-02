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
import com.google.pubsub.v1.PublishResponse;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import java.util.ArrayList;
import java.util.List;

public class PublishTopicNameTopicListPubsubMessageMessages {

  public static void main(String[] args) throws Exception {
    publishTopicNameTopicListPubsubMessageMessages();
  }

  public static void publishTopicNameTopicListPubsubMessageMessages() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TopicName topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]");
      List<PubsubMessage> messages = new ArrayList<>();
      PublishResponse response = topicAdminClient.publish(topic, messages);
    }
  }
}
// [END REGION TAG]