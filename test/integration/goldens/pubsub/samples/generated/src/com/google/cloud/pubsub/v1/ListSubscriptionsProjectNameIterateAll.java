package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Subscription;

public class ListSubscriptionsProjectNameIterateAll {

  public static void main(String[] args) throws Exception {
    listSubscriptionsProjectNameIterateAll();
  }

  public static void listSubscriptionsProjectNameIterateAll() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ProjectName project = ProjectName.of("[PROJECT]");
      for (Subscription element : subscriptionAdminClient.listSubscriptions(project).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
