package com.google.cloud.pubsub.v1.samples;

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
