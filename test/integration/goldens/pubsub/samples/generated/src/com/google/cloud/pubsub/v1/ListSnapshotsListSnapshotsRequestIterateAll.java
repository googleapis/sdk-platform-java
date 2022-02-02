package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.ListSnapshotsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Snapshot;

public class ListSnapshotsListSnapshotsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listSnapshotsListSnapshotsRequestIterateAll();
  }

  public static void listSnapshotsListSnapshotsRequestIterateAll() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ListSnapshotsRequest request =
          ListSnapshotsRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (Snapshot element : subscriptionAdminClient.listSnapshots(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
