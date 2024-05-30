/*
 * Copyright 2024 Google LLC
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

// Protobuf Java Version: 3.25.3
package com.google.api;

public interface HttpBodyOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.HttpBody)
    com.google.protobuf.MessageOrBuilder {

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
  java.lang.String getContentType();
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
  com.google.protobuf.ByteString getContentTypeBytes();

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
  com.google.protobuf.ByteString getData();

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
  java.util.List<com.google.protobuf.Any> getExtensionsList();
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
  com.google.protobuf.Any getExtensions(int index);
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
  int getExtensionsCount();
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
  java.util.List<? extends com.google.protobuf.AnyOrBuilder> getExtensionsOrBuilderList();
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
  com.google.protobuf.AnyOrBuilder getExtensionsOrBuilder(int index);
}
