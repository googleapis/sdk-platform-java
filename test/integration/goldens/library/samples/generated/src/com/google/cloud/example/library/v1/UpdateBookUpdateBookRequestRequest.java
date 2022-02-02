package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.UpdateBookRequest;
import com.google.protobuf.FieldMask;

public class UpdateBookUpdateBookRequestRequest {

  public static void main(String[] args) throws Exception {
    updateBookUpdateBookRequestRequest();
  }

  public static void updateBookUpdateBookRequestRequest() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      UpdateBookRequest request =
          UpdateBookRequest.newBuilder()
              .setBook(Book.newBuilder().build())
              .setUpdateMask(FieldMask.newBuilder().build())
              .build();
      Book response = libraryServiceClient.updateBook(request);
    }
  }
}
