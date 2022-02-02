package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.ListViewsRequest;
import com.google.logging.v2.LogView;

public class ListViewsListViewsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listViewsListViewsRequestIterateAll();
  }

  public static void listViewsListViewsRequestIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ListViewsRequest request =
          ListViewsRequest.newBuilder()
              .setParent("parent-995424086")
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      for (LogView element : configClient.listViews(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
