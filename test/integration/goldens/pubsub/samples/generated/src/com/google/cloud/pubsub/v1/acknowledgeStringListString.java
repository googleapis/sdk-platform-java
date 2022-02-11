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

// [START 1.0_10_generated_subscriptionadminclient_acknowledge_stringliststring]
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.SubscriptionName;
import java.util.ArrayList;
import java.util.List;

public class AcknowledgeStringListString {

  public static void main(String[] args) throws Exception {
    acknowledgeStringListString();
  }

  public static void acknowledgeStringListString() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString();
      List<String> ackIds = new ArrayList<>();
      subscriptionAdminClient.acknowledge(subscription, ackIds);
    }
  }
}
// [END 1.0_10_generated_subscriptionadminclient_acknowledge_stringliststring]