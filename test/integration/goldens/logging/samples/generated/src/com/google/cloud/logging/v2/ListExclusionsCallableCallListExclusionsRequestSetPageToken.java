package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.common.base.Strings;
import com.google.logging.v2.ListExclusionsRequest;
import com.google.logging.v2.ListExclusionsResponse;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.ProjectName;

public class ListExclusionsCallableCallListExclusionsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listExclusionsCallableCallListExclusionsRequestSetPageToken();
  }

  public static void listExclusionsCallableCallListExclusionsRequestSetPageToken()
      throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ListExclusionsRequest request =
          ListExclusionsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      while (true) {
        ListExclusionsResponse response = configClient.listExclusionsCallable().call(request);
        for (LogExclusion element : response.getResponsesList()) {
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
