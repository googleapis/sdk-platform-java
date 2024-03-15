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
// source: google/rpc/code.proto

// Protobuf Java Version: 3.25.2
package com.google.rpc;

/**
 *
 *
 * <pre>
 * The canonical error codes for gRPC APIs.
 *
 *
 * Sometimes multiple error codes may apply.  Services should return
 * the most specific error code that applies.  For example, prefer
 * `OUT_OF_RANGE` over `FAILED_PRECONDITION` if both codes apply.
 * Similarly prefer `NOT_FOUND` or `ALREADY_EXISTS` over `FAILED_PRECONDITION`.
 * </pre>
 *
 * Protobuf enum {@code google.rpc.Code}
 */
public enum Code implements com.google.protobuf.ProtocolMessageEnum {
  /**
   *
   *
   * <pre>
   * Not an error; returned on success.
   *
   * HTTP Mapping: 200 OK
   * </pre>
   *
   * <code>OK = 0;</code>
   */
  OK(0),
  /**
   *
   *
   * <pre>
   * The operation was cancelled, typically by the caller.
   *
   * HTTP Mapping: 499 Client Closed Request
   * </pre>
   *
   * <code>CANCELLED = 1;</code>
   */
  CANCELLED(1),
  /**
   *
   *
   * <pre>
   * Unknown error.  For example, this error may be returned when
   * a `Status` value received from another address space belongs to
   * an error space that is not known in this address space.  Also
   * errors raised by APIs that do not return enough error information
   * may be converted to this error.
   *
   * HTTP Mapping: 500 Internal Server Error
   * </pre>
   *
   * <code>UNKNOWN = 2;</code>
   */
  UNKNOWN(2),
  /**
   *
   *
   * <pre>
   * The client specified an invalid argument.  Note that this differs
   * from `FAILED_PRECONDITION`.  `INVALID_ARGUMENT` indicates arguments
   * that are problematic regardless of the state of the system
   * (e.g., a malformed file name).
   *
   * HTTP Mapping: 400 Bad Request
   * </pre>
   *
   * <code>INVALID_ARGUMENT = 3;</code>
   */
  INVALID_ARGUMENT(3),
  /**
   *
   *
   * <pre>
   * The deadline expired before the operation could complete. For operations
   * that change the state of the system, this error may be returned
   * even if the operation has completed successfully.  For example, a
   * successful response from a server could have been delayed long
   * enough for the deadline to expire.
   *
   * HTTP Mapping: 504 Gateway Timeout
   * </pre>
   *
   * <code>DEADLINE_EXCEEDED = 4;</code>
   */
  DEADLINE_EXCEEDED(4),
  /**
   *
   *
   * <pre>
   * Some requested entity (e.g., file or directory) was not found.
   *
   * Note to server developers: if a request is denied for an entire class
   * of users, such as gradual feature rollout or undocumented allowlist,
   * `NOT_FOUND` may be used. If a request is denied for some users within
   * a class of users, such as user-based access control, `PERMISSION_DENIED`
   * must be used.
   *
   * HTTP Mapping: 404 Not Found
   * </pre>
   *
   * <code>NOT_FOUND = 5;</code>
   */
  NOT_FOUND(5),
  /**
   *
   *
   * <pre>
   * The entity that a client attempted to create (e.g., file or directory)
   * already exists.
   *
   * HTTP Mapping: 409 Conflict
   * </pre>
   *
   * <code>ALREADY_EXISTS = 6;</code>
   */
  ALREADY_EXISTS(6),
  /**
   *
   *
   * <pre>
   * The caller does not have permission to execute the specified
   * operation. `PERMISSION_DENIED` must not be used for rejections
   * caused by exhausting some resource (use `RESOURCE_EXHAUSTED`
   * instead for those errors). `PERMISSION_DENIED` must not be
   * used if the caller can not be identified (use `UNAUTHENTICATED`
   * instead for those errors). This error code does not imply the
   * request is valid or the requested entity exists or satisfies
   * other pre-conditions.
   *
   * HTTP Mapping: 403 Forbidden
   * </pre>
   *
   * <code>PERMISSION_DENIED = 7;</code>
   */
  PERMISSION_DENIED(7),
  /**
   *
   *
   * <pre>
   * The request does not have valid authentication credentials for the
   * operation.
   *
   * HTTP Mapping: 401 Unauthorized
   * </pre>
   *
   * <code>UNAUTHENTICATED = 16;</code>
   */
  UNAUTHENTICATED(16),
  /**
   *
   *
   * <pre>
   * Some resource has been exhausted, perhaps a per-user quota, or
   * perhaps the entire file system is out of space.
   *
   * HTTP Mapping: 429 Too Many Requests
   * </pre>
   *
   * <code>RESOURCE_EXHAUSTED = 8;</code>
   */
  RESOURCE_EXHAUSTED(8),
  /**
   *
   *
   * <pre>
   * The operation was rejected because the system is not in a state
   * required for the operation's execution.  For example, the directory
   * to be deleted is non-empty, an rmdir operation is applied to
   * a non-directory, etc.
   *
   * Service implementors can use the following guidelines to decide
   * between `FAILED_PRECONDITION`, `ABORTED`, and `UNAVAILABLE`:
   *  (a) Use `UNAVAILABLE` if the client can retry just the failing call.
   *  (b) Use `ABORTED` if the client should retry at a higher level. For
   *      example, when a client-specified test-and-set fails, indicating the
   *      client should restart a read-modify-write sequence.
   *  (c) Use `FAILED_PRECONDITION` if the client should not retry until
   *      the system state has been explicitly fixed. For example, if an "rmdir"
   *      fails because the directory is non-empty, `FAILED_PRECONDITION`
   *      should be returned since the client should not retry unless
   *      the files are deleted from the directory.
   *
   * HTTP Mapping: 400 Bad Request
   * </pre>
   *
   * <code>FAILED_PRECONDITION = 9;</code>
   */
  FAILED_PRECONDITION(9),
  /**
   *
   *
   * <pre>
   * The operation was aborted, typically due to a concurrency issue such as
   * a sequencer check failure or transaction abort.
   *
   * See the guidelines above for deciding between `FAILED_PRECONDITION`,
   * `ABORTED`, and `UNAVAILABLE`.
   *
   * HTTP Mapping: 409 Conflict
   * </pre>
   *
   * <code>ABORTED = 10;</code>
   */
  ABORTED(10),
  /**
   *
   *
   * <pre>
   * The operation was attempted past the valid range.  E.g., seeking or
   * reading past end-of-file.
   *
   * Unlike `INVALID_ARGUMENT`, this error indicates a problem that may
   * be fixed if the system state changes. For example, a 32-bit file
   * system will generate `INVALID_ARGUMENT` if asked to read at an
   * offset that is not in the range [0,2^32-1], but it will generate
   * `OUT_OF_RANGE` if asked to read from an offset past the current
   * file size.
   *
   * There is a fair bit of overlap between `FAILED_PRECONDITION` and
   * `OUT_OF_RANGE`.  We recommend using `OUT_OF_RANGE` (the more specific
   * error) when it applies so that callers who are iterating through
   * a space can easily look for an `OUT_OF_RANGE` error to detect when
   * they are done.
   *
   * HTTP Mapping: 400 Bad Request
   * </pre>
   *
   * <code>OUT_OF_RANGE = 11;</code>
   */
  OUT_OF_RANGE(11),
  /**
   *
   *
   * <pre>
   * The operation is not implemented or is not supported/enabled in this
   * service.
   *
   * HTTP Mapping: 501 Not Implemented
   * </pre>
   *
   * <code>UNIMPLEMENTED = 12;</code>
   */
  UNIMPLEMENTED(12),
  /**
   *
   *
   * <pre>
   * Internal errors.  This means that some invariants expected by the
   * underlying system have been broken.  This error code is reserved
   * for serious errors.
   *
   * HTTP Mapping: 500 Internal Server Error
   * </pre>
   *
   * <code>INTERNAL = 13;</code>
   */
  INTERNAL(13),
  /**
   *
   *
   * <pre>
   * The service is currently unavailable.  This is most likely a
   * transient condition, which can be corrected by retrying with
   * a backoff. Note that it is not always safe to retry
   * non-idempotent operations.
   *
   * See the guidelines above for deciding between `FAILED_PRECONDITION`,
   * `ABORTED`, and `UNAVAILABLE`.
   *
   * HTTP Mapping: 503 Service Unavailable
   * </pre>
   *
   * <code>UNAVAILABLE = 14;</code>
   */
  UNAVAILABLE(14),
  /**
   *
   *
   * <pre>
   * Unrecoverable data loss or corruption.
   *
   * HTTP Mapping: 500 Internal Server Error
   * </pre>
   *
   * <code>DATA_LOSS = 15;</code>
   */
  DATA_LOSS(15),
  UNRECOGNIZED(-1),
  ;

