package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.ModifyAckDeadlineRequest;
import com.google.pubsub.v1.SubscriptionName;
import java.util.ArrayList;

public class ModifyAckDeadlineCallableFutureCallModifyAckDeadlineRequest {

  public static void main(String[] args) throws Exception {
    modifyAckDeadlineCallableFutureCallModifyAckDeadlineRequest();
  }

  public static void modifyAckDeadlineCallableFutureCallModifyAckDeadlineRequest()
      throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ModifyAckDeadlineRequest request =
          ModifyAckDeadlineRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .addAllAckIds(new ArrayList<String>())
              .setAckDeadlineSeconds(2135351438)
              .build();
      ApiFuture<Empty> future =
          subscriptionAdminClient.modifyAckDeadlineCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
