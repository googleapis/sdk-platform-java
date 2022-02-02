package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;

public class CreateTopicStringName {

  public static void main(String[] args) throws Exception {
    createTopicStringName();
  }

  public static void createTopicStringName() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      String name = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString();
      Topic response = topicAdminClient.createTopic(name);
    }
  }
}
