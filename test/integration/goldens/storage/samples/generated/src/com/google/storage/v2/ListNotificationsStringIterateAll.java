package com.google.storage.v2.samples;

import com.google.storage.v2.Notification;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListNotificationsStringIterateAll {

  public static void main(String[] args) throws Exception {
    listNotificationsStringIterateAll();
  }

  public static void listNotificationsStringIterateAll() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      for (Notification element : storageClient.listNotifications(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
