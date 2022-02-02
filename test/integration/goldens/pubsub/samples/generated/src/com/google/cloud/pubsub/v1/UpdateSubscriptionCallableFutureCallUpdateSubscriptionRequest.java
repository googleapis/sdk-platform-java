package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.FieldMask;
import com.google.pubsub.v1.Subscription;
import com.google.pubsub.v1.UpdateSubscriptionRequest;

public class UpdateSubscriptionCallableFutureCallUpdateSubscriptionRequest {

  public static void main(String[] args) throws Exception {
    updateSubscriptionCallableFutureCallUpdateSubscriptionRequest();
  }

  public static void updateSubscriptionCallableFutureCallUpdateSubscriptionRequest()
      throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      UpdateSubscriptionRequest request =
          UpdateSubscriptionRequest.newBuilder()
              .setSubscription(Subscription.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<Subscription> future =
          subscriptionAdminClient.updateSubscriptionCallable().futureCall(request);
      // Do something.
      Subscription response = future.get();
    }
  }
}
