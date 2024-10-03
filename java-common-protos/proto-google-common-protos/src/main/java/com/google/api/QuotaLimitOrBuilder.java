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
// source: google/api/quota.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

public interface QuotaLimitOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.QuotaLimit)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Name of the quota limit.
   *
   * The name must be provided, and it must be unique within the service. The
   * name can only include alphanumeric characters as well as '-'.
   *
   * The maximum length of the limit name is 64 characters.
   * </pre>
   *
   * <code>string name = 6;</code>
   *
   * @return The name.
   */
  java.lang.String getName();

  /**
   *
   *
   * <pre>
   * Name of the quota limit.
   *
   * The name must be provided, and it must be unique within the service. The
   * name can only include alphanumeric characters as well as '-'.
   *
   * The maximum length of the limit name is 64 characters.
   * </pre>
   *
   * <code>string name = 6;</code>
   *
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString getNameBytes();

  /**
   *
   *
   * <pre>
   * Optional. User-visible, extended description for this quota limit.
   * Should be used only when more context is needed to understand this limit
   * than provided by the limit's display name (see: `display_name`).
   * </pre>
   *
   * <code>string description = 2;</code>
   *
   * @return The description.
   */
  java.lang.String getDescription();

  /**
   *
   *
   * <pre>
   * Optional. User-visible, extended description for this quota limit.
   * Should be used only when more context is needed to understand this limit
   * than provided by the limit's display name (see: `display_name`).
   * </pre>
   *
   * <code>string description = 2;</code>
   *
   * @return The bytes for description.
   */
  com.google.protobuf.ByteString getDescriptionBytes();

  /**
   *
   *
   * <pre>
   * Default number of tokens that can be consumed during the specified
   * duration. This is the number of tokens assigned when a client
   * application developer activates the service for his/her project.
   *
   * Specifying a value of 0 will block all requests. This can be used if you
   * are provisioning quota to selected consumers and blocking others.
   * Similarly, a value of -1 will indicate an unlimited quota. No other
   * negative values are allowed.
   *
   * Used by group-based quotas only.
   * </pre>
   *
   * <code>int64 default_limit = 3;</code>
   *
   * @return The defaultLimit.
   */
  long getDefaultLimit();

  /**
   *
   *
   * <pre>
   * Maximum number of tokens that can be consumed during the specified
   * duration. Client application developers can override the default limit up
   * to this maximum. If specified, this value cannot be set to a value less
   * than the default limit. If not specified, it is set to the default limit.
   *
   * To allow clients to apply overrides with no upper bound, set this to -1,
   * indicating unlimited maximum quota.
   *
   * Used by group-based quotas only.
   * </pre>
   *
   * <code>int64 max_limit = 4;</code>
   *
   * @return The maxLimit.
   */
  long getMaxLimit();

  /**
   *
   *
   * <pre>
   * Free tier value displayed in the Developers Console for this limit.
   * The free tier is the number of tokens that will be subtracted from the
   * billed amount when billing is enabled.
   * This field can only be set on a limit with duration "1d", in a billable
   * group; it is invalid on any other limit. If this field is not set, it
   * defaults to 0, indicating that there is no free tier for this service.
   *
   * Used by group-based quotas only.
   * </pre>
   *
   * <code>int64 free_tier = 7;</code>
   *
   * @return The freeTier.
   */
  long getFreeTier();

  /**
   *
   *
   * <pre>
   * Duration of this limit in textual notation. Must be "100s" or "1d".
   *
   * Used by group-based quotas only.
   * </pre>
   *
   * <code>string duration = 5;</code>
   *
   * @return The duration.
   */
  java.lang.String getDuration();

  /**
   *
   *
   * <pre>
   * Duration of this limit in textual notation. Must be "100s" or "1d".
   *
   * Used by group-based quotas only.
   * </pre>
   *
   * <code>string duration = 5;</code>
   *
   * @return The bytes for duration.
   */
  com.google.protobuf.ByteString getDurationBytes();

  /**
   *
   *
   * <pre>
   * The name of the metric this quota limit applies to. The quota limits with
   * the same metric will be checked together during runtime. The metric must be
   * defined within the service config.
   * </pre>
   *
   * <code>string metric = 8;</code>
   *
   * @return The metric.
   */
  java.lang.String getMetric();

  /**
   *
   *
   * <pre>
   * The name of the metric this quota limit applies to. The quota limits with
   * the same metric will be checked together during runtime. The metric must be
   * defined within the service config.
   * </pre>
   *
   * <code>string metric = 8;</code>
   *
   * @return The bytes for metric.
   */
  com.google.protobuf.ByteString getMetricBytes();

  /**
   *
   *
   * <pre>
   * Specify the unit of the quota limit. It uses the same syntax as
   * [Metric.unit][]. The supported unit kinds are determined by the quota
   * backend system.
   *
   * Here are some examples:
   * * "1/min/{project}" for quota per minute per project.
   *
   * Note: the order of unit components is insignificant.
   * The "1" at the beginning is required to follow the metric unit syntax.
   * </pre>
   *
   * <code>string unit = 9;</code>
   *
   * @return The unit.
   */
  java.lang.String getUnit();

  /**
   *
   *
   * <pre>
   * Specify the unit of the quota limit. It uses the same syntax as
   * [Metric.unit][]. The supported unit kinds are determined by the quota
   * backend system.
   *
   * Here are some examples:
   * * "1/min/{project}" for quota per minute per project.
   *
   * Note: the order of unit components is insignificant.
   * The "1" at the beginning is required to follow the metric unit syntax.
   * </pre>
   *
   * <code>string unit = 9;</code>
   *
   * @return The bytes for unit.
   */
  com.google.protobuf.ByteString getUnitBytes();

  /**
   *
   *
   * <pre>
   * Tiered limit values. You must specify this as a key:value pair, with an
   * integer value that is the maximum number of requests allowed for the
   * specified unit. Currently only STANDARD is supported.
   * </pre>
   *
   * <code>map&lt;string, int64&gt; values = 10;</code>
   */
  int getValuesCount();

  /**
   *
   *
   * <pre>
   * Tiered limit values. You must specify this as a key:value pair, with an
   * integer value that is the maximum number of requests allowed for the
   * specified unit. Currently only STANDARD is supported.
   * </pre>
   *
   * <code>map&lt;string, int64&gt; values = 10;</code>
   */
  boolean containsValues(java.lang.String key);

  /** Use {@link #getValuesMap()} instead. */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.Long> getValues();

  /**
   *
   *
   * <pre>
   * Tiered limit values. You must specify this as a key:value pair, with an
   * integer value that is the maximum number of requests allowed for the
   * specified unit. Currently only STANDARD is supported.
   * </pre>
   *
   * <code>map&lt;string, int64&gt; values = 10;</code>
   */
  java.util.Map<java.lang.String, java.lang.Long> getValuesMap();

  /**
   *
   *
   * <pre>
   * Tiered limit values. You must specify this as a key:value pair, with an
   * integer value that is the maximum number of requests allowed for the
   * specified unit. Currently only STANDARD is supported.
   * </pre>
   *
   * <code>map&lt;string, int64&gt; values = 10;</code>
   */
  long getValuesOrDefault(java.lang.String key, long defaultValue);

  /**
   *
   *
   * <pre>
   * Tiered limit values. You must specify this as a key:value pair, with an
   * integer value that is the maximum number of requests allowed for the
   * specified unit. Currently only STANDARD is supported.
   * </pre>
   *
   * <code>map&lt;string, int64&gt; values = 10;</code>
   */
  long getValuesOrThrow(java.lang.String key);

  /**
   *
   *
   * <pre>
   * User-visible display name for this limit.
   * Optional. If not set, the UI will provide a default display name based on
   * the quota configuration. This field can be used to override the default
   * display name generated from the configuration.
   * </pre>
   *
   * <code>string display_name = 12;</code>
   *
   * @return The displayName.
   */
  java.lang.String getDisplayName();

  /**
   *
   *
   * <pre>
   * User-visible display name for this limit.
   * Optional. If not set, the UI will provide a default display name based on
   * the quota configuration. This field can be used to override the default
   * display name generated from the configuration.
   * </pre>
   *
   * <code>string display_name = 12;</code>
   *
   * @return The bytes for displayName.
   */
  com.google.protobuf.ByteString getDisplayNameBytes();
}
