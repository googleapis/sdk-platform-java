package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.PullResponse;
import com.google.pubsub.v1.SubscriptionName;

public class PullSubscriptionNameSubscriptionIntMaxMessages {

  public static void main(String[] args) throws Exception {
    pullSubscriptionNameSubscriptionIntMaxMessages();
  }

  public static void pullSubscriptionNameSubscriptionIntMaxMessages() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      SubscriptionName subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]");
      int maxMessages = 496131527;
      PullResponse response = subscriptionAdminClient.pull(subscription, maxMessages);
    }
  }
}
