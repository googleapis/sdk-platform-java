package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingClient;
import com.google.common.base.Strings;
import com.google.logging.v2.ListLogEntriesRequest;
import com.google.logging.v2.ListLogEntriesResponse;
import com.google.logging.v2.LogEntry;
import java.util.ArrayList;

public class ListLogEntriesCallableCallListLogEntriesRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listLogEntriesCallableCallListLogEntriesRequestSetPageToken();
  }

  public static void listLogEntriesCallableCallListLogEntriesRequestSetPageToken()
      throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      ListLogEntriesRequest request =
          ListLogEntriesRequest.newBuilder()
              .addAllResourceNames(new ArrayList<String>())
              .setFilter("filter-1274492040")
              .setOrderBy("orderBy-1207110587")
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      while (true) {
        ListLogEntriesResponse response = loggingClient.listLogEntriesCallable().call(request);
        for (LogEntry element : response.getResponsesList()) {
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
