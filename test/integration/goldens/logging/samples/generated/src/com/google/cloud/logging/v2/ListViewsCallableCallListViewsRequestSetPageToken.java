package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.common.base.Strings;
import com.google.logging.v2.ListViewsRequest;
import com.google.logging.v2.ListViewsResponse;
import com.google.logging.v2.LogView;

public class ListViewsCallableCallListViewsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listViewsCallableCallListViewsRequestSetPageToken();
  }

  public static void listViewsCallableCallListViewsRequestSetPageToken() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ListViewsRequest request =
          ListViewsRequest.newBuilder()
              .setParent("parent-995424086")
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      while (true) {
        ListViewsResponse response = configClient.listViewsCallable().call(request);
        for (LogView element : response.getResponsesList()) {
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
