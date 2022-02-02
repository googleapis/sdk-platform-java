package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.FieldMask;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.UpdateSubscriptionRequest;

public class UpdateSubscriptionUpdateSubscriptionRequestRequest {

  public static void main(String[] args) throws Exception {
    updateSubscriptionUpdateSubscriptionRequestRequest();
  }

  public static void updateSubscriptionUpdateSubscriptionRequestRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      UpdateSubscriptionRequest request =
          UpdateSubscriptionRequest.newBuilder()
              .setSubscription(Subscription.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      Subscription response = subscriptionAdminClient.updateSubscription(request);
    }
  }
}
