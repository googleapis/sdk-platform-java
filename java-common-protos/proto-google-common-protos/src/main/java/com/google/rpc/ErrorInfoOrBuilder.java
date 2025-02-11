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
// source: google/rpc/error_details.proto

// Protobuf Java Version: 3.25.5
package com.google.rpc;

public interface ErrorInfoOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.rpc.ErrorInfo)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   *
   *
   * <pre>
   * The reason of the error. This is a constant value that identifies the
   * proximate cause of the error. Error reasons are unique within a particular
   * domain of errors. This should be at most 63 characters and match a
   * regular expression of `[A-Z][A-Z0-9_]+[A-Z0-9]`, which represents
   * UPPER_SNAKE_CASE.
   * </pre>
   *
   * <code>string reason = 1;</code>
   *
   * @return The reason.
   */
  java.lang.String getReason();
  /**
   *
   *
   * <pre>
   * The reason of the error. This is a constant value that identifies the
   * proximate cause of the error. Error reasons are unique within a particular
   * domain of errors. This should be at most 63 characters and match a
   * regular expression of `[A-Z][A-Z0-9_]+[A-Z0-9]`, which represents
   * UPPER_SNAKE_CASE.
   * </pre>
   *
   * <code>string reason = 1;</code>
   *
   * @return The bytes for reason.
   */
  com.google.protobuf.ByteString getReasonBytes();

  /**
   *
   *
   * <pre>
   * The logical grouping to which the "reason" belongs. The error domain
   * is typically the registered service name of the tool or product that
   * generates the error. Example: "pubsub.googleapis.com". If the error is
   * generated by some common infrastructure, the error domain must be a
   * globally unique value that identifies the infrastructure. For Google API
   * infrastructure, the error domain is "googleapis.com".
   * </pre>
   *
   * <code>string domain = 2;</code>
   *
   * @return The domain.
   */
  java.lang.String getDomain();
  /**
   *
   *
   * <pre>
   * The logical grouping to which the "reason" belongs. The error domain
   * is typically the registered service name of the tool or product that
   * generates the error. Example: "pubsub.googleapis.com". If the error is
   * generated by some common infrastructure, the error domain must be a
   * globally unique value that identifies the infrastructure. For Google API
   * infrastructure, the error domain is "googleapis.com".
   * </pre>
   *
   * <code>string domain = 2;</code>
   *
   * @return The bytes for domain.
   */
  com.google.protobuf.ByteString getDomainBytes();

  /**
   *
   *
   * <pre>
   * Additional structured details about this error.
   *
   * Keys must match a regular expression of `[a-z][a-zA-Z0-9-_]+` but should
   * ideally be lowerCamelCase. Also, they must be limited to 64 characters in
   * length. When identifying the current value of an exceeded limit, the units
   * should be contained in the key, not the value.  For example, rather than
   * `{"instanceLimit": "100/request"}`, should be returned as,
   * `{"instanceLimitPerRequest": "100"}`, if the client exceeds the number of
   * instances that can be created in a single (batch) request.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 3;</code>
   */
  int getMetadataCount();
  /**
   *
   *
   * <pre>
   * Additional structured details about this error.
   *
   * Keys must match a regular expression of `[a-z][a-zA-Z0-9-_]+` but should
   * ideally be lowerCamelCase. Also, they must be limited to 64 characters in
   * length. When identifying the current value of an exceeded limit, the units
   * should be contained in the key, not the value.  For example, rather than
   * `{"instanceLimit": "100/request"}`, should be returned as,
   * `{"instanceLimitPerRequest": "100"}`, if the client exceeds the number of
   * instances that can be created in a single (batch) request.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 3;</code>
   */
  boolean containsMetadata(java.lang.String key);
  /** Use {@link #getMetadataMap()} instead. */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String> getMetadata();
  /**
   *
   *
   * <pre>
   * Additional structured details about this error.
   *
   * Keys must match a regular expression of `[a-z][a-zA-Z0-9-_]+` but should
   * ideally be lowerCamelCase. Also, they must be limited to 64 characters in
   * length. When identifying the current value of an exceeded limit, the units
   * should be contained in the key, not the value.  For example, rather than
   * `{"instanceLimit": "100/request"}`, should be returned as,
   * `{"instanceLimitPerRequest": "100"}`, if the client exceeds the number of
   * instances that can be created in a single (batch) request.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 3;</code>
   */
  java.util.Map<java.lang.String, java.lang.String> getMetadataMap();
  /**
   *
   *
   * <pre>
   * Additional structured details about this error.
   *
   * Keys must match a regular expression of `[a-z][a-zA-Z0-9-_]+` but should
   * ideally be lowerCamelCase. Also, they must be limited to 64 characters in
   * length. When identifying the current value of an exceeded limit, the units
   * should be contained in the key, not the value.  For example, rather than
   * `{"instanceLimit": "100/request"}`, should be returned as,
   * `{"instanceLimitPerRequest": "100"}`, if the client exceeds the number of
   * instances that can be created in a single (batch) request.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 3;</code>
   */

  /* nullable */
  java.lang.String getMetadataOrDefault(
      java.lang.String key,
      /* nullable */
      java.lang.String defaultValue);
  /**
   *
   *
   * <pre>
   * Additional structured details about this error.
   *
   * Keys must match a regular expression of `[a-z][a-zA-Z0-9-_]+` but should
   * ideally be lowerCamelCase. Also, they must be limited to 64 characters in
   * length. When identifying the current value of an exceeded limit, the units
   * should be contained in the key, not the value.  For example, rather than
   * `{"instanceLimit": "100/request"}`, should be returned as,
   * `{"instanceLimitPerRequest": "100"}`, if the client exceeds the number of
   * instances that can be created in a single (batch) request.
   * </pre>
   *
   * <code>map&lt;string, string&gt; metadata = 3;</code>
   */
  java.lang.String getMetadataOrThrow(java.lang.String key);
}
