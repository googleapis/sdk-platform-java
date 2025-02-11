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
// source: google/apps/card/v1/card.proto

// Protobuf Java Version: 3.25.5
package com.google.apps.card.v1;

public interface BorderStyleOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.apps.card.v1.BorderStyle)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   *
   *
   * <pre>
   * The border type.
   * </pre>
   *
   * <code>.google.apps.card.v1.BorderStyle.BorderType type = 1;</code>
   *
   * @return The enum numeric value on the wire for type.
   */
  int getTypeValue();
  /**
   *
   *
   * <pre>
   * The border type.
   * </pre>
   *
   * <code>.google.apps.card.v1.BorderStyle.BorderType type = 1;</code>
   *
   * @return The type.
   */
  com.google.apps.card.v1.BorderStyle.BorderType getType();

  /**
   *
   *
   * <pre>
   * The colors to use when the type is `BORDER_TYPE_STROKE`.
   * </pre>
   *
   * <code>.google.type.Color stroke_color = 2;</code>
   *
   * @return Whether the strokeColor field is set.
   */
  boolean hasStrokeColor();
  /**
   *
   *
   * <pre>
   * The colors to use when the type is `BORDER_TYPE_STROKE`.
   * </pre>
   *
   * <code>.google.type.Color stroke_color = 2;</code>
   *
   * @return The strokeColor.
   */
  com.google.type.Color getStrokeColor();

  /**
   *
   *
   * <pre>
   * The corner radius for the border.
   * </pre>
   *
   * <code>int32 corner_radius = 3;</code>
   *
   * @return The cornerRadius.
   */
  int getCornerRadius();
}
