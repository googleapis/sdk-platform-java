package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.SubscriptionName;

public class ModifyPushConfigSubscriptionNameSubscriptionPushConfigPushConfig {

  public static void main(String[] args) throws Exception {
    modifyPushConfigSubscriptionNameSubscriptionPushConfigPushConfig();
  }

  public static void modifyPushConfigSubscriptionNameSubscriptionPushConfigPushConfig()
      throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      SubscriptionName subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]");
      PushConfig pushConfig = PushConfig.newBuilder().build();
      subscriptionAdminClient.modifyPushConfig(subscription, pushConfig);
    }
  }
}
