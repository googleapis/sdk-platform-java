package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.ListSinksRequest;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.ProjectName;

public class ListSinksListSinksRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listSinksListSinksRequestIterateAll();
  }

  public static void listSinksListSinksRequestIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ListSinksRequest request =
          ListSinksRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      for (LogSink element : configClient.listSinks(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
