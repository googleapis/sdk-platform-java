package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.ListSinksRequest;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.ProjectName;

public class ListSinksPagedCallableFutureCallListSinksRequest {

  public static void main(String[] args) throws Exception {
    listSinksPagedCallableFutureCallListSinksRequest();
  }

  public static void listSinksPagedCallableFutureCallListSinksRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ListSinksRequest request =
          ListSinksRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      ApiFuture<LogSink> future = configClient.listSinksPagedCallable().futureCall(request);
      // Do something.
      for (LogSink element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
