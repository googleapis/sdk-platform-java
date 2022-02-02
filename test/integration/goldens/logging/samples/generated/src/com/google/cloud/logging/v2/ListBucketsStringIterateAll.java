package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LocationName;
import com.google.logging.v2.LogBucket;

public class ListBucketsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listBucketsStringIterateAll();
  }

  public static void listBucketsStringIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      String parent = LocationName.of("[PROJECT]", "[LOCATION]").toString();
      for (LogBucket element : configClient.listBuckets(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
