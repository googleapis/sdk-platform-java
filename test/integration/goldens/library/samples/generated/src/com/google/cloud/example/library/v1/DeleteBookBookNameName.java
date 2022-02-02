package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.BookName;
import com.google.protobuf.Empty;

public class DeleteBookBookNameName {

  public static void main(String[] args) throws Exception {
    deleteBookBookNameName();
  }

  public static void deleteBookBookNameName() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      BookName name = BookName.of("[SHELF]", "[BOOK]");
      libraryServiceClient.deleteBook(name);
    }
  }
}
