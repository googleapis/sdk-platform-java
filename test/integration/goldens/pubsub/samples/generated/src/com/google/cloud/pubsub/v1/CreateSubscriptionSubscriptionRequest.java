package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Duration;
import com.google.pubsub.v1.DeadLetterPolicy;
import com.google.pubsub.v1.ExpirationPolicy;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.RetryPolicy;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;
import com.google.pubsub.v1.TopicName;
import java.util.HashMap;

public class CreateSubscriptionSubscriptionRequest {

  public static void main(String[] args) throws Exception {
    createSubscriptionSubscriptionRequest();
  }

  public static void createSubscriptionSubscriptionRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      Subscription request =
          Subscription.newBuilder()
              .setName(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .setTopic(TopicName.ofProjectTopicName("[PROJECT]", "[TOPIC]").toString())
              .setPushConfig(PushConfig.newBuilder().build())
              .setAckDeadlineSeconds(2135351438)
              .setRetainAckedMessages(true)
              .setMessageRetentionDuration(Duration.newBuilder().build())
              .putAllLabels(new HashMap<String, String>())
              .setEnableMessageOrdering(true)
              .setExpirationPolicy(ExpirationPolicy.newBuilder().build())
              .setFilter("filter-1274492040")
              .setDeadLetterPolicy(DeadLetterPolicy.newBuilder().build())
              .setRetryPolicy(RetryPolicy.newBuilder().build())
              .setDetached(true)
              .setTopicMessageRetentionDuration(Duration.newBuilder().build())
              .build();
      Subscription response = subscriptionAdminClient.createSubscription(request);
    }
  }
}
