package com.google.cloud.logging.v2.samples;

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
