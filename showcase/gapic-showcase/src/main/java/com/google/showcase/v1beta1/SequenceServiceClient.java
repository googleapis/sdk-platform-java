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

import com.google.api.core.BetaApi;
import com.google.api.gax.core.BackgroundResource;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.protobuf.Empty;
import com.google.showcase.v1beta1.stub.SequenceServiceStub;
import com.google.showcase.v1beta1.stub.SequenceServiceStubSettings;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * This class provides the ability to make remote calls to the backing service through method calls
 * that map to API methods. Sample code to get started:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
 *   Sequence sequence = Sequence.newBuilder().build();
 *   Sequence response = sequenceServiceClient.createSequence(sequence);
 * }
 * }</pre>
 *
 * <p>Note: close() needs to be called on the SequenceServiceClient object to clean up resources
 * such as threads. In the example above, try-with-resources is used, which automatically calls
 * close().
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
 * <p>This class can be customized by passing in a custom instance of SequenceServiceSettings to
 * create(). For example:
 *
 * <p>To customize credentials:
 *
 * <pre>{@code
 * // This snippet has been automatically generated and should be regarded as a code template only.
 * // It will require modifications to work:
 * // - It may require correct/in-range values for request initialization.
 * // - It may require specifying regional endpoints when creating the service client as shown in
 * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
 * SequenceServiceSettings sequenceServiceSettings =
 *     SequenceServiceSettings.newBuilder()
 *         .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
 *         .build();
 * SequenceServiceClient sequenceServiceClient =
 *     SequenceServiceClient.create(sequenceServiceSettings);
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
 * SequenceServiceSettings sequenceServiceSettings =
 *     SequenceServiceSettings.newBuilder().setEndpoint(myEndpoint).build();
 * SequenceServiceClient sequenceServiceClient =
 *     SequenceServiceClient.create(sequenceServiceSettings);
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
 * SequenceServiceSettings sequenceServiceSettings =
 *     SequenceServiceSettings.newHttpJsonBuilder().build();
 * SequenceServiceClient sequenceServiceClient =
 *     SequenceServiceClient.create(sequenceServiceSettings);
 * }</pre>
 *
 * <p>Please refer to the GitHub repository's samples for more quickstart code snippets.
 */
@BetaApi
@Generated("by gapic-generator-java")
public class SequenceServiceClient implements BackgroundResource {
  private final SequenceServiceSettings settings;
  private final SequenceServiceStub stub;

  /** Constructs an instance of SequenceServiceClient with default settings. */
  public static final SequenceServiceClient create() throws IOException {
    return create(SequenceServiceSettings.newBuilder().build());
  }

  /**
   * Constructs an instance of SequenceServiceClient, using the given settings. The channels are
   * created based on the settings passed in, or defaults for any settings that are not set.
   */
  public static final SequenceServiceClient create(SequenceServiceSettings settings)
      throws IOException {
    return new SequenceServiceClient(settings);
  }

  /**
   * Constructs an instance of SequenceServiceClient, using the given stub for making calls. This is
   * for advanced usage - prefer using create(SequenceServiceSettings).
   */
  public static final SequenceServiceClient create(SequenceServiceStub stub) {
    return new SequenceServiceClient(stub);
  }

  /**
   * Constructs an instance of SequenceServiceClient, using the given settings. This is protected so
   * that it is easy to make a subclass, but otherwise, the static factory methods should be
   * preferred.
   */
  protected SequenceServiceClient(SequenceServiceSettings settings) throws IOException {
    this.settings = settings;
    this.stub = ((SequenceServiceStubSettings) settings.getStubSettings()).createStub();
  }

  protected SequenceServiceClient(SequenceServiceStub stub) {
    this.settings = null;
    this.stub = stub;
  }

  public final SequenceServiceSettings getSettings() {
    return settings;
  }

