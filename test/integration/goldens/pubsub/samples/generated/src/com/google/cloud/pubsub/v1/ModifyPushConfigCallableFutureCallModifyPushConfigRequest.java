package com.google.cloud.pubsub.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.ModifyPushConfigRequest;
import com.google.pubsub.v1.PushConfig;
import com.google.pubsub.v1.SubscriptionName;

public class ModifyPushConfigCallableFutureCallModifyPushConfigRequest {

  public static void main(String[] args) throws Exception {
    modifyPushConfigCallableFutureCallModifyPushConfigRequest();
  }

  public static void modifyPushConfigCallableFutureCallModifyPushConfigRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ModifyPushConfigRequest request =
          ModifyPushConfigRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .setPushConfig(PushConfig.newBuilder().build())
              .build();
      ApiFuture<Empty> future =
          subscriptionAdminClient.modifyPushConfigCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
