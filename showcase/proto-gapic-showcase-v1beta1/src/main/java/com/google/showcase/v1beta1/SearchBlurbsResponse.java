// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/messaging.proto

// Protobuf Java Version: 3.25.4
package com.google.showcase.v1beta1;

/**
 * <pre>
 * The operation response message for the
 * google.showcase.v1beta1.Messaging&#92;SearchBlurbs method.
 * </pre>
 *
 * Protobuf type {@code google.showcase.v1beta1.SearchBlurbsResponse}
 */
public final class SearchBlurbsResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:google.showcase.v1beta1.SearchBlurbsResponse)
    SearchBlurbsResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use SearchBlurbsResponse.newBuilder() to construct.
  private SearchBlurbsResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private SearchBlurbsResponse() {
    blurbs_ = java.util.Collections.emptyList();
    nextPageToken_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new SearchBlurbsResponse();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.google.showcase.v1beta1.MessagingOuterClass.internal_static_google_showcase_v1beta1_SearchBlurbsResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.showcase.v1beta1.MessagingOuterClass.internal_static_google_showcase_v1beta1_SearchBlurbsResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.showcase.v1beta1.SearchBlurbsResponse.class, com.google.showcase.v1beta1.SearchBlurbsResponse.Builder.class);
  }

  public static final int BLURBS_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private java.util.List<com.google.showcase.v1beta1.Blurb> blurbs_;
  /**
   * <pre>
   * Blurbs that matched the search query.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
   */
  @java.lang.Override
  public java.util.List<com.google.showcase.v1beta1.Blurb> getBlurbsList() {
    return blurbs_;
  }
  /**
   * <pre>
   * Blurbs that matched the search query.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends com.google.showcase.v1beta1.BlurbOrBuilder> 
      getBlurbsOrBuilderList() {
    return blurbs_;
  }
  /**
   * <pre>
   * Blurbs that matched the search query.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
   */
  @java.lang.Override
  public int getBlurbsCount() {
    return blurbs_.size();
  }
  /**
   * <pre>
   * Blurbs that matched the search query.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
   */
  @java.lang.Override
  public com.google.showcase.v1beta1.Blurb getBlurbs(int index) {
    return blurbs_.get(index);
  }
  /**
   * <pre>
   * Blurbs that matched the search query.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
   */
  @java.lang.Override
  public com.google.showcase.v1beta1.BlurbOrBuilder getBlurbsOrBuilder(
      int index) {
    return blurbs_.get(index);
  }

  public static final int NEXT_PAGE_TOKEN_FIELD_NUMBER = 2;
  @SuppressWarnings("serial")
  private volatile java.lang.Object nextPageToken_ = "";
  /**
   * <pre>
   * A token to retrieve next page of results.
   * Pass this value in SearchBlurbsRequest.page_token field in the subsequent
   * call to `google.showcase.v1beta1.Blurb&#92;SearchBlurbs` method to
   * retrieve the next page of results.
   * </pre>
   *
   * <code>string next_page_token = 2;</code>
   * @return The nextPageToken.
   */
  @java.lang.Override
  public java.lang.String getNextPageToken() {
    java.lang.Object ref = nextPageToken_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      nextPageToken_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * A token to retrieve next page of results.
   * Pass this value in SearchBlurbsRequest.page_token field in the subsequent
   * call to `google.showcase.v1beta1.Blurb&#92;SearchBlurbs` method to
   * retrieve the next page of results.
   * </pre>
   *
   * <code>string next_page_token = 2;</code>
   * @return The bytes for nextPageToken.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getNextPageTokenBytes() {
    java.lang.Object ref = nextPageToken_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      nextPageToken_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    for (int i = 0; i < blurbs_.size(); i++) {
      output.writeMessage(1, blurbs_.get(i));
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(nextPageToken_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, nextPageToken_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < blurbs_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, blurbs_.get(i));
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(nextPageToken_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, nextPageToken_);
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
    if (!(obj instanceof com.google.showcase.v1beta1.SearchBlurbsResponse)) {
      return super.equals(obj);
    }
    com.google.showcase.v1beta1.SearchBlurbsResponse other = (com.google.showcase.v1beta1.SearchBlurbsResponse) obj;

    if (!getBlurbsList()
        .equals(other.getBlurbsList())) return false;
    if (!getNextPageToken()
        .equals(other.getNextPageToken())) return false;
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
    if (getBlurbsCount() > 0) {
      hash = (37 * hash) + BLURBS_FIELD_NUMBER;
      hash = (53 * hash) + getBlurbsList().hashCode();
    }
    hash = (37 * hash) + NEXT_PAGE_TOKEN_FIELD_NUMBER;
    hash = (53 * hash) + getNextPageToken().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.showcase.v1beta1.SearchBlurbsResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.SearchBlurbsResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.SearchBlurbsResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.SearchBlurbsResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.SearchBlurbsResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.SearchBlurbsResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.SearchBlurbsResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.showcase.v1beta1.SearchBlurbsResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.SearchBlurbsResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.SearchBlurbsResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.SearchBlurbsResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.showcase.v1beta1.SearchBlurbsResponse parseFrom(
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
  public static Builder newBuilder(com.google.showcase.v1beta1.SearchBlurbsResponse prototype) {
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
   * The operation response message for the
   * google.showcase.v1beta1.Messaging&#92;SearchBlurbs method.
   * </pre>
   *
   * Protobuf type {@code google.showcase.v1beta1.SearchBlurbsResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:google.showcase.v1beta1.SearchBlurbsResponse)
      com.google.showcase.v1beta1.SearchBlurbsResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.google.showcase.v1beta1.MessagingOuterClass.internal_static_google_showcase_v1beta1_SearchBlurbsResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.showcase.v1beta1.MessagingOuterClass.internal_static_google_showcase_v1beta1_SearchBlurbsResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.showcase.v1beta1.SearchBlurbsResponse.class, com.google.showcase.v1beta1.SearchBlurbsResponse.Builder.class);
    }

    // Construct using com.google.showcase.v1beta1.SearchBlurbsResponse.newBuilder()
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
      if (blurbsBuilder_ == null) {
        blurbs_ = java.util.Collections.emptyList();
      } else {
        blurbs_ = null;
        blurbsBuilder_.clear();
      }
      bitField0_ = (bitField0_ & ~0x00000001);
      nextPageToken_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.google.showcase.v1beta1.MessagingOuterClass.internal_static_google_showcase_v1beta1_SearchBlurbsResponse_descriptor;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.SearchBlurbsResponse getDefaultInstanceForType() {
      return com.google.showcase.v1beta1.SearchBlurbsResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.SearchBlurbsResponse build() {
      com.google.showcase.v1beta1.SearchBlurbsResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.SearchBlurbsResponse buildPartial() {
      com.google.showcase.v1beta1.SearchBlurbsResponse result = new com.google.showcase.v1beta1.SearchBlurbsResponse(this);
      buildPartialRepeatedFields(result);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartialRepeatedFields(com.google.showcase.v1beta1.SearchBlurbsResponse result) {
      if (blurbsBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          blurbs_ = java.util.Collections.unmodifiableList(blurbs_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.blurbs_ = blurbs_;
      } else {
        result.blurbs_ = blurbsBuilder_.build();
      }
    }

    private void buildPartial0(com.google.showcase.v1beta1.SearchBlurbsResponse result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.nextPageToken_ = nextPageToken_;
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
      if (other instanceof com.google.showcase.v1beta1.SearchBlurbsResponse) {
        return mergeFrom((com.google.showcase.v1beta1.SearchBlurbsResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.showcase.v1beta1.SearchBlurbsResponse other) {
      if (other == com.google.showcase.v1beta1.SearchBlurbsResponse.getDefaultInstance()) return this;
      if (blurbsBuilder_ == null) {
        if (!other.blurbs_.isEmpty()) {
          if (blurbs_.isEmpty()) {
            blurbs_ = other.blurbs_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureBlurbsIsMutable();
            blurbs_.addAll(other.blurbs_);
          }
          onChanged();
        }
      } else {
        if (!other.blurbs_.isEmpty()) {
          if (blurbsBuilder_.isEmpty()) {
            blurbsBuilder_.dispose();
            blurbsBuilder_ = null;
            blurbs_ = other.blurbs_;
            bitField0_ = (bitField0_ & ~0x00000001);
            blurbsBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getBlurbsFieldBuilder() : null;
          } else {
            blurbsBuilder_.addAllMessages(other.blurbs_);
          }
        }
      }
      if (!other.getNextPageToken().isEmpty()) {
        nextPageToken_ = other.nextPageToken_;
        bitField0_ |= 0x00000002;
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
              com.google.showcase.v1beta1.Blurb m =
                  input.readMessage(
                      com.google.showcase.v1beta1.Blurb.parser(),
                      extensionRegistry);
              if (blurbsBuilder_ == null) {
                ensureBlurbsIsMutable();
                blurbs_.add(m);
              } else {
                blurbsBuilder_.addMessage(m);
              }
              break;
            } // case 10
            case 18: {
              nextPageToken_ = input.readStringRequireUtf8();
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

    private java.util.List<com.google.showcase.v1beta1.Blurb> blurbs_ =
      java.util.Collections.emptyList();
    private void ensureBlurbsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        blurbs_ = new java.util.ArrayList<com.google.showcase.v1beta1.Blurb>(blurbs_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.google.showcase.v1beta1.Blurb, com.google.showcase.v1beta1.Blurb.Builder, com.google.showcase.v1beta1.BlurbOrBuilder> blurbsBuilder_;

    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public java.util.List<com.google.showcase.v1beta1.Blurb> getBlurbsList() {
      if (blurbsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(blurbs_);
      } else {
        return blurbsBuilder_.getMessageList();
      }
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public int getBlurbsCount() {
      if (blurbsBuilder_ == null) {
        return blurbs_.size();
      } else {
        return blurbsBuilder_.getCount();
      }
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public com.google.showcase.v1beta1.Blurb getBlurbs(int index) {
      if (blurbsBuilder_ == null) {
        return blurbs_.get(index);
      } else {
        return blurbsBuilder_.getMessage(index);
      }
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public Builder setBlurbs(
        int index, com.google.showcase.v1beta1.Blurb value) {
      if (blurbsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBlurbsIsMutable();
        blurbs_.set(index, value);
        onChanged();
      } else {
        blurbsBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public Builder setBlurbs(
        int index, com.google.showcase.v1beta1.Blurb.Builder builderForValue) {
      if (blurbsBuilder_ == null) {
        ensureBlurbsIsMutable();
        blurbs_.set(index, builderForValue.build());
        onChanged();
      } else {
        blurbsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public Builder addBlurbs(com.google.showcase.v1beta1.Blurb value) {
      if (blurbsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBlurbsIsMutable();
        blurbs_.add(value);
        onChanged();
      } else {
        blurbsBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public Builder addBlurbs(
        int index, com.google.showcase.v1beta1.Blurb value) {
      if (blurbsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureBlurbsIsMutable();
        blurbs_.add(index, value);
        onChanged();
      } else {
        blurbsBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public Builder addBlurbs(
        com.google.showcase.v1beta1.Blurb.Builder builderForValue) {
      if (blurbsBuilder_ == null) {
        ensureBlurbsIsMutable();
        blurbs_.add(builderForValue.build());
        onChanged();
      } else {
        blurbsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public Builder addBlurbs(
        int index, com.google.showcase.v1beta1.Blurb.Builder builderForValue) {
      if (blurbsBuilder_ == null) {
        ensureBlurbsIsMutable();
        blurbs_.add(index, builderForValue.build());
        onChanged();
      } else {
        blurbsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public Builder addAllBlurbs(
        java.lang.Iterable<? extends com.google.showcase.v1beta1.Blurb> values) {
      if (blurbsBuilder_ == null) {
        ensureBlurbsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, blurbs_);
        onChanged();
      } else {
        blurbsBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public Builder clearBlurbs() {
      if (blurbsBuilder_ == null) {
        blurbs_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        blurbsBuilder_.clear();
      }
      return this;
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public Builder removeBlurbs(int index) {
      if (blurbsBuilder_ == null) {
        ensureBlurbsIsMutable();
        blurbs_.remove(index);
        onChanged();
      } else {
        blurbsBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public com.google.showcase.v1beta1.Blurb.Builder getBlurbsBuilder(
        int index) {
      return getBlurbsFieldBuilder().getBuilder(index);
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public com.google.showcase.v1beta1.BlurbOrBuilder getBlurbsOrBuilder(
        int index) {
      if (blurbsBuilder_ == null) {
        return blurbs_.get(index);  } else {
        return blurbsBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public java.util.List<? extends com.google.showcase.v1beta1.BlurbOrBuilder> 
         getBlurbsOrBuilderList() {
      if (blurbsBuilder_ != null) {
        return blurbsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(blurbs_);
      }
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public com.google.showcase.v1beta1.Blurb.Builder addBlurbsBuilder() {
      return getBlurbsFieldBuilder().addBuilder(
          com.google.showcase.v1beta1.Blurb.getDefaultInstance());
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public com.google.showcase.v1beta1.Blurb.Builder addBlurbsBuilder(
        int index) {
      return getBlurbsFieldBuilder().addBuilder(
          index, com.google.showcase.v1beta1.Blurb.getDefaultInstance());
    }
    /**
     * <pre>
     * Blurbs that matched the search query.
     * </pre>
     *
     * <code>repeated .google.showcase.v1beta1.Blurb blurbs = 1;</code>
     */
    public java.util.List<com.google.showcase.v1beta1.Blurb.Builder> 
         getBlurbsBuilderList() {
      return getBlurbsFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.google.showcase.v1beta1.Blurb, com.google.showcase.v1beta1.Blurb.Builder, com.google.showcase.v1beta1.BlurbOrBuilder> 
        getBlurbsFieldBuilder() {
      if (blurbsBuilder_ == null) {
        blurbsBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.google.showcase.v1beta1.Blurb, com.google.showcase.v1beta1.Blurb.Builder, com.google.showcase.v1beta1.BlurbOrBuilder>(
                blurbs_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        blurbs_ = null;
      }
      return blurbsBuilder_;
    }

    private java.lang.Object nextPageToken_ = "";
    /**
     * <pre>
     * A token to retrieve next page of results.
     * Pass this value in SearchBlurbsRequest.page_token field in the subsequent
     * call to `google.showcase.v1beta1.Blurb&#92;SearchBlurbs` method to
     * retrieve the next page of results.
     * </pre>
     *
     * <code>string next_page_token = 2;</code>
     * @return The nextPageToken.
     */
    public java.lang.String getNextPageToken() {
      java.lang.Object ref = nextPageToken_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        nextPageToken_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * A token to retrieve next page of results.
     * Pass this value in SearchBlurbsRequest.page_token field in the subsequent
     * call to `google.showcase.v1beta1.Blurb&#92;SearchBlurbs` method to
     * retrieve the next page of results.
     * </pre>
     *
     * <code>string next_page_token = 2;</code>
     * @return The bytes for nextPageToken.
     */
    public com.google.protobuf.ByteString
        getNextPageTokenBytes() {
      java.lang.Object ref = nextPageToken_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        nextPageToken_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * A token to retrieve next page of results.
     * Pass this value in SearchBlurbsRequest.page_token field in the subsequent
     * call to `google.showcase.v1beta1.Blurb&#92;SearchBlurbs` method to
     * retrieve the next page of results.
     * </pre>
     *
     * <code>string next_page_token = 2;</code>
     * @param value The nextPageToken to set.
     * @return This builder for chaining.
     */
    public Builder setNextPageToken(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      nextPageToken_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * A token to retrieve next page of results.
     * Pass this value in SearchBlurbsRequest.page_token field in the subsequent
     * call to `google.showcase.v1beta1.Blurb&#92;SearchBlurbs` method to
     * retrieve the next page of results.
     * </pre>
     *
     * <code>string next_page_token = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearNextPageToken() {
      nextPageToken_ = getDefaultInstance().getNextPageToken();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * A token to retrieve next page of results.
     * Pass this value in SearchBlurbsRequest.page_token field in the subsequent
     * call to `google.showcase.v1beta1.Blurb&#92;SearchBlurbs` method to
     * retrieve the next page of results.
     * </pre>
     *
     * <code>string next_page_token = 2;</code>
     * @param value The bytes for nextPageToken to set.
     * @return This builder for chaining.
     */
    public Builder setNextPageTokenBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      nextPageToken_ = value;
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


    // @@protoc_insertion_point(builder_scope:google.showcase.v1beta1.SearchBlurbsResponse)
  }

  // @@protoc_insertion_point(class_scope:google.showcase.v1beta1.SearchBlurbsResponse)
  private static final com.google.showcase.v1beta1.SearchBlurbsResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.google.showcase.v1beta1.SearchBlurbsResponse();
  }

  public static com.google.showcase.v1beta1.SearchBlurbsResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<SearchBlurbsResponse>
      PARSER = new com.google.protobuf.AbstractParser<SearchBlurbsResponse>() {
    @java.lang.Override
    public SearchBlurbsResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<SearchBlurbsResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<SearchBlurbsResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.showcase.v1beta1.SearchBlurbsResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

