package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.storage.v2.CreateNotificationRequest;
import com.google.storage.v2.Notification;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class CreateNotificationCallableFutureCallCreateNotificationRequest {

  public static void main(String[] args) throws Exception {
    createNotificationCallableFutureCallCreateNotificationRequest();
  }

  public static void createNotificationCallableFutureCallCreateNotificationRequest()
      throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      CreateNotificationRequest request =
          CreateNotificationRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setNotification(Notification.newBuilder().build())
              .build();
      ApiFuture<Notification> future =
          storageClient.createNotificationCallable().futureCall(request);
      // Do something.
      Notification response = future.get();
    }
  }
}
