package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.protobuf.Empty;
import com.google.storage.v2.DeleteNotificationRequest;
import com.google.storage.v2.NotificationName;
import com.google.storage.v2.StorageClient;

public class DeleteNotificationCallableFutureCallDeleteNotificationRequest {

  public static void main(String[] args) throws Exception {
    deleteNotificationCallableFutureCallDeleteNotificationRequest();
  }

  public static void deleteNotificationCallableFutureCallDeleteNotificationRequest()
      throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      DeleteNotificationRequest request =
          DeleteNotificationRequest.newBuilder()
              .setName(NotificationName.of("[PROJECT]", "[BUCKET]", "[NOTIFICATION]").toString())
              .build();
      ApiFuture<Empty> future = storageClient.deleteNotificationCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
