package com.google.storage.v2.samples;

import com.google.protobuf.Empty;
import com.google.storage.v2.NotificationName;
import com.google.storage.v2.StorageClient;

public class DeleteNotificationStringName {

  public static void main(String[] args) throws Exception {
    deleteNotificationStringName();
  }

  public static void deleteNotificationStringName() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String name = NotificationName.of("[PROJECT]", "[BUCKET]", "[NOTIFICATION]").toString();
      storageClient.deleteNotification(name);
    }
  }
}
