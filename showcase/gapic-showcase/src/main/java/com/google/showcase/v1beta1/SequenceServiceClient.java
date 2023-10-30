/*
 * Copyright 2023 Google LLC
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
import com.google.api.gax.rpc.ServerStreamingCallable;
import com.google.api.gax.rpc.UnaryCallable;
import com.google.cloud.location.GetLocationRequest;
import com.google.cloud.location.ListLocationsRequest;
import com.google.cloud.location.ListLocationsResponse;
import com.google.cloud.location.Location;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.iam.v1.GetIamPolicyRequest;
import com.google.iam.v1.Policy;
import com.google.iam.v1.SetIamPolicyRequest;
import com.google.iam.v1.TestIamPermissionsRequest;
import com.google.iam.v1.TestIamPermissionsResponse;
import com.google.longrunning.CancelOperationRequest;
import com.google.longrunning.DeleteOperationRequest;
import com.google.longrunning.GetOperationRequest;
import com.google.longrunning.ListOperationsRequest;
import com.google.longrunning.ListOperationsResponse;
import com.google.longrunning.Operation;
import com.google.protobuf.Empty;
import com.google.showcase.v1beta1.stub.SequenceServiceStub;
import com.google.showcase.v1beta1.stub.SequenceServiceStubSettings;
import java.io.IOException;
import java.util.List;
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
   *   StreamingSequence streamingSequence = StreamingSequence.newBuilder().build();
   *   StreamingSequence response = sequenceServiceClient.createStreamingSequence(streamingSequence);
   * }
   * }</pre>
   *
   * @param streamingSequence
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final StreamingSequence createStreamingSequence(StreamingSequence streamingSequence) {
    CreateStreamingSequenceRequest request =
        CreateStreamingSequenceRequest.newBuilder().setStreamingSequence(streamingSequence).build();
    return createStreamingSequence(request);
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
   *   CreateStreamingSequenceRequest request =
   *       CreateStreamingSequenceRequest.newBuilder()
   *           .setStreamingSequence(StreamingSequence.newBuilder().build())
   *           .build();
   *   StreamingSequence response = sequenceServiceClient.createStreamingSequence(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final StreamingSequence createStreamingSequence(CreateStreamingSequenceRequest request) {
    return createStreamingSequenceCallable().call(request);
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
   *   CreateStreamingSequenceRequest request =
   *       CreateStreamingSequenceRequest.newBuilder()
   *           .setStreamingSequence(StreamingSequence.newBuilder().build())
   *           .build();
   *   ApiFuture<StreamingSequence> future =
   *       sequenceServiceClient.createStreamingSequenceCallable().futureCall(request);
   *   // Do something.
   *   StreamingSequence response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<CreateStreamingSequenceRequest, StreamingSequence>
      createStreamingSequenceCallable() {
    return stub.createStreamingSequenceCallable();
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
   *   StreamingSequenceReportName name = StreamingSequenceReportName.of("[STREAMING_SEQUENCE]");
   *   StreamingSequenceReport response = sequenceServiceClient.getStreamingSequenceReport(name);
   * }
   * }</pre>
   *
   * @param name
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final StreamingSequenceReport getStreamingSequenceReport(
      StreamingSequenceReportName name) {
    GetStreamingSequenceReportRequest request =
        GetStreamingSequenceReportRequest.newBuilder()
            .setName(name == null ? null : name.toString())
            .build();
    return getStreamingSequenceReport(request);
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
   *   String name = StreamingSequenceReportName.of("[STREAMING_SEQUENCE]").toString();
   *   StreamingSequenceReport response = sequenceServiceClient.getStreamingSequenceReport(name);
   * }
   * }</pre>
   *
   * @param name
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final StreamingSequenceReport getStreamingSequenceReport(String name) {
    GetStreamingSequenceReportRequest request =
        GetStreamingSequenceReportRequest.newBuilder().setName(name).build();
    return getStreamingSequenceReport(request);
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
   *   GetStreamingSequenceReportRequest request =
   *       GetStreamingSequenceReportRequest.newBuilder()
   *           .setName(StreamingSequenceReportName.of("[STREAMING_SEQUENCE]").toString())
   *           .build();
   *   StreamingSequenceReport response = sequenceServiceClient.getStreamingSequenceReport(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final StreamingSequenceReport getStreamingSequenceReport(
      GetStreamingSequenceReportRequest request) {
    return getStreamingSequenceReportCallable().call(request);
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
   *   GetStreamingSequenceReportRequest request =
   *       GetStreamingSequenceReportRequest.newBuilder()
   *           .setName(StreamingSequenceReportName.of("[STREAMING_SEQUENCE]").toString())
   *           .build();
   *   ApiFuture<StreamingSequenceReport> future =
   *       sequenceServiceClient.getStreamingSequenceReportCallable().futureCall(request);
   *   // Do something.
   *   StreamingSequenceReport response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<GetStreamingSequenceReportRequest, StreamingSequenceReport>
      getStreamingSequenceReportCallable() {
    return stub.getStreamingSequenceReportCallable();
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

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Attempts a streaming sequence.
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
   *   AttemptStreamingSequenceRequest request =
   *       AttemptStreamingSequenceRequest.newBuilder()
   *           .setName(StreamingSequenceName.of("[STREAMING_SEQUENCE]").toString())
   *           .build();
   *   ServerStream<AttemptStreamingSequenceResponse> stream =
   *       sequenceServiceClient.attemptStreamingSequenceCallable().call(request);
   *   for (AttemptStreamingSequenceResponse response : stream) {
   *     // Do something when a response is received.
   *   }
   * }
   * }</pre>
   */
  public final ServerStreamingCallable<
          AttemptStreamingSequenceRequest, AttemptStreamingSequenceResponse>
      attemptStreamingSequenceCallable() {
    return stub.attemptStreamingSequenceCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists operations that match the specified filter in the request. If the server doesn't support
   * this method, it returns `UNIMPLEMENTED`.
   *
   * <p>NOTE: the `name` binding allows API services to override the binding to use different
   * resource name schemes, such as `users/&#42;/operations`. To override the binding, API services
   * can add a binding such as `"/v1/{name=users/&#42;}/operations"` to their service configuration.
   * For backwards compatibility, the default name includes the operations collection id, however
   * overriding users must ensure the name binding is the parent resource, without the operations
   * collection id.
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
   *   String name = "name3373707";
   *   String filter = "filter-1274492040";
   *   for (Operation element : sequenceServiceClient.listOperations(name, filter).iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   *
   * @param name The name of the operation's parent resource.
   * @param filter The standard list filter.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListOperationsPagedResponse listOperations(String name, String filter) {
    ListOperationsRequest request =
        ListOperationsRequest.newBuilder().setName(name).setFilter(filter).build();
    return listOperations(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists operations that match the specified filter in the request. If the server doesn't support
   * this method, it returns `UNIMPLEMENTED`.
   *
   * <p>NOTE: the `name` binding allows API services to override the binding to use different
   * resource name schemes, such as `users/&#42;/operations`. To override the binding, API services
   * can add a binding such as `"/v1/{name=users/&#42;}/operations"` to their service configuration.
   * For backwards compatibility, the default name includes the operations collection id, however
   * overriding users must ensure the name binding is the parent resource, without the operations
   * collection id.
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
   *   ListOperationsRequest request =
   *       ListOperationsRequest.newBuilder()
   *           .setName("name3373707")
   *           .setFilter("filter-1274492040")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   for (Operation element : sequenceServiceClient.listOperations(request).iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListOperationsPagedResponse listOperations(ListOperationsRequest request) {
    return listOperationsPagedCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists operations that match the specified filter in the request. If the server doesn't support
   * this method, it returns `UNIMPLEMENTED`.
   *
   * <p>NOTE: the `name` binding allows API services to override the binding to use different
   * resource name schemes, such as `users/&#42;/operations`. To override the binding, API services
   * can add a binding such as `"/v1/{name=users/&#42;}/operations"` to their service configuration.
   * For backwards compatibility, the default name includes the operations collection id, however
   * overriding users must ensure the name binding is the parent resource, without the operations
   * collection id.
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
   *   ListOperationsRequest request =
   *       ListOperationsRequest.newBuilder()
   *           .setName("name3373707")
   *           .setFilter("filter-1274492040")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   ApiFuture<Operation> future =
   *       sequenceServiceClient.listOperationsPagedCallable().futureCall(request);
   *   // Do something.
   *   for (Operation element : future.get().iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   */
  public final UnaryCallable<ListOperationsRequest, ListOperationsPagedResponse>
      listOperationsPagedCallable() {
    return stub.listOperationsPagedCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists operations that match the specified filter in the request. If the server doesn't support
   * this method, it returns `UNIMPLEMENTED`.
   *
   * <p>NOTE: the `name` binding allows API services to override the binding to use different
   * resource name schemes, such as `users/&#42;/operations`. To override the binding, API services
   * can add a binding such as `"/v1/{name=users/&#42;}/operations"` to their service configuration.
   * For backwards compatibility, the default name includes the operations collection id, however
   * overriding users must ensure the name binding is the parent resource, without the operations
   * collection id.
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
   *   ListOperationsRequest request =
   *       ListOperationsRequest.newBuilder()
   *           .setName("name3373707")
   *           .setFilter("filter-1274492040")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   while (true) {
   *     ListOperationsResponse response =
   *         sequenceServiceClient.listOperationsCallable().call(request);
   *     for (Operation element : response.getOperationsList()) {
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
  public final UnaryCallable<ListOperationsRequest, ListOperationsResponse>
      listOperationsCallable() {
    return stub.listOperationsCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets the latest state of a long-running operation. Clients can use this method to poll the
   * operation result at intervals as recommended by the API service.
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
   *   String name = "name3373707";
   *   Operation response = sequenceServiceClient.getOperation(name);
   * }
   * }</pre>
   *
   * @param name The name of the operation resource.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Operation getOperation(String name) {
    GetOperationRequest request = GetOperationRequest.newBuilder().setName(name).build();
    return getOperation(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets the latest state of a long-running operation. Clients can use this method to poll the
   * operation result at intervals as recommended by the API service.
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
   *   GetOperationRequest request = GetOperationRequest.newBuilder().setName("name3373707").build();
   *   Operation response = sequenceServiceClient.getOperation(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Operation getOperation(GetOperationRequest request) {
    return getOperationCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets the latest state of a long-running operation. Clients can use this method to poll the
   * operation result at intervals as recommended by the API service.
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
   *   GetOperationRequest request = GetOperationRequest.newBuilder().setName("name3373707").build();
   *   ApiFuture<Operation> future =
   *       sequenceServiceClient.getOperationCallable().futureCall(request);
   *   // Do something.
   *   Operation response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<GetOperationRequest, Operation> getOperationCallable() {
    return stub.getOperationCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a long-running operation. This method indicates that the client is no longer interested
   * in the operation result. It does not cancel the operation. If the server doesn't support this
   * method, it returns `google.rpc.Code.UNIMPLEMENTED`.
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
   *   String name = "name3373707";
   *   sequenceServiceClient.deleteOperation(name);
   * }
   * }</pre>
   *
   * @param name The name of the operation resource to be deleted.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteOperation(String name) {
    DeleteOperationRequest request = DeleteOperationRequest.newBuilder().setName(name).build();
    deleteOperation(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a long-running operation. This method indicates that the client is no longer interested
   * in the operation result. It does not cancel the operation. If the server doesn't support this
   * method, it returns `google.rpc.Code.UNIMPLEMENTED`.
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
   *   DeleteOperationRequest request =
   *       DeleteOperationRequest.newBuilder().setName("name3373707").build();
   *   sequenceServiceClient.deleteOperation(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteOperation(DeleteOperationRequest request) {
    deleteOperationCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a long-running operation. This method indicates that the client is no longer interested
   * in the operation result. It does not cancel the operation. If the server doesn't support this
   * method, it returns `google.rpc.Code.UNIMPLEMENTED`.
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
   *   DeleteOperationRequest request =
   *       DeleteOperationRequest.newBuilder().setName("name3373707").build();
   *   ApiFuture<Empty> future = sequenceServiceClient.deleteOperationCallable().futureCall(request);
   *   // Do something.
   *   future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<DeleteOperationRequest, Empty> deleteOperationCallable() {
    return stub.deleteOperationCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Starts asynchronous cancellation on a long-running operation. The server makes a best effort to
   * cancel the operation, but success is not guaranteed. If the server doesn't support this method,
   * it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use
   * [Operations.GetOperation][google.longrunning.Operations.GetOperation] or other methods to check
   * whether the cancellation succeeded or whether the operation completed despite cancellation. On
   * successful cancellation, the operation is not deleted; instead, it becomes an operation with an
   * [Operation.error][google.longrunning.Operation.error] value with a
   * [google.rpc.Status.code][google.rpc.Status.code] of 1, corresponding to `Code.CANCELLED`.
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
   *   String name = "name3373707";
   *   sequenceServiceClient.cancelOperation(name);
   * }
   * }</pre>
   *
   * @param name The name of the operation resource to be cancelled.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void cancelOperation(String name) {
    CancelOperationRequest request = CancelOperationRequest.newBuilder().setName(name).build();
    cancelOperation(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Starts asynchronous cancellation on a long-running operation. The server makes a best effort to
   * cancel the operation, but success is not guaranteed. If the server doesn't support this method,
   * it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use
   * [Operations.GetOperation][google.longrunning.Operations.GetOperation] or other methods to check
   * whether the cancellation succeeded or whether the operation completed despite cancellation. On
   * successful cancellation, the operation is not deleted; instead, it becomes an operation with an
   * [Operation.error][google.longrunning.Operation.error] value with a
   * [google.rpc.Status.code][google.rpc.Status.code] of 1, corresponding to `Code.CANCELLED`.
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
   *   CancelOperationRequest request =
   *       CancelOperationRequest.newBuilder().setName("name3373707").build();
   *   sequenceServiceClient.cancelOperation(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void cancelOperation(CancelOperationRequest request) {
    cancelOperationCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Starts asynchronous cancellation on a long-running operation. The server makes a best effort to
   * cancel the operation, but success is not guaranteed. If the server doesn't support this method,
   * it returns `google.rpc.Code.UNIMPLEMENTED`. Clients can use
   * [Operations.GetOperation][google.longrunning.Operations.GetOperation] or other methods to check
   * whether the cancellation succeeded or whether the operation completed despite cancellation. On
   * successful cancellation, the operation is not deleted; instead, it becomes an operation with an
   * [Operation.error][google.longrunning.Operation.error] value with a
   * [google.rpc.Status.code][google.rpc.Status.code] of 1, corresponding to `Code.CANCELLED`.
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
   *   CancelOperationRequest request =
   *       CancelOperationRequest.newBuilder().setName("name3373707").build();
   *   ApiFuture<Empty> future = sequenceServiceClient.cancelOperationCallable().futureCall(request);
   *   // Do something.
   *   future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<CancelOperationRequest, Empty> cancelOperationCallable() {
    return stub.cancelOperationCallable();
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
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   ListLocationsRequest request =
   *       ListLocationsRequest.newBuilder()
   *           .setName("name3373707")
   *           .setFilter("filter-1274492040")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   for (Location element : sequenceServiceClient.listLocations(request).iterateAll()) {
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
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   ListLocationsRequest request =
   *       ListLocationsRequest.newBuilder()
   *           .setName("name3373707")
   *           .setFilter("filter-1274492040")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   ApiFuture<Location> future =
   *       sequenceServiceClient.listLocationsPagedCallable().futureCall(request);
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
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   ListLocationsRequest request =
   *       ListLocationsRequest.newBuilder()
   *           .setName("name3373707")
   *           .setFilter("filter-1274492040")
   *           .setPageSize(883849137)
   *           .setPageToken("pageToken873572522")
   *           .build();
   *   while (true) {
   *     ListLocationsResponse response =
   *         sequenceServiceClient.listLocationsCallable().call(request);
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
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   GetLocationRequest request = GetLocationRequest.newBuilder().setName("name3373707").build();
   *   Location response = sequenceServiceClient.getLocation(request);
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
   * try (SequenceServiceClient sequenceServiceClient = SequenceServiceClient.create()) {
   *   GetLocationRequest request = GetLocationRequest.newBuilder().setName("name3373707").build();
   *   ApiFuture<Location> future = sequenceServiceClient.getLocationCallable().futureCall(request);
   *   // Do something.
   *   Location response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<GetLocationRequest, Location> getLocationCallable() {
    return stub.getLocationCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Sets the access control policy on the specified resource. Replaces any existing policy.
   *
   * <p>Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors.
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
   *   SetIamPolicyRequest request =
   *       SetIamPolicyRequest.newBuilder()
   *           .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
   *           .setPolicy(Policy.newBuilder().build())
   *           .setUpdateMask(FieldMask.newBuilder().build())
   *           .build();
   *   Policy response = sequenceServiceClient.setIamPolicy(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Policy setIamPolicy(SetIamPolicyRequest request) {
    return setIamPolicyCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Sets the access control policy on the specified resource. Replaces any existing policy.
   *
   * <p>Can return `NOT_FOUND`, `INVALID_ARGUMENT`, and `PERMISSION_DENIED` errors.
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
   *   SetIamPolicyRequest request =
   *       SetIamPolicyRequest.newBuilder()
   *           .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
   *           .setPolicy(Policy.newBuilder().build())
   *           .setUpdateMask(FieldMask.newBuilder().build())
   *           .build();
   *   ApiFuture<Policy> future = sequenceServiceClient.setIamPolicyCallable().futureCall(request);
   *   // Do something.
   *   Policy response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<SetIamPolicyRequest, Policy> setIamPolicyCallable() {
    return stub.setIamPolicyCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets the access control policy for a resource. Returns an empty policy if the resource exists
   * and does not have a policy set.
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
   *   GetIamPolicyRequest request =
   *       GetIamPolicyRequest.newBuilder()
   *           .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
   *           .setOptions(GetPolicyOptions.newBuilder().build())
   *           .build();
   *   Policy response = sequenceServiceClient.getIamPolicy(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Policy getIamPolicy(GetIamPolicyRequest request) {
    return getIamPolicyCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets the access control policy for a resource. Returns an empty policy if the resource exists
   * and does not have a policy set.
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
   *   GetIamPolicyRequest request =
   *       GetIamPolicyRequest.newBuilder()
   *           .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
   *           .setOptions(GetPolicyOptions.newBuilder().build())
   *           .build();
   *   ApiFuture<Policy> future = sequenceServiceClient.getIamPolicyCallable().futureCall(request);
   *   // Do something.
   *   Policy response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<GetIamPolicyRequest, Policy> getIamPolicyCallable() {
    return stub.getIamPolicyCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Returns permissions that a caller has on the specified resource. If the resource does not
   * exist, this will return an empty set of permissions, not a `NOT_FOUND` error.
   *
   * <p>Note: This operation is designed to be used for building permission-aware UIs and
   * command-line tools, not for authorization checking. This operation may "fail open" without
   * warning.
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
   *   TestIamPermissionsRequest request =
   *       TestIamPermissionsRequest.newBuilder()
   *           .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
   *           .addAllPermissions(new ArrayList<String>())
   *           .build();
   *   TestIamPermissionsResponse response = sequenceServiceClient.testIamPermissions(request);
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final TestIamPermissionsResponse testIamPermissions(TestIamPermissionsRequest request) {
    return testIamPermissionsCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Returns permissions that a caller has on the specified resource. If the resource does not
   * exist, this will return an empty set of permissions, not a `NOT_FOUND` error.
   *
   * <p>Note: This operation is designed to be used for building permission-aware UIs and
   * command-line tools, not for authorization checking. This operation may "fail open" without
   * warning.
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
   *   TestIamPermissionsRequest request =
   *       TestIamPermissionsRequest.newBuilder()
   *           .setResource(BlurbName.ofRoomBlurbName("[ROOM]", "[BLURB]").toString())
   *           .addAllPermissions(new ArrayList<String>())
   *           .build();
   *   ApiFuture<TestIamPermissionsResponse> future =
   *       sequenceServiceClient.testIamPermissionsCallable().futureCall(request);
   *   // Do something.
   *   TestIamPermissionsResponse response = future.get();
   * }
   * }</pre>
   */
  public final UnaryCallable<TestIamPermissionsRequest, TestIamPermissionsResponse>
      testIamPermissionsCallable() {
    return stub.testIamPermissionsCallable();
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

  public static class ListOperationsPagedResponse
      extends AbstractPagedListResponse<
          ListOperationsRequest,
          ListOperationsResponse,
          Operation,
          ListOperationsPage,
          ListOperationsFixedSizeCollection> {

    public static ApiFuture<ListOperationsPagedResponse> createAsync(
        PageContext<ListOperationsRequest, ListOperationsResponse, Operation> context,
        ApiFuture<ListOperationsResponse> futureResponse) {
      ApiFuture<ListOperationsPage> futurePage =
          ListOperationsPage.createEmptyPage().createPageAsync(context, futureResponse);
      return ApiFutures.transform(
          futurePage,
          input -> new ListOperationsPagedResponse(input),
          MoreExecutors.directExecutor());
    }

    private ListOperationsPagedResponse(ListOperationsPage page) {
      super(page, ListOperationsFixedSizeCollection.createEmptyCollection());
    }
  }

  public static class ListOperationsPage
      extends AbstractPage<
          ListOperationsRequest, ListOperationsResponse, Operation, ListOperationsPage> {

    private ListOperationsPage(
        PageContext<ListOperationsRequest, ListOperationsResponse, Operation> context,
        ListOperationsResponse response) {
      super(context, response);
    }

    private static ListOperationsPage createEmptyPage() {
      return new ListOperationsPage(null, null);
    }

    @Override
    protected ListOperationsPage createPage(
        PageContext<ListOperationsRequest, ListOperationsResponse, Operation> context,
        ListOperationsResponse response) {
      return new ListOperationsPage(context, response);
    }

    @Override
    public ApiFuture<ListOperationsPage> createPageAsync(
        PageContext<ListOperationsRequest, ListOperationsResponse, Operation> context,
        ApiFuture<ListOperationsResponse> futureResponse) {
      return super.createPageAsync(context, futureResponse);
    }
  }

  public static class ListOperationsFixedSizeCollection
      extends AbstractFixedSizeCollection<
          ListOperationsRequest,
          ListOperationsResponse,
          Operation,
          ListOperationsPage,
          ListOperationsFixedSizeCollection> {

    private ListOperationsFixedSizeCollection(List<ListOperationsPage> pages, int collectionSize) {
      super(pages, collectionSize);
    }

    private static ListOperationsFixedSizeCollection createEmptyCollection() {
      return new ListOperationsFixedSizeCollection(null, 0);
    }

    @Override
    protected ListOperationsFixedSizeCollection createCollection(
        List<ListOperationsPage> pages, int collectionSize) {
      return new ListOperationsFixedSizeCollection(pages, collectionSize);
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
