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

/**
 *
 *
 * <pre>
 * Bind API methods to metrics. Binding a method to a metric causes that
 * metric's configured quota behaviors to apply to the method call.
 * </pre>
 *
 * Protobuf type {@code google.api.MetricRule}
 */
public final class MetricRule
    extends com.google.protobuf.GeneratedMessageLite<MetricRule, MetricRule.Builder>
    implements
    // @@protoc_insertion_point(message_implements:google.api.MetricRule)
    MetricRuleOrBuilder {
  private MetricRule() {
    selector_ = "";
  }

  public static final int SELECTOR_FIELD_NUMBER = 1;
  private java.lang.String selector_;
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
  @java.lang.Override
  public java.lang.String getSelector() {
    return selector_;
  }
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
  @java.lang.Override
  public com.google.protobuf.ByteString getSelectorBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(selector_);
  }
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
   * @param value The selector to set.
   */
  private void setSelector(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();

    selector_ = value;
  }
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
   */
  private void clearSelector() {

    selector_ = getDefaultInstance().getSelector();
  }
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
   * @param value The bytes for selector to set.
   */
  private void setSelectorBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    selector_ = value.toStringUtf8();
  }

  public static final int METRIC_COSTS_FIELD_NUMBER = 2;

  private static final class MetricCostsDefaultEntryHolder {
    static final com.google.protobuf.MapEntryLite<java.lang.String, java.lang.Long> defaultEntry =
        com.google.protobuf.MapEntryLite.<java.lang.String, java.lang.Long>newDefaultInstance(
            com.google.protobuf.WireFormat.FieldType.STRING,
            "",
            com.google.protobuf.WireFormat.FieldType.INT64,
            0L);
  }

  private com.google.protobuf.MapFieldLite<java.lang.String, java.lang.Long> metricCosts_ =
      com.google.protobuf.MapFieldLite.emptyMapField();

  private com.google.protobuf.MapFieldLite<java.lang.String, java.lang.Long>
      internalGetMetricCosts() {
    return metricCosts_;
  }

  private com.google.protobuf.MapFieldLite<java.lang.String, java.lang.Long>
      internalGetMutableMetricCosts() {
    if (!metricCosts_.isMutable()) {
      metricCosts_ = metricCosts_.mutableCopy();
    }
    return metricCosts_;
  }

  @java.lang.Override
  public int getMetricCostsCount() {
    return internalGetMetricCosts().size();
  }
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
  @java.lang.Override
  public boolean containsMetricCosts(java.lang.String key) {
    java.lang.Class<?> keyClass = key.getClass();
    return internalGetMetricCosts().containsKey(key);
  }
  /** Use {@link #getMetricCostsMap()} instead. */
  @java.lang.Override
  @java.lang.Deprecated
  public java.util.Map<java.lang.String, java.lang.Long> getMetricCosts() {
    return getMetricCostsMap();
  }
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
  @java.lang.Override
  public java.util.Map<java.lang.String, java.lang.Long> getMetricCostsMap() {
    return java.util.Collections.unmodifiableMap(internalGetMetricCosts());
  }
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
  @java.lang.Override
  public long getMetricCostsOrDefault(java.lang.String key, long defaultValue) {
    java.lang.Class<?> keyClass = key.getClass();
    java.util.Map<java.lang.String, java.lang.Long> map = internalGetMetricCosts();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
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
  @java.lang.Override
  public long getMetricCostsOrThrow(java.lang.String key) {
    java.lang.Class<?> keyClass = key.getClass();
    java.util.Map<java.lang.String, java.lang.Long> map = internalGetMetricCosts();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }
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
  private java.util.Map<java.lang.String, java.lang.Long> getMutableMetricCostsMap() {
    return internalGetMutableMetricCosts();
  }

  public static com.google.api.MetricRule parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.MetricRule parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.MetricRule parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.MetricRule parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.MetricRule parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.MetricRule parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.MetricRule parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.MetricRule parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.MetricRule parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.MetricRule parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.MetricRule parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.MetricRule parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }

  public static Builder newBuilder(com.google.api.MetricRule prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   *
   *
   * <pre>
   * Bind API methods to metrics. Binding a method to a metric causes that
   * metric's configured quota behaviors to apply to the method call.
   * </pre>
   *
   * Protobuf type {@code google.api.MetricRule}
   */
  public static final class Builder
      extends com.google.protobuf.GeneratedMessageLite.Builder<com.google.api.MetricRule, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.MetricRule)
      com.google.api.MetricRuleOrBuilder {
    // Construct using com.google.api.MetricRule.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }

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
    @java.lang.Override
    public java.lang.String getSelector() {
      return instance.getSelector();
    }
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
    @java.lang.Override
    public com.google.protobuf.ByteString getSelectorBytes() {
      return instance.getSelectorBytes();
    }
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
     * @param value The selector to set.
     * @return This builder for chaining.
     */
    public Builder setSelector(java.lang.String value) {
      copyOnWrite();
      instance.setSelector(value);
      return this;
    }
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
     * @return This builder for chaining.
     */
    public Builder clearSelector() {
      copyOnWrite();
      instance.clearSelector();
      return this;
    }
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
     * @param value The bytes for selector to set.
     * @return This builder for chaining.
     */
    public Builder setSelectorBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setSelectorBytes(value);
      return this;
    }

    @java.lang.Override
    public int getMetricCostsCount() {
      return instance.getMetricCostsMap().size();
    }
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
    @java.lang.Override
    public boolean containsMetricCosts(java.lang.String key) {
      java.lang.Class<?> keyClass = key.getClass();
      return instance.getMetricCostsMap().containsKey(key);
    }

    public Builder clearMetricCosts() {
      copyOnWrite();
      instance.getMutableMetricCostsMap().clear();
      return this;
    }
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
    public Builder removeMetricCosts(java.lang.String key) {
      java.lang.Class<?> keyClass = key.getClass();
      copyOnWrite();
      instance.getMutableMetricCostsMap().remove(key);
      return this;
    }
    /** Use {@link #getMetricCostsMap()} instead. */
    @java.lang.Override
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.Long> getMetricCosts() {
      return getMetricCostsMap();
    }
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
    @java.lang.Override
    public java.util.Map<java.lang.String, java.lang.Long> getMetricCostsMap() {
      return java.util.Collections.unmodifiableMap(instance.getMetricCostsMap());
    }
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
    @java.lang.Override
    public long getMetricCostsOrDefault(java.lang.String key, long defaultValue) {
      java.lang.Class<?> keyClass = key.getClass();
      java.util.Map<java.lang.String, java.lang.Long> map = instance.getMetricCostsMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
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
    @java.lang.Override
    public long getMetricCostsOrThrow(java.lang.String key) {
      java.lang.Class<?> keyClass = key.getClass();
      java.util.Map<java.lang.String, java.lang.Long> map = instance.getMetricCostsMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }
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
    public Builder putMetricCosts(java.lang.String key, long value) {
      java.lang.Class<?> keyClass = key.getClass();

      copyOnWrite();
      instance.getMutableMetricCostsMap().put(key, value);
      return this;
    }
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
    public Builder putAllMetricCosts(java.util.Map<java.lang.String, java.lang.Long> values) {
      copyOnWrite();
      instance.getMutableMetricCostsMap().putAll(values);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.api.MetricRule)
  }

  @java.lang.Override
  @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
  protected final java.lang.Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      java.lang.Object arg0,
      java.lang.Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE:
        {
          return new com.google.api.MetricRule();
        }
      case NEW_BUILDER:
        {
          return new Builder();
        }
      case BUILD_MESSAGE_INFO:
        {
          java.lang.Object[] objects =
              new java.lang.Object[] {
                "selector_", "metricCosts_", MetricCostsDefaultEntryHolder.defaultEntry,
              };
          java.lang.String info =
              "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0001\u0000\u0000\u0001\u0208\u00022";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
      case GET_DEFAULT_INSTANCE:
        {
          return DEFAULT_INSTANCE;
        }
      case GET_PARSER:
        {
          com.google.protobuf.Parser<com.google.api.MetricRule> parser = PARSER;
          if (parser == null) {
            synchronized (com.google.api.MetricRule.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<com.google.api.MetricRule>(DEFAULT_INSTANCE);
                PARSER = parser;
              }
            }
          }
          return parser;
        }
      case GET_MEMOIZED_IS_INITIALIZED:
        {
          return (byte) 1;
        }
      case SET_MEMOIZED_IS_INITIALIZED:
        {
          return null;
        }
    }
    throw new UnsupportedOperationException();
  }

  // @@protoc_insertion_point(class_scope:google.api.MetricRule)
  private static final com.google.api.MetricRule DEFAULT_INSTANCE;

  static {
    MetricRule defaultInstance = new MetricRule();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        MetricRule.class, defaultInstance);
  }

  public static com.google.api.MetricRule getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<MetricRule> PARSER;

  public static com.google.protobuf.Parser<MetricRule> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