  public SequenceServiceStub getStub() {
    return stub;
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a sequence.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   Sequence sequence = Sequence.newBuilder().build();
   *   Sequence response = sequenceServiceClient.createSequence(sequence);
   * }
   * }</pre>
   *
   * @param sequence
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Sequence createSequence(Sequence sequence) {
    CreateSequenceRequest request =
        CreateSequenceRequest.newBuilder().setSequence(sequence).build();
    return createSequence(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a sequence.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   CreateSequenceRequest request =
   *       CreateSequenceRequest.newBuilder().setSequence(Sequence.newBuilder().build()).build();
   *   Sequence response = sequenceServiceClient.createSequence(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Sequence createSequence(CreateSequenceRequest request) {
    return createSequenceCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a sequence.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   CreateSequenceRequest request =
   *       CreateSequenceRequest.newBuilder().setSequence(Sequence.newBuilder().build()).build();
   *   ApiFuture<Sequence> future =
   *       sequenceServiceClient.createSequenceCallable().futureCall(request);
   *   // Do something.
   *   Sequence response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<CreateSequenceRequest, Sequence> createSequenceCallable() {
    return stub.createSequenceCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Retrieves a sequence.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   SequenceReportName name = SequenceReportName.of("[SEQUENCE]");
   *   SequenceReport response = sequenceServiceClient.getSequenceReport(name);
   * }
   * }</pre>
   *
   * @param name
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final SequenceReport getSequenceReport(SequenceReportName name) {
    GetSequenceReportRequest request =
        GetSequenceReportRequest.newBuilder()
            .setName(name == null ? null : name.toString())
            .build();
    return getSequenceReport(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Retrieves a sequence.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   String name = SequenceReportName.of("[SEQUENCE]").toString();
   *   SequenceReport response = sequenceServiceClient.getSequenceReport(name);
   * }
   * }</pre>
   *
   * @param name
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final SequenceReport getSequenceReport(String name) {
    GetSequenceReportRequest request = GetSequenceReportRequest.newBuilder().setName(name).build();
    return getSequenceReport(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Retrieves a sequence.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   GetSequenceReportRequest request =
   *       GetSequenceReportRequest.newBuilder()
   *           .setName(SequenceReportName.of("[SEQUENCE]").toString())
   *           .build();
   *   SequenceReport response = sequenceServiceClient.getSequenceReport(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final SequenceReport getSequenceReport(GetSequenceReportRequest request) {
    return getSequenceReportCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Retrieves a sequence.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   GetSequenceReportRequest request =
   *       GetSequenceReportRequest.newBuilder()
   *           .setName(SequenceReportName.of("[SEQUENCE]").toString())
   *           .build();
   *   ApiFuture<SequenceReport> future =
   *       sequenceServiceClient.getSequenceReportCallable().futureCall(request);
   *   // Do something.
   *   SequenceReport response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<GetSequenceReportRequest, SequenceReport> getSequenceReportCallable() {
    return stub.getSequenceReportCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Attempts a sequence.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   SequenceName name = SequenceName.of("[SEQUENCE]");
   *   sequenceServiceClient.attemptSequence(name);
   * }
   * }</pre>
   *
   * @param name
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void attemptSequence(SequenceName name) {
    AttemptSequenceRequest request =
        AttemptSequenceRequest.newBuilder().setName(name == null ? null : name.toString()).build();
    attemptSequence(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Attempts a sequence.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   String name = SequenceName.of("[SEQUENCE]").toString();
   *   sequenceServiceClient.attemptSequence(name);
   * }
   * }</pre>
   *
   * @param name
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void attemptSequence(String name) {
    AttemptSequenceRequest request = AttemptSequenceRequest.newBuilder().setName(name).build();
    attemptSequence(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Attempts a sequence.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   AttemptSequenceRequest request =
   *       AttemptSequenceRequest.newBuilder()
   *           .setName(SequenceName.of("[SEQUENCE]").toString())
   *           .build();
   *   sequenceServiceClient.attemptSequence(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void attemptSequence(AttemptSequenceRequest request) {
    attemptSequenceCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Attempts a sequence.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * // This snippet has been automatically generated and should be regarded as a code template only.
   * // It will require modifications to work:
   * // - It may require correct/in-range values for request initialization.
   * // - It may require specifying regional endpoints when creating the service client as shown in
   * // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   AttemptSequenceRequest request =
   *       AttemptSequenceRequest.newBuilder()
   *           .setName(SequenceName.of("[SEQUENCE]").toString())
   *           .build();
   *   ApiFuture<Empty> future = sequenceServiceClient.attemptSequenceCallable().futureCall(request);
   *   // Do something.
   *   future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<AttemptSequenceRequest, Empty> attemptSequenceCallable() {
    return stub.attemptSequenceCallable();
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
