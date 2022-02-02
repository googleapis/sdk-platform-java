package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ListTopicSubscriptionsRequest;
import com.google.pubsub.v1.TopicName;

public class ListTopicSubscriptionsPagedCallableFutureCallListTopicSubscriptionsRequest {

  public static void main(String[] args) throws Exception {
    listTopicSubscriptionsPagedCallableFutureCallListTopicSubscriptionsRequest();
  }

  public static void listTopicSubscriptionsPagedCallableFutureCallListTopicSubscriptionsRequest()
      throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      ListTopicSubscriptionsRequest request =
          ListTopicSubscriptionsRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<String> future =
          topicAdminClient.listTopicSubscriptionsPagedCallable().futureCall(request);
      // Do something.
      for (String element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
