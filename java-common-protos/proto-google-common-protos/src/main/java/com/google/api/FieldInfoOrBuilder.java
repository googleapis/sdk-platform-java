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
// source: google/api/field_info.proto

// Protobuf Java Version: 3.25.8
package com.google.api;

public interface FieldInfoOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.api.FieldInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The standard format of a field value. This does not explicitly configure
   * any API consumer, just documents the API's format for the field it is
   * applied to.
   * </pre>
   *
   * <code>.google.api.FieldInfo.Format format = 1;</code>
   *
   * @return The enum numeric value on the wire for format.
   */
  int getFormatValue();

  /**
   *
   *
   * <pre>
   * The standard format of a field value. This does not explicitly configure
   * any API consumer, just documents the API's format for the field it is
   * applied to.
   * </pre>
   *
   * <code>.google.api.FieldInfo.Format format = 1;</code>
   *
   * @return The format.
   */
  com.google.api.FieldInfo.Format getFormat();

  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  java.util.List<com.google.api.TypeReference> getReferencedTypesList();

  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  com.google.api.TypeReference getReferencedTypes(int index);

  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  int getReferencedTypesCount();

  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  java.util.List<? extends com.google.api.TypeReferenceOrBuilder> getReferencedTypesOrBuilderList();

  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  com.google.api.TypeReferenceOrBuilder getReferencedTypesOrBuilder(int index);
}
