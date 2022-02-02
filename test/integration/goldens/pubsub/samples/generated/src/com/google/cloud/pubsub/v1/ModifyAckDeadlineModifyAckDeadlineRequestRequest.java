package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.ModifyAckDeadlineRequest;
import com.google.pubsub.v1.SubscriptionName;
import java.util.ArrayList;

public class ModifyAckDeadlineModifyAckDeadlineRequestRequest {

  public static void main(String[] args) throws Exception {
    modifyAckDeadlineModifyAckDeadlineRequestRequest();
  }

  public static void modifyAckDeadlineModifyAckDeadlineRequestRequest() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ModifyAckDeadlineRequest request =
          ModifyAckDeadlineRequest.newBuilder()
              .setSubscription(SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString())
              .addAllAckIds(new ArrayList<String>())
              .setAckDeadlineSeconds(2135351438)
              .build();
      subscriptionAdminClient.modifyAckDeadline(request);
    }
  }
}
