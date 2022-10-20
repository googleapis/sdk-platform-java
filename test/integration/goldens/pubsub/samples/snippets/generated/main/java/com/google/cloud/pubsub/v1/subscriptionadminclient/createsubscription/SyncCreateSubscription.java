/*
 * Copyright 2022 Google LLC
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

// [START pubsub_v1_generated_subscriptionadminclient_createsubscription_sync]
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

public class SyncCreateSubscription {

  public static void main(String[] args) throws Exception {
    syncCreateSubscription();
  }

  public static void syncCreateSubscription() throws Exception {
    // This snippet has been automatically generated and should be regarded as a code template only.
    // It will require modifications to work:
    // - It may require correct/in-range values for request initialization.
    // - It may require specifying regional endpoints when creating the service client as shown in
    // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
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
// [END pubsub_v1_generated_subscriptionadminclient_createsubscription_sync]
