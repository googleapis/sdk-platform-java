package com.google.cloud.pubsub.v1.stub.samples;

import com.google.cloud.pubsub.v1.stub.SubscriberStubSettings;
import java.time.Duration;

public class SubscriberStubSettingsCreateSubscription {

  public static void main(String[] args) throws Exception {
    subscriberStubSettingsCreateSubscription();
  }

  public static void subscriberStubSettingsCreateSubscription() throws Exception {
    SubscriberStubSettings.Builder subscriptionAdminSettingsBuilder =
        SubscriberStubSettings.newBuilder();
    subscriptionAdminSettingsBuilder
        .createSubscriptionSettings()
        .setRetrySettings(
            subscriptionAdminSettingsBuilder
                .createSubscriptionSettings()
                .getRetrySettings()
                .toBuilder()
                .setTotalTimeout(Duration.ofSeconds(30))
                .build());
    SubscriberStubSettings subscriptionAdminSettings = subscriptionAdminSettingsBuilder.build();
  }
}
