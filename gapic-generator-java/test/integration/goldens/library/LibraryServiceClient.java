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

package com.google.cloud.example.library.v1;

import com.google.api.core.ApiFunction;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.api.core.BetaApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.paging.AbstractFixedSizeCollection;
import com.google.api.gax.paging.AbstractPage;
import com.google.api.gax.paging.AbstractPagedListResponse;
import com.google.api.gax.rpc.PageContext;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.cloud.example.library.v1.stub.LibraryServiceStub;
import com.google.cloud.example.library.v1.stub.LibraryServiceStubSettings;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.example.library.v1.Book;
import com.google.example.library.v1.BookName;
import com.google.example.library.v1.CreateBookRequest;
import com.google.example.library.v1.CreateShelfRequest;
import com.google.example.library.v1.DeleteBookRequest;
import com.google.example.library.v1.DeleteShelfRequest;
import com.google.example.library.v1.GetBookRequest;
import com.google.example.library.v1.GetShelfRequest;
import com.google.example.library.v1.ListBooksRequest;
import com.google.example.library.v1.ListBooksResponse;
import com.google.example.library.v1.ListShelvesRequest;
import com.google.example.library.v1.ListShelvesResponse;
import com.google.example.library.v1.MergeShelvesRequest;
import com.google.example.library.v1.MoveBookRequest;
import com.google.example.library.v1.Shelf;
import com.google.example.library.v1.ShelfName;
import com.google.example.library.v1.UpdateBookRequest;
import com.google.protobuf.Empty;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * Service Description: This API represents a simple digital library. It lets you manage Shelf
 * resources and Book resources in the library. It defines the following resource model:
 *
 * <p>- The API has a collection of [Shelf][google.example.library.v1.Shelf] resources, named
 * `shelves/&#42;`
 *
 * <p>- Each Shelf has a collection of [Book][google.example.library.v1.Book] resources, named
 * `shelves/&#42;/books/&#42;`
 *
 * <p>This class provides the ability to make remote calls to the backing service through method
 * calls that map to API methods. Sample code to get started:
 *
 * <p>Note: close() needs to be called on the LibraryServiceClient object to clean up resources such
 * as threads. In the example above, try-with-resources is used, which automatically calls close().
 *
 * <p>The surface of this class includes several types of Java methods for each of the API's
 * methods:
 *
 * <ol>
 *   <li>A "flattened" method. With this type of method, the fields of the request type have been
 *       converted into function parameters. It may be the case that not all fields are available as
 *       parameters, and not every API method will have a flattened method entry point.
 *   <li>A "request object" method. This type of method only takes one parameter, a request object,
 *       which must be constructed before the call. Not every API method will have a request object
 *       method.
 *   <li>A "callable" method. This type of method takes no parameters and returns an immutable API
 *       callable object, which can be used to initiate calls to the service.
 * </ol>
 *
 * <p>See the individual methods for example code.
 *
 * <p>Many parameters require resource names to be formatted in a particular way. To assist with
 * these names, this class includes a format method for each type of name, and additionally a parse
 * method to extract the individual identifiers contained within names that are returned.
 *
 * <p>This class can be customized by passing in a custom instance of LibraryServiceSettings to
 * create(). For example:
 *
 * <p>To customize credentials:
 *
 * <pre>{@code
 * LibraryServiceSettings libraryServiceSettings =
 *     LibraryServiceSettings.newBuilder()
 *         .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
 *         .build();
 * LibraryServiceClient libraryServiceClient = LibraryServiceClient.create(libraryServiceSettings);
 * }</pre>
 *
 * <p>To customize the endpoint:
 *
 * <pre>{@code
 * LibraryServiceSettings libraryServiceSettings =
 *     LibraryServiceSettings.newBuilder().setEndpoint(myEndpoint).build();
 * LibraryServiceClient libraryServiceClient = LibraryServiceClient.create(libraryServiceSettings);
 * }</pre>
 *
 * <p>Please refer to the GitHub repository's samples for more quickstart code snippets.
 */
@Generated("by gapic-generator-java")
public class LibraryServiceClient implements BackgroundResource {
  private final LibraryServiceSettings settings;
  private final LibraryServiceStub stub;

