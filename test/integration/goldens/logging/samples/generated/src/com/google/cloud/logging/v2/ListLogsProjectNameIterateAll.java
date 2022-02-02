package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.ProjectName;

public class ListLogsProjectNameIterateAll {

  public static void main(String[] args) throws Exception {
    listLogsProjectNameIterateAll();
  }

  public static void listLogsProjectNameIterateAll() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      for (String element : loggingClient.listLogs(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
