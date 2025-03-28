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

public interface ImageComponentOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.apps.card.v1.ImageComponent)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The image URL.
   * </pre>
   *
   * <code>string image_uri = 1;</code>
   *
   * @return The imageUri.
   */
  java.lang.String getImageUri();

  /**
   *
   *
   * <pre>
   * The image URL.
   * </pre>
   *
   * <code>string image_uri = 1;</code>
   *
   * @return The bytes for imageUri.
   */
  com.google.protobuf.ByteString getImageUriBytes();

  /**
   *
   *
   * <pre>
   * The accessibility label for the image.
   * </pre>
   *
   * <code>string alt_text = 2;</code>
   *
   * @return The altText.
   */
  java.lang.String getAltText();

  /**
   *
   *
   * <pre>
   * The accessibility label for the image.
   * </pre>
   *
   * <code>string alt_text = 2;</code>
   *
   * @return The bytes for altText.
   */
  com.google.protobuf.ByteString getAltTextBytes();

  /**
   *
   *
   * <pre>
   * The crop style to apply to the image.
   * </pre>
   *
   * <code>.google.apps.card.v1.ImageCropStyle crop_style = 3;</code>
   *
   * @return Whether the cropStyle field is set.
   */
  boolean hasCropStyle();

  /**
   *
   *
   * <pre>
   * The crop style to apply to the image.
   * </pre>
   *
   * <code>.google.apps.card.v1.ImageCropStyle crop_style = 3;</code>
   *
   * @return The cropStyle.
   */
  com.google.apps.card.v1.ImageCropStyle getCropStyle();

  /**
   *
   *
   * <pre>
   * The crop style to apply to the image.
   * </pre>
   *
   * <code>.google.apps.card.v1.ImageCropStyle crop_style = 3;</code>
   */
  com.google.apps.card.v1.ImageCropStyleOrBuilder getCropStyleOrBuilder();

  /**
   *
   *
   * <pre>
   * The border style to apply to the image.
   * </pre>
   *
   * <code>.google.apps.card.v1.BorderStyle border_style = 4;</code>
   *
   * @return Whether the borderStyle field is set.
   */
  boolean hasBorderStyle();

  /**
   *
   *
   * <pre>
   * The border style to apply to the image.
   * </pre>
   *
   * <code>.google.apps.card.v1.BorderStyle border_style = 4;</code>
   *
   * @return The borderStyle.
   */
  com.google.apps.card.v1.BorderStyle getBorderStyle();

  /**
   *
   *
   * <pre>
   * The border style to apply to the image.
   * </pre>
   *
   * <code>.google.apps.card.v1.BorderStyle border_style = 4;</code>
   */
  com.google.apps.card.v1.BorderStyleOrBuilder getBorderStyleOrBuilder();
}
