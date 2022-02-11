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

// [START v2_logging_generated_loggingclient_writelogentries_callablefuturecallwritelogentriesrequest]
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
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
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
// [END v2_logging_generated_loggingclient_writelogentries_callablefuturecallwritelogentriesrequest]