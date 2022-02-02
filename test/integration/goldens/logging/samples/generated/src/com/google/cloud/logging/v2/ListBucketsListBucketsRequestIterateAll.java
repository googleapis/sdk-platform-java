package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.ListBucketsRequest;
import com.google.logging.v2.LocationName;
import com.google.logging.v2.LogBucket;

public class ListBucketsListBucketsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listBucketsListBucketsRequestIterateAll();
  }

  public static void listBucketsListBucketsRequestIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ListBucketsRequest request =
          ListBucketsRequest.newBuilder()
              .setParent(LocationName.of("[PROJECT]", "[LOCATION]").toString())
              .setPageToken("pageToken873572522")
              .setPageSize(883849137)
              .build();
      for (LogBucket element : configClient.listBuckets(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
