package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.SeekRequest;
import com.google.pubsub.v1.SeekResponse;
import com.google.pubsub.v1.SubscriptionName;

public class SeekSeekRequestRequest {

  public static void main(String[] args) throws Exception {
    seekSeekRequestRequest();
  }

  public static void seekSeekRequestRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      SeekRequest request =
          SeekRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .build();
      SeekResponse response = subscriptionAdminClient.seek(request);
    }
  }
}
