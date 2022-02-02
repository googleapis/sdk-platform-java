package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.GetSubscriptionRequest;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;

public class GetSubscriptionGetSubscriptionRequestRequest {

  public static void main(String[] args) throws Exception {
    getSubscriptionGetSubscriptionRequestRequest();
  }

  public static void getSubscriptionGetSubscriptionRequestRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      GetSubscriptionRequest request =
          GetSubscriptionRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .build();
      Subscription response = subscriptionAdminClient.getSubscription(request);
    }
  }
}
