package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.GetSnapshotRequest;
import com.google.pubsub.v1.Snapshot;
import com.google.pubsub.v1.SnapshotName;

public class GetSnapshotCallableFutureCallGetSnapshotRequest {

  public static void main(String[] args) throws Exception {
    getSnapshotCallableFutureCallGetSnapshotRequest();
  }

  public static void getSnapshotCallableFutureCallGetSnapshotRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      GetSnapshotRequest request =
          GetSnapshotRequest.newBuilder()
              .setSnapshot(SnapshotName.of("[PROJECT]", "[SNAPSHOT]").toString())
              .build();
      ApiFuture<Snapshot> future =
          subscriptionAdminClient.getSnapshotCallable().futureCall(request);
      // Do something.
      Snapshot response = future.get();
    }
  }
}
