package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.common.base.Strings;
import com.google.pubsub.v1.ListTopicSubscriptionsRequest;
import com.google.pubsub.v1.ListTopicSubscriptionsResponse;
import com.google.pubsub.v1.TopicName;

public class ListTopicSubscriptionsCallableCallListTopicSubscriptionsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listTopicSubscriptionsCallableCallListTopicSubscriptionsRequestSetPageToken();
  }

  public static void listTopicSubscriptionsCallableCallListTopicSubscriptionsRequestSetPageToken()
      throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      ListTopicSubscriptionsRequest request =
          ListTopicSubscriptionsRequest.newBuilder()
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      while (true) {
        ListTopicSubscriptionsResponse response =
            topicAdminClient.listTopicSubscriptionsCallable().call(request);
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
