/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.example.library.v1;

import com.google.api.pathtemplate.PathTemplate;
import com.google.api.resourcenames.ResourceName;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
@Generated("by gapic-generator-java")
public class BookName implements ResourceName {
  private static final PathTemplate SHELF_ID_BOOK_ID =
      PathTemplate.createWithoutUrlEncoding("shelves/{shelf_id}/books/{book_id}");
  private volatile Map<String, String> fieldValuesMap;
  private final String shelfId;
  private final String bookId;

  @Deprecated
  protected BookName() {
    shelfId = null;
    bookId = null;
  }

  private BookName(Builder builder) {
    shelfId = Preconditions.checkNotNull(builder.getShelfId());
    bookId = Preconditions.checkNotNull(builder.getBookId());
  }

  public String getShelfId() {
    return shelfId;
  }

  public String getBookId() {
    return bookId;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public Builder toBuilder() {
    return new Builder(this);
  }

  public static BookName of(String shelfId, String bookId) {
    return newBuilder().setShelfId(shelfId).setBookId(bookId).build();
  }

  public static String format(String shelfId, String bookId) {
    return newBuilder().setShelfId(shelfId).setBookId(bookId).build().toString();
  }

  public static BookName parse(String formattedString) {
    if (formattedString.isEmpty()) {
      return null;
    }
    Map<String, String> matchMap =
        SHELF_ID_BOOK_ID.validatedMatch(
            formattedString, "BookName.parse: formattedString not in valid format");
    return of(matchMap.get("shelf_id"), matchMap.get("book_id"));
  }

  public static List<BookName> parseList(List<String> formattedStrings) {
    List<BookName> list = new ArrayList<>(formattedStrings.size());
    for (String formattedString : formattedStrings) {
      list.add(parse(formattedString));
    }
    return list;
  }

  public static List<String> toStringList(List<BookName> values) {
    List<String> list = new ArrayList<>(values.size());
    for (BookName value : values) {
      if (value == null) {
        list.add("");
      } else {
        list.add(value.toString());
      }
    }
    return list;
  }

  public static boolean isParsableFrom(String formattedString) {
    return SHELF_ID_BOOK_ID.matches(formattedString);
  }

  @Override
  public Map<String, String> getFieldValuesMap() {
    if (fieldValuesMap == null) {
      synchronized (this) {
        if (fieldValuesMap == null) {
          ImmutableMap.Builder<String, String> fieldMapBuilder = ImmutableMap.builder();
          if (shelfId != null) {
            fieldMapBuilder.put("shelf_id", shelfId);
          }
          if (bookId != null) {
            fieldMapBuilder.put("book_id", bookId);
          }
          fieldValuesMap = fieldMapBuilder.build();
        }
      }
    }
    return fieldValuesMap;
  }

  public String getFieldValue(String fieldName) {
    return getFieldValuesMap().get(fieldName);
  }

  @Override
  public String toString() {
    return SHELF_ID_BOOK_ID.instantiate("shelf_id", shelfId, "book_id", bookId);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o != null || getClass() == o.getClass()) {
      BookName that = ((BookName) o);
      return Objects.equals(this.shelfId, that.shelfId) && Objects.equals(this.bookId, that.bookId);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= Objects.hashCode(shelfId);
    h *= 1000003;
    h ^= Objects.hashCode(bookId);
    return h;
  }

  /** Builder for shelves/{shelf_id}/books/{book_id}. */
  public static class Builder {
    private String shelfId;
    private String bookId;

    protected Builder() {}

    public String getShelfId() {
      return shelfId;
    }

    public String getBookId() {
      return bookId;
    }

    public Builder setShelfId(String shelfId) {
      this.shelfId = shelfId;
      return this;
    }

    public Builder setBookId(String bookId) {
      this.bookId = bookId;
      return this;
    }

    private Builder(BookName bookName) {
      shelfId = bookName.shelfId;
      bookId = bookName.bookId;
    }

    public BookName build() {
      return new BookName(this);
    }
  }
}
