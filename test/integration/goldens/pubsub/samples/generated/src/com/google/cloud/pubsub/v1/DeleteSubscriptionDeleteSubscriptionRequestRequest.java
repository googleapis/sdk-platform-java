package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.DeleteSubscriptionRequest;
import com.google.pubsub.v1.SubscriptionName;

public class DeleteSubscriptionDeleteSubscriptionRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteSubscriptionDeleteSubscriptionRequestRequest();
  }

  public static void deleteSubscriptionDeleteSubscriptionRequestRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      DeleteSubscriptionRequest request =
          DeleteSubscriptionRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .build();
      subscriptionAdminClient.deleteSubscription(request);
    }
  }
}
