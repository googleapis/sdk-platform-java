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
// source: google/type/phone_number.proto

// Protobuf Java Version: 3.25.8
package com.google.type;

public interface PhoneNumberOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.type.PhoneNumber)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The phone number, represented as a leading plus sign ('+'), followed by a
   * phone number that uses a relaxed ITU E.164 format consisting of the
   * country calling code (1 to 3 digits) and the subscriber number, with no
   * additional spaces or formatting, e.g.:
   *  - correct: "+15552220123"
   *  - incorrect: "+1 (555) 222-01234 x123".
   *
   * The ITU E.164 format limits the latter to 12 digits, but in practice not
   * all countries respect that, so we relax that restriction here.
   * National-only numbers are not allowed.
   *
   * References:
   *  - https://www.itu.int/rec/T-REC-E.164-201011-I
   *  - https://en.wikipedia.org/wiki/E.164.
   *  - https://en.wikipedia.org/wiki/List_of_country_calling_codes
   * </pre>
   *
   * <code>string e164_number = 1;</code>
   *
   * @return Whether the e164Number field is set.
   */
  boolean hasE164Number();

  /**
   *
   *
   * <pre>
   * The phone number, represented as a leading plus sign ('+'), followed by a
   * phone number that uses a relaxed ITU E.164 format consisting of the
   * country calling code (1 to 3 digits) and the subscriber number, with no
   * additional spaces or formatting, e.g.:
   *  - correct: "+15552220123"
   *  - incorrect: "+1 (555) 222-01234 x123".
   *
   * The ITU E.164 format limits the latter to 12 digits, but in practice not
   * all countries respect that, so we relax that restriction here.
   * National-only numbers are not allowed.
   *
   * References:
   *  - https://www.itu.int/rec/T-REC-E.164-201011-I
   *  - https://en.wikipedia.org/wiki/E.164.
   *  - https://en.wikipedia.org/wiki/List_of_country_calling_codes
   * </pre>
   *
   * <code>string e164_number = 1;</code>
   *
   * @return The e164Number.
   */
  java.lang.String getE164Number();

  /**
   *
   *
   * <pre>
   * The phone number, represented as a leading plus sign ('+'), followed by a
   * phone number that uses a relaxed ITU E.164 format consisting of the
   * country calling code (1 to 3 digits) and the subscriber number, with no
   * additional spaces or formatting, e.g.:
   *  - correct: "+15552220123"
   *  - incorrect: "+1 (555) 222-01234 x123".
   *
   * The ITU E.164 format limits the latter to 12 digits, but in practice not
   * all countries respect that, so we relax that restriction here.
   * National-only numbers are not allowed.
   *
   * References:
   *  - https://www.itu.int/rec/T-REC-E.164-201011-I
   *  - https://en.wikipedia.org/wiki/E.164.
   *  - https://en.wikipedia.org/wiki/List_of_country_calling_codes
   * </pre>
   *
   * <code>string e164_number = 1;</code>
   *
   * @return The bytes for e164Number.
   */
  com.google.protobuf.ByteString getE164NumberBytes();

  /**
   *
   *
   * <pre>
   * A short code.
   *
   * Reference(s):
   *  - https://en.wikipedia.org/wiki/Short_code
   * </pre>
   *
   * <code>.google.type.PhoneNumber.ShortCode short_code = 2;</code>
   *
   * @return Whether the shortCode field is set.
   */
  boolean hasShortCode();

  /**
   *
   *
   * <pre>
   * A short code.
   *
   * Reference(s):
   *  - https://en.wikipedia.org/wiki/Short_code
   * </pre>
   *
   * <code>.google.type.PhoneNumber.ShortCode short_code = 2;</code>
   *
   * @return The shortCode.
   */
  com.google.type.PhoneNumber.ShortCode getShortCode();

  /**
   *
   *
   * <pre>
   * A short code.
   *
   * Reference(s):
   *  - https://en.wikipedia.org/wiki/Short_code
   * </pre>
   *
   * <code>.google.type.PhoneNumber.ShortCode short_code = 2;</code>
   */
  com.google.type.PhoneNumber.ShortCodeOrBuilder getShortCodeOrBuilder();

  /**
   *
   *
   * <pre>
   * The phone number's extension. The extension is not standardized in ITU
   * recommendations, except for being defined as a series of numbers with a
   * maximum length of 40 digits. Other than digits, some other dialing
   * characters such as ',' (indicating a wait) or '#' may be stored here.
   *
   * Note that no regions currently use extensions with short codes, so this
   * field is normally only set in conjunction with an E.164 number. It is held
   * separately from the E.164 number to allow for short code extensions in the
   * future.
   * </pre>
   *
   * <code>string extension = 3;</code>
   *
   * @return The extension.
   */
  java.lang.String getExtension();

  /**
   *
   *
   * <pre>
   * The phone number's extension. The extension is not standardized in ITU
   * recommendations, except for being defined as a series of numbers with a
   * maximum length of 40 digits. Other than digits, some other dialing
   * characters such as ',' (indicating a wait) or '#' may be stored here.
   *
   * Note that no regions currently use extensions with short codes, so this
   * field is normally only set in conjunction with an E.164 number. It is held
   * separately from the E.164 number to allow for short code extensions in the
   * future.
   * </pre>
   *
   * <code>string extension = 3;</code>
   *
   * @return The bytes for extension.
   */
  com.google.protobuf.ByteString getExtensionBytes();

  com.google.type.PhoneNumber.KindCase getKindCase();
}
