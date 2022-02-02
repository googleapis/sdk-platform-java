package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.ListSnapshotsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Snapshot;

public class ListSnapshotsPagedCallableFutureCallListSnapshotsRequest {

  public static void main(String[] args) throws Exception {
    listSnapshotsPagedCallableFutureCallListSnapshotsRequest();
  }

  public static void listSnapshotsPagedCallableFutureCallListSnapshotsRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ListSnapshotsRequest request =
          ListSnapshotsRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<Snapshot> future =
          subscriptionAdminClient.listSnapshotsPagedCallable().futureCall(request);
      // Do something.
      for (Snapshot element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
