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

// [START 1.0_10_generated_topicadminclient_gettopic_callablefuturecallgettopicrequest]
import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.GetTopicRequest;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;

public class GetTopicCallableFutureCallGetTopicRequest {

  public static void main(String[] args) throws Exception {
    getTopicCallableFutureCallGetTopicRequest();
  }

  public static void getTopicCallableFutureCallGetTopicRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      GetTopicRequest request =
          GetTopicRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .build();
      ApiFuture<Topic> future = topicAdminClient.getTopicCallable().futureCall(request);
      // Do something.
      Topic response = future.get();
    }
  }
}
// [END 1.0_10_generated_topicadminclient_gettopic_callablefuturecallgettopicrequest]