package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Subscription;

public class ListSubscriptionsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listSubscriptionsStringIterateAll();
  }

  public static void listSubscriptionsStringIterateAll() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String project = ProjectName.of("[PROJECT]").toString();
      for (Subscription element : subscriptionAdminClient.listSubscriptions(project).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
