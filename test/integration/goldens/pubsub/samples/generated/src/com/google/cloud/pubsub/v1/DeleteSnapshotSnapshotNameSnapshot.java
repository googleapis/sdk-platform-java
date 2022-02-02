package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.SnapshotName;

public class DeleteSnapshotSnapshotNameSnapshot {

  public static void main(String[] args) throws Exception {
    deleteSnapshotSnapshotNameSnapshot();
  }

  public static void deleteSnapshotSnapshotNameSnapshot() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      SnapshotName snapshot = SnapshotName.of("[PROJECT]", "[SNAPSHOT]");
      subscriptionAdminClient.deleteSnapshot(snapshot);
    }
  }
}
