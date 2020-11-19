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

/**
 * The interfaces provided are listed below, along with usage samples.
 *
 * <p>======================= LoggingServiceV2Client =======================
 *
 * <p>Service Description: Service for ingesting and querying logs.
 *
 * <p>Sample for LoggingServiceV2Client:
 *
 * <pre>{@code
 * try (LoggingServiceV2Client loggingServiceV2Client = LoggingServiceV2Client.create()) {
 *   LogName log_name = LogName.ofProjectLogName("[PROJECT]", "[LOG]");
 *   Empty response = loggingServiceV2Client.DeleteLog(log_name);
 * }
 * }</pre>
 *
 * <p>======================= ConfigServiceV2Client =======================
 *
 * <p>Service Description: Service for configuring sinks used to route log entries.
 *
 * <p>Sample for ConfigServiceV2Client:
 *
 * <pre>{@code
 * try (ConfigServiceV2Client configServiceV2Client = ConfigServiceV2Client.create()) {
 *   GetBucketRequest request = GetBucketRequest.newBuilder().build();
 *   LogBucket response = configServiceV2Client.GetBucket(request);
 * }
 * }</pre>
 *
 * <p>======================= MetricsServiceV2Client =======================
 *
 * <p>Service Description: Service for configuring logs-based metrics.
 *
 * <p>Sample for MetricsServiceV2Client:
 *
 * <pre>{@code
 * try (MetricsServiceV2Client metricsServiceV2Client = MetricsServiceV2Client.create()) {
 *   LogMetricName metric_name = LogMetricName.of("[PROJECT]", "[METRIC]");
 *   LogMetric response = metricsServiceV2Client.GetLogMetric(metric_name);
 * }
 * }</pre>
 */
@Generated("by gapic-generator-java")
package com.google.logging.v2;

import javax.annotation.Generated;
