package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.DetachSubscriptionRequest;
import com.google.pubsub.v1.DetachSubscriptionResponse;
import com.google.pubsub.v1.SubscriptionName;

public class DetachSubscriptionDetachSubscriptionRequestRequest {

  public static void main(String[] args) throws Exception {
    detachSubscriptionDetachSubscriptionRequestRequest();
  }

  public static void detachSubscriptionDetachSubscriptionRequestRequest() throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      DetachSubscriptionRequest request =
          DetachSubscriptionRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .build();
      DetachSubscriptionResponse response = topicAdminClient.detachSubscription(request);
    }
  }
}
