package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.TopicName;

public class ListTopicSnapshotsTopicNameIterateAll {

  public static void main(String[] args) throws Exception {
    listTopicSnapshotsTopicNameIterateAll();
  }

  public static void listTopicSnapshotsTopicNameIterateAll() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TopicName topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]");
      for (String element : topicAdminClient.listTopicSnapshots(topic).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
