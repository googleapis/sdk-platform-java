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
// source: google/cloud/location/locations.proto

// Protobuf Java Version: 3.25.5
package com.google.cloud.location;

/**
 *
 *
 * <pre>
 * The response message for [Locations.ListLocations][google.cloud.location.Locations.ListLocations].
 * </pre>
 *
 * Protobuf type {@code google.cloud.location.ListLocationsResponse}
 */
public final class ListLocationsResponse extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.cloud.location.ListLocationsResponse)
    ListLocationsResponseOrBuilder {
  private static final long serialVersionUID = 0L;

  // Use ListLocationsResponse.newBuilder() to construct.
  private ListLocationsResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private ListLocationsResponse() {
    locations_ = java.util.Collections.emptyList();
    nextPageToken_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new ListLocationsResponse();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.cloud.location.LocationsProto
        .internal_static_google_cloud_location_ListLocationsResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.cloud.location.LocationsProto
        .internal_static_google_cloud_location_ListLocationsResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.cloud.location.ListLocationsResponse.class,
            com.google.cloud.location.ListLocationsResponse.Builder.class);
  }

  public static final int LOCATIONS_FIELD_NUMBER = 1;

  @SuppressWarnings("serial")
  private java.util.List<com.google.cloud.location.Location> locations_;

  /**
   *
   *
   * <pre>
   * A list of locations that matches the specified filter in the request.
   * </pre>
   *
   * <code>repeated .google.cloud.location.Location locations = 1;</code>
   */
  @java.lang.Override
  public java.util.List<com.google.cloud.location.Location> getLocationsList() {
    return locations_;
  }

  /**
   *
   *
   * <pre>
   * A list of locations that matches the specified filter in the request.
   * </pre>
   *
   * <code>repeated .google.cloud.location.Location locations = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends com.google.cloud.location.LocationOrBuilder>
      getLocationsOrBuilderList() {
    return locations_;
  }

  /**
   *
   *
   * <pre>
   * A list of locations that matches the specified filter in the request.
   * </pre>
   *
   * <code>repeated .google.cloud.location.Location locations = 1;</code>
   */
  @java.lang.Override
  public int getLocationsCount() {
    return locations_.size();
  }

  /**
   *
   *
   * <pre>
   * A list of locations that matches the specified filter in the request.
   * </pre>
   *
   * <code>repeated .google.cloud.location.Location locations = 1;</code>
   */
  @java.lang.Override
  public com.google.cloud.location.Location getLocations(int index) {
    return locations_.get(index);
  }

  /**
   *
   *
   * <pre>
   * A list of locations that matches the specified filter in the request.
   * </pre>
   *
   * <code>repeated .google.cloud.location.Location locations = 1;</code>
   */
  @java.lang.Override
  public com.google.cloud.location.LocationOrBuilder getLocationsOrBuilder(int index) {
    return locations_.get(index);
  }

  public static final int NEXT_PAGE_TOKEN_FIELD_NUMBER = 2;

  @SuppressWarnings("serial")
  private volatile java.lang.Object nextPageToken_ = "";

  /**
   *
   *
   * <pre>
   * The standard List next-page token.
   * </pre>
   *
   * <code>string next_page_token = 2;</code>
   *
   * @return The nextPageToken.
   */
  @java.lang.Override
  public java.lang.String getNextPageToken() {
    java.lang.Object ref = nextPageToken_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      nextPageToken_ = s;
      return s;
    }
  }

  /**
   *
   *
   * <pre>
   * The standard List next-page token.
   * </pre>
   *
   * <code>string next_page_token = 2;</code>
   *
   * @return The bytes for nextPageToken.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getNextPageTokenBytes() {
    java.lang.Object ref = nextPageToken_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
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
  public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
    for (int i = 0; i < locations_.size(); i++) {
      output.writeMessage(1, locations_.get(i));
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
    for (int i = 0; i < locations_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, locations_.get(i));
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
    if (!(obj instanceof com.google.cloud.location.ListLocationsResponse)) {
      return super.equals(obj);
    }
    com.google.cloud.location.ListLocationsResponse other =
        (com.google.cloud.location.ListLocationsResponse) obj;

    if (!getLocationsList().equals(other.getLocationsList())) return false;
    if (!getNextPageToken().equals(other.getNextPageToken())) return false;
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
    if (getLocationsCount() > 0) {
      hash = (37 * hash) + LOCATIONS_FIELD_NUMBER;
      hash = (53 * hash) + getLocationsList().hashCode();
    }
    hash = (37 * hash) + NEXT_PAGE_TOKEN_FIELD_NUMBER;
    hash = (53 * hash) + getNextPageToken().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.cloud.location.ListLocationsResponse parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.cloud.location.ListLocationsResponse parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.cloud.location.ListLocationsResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.cloud.location.ListLocationsResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.cloud.location.ListLocationsResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.cloud.location.ListLocationsResponse parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.cloud.location.ListLocationsResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.cloud.location.ListLocationsResponse parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.cloud.location.ListLocationsResponse parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.cloud.location.ListLocationsResponse parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.cloud.location.ListLocationsResponse parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.cloud.location.ListLocationsResponse parseFrom(
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

  public static Builder newBuilder(com.google.cloud.location.ListLocationsResponse prototype) {
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
   * The response message for [Locations.ListLocations][google.cloud.location.Locations.ListLocations].
   * </pre>
   *
   * Protobuf type {@code google.cloud.location.ListLocationsResponse}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.cloud.location.ListLocationsResponse)
      com.google.cloud.location.ListLocationsResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.cloud.location.LocationsProto
          .internal_static_google_cloud_location_ListLocationsResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.cloud.location.LocationsProto
          .internal_static_google_cloud_location_ListLocationsResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.cloud.location.ListLocationsResponse.class,
              com.google.cloud.location.ListLocationsResponse.Builder.class);
    }

    // Construct using com.google.cloud.location.ListLocationsResponse.newBuilder()
    private Builder() {}

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      if (locationsBuilder_ == null) {
        locations_ = java.util.Collections.emptyList();
      } else {
        locations_ = null;
        locationsBuilder_.clear();
      }
      bitField0_ = (bitField0_ & ~0x00000001);
      nextPageToken_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.cloud.location.LocationsProto
          .internal_static_google_cloud_location_ListLocationsResponse_descriptor;
    }

    @java.lang.Override
    public com.google.cloud.location.ListLocationsResponse getDefaultInstanceForType() {
      return com.google.cloud.location.ListLocationsResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.cloud.location.ListLocationsResponse build() {
      com.google.cloud.location.ListLocationsResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.cloud.location.ListLocationsResponse buildPartial() {
      com.google.cloud.location.ListLocationsResponse result =
          new com.google.cloud.location.ListLocationsResponse(this);
      buildPartialRepeatedFields(result);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartialRepeatedFields(
        com.google.cloud.location.ListLocationsResponse result) {
      if (locationsBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          locations_ = java.util.Collections.unmodifiableList(locations_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.locations_ = locations_;
      } else {
        result.locations_ = locationsBuilder_.build();
      }
    }

    private void buildPartial0(com.google.cloud.location.ListLocationsResponse result) {
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
      if (other instanceof com.google.cloud.location.ListLocationsResponse) {
        return mergeFrom((com.google.cloud.location.ListLocationsResponse) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.cloud.location.ListLocationsResponse other) {
      if (other == com.google.cloud.location.ListLocationsResponse.getDefaultInstance())
        return this;
      if (locationsBuilder_ == null) {
        if (!other.locations_.isEmpty()) {
          if (locations_.isEmpty()) {
            locations_ = other.locations_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureLocationsIsMutable();
            locations_.addAll(other.locations_);
          }
          onChanged();
        }
      } else {
        if (!other.locations_.isEmpty()) {
          if (locationsBuilder_.isEmpty()) {
            locationsBuilder_.dispose();
            locationsBuilder_ = null;
            locations_ = other.locations_;
            bitField0_ = (bitField0_ & ~0x00000001);
            locationsBuilder_ =
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders
                    ? getLocationsFieldBuilder()
                    : null;
          } else {
            locationsBuilder_.addAllMessages(other.locations_);
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
            case 10:
              {
                com.google.cloud.location.Location m =
                    input.readMessage(
                        com.google.cloud.location.Location.parser(), extensionRegistry);
                if (locationsBuilder_ == null) {
                  ensureLocationsIsMutable();
                  locations_.add(m);
                } else {
                  locationsBuilder_.addMessage(m);
                }
                break;
              } // case 10
            case 18:
              {
                nextPageToken_ = input.readStringRequireUtf8();
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

    private java.util.List<com.google.cloud.location.Location> locations_ =
        java.util.Collections.emptyList();

    private void ensureLocationsIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        locations_ = new java.util.ArrayList<com.google.cloud.location.Location>(locations_);
        bitField0_ |= 0x00000001;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.google.cloud.location.Location,
            com.google.cloud.location.Location.Builder,
            com.google.cloud.location.LocationOrBuilder>
        locationsBuilder_;

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public java.util.List<com.google.cloud.location.Location> getLocationsList() {
      if (locationsBuilder_ == null) {
        return java.util.Collections.unmodifiableList(locations_);
      } else {
        return locationsBuilder_.getMessageList();
      }
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public int getLocationsCount() {
      if (locationsBuilder_ == null) {
        return locations_.size();
      } else {
        return locationsBuilder_.getCount();
      }
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public com.google.cloud.location.Location getLocations(int index) {
      if (locationsBuilder_ == null) {
        return locations_.get(index);
      } else {
        return locationsBuilder_.getMessage(index);
      }
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public Builder setLocations(int index, com.google.cloud.location.Location value) {
      if (locationsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLocationsIsMutable();
        locations_.set(index, value);
        onChanged();
      } else {
        locationsBuilder_.setMessage(index, value);
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public Builder setLocations(
        int index, com.google.cloud.location.Location.Builder builderForValue) {
      if (locationsBuilder_ == null) {
        ensureLocationsIsMutable();
        locations_.set(index, builderForValue.build());
        onChanged();
      } else {
        locationsBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public Builder addLocations(com.google.cloud.location.Location value) {
      if (locationsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLocationsIsMutable();
        locations_.add(value);
        onChanged();
      } else {
        locationsBuilder_.addMessage(value);
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public Builder addLocations(int index, com.google.cloud.location.Location value) {
      if (locationsBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLocationsIsMutable();
        locations_.add(index, value);
        onChanged();
      } else {
        locationsBuilder_.addMessage(index, value);
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public Builder addLocations(com.google.cloud.location.Location.Builder builderForValue) {
      if (locationsBuilder_ == null) {
        ensureLocationsIsMutable();
        locations_.add(builderForValue.build());
        onChanged();
      } else {
        locationsBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public Builder addLocations(
        int index, com.google.cloud.location.Location.Builder builderForValue) {
      if (locationsBuilder_ == null) {
        ensureLocationsIsMutable();
        locations_.add(index, builderForValue.build());
        onChanged();
      } else {
        locationsBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public Builder addAllLocations(
        java.lang.Iterable<? extends com.google.cloud.location.Location> values) {
      if (locationsBuilder_ == null) {
        ensureLocationsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, locations_);
        onChanged();
      } else {
        locationsBuilder_.addAllMessages(values);
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public Builder clearLocations() {
      if (locationsBuilder_ == null) {
        locations_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        locationsBuilder_.clear();
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public Builder removeLocations(int index) {
      if (locationsBuilder_ == null) {
        ensureLocationsIsMutable();
        locations_.remove(index);
        onChanged();
      } else {
        locationsBuilder_.remove(index);
      }
      return this;
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public com.google.cloud.location.Location.Builder getLocationsBuilder(int index) {
      return getLocationsFieldBuilder().getBuilder(index);
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public com.google.cloud.location.LocationOrBuilder getLocationsOrBuilder(int index) {
      if (locationsBuilder_ == null) {
        return locations_.get(index);
      } else {
        return locationsBuilder_.getMessageOrBuilder(index);
      }
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public java.util.List<? extends com.google.cloud.location.LocationOrBuilder>
        getLocationsOrBuilderList() {
      if (locationsBuilder_ != null) {
        return locationsBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(locations_);
      }
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public com.google.cloud.location.Location.Builder addLocationsBuilder() {
      return getLocationsFieldBuilder()
          .addBuilder(com.google.cloud.location.Location.getDefaultInstance());
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public com.google.cloud.location.Location.Builder addLocationsBuilder(int index) {
      return getLocationsFieldBuilder()
          .addBuilder(index, com.google.cloud.location.Location.getDefaultInstance());
    }

    /**
     *
     *
     * <pre>
     * A list of locations that matches the specified filter in the request.
     * </pre>
     *
     * <code>repeated .google.cloud.location.Location locations = 1;</code>
     */
    public java.util.List<com.google.cloud.location.Location.Builder> getLocationsBuilderList() {
      return getLocationsFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
            com.google.cloud.location.Location,
            com.google.cloud.location.Location.Builder,
            com.google.cloud.location.LocationOrBuilder>
        getLocationsFieldBuilder() {
      if (locationsBuilder_ == null) {
        locationsBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilderV3<
                com.google.cloud.location.Location,
                com.google.cloud.location.Location.Builder,
                com.google.cloud.location.LocationOrBuilder>(
                locations_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
        locations_ = null;
      }
      return locationsBuilder_;
    }

    private java.lang.Object nextPageToken_ = "";

    /**
     *
     *
     * <pre>
     * The standard List next-page token.
     * </pre>
     *
     * <code>string next_page_token = 2;</code>
     *
     * @return The nextPageToken.
     */
    public java.lang.String getNextPageToken() {
      java.lang.Object ref = nextPageToken_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        nextPageToken_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }

    /**
     *
     *
     * <pre>
     * The standard List next-page token.
     * </pre>
     *
     * <code>string next_page_token = 2;</code>
     *
     * @return The bytes for nextPageToken.
     */
    public com.google.protobuf.ByteString getNextPageTokenBytes() {
      java.lang.Object ref = nextPageToken_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        nextPageToken_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    /**
     *
     *
     * <pre>
     * The standard List next-page token.
     * </pre>
     *
     * <code>string next_page_token = 2;</code>
     *
     * @param value The nextPageToken to set.
     * @return This builder for chaining.
     */
    public Builder setNextPageToken(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      nextPageToken_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The standard List next-page token.
     * </pre>
     *
     * <code>string next_page_token = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearNextPageToken() {
      nextPageToken_ = getDefaultInstance().getNextPageToken();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The standard List next-page token.
     * </pre>
     *
     * <code>string next_page_token = 2;</code>
     *
     * @param value The bytes for nextPageToken to set.
     * @return This builder for chaining.
     */
    public Builder setNextPageTokenBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);
      nextPageToken_ = value;
      bitField0_ |= 0x00000002;
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

    // @@protoc_insertion_point(builder_scope:google.cloud.location.ListLocationsResponse)
  }

  // @@protoc_insertion_point(class_scope:google.cloud.location.ListLocationsResponse)
  private static final com.google.cloud.location.ListLocationsResponse DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.cloud.location.ListLocationsResponse();
  }

  public static com.google.cloud.location.ListLocationsResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ListLocationsResponse> PARSER =
      new com.google.protobuf.AbstractParser<ListLocationsResponse>() {
        @java.lang.Override
        public ListLocationsResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<ListLocationsResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ListLocationsResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.cloud.location.ListLocationsResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
