package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.storage.v2.BucketName;
import com.google.storage.v2.GetNotificationRequest;
import com.google.storage.v2.Notification;
import com.google.storage.v2.StorageClient;

public class GetNotificationCallableFutureCallGetNotificationRequest {

  public static void main(String[] args) throws Exception {
    getNotificationCallableFutureCallGetNotificationRequest();
  }

  public static void getNotificationCallableFutureCallGetNotificationRequest() throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      GetNotificationRequest request =
          GetNotificationRequest.newBuilder()
              .setName(BucketName.of("[PROJECT]", "[BUCKET]").toString())
              .build();
      ApiFuture<Notification> future = storageClient.getNotificationCallable().futureCall(request);
      // Do something.
      Notification response = future.get();
    }
  }
}
