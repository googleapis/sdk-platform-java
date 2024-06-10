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
// NO CHECKED-IN PROTOBUF GENCODE
// source: google/type/decimal.proto
// Protobuf Java Version: 4.27.1

package com.google.type;

public final class DecimalProto {
  private DecimalProto() {}

  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 27,
        /* patch= */ 1,
        /* suffix= */ "",
        DecimalProto.class.getName());
  }

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_type_Decimal_descriptor;
  static final com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_google_type_Decimal_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\031google/type/decimal.proto\022\013google.type"
          + "\"\030\n\007Decimal\022\r\n\005value\030\001 \001(\tBf\n\017com.google"
          + ".typeB\014DecimalProtoP\001Z:google.golang.org"
          + "/genproto/googleapis/type/decimal;decima"
          + "l\370\001\001\242\002\003GTPb\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData, new com.google.protobuf.Descriptors.FileDescriptor[] {});
    internal_static_google_type_Decimal_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_google_type_Decimal_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessage.FieldAccessorTable(
            internal_static_google_type_Decimal_descriptor,
            new java.lang.String[] {
              "Value",
            });
    descriptor.resolveAllFeaturesImmutable();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
