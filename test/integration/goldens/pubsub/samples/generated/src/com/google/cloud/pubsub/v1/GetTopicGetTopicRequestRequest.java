package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.GetTopicRequest;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;

public class GetTopicGetTopicRequestRequest {

  public static void main(String[] args) throws Exception {
    getTopicGetTopicRequestRequest();
  }

  public static void getTopicGetTopicRequestRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      GetTopicRequest request =
          GetTopicRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .build();
      Topic response = topicAdminClient.getTopic(request);
    }
  }
}
