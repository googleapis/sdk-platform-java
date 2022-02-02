package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.BookName;

public class GetBookStringName {

  public static void main(String[] args) throws Exception {
    getBookStringName();
  }

  public static void getBookStringName() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      String name = BookName.of("[SHELF]", "[BOOK]").toString();
      Book response = libraryServiceClient.getBook(name);
    }
  }
}
