package com.google.cloud.logging.v2.samples;

import com.google.api.MonitoredResourceDescriptor;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.ListMonitoredResourceDescriptorsRequest;

public class ListMonitoredResourceDescriptorsListMonitoredResourceDescriptorsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listMonitoredResourceDescriptorsListMonitoredResourceDescriptorsRequestIterateAll();
  }

  public static void
      listMonitoredResourceDescriptorsListMonitoredResourceDescriptorsRequestIterateAll()
          throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      ListMonitoredResourceDescriptorsRequest request =
          ListMonitoredResourceDescriptorsRequest.newBuilder()
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (MonitoredResourceDescriptor element :
          loggingClient.listMonitoredResourceDescriptors(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
