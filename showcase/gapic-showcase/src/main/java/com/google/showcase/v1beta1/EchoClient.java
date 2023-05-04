/*
 * Copyright 2022 Google LLC
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

package com.google.showcase.v1beta1;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.api.core.BetaApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.httpjson.longrunning.OperationsClient;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.api.gax.paging.AbstractFixedSizeCollection;
import com.google.api.gax.paging.AbstractPage;
import com.google.api.gax.paging.AbstractPagedListResponse;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientStreamingCallable;
import com.google.api.gax.rpc.OperationCallable;
import com.google.api.gax.rpc.PageContext;
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.cloud.location.GetLocationRequest;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.ListLocationsResponse;
import com.google.cloud.location.Location;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.longrunning.Operation;
import com.google.showcase.v1beta1.stub.EchoStub;
import com.google.showcase.v1beta1.stub.EchoStubSettings;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * Service Description: This service is used showcase the four main types of rpcs - unary, server
 * side streaming, client side streaming, and bidirectional streaming. This service also exposes
 * methods that explicitly implement server delay, and paginated calls. Set the 'showcase-trailer'
 * metadata key on any method to have the values echoed in the response trailers. Set the
 * 'x-goog-request-params' metadata key on any method to have the values echoed in the response
 * headers.
 *
 * <p>This class provides the ability to make remote calls to the backing service through method
 * calls that map to API methods. Sample code to get started:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * try (EchoClient echoClient = EchoClient.create()) {
 *   EchoRequest request =
 *       EchoRequest.newBuilder()
 *           .setSeverity(Severity.forNumber(0))
 *           .setHeader("header-1221270899")
 *           .setOtherHeader("otherHeader-2026585667")
 *           .build();
 *   EchoResponse response = echoClient.echo(request);
 * }
 * }</pre>
 *
 * <p>Note: close() needs to be called on the EchoClient object to clean up resources such as
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
 * <p>This class can be customized by passing in a custom instance of EchoSettings to create(). For
 * example:
 *
 * <p>To customize credentials:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * EchoSettings echoSettings =
 *     EchoSettings.newBuilder()
 *         .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
 *         .build();
 * EchoClient echoClient = EchoClient.create(echoSettings);
 * }</pre>
 *
 * <p>To customize the endpoint:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * EchoSettings echoSettings = EchoSettings.newBuilder().setEndpoint(myEndpoint).build();
 * EchoClient echoClient = EchoClient.create(echoSettings);
 * }</pre>
 *
 * <p>To use REST (HTTP1.1/JSON) transport (instead of gRPC) for sending and receiving requests over
 * the wire:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * EchoSettings echoSettings = EchoSettings.newHttpJsonBuilder().build();
 * EchoClient echoClient = EchoClient.create(echoSettings);
 * }</pre>
 *
 * <p>Please refer to the GitHub repository's samples for more quickstart code snippets.
 */
@BetaApi
@Generated("by gapic-generator-java")
public class EchoClient implements BackgroundResource {
  private final EchoSettings settings;
  private final EchoStub stub;
  private final OperationsClient httpJsonOperationsClient;
  private final com.google.longrunning.OperationsClient operationsClient;

  /** Constructs an instance of EchoClient with default settings. */
  public static final EchoClient create() throws IOException {
    return create(EchoSettings.newBuilder().build());
  }

  /**
   * Constructs an instance of EchoClient, using the given settings. The channels are created based
   * on the settings passed in, or defaults for any settings that are not set.
   */
  public static final EchoClient create(EchoSettings settings) throws IOException {
    return new EchoClient(settings);
  }

  /**
   * Constructs an instance of EchoClient, using the given stub for making calls. This is for
   * advanced usage - prefer using create(EchoSettings).
   */
  public static final EchoClient create(EchoStub stub) {
    return new EchoClient(stub);
  }

