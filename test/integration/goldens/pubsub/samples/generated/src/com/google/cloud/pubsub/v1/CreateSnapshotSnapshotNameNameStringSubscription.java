package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.Snapshot;
import com.google.pubsub.v1.SnapshotName;
import com.google.pubsub.v1.SubscriptionName;

public class CreateSnapshotSnapshotNameNameStringSubscription {

  public static void main(String[] args) throws Exception {
    createSnapshotSnapshotNameNameStringSubscription();
  }

  public static void createSnapshotSnapshotNameNameStringSubscription() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      SnapshotName name = SnapshotName.of("[PROJECT]", "[SNAPSHOT]");
      String subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString();
      Snapshot response = subscriptionAdminClient.createSnapshot(name, subscription);
    }
  }
}
