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
// source: google/api/metric.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

/**
 *
 *
 * <pre>
 * A specific metric, identified by specifying values for all of the
 * labels of a [`MetricDescriptor`][google.api.MetricDescriptor].
 * </pre>
 *
 * Protobuf type {@code google.api.Metric}
 */
public final class Metric extends com.google.protobuf.GeneratedMessageLite<Metric, Metric.Builder>
    implements
    // @@protoc_insertion_point(message_implements:google.api.Metric)
    MetricOrBuilder {
  private Metric() {
    type_ = "";
  }

  public static final int TYPE_FIELD_NUMBER = 3;
  private java.lang.String type_;
  /**
   *
   *
   * <pre>
   * An existing metric type, see
   * [google.api.MetricDescriptor][google.api.MetricDescriptor]. For example,
   * `custom.googleapis.com/invoice/paid/amount`.
   * </pre>
   *
   * <code>string type = 3;</code>
   *
   * @return The type.
   */
  @java.lang.Override
  public java.lang.String getType() {
    return type_;
  }
  /**
   *
   *
   * <pre>
   * An existing metric type, see
   * [google.api.MetricDescriptor][google.api.MetricDescriptor]. For example,
   * `custom.googleapis.com/invoice/paid/amount`.
   * </pre>
   *
   * <code>string type = 3;</code>
   *
   * @return The bytes for type.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getTypeBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(type_);
  }
  /**
   *
   *
   * <pre>
   * An existing metric type, see
   * [google.api.MetricDescriptor][google.api.MetricDescriptor]. For example,
   * `custom.googleapis.com/invoice/paid/amount`.
   * </pre>
   *
   * <code>string type = 3;</code>
   *
   * @param value The type to set.
   */
  private void setType(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();

    type_ = value;
  }
  /**
   *
   *
   * <pre>
   * An existing metric type, see
   * [google.api.MetricDescriptor][google.api.MetricDescriptor]. For example,
   * `custom.googleapis.com/invoice/paid/amount`.
   * </pre>
   *
   * <code>string type = 3;</code>
   */
  private void clearType() {

    type_ = getDefaultInstance().getType();
  }
  /**
   *
   *
   * <pre>
   * An existing metric type, see
   * [google.api.MetricDescriptor][google.api.MetricDescriptor]. For example,
   * `custom.googleapis.com/invoice/paid/amount`.
   * </pre>
   *
   * <code>string type = 3;</code>
   *
   * @param value The bytes for type to set.
   */
  private void setTypeBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    type_ = value.toStringUtf8();
  }

  public static final int LABELS_FIELD_NUMBER = 2;

  private static final class LabelsDefaultEntryHolder {
    static final com.google.protobuf.MapEntryLite<java.lang.String, java.lang.String> defaultEntry =
        com.google.protobuf.MapEntryLite.<java.lang.String, java.lang.String>newDefaultInstance(
            com.google.protobuf.WireFormat.FieldType.STRING,
            "",
            com.google.protobuf.WireFormat.FieldType.STRING,
            "");
  }

  private com.google.protobuf.MapFieldLite<java.lang.String, java.lang.String> labels_ =
      com.google.protobuf.MapFieldLite.emptyMapField();

  private com.google.protobuf.MapFieldLite<java.lang.String, java.lang.String> internalGetLabels() {
    return labels_;
  }

  private com.google.protobuf.MapFieldLite<java.lang.String, java.lang.String>
      internalGetMutableLabels() {
    if (!labels_.isMutable()) {
      labels_ = labels_.mutableCopy();
    }
    return labels_;
  }

  @java.lang.Override
  public int getLabelsCount() {
    return internalGetLabels().size();
  }
  /**
   *
   *
   * <pre>
   * The set of label values that uniquely identify this metric. All
   * labels listed in the `MetricDescriptor` must be assigned values.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */
  @java.lang.Override
  public boolean containsLabels(java.lang.String key) {
    java.lang.Class<?> keyClass = key.getClass();
    return internalGetLabels().containsKey(key);
  }
  /** Use {@link #getLabelsMap()} instead. */
  @java.lang.Override
  @java.lang.Deprecated
  public java.util.Map<java.lang.String, java.lang.String> getLabels() {
    return getLabelsMap();
  }
  /**
   *
   *
   * <pre>
   * The set of label values that uniquely identify this metric. All
   * labels listed in the `MetricDescriptor` must be assigned values.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */
  @java.lang.Override
  public java.util.Map<java.lang.String, java.lang.String> getLabelsMap() {
    return java.util.Collections.unmodifiableMap(internalGetLabels());
  }
  /**
   *
   *
   * <pre>
   * The set of label values that uniquely identify this metric. All
   * labels listed in the `MetricDescriptor` must be assigned values.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */
  @java.lang.Override
  public /* nullable */ java.lang.String getLabelsOrDefault(
      java.lang.String key,
      /* nullable */
      java.lang.String defaultValue) {
    java.lang.Class<?> keyClass = key.getClass();
    java.util.Map<java.lang.String, java.lang.String> map = internalGetLabels();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
  /**
   *
   *
   * <pre>
   * The set of label values that uniquely identify this metric. All
   * labels listed in the `MetricDescriptor` must be assigned values.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */
  @java.lang.Override
  public java.lang.String getLabelsOrThrow(java.lang.String key) {
    java.lang.Class<?> keyClass = key.getClass();
    java.util.Map<java.lang.String, java.lang.String> map = internalGetLabels();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }
  /**
   *
   *
   * <pre>
   * The set of label values that uniquely identify this metric. All
   * labels listed in the `MetricDescriptor` must be assigned values.
   * </pre>
   *
   * <code>map&lt;string, string&gt; labels = 2;</code>
   */
  private java.util.Map<java.lang.String, java.lang.String> getMutableLabelsMap() {
    return internalGetMutableLabels();
  }

  public static com.google.api.Metric parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.Metric parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.Metric parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.Metric parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.Metric parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.Metric parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.Metric parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.Metric parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.Metric parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.Metric parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.Metric parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.Metric parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }

  public static Builder newBuilder(com.google.api.Metric prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   *
   *
   * <pre>
   * A specific metric, identified by specifying values for all of the
   * labels of a [`MetricDescriptor`][google.api.MetricDescriptor].
   * </pre>
   *
   * Protobuf type {@code google.api.Metric}
   */
  public static final class Builder
      extends com.google.protobuf.GeneratedMessageLite.Builder<com.google.api.Metric, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.Metric)
      com.google.api.MetricOrBuilder {
    // Construct using com.google.api.Metric.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }

    /**
     *
     *
     * <pre>
     * An existing metric type, see
     * [google.api.MetricDescriptor][google.api.MetricDescriptor]. For example,
     * `custom.googleapis.com/invoice/paid/amount`.
     * </pre>
     *
     * <code>string type = 3;</code>
     *
     * @return The type.
     */
    @java.lang.Override
    public java.lang.String getType() {
      return instance.getType();
    }
    /**
     *
     *
     * <pre>
     * An existing metric type, see
     * [google.api.MetricDescriptor][google.api.MetricDescriptor]. For example,
     * `custom.googleapis.com/invoice/paid/amount`.
     * </pre>
     *
     * <code>string type = 3;</code>
     *
     * @return The bytes for type.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getTypeBytes() {
      return instance.getTypeBytes();
    }
    /**
     *
     *
     * <pre>
     * An existing metric type, see
     * [google.api.MetricDescriptor][google.api.MetricDescriptor]. For example,
     * `custom.googleapis.com/invoice/paid/amount`.
     * </pre>
     *
     * <code>string type = 3;</code>
     *
     * @param value The type to set.
     * @return This builder for chaining.
     */
    public Builder setType(java.lang.String value) {
      copyOnWrite();
      instance.setType(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * An existing metric type, see
     * [google.api.MetricDescriptor][google.api.MetricDescriptor]. For example,
     * `custom.googleapis.com/invoice/paid/amount`.
     * </pre>
     *
     * <code>string type = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearType() {
      copyOnWrite();
      instance.clearType();
      return this;
    }
    /**
     *
     *
     * <pre>
     * An existing metric type, see
     * [google.api.MetricDescriptor][google.api.MetricDescriptor]. For example,
     * `custom.googleapis.com/invoice/paid/amount`.
     * </pre>
     *
     * <code>string type = 3;</code>
     *
     * @param value The bytes for type to set.
     * @return This builder for chaining.
     */
    public Builder setTypeBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setTypeBytes(value);
      return this;
    }

    @java.lang.Override
    public int getLabelsCount() {
      return instance.getLabelsMap().size();
    }
    /**
     *
     *
     * <pre>
     * The set of label values that uniquely identify this metric. All
     * labels listed in the `MetricDescriptor` must be assigned values.
     * </pre>
     *
     * <code>map&lt;string, string&gt; labels = 2;</code>
     */
    @java.lang.Override
    public boolean containsLabels(java.lang.String key) {
      java.lang.Class<?> keyClass = key.getClass();
      return instance.getLabelsMap().containsKey(key);
    }

    public Builder clearLabels() {
      copyOnWrite();
      instance.getMutableLabelsMap().clear();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The set of label values that uniquely identify this metric. All
     * labels listed in the `MetricDescriptor` must be assigned values.
     * </pre>
     *
     * <code>map&lt;string, string&gt; labels = 2;</code>
     */
    public Builder removeLabels(java.lang.String key) {
      java.lang.Class<?> keyClass = key.getClass();
      copyOnWrite();
      instance.getMutableLabelsMap().remove(key);
      return this;
    }
    /** Use {@link #getLabelsMap()} instead. */
    @java.lang.Override
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String> getLabels() {
      return getLabelsMap();
    }
    /**
     *
     *
     * <pre>
     * The set of label values that uniquely identify this metric. All
     * labels listed in the `MetricDescriptor` must be assigned values.
     * </pre>
     *
     * <code>map&lt;string, string&gt; labels = 2;</code>
     */
    @java.lang.Override
    public java.util.Map<java.lang.String, java.lang.String> getLabelsMap() {
      return java.util.Collections.unmodifiableMap(instance.getLabelsMap());
    }
    /**
     *
     *
     * <pre>
     * The set of label values that uniquely identify this metric. All
     * labels listed in the `MetricDescriptor` must be assigned values.
     * </pre>
     *
     * <code>map&lt;string, string&gt; labels = 2;</code>
     */
    @java.lang.Override
    public /* nullable */ java.lang.String getLabelsOrDefault(
        java.lang.String key,
        /* nullable */
        java.lang.String defaultValue) {
      java.lang.Class<?> keyClass = key.getClass();
      java.util.Map<java.lang.String, java.lang.String> map = instance.getLabelsMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     *
     *
     * <pre>
     * The set of label values that uniquely identify this metric. All
     * labels listed in the `MetricDescriptor` must be assigned values.
     * </pre>
     *
     * <code>map&lt;string, string&gt; labels = 2;</code>
     */
    @java.lang.Override
    public java.lang.String getLabelsOrThrow(java.lang.String key) {
      java.lang.Class<?> keyClass = key.getClass();
      java.util.Map<java.lang.String, java.lang.String> map = instance.getLabelsMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }
    /**
     *
     *
     * <pre>
     * The set of label values that uniquely identify this metric. All
     * labels listed in the `MetricDescriptor` must be assigned values.
     * </pre>
     *
     * <code>map&lt;string, string&gt; labels = 2;</code>
     */
    public Builder putLabels(java.lang.String key, java.lang.String value) {
      java.lang.Class<?> keyClass = key.getClass();
      java.lang.Class<?> valueClass = value.getClass();
      copyOnWrite();
      instance.getMutableLabelsMap().put(key, value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The set of label values that uniquely identify this metric. All
     * labels listed in the `MetricDescriptor` must be assigned values.
     * </pre>
     *
     * <code>map&lt;string, string&gt; labels = 2;</code>
     */
    public Builder putAllLabels(java.util.Map<java.lang.String, java.lang.String> values) {
      copyOnWrite();
      instance.getMutableLabelsMap().putAll(values);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.api.Metric)
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
          return new com.google.api.Metric();
        }
      case NEW_BUILDER:
        {
          return new Builder();
        }
      case BUILD_MESSAGE_INFO:
        {
          java.lang.Object[] objects =
              new java.lang.Object[] {
                "labels_", LabelsDefaultEntryHolder.defaultEntry, "type_",
              };
          java.lang.String info =
              "\u0000\u0002\u0000\u0000\u0002\u0003\u0002\u0001\u0000\u0000\u00022\u0003\u0208";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
      case GET_DEFAULT_INSTANCE:
        {
          return DEFAULT_INSTANCE;
        }
      case GET_PARSER:
        {
          com.google.protobuf.Parser<com.google.api.Metric> parser = PARSER;
          if (parser == null) {
            synchronized (com.google.api.Metric.class) {
              parser = PARSER;
              if (parser == null) {
                parser = new DefaultInstanceBasedParser<com.google.api.Metric>(DEFAULT_INSTANCE);
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

  // @@protoc_insertion_point(class_scope:google.api.Metric)
  private static final com.google.api.Metric DEFAULT_INSTANCE;

  static {
    Metric defaultInstance = new Metric();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(Metric.class, defaultInstance);
  }

  public static com.google.api.Metric getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<Metric> PARSER;

  public static com.google.protobuf.Parser<Metric> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
