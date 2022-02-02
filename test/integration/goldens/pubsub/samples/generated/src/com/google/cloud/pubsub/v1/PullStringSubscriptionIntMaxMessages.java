package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.PullResponse;
import com.google.pubsub.v1.SubscriptionName;

public class PullStringSubscriptionIntMaxMessages {

  public static void main(String[] args) throws Exception {
    pullStringSubscriptionIntMaxMessages();
  }

  public static void pullStringSubscriptionIntMaxMessages() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString();
      int maxMessages = 496131527;
      PullResponse response = subscriptionAdminClient.pull(subscription, maxMessages);
    }
  }
}
