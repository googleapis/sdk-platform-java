package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.DeleteSubscriptionRequest;
import com.google.pubsub.v1.SubscriptionName;

public class DeleteSubscriptionCallableFutureCallDeleteSubscriptionRequest {

  public static void main(String[] args) throws Exception {
    deleteSubscriptionCallableFutureCallDeleteSubscriptionRequest();
  }

  public static void deleteSubscriptionCallableFutureCallDeleteSubscriptionRequest()
      throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      DeleteSubscriptionRequest request =
          DeleteSubscriptionRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .build();
      ApiFuture<Empty> future =
          subscriptionAdminClient.deleteSubscriptionCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
