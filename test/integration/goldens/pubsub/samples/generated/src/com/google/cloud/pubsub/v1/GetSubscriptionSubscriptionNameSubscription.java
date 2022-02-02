package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;

public class GetSubscriptionSubscriptionNameSubscription {

  public static void main(String[] args) throws Exception {
    getSubscriptionSubscriptionNameSubscription();
  }

  public static void getSubscriptionSubscriptionNameSubscription() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      SubscriptionName subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]");
      Subscription response = subscriptionAdminClient.getSubscription(subscription);
    }
  }
}
