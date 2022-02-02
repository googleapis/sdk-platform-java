package com.google.storage.v2.samples;

import com.google.storage.v2.Notification;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class CreateNotificationStringParentNotificationNotification {

  public static void main(String[] args) throws Exception {
    createNotificationStringParentNotificationNotification();
  }

  public static void createNotificationStringParentNotificationNotification() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String parent = ProjectName.of("[PROJECT]").toString();
      Notification notification = Notification.newBuilder().build();
      Notification response = storageClient.createNotification(parent, notification);
    }
  }
}
