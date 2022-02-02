package com.google.storage.v2.samples;

import com.google.storage.v2.Notification;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class CreateNotificationProjectNameParentNotificationNotification {

  public static void main(String[] args) throws Exception {
    createNotificationProjectNameParentNotificationNotification();
  }

  public static void createNotificationProjectNameParentNotificationNotification()
      throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ProjectName parent = ProjectName.of("[PROJECT]");
      Notification notification = Notification.newBuilder().build();
      Notification response = storageClient.createNotification(parent, notification);
    }
  }
}
