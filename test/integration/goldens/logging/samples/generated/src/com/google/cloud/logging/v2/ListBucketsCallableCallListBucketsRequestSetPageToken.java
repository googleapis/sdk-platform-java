package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.common.base.Strings;
import com.google.logging.v2.ListBucketsRequest;
import com.google.logging.v2.ListBucketsResponse;
import com.google.logging.v2.LocationName;
import com.google.logging.v2.LogBucket;

public class ListBucketsCallableCallListBucketsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listBucketsCallableCallListBucketsRequestSetPageToken();
  }

  public static void listBucketsCallableCallListBucketsRequestSetPageToken() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ListBucketsRequest request =
          ListBucketsRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      while (true) {
        ListBucketsResponse response = configClient.listBucketsCallable().call(request);
        for (LogBucket element : response.getResponsesList()) {
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