  /**
   *
   *
   * <pre>
   * Not an error; returned on success.
   *
   * HTTP Mapping: 200 OK
   * </pre>
   *
   * <code>OK = 0;</code>
   */
  public static final int OK_VALUE = 0;
  /**
   *
   *
   * <pre>
   * The operation was cancelled, typically by the caller.
   *
   * HTTP Mapping: 499 Client Closed Request
   * </pre>
   *
   * <code>CANCELLED = 1;</code>
   */
  public static final int CANCELLED_VALUE = 1;
  /**
   *
   *
   * <pre>
   * Unknown error.  For example, this error may be returned when
   * a `Status` value received from another address space belongs to
   * an error space that is not known in this address space.  Also
   * errors raised by APIs that do not return enough error information
   * may be converted to this error.
   *
   * HTTP Mapping: 500 Internal Server Error
   * </pre>
   *
   * <code>UNKNOWN = 2;</code>
   */
  public static final int UNKNOWN_VALUE = 2;
  /**
   *
   *
   * <pre>
   * The client specified an invalid argument.  Note that this differs
   * from `FAILED_PRECONDITION`.  `INVALID_ARGUMENT` indicates arguments
   * that are problematic regardless of the state of the system
   * (e.g., a malformed file name).
   *
   * HTTP Mapping: 400 Bad Request
   * </pre>
   *
   * <code>INVALID_ARGUMENT = 3;</code>
   */
  public static final int INVALID_ARGUMENT_VALUE = 3;
  /**
   *
   *
   * <pre>
   * The deadline expired before the operation could complete. For operations
   * that change the state of the system, this error may be returned
   * even if the operation has completed successfully.  For example, a
   * successful response from a server could have been delayed long
   * enough for the deadline to expire.
   *
   * HTTP Mapping: 504 Gateway Timeout
   * </pre>
   *
   * <code>DEADLINE_EXCEEDED = 4;</code>
   */
  public static final int DEADLINE_EXCEEDED_VALUE = 4;
  /**
   *
   *
   * <pre>
   * Some requested entity (e.g., file or directory) was not found.
   *
   * Note to server developers: if a request is denied for an entire class
   * of users, such as gradual feature rollout or undocumented allowlist,
   * `NOT_FOUND` may be used. If a request is denied for some users within
   * a class of users, such as user-based access control, `PERMISSION_DENIED`
   * must be used.
   *
   * HTTP Mapping: 404 Not Found
   * </pre>
   *
   * <code>NOT_FOUND = 5;</code>
   */
  public static final int NOT_FOUND_VALUE = 5;
  /**
   *
   *
   * <pre>
   * The entity that a client attempted to create (e.g., file or directory)
   * already exists.
   *
   * HTTP Mapping: 409 Conflict
   * </pre>
   *
   * <code>ALREADY_EXISTS = 6;</code>
   */
  public static final int ALREADY_EXISTS_VALUE = 6;
  /**
   *
   *
   * <pre>
   * The caller does not have permission to execute the specified
   * operation. `PERMISSION_DENIED` must not be used for rejections
   * caused by exhausting some resource (use `RESOURCE_EXHAUSTED`
   * instead for those errors). `PERMISSION_DENIED` must not be
   * used if the caller can not be identified (use `UNAUTHENTICATED`
   * instead for those errors). This error code does not imply the
   * request is valid or the requested entity exists or satisfies
   * other pre-conditions.
   *
   * HTTP Mapping: 403 Forbidden
   * </pre>
   *
   * <code>PERMISSION_DENIED = 7;</code>
   */
  public static final int PERMISSION_DENIED_VALUE = 7;
  /**
   *
   *
   * <pre>
   * The request does not have valid authentication credentials for the
   * operation.
   *
   * HTTP Mapping: 401 Unauthorized
   * </pre>
   *
   * <code>UNAUTHENTICATED = 16;</code>
   */
  public static final int UNAUTHENTICATED_VALUE = 16;
  /**
   *
   *
   * <pre>
   * Some resource has been exhausted, perhaps a per-user quota, or
   * perhaps the entire file system is out of space.
   *
   * HTTP Mapping: 429 Too Many Requests
   * </pre>
   *
   * <code>RESOURCE_EXHAUSTED = 8;</code>
   */
  public static final int RESOURCE_EXHAUSTED_VALUE = 8;
  /**
   *
   *
   * <pre>
   * The operation was rejected because the system is not in a state
   * required for the operation's execution.  For example, the directory
   * to be deleted is non-empty, an rmdir operation is applied to
   * a non-directory, etc.
   *
   * Service implementors can use the following guidelines to decide
   * between `FAILED_PRECONDITION`, `ABORTED`, and `UNAVAILABLE`:
   *  (a) Use `UNAVAILABLE` if the client can retry just the failing call.
   *  (b) Use `ABORTED` if the client should retry at a higher level. For
   *      example, when a client-specified test-and-set fails, indicating the
   *      client should restart a read-modify-write sequence.
   *  (c) Use `FAILED_PRECONDITION` if the client should not retry until
   *      the system state has been explicitly fixed. For example, if an "rmdir"
   *      fails because the directory is non-empty, `FAILED_PRECONDITION`
   *      should be returned since the client should not retry unless
   *      the files are deleted from the directory.
   *
   * HTTP Mapping: 400 Bad Request
   * </pre>
   *
   * <code>FAILED_PRECONDITION = 9;</code>
   */
  public static final int FAILED_PRECONDITION_VALUE = 9;
  /**
   *
   *
   * <pre>
   * The operation was aborted, typically due to a concurrency issue such as
   * a sequencer check failure or transaction abort.
   *
   * See the guidelines above for deciding between `FAILED_PRECONDITION`,
   * `ABORTED`, and `UNAVAILABLE`.
   *
   * HTTP Mapping: 409 Conflict
   * </pre>
   *
   * <code>ABORTED = 10;</code>
   */
  public static final int ABORTED_VALUE = 10;
  /**
   *
   *
   * <pre>
   * The operation was attempted past the valid range.  E.g., seeking or
   * reading past end-of-file.
   *
   * Unlike `INVALID_ARGUMENT`, this error indicates a problem that may
   * be fixed if the system state changes. For example, a 32-bit file
   * system will generate `INVALID_ARGUMENT` if asked to read at an
   * offset that is not in the range [0,2^32-1], but it will generate
   * `OUT_OF_RANGE` if asked to read from an offset past the current
   * file size.
   *
   * There is a fair bit of overlap between `FAILED_PRECONDITION` and
   * `OUT_OF_RANGE`.  We recommend using `OUT_OF_RANGE` (the more specific
   * error) when it applies so that callers who are iterating through
   * a space can easily look for an `OUT_OF_RANGE` error to detect when
   * they are done.
   *
   * HTTP Mapping: 400 Bad Request
   * </pre>
   *
   * <code>OUT_OF_RANGE = 11;</code>
   */
  public static final int OUT_OF_RANGE_VALUE = 11;
  /**
   *
   *
   * <pre>
   * The operation is not implemented or is not supported/enabled in this
   * service.
   *
   * HTTP Mapping: 501 Not Implemented
   * </pre>
   *
   * <code>UNIMPLEMENTED = 12;</code>
   */
  public static final int UNIMPLEMENTED_VALUE = 12;
  /**
   *
   *
   * <pre>
   * Internal errors.  This means that some invariants expected by the
   * underlying system have been broken.  This error code is reserved
   * for serious errors.
   *
   * HTTP Mapping: 500 Internal Server Error
   * </pre>
   *
   * <code>INTERNAL = 13;</code>
   */
  public static final int INTERNAL_VALUE = 13;
  /**
   *
   *
   * <pre>
   * The service is currently unavailable.  This is most likely a
   * transient condition, which can be corrected by retrying with
   * a backoff. Note that it is not always safe to retry
   * non-idempotent operations.
   *
   * See the guidelines above for deciding between `FAILED_PRECONDITION`,
   * `ABORTED`, and `UNAVAILABLE`.
   *
   * HTTP Mapping: 503 Service Unavailable
   * </pre>
   *
   * <code>UNAVAILABLE = 14;</code>
   */
  public static final int UNAVAILABLE_VALUE = 14;
  /**
   *
   *
   * <pre>
   * Unrecoverable data loss or corruption.
   *
   * HTTP Mapping: 500 Internal Server Error
   * </pre>
   *
   * <code>DATA_LOSS = 15;</code>
   */
  public static final int DATA_LOSS_VALUE = 15;

  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static Code valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static Code forNumber(int value) {
    switch (value) {
      case 0:
        return OK;
      case 1:
        return CANCELLED;
      case 2:
        return UNKNOWN;
      case 3:
        return INVALID_ARGUMENT;
      case 4:
        return DEADLINE_EXCEEDED;
      case 5:
        return NOT_FOUND;
      case 6:
        return ALREADY_EXISTS;
      case 7:
        return PERMISSION_DENIED;
      case 16:
        return UNAUTHENTICATED;
      case 8:
        return RESOURCE_EXHAUSTED;
      case 9:
        return FAILED_PRECONDITION;
      case 10:
        return ABORTED;
      case 11:
        return OUT_OF_RANGE;
      case 12:
        return UNIMPLEMENTED;
      case 13:
        return INTERNAL;
      case 14:
        return UNAVAILABLE;
      case 15:
        return DATA_LOSS;
      default:
        return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<Code> internalGetValueMap() {
    return internalValueMap;
  }

  private static final com.google.protobuf.Internal.EnumLiteMap<Code> internalValueMap =
      new com.google.protobuf.Internal.EnumLiteMap<Code>() {
        public Code findValueByNumber(int number) {
          return Code.forNumber(number);
        }
      };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }

  public final com.google.protobuf.Descriptors.EnumDescriptor getDescriptorForType() {
    return getDescriptor();
  }

  public static final com.google.protobuf.Descriptors.EnumDescriptor getDescriptor() {
    return com.google.rpc.CodeProto.getDescriptor().getEnumTypes().get(0);
  }

  private static final Code[] VALUES = values();

  public static Code valueOf(com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException("EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private Code(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:google.rpc.Code)
}
