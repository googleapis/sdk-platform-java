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
// source: google/type/localized_text.proto

// Protobuf Java Version: 3.25.5
package com.google.type;

public interface LocalizedTextOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.type.LocalizedText)
    com.google.protobuf.MessageLiteOrBuilder {

  /**
   *
   *
   * <pre>
   * Localized string in the language corresponding to `language_code' below.
   * </pre>
   *
   * <code>string text = 1;</code>
   *
   * @return The text.
   */
  java.lang.String getText();
  /**
   *
   *
   * <pre>
   * Localized string in the language corresponding to `language_code' below.
   * </pre>
   *
   * <code>string text = 1;</code>
   *
   * @return The bytes for text.
   */
  com.google.protobuf.ByteString getTextBytes();

  /**
   *
   *
   * <pre>
   * The text's BCP-47 language code, such as "en-US" or "sr-Latn".
   *
   * For more information, see
   * http://www.unicode.org/reports/tr35/#Unicode_locale_identifier.
   * </pre>
   *
   * <code>string language_code = 2;</code>
   *
   * @return The languageCode.
   */
  java.lang.String getLanguageCode();
  /**
   *
   *
   * <pre>
   * The text's BCP-47 language code, such as "en-US" or "sr-Latn".
   *
   * For more information, see
   * http://www.unicode.org/reports/tr35/#Unicode_locale_identifier.
   * </pre>
   *
   * <code>string language_code = 2;</code>
   *
   * @return The bytes for languageCode.
   */
  com.google.protobuf.ByteString getLanguageCodeBytes();
}
