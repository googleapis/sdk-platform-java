/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.pubsub.v1.samples;

// [START v1_pubsub_generated_subscriptionadminclient_createsubscription_subscription]
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

public class CreateSubscriptionSubscription {

  public static void main(String[] args) throws Exception {
    createSubscriptionSubscription();
  }

  public static void createSubscriptionSubscription() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
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
              .setEnableExactlyOnceDelivery(true)
              .setTopicMessageRetentionDuration(Duration.newBuilder().build())
              .build();
      Subscription response = subscriptionAdminClient.createSubscription(request);
    }
  }
}
// [END v1_pubsub_generated_subscriptionadminclient_createsubscription_subscription]