package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.SubscriptionName;

public class ModifyPushConfigStringSubscriptionPushConfigPushConfig {

  public static void main(String[] args) throws Exception {
    modifyPushConfigStringSubscriptionPushConfigPushConfig();
  }

  public static void modifyPushConfigStringSubscriptionPushConfigPushConfig() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString();
      PushConfig pushConfig = PushConfig.newBuilder().build();
      subscriptionAdminClient.modifyPushConfig(subscription, pushConfig);
    }
  }
}
