package com.google.storage.v2.samples;

import com.google.storage.v2.BucketName;
import com.google.storage.v2.Notification;
import com.google.storage.v2.StorageClient;

public class GetNotificationStringName {

  public static void main(String[] args) throws Exception {
    getNotificationStringName();
  }

  public static void getNotificationStringName() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      String name = BucketName.of("[PROJECT]", "[BUCKET]").toString();
      Notification response = storageClient.getNotification(name);
    }
  }
}
