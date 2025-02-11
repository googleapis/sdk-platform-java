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
 * The request message for [Locations.ListLocations][google.cloud.location.Locations.ListLocations].
 * </pre>
 *
 * Protobuf type {@code google.cloud.location.ListLocationsRequest}
 */
public final class ListLocationsRequest
    extends com.google.protobuf.GeneratedMessageLite<
        ListLocationsRequest, ListLocationsRequest.Builder>
    implements
    // @@protoc_insertion_point(message_implements:google.cloud.location.ListLocationsRequest)
    ListLocationsRequestOrBuilder {
  private ListLocationsRequest() {
    name_ = "";
    filter_ = "";
    pageToken_ = "";
  }

  public static final int NAME_FIELD_NUMBER = 1;
  private java.lang.String name_;
  /**
   *
   *
   * <pre>
   * The resource that owns the locations collection, if applicable.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The name.
   */
  @java.lang.Override
  public java.lang.String getName() {
    return name_;
  }
  /**
   *
   *
   * <pre>
   * The resource that owns the locations collection, if applicable.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The bytes for name.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getNameBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(name_);
  }
  /**
   *
   *
   * <pre>
   * The resource that owns the locations collection, if applicable.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @param value The name to set.
   */
  private void setName(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();

    name_ = value;
  }
  /**
   *
   *
   * <pre>
   * The resource that owns the locations collection, if applicable.
   * </pre>
   *
   * <code>string name = 1;</code>
   */
  private void clearName() {

    name_ = getDefaultInstance().getName();
  }
  /**
   *
   *
   * <pre>
   * The resource that owns the locations collection, if applicable.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @param value The bytes for name to set.
   */
  private void setNameBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    name_ = value.toStringUtf8();
  }

  public static final int FILTER_FIELD_NUMBER = 2;
  private java.lang.String filter_;
  /**
   *
   *
   * <pre>
   * The standard list filter.
   * </pre>
   *
   * <code>string filter = 2;</code>
   *
   * @return The filter.
   */
  @java.lang.Override
  public java.lang.String getFilter() {
    return filter_;
  }
  /**
   *
   *
   * <pre>
   * The standard list filter.
   * </pre>
   *
   * <code>string filter = 2;</code>
   *
   * @return The bytes for filter.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getFilterBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(filter_);
  }
  /**
   *
   *
   * <pre>
   * The standard list filter.
   * </pre>
   *
   * <code>string filter = 2;</code>
   *
   * @param value The filter to set.
   */
  private void setFilter(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();

    filter_ = value;
  }
  /**
   *
   *
   * <pre>
   * The standard list filter.
   * </pre>
   *
   * <code>string filter = 2;</code>
   */
  private void clearFilter() {

    filter_ = getDefaultInstance().getFilter();
  }
  /**
   *
   *
   * <pre>
   * The standard list filter.
   * </pre>
   *
   * <code>string filter = 2;</code>
   *
   * @param value The bytes for filter to set.
   */
  private void setFilterBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    filter_ = value.toStringUtf8();
  }

  public static final int PAGE_SIZE_FIELD_NUMBER = 3;
  private int pageSize_;
  /**
   *
   *
   * <pre>
   * The standard list page size.
   * </pre>
   *
   * <code>int32 page_size = 3;</code>
   *
   * @return The pageSize.
   */
  @java.lang.Override
  public int getPageSize() {
    return pageSize_;
  }
  /**
   *
   *
   * <pre>
   * The standard list page size.
   * </pre>
   *
   * <code>int32 page_size = 3;</code>
   *
   * @param value The pageSize to set.
   */
  private void setPageSize(int value) {

    pageSize_ = value;
  }
  /**
   *
   *
   * <pre>
   * The standard list page size.
   * </pre>
   *
   * <code>int32 page_size = 3;</code>
   */
  private void clearPageSize() {

    pageSize_ = 0;
  }

  public static final int PAGE_TOKEN_FIELD_NUMBER = 4;
  private java.lang.String pageToken_;
  /**
   *
   *
   * <pre>
   * The standard list page token.
   * </pre>
   *
   * <code>string page_token = 4;</code>
   *
   * @return The pageToken.
   */
  @java.lang.Override
  public java.lang.String getPageToken() {
    return pageToken_;
  }
  /**
   *
   *
   * <pre>
   * The standard list page token.
   * </pre>
   *
   * <code>string page_token = 4;</code>
   *
   * @return The bytes for pageToken.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getPageTokenBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(pageToken_);
  }
  /**
   *
   *
   * <pre>
   * The standard list page token.
   * </pre>
   *
   * <code>string page_token = 4;</code>
   *
   * @param value The pageToken to set.
   */
  private void setPageToken(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();

    pageToken_ = value;
  }
  /**
   *
   *
   * <pre>
   * The standard list page token.
   * </pre>
   *
   * <code>string page_token = 4;</code>
   */
  private void clearPageToken() {

    pageToken_ = getDefaultInstance().getPageToken();
  }
  /**
   *
   *
   * <pre>
   * The standard list page token.
   * </pre>
   *
   * <code>string page_token = 4;</code>
   *
   * @param value The bytes for pageToken to set.
   */
  private void setPageTokenBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    pageToken_ = value.toStringUtf8();
  }

  public static com.google.cloud.location.ListLocationsRequest parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.cloud.location.ListLocationsRequest parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.cloud.location.ListLocationsRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.cloud.location.ListLocationsRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.cloud.location.ListLocationsRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.cloud.location.ListLocationsRequest parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.cloud.location.ListLocationsRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.cloud.location.ListLocationsRequest parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.cloud.location.ListLocationsRequest parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.cloud.location.ListLocationsRequest parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.cloud.location.ListLocationsRequest parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.cloud.location.ListLocationsRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }

  public static Builder newBuilder(com.google.cloud.location.ListLocationsRequest prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   *
   *
   * <pre>
   * The request message for [Locations.ListLocations][google.cloud.location.Locations.ListLocations].
   * </pre>
   *
   * Protobuf type {@code google.cloud.location.ListLocationsRequest}
   */
  public static final class Builder
      extends com.google.protobuf.GeneratedMessageLite.Builder<
          com.google.cloud.location.ListLocationsRequest, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.cloud.location.ListLocationsRequest)
      com.google.cloud.location.ListLocationsRequestOrBuilder {
    // Construct using com.google.cloud.location.ListLocationsRequest.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }

    /**
     *
     *
     * <pre>
     * The resource that owns the locations collection, if applicable.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return The name.
     */
    @java.lang.Override
    public java.lang.String getName() {
      return instance.getName();
    }
    /**
     *
     *
     * <pre>
     * The resource that owns the locations collection, if applicable.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return The bytes for name.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getNameBytes() {
      return instance.getNameBytes();
    }
    /**
     *
     *
     * <pre>
     * The resource that owns the locations collection, if applicable.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @param value The name to set.
     * @return This builder for chaining.
     */
    public Builder setName(java.lang.String value) {
      copyOnWrite();
      instance.setName(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The resource that owns the locations collection, if applicable.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearName() {
      copyOnWrite();
      instance.clearName();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The resource that owns the locations collection, if applicable.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @param value The bytes for name to set.
     * @return This builder for chaining.
     */
    public Builder setNameBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setNameBytes(value);
      return this;
    }

    /**
     *
     *
     * <pre>
     * The standard list filter.
     * </pre>
     *
     * <code>string filter = 2;</code>
     *
     * @return The filter.
     */
    @java.lang.Override
    public java.lang.String getFilter() {
      return instance.getFilter();
    }
    /**
     *
     *
     * <pre>
     * The standard list filter.
     * </pre>
     *
     * <code>string filter = 2;</code>
     *
     * @return The bytes for filter.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getFilterBytes() {
      return instance.getFilterBytes();
    }
    /**
     *
     *
     * <pre>
     * The standard list filter.
     * </pre>
     *
     * <code>string filter = 2;</code>
     *
     * @param value The filter to set.
     * @return This builder for chaining.
     */
    public Builder setFilter(java.lang.String value) {
      copyOnWrite();
      instance.setFilter(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The standard list filter.
     * </pre>
     *
     * <code>string filter = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearFilter() {
      copyOnWrite();
      instance.clearFilter();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The standard list filter.
     * </pre>
     *
     * <code>string filter = 2;</code>
     *
     * @param value The bytes for filter to set.
     * @return This builder for chaining.
     */
    public Builder setFilterBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setFilterBytes(value);
      return this;
    }

    /**
     *
     *
     * <pre>
     * The standard list page size.
     * </pre>
     *
     * <code>int32 page_size = 3;</code>
     *
     * @return The pageSize.
     */
    @java.lang.Override
    public int getPageSize() {
      return instance.getPageSize();
    }
    /**
     *
     *
     * <pre>
     * The standard list page size.
     * </pre>
     *
     * <code>int32 page_size = 3;</code>
     *
     * @param value The pageSize to set.
     * @return This builder for chaining.
     */
    public Builder setPageSize(int value) {
      copyOnWrite();
      instance.setPageSize(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The standard list page size.
     * </pre>
     *
     * <code>int32 page_size = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearPageSize() {
      copyOnWrite();
      instance.clearPageSize();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The standard list page token.
     * </pre>
     *
     * <code>string page_token = 4;</code>
     *
     * @return The pageToken.
     */
    @java.lang.Override
    public java.lang.String getPageToken() {
      return instance.getPageToken();
    }
    /**
     *
     *
     * <pre>
     * The standard list page token.
     * </pre>
     *
     * <code>string page_token = 4;</code>
     *
     * @return The bytes for pageToken.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getPageTokenBytes() {
      return instance.getPageTokenBytes();
    }
    /**
     *
     *
     * <pre>
     * The standard list page token.
     * </pre>
     *
     * <code>string page_token = 4;</code>
     *
     * @param value The pageToken to set.
     * @return This builder for chaining.
     */
    public Builder setPageToken(java.lang.String value) {
      copyOnWrite();
      instance.setPageToken(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The standard list page token.
     * </pre>
     *
     * <code>string page_token = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearPageToken() {
      copyOnWrite();
      instance.clearPageToken();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The standard list page token.
     * </pre>
     *
     * <code>string page_token = 4;</code>
     *
     * @param value The bytes for pageToken to set.
     * @return This builder for chaining.
     */
    public Builder setPageTokenBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setPageTokenBytes(value);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.cloud.location.ListLocationsRequest)
  }

  @java.lang.Override
  @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
  protected final java.lang.Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      java.lang.Object arg0,
      java.lang.Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE:
        {
          return new com.google.cloud.location.ListLocationsRequest();
        }
      case NEW_BUILDER:
        {
          return new Builder();
        }
      case BUILD_MESSAGE_INFO:
        {
          java.lang.Object[] objects =
              new java.lang.Object[] {
                "name_", "filter_", "pageSize_", "pageToken_",
              };
          java.lang.String info =
              "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0208\u0002\u0208"
                  + "\u0003\u0004\u0004\u0208";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
      case GET_DEFAULT_INSTANCE:
        {
          return DEFAULT_INSTANCE;
        }
      case GET_PARSER:
        {
          com.google.protobuf.Parser<com.google.cloud.location.ListLocationsRequest> parser =
              PARSER;
          if (parser == null) {
            synchronized (com.google.cloud.location.ListLocationsRequest.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<com.google.cloud.location.ListLocationsRequest>(
                        DEFAULT_INSTANCE);
                PARSER = parser;
              }
            }
          }
          return parser;
        }
      case GET_MEMOIZED_IS_INITIALIZED:
        {
          return (byte) 1;
        }
      case SET_MEMOIZED_IS_INITIALIZED:
        {
          return null;
        }
    }
    throw new UnsupportedOperationException();
  }

  // @@protoc_insertion_point(class_scope:google.cloud.location.ListLocationsRequest)
  private static final com.google.cloud.location.ListLocationsRequest DEFAULT_INSTANCE;

  static {
    ListLocationsRequest defaultInstance = new ListLocationsRequest();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        ListLocationsRequest.class, defaultInstance);
  }

  public static com.google.cloud.location.ListLocationsRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<ListLocationsRequest> PARSER;

  public static com.google.protobuf.Parser<ListLocationsRequest> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
