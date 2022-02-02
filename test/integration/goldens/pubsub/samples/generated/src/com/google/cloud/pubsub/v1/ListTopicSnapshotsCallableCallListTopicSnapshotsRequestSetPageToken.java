package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.common.base.Strings;
import com.google.pubsub.v1.ListTopicSnapshotsRequest;
import com.google.pubsub.v1.ListTopicSnapshotsResponse;
import com.google.pubsub.v1.TopicName;

public class ListTopicSnapshotsCallableCallListTopicSnapshotsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listTopicSnapshotsCallableCallListTopicSnapshotsRequestSetPageToken();
  }

  public static void listTopicSnapshotsCallableCallListTopicSnapshotsRequestSetPageToken()
      throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      ListTopicSnapshotsRequest request =
          ListTopicSnapshotsRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      while (true) {
        ListTopicSnapshotsResponse response =
            topicAdminClient.listTopicSnapshotsCallable().call(request);
        for (String element : response.getResponsesList()) {
          // doThingsWith(element);
        }
        String nextPageToken = response.getNextPageToken();
        if (!Strings.isNullOrEmpty(nextPageToken)) {
          request = request.toBuilder().setPageToken(nextPageToken).build();
        } else {
          break;
        }
      }
    }
  }
}
