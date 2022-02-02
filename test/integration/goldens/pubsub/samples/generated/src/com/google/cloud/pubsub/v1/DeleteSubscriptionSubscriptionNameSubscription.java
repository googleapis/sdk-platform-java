package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.SubscriptionName;

public class DeleteSubscriptionSubscriptionNameSubscription {

  public static void main(String[] args) throws Exception {
    deleteSubscriptionSubscriptionNameSubscription();
  }

  public static void deleteSubscriptionSubscriptionNameSubscription() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      SubscriptionName subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]");
      subscriptionAdminClient.deleteSubscription(subscription);
    }
  }
}
