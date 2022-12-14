/*
 * Copyright 2022 Google LLC
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

// [START logging_v2_generated_LoggingServiceV2_WriteLogEntries_sync]
import com.google.api.MonitoredResource;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.LogEntry;
import com.google.logging.v2.LogName;
import com.google.logging.v2.WriteLogEntriesRequest;
import com.google.logging.v2.WriteLogEntriesResponse;
import java.util.ArrayList;
import java.util.HashMap;

public class SyncWriteLogEntries {

  public static void main(String[] args) throws Exception {
    syncWriteLogEntries();
  }

  public static void syncWriteLogEntries() throws Exception {
    // This snippet has been automatically generated and should be regarded as a code template only.
    // It will require modifications to work:
    // - It may require correct/in-range values for request initialization.
    // - It may require specifying regional endpoints when creating the service client as shown in
    // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
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
// [END logging_v2_generated_LoggingServiceV2_WriteLogEntries_sync]
