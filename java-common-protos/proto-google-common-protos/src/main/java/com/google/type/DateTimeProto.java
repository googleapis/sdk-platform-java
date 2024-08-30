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
// source: google/type/datetime.proto

// Protobuf Java Version: 3.25.4
package com.google.type;

public final class DateTimeProto {
  private DateTimeProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_type_DateTime_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_type_DateTime_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_type_TimeZone_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_type_TimeZone_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\032google/type/datetime.proto\022\013google.typ"
          + "e\032\036google/protobuf/duration.proto\"\340\001\n\010Da"
          + "teTime\022\014\n\004year\030\001 \001(\005\022\r\n\005month\030\002 \001(\005\022\013\n\003d"
          + "ay\030\003 \001(\005\022\r\n\005hours\030\004 \001(\005\022\017\n\007minutes\030\005 \001(\005"
          + "\022\017\n\007seconds\030\006 \001(\005\022\r\n\005nanos\030\007 \001(\005\022/\n\nutc_"
          + "offset\030\010 \001(\0132\031.google.protobuf.DurationH"
          + "\000\022*\n\ttime_zone\030\t \001(\0132\025.google.type.TimeZ"
          + "oneH\000B\r\n\013time_offset\"\'\n\010TimeZone\022\n\n\002id\030\001"
          + " \001(\t\022\017\n\007version\030\002 \001(\tBi\n\017com.google.type"
          + "B\rDateTimeProtoP\001Z<google.golang.org/gen"
          + "proto/googleapis/type/datetime;datetime\370"
          + "\001\001\242\002\003GTPb\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.protobuf.DurationProto.getDescriptor(),
            });
    internal_static_google_type_DateTime_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_google_type_DateTime_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_type_DateTime_descriptor,
            new java.lang.String[] {
              "Year",
              "Month",
              "Day",
              "Hours",
              "Minutes",
              "Seconds",
              "Nanos",
              "UtcOffset",
              "TimeZone",
              "TimeOffset",
            });
    internal_static_google_type_TimeZone_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_google_type_TimeZone_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_type_TimeZone_descriptor,
            new java.lang.String[] {
              "Id", "Version",
            });
    com.google.protobuf.DurationProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
