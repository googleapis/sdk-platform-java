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
// source: google/rpc/context/audit_context.proto

// Protobuf Java Version: 3.25.5
package com.google.rpc.context;

public final class AuditContextProto {
  private AuditContextProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_rpc_context_AuditContext_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_rpc_context_AuditContext_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n&google/rpc/context/audit_context.proto"
          + "\022\022google.rpc.context\032\034google/protobuf/st"
          + "ruct.proto\"\307\001\n\014AuditContext\022\021\n\taudit_log"
          + "\030\001 \001(\014\0221\n\020scrubbed_request\030\002 \001(\0132\027.googl"
          + "e.protobuf.Struct\0222\n\021scrubbed_response\030\003"
          + " \001(\0132\027.google.protobuf.Struct\022$\n\034scrubbe"
          + "d_response_item_count\030\004 \001(\005\022\027\n\017target_re"
          + "source\030\005 \001(\tBh\n\026com.google.rpc.contextB\021"
          + "AuditContextProtoP\001Z9google.golang.org/g"
          + "enproto/googleapis/rpc/context;contextb\006"
          + "proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.protobuf.StructProto.getDescriptor(),
            });
    internal_static_google_rpc_context_AuditContext_descriptor =
        getDescriptor().getMessageTypes().get(0);
    internal_static_google_rpc_context_AuditContext_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_rpc_context_AuditContext_descriptor,
            new java.lang.String[] {
              "AuditLog",
              "ScrubbedRequest",
              "ScrubbedResponse",
              "ScrubbedResponseItemCount",
              "TargetResource",
            });
    com.google.protobuf.StructProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
