/*
 * Copyright 2021 Google LLC
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

/**
 * The interfaces provided are listed below, along with usage samples.
 *
 * <p>======================= LibraryServiceClient =======================
 *
 * <p>Service Description: This API represents a simple digital library. It lets you manage Shelf
 * resources and Book resources in the library. It defines the following resource model:
 *
 * <p>- The API has a collection of [Shelf][google.example.library.v1.Shelf] resources, named
 * `shelves/&#42;`
 *
 * <p>- Each Shelf has a collection of [Book][google.example.library.v1.Book] resources, named
 * `shelves/&#42;/books/&#42;`
 *
 * <p>Sample for LibraryServiceClient:
 *
 * <pre>{@code
 * try (LibraryServiceClient libraryServiceClient = LibraryServiceClient.create()) {
 *   Shelf shelf = Shelf.newBuilder().build();
 *   Shelf response = libraryServiceClient.createShelf(shelf);
 * }
 * }</pre>
 */
@Generated("by gapic-generator-java")
package com.google.cloud.example.library.v1;

import javax.annotation.Generated;
