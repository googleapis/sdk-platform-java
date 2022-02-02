package com.google.cloud.logging.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.ListBucketsRequest;
import com.google.logging.v2.LocationName;
import com.google.logging.v2.LogBucket;

public class ListBucketsPagedCallableFutureCallListBucketsRequest {

  public static void main(String[] args) throws Exception {
    listBucketsPagedCallableFutureCallListBucketsRequest();
  }

  public static void listBucketsPagedCallableFutureCallListBucketsRequest() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ListBucketsRequest request =
          ListBucketsRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      ApiFuture<LogBucket> future = configClient.listBucketsPagedCallable().futureCall(request);
      // Do something.
      for (LogBucket element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
