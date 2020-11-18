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

package com.google.logging.v2;

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
import com.google.common.util.concurrent.MoreExecutors;
import com.google.logging.v2.stub.MetricsServiceV2Stub;
import com.google.logging.v2.stub.MetricsServiceV2StubSettings;
import com.google.protobuf.Empty;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;

// AUTO-GENERATED DOCUMENTATION AND CLASS.
/**
 * Service Description: Service for configuring logs-based metrics.
 *
 * <p>This class provides the ability to make remote calls to the backing service through method
 * calls that map to API methods. Sample code to get started:
 *
 * <p>Note: close() needs to be called on the metricsServiceV2Client object to clean up resources
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
 * <p>This class can be customized by passing in a custom instance of MetricsServiceV2Settings to
 * create(). For example:
 *
 * <p>To customize credentials:
 *
 * <pre>{@code
 * MetricsServiceV2Settings metricsServiceV2Settings =
 *     MetricsServiceV2Settings.newBuilder()
 *         .setCredentialsProvider(FixedCredentialsProvider.create("myCredentials"))
 *         .build();
 * MetricsServiceV2Client metricsServiceV2Client =
 *     MetricsServiceV2Client.create(metricsServiceV2Settings);
 * }</pre>
 *
 * <p>To customize the endpoint:
 *
 * <pre>{@code
 * MetricsServiceV2Settings metricsServiceV2Settings =
 *     MetricsServiceV2Settings.newBuilder().setEndpoint("myEndpoint").build();
 * MetricsServiceV2Client metricsServiceV2Client =
 *     MetricsServiceV2Client.create(metricsServiceV2Settings);
 * }</pre>
 */
@BetaApi
@Generated("by gapic-generator")
public class MetricsServiceV2Client implements BackgroundResource {
  private final MetricsServiceV2Settings settings;
  private final MetricsServiceV2Stub stub;

  /** Constructs an instance of MetricsServiceV2Client with default settings. */
  public static final MetricsServiceV2Client create() throws IOException {
    return create(MetricsServiceV2Settings.newBuilder().build());
  }

  /**
   * Constructs an instance of MetricsServiceV2Client, using the given settings. The channels are
   * created based on the settings passed in, or defaults for any settings that are not set.
   */
  public static final MetricsServiceV2Client create(MetricsServiceV2Settings settings)
      throws IOException {
    return new MetricsServiceV2Client(settings);
  }

  /**
   * Constructs an instance of MetricsServiceV2Client, using the given stub for making calls. This
   * is for advanced usage - prefer using create(MetricsServiceV2Settings).
   */
  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  public static final MetricsServiceV2Client create(MetricsServiceV2Stub stub) {
    return new MetricsServiceV2Client(stub);
  }

  /**
   * Constructs an instance of MetricsServiceV2Client, using the given settings. This is protected
   * so that it is easy to make a subclass, but otherwise, the static factory methods should be
   * preferred.
   */
  protected MetricsServiceV2Client(MetricsServiceV2Settings settings) throws IOException {
    this.settings = settings;
    this.stub = ((MetricsServiceV2StubSettings) settings.getStubSettings()).createStub();
  }

  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  protected MetricsServiceV2Client(MetricsServiceV2Stub stub) {
    this.settings = null;
    this.stub = stub;
  }

  public final MetricsServiceV2Settings getSettings() {
    return settings;
  }

  @BetaApi("A restructuring of stub classes is planned, so this may break in the future")
  public MetricsServiceV2Stub getStub() {
    return stub;
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists logs-based metrics.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   String parent = "parent-995424086";
   *   for (MetricsServiceV2Client metricsServiceV2Client :
   *       metricsServiceV2Client.ListLogMetrics(parent).iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   *
   * @param parent Required. The name of the project containing the metrics:
   *     <p>"projects/[PROJECT_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListLogMetricsPagedResponse listLogMetrics(ProjectName parent) {
    ListLogMetricsRequest request =
        ListLogMetricsRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .build();
    return listLogMetrics(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists logs-based metrics.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   String parent = "parent-995424086";
   *   for (MetricsServiceV2Client metricsServiceV2Client :
   *       metricsServiceV2Client.ListLogMetrics(parent).iterateAll()) {
   *     // doThingsWith(element);
   *   }
   * }
   * }</pre>
   *
   * @param parent Required. The name of the project containing the metrics:
   *     <p>"projects/[PROJECT_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListLogMetricsPagedResponse listLogMetrics(String parent) {
    ListLogMetricsRequest request = ListLogMetricsRequest.newBuilder().setParent(parent).build();
    return listLogMetrics(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists logs-based metrics.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   // Note: Not Implement yet. Holder for unary paged rpc method sample code.
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final ListLogMetricsPagedResponse listLogMetrics(ListLogMetricsRequest request) {
    return listLogMetricsPagedCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists logs-based metrics.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<ListLogMetricsRequest, ListLogMetricsPagedResponse>
      listLogMetricsPagedCallable() {
    return stub.listLogMetricsPagedCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Lists logs-based metrics.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<ListLogMetricsRequest, ListLogMetricsResponse>
      listLogMetricsCallable() {
    return stub.listLogMetricsCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a logs-based metric.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   LogMetricName metric_name = LogMetricName.of("[PROJECT]", "[METRIC]");
   *   LogMetric response = metricsServiceV2Client.GetLogMetric(metric_name);
   * }
   * }</pre>
   *
   * @param metric_name Required. The resource name of the desired metric:
   *     <p>"projects/[PROJECT_ID]/metrics/[METRIC_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogMetric getLogMetric(LogMetricName metricName) {
    GetLogMetricRequest request =
        GetLogMetricRequest.newBuilder()
            .setMetricName(Objects.isNull(metricName) ? null : metricName.toString())
            .build();
    return getLogMetric(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a logs-based metric.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   String metric_name = "metric_name-1737602118";
   *   LogMetric response = metricsServiceV2Client.GetLogMetric(metric_name);
   * }
   * }</pre>
   *
   * @param metric_name Required. The resource name of the desired metric:
   *     <p>"projects/[PROJECT_ID]/metrics/[METRIC_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogMetric getLogMetric(String metricName) {
    GetLogMetricRequest request =
        GetLogMetricRequest.newBuilder().setMetricName(metricName).build();
    return getLogMetric(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a logs-based metric.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   // Note: Not Implement yet. Holder for unary default rpc method sample code.
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogMetric getLogMetric(GetLogMetricRequest request) {
    return getLogMetricCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Gets a logs-based metric.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<GetLogMetricRequest, LogMetric> getLogMetricCallable() {
    return stub.getLogMetricCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a logs-based metric.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   ProjectName parent = ProjectName.of("[PROJECT]");
   *   LogMetric metric = LogMetric.newBuilder().build();
   *   LogMetric response = metricsServiceV2Client.CreateLogMetric(parent, metric);
   * }
   * }</pre>
   *
   * @param parent Required. The resource name of the project in which to create the metric:
   *     <p>"projects/[PROJECT_ID]"
   *     <p>The new metric must be provided in the request.
   * @param metric Required. The new logs-based metric, which must not have an identifier that
   *     already exists.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogMetric createLogMetric(ProjectName parent, LogMetric metric) {
    CreateLogMetricRequest request =
        CreateLogMetricRequest.newBuilder()
            .setParent(Objects.isNull(parent) ? null : parent.toString())
            .setMetric(metric)
            .build();
    return createLogMetric(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a logs-based metric.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   String parent = "parent-995424086";
   *   LogMetric metric = LogMetric.newBuilder().build();
   *   LogMetric response = metricsServiceV2Client.CreateLogMetric(parent, metric);
   * }
   * }</pre>
   *
   * @param parent Required. The resource name of the project in which to create the metric:
   *     <p>"projects/[PROJECT_ID]"
   *     <p>The new metric must be provided in the request.
   * @param metric Required. The new logs-based metric, which must not have an identifier that
   *     already exists.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogMetric createLogMetric(String parent, LogMetric metric) {
    CreateLogMetricRequest request =
        CreateLogMetricRequest.newBuilder().setParent(parent).setMetric(metric).build();
    return createLogMetric(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a logs-based metric.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   // Note: Not Implement yet. Holder for unary default rpc method sample code.
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogMetric createLogMetric(CreateLogMetricRequest request) {
    return createLogMetricCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates a logs-based metric.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<CreateLogMetricRequest, LogMetric> createLogMetricCallable() {
    return stub.createLogMetricCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates or updates a logs-based metric.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   LogMetricName metric_name = LogMetricName.of("[PROJECT]", "[METRIC]");
   *   LogMetric metric = LogMetric.newBuilder().build();
   *   LogMetric response = metricsServiceV2Client.UpdateLogMetric(metric_name, metric);
   * }
   * }</pre>
   *
   * @param metric_name Required. The resource name of the metric to update:
   *     <p>"projects/[PROJECT_ID]/metrics/[METRIC_ID]"
   *     <p>The updated metric must be provided in the request and it's `name` field must be the
   *     same as `[METRIC_ID]` If the metric does not exist in `[PROJECT_ID]`, then a new metric is
   *     created.
   * @param metric Required. The updated metric.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogMetric updateLogMetric(LogMetricName metricName, LogMetric metric) {
    UpdateLogMetricRequest request =
        UpdateLogMetricRequest.newBuilder()
            .setMetricName(Objects.isNull(metricName) ? null : metricName.toString())
            .setMetric(metric)
            .build();
    return updateLogMetric(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates or updates a logs-based metric.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   String metric_name = "metric_name-1737602118";
   *   LogMetric metric = LogMetric.newBuilder().build();
   *   LogMetric response = metricsServiceV2Client.UpdateLogMetric(metric_name, metric);
   * }
   * }</pre>
   *
   * @param metric_name Required. The resource name of the metric to update:
   *     <p>"projects/[PROJECT_ID]/metrics/[METRIC_ID]"
   *     <p>The updated metric must be provided in the request and it's `name` field must be the
   *     same as `[METRIC_ID]` If the metric does not exist in `[PROJECT_ID]`, then a new metric is
   *     created.
   * @param metric Required. The updated metric.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogMetric updateLogMetric(String metricName, LogMetric metric) {
    UpdateLogMetricRequest request =
        UpdateLogMetricRequest.newBuilder().setMetricName(metricName).setMetric(metric).build();
    return updateLogMetric(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates or updates a logs-based metric.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   // Note: Not Implement yet. Holder for unary default rpc method sample code.
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final LogMetric updateLogMetric(UpdateLogMetricRequest request) {
    return updateLogMetricCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Creates or updates a logs-based metric.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<UpdateLogMetricRequest, LogMetric> updateLogMetricCallable() {
    return stub.updateLogMetricCallable();
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a logs-based metric.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   LogMetricName metric_name = LogMetricName.of("[PROJECT]", "[METRIC]");
   *   Empty response = metricsServiceV2Client.DeleteLogMetric(metric_name);
   * }
   * }</pre>
   *
   * @param metric_name Required. The resource name of the metric to delete:
   *     <p>"projects/[PROJECT_ID]/metrics/[METRIC_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Empty deleteLogMetric(LogMetricName metricName) {
    DeleteLogMetricRequest request =
        DeleteLogMetricRequest.newBuilder()
            .setMetricName(Objects.isNull(metricName) ? null : metricName.toString())
            .build();
    return deleteLogMetric(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a logs-based metric.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   String metric_name = "metric_name-1737602118";
   *   Empty response = metricsServiceV2Client.DeleteLogMetric(metric_name);
   * }
   * }</pre>
   *
   * @param metric_name Required. The resource name of the metric to delete:
   *     <p>"projects/[PROJECT_ID]/metrics/[METRIC_ID]"
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Empty deleteLogMetric(String metricName) {
    DeleteLogMetricRequest request =
        DeleteLogMetricRequest.newBuilder().setMetricName(metricName).build();
    return deleteLogMetric(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a logs-based metric.
   *
   * <p>Sample code:
   *
   * <pre>{@code
   * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
   *   // Note: Not Implement yet. Holder for unary default rpc method sample code.
   * }
   * }</pre>
   *
   * @param request The request object containing all of the parameters for the API call.
   * @throws com.google.api.gax.rpc.ApiException if the remote call fails
   */
  public final Empty deleteLogMetric(DeleteLogMetricRequest request) {
    return deleteLogMetricCallable().call(request);
  }

  // AUTO-GENERATED DOCUMENTATION AND METHOD.
  /**
   * Deletes a logs-based metric.
   *
   * <p>Sample code:
   */
  public final UnaryCallable<DeleteLogMetricRequest, Empty> deleteLogMetricCallable() {
    return stub.deleteLogMetricCallable();
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

  public static class ListLogMetricsPagedResponse
      extends AbstractPagedListResponse<
          ListLogMetricsRequest,
          ListLogMetricsResponse,
          LogMetric,
          ListLogMetricsPage,
          ListLogMetricsFixedSizeCollection> {

    public static ApiFuture<ListLogMetricsPagedResponse> createAsync(
        PageContext<ListLogMetricsRequest, ListLogMetricsResponse, LogMetric> context,
        ApiFuture<ListLogMetricsResponse> futureResponse) {
      ApiFuture<ListLogMetricsPage> futurePage =
          ListLogMetricsPage.createEmptyPage().createPageAsync(context, futureResponse);
      return ApiFutures.transform(
          futurePage,
          new ApiFunction<ListLogMetricsPage, ListLogMetricsPagedResponse>() {
            @Override
            public ListLogMetricsPagedResponse apply(ListLogMetricsPage input) {
              return new ListLogMetricsPagedResponse(input);
            }
          },
          MoreExecutors.directExecutor());
    }

    private ListLogMetricsPagedResponse(ListLogMetricsPage page) {
      super(page, ListLogMetricsFixedSizeCollection.createEmptyCollection());
    }
  }

  public static class ListLogMetricsPage
      extends AbstractPage<
          ListLogMetricsRequest, ListLogMetricsResponse, LogMetric, ListLogMetricsPage> {

    private ListLogMetricsPage(
        PageContext<ListLogMetricsRequest, ListLogMetricsResponse, LogMetric> context,
        ListLogMetricsResponse response) {
      super(context, response);
    }

    private static ListLogMetricsPage createEmptyPage() {
      return new ListLogMetricsPage(null, null);
    }

    @Override
    protected ListLogMetricsPage createPage(
        PageContext<ListLogMetricsRequest, ListLogMetricsResponse, LogMetric> context,
        ListLogMetricsResponse response) {
      return new ListLogMetricsPage(context, response);
    }

    @Override
    public ApiFuture<ListLogMetricsPage> createPageAsync(
        PageContext<ListLogMetricsRequest, ListLogMetricsResponse, LogMetric> context,
        ApiFuture<ListLogMetricsResponse> futureResponse) {
      return super.createPageAsync(context, futureResponse);
    }
  }

  public static class ListLogMetricsFixedSizeCollection
      extends AbstractFixedSizeCollection<
          ListLogMetricsRequest,
          ListLogMetricsResponse,
          LogMetric,
          ListLogMetricsPage,
          ListLogMetricsFixedSizeCollection> {

    private ListLogMetricsFixedSizeCollection(List<ListLogMetricsPage> pages, int collectionSize) {
      super(pages, collectionSize);
    }

    private static ListLogMetricsFixedSizeCollection createEmptyCollection() {
      return new ListLogMetricsFixedSizeCollection(null, 0);
    }

    @Override
    protected ListLogMetricsFixedSizeCollection createCollection(
        List<ListLogMetricsPage> pages, int collectionSize) {
      return new ListLogMetricsFixedSizeCollection(pages, collectionSize);
    }
  }
}
