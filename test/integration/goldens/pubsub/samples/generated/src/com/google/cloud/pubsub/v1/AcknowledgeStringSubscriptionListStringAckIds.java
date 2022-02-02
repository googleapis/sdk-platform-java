package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.protobuf.Empty;
import com.google.pubsub.v1.SubscriptionName;
import java.util.ArrayList;
import java.util.List;

public class AcknowledgeStringSubscriptionListStringAckIds {

  public static void main(String[] args) throws Exception {
    acknowledgeStringSubscriptionListStringAckIds();
  }

  public static void acknowledgeStringSubscriptionListStringAckIds() throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      String subscription = SubscriptionName.of("[PROJECT]", "[SUBSCRIPTION]").toString();
      List<String> ackIds = new ArrayList<>();
      subscriptionAdminClient.acknowledge(subscription, ackIds);
    }
  }
}
