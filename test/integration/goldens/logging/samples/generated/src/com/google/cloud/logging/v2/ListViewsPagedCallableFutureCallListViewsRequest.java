package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.ListViewsRequest;
import com.google.logging.v2.LogView;

public class ListViewsPagedCallableFutureCallListViewsRequest {

  public static void main(String[] args) throws Exception {
    listViewsPagedCallableFutureCallListViewsRequest();
  }

  public static void listViewsPagedCallableFutureCallListViewsRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ListViewsRequest request =
          ListViewsRequest.newBuilder()
              .setParent("parent-995424086")
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      ApiFuture<LogView> future = configClient.listViewsPagedCallable().futureCall(request);
      // Do something.
      for (LogView element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
