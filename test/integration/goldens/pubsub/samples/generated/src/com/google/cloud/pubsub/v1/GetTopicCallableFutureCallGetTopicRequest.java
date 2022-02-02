package com.google.cloud.pubsub.v1.samples;

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
