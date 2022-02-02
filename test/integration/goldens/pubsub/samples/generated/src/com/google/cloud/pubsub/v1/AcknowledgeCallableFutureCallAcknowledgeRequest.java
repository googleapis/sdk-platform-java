package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.AcknowledgeRequest;
import com.google.pubsub.v1.SubscriptionName;
import java.util.ArrayList;

public class AcknowledgeCallableFutureCallAcknowledgeRequest {

  public static void main(String[] args) throws Exception {
    acknowledgeCallableFutureCallAcknowledgeRequest();
  }

  public static void acknowledgeCallableFutureCallAcknowledgeRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      AcknowledgeRequest request =
          AcknowledgeRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .addAllAckIds(new ArrayList<String>())
              .build();
      ApiFuture<Empty> future = subscriptionAdminClient.acknowledgeCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
