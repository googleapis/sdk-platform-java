package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.CreateSnapshotRequest;
import com.google.pubsub.v1.Snapshot;
import com.google.pubsub.v1.SnapshotName;
import com.google.pubsub.v1.SubscriptionName;
import java.util.HashMap;

public class CreateSnapshotCreateSnapshotRequestRequest {

  public static void main(String[] args) throws Exception {
    createSnapshotCreateSnapshotRequestRequest();
  }

  public static void createSnapshotCreateSnapshotRequestRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      CreateSnapshotRequest request =
          CreateSnapshotRequest.newBuilder()
              .setName(SnapshotName.of("[PROJECT]", "[SNAPSHOT]").toString())
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .putAllLabels(new HashMap<String, String>())
              .build();
      Snapshot response = subscriptionAdminClient.createSnapshot(request);
    }
  }
}
