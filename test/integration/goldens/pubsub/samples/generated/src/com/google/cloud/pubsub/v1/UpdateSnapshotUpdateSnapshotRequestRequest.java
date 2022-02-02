package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.FieldMask;
import com.google.pubsub.v1.Snapshot;
import com.google.pubsub.v1.UpdateSnapshotRequest;

public class UpdateSnapshotUpdateSnapshotRequestRequest {

  public static void main(String[] args) throws Exception {
    updateSnapshotUpdateSnapshotRequestRequest();
  }

  public static void updateSnapshotUpdateSnapshotRequestRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      UpdateSnapshotRequest request =
          UpdateSnapshotRequest.newBuilder()
              .setSnapshot(Snapshot.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      Snapshot response = subscriptionAdminClient.updateSnapshot(request);
    }
  }
}
