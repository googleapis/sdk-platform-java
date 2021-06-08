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

package com.google.cloud.compute.v1;

import com.google.api.core.BetaApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.cloud.compute.v1.stub.AddressesStub;
import com.google.cloud.compute.v1.stub.AddressesStubSettings;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * Service Description: Services
 *
 * <p>The Addresses API.
 *
 * <p>This class provides the ability to make remote calls to the backing service through method
 * calls that map to API methods. Sample code to get started:
 *
 * <pre>{@code
 * try (AddressesClient addressesClient = AddressesClient.create()) {
 *   String project = "project-309310695";
 *   AddressAggregatedList response = addressesClient.aggregatedList(project);
 * }
 * }</pre>
 *
 * <p>Note: close() needs to be called on the AddressesClient object to clean up resources such as
 * threads. In the example above, try-with-resources is used, which automatically calls close().
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
 * <p>This class can be customized by passing in a custom instance of AddressesSettings to create().
 * For example:
 *
 * <p>To customize credentials:
 *
 * <pre>{@code
 * AddressesSettings addressesSettings =
 *     AddressesSettings.newBuilder()
 *         .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
 *         .build();
 * AddressesClient addressesClient = AddressesClient.create(addressesSettings);
 * }</pre>
 *
 * <p>To customize the endpoint:
 *
 * <pre>{@code
 * AddressesSettings addressesSettings =
 *     AddressesSettings.newBuilder().setEndpoint(myEndpoint).build();
 * AddressesClient addressesClient = AddressesClient.create(addressesSettings);
 * }</pre>
 *
 * <p>Please refer to the GitHub repository's samples for more quickstart code snippets.
 */
@Generated("by gapic-generator-java")
public class AddressesClient implements BackgroundResource {
  private final AddressesSettings settings;
  private final AddressesStub stub;

  /** Constructs an instance of AddressesClient with default settings. */
  public static final AddressesClient create() throws IOException {
    return create(AddressesSettings.newBuilder().build());
  }

  /**
   * Constructs an instance of AddressesClient, using the given settings. The channels are created
   * based on the settings passed in, or defaults for any settings that are not set.
   */
  public static final AddressesClient create(AddressesSettings settings) throws IOException {
    return new AddressesClient(settings);
  }

  /**
   * Constructs an instance of AddressesClient, using the given stub for making calls. This is for
   * advanced usage - prefer using create(AddressesSettings).
   */
  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  public static final AddressesClient create(AddressesStub stub) {
    return new AddressesClient(stub);
  }

  /**
   * Constructs an instance of AddressesClient, using the given settings. This is protected so that
   * it is easy to make a subclass, but otherwise, the static factory methods should be preferred.
   */
  protected AddressesClient(AddressesSettings settings) throws IOException {
    this.settings = settings;
    this.stub = ((AddressesStubSettings) settings.getStubSettings()).createStub();
  }

  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  protected AddressesClient(AddressesStub stub) {
    this.settings = null;
    this.stub = stub;
  }

