// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/echo.proto

// Protobuf Java Version: 3.25.5
package com.google.showcase.v1beta1;

/**
 * <pre>
 * The request message used for the EchoErrorDetails method.
 * </pre>
 *
 * Protobuf type {@code google.showcase.v1beta1.EchoErrorDetailsRequest}
 */
public final class EchoErrorDetailsRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:google.showcase.v1beta1.EchoErrorDetailsRequest)
    EchoErrorDetailsRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use EchoErrorDetailsRequest.newBuilder() to construct.
  private EchoErrorDetailsRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private EchoErrorDetailsRequest() {
    singleDetailText_ = "";
    multiDetailText_ =
        com.google.protobuf.LazyStringArrayList.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new EchoErrorDetailsRequest();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.google.showcase.v1beta1.EchoOuterClass.internal_static_google_showcase_v1beta1_EchoErrorDetailsRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.showcase.v1beta1.EchoOuterClass.internal_static_google_showcase_v1beta1_EchoErrorDetailsRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.showcase.v1beta1.EchoErrorDetailsRequest.class, com.google.showcase.v1beta1.EchoErrorDetailsRequest.Builder.class);
  }

  public static final int SINGLE_DETAIL_TEXT_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object singleDetailText_ = "";
  /**
   * <pre>
   * Content to return in a singular `*.error.details` field of type
   * `google.protobuf.Any`
   * </pre>
   *
   * <code>string single_detail_text = 1;</code>
   * @return The singleDetailText.
   */
  @java.lang.Override
  public java.lang.String getSingleDetailText() {
    java.lang.Object ref = singleDetailText_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      singleDetailText_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Content to return in a singular `*.error.details` field of type
   * `google.protobuf.Any`
   * </pre>
   *
   * <code>string single_detail_text = 1;</code>
   * @return The bytes for singleDetailText.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getSingleDetailTextBytes() {
    java.lang.Object ref = singleDetailText_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      singleDetailText_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int MULTI_DETAIL_TEXT_FIELD_NUMBER = 2;
  @SuppressWarnings("serial")
  private com.google.protobuf.LazyStringArrayList multiDetailText_ =
      com.google.protobuf.LazyStringArrayList.emptyList();
  /**
   * <pre>
   * Content to return in a repeated `*.error.details` field of type
   * `google.protobuf.Any`
   * </pre>
   *
   * <code>repeated string multi_detail_text = 2;</code>
   * @return A list containing the multiDetailText.
   */
  public com.google.protobuf.ProtocolStringList
      getMultiDetailTextList() {
    return multiDetailText_;
  }
  /**
   * <pre>
   * Content to return in a repeated `*.error.details` field of type
   * `google.protobuf.Any`
   * </pre>
   *
   * <code>repeated string multi_detail_text = 2;</code>
   * @return The count of multiDetailText.
   */
  public int getMultiDetailTextCount() {
    return multiDetailText_.size();
  }
  /**
   * <pre>
   * Content to return in a repeated `*.error.details` field of type
   * `google.protobuf.Any`
   * </pre>
   *
   * <code>repeated string multi_detail_text = 2;</code>
   * @param index The index of the element to return.
   * @return The multiDetailText at the given index.
   */
  public java.lang.String getMultiDetailText(int index) {
    return multiDetailText_.get(index);
  }
  /**
   * <pre>
   * Content to return in a repeated `*.error.details` field of type
   * `google.protobuf.Any`
   * </pre>
   *
   * <code>repeated string multi_detail_text = 2;</code>
   * @param index The index of the value to return.
   * @return The bytes of the multiDetailText at the given index.
   */
  public com.google.protobuf.ByteString
      getMultiDetailTextBytes(int index) {
    return multiDetailText_.getByteString(index);
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
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(singleDetailText_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, singleDetailText_);
    }
    for (int i = 0; i < multiDetailText_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, multiDetailText_.getRaw(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(singleDetailText_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, singleDetailText_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < multiDetailText_.size(); i++) {
        dataSize += computeStringSizeNoTag(multiDetailText_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getMultiDetailTextList().size();
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
    if (!(obj instanceof com.google.showcase.v1beta1.EchoErrorDetailsRequest)) {
      return super.equals(obj);
    }
    com.google.showcase.v1beta1.EchoErrorDetailsRequest other = (com.google.showcase.v1beta1.EchoErrorDetailsRequest) obj;

    if (!getSingleDetailText()
        .equals(other.getSingleDetailText())) return false;
    if (!getMultiDetailTextList()
        .equals(other.getMultiDetailTextList())) return false;
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
    hash = (37 * hash) + SINGLE_DETAIL_TEXT_FIELD_NUMBER;
    hash = (53 * hash) + getSingleDetailText().hashCode();
    if (getMultiDetailTextCount() > 0) {
      hash = (37 * hash) + MULTI_DETAIL_TEXT_FIELD_NUMBER;
      hash = (53 * hash) + getMultiDetailTextList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.showcase.v1beta1.EchoErrorDetailsRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.EchoErrorDetailsRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.EchoErrorDetailsRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.EchoErrorDetailsRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.EchoErrorDetailsRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.EchoErrorDetailsRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.EchoErrorDetailsRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.showcase.v1beta1.EchoErrorDetailsRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.EchoErrorDetailsRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.EchoErrorDetailsRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.EchoErrorDetailsRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.showcase.v1beta1.EchoErrorDetailsRequest parseFrom(
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
  public static Builder newBuilder(com.google.showcase.v1beta1.EchoErrorDetailsRequest prototype) {
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
   * The request message used for the EchoErrorDetails method.
   * </pre>
   *
   * Protobuf type {@code google.showcase.v1beta1.EchoErrorDetailsRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:google.showcase.v1beta1.EchoErrorDetailsRequest)
      com.google.showcase.v1beta1.EchoErrorDetailsRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.google.showcase.v1beta1.EchoOuterClass.internal_static_google_showcase_v1beta1_EchoErrorDetailsRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.showcase.v1beta1.EchoOuterClass.internal_static_google_showcase_v1beta1_EchoErrorDetailsRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.showcase.v1beta1.EchoErrorDetailsRequest.class, com.google.showcase.v1beta1.EchoErrorDetailsRequest.Builder.class);
    }

    // Construct using com.google.showcase.v1beta1.EchoErrorDetailsRequest.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      singleDetailText_ = "";
      multiDetailText_ =
          com.google.protobuf.LazyStringArrayList.emptyList();
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.google.showcase.v1beta1.EchoOuterClass.internal_static_google_showcase_v1beta1_EchoErrorDetailsRequest_descriptor;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.EchoErrorDetailsRequest getDefaultInstanceForType() {
      return com.google.showcase.v1beta1.EchoErrorDetailsRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.EchoErrorDetailsRequest build() {
      com.google.showcase.v1beta1.EchoErrorDetailsRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.EchoErrorDetailsRequest buildPartial() {
      com.google.showcase.v1beta1.EchoErrorDetailsRequest result = new com.google.showcase.v1beta1.EchoErrorDetailsRequest(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.showcase.v1beta1.EchoErrorDetailsRequest result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.singleDetailText_ = singleDetailText_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        multiDetailText_.makeImmutable();
        result.multiDetailText_ = multiDetailText_;
      }
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
      if (other instanceof com.google.showcase.v1beta1.EchoErrorDetailsRequest) {
        return mergeFrom((com.google.showcase.v1beta1.EchoErrorDetailsRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.showcase.v1beta1.EchoErrorDetailsRequest other) {
      if (other == com.google.showcase.v1beta1.EchoErrorDetailsRequest.getDefaultInstance()) return this;
      if (!other.getSingleDetailText().isEmpty()) {
        singleDetailText_ = other.singleDetailText_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (!other.multiDetailText_.isEmpty()) {
        if (multiDetailText_.isEmpty()) {
          multiDetailText_ = other.multiDetailText_;
          bitField0_ |= 0x00000002;
        } else {
          ensureMultiDetailTextIsMutable();
          multiDetailText_.addAll(other.multiDetailText_);
        }
        onChanged();
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
              singleDetailText_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();
              ensureMultiDetailTextIsMutable();
              multiDetailText_.add(s);
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

    private java.lang.Object singleDetailText_ = "";
    /**
     * <pre>
     * Content to return in a singular `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>string single_detail_text = 1;</code>
     * @return The singleDetailText.
     */
    public java.lang.String getSingleDetailText() {
      java.lang.Object ref = singleDetailText_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        singleDetailText_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Content to return in a singular `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>string single_detail_text = 1;</code>
     * @return The bytes for singleDetailText.
     */
    public com.google.protobuf.ByteString
        getSingleDetailTextBytes() {
      java.lang.Object ref = singleDetailText_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        singleDetailText_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Content to return in a singular `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>string single_detail_text = 1;</code>
     * @param value The singleDetailText to set.
     * @return This builder for chaining.
     */
    public Builder setSingleDetailText(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      singleDetailText_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Content to return in a singular `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>string single_detail_text = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearSingleDetailText() {
      singleDetailText_ = getDefaultInstance().getSingleDetailText();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Content to return in a singular `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>string single_detail_text = 1;</code>
     * @param value The bytes for singleDetailText to set.
     * @return This builder for chaining.
     */
    public Builder setSingleDetailTextBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      singleDetailText_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private com.google.protobuf.LazyStringArrayList multiDetailText_ =
        com.google.protobuf.LazyStringArrayList.emptyList();
    private void ensureMultiDetailTextIsMutable() {
      if (!multiDetailText_.isModifiable()) {
        multiDetailText_ = new com.google.protobuf.LazyStringArrayList(multiDetailText_);
      }
      bitField0_ |= 0x00000002;
    }
    /**
     * <pre>
     * Content to return in a repeated `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>repeated string multi_detail_text = 2;</code>
     * @return A list containing the multiDetailText.
     */
    public com.google.protobuf.ProtocolStringList
        getMultiDetailTextList() {
      multiDetailText_.makeImmutable();
      return multiDetailText_;
    }
    /**
     * <pre>
     * Content to return in a repeated `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>repeated string multi_detail_text = 2;</code>
     * @return The count of multiDetailText.
     */
    public int getMultiDetailTextCount() {
      return multiDetailText_.size();
    }
    /**
     * <pre>
     * Content to return in a repeated `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>repeated string multi_detail_text = 2;</code>
     * @param index The index of the element to return.
     * @return The multiDetailText at the given index.
     */
    public java.lang.String getMultiDetailText(int index) {
      return multiDetailText_.get(index);
    }
    /**
     * <pre>
     * Content to return in a repeated `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>repeated string multi_detail_text = 2;</code>
     * @param index The index of the value to return.
     * @return The bytes of the multiDetailText at the given index.
     */
    public com.google.protobuf.ByteString
        getMultiDetailTextBytes(int index) {
      return multiDetailText_.getByteString(index);
    }
    /**
     * <pre>
     * Content to return in a repeated `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>repeated string multi_detail_text = 2;</code>
     * @param index The index to set the value at.
     * @param value The multiDetailText to set.
     * @return This builder for chaining.
     */
    public Builder setMultiDetailText(
        int index, java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      ensureMultiDetailTextIsMutable();
      multiDetailText_.set(index, value);
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Content to return in a repeated `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>repeated string multi_detail_text = 2;</code>
     * @param value The multiDetailText to add.
     * @return This builder for chaining.
     */
    public Builder addMultiDetailText(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      ensureMultiDetailTextIsMutable();
      multiDetailText_.add(value);
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Content to return in a repeated `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>repeated string multi_detail_text = 2;</code>
     * @param values The multiDetailText to add.
     * @return This builder for chaining.
     */
    public Builder addAllMultiDetailText(
        java.lang.Iterable<java.lang.String> values) {
      ensureMultiDetailTextIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, multiDetailText_);
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Content to return in a repeated `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>repeated string multi_detail_text = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearMultiDetailText() {
      multiDetailText_ =
        com.google.protobuf.LazyStringArrayList.emptyList();
      bitField0_ = (bitField0_ & ~0x00000002);;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Content to return in a repeated `*.error.details` field of type
     * `google.protobuf.Any`
     * </pre>
     *
     * <code>repeated string multi_detail_text = 2;</code>
     * @param value The bytes of the multiDetailText to add.
     * @return This builder for chaining.
     */
    public Builder addMultiDetailTextBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      ensureMultiDetailTextIsMutable();
      multiDetailText_.add(value);
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
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


    // @@protoc_insertion_point(builder_scope:google.showcase.v1beta1.EchoErrorDetailsRequest)
  }

  // @@protoc_insertion_point(class_scope:google.showcase.v1beta1.EchoErrorDetailsRequest)
  private static final com.google.showcase.v1beta1.EchoErrorDetailsRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.google.showcase.v1beta1.EchoErrorDetailsRequest();
  }

  public static com.google.showcase.v1beta1.EchoErrorDetailsRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<EchoErrorDetailsRequest>
      PARSER = new com.google.protobuf.AbstractParser<EchoErrorDetailsRequest>() {
    @java.lang.Override
    public EchoErrorDetailsRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<EchoErrorDetailsRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<EchoErrorDetailsRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.showcase.v1beta1.EchoErrorDetailsRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

