package com.google.storage.v2.samples;

import com.google.common.base.Strings;
import com.google.storage.v2.ListNotificationsRequest;
import com.google.storage.v2.ListNotificationsResponse;
import com.google.storage.v2.Notification;
import com.google.storage.v2.ProjectName;
import com.google.storage.v2.StorageClient;

public class ListNotificationsCallableCallListNotificationsRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listNotificationsCallableCallListNotificationsRequestSetPageToken();
  }

  public static void listNotificationsCallableCallListNotificationsRequestSetPageToken()
      throws Exception {
    try (StorageClient storageClient = StorageClient.create()) {
      ListNotificationsRequest request =
          ListNotificationsRequest.newBuilder()
              .setParent(ProjectName.of("[PROJECT]").toString())
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      while (true) {
        ListNotificationsResponse response =
            storageClient.listNotificationsCallable().call(request);
        for (Notification element : response.getResponsesList()) {
          // doThingsWith(element);
        }
        String nextPageToken = response.getNextPageToken();
        if (!Strings.isNullOrEmpty(nextPageToken)) {
          request = request.toBuilder().setPageToken(nextPageToken).build();
        } else {
          break;
        }
      }
    }
  }
}
