package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.TopicName;

public class ListTopicSubscriptionsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listTopicSubscriptionsStringIterateAll();
  }

  public static void listTopicSubscriptionsStringIterateAll() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      String topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString();
      for (String element : topicAdminClient.listTopicSubscriptions(topic).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
