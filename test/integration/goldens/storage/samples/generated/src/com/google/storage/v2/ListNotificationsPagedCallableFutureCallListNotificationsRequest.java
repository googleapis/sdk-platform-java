package com.google.storage.v2.samples;

import com.google.api.core.ApiFuture;
import com.google.storage.v2.ListNotificationsRequest;
import com.google.storage.v2.Notification;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListNotificationsPagedCallableFutureCallListNotificationsRequest {

  public static void main(String[] args) throws Exception {
    listNotificationsPagedCallableFutureCallListNotificationsRequest();
  }

  public static void listNotificationsPagedCallableFutureCallListNotificationsRequest()
      throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ListNotificationsRequest request =
          ListNotificationsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      ApiFuture<Notification> future =
          storageClient.listNotificationsPagedCallable().futureCall(request);
      // Do something.
      for (Notification element : future.get().iterateAll()) {
        // doThingsWith(element);
      }
    }
  }
}
