package com.google.cloud.logging.v2.samples;

import com.google.api.MonitoredResourceDescriptor;
import com.google.cloud.logging.v2.LoggingClient;
import com.google.common.base.Strings;
import com.google.logging.v2.ListMonitoredResourceDescriptorsRequest;
import com.google.logging.v2.ListMonitoredResourceDescriptorsResponse;

public
class ListMonitoredResourceDescriptorsCallableCallListMonitoredResourceDescriptorsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listMonitoredResourceDescriptorsCallableCallListMonitoredResourceDescriptorsRequestSetPageToken();
  }

  public static void
      listMonitoredResourceDescriptorsCallableCallListMonitoredResourceDescriptorsRequestSetPageToken()
          throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      ListMonitoredResourceDescriptorsRequest request =
          ListMonitoredResourceDescriptorsRequest.newBuilder()
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      while (true) {
        ListMonitoredResourceDescriptorsResponse response =
            loggingClient.listMonitoredResourceDescriptorsCallable().call(request);
        for (MonitoredResourceDescriptor element : response.getResponsesList()) {
          // doThingsWith(element);
        }
        String nextPageToken = response.getNextPageToken();
        if (!Strings.isNullOrEmpty(nextPageToken)) {
          request = request.toBuilder().setPageToken(nextPageToken).build();
        } else {
          break;
        }
      }
    }
  }
}
