/*
 * Copyright 2023 Google LLC
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
// source: google/type/latlng.proto

// Protobuf Java Version: 3.25.2
package com.google.type;

public interface LatLngOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.type.LatLng)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The latitude in degrees. It must be in the range [-90.0, +90.0].
   * </pre>
   *
   * <code>double latitude = 1;</code>
   *
   * @return The latitude.
   */
  double getLatitude();

  /**
   *
   *
   * <pre>
   * The longitude in degrees. It must be in the range [-180.0, +180.0].
   * </pre>
   *
   * <code>double longitude = 2;</code>
   *
   * @return The longitude.
   */
  double getLongitude();
}