  /**
   * Constructs an instance of EchoClient, using the given settings. This is protected so that it is
   * easy to make a subclass, but otherwise, the static factory methods should be preferred.
   */
  protected EchoClient(EchoSettings settings) throws IOException {
    this.settings = settings;
    this.stub = ((EchoStubSettings) settings.getStubSettings()).createStub();
    this.operationsClient =
        com.google.longrunning.OperationsClient.create(this.stub.getOperationsStub());
    this.httpJsonOperationsClient = OperationsClient.create(this.stub.getHttpJsonOperationsStub());
  }

  protected EchoClient(EchoStub stub) {
    this.settings = null;
    this.stub = stub;
    this.operationsClient =
        com.google.longrunning.OperationsClient.create(this.stub.getOperationsStub());
    this.httpJsonOperationsClient = OperationsClient.create(this.stub.getHttpJsonOperationsStub());
  }

  public final EchoSettings getSettings() {
    return settings;
  }

  public EchoStub getStub() {
    return stub;
  }

  /**
   * Returns the OperationsClient that can be used to query the status of a long-running operation
   * returned by another API method call.
   */
  public final com.google.longrunning.OperationsClient getOperationsClient() {
    return operationsClient;
  }

  /**
   * Returns the OperationsClient that can be used to query the status of a long-running operation
   * returned by another API method call.
   */
  @BetaApi
  public final OperationsClient getHttpJsonOperationsClient() {
    return httpJsonOperationsClient;
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method simply echoes the request. This method showcases unary RPCs.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   EchoRequest request =
   *       EchoRequest.newBuilder()
   *           .setSeverity(Severity.forNumber(0))
   *           .setHeader("header-1221270899")
   *           .setOtherHeader("otherHeader-2026585667")
   *           .build();
   *   EchoResponse response = echoClient.echo(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final EchoResponse echo(EchoRequest request) {
    return echoCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method simply echoes the request. This method showcases unary RPCs.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   EchoRequest request =
   *       EchoRequest.newBuilder()
   *           .setSeverity(Severity.forNumber(0))
   *           .setHeader("header-1221270899")
   *           .setOtherHeader("otherHeader-2026585667")
   *           .build();
   *   ApiFuture<EchoResponse> future = echoClient.echoCallable().futureCall(request);
   *   // Do something.
   *   EchoResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<EchoRequest, EchoResponse> echoCallable() {
    return stub.echoCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method splits the given content into words and will pass each word back through the
   * stream. This method showcases server-side streaming RPCs.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   ExpandRequest request =
   *       ExpandRequest.newBuilder()
   *           .setContent("content951530617")
   *           .setError(Status.newBuilder().build())
   *           .build();
   *   ServerStream<EchoResponse> stream = echoClient.expandCallable().call(request);
   *   for (EchoResponse response : stream) {
   *     // Do something when a response is received.
   *   }
   * }
   * }</pre>
   */
  public final ServerStreamingCallable<ExpandRequest, EchoResponse> expandCallable() {
    return stub.expandCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method will collect the words given to it. When the stream is closed by the client, this
   * method will return the a concatenation of the strings passed to it. This method showcases
   * client-side streaming RPCs.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   ApiStreamObserver<EchoRequest> responseObserver =
   *       new ApiStreamObserver<EchoRequest>() {
   *         {@literal @}Override
   *         public void onNext(EchoResponse response) {
   *           // Do something when a response is received.
   *         }
   *
   *         {@literal @}Override
   *         public void onError(Throwable t) {
   *           // Add error-handling
   *         }
   *
   *         {@literal @}Override
   *         public void onCompleted() {
   *           // Do something when complete.
   *         }
   *       };
   *   ApiStreamObserver<EchoRequest> requestObserver =
   *       echoClient.collect().clientStreamingCall(responseObserver);
   *   EchoRequest request =
   *       EchoRequest.newBuilder()
   *           .setSeverity(Severity.forNumber(0))
   *           .setHeader("header-1221270899")
   *           .setOtherHeader("otherHeader-2026585667")
   *           .build();
   *   requestObserver.onNext(request);
   * }
   * }</pre>
   */
  public final ClientStreamingCallable<EchoRequest, EchoResponse> collectCallable() {
    return stub.collectCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method, upon receiving a request on the stream, will pass the same content back on the
   * stream. This method showcases bidirectional streaming RPCs.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   BidiStream<EchoRequest, EchoResponse> bidiStream = echoClient.chatCallable().call();
   *   EchoRequest request =
   *       EchoRequest.newBuilder()
   *           .setSeverity(Severity.forNumber(0))
   *           .setHeader("header-1221270899")
   *           .setOtherHeader("otherHeader-2026585667")
   *           .build();
   *   bidiStream.send(request);
   *   for (EchoResponse response : bidiStream) {
   *     // Do something when a response is received.
   *   }
   * }
   * }</pre>
   */
  public final BidiStreamingCallable<EchoRequest, EchoResponse> chatCallable() {
    return stub.chatCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This is similar to the Expand method but instead of returning a stream of expanded words, this
   * method returns a paged list of expanded words.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   PagedExpandRequest request =
   *       PagedExpandRequest.newBuilder()
   *           .setContent("content951530617")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   for (EchoResponse element : echoClient.pagedExpand(request).iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final PagedExpandPagedResponse pagedExpand(PagedExpandRequest request) {
    return pagedExpandPagedCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This is similar to the Expand method but instead of returning a stream of expanded words, this
   * method returns a paged list of expanded words.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   PagedExpandRequest request =
   *       PagedExpandRequest.newBuilder()
   *           .setContent("content951530617")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   ApiFuture<EchoResponse> future = echoClient.pagedExpandPagedCallable().futureCall(request);
   *   // Do something.
   *   for (EchoResponse element : future.get().iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   */
  public final UnaryCallable<PagedExpandRequest, PagedExpandPagedResponse>
      pagedExpandPagedCallable() {
    return stub.pagedExpandPagedCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This is similar to the Expand method but instead of returning a stream of expanded words, this
   * method returns a paged list of expanded words.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   PagedExpandRequest request =
   *       PagedExpandRequest.newBuilder()
   *           .setContent("content951530617")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   while (true) {
   *     PagedExpandResponse response = echoClient.pagedExpandCallable().call(request);
   *     for (EchoResponse element : response.getResponsesList()) {
   *       // doThingsWith(element);
   *     }
   *     String nextPageToken = response.getNextPageToken();
   *     if (!Strings.isNullOrEmpty(nextPageToken)) {
   *       request = request.toBuilder().setPageToken(nextPageToken).build();
   *     } else {
   *       break;
   *     }
   *   }
   * }
   * }</pre>
   */
  public final UnaryCallable<PagedExpandRequest, PagedExpandResponse> pagedExpandCallable() {
    return stub.pagedExpandCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This is similar to the PagedExpand except that it uses max_results instead of page_size, as
   * some legacy APIs still do. New APIs should NOT use this pattern.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   PagedExpandLegacyRequest request =
   *       PagedExpandLegacyRequest.newBuilder()
   *           .setContent("content951530617")
   *           .setMaxResults(1128457243)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   PagedExpandResponse response = echoClient.pagedExpandLegacy(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final PagedExpandResponse pagedExpandLegacy(PagedExpandLegacyRequest request) {
    return pagedExpandLegacyCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This is similar to the PagedExpand except that it uses max_results instead of page_size, as
   * some legacy APIs still do. New APIs should NOT use this pattern.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   PagedExpandLegacyRequest request =
   *       PagedExpandLegacyRequest.newBuilder()
   *           .setContent("content951530617")
   *           .setMaxResults(1128457243)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   ApiFuture<PagedExpandResponse> future =
   *       echoClient.pagedExpandLegacyCallable().futureCall(request);
   *   // Do something.
   *   PagedExpandResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<PagedExpandLegacyRequest, PagedExpandResponse>
      pagedExpandLegacyCallable() {
    return stub.pagedExpandLegacyCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method returns a map containing lists of words that appear in the input, keyed by their
   * initial character. The only words returned are the ones included in the current page, as
   * determined by page_token and page_size, which both refer to the word indices in the input. This
   * paging result consisting of a map of lists is a pattern used by some legacy APIs. New APIs
   * should NOT use this pattern.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   PagedExpandRequest request =
   *       PagedExpandRequest.newBuilder()
   *           .setContent("content951530617")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   for (Map.Entry<String, PagedExpandResponseList> element :
   *       echoClient.pagedExpandLegacyMapped(request).iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final PagedExpandLegacyMappedPagedResponse pagedExpandLegacyMapped(
      PagedExpandRequest request) {
    return pagedExpandLegacyMappedPagedCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method returns a map containing lists of words that appear in the input, keyed by their
   * initial character. The only words returned are the ones included in the current page, as
   * determined by page_token and page_size, which both refer to the word indices in the input. This
   * paging result consisting of a map of lists is a pattern used by some legacy APIs. New APIs
   * should NOT use this pattern.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   PagedExpandRequest request =
   *       PagedExpandRequest.newBuilder()
   *           .setContent("content951530617")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   ApiFuture<Map.Entry<String, PagedExpandResponseList>> future =
   *       echoClient.pagedExpandLegacyMappedPagedCallable().futureCall(request);
   *   // Do something.
   *   for (Map.Entry<String, PagedExpandResponseList> element : future.get().iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   */
  public final UnaryCallable<PagedExpandRequest, PagedExpandLegacyMappedPagedResponse>
      pagedExpandLegacyMappedPagedCallable() {
    return stub.pagedExpandLegacyMappedPagedCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method returns a map containing lists of words that appear in the input, keyed by their
   * initial character. The only words returned are the ones included in the current page, as
   * determined by page_token and page_size, which both refer to the word indices in the input. This
   * paging result consisting of a map of lists is a pattern used by some legacy APIs. New APIs
   * should NOT use this pattern.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   PagedExpandRequest request =
   *       PagedExpandRequest.newBuilder()
   *           .setContent("content951530617")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   while (true) {
   *     PagedExpandLegacyMappedResponse response =
   *         echoClient.pagedExpandLegacyMappedCallable().call(request);
   *     for (Map.Entry<String, PagedExpandResponseList> element : response.getAlphabetizedList()) {
   *       // doThingsWith(element);
   *     }
   *     String nextPageToken = response.getNextPageToken();
   *     if (!Strings.isNullOrEmpty(nextPageToken)) {
   *       request = request.toBuilder().setPageToken(nextPageToken).build();
   *     } else {
   *       break;
   *     }
   *   }
   * }
   * }</pre>
   */
  public final UnaryCallable<PagedExpandRequest, PagedExpandLegacyMappedResponse>
      pagedExpandLegacyMappedCallable() {
    return stub.pagedExpandLegacyMappedCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method will wait for the requested amount of time and then return. This method showcases
   * how a client handles a request timeout.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   WaitRequest request = WaitRequest.newBuilder().build();
   *   WaitResponse response = echoClient.waitAsync(request).get();
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final OperationFuture<WaitResponse, WaitMetadata> waitAsync(WaitRequest request) {
    return waitOperationCallable().futureCall(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method will wait for the requested amount of time and then return. This method showcases
   * how a client handles a request timeout.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   WaitRequest request = WaitRequest.newBuilder().build();
   *   OperationFuture<WaitResponse, WaitMetadata> future =
   *       echoClient.waitOperationCallable().futureCall(request);
   *   // Do something.
   *   WaitResponse response = future.get();
   * }
   * }</pre>
   */
  public final OperationCallable<WaitRequest, WaitResponse, WaitMetadata> waitOperationCallable() {
    return stub.waitOperationCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method will wait for the requested amount of time and then return. This method showcases
   * how a client handles a request timeout.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   WaitRequest request = WaitRequest.newBuilder().build();
   *   ApiFuture<Operation> future = echoClient.waitCallable().futureCall(request);
   *   // Do something.
   *   Operation response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<WaitRequest, Operation> waitCallable() {
    return stub.waitCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method will block (wait) for the requested amount of time and then return the response or
   * error. This method showcases how a client handles delays or retries.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   BlockRequest request =
   *       BlockRequest.newBuilder().setResponseDelay(Duration.newBuilder().build()).build();
   *   BlockResponse response = echoClient.block(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final BlockResponse block(BlockRequest request) {
    return blockCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * This method will block (wait) for the requested amount of time and then return the response or
   * error. This method showcases how a client handles delays or retries.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   BlockRequest request =
   *       BlockRequest.newBuilder().setResponseDelay(Duration.newBuilder().build()).build();
   *   ApiFuture<BlockResponse> future = echoClient.blockCallable().futureCall(request);
   *   // Do something.
   *   BlockResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<BlockRequest, BlockResponse> blockCallable() {
    return stub.blockCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists information about the supported locations for this service.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   ListLocationsRequest request =
   *       ListLocationsRequest.newBuilder()
   *           .setName("name3373707")
   *           .setFilter("filter-1274492040")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   for (Location element : echoClient.listLocations(request).iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListLocationsPagedResponse listLocations(ListLocationsRequest request) {
    return listLocationsPagedCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists information about the supported locations for this service.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   ListLocationsRequest request =
   *       ListLocationsRequest.newBuilder()
   *           .setName("name3373707")
   *           .setFilter("filter-1274492040")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   ApiFuture<Location> future = echoClient.listLocationsPagedCallable().futureCall(request);
   *   // Do something.
   *   for (Location element : future.get().iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   */
  public final UnaryCallable<ListLocationsRequest, ListLocationsPagedResponse>
      listLocationsPagedCallable() {
    return stub.listLocationsPagedCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists information about the supported locations for this service.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   ListLocationsRequest request =
   *       ListLocationsRequest.newBuilder()
   *           .setName("name3373707")
   *           .setFilter("filter-1274492040")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   while (true) {
   *     ListLocationsResponse response = echoClient.listLocationsCallable().call(request);
   *     for (Location element : response.getLocationsList()) {
   *       // doThingsWith(element);
   *     }
   *     String nextPageToken = response.getNextPageToken();
   *     if (!Strings.isNullOrEmpty(nextPageToken)) {
   *       request = request.toBuilder().setPageToken(nextPageToken).build();
   *     } else {
   *       break;
   *     }
   *   }
   * }
   * }</pre>
   */
  public final UnaryCallable<ListLocationsRequest, ListLocationsResponse> listLocationsCallable() {
    return stub.listLocationsCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets information about a location.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   GetLocationRequest request = GetLocationRequest.newBuilder().setName("name3373707").build();
   *   Location response = echoClient.getLocation(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Location getLocation(GetLocationRequest request) {
    return getLocationCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets information about a location.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (EchoClient echoClient = EchoClient.create()) {
   *   GetLocationRequest request = GetLocationRequest.newBuilder().setName("name3373707").build();
   *   ApiFuture<Location> future = echoClient.getLocationCallable().futureCall(request);
   *   // Do something.
   *   Location response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<GetLocationRequest, Location> getLocationCallable() {
    return stub.getLocationCallable();
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

  public static class PagedExpandPagedResponse
      extends AbstractPagedListResponse<
          PagedExpandRequest,
          PagedExpandResponse,
          EchoResponse,
          PagedExpandPage,
          PagedExpandFixedSizeCollection> {

    public static ApiFuture<PagedExpandPagedResponse> createAsync(
        PageContext<PagedExpandRequest, PagedExpandResponse, EchoResponse> context,
        ApiFuture<PagedExpandResponse> futureResponse) {
      ApiFuture<PagedExpandPage> futurePage =
          PagedExpandPage.createEmptyPage().createPageAsync(context, futureResponse);
      return ApiFutures.transform(
          futurePage, input -> new PagedExpandPagedResponse(input), MoreExecutors.directExecutor());
    }

    private PagedExpandPagedResponse(PagedExpandPage page) {
      super(page, PagedExpandFixedSizeCollection.createEmptyCollection());
    }
  }

  public static class PagedExpandPage
      extends AbstractPage<PagedExpandRequest, PagedExpandResponse, EchoResponse, PagedExpandPage> {

    private PagedExpandPage(
        PageContext<PagedExpandRequest, PagedExpandResponse, EchoResponse> context,
        PagedExpandResponse response) {
      super(context, response);
    }

    private static PagedExpandPage createEmptyPage() {
      return new PagedExpandPage(null, null);
    }

    @Override
    protected PagedExpandPage createPage(
        PageContext<PagedExpandRequest, PagedExpandResponse, EchoResponse> context,
        PagedExpandResponse response) {
      return new PagedExpandPage(context, response);
    }

    @Override
    public ApiFuture<PagedExpandPage> createPageAsync(
        PageContext<PagedExpandRequest, PagedExpandResponse, EchoResponse> context,
        ApiFuture<PagedExpandResponse> futureResponse) {
      return super.createPageAsync(context, futureResponse);
    }
  }

  public static class PagedExpandFixedSizeCollection
      extends AbstractFixedSizeCollection<
          PagedExpandRequest,
          PagedExpandResponse,
          EchoResponse,
          PagedExpandPage,
          PagedExpandFixedSizeCollection> {

    private PagedExpandFixedSizeCollection(List<PagedExpandPage> pages, int collectionSize) {
      super(pages, collectionSize);
    }

    private static PagedExpandFixedSizeCollection createEmptyCollection() {
      return new PagedExpandFixedSizeCollection(null, 0);
    }

    @Override
    protected PagedExpandFixedSizeCollection createCollection(
        List<PagedExpandPage> pages, int collectionSize) {
      return new PagedExpandFixedSizeCollection(pages, collectionSize);
    }
  }

  public static class PagedExpandLegacyMappedPagedResponse
      extends AbstractPagedListResponse<
          PagedExpandRequest,
          PagedExpandLegacyMappedResponse,
          Map.Entry<String, PagedExpandResponseList>,
          PagedExpandLegacyMappedPage,
          PagedExpandLegacyMappedFixedSizeCollection> {

    public static ApiFuture<PagedExpandLegacyMappedPagedResponse> createAsync(
        PageContext<
                PagedExpandRequest,
                PagedExpandLegacyMappedResponse,
                Map.Entry<String, PagedExpandResponseList>>
            context,
        ApiFuture<PagedExpandLegacyMappedResponse> futureResponse) {
      ApiFuture<PagedExpandLegacyMappedPage> futurePage =
          PagedExpandLegacyMappedPage.createEmptyPage().createPageAsync(context, futureResponse);
      return ApiFutures.transform(
          futurePage,
          input -> new PagedExpandLegacyMappedPagedResponse(input),
          MoreExecutors.directExecutor());
    }

    private PagedExpandLegacyMappedPagedResponse(PagedExpandLegacyMappedPage page) {
      super(page, PagedExpandLegacyMappedFixedSizeCollection.createEmptyCollection());
    }
  }

  public static class PagedExpandLegacyMappedPage
      extends AbstractPage<
          PagedExpandRequest,
          PagedExpandLegacyMappedResponse,
          Map.Entry<String, PagedExpandResponseList>,
          PagedExpandLegacyMappedPage> {

    private PagedExpandLegacyMappedPage(
        PageContext<
                PagedExpandRequest,
                PagedExpandLegacyMappedResponse,
                Map.Entry<String, PagedExpandResponseList>>
            context,
        PagedExpandLegacyMappedResponse response) {
      super(context, response);
    }

    private static PagedExpandLegacyMappedPage createEmptyPage() {
      return new PagedExpandLegacyMappedPage(null, null);
    }

    @Override
    protected PagedExpandLegacyMappedPage createPage(
        PageContext<
                PagedExpandRequest,
                PagedExpandLegacyMappedResponse,
                Map.Entry<String, PagedExpandResponseList>>
            context,
        PagedExpandLegacyMappedResponse response) {
      return new PagedExpandLegacyMappedPage(context, response);
    }

    @Override
    public ApiFuture<PagedExpandLegacyMappedPage> createPageAsync(
        PageContext<
                PagedExpandRequest,
                PagedExpandLegacyMappedResponse,
                Map.Entry<String, PagedExpandResponseList>>
            context,
        ApiFuture<PagedExpandLegacyMappedResponse> futureResponse) {
      return super.createPageAsync(context, futureResponse);
    }
  }

  public static class PagedExpandLegacyMappedFixedSizeCollection
      extends AbstractFixedSizeCollection<
          PagedExpandRequest,
          PagedExpandLegacyMappedResponse,
          Map.Entry<String, PagedExpandResponseList>,
          PagedExpandLegacyMappedPage,
          PagedExpandLegacyMappedFixedSizeCollection> {

    private PagedExpandLegacyMappedFixedSizeCollection(
        List<PagedExpandLegacyMappedPage> pages, int collectionSize) {
      super(pages, collectionSize);
    }

    private static PagedExpandLegacyMappedFixedSizeCollection createEmptyCollection() {
      return new PagedExpandLegacyMappedFixedSizeCollection(null, 0);
    }

    @Override
    protected PagedExpandLegacyMappedFixedSizeCollection createCollection(
        List<PagedExpandLegacyMappedPage> pages, int collectionSize) {
      return new PagedExpandLegacyMappedFixedSizeCollection(pages, collectionSize);
    }
  }

  public static class ListLocationsPagedResponse
      extends AbstractPagedListResponse<
          ListLocationsRequest,
          ListLocationsResponse,
          Location,
          ListLocationsPage,
          ListLocationsFixedSizeCollection> {

    public static ApiFuture<ListLocationsPagedResponse> createAsync(
        PageContext<ListLocationsRequest, ListLocationsResponse, Location> context,
        ApiFuture<ListLocationsResponse> futureResponse) {
      ApiFuture<ListLocationsPage> futurePage =
          ListLocationsPage.createEmptyPage().createPageAsync(context, futureResponse);
      return ApiFutures.transform(
          futurePage,
          input -> new ListLocationsPagedResponse(input),
          MoreExecutors.directExecutor());
    }

    private ListLocationsPagedResponse(ListLocationsPage page) {
      super(page, ListLocationsFixedSizeCollection.createEmptyCollection());
    }
  }

  public static class ListLocationsPage
      extends AbstractPage<
          ListLocationsRequest, ListLocationsResponse, Location, ListLocationsPage> {

    private ListLocationsPage(
        PageContext<ListLocationsRequest, ListLocationsResponse, Location> context,
        ListLocationsResponse response) {
      super(context, response);
    }

    private static ListLocationsPage createEmptyPage() {
      return new ListLocationsPage(null, null);
    }

    @Override
    protected ListLocationsPage createPage(
        PageContext<ListLocationsRequest, ListLocationsResponse, Location> context,
        ListLocationsResponse response) {
      return new ListLocationsPage(context, response);
    }

    @Override
    public ApiFuture<ListLocationsPage> createPageAsync(
        PageContext<ListLocationsRequest, ListLocationsResponse, Location> context,
        ApiFuture<ListLocationsResponse> futureResponse) {
      return super.createPageAsync(context, futureResponse);
    }
  }

  public static class ListLocationsFixedSizeCollection
      extends AbstractFixedSizeCollection<
          ListLocationsRequest,
          ListLocationsResponse,
          Location,
          ListLocationsPage,
          ListLocationsFixedSizeCollection> {

    private ListLocationsFixedSizeCollection(List<ListLocationsPage> pages, int collectionSize) {
      super(pages, collectionSize);
    }

    private static ListLocationsFixedSizeCollection createEmptyCollection() {
      return new ListLocationsFixedSizeCollection(null, 0);
    }

    @Override
    protected ListLocationsFixedSizeCollection createCollection(
        List<ListLocationsPage> pages, int collectionSize) {
      return new ListLocationsFixedSizeCollection(pages, collectionSize);
    }
  }
}
