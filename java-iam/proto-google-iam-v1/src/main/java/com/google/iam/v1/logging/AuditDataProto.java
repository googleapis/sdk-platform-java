/*
 * Copyright 2024 Google LLC
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
// source: google/iam/v1/logging/audit_data.proto

// Protobuf Java Version: 3.25.5
package com.google.iam.v1.logging;

public final class AuditDataProto {
  private AuditDataProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v1_logging_AuditData_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v1_logging_AuditData_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n&google/iam/v1/logging/audit_data.proto"
          + "\022\025google.iam.v1.logging\032\032google/iam/v1/p"
          + "olicy.proto\"=\n\tAuditData\0220\n\014policy_delta"
          + "\030\002 \001(\0132\032.google.iam.v1.PolicyDeltaB\206\001\n\031c"
          + "om.google.iam.v1.loggingB\016AuditDataProto"
          + "P\001Z9cloud.google.com/go/iam/apiv1/loggin"
          + "g/loggingpb;loggingpb\252\002\033Google.Cloud.Iam"
          + ".V1.Loggingb\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.iam.v1.PolicyProto.getDescriptor(),
            });
    internal_static_google_iam_v1_logging_AuditData_descriptor =
        getDescriptor().getMessageTypes().get(0);
    internal_static_google_iam_v1_logging_AuditData_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v1_logging_AuditData_descriptor,
            new java.lang.String[] {
              "PolicyDelta",
            });
    com.google.iam.v1.PolicyProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
