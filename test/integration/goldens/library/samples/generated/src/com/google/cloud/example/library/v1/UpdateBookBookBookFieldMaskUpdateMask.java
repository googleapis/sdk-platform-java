package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.protobuf.FieldMask;

public class UpdateBookBookBookFieldMaskUpdateMask {

  public static void main(String[] args) throws Exception {
    updateBookBookBookFieldMaskUpdateMask();
  }

  public static void updateBookBookBookFieldMaskUpdateMask() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      Book book = Book.newBuilder().build();
      FieldMask updateMask = FieldMask.newBuilder().build();
      Book response = libraryServiceClient.updateBook(book, updateMask);
    }
  }
}
