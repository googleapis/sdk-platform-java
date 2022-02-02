package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.SeekRequest;
import com.google.pubsub.v1.SeekResponse;
import com.google.pubsub.v1.SubscriptionName;

public class SeekCallableFutureCallSeekRequest {

  public static void main(String[] args) throws Exception {
    seekCallableFutureCallSeekRequest();
  }

  public static void seekCallableFutureCallSeekRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      SeekRequest request =
          SeekRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .build();
      ApiFuture<SeekResponse> future = subscriptionAdminClient.seekCallable().futureCall(request);
      // Do something.
      SeekResponse response = future.get();
    }
  }
}
