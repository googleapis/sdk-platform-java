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
// source: google/api/client.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

public interface SelectiveGapicGenerationOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.SelectiveGapicGeneration)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * An allowlist of the fully qualified names of RPCs that should be included
   * on public client surfaces.
   * </pre>
   *
   * <code>repeated string methods = 1;</code>
   *
   * @return A list containing the methods.
   */
  java.util.List<java.lang.String> getMethodsList();

  /**
   *
   *
   * <pre>
   * An allowlist of the fully qualified names of RPCs that should be included
   * on public client surfaces.
   * </pre>
   *
   * <code>repeated string methods = 1;</code>
   *
   * @return The count of methods.
   */
  int getMethodsCount();

  /**
   *
   *
   * <pre>
   * An allowlist of the fully qualified names of RPCs that should be included
   * on public client surfaces.
   * </pre>
   *
   * <code>repeated string methods = 1;</code>
   *
   * @param index The index of the element to return.
   * @return The methods at the given index.
   */
  java.lang.String getMethods(int index);

  /**
   *
   *
   * <pre>
   * An allowlist of the fully qualified names of RPCs that should be included
   * on public client surfaces.
   * </pre>
   *
   * <code>repeated string methods = 1;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the methods at the given index.
   */
  com.google.protobuf.ByteString getMethodsBytes(int index);
}
