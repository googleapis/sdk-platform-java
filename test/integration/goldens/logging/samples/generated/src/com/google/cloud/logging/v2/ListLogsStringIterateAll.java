package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.ProjectName;

public class ListLogsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listLogsStringIterateAll();
  }

  public static void listLogsStringIterateAll() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      for (String element : loggingClient.listLogs(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
