/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.logging.v2.samples;

// [START logging_v2_generated_loggingclient_writelogentries_lognamemonitoredresourcemapstringstringlistlogentry]
import com.google.api.MonitoredResource;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.LogEntry;
import com.google.logging.v2.LogName;
import com.google.logging.v2.WriteLogEntriesResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteLogEntriesLogNameMonitoredResourceMapStringStringListLogEntry {

  public static void main(String[] args) throws Exception {
    writeLogEntriesLogNameMonitoredResourceMapStringStringListLogEntry();
  }

  public static void writeLogEntriesLogNameMonitoredResourceMapStringStringListLogEntry()
      throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (LoggingClient loggingClient = LoggingClient.create()) {
      LogName logName = LogName.ofProjectLogName("[PROJECT]", "[LOG]");
      MonitoredResource resource = MonitoredResource.newBuilder().build();
      Map<String, String> labels = new HashMap<>();
      List<LogEntry> entries = new ArrayList<>();
      WriteLogEntriesResponse response =
          loggingClient.writeLogEntries(logName, resource, labels, entries);
    }
  }
}
// [END logging_v2_generated_loggingclient_writelogentries_lognamemonitoredresourcemapstringstringlistlogentry]