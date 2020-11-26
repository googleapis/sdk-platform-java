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

package com.google.cloud.logging.v2;

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
import com.google.cloud.logging.v2.stub.ConfigServiceV2Stub;
import com.google.cloud.logging.v2.stub.ConfigServiceV2StubSettings;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.logging.v2.BillingAccountLocationName;
import com.google.logging.v2.BillingAccountName;
import com.google.logging.v2.CmekSettings;
import com.google.logging.v2.CreateExclusionRequest;
import com.google.logging.v2.CreateSinkRequest;
import com.google.logging.v2.DeleteExclusionRequest;
import com.google.logging.v2.DeleteSinkRequest;
import com.google.logging.v2.FolderLocationName;
import com.google.logging.v2.FolderName;
import com.google.logging.v2.GetBucketRequest;
import com.google.logging.v2.GetCmekSettingsRequest;
import com.google.logging.v2.GetExclusionRequest;
import com.google.logging.v2.GetSinkRequest;
import com.google.logging.v2.ListBucketsRequest;
import com.google.logging.v2.ListBucketsResponse;
import com.google.logging.v2.ListExclusionsRequest;
import com.google.logging.v2.ListExclusionsResponse;
import com.google.logging.v2.ListSinksRequest;
import com.google.logging.v2.ListSinksResponse;
import com.google.logging.v2.LocationName;
import com.google.logging.v2.LogBucket;
import com.google.logging.v2.LogExclusion;
import com.google.logging.v2.LogExclusionName;
import com.google.logging.v2.LogSink;
import com.google.logging.v2.LogSinkName;
import com.google.logging.v2.OrganizationLocationName;
import com.google.logging.v2.OrganizationName;
import com.google.logging.v2.ProjectName;
import com.google.logging.v2.UpdateBucketRequest;
import com.google.logging.v2.UpdateCmekSettingsRequest;
import com.google.logging.v2.UpdateExclusionRequest;
import com.google.logging.v2.UpdateSinkRequest;
import com.google.protobuf.Empty;
import com.google.protobuf.FieldMask;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * Service Description: Service for configuring sinks used to route log entries.
 *
 * <p>This class provides the ability to make remote calls to the backing service through method
 * calls that map to API methods. Sample code to get started:
 *
 * <p>Note: close() needs to be called on the ConfigClient object to clean up resources such as
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
 * <p>This class can be customized by passing in a custom instance of ConfigSettings to create().
 * For example:
 *
 * <p>To customize credentials:
 *
 * <pre>{@code
 * ConfigSettings configSettings =
 *     ConfigSettings.newBuilder()
 *         .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
 *         .build();
 * ConfigClient configClient = ConfigClient.create(configSettings);
 * }</pre>
 *
 * <p>To customize the endpoint:
 *
 * <pre>{@code
 * ConfigSettings configSettings = ConfigSettings.newBuilder().setEndpoint(myEndpoint).build();
 * ConfigClient configClient = ConfigClient.create(configSettings);
 * }</pre>
 *
 * <p>Please refer to the GitHub repository's samples for more quickstart code snippets.
 */
@BetaApi
@Generated("by gapic-generator")
public class ConfigClient implements BackgroundResource {
  private final ConfigSettings settings;
  private final ConfigServiceV2Stub stub;

  /** Constructs an instance of ConfigClient with default settings. */
  public static final ConfigClient create() throws IOException {
    return create(ConfigSettings.newBuilder().build());
  }

  /**
   * Constructs an instance of ConfigClient, using the given settings. The channels are created
   * based on the settings passed in, or defaults for any settings that are not set.
   */
  public static final ConfigClient create(ConfigSettings settings) throws IOException {
    return new ConfigClient(settings);
  }

  /**
   * Constructs an instance of ConfigClient, using the given stub for making calls. This is for
   * advanced usage - prefer using create(ConfigSettings).
   */
  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  public static final ConfigClient create(ConfigServiceV2Stub stub) {
    return new ConfigClient(stub);
  }

  /**
   * Constructs an instance of ConfigClient, using the given settings. This is protected so that it
   * is easy to make a subclass, but otherwise, the static factory methods should be preferred.
   */
  protected ConfigClient(ConfigSettings settings) throws IOException {
    this.settings = settings;
    this.stub = ((ConfigServiceV2StubSettings) settings.getStubSettings()).createStub();
  }

  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  protected ConfigClient(ConfigServiceV2Stub stub) {
    this.settings = null;
    this.stub = stub;
  }

