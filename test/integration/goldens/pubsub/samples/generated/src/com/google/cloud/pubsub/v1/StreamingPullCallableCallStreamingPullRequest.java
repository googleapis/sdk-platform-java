package com.google.cloud.pubsub.v1.samples;

import com.google.api.gax.rpc.BidiStream;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.pubsub.v1.StreamingPullRequest;
import com.google.pubsub.v1.StreamingPullResponse;
import com.google.pubsub.v1.SubscriptionName;
import java.util.ArrayList;

public class StreamingPullCallableCallStreamingPullRequest {

  public static void main(String[] args) throws Exception {
    streamingPullCallableCallStreamingPullRequest();
  }

  public static void streamingPullCallableCallStreamingPullRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      BidiStream<StreamingPullRequest, StreamingPullResponse> bidiStream =
          subscriptionAdminClient.streamingPullCallable().call();
      StreamingPullRequest request =
          StreamingPullRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .addAllAckIds(new ArrayList<String>())
              .addAllModifyDeadlineSeconds(new ArrayList<Integer>())
              .addAllModifyDeadlineAckIds(new ArrayList<String>())
              .setStreamAckDeadlineSeconds(1875467245)
              .setClientId("clientId908408390")
              .setMaxOutstandingMessages(-1315266996)
              .setMaxOutstandingBytes(-2103098517)
              .build();
      bidiStream.send(request);
      for (StreamingPullResponse response : bidiStream) {
        // Do something when a response is received.
      }
    }
  }
}
