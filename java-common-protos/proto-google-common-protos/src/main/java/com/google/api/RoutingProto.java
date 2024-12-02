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
// source: google/api/routing.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

public final class RoutingProto {
  private RoutingProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {
    registry.add(com.google.api.RoutingProto.routing);
  }

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public static final int ROUTING_FIELD_NUMBER = 72295729;

  /**
   *
   *
   * <pre>
   * See RoutingRule.
   * </pre>
   *
   * <code>extend .google.protobuf.MethodOptions { ... }</code>
   */
  public static final com.google.protobuf.GeneratedMessage.GeneratedExtension<
          com.google.protobuf.DescriptorProtos.MethodOptions, com.google.api.RoutingRule>
      routing =
          com.google.protobuf.GeneratedMessage.newFileScopedGeneratedExtension(
              com.google.api.RoutingRule.class, com.google.api.RoutingRule.getDefaultInstance());

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_RoutingRule_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_RoutingRule_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_RoutingParameter_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_RoutingParameter_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\030google/api/routing.proto\022\ngoogle.api\032 "
          + "google/protobuf/descriptor.proto\"G\n\013Rout"
          + "ingRule\0228\n\022routing_parameters\030\002 \003(\0132\034.go"
          + "ogle.api.RoutingParameter\"8\n\020RoutingPara"
          + "meter\022\r\n\005field\030\001 \001(\t\022\025\n\rpath_template\030\002 "
          + "\001(\t:K\n\007routing\022\036.google.protobuf.MethodO"
          + "ptions\030\261\312\274\" \001(\0132\027.google.api.RoutingRule"
          + "Bj\n\016com.google.apiB\014RoutingProtoP\001ZAgoog"
          + "le.golang.org/genproto/googleapis/api/an"
          + "notations;annotations\242\002\004GAPIb\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.protobuf.DescriptorProtos.getDescriptor(),
            });
    internal_static_google_api_RoutingRule_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_google_api_RoutingRule_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_RoutingRule_descriptor,
            new java.lang.String[] {
              "RoutingParameters",
            });
    internal_static_google_api_RoutingParameter_descriptor =
        getDescriptor().getMessageTypes().get(1);
    internal_static_google_api_RoutingParameter_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_RoutingParameter_descriptor,
            new java.lang.String[] {
              "Field", "PathTemplate",
            });
    routing.internalInit(descriptor.getExtensions().get(0));
    com.google.protobuf.DescriptorProtos.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
