package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.BookName;
import com.google.protobuf.Empty;

public class DeleteBookStringName {

  public static void main(String[] args) throws Exception {
    deleteBookStringName();
  }

  public static void deleteBookStringName() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      String name = BookName.of("[SHELF]", "[BOOK]").toString();
      libraryServiceClient.deleteBook(name);
    }
  }
}
