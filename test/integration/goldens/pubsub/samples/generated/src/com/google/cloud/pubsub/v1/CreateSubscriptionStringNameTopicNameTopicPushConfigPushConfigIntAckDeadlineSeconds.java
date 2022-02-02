package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;
import com.google.pubsub.v1.TopicName;

public class CreateSubscriptionStringNameTopicNameTopicPushConfigPushConfigIntAckDeadlineSeconds {

  public static void main(String[] args) throws Exception {
    createSubscriptionStringNameTopicNameTopicPushConfigPushConfigIntAckDeadlineSeconds();
  }

  public static void
      createSubscriptionStringNameTopicNameTopicPushConfigPushConfigIntAckDeadlineSeconds()
          throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String name = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString();
      TopicName topic = TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]");
      PushConfig pushConfig = PushConfig.newBuilder().build();
      int ackDeadlineSeconds = 2135351438;
      Subscription response =
          subscriptionAdminClient.createSubscription(name, topic, pushConfig, ackDeadlineSeconds);
    }
  }
}
