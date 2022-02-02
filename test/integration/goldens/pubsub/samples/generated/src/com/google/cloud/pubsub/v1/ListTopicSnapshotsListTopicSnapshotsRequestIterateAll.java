package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ListTopicSnapshotsRequest;
import com.google.pubsub.v1.TopicName;

public class ListTopicSnapshotsListTopicSnapshotsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listTopicSnapshotsListTopicSnapshotsRequestIterateAll();
  }

  public static void listTopicSnapshotsListTopicSnapshotsRequestIterateAll() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      ListTopicSnapshotsRequest request =
          ListTopicSnapshotsRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (String element : topicAdminClient.listTopicSnapshots(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
