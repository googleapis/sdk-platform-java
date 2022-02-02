package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.Snapshot;
import com.google.pubsub.v1.SnapshotName;
import com.google.pubsub.v1.SubscriptionName;

public class CreateSnapshotStringNameSubscriptionNameSubscription {

  public static void main(String[] args) throws Exception {
    createSnapshotStringNameSubscriptionNameSubscription();
  }

  public static void createSnapshotStringNameSubscriptionNameSubscription() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String name = SnapshotName.of("[PROJECT]", "[SNAPSHOT]").toString();
      SubscriptionName subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]");
      Snapshot response = subscriptionAdminClient.createSnapshot(name, subscription);
    }
  }
}
