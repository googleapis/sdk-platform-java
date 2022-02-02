package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.SubscriptionName;
import java.util.ArrayList;
import java.util.List;

public class ModifyAckDeadlineSubscriptionNameSubscriptionListStringAckIdsIntAckDeadlineSeconds {

  public static void main(String[] args) throws Exception {
    modifyAckDeadlineSubscriptionNameSubscriptionListStringAckIdsIntAckDeadlineSeconds();
  }

  public static void
      modifyAckDeadlineSubscriptionNameSubscriptionListStringAckIdsIntAckDeadlineSeconds()
          throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      SubscriptionName subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]");
      List<String> ackIds = new ArrayList<>();
      int ackDeadlineSeconds = 2135351438;
      subscriptionAdminClient.modifyAckDeadline(subscription, ackIds, ackDeadlineSeconds);
    }
  }
}
