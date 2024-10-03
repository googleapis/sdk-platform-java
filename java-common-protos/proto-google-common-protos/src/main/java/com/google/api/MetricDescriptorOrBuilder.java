/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/api/metric.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

public interface MetricDescriptorOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.MetricDescriptor)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The resource name of the metric descriptor.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The name.
   */
  java.lang.String getName();
  /**
   *
   *
   * <pre>
   * The resource name of the metric descriptor.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString getNameBytes();

  /**
   *
   *
   * <pre>
   * The metric type, including its DNS name prefix. The type is not
   * URL-encoded. All user-defined metric types have the DNS name
   * `custom.googleapis.com` or `external.googleapis.com`. Metric types should
   * use a natural hierarchical grouping. For example:
   *
   *     "custom.googleapis.com/invoice/paid/amount"
   *     "external.googleapis.com/prometheus/up"
   *     "appengine.googleapis.com/http/server/response_latencies"
   * </pre>
   *
   * <code>string type = 8;</code>
   *
   * @return The type.
   */
  java.lang.String getType();
  /**
   *
   *
   * <pre>
   * The metric type, including its DNS name prefix. The type is not
   * URL-encoded. All user-defined metric types have the DNS name
   * `custom.googleapis.com` or `external.googleapis.com`. Metric types should
   * use a natural hierarchical grouping. For example:
   *
   *     "custom.googleapis.com/invoice/paid/amount"
   *     "external.googleapis.com/prometheus/up"
   *     "appengine.googleapis.com/http/server/response_latencies"
   * </pre>
   *
   * <code>string type = 8;</code>
   *
   * @return The bytes for type.
   */
  com.google.protobuf.ByteString getTypeBytes();

  /**
   *
   *
   * <pre>
   * The set of labels that can be used to describe a specific
   * instance of this metric type. For example, the
   * `appengine.googleapis.com/http/server/response_latencies` metric
   * type has a label for the HTTP response code, `response_code`, so
   * you can look at latencies for successful responses or just
   * for responses that failed.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 2;</code>
   */
  java.util.List<com.google.api.LabelDescriptor> getLabelsList();
  /**
   *
   *
   * <pre>
   * The set of labels that can be used to describe a specific
   * instance of this metric type. For example, the
   * `appengine.googleapis.com/http/server/response_latencies` metric
   * type has a label for the HTTP response code, `response_code`, so
   * you can look at latencies for successful responses or just
   * for responses that failed.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 2;</code>
   */
  com.google.api.LabelDescriptor getLabels(int index);
  /**
   *
   *
   * <pre>
   * The set of labels that can be used to describe a specific
   * instance of this metric type. For example, the
   * `appengine.googleapis.com/http/server/response_latencies` metric
   * type has a label for the HTTP response code, `response_code`, so
   * you can look at latencies for successful responses or just
   * for responses that failed.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 2;</code>
   */
  int getLabelsCount();
  /**
   *
   *
   * <pre>
   * The set of labels that can be used to describe a specific
   * instance of this metric type. For example, the
   * `appengine.googleapis.com/http/server/response_latencies` metric
   * type has a label for the HTTP response code, `response_code`, so
   * you can look at latencies for successful responses or just
   * for responses that failed.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 2;</code>
   */
  java.util.List<? extends com.google.api.LabelDescriptorOrBuilder> getLabelsOrBuilderList();
  /**
   *
   *
   * <pre>
   * The set of labels that can be used to describe a specific
   * instance of this metric type. For example, the
   * `appengine.googleapis.com/http/server/response_latencies` metric
   * type has a label for the HTTP response code, `response_code`, so
   * you can look at latencies for successful responses or just
   * for responses that failed.
   * </pre>
   *
   * <code>repeated .google.api.LabelDescriptor labels = 2;</code>
   */
  com.google.api.LabelDescriptorOrBuilder getLabelsOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * Whether the metric records instantaneous values, changes to a value, etc.
   * Some combinations of `metric_kind` and `value_type` might not be supported.
   * </pre>
   *
   * <code>.google.api.MetricDescriptor.MetricKind metric_kind = 3;</code>
   *
   * @return The enum numeric value on the wire for metricKind.
   */
  int getMetricKindValue();
  /**
   *
   *
   * <pre>
   * Whether the metric records instantaneous values, changes to a value, etc.
   * Some combinations of `metric_kind` and `value_type` might not be supported.
   * </pre>
   *
   * <code>.google.api.MetricDescriptor.MetricKind metric_kind = 3;</code>
   *
   * @return The metricKind.
   */
  com.google.api.MetricDescriptor.MetricKind getMetricKind();

  /**
   *
   *
   * <pre>
   * Whether the measurement is an integer, a floating-point number, etc.
   * Some combinations of `metric_kind` and `value_type` might not be supported.
   * </pre>
   *
   * <code>.google.api.MetricDescriptor.ValueType value_type = 4;</code>
   *
   * @return The enum numeric value on the wire for valueType.
   */
  int getValueTypeValue();
  /**
   *
   *
   * <pre>
   * Whether the measurement is an integer, a floating-point number, etc.
   * Some combinations of `metric_kind` and `value_type` might not be supported.
   * </pre>
   *
   * <code>.google.api.MetricDescriptor.ValueType value_type = 4;</code>
   *
   * @return The valueType.
   */
  com.google.api.MetricDescriptor.ValueType getValueType();

  /**
   *
   *
   * <pre>
   * The units in which the metric value is reported. It is only applicable
   * if the `value_type` is `INT64`, `DOUBLE`, or `DISTRIBUTION`. The `unit`
   * defines the representation of the stored metric values.
   *
   * Different systems might scale the values to be more easily displayed (so a
   * value of `0.02kBy` _might_ be displayed as `20By`, and a value of
   * `3523kBy` _might_ be displayed as `3.5MBy`). However, if the `unit` is
   * `kBy`, then the value of the metric is always in thousands of bytes, no
   * matter how it might be displayed.
   *
   * If you want a custom metric to record the exact number of CPU-seconds used
   * by a job, you can create an `INT64 CUMULATIVE` metric whose `unit` is
   * `s{CPU}` (or equivalently `1s{CPU}` or just `s`). If the job uses 12,005
   * CPU-seconds, then the value is written as `12005`.
   *
   * Alternatively, if you want a custom metric to record data in a more
   * granular way, you can create a `DOUBLE CUMULATIVE` metric whose `unit` is
   * `ks{CPU}`, and then write the value `12.005` (which is `12005/1000`),
   * or use `Kis{CPU}` and write `11.723` (which is `12005/1024`).
   *
   * The supported units are a subset of [The Unified Code for Units of
   * Measure](https://unitsofmeasure.org/ucum.html) standard:
   *
   * **Basic units (UNIT)**
   *
   * * `bit`   bit
   * * `By`    byte
   * * `s`     second
   * * `min`   minute
   * * `h`     hour
   * * `d`     day
   * * `1`     dimensionless
   *
   * **Prefixes (PREFIX)**
   *
   * * `k`     kilo    (10^3)
   * * `M`     mega    (10^6)
   * * `G`     giga    (10^9)
   * * `T`     tera    (10^12)
   * * `P`     peta    (10^15)
   * * `E`     exa     (10^18)
   * * `Z`     zetta   (10^21)
   * * `Y`     yotta   (10^24)
   *
   * * `m`     milli   (10^-3)
   * * `u`     micro   (10^-6)
   * * `n`     nano    (10^-9)
   * * `p`     pico    (10^-12)
   * * `f`     femto   (10^-15)
   * * `a`     atto    (10^-18)
   * * `z`     zepto   (10^-21)
   * * `y`     yocto   (10^-24)
   *
   * * `Ki`    kibi    (2^10)
   * * `Mi`    mebi    (2^20)
   * * `Gi`    gibi    (2^30)
   * * `Ti`    tebi    (2^40)
   * * `Pi`    pebi    (2^50)
   *
   * **Grammar**
   *
   * The grammar also includes these connectors:
   *
   * * `/`    division or ratio (as an infix operator). For examples,
   *          `kBy/{email}` or `MiBy/10ms` (although you should almost never
   *          have `/s` in a metric `unit`; rates should always be computed at
   *          query time from the underlying cumulative or delta value).
   * * `.`    multiplication or composition (as an infix operator). For
   *          examples, `GBy.d` or `k{watt}.h`.
   *
   * The grammar for a unit is as follows:
   *
   *     Expression = Component { "." Component } { "/" Component } ;
   *
   *     Component = ( [ PREFIX ] UNIT | "%" ) [ Annotation ]
   *               | Annotation
   *               | "1"
   *               ;
   *
   *     Annotation = "{" NAME "}" ;
   *
   * Notes:
   *
   * * `Annotation` is just a comment if it follows a `UNIT`. If the annotation
   *    is used alone, then the unit is equivalent to `1`. For examples,
   *    `{request}/s == 1/s`, `By{transmitted}/s == By/s`.
   * * `NAME` is a sequence of non-blank printable ASCII characters not
   *    containing `{` or `}`.
   * * `1` represents a unitary [dimensionless
   *    unit](https://en.wikipedia.org/wiki/Dimensionless_quantity) of 1, such
   *    as in `1/s`. It is typically used when none of the basic units are
   *    appropriate. For example, "new users per day" can be represented as
   *    `1/d` or `{new-users}/d` (and a metric value `5` would mean "5 new
   *    users). Alternatively, "thousands of page views per day" would be
   *    represented as `1000/d` or `k1/d` or `k{page_views}/d` (and a metric
   *    value of `5.3` would mean "5300 page views per day").
   * * `%` represents dimensionless value of 1/100, and annotates values giving
   *    a percentage (so the metric values are typically in the range of 0..100,
   *    and a metric value `3` means "3 percent").
   * * `10^2.%` indicates a metric contains a ratio, typically in the range
   *    0..1, that will be multiplied by 100 and displayed as a percentage
   *    (so a metric value `0.03` means "3 percent").
   * </pre>
   *
   * <code>string unit = 5;</code>
   *
   * @return The unit.
   */
  java.lang.String getUnit();
  /**
   *
   *
   * <pre>
   * The units in which the metric value is reported. It is only applicable
   * if the `value_type` is `INT64`, `DOUBLE`, or `DISTRIBUTION`. The `unit`
   * defines the representation of the stored metric values.
   *
   * Different systems might scale the values to be more easily displayed (so a
   * value of `0.02kBy` _might_ be displayed as `20By`, and a value of
   * `3523kBy` _might_ be displayed as `3.5MBy`). However, if the `unit` is
   * `kBy`, then the value of the metric is always in thousands of bytes, no
   * matter how it might be displayed.
   *
   * If you want a custom metric to record the exact number of CPU-seconds used
   * by a job, you can create an `INT64 CUMULATIVE` metric whose `unit` is
   * `s{CPU}` (or equivalently `1s{CPU}` or just `s`). If the job uses 12,005
   * CPU-seconds, then the value is written as `12005`.
   *
   * Alternatively, if you want a custom metric to record data in a more
   * granular way, you can create a `DOUBLE CUMULATIVE` metric whose `unit` is
   * `ks{CPU}`, and then write the value `12.005` (which is `12005/1000`),
   * or use `Kis{CPU}` and write `11.723` (which is `12005/1024`).
   *
   * The supported units are a subset of [The Unified Code for Units of
   * Measure](https://unitsofmeasure.org/ucum.html) standard:
   *
   * **Basic units (UNIT)**
   *
   * * `bit`   bit
   * * `By`    byte
   * * `s`     second
   * * `min`   minute
   * * `h`     hour
   * * `d`     day
   * * `1`     dimensionless
   *
   * **Prefixes (PREFIX)**
   *
   * * `k`     kilo    (10^3)
   * * `M`     mega    (10^6)
   * * `G`     giga    (10^9)
   * * `T`     tera    (10^12)
   * * `P`     peta    (10^15)
   * * `E`     exa     (10^18)
   * * `Z`     zetta   (10^21)
   * * `Y`     yotta   (10^24)
   *
   * * `m`     milli   (10^-3)
   * * `u`     micro   (10^-6)
   * * `n`     nano    (10^-9)
   * * `p`     pico    (10^-12)
   * * `f`     femto   (10^-15)
   * * `a`     atto    (10^-18)
   * * `z`     zepto   (10^-21)
   * * `y`     yocto   (10^-24)
   *
   * * `Ki`    kibi    (2^10)
   * * `Mi`    mebi    (2^20)
   * * `Gi`    gibi    (2^30)
   * * `Ti`    tebi    (2^40)
   * * `Pi`    pebi    (2^50)
   *
   * **Grammar**
   *
   * The grammar also includes these connectors:
   *
   * * `/`    division or ratio (as an infix operator). For examples,
   *          `kBy/{email}` or `MiBy/10ms` (although you should almost never
   *          have `/s` in a metric `unit`; rates should always be computed at
   *          query time from the underlying cumulative or delta value).
   * * `.`    multiplication or composition (as an infix operator). For
   *          examples, `GBy.d` or `k{watt}.h`.
   *
   * The grammar for a unit is as follows:
   *
   *     Expression = Component { "." Component } { "/" Component } ;
   *
   *     Component = ( [ PREFIX ] UNIT | "%" ) [ Annotation ]
   *               | Annotation
   *               | "1"
   *               ;
   *
   *     Annotation = "{" NAME "}" ;
   *
   * Notes:
   *
   * * `Annotation` is just a comment if it follows a `UNIT`. If the annotation
   *    is used alone, then the unit is equivalent to `1`. For examples,
   *    `{request}/s == 1/s`, `By{transmitted}/s == By/s`.
   * * `NAME` is a sequence of non-blank printable ASCII characters not
   *    containing `{` or `}`.
   * * `1` represents a unitary [dimensionless
   *    unit](https://en.wikipedia.org/wiki/Dimensionless_quantity) of 1, such
   *    as in `1/s`. It is typically used when none of the basic units are
   *    appropriate. For example, "new users per day" can be represented as
   *    `1/d` or `{new-users}/d` (and a metric value `5` would mean "5 new
   *    users). Alternatively, "thousands of page views per day" would be
   *    represented as `1000/d` or `k1/d` or `k{page_views}/d` (and a metric
   *    value of `5.3` would mean "5300 page views per day").
   * * `%` represents dimensionless value of 1/100, and annotates values giving
   *    a percentage (so the metric values are typically in the range of 0..100,
   *    and a metric value `3` means "3 percent").
   * * `10^2.%` indicates a metric contains a ratio, typically in the range
   *    0..1, that will be multiplied by 100 and displayed as a percentage
   *    (so a metric value `0.03` means "3 percent").
   * </pre>
   *
   * <code>string unit = 5;</code>
   *
   * @return The bytes for unit.
   */
  com.google.protobuf.ByteString getUnitBytes();

  /**
   *
   *
   * <pre>
   * A detailed description of the metric, which can be used in documentation.
   * </pre>
   *
   * <code>string description = 6;</code>
   *
   * @return The description.
   */
  java.lang.String getDescription();
  /**
   *
   *
   * <pre>
   * A detailed description of the metric, which can be used in documentation.
   * </pre>
   *
   * <code>string description = 6;</code>
   *
   * @return The bytes for description.
   */
  com.google.protobuf.ByteString getDescriptionBytes();

  /**
   *
   *
   * <pre>
   * A concise name for the metric, which can be displayed in user interfaces.
   * Use sentence case without an ending period, for example "Request count".
   * This field is optional but it is recommended to be set for any metrics
   * associated with user-visible concepts, such as Quota.
   * </pre>
   *
   * <code>string display_name = 7;</code>
   *
   * @return The displayName.
   */
  java.lang.String getDisplayName();
  /**
   *
   *
   * <pre>
   * A concise name for the metric, which can be displayed in user interfaces.
   * Use sentence case without an ending period, for example "Request count".
   * This field is optional but it is recommended to be set for any metrics
   * associated with user-visible concepts, such as Quota.
   * </pre>
   *
   * <code>string display_name = 7;</code>
   *
   * @return The bytes for displayName.
   */
  com.google.protobuf.ByteString getDisplayNameBytes();

  /**
   *
   *
   * <pre>
   * Optional. Metadata which can be used to guide usage of the metric.
   * </pre>
   *
   * <code>.google.api.MetricDescriptor.MetricDescriptorMetadata metadata = 10;</code>
   *
   * @return Whether the metadata field is set.
   */
  boolean hasMetadata();
  /**
   *
   *
   * <pre>
   * Optional. Metadata which can be used to guide usage of the metric.
   * </pre>
   *
   * <code>.google.api.MetricDescriptor.MetricDescriptorMetadata metadata = 10;</code>
   *
   * @return The metadata.
   */
  com.google.api.MetricDescriptor.MetricDescriptorMetadata getMetadata();
  /**
   *
   *
   * <pre>
   * Optional. Metadata which can be used to guide usage of the metric.
   * </pre>
   *
   * <code>.google.api.MetricDescriptor.MetricDescriptorMetadata metadata = 10;</code>
   */
  com.google.api.MetricDescriptor.MetricDescriptorMetadataOrBuilder getMetadataOrBuilder();

  /**
   *
   *
   * <pre>
   * Optional. The launch stage of the metric definition.
   * </pre>
   *
   * <code>.google.api.LaunchStage launch_stage = 12;</code>
   *
   * @return The enum numeric value on the wire for launchStage.
   */
  int getLaunchStageValue();
  /**
   *
   *
   * <pre>
   * Optional. The launch stage of the metric definition.
   * </pre>
   *
   * <code>.google.api.LaunchStage launch_stage = 12;</code>
   *
   * @return The launchStage.
   */
  com.google.api.LaunchStage getLaunchStage();

  /**
   *
   *
   * <pre>
   * Read-only. If present, then a [time
   * series][google.monitoring.v3.TimeSeries], which is identified partially by
   * a metric type and a
   * [MonitoredResourceDescriptor][google.api.MonitoredResourceDescriptor], that
   * is associated with this metric type can only be associated with one of the
   * monitored resource types listed here.
   * </pre>
   *
   * <code>repeated string monitored_resource_types = 13;</code>
   *
   * @return A list containing the monitoredResourceTypes.
   */
  java.util.List<java.lang.String> getMonitoredResourceTypesList();
  /**
   *
   *
   * <pre>
   * Read-only. If present, then a [time
   * series][google.monitoring.v3.TimeSeries], which is identified partially by
   * a metric type and a
   * [MonitoredResourceDescriptor][google.api.MonitoredResourceDescriptor], that
   * is associated with this metric type can only be associated with one of the
   * monitored resource types listed here.
   * </pre>
   *
   * <code>repeated string monitored_resource_types = 13;</code>
   *
   * @return The count of monitoredResourceTypes.
   */
  int getMonitoredResourceTypesCount();
  /**
   *
   *
   * <pre>
   * Read-only. If present, then a [time
   * series][google.monitoring.v3.TimeSeries], which is identified partially by
   * a metric type and a
   * [MonitoredResourceDescriptor][google.api.MonitoredResourceDescriptor], that
   * is associated with this metric type can only be associated with one of the
   * monitored resource types listed here.
   * </pre>
   *
   * <code>repeated string monitored_resource_types = 13;</code>
   *
   * @param index The index of the element to return.
   * @return The monitoredResourceTypes at the given index.
   */
  java.lang.String getMonitoredResourceTypes(int index);
  /**
   *
   *
   * <pre>
   * Read-only. If present, then a [time
   * series][google.monitoring.v3.TimeSeries], which is identified partially by
   * a metric type and a
   * [MonitoredResourceDescriptor][google.api.MonitoredResourceDescriptor], that
   * is associated with this metric type can only be associated with one of the
   * monitored resource types listed here.
   * </pre>
   *
   * <code>repeated string monitored_resource_types = 13;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the monitoredResourceTypes at the given index.
   */
  com.google.protobuf.ByteString getMonitoredResourceTypesBytes(int index);
}
