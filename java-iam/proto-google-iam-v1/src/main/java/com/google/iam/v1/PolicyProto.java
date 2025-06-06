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
// source: google/iam/v1/policy.proto

// Protobuf Java Version: 3.25.8
package com.google.iam.v1;

public final class PolicyProto {
  private PolicyProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v1_Policy_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v1_Policy_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v1_Binding_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v1_Binding_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v1_AuditConfig_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v1_AuditConfig_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v1_AuditLogConfig_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v1_AuditLogConfig_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v1_PolicyDelta_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v1_PolicyDelta_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v1_BindingDelta_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v1_BindingDelta_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v1_AuditConfigDelta_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v1_AuditConfigDelta_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\032google/iam/v1/policy.proto\022\rgoogle.iam"
          + ".v1\032\026google/type/expr.proto\"\204\001\n\006Policy\022\017"
          + "\n\007version\030\001 \001(\005\022(\n\010bindings\030\004 \003(\0132\026.goog"
          + "le.iam.v1.Binding\0221\n\raudit_configs\030\006 \003(\013"
          + "2\032.google.iam.v1.AuditConfig\022\014\n\004etag\030\003 \001"
          + "(\014\"N\n\007Binding\022\014\n\004role\030\001 \001(\t\022\017\n\007members\030\002"
          + " \003(\t\022$\n\tcondition\030\003 \001(\0132\021.google.type.Ex"
          + "pr\"X\n\013AuditConfig\022\017\n\007service\030\001 \001(\t\0228\n\021au"
          + "dit_log_configs\030\003 \003(\0132\035.google.iam.v1.Au"
          + "ditLogConfig\"\267\001\n\016AuditLogConfig\0227\n\010log_t"
          + "ype\030\001 \001(\0162%.google.iam.v1.AuditLogConfig"
          + ".LogType\022\030\n\020exempted_members\030\002 \003(\t\"R\n\007Lo"
          + "gType\022\030\n\024LOG_TYPE_UNSPECIFIED\020\000\022\016\n\nADMIN"
          + "_READ\020\001\022\016\n\nDATA_WRITE\020\002\022\r\n\tDATA_READ\020\003\"\200"
          + "\001\n\013PolicyDelta\0223\n\016binding_deltas\030\001 \003(\0132\033"
          + ".google.iam.v1.BindingDelta\022<\n\023audit_con"
          + "fig_deltas\030\002 \003(\0132\037.google.iam.v1.AuditCo"
          + "nfigDelta\"\275\001\n\014BindingDelta\0222\n\006action\030\001 \001"
          + "(\0162\".google.iam.v1.BindingDelta.Action\022\014"
          + "\n\004role\030\002 \001(\t\022\016\n\006member\030\003 \001(\t\022$\n\tconditio"
          + "n\030\004 \001(\0132\021.google.type.Expr\"5\n\006Action\022\026\n\022"
          + "ACTION_UNSPECIFIED\020\000\022\007\n\003ADD\020\001\022\n\n\006REMOVE\020"
          + "\002\"\275\001\n\020AuditConfigDelta\0226\n\006action\030\001 \001(\0162&"
          + ".google.iam.v1.AuditConfigDelta.Action\022\017"
          + "\n\007service\030\002 \001(\t\022\027\n\017exempted_member\030\003 \001(\t"
          + "\022\020\n\010log_type\030\004 \001(\t\"5\n\006Action\022\026\n\022ACTION_U"
          + "NSPECIFIED\020\000\022\007\n\003ADD\020\001\022\n\n\006REMOVE\020\002B|\n\021com"
          + ".google.iam.v1B\013PolicyProtoP\001Z)cloud.goo"
          + "gle.com/go/iam/apiv1/iampb;iampb\370\001\001\252\002\023Go"
          + "ogle.Cloud.Iam.V1\312\002\023Google\\Cloud\\Iam\\V1b"
          + "\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.type.ExprProto.getDescriptor(),
            });
    internal_static_google_iam_v1_Policy_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_google_iam_v1_Policy_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v1_Policy_descriptor,
            new java.lang.String[] {
              "Version", "Bindings", "AuditConfigs", "Etag",
            });
    internal_static_google_iam_v1_Binding_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_google_iam_v1_Binding_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v1_Binding_descriptor,
            new java.lang.String[] {
              "Role", "Members", "Condition",
            });
    internal_static_google_iam_v1_AuditConfig_descriptor = getDescriptor().getMessageTypes().get(2);
    internal_static_google_iam_v1_AuditConfig_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v1_AuditConfig_descriptor,
            new java.lang.String[] {
              "Service", "AuditLogConfigs",
            });
    internal_static_google_iam_v1_AuditLogConfig_descriptor =
        getDescriptor().getMessageTypes().get(3);
    internal_static_google_iam_v1_AuditLogConfig_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v1_AuditLogConfig_descriptor,
            new java.lang.String[] {
              "LogType", "ExemptedMembers",
            });
    internal_static_google_iam_v1_PolicyDelta_descriptor = getDescriptor().getMessageTypes().get(4);
    internal_static_google_iam_v1_PolicyDelta_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v1_PolicyDelta_descriptor,
            new java.lang.String[] {
              "BindingDeltas", "AuditConfigDeltas",
            });
    internal_static_google_iam_v1_BindingDelta_descriptor =
        getDescriptor().getMessageTypes().get(5);
    internal_static_google_iam_v1_BindingDelta_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v1_BindingDelta_descriptor,
            new java.lang.String[] {
              "Action", "Role", "Member", "Condition",
            });
    internal_static_google_iam_v1_AuditConfigDelta_descriptor =
        getDescriptor().getMessageTypes().get(6);
    internal_static_google_iam_v1_AuditConfigDelta_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v1_AuditConfigDelta_descriptor,
            new java.lang.String[] {
              "Action", "Service", "ExemptedMember", "LogType",
            });
    com.google.type.ExprProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
