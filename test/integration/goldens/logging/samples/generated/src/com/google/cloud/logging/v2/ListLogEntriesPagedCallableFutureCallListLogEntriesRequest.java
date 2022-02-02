package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.ListLogEntriesRequest;
import com.google.logging.v2.LogEntry;
import java.util.ArrayList;

public class ListLogEntriesPagedCallableFutureCallListLogEntriesRequest {

  public static void main(String[] args) throws Exception {
    listLogEntriesPagedCallableFutureCallListLogEntriesRequest();
  }

  public static void listLogEntriesPagedCallableFutureCallListLogEntriesRequest() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      ListLogEntriesRequest request =
          ListLogEntriesRequest.newBuilder()
              .addAllResourceNames(new ArrayList<String>())
              .setFilter("filter-1274492040")
              .setOrderBy("orderBy-1207110587")
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<LogEntry> future = loggingClient.listLogEntriesPagedCallable().futureCall(request);
      // Do something.
      for (LogEntry element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
