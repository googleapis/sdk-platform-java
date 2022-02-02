package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.BookName;
import com.google.example.library.v1.ShelfName;

public class MoveBookStringNameStringOtherShelfName {

  public static void main(String[] args) throws Exception {
    moveBookStringNameStringOtherShelfName();
  }

  public static void moveBookStringNameStringOtherShelfName() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      String name = BookName.of("[SHELF]", "[BOOK]").toString();
      String otherShelfName = ShelfName.of("[SHELF_ID]").toString();
      Book response = libraryServiceClient.moveBook(name, otherShelfName);
    }
  }
}