  public final ConfigSettings getSettings() {
    return settings;
  }

  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  public ConfigServiceV2Stub getStub() {
    return stub;
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists buckets (Beta).
   *
   * @param parent Required. The parent resource whose buckets are to be listed:
   *     <p>"projects/[PROJECT_ID]/locations/[LOCATION_ID]"
   *     "organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]"
   *     "folders/[FOLDER_ID]/locations/[LOCATION_ID]"
   *     <p>Note: The locations portion of the resource must be specified, but supplying the
   *     character `-` in place of [LOCATION_ID] will return all buckets.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListBucketsPagedResponse listBuckets(BillingAccountLocationName parent) {
    ListBucketsRequest request =
        ListBucketsRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .build();
    return listBuckets(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists buckets (Beta).
   *
   * @param parent Required. The parent resource whose buckets are to be listed:
   *     <p>"projects/[PROJECT_ID]/locations/[LOCATION_ID]"
   *     "organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]"
   *     "folders/[FOLDER_ID]/locations/[LOCATION_ID]"
   *     <p>Note: The locations portion of the resource must be specified, but supplying the
   *     character `-` in place of [LOCATION_ID] will return all buckets.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListBucketsPagedResponse listBuckets(FolderLocationName parent) {
    ListBucketsRequest request =
        ListBucketsRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .build();
    return listBuckets(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists buckets (Beta).
   *
   * @param parent Required. The parent resource whose buckets are to be listed:
   *     <p>"projects/[PROJECT_ID]/locations/[LOCATION_ID]"
   *     "organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]"
   *     "folders/[FOLDER_ID]/locations/[LOCATION_ID]"
   *     <p>Note: The locations portion of the resource must be specified, but supplying the
   *     character `-` in place of [LOCATION_ID] will return all buckets.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListBucketsPagedResponse listBuckets(LocationName parent) {
    ListBucketsRequest request =
        ListBucketsRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .build();
    return listBuckets(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists buckets (Beta).
   *
   * @param parent Required. The parent resource whose buckets are to be listed:
   *     <p>"projects/[PROJECT_ID]/locations/[LOCATION_ID]"
   *     "organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]"
   *     "folders/[FOLDER_ID]/locations/[LOCATION_ID]"
   *     <p>Note: The locations portion of the resource must be specified, but supplying the
   *     character `-` in place of [LOCATION_ID] will return all buckets.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListBucketsPagedResponse listBuckets(OrganizationLocationName parent) {
    ListBucketsRequest request =
        ListBucketsRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .build();
    return listBuckets(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists buckets (Beta).
   *
   * @param parent Required. The parent resource whose buckets are to be listed:
   *     <p>"projects/[PROJECT_ID]/locations/[LOCATION_ID]"
   *     "organizations/[ORGANIZATION_ID]/locations/[LOCATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/locations/[LOCATION_ID]"
   *     "folders/[FOLDER_ID]/locations/[LOCATION_ID]"
   *     <p>Note: The locations portion of the resource must be specified, but supplying the
   *     character `-` in place of [LOCATION_ID] will return all buckets.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListBucketsPagedResponse listBuckets(String parent) {
    ListBucketsRequest request = ListBucketsRequest.newBuilder().setParent(parent).build();
    return listBuckets(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists buckets (Beta).
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListBucketsPagedResponse listBuckets(ListBucketsRequest request) {
    return listBucketsPagedCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists buckets (Beta).
   *
   * <p>Sample code:
   */
  public final UnaryCallable<ListBucketsRequest, ListBucketsPagedResponse>
      listBucketsPagedCallable() {
    return stub.listBucketsPagedCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists buckets (Beta).
   *
   * <p>Sample code:
   */
  public final UnaryCallable<ListBucketsRequest, ListBucketsResponse> listBucketsCallable() {
    return stub.listBucketsCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a bucket (Beta).
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogBucket getBucket(GetBucketRequest request) {
    return getBucketCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a bucket (Beta).
   *
   * <p>Sample code:
   */
  public final UnaryCallable<GetBucketRequest, LogBucket> getBucketCallable() {
    return stub.getBucketCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Updates a bucket. This method replaces the following fields in the existing bucket with values
   * from the new bucket: `retention_period`
   *
   * <p>If the retention period is decreased and the bucket is locked, FAILED_PRECONDITION will be
   * returned.
   *
   * <p>If the bucket has a LifecycleState of DELETE_REQUESTED, FAILED_PRECONDITION will be
   * returned.
   *
   * <p>A buckets region may not be modified after it is created. This method is in Beta.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogBucket updateBucket(UpdateBucketRequest request) {
    return updateBucketCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Updates a bucket. This method replaces the following fields in the existing bucket with values
   * from the new bucket: `retention_period`
   *
   * <p>If the retention period is decreased and the bucket is locked, FAILED_PRECONDITION will be
   * returned.
   *
   * <p>If the bucket has a LifecycleState of DELETE_REQUESTED, FAILED_PRECONDITION will be
   * returned.
   *
   * <p>A buckets region may not be modified after it is created. This method is in Beta.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<UpdateBucketRequest, LogBucket> updateBucketCallable() {
    return stub.updateBucketCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists sinks.
   *
   * @param parent Required. The parent resource whose sinks are to be listed:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListSinksPagedResponse listSinks(BillingAccountName parent) {
    ListSinksRequest request =
        ListSinksRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .build();
    return listSinks(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists sinks.
   *
   * @param parent Required. The parent resource whose sinks are to be listed:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListSinksPagedResponse listSinks(FolderName parent) {
    ListSinksRequest request =
        ListSinksRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .build();
    return listSinks(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists sinks.
   *
   * @param parent Required. The parent resource whose sinks are to be listed:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListSinksPagedResponse listSinks(OrganizationName parent) {
    ListSinksRequest request =
        ListSinksRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .build();
    return listSinks(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists sinks.
   *
   * @param parent Required. The parent resource whose sinks are to be listed:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListSinksPagedResponse listSinks(ProjectName parent) {
    ListSinksRequest request =
        ListSinksRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .build();
    return listSinks(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists sinks.
   *
   * @param parent Required. The parent resource whose sinks are to be listed:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListSinksPagedResponse listSinks(String parent) {
    ListSinksRequest request = ListSinksRequest.newBuilder().setParent(parent).build();
    return listSinks(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists sinks.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListSinksPagedResponse listSinks(ListSinksRequest request) {
    return listSinksPagedCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists sinks.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<ListSinksRequest, ListSinksPagedResponse> listSinksPagedCallable() {
    return stub.listSinksPagedCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists sinks.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<ListSinksRequest, ListSinksResponse> listSinksCallable() {
    return stub.listSinksCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a sink.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   LogSinkName sinkName = LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]");
   *   LogSink response = configClient.getSink(sinkName);
   * }
   * }</pre>
   *
   * @param sink_name Required. The resource name of the sink:
   *     <p>"projects/[PROJECT_ID]/sinks/[SINK_ID]"
   *     "organizations/[ORGANIZATION_ID]/sinks/[SINK_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/sinks/[SINK_ID]"
   *     "folders/[FOLDER_ID]/sinks/[SINK_ID]"
   *     <p>Example: `"projects/my-project-id/sinks/my-sink-id"`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink getSink(LogSinkName sinkName) {
    GetSinkRequest request =
        GetSinkRequest.newBuilder()
            .setSinkName(Objects.isNull(sinkName) ? null : sinkName.toString())
            .build();
    return getSink(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a sink.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   String sinkName = "sink_name-1391757129";
   *   LogSink response = configClient.getSink(sinkName);
   * }
   * }</pre>
   *
   * @param sink_name Required. The resource name of the sink:
   *     <p>"projects/[PROJECT_ID]/sinks/[SINK_ID]"
   *     "organizations/[ORGANIZATION_ID]/sinks/[SINK_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/sinks/[SINK_ID]"
   *     "folders/[FOLDER_ID]/sinks/[SINK_ID]"
   *     <p>Example: `"projects/my-project-id/sinks/my-sink-id"`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink getSink(String sinkName) {
    GetSinkRequest request = GetSinkRequest.newBuilder().setSinkName(sinkName).build();
    return getSink(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a sink.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink getSink(GetSinkRequest request) {
    return getSinkCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a sink.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<GetSinkRequest, LogSink> getSinkCallable() {
    return stub.getSinkCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a sink that exports specified log entries to a destination. The export of
   * newly-ingested log entries begins immediately, unless the sink's `writer_identity` is not
   * permitted to write to the destination. A sink can export log entries only from the resource
   * owning the sink.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   BillingAccountName parent = BillingAccountName.of("[BILLING_ACCOUNT]");
   *   LogSink sink = LogSink.newBuilder().build();
   *   LogSink response = configClient.createSink(parent, sink);
   * }
   * }</pre>
   *
   * @param parent Required. The resource in which to create the sink:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   *     <p>Examples: `"projects/my-logging-project"`, `"organizations/123456789"`.
   * @param sink Required. The new sink, whose `name` parameter is a sink identifier that is not
   *     already in use.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink createSink(BillingAccountName parent, LogSink sink) {
    CreateSinkRequest request =
        CreateSinkRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .setSink(sink)
            .build();
    return createSink(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a sink that exports specified log entries to a destination. The export of
   * newly-ingested log entries begins immediately, unless the sink's `writer_identity` is not
   * permitted to write to the destination. A sink can export log entries only from the resource
   * owning the sink.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   FolderName parent = FolderName.of("[FOLDER]");
   *   LogSink sink = LogSink.newBuilder().build();
   *   LogSink response = configClient.createSink(parent, sink);
   * }
   * }</pre>
   *
   * @param parent Required. The resource in which to create the sink:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   *     <p>Examples: `"projects/my-logging-project"`, `"organizations/123456789"`.
   * @param sink Required. The new sink, whose `name` parameter is a sink identifier that is not
   *     already in use.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink createSink(FolderName parent, LogSink sink) {
    CreateSinkRequest request =
        CreateSinkRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .setSink(sink)
            .build();
    return createSink(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a sink that exports specified log entries to a destination. The export of
   * newly-ingested log entries begins immediately, unless the sink's `writer_identity` is not
   * permitted to write to the destination. A sink can export log entries only from the resource
   * owning the sink.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   OrganizationName parent = OrganizationName.of("[ORGANIZATION]");
   *   LogSink sink = LogSink.newBuilder().build();
   *   LogSink response = configClient.createSink(parent, sink);
   * }
   * }</pre>
   *
   * @param parent Required. The resource in which to create the sink:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   *     <p>Examples: `"projects/my-logging-project"`, `"organizations/123456789"`.
   * @param sink Required. The new sink, whose `name` parameter is a sink identifier that is not
   *     already in use.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink createSink(OrganizationName parent, LogSink sink) {
    CreateSinkRequest request =
        CreateSinkRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .setSink(sink)
            .build();
    return createSink(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a sink that exports specified log entries to a destination. The export of
   * newly-ingested log entries begins immediately, unless the sink's `writer_identity` is not
   * permitted to write to the destination. A sink can export log entries only from the resource
   * owning the sink.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   ProjectName parent = ProjectName.of("[PROJECT]");
   *   LogSink sink = LogSink.newBuilder().build();
   *   LogSink response = configClient.createSink(parent, sink);
   * }
   * }</pre>
   *
   * @param parent Required. The resource in which to create the sink:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   *     <p>Examples: `"projects/my-logging-project"`, `"organizations/123456789"`.
   * @param sink Required. The new sink, whose `name` parameter is a sink identifier that is not
   *     already in use.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink createSink(ProjectName parent, LogSink sink) {
    CreateSinkRequest request =
        CreateSinkRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .setSink(sink)
            .build();
    return createSink(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a sink that exports specified log entries to a destination. The export of
   * newly-ingested log entries begins immediately, unless the sink's `writer_identity` is not
   * permitted to write to the destination. A sink can export log entries only from the resource
   * owning the sink.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   String parent = "parent-995424086";
   *   LogSink sink = LogSink.newBuilder().build();
   *   LogSink response = configClient.createSink(parent, sink);
   * }
   * }</pre>
   *
   * @param parent Required. The resource in which to create the sink:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   *     <p>Examples: `"projects/my-logging-project"`, `"organizations/123456789"`.
   * @param sink Required. The new sink, whose `name` parameter is a sink identifier that is not
   *     already in use.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink createSink(String parent, LogSink sink) {
    CreateSinkRequest request =
        CreateSinkRequest.newBuilder().setParent(parent).setSink(sink).build();
    return createSink(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a sink that exports specified log entries to a destination. The export of
   * newly-ingested log entries begins immediately, unless the sink's `writer_identity` is not
   * permitted to write to the destination. A sink can export log entries only from the resource
   * owning the sink.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink createSink(CreateSinkRequest request) {
    return createSinkCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a sink that exports specified log entries to a destination. The export of
   * newly-ingested log entries begins immediately, unless the sink's `writer_identity` is not
   * permitted to write to the destination. A sink can export log entries only from the resource
   * owning the sink.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<CreateSinkRequest, LogSink> createSinkCallable() {
    return stub.createSinkCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Updates a sink. This method replaces the following fields in the existing sink with values from
   * the new sink: `destination`, and `filter`.
   *
   * <p>The updated sink might also have a new `writer_identity`; see the `unique_writer_identity`
   * field.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   LogSinkName sinkName = LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]");
   *   LogSink sink = LogSink.newBuilder().build();
   *   LogSink response = configClient.updateSink(sinkName, sink);
   * }
   * }</pre>
   *
   * @param sink_name Required. The full resource name of the sink to update, including the parent
   *     resource and the sink identifier:
   *     <p>"projects/[PROJECT_ID]/sinks/[SINK_ID]"
   *     "organizations/[ORGANIZATION_ID]/sinks/[SINK_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/sinks/[SINK_ID]"
   *     "folders/[FOLDER_ID]/sinks/[SINK_ID]"
   *     <p>Example: `"projects/my-project-id/sinks/my-sink-id"`.
   * @param sink Required. The updated sink, whose name is the same identifier that appears as part
   *     of `sink_name`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink updateSink(LogSinkName sinkName, LogSink sink) {
    UpdateSinkRequest request =
        UpdateSinkRequest.newBuilder()
            .setSinkName(Objects.isNull(sinkName) ? null : sinkName.toString())
            .setSink(sink)
            .build();
    return updateSink(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Updates a sink. This method replaces the following fields in the existing sink with values from
   * the new sink: `destination`, and `filter`.
   *
   * <p>The updated sink might also have a new `writer_identity`; see the `unique_writer_identity`
   * field.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   String sinkName = "sink_name-1391757129";
   *   LogSink sink = LogSink.newBuilder().build();
   *   LogSink response = configClient.updateSink(sinkName, sink);
   * }
   * }</pre>
   *
   * @param sink_name Required. The full resource name of the sink to update, including the parent
   *     resource and the sink identifier:
   *     <p>"projects/[PROJECT_ID]/sinks/[SINK_ID]"
   *     "organizations/[ORGANIZATION_ID]/sinks/[SINK_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/sinks/[SINK_ID]"
   *     "folders/[FOLDER_ID]/sinks/[SINK_ID]"
   *     <p>Example: `"projects/my-project-id/sinks/my-sink-id"`.
   * @param sink Required. The updated sink, whose name is the same identifier that appears as part
   *     of `sink_name`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink updateSink(String sinkName, LogSink sink) {
    UpdateSinkRequest request =
        UpdateSinkRequest.newBuilder().setSinkName(sinkName).setSink(sink).build();
    return updateSink(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Updates a sink. This method replaces the following fields in the existing sink with values from
   * the new sink: `destination`, and `filter`.
   *
   * <p>The updated sink might also have a new `writer_identity`; see the `unique_writer_identity`
   * field.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   LogSinkName sinkName = LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]");
   *   LogSink sink = LogSink.newBuilder().build();
   *   FieldMask updateMask = FieldMask.newBuilder().build();
   *   LogSink response = configClient.updateSink(sinkName, sink, updateMask);
   * }
   * }</pre>
   *
   * @param sink_name Required. The full resource name of the sink to update, including the parent
   *     resource and the sink identifier:
   *     <p>"projects/[PROJECT_ID]/sinks/[SINK_ID]"
   *     "organizations/[ORGANIZATION_ID]/sinks/[SINK_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/sinks/[SINK_ID]"
   *     "folders/[FOLDER_ID]/sinks/[SINK_ID]"
   *     <p>Example: `"projects/my-project-id/sinks/my-sink-id"`.
   * @param sink Required. The updated sink, whose name is the same identifier that appears as part
   *     of `sink_name`.
   * @param update_mask Optional. Field mask that specifies the fields in `sink` that need an
   *     update. A sink field will be overwritten if, and only if, it is in the update mask. `name`
   *     and output only fields cannot be updated.
   *     <p>An empty updateMask is temporarily treated as using the following mask for backwards
   *     compatibility purposes: destination,filter,includeChildren At some point in the future,
   *     behavior will be removed and specifying an empty updateMask will be an error.
   *     <p>For a detailed `FieldMask` definition, see
   *     https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMask
   *     <p>Example: `updateMask=filter`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink updateSink(LogSinkName sinkName, LogSink sink, FieldMask updateMask) {
    UpdateSinkRequest request =
        UpdateSinkRequest.newBuilder()
            .setSinkName(Objects.isNull(sinkName) ? null : sinkName.toString())
            .setSink(sink)
            .setUpdateMask(updateMask)
            .build();
    return updateSink(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Updates a sink. This method replaces the following fields in the existing sink with values from
   * the new sink: `destination`, and `filter`.
   *
   * <p>The updated sink might also have a new `writer_identity`; see the `unique_writer_identity`
   * field.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   String sinkName = "sink_name-1391757129";
   *   LogSink sink = LogSink.newBuilder().build();
   *   FieldMask updateMask = FieldMask.newBuilder().build();
   *   LogSink response = configClient.updateSink(sinkName, sink, updateMask);
   * }
   * }</pre>
   *
   * @param sink_name Required. The full resource name of the sink to update, including the parent
   *     resource and the sink identifier:
   *     <p>"projects/[PROJECT_ID]/sinks/[SINK_ID]"
   *     "organizations/[ORGANIZATION_ID]/sinks/[SINK_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/sinks/[SINK_ID]"
   *     "folders/[FOLDER_ID]/sinks/[SINK_ID]"
   *     <p>Example: `"projects/my-project-id/sinks/my-sink-id"`.
   * @param sink Required. The updated sink, whose name is the same identifier that appears as part
   *     of `sink_name`.
   * @param update_mask Optional. Field mask that specifies the fields in `sink` that need an
   *     update. A sink field will be overwritten if, and only if, it is in the update mask. `name`
   *     and output only fields cannot be updated.
   *     <p>An empty updateMask is temporarily treated as using the following mask for backwards
   *     compatibility purposes: destination,filter,includeChildren At some point in the future,
   *     behavior will be removed and specifying an empty updateMask will be an error.
   *     <p>For a detailed `FieldMask` definition, see
   *     https://developers.google.com/protocol-buffers/docs/reference/google.protobuf#google.protobuf.FieldMask
   *     <p>Example: `updateMask=filter`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink updateSink(String sinkName, LogSink sink, FieldMask updateMask) {
    UpdateSinkRequest request =
        UpdateSinkRequest.newBuilder()
            .setSinkName(sinkName)
            .setSink(sink)
            .setUpdateMask(updateMask)
            .build();
    return updateSink(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Updates a sink. This method replaces the following fields in the existing sink with values from
   * the new sink: `destination`, and `filter`.
   *
   * <p>The updated sink might also have a new `writer_identity`; see the `unique_writer_identity`
   * field.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogSink updateSink(UpdateSinkRequest request) {
    return updateSinkCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Updates a sink. This method replaces the following fields in the existing sink with values from
   * the new sink: `destination`, and `filter`.
   *
   * <p>The updated sink might also have a new `writer_identity`; see the `unique_writer_identity`
   * field.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<UpdateSinkRequest, LogSink> updateSinkCallable() {
    return stub.updateSinkCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a sink. If the sink has a unique `writer_identity`, then that service account is also
   * deleted.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   LogSinkName sinkName = LogSinkName.ofProjectSinkName("[PROJECT]", "[SINK]");
   *   configClient.deleteSink(sinkName);
   * }
   * }</pre>
   *
   * @param sink_name Required. The full resource name of the sink to delete, including the parent
   *     resource and the sink identifier:
   *     <p>"projects/[PROJECT_ID]/sinks/[SINK_ID]"
   *     "organizations/[ORGANIZATION_ID]/sinks/[SINK_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/sinks/[SINK_ID]"
   *     "folders/[FOLDER_ID]/sinks/[SINK_ID]"
   *     <p>Example: `"projects/my-project-id/sinks/my-sink-id"`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteSink(LogSinkName sinkName) {
    DeleteSinkRequest request =
        DeleteSinkRequest.newBuilder()
            .setSinkName(Objects.isNull(sinkName) ? null : sinkName.toString())
            .build();
    deleteSink(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a sink. If the sink has a unique `writer_identity`, then that service account is also
   * deleted.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   String sinkName = "sink_name-1391757129";
   *   configClient.deleteSink(sinkName);
   * }
   * }</pre>
   *
   * @param sink_name Required. The full resource name of the sink to delete, including the parent
   *     resource and the sink identifier:
   *     <p>"projects/[PROJECT_ID]/sinks/[SINK_ID]"
   *     "organizations/[ORGANIZATION_ID]/sinks/[SINK_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/sinks/[SINK_ID]"
   *     "folders/[FOLDER_ID]/sinks/[SINK_ID]"
   *     <p>Example: `"projects/my-project-id/sinks/my-sink-id"`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteSink(String sinkName) {
    DeleteSinkRequest request = DeleteSinkRequest.newBuilder().setSinkName(sinkName).build();
    deleteSink(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a sink. If the sink has a unique `writer_identity`, then that service account is also
   * deleted.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteSink(DeleteSinkRequest request) {
    deleteSinkCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a sink. If the sink has a unique `writer_identity`, then that service account is also
   * deleted.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<DeleteSinkRequest, Empty> deleteSinkCallable() {
    return stub.deleteSinkCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists all the exclusions in a parent resource.
   *
   * @param parent Required. The parent resource whose exclusions are to be listed.
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListExclusionsPagedResponse listExclusions(BillingAccountName parent) {
    ListExclusionsRequest request =
        ListExclusionsRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .build();
    return listExclusions(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists all the exclusions in a parent resource.
   *
   * @param parent Required. The parent resource whose exclusions are to be listed.
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListExclusionsPagedResponse listExclusions(FolderName parent) {
    ListExclusionsRequest request =
        ListExclusionsRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .build();
    return listExclusions(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists all the exclusions in a parent resource.
   *
   * @param parent Required. The parent resource whose exclusions are to be listed.
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListExclusionsPagedResponse listExclusions(OrganizationName parent) {
    ListExclusionsRequest request =
        ListExclusionsRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .build();
    return listExclusions(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists all the exclusions in a parent resource.
   *
   * @param parent Required. The parent resource whose exclusions are to be listed.
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListExclusionsPagedResponse listExclusions(ProjectName parent) {
    ListExclusionsRequest request =
        ListExclusionsRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .build();
    return listExclusions(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists all the exclusions in a parent resource.
   *
   * @param parent Required. The parent resource whose exclusions are to be listed.
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListExclusionsPagedResponse listExclusions(String parent) {
    ListExclusionsRequest request = ListExclusionsRequest.newBuilder().setParent(parent).build();
    return listExclusions(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists all the exclusions in a parent resource.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListExclusionsPagedResponse listExclusions(ListExclusionsRequest request) {
    return listExclusionsPagedCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists all the exclusions in a parent resource.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<ListExclusionsRequest, ListExclusionsPagedResponse>
      listExclusionsPagedCallable() {
    return stub.listExclusionsPagedCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists all the exclusions in a parent resource.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<ListExclusionsRequest, ListExclusionsResponse>
      listExclusionsCallable() {
    return stub.listExclusionsCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets the description of an exclusion.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   LogExclusionName name = LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]");
   *   LogExclusion response = configClient.getExclusion(name);
   * }
   * }</pre>
   *
   * @param name Required. The resource name of an existing exclusion:
   *     <p>"projects/[PROJECT_ID]/exclusions/[EXCLUSION_ID]"
   *     "organizations/[ORGANIZATION_ID]/exclusions/[EXCLUSION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/exclusions/[EXCLUSION_ID]"
   *     "folders/[FOLDER_ID]/exclusions/[EXCLUSION_ID]"
   *     <p>Example: `"projects/my-project-id/exclusions/my-exclusion-id"`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogExclusion getExclusion(LogExclusionName name) {
    GetExclusionRequest request =
        GetExclusionRequest.newBuilder()
            .setName(Objects.isNull(name) ? null : name.toString())
            .build();
    return getExclusion(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets the description of an exclusion.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   String name = "name3373707";
   *   LogExclusion response = configClient.getExclusion(name);
   * }
   * }</pre>
   *
   * @param name Required. The resource name of an existing exclusion:
   *     <p>"projects/[PROJECT_ID]/exclusions/[EXCLUSION_ID]"
   *     "organizations/[ORGANIZATION_ID]/exclusions/[EXCLUSION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/exclusions/[EXCLUSION_ID]"
   *     "folders/[FOLDER_ID]/exclusions/[EXCLUSION_ID]"
   *     <p>Example: `"projects/my-project-id/exclusions/my-exclusion-id"`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogExclusion getExclusion(String name) {
    GetExclusionRequest request = GetExclusionRequest.newBuilder().setName(name).build();
    return getExclusion(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets the description of an exclusion.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogExclusion getExclusion(GetExclusionRequest request) {
    return getExclusionCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets the description of an exclusion.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<GetExclusionRequest, LogExclusion> getExclusionCallable() {
    return stub.getExclusionCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a new exclusion in a specified parent resource. Only log entries belonging to that
   * resource can be excluded. You can have up to 10 exclusions in a resource.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   BillingAccountName parent = BillingAccountName.of("[BILLING_ACCOUNT]");
   *   LogExclusion exclusion = LogExclusion.newBuilder().build();
   *   LogExclusion response = configClient.createExclusion(parent, exclusion);
   * }
   * }</pre>
   *
   * @param parent Required. The parent resource in which to create the exclusion:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   *     <p>Examples: `"projects/my-logging-project"`, `"organizations/123456789"`.
   * @param exclusion Required. The new exclusion, whose `name` parameter is an exclusion name that
   *     is not already used in the parent resource.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogExclusion createExclusion(BillingAccountName parent, LogExclusion exclusion) {
    CreateExclusionRequest request =
        CreateExclusionRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .setExclusion(exclusion)
            .build();
    return createExclusion(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a new exclusion in a specified parent resource. Only log entries belonging to that
   * resource can be excluded. You can have up to 10 exclusions in a resource.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   FolderName parent = FolderName.of("[FOLDER]");
   *   LogExclusion exclusion = LogExclusion.newBuilder().build();
   *   LogExclusion response = configClient.createExclusion(parent, exclusion);
   * }
   * }</pre>
   *
   * @param parent Required. The parent resource in which to create the exclusion:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   *     <p>Examples: `"projects/my-logging-project"`, `"organizations/123456789"`.
   * @param exclusion Required. The new exclusion, whose `name` parameter is an exclusion name that
   *     is not already used in the parent resource.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogExclusion createExclusion(FolderName parent, LogExclusion exclusion) {
    CreateExclusionRequest request =
        CreateExclusionRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .setExclusion(exclusion)
            .build();
    return createExclusion(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a new exclusion in a specified parent resource. Only log entries belonging to that
   * resource can be excluded. You can have up to 10 exclusions in a resource.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   OrganizationName parent = OrganizationName.of("[ORGANIZATION]");
   *   LogExclusion exclusion = LogExclusion.newBuilder().build();
   *   LogExclusion response = configClient.createExclusion(parent, exclusion);
   * }
   * }</pre>
   *
   * @param parent Required. The parent resource in which to create the exclusion:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   *     <p>Examples: `"projects/my-logging-project"`, `"organizations/123456789"`.
   * @param exclusion Required. The new exclusion, whose `name` parameter is an exclusion name that
   *     is not already used in the parent resource.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogExclusion createExclusion(OrganizationName parent, LogExclusion exclusion) {
    CreateExclusionRequest request =
        CreateExclusionRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .setExclusion(exclusion)
            .build();
    return createExclusion(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a new exclusion in a specified parent resource. Only log entries belonging to that
   * resource can be excluded. You can have up to 10 exclusions in a resource.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   ProjectName parent = ProjectName.of("[PROJECT]");
   *   LogExclusion exclusion = LogExclusion.newBuilder().build();
   *   LogExclusion response = configClient.createExclusion(parent, exclusion);
   * }
   * }</pre>
   *
   * @param parent Required. The parent resource in which to create the exclusion:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   *     <p>Examples: `"projects/my-logging-project"`, `"organizations/123456789"`.
   * @param exclusion Required. The new exclusion, whose `name` parameter is an exclusion name that
   *     is not already used in the parent resource.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogExclusion createExclusion(ProjectName parent, LogExclusion exclusion) {
    CreateExclusionRequest request =
        CreateExclusionRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .setExclusion(exclusion)
            .build();
    return createExclusion(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a new exclusion in a specified parent resource. Only log entries belonging to that
   * resource can be excluded. You can have up to 10 exclusions in a resource.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   String parent = "parent-995424086";
   *   LogExclusion exclusion = LogExclusion.newBuilder().build();
   *   LogExclusion response = configClient.createExclusion(parent, exclusion);
   * }
   * }</pre>
   *
   * @param parent Required. The parent resource in which to create the exclusion:
   *     <p>"projects/[PROJECT_ID]" "organizations/[ORGANIZATION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]" "folders/[FOLDER_ID]"
   *     <p>Examples: `"projects/my-logging-project"`, `"organizations/123456789"`.
   * @param exclusion Required. The new exclusion, whose `name` parameter is an exclusion name that
   *     is not already used in the parent resource.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogExclusion createExclusion(String parent, LogExclusion exclusion) {
    CreateExclusionRequest request =
        CreateExclusionRequest.newBuilder().setParent(parent).setExclusion(exclusion).build();
    return createExclusion(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a new exclusion in a specified parent resource. Only log entries belonging to that
   * resource can be excluded. You can have up to 10 exclusions in a resource.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogExclusion createExclusion(CreateExclusionRequest request) {
    return createExclusionCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a new exclusion in a specified parent resource. Only log entries belonging to that
   * resource can be excluded. You can have up to 10 exclusions in a resource.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<CreateExclusionRequest, LogExclusion> createExclusionCallable() {
    return stub.createExclusionCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Changes one or more properties of an existing exclusion.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   LogExclusionName name = LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]");
   *   LogExclusion exclusion = LogExclusion.newBuilder().build();
   *   FieldMask updateMask = FieldMask.newBuilder().build();
   *   LogExclusion response = configClient.updateExclusion(name, exclusion, updateMask);
   * }
   * }</pre>
   *
   * @param name Required. The resource name of the exclusion to update:
   *     <p>"projects/[PROJECT_ID]/exclusions/[EXCLUSION_ID]"
   *     "organizations/[ORGANIZATION_ID]/exclusions/[EXCLUSION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/exclusions/[EXCLUSION_ID]"
   *     "folders/[FOLDER_ID]/exclusions/[EXCLUSION_ID]"
   *     <p>Example: `"projects/my-project-id/exclusions/my-exclusion-id"`.
   * @param exclusion Required. New values for the existing exclusion. Only the fields specified in
   *     `update_mask` are relevant.
   * @param update_mask Required. A non-empty list of fields to change in the existing exclusion.
   *     New values for the fields are taken from the corresponding fields in the
   *     [LogExclusion][google.logging.v2.LogExclusion] included in this request. Fields not
   *     mentioned in `update_mask` are not changed and are ignored in the request.
   *     <p>For example, to change the filter and description of an exclusion, specify an
   *     `update_mask` of `"filter,description"`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogExclusion updateExclusion(
      LogExclusionName name, LogExclusion exclusion, FieldMask updateMask) {
    UpdateExclusionRequest request =
        UpdateExclusionRequest.newBuilder()
            .setName(Objects.isNull(name) ? null : name.toString())
            .setExclusion(exclusion)
            .setUpdateMask(updateMask)
            .build();
    return updateExclusion(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Changes one or more properties of an existing exclusion.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   String name = "name3373707";
   *   LogExclusion exclusion = LogExclusion.newBuilder().build();
   *   FieldMask updateMask = FieldMask.newBuilder().build();
   *   LogExclusion response = configClient.updateExclusion(name, exclusion, updateMask);
   * }
   * }</pre>
   *
   * @param name Required. The resource name of the exclusion to update:
   *     <p>"projects/[PROJECT_ID]/exclusions/[EXCLUSION_ID]"
   *     "organizations/[ORGANIZATION_ID]/exclusions/[EXCLUSION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/exclusions/[EXCLUSION_ID]"
   *     "folders/[FOLDER_ID]/exclusions/[EXCLUSION_ID]"
   *     <p>Example: `"projects/my-project-id/exclusions/my-exclusion-id"`.
   * @param exclusion Required. New values for the existing exclusion. Only the fields specified in
   *     `update_mask` are relevant.
   * @param update_mask Required. A non-empty list of fields to change in the existing exclusion.
   *     New values for the fields are taken from the corresponding fields in the
   *     [LogExclusion][google.logging.v2.LogExclusion] included in this request. Fields not
   *     mentioned in `update_mask` are not changed and are ignored in the request.
   *     <p>For example, to change the filter and description of an exclusion, specify an
   *     `update_mask` of `"filter,description"`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogExclusion updateExclusion(
      String name, LogExclusion exclusion, FieldMask updateMask) {
    UpdateExclusionRequest request =
        UpdateExclusionRequest.newBuilder()
            .setName(name)
            .setExclusion(exclusion)
            .setUpdateMask(updateMask)
            .build();
    return updateExclusion(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Changes one or more properties of an existing exclusion.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogExclusion updateExclusion(UpdateExclusionRequest request) {
    return updateExclusionCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Changes one or more properties of an existing exclusion.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<UpdateExclusionRequest, LogExclusion> updateExclusionCallable() {
    return stub.updateExclusionCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes an exclusion.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   LogExclusionName name = LogExclusionName.ofProjectExclusionName("[PROJECT]", "[EXCLUSION]");
   *   configClient.deleteExclusion(name);
   * }
   * }</pre>
   *
   * @param name Required. The resource name of an existing exclusion to delete:
   *     <p>"projects/[PROJECT_ID]/exclusions/[EXCLUSION_ID]"
   *     "organizations/[ORGANIZATION_ID]/exclusions/[EXCLUSION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/exclusions/[EXCLUSION_ID]"
   *     "folders/[FOLDER_ID]/exclusions/[EXCLUSION_ID]"
   *     <p>Example: `"projects/my-project-id/exclusions/my-exclusion-id"`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteExclusion(LogExclusionName name) {
    DeleteExclusionRequest request =
        DeleteExclusionRequest.newBuilder()
            .setName(Objects.isNull(name) ? null : name.toString())
            .build();
    deleteExclusion(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes an exclusion.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (ConfigClient configClient = ConfigClient.create()) {
   *   String name = "name3373707";
   *   configClient.deleteExclusion(name);
   * }
   * }</pre>
   *
   * @param name Required. The resource name of an existing exclusion to delete:
   *     <p>"projects/[PROJECT_ID]/exclusions/[EXCLUSION_ID]"
   *     "organizations/[ORGANIZATION_ID]/exclusions/[EXCLUSION_ID]"
   *     "billingAccounts/[BILLING_ACCOUNT_ID]/exclusions/[EXCLUSION_ID]"
   *     "folders/[FOLDER_ID]/exclusions/[EXCLUSION_ID]"
   *     <p>Example: `"projects/my-project-id/exclusions/my-exclusion-id"`.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteExclusion(String name) {
    DeleteExclusionRequest request = DeleteExclusionRequest.newBuilder().setName(name).build();
    deleteExclusion(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes an exclusion.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final void deleteExclusion(DeleteExclusionRequest request) {
    deleteExclusionCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes an exclusion.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<DeleteExclusionRequest, Empty> deleteExclusionCallable() {
    return stub.deleteExclusionCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets the Logs Router CMEK settings for the given resource.
   *
   * <p>Note: CMEK for the Logs Router can currently only be configured for GCP organizations. Once
   * configured, it applies to all projects and folders in the GCP organization.
   *
   * <p>See [Enabling CMEK for Logs
   * Router](https://cloud.google.com/logging/docs/routing/managed-encryption) for more information.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final CmekSettings getCmekSettings(GetCmekSettingsRequest request) {
    return getCmekSettingsCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets the Logs Router CMEK settings for the given resource.
   *
   * <p>Note: CMEK for the Logs Router can currently only be configured for GCP organizations. Once
   * configured, it applies to all projects and folders in the GCP organization.
   *
   * <p>See [Enabling CMEK for Logs
   * Router](https://cloud.google.com/logging/docs/routing/managed-encryption) for more information.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<GetCmekSettingsRequest, CmekSettings> getCmekSettingsCallable() {
    return stub.getCmekSettingsCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Updates the Logs Router CMEK settings for the given resource.
   *
   * <p>Note: CMEK for the Logs Router can currently only be configured for GCP organizations. Once
   * configured, it applies to all projects and folders in the GCP organization.
   *
   * <p>[UpdateCmekSettings][google.logging.v2.ConfigServiceV2.UpdateCmekSettings] will fail if 1)
   * `kms_key_name` is invalid, or 2) the associated service account does not have the required
   * `roles/cloudkms.cryptoKeyEncrypterDecrypter` role assigned for the key, or 3) access to the key
   * is disabled.
   *
   * <p>See [Enabling CMEK for Logs
   * Router](https://cloud.google.com/logging/docs/routing/managed-encryption) for more information.
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final CmekSettings updateCmekSettings(UpdateCmekSettingsRequest request) {
    return updateCmekSettingsCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Updates the Logs Router CMEK settings for the given resource.
   *
   * <p>Note: CMEK for the Logs Router can currently only be configured for GCP organizations. Once
   * configured, it applies to all projects and folders in the GCP organization.
   *
   * <p>[UpdateCmekSettings][google.logging.v2.ConfigServiceV2.UpdateCmekSettings] will fail if 1)
   * `kms_key_name` is invalid, or 2) the associated service account does not have the required
   * `roles/cloudkms.cryptoKeyEncrypterDecrypter` role assigned for the key, or 3) access to the key
   * is disabled.
   *
   * <p>See [Enabling CMEK for Logs
   * Router](https://cloud.google.com/logging/docs/routing/managed-encryption) for more information.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<UpdateCmekSettingsRequest, CmekSettings> updateCmekSettingsCallable() {
    return stub.updateCmekSettingsCallable();
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

  public static class ListBucketsPagedResponse
      extends AbstractPagedListResponse<
          ListBucketsRequest,
          ListBucketsResponse,
          LogBucket,
          ListBucketsPage,
          ListBucketsFixedSizeCollection> {

    public static ApiFuture<ListBucketsPagedResponse> createAsync(
        PageContext<ListBucketsRequest, ListBucketsResponse, LogBucket> context,
        ApiFuture<ListBucketsResponse> futureResponse) {
      ApiFuture<ListBucketsPage> futurePage =
          ListBucketsPage.createEmptyPage().createPageAsync(context, futureResponse);
      return ApiFutures.transform(
          futurePage,
          new ApiFunction<ListBucketsPage, ListBucketsPagedResponse>() {
            @Override
            public ListBucketsPagedResponse apply(ListBucketsPage input) {
              return new ListBucketsPagedResponse(input);
            }
          },
          MoreExecutors.directExecutor());
    }

    private ListBucketsPagedResponse(ListBucketsPage page) {
      super(page, ListBucketsFixedSizeCollection.createEmptyCollection());
    }
  }

  public static class ListBucketsPage
      extends AbstractPage<ListBucketsRequest, ListBucketsResponse, LogBucket, ListBucketsPage> {

    private ListBucketsPage(
        PageContext<ListBucketsRequest, ListBucketsResponse, LogBucket> context,
        ListBucketsResponse response) {
      super(context, response);
    }

    private static ListBucketsPage createEmptyPage() {
      return new ListBucketsPage(null, null);
    }

    @Override
    protected ListBucketsPage createPage(
        PageContext<ListBucketsRequest, ListBucketsResponse, LogBucket> context,
        ListBucketsResponse response) {
      return new ListBucketsPage(context, response);
    }

    @Override
    public ApiFuture<ListBucketsPage> createPageAsync(
        PageContext<ListBucketsRequest, ListBucketsResponse, LogBucket> context,
        ApiFuture<ListBucketsResponse> futureResponse) {
      return super.createPageAsync(context, futureResponse);
    }
  }

  public static class ListBucketsFixedSizeCollection
      extends AbstractFixedSizeCollection<
          ListBucketsRequest,
          ListBucketsResponse,
          LogBucket,
          ListBucketsPage,
          ListBucketsFixedSizeCollection> {

    private ListBucketsFixedSizeCollection(List<ListBucketsPage> pages, int collectionSize) {
      super(pages, collectionSize);
    }

    private static ListBucketsFixedSizeCollection createEmptyCollection() {
      return new ListBucketsFixedSizeCollection(null, 0);
    }

    @Override
    protected ListBucketsFixedSizeCollection createCollection(
        List<ListBucketsPage> pages, int collectionSize) {
      return new ListBucketsFixedSizeCollection(pages, collectionSize);
    }
  }

  public static class ListSinksPagedResponse
      extends AbstractPagedListResponse<
          ListSinksRequest,
          ListSinksResponse,
          LogSink,
          ListSinksPage,
          ListSinksFixedSizeCollection> {

    public static ApiFuture<ListSinksPagedResponse> createAsync(
        PageContext<ListSinksRequest, ListSinksResponse, LogSink> context,
        ApiFuture<ListSinksResponse> futureResponse) {
      ApiFuture<ListSinksPage> futurePage =
          ListSinksPage.createEmptyPage().createPageAsync(context, futureResponse);
      return ApiFutures.transform(
          futurePage,
          new ApiFunction<ListSinksPage, ListSinksPagedResponse>() {
            @Override
            public ListSinksPagedResponse apply(ListSinksPage input) {
              return new ListSinksPagedResponse(input);
            }
          },
          MoreExecutors.directExecutor());
    }

    private ListSinksPagedResponse(ListSinksPage page) {
      super(page, ListSinksFixedSizeCollection.createEmptyCollection());
    }
  }

  public static class ListSinksPage
      extends AbstractPage<ListSinksRequest, ListSinksResponse, LogSink, ListSinksPage> {

    private ListSinksPage(
        PageContext<ListSinksRequest, ListSinksResponse, LogSink> context,
        ListSinksResponse response) {
      super(context, response);
    }

    private static ListSinksPage createEmptyPage() {
      return new ListSinksPage(null, null);
    }

    @Override
    protected ListSinksPage createPage(
        PageContext<ListSinksRequest, ListSinksResponse, LogSink> context,
        ListSinksResponse response) {
      return new ListSinksPage(context, response);
    }

    @Override
    public ApiFuture<ListSinksPage> createPageAsync(
        PageContext<ListSinksRequest, ListSinksResponse, LogSink> context,
        ApiFuture<ListSinksResponse> futureResponse) {
      return super.createPageAsync(context, futureResponse);
    }
  }

  public static class ListSinksFixedSizeCollection
      extends AbstractFixedSizeCollection<
          ListSinksRequest,
          ListSinksResponse,
          LogSink,
          ListSinksPage,
          ListSinksFixedSizeCollection> {

    private ListSinksFixedSizeCollection(List<ListSinksPage> pages, int collectionSize) {
      super(pages, collectionSize);
    }

    private static ListSinksFixedSizeCollection createEmptyCollection() {
      return new ListSinksFixedSizeCollection(null, 0);
    }

    @Override
    protected ListSinksFixedSizeCollection createCollection(
        List<ListSinksPage> pages, int collectionSize) {
      return new ListSinksFixedSizeCollection(pages, collectionSize);
    }
  }

  public static class ListExclusionsPagedResponse
      extends AbstractPagedListResponse<
          ListExclusionsRequest,
          ListExclusionsResponse,
          LogExclusion,
          ListExclusionsPage,
          ListExclusionsFixedSizeCollection> {

    public static ApiFuture<ListExclusionsPagedResponse> createAsync(
        PageContext<ListExclusionsRequest, ListExclusionsResponse, LogExclusion> context,
        ApiFuture<ListExclusionsResponse> futureResponse) {
      ApiFuture<ListExclusionsPage> futurePage =
          ListExclusionsPage.createEmptyPage().createPageAsync(context, futureResponse);
      return ApiFutures.transform(
          futurePage,
          new ApiFunction<ListExclusionsPage, ListExclusionsPagedResponse>() {
            @Override
            public ListExclusionsPagedResponse apply(ListExclusionsPage input) {
              return new ListExclusionsPagedResponse(input);
            }
          },
          MoreExecutors.directExecutor());
    }

    private ListExclusionsPagedResponse(ListExclusionsPage page) {
      super(page, ListExclusionsFixedSizeCollection.createEmptyCollection());
    }
  }

  public static class ListExclusionsPage
      extends AbstractPage<
          ListExclusionsRequest, ListExclusionsResponse, LogExclusion, ListExclusionsPage> {

    private ListExclusionsPage(
        PageContext<ListExclusionsRequest, ListExclusionsResponse, LogExclusion> context,
        ListExclusionsResponse response) {
      super(context, response);
    }

    private static ListExclusionsPage createEmptyPage() {
      return new ListExclusionsPage(null, null);
    }

    @Override
    protected ListExclusionsPage createPage(
        PageContext<ListExclusionsRequest, ListExclusionsResponse, LogExclusion> context,
        ListExclusionsResponse response) {
      return new ListExclusionsPage(context, response);
    }

    @Override
    public ApiFuture<ListExclusionsPage> createPageAsync(
        PageContext<ListExclusionsRequest, ListExclusionsResponse, LogExclusion> context,
        ApiFuture<ListExclusionsResponse> futureResponse) {
      return super.createPageAsync(context, futureResponse);
    }
  }

  public static class ListExclusionsFixedSizeCollection
      extends AbstractFixedSizeCollection<
          ListExclusionsRequest,
          ListExclusionsResponse,
          LogExclusion,
          ListExclusionsPage,
          ListExclusionsFixedSizeCollection> {

    private ListExclusionsFixedSizeCollection(List<ListExclusionsPage> pages, int collectionSize) {
      super(pages, collectionSize);
    }

    private static ListExclusionsFixedSizeCollection createEmptyCollection() {
      return new ListExclusionsFixedSizeCollection(null, 0);
    }

    @Override
    protected ListExclusionsFixedSizeCollection createCollection(
        List<ListExclusionsPage> pages, int collectionSize) {
      return new ListExclusionsFixedSizeCollection(pages, collectionSize);
    }
  }
}
