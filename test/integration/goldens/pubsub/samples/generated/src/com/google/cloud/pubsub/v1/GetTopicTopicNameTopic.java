package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;

public class GetTopicTopicNameTopic {

  public static void main(String[] args) throws Exception {
    getTopicTopicNameTopic();
  }

  public static void getTopicTopicNameTopic() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TopicName topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]");
      Topic response = topicAdminClient.getTopic(topic);
    }
  }
}
