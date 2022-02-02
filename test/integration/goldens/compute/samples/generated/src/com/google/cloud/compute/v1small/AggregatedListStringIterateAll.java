package com.google.cloud.compute.v1small.samples;

import com.google.cloud.compute.v1small.AddressesClient;
import com.google.cloud.compute.v1small.AddressesScopedList;
import java.util.Map;

public class AggregatedListStringIterateAll {

  public static void main(String[] args) throws Exception {
    aggregatedListStringIterateAll();
  }

  public static void aggregatedListStringIterateAll() throws Exception {
    try (AddressesClient addressesClient = AddressesClient.create()) {
      String project = "project-309310695";
      for (Map.Entry<String, AddressesScopedList> element :
          addressesClient.aggregatedList(project).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
