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

// [START v2_logging_generated_loggingclient_listlogs_listlogsrequestiterateall]
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.ListLogsRequest;
import com.google.logging.v2.ProjectName;
import java.util.ArrayList;

public class ListLogsListLogsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listLogsListLogsRequestIterateAll();
  }

  public static void listLogsListLogsRequestIterateAll() throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (LoggingClient loggingClient = LoggingClient.create()) {
      ListLogsRequest request =
          ListLogsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .addAllResourceNames(new ArrayList<String>())
              .build();
      for (String element : loggingClient.listLogs(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
// [END v2_logging_generated_loggingclient_listlogs_listlogsrequestiterateall]