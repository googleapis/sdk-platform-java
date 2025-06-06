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

// Protobuf Java Version: 3.25.8
package com.google.showcase.v1beta1;

public interface StreamingSequenceOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.StreamingSequence)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string name = 1 [(.google.api.field_behavior) = OUTPUT_ONLY];</code>
   *
   * @return The name.
   */
  java.lang.String getName();

  /**
   * <code>string name = 1 [(.google.api.field_behavior) = OUTPUT_ONLY];</code>
   *
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString getNameBytes();

  /**
   *
   *
   * <pre>
   * The Content that the stream will send
   * </pre>
   *
   * <code>string content = 2;</code>
   *
   * @return The content.
   */
  java.lang.String getContent();

  /**
   *
   *
   * <pre>
   * The Content that the stream will send
   * </pre>
   *
   * <code>string content = 2;</code>
   *
   * @return The bytes for content.
   */
  com.google.protobuf.ByteString getContentBytes();

  /**
   *
   *
   * <pre>
   * Sequence of responses to return in order for each attempt. If empty, the
   * default response is an immediate OK.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.StreamingSequence.Response responses = 3;</code>
   */
  java.util.List<com.google.showcase.v1beta1.StreamingSequence.Response> getResponsesList();

  /**
   *
   *
   * <pre>
   * Sequence of responses to return in order for each attempt. If empty, the
   * default response is an immediate OK.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.StreamingSequence.Response responses = 3;</code>
   */
  com.google.showcase.v1beta1.StreamingSequence.Response getResponses(int index);

  /**
   *
   *
   * <pre>
   * Sequence of responses to return in order for each attempt. If empty, the
   * default response is an immediate OK.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.StreamingSequence.Response responses = 3;</code>
   */
  int getResponsesCount();

  /**
   *
   *
   * <pre>
   * Sequence of responses to return in order for each attempt. If empty, the
   * default response is an immediate OK.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.StreamingSequence.Response responses = 3;</code>
   */
  java.util.List<? extends com.google.showcase.v1beta1.StreamingSequence.ResponseOrBuilder>
      getResponsesOrBuilderList();

  /**
   *
   *
   * <pre>
   * Sequence of responses to return in order for each attempt. If empty, the
   * default response is an immediate OK.
   * </pre>
   *
   * <code>repeated .google.showcase.v1beta1.StreamingSequence.Response responses = 3;</code>
   */
  com.google.showcase.v1beta1.StreamingSequence.ResponseOrBuilder getResponsesOrBuilder(int index);
}
