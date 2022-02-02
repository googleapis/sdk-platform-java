package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.DeleteTopicRequest;
import com.google.pubsub.v1.TopicName;

public class DeleteTopicDeleteTopicRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteTopicDeleteTopicRequestRequest();
  }

  public static void deleteTopicDeleteTopicRequestRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      DeleteTopicRequest request =
          DeleteTopicRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .build();
      topicAdminClient.deleteTopic(request);
    }
  }
}
