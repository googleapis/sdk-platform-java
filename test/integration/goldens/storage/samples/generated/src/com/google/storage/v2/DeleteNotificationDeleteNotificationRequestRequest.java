package com.google.storage.v2.samples;

import com.google.protobuf.Empty;
import com.google.storage.v2.DeleteNotificationRequest;
import com.google.storage.v2.NotificationName;
import com.google.storage.v2.StorageClient;

public class DeleteNotificationDeleteNotificationRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteNotificationDeleteNotificationRequestRequest();
  }

  public static void deleteNotificationDeleteNotificationRequestRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      DeleteNotificationRequest request =
          DeleteNotificationRequest.newBuilder()
              .setName(NotificationName.of("[PROJECT]", "[BUCKET]", "[NOTIFICATION]").toString())
              .build();
      storageClient.deleteNotification(request);
    }
  }
}
