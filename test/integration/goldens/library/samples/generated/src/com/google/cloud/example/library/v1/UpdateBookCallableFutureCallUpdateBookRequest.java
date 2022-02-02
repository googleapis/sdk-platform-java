package com.google.cloud.example.library.v1.samples;

import com.google.api.core.ApiFuture;
import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.UpdateBookRequest;
import com.google.protobuf.FieldMask;

public class UpdateBookCallableFutureCallUpdateBookRequest {

  public static void main(String[] args) throws Exception {
    updateBookCallableFutureCallUpdateBookRequest();
  }

  public static void updateBookCallableFutureCallUpdateBookRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      UpdateBookRequest request =
          UpdateBookRequest.newBuilder()
              .setBook(Book.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      ApiFuture<Book> future = libraryServiceClient.updateBookCallable().futureCall(request);
      // Do something.
      Book response = future.get();
    }
  }
}
