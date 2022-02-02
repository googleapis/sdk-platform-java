package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.Snapshot;
import com.google.pubsub.v1.SnapshotName;

public class GetSnapshotStringSnapshot {

  public static void main(String[] args) throws Exception {
    getSnapshotStringSnapshot();
  }

  public static void getSnapshotStringSnapshot() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String snapshot = SnapshotName.of("[PROJECT]", "[SNAPSHOT]").toString();
      Snapshot response = subscriptionAdminClient.getSnapshot(snapshot);
    }
  }
}
