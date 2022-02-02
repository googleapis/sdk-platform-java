package com.google.storage.v2.samples;

import com.google.storage.v2.CreateNotificationRequest;
import com.google.storage.v2.Notification;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class CreateNotificationCreateNotificationRequestRequest {

  public static void main(String[] args) throws Exception {
    createNotificationCreateNotificationRequestRequest();
  }

  public static void createNotificationCreateNotificationRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      CreateNotificationRequest request =
          CreateNotificationRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setNotification(Notification.newBuilder().build())
              .build();
      Notification response = storageClient.createNotification(request);
    }
  }
}
