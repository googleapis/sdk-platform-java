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
// source: google/geo/type/viewport.proto

// Protobuf Java Version: 3.25.4
package com.google.geo.type;

public interface ViewportOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.geo.type.Viewport)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Required. The low point of the viewport.
   * </pre>
   *
   * <code>.google.type.LatLng low = 1;</code>
   *
   * @return Whether the low field is set.
   */
  boolean hasLow();
  /**
   *
   *
   * <pre>
   * Required. The low point of the viewport.
   * </pre>
   *
   * <code>.google.type.LatLng low = 1;</code>
   *
   * @return The low.
   */
  com.google.type.LatLng getLow();
  /**
   *
   *
   * <pre>
   * Required. The low point of the viewport.
   * </pre>
   *
   * <code>.google.type.LatLng low = 1;</code>
   */
  com.google.type.LatLngOrBuilder getLowOrBuilder();

  /**
   *
   *
   * <pre>
   * Required. The high point of the viewport.
   * </pre>
   *
   * <code>.google.type.LatLng high = 2;</code>
   *
   * @return Whether the high field is set.
   */
  boolean hasHigh();
  /**
   *
   *
   * <pre>
   * Required. The high point of the viewport.
   * </pre>
   *
   * <code>.google.type.LatLng high = 2;</code>
   *
   * @return The high.
   */
  com.google.type.LatLng getHigh();
  /**
   *
   *
   * <pre>
   * Required. The high point of the viewport.
   * </pre>
   *
   * <code>.google.type.LatLng high = 2;</code>
   */
  com.google.type.LatLngOrBuilder getHighOrBuilder();
}
