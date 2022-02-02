package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.ProjectName;

public class ListSinksProjectNameIterateAll {

  public static void main(String[] args) throws Exception {
    listSinksProjectNameIterateAll();
  }

  public static void listSinksProjectNameIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      for (LogSink element : configClient.listSinks(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
