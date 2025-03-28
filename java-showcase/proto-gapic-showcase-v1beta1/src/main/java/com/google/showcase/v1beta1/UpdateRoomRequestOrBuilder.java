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

public interface UpdateRoomRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.UpdateRoomRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The room to update.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Room room = 1;</code>
   *
   * @return Whether the room field is set.
   */
  boolean hasRoom();

  /**
   *
   *
   * <pre>
   * The room to update.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Room room = 1;</code>
   *
   * @return The room.
   */
  com.google.showcase.v1beta1.Room getRoom();

  /**
   *
   *
   * <pre>
   * The room to update.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Room room = 1;</code>
   */
  com.google.showcase.v1beta1.RoomOrBuilder getRoomOrBuilder();

  /**
   *
   *
   * <pre>
   * The field mask to determine which fields are to be updated. If empty, the
   * server will assume all fields are to be updated.
   * </pre>
   *
   * <code>.google.protobuf.FieldMask update_mask = 2;</code>
   *
   * @return Whether the updateMask field is set.
   */
  boolean hasUpdateMask();

  /**
   *
   *
   * <pre>
   * The field mask to determine which fields are to be updated. If empty, the
   * server will assume all fields are to be updated.
   * </pre>
   *
   * <code>.google.protobuf.FieldMask update_mask = 2;</code>
   *
   * @return The updateMask.
   */
  com.google.protobuf.FieldMask getUpdateMask();

  /**
   *
   *
   * <pre>
   * The field mask to determine which fields are to be updated. If empty, the
   * server will assume all fields are to be updated.
   * </pre>
   *
   * <code>.google.protobuf.FieldMask update_mask = 2;</code>
   */
  com.google.protobuf.FieldMaskOrBuilder getUpdateMaskOrBuilder();
}
