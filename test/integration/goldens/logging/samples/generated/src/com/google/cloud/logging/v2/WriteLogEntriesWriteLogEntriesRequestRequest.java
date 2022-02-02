package com.google.cloud.logging.v2.samples;

import com.google.api.MonitoredResource;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.LogEntry;
import com.google.logging.v2.LogName;
import com.google.logging.v2.WriteLogEntriesRequest;
import com.google.logging.v2.WriteLogEntriesResponse;
import java.util.ArrayList;
import java.util.HashMap;

public class WriteLogEntriesWriteLogEntriesRequestRequest {

  public static void main(String[] args) throws Exception {
    writeLogEntriesWriteLogEntriesRequestRequest();
  }

  public static void writeLogEntriesWriteLogEntriesRequestRequest() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      WriteLogEntriesRequest request =
          WriteLogEntriesRequest.newBuilder()
              .setLogName(LogName.ofProjectLogName("[PROJECT]", "[LOG]").toString())
              .setResource(MonitoredResource.newBuilder().build())
              .putAllLabels(new HashMap<String, String>())
              .addAllEntries(new ArrayList<LogEntry>())
              .setPartialSuccess(true)
              .setDryRun(true)
              .build();
      WriteLogEntriesResponse response = loggingClient.writeLogEntries(request);
    }
  }
}
