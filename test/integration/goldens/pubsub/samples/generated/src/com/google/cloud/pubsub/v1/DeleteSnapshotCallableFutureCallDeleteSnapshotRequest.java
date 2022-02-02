package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.DeleteSnapshotRequest;
import com.google.pubsub.v1.SnapshotName;

public class DeleteSnapshotCallableFutureCallDeleteSnapshotRequest {

  public static void main(String[] args) throws Exception {
    deleteSnapshotCallableFutureCallDeleteSnapshotRequest();
  }

  public static void deleteSnapshotCallableFutureCallDeleteSnapshotRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      DeleteSnapshotRequest request =
          DeleteSnapshotRequest.newBuilder()
              .setSnapshot(SnapshotName.of("[PROJECT]", "[SNAPSHOT]").toString())
              .build();
      ApiFuture<Empty> future =
          subscriptionAdminClient.deleteSnapshotCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
