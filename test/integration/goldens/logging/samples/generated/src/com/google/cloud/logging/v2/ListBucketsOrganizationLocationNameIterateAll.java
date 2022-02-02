package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogBucket;
import com.google.logging.v2.OrganizationLocationName;

public class ListBucketsOrganizationLocationNameIterateAll {

  public static void main(String[] args) throws Exception {
    listBucketsOrganizationLocationNameIterateAll();
  }

  public static void listBucketsOrganizationLocationNameIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      OrganizationLocationName parent = OrganizationLocationName.of("[ORGANIZATION]", "[LOCATION]");
      for (LogBucket element : configClient.listBuckets(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
