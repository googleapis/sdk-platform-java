package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.BookName;
import com.google.example.library.v1.ShelfName;

public class MoveBookBookNameNameStringOtherShelfName {

  public static void main(String[] args) throws Exception {
    moveBookBookNameNameStringOtherShelfName();
  }

  public static void moveBookBookNameNameStringOtherShelfName() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      BookName name = BookName.of("[SHELF]", "[BOOK]");
      String otherShelfName = ShelfName.of("[SHELF_ID]").toString();
      Book response = libraryServiceClient.moveBook(name, otherShelfName);
    }
  }
}
