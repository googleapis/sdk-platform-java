package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.GetSubscriptionRequest;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;

public class GetSubscriptionCallableFutureCallGetSubscriptionRequest {

  public static void main(String[] args) throws Exception {
    getSubscriptionCallableFutureCallGetSubscriptionRequest();
  }

  public static void getSubscriptionCallableFutureCallGetSubscriptionRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      GetSubscriptionRequest request =
          GetSubscriptionRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .build();
      ApiFuture<Subscription> future =
          subscriptionAdminClient.getSubscriptionCallable().futureCall(request);
      // Do something.
      Subscription response = future.get();
    }
  }
}
