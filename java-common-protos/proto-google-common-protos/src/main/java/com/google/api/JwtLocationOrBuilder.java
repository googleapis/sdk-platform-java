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

public interface JwtLocationOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.JwtLocation)
    com.google.protobuf.MessageOrBuilder {

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
  boolean hasHeader();
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
  java.lang.String getHeader();
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
  com.google.protobuf.ByteString getHeaderBytes();

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
  boolean hasQuery();
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
  java.lang.String getQuery();
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
  com.google.protobuf.ByteString getQueryBytes();

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
  boolean hasCookie();
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
  java.lang.String getCookie();
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
  com.google.protobuf.ByteString getCookieBytes();

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
  java.lang.String getValuePrefix();
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
  com.google.protobuf.ByteString getValuePrefixBytes();

  com.google.api.JwtLocation.InCase getInCase();
}
