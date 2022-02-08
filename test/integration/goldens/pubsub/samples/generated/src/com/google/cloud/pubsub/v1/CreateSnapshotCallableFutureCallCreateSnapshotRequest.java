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

// [START 10_10_generated_subscriptionAdminClient_createSnapshotCallable_futureCallCreateSnapshotRequest]
import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.CreateSnapshotRequest;
import com.google.pubsub.v1.Snapshot;
import com.google.pubsub.v1.SnapshotName;
import com.google.pubsub.v1.SubscriptionName;
import java.util.HashMap;

public class CreateSnapshotCallableFutureCallCreateSnapshotRequest {

  public static void main(String[] args) throws Exception {
    createSnapshotCallableFutureCallCreateSnapshotRequest();
  }

  public static void createSnapshotCallableFutureCallCreateSnapshotRequest() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      CreateSnapshotRequest request =
          CreateSnapshotRequest.newBuilder()
              .setName(SnapshotName.of("[PROJECT]", "[SNAPSHOT]").toString())
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .putAllLabels(new HashMap<String, String>())
              .build();
      ApiFuture<Snapshot> future =
          subscriptionAdminClient.createSnapshotCallable().futureCall(request);
      // Do something.
      Snapshot response = future.get();
    }
  }
}
// [END 10_10_generated_subscriptionAdminClient_createSnapshotCallable_futureCallCreateSnapshotRequest]