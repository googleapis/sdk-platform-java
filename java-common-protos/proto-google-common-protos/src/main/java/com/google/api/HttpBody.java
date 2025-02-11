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
// source: google/api/httpbody.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

/**
 *
 *
 * <pre>
 * Message that represents an arbitrary HTTP body. It should only be used for
 * payload formats that can't be represented as JSON, such as raw binary or
 * an HTML page.
 *
 *
 * This message can be used both in streaming and non-streaming API methods in
 * the request as well as the response.
 *
 * It can be used as a top-level request field, which is convenient if one
 * wants to extract parameters from either the URL or HTTP template into the
 * request fields and also want access to the raw HTTP body.
 *
 * Example:
 *
 *     message GetResourceRequest {
 *       // A unique request id.
 *       string request_id = 1;
 *
 *       // The raw HTTP body is bound to this field.
 *       google.api.HttpBody http_body = 2;
 *
 *     }
 *
 *     service ResourceService {
 *       rpc GetResource(GetResourceRequest)
 *         returns (google.api.HttpBody);
 *       rpc UpdateResource(google.api.HttpBody)
 *         returns (google.protobuf.Empty);
 *
 *     }
 *
 * Example with streaming methods:
 *
 *     service CaldavService {
 *       rpc GetCalendar(stream google.api.HttpBody)
 *         returns (stream google.api.HttpBody);
 *       rpc UpdateCalendar(stream google.api.HttpBody)
 *         returns (stream google.api.HttpBody);
 *
 *     }
 *
 * Use of this type only changes how the request and response bodies are
 * handled, all other features will continue to work unchanged.
 * </pre>
 *
 * Protobuf type {@code google.api.HttpBody}
 */
public final class HttpBody
    extends com.google.protobuf.GeneratedMessageLite<HttpBody, HttpBody.Builder>
    implements
    // @@protoc_insertion_point(message_implements:google.api.HttpBody)
    HttpBodyOrBuilder {
  private HttpBody() {
    contentType_ = "";
    data_ = com.google.protobuf.ByteString.EMPTY;
    extensions_ = emptyProtobufList();
  }

  public static final int CONTENT_TYPE_FIELD_NUMBER = 1;
  private java.lang.String contentType_;
  /**
   *
   *
   * <pre>
   * The HTTP Content-Type header value specifying the content type of the body.
   * </pre>
   *
   * <code>string content_type = 1;</code>
   *
   * @return The contentType.
   */
  @java.lang.Override
  public java.lang.String getContentType() {
    return contentType_;
  }
  /**
   *
   *
   * <pre>
   * The HTTP Content-Type header value specifying the content type of the body.
   * </pre>
   *
   * <code>string content_type = 1;</code>
   *
   * @return The bytes for contentType.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getContentTypeBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(contentType_);
  }
  /**
   *
   *
   * <pre>
   * The HTTP Content-Type header value specifying the content type of the body.
   * </pre>
   *
   * <code>string content_type = 1;</code>
   *
   * @param value The contentType to set.
   */
  private void setContentType(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();

    contentType_ = value;
  }
  /**
   *
   *
   * <pre>
   * The HTTP Content-Type header value specifying the content type of the body.
   * </pre>
   *
   * <code>string content_type = 1;</code>
   */
  private void clearContentType() {

    contentType_ = getDefaultInstance().getContentType();
  }
  /**
   *
   *
   * <pre>
   * The HTTP Content-Type header value specifying the content type of the body.
   * </pre>
   *
   * <code>string content_type = 1;</code>
   *
   * @param value The bytes for contentType to set.
   */
  private void setContentTypeBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    contentType_ = value.toStringUtf8();
  }

  public static final int DATA_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString data_;
  /**
   *
   *
   * <pre>
   * The HTTP request/response body as raw binary.
   * </pre>
   *
   * <code>bytes data = 2;</code>
   *
   * @return The data.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getData() {
    return data_;
  }
  /**
   *
   *
   * <pre>
   * The HTTP request/response body as raw binary.
   * </pre>
   *
   * <code>bytes data = 2;</code>
   *
   * @param value The data to set.
   */
  private void setData(com.google.protobuf.ByteString value) {
    java.lang.Class<?> valueClass = value.getClass();

    data_ = value;
  }
  /**
   *
   *
   * <pre>
   * The HTTP request/response body as raw binary.
   * </pre>
   *
   * <code>bytes data = 2;</code>
   */
  private void clearData() {

    data_ = getDefaultInstance().getData();
  }

  public static final int EXTENSIONS_FIELD_NUMBER = 3;
  private com.google.protobuf.Internal.ProtobufList<com.google.protobuf.Any> extensions_;
  /**
   *
   *
   * <pre>
   * Application specific response metadata. Must be set in the first response
   * for streaming APIs.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 3;</code>
   */
  @java.lang.Override
  public java.util.List<com.google.protobuf.Any> getExtensionsList() {
    return extensions_;
  }
  /**
   *
   *
   * <pre>
   * Application specific response metadata. Must be set in the first response
   * for streaming APIs.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 3;</code>
   */
  public java.util.List<? extends com.google.protobuf.AnyOrBuilder> getExtensionsOrBuilderList() {
    return extensions_;
  }
  /**
   *
   *
   * <pre>
   * Application specific response metadata. Must be set in the first response
   * for streaming APIs.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 3;</code>
   */
  @java.lang.Override
  public int getExtensionsCount() {
    return extensions_.size();
  }
  /**
   *
   *
   * <pre>
   * Application specific response metadata. Must be set in the first response
   * for streaming APIs.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 3;</code>
   */
  @java.lang.Override
  public com.google.protobuf.Any getExtensions(int index) {
    return extensions_.get(index);
  }
  /**
   *
   *
   * <pre>
   * Application specific response metadata. Must be set in the first response
   * for streaming APIs.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 3;</code>
   */
  public com.google.protobuf.AnyOrBuilder getExtensionsOrBuilder(int index) {
    return extensions_.get(index);
  }

  private void ensureExtensionsIsMutable() {
    com.google.protobuf.Internal.ProtobufList<com.google.protobuf.Any> tmp = extensions_;
    if (!tmp.isModifiable()) {
      extensions_ = com.google.protobuf.GeneratedMessageLite.mutableCopy(tmp);
    }
  }

  /**
   *
   *
   * <pre>
   * Application specific response metadata. Must be set in the first response
   * for streaming APIs.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 3;</code>
   */
  private void setExtensions(int index, com.google.protobuf.Any value) {
    value.getClass();
    ensureExtensionsIsMutable();
    extensions_.set(index, value);
  }
  /**
   *
   *
   * <pre>
   * Application specific response metadata. Must be set in the first response
   * for streaming APIs.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 3;</code>
   */
  private void addExtensions(com.google.protobuf.Any value) {
    value.getClass();
    ensureExtensionsIsMutable();
    extensions_.add(value);
  }
  /**
   *
   *
   * <pre>
   * Application specific response metadata. Must be set in the first response
   * for streaming APIs.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 3;</code>
   */
  private void addExtensions(int index, com.google.protobuf.Any value) {
    value.getClass();
    ensureExtensionsIsMutable();
    extensions_.add(index, value);
  }
  /**
   *
   *
   * <pre>
   * Application specific response metadata. Must be set in the first response
   * for streaming APIs.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 3;</code>
   */
  private void addAllExtensions(java.lang.Iterable<? extends com.google.protobuf.Any> values) {
    ensureExtensionsIsMutable();
    com.google.protobuf.AbstractMessageLite.addAll(values, extensions_);
  }
  /**
   *
   *
   * <pre>
   * Application specific response metadata. Must be set in the first response
   * for streaming APIs.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 3;</code>
   */
  private void clearExtensions() {
    extensions_ = emptyProtobufList();
  }
  /**
   *
   *
   * <pre>
   * Application specific response metadata. Must be set in the first response
   * for streaming APIs.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 3;</code>
   */
  private void removeExtensions(int index) {
    ensureExtensionsIsMutable();
    extensions_.remove(index);
  }

  public static com.google.api.HttpBody parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.HttpBody parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.HttpBody parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.HttpBody parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.HttpBody parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.HttpBody parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.HttpBody parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.HttpBody parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.HttpBody parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.HttpBody parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.HttpBody parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.HttpBody parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }

  public static Builder newBuilder(com.google.api.HttpBody prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   *
   *
   * <pre>
   * Message that represents an arbitrary HTTP body. It should only be used for
   * payload formats that can't be represented as JSON, such as raw binary or
   * an HTML page.
   *
   *
   * This message can be used both in streaming and non-streaming API methods in
   * the request as well as the response.
   *
   * It can be used as a top-level request field, which is convenient if one
   * wants to extract parameters from either the URL or HTTP template into the
   * request fields and also want access to the raw HTTP body.
   *
   * Example:
   *
   *     message GetResourceRequest {
   *       // A unique request id.
   *       string request_id = 1;
   *
   *       // The raw HTTP body is bound to this field.
   *       google.api.HttpBody http_body = 2;
   *
   *     }
   *
   *     service ResourceService {
   *       rpc GetResource(GetResourceRequest)
   *         returns (google.api.HttpBody);
   *       rpc UpdateResource(google.api.HttpBody)
   *         returns (google.protobuf.Empty);
   *
   *     }
   *
   * Example with streaming methods:
   *
   *     service CaldavService {
   *       rpc GetCalendar(stream google.api.HttpBody)
   *         returns (stream google.api.HttpBody);
   *       rpc UpdateCalendar(stream google.api.HttpBody)
   *         returns (stream google.api.HttpBody);
   *
   *     }
   *
   * Use of this type only changes how the request and response bodies are
   * handled, all other features will continue to work unchanged.
   * </pre>
   *
   * Protobuf type {@code google.api.HttpBody}
   */
  public static final class Builder
      extends com.google.protobuf.GeneratedMessageLite.Builder<com.google.api.HttpBody, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.HttpBody)
      com.google.api.HttpBodyOrBuilder {
    // Construct using com.google.api.HttpBody.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }

    /**
     *
     *
     * <pre>
     * The HTTP Content-Type header value specifying the content type of the body.
     * </pre>
     *
     * <code>string content_type = 1;</code>
     *
     * @return The contentType.
     */
    @java.lang.Override
    public java.lang.String getContentType() {
      return instance.getContentType();
    }
    /**
     *
     *
     * <pre>
     * The HTTP Content-Type header value specifying the content type of the body.
     * </pre>
     *
     * <code>string content_type = 1;</code>
     *
     * @return The bytes for contentType.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getContentTypeBytes() {
      return instance.getContentTypeBytes();
    }
    /**
     *
     *
     * <pre>
     * The HTTP Content-Type header value specifying the content type of the body.
     * </pre>
     *
     * <code>string content_type = 1;</code>
     *
     * @param value The contentType to set.
     * @return This builder for chaining.
     */
    public Builder setContentType(java.lang.String value) {
      copyOnWrite();
      instance.setContentType(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The HTTP Content-Type header value specifying the content type of the body.
     * </pre>
     *
     * <code>string content_type = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearContentType() {
      copyOnWrite();
      instance.clearContentType();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The HTTP Content-Type header value specifying the content type of the body.
     * </pre>
     *
     * <code>string content_type = 1;</code>
     *
     * @param value The bytes for contentType to set.
     * @return This builder for chaining.
     */
    public Builder setContentTypeBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setContentTypeBytes(value);
      return this;
    }

    /**
     *
     *
     * <pre>
     * The HTTP request/response body as raw binary.
     * </pre>
     *
     * <code>bytes data = 2;</code>
     *
     * @return The data.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getData() {
      return instance.getData();
    }
    /**
     *
     *
     * <pre>
     * The HTTP request/response body as raw binary.
     * </pre>
     *
     * <code>bytes data = 2;</code>
     *
     * @param value The data to set.
     * @return This builder for chaining.
     */
    public Builder setData(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setData(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The HTTP request/response body as raw binary.
     * </pre>
     *
     * <code>bytes data = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearData() {
      copyOnWrite();
      instance.clearData();
      return this;
    }

    /**
     *
     *
     * <pre>
     * Application specific response metadata. Must be set in the first response
     * for streaming APIs.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any extensions = 3;</code>
     */
    @java.lang.Override
    public java.util.List<com.google.protobuf.Any> getExtensionsList() {
      return java.util.Collections.unmodifiableList(instance.getExtensionsList());
    }
    /**
     *
     *
     * <pre>
     * Application specific response metadata. Must be set in the first response
     * for streaming APIs.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any extensions = 3;</code>
     */
    @java.lang.Override
    public int getExtensionsCount() {
      return instance.getExtensionsCount();
    }
    /**
     *
     *
     * <pre>
     * Application specific response metadata. Must be set in the first response
     * for streaming APIs.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any extensions = 3;</code>
     */

    @java.lang.Override
    public com.google.protobuf.Any getExtensions(int index) {
      return instance.getExtensions(index);
    }
    /**
     *
     *
     * <pre>
     * Application specific response metadata. Must be set in the first response
     * for streaming APIs.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any extensions = 3;</code>
     */
    public Builder setExtensions(int index, com.google.protobuf.Any value) {
      copyOnWrite();
      instance.setExtensions(index, value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Application specific response metadata. Must be set in the first response
     * for streaming APIs.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any extensions = 3;</code>
     */
    public Builder setExtensions(int index, com.google.protobuf.Any.Builder builderForValue) {
      copyOnWrite();
      instance.setExtensions(index, builderForValue.build());
      return this;
    }
    /**
     *
     *
     * <pre>
     * Application specific response metadata. Must be set in the first response
     * for streaming APIs.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any extensions = 3;</code>
     */
    public Builder addExtensions(com.google.protobuf.Any value) {
      copyOnWrite();
      instance.addExtensions(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Application specific response metadata. Must be set in the first response
     * for streaming APIs.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any extensions = 3;</code>
     */
    public Builder addExtensions(int index, com.google.protobuf.Any value) {
      copyOnWrite();
      instance.addExtensions(index, value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Application specific response metadata. Must be set in the first response
     * for streaming APIs.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any extensions = 3;</code>
     */
    public Builder addExtensions(com.google.protobuf.Any.Builder builderForValue) {
      copyOnWrite();
      instance.addExtensions(builderForValue.build());
      return this;
    }
    /**
     *
     *
     * <pre>
     * Application specific response metadata. Must be set in the first response
     * for streaming APIs.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any extensions = 3;</code>
     */
    public Builder addExtensions(int index, com.google.protobuf.Any.Builder builderForValue) {
      copyOnWrite();
      instance.addExtensions(index, builderForValue.build());
      return this;
    }
    /**
     *
     *
     * <pre>
     * Application specific response metadata. Must be set in the first response
     * for streaming APIs.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any extensions = 3;</code>
     */
    public Builder addAllExtensions(java.lang.Iterable<? extends com.google.protobuf.Any> values) {
      copyOnWrite();
      instance.addAllExtensions(values);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Application specific response metadata. Must be set in the first response
     * for streaming APIs.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any extensions = 3;</code>
     */
    public Builder clearExtensions() {
      copyOnWrite();
      instance.clearExtensions();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Application specific response metadata. Must be set in the first response
     * for streaming APIs.
     * </pre>
     *
     * <code>repeated .google.protobuf.Any extensions = 3;</code>
     */
    public Builder removeExtensions(int index) {
      copyOnWrite();
      instance.removeExtensions(index);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.api.HttpBody)
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
          return new com.google.api.HttpBody();
        }
      case NEW_BUILDER:
        {
          return new Builder();
        }
      case BUILD_MESSAGE_INFO:
        {
          java.lang.Object[] objects =
              new java.lang.Object[] {
                "contentType_", "data_", "extensions_", com.google.protobuf.Any.class,
              };
          java.lang.String info =
              "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0001\u0000\u0001\u0208\u0002\n"
                  + "\u0003\u001b";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
      case GET_DEFAULT_INSTANCE:
        {
          return DEFAULT_INSTANCE;
        }
      case GET_PARSER:
        {
          com.google.protobuf.Parser<com.google.api.HttpBody> parser = PARSER;
          if (parser == null) {
            synchronized (com.google.api.HttpBody.class) {
              parser = PARSER;
              if (parser == null) {
                parser = new DefaultInstanceBasedParser<com.google.api.HttpBody>(DEFAULT_INSTANCE);
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

  // @@protoc_insertion_point(class_scope:google.api.HttpBody)
  private static final com.google.api.HttpBody DEFAULT_INSTANCE;

  static {
    HttpBody defaultInstance = new HttpBody();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        HttpBody.class, defaultInstance);
  }

  public static com.google.api.HttpBody getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<HttpBody> PARSER;

  public static com.google.protobuf.Parser<HttpBody> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
