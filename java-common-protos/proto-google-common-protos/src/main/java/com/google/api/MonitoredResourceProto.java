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
// source: google/api/monitored_resource.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

public final class MonitoredResourceProto {
  private MonitoredResourceProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_MonitoredResourceDescriptor_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_MonitoredResourceDescriptor_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_MonitoredResource_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_MonitoredResource_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_MonitoredResource_LabelsEntry_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_MonitoredResource_LabelsEntry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_MonitoredResourceMetadata_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_MonitoredResourceMetadata_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_MonitoredResourceMetadata_UserLabelsEntry_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_MonitoredResourceMetadata_UserLabelsEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n"
          + "#google/api/monitored_resource.proto\022\n"
          + "google.api\032\026google/api/label.proto\032\035googl"
          + "e/api/launch_stage.proto\032\034google/protobuf/struct.proto\"\300\001\n"
          + "\033MonitoredResourceDescriptor\022\014\n"
          + "\004name\030\005 \001(\t\022\014\n"
          + "\004type\030\001 \001(\t\022\024\n"
          + "\014display_name\030\002 \001(\t\022\023\n"
          + "\013description\030\003 \001(\t\022+\n"
          + "\006labels\030\004 \003(\0132\033.google.api.LabelDescriptor\022-\n"
          + "\014launch_stage\030\007 \001(\0162\027.google.api.LaunchStage\"\213\001\n"
          + "\021MonitoredResource\022\014\n"
          + "\004type\030\001 \001(\t\0229\n"
          + "\006labels\030\002 \003(\0132).google.api.MonitoredResource.LabelsEntry\032-\n"
          + "\013LabelsEntry\022\013\n"
          + "\003key\030\001 \001(\t\022\r\n"
          + "\005value\030\002 \001(\t:\0028\001\"\312\001\n"
          + "\031MonitoredResourceMetadata\022.\n\r"
          + "system_labels\030\001 \001(\0132\027.google.protobuf.Struct\022J\n"
          + "\013user_labels\030\002"
          + " \003(\01325.google.api.MonitoredResourceMetadata.UserLabelsEntry\0321\n"
          + "\017UserLabelsEntry\022\013\n"
          + "\003key\030\001 \001(\t\022\r\n"
          + "\005value\030\002 \001(\t:\0028\001Bv\n"
          + "\016com.google.apiB\026MonitoredResourceProtoP\001"
          + "ZCgoogle.golang.org/genproto/googleapis/"
          + "api/monitoredres;monitoredres\242\002\004GAPIb\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.api.LabelProto.getDescriptor(),
              com.google.api.LaunchStageProto.getDescriptor(),
              com.google.protobuf.StructProto.getDescriptor(),
            });
    internal_static_google_api_MonitoredResourceDescriptor_descriptor =
        getDescriptor().getMessageTypes().get(0);
    internal_static_google_api_MonitoredResourceDescriptor_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_MonitoredResourceDescriptor_descriptor,
            new java.lang.String[] {
              "Name", "Type", "DisplayName", "Description", "Labels", "LaunchStage",
            });
    internal_static_google_api_MonitoredResource_descriptor =
        getDescriptor().getMessageTypes().get(1);
    internal_static_google_api_MonitoredResource_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_MonitoredResource_descriptor,
            new java.lang.String[] {
              "Type", "Labels",
            });
    internal_static_google_api_MonitoredResource_LabelsEntry_descriptor =
        internal_static_google_api_MonitoredResource_descriptor.getNestedTypes().get(0);
    internal_static_google_api_MonitoredResource_LabelsEntry_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_MonitoredResource_LabelsEntry_descriptor,
            new java.lang.String[] {
              "Key", "Value",
            });
    internal_static_google_api_MonitoredResourceMetadata_descriptor =
        getDescriptor().getMessageTypes().get(2);
    internal_static_google_api_MonitoredResourceMetadata_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_MonitoredResourceMetadata_descriptor,
            new java.lang.String[] {
              "SystemLabels", "UserLabels",
            });
    internal_static_google_api_MonitoredResourceMetadata_UserLabelsEntry_descriptor =
        internal_static_google_api_MonitoredResourceMetadata_descriptor.getNestedTypes().get(0);
    internal_static_google_api_MonitoredResourceMetadata_UserLabelsEntry_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_MonitoredResourceMetadata_UserLabelsEntry_descriptor,
            new java.lang.String[] {
              "Key", "Value",
            });
    com.google.api.LabelProto.getDescriptor();
    com.google.api.LaunchStageProto.getDescriptor();
    com.google.protobuf.StructProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
