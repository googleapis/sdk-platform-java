package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.SnapshotName;

public class DeleteSnapshotStringSnapshot {

  public static void main(String[] args) throws Exception {
    deleteSnapshotStringSnapshot();
  }

  public static void deleteSnapshotStringSnapshot() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String snapshot = SnapshotName.of("[PROJECT]", "[SNAPSHOT]").toString();
      subscriptionAdminClient.deleteSnapshot(snapshot);
    }
  }
}
