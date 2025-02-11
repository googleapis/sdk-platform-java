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
// source: google/cloud/extended_operations.proto

// Protobuf Java Version: 3.25.5
package com.google.cloud;

/**
 *
 *
 * <pre>
 * An enum to be used to mark the essential (for polling) fields in an
 * API-specific Operation object. A custom Operation object may contain many
 * different fields, but only few of them are essential to conduct a successful
 * polling process.
 * </pre>
 *
 * Protobuf enum {@code google.cloud.OperationResponseMapping}
 */
public enum OperationResponseMapping implements com.google.protobuf.Internal.EnumLite {
  /**
   *
   *
   * <pre>
   * Do not use.
   * </pre>
   *
   * <code>UNDEFINED = 0;</code>
   */
  UNDEFINED(0),
  /**
   *
   *
   * <pre>
   * A field in an API-specific (custom) Operation object which carries the same
   * meaning as google.longrunning.Operation.name.
   * </pre>
   *
   * <code>NAME = 1;</code>
   */
  NAME(1),
  /**
   *
   *
   * <pre>
   * A field in an API-specific (custom) Operation object which carries the same
   * meaning as google.longrunning.Operation.done. If the annotated field is of
   * an enum type, `annotated_field_name == EnumType.DONE` semantics should be
   * equivalent to `Operation.done == true`. If the annotated field is of type
   * boolean, then it should follow the same semantics as Operation.done.
   * Otherwise, a non-empty value should be treated as `Operation.done == true`.
   * </pre>
   *
   * <code>STATUS = 2;</code>
   */
  STATUS(2),
  /**
   *
   *
   * <pre>
   * A field in an API-specific (custom) Operation object which carries the same
   * meaning as google.longrunning.Operation.error.code.
   * </pre>
   *
   * <code>ERROR_CODE = 3;</code>
   */
  ERROR_CODE(3),
  /**
   *
   *
   * <pre>
   * A field in an API-specific (custom) Operation object which carries the same
   * meaning as google.longrunning.Operation.error.message.
   * </pre>
   *
   * <code>ERROR_MESSAGE = 4;</code>
   */
  ERROR_MESSAGE(4),
  UNRECOGNIZED(-1),
  ;

  /**
   *
   *
   * <pre>
   * Do not use.
   * </pre>
   *
   * <code>UNDEFINED = 0;</code>
   */
  public static final int UNDEFINED_VALUE = 0;
  /**
   *
   *
   * <pre>
   * A field in an API-specific (custom) Operation object which carries the same
   * meaning as google.longrunning.Operation.name.
   * </pre>
   *
   * <code>NAME = 1;</code>
   */
  public static final int NAME_VALUE = 1;
  /**
   *
   *
   * <pre>
   * A field in an API-specific (custom) Operation object which carries the same
   * meaning as google.longrunning.Operation.done. If the annotated field is of
   * an enum type, `annotated_field_name == EnumType.DONE` semantics should be
   * equivalent to `Operation.done == true`. If the annotated field is of type
   * boolean, then it should follow the same semantics as Operation.done.
   * Otherwise, a non-empty value should be treated as `Operation.done == true`.
   * </pre>
   *
   * <code>STATUS = 2;</code>
   */
  public static final int STATUS_VALUE = 2;
  /**
   *
   *
   * <pre>
   * A field in an API-specific (custom) Operation object which carries the same
   * meaning as google.longrunning.Operation.error.code.
   * </pre>
   *
   * <code>ERROR_CODE = 3;</code>
   */
  public static final int ERROR_CODE_VALUE = 3;
  /**
   *
   *
   * <pre>
   * A field in an API-specific (custom) Operation object which carries the same
   * meaning as google.longrunning.Operation.error.message.
   * </pre>
   *
   * <code>ERROR_MESSAGE = 4;</code>
   */
  public static final int ERROR_MESSAGE_VALUE = 4;

  @java.lang.Override
  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The number of the enum to look for.
   * @return The enum associated with the given number.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static OperationResponseMapping valueOf(int value) {
    return forNumber(value);
  }

  public static OperationResponseMapping forNumber(int value) {
    switch (value) {
      case 0:
        return UNDEFINED;
      case 1:
        return NAME;
      case 2:
        return STATUS;
      case 3:
        return ERROR_CODE;
      case 4:
        return ERROR_MESSAGE;
      default:
        return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<OperationResponseMapping>
      internalGetValueMap() {
    return internalValueMap;
  }

  private static final com.google.protobuf.Internal.EnumLiteMap<OperationResponseMapping>
      internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<OperationResponseMapping>() {
            @java.lang.Override
            public OperationResponseMapping findValueByNumber(int number) {
              return OperationResponseMapping.forNumber(number);
            }
          };

  public static com.google.protobuf.Internal.EnumVerifier internalGetVerifier() {
    return OperationResponseMappingVerifier.INSTANCE;
  }

  private static final class OperationResponseMappingVerifier
      implements com.google.protobuf.Internal.EnumVerifier {
    static final com.google.protobuf.Internal.EnumVerifier INSTANCE =
        new OperationResponseMappingVerifier();

    @java.lang.Override
    public boolean isInRange(int number) {
      return OperationResponseMapping.forNumber(number) != null;
    }
  };

  private final int value;

  private OperationResponseMapping(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:google.cloud.OperationResponseMapping)
}
