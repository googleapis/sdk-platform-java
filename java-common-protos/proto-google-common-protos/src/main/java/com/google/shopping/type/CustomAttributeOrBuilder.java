/*
 * Copyright 2020 Google LLC
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
// source: google/shopping/type/types.proto

// Protobuf Java Version: 3.25.3
package com.google.shopping.type;

public interface CustomAttributeOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.shopping.type.CustomAttribute)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The name of the attribute.
   * </pre>
   *
   * <code>optional string name = 1;</code>
   *
   * @return Whether the name field is set.
   */
  boolean hasName();
  /**
   *
   *
   * <pre>
   * The name of the attribute.
   * </pre>
   *
   * <code>optional string name = 1;</code>
   *
   * @return The name.
   */
  java.lang.String getName();
  /**
   *
   *
   * <pre>
   * The name of the attribute.
   * </pre>
   *
   * <code>optional string name = 1;</code>
   *
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString getNameBytes();

  /**
   *
   *
   * <pre>
   * The value of the attribute. If `value` is not empty, `group_values` must be
   * empty.
   * </pre>
   *
   * <code>optional string value = 2;</code>
   *
   * @return Whether the value field is set.
   */
  boolean hasValue();
  /**
   *
   *
   * <pre>
   * The value of the attribute. If `value` is not empty, `group_values` must be
   * empty.
   * </pre>
   *
   * <code>optional string value = 2;</code>
   *
   * @return The value.
   */
  java.lang.String getValue();
  /**
   *
   *
   * <pre>
   * The value of the attribute. If `value` is not empty, `group_values` must be
   * empty.
   * </pre>
   *
   * <code>optional string value = 2;</code>
   *
   * @return The bytes for value.
   */
  com.google.protobuf.ByteString getValueBytes();

  /**
   *
   *
   * <pre>
   * Subattributes within this attribute group.  If
   * `group_values` is not empty, `value` must be empty.
   * </pre>
   *
   * <code>repeated .google.shopping.type.CustomAttribute group_values = 3;</code>
   */
  java.util.List<com.google.shopping.type.CustomAttribute> getGroupValuesList();
  /**
   *
   *
   * <pre>
   * Subattributes within this attribute group.  If
   * `group_values` is not empty, `value` must be empty.
   * </pre>
   *
   * <code>repeated .google.shopping.type.CustomAttribute group_values = 3;</code>
   */
  com.google.shopping.type.CustomAttribute getGroupValues(int index);
  /**
   *
   *
   * <pre>
   * Subattributes within this attribute group.  If
   * `group_values` is not empty, `value` must be empty.
   * </pre>
   *
   * <code>repeated .google.shopping.type.CustomAttribute group_values = 3;</code>
   */
  int getGroupValuesCount();
  /**
   *
   *
   * <pre>
   * Subattributes within this attribute group.  If
   * `group_values` is not empty, `value` must be empty.
   * </pre>
   *
   * <code>repeated .google.shopping.type.CustomAttribute group_values = 3;</code>
   */
  java.util.List<? extends com.google.shopping.type.CustomAttributeOrBuilder>
      getGroupValuesOrBuilderList();
  /**
   *
   *
   * <pre>
   * Subattributes within this attribute group.  If
   * `group_values` is not empty, `value` must be empty.
   * </pre>
   *
   * <code>repeated .google.shopping.type.CustomAttribute group_values = 3;</code>
   */
  com.google.shopping.type.CustomAttributeOrBuilder getGroupValuesOrBuilder(int index);
}
