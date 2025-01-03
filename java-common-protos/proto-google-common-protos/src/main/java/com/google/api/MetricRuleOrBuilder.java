/*
 * Copyright 2025 Google LLC
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
// source: google/api/quota.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

public interface MetricRuleOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.MetricRule)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Selects the methods to which this rule applies.
   *
   * Refer to [selector][google.api.DocumentationRule.selector] for syntax
   * details.
   * </pre>
   *
   * <code>string selector = 1;</code>
   *
   * @return The selector.
   */
  java.lang.String getSelector();
  /**
   *
   *
   * <pre>
   * Selects the methods to which this rule applies.
   *
   * Refer to [selector][google.api.DocumentationRule.selector] for syntax
   * details.
   * </pre>
   *
   * <code>string selector = 1;</code>
   *
   * @return The bytes for selector.
   */
  com.google.protobuf.ByteString getSelectorBytes();

  /**
   *
   *
   * <pre>
   * Metrics to update when the selected methods are called, and the associated
   * cost applied to each metric.
   *
   * The key of the map is the metric name, and the values are the amount
   * increased for the metric against which the quota limits are defined.
   * The value must not be negative.
   * </pre>
   *
   * <code>map&lt;string, int64&gt; metric_costs = 2;</code>
   */
  int getMetricCostsCount();
  /**
   *
   *
   * <pre>
   * Metrics to update when the selected methods are called, and the associated
   * cost applied to each metric.
   *
   * The key of the map is the metric name, and the values are the amount
   * increased for the metric against which the quota limits are defined.
   * The value must not be negative.
   * </pre>
   *
   * <code>map&lt;string, int64&gt; metric_costs = 2;</code>
   */
  boolean containsMetricCosts(java.lang.String key);
  /** Use {@link #getMetricCostsMap()} instead. */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.Long> getMetricCosts();
  /**
   *
   *
   * <pre>
   * Metrics to update when the selected methods are called, and the associated
   * cost applied to each metric.
   *
   * The key of the map is the metric name, and the values are the amount
   * increased for the metric against which the quota limits are defined.
   * The value must not be negative.
   * </pre>
   *
   * <code>map&lt;string, int64&gt; metric_costs = 2;</code>
   */
  java.util.Map<java.lang.String, java.lang.Long> getMetricCostsMap();
  /**
   *
   *
   * <pre>
   * Metrics to update when the selected methods are called, and the associated
   * cost applied to each metric.
   *
   * The key of the map is the metric name, and the values are the amount
   * increased for the metric against which the quota limits are defined.
   * The value must not be negative.
   * </pre>
   *
   * <code>map&lt;string, int64&gt; metric_costs = 2;</code>
   */
  long getMetricCostsOrDefault(java.lang.String key, long defaultValue);
  /**
   *
   *
   * <pre>
   * Metrics to update when the selected methods are called, and the associated
   * cost applied to each metric.
   *
   * The key of the map is the metric name, and the values are the amount
   * increased for the metric against which the quota limits are defined.
   * The value must not be negative.
   * </pre>
   *
   * <code>map&lt;string, int64&gt; metric_costs = 2;</code>
   */
  long getMetricCostsOrThrow(java.lang.String key);
}
