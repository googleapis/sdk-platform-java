package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.Snapshot;
import com.google.pubsub.v1.SnapshotName;
import com.google.pubsub.v1.SubscriptionName;

public class CreateSnapshotStringNameStringSubscription {

  public static void main(String[] args) throws Exception {
    createSnapshotStringNameStringSubscription();
  }

  public static void createSnapshotStringNameStringSubscription() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String name = SnapshotName.of("[PROJECT]", "[SNAPSHOT]").toString();
      String subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString();
      Snapshot response = subscriptionAdminClient.createSnapshot(name, subscription);
    }
  }
}
