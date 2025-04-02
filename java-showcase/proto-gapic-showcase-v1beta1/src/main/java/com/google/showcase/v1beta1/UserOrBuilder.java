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
// source: schema/google/showcase/v1beta1/identity.proto

// Protobuf Java Version: 3.25.5
package com.google.showcase.v1beta1;

public interface UserOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.User)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The resource name of the user.
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
   * The resource name of the user.
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
   * The display_name of the user.
   * </pre>
   *
   * <code>string display_name = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return The displayName.
   */
  java.lang.String getDisplayName();

  /**
   *
   *
   * <pre>
   * The display_name of the user.
   * </pre>
   *
   * <code>string display_name = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return The bytes for displayName.
   */
  com.google.protobuf.ByteString getDisplayNameBytes();

  /**
   *
   *
   * <pre>
   * The email address of the user.
   * </pre>
   *
   * <code>string email = 3 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return The email.
   */
  java.lang.String getEmail();

  /**
   *
   *
   * <pre>
   * The email address of the user.
   * </pre>
   *
   * <code>string email = 3 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return The bytes for email.
   */
  com.google.protobuf.ByteString getEmailBytes();

  /**
   *
   *
   * <pre>
   * The timestamp at which the user was created.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp create_time = 4 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return Whether the createTime field is set.
   */
  boolean hasCreateTime();

  /**
   *
   *
   * <pre>
   * The timestamp at which the user was created.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp create_time = 4 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return The createTime.
   */
  com.google.protobuf.Timestamp getCreateTime();

  /**
   *
   *
   * <pre>
   * The timestamp at which the user was created.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp create_time = 4 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   */
  com.google.protobuf.TimestampOrBuilder getCreateTimeOrBuilder();

  /**
   *
   *
   * <pre>
   * The latest timestamp at which the user was updated.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp update_time = 5 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return Whether the updateTime field is set.
   */
  boolean hasUpdateTime();

  /**
   *
   *
   * <pre>
   * The latest timestamp at which the user was updated.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp update_time = 5 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return The updateTime.
   */
  com.google.protobuf.Timestamp getUpdateTime();

  /**
   *
   *
   * <pre>
   * The latest timestamp at which the user was updated.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp update_time = 5 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   */
  com.google.protobuf.TimestampOrBuilder getUpdateTimeOrBuilder();

  /**
   *
   *
   * <pre>
   * The age of the user in years.
   * </pre>
   *
   * <code>optional int32 age = 6;</code>
   *
   * @return Whether the age field is set.
   */
  boolean hasAge();

  /**
   *
   *
   * <pre>
   * The age of the user in years.
   * </pre>
   *
   * <code>optional int32 age = 6;</code>
   *
   * @return The age.
   */
  int getAge();

  /**
   *
   *
   * <pre>
   * The height of the user in feet.
   * </pre>
   *
   * <code>optional double height_feet = 7;</code>
   *
   * @return Whether the heightFeet field is set.
   */
  boolean hasHeightFeet();

  /**
   *
   *
   * <pre>
   * The height of the user in feet.
   * </pre>
   *
   * <code>optional double height_feet = 7;</code>
   *
   * @return The heightFeet.
   */
  double getHeightFeet();

  /**
   *
   *
   * <pre>
   * The nickname of the user.
   *
   * (-- aip.dev/not-precedent: An empty string is a valid nickname.
   *     Ordinarily, proto3_optional should not be used on a `string` field. --)
   * </pre>
   *
   * <code>optional string nickname = 8;</code>
   *
   * @return Whether the nickname field is set.
   */
  boolean hasNickname();

  /**
   *
   *
   * <pre>
   * The nickname of the user.
   *
   * (-- aip.dev/not-precedent: An empty string is a valid nickname.
   *     Ordinarily, proto3_optional should not be used on a `string` field. --)
   * </pre>
   *
   * <code>optional string nickname = 8;</code>
   *
   * @return The nickname.
   */
  java.lang.String getNickname();

  /**
   *
   *
   * <pre>
   * The nickname of the user.
   *
   * (-- aip.dev/not-precedent: An empty string is a valid nickname.
   *     Ordinarily, proto3_optional should not be used on a `string` field. --)
   * </pre>
   *
   * <code>optional string nickname = 8;</code>
   *
   * @return The bytes for nickname.
   */
  com.google.protobuf.ByteString getNicknameBytes();

  /**
   *
   *
   * <pre>
   * Enables the receiving of notifications. The default is true if unset.
   *
   * (-- aip.dev/not-precedent: The default for the feature is true.
   *     Ordinarily, the default for a `bool` field should be false. --)
   * </pre>
   *
   * <code>optional bool enable_notifications = 9;</code>
   *
   * @return Whether the enableNotifications field is set.
   */
  boolean hasEnableNotifications();

  /**
   *
   *
   * <pre>
   * Enables the receiving of notifications. The default is true if unset.
   *
   * (-- aip.dev/not-precedent: The default for the feature is true.
   *     Ordinarily, the default for a `bool` field should be false. --)
   * </pre>
   *
   * <code>optional bool enable_notifications = 9;</code>
   *
   * @return The enableNotifications.
   */
  boolean getEnableNotifications();
}
