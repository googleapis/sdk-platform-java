package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.ListExclusionsRequest;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.ProjectName;

public class ListExclusionsPagedCallableFutureCallListExclusionsRequest {

  public static void main(String[] args) throws Exception {
    listExclusionsPagedCallableFutureCallListExclusionsRequest();
  }

  public static void listExclusionsPagedCallableFutureCallListExclusionsRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ListExclusionsRequest request =
          ListExclusionsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      ApiFuture<LogExclusion> future =
          configClient.listExclusionsPagedCallable().futureCall(request);
      // Do something.
      for (LogExclusion element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
