package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.LoggingClient;
import com.google.logging.v2.FolderName;

public class ListLogsFolderNameIterateAll {

  public static void main(String[] args) throws Exception {
    listLogsFolderNameIterateAll();
  }

  public static void listLogsFolderNameIterateAll() throws Exception {
    try (LoggingClient loggingClient = LoggingClient.create()) {
      FolderName parent = FolderName.of("[FOLDER]");
      for (String element : loggingClient.listLogs(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
