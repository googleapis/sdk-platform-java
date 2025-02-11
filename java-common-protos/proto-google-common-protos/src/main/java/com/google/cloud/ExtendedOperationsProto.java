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

public final class ExtendedOperationsProto {
  private ExtendedOperationsProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {
    registry.add(com.google.cloud.ExtendedOperationsProto.operationField);
    registry.add(com.google.cloud.ExtendedOperationsProto.operationRequestField);
    registry.add(com.google.cloud.ExtendedOperationsProto.operationResponseField);
    registry.add(com.google.cloud.ExtendedOperationsProto.operationService);
    registry.add(com.google.cloud.ExtendedOperationsProto.operationPollingMethod);
  }

  public static final int OPERATION_FIELD_FIELD_NUMBER = 1149;
  /**
   *
   *
   * <pre>
   * A field annotation that maps fields in an API-specific Operation object to
   * their standard counterparts in google.longrunning.Operation. See
   * OperationResponseMapping enum definition.
   * </pre>
   *
   * <code>extend .google.protobuf.FieldOptions { ... }</code>
   */
  public static final com.google.protobuf.GeneratedMessageLite.GeneratedExtension<
          com.google.protobuf.DescriptorProtos.FieldOptions,
          com.google.cloud.OperationResponseMapping>
      operationField =
          com.google.protobuf.GeneratedMessageLite.newSingularGeneratedExtension(
              com.google.protobuf.DescriptorProtos.FieldOptions.getDefaultInstance(),
              com.google.cloud.OperationResponseMapping.UNDEFINED,
              null,
              com.google.cloud.OperationResponseMapping.internalGetValueMap(),
              1149,
              com.google.protobuf.WireFormat.FieldType.ENUM,
              com.google.cloud.OperationResponseMapping.class);

  public static final int OPERATION_REQUEST_FIELD_FIELD_NUMBER = 1150;
  /**
   *
   *
   * <pre>
   * A field annotation that maps fields in the initial request message
   * (the one which started the LRO) to their counterparts in the polling
   * request message. For non-standard LRO, the polling response may be missing
   * some of the information needed to make a subsequent polling request. The
   * missing information (for example, project or region ID) is contained in the
   * fields of the initial request message that this annotation must be applied
   * to. The string value of the annotation corresponds to the name of the
   * counterpart field in the polling request message that the annotated field's
   * value will be copied to.
   * </pre>
   *
   * <code>extend .google.protobuf.FieldOptions { ... }</code>
   */
  public static final com.google.protobuf.GeneratedMessageLite.GeneratedExtension<
          com.google.protobuf.DescriptorProtos.FieldOptions, java.lang.String>
      operationRequestField =
          com.google.protobuf.GeneratedMessageLite.newSingularGeneratedExtension(
              com.google.protobuf.DescriptorProtos.FieldOptions.getDefaultInstance(),
              "",
              null,
              null,
              1150,
              com.google.protobuf.WireFormat.FieldType.STRING,
              java.lang.String.class);

  public static final int OPERATION_RESPONSE_FIELD_FIELD_NUMBER = 1151;
  /**
   *
   *
   * <pre>
   * A field annotation that maps fields in the polling request message to their
   * counterparts in the initial and/or polling response message. The initial
   * and the polling methods return an API-specific Operation object. Some of
   * the fields from that response object must be reused in the subsequent
   * request (like operation name/ID) to fully identify the polled operation.
   * This annotation must be applied to the fields in the polling request
   * message, the string value of the annotation must correspond to the name of
   * the counterpart field in the Operation response object whose value will be
   * copied to the annotated field.
   * </pre>
   *
   * <code>extend .google.protobuf.FieldOptions { ... }</code>
   */
  public static final com.google.protobuf.GeneratedMessageLite.GeneratedExtension<
          com.google.protobuf.DescriptorProtos.FieldOptions, java.lang.String>
      operationResponseField =
          com.google.protobuf.GeneratedMessageLite.newSingularGeneratedExtension(
              com.google.protobuf.DescriptorProtos.FieldOptions.getDefaultInstance(),
              "",
              null,
              null,
              1151,
              com.google.protobuf.WireFormat.FieldType.STRING,
              java.lang.String.class);

  public static final int OPERATION_SERVICE_FIELD_NUMBER = 1249;
  /**
   *
   *
   * <pre>
   * A method annotation that maps an LRO method (the one which starts an LRO)
   * to the service, which will be used to poll for the operation status. The
   * annotation must be applied to the method which starts an LRO, the string
   * value of the annotation must correspond to the name of the service used to
   * poll for the operation status.
   * </pre>
   *
   * <code>extend .google.protobuf.MethodOptions { ... }</code>
   */
  public static final com.google.protobuf.GeneratedMessageLite.GeneratedExtension<
          com.google.protobuf.DescriptorProtos.MethodOptions, java.lang.String>
      operationService =
          com.google.protobuf.GeneratedMessageLite.newSingularGeneratedExtension(
              com.google.protobuf.DescriptorProtos.MethodOptions.getDefaultInstance(),
              "",
              null,
              null,
              1249,
              com.google.protobuf.WireFormat.FieldType.STRING,
              java.lang.String.class);

  public static final int OPERATION_POLLING_METHOD_FIELD_NUMBER = 1250;
  /**
   *
   *
   * <pre>
   * A method annotation that marks methods that can be used for polling
   * operation status (e.g. the MyPollingService.Get(MyPollingRequest) method).
   * </pre>
   *
   * <code>extend .google.protobuf.MethodOptions { ... }</code>
   */
  public static final com.google.protobuf.GeneratedMessageLite.GeneratedExtension<
          com.google.protobuf.DescriptorProtos.MethodOptions, java.lang.Boolean>
      operationPollingMethod =
          com.google.protobuf.GeneratedMessageLite.newSingularGeneratedExtension(
              com.google.protobuf.DescriptorProtos.MethodOptions.getDefaultInstance(),
              false,
              null,
              null,
              1250,
              com.google.protobuf.WireFormat.FieldType.BOOL,
              java.lang.Boolean.class);

  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}
