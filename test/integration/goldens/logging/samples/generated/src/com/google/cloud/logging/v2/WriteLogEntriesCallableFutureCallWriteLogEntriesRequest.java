package com.google.cloud.logging.v2.samples;

import com.google.api.MonitoredResource;
import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.LogEntry;
import com.google.logging.v2.LogName;
import com.google.logging.v2.WriteLogEntriesRequest;
import com.google.logging.v2.WriteLogEntriesResponse;
import java.util.ArrayList;
import java.util.HashMap;

public class WriteLogEntriesCallableFutureCallWriteLogEntriesRequest {

  public static void main(String[] args) throws Exception {
    writeLogEntriesCallableFutureCallWriteLogEntriesRequest();
  }

  public static void writeLogEntriesCallableFutureCallWriteLogEntriesRequest() throws Exception {
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
      ApiFuture<WriteLogEntriesResponse> future =
          loggingClient.writeLogEntriesCallable().futureCall(request);
      // Do something.
      WriteLogEntriesResponse response = future.get();
    }
  }
}
