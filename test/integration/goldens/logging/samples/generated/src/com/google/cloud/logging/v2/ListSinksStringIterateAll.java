package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.ProjectName;

public class ListSinksStringIterateAll {

  public static void main(String[] args) throws Exception {
    listSinksStringIterateAll();
  }

  public static void listSinksStringIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      for (LogSink element : configClient.listSinks(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
