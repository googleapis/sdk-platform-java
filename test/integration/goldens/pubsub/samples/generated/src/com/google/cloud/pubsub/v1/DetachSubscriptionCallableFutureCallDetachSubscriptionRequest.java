package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.DetachSubscriptionRequest;
import com.google.pubsub.v1.DetachSubscriptionResponse;
import com.google.pubsub.v1.SubscriptionName;

public class DetachSubscriptionCallableFutureCallDetachSubscriptionRequest {

  public static void main(String[] args) throws Exception {
    detachSubscriptionCallableFutureCallDetachSubscriptionRequest();
  }

  public static void detachSubscriptionCallableFutureCallDetachSubscriptionRequest()
      throws Exception {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      DetachSubscriptionRequest request =
          DetachSubscriptionRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .build();
      ApiFuture<DetachSubscriptionResponse> future =
          topicAdminClient.detachSubscriptionCallable().futureCall(request);
      // Do something.
      DetachSubscriptionResponse response = future.get();
    }
  }
}
