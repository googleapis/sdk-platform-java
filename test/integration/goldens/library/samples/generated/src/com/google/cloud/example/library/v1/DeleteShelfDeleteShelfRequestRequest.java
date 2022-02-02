package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.DeleteShelfRequest;
import com.google.example.library.v1.ShelfName;
import com.google.protobuf.Empty;

public class DeleteShelfDeleteShelfRequestRequest {

  public static void main(String[] args) throws Exception {
    deleteShelfDeleteShelfRequestRequest();
  }

  public static void deleteShelfDeleteShelfRequestRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      DeleteShelfRequest request =
          DeleteShelfRequest.newBuilder().setName(ShelfName.of("[SHELF_ID]").toString()).build();
      libraryServiceClient.deleteShelf(request);
    }
  }
}
