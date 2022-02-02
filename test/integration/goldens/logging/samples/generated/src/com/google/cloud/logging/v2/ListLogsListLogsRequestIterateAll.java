package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.ListLogsRequest;
import com.google.logging.v2.ProjectName;
import java.util.ArrayList;

public class ListLogsListLogsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listLogsListLogsRequestIterateAll();
  }

  public static void listLogsListLogsRequestIterateAll() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      ListLogsRequest request =
          ListLogsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .addAllResourceNames(new ArrayList<String>())
              .build();
      for (String element : loggingClient.listLogs(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
