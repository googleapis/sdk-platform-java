package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.PullRequest;
import com.google.pubsub.v1.PullResponse;
import com.google.pubsub.v1.SubscriptionName;

public class PullPullRequestRequest {

  public static void main(String[] args) throws Exception {
    pullPullRequestRequest();
  }

  public static void pullPullRequestRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      PullRequest request =
          PullRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .setReturnImmediately(true)
              .setMaxMessages(496131527)
              .build();
      PullResponse response = subscriptionAdminClient.pull(request);
    }
  }
}
