package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.Snapshot;
import com.google.pubsub.v1.SnapshotName;
import com.google.pubsub.v1.SubscriptionName;

public class CreateSnapshotSnapshotNameNameSubscriptionNameSubscription {

  public static void main(String[] args) throws Exception {
    createSnapshotSnapshotNameNameSubscriptionNameSubscription();
  }

  public static void createSnapshotSnapshotNameNameSubscriptionNameSubscription() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      SnapshotName name = SnapshotName.of("[PROJECT]", "[SNAPSHOT]");
      SubscriptionName subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]");
      Snapshot response = subscriptionAdminClient.createSnapshot(name, subscription);
    }
  }
}
