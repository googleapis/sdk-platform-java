package com.google.cloud.logging.v2.samples;

import com.google.cloud.logging.v2.ConfigClient;
import com.google.logging.v2.LogView;

public class ListViewsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listViewsStringIterateAll();
  }

  public static void listViewsStringIterateAll() throws Exception {
    try (ConfigClient configClient = ConfigClient.create()) {
      String parent = "parent-995424086";
      for (LogView element : configClient.listViews(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
