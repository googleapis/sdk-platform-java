package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LocationName;
import com.google.logging.v2.LogBucket;

public class ListBucketsLocationNameIterateAll {

  public static void main(String[] args) throws Exception {
    listBucketsLocationNameIterateAll();
  }

  public static void listBucketsLocationNameIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      LocationName parent = LocationName.of("[PROJECT]", "[LOCATION]");
      for (LogBucket element : configClient.listBuckets(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
