package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.TopicName;

public class DeleteTopicTopicNameTopic {

  public static void main(String[] args) throws Exception {
    deleteTopicTopicNameTopic();
  }

  public static void deleteTopicTopicNameTopic() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TopicName topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]");
      topicAdminClient.deleteTopic(topic);
    }
  }
}
