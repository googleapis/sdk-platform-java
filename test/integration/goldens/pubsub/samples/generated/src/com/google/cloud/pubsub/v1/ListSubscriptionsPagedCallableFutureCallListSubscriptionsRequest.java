package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.ListSubscriptionsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Subscription;

public class ListSubscriptionsPagedCallableFutureCallListSubscriptionsRequest {

  public static void main(String[] args) throws Exception {
    listSubscriptionsPagedCallableFutureCallListSubscriptionsRequest();
  }

  public static void listSubscriptionsPagedCallableFutureCallListSubscriptionsRequest()
      throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ListSubscriptionsRequest request =
          ListSubscriptionsRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<Subscription> future =
          subscriptionAdminClient.listSubscriptionsPagedCallable().futureCall(request);
      // Do something.
      for (Subscription element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
