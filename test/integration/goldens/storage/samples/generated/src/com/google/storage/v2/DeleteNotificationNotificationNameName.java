package com.google.storage.v2.samples;

import com.google.protobuf.Empty;
import com.google.storage.v2.NotificationName;
import com.google.storage.v2.StorageClient;

public class DeleteNotificationNotificationNameName {

  public static void main(String[] args) throws Exception {
    deleteNotificationNotificationNameName();
  }

  public static void deleteNotificationNotificationNameName() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      NotificationName name = NotificationName.of("[PROJECT]", "[BUCKET]", "[NOTIFICATION]");
      storageClient.deleteNotification(name);
    }
  }
}
