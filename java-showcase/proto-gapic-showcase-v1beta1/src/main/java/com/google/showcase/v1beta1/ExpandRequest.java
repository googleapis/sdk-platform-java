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
// source: schema/google/showcase/v1beta1/echo.proto

// Protobuf Java Version: 3.25.8
package com.google.showcase.v1beta1;

/**
 *
 *
 * <pre>
 * The request message for the Expand method.
 * </pre>
 *
 * Protobuf type {@code google.showcase.v1beta1.ExpandRequest}
 */
public final class ExpandRequest extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.showcase.v1beta1.ExpandRequest)
    ExpandRequestOrBuilder {
  private static final long serialVersionUID = 0L;

  // Use ExpandRequest.newBuilder() to construct.
  private ExpandRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private ExpandRequest() {
    content_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new ExpandRequest();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.showcase.v1beta1.EchoOuterClass
        .internal_static_google_showcase_v1beta1_ExpandRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.showcase.v1beta1.EchoOuterClass
        .internal_static_google_showcase_v1beta1_ExpandRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.showcase.v1beta1.ExpandRequest.class,
            com.google.showcase.v1beta1.ExpandRequest.Builder.class);
  }

  private int bitField0_;
  public static final int CONTENT_FIELD_NUMBER = 1;

  @SuppressWarnings("serial")
  private volatile java.lang.Object content_ = "";

  /**
   *
   *
   * <pre>
   * The content that will be split into words and returned on the stream.
   * </pre>
   *
   * <code>string content = 1;</code>
   *
   * @return The content.
   */
  @java.lang.Override
  public java.lang.String getContent() {
    java.lang.Object ref = content_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      content_ = s;
      return s;
    }
  }

  /**
   *
   *
   * <pre>
   * The content that will be split into words and returned on the stream.
   * </pre>
   *
   * <code>string content = 1;</code>
   *
   * @return The bytes for content.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getContentBytes() {
    java.lang.Object ref = content_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      content_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ERROR_FIELD_NUMBER = 2;
  private com.google.rpc.Status error_;

  /**
   *
   *
   * <pre>
   * The error that is thrown after all words are sent on the stream.
   * </pre>
   *
   * <code>.google.rpc.Status error = 2;</code>
   *
   * @return Whether the error field is set.
   */
  @java.lang.Override
  public boolean hasError() {
    return ((bitField0_ & 0x00000001) != 0);
  }

  /**
   *
   *
   * <pre>
   * The error that is thrown after all words are sent on the stream.
   * </pre>
   *
   * <code>.google.rpc.Status error = 2;</code>
   *
   * @return The error.
   */
  @java.lang.Override
  public com.google.rpc.Status getError() {
    return error_ == null ? com.google.rpc.Status.getDefaultInstance() : error_;
  }

  /**
   *
   *
   * <pre>
   * The error that is thrown after all words are sent on the stream.
   * </pre>
   *
   * <code>.google.rpc.Status error = 2;</code>
   */
  @java.lang.Override
  public com.google.rpc.StatusOrBuilder getErrorOrBuilder() {
    return error_ == null ? com.google.rpc.Status.getDefaultInstance() : error_;
  }

  public static final int STREAM_WAIT_TIME_FIELD_NUMBER = 3;
  private com.google.protobuf.Duration streamWaitTime_;

  /**
   *
   *
   * <pre>
   * The wait time between each server streaming messages
   * </pre>
   *
   * <code>.google.protobuf.Duration stream_wait_time = 3;</code>
   *
   * @return Whether the streamWaitTime field is set.
   */
  @java.lang.Override
  public boolean hasStreamWaitTime() {
    return ((bitField0_ & 0x00000002) != 0);
  }

  /**
   *
   *
   * <pre>
   * The wait time between each server streaming messages
   * </pre>
   *
   * <code>.google.protobuf.Duration stream_wait_time = 3;</code>
   *
   * @return The streamWaitTime.
   */
  @java.lang.Override
  public com.google.protobuf.Duration getStreamWaitTime() {
    return streamWaitTime_ == null
        ? com.google.protobuf.Duration.getDefaultInstance()
        : streamWaitTime_;
  }

  /**
   *
   *
   * <pre>
   * The wait time between each server streaming messages
   * </pre>
   *
   * <code>.google.protobuf.Duration stream_wait_time = 3;</code>
   */
  @java.lang.Override
  public com.google.protobuf.DurationOrBuilder getStreamWaitTimeOrBuilder() {
    return streamWaitTime_ == null
        ? com.google.protobuf.Duration.getDefaultInstance()
        : streamWaitTime_;
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
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(content_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, content_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(2, getError());
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      output.writeMessage(3, getStreamWaitTime());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(content_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, content_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getError());
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(3, getStreamWaitTime());
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
    if (!(obj instanceof com.google.showcase.v1beta1.ExpandRequest)) {
      return super.equals(obj);
    }
    com.google.showcase.v1beta1.ExpandRequest other =
        (com.google.showcase.v1beta1.ExpandRequest) obj;

    if (!getContent().equals(other.getContent())) return false;
    if (hasError() != other.hasError()) return false;
    if (hasError()) {
      if (!getError().equals(other.getError())) return false;
    }
    if (hasStreamWaitTime() != other.hasStreamWaitTime()) return false;
    if (hasStreamWaitTime()) {
      if (!getStreamWaitTime().equals(other.getStreamWaitTime())) return false;
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
    hash = (37 * hash) + CONTENT_FIELD_NUMBER;
    hash = (53 * hash) + getContent().hashCode();
    if (hasError()) {
      hash = (37 * hash) + ERROR_FIELD_NUMBER;
      hash = (53 * hash) + getError().hashCode();
    }
    if (hasStreamWaitTime()) {
      hash = (37 * hash) + STREAM_WAIT_TIME_FIELD_NUMBER;
      hash = (53 * hash) + getStreamWaitTime().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.showcase.v1beta1.ExpandRequest parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.showcase.v1beta1.ExpandRequest parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.ExpandRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.showcase.v1beta1.ExpandRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.ExpandRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.showcase.v1beta1.ExpandRequest parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.ExpandRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.ExpandRequest parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.ExpandRequest parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.ExpandRequest parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.ExpandRequest parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.ExpandRequest parseFrom(
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

  public static Builder newBuilder(com.google.showcase.v1beta1.ExpandRequest prototype) {
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
   * The request message for the Expand method.
   * </pre>
   *
   * Protobuf type {@code google.showcase.v1beta1.ExpandRequest}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.showcase.v1beta1.ExpandRequest)
      com.google.showcase.v1beta1.ExpandRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.showcase.v1beta1.EchoOuterClass
          .internal_static_google_showcase_v1beta1_ExpandRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.showcase.v1beta1.EchoOuterClass
          .internal_static_google_showcase_v1beta1_ExpandRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.showcase.v1beta1.ExpandRequest.class,
              com.google.showcase.v1beta1.ExpandRequest.Builder.class);
    }

    // Construct using com.google.showcase.v1beta1.ExpandRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
        getErrorFieldBuilder();
        getStreamWaitTimeFieldBuilder();
      }
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      content_ = "";
      error_ = null;
      if (errorBuilder_ != null) {
        errorBuilder_.dispose();
        errorBuilder_ = null;
      }
      streamWaitTime_ = null;
      if (streamWaitTimeBuilder_ != null) {
        streamWaitTimeBuilder_.dispose();
        streamWaitTimeBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.showcase.v1beta1.EchoOuterClass
          .internal_static_google_showcase_v1beta1_ExpandRequest_descriptor;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.ExpandRequest getDefaultInstanceForType() {
      return com.google.showcase.v1beta1.ExpandRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.ExpandRequest build() {
      com.google.showcase.v1beta1.ExpandRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.ExpandRequest buildPartial() {
      com.google.showcase.v1beta1.ExpandRequest result =
          new com.google.showcase.v1beta1.ExpandRequest(this);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.showcase.v1beta1.ExpandRequest result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.content_ = content_;
      }
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.error_ = errorBuilder_ == null ? error_ : errorBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.streamWaitTime_ =
            streamWaitTimeBuilder_ == null ? streamWaitTime_ : streamWaitTimeBuilder_.build();
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
      if (other instanceof com.google.showcase.v1beta1.ExpandRequest) {
        return mergeFrom((com.google.showcase.v1beta1.ExpandRequest) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.showcase.v1beta1.ExpandRequest other) {
      if (other == com.google.showcase.v1beta1.ExpandRequest.getDefaultInstance()) return this;
      if (!other.getContent().isEmpty()) {
        content_ = other.content_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (other.hasError()) {
        mergeError(other.getError());
      }
      if (other.hasStreamWaitTime()) {
        mergeStreamWaitTime(other.getStreamWaitTime());
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
            case 10:
              {
                content_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000001;
                break;
              } // case 10
            case 18:
              {
                input.readMessage(getErrorFieldBuilder().getBuilder(), extensionRegistry);
                bitField0_ |= 0x00000002;
                break;
              } // case 18
            case 26:
              {
                input.readMessage(getStreamWaitTimeFieldBuilder().getBuilder(), extensionRegistry);
                bitField0_ |= 0x00000004;
                break;
              } // case 26
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

    private java.lang.Object content_ = "";

    /**
     *
     *
     * <pre>
     * The content that will be split into words and returned on the stream.
     * </pre>
     *
     * <code>string content = 1;</code>
     *
     * @return The content.
     */
    public java.lang.String getContent() {
      java.lang.Object ref = content_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        content_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }

    /**
     *
     *
     * <pre>
     * The content that will be split into words and returned on the stream.
     * </pre>
     *
     * <code>string content = 1;</code>
     *
     * @return The bytes for content.
     */
    public com.google.protobuf.ByteString getContentBytes() {
      java.lang.Object ref = content_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        content_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    /**
     *
     *
     * <pre>
     * The content that will be split into words and returned on the stream.
     * </pre>
     *
     * <code>string content = 1;</code>
     *
     * @param value The content to set.
     * @return This builder for chaining.
     */
    public Builder setContent(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      content_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The content that will be split into words and returned on the stream.
     * </pre>
     *
     * <code>string content = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearContent() {
      content_ = getDefaultInstance().getContent();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The content that will be split into words and returned on the stream.
     * </pre>
     *
     * <code>string content = 1;</code>
     *
     * @param value The bytes for content to set.
     * @return This builder for chaining.
     */
    public Builder setContentBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);
      content_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private com.google.rpc.Status error_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.google.rpc.Status, com.google.rpc.Status.Builder, com.google.rpc.StatusOrBuilder>
        errorBuilder_;

    /**
     *
     *
     * <pre>
     * The error that is thrown after all words are sent on the stream.
     * </pre>
     *
     * <code>.google.rpc.Status error = 2;</code>
     *
     * @return Whether the error field is set.
     */
    public boolean hasError() {
      return ((bitField0_ & 0x00000002) != 0);
    }

    /**
     *
     *
     * <pre>
     * The error that is thrown after all words are sent on the stream.
     * </pre>
     *
     * <code>.google.rpc.Status error = 2;</code>
     *
     * @return The error.
     */
    public com.google.rpc.Status getError() {
      if (errorBuilder_ == null) {
        return error_ == null ? com.google.rpc.Status.getDefaultInstance() : error_;
      } else {
        return errorBuilder_.getMessage();
      }
    }

    /**
     *
     *
     * <pre>
     * The error that is thrown after all words are sent on the stream.
     * </pre>
     *
     * <code>.google.rpc.Status error = 2;</code>
     */
    public Builder setError(com.google.rpc.Status value) {
      if (errorBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        error_ = value;
      } else {
        errorBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The error that is thrown after all words are sent on the stream.
     * </pre>
     *
     * <code>.google.rpc.Status error = 2;</code>
     */
    public Builder setError(com.google.rpc.Status.Builder builderForValue) {
      if (errorBuilder_ == null) {
        error_ = builderForValue.build();
      } else {
        errorBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The error that is thrown after all words are sent on the stream.
     * </pre>
     *
     * <code>.google.rpc.Status error = 2;</code>
     */
    public Builder mergeError(com.google.rpc.Status value) {
      if (errorBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0)
            && error_ != null
            && error_ != com.google.rpc.Status.getDefaultInstance()) {
          getErrorBuilder().mergeFrom(value);
        } else {
          error_ = value;
        }
      } else {
        errorBuilder_.mergeFrom(value);
      }
      if (error_ != null) {
        bitField0_ |= 0x00000002;
        onChanged();
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * The error that is thrown after all words are sent on the stream.
     * </pre>
     *
     * <code>.google.rpc.Status error = 2;</code>
     */
    public Builder clearError() {
      bitField0_ = (bitField0_ & ~0x00000002);
      error_ = null;
      if (errorBuilder_ != null) {
        errorBuilder_.dispose();
        errorBuilder_ = null;
      }
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The error that is thrown after all words are sent on the stream.
     * </pre>
     *
     * <code>.google.rpc.Status error = 2;</code>
     */
    public com.google.rpc.Status.Builder getErrorBuilder() {
      bitField0_ |= 0x00000002;
      onChanged();
      return getErrorFieldBuilder().getBuilder();
    }

    /**
     *
     *
     * <pre>
     * The error that is thrown after all words are sent on the stream.
     * </pre>
     *
     * <code>.google.rpc.Status error = 2;</code>
     */
    public com.google.rpc.StatusOrBuilder getErrorOrBuilder() {
      if (errorBuilder_ != null) {
        return errorBuilder_.getMessageOrBuilder();
      } else {
        return error_ == null ? com.google.rpc.Status.getDefaultInstance() : error_;
      }
    }

    /**
     *
     *
     * <pre>
     * The error that is thrown after all words are sent on the stream.
     * </pre>
     *
     * <code>.google.rpc.Status error = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
            com.google.rpc.Status, com.google.rpc.Status.Builder, com.google.rpc.StatusOrBuilder>
        getErrorFieldBuilder() {
      if (errorBuilder_ == null) {
        errorBuilder_ =
            new com.google.protobuf.SingleFieldBuilderV3<
                com.google.rpc.Status,
                com.google.rpc.Status.Builder,
                com.google.rpc.StatusOrBuilder>(getError(), getParentForChildren(), isClean());
        error_ = null;
      }
      return errorBuilder_;
    }

    private com.google.protobuf.Duration streamWaitTime_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Duration,
            com.google.protobuf.Duration.Builder,
            com.google.protobuf.DurationOrBuilder>
        streamWaitTimeBuilder_;

    /**
     *
     *
     * <pre>
     * The wait time between each server streaming messages
     * </pre>
     *
     * <code>.google.protobuf.Duration stream_wait_time = 3;</code>
     *
     * @return Whether the streamWaitTime field is set.
     */
    public boolean hasStreamWaitTime() {
      return ((bitField0_ & 0x00000004) != 0);
    }

    /**
     *
     *
     * <pre>
     * The wait time between each server streaming messages
     * </pre>
     *
     * <code>.google.protobuf.Duration stream_wait_time = 3;</code>
     *
     * @return The streamWaitTime.
     */
    public com.google.protobuf.Duration getStreamWaitTime() {
      if (streamWaitTimeBuilder_ == null) {
        return streamWaitTime_ == null
            ? com.google.protobuf.Duration.getDefaultInstance()
            : streamWaitTime_;
      } else {
        return streamWaitTimeBuilder_.getMessage();
      }
    }

    /**
     *
     *
     * <pre>
     * The wait time between each server streaming messages
     * </pre>
     *
     * <code>.google.protobuf.Duration stream_wait_time = 3;</code>
     */
    public Builder setStreamWaitTime(com.google.protobuf.Duration value) {
      if (streamWaitTimeBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        streamWaitTime_ = value;
      } else {
        streamWaitTimeBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The wait time between each server streaming messages
     * </pre>
     *
     * <code>.google.protobuf.Duration stream_wait_time = 3;</code>
     */
    public Builder setStreamWaitTime(com.google.protobuf.Duration.Builder builderForValue) {
      if (streamWaitTimeBuilder_ == null) {
        streamWaitTime_ = builderForValue.build();
      } else {
        streamWaitTimeBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The wait time between each server streaming messages
     * </pre>
     *
     * <code>.google.protobuf.Duration stream_wait_time = 3;</code>
     */
    public Builder mergeStreamWaitTime(com.google.protobuf.Duration value) {
      if (streamWaitTimeBuilder_ == null) {
        if (((bitField0_ & 0x00000004) != 0)
            && streamWaitTime_ != null
            && streamWaitTime_ != com.google.protobuf.Duration.getDefaultInstance()) {
          getStreamWaitTimeBuilder().mergeFrom(value);
        } else {
          streamWaitTime_ = value;
        }
      } else {
        streamWaitTimeBuilder_.mergeFrom(value);
      }
      if (streamWaitTime_ != null) {
        bitField0_ |= 0x00000004;
        onChanged();
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * The wait time between each server streaming messages
     * </pre>
     *
     * <code>.google.protobuf.Duration stream_wait_time = 3;</code>
     */
    public Builder clearStreamWaitTime() {
      bitField0_ = (bitField0_ & ~0x00000004);
      streamWaitTime_ = null;
      if (streamWaitTimeBuilder_ != null) {
        streamWaitTimeBuilder_.dispose();
        streamWaitTimeBuilder_ = null;
      }
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The wait time between each server streaming messages
     * </pre>
     *
     * <code>.google.protobuf.Duration stream_wait_time = 3;</code>
     */
    public com.google.protobuf.Duration.Builder getStreamWaitTimeBuilder() {
      bitField0_ |= 0x00000004;
      onChanged();
      return getStreamWaitTimeFieldBuilder().getBuilder();
    }

    /**
     *
     *
     * <pre>
     * The wait time between each server streaming messages
     * </pre>
     *
     * <code>.google.protobuf.Duration stream_wait_time = 3;</code>
     */
    public com.google.protobuf.DurationOrBuilder getStreamWaitTimeOrBuilder() {
      if (streamWaitTimeBuilder_ != null) {
        return streamWaitTimeBuilder_.getMessageOrBuilder();
      } else {
        return streamWaitTime_ == null
            ? com.google.protobuf.Duration.getDefaultInstance()
            : streamWaitTime_;
      }
    }

    /**
     *
     *
     * <pre>
     * The wait time between each server streaming messages
     * </pre>
     *
     * <code>.google.protobuf.Duration stream_wait_time = 3;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Duration,
            com.google.protobuf.Duration.Builder,
            com.google.protobuf.DurationOrBuilder>
        getStreamWaitTimeFieldBuilder() {
      if (streamWaitTimeBuilder_ == null) {
        streamWaitTimeBuilder_ =
            new com.google.protobuf.SingleFieldBuilderV3<
                com.google.protobuf.Duration,
                com.google.protobuf.Duration.Builder,
                com.google.protobuf.DurationOrBuilder>(
                getStreamWaitTime(), getParentForChildren(), isClean());
        streamWaitTime_ = null;
      }
      return streamWaitTimeBuilder_;
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

    // @@protoc_insertion_point(builder_scope:google.showcase.v1beta1.ExpandRequest)
  }

  // @@protoc_insertion_point(class_scope:google.showcase.v1beta1.ExpandRequest)
  private static final com.google.showcase.v1beta1.ExpandRequest DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.showcase.v1beta1.ExpandRequest();
  }

  public static com.google.showcase.v1beta1.ExpandRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ExpandRequest> PARSER =
      new com.google.protobuf.AbstractParser<ExpandRequest>() {
        @java.lang.Override
        public ExpandRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<ExpandRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ExpandRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.showcase.v1beta1.ExpandRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
