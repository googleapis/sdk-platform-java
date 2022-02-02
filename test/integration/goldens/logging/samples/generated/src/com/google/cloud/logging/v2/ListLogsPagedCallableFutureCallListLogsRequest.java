package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.ListLogsRequest;
import com.google.logging.v2.ProjectName;
import java.util.ArrayList;

public class ListLogsPagedCallableFutureCallListLogsRequest {

  public static void main(String[] args) throws Exception {
    listLogsPagedCallableFutureCallListLogsRequest();
  }

  public static void listLogsPagedCallableFutureCallListLogsRequest() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      ListLogsRequest request =
          ListLogsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .addAllResourceNames(new ArrayList<String>())
              .build();
      ApiFuture<String> future = loggingClient.listLogsPagedCallable().futureCall(request);
      // Do something.
      for (String element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
