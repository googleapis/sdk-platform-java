package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;

public class GetSubscriptionStringSubscription {

  public static void main(String[] args) throws Exception {
    getSubscriptionStringSubscription();
  }

  public static void getSubscriptionStringSubscription() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString();
      Subscription response = subscriptionAdminClient.getSubscription(subscription);
    }
  }
}
