package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.OrganizationName;

public class ListLogsOrganizationNameIterateAll {

  public static void main(String[] args) throws Exception {
    listLogsOrganizationNameIterateAll();
  }

  public static void listLogsOrganizationNameIterateAll() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      OrganizationName parent = OrganizationName.of("[ORGANIZATION]");
      for (String element : loggingClient.listLogs(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
