package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;

public class GetTopicStringTopic {

  public static void main(String[] args) throws Exception {
    getTopicStringTopic();
  }

  public static void getTopicStringTopic() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      String topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString();
      Topic response = topicAdminClient.getTopic(topic);
    }
  }
}
