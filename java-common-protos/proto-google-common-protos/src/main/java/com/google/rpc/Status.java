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
// source: google/rpc/status.proto

// Protobuf Java Version: 3.25.5
package com.google.rpc;

/**
 *
 *
 * <pre>
 * The `Status` type defines a logical error model that is suitable for
 * different programming environments, including REST APIs and RPC APIs. It is
 * used by [gRPC](https://github.com/grpc). Each `Status` message contains
 * three pieces of data: error code, error message, and error details.
 *
 * You can find out more about this error model and how to work with it in the
 * [API Design Guide](https://cloud.google.com/apis/design/errors).
 * </pre>
 *
 * Protobuf type {@code google.rpc.Status}
 */
public final class Status extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.rpc.Status)
    StatusOrBuilder {
  private static final long serialVersionUID = 0L;

  // Use Status.newBuilder() to construct.
  private Status(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private Status() {
    message_ = "";
    details_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new Status();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.rpc.StatusProto.internal_static_google_rpc_Status_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.rpc.StatusProto.internal_static_google_rpc_Status_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.rpc.Status.class, com.google.rpc.Status.Builder.class);
  }

  public static final int CODE_FIELD_NUMBER = 1;
  private int code_ = 0;

  /**
   *
   *
   * <pre>
   * The status code, which should be an enum value of
   * [google.rpc.Code][google.rpc.Code].
   * </pre>
   *
   * <code>int32 code = 1;</code>
   *
   * @return The code.
   */
  @java.lang.Override
  public int getCode() {
    return code_;
  }

  public static final int MESSAGE_FIELD_NUMBER = 2;

  @SuppressWarnings("serial")
  private volatile java.lang.Object message_ = "";

  /**
   *
   *
   * <pre>
   * A developer-facing error message, which should be in English. Any
   * user-facing error message should be localized and sent in the
   * [google.rpc.Status.details][google.rpc.Status.details] field, or localized
   * by the client.
   * </pre>
   *
   * <code>string message = 2;</code>
   *
   * @return The message.
   */
  @java.lang.Override
  public java.lang.String getMessage() {
    java.lang.Object ref = message_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      message_ = s;
      return s;
    }
  }

  /**
   *
   *
   * <pre>
   * A developer-facing error message, which should be in English. Any
   * user-facing error message should be localized and sent in the
   * [google.rpc.Status.details][google.rpc.Status.details] field, or localized
   * by the client.
   * </pre>
   *
   * <code>string message = 2;</code>
   *
   * @return The bytes for message.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getMessageBytes() {
    java.lang.Object ref = message_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      message_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int DETAILS_FIELD_NUMBER = 3;

  @SuppressWarnings("serial")
  private java.util.List<com.google.protobuf.Any> details_;

  /**
   *
   *
   * <pre>
   * A list of messages that carry the error details.  There is a common set of
   * message types for APIs to use.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any details = 3;</code>
   */
  @java.lang.Override
  public java.util.List<com.google.protobuf.Any> getDetailsList() {
    return details_;
  }

  /**
   *
   *
   * <pre>
   * A list of messages that carry the error details.  There is a common set of
   * message types for APIs to use.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any details = 3;</code>
   */
  @java.lang.Override
  public java.util.List<? extends com.google.protobuf.AnyOrBuilder> getDetailsOrBuilderList() {
    return details_;
  }

  /**
   *
   *
   * <pre>
   * A list of messages that carry the error details.  There is a common set of
   * message types for APIs to use.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any details = 3;</code>
   */
  @java.lang.Override
  public int getDetailsCount() {
    return details_.size();
  }

  /**
   *
   *
   * <pre>
   * A list of messages that carry the error details.  There is a common set of
   * message types for APIs to use.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any details = 3;</code>
   */
  @java.lang.Override
  public com.google.protobuf.Any getDetails(int index) {
    return details_.get(index);
  }

  /**
   *
   *
   * <pre>
   * A list of messages that carry the error details.  There is a common set of
   * message types for APIs to use.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any details = 3;</code>
   */
  @java.lang.Override
  public com.google.protobuf.AnyOrBuilder getDetailsOrBuilder(int index) {
    return details_.get(index);
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
    if (code_ != 0) {
      output.writeInt32(1, code_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(message_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, message_);
    }
    for (int i = 0; i < details_.size(); i++) {
      output.writeMessage(3, details_.get(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (code_ != 0) {
      size += com.google.protobuf.CodedOutputStream.computeInt32Size(1, code_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(message_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, message_);
    }
    for (int i = 0; i < details_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(3, details_.get(i));
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
    if (!(obj instanceof com.google.rpc.Status)) {
      return super.equals(obj);
    }
    com.google.rpc.Status other = (com.google.rpc.Status) obj;

    if (getCode() != other.getCode()) return false;
    if (!getMessage().equals(other.getMessage())) return false;
    if (!getDetailsList().equals(other.getDetailsList())) return false;
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
    hash = (37 * hash) + CODE_FIELD_NUMBER;
    hash = (53 * hash) + getCode();
    hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
    hash = (53 * hash) + getMessage().hashCode();
    if (getDetailsCount() > 0) {
      hash = (37 * hash) + DETAILS_FIELD_NUMBER;
      hash = (53 * hash) + getDetailsList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.rpc.Status parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.rpc.Status parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.rpc.Status parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.rpc.Status parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.rpc.Status parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.rpc.Status parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.rpc.Status parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.rpc.Status parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.rpc.Status parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.rpc.Status parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.rpc.Status parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.rpc.Status parseFrom(
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

  public static Builder newBuilder(com.google.rpc.Status prototype) {
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
   * The `Status` type defines a logical error model that is suitable for
   * different programming environments, including REST APIs and RPC APIs. It is
   * used by [gRPC](https://github.com/grpc). Each `Status` message contains
   * three pieces of data: error code, error message, and error details.
   *
   * You can find out more about this error model and how to work with it in the
   * [API Design Guide](https://cloud.google.com/apis/design/errors).
   * </pre>
   *
   * Protobuf type {@code google.rpc.Status}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.rpc.Status)
      com.google.rpc.StatusOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.rpc.StatusProto.internal_static_google_rpc_Status_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.rpc.StatusProto.internal_static_google_rpc_Status_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.rpc.Status.class, com.google.rpc.Status.Builder.class);
    }

    // Construct using com.google.rpc.Status.newBuilder()
    private Builder() {}

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      code_ = 0;
      message_ = "";
      if (detailsBuilder_ == null) {
        details_ = java.util.Collections.emptyList();
      } else {
        details_ = null;
        detailsBuilder_.clear();
      }
      bitField0_ = (bitField0_ & ~0x00000004);
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.rpc.StatusProto.internal_static_google_rpc_Status_descriptor;
    }

    @java.lang.Override
    public com.google.rpc.Status getDefaultInstanceForType() {
      return com.google.rpc.Status.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.rpc.Status build() {
      com.google.rpc.Status result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.rpc.Status buildPartial() {
      com.google.rpc.Status result = new com.google.rpc.Status(this);
      buildPartialRepeatedFields(result);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartialRepeatedFields(com.google.rpc.Status result) {
      if (detailsBuilder_ == null) {
        if (((bitField0_ & 0x00000004) != 0)) {
          details_ = java.util.Collections.unmodifiableList(details_);
          bitField0_ = (bitField0_ & ~0x00000004);
        }
        result.details_ = details_;
      } else {
        result.details_ = detailsBuilder_.build();
      }
    }

    private void buildPartial0(com.google.rpc.Status result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.code_ = code_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.message_ = message_;
      }
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
      if (other instanceof com.google.rpc.Status) {
        return mergeFrom((com.google.rpc.Status) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.rpc.Status other) {
      if (other == com.google.rpc.Status.getDefaultInstance()) return this;
      if (other.getCode() != 0) {
        setCode(other.getCode());
      }
      if (!other.getMessage().isEmpty()) {
        message_ = other.message_;
        bitField0_ |= 0x00000002;
        onChanged();
      }
      if (detailsBuilder_ == null) {
        if (!other.details_.isEmpty()) {
          if (details_.isEmpty()) {
            details_ = other.details_;
            bitField0_ = (bitField0_ & ~0x00000004);
          } else {
            ensureDetailsIsMutable();
            details_.addAll(other.details_);
          }
          onChanged();
        }
      } else {
        if (!other.details_.isEmpty()) {
          if (detailsBuilder_.isEmpty()) {
            detailsBuilder_.dispose();
            detailsBuilder_ = null;
            details_ = other.details_;
            bitField0_ = (bitField0_ & ~0x00000004);
            detailsBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getDetailsFieldBuilder()
                    : null;
          } else {
            detailsBuilder_.addAllMessages(other.details_);
          }
        }
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
            case 8:
              {
                code_ = input.readInt32();
                bitField0_ |= 0x00000001;
                break;
              } // case 8
            case 18:
              {
                message_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000002;
                break;
              } // case 18
            case 26:
              {
                com.google.protobuf.Any m =
                    input.readMessage(com.google.protobuf.Any.parser(), extensionRegistry);
                if (detailsBuilder_ == null) {
                  ensureDetailsIsMutable();
                  details_.add(m);
                } else {
                  detailsBuilder_.addMessage(m);
                }
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

    private int code_;

    /**
     *
     *
     * <pre>
     * The status code, which should be an enum value of
     * [google.rpc.Code][google.rpc.Code].
     * </pre>
     *
     * <code>int32 code = 1;</code>
     *
     * @return The code.
     */
    @java.lang.Override
    public int getCode() {
      return code_;
    }

    /**
     *
     *
     * <pre>
     * The status code, which should be an enum value of
     * [google.rpc.Code][google.rpc.Code].
     * </pre>
     *
     * <code>int32 code = 1;</code>
     *
     * @param value The code to set.
     * @return This builder for chaining.
     */
    public Builder setCode(int value) {

      code_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The status code, which should be an enum value of
     * [google.rpc.Code][google.rpc.Code].
     * </pre>
     *
     * <code>int32 code = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCode() {
      bitField0_ = (bitField0_ & ~0x00000001);
      code_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object message_ = "";

    /**
     *
     *
     * <pre>
     * A developer-facing error message, which should be in English. Any
     * user-facing error message should be localized and sent in the
     * [google.rpc.Status.details][google.rpc.Status.details] field, or localized
     * by the client.
     * </pre>
     *
     * <code>string message = 2;</code>
     *
     * @return The message.
     */
    public java.lang.String getMessage() {
      java.lang.Object ref = message_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        message_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }

    /**
     *
     *
     * <pre>
     * A developer-facing error message, which should be in English. Any
     * user-facing error message should be localized and sent in the
     * [google.rpc.Status.details][google.rpc.Status.details] field, or localized
     * by the client.
     * </pre>
     *
     * <code>string message = 2;</code>
     *
     * @return The bytes for message.
     */
    public com.google.protobuf.ByteString getMessageBytes() {
      java.lang.Object ref = message_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        message_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    /**
     *
     *
     * <pre>
     * A developer-facing error message, which should be in English. Any
     * user-facing error message should be localized and sent in the
     * [google.rpc.Status.details][google.rpc.Status.details] field, or localized
     * by the client.
     * </pre>
     *
     * <code>string message = 2;</code>
     *
     * @param value The message to set.
     * @return This builder for chaining.
     */
    public Builder setMessage(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      message_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * A developer-facing error message, which should be in English. Any
     * user-facing error message should be localized and sent in the
     * [google.rpc.Status.details][google.rpc.Status.details] field, or localized
     * by the client.
     * </pre>
     *
     * <code>string message = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearMessage() {
      message_ = getDefaultInstance().getMessage();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * A developer-facing error message, which should be in English. Any
     * user-facing error message should be localized and sent in the
     * [google.rpc.Status.details][google.rpc.Status.details] field, or localized
     * by the client.
     * </pre>
     *
     * <code>string message = 2;</code>
     *
     * @param value The bytes for message to set.
     * @return This builder for chaining.
     */
    public Builder setMessageBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);
      message_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    private java.util.List<com.google.protobuf.Any> details_ = java.util.Collections.emptyList();

    private void ensureDetailsIsMutable() {
      if (!((bitField0_ & 0x00000004) != 0)) {
        details_ = new java.util.ArrayList<com.google.protobuf.Any>(details_);
        bitField0_ |= 0x00000004;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.google.protobuf.Any,
            com.google.protobuf.Any.Builder,
            com.google.protobuf.AnyOrBuilder>
        detailsBuilder_;

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public java.util.List<com.google.protobuf.Any> getDetailsList() {
      if (detailsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(details_);
      } else {
        return detailsBuilder_.getMessageList();
      }
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public int getDetailsCount() {
      if (detailsBuilder_ == null) {
        return details_.size();
      } else {
        return detailsBuilder_.getCount();
      }
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public com.google.protobuf.Any getDetails(int index) {
      if (detailsBuilder_ == null) {
        return details_.get(index);
      } else {
        return detailsBuilder_.getMessage(index);
      }
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public Builder setDetails(int index, com.google.protobuf.Any value) {
      if (detailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDetailsIsMutable();
        details_.set(index, value);
        onChanged();
      } else {
        detailsBuilder_.setMessage(index, value);
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public Builder setDetails(int index, com.google.protobuf.Any.Builder builderForValue) {
      if (detailsBuilder_ == null) {
        ensureDetailsIsMutable();
        details_.set(index, builderForValue.build());
        onChanged();
      } else {
        detailsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public Builder addDetails(com.google.protobuf.Any value) {
      if (detailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDetailsIsMutable();
        details_.add(value);
        onChanged();
      } else {
        detailsBuilder_.addMessage(value);
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public Builder addDetails(int index, com.google.protobuf.Any value) {
      if (detailsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureDetailsIsMutable();
        details_.add(index, value);
        onChanged();
      } else {
        detailsBuilder_.addMessage(index, value);
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public Builder addDetails(com.google.protobuf.Any.Builder builderForValue) {
      if (detailsBuilder_ == null) {
        ensureDetailsIsMutable();
        details_.add(builderForValue.build());
        onChanged();
      } else {
        detailsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public Builder addDetails(int index, com.google.protobuf.Any.Builder builderForValue) {
      if (detailsBuilder_ == null) {
        ensureDetailsIsMutable();
        details_.add(index, builderForValue.build());
        onChanged();
      } else {
        detailsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public Builder addAllDetails(java.lang.Iterable<? extends com.google.protobuf.Any> values) {
      if (detailsBuilder_ == null) {
        ensureDetailsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, details_);
        onChanged();
      } else {
        detailsBuilder_.addAllMessages(values);
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public Builder clearDetails() {
      if (detailsBuilder_ == null) {
        details_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000004);
        onChanged();
      } else {
        detailsBuilder_.clear();
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public Builder removeDetails(int index) {
      if (detailsBuilder_ == null) {
        ensureDetailsIsMutable();
        details_.remove(index);
        onChanged();
      } else {
        detailsBuilder_.remove(index);
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public com.google.protobuf.Any.Builder getDetailsBuilder(int index) {
      return getDetailsFieldBuilder().getBuilder(index);
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public com.google.protobuf.AnyOrBuilder getDetailsOrBuilder(int index) {
      if (detailsBuilder_ == null) {
        return details_.get(index);
      } else {
        return detailsBuilder_.getMessageOrBuilder(index);
      }
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public java.util.List<? extends com.google.protobuf.AnyOrBuilder> getDetailsOrBuilderList() {
      if (detailsBuilder_ != null) {
        return detailsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(details_);
      }
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public com.google.protobuf.Any.Builder addDetailsBuilder() {
      return getDetailsFieldBuilder().addBuilder(com.google.protobuf.Any.getDefaultInstance());
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public com.google.protobuf.Any.Builder addDetailsBuilder(int index) {
      return getDetailsFieldBuilder()
          .addBuilder(index, com.google.protobuf.Any.getDefaultInstance());
    }

    /**
     *
     *
     * <pre>
     * A list of messages that carry the error details.  There is a common set of
     * message types for APIs to use.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any details = 3;</code>
     */
    public java.util.List<com.google.protobuf.Any.Builder> getDetailsBuilderList() {
      return getDetailsFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.google.protobuf.Any,
            com.google.protobuf.Any.Builder,
            com.google.protobuf.AnyOrBuilder>
        getDetailsFieldBuilder() {
      if (detailsBuilder_ == null) {
        detailsBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.google.protobuf.Any,
                com.google.protobuf.Any.Builder,
                com.google.protobuf.AnyOrBuilder>(
                details_, ((bitField0_ & 0x00000004) != 0), getParentForChildren(), isClean());
        details_ = null;
      }
      return detailsBuilder_;
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

    // @@protoc_insertion_point(builder_scope:google.rpc.Status)
  }

  // @@protoc_insertion_point(class_scope:google.rpc.Status)
  private static final com.google.rpc.Status DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.rpc.Status();
  }

  public static com.google.rpc.Status getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Status> PARSER =
      new com.google.protobuf.AbstractParser<Status>() {
        @java.lang.Override
        public Status parsePartialFrom(
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

  public static com.google.protobuf.Parser<Status> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Status> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.rpc.Status getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
