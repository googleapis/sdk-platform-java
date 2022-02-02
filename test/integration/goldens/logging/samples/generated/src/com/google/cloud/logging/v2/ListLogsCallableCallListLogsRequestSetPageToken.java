package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingClient;
import com.google.common.base.Strings;
import com.google.logging.v2.ListLogsRequest;
import com.google.logging.v2.ListLogsResponse;
import com.google.logging.v2.ProjectName;
import java.util.ArrayList;

public class ListLogsCallableCallListLogsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listLogsCallableCallListLogsRequestSetPageToken();
  }

  public static void listLogsCallableCallListLogsRequestSetPageToken() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      ListLogsRequest request =
          ListLogsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .addAllResourceNames(new ArrayList<String>())
              .build();
      while (true) {
        ListLogsResponse response = loggingClient.listLogsCallable().call(request);
        for (String element : response.getResponsesList()) {
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
