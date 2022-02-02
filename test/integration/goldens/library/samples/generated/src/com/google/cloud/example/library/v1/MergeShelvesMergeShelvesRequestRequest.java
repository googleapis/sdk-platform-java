package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.MergeShelvesRequest;
import com.google.example.library.v1.Shelf;
import com.google.example.library.v1.ShelfName;

public class MergeShelvesMergeShelvesRequestRequest {

  public static void main(String[] args) throws Exception {
    mergeShelvesMergeShelvesRequestRequest();
  }

  public static void mergeShelvesMergeShelvesRequestRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      MergeShelvesRequest request =
          MergeShelvesRequest.newBuilder()
              .setName(ShelfName.of("[SHELF_ID]").toString())
              .setOtherShelf(ShelfName.of("[SHELF_ID]").toString())
              .build();
      Shelf response = libraryServiceClient.mergeShelves(request);
    }
  }
}
