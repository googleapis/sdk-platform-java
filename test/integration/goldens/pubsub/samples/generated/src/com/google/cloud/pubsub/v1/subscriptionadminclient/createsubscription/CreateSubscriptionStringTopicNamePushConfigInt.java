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

// [START pubsub_v1_generated_subscriptionadminclient_createsubscription_stringtopicnamepushconfigint]
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.SubscriptionName;
import com.google.pubsub.v1.TopicName;

public class CreateSubscriptionStringTopicNamePushConfigInt {

  public static void main(String[] args) throws Exception {
    createSubscriptionStringTopicNamePushConfigInt();
  }

  public static void createSubscriptionStringTopicNamePushConfigInt() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
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
// [END pubsub_v1_generated_subscriptionadminclient_createsubscription_stringtopicnamepushconfigint]
