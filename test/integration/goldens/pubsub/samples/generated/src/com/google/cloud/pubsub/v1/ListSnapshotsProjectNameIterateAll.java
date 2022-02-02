package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Snapshot;

public class ListSnapshotsProjectNameIterateAll {

  public static void main(String[] args) throws Exception {
    listSnapshotsProjectNameIterateAll();
  }

  public static void listSnapshotsProjectNameIterateAll() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ProjectName project = ProjectName.of("[PROJECT]");
      for (Snapshot element : subscriptionAdminClient.listSnapshots(project).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
