package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.GetSnapshotRequest;
import com.google.pubsub.v1.Snapshot;
import com.google.pubsub.v1.SnapshotName;

public class GetSnapshotGetSnapshotRequestRequest {

  public static void main(String[] args) throws Exception {
    getSnapshotGetSnapshotRequestRequest();
  }

  public static void getSnapshotGetSnapshotRequestRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      GetSnapshotRequest request =
          GetSnapshotRequest.newBuilder()
              .setSnapshot(SnapshotName.of("[PROJECT]", "[SNAPSHOT]").toString())
              .build();
      Snapshot response = subscriptionAdminClient.getSnapshot(request);
    }
  }
}
