package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.common.base.Strings;
import com.google.pubsub.v1.ListSnapshotsRequest;
import com.google.pubsub.v1.ListSnapshotsResponse;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Snapshot;

public class ListSnapshotsCallableCallListSnapshotsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listSnapshotsCallableCallListSnapshotsRequestSetPageToken();
  }

  public static void listSnapshotsCallableCallListSnapshotsRequestSetPageToken() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ListSnapshotsRequest request =
          ListSnapshotsRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      while (true) {
        ListSnapshotsResponse response =
            subscriptionAdminClient.listSnapshotsCallable().call(request);
        for (Snapshot element : response.getResponsesList()) {
          // doThingsWith(element);
        }
        String nextPageToken = response.getNextPageToken();
        if (!Strings.isNullOrEmpty(nextPageToken)) {
          request = request.toBuilder().setPageToken(nextPageToken).build();
        } else {
          break;
        }
      }
    }
  }
}
