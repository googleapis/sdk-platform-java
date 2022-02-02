package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ListTopicSubscriptionsRequest;
import com.google.pubsub.v1.TopicName;

public class ListTopicSubscriptionsListTopicSubscriptionsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listTopicSubscriptionsListTopicSubscriptionsRequestIterateAll();
  }

  public static void listTopicSubscriptionsListTopicSubscriptionsRequestIterateAll()
      throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      ListTopicSubscriptionsRequest request =
          ListTopicSubscriptionsRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (String element : topicAdminClient.listTopicSubscriptions(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
