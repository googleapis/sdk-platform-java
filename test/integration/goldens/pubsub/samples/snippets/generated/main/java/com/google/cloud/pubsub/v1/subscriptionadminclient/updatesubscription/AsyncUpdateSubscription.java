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

// [START pubsub_v1_generated_subscriptionadminclient_updatesubscription_async]
import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.FieldMask;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.UpdateSubscriptionRequest;

public class AsyncUpdateSubscription {

  public static void main(String[] args) throws Exception {
    asyncUpdateSubscription();
  }

  public static void asyncUpdateSubscription() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      UpdateSubscriptionRequest request =
          UpdateSubscriptionRequest.newBuilder()
              .setSubscription(Subscription.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<Subscription> future =
          subscriptionAdminClient.updateSubscriptionCallable().futureCall(request);
      // Do something.
      Subscription response = future.get();
    }
  }
}
// [END pubsub_v1_generated_subscriptionadminclient_updatesubscription_async]
