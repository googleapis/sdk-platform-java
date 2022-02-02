package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.FolderName;
import com.google.logging.v2.LogSink;

public class ListSinksFolderNameIterateAll {

  public static void main(String[] args) throws Exception {
    listSinksFolderNameIterateAll();
  }

  public static void listSinksFolderNameIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      FolderName parent = FolderName.of("[FOLDER]");
      for (LogSink element : configClient.listSinks(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
