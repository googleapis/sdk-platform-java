package com.google.cloud.example.library.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.DeleteShelfRequest;
import com.google.example.library.v1.ShelfName;
import com.google.protobuf.Empty;

public class DeleteShelfCallableFutureCallDeleteShelfRequest {

  public static void main(String[] args) throws Exception {
    deleteShelfCallableFutureCallDeleteShelfRequest();
  }

  public static void deleteShelfCallableFutureCallDeleteShelfRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      DeleteShelfRequest request =
          DeleteShelfRequest.newBuilder().setName(ShelfName.of("[SHELF_ID]").toString()).build();
      ApiFuture<Empty> future = libraryServiceClient.deleteShelfCallable().futureCall(request);
      // Do something.
      future.get();
    }
  }
}
