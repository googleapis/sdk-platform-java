package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Snapshot;

public class ListSnapshotsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listSnapshotsStringIterateAll();
  }

  public static void listSnapshotsStringIterateAll() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String project = ProjectName.of("[PROJECT]").toString();
      for (Snapshot element : subscriptionAdminClient.listSnapshots(project).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
