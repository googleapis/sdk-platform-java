package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.TopicName;

public class ListTopicSnapshotsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listTopicSnapshotsStringIterateAll();
  }

  public static void listTopicSnapshotsStringIterateAll() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      String topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString();
      for (String element : topicAdminClient.listTopicSnapshots(topic).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
