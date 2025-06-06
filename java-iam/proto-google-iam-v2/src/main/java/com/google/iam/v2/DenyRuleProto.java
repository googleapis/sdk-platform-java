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
// source: google/iam/v2/deny.proto

// Protobuf Java Version: 3.25.8
package com.google.iam.v2;

public final class DenyRuleProto {
  private DenyRuleProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v2_DenyRule_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2_DenyRule_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\030google/iam/v2/deny.proto\022\rgoogle.iam.v"
          + "2\032\026google/type/expr.proto\"\253\001\n\010DenyRule\022\031"
          + "\n\021denied_principals\030\001 \003(\t\022\034\n\024exception_p"
          + "rincipals\030\002 \003(\t\022\032\n\022denied_permissions\030\003 "
          + "\003(\t\022\035\n\025exception_permissions\030\004 \003(\t\022+\n\020de"
          + "nial_condition\030\005 \001(\0132\021.google.type.ExprB"
          + "{\n\021com.google.iam.v2B\rDenyRuleProtoP\001Z)c"
          + "loud.google.com/go/iam/apiv2/iampb;iampb"
          + "\252\002\023Google.Cloud.Iam.V2\312\002\023Google\\Cloud\\Ia"
          + "m\\V2b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.type.ExprProto.getDescriptor(),
            });
    internal_static_google_iam_v2_DenyRule_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_google_iam_v2_DenyRule_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v2_DenyRule_descriptor,
            new java.lang.String[] {
              "DeniedPrincipals",
              "ExceptionPrincipals",
              "DeniedPermissions",
              "ExceptionPermissions",
              "DenialCondition",
            });
    com.google.type.ExprProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
