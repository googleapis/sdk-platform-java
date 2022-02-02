package com.google.cloud.logging.v2.samples;

import com.google.api.MonitoredResource;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.LogEntry;
import com.google.logging.v2.LogName;
import com.google.logging.v2.WriteLogEntriesResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public
class WriteLogEntriesStringLogNameMonitoredResourceResourceMapStringStringLabelsListLogEntryEntries {

  public static void main(String[] args) throws Exception {
    writeLogEntriesStringLogNameMonitoredResourceResourceMapStringStringLabelsListLogEntryEntries();
  }

  public static void
      writeLogEntriesStringLogNameMonitoredResourceResourceMapStringStringLabelsListLogEntryEntries()
          throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      String logName = LogName.ofProjectLogName("[PROJECT]", "[LOG]").toString();
      MonitoredResource resource = MonitoredResource.newBuilder().build();
      Map<String, String> labels = new HashMap<>();
      List<LogEntry> entries = new ArrayList<>();
      WriteLogEntriesResponse response =
          loggingClient.writeLogEntries(logName, resource, labels, entries);
    }
  }
}
