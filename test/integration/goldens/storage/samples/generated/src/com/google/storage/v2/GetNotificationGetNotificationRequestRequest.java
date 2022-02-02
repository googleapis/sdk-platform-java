package com.google.storage.v2.samples;

import com.google.storage.v2.BucketName;
import com.google.storage.v2.GetNotificationRequest;
import com.google.storage.v2.Notification;
import com.google.storage.v2.StorageClient;

public class GetNotificationGetNotificationRequestRequest {

  public static void main(String[] args) throws Exception {
    getNotificationGetNotificationRequestRequest();
  }

  public static void getNotificationGetNotificationRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      GetNotificationRequest request =
          GetNotificationRequest.newBuilder()
              .setName(BucketName.of("[PROJECT]", "[BUCKET]").toString())
              .build();
      Notification response = storageClient.getNotification(request);
    }
  }
}
