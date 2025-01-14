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
// source: schema/google/showcase/v1beta1/messaging.proto

// Protobuf Java Version: 3.25.5
package com.google.showcase.v1beta1;

public interface SearchBlurbsMetadataOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.SearchBlurbsMetadata)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * This signals to the client when to next poll for response.
   * </pre>
   *
   * <code>.google.rpc.RetryInfo retry_info = 1;</code>
   *
   * @return Whether the retryInfo field is set.
   */
  boolean hasRetryInfo();
  /**
   *
   *
   * <pre>
   * This signals to the client when to next poll for response.
   * </pre>
   *
   * <code>.google.rpc.RetryInfo retry_info = 1;</code>
   *
   * @return The retryInfo.
   */
  com.google.rpc.RetryInfo getRetryInfo();
  /**
   *
   *
   * <pre>
   * This signals to the client when to next poll for response.
   * </pre>
   *
   * <code>.google.rpc.RetryInfo retry_info = 1;</code>
   */
  com.google.rpc.RetryInfoOrBuilder getRetryInfoOrBuilder();
}
