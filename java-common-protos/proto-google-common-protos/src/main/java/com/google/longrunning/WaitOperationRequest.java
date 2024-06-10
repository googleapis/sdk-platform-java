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
// NO CHECKED-IN PROTOBUF GENCODE
// source: google/longrunning/operations.proto
// Protobuf Java Version: 4.27.1

package com.google.longrunning;

/**
 *
 *
 * <pre>
 * The request message for [Operations.WaitOperation][google.longrunning.Operations.WaitOperation].
 * </pre>
 *
 * Protobuf type {@code google.longrunning.WaitOperationRequest}
 */
public final class WaitOperationRequest extends com.google.protobuf.GeneratedMessage
    implements
    // @@protoc_insertion_point(message_implements:google.longrunning.WaitOperationRequest)
    WaitOperationRequestOrBuilder {
  private static final long serialVersionUID = 0L;

  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 27,
        /* patch= */ 1,
        /* suffix= */ "",
        WaitOperationRequest.class.getName());
  }
  // Use WaitOperationRequest.newBuilder() to construct.
  private WaitOperationRequest(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }

  private WaitOperationRequest() {
    name_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.longrunning.OperationsProto
        .internal_static_google_longrunning_WaitOperationRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.longrunning.OperationsProto
        .internal_static_google_longrunning_WaitOperationRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.longrunning.WaitOperationRequest.class,
            com.google.longrunning.WaitOperationRequest.Builder.class);
  }

  private int bitField0_;
  public static final int NAME_FIELD_NUMBER = 1;

  @SuppressWarnings("serial")
  private volatile java.lang.Object name_ = "";
  /**
   *
   *
   * <pre>
   * The name of the operation resource to wait on.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The name.
   */
  @java.lang.Override
  public java.lang.String getName() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      name_ = s;
      return s;
    }
  }
  /**
   *
   *
   * <pre>
   * The name of the operation resource to wait on.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The bytes for name.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getNameBytes() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      name_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TIMEOUT_FIELD_NUMBER = 2;
  private com.google.protobuf.Duration timeout_;
  /**
   *
   *
   * <pre>
   * The maximum duration to wait before timing out. If left blank, the wait
   * will be at most the time permitted by the underlying HTTP/RPC protocol.
   * If RPC context deadline is also specified, the shorter one will be used.
   * </pre>
   *
   * <code>.google.protobuf.Duration timeout = 2;</code>
   *
   * @return Whether the timeout field is set.
   */
  @java.lang.Override
  public boolean hasTimeout() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   *
   *
   * <pre>
   * The maximum duration to wait before timing out. If left blank, the wait
   * will be at most the time permitted by the underlying HTTP/RPC protocol.
   * If RPC context deadline is also specified, the shorter one will be used.
   * </pre>
   *
   * <code>.google.protobuf.Duration timeout = 2;</code>
   *
   * @return The timeout.
   */
  @java.lang.Override
  public com.google.protobuf.Duration getTimeout() {
    return timeout_ == null ? com.google.protobuf.Duration.getDefaultInstance() : timeout_;
  }
  /**
   *
   *
   * <pre>
   * The maximum duration to wait before timing out. If left blank, the wait
   * will be at most the time permitted by the underlying HTTP/RPC protocol.
   * If RPC context deadline is also specified, the shorter one will be used.
   * </pre>
   *
   * <code>.google.protobuf.Duration timeout = 2;</code>
   */
  @java.lang.Override
  public com.google.protobuf.DurationOrBuilder getTimeoutOrBuilder() {
    return timeout_ == null ? com.google.protobuf.Duration.getDefaultInstance() : timeout_;
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
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(name_)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 1, name_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(2, getTimeout());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessage.isStringEmpty(name_)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(1, name_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(2, getTimeout());
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
    if (!(obj instanceof com.google.longrunning.WaitOperationRequest)) {
      return super.equals(obj);
    }
    com.google.longrunning.WaitOperationRequest other =
        (com.google.longrunning.WaitOperationRequest) obj;

    if (!getName().equals(other.getName())) return false;
    if (hasTimeout() != other.hasTimeout()) return false;
    if (hasTimeout()) {
      if (!getTimeout().equals(other.getTimeout())) return false;
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
    hash = (37 * hash) + NAME_FIELD_NUMBER;
    hash = (53 * hash) + getName().hashCode();
    if (hasTimeout()) {
      hash = (37 * hash) + TIMEOUT_FIELD_NUMBER;
      hash = (53 * hash) + getTimeout().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.longrunning.WaitOperationRequest parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.longrunning.WaitOperationRequest parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.longrunning.WaitOperationRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.longrunning.WaitOperationRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.longrunning.WaitOperationRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.longrunning.WaitOperationRequest parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.longrunning.WaitOperationRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage.parseWithIOException(PARSER, input);
  }

  public static com.google.longrunning.WaitOperationRequest parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.longrunning.WaitOperationRequest parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.longrunning.WaitOperationRequest parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.longrunning.WaitOperationRequest parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage.parseWithIOException(PARSER, input);
  }

  public static com.google.longrunning.WaitOperationRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() {
    return newBuilder();
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(com.google.longrunning.WaitOperationRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   *
   *
   * <pre>
   * The request message for [Operations.WaitOperation][google.longrunning.Operations.WaitOperation].
   * </pre>
   *
   * Protobuf type {@code google.longrunning.WaitOperationRequest}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.longrunning.WaitOperationRequest)
      com.google.longrunning.WaitOperationRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.longrunning.OperationsProto
          .internal_static_google_longrunning_WaitOperationRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.longrunning.OperationsProto
          .internal_static_google_longrunning_WaitOperationRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.longrunning.WaitOperationRequest.class,
              com.google.longrunning.WaitOperationRequest.Builder.class);
    }

    // Construct using com.google.longrunning.WaitOperationRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        getTimeoutFieldBuilder();
      }
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      name_ = "";
      timeout_ = null;
      if (timeoutBuilder_ != null) {
        timeoutBuilder_.dispose();
        timeoutBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.longrunning.OperationsProto
          .internal_static_google_longrunning_WaitOperationRequest_descriptor;
    }

    @java.lang.Override
    public com.google.longrunning.WaitOperationRequest getDefaultInstanceForType() {
      return com.google.longrunning.WaitOperationRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.longrunning.WaitOperationRequest build() {
      com.google.longrunning.WaitOperationRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.longrunning.WaitOperationRequest buildPartial() {
      com.google.longrunning.WaitOperationRequest result =
          new com.google.longrunning.WaitOperationRequest(this);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.longrunning.WaitOperationRequest result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.name_ = name_;
      }
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.timeout_ = timeoutBuilder_ == null ? timeout_ : timeoutBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.google.longrunning.WaitOperationRequest) {
        return mergeFrom((com.google.longrunning.WaitOperationRequest) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.longrunning.WaitOperationRequest other) {
      if (other == com.google.longrunning.WaitOperationRequest.getDefaultInstance()) return this;
      if (!other.getName().isEmpty()) {
        name_ = other.name_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (other.hasTimeout()) {
        mergeTimeout(other.getTimeout());
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
                name_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000001;
                break;
              } // case 10
            case 18:
              {
                input.readMessage(getTimeoutFieldBuilder().getBuilder(), extensionRegistry);
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

    private java.lang.Object name_ = "";
    /**
     *
     *
     * <pre>
     * The name of the operation resource to wait on.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return The name.
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     *
     *
     * <pre>
     * The name of the operation resource to wait on.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return The bytes for name.
     */
    public com.google.protobuf.ByteString getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     *
     *
     * <pre>
     * The name of the operation resource to wait on.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @param value The name to set.
     * @return This builder for chaining.
     */
    public Builder setName(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      name_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The name of the operation resource to wait on.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearName() {
      name_ = getDefaultInstance().getName();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The name of the operation resource to wait on.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @param value The bytes for name to set.
     * @return This builder for chaining.
     */
    public Builder setNameBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);
      name_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private com.google.protobuf.Duration timeout_;
    private com.google.protobuf.SingleFieldBuilder<
            com.google.protobuf.Duration,
            com.google.protobuf.Duration.Builder,
            com.google.protobuf.DurationOrBuilder>
        timeoutBuilder_;
    /**
     *
     *
     * <pre>
     * The maximum duration to wait before timing out. If left blank, the wait
     * will be at most the time permitted by the underlying HTTP/RPC protocol.
     * If RPC context deadline is also specified, the shorter one will be used.
     * </pre>
     *
     * <code>.google.protobuf.Duration timeout = 2;</code>
     *
     * @return Whether the timeout field is set.
     */
    public boolean hasTimeout() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     *
     *
     * <pre>
     * The maximum duration to wait before timing out. If left blank, the wait
     * will be at most the time permitted by the underlying HTTP/RPC protocol.
     * If RPC context deadline is also specified, the shorter one will be used.
     * </pre>
     *
     * <code>.google.protobuf.Duration timeout = 2;</code>
     *
     * @return The timeout.
     */
    public com.google.protobuf.Duration getTimeout() {
      if (timeoutBuilder_ == null) {
        return timeout_ == null ? com.google.protobuf.Duration.getDefaultInstance() : timeout_;
      } else {
        return timeoutBuilder_.getMessage();
      }
    }
    /**
     *
     *
     * <pre>
     * The maximum duration to wait before timing out. If left blank, the wait
     * will be at most the time permitted by the underlying HTTP/RPC protocol.
     * If RPC context deadline is also specified, the shorter one will be used.
     * </pre>
     *
     * <code>.google.protobuf.Duration timeout = 2;</code>
     */
    public Builder setTimeout(com.google.protobuf.Duration value) {
      if (timeoutBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        timeout_ = value;
      } else {
        timeoutBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The maximum duration to wait before timing out. If left blank, the wait
     * will be at most the time permitted by the underlying HTTP/RPC protocol.
     * If RPC context deadline is also specified, the shorter one will be used.
     * </pre>
     *
     * <code>.google.protobuf.Duration timeout = 2;</code>
     */
    public Builder setTimeout(com.google.protobuf.Duration.Builder builderForValue) {
      if (timeoutBuilder_ == null) {
        timeout_ = builderForValue.build();
      } else {
        timeoutBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The maximum duration to wait before timing out. If left blank, the wait
     * will be at most the time permitted by the underlying HTTP/RPC protocol.
     * If RPC context deadline is also specified, the shorter one will be used.
     * </pre>
     *
     * <code>.google.protobuf.Duration timeout = 2;</code>
     */
    public Builder mergeTimeout(com.google.protobuf.Duration value) {
      if (timeoutBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0)
            && timeout_ != null
            && timeout_ != com.google.protobuf.Duration.getDefaultInstance()) {
          getTimeoutBuilder().mergeFrom(value);
        } else {
          timeout_ = value;
        }
      } else {
        timeoutBuilder_.mergeFrom(value);
      }
      if (timeout_ != null) {
        bitField0_ |= 0x00000002;
        onChanged();
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * The maximum duration to wait before timing out. If left blank, the wait
     * will be at most the time permitted by the underlying HTTP/RPC protocol.
     * If RPC context deadline is also specified, the shorter one will be used.
     * </pre>
     *
     * <code>.google.protobuf.Duration timeout = 2;</code>
     */
    public Builder clearTimeout() {
      bitField0_ = (bitField0_ & ~0x00000002);
      timeout_ = null;
      if (timeoutBuilder_ != null) {
        timeoutBuilder_.dispose();
        timeoutBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The maximum duration to wait before timing out. If left blank, the wait
     * will be at most the time permitted by the underlying HTTP/RPC protocol.
     * If RPC context deadline is also specified, the shorter one will be used.
     * </pre>
     *
     * <code>.google.protobuf.Duration timeout = 2;</code>
     */
    public com.google.protobuf.Duration.Builder getTimeoutBuilder() {
      bitField0_ |= 0x00000002;
      onChanged();
      return getTimeoutFieldBuilder().getBuilder();
    }
    /**
     *
     *
     * <pre>
     * The maximum duration to wait before timing out. If left blank, the wait
     * will be at most the time permitted by the underlying HTTP/RPC protocol.
     * If RPC context deadline is also specified, the shorter one will be used.
     * </pre>
     *
     * <code>.google.protobuf.Duration timeout = 2;</code>
     */
    public com.google.protobuf.DurationOrBuilder getTimeoutOrBuilder() {
      if (timeoutBuilder_ != null) {
        return timeoutBuilder_.getMessageOrBuilder();
      } else {
        return timeout_ == null ? com.google.protobuf.Duration.getDefaultInstance() : timeout_;
      }
    }
    /**
     *
     *
     * <pre>
     * The maximum duration to wait before timing out. If left blank, the wait
     * will be at most the time permitted by the underlying HTTP/RPC protocol.
     * If RPC context deadline is also specified, the shorter one will be used.
     * </pre>
     *
     * <code>.google.protobuf.Duration timeout = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilder<
            com.google.protobuf.Duration,
            com.google.protobuf.Duration.Builder,
            com.google.protobuf.DurationOrBuilder>
        getTimeoutFieldBuilder() {
      if (timeoutBuilder_ == null) {
        timeoutBuilder_ =
            new com.google.protobuf.SingleFieldBuilder<
                com.google.protobuf.Duration,
                com.google.protobuf.Duration.Builder,
                com.google.protobuf.DurationOrBuilder>(
                getTimeout(), getParentForChildren(), isClean());
        timeout_ = null;
      }
      return timeoutBuilder_;
    }

    // @@protoc_insertion_point(builder_scope:google.longrunning.WaitOperationRequest)
  }

  // @@protoc_insertion_point(class_scope:google.longrunning.WaitOperationRequest)
  private static final com.google.longrunning.WaitOperationRequest DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.longrunning.WaitOperationRequest();
  }

  public static com.google.longrunning.WaitOperationRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<WaitOperationRequest> PARSER =
      new com.google.protobuf.AbstractParser<WaitOperationRequest>() {
        @java.lang.Override
        public WaitOperationRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<WaitOperationRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<WaitOperationRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.longrunning.WaitOperationRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
