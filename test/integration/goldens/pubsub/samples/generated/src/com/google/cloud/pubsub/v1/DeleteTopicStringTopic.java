package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.TopicName;

public class DeleteTopicStringTopic {

  public static void main(String[] args) throws Exception {
    deleteTopicStringTopic();
  }

  public static void deleteTopicStringTopic() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      String topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString();
      topicAdminClient.deleteTopic(topic);
    }
  }
}
