package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;
import com.google.pubsub.v1.TopicName;

public
class CreateSubscriptionSubscriptionNameNameStringTopicPushConfigPushConfigIntAckDeadlineSeconds {

  public static void main(String[] args) throws Exception {
    createSubscriptionSubscriptionNameNameStringTopicPushConfigPushConfigIntAckDeadlineSeconds();
  }

  public static void
      createSubscriptionSubscriptionNameNameStringTopicPushConfigPushConfigIntAckDeadlineSeconds()
          throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      SubscriptionName name = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]");
      String topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString();
      PushConfig pushConfig = PushConfig.newBuilder().build();
      int ackDeadlineSeconds = 2135351438;
      Subscription response =
          subscriptionAdminClient.createSubscription(name, topic, pushConfig, ackDeadlineSeconds);
    }
  }
}
