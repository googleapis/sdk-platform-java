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
import com.google.api.gax.paging.AbstractFixedSizeCollection;
import com.google.api.gax.paging.AbstractPage;
import com.google.api.gax.paging.AbstractPagedListResponse;
import com.google.api.gax.rpc.PageContext;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.Empty;
import com.google.showcase.v1beta1.stub.TestingStub;
import com.google.showcase.v1beta1.stub.TestingStubSettings;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * Service Description: A service to facilitate running discrete sets of tests against Showcase.
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
 * try (TestingClient testingClient = TestingClient.create()) {
 *   CreateSessionRequest request =
 *       CreateSessionRequest.newBuilder().setSession(Session.newBuilder().build()).build();
 *   Session response = testingClient.createSession(request);
 * }
 * }</pre>
 *
 * <p>Note: close() needs to be called on the TestingClient object to clean up resources such as
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
 * <p>This class can be customized by passing in a custom instance of TestingSettings to create().
 * For example:
 *
 * <p>To customize credentials:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * TestingSettings testingSettings =
 *     TestingSettings.newBuilder()
 *         .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
 *         .build();
 * TestingClient testingClient = TestingClient.create(testingSettings);
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
 * TestingSettings testingSettings = TestingSettings.newBuilder().setEndpoint(myEndpoint).build();
 * TestingClient testingClient = TestingClient.create(testingSettings);
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
 * TestingSettings testingSettings = TestingSettings.newHttpJsonBuilder().build();
 * TestingClient testingClient = TestingClient.create(testingSettings);
 * }</pre>
 *
 * <p>Please refer to the GitHub repository's samples for more quickstart code snippets.
 */
@BetaApi
@Generated("by gapic-generator-java")
public class TestingClient implements BackgroundResource {
  private final TestingSettings settings;
  private final TestingStub stub;

  /** Constructs an instance of TestingClient with default settings. */
  public static final TestingClient create() throws IOException {
    return create(TestingSettings.newBuilder().build());
  }

  /**
   * Constructs an instance of TestingClient, using the given settings. The channels are created
   * based on the settings passed in, or defaults for any settings that are not set.
   */
  public static final TestingClient create(TestingSettings settings) throws IOException {
    return new TestingClient(settings);
  }

  /**
   * Constructs an instance of TestingClient, using the given stub for making calls. This is for
   * advanced usage - prefer using create(TestingSettings).
   */
  public static final TestingClient create(TestingStub stub) {
    return new TestingClient(stub);
  }

  /**
   * Constructs an instance of TestingClient, using the given settings. This is protected so that it
   * is easy to make a subclass, but otherwise, the static factory methods should be preferred.
   */
  protected TestingClient(TestingSettings settings) throws IOException {
    this.settings = settings;
    this.stub = ((TestingStubSettings) settings.getStubSettings()).createStub();
  }

  protected TestingClient(TestingStub stub) {
    this.settings = null;
    this.stub = stub;
  }

  public final TestingSettings getSettings() {
    return settings;
  }

