package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.DeleteSnapshotRequest;
import com.google.pubsub.v1.SnapshotName;

public class DeleteSnapshotDeleteSnapshotRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteSnapshotDeleteSnapshotRequestRequest();
  }

  public static void deleteSnapshotDeleteSnapshotRequestRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      DeleteSnapshotRequest request =
          DeleteSnapshotRequest.newBuilder()
              .setSnapshot(SnapshotName.of("[PROJECT]", "[SNAPSHOT]").toString())
              .build();
      subscriptionAdminClient.deleteSnapshot(request);
    }
  }
}