  /** Constructs an instance of LibraryServiceClient with default settings. */
  public static final LibraryServiceClient create() throws IOException {
    return create(LibraryServiceSettings.newBuilder().build());
  }

  /**
   * Constructs an instance of LibraryServiceClient, using the given settings. The channels are
   * created based on the settings passed in, or defaults for any settings that are not set.
   */
  public static final LibraryServiceClient create(LibraryServiceSettings settings)
      throws IOException {
    return new LibraryServiceClient(settings);
  }

  /**
   * Constructs an instance of LibraryServiceClient, using the given stub for making calls. This is
   * for advanced usage - prefer using create(LibraryServiceSettings).
   */
  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  public static final LibraryServiceClient create(LibraryServiceStub stub) {
    return new LibraryServiceClient(stub);
  }

  /**
   * Constructs an instance of LibraryServiceClient, using the given settings. This is protected so
   * that it is easy to make a subclass, but otherwise, the static factory methods should be
   * preferred.
   */
  protected LibraryServiceClient(LibraryServiceSettings settings) throws IOException {
    this.settings = settings;
    this.stub = ((LibraryServiceStubSettings) settings.getStubSettings()).createStub();
  }

  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  protected LibraryServiceClient(LibraryServiceStub stub) {
    this.settings = null;
    this.stub = stub;
  }

