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

public interface BlurbOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.Blurb)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The resource name of the chat room.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The name.
   */
  java.lang.String getName();
  /**
   *
   *
   * <pre>
   * The resource name of the chat room.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString getNameBytes();

  /**
   *
   *
   * <pre>
   * The resource name of the blurb's author.
   * </pre>
   *
   * <code>
   * string user = 2 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @return The user.
   */
  java.lang.String getUser();
  /**
   *
   *
   * <pre>
   * The resource name of the blurb's author.
   * </pre>
   *
   * <code>
   * string user = 2 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @return The bytes for user.
   */
  com.google.protobuf.ByteString getUserBytes();

  /**
   *
   *
   * <pre>
   * The textual content of this blurb.
   * </pre>
   *
   * <code>string text = 3;</code>
   *
   * @return Whether the text field is set.
   */
  boolean hasText();
  /**
   *
   *
   * <pre>
   * The textual content of this blurb.
   * </pre>
   *
   * <code>string text = 3;</code>
   *
   * @return The text.
   */
  java.lang.String getText();
  /**
   *
   *
   * <pre>
   * The textual content of this blurb.
   * </pre>
   *
   * <code>string text = 3;</code>
   *
   * @return The bytes for text.
   */
  com.google.protobuf.ByteString getTextBytes();

  /**
   *
   *
   * <pre>
   * The image content of this blurb.
   * </pre>
   *
   * <code>bytes image = 4;</code>
   *
   * @return Whether the image field is set.
   */
  boolean hasImage();
  /**
   *
   *
   * <pre>
   * The image content of this blurb.
   * </pre>
   *
   * <code>bytes image = 4;</code>
   *
   * @return The image.
   */
  com.google.protobuf.ByteString getImage();

  /**
   *
   *
   * <pre>
   * The timestamp at which the blurb was created.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp create_time = 5 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return Whether the createTime field is set.
   */
  boolean hasCreateTime();
  /**
   *
   *
   * <pre>
   * The timestamp at which the blurb was created.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp create_time = 5 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return The createTime.
   */
  com.google.protobuf.Timestamp getCreateTime();
  /**
   *
   *
   * <pre>
   * The timestamp at which the blurb was created.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp create_time = 5 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   */
  com.google.protobuf.TimestampOrBuilder getCreateTimeOrBuilder();

  /**
   *
   *
   * <pre>
   * The latest timestamp at which the blurb was updated.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp update_time = 6 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return Whether the updateTime field is set.
   */
  boolean hasUpdateTime();
  /**
   *
   *
   * <pre>
   * The latest timestamp at which the blurb was updated.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp update_time = 6 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return The updateTime.
   */
  com.google.protobuf.Timestamp getUpdateTime();
  /**
   *
   *
   * <pre>
   * The latest timestamp at which the blurb was updated.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp update_time = 6 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   */
  com.google.protobuf.TimestampOrBuilder getUpdateTimeOrBuilder();

  /**
   *
   *
   * <pre>
   * The legacy id of the room. This field is used to signal
   * the use of the compound resource pattern
   * `rooms/{room}/blurbs/legacy/{legacy_room}.{blurb}`
   * </pre>
   *
   * <code>string legacy_room_id = 7;</code>
   *
   * @return Whether the legacyRoomId field is set.
   */
  boolean hasLegacyRoomId();
  /**
   *
   *
   * <pre>
   * The legacy id of the room. This field is used to signal
   * the use of the compound resource pattern
   * `rooms/{room}/blurbs/legacy/{legacy_room}.{blurb}`
   * </pre>
   *
   * <code>string legacy_room_id = 7;</code>
   *
   * @return The legacyRoomId.
   */
  java.lang.String getLegacyRoomId();
  /**
   *
   *
   * <pre>
   * The legacy id of the room. This field is used to signal
   * the use of the compound resource pattern
   * `rooms/{room}/blurbs/legacy/{legacy_room}.{blurb}`
   * </pre>
   *
   * <code>string legacy_room_id = 7;</code>
   *
   * @return The bytes for legacyRoomId.
   */
  com.google.protobuf.ByteString getLegacyRoomIdBytes();

  /**
   *
   *
   * <pre>
   * The legacy id of the user. This field is used to signal
   * the use of the compound resource pattern
   * `users/{user}/profile/blurbs/legacy/{legacy_user}~{blurb}`
   * </pre>
   *
   * <code>string legacy_user_id = 8;</code>
   *
   * @return Whether the legacyUserId field is set.
   */
  boolean hasLegacyUserId();
  /**
   *
   *
   * <pre>
   * The legacy id of the user. This field is used to signal
   * the use of the compound resource pattern
   * `users/{user}/profile/blurbs/legacy/{legacy_user}~{blurb}`
   * </pre>
   *
   * <code>string legacy_user_id = 8;</code>
   *
   * @return The legacyUserId.
   */
  java.lang.String getLegacyUserId();
  /**
   *
   *
   * <pre>
   * The legacy id of the user. This field is used to signal
   * the use of the compound resource pattern
   * `users/{user}/profile/blurbs/legacy/{legacy_user}~{blurb}`
   * </pre>
   *
   * <code>string legacy_user_id = 8;</code>
   *
   * @return The bytes for legacyUserId.
   */
  com.google.protobuf.ByteString getLegacyUserIdBytes();

  com.google.showcase.v1beta1.Blurb.ContentCase getContentCase();

  com.google.showcase.v1beta1.Blurb.LegacyIdCase getLegacyIdCase();
}
