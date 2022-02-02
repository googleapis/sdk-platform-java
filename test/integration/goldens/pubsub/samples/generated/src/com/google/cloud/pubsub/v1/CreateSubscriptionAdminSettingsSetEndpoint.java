package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.cloud.pubsub.v1.myEndpoint;

public class CreateSubscriptionAdminSettingsSetEndpoint {

  public static void main(String[] args) throws Exception {
    createSubscriptionAdminSettingsSetEndpoint();
  }

  public static void createSubscriptionAdminSettingsSetEndpoint() throws Exception {
    SubscriptionAdminSettings subscriptionAdminSettings =
        SubscriptionAdminSettings.newBuilder().setEndpoint(myEndpoint).build();
    SubscriptionAdminClient subscriptionAdminClient =
        SubscriptionAdminClient.create(subscriptionAdminSettings);
  }
}
