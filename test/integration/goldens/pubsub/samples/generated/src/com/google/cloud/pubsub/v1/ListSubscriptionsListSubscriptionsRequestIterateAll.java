package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.ListSubscriptionsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Subscription;

public class ListSubscriptionsListSubscriptionsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listSubscriptionsListSubscriptionsRequestIterateAll();
  }

  public static void listSubscriptionsListSubscriptionsRequestIterateAll() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ListSubscriptionsRequest request =
          ListSubscriptionsRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (Subscription element : subscriptionAdminClient.listSubscriptions(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
