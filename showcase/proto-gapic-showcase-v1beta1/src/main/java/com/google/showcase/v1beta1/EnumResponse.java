// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/compliance.proto

package com.google.showcase.v1beta1;

/**
 * Protobuf type {@code google.showcase.v1beta1.EnumResponse}
 */
public final class EnumResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:google.showcase.v1beta1.EnumResponse)
    EnumResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use EnumResponse.newBuilder() to construct.
  private EnumResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private EnumResponse() {
    continent_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new EnumResponse();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.google.showcase.v1beta1.ComplianceOuterClass.internal_static_google_showcase_v1beta1_EnumResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.showcase.v1beta1.ComplianceOuterClass.internal_static_google_showcase_v1beta1_EnumResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.showcase.v1beta1.EnumResponse.class, com.google.showcase.v1beta1.EnumResponse.Builder.class);
  }

  public static final int REQUEST_FIELD_NUMBER = 1;
  private com.google.showcase.v1beta1.EnumRequest request_;
  /**
   * <pre>
   * The original request for a known or unknown enum from the server.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
   * @return Whether the request field is set.
   */
  @java.lang.Override
  public boolean hasRequest() {
    return request_ != null;
  }
  /**
   * <pre>
   * The original request for a known or unknown enum from the server.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
   * @return The request.
   */
  @java.lang.Override
  public com.google.showcase.v1beta1.EnumRequest getRequest() {
    return request_ == null ? com.google.showcase.v1beta1.EnumRequest.getDefaultInstance() : request_;
  }
  /**
   * <pre>
   * The original request for a known or unknown enum from the server.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
   */
  @java.lang.Override
  public com.google.showcase.v1beta1.EnumRequestOrBuilder getRequestOrBuilder() {
    return request_ == null ? com.google.showcase.v1beta1.EnumRequest.getDefaultInstance() : request_;
  }

  public static final int CONTINENT_FIELD_NUMBER = 2;
  private int continent_ = 0;
  /**
   * <pre>
   * The actual enum the server provided.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Continent continent = 2;</code>
   * @return The enum numeric value on the wire for continent.
   */
  @java.lang.Override public int getContinentValue() {
    return continent_;
  }
  /**
   * <pre>
   * The actual enum the server provided.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Continent continent = 2;</code>
   * @return The continent.
   */
  @java.lang.Override public com.google.showcase.v1beta1.Continent getContinent() {
    com.google.showcase.v1beta1.Continent result = com.google.showcase.v1beta1.Continent.forNumber(continent_);
    return result == null ? com.google.showcase.v1beta1.Continent.UNRECOGNIZED : result;
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
    if (request_ != null) {
      output.writeMessage(1, getRequest());
    }
    if (continent_ != com.google.showcase.v1beta1.Continent.CONTINENT_UNSPECIFIED.getNumber()) {
      output.writeEnum(2, continent_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (request_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getRequest());
    }
    if (continent_ != com.google.showcase.v1beta1.Continent.CONTINENT_UNSPECIFIED.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(2, continent_);
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
    if (!(obj instanceof com.google.showcase.v1beta1.EnumResponse)) {
      return super.equals(obj);
    }
    com.google.showcase.v1beta1.EnumResponse other = (com.google.showcase.v1beta1.EnumResponse) obj;

    if (hasRequest() != other.hasRequest()) return false;
    if (hasRequest()) {
      if (!getRequest()
          .equals(other.getRequest())) return false;
    }
    if (continent_ != other.continent_) return false;
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
    if (hasRequest()) {
      hash = (37 * hash) + REQUEST_FIELD_NUMBER;
      hash = (53 * hash) + getRequest().hashCode();
    }
    hash = (37 * hash) + CONTINENT_FIELD_NUMBER;
    hash = (53 * hash) + continent_;
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.showcase.v1beta1.EnumResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.EnumResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.EnumResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.EnumResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.EnumResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.EnumResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.EnumResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.showcase.v1beta1.EnumResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.EnumResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.EnumResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.EnumResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.showcase.v1beta1.EnumResponse parseFrom(
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
  public static Builder newBuilder(com.google.showcase.v1beta1.EnumResponse prototype) {
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
   * Protobuf type {@code google.showcase.v1beta1.EnumResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:google.showcase.v1beta1.EnumResponse)
      com.google.showcase.v1beta1.EnumResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.google.showcase.v1beta1.ComplianceOuterClass.internal_static_google_showcase_v1beta1_EnumResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.showcase.v1beta1.ComplianceOuterClass.internal_static_google_showcase_v1beta1_EnumResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.showcase.v1beta1.EnumResponse.class, com.google.showcase.v1beta1.EnumResponse.Builder.class);
    }

    // Construct using com.google.showcase.v1beta1.EnumResponse.newBuilder()
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
      request_ = null;
      if (requestBuilder_ != null) {
        requestBuilder_.dispose();
        requestBuilder_ = null;
      }
      continent_ = 0;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.google.showcase.v1beta1.ComplianceOuterClass.internal_static_google_showcase_v1beta1_EnumResponse_descriptor;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.EnumResponse getDefaultInstanceForType() {
      return com.google.showcase.v1beta1.EnumResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.EnumResponse build() {
      com.google.showcase.v1beta1.EnumResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.EnumResponse buildPartial() {
      com.google.showcase.v1beta1.EnumResponse result = new com.google.showcase.v1beta1.EnumResponse(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.showcase.v1beta1.EnumResponse result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.request_ = requestBuilder_ == null
            ? request_
            : requestBuilder_.build();
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.continent_ = continent_;
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
      if (other instanceof com.google.showcase.v1beta1.EnumResponse) {
        return mergeFrom((com.google.showcase.v1beta1.EnumResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.showcase.v1beta1.EnumResponse other) {
      if (other == com.google.showcase.v1beta1.EnumResponse.getDefaultInstance()) return this;
      if (other.hasRequest()) {
        mergeRequest(other.getRequest());
      }
      if (other.continent_ != 0) {
        setContinentValue(other.getContinentValue());
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
                  getRequestFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 16: {
              continent_ = input.readEnum();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
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

    private com.google.showcase.v1beta1.EnumRequest request_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.showcase.v1beta1.EnumRequest, com.google.showcase.v1beta1.EnumRequest.Builder, com.google.showcase.v1beta1.EnumRequestOrBuilder> requestBuilder_;
    /**
     * <pre>
     * The original request for a known or unknown enum from the server.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
     * @return Whether the request field is set.
     */
    public boolean hasRequest() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <pre>
     * The original request for a known or unknown enum from the server.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
     * @return The request.
     */
    public com.google.showcase.v1beta1.EnumRequest getRequest() {
      if (requestBuilder_ == null) {
        return request_ == null ? com.google.showcase.v1beta1.EnumRequest.getDefaultInstance() : request_;
      } else {
        return requestBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * The original request for a known or unknown enum from the server.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
     */
    public Builder setRequest(com.google.showcase.v1beta1.EnumRequest value) {
      if (requestBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        request_ = value;
      } else {
        requestBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The original request for a known or unknown enum from the server.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
     */
    public Builder setRequest(
        com.google.showcase.v1beta1.EnumRequest.Builder builderForValue) {
      if (requestBuilder_ == null) {
        request_ = builderForValue.build();
      } else {
        requestBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The original request for a known or unknown enum from the server.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
     */
    public Builder mergeRequest(com.google.showcase.v1beta1.EnumRequest value) {
      if (requestBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0) &&
          request_ != null &&
          request_ != com.google.showcase.v1beta1.EnumRequest.getDefaultInstance()) {
          getRequestBuilder().mergeFrom(value);
        } else {
          request_ = value;
        }
      } else {
        requestBuilder_.mergeFrom(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The original request for a known or unknown enum from the server.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
     */
    public Builder clearRequest() {
      bitField0_ = (bitField0_ & ~0x00000001);
      request_ = null;
      if (requestBuilder_ != null) {
        requestBuilder_.dispose();
        requestBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The original request for a known or unknown enum from the server.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
     */
    public com.google.showcase.v1beta1.EnumRequest.Builder getRequestBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getRequestFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * The original request for a known or unknown enum from the server.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
     */
    public com.google.showcase.v1beta1.EnumRequestOrBuilder getRequestOrBuilder() {
      if (requestBuilder_ != null) {
        return requestBuilder_.getMessageOrBuilder();
      } else {
        return request_ == null ?
            com.google.showcase.v1beta1.EnumRequest.getDefaultInstance() : request_;
      }
    }
    /**
     * <pre>
     * The original request for a known or unknown enum from the server.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.showcase.v1beta1.EnumRequest, com.google.showcase.v1beta1.EnumRequest.Builder, com.google.showcase.v1beta1.EnumRequestOrBuilder> 
        getRequestFieldBuilder() {
      if (requestBuilder_ == null) {
        requestBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.showcase.v1beta1.EnumRequest, com.google.showcase.v1beta1.EnumRequest.Builder, com.google.showcase.v1beta1.EnumRequestOrBuilder>(
                getRequest(),
                getParentForChildren(),
                isClean());
        request_ = null;
      }
      return requestBuilder_;
    }

    private int continent_ = 0;
    /**
     * <pre>
     * The actual enum the server provided.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Continent continent = 2;</code>
     * @return The enum numeric value on the wire for continent.
     */
    @java.lang.Override public int getContinentValue() {
      return continent_;
    }
    /**
     * <pre>
     * The actual enum the server provided.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Continent continent = 2;</code>
     * @param value The enum numeric value on the wire for continent to set.
     * @return This builder for chaining.
     */
    public Builder setContinentValue(int value) {
      continent_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The actual enum the server provided.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Continent continent = 2;</code>
     * @return The continent.
     */
    @java.lang.Override
    public com.google.showcase.v1beta1.Continent getContinent() {
      com.google.showcase.v1beta1.Continent result = com.google.showcase.v1beta1.Continent.forNumber(continent_);
      return result == null ? com.google.showcase.v1beta1.Continent.UNRECOGNIZED : result;
    }
    /**
     * <pre>
     * The actual enum the server provided.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Continent continent = 2;</code>
     * @param value The continent to set.
     * @return This builder for chaining.
     */
    public Builder setContinent(com.google.showcase.v1beta1.Continent value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000002;
      continent_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The actual enum the server provided.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Continent continent = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearContinent() {
      bitField0_ = (bitField0_ & ~0x00000002);
      continent_ = 0;
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


    // @@protoc_insertion_point(builder_scope:google.showcase.v1beta1.EnumResponse)
  }

  // @@protoc_insertion_point(class_scope:google.showcase.v1beta1.EnumResponse)
  private static final com.google.showcase.v1beta1.EnumResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.google.showcase.v1beta1.EnumResponse();
  }

  public static com.google.showcase.v1beta1.EnumResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<EnumResponse>
      PARSER = new com.google.protobuf.AbstractParser<EnumResponse>() {
    @java.lang.Override
    public EnumResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<EnumResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<EnumResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.showcase.v1beta1.EnumResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

