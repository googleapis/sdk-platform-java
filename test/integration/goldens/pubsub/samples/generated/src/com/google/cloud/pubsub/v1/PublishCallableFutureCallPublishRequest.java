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
import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.PublishRequest;
import com.google.pubsub.v1.PublishResponse;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import java.util.ArrayList;

public class PublishCallableFutureCallPublishRequest {

  public static void main(String[] args) throws Exception {
    publishCallableFutureCallPublishRequest();
  }

  public static void publishCallableFutureCallPublishRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      PublishRequest request =
          PublishRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .addAllMessages(new ArrayList<PubsubMessage>())
              .build();
      ApiFuture<PublishResponse> future = topicAdminClient.publishCallable().futureCall(request);
      // Do something.
      PublishResponse response = future.get();
    }
  }
}
// [END REGION TAG]