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
// source: google/api/auth.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

/**
 *
 *
 * <pre>
 * Specifies a location to extract JWT from an API request.
 * </pre>
 *
 * Protobuf type {@code google.api.JwtLocation}
 */
public final class JwtLocation
    extends com.google.protobuf.GeneratedMessageLite<JwtLocation, JwtLocation.Builder>
    implements
    // @@protoc_insertion_point(message_implements:google.api.JwtLocation)
    JwtLocationOrBuilder {
  private JwtLocation() {
    valuePrefix_ = "";
  }

  private int inCase_ = 0;
  private java.lang.Object in_;

  public enum InCase {
    HEADER(1),
    QUERY(2),
    COOKIE(4),
    IN_NOT_SET(0);
    private final int value;

    private InCase(int value) {
      this.value = value;
    }
    /** @deprecated Use {@link #forNumber(int)} instead. */
    @java.lang.Deprecated
    public static InCase valueOf(int value) {
      return forNumber(value);
    }

    public static InCase forNumber(int value) {
      switch (value) {
        case 1:
          return HEADER;
        case 2:
          return QUERY;
        case 4:
          return COOKIE;
        case 0:
          return IN_NOT_SET;
        default:
          return null;
      }
    }

    public int getNumber() {
      return this.value;
    }
  };

  @java.lang.Override
  public InCase getInCase() {
    return InCase.forNumber(inCase_);
  }

  private void clearIn() {
    inCase_ = 0;
    in_ = null;
  }

  public static final int HEADER_FIELD_NUMBER = 1;
  /**
   *
   *
   * <pre>
   * Specifies HTTP header name to extract JWT token.
   * </pre>
   *
   * <code>string header = 1;</code>
   *
   * @return Whether the header field is set.
   */
  @java.lang.Override
  public boolean hasHeader() {
    return inCase_ == 1;
  }
  /**
   *
   *
   * <pre>
   * Specifies HTTP header name to extract JWT token.
   * </pre>
   *
   * <code>string header = 1;</code>
   *
   * @return The header.
   */
  @java.lang.Override
  public java.lang.String getHeader() {
    java.lang.String ref = "";
    if (inCase_ == 1) {
      ref = (java.lang.String) in_;
    }
    return ref;
  }
  /**
   *
   *
   * <pre>
   * Specifies HTTP header name to extract JWT token.
   * </pre>
   *
   * <code>string header = 1;</code>
   *
   * @return The bytes for header.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getHeaderBytes() {
    java.lang.String ref = "";
    if (inCase_ == 1) {
      ref = (java.lang.String) in_;
    }
    return com.google.protobuf.ByteString.copyFromUtf8(ref);
  }
  /**
   *
   *
   * <pre>
   * Specifies HTTP header name to extract JWT token.
   * </pre>
   *
   * <code>string header = 1;</code>
   *
   * @param value The header to set.
   */
  private void setHeader(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();
    inCase_ = 1;
    in_ = value;
  }
  /**
   *
   *
   * <pre>
   * Specifies HTTP header name to extract JWT token.
   * </pre>
   *
   * <code>string header = 1;</code>
   */
  private void clearHeader() {
    if (inCase_ == 1) {
      inCase_ = 0;
      in_ = null;
    }
  }
  /**
   *
   *
   * <pre>
   * Specifies HTTP header name to extract JWT token.
   * </pre>
   *
   * <code>string header = 1;</code>
   *
   * @param value The bytes for header to set.
   */
  private void setHeaderBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    in_ = value.toStringUtf8();
    inCase_ = 1;
  }

  public static final int QUERY_FIELD_NUMBER = 2;
  /**
   *
   *
   * <pre>
   * Specifies URL query parameter name to extract JWT token.
   * </pre>
   *
   * <code>string query = 2;</code>
   *
   * @return Whether the query field is set.
   */
  @java.lang.Override
  public boolean hasQuery() {
    return inCase_ == 2;
  }
  /**
   *
   *
   * <pre>
   * Specifies URL query parameter name to extract JWT token.
   * </pre>
   *
   * <code>string query = 2;</code>
   *
   * @return The query.
   */
  @java.lang.Override
  public java.lang.String getQuery() {
    java.lang.String ref = "";
    if (inCase_ == 2) {
      ref = (java.lang.String) in_;
    }
    return ref;
  }
  /**
   *
   *
   * <pre>
   * Specifies URL query parameter name to extract JWT token.
   * </pre>
   *
   * <code>string query = 2;</code>
   *
   * @return The bytes for query.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getQueryBytes() {
    java.lang.String ref = "";
    if (inCase_ == 2) {
      ref = (java.lang.String) in_;
    }
    return com.google.protobuf.ByteString.copyFromUtf8(ref);
  }
  /**
   *
   *
   * <pre>
   * Specifies URL query parameter name to extract JWT token.
   * </pre>
   *
   * <code>string query = 2;</code>
   *
   * @param value The query to set.
   */
  private void setQuery(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();
    inCase_ = 2;
    in_ = value;
  }
  /**
   *
   *
   * <pre>
   * Specifies URL query parameter name to extract JWT token.
   * </pre>
   *
   * <code>string query = 2;</code>
   */
  private void clearQuery() {
    if (inCase_ == 2) {
      inCase_ = 0;
      in_ = null;
    }
  }
  /**
   *
   *
   * <pre>
   * Specifies URL query parameter name to extract JWT token.
   * </pre>
   *
   * <code>string query = 2;</code>
   *
   * @param value The bytes for query to set.
   */
  private void setQueryBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    in_ = value.toStringUtf8();
    inCase_ = 2;
  }

  public static final int COOKIE_FIELD_NUMBER = 4;
  /**
   *
   *
   * <pre>
   * Specifies cookie name to extract JWT token.
   * </pre>
   *
   * <code>string cookie = 4;</code>
   *
   * @return Whether the cookie field is set.
   */
  @java.lang.Override
  public boolean hasCookie() {
    return inCase_ == 4;
  }
  /**
   *
   *
   * <pre>
   * Specifies cookie name to extract JWT token.
   * </pre>
   *
   * <code>string cookie = 4;</code>
   *
   * @return The cookie.
   */
  @java.lang.Override
  public java.lang.String getCookie() {
    java.lang.String ref = "";
    if (inCase_ == 4) {
      ref = (java.lang.String) in_;
    }
    return ref;
  }
  /**
   *
   *
   * <pre>
   * Specifies cookie name to extract JWT token.
   * </pre>
   *
   * <code>string cookie = 4;</code>
   *
   * @return The bytes for cookie.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getCookieBytes() {
    java.lang.String ref = "";
    if (inCase_ == 4) {
      ref = (java.lang.String) in_;
    }
    return com.google.protobuf.ByteString.copyFromUtf8(ref);
  }
  /**
   *
   *
   * <pre>
   * Specifies cookie name to extract JWT token.
   * </pre>
   *
   * <code>string cookie = 4;</code>
   *
   * @param value The cookie to set.
   */
  private void setCookie(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();
    inCase_ = 4;
    in_ = value;
  }
  /**
   *
   *
   * <pre>
   * Specifies cookie name to extract JWT token.
   * </pre>
   *
   * <code>string cookie = 4;</code>
   */
  private void clearCookie() {
    if (inCase_ == 4) {
      inCase_ = 0;
      in_ = null;
    }
  }
  /**
   *
   *
   * <pre>
   * Specifies cookie name to extract JWT token.
   * </pre>
   *
   * <code>string cookie = 4;</code>
   *
   * @param value The bytes for cookie to set.
   */
  private void setCookieBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    in_ = value.toStringUtf8();
    inCase_ = 4;
  }

  public static final int VALUE_PREFIX_FIELD_NUMBER = 3;
  private java.lang.String valuePrefix_;
  /**
   *
   *
   * <pre>
   * The value prefix. The value format is "value_prefix{token}"
   * Only applies to "in" header type. Must be empty for "in" query type.
   * If not empty, the header value has to match (case sensitive) this prefix.
   * If not matched, JWT will not be extracted. If matched, JWT will be
   * extracted after the prefix is removed.
   *
   * For example, for "Authorization: Bearer {JWT}",
   * value_prefix="Bearer " with a space at the end.
   * </pre>
   *
   * <code>string value_prefix = 3;</code>
   *
   * @return The valuePrefix.
   */
  @java.lang.Override
  public java.lang.String getValuePrefix() {
    return valuePrefix_;
  }
  /**
   *
   *
   * <pre>
   * The value prefix. The value format is "value_prefix{token}"
   * Only applies to "in" header type. Must be empty for "in" query type.
   * If not empty, the header value has to match (case sensitive) this prefix.
   * If not matched, JWT will not be extracted. If matched, JWT will be
   * extracted after the prefix is removed.
   *
   * For example, for "Authorization: Bearer {JWT}",
   * value_prefix="Bearer " with a space at the end.
   * </pre>
   *
   * <code>string value_prefix = 3;</code>
   *
   * @return The bytes for valuePrefix.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getValuePrefixBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(valuePrefix_);
  }
  /**
   *
   *
   * <pre>
   * The value prefix. The value format is "value_prefix{token}"
   * Only applies to "in" header type. Must be empty for "in" query type.
   * If not empty, the header value has to match (case sensitive) this prefix.
   * If not matched, JWT will not be extracted. If matched, JWT will be
   * extracted after the prefix is removed.
   *
   * For example, for "Authorization: Bearer {JWT}",
   * value_prefix="Bearer " with a space at the end.
   * </pre>
   *
   * <code>string value_prefix = 3;</code>
   *
   * @param value The valuePrefix to set.
   */
  private void setValuePrefix(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();

    valuePrefix_ = value;
  }
  /**
   *
   *
   * <pre>
   * The value prefix. The value format is "value_prefix{token}"
   * Only applies to "in" header type. Must be empty for "in" query type.
   * If not empty, the header value has to match (case sensitive) this prefix.
   * If not matched, JWT will not be extracted. If matched, JWT will be
   * extracted after the prefix is removed.
   *
   * For example, for "Authorization: Bearer {JWT}",
   * value_prefix="Bearer " with a space at the end.
   * </pre>
   *
   * <code>string value_prefix = 3;</code>
   */
  private void clearValuePrefix() {

    valuePrefix_ = getDefaultInstance().getValuePrefix();
  }
  /**
   *
   *
   * <pre>
   * The value prefix. The value format is "value_prefix{token}"
   * Only applies to "in" header type. Must be empty for "in" query type.
   * If not empty, the header value has to match (case sensitive) this prefix.
   * If not matched, JWT will not be extracted. If matched, JWT will be
   * extracted after the prefix is removed.
   *
   * For example, for "Authorization: Bearer {JWT}",
   * value_prefix="Bearer " with a space at the end.
   * </pre>
   *
   * <code>string value_prefix = 3;</code>
   *
   * @param value The bytes for valuePrefix to set.
   */
  private void setValuePrefixBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    valuePrefix_ = value.toStringUtf8();
  }

  public static com.google.api.JwtLocation parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.JwtLocation parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.JwtLocation parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.JwtLocation parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.JwtLocation parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.JwtLocation parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.JwtLocation parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.JwtLocation parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.JwtLocation parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.JwtLocation parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.JwtLocation parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.JwtLocation parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }

  public static Builder newBuilder(com.google.api.JwtLocation prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   *
   *
   * <pre>
   * Specifies a location to extract JWT from an API request.
   * </pre>
   *
   * Protobuf type {@code google.api.JwtLocation}
   */
  public static final class Builder
      extends com.google.protobuf.GeneratedMessageLite.Builder<com.google.api.JwtLocation, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.JwtLocation)
      com.google.api.JwtLocationOrBuilder {
    // Construct using com.google.api.JwtLocation.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }

    @java.lang.Override
    public InCase getInCase() {
      return instance.getInCase();
    }

    public Builder clearIn() {
      copyOnWrite();
      instance.clearIn();
      return this;
    }

    /**
     *
     *
     * <pre>
     * Specifies HTTP header name to extract JWT token.
     * </pre>
     *
     * <code>string header = 1;</code>
     *
     * @return Whether the header field is set.
     */
    @java.lang.Override
    public boolean hasHeader() {
      return instance.hasHeader();
    }
    /**
     *
     *
     * <pre>
     * Specifies HTTP header name to extract JWT token.
     * </pre>
     *
     * <code>string header = 1;</code>
     *
     * @return The header.
     */
    @java.lang.Override
    public java.lang.String getHeader() {
      return instance.getHeader();
    }
    /**
     *
     *
     * <pre>
     * Specifies HTTP header name to extract JWT token.
     * </pre>
     *
     * <code>string header = 1;</code>
     *
     * @return The bytes for header.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getHeaderBytes() {
      return instance.getHeaderBytes();
    }
    /**
     *
     *
     * <pre>
     * Specifies HTTP header name to extract JWT token.
     * </pre>
     *
     * <code>string header = 1;</code>
     *
     * @param value The header to set.
     * @return This builder for chaining.
     */
    public Builder setHeader(java.lang.String value) {
      copyOnWrite();
      instance.setHeader(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Specifies HTTP header name to extract JWT token.
     * </pre>
     *
     * <code>string header = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearHeader() {
      copyOnWrite();
      instance.clearHeader();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Specifies HTTP header name to extract JWT token.
     * </pre>
     *
     * <code>string header = 1;</code>
     *
     * @param value The bytes for header to set.
     * @return This builder for chaining.
     */
    public Builder setHeaderBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setHeaderBytes(value);
      return this;
    }

    /**
     *
     *
     * <pre>
     * Specifies URL query parameter name to extract JWT token.
     * </pre>
     *
     * <code>string query = 2;</code>
     *
     * @return Whether the query field is set.
     */
    @java.lang.Override
    public boolean hasQuery() {
      return instance.hasQuery();
    }
    /**
     *
     *
     * <pre>
     * Specifies URL query parameter name to extract JWT token.
     * </pre>
     *
     * <code>string query = 2;</code>
     *
     * @return The query.
     */
    @java.lang.Override
    public java.lang.String getQuery() {
      return instance.getQuery();
    }
    /**
     *
     *
     * <pre>
     * Specifies URL query parameter name to extract JWT token.
     * </pre>
     *
     * <code>string query = 2;</code>
     *
     * @return The bytes for query.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getQueryBytes() {
      return instance.getQueryBytes();
    }
    /**
     *
     *
     * <pre>
     * Specifies URL query parameter name to extract JWT token.
     * </pre>
     *
     * <code>string query = 2;</code>
     *
     * @param value The query to set.
     * @return This builder for chaining.
     */
    public Builder setQuery(java.lang.String value) {
      copyOnWrite();
      instance.setQuery(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Specifies URL query parameter name to extract JWT token.
     * </pre>
     *
     * <code>string query = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearQuery() {
      copyOnWrite();
      instance.clearQuery();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Specifies URL query parameter name to extract JWT token.
     * </pre>
     *
     * <code>string query = 2;</code>
     *
     * @param value The bytes for query to set.
     * @return This builder for chaining.
     */
    public Builder setQueryBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setQueryBytes(value);
      return this;
    }

    /**
     *
     *
     * <pre>
     * Specifies cookie name to extract JWT token.
     * </pre>
     *
     * <code>string cookie = 4;</code>
     *
     * @return Whether the cookie field is set.
     */
    @java.lang.Override
    public boolean hasCookie() {
      return instance.hasCookie();
    }
    /**
     *
     *
     * <pre>
     * Specifies cookie name to extract JWT token.
     * </pre>
     *
     * <code>string cookie = 4;</code>
     *
     * @return The cookie.
     */
    @java.lang.Override
    public java.lang.String getCookie() {
      return instance.getCookie();
    }
    /**
     *
     *
     * <pre>
     * Specifies cookie name to extract JWT token.
     * </pre>
     *
     * <code>string cookie = 4;</code>
     *
     * @return The bytes for cookie.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getCookieBytes() {
      return instance.getCookieBytes();
    }
    /**
     *
     *
     * <pre>
     * Specifies cookie name to extract JWT token.
     * </pre>
     *
     * <code>string cookie = 4;</code>
     *
     * @param value The cookie to set.
     * @return This builder for chaining.
     */
    public Builder setCookie(java.lang.String value) {
      copyOnWrite();
      instance.setCookie(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Specifies cookie name to extract JWT token.
     * </pre>
     *
     * <code>string cookie = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearCookie() {
      copyOnWrite();
      instance.clearCookie();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Specifies cookie name to extract JWT token.
     * </pre>
     *
     * <code>string cookie = 4;</code>
     *
     * @param value The bytes for cookie to set.
     * @return This builder for chaining.
     */
    public Builder setCookieBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setCookieBytes(value);
      return this;
    }

    /**
     *
     *
     * <pre>
     * The value prefix. The value format is "value_prefix{token}"
     * Only applies to "in" header type. Must be empty for "in" query type.
     * If not empty, the header value has to match (case sensitive) this prefix.
     * If not matched, JWT will not be extracted. If matched, JWT will be
     * extracted after the prefix is removed.
     *
     * For example, for "Authorization: Bearer {JWT}",
     * value_prefix="Bearer " with a space at the end.
     * </pre>
     *
     * <code>string value_prefix = 3;</code>
     *
     * @return The valuePrefix.
     */
    @java.lang.Override
    public java.lang.String getValuePrefix() {
      return instance.getValuePrefix();
    }
    /**
     *
     *
     * <pre>
     * The value prefix. The value format is "value_prefix{token}"
     * Only applies to "in" header type. Must be empty for "in" query type.
     * If not empty, the header value has to match (case sensitive) this prefix.
     * If not matched, JWT will not be extracted. If matched, JWT will be
     * extracted after the prefix is removed.
     *
     * For example, for "Authorization: Bearer {JWT}",
     * value_prefix="Bearer " with a space at the end.
     * </pre>
     *
     * <code>string value_prefix = 3;</code>
     *
     * @return The bytes for valuePrefix.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getValuePrefixBytes() {
      return instance.getValuePrefixBytes();
    }
    /**
     *
     *
     * <pre>
     * The value prefix. The value format is "value_prefix{token}"
     * Only applies to "in" header type. Must be empty for "in" query type.
     * If not empty, the header value has to match (case sensitive) this prefix.
     * If not matched, JWT will not be extracted. If matched, JWT will be
     * extracted after the prefix is removed.
     *
     * For example, for "Authorization: Bearer {JWT}",
     * value_prefix="Bearer " with a space at the end.
     * </pre>
     *
     * <code>string value_prefix = 3;</code>
     *
     * @param value The valuePrefix to set.
     * @return This builder for chaining.
     */
    public Builder setValuePrefix(java.lang.String value) {
      copyOnWrite();
      instance.setValuePrefix(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The value prefix. The value format is "value_prefix{token}"
     * Only applies to "in" header type. Must be empty for "in" query type.
     * If not empty, the header value has to match (case sensitive) this prefix.
     * If not matched, JWT will not be extracted. If matched, JWT will be
     * extracted after the prefix is removed.
     *
     * For example, for "Authorization: Bearer {JWT}",
     * value_prefix="Bearer " with a space at the end.
     * </pre>
     *
     * <code>string value_prefix = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearValuePrefix() {
      copyOnWrite();
      instance.clearValuePrefix();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The value prefix. The value format is "value_prefix{token}"
     * Only applies to "in" header type. Must be empty for "in" query type.
     * If not empty, the header value has to match (case sensitive) this prefix.
     * If not matched, JWT will not be extracted. If matched, JWT will be
     * extracted after the prefix is removed.
     *
     * For example, for "Authorization: Bearer {JWT}",
     * value_prefix="Bearer " with a space at the end.
     * </pre>
     *
     * <code>string value_prefix = 3;</code>
     *
     * @param value The bytes for valuePrefix to set.
     * @return This builder for chaining.
     */
    public Builder setValuePrefixBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setValuePrefixBytes(value);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.api.JwtLocation)
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
          return new com.google.api.JwtLocation();
        }
      case NEW_BUILDER:
        {
          return new Builder();
        }
      case BUILD_MESSAGE_INFO:
        {
          java.lang.Object[] objects =
              new java.lang.Object[] {
                "in_", "inCase_", "valuePrefix_",
              };
          java.lang.String info =
              "\u0000\u0004\u0001\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u023b\u0000\u0002"
                  + "\u023b\u0000\u0003\u0208\u0004\u023b\u0000";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
      case GET_DEFAULT_INSTANCE:
        {
          return DEFAULT_INSTANCE;
        }
      case GET_PARSER:
        {
          com.google.protobuf.Parser<com.google.api.JwtLocation> parser = PARSER;
          if (parser == null) {
            synchronized (com.google.api.JwtLocation.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<com.google.api.JwtLocation>(DEFAULT_INSTANCE);
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

  // @@protoc_insertion_point(class_scope:google.api.JwtLocation)
  private static final com.google.api.JwtLocation DEFAULT_INSTANCE;

  static {
    JwtLocation defaultInstance = new JwtLocation();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        JwtLocation.class, defaultInstance);
  }

  public static com.google.api.JwtLocation getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<JwtLocation> PARSER;

  public static com.google.protobuf.Parser<JwtLocation> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