  public final AddressesSettings getSettings() {
    return settings;
  }

  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  public AddressesStub getStub() {
    return stub;
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Retrieves an aggregated list of addresses.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (AddressesClient addressesClient = AddressesClient.create()) {
   *   String project = "project-309310695";
   *   AddressAggregatedList response = addressesClient.aggregatedList(project);
   * }
   * }</pre>
   *
   * @param project Project ID for this request.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final AddressAggregatedList aggregatedList(String project) {
    AggregatedListAddressesRequest request =
        AggregatedListAddressesRequest.newBuilder().setProject(project).build();
    return aggregatedList(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Retrieves an aggregated list of addresses.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (AddressesClient addressesClient = AddressesClient.create()) {
   *   AggregatedListAddressesRequest request =
   *       AggregatedListAddressesRequest.newBuilder()
   *           .setFilter("filter-1274492040")
   *           .setIncludeAllScopes(true)
   *           .setMaxResults(1128457243)
   *           .setOrderBy("orderBy-1207110587")
   *           .setPageToken("pageToken873572522")
   *           .setProject("project-309310695")
   *           .build();
   *   AddressAggregatedList response = addressesClient.aggregatedList(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final AddressAggregatedList aggregatedList(AggregatedListAddressesRequest request) {
    return aggregatedListCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Retrieves an aggregated list of addresses.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (AddressesClient addressesClient = AddressesClient.create()) {
   *   AggregatedListAddressesRequest request =
   *       AggregatedListAddressesRequest.newBuilder()
   *           .setFilter("filter-1274492040")
   *           .setIncludeAllScopes(true)
   *           .setMaxResults(1128457243)
   *           .setOrderBy("orderBy-1207110587")
   *           .setPageToken("pageToken873572522")
   *           .setProject("project-309310695")
   *           .build();
   *   ApiFuture<AddressAggregatedList> future =
   *       addressesClient.aggregatedListCallable().futureCall(request);
   *   // Do something.
   *   AddressAggregatedList response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<AggregatedListAddressesRequest, AddressAggregatedList>
      aggregatedListCallable() {
    return stub.aggregatedListCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes the specified address resource.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (AddressesClient addressesClient = AddressesClient.create()) {
   *   String project = "project-309310695";
   *   String region = "region-934795532";
   *   String address = "address-1147692044";
   *   Operation response = addressesClient.delete(project, region, address);
   * }
   * }</pre>
   *
   * @param project Project ID for this request.
   * @param region Name of the region for this request.
   * @param address Name of the address resource to delete.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Operation delete(String project, String region, String address) {
    DeleteAddressRequest request =
        DeleteAddressRequest.newBuilder()
            .setProject(project)
            .setRegion(region)
            .setAddress(address)
            .build();
    return delete(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes the specified address resource.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (AddressesClient addressesClient = AddressesClient.create()) {
   *   DeleteAddressRequest request =
   *       DeleteAddressRequest.newBuilder()
   *           .setAddress("address-1147692044")
   *           .setProject("project-309310695")
   *           .setRegion("region-934795532")
   *           .setRequestId("requestId693933066")
   *           .build();
   *   Operation response = addressesClient.delete(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Operation delete(DeleteAddressRequest request) {
    return deleteCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes the specified address resource.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (AddressesClient addressesClient = AddressesClient.create()) {
   *   DeleteAddressRequest request =
   *       DeleteAddressRequest.newBuilder()
   *           .setAddress("address-1147692044")
   *           .setProject("project-309310695")
   *           .setRegion("region-934795532")
   *           .setRequestId("requestId693933066")
   *           .build();
   *   ApiFuture<Operation> future = addressesClient.deleteCallable().futureCall(request);
   *   // Do something.
   *   Operation response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<DeleteAddressRequest, Operation> deleteCallable() {
    return stub.deleteCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates an address resource in the specified project by using the data included in the request.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (AddressesClient addressesClient = AddressesClient.create()) {
   *   String project = "project-309310695";
   *   String region = "region-934795532";
   *   Address addressResource = Address.newBuilder().build();
   *   Operation response = addressesClient.insert(project, region, addressResource);
   * }
   * }</pre>
   *
   * @param project Project ID for this request.
   * @param region Name of the region for this request.
   * @param addressResource The body resource for this request
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Operation insert(String project, String region, Address addressResource) {
    InsertAddressRequest request =
        InsertAddressRequest.newBuilder()
            .setProject(project)
            .setRegion(region)
            .setAddressResource(addressResource)
            .build();
    return insert(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates an address resource in the specified project by using the data included in the request.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (AddressesClient addressesClient = AddressesClient.create()) {
   *   InsertAddressRequest request =
   *       InsertAddressRequest.newBuilder()
   *           .setAddressResource(Address.newBuilder().build())
   *           .setProject("project-309310695")
   *           .setRegion("region-934795532")
   *           .setRequestId("requestId693933066")
   *           .build();
   *   Operation response = addressesClient.insert(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Operation insert(InsertAddressRequest request) {
    return insertCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates an address resource in the specified project by using the data included in the request.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (AddressesClient addressesClient = AddressesClient.create()) {
   *   InsertAddressRequest request =
   *       InsertAddressRequest.newBuilder()
   *           .setAddressResource(Address.newBuilder().build())
   *           .setProject("project-309310695")
   *           .setRegion("region-934795532")
   *           .setRequestId("requestId693933066")
   *           .build();
   *   ApiFuture<Operation> future = addressesClient.insertCallable().futureCall(request);
   *   // Do something.
   *   Operation response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<InsertAddressRequest, Operation> insertCallable() {
    return stub.insertCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Retrieves a list of addresses contained within the specified region.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (AddressesClient addressesClient = AddressesClient.create()) {
   *   String project = "project-309310695";
   *   String region = "region-934795532";
   *   String orderBy = "orderBy-1207110587";
   *   AddressList response = addressesClient.list(project, region, orderBy);
   * }
   * }</pre>
   *
   * @param project Project ID for this request.
   * @param region Name of the region for this request.
   * @param orderBy Sorts list results by a certain order. By default, results are returned in
   *     alphanumerical order based on the resource name.
   *     <p>You can also sort results in descending order based on the creation timestamp using
   *     orderBy="creationTimestamp desc". This sorts results based on the creationTimestamp field
   *     in reverse chronological order (newest result first). Use this to sort resources like
   *     operations so that the newest operation is returned first.
   *     <p>Currently, only sorting by name or creationTimestamp desc is supported.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final AddressList list(String project, String region, String orderBy) {
    ListAddressesRequest request =
        ListAddressesRequest.newBuilder()
            .setProject(project)
            .setRegion(region)
            .setOrderBy(orderBy)
            .build();
    return list(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Retrieves a list of addresses contained within the specified region.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (AddressesClient addressesClient = AddressesClient.create()) {
   *   ListAddressesRequest request =
   *       ListAddressesRequest.newBuilder()
   *           .setFilter("filter-1274492040")
   *           .setMaxResults(1128457243)
   *           .setOrderBy("orderBy-1207110587")
   *           .setPageToken("pageToken873572522")
   *           .setProject("project-309310695")
   *           .setRegion("region-934795532")
   *           .build();
   *   AddressList response = addressesClient.list(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final AddressList list(ListAddressesRequest request) {
    return listCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Retrieves a list of addresses contained within the specified region.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (AddressesClient addressesClient = AddressesClient.create()) {
   *   ListAddressesRequest request =
   *       ListAddressesRequest.newBuilder()
   *           .setFilter("filter-1274492040")
   *           .setMaxResults(1128457243)
   *           .setOrderBy("orderBy-1207110587")
   *           .setPageToken("pageToken873572522")
   *           .setProject("project-309310695")
   *           .setRegion("region-934795532")
   *           .build();
   *   ApiFuture<AddressList> future = addressesClient.listCallable().futureCall(request);
   *   // Do something.
   *   AddressList response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<ListAddressesRequest, AddressList> listCallable() {
    return stub.listCallable();
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
}
