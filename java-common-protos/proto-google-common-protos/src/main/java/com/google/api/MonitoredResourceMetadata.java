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
// source: google/api/monitored_resource.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

/**
 *
 *
 * <pre>
 * Auxiliary metadata for a [MonitoredResource][google.api.MonitoredResource]
 * object. [MonitoredResource][google.api.MonitoredResource] objects contain the
 * minimum set of information to uniquely identify a monitored resource
 * instance. There is some other useful auxiliary metadata. Monitoring and
 * Logging use an ingestion pipeline to extract metadata for cloud resources of
 * all types, and store the metadata in this message.
 * </pre>
 *
 * Protobuf type {@code google.api.MonitoredResourceMetadata}
 */
public final class MonitoredResourceMetadata extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.api.MonitoredResourceMetadata)
    MonitoredResourceMetadataOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use MonitoredResourceMetadata.newBuilder() to construct.
  private MonitoredResourceMetadata(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private MonitoredResourceMetadata() {}

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new MonitoredResourceMetadata();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.api.MonitoredResourceProto
        .internal_static_google_api_MonitoredResourceMetadata_descriptor;
  }

  @SuppressWarnings({"rawtypes"})
  @java.lang.Override
  protected com.google.protobuf.MapFieldReflectionAccessor internalGetMapFieldReflection(
      int number) {
    switch (number) {
      case 2:
        return internalGetUserLabels();
      default:
        throw new RuntimeException("Invalid map field number: " + number);
    }
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.api.MonitoredResourceProto
        .internal_static_google_api_MonitoredResourceMetadata_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.api.MonitoredResourceMetadata.class,
            com.google.api.MonitoredResourceMetadata.Builder.class);
  }

  private int bitField0_;
  public static final int SYSTEM_LABELS_FIELD_NUMBER = 1;
  private com.google.protobuf.Struct systemLabels_;
  /**
   *
   *
   * <pre>
   * Output only. Values for predefined system metadata labels.
   * System labels are a kind of metadata extracted by Google, including
   * "machine_image", "vpc", "subnet_id",
   * "security_group", "name", etc.
   * System label values can be only strings, Boolean values, or a list of
   * strings. For example:
   *
   *     { "name": "my-test-instance",
   *       "security_group": ["a", "b", "c"],
   *       "spot_instance": false }
   * </pre>
   *
   * <code>.google.protobuf.Struct system_labels = 1;</code>
   *
   * @return Whether the systemLabels field is set.
   */
  @java.lang.Override
  public boolean hasSystemLabels() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   *
   *
   * <pre>
   * Output only. Values for predefined system metadata labels.
   * System labels are a kind of metadata extracted by Google, including
   * "machine_image", "vpc", "subnet_id",
   * "security_group", "name", etc.
   * System label values can be only strings, Boolean values, or a list of
   * strings. For example:
   *
   *     { "name": "my-test-instance",
   *       "security_group": ["a", "b", "c"],
   *       "spot_instance": false }
   * </pre>
   *
   * <code>.google.protobuf.Struct system_labels = 1;</code>
   *
   * @return The systemLabels.
   */
  @java.lang.Override
  public com.google.protobuf.Struct getSystemLabels() {
    return systemLabels_ == null ? com.google.protobuf.Struct.getDefaultInstance() : systemLabels_;
  }
  /**
   *
   *
   * <pre>
   * Output only. Values for predefined system metadata labels.
   * System labels are a kind of metadata extracted by Google, including
   * "machine_image", "vpc", "subnet_id",
   * "security_group", "name", etc.
   * System label values can be only strings, Boolean values, or a list of
   * strings. For example:
   *
   *     { "name": "my-test-instance",
   *       "security_group": ["a", "b", "c"],
   *       "spot_instance": false }
   * </pre>
   *
   * <code>.google.protobuf.Struct system_labels = 1;</code>
   */
  @java.lang.Override
  public com.google.protobuf.StructOrBuilder getSystemLabelsOrBuilder() {
    return systemLabels_ == null ? com.google.protobuf.Struct.getDefaultInstance() : systemLabels_;
  }

  public static final int USER_LABELS_FIELD_NUMBER = 2;

  private static final class UserLabelsDefaultEntryHolder {
    static final com.google.protobuf.MapEntry<java.lang.String, java.lang.String> defaultEntry =
        com.google.protobuf.MapEntry.<java.lang.String, java.lang.String>newDefaultInstance(
            com.google.api.MonitoredResourceProto
                .internal_static_google_api_MonitoredResourceMetadata_UserLabelsEntry_descriptor,
            com.google.protobuf.WireFormat.FieldType.STRING,
            "",
            com.google.protobuf.WireFormat.FieldType.STRING,
            "");
  }

  @SuppressWarnings("serial")
  private com.google.protobuf.MapField<java.lang.String, java.lang.String> userLabels_;

  private com.google.protobuf.MapField<java.lang.String, java.lang.String> internalGetUserLabels() {
    if (userLabels_ == null) {
      return com.google.protobuf.MapField.emptyMapField(UserLabelsDefaultEntryHolder.defaultEntry);
    }
    return userLabels_;
  }

  public int getUserLabelsCount() {
    return internalGetUserLabels().getMap().size();
  }
  /**
   *
   *
   * <pre>
   * Output only. A map of user-defined metadata labels.
   * </pre>
   *
   * <code>map&lt;string, string&gt; user_labels = 2;</code>
   */
  @java.lang.Override
  public boolean containsUserLabels(java.lang.String key) {
    if (key == null) {
      throw new NullPointerException("map key");
    }
    return internalGetUserLabels().getMap().containsKey(key);
  }
  /** Use {@link #getUserLabelsMap()} instead. */
  @java.lang.Override
  @java.lang.Deprecated
  public java.util.Map<java.lang.String, java.lang.String> getUserLabels() {
    return getUserLabelsMap();
  }
  /**
   *
   *
   * <pre>
   * Output only. A map of user-defined metadata labels.
   * </pre>
   *
   * <code>map&lt;string, string&gt; user_labels = 2;</code>
   */
  @java.lang.Override
  public java.util.Map<java.lang.String, java.lang.String> getUserLabelsMap() {
    return internalGetUserLabels().getMap();
  }
  /**
   *
   *
   * <pre>
   * Output only. A map of user-defined metadata labels.
   * </pre>
   *
   * <code>map&lt;string, string&gt; user_labels = 2;</code>
   */
  @java.lang.Override
  public /* nullable */ java.lang.String getUserLabelsOrDefault(
      java.lang.String key,
      /* nullable */
      java.lang.String defaultValue) {
    if (key == null) {
      throw new NullPointerException("map key");
    }
    java.util.Map<java.lang.String, java.lang.String> map = internalGetUserLabels().getMap();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
  /**
   *
   *
   * <pre>
   * Output only. A map of user-defined metadata labels.
   * </pre>
   *
   * <code>map&lt;string, string&gt; user_labels = 2;</code>
   */
  @java.lang.Override
  public java.lang.String getUserLabelsOrThrow(java.lang.String key) {
    if (key == null) {
      throw new NullPointerException("map key");
    }
    java.util.Map<java.lang.String, java.lang.String> map = internalGetUserLabels().getMap();
    if (!map.containsKey(key)) {
      throw new java.lang.IllegalArgumentException();
    }
    return map.get(key);
  }

  private byte memoizedIsInitialized = -1;

  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(1, getSystemLabels());
    }
    com.google.protobuf.GeneratedMessageV3.serializeStringMapTo(
        output, internalGetUserLabels(), UserLabelsDefaultEntryHolder.defaultEntry, 2);
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getSystemLabels());
    }
    for (java.util.Map.Entry<java.lang.String, java.lang.String> entry :
        internalGetUserLabels().getMap().entrySet()) {
      com.google.protobuf.MapEntry<java.lang.String, java.lang.String> userLabels__ =
          UserLabelsDefaultEntryHolder.defaultEntry
              .newBuilderForType()
              .setKey(entry.getKey())
              .setValue(entry.getValue())
              .build();
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, userLabels__);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof com.google.api.MonitoredResourceMetadata)) {
      return super.equals(obj);
    }
    com.google.api.MonitoredResourceMetadata other = (com.google.api.MonitoredResourceMetadata) obj;

    if (hasSystemLabels() != other.hasSystemLabels()) return false;
    if (hasSystemLabels()) {
      if (!getSystemLabels().equals(other.getSystemLabels())) return false;
    }
    if (!internalGetUserLabels().equals(other.internalGetUserLabels())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasSystemLabels()) {
      hash = (37 * hash) + SYSTEM_LABELS_FIELD_NUMBER;
      hash = (53 * hash) + getSystemLabels().hashCode();
    }
    if (!internalGetUserLabels().getMap().isEmpty()) {
      hash = (37 * hash) + USER_LABELS_FIELD_NUMBER;
      hash = (53 * hash) + internalGetUserLabels().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.api.MonitoredResourceMetadata parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.MonitoredResourceMetadata parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.MonitoredResourceMetadata parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.MonitoredResourceMetadata parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.MonitoredResourceMetadata parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.MonitoredResourceMetadata parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.MonitoredResourceMetadata parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.api.MonitoredResourceMetadata parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.api.MonitoredResourceMetadata parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.api.MonitoredResourceMetadata parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.api.MonitoredResourceMetadata parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.api.MonitoredResourceMetadata parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() {
    return newBuilder();
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(com.google.api.MonitoredResourceMetadata prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   *
   *
   * <pre>
   * Auxiliary metadata for a [MonitoredResource][google.api.MonitoredResource]
   * object. [MonitoredResource][google.api.MonitoredResource] objects contain the
   * minimum set of information to uniquely identify a monitored resource
   * instance. There is some other useful auxiliary metadata. Monitoring and
   * Logging use an ingestion pipeline to extract metadata for cloud resources of
   * all types, and store the metadata in this message.
   * </pre>
   *
   * Protobuf type {@code google.api.MonitoredResourceMetadata}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.MonitoredResourceMetadata)
      com.google.api.MonitoredResourceMetadataOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.api.MonitoredResourceProto
          .internal_static_google_api_MonitoredResourceMetadata_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapFieldReflectionAccessor internalGetMapFieldReflection(
        int number) {
      switch (number) {
        case 2:
          return internalGetUserLabels();
        default:
          throw new RuntimeException("Invalid map field number: " + number);
      }
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapFieldReflectionAccessor internalGetMutableMapFieldReflection(
        int number) {
      switch (number) {
        case 2:
          return internalGetMutableUserLabels();
        default:
          throw new RuntimeException("Invalid map field number: " + number);
      }
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.api.MonitoredResourceProto
          .internal_static_google_api_MonitoredResourceMetadata_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.api.MonitoredResourceMetadata.class,
              com.google.api.MonitoredResourceMetadata.Builder.class);
    }

    // Construct using com.google.api.MonitoredResourceMetadata.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
        getSystemLabelsFieldBuilder();
      }
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      systemLabels_ = null;
      if (systemLabelsBuilder_ != null) {
        systemLabelsBuilder_.dispose();
        systemLabelsBuilder_ = null;
      }
      internalGetMutableUserLabels().clear();
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.api.MonitoredResourceProto
          .internal_static_google_api_MonitoredResourceMetadata_descriptor;
    }

    @java.lang.Override
    public com.google.api.MonitoredResourceMetadata getDefaultInstanceForType() {
      return com.google.api.MonitoredResourceMetadata.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.api.MonitoredResourceMetadata build() {
      com.google.api.MonitoredResourceMetadata result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.api.MonitoredResourceMetadata buildPartial() {
      com.google.api.MonitoredResourceMetadata result =
          new com.google.api.MonitoredResourceMetadata(this);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.api.MonitoredResourceMetadata result) {
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.systemLabels_ =
            systemLabelsBuilder_ == null ? systemLabels_ : systemLabelsBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.userLabels_ = internalGetUserLabels();
        result.userLabels_.makeImmutable();
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }

    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
      return super.setField(field, value);
    }

    @java.lang.Override
    public Builder clearField(com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }

    @java.lang.Override
    public Builder clearOneof(com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }

    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field, int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }

    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.google.api.MonitoredResourceMetadata) {
        return mergeFrom((com.google.api.MonitoredResourceMetadata) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.api.MonitoredResourceMetadata other) {
      if (other == com.google.api.MonitoredResourceMetadata.getDefaultInstance()) return this;
      if (other.hasSystemLabels()) {
        mergeSystemLabels(other.getSystemLabels());
      }
      internalGetMutableUserLabels().mergeFrom(other.internalGetUserLabels());
      bitField0_ |= 0x00000002;
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10:
              {
                input.readMessage(getSystemLabelsFieldBuilder().getBuilder(), extensionRegistry);
                bitField0_ |= 0x00000001;
                break;
              } // case 10
            case 18:
              {
                com.google.protobuf.MapEntry<java.lang.String, java.lang.String> userLabels__ =
                    input.readMessage(
                        UserLabelsDefaultEntryHolder.defaultEntry.getParserForType(),
                        extensionRegistry);
                internalGetMutableUserLabels()
                    .getMutableMap()
                    .put(userLabels__.getKey(), userLabels__.getValue());
                bitField0_ |= 0x00000002;
                break;
              } // case 18
            default:
              {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }

    private int bitField0_;

    private com.google.protobuf.Struct systemLabels_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Struct,
            com.google.protobuf.Struct.Builder,
            com.google.protobuf.StructOrBuilder>
        systemLabelsBuilder_;
    /**
     *
     *
     * <pre>
     * Output only. Values for predefined system metadata labels.
     * System labels are a kind of metadata extracted by Google, including
     * "machine_image", "vpc", "subnet_id",
     * "security_group", "name", etc.
     * System label values can be only strings, Boolean values, or a list of
     * strings. For example:
     *
     *     { "name": "my-test-instance",
     *       "security_group": ["a", "b", "c"],
     *       "spot_instance": false }
     * </pre>
     *
     * <code>.google.protobuf.Struct system_labels = 1;</code>
     *
     * @return Whether the systemLabels field is set.
     */
    public boolean hasSystemLabels() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     *
     *
     * <pre>
     * Output only. Values for predefined system metadata labels.
     * System labels are a kind of metadata extracted by Google, including
     * "machine_image", "vpc", "subnet_id",
     * "security_group", "name", etc.
     * System label values can be only strings, Boolean values, or a list of
     * strings. For example:
     *
     *     { "name": "my-test-instance",
     *       "security_group": ["a", "b", "c"],
     *       "spot_instance": false }
     * </pre>
     *
     * <code>.google.protobuf.Struct system_labels = 1;</code>
     *
     * @return The systemLabels.
     */
    public com.google.protobuf.Struct getSystemLabels() {
      if (systemLabelsBuilder_ == null) {
        return systemLabels_ == null
            ? com.google.protobuf.Struct.getDefaultInstance()
            : systemLabels_;
      } else {
        return systemLabelsBuilder_.getMessage();
      }
    }
    /**
     *
     *
     * <pre>
     * Output only. Values for predefined system metadata labels.
     * System labels are a kind of metadata extracted by Google, including
     * "machine_image", "vpc", "subnet_id",
     * "security_group", "name", etc.
     * System label values can be only strings, Boolean values, or a list of
     * strings. For example:
     *
     *     { "name": "my-test-instance",
     *       "security_group": ["a", "b", "c"],
     *       "spot_instance": false }
     * </pre>
     *
     * <code>.google.protobuf.Struct system_labels = 1;</code>
     */
    public Builder setSystemLabels(com.google.protobuf.Struct value) {
      if (systemLabelsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        systemLabels_ = value;
      } else {
        systemLabelsBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Output only. Values for predefined system metadata labels.
     * System labels are a kind of metadata extracted by Google, including
     * "machine_image", "vpc", "subnet_id",
     * "security_group", "name", etc.
     * System label values can be only strings, Boolean values, or a list of
     * strings. For example:
     *
     *     { "name": "my-test-instance",
     *       "security_group": ["a", "b", "c"],
     *       "spot_instance": false }
     * </pre>
     *
     * <code>.google.protobuf.Struct system_labels = 1;</code>
     */
    public Builder setSystemLabels(com.google.protobuf.Struct.Builder builderForValue) {
      if (systemLabelsBuilder_ == null) {
        systemLabels_ = builderForValue.build();
      } else {
        systemLabelsBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Output only. Values for predefined system metadata labels.
     * System labels are a kind of metadata extracted by Google, including
     * "machine_image", "vpc", "subnet_id",
     * "security_group", "name", etc.
     * System label values can be only strings, Boolean values, or a list of
     * strings. For example:
     *
     *     { "name": "my-test-instance",
     *       "security_group": ["a", "b", "c"],
     *       "spot_instance": false }
     * </pre>
     *
     * <code>.google.protobuf.Struct system_labels = 1;</code>
     */
    public Builder mergeSystemLabels(com.google.protobuf.Struct value) {
      if (systemLabelsBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)
            && systemLabels_ != null
            && systemLabels_ != com.google.protobuf.Struct.getDefaultInstance()) {
          getSystemLabelsBuilder().mergeFrom(value);
        } else {
          systemLabels_ = value;
        }
      } else {
        systemLabelsBuilder_.mergeFrom(value);
      }
      if (systemLabels_ != null) {
        bitField0_ |= 0x00000001;
        onChanged();
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * Output only. Values for predefined system metadata labels.
     * System labels are a kind of metadata extracted by Google, including
     * "machine_image", "vpc", "subnet_id",
     * "security_group", "name", etc.
     * System label values can be only strings, Boolean values, or a list of
     * strings. For example:
     *
     *     { "name": "my-test-instance",
     *       "security_group": ["a", "b", "c"],
     *       "spot_instance": false }
     * </pre>
     *
     * <code>.google.protobuf.Struct system_labels = 1;</code>
     */
    public Builder clearSystemLabels() {
      bitField0_ = (bitField0_ & ~0x00000001);
      systemLabels_ = null;
      if (systemLabelsBuilder_ != null) {
        systemLabelsBuilder_.dispose();
        systemLabelsBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Output only. Values for predefined system metadata labels.
     * System labels are a kind of metadata extracted by Google, including
     * "machine_image", "vpc", "subnet_id",
     * "security_group", "name", etc.
     * System label values can be only strings, Boolean values, or a list of
     * strings. For example:
     *
     *     { "name": "my-test-instance",
     *       "security_group": ["a", "b", "c"],
     *       "spot_instance": false }
     * </pre>
     *
     * <code>.google.protobuf.Struct system_labels = 1;</code>
     */
    public com.google.protobuf.Struct.Builder getSystemLabelsBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getSystemLabelsFieldBuilder().getBuilder();
    }
    /**
     *
     *
     * <pre>
     * Output only. Values for predefined system metadata labels.
     * System labels are a kind of metadata extracted by Google, including
     * "machine_image", "vpc", "subnet_id",
     * "security_group", "name", etc.
     * System label values can be only strings, Boolean values, or a list of
     * strings. For example:
     *
     *     { "name": "my-test-instance",
     *       "security_group": ["a", "b", "c"],
     *       "spot_instance": false }
     * </pre>
     *
     * <code>.google.protobuf.Struct system_labels = 1;</code>
     */
    public com.google.protobuf.StructOrBuilder getSystemLabelsOrBuilder() {
      if (systemLabelsBuilder_ != null) {
        return systemLabelsBuilder_.getMessageOrBuilder();
      } else {
        return systemLabels_ == null
            ? com.google.protobuf.Struct.getDefaultInstance()
            : systemLabels_;
      }
    }
    /**
     *
     *
     * <pre>
     * Output only. Values for predefined system metadata labels.
     * System labels are a kind of metadata extracted by Google, including
     * "machine_image", "vpc", "subnet_id",
     * "security_group", "name", etc.
     * System label values can be only strings, Boolean values, or a list of
     * strings. For example:
     *
     *     { "name": "my-test-instance",
     *       "security_group": ["a", "b", "c"],
     *       "spot_instance": false }
     * </pre>
     *
     * <code>.google.protobuf.Struct system_labels = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Struct,
            com.google.protobuf.Struct.Builder,
            com.google.protobuf.StructOrBuilder>
        getSystemLabelsFieldBuilder() {
      if (systemLabelsBuilder_ == null) {
        systemLabelsBuilder_ =
            new com.google.protobuf.SingleFieldBuilderV3<
                com.google.protobuf.Struct,
                com.google.protobuf.Struct.Builder,
                com.google.protobuf.StructOrBuilder>(
                getSystemLabels(), getParentForChildren(), isClean());
        systemLabels_ = null;
      }
      return systemLabelsBuilder_;
    }

    private com.google.protobuf.MapField<java.lang.String, java.lang.String> userLabels_;

    private com.google.protobuf.MapField<java.lang.String, java.lang.String>
        internalGetUserLabels() {
      if (userLabels_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            UserLabelsDefaultEntryHolder.defaultEntry);
      }
      return userLabels_;
    }

    private com.google.protobuf.MapField<java.lang.String, java.lang.String>
        internalGetMutableUserLabels() {
      if (userLabels_ == null) {
        userLabels_ =
            com.google.protobuf.MapField.newMapField(UserLabelsDefaultEntryHolder.defaultEntry);
      }
      if (!userLabels_.isMutable()) {
        userLabels_ = userLabels_.copy();
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return userLabels_;
    }

    public int getUserLabelsCount() {
      return internalGetUserLabels().getMap().size();
    }
    /**
     *
     *
     * <pre>
     * Output only. A map of user-defined metadata labels.
     * </pre>
     *
     * <code>map&lt;string, string&gt; user_labels = 2;</code>
     */
    @java.lang.Override
    public boolean containsUserLabels(java.lang.String key) {
      if (key == null) {
        throw new NullPointerException("map key");
      }
      return internalGetUserLabels().getMap().containsKey(key);
    }
    /** Use {@link #getUserLabelsMap()} instead. */
    @java.lang.Override
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String> getUserLabels() {
      return getUserLabelsMap();
    }
    /**
     *
     *
     * <pre>
     * Output only. A map of user-defined metadata labels.
     * </pre>
     *
     * <code>map&lt;string, string&gt; user_labels = 2;</code>
     */
    @java.lang.Override
    public java.util.Map<java.lang.String, java.lang.String> getUserLabelsMap() {
      return internalGetUserLabels().getMap();
    }
    /**
     *
     *
     * <pre>
     * Output only. A map of user-defined metadata labels.
     * </pre>
     *
     * <code>map&lt;string, string&gt; user_labels = 2;</code>
     */
    @java.lang.Override
    public /* nullable */ java.lang.String getUserLabelsOrDefault(
        java.lang.String key,
        /* nullable */
        java.lang.String defaultValue) {
      if (key == null) {
        throw new NullPointerException("map key");
      }
      java.util.Map<java.lang.String, java.lang.String> map = internalGetUserLabels().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     *
     *
     * <pre>
     * Output only. A map of user-defined metadata labels.
     * </pre>
     *
     * <code>map&lt;string, string&gt; user_labels = 2;</code>
     */
    @java.lang.Override
    public java.lang.String getUserLabelsOrThrow(java.lang.String key) {
      if (key == null) {
        throw new NullPointerException("map key");
      }
      java.util.Map<java.lang.String, java.lang.String> map = internalGetUserLabels().getMap();
      if (!map.containsKey(key)) {
        throw new java.lang.IllegalArgumentException();
      }
      return map.get(key);
    }

    public Builder clearUserLabels() {
      bitField0_ = (bitField0_ & ~0x00000002);
      internalGetMutableUserLabels().getMutableMap().clear();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Output only. A map of user-defined metadata labels.
     * </pre>
     *
     * <code>map&lt;string, string&gt; user_labels = 2;</code>
     */
    public Builder removeUserLabels(java.lang.String key) {
      if (key == null) {
        throw new NullPointerException("map key");
      }
      internalGetMutableUserLabels().getMutableMap().remove(key);
      return this;
    }
    /** Use alternate mutation accessors instead. */
    @java.lang.Deprecated
    public java.util.Map<java.lang.String, java.lang.String> getMutableUserLabels() {
      bitField0_ |= 0x00000002;
      return internalGetMutableUserLabels().getMutableMap();
    }
    /**
     *
     *
     * <pre>
     * Output only. A map of user-defined metadata labels.
     * </pre>
     *
     * <code>map&lt;string, string&gt; user_labels = 2;</code>
     */
    public Builder putUserLabels(java.lang.String key, java.lang.String value) {
      if (key == null) {
        throw new NullPointerException("map key");
      }
      if (value == null) {
        throw new NullPointerException("map value");
      }
      internalGetMutableUserLabels().getMutableMap().put(key, value);
      bitField0_ |= 0x00000002;
      return this;
    }
    /**
     *
     *
     * <pre>
     * Output only. A map of user-defined metadata labels.
     * </pre>
     *
     * <code>map&lt;string, string&gt; user_labels = 2;</code>
     */
    public Builder putAllUserLabels(java.util.Map<java.lang.String, java.lang.String> values) {
      internalGetMutableUserLabels().getMutableMap().putAll(values);
      bitField0_ |= 0x00000002;
      return this;
    }

    @java.lang.Override
    public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }

    // @@protoc_insertion_point(builder_scope:google.api.MonitoredResourceMetadata)
  }

  // @@protoc_insertion_point(class_scope:google.api.MonitoredResourceMetadata)
  private static final com.google.api.MonitoredResourceMetadata DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.api.MonitoredResourceMetadata();
  }

  public static com.google.api.MonitoredResourceMetadata getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MonitoredResourceMetadata> PARSER =
      new com.google.protobuf.AbstractParser<MonitoredResourceMetadata>() {
        @java.lang.Override
        public MonitoredResourceMetadata parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          Builder builder = newBuilder();
          try {
            builder.mergeFrom(input, extensionRegistry);
          } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(builder.buildPartial());
          } catch (com.google.protobuf.UninitializedMessageException e) {
            throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
          } catch (java.io.IOException e) {
            throw new com.google.protobuf.InvalidProtocolBufferException(e)
                .setUnfinishedMessage(builder.buildPartial());
          }
          return builder.buildPartial();
        }
      };

  public static com.google.protobuf.Parser<MonitoredResourceMetadata> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MonitoredResourceMetadata> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.api.MonitoredResourceMetadata getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
