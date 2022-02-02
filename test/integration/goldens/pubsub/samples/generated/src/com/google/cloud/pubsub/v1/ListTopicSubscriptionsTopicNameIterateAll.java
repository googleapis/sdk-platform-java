package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.TopicName;

public class ListTopicSubscriptionsTopicNameIterateAll {

  public static void main(String[] args) throws Exception {
    listTopicSubscriptionsTopicNameIterateAll();
  }

  public static void listTopicSubscriptionsTopicNameIterateAll() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TopicName topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]");
      for (String element : topicAdminClient.listTopicSubscriptions(topic).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
