package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.FieldMask;
import com.google.pubsub.v1.Snapshot;
import com.google.pubsub.v1.UpdateSnapshotRequest;

public class UpdateSnapshotCallableFutureCallUpdateSnapshotRequest {

  public static void main(String[] args) throws Exception {
    updateSnapshotCallableFutureCallUpdateSnapshotRequest();
  }

  public static void updateSnapshotCallableFutureCallUpdateSnapshotRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      UpdateSnapshotRequest request =
          UpdateSnapshotRequest.newBuilder()
              .setSnapshot(Snapshot.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<Snapshot> future =
          subscriptionAdminClient.updateSnapshotCallable().futureCall(request);
      // Do something.
      Snapshot response = future.get();
    }
  }
}
