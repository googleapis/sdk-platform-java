package com.google.cloud.pubsub.v1.samples;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import com.google.cloud.pubsub.v1.myCredentials;

public class CreateSubscriptionAdminSettingsSetCredentialsProvider {

  public static void main(String[] args) throws Exception {
    createSubscriptionAdminSettingsSetCredentialsProvider();
  }

  public static void createSubscriptionAdminSettingsSetCredentialsProvider() throws Exception {
    SubscriptionAdminSettings subscriptionAdminSettings =
        SubscriptionAdminSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
            .build();
    SubscriptionAdminClient subscriptionAdminClient =
        SubscriptionAdminClient.create(subscriptionAdminSettings);
  }
}
