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
// source: google/geo/type/viewport.proto

// Protobuf Java Version: 3.25.4
package com.google.geo.type;

public final class ViewportProto {
  private ViewportProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_geo_type_Viewport_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_geo_type_Viewport_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\036google/geo/type/viewport.proto\022\017google"
          + ".geo.type\032\030google/type/latlng.proto\"O\n\010V"
          + "iewport\022 \n\003low\030\001 \001(\0132\023.google.type.LatLn"
          + "g\022!\n\004high\030\002 \001(\0132\023.google.type.LatLngBo\n\023"
          + "com.google.geo.typeB\rViewportProtoP\001Z@go"
          + "ogle.golang.org/genproto/googleapis/geo/"
          + "type/viewport;viewport\242\002\004GGTPb\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.type.LatLngProto.getDescriptor(),
            });
    internal_static_google_geo_type_Viewport_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_google_geo_type_Viewport_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_geo_type_Viewport_descriptor,
            new java.lang.String[] {
              "Low", "High",
            });
    com.google.type.LatLngProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
