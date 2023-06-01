/*
 * Copyright 2020 Google LLC
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
// source: google/api/backend.proto

package com.google.api;

public final class BackendProto {
  private BackendProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_Backend_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_Backend_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_BackendRule_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_BackendRule_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_BackendRule_OverridesByRequestProtocolEntry_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_BackendRule_OverridesByRequestProtocolEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\030google/api/backend.proto\022\ngoogle.api\"1"
          + "\n\007Backend\022&\n\005rules\030\001 \003(\0132\027.google.api.Ba"
          + "ckendRule\"\262\004\n\013BackendRule\022\020\n\010selector\030\001 "
          + "\001(\t\022\017\n\007address\030\002 \001(\t\022\020\n\010deadline\030\003 \001(\001\022\030"
          + "\n\014min_deadline\030\004 \001(\001B\002\030\001\022\032\n\022operation_de"
          + "adline\030\005 \001(\001\022A\n\020path_translation\030\006 \001(\0162\'"
          + ".google.api.BackendRule.PathTranslation\022"
          + "\026\n\014jwt_audience\030\007 \001(\tH\000\022\026\n\014disable_auth\030"
          + "\010 \001(\010H\000\022\020\n\010protocol\030\t \001(\t\022^\n\035overrides_b"
          + "y_request_protocol\030\n \003(\01327.google.api.Ba"
          + "ckendRule.OverridesByRequestProtocolEntr"
          + "y\032Z\n\037OverridesByRequestProtocolEntry\022\013\n\003"
          + "key\030\001 \001(\t\022&\n\005value\030\002 \001(\0132\027.google.api.Ba"
          + "ckendRule:\0028\001\"e\n\017PathTranslation\022 \n\034PATH"
          + "_TRANSLATION_UNSPECIFIED\020\000\022\024\n\020CONSTANT_A"
          + "DDRESS\020\001\022\032\n\026APPEND_PATH_TO_ADDRESS\020\002B\020\n\016"
          + "authenticationBn\n\016com.google.apiB\014Backen"
          + "dProtoP\001ZEgoogle.golang.org/genproto/goo"
          + "gleapis/api/serviceconfig;serviceconfig\242"
          + "\002\004GAPIb\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData, new com.google.protobuf.Descriptors.FileDescriptor[] {});
    internal_static_google_api_Backend_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_google_api_Backend_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_Backend_descriptor,
            new java.lang.String[] {
              "Rules",
            });
    internal_static_google_api_BackendRule_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_google_api_BackendRule_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_BackendRule_descriptor,
            new java.lang.String[] {
              "Selector",
              "Address",
              "Deadline",
              "MinDeadline",
              "OperationDeadline",
              "PathTranslation",
              "JwtAudience",
              "DisableAuth",
              "Protocol",
              "OverridesByRequestProtocol",
              "Authentication",
            });
    internal_static_google_api_BackendRule_OverridesByRequestProtocolEntry_descriptor =
        internal_static_google_api_BackendRule_descriptor.getNestedTypes().get(0);
    internal_static_google_api_BackendRule_OverridesByRequestProtocolEntry_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_BackendRule_OverridesByRequestProtocolEntry_descriptor,
            new java.lang.String[] {
              "Key", "Value",
            });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
