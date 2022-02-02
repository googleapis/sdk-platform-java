package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.BookName;

public class GetBookBookNameName {

  public static void main(String[] args) throws Exception {
    getBookBookNameName();
  }

  public static void getBookBookNameName() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      BookName name = BookName.of("[SHELF]", "[BOOK]");
      Book response = libraryServiceClient.getBook(name);
    }
  }
}