  public TestingStub getStub() {
    return stub;
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a new testing session.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   CreateSessionRequest request =
   *       CreateSessionRequest.newBuilder().setSession(Session.newBuilder().build()).build();
   *   Session response = testingClient.createSession(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Session createSession(CreateSessionRequest request) {
    return createSessionCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a new testing session.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   CreateSessionRequest request =
   *       CreateSessionRequest.newBuilder().setSession(Session.newBuilder().build()).build();
   *   ApiFuture<Session> future = testingClient.createSessionCallable().futureCall(request);
   *   // Do something.
   *   Session response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<CreateSessionRequest, Session> createSessionCallable() {
    return stub.createSessionCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a testing session.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   GetSessionRequest request =
   *       GetSessionRequest.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();
   *   Session response = testingClient.getSession(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Session getSession(GetSessionRequest request) {
    return getSessionCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a testing session.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   GetSessionRequest request =
   *       GetSessionRequest.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();
   *   ApiFuture<Session> future = testingClient.getSessionCallable().futureCall(request);
   *   // Do something.
   *   Session response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<GetSessionRequest, Session> getSessionCallable() {
    return stub.getSessionCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists the current test sessions.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   ListSessionsRequest request =
   *       ListSessionsRequest.newBuilder()
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   for (Session element : testingClient.listSessions(request).iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListSessionsPagedResponse listSessions(ListSessionsRequest request) {
    return listSessionsPagedCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists the current test sessions.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   ListSessionsRequest request =
   *       ListSessionsRequest.newBuilder()
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   ApiFuture<Session> future = testingClient.listSessionsPagedCallable().futureCall(request);
   *   // Do something.
   *   for (Session element : future.get().iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   */
  public final UnaryCallable<ListSessionsRequest, ListSessionsPagedResponse>
      listSessionsPagedCallable() {
    return stub.listSessionsPagedCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists the current test sessions.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   ListSessionsRequest request =
   *       ListSessionsRequest.newBuilder()
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   while (true) {
   *     ListSessionsResponse response = testingClient.listSessionsCallable().call(request);
   *     for (Session element : response.getSessionsList()) {
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
  public final UnaryCallable<ListSessionsRequest, ListSessionsResponse> listSessionsCallable() {
    return stub.listSessionsCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Delete a test session.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   DeleteSessionRequest request =
   *       DeleteSessionRequest.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();
   *   testingClient.deleteSession(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteSession(DeleteSessionRequest request) {
    deleteSessionCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Delete a test session.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   DeleteSessionRequest request =
   *       DeleteSessionRequest.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();
   *   ApiFuture<Empty> future = testingClient.deleteSessionCallable().futureCall(request);
   *   // Do something.
   *   future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<DeleteSessionRequest, Empty> deleteSessionCallable() {
    return stub.deleteSessionCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Report on the status of a session. This generates a report detailing which tests have been
   * completed, and an overall rollup.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   ReportSessionRequest request =
   *       ReportSessionRequest.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();
   *   ReportSessionResponse response = testingClient.reportSession(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ReportSessionResponse reportSession(ReportSessionRequest request) {
    return reportSessionCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Report on the status of a session. This generates a report detailing which tests have been
   * completed, and an overall rollup.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   ReportSessionRequest request =
   *       ReportSessionRequest.newBuilder().setName(SessionName.of("[SESSION]").toString()).build();
   *   ApiFuture<ReportSessionResponse> future =
   *       testingClient.reportSessionCallable().futureCall(request);
   *   // Do something.
   *   ReportSessionResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<ReportSessionRequest, ReportSessionResponse> reportSessionCallable() {
    return stub.reportSessionCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * List the tests of a sessesion.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   ListTestsRequest request =
   *       ListTestsRequest.newBuilder()
   *           .setParent(SessionName.of("[SESSION]").toString())
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   for (Test element : testingClient.listTests(request).iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListTestsPagedResponse listTests(ListTestsRequest request) {
    return listTestsPagedCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * List the tests of a sessesion.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   ListTestsRequest request =
   *       ListTestsRequest.newBuilder()
   *           .setParent(SessionName.of("[SESSION]").toString())
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   ApiFuture<Test> future = testingClient.listTestsPagedCallable().futureCall(request);
   *   // Do something.
   *   for (Test element : future.get().iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   */
  public final UnaryCallable<ListTestsRequest, ListTestsPagedResponse> listTestsPagedCallable() {
    return stub.listTestsPagedCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * List the tests of a sessesion.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   ListTestsRequest request =
   *       ListTestsRequest.newBuilder()
   *           .setParent(SessionName.of("[SESSION]").toString())
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   while (true) {
   *     ListTestsResponse response = testingClient.listTestsCallable().call(request);
   *     for (Test element : response.getTestsList()) {
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
  public final UnaryCallable<ListTestsRequest, ListTestsResponse> listTestsCallable() {
    return stub.listTestsCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Explicitly decline to implement a test.
   *
   * <p>This removes the test from subsequent `ListTests` calls, and attempting to do the test will
   * error.
   *
   * <p>This method will error if attempting to delete a required test.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   DeleteTestRequest request =
   *       DeleteTestRequest.newBuilder()
   *           .setName(TestName.of("[SESSION]", "[TEST]").toString())
   *           .build();
   *   testingClient.deleteTest(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteTest(DeleteTestRequest request) {
    deleteTestCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Explicitly decline to implement a test.
   *
   * <p>This removes the test from subsequent `ListTests` calls, and attempting to do the test will
   * error.
   *
   * <p>This method will error if attempting to delete a required test.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   DeleteTestRequest request =
   *       DeleteTestRequest.newBuilder()
   *           .setName(TestName.of("[SESSION]", "[TEST]").toString())
   *           .build();
   *   ApiFuture<Empty> future = testingClient.deleteTestCallable().futureCall(request);
   *   // Do something.
   *   future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<DeleteTestRequest, Empty> deleteTestCallable() {
    return stub.deleteTestCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Register a response to a test.
   *
   * <p>In cases where a test involves registering a final answer at the end of the test, this
   * method provides the means to do so.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   VerifyTestRequest request =
   *       VerifyTestRequest.newBuilder()
   *           .setName(TestName.of("[SESSION]", "[TEST]").toString())
   *           .setAnswer(ByteString.EMPTY)
   *           .addAllAnswers(new ArrayList<ByteString>())
   *           .build();
   *   VerifyTestResponse response = testingClient.verifyTest(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final VerifyTestResponse verifyTest(VerifyTestRequest request) {
    return verifyTestCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Register a response to a test.
   *
   * <p>In cases where a test involves registering a final answer at the end of the test, this
   * method provides the means to do so.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (TestingClient testingClient = TestingClient.create()) {
   *   VerifyTestRequest request =
   *       VerifyTestRequest.newBuilder()
   *           .setName(TestName.of("[SESSION]", "[TEST]").toString())
   *           .setAnswer(ByteString.EMPTY)
   *           .addAllAnswers(new ArrayList<ByteString>())
   *           .build();
   *   ApiFuture<VerifyTestResponse> future = testingClient.verifyTestCallable().futureCall(request);
   *   // Do something.
   *   VerifyTestResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<VerifyTestRequest, VerifyTestResponse> verifyTestCallable() {
    return stub.verifyTestCallable();
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

  public static class ListSessionsPagedResponse
      extends AbstractPagedListResponse<
          ListSessionsRequest,
          ListSessionsResponse,
          Session,
          ListSessionsPage,
          ListSessionsFixedSizeCollection> {

    public static ApiFuture<ListSessionsPagedResponse> createAsync(
        PageContext<ListSessionsRequest, ListSessionsResponse, Session> context,
        ApiFuture<ListSessionsResponse> futureResponse) {
      ApiFuture<ListSessionsPage> futurePage =
          ListSessionsPage.createEmptyPage().createPageAsync(context, futureResponse);
      return ApiFutures.transform(
          futurePage,
          input -> new ListSessionsPagedResponse(input),
          MoreExecutors.directExecutor());
    }

    private ListSessionsPagedResponse(ListSessionsPage page) {
      super(page, ListSessionsFixedSizeCollection.createEmptyCollection());
    }
  }

  public static class ListSessionsPage
      extends AbstractPage<ListSessionsRequest, ListSessionsResponse, Session, ListSessionsPage> {

    private ListSessionsPage(
        PageContext<ListSessionsRequest, ListSessionsResponse, Session> context,
        ListSessionsResponse response) {
      super(context, response);
    }

    private static ListSessionsPage createEmptyPage() {
      return new ListSessionsPage(null, null);
    }

    @Override
    protected ListSessionsPage createPage(
        PageContext<ListSessionsRequest, ListSessionsResponse, Session> context,
        ListSessionsResponse response) {
      return new ListSessionsPage(context, response);
    }

    @Override
    public ApiFuture<ListSessionsPage> createPageAsync(
        PageContext<ListSessionsRequest, ListSessionsResponse, Session> context,
        ApiFuture<ListSessionsResponse> futureResponse) {
      return super.createPageAsync(context, futureResponse);
    }
  }

  public static class ListSessionsFixedSizeCollection
      extends AbstractFixedSizeCollection<
          ListSessionsRequest,
          ListSessionsResponse,
          Session,
          ListSessionsPage,
          ListSessionsFixedSizeCollection> {

    private ListSessionsFixedSizeCollection(List<ListSessionsPage> pages, int collectionSize) {
      super(pages, collectionSize);
    }

    private static ListSessionsFixedSizeCollection createEmptyCollection() {
      return new ListSessionsFixedSizeCollection(null, 0);
    }

    @Override
    protected ListSessionsFixedSizeCollection createCollection(
        List<ListSessionsPage> pages, int collectionSize) {
      return new ListSessionsFixedSizeCollection(pages, collectionSize);
    }
  }

  public static class ListTestsPagedResponse
      extends AbstractPagedListResponse<
          ListTestsRequest, ListTestsResponse, Test, ListTestsPage, ListTestsFixedSizeCollection> {

    public static ApiFuture<ListTestsPagedResponse> createAsync(
        PageContext<ListTestsRequest, ListTestsResponse, Test> context,
        ApiFuture<ListTestsResponse> futureResponse) {
      ApiFuture<ListTestsPage> futurePage =
          ListTestsPage.createEmptyPage().createPageAsync(context, futureResponse);
      return ApiFutures.transform(
          futurePage, input -> new ListTestsPagedResponse(input), MoreExecutors.directExecutor());
    }

    private ListTestsPagedResponse(ListTestsPage page) {
      super(page, ListTestsFixedSizeCollection.createEmptyCollection());
    }
  }

  public static class ListTestsPage
      extends AbstractPage<ListTestsRequest, ListTestsResponse, Test, ListTestsPage> {

    private ListTestsPage(
        PageContext<ListTestsRequest, ListTestsResponse, Test> context,
        ListTestsResponse response) {
      super(context, response);
    }

    private static ListTestsPage createEmptyPage() {
      return new ListTestsPage(null, null);
    }

    @Override
    protected ListTestsPage createPage(
        PageContext<ListTestsRequest, ListTestsResponse, Test> context,
        ListTestsResponse response) {
      return new ListTestsPage(context, response);
    }

    @Override
    public ApiFuture<ListTestsPage> createPageAsync(
        PageContext<ListTestsRequest, ListTestsResponse, Test> context,
        ApiFuture<ListTestsResponse> futureResponse) {
      return super.createPageAsync(context, futureResponse);
    }
  }

  public static class ListTestsFixedSizeCollection
      extends AbstractFixedSizeCollection<
          ListTestsRequest, ListTestsResponse, Test, ListTestsPage, ListTestsFixedSizeCollection> {

    private ListTestsFixedSizeCollection(List<ListTestsPage> pages, int collectionSize) {
      super(pages, collectionSize);
    }

    private static ListTestsFixedSizeCollection createEmptyCollection() {
      return new ListTestsFixedSizeCollection(null, 0);
    }

    @Override
    protected ListTestsFixedSizeCollection createCollection(
        List<ListTestsPage> pages, int collectionSize) {
      return new ListTestsFixedSizeCollection(pages, collectionSize);
    }
  }
}
