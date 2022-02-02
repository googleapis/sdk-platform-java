package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;

public class CreateTopicTopicNameName {

  public static void main(String[] args) throws Exception {
    createTopicTopicNameName();
  }

  public static void createTopicTopicNameName() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TopicName name = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]");
      Topic response = topicAdminClient.createTopic(name);
    }
  }
}
