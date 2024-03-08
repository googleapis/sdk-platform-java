// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/type/interval.proto

// Protobuf Java Version: 3.25.2
package com.google.type;

/**
 * <pre>
 * Represents a time interval, encoded as a Timestamp start (inclusive) and a
 * Timestamp end (exclusive).
 *
 * The start must be less than or equal to the end.
 * When the start equals the end, the interval is empty (matches no time).
 * When both start and end are unspecified, the interval matches any time.
 * </pre>
 *
 * Protobuf type {@code google.type.Interval}
 */
public final class Interval extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:google.type.Interval)
    IntervalOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Interval.newBuilder() to construct.
  private Interval(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Interval() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Interval();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.google.type.IntervalProto.internal_static_google_type_Interval_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.type.IntervalProto.internal_static_google_type_Interval_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.type.Interval.class, com.google.type.Interval.Builder.class);
  }

  private int bitField0_;
  public static final int START_TIME_FIELD_NUMBER = 1;
  private com.google.protobuf.Timestamp startTime_;
  /**
   * <pre>
   * Optional. Inclusive start of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be the same
   * or after the start.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp start_time = 1;</code>
   * @return Whether the startTime field is set.
   */
  @java.lang.Override
  public boolean hasStartTime() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <pre>
   * Optional. Inclusive start of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be the same
   * or after the start.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp start_time = 1;</code>
   * @return The startTime.
   */
  @java.lang.Override
  public com.google.protobuf.Timestamp getStartTime() {
    return startTime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : startTime_;
  }
  /**
   * <pre>
   * Optional. Inclusive start of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be the same
   * or after the start.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp start_time = 1;</code>
   */
  @java.lang.Override
  public com.google.protobuf.TimestampOrBuilder getStartTimeOrBuilder() {
    return startTime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : startTime_;
  }

  public static final int END_TIME_FIELD_NUMBER = 2;
  private com.google.protobuf.Timestamp endTime_;
  /**
   * <pre>
   * Optional. Exclusive end of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be before the
   * end.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp end_time = 2;</code>
   * @return Whether the endTime field is set.
   */
  @java.lang.Override
  public boolean hasEndTime() {
    return ((bitField0_ & 0x00000002) != 0);
  }
  /**
   * <pre>
   * Optional. Exclusive end of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be before the
   * end.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp end_time = 2;</code>
   * @return The endTime.
   */
  @java.lang.Override
  public com.google.protobuf.Timestamp getEndTime() {
    return endTime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : endTime_;
  }
  /**
   * <pre>
   * Optional. Exclusive end of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be before the
   * end.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp end_time = 2;</code>
   */
  @java.lang.Override
  public com.google.protobuf.TimestampOrBuilder getEndTimeOrBuilder() {
    return endTime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : endTime_;
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
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(1, getStartTime());
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      output.writeMessage(2, getEndTime());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getStartTime());
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getEndTime());
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
    if (!(obj instanceof com.google.type.Interval)) {
      return super.equals(obj);
    }
    com.google.type.Interval other = (com.google.type.Interval) obj;

    if (hasStartTime() != other.hasStartTime()) return false;
    if (hasStartTime()) {
      if (!getStartTime()
          .equals(other.getStartTime())) return false;
    }
    if (hasEndTime() != other.hasEndTime()) return false;
    if (hasEndTime()) {
      if (!getEndTime()
          .equals(other.getEndTime())) return false;
    }
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
    if (hasStartTime()) {
      hash = (37 * hash) + START_TIME_FIELD_NUMBER;
      hash = (53 * hash) + getStartTime().hashCode();
    }
    if (hasEndTime()) {
      hash = (37 * hash) + END_TIME_FIELD_NUMBER;
      hash = (53 * hash) + getEndTime().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.type.Interval parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.type.Interval parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.type.Interval parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.type.Interval parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.type.Interval parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.type.Interval parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.type.Interval parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.type.Interval parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.google.type.Interval parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.type.Interval parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.type.Interval parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.type.Interval parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.google.type.Interval prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * Represents a time interval, encoded as a Timestamp start (inclusive) and a
   * Timestamp end (exclusive).
   *
   * The start must be less than or equal to the end.
   * When the start equals the end, the interval is empty (matches no time).
   * When both start and end are unspecified, the interval matches any time.
   * </pre>
   *
   * Protobuf type {@code google.type.Interval}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:google.type.Interval)
      com.google.type.IntervalOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.google.type.IntervalProto.internal_static_google_type_Interval_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.type.IntervalProto.internal_static_google_type_Interval_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.type.Interval.class, com.google.type.Interval.Builder.class);
    }

    // Construct using com.google.type.Interval.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getStartTimeFieldBuilder();
        getEndTimeFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      startTime_ = null;
      if (startTimeBuilder_ != null) {
        startTimeBuilder_.dispose();
        startTimeBuilder_ = null;
      }
      endTime_ = null;
      if (endTimeBuilder_ != null) {
        endTimeBuilder_.dispose();
        endTimeBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.google.type.IntervalProto.internal_static_google_type_Interval_descriptor;
    }

    @java.lang.Override
    public com.google.type.Interval getDefaultInstanceForType() {
      return com.google.type.Interval.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.type.Interval build() {
      com.google.type.Interval result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.type.Interval buildPartial() {
      com.google.type.Interval result = new com.google.type.Interval(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.type.Interval result) {
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.startTime_ = startTimeBuilder_ == null
            ? startTime_
            : startTimeBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.endTime_ = endTimeBuilder_ == null
            ? endTime_
            : endTimeBuilder_.build();
        to_bitField0_ |= 0x00000002;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.google.type.Interval) {
        return mergeFrom((com.google.type.Interval)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.type.Interval other) {
      if (other == com.google.type.Interval.getDefaultInstance()) return this;
      if (other.hasStartTime()) {
        mergeStartTime(other.getStartTime());
      }
      if (other.hasEndTime()) {
        mergeEndTime(other.getEndTime());
      }
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
            case 10: {
              input.readMessage(
                  getStartTimeFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              input.readMessage(
                  getEndTimeFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000002;
              break;
            } // case 18
            default: {
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

    private com.google.protobuf.Timestamp startTime_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> startTimeBuilder_;
    /**
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     * @return Whether the startTime field is set.
     */
    public boolean hasStartTime() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     * @return The startTime.
     */
    public com.google.protobuf.Timestamp getStartTime() {
      if (startTimeBuilder_ == null) {
        return startTime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : startTime_;
      } else {
        return startTimeBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     */
    public Builder setStartTime(com.google.protobuf.Timestamp value) {
      if (startTimeBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        startTime_ = value;
      } else {
        startTimeBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     */
    public Builder setStartTime(
        com.google.protobuf.Timestamp.Builder builderForValue) {
      if (startTimeBuilder_ == null) {
        startTime_ = builderForValue.build();
      } else {
        startTimeBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     */
    public Builder mergeStartTime(com.google.protobuf.Timestamp value) {
      if (startTimeBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0) &&
          startTime_ != null &&
          startTime_ != com.google.protobuf.Timestamp.getDefaultInstance()) {
          getStartTimeBuilder().mergeFrom(value);
        } else {
          startTime_ = value;
        }
      } else {
        startTimeBuilder_.mergeFrom(value);
      }
      if (startTime_ != null) {
        bitField0_ |= 0x00000001;
        onChanged();
      }
      return this;
    }
    /**
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     */
    public Builder clearStartTime() {
      bitField0_ = (bitField0_ & ~0x00000001);
      startTime_ = null;
      if (startTimeBuilder_ != null) {
        startTimeBuilder_.dispose();
        startTimeBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     */
    public com.google.protobuf.Timestamp.Builder getStartTimeBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getStartTimeFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     */
    public com.google.protobuf.TimestampOrBuilder getStartTimeOrBuilder() {
      if (startTimeBuilder_ != null) {
        return startTimeBuilder_.getMessageOrBuilder();
      } else {
        return startTime_ == null ?
            com.google.protobuf.Timestamp.getDefaultInstance() : startTime_;
      }
    }
    /**
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> 
        getStartTimeFieldBuilder() {
      if (startTimeBuilder_ == null) {
        startTimeBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder>(
                getStartTime(),
                getParentForChildren(),
                isClean());
        startTime_ = null;
      }
      return startTimeBuilder_;
    }

    private com.google.protobuf.Timestamp endTime_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> endTimeBuilder_;
    /**
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     * @return Whether the endTime field is set.
     */
    public boolean hasEndTime() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     * @return The endTime.
     */
    public com.google.protobuf.Timestamp getEndTime() {
      if (endTimeBuilder_ == null) {
        return endTime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : endTime_;
      } else {
        return endTimeBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     */
    public Builder setEndTime(com.google.protobuf.Timestamp value) {
      if (endTimeBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        endTime_ = value;
      } else {
        endTimeBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     */
    public Builder setEndTime(
        com.google.protobuf.Timestamp.Builder builderForValue) {
      if (endTimeBuilder_ == null) {
        endTime_ = builderForValue.build();
      } else {
        endTimeBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     */
    public Builder mergeEndTime(com.google.protobuf.Timestamp value) {
      if (endTimeBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0) &&
          endTime_ != null &&
          endTime_ != com.google.protobuf.Timestamp.getDefaultInstance()) {
          getEndTimeBuilder().mergeFrom(value);
        } else {
          endTime_ = value;
        }
      } else {
        endTimeBuilder_.mergeFrom(value);
      }
      if (endTime_ != null) {
        bitField0_ |= 0x00000002;
        onChanged();
      }
      return this;
    }
    /**
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     */
    public Builder clearEndTime() {
      bitField0_ = (bitField0_ & ~0x00000002);
      endTime_ = null;
      if (endTimeBuilder_ != null) {
        endTimeBuilder_.dispose();
        endTimeBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     */
    public com.google.protobuf.Timestamp.Builder getEndTimeBuilder() {
      bitField0_ |= 0x00000002;
      onChanged();
      return getEndTimeFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     */
    public com.google.protobuf.TimestampOrBuilder getEndTimeOrBuilder() {
      if (endTimeBuilder_ != null) {
        return endTimeBuilder_.getMessageOrBuilder();
      } else {
        return endTime_ == null ?
            com.google.protobuf.Timestamp.getDefaultInstance() : endTime_;
      }
    }
    /**
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> 
        getEndTimeFieldBuilder() {
      if (endTimeBuilder_ == null) {
        endTimeBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder>(
                getEndTime(),
                getParentForChildren(),
                isClean());
        endTime_ = null;
      }
      return endTimeBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:google.type.Interval)
  }

  // @@protoc_insertion_point(class_scope:google.type.Interval)
  private static final com.google.type.Interval DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.google.type.Interval();
  }

  public static com.google.type.Interval getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Interval>
      PARSER = new com.google.protobuf.AbstractParser<Interval>() {
    @java.lang.Override
    public Interval parsePartialFrom(
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

  public static com.google.protobuf.Parser<Interval> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Interval> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.type.Interval getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