  public final LibraryServiceSettings getSettings() {
    return settings;
  }

  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  public LibraryServiceStub getStub() {
    return stub;
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a shelf, and returns the new Shelf.
   *
   * @param shelf The shelf to create.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Shelf createShelf(Shelf shelf) {
    CreateShelfRequest request = CreateShelfRequest.newBuilder().setShelf(shelf).build();
    return createShelf(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a shelf, and returns the new Shelf.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Shelf createShelf(CreateShelfRequest request) {
    return createShelfCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a shelf, and returns the new Shelf.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<CreateShelfRequest, Shelf> createShelfCallable() {
    return stub.createShelfCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a shelf. Returns NOT_FOUND if the shelf does not exist.
   *
   * @param name The name of the shelf to retrieve.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Shelf getShelf(ShelfName name) {
    GetShelfRequest request =
        GetShelfRequest.newBuilder().setName(name == null ? null : name.toString()).build();
    return getShelf(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a shelf. Returns NOT_FOUND if the shelf does not exist.
   *
   * @param name The name of the shelf to retrieve.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Shelf getShelf(String name) {
    GetShelfRequest request = GetShelfRequest.newBuilder().setName(name).build();
    return getShelf(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a shelf. Returns NOT_FOUND if the shelf does not exist.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Shelf getShelf(GetShelfRequest request) {
    return getShelfCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a shelf. Returns NOT_FOUND if the shelf does not exist.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<GetShelfRequest, Shelf> getShelfCallable() {
    return stub.getShelfCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists shelves. The order is unspecified but deterministic. Newly created shelves will not
   * necessarily be added to the end of this list.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListShelvesPagedResponse listShelves(ListShelvesRequest request) {
    return listShelvesPagedCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists shelves. The order is unspecified but deterministic. Newly created shelves will not
   * necessarily be added to the end of this list.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<ListShelvesRequest, ListShelvesPagedResponse>
      listShelvesPagedCallable() {
    return stub.listShelvesPagedCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists shelves. The order is unspecified but deterministic. Newly created shelves will not
   * necessarily be added to the end of this list.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<ListShelvesRequest, ListShelvesResponse> listShelvesCallable() {
    return stub.listShelvesCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a shelf. Returns NOT_FOUND if the shelf does not exist.
   *
   * @param name The name of the shelf to delete.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteShelf(ShelfName name) {
    DeleteShelfRequest request =
        DeleteShelfRequest.newBuilder().setName(name == null ? null : name.toString()).build();
    deleteShelf(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a shelf. Returns NOT_FOUND if the shelf does not exist.
   *
   * @param name The name of the shelf to delete.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteShelf(String name) {
    DeleteShelfRequest request = DeleteShelfRequest.newBuilder().setName(name).build();
    deleteShelf(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a shelf. Returns NOT_FOUND if the shelf does not exist.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteShelf(DeleteShelfRequest request) {
    deleteShelfCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a shelf. Returns NOT_FOUND if the shelf does not exist.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<DeleteShelfRequest, Empty> deleteShelfCallable() {
    return stub.deleteShelfCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Merges two shelves by adding all books from the shelf named `other_shelf_name` to shelf `name`,
   * and deletes `other_shelf_name`. Returns the updated shelf. The book ids of the moved books may
   * not be the same as the original books.
   *
   * <p>Returns NOT_FOUND if either shelf does not exist. This call is a no-op if the specified
   * shelves are the same.
   *
   * @param name The name of the shelf we're adding books to.
   * @param otherShelfName The name of the shelf we're removing books from and deleting.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Shelf mergeShelves(ShelfName name, ShelfName otherShelfName) {
    MergeShelvesRequest request =
        MergeShelvesRequest.newBuilder()
            .setName(name == null ? null : name.toString())
            .setOtherShelfName(otherShelfName == null ? null : otherShelfName.toString())
            .build();
    return mergeShelves(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Merges two shelves by adding all books from the shelf named `other_shelf_name` to shelf `name`,
   * and deletes `other_shelf_name`. Returns the updated shelf. The book ids of the moved books may
   * not be the same as the original books.
   *
   * <p>Returns NOT_FOUND if either shelf does not exist. This call is a no-op if the specified
   * shelves are the same.
   *
   * @param name The name of the shelf we're adding books to.
   * @param otherShelfName The name of the shelf we're removing books from and deleting.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Shelf mergeShelves(ShelfName name, String otherShelfName) {
    MergeShelvesRequest request =
        MergeShelvesRequest.newBuilder()
            .setName(name == null ? null : name.toString())
            .setOtherShelfName(otherShelfName)
            .build();
    return mergeShelves(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Merges two shelves by adding all books from the shelf named `other_shelf_name` to shelf `name`,
   * and deletes `other_shelf_name`. Returns the updated shelf. The book ids of the moved books may
   * not be the same as the original books.
   *
   * <p>Returns NOT_FOUND if either shelf does not exist. This call is a no-op if the specified
   * shelves are the same.
   *
   * @param name The name of the shelf we're adding books to.
   * @param otherShelfName The name of the shelf we're removing books from and deleting.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Shelf mergeShelves(String name, ShelfName otherShelfName) {
    MergeShelvesRequest request =
        MergeShelvesRequest.newBuilder()
            .setName(name)
            .setOtherShelfName(otherShelfName == null ? null : otherShelfName.toString())
            .build();
    return mergeShelves(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Merges two shelves by adding all books from the shelf named `other_shelf_name` to shelf `name`,
   * and deletes `other_shelf_name`. Returns the updated shelf. The book ids of the moved books may
   * not be the same as the original books.
   *
   * <p>Returns NOT_FOUND if either shelf does not exist. This call is a no-op if the specified
   * shelves are the same.
   *
   * @param name The name of the shelf we're adding books to.
   * @param otherShelfName The name of the shelf we're removing books from and deleting.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Shelf mergeShelves(String name, String otherShelfName) {
    MergeShelvesRequest request =
        MergeShelvesRequest.newBuilder().setName(name).setOtherShelfName(otherShelfName).build();
    return mergeShelves(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Merges two shelves by adding all books from the shelf named `other_shelf_name` to shelf `name`,
   * and deletes `other_shelf_name`. Returns the updated shelf. The book ids of the moved books may
   * not be the same as the original books.
   *
   * <p>Returns NOT_FOUND if either shelf does not exist. This call is a no-op if the specified
   * shelves are the same.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Shelf mergeShelves(MergeShelvesRequest request) {
    return mergeShelvesCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Merges two shelves by adding all books from the shelf named `other_shelf_name` to shelf `name`,
   * and deletes `other_shelf_name`. Returns the updated shelf. The book ids of the moved books may
   * not be the same as the original books.
   *
   * <p>Returns NOT_FOUND if either shelf does not exist. This call is a no-op if the specified
   * shelves are the same.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<MergeShelvesRequest, Shelf> mergeShelvesCallable() {
    return stub.mergeShelvesCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a book, and returns the new Book.
   *
   * @param name The name of the shelf in which the book is created.
   * @param book The book to create.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Book createBook(ShelfName name, Book book) {
    CreateBookRequest request =
        CreateBookRequest.newBuilder()
            .setName(name == null ? null : name.toString())
            .setBook(book)
            .build();
    return createBook(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a book, and returns the new Book.
   *
   * @param name The name of the shelf in which the book is created.
   * @param book The book to create.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Book createBook(String name, Book book) {
    CreateBookRequest request = CreateBookRequest.newBuilder().setName(name).setBook(book).build();
    return createBook(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a book, and returns the new Book.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Book createBook(CreateBookRequest request) {
    return createBookCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a book, and returns the new Book.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<CreateBookRequest, Book> createBookCallable() {
    return stub.createBookCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a book. Returns NOT_FOUND if the book does not exist.
   *
   * @param name The name of the book to retrieve.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Book getBook(BookName name) {
    GetBookRequest request =
        GetBookRequest.newBuilder().setName(name == null ? null : name.toString()).build();
    return getBook(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a book. Returns NOT_FOUND if the book does not exist.
   *
   * @param name The name of the book to retrieve.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Book getBook(String name) {
    GetBookRequest request = GetBookRequest.newBuilder().setName(name).build();
    return getBook(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a book. Returns NOT_FOUND if the book does not exist.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Book getBook(GetBookRequest request) {
    return getBookCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a book. Returns NOT_FOUND if the book does not exist.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<GetBookRequest, Book> getBookCallable() {
    return stub.getBookCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists books in a shelf. The order is unspecified but deterministic. Newly created books will
   * not necessarily be added to the end of this list. Returns NOT_FOUND if the shelf does not
   * exist.
   *
   * @param name The name of the shelf whose books we'd like to list.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListBooksPagedResponse listBooks(ShelfName name) {
    ListBooksRequest request =
        ListBooksRequest.newBuilder().setName(name == null ? null : name.toString()).build();
    return listBooks(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists books in a shelf. The order is unspecified but deterministic. Newly created books will
   * not necessarily be added to the end of this list. Returns NOT_FOUND if the shelf does not
   * exist.
   *
   * @param name The name of the shelf whose books we'd like to list.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListBooksPagedResponse listBooks(String name) {
    ListBooksRequest request = ListBooksRequest.newBuilder().setName(name).build();
    return listBooks(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists books in a shelf. The order is unspecified but deterministic. Newly created books will
   * not necessarily be added to the end of this list. Returns NOT_FOUND if the shelf does not
   * exist.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListBooksPagedResponse listBooks(ListBooksRequest request) {
    return listBooksPagedCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists books in a shelf. The order is unspecified but deterministic. Newly created books will
   * not necessarily be added to the end of this list. Returns NOT_FOUND if the shelf does not
   * exist.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<ListBooksRequest, ListBooksPagedResponse> listBooksPagedCallable() {
    return stub.listBooksPagedCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists books in a shelf. The order is unspecified but deterministic. Newly created books will
   * not necessarily be added to the end of this list. Returns NOT_FOUND if the shelf does not
   * exist.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<ListBooksRequest, ListBooksResponse> listBooksCallable() {
    return stub.listBooksCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a book. Returns NOT_FOUND if the book does not exist.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteBook(DeleteBookRequest request) {
    deleteBookCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a book. Returns NOT_FOUND if the book does not exist.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<DeleteBookRequest, Empty> deleteBookCallable() {
    return stub.deleteBookCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Updates a book. Returns INVALID_ARGUMENT if the name of the book is non-empty and does not
   * equal the existing name.
   *
   * @param book The book to update with. The name must match or be empty.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Book updateBook(Book book) {
    UpdateBookRequest request = UpdateBookRequest.newBuilder().setBook(book).build();
    return updateBook(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Updates a book. Returns INVALID_ARGUMENT if the name of the book is non-empty and does not
   * equal the existing name.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Book updateBook(UpdateBookRequest request) {
    return updateBookCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Updates a book. Returns INVALID_ARGUMENT if the name of the book is non-empty and does not
   * equal the existing name.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<UpdateBookRequest, Book> updateBookCallable() {
    return stub.updateBookCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Moves a book to another shelf, and returns the new book. The book id of the new book may not be
   * the same as the original book.
   *
   * @param name The name of the book to move.
   * @param otherShelfName The name of the destination shelf.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Book moveBook(BookName name, ShelfName otherShelfName) {
    MoveBookRequest request =
        MoveBookRequest.newBuilder()
            .setName(name == null ? null : name.toString())
            .setOtherShelfName(otherShelfName == null ? null : otherShelfName.toString())
            .build();
    return moveBook(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Moves a book to another shelf, and returns the new book. The book id of the new book may not be
   * the same as the original book.
   *
   * @param name The name of the book to move.
   * @param otherShelfName The name of the destination shelf.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Book moveBook(BookName name, String otherShelfName) {
    MoveBookRequest request =
        MoveBookRequest.newBuilder()
            .setName(name == null ? null : name.toString())
            .setOtherShelfName(otherShelfName)
            .build();
    return moveBook(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Moves a book to another shelf, and returns the new book. The book id of the new book may not be
   * the same as the original book.
   *
   * @param name The name of the book to move.
   * @param otherShelfName The name of the destination shelf.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Book moveBook(String name, ShelfName otherShelfName) {
    MoveBookRequest request =
        MoveBookRequest.newBuilder()
            .setName(name)
            .setOtherShelfName(otherShelfName == null ? null : otherShelfName.toString())
            .build();
    return moveBook(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Moves a book to another shelf, and returns the new book. The book id of the new book may not be
   * the same as the original book.
   *
   * @param name The name of the book to move.
   * @param otherShelfName The name of the destination shelf.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Book moveBook(String name, String otherShelfName) {
    MoveBookRequest request =
        MoveBookRequest.newBuilder().setName(name).setOtherShelfName(otherShelfName).build();
    return moveBook(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Moves a book to another shelf, and returns the new book. The book id of the new book may not be
   * the same as the original book.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Book moveBook(MoveBookRequest request) {
    return moveBookCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Moves a book to another shelf, and returns the new book. The book id of the new book may not be
   * the same as the original book.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<MoveBookRequest, Book> moveBookCallable() {
    return stub.moveBookCallable();
  }

  @Override
  public final void close() {
    stub.close();
  }

  @Override
  public void shutdown() {
    stub.shutdown();
  }

  @Override
  public boolean isShutdown() {
    return stub.isShutdown();
  }

  @Override
  public boolean isTerminated() {
    return stub.isTerminated();
  }

  @Override
  public void shutdownNow() {
    stub.shutdownNow();
  }

  @Override
  public boolean awaitTermination(long duration, TimeUnit unit) throws InterruptedException {
    return stub.awaitTermination(duration, unit);
  }

  public static class ListShelvesPagedResponse
      extends AbstractPagedListResponse<
          ListShelvesRequest,
          ListShelvesResponse,
          Shelf,
          ListShelvesPage,
          ListShelvesFixedSizeCollection> {

    public static ApiFuture<ListShelvesPagedResponse> createAsync(
        PageContext<ListShelvesRequest, ListShelvesResponse, Shelf> context,
        ApiFuture<ListShelvesResponse> futureResponse) {
      ApiFuture<ListShelvesPage> futurePage =
          ListShelvesPage.createEmptyPage().createPageAsync(context, futureResponse);
      return ApiFutures.transform(
          futurePage,
          new ApiFunction<ListShelvesPage, ListShelvesPagedResponse>() {
            @Override
            public ListShelvesPagedResponse apply(ListShelvesPage input) {
              return new ListShelvesPagedResponse(input);
            }
          },
          MoreExecutors.directExecutor());
    }

    private ListShelvesPagedResponse(ListShelvesPage page) {
      super(page, ListShelvesFixedSizeCollection.createEmptyCollection());
    }
  }

  public static class ListShelvesPage
      extends AbstractPage<ListShelvesRequest, ListShelvesResponse, Shelf, ListShelvesPage> {

    private ListShelvesPage(
        PageContext<ListShelvesRequest, ListShelvesResponse, Shelf> context,
        ListShelvesResponse response) {
      super(context, response);
    }

    private static ListShelvesPage createEmptyPage() {
      return new ListShelvesPage(null, null);
    }

    @Override
    protected ListShelvesPage createPage(
        PageContext<ListShelvesRequest, ListShelvesResponse, Shelf> context,
        ListShelvesResponse response) {
      return new ListShelvesPage(context, response);
    }

    @Override
    public ApiFuture<ListShelvesPage> createPageAsync(
        PageContext<ListShelvesRequest, ListShelvesResponse, Shelf> context,
        ApiFuture<ListShelvesResponse> futureResponse) {
      return super.createPageAsync(context, futureResponse);
    }
  }

  public static class ListShelvesFixedSizeCollection
      extends AbstractFixedSizeCollection<
          ListShelvesRequest,
          ListShelvesResponse,
          Shelf,
          ListShelvesPage,
          ListShelvesFixedSizeCollection> {

    private ListShelvesFixedSizeCollection(List<ListShelvesPage> pages, int collectionSize) {
      super(pages, collectionSize);
    }

    private static ListShelvesFixedSizeCollection createEmptyCollection() {
      return new ListShelvesFixedSizeCollection(null, 0);
    }

    @Override
    protected ListShelvesFixedSizeCollection createCollection(
        List<ListShelvesPage> pages, int collectionSize) {
      return new ListShelvesFixedSizeCollection(pages, collectionSize);
    }
  }

  public static class ListBooksPagedResponse
      extends AbstractPagedListResponse<
          ListBooksRequest, ListBooksResponse, Book, ListBooksPage, ListBooksFixedSizeCollection> {

    public static ApiFuture<ListBooksPagedResponse> createAsync(
        PageContext<ListBooksRequest, ListBooksResponse, Book> context,
        ApiFuture<ListBooksResponse> futureResponse) {
      ApiFuture<ListBooksPage> futurePage =
          ListBooksPage.createEmptyPage().createPageAsync(context, futureResponse);
      return ApiFutures.transform(
          futurePage,
          new ApiFunction<ListBooksPage, ListBooksPagedResponse>() {
            @Override
            public ListBooksPagedResponse apply(ListBooksPage input) {
              return new ListBooksPagedResponse(input);
            }
          },
          MoreExecutors.directExecutor());
    }

    private ListBooksPagedResponse(ListBooksPage page) {
      super(page, ListBooksFixedSizeCollection.createEmptyCollection());
    }
  }

  public static class ListBooksPage
      extends AbstractPage<ListBooksRequest, ListBooksResponse, Book, ListBooksPage> {

    private ListBooksPage(
        PageContext<ListBooksRequest, ListBooksResponse, Book> context,
        ListBooksResponse response) {
      super(context, response);
    }

    private static ListBooksPage createEmptyPage() {
      return new ListBooksPage(null, null);
    }

    @Override
    protected ListBooksPage createPage(
        PageContext<ListBooksRequest, ListBooksResponse, Book> context,
        ListBooksResponse response) {
      return new ListBooksPage(context, response);
    }

    @Override
    public ApiFuture<ListBooksPage> createPageAsync(
        PageContext<ListBooksRequest, ListBooksResponse, Book> context,
        ApiFuture<ListBooksResponse> futureResponse) {
      return super.createPageAsync(context, futureResponse);
    }
  }

  public static class ListBooksFixedSizeCollection
      extends AbstractFixedSizeCollection<
          ListBooksRequest, ListBooksResponse, Book, ListBooksPage, ListBooksFixedSizeCollection> {

    private ListBooksFixedSizeCollection(List<ListBooksPage> pages, int collectionSize) {
      super(pages, collectionSize);
    }

    private static ListBooksFixedSizeCollection createEmptyCollection() {
      return new ListBooksFixedSizeCollection(null, 0);
    }

    @Override
    protected ListBooksFixedSizeCollection createCollection(
        List<ListBooksPage> pages, int collectionSize) {
      return new ListBooksFixedSizeCollection(pages, collectionSize);
    }
  }
}
