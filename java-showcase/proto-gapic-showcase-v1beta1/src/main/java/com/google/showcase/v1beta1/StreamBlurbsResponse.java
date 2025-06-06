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
// source: schema/google/showcase/v1beta1/messaging.proto

// Protobuf Java Version: 3.25.8
package com.google.showcase.v1beta1;

/**
 *
 *
 * <pre>
 * The response message for the google.showcase.v1beta1.Messaging&#92;StreamBlurbs
 * method.
 * </pre>
 *
 * Protobuf type {@code google.showcase.v1beta1.StreamBlurbsResponse}
 */
public final class StreamBlurbsResponse extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.showcase.v1beta1.StreamBlurbsResponse)
    StreamBlurbsResponseOrBuilder {
  private static final long serialVersionUID = 0L;

  // Use StreamBlurbsResponse.newBuilder() to construct.
  private StreamBlurbsResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private StreamBlurbsResponse() {
    action_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new StreamBlurbsResponse();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.showcase.v1beta1.MessagingOuterClass
        .internal_static_google_showcase_v1beta1_StreamBlurbsResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.showcase.v1beta1.MessagingOuterClass
        .internal_static_google_showcase_v1beta1_StreamBlurbsResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.showcase.v1beta1.StreamBlurbsResponse.class,
            com.google.showcase.v1beta1.StreamBlurbsResponse.Builder.class);
  }

  /**
   *
   *
   * <pre>
   * The action that triggered the blurb to be returned.
   * </pre>
   *
   * Protobuf enum {@code google.showcase.v1beta1.StreamBlurbsResponse.Action}
   */
  public enum Action implements com.google.protobuf.ProtocolMessageEnum {
    /** <code>ACTION_UNSPECIFIED = 0;</code> */
    ACTION_UNSPECIFIED(0),
    /**
     *
     *
     * <pre>
     * Specifies that the blurb was created.
     * </pre>
     *
     * <code>CREATE = 1;</code>
     */
    CREATE(1),
    /**
     *
     *
     * <pre>
     * Specifies that the blurb was updated.
     * </pre>
     *
     * <code>UPDATE = 2;</code>
     */
    UPDATE(2),
    /**
     *
     *
     * <pre>
     * Specifies that the blurb was deleted.
     * </pre>
     *
     * <code>DELETE = 3;</code>
     */
    DELETE(3),
    UNRECOGNIZED(-1),
    ;

    /** <code>ACTION_UNSPECIFIED = 0;</code> */
    public static final int ACTION_UNSPECIFIED_VALUE = 0;

    /**
     *
     *
     * <pre>
     * Specifies that the blurb was created.
     * </pre>
     *
     * <code>CREATE = 1;</code>
     */
    public static final int CREATE_VALUE = 1;

    /**
     *
     *
     * <pre>
     * Specifies that the blurb was updated.
     * </pre>
     *
     * <code>UPDATE = 2;</code>
     */
    public static final int UPDATE_VALUE = 2;

    /**
     *
     *
     * <pre>
     * Specifies that the blurb was deleted.
     * </pre>
     *
     * <code>DELETE = 3;</code>
     */
    public static final int DELETE_VALUE = 3;

    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static Action valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static Action forNumber(int value) {
      switch (value) {
        case 0:
          return ACTION_UNSPECIFIED;
        case 1:
          return CREATE;
        case 2:
          return UPDATE;
        case 3:
          return DELETE;
        default:
          return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<Action> internalGetValueMap() {
      return internalValueMap;
    }

    private static final com.google.protobuf.Internal.EnumLiteMap<Action> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<Action>() {
          public Action findValueByNumber(int number) {
            return Action.forNumber(number);
          }
        };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor getValueDescriptor() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalStateException(
            "Can't get the descriptor of an unrecognized enum value.");
      }
      return getDescriptor().getValues().get(ordinal());
    }

    public final com.google.protobuf.Descriptors.EnumDescriptor getDescriptorForType() {
      return getDescriptor();
    }

    public static final com.google.protobuf.Descriptors.EnumDescriptor getDescriptor() {
      return com.google.showcase.v1beta1.StreamBlurbsResponse.getDescriptor().getEnumTypes().get(0);
    }

    private static final Action[] VALUES = values();

    public static Action valueOf(com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException("EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private Action(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:google.showcase.v1beta1.StreamBlurbsResponse.Action)
  }

  private int bitField0_;
  public static final int BLURB_FIELD_NUMBER = 1;
  private com.google.showcase.v1beta1.Blurb blurb_;

  /**
   *
   *
   * <pre>
   * The blurb that was either created, updated, or deleted.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
   *
   * @return Whether the blurb field is set.
   */
  @java.lang.Override
  public boolean hasBlurb() {
    return ((bitField0_ & 0x00000001) != 0);
  }

  /**
   *
   *
   * <pre>
   * The blurb that was either created, updated, or deleted.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
   *
   * @return The blurb.
   */
  @java.lang.Override
  public com.google.showcase.v1beta1.Blurb getBlurb() {
    return blurb_ == null ? com.google.showcase.v1beta1.Blurb.getDefaultInstance() : blurb_;
  }

  /**
   *
   *
   * <pre>
   * The blurb that was either created, updated, or deleted.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
   */
  @java.lang.Override
  public com.google.showcase.v1beta1.BlurbOrBuilder getBlurbOrBuilder() {
    return blurb_ == null ? com.google.showcase.v1beta1.Blurb.getDefaultInstance() : blurb_;
  }

  public static final int ACTION_FIELD_NUMBER = 2;
  private int action_ = 0;

  /**
   *
   *
   * <pre>
   * The action that triggered the blurb to be returned.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.StreamBlurbsResponse.Action action = 2;</code>
   *
   * @return The enum numeric value on the wire for action.
   */
  @java.lang.Override
  public int getActionValue() {
    return action_;
  }

  /**
   *
   *
   * <pre>
   * The action that triggered the blurb to be returned.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.StreamBlurbsResponse.Action action = 2;</code>
   *
   * @return The action.
   */
  @java.lang.Override
  public com.google.showcase.v1beta1.StreamBlurbsResponse.Action getAction() {
    com.google.showcase.v1beta1.StreamBlurbsResponse.Action result =
        com.google.showcase.v1beta1.StreamBlurbsResponse.Action.forNumber(action_);
    return result == null
        ? com.google.showcase.v1beta1.StreamBlurbsResponse.Action.UNRECOGNIZED
        : result;
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
      output.writeMessage(1, getBlurb());
    }
    if (action_
        != com.google.showcase.v1beta1.StreamBlurbsResponse.Action.ACTION_UNSPECIFIED.getNumber()) {
      output.writeEnum(2, action_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getBlurb());
    }
    if (action_
        != com.google.showcase.v1beta1.StreamBlurbsResponse.Action.ACTION_UNSPECIFIED.getNumber()) {
      size += com.google.protobuf.CodedOutputStream.computeEnumSize(2, action_);
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
    if (!(obj instanceof com.google.showcase.v1beta1.StreamBlurbsResponse)) {
      return super.equals(obj);
    }
    com.google.showcase.v1beta1.StreamBlurbsResponse other =
        (com.google.showcase.v1beta1.StreamBlurbsResponse) obj;

    if (hasBlurb() != other.hasBlurb()) return false;
    if (hasBlurb()) {
      if (!getBlurb().equals(other.getBlurb())) return false;
    }
    if (action_ != other.action_) return false;
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
    if (hasBlurb()) {
      hash = (37 * hash) + BLURB_FIELD_NUMBER;
      hash = (53 * hash) + getBlurb().hashCode();
    }
    hash = (37 * hash) + ACTION_FIELD_NUMBER;
    hash = (53 * hash) + action_;
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.showcase.v1beta1.StreamBlurbsResponse parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.showcase.v1beta1.StreamBlurbsResponse parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.StreamBlurbsResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.showcase.v1beta1.StreamBlurbsResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.StreamBlurbsResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.showcase.v1beta1.StreamBlurbsResponse parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.StreamBlurbsResponse parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.StreamBlurbsResponse parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.StreamBlurbsResponse parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.StreamBlurbsResponse parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.StreamBlurbsResponse parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.StreamBlurbsResponse parseFrom(
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

  public static Builder newBuilder(com.google.showcase.v1beta1.StreamBlurbsResponse prototype) {
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
   * The response message for the google.showcase.v1beta1.Messaging&#92;StreamBlurbs
   * method.
   * </pre>
   *
   * Protobuf type {@code google.showcase.v1beta1.StreamBlurbsResponse}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.showcase.v1beta1.StreamBlurbsResponse)
      com.google.showcase.v1beta1.StreamBlurbsResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.showcase.v1beta1.MessagingOuterClass
          .internal_static_google_showcase_v1beta1_StreamBlurbsResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.showcase.v1beta1.MessagingOuterClass
          .internal_static_google_showcase_v1beta1_StreamBlurbsResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.showcase.v1beta1.StreamBlurbsResponse.class,
              com.google.showcase.v1beta1.StreamBlurbsResponse.Builder.class);
    }

    // Construct using com.google.showcase.v1beta1.StreamBlurbsResponse.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {
        getBlurbFieldBuilder();
      }
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      blurb_ = null;
      if (blurbBuilder_ != null) {
        blurbBuilder_.dispose();
        blurbBuilder_ = null;
      }
      action_ = 0;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.showcase.v1beta1.MessagingOuterClass
          .internal_static_google_showcase_v1beta1_StreamBlurbsResponse_descriptor;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.StreamBlurbsResponse getDefaultInstanceForType() {
      return com.google.showcase.v1beta1.StreamBlurbsResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.StreamBlurbsResponse build() {
      com.google.showcase.v1beta1.StreamBlurbsResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.StreamBlurbsResponse buildPartial() {
      com.google.showcase.v1beta1.StreamBlurbsResponse result =
          new com.google.showcase.v1beta1.StreamBlurbsResponse(this);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.showcase.v1beta1.StreamBlurbsResponse result) {
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.blurb_ = blurbBuilder_ == null ? blurb_ : blurbBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.action_ = action_;
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
      if (other instanceof com.google.showcase.v1beta1.StreamBlurbsResponse) {
        return mergeFrom((com.google.showcase.v1beta1.StreamBlurbsResponse) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.showcase.v1beta1.StreamBlurbsResponse other) {
      if (other == com.google.showcase.v1beta1.StreamBlurbsResponse.getDefaultInstance())
        return this;
      if (other.hasBlurb()) {
        mergeBlurb(other.getBlurb());
      }
      if (other.action_ != 0) {
        setActionValue(other.getActionValue());
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
                input.readMessage(getBlurbFieldBuilder().getBuilder(), extensionRegistry);
                bitField0_ |= 0x00000001;
                break;
              } // case 10
            case 16:
              {
                action_ = input.readEnum();
                bitField0_ |= 0x00000002;
                break;
              } // case 16
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

    private com.google.showcase.v1beta1.Blurb blurb_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.google.showcase.v1beta1.Blurb,
            com.google.showcase.v1beta1.Blurb.Builder,
            com.google.showcase.v1beta1.BlurbOrBuilder>
        blurbBuilder_;

    /**
     *
     *
     * <pre>
     * The blurb that was either created, updated, or deleted.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
     *
     * @return Whether the blurb field is set.
     */
    public boolean hasBlurb() {
      return ((bitField0_ & 0x00000001) != 0);
    }

    /**
     *
     *
     * <pre>
     * The blurb that was either created, updated, or deleted.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
     *
     * @return The blurb.
     */
    public com.google.showcase.v1beta1.Blurb getBlurb() {
      if (blurbBuilder_ == null) {
        return blurb_ == null ? com.google.showcase.v1beta1.Blurb.getDefaultInstance() : blurb_;
      } else {
        return blurbBuilder_.getMessage();
      }
    }

    /**
     *
     *
     * <pre>
     * The blurb that was either created, updated, or deleted.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
     */
    public Builder setBlurb(com.google.showcase.v1beta1.Blurb value) {
      if (blurbBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        blurb_ = value;
      } else {
        blurbBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The blurb that was either created, updated, or deleted.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
     */
    public Builder setBlurb(com.google.showcase.v1beta1.Blurb.Builder builderForValue) {
      if (blurbBuilder_ == null) {
        blurb_ = builderForValue.build();
      } else {
        blurbBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The blurb that was either created, updated, or deleted.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
     */
    public Builder mergeBlurb(com.google.showcase.v1beta1.Blurb value) {
      if (blurbBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)
            && blurb_ != null
            && blurb_ != com.google.showcase.v1beta1.Blurb.getDefaultInstance()) {
          getBlurbBuilder().mergeFrom(value);
        } else {
          blurb_ = value;
        }
      } else {
        blurbBuilder_.mergeFrom(value);
      }
      if (blurb_ != null) {
        bitField0_ |= 0x00000001;
        onChanged();
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * The blurb that was either created, updated, or deleted.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
     */
    public Builder clearBlurb() {
      bitField0_ = (bitField0_ & ~0x00000001);
      blurb_ = null;
      if (blurbBuilder_ != null) {
        blurbBuilder_.dispose();
        blurbBuilder_ = null;
      }
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The blurb that was either created, updated, or deleted.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
     */
    public com.google.showcase.v1beta1.Blurb.Builder getBlurbBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getBlurbFieldBuilder().getBuilder();
    }

    /**
     *
     *
     * <pre>
     * The blurb that was either created, updated, or deleted.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
     */
    public com.google.showcase.v1beta1.BlurbOrBuilder getBlurbOrBuilder() {
      if (blurbBuilder_ != null) {
        return blurbBuilder_.getMessageOrBuilder();
      } else {
        return blurb_ == null ? com.google.showcase.v1beta1.Blurb.getDefaultInstance() : blurb_;
      }
    }

    /**
     *
     *
     * <pre>
     * The blurb that was either created, updated, or deleted.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
            com.google.showcase.v1beta1.Blurb,
            com.google.showcase.v1beta1.Blurb.Builder,
            com.google.showcase.v1beta1.BlurbOrBuilder>
        getBlurbFieldBuilder() {
      if (blurbBuilder_ == null) {
        blurbBuilder_ =
            new com.google.protobuf.SingleFieldBuilderV3<
                com.google.showcase.v1beta1.Blurb,
                com.google.showcase.v1beta1.Blurb.Builder,
                com.google.showcase.v1beta1.BlurbOrBuilder>(
                getBlurb(), getParentForChildren(), isClean());
        blurb_ = null;
      }
      return blurbBuilder_;
    }

    private int action_ = 0;

    /**
     *
     *
     * <pre>
     * The action that triggered the blurb to be returned.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.StreamBlurbsResponse.Action action = 2;</code>
     *
     * @return The enum numeric value on the wire for action.
     */
    @java.lang.Override
    public int getActionValue() {
      return action_;
    }

    /**
     *
     *
     * <pre>
     * The action that triggered the blurb to be returned.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.StreamBlurbsResponse.Action action = 2;</code>
     *
     * @param value The enum numeric value on the wire for action to set.
     * @return This builder for chaining.
     */
    public Builder setActionValue(int value) {
      action_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The action that triggered the blurb to be returned.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.StreamBlurbsResponse.Action action = 2;</code>
     *
     * @return The action.
     */
    @java.lang.Override
    public com.google.showcase.v1beta1.StreamBlurbsResponse.Action getAction() {
      com.google.showcase.v1beta1.StreamBlurbsResponse.Action result =
          com.google.showcase.v1beta1.StreamBlurbsResponse.Action.forNumber(action_);
      return result == null
          ? com.google.showcase.v1beta1.StreamBlurbsResponse.Action.UNRECOGNIZED
          : result;
    }

    /**
     *
     *
     * <pre>
     * The action that triggered the blurb to be returned.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.StreamBlurbsResponse.Action action = 2;</code>
     *
     * @param value The action to set.
     * @return This builder for chaining.
     */
    public Builder setAction(com.google.showcase.v1beta1.StreamBlurbsResponse.Action value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000002;
      action_ = value.getNumber();
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The action that triggered the blurb to be returned.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.StreamBlurbsResponse.Action action = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearAction() {
      bitField0_ = (bitField0_ & ~0x00000002);
      action_ = 0;
      onChanged();
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

    // @@protoc_insertion_point(builder_scope:google.showcase.v1beta1.StreamBlurbsResponse)
  }

  // @@protoc_insertion_point(class_scope:google.showcase.v1beta1.StreamBlurbsResponse)
  private static final com.google.showcase.v1beta1.StreamBlurbsResponse DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.showcase.v1beta1.StreamBlurbsResponse();
  }

  public static com.google.showcase.v1beta1.StreamBlurbsResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<StreamBlurbsResponse> PARSER =
      new com.google.protobuf.AbstractParser<StreamBlurbsResponse>() {
        @java.lang.Override
        public StreamBlurbsResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<StreamBlurbsResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<StreamBlurbsResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.showcase.v1beta1.StreamBlurbsResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
