package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.SubscriptionName;
import java.util.ArrayList;
import java.util.List;

public class ModifyAckDeadlineStringSubscriptionListStringAckIdsIntAckDeadlineSeconds {

  public static void main(String[] args) throws Exception {
    modifyAckDeadlineStringSubscriptionListStringAckIdsIntAckDeadlineSeconds();
  }

  public static void modifyAckDeadlineStringSubscriptionListStringAckIdsIntAckDeadlineSeconds()
      throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString();
      List<String> ackIds = new ArrayList<>();
      int ackDeadlineSeconds = 2135351438;
      subscriptionAdminClient.modifyAckDeadline(subscription, ackIds, ackDeadlineSeconds);
    }
  }
}
