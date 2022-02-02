package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.OrganizationName;

public class ListSinksOrganizationNameIterateAll {

  public static void main(String[] args) throws Exception {
    listSinksOrganizationNameIterateAll();
  }

  public static void listSinksOrganizationNameIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      OrganizationName parent = OrganizationName.of("[ORGANIZATION]");
      for (LogSink element : configClient.listSinks(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
