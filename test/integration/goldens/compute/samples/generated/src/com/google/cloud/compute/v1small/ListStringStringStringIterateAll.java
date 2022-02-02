package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.Address;
import com.google.cloud.compute.v1small.AddressesClient;

public class ListStringStringStringIterateAll {

  public static void main(String[] args) throws Exception {
    listStringStringStringIterateAll();
  }

  public static void listStringStringStringIterateAll() throws Exception {
    try (AddressesClient addressesClient = AddressesClient.create()) {
      String project = "project-309310695";
      String region = "region-934795532";
      String orderBy = "orderBy-1207110587";
      for (Address element : addressesClient.list(project, region, orderBy).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
