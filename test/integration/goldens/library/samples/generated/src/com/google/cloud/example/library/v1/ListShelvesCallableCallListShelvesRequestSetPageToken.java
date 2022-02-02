package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.common.base.Strings;
import com.google.example.library.v1.ListShelvesRequest;
import com.google.example.library.v1.ListShelvesResponse;
import com.google.example.library.v1.Shelf;

public class ListShelvesCallableCallListShelvesRequestSetPageToken {

  public static void main(String[] args) throws Exception {
    listShelvesCallableCallListShelvesRequestSetPageToken();
  }

  public static void listShelvesCallableCallListShelvesRequestSetPageToken() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      ListShelvesRequest request =
          ListShelvesRequest.newBuilder()
              .setPageSize(883849137)
              .setPageToken("pageToken873572522")
              .build();
      while (true) {
        ListShelvesResponse response = libraryServiceClient.listShelvesCallable().call(request);
        for (Shelf element : response.getResponsesList()) {
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
