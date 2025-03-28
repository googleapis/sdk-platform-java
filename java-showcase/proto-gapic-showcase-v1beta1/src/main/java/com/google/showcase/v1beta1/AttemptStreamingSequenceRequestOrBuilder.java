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
// source: schema/google/showcase/v1beta1/sequence.proto

// Protobuf Java Version: 3.25.5
package com.google.showcase.v1beta1;

public interface AttemptStreamingSequenceRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.AttemptStreamingSequenceRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>
   * string name = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <code>
   * string name = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString getNameBytes();

  /**
   *
   *
   * <pre>
   * used to send the index of the last failed message
   * in the string "content" of an AttemptStreamingSequenceResponse
   * needed for stream resumption logic testing
   * </pre>
   *
   * <code>int32 last_fail_index = 2 [(.google.api.field_behavior) = OPTIONAL];</code>
   *
   * @return The lastFailIndex.
   */
  int getLastFailIndex();
}
