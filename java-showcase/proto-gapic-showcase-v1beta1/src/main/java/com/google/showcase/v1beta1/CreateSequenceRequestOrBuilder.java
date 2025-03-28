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

public interface CreateSequenceRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.CreateSequenceRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.google.showcase.v1beta1.Sequence sequence = 1;</code>
   *
   * @return Whether the sequence field is set.
   */
  boolean hasSequence();
  /**
   * <code>.google.showcase.v1beta1.Sequence sequence = 1;</code>
   *
   * @return The sequence.
   */
  com.google.showcase.v1beta1.Sequence getSequence();
  /** <code>.google.showcase.v1beta1.Sequence sequence = 1;</code> */
  com.google.showcase.v1beta1.SequenceOrBuilder getSequenceOrBuilder();
}
