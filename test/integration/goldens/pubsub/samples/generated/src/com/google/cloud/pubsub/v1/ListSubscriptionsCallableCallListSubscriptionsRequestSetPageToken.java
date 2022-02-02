package com.google.cloud.pubsub.v1.samples;

import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.common.base.Strings;
import com.google.pubsub.v1.ListSubscriptionsRequest;
import com.google.pubsub.v1.ListSubscriptionsResponse;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Subscription;

public class ListSubscriptionsCallableCallListSubscriptionsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listSubscriptionsCallableCallListSubscriptionsRequestSetPageToken();
  }

  public static void listSubscriptionsCallableCallListSubscriptionsRequestSetPageToken()
      throws Exception {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ListSubscriptionsRequest request =
          ListSubscriptionsRequest.newBuilder()
              .setProject(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      while (true) {
        ListSubscriptionsResponse response =
            subscriptionAdminClient.listSubscriptionsCallable().call(request);
        for (Subscription element : response.getResponsesList()) {
          // doThingsWith(element);
        }
        String nextPageToken = response.getNextPageToken();
        if (!Strings.isNullOrEmpty(nextPageToken)) {
          request = request.toBuilder().setPageToken(nextPageToken).build();
        } else {
          break;
        }
      }
    }
  }
}
