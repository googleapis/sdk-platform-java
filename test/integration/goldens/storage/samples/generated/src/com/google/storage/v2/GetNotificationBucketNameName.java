package com.google.storage.v2.samples;

import com.google.storage.v2.BucketName;
import com.google.storage.v2.Notification;
import com.google.storage.v2.StorageClient;

public class GetNotificationBucketNameName {

  public static void main(String[] args) throws Exception {
    getNotificationBucketNameName();
  }

  public static void getNotificationBucketNameName() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      BucketName name = BucketName.of("[PROJECT]", "[BUCKET]");
      Notification response = storageClient.getNotification(name);
    }
  }
}
