package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminSettings;
import java.time.Duration;

public class SubscriptionAdminSettingsCreateSubscription {

  public static void main(String[] args) throws Exception {
    subscriptionAdminSettingsCreateSubscription();
  }

  public static void subscriptionAdminSettingsCreateSubscription() throws Exception {
    SubscriptionAdminSettings.Builder subscriptionAdminSettingsBuilder =
        SubscriptionAdminSettings.newBuilder();
    subscriptionAdminSettingsBuilder
        .createSubscriptionSettings()
        .setRetrySettings(
            subscriptionAdminSettingsBuilder
                .createSubscriptionSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    SubscriptionAdminSettings subscriptionAdminSettings = subscriptionAdminSettingsBuilder.build();
  }
}
