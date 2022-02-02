package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.SubscriptionName;

public class DeleteSubscriptionStringSubscription {

  public static void main(String[] args) throws Exception {
    deleteSubscriptionStringSubscription();
  }

  public static void deleteSubscriptionStringSubscription() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString();
      subscriptionAdminClient.deleteSubscription(subscription);
    }
  }
}
