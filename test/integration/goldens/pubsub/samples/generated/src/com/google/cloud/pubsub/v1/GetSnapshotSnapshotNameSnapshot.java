package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.Snapshot;
import com.google.pubsub.v1.SnapshotName;

public class GetSnapshotSnapshotNameSnapshot {

  public static void main(String[] args) throws Exception {
    getSnapshotSnapshotNameSnapshot();
  }

  public static void getSnapshotSnapshotNameSnapshot() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      SnapshotName snapshot = SnapshotName.of("[PROJECT]", "[SNAPSHOT]");
      Snapshot response = subscriptionAdminClient.getSnapshot(snapshot);
    }
  }
}
