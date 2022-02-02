package com.google.cloud.example.library.v1.samples;

import com.google.cloud.example.library.v1.LibraryServiceClient;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.BookName;
import com.google.example.library.v1.ShelfName;

public class MoveBookBookNameNameShelfNameOtherShelfName {

  public static void main(String[] args) throws Exception {
    moveBookBookNameNameShelfNameOtherShelfName();
  }

  public static void moveBookBookNameNameShelfNameOtherShelfName() throws Exception {
    try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
      BookName name = BookName.of("[SHELF]", "[BOOK]");
      ShelfName otherShelfName = ShelfName.of("[SHELF_ID]");
      Book response = libraryServiceClient.moveBook(name, otherShelfName);
    }
  }
}
