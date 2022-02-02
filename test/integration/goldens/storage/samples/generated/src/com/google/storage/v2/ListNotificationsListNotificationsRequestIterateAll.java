package com.google.storage.v2.samples;

import com.google.storage.v2.ListNotificationsRequest;
import com.google.storage.v2.Notification;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListNotificationsListNotificationsRequestIterateAll {

  public static void main(String[] args) throws Exception {
    listNotificationsListNotificationsRequestIterateAll();
  }

  public static void listNotificationsListNotificationsRequestIterateAll() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ListNotificationsRequest request =
          ListNotificationsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      for (Notification element : storageClient.listNotifications(request).iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
