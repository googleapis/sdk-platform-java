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

// [START v2_logging_generated_loggingclient_listmonitoredresourcedescriptors_pagedcallablefuturecalllistmonitoredresourcedescriptorsrequest]
import com.google.api.MonitoredResourceDescriptor;
import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.ListMonitoredResourceDescriptorsRequest;

public
class ListMonitoredResourceDescriptorsPagedCallableFutureCallListMonitoredResourceDescriptorsRequest {

  public static void main(String[] args) throws Exception {
    listMonitoredResourceDescriptorsPagedCallableFutureCallListMonitoredResourceDescriptorsRequest();
  }

  public static void
      listMonitoredResourceDescriptorsPagedCallableFutureCallListMonitoredResourceDescriptorsRequest()
          throws Exception {
    // This snippet has been automatically generated for illustrative purposes only.
    // It may require modifications to work in your environment.
    try (LoggingClient loggingClient = LoggingClient.create()) {
      ListMonitoredResourceDescriptorsRequest request =
          ListMonitoredResourceDescriptorsRequest.newBuilder()
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<MonitoredResourceDescriptor> future =
          loggingClient.listMonitoredResourceDescriptorsPagedCallable().futureCall(request);
      // Do something.
      for (MonitoredResourceDescriptor element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
// [END v2_logging_generated_loggingclient_listmonitoredresourcedescriptors_pagedcallablefuturecalllistmonitoredresourcedescriptorsrequest]