package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ListTopicSnapshotsRequest;
import com.google.pubsub.v1.TopicName;

public class ListTopicSnapshotsPagedCallableFutureCallListTopicSnapshotsRequest {

  public static void main(String[] args) throws Exception {
    listTopicSnapshotsPagedCallableFutureCallListTopicSnapshotsRequest();
  }

  public static void listTopicSnapshotsPagedCallableFutureCallListTopicSnapshotsRequest()
      throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      ListTopicSnapshotsRequest request =
          ListTopicSnapshotsRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<String> future =
          topicAdminClient.listTopicSnapshotsPagedCallable().futureCall(request);
      // Do something.
      for (String element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
