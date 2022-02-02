package com.google.storage.v2.samples;

import com.google.storage.v2.Notification;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListNotificationsProjectNameIterateAll {

  public static void main(String[] args) throws Exception {
    listNotificationsProjectNameIterateAll();
  }

  public static void listNotificationsProjectNameIterateAll() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      for (Notification element : storageClient.listNotifications(parent).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
