package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.LogEntry;
import java.util.ArrayList;
import java.util.List;

public class ListLogEntriesListStringStringStringIterateAll {

  public static void main(String[] args) throws Exception {
    listLogEntriesListStringStringStringIterateAll();
  }

  public static void listLogEntriesListStringStringStringIterateAll() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      List<String> resourceNames = new ArrayList<>();
      String filter = "filter-1274492040";
      String orderBy = "orderBy-1207110587";
      for (LogEntry element :
          loggingClient.listLogEntries(resourceNames, filter, orderBy).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
