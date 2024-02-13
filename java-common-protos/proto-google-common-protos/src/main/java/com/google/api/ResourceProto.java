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
// source: google/api/resource.proto

// Protobuf Java Version: 3.25.2
package com.google.api;

public final class ResourceProto {
  private ResourceProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {
    registry.add(com.google.api.ResourceProto.resourceReference);
    registry.add(com.google.api.ResourceProto.resourceDefinition);
    registry.add(com.google.api.ResourceProto.resource);
  }

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public static final int RESOURCE_REFERENCE_FIELD_NUMBER = 1055;
  /**
   *
   *
   * <pre>
   * An annotation that describes a resource reference, see
   * [ResourceReference][].
   * </pre>
   *
   * <code>extend .google.protobuf.FieldOptions { ... }</code>
   */
  public static final com.google.protobuf.GeneratedMessage.GeneratedExtension<
          com.google.protobuf.DescriptorProtos.FieldOptions, com.google.api.ResourceReference>
      resourceReference =
          com.google.protobuf.GeneratedMessage.newFileScopedGeneratedExtension(
              com.google.api.ResourceReference.class,
              com.google.api.ResourceReference.getDefaultInstance());

  public static final int RESOURCE_DEFINITION_FIELD_NUMBER = 1053;
  /**
   *
   *
   * <pre>
   * An annotation that describes a resource definition without a corresponding
   * message; see [ResourceDescriptor][].
   * </pre>
   *
   * <code>extend .google.protobuf.FileOptions { ... }</code>
   */
  public static final com.google.protobuf.GeneratedMessage.GeneratedExtension<
          com.google.protobuf.DescriptorProtos.FileOptions,
          java.util.List<com.google.api.ResourceDescriptor>>
      resourceDefinition =
          com.google.protobuf.GeneratedMessage.newFileScopedGeneratedExtension(
              com.google.api.ResourceDescriptor.class,
              com.google.api.ResourceDescriptor.getDefaultInstance());

  public static final int RESOURCE_FIELD_NUMBER = 1053;
  /**
   *
   *
   * <pre>
   * An annotation that describes a resource definition, see
   * [ResourceDescriptor][].
   * </pre>
   *
   * <code>extend .google.protobuf.MessageOptions { ... }</code>
   */
  public static final com.google.protobuf.GeneratedMessage.GeneratedExtension<
          com.google.protobuf.DescriptorProtos.MessageOptions, com.google.api.ResourceDescriptor>
      resource =
          com.google.protobuf.GeneratedMessage.newFileScopedGeneratedExtension(
              com.google.api.ResourceDescriptor.class,
              com.google.api.ResourceDescriptor.getDefaultInstance());

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_ResourceDescriptor_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_ResourceDescriptor_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_ResourceReference_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_ResourceReference_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\031google/api/resource.proto\022\ngoogle.api\032"
          + " google/protobuf/descriptor.proto\"\356\002\n\022Re"
          + "sourceDescriptor\022\014\n\004type\030\001 \001(\t\022\017\n\007patter"
          + "n\030\002 \003(\t\022\022\n\nname_field\030\003 \001(\t\0227\n\007history\030\004"
          + " \001(\0162&.google.api.ResourceDescriptor.His"
          + "tory\022\016\n\006plural\030\005 \001(\t\022\020\n\010singular\030\006 \001(\t\0223"
          + "\n\005style\030\n \003(\0162$.google.api.ResourceDescr"
          + "iptor.Style\"[\n\007History\022\027\n\023HISTORY_UNSPEC"
          + "IFIED\020\000\022\035\n\031ORIGINALLY_SINGLE_PATTERN\020\001\022\030"
          + "\n\024FUTURE_MULTI_PATTERN\020\002\"8\n\005Style\022\025\n\021STY"
          + "LE_UNSPECIFIED\020\000\022\030\n\024DECLARATIVE_FRIENDLY"
          + "\020\001\"5\n\021ResourceReference\022\014\n\004type\030\001 \001(\t\022\022\n"
          + "\nchild_type\030\002 \001(\t:Y\n\022resource_reference\022"
          + "\035.google.protobuf.FieldOptions\030\237\010 \001(\0132\035."
          + "google.api.ResourceReference:Z\n\023resource"
          + "_definition\022\034.google.protobuf.FileOption"
          + "s\030\235\010 \003(\0132\036.google.api.ResourceDescriptor"
          + ":R\n\010resource\022\037.google.protobuf.MessageOp"
          + "tions\030\235\010 \001(\0132\036.google.api.ResourceDescri"
          + "ptorBn\n\016com.google.apiB\rResourceProtoP\001Z"
          + "Agoogle.golang.org/genproto/googleapis/a"
          + "pi/annotations;annotations\370\001\001\242\002\004GAPIb\006pr"
          + "oto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.protobuf.DescriptorProtos.getDescriptor(),
            });
    internal_static_google_api_ResourceDescriptor_descriptor =
        getDescriptor().getMessageTypes().get(0);
    internal_static_google_api_ResourceDescriptor_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_ResourceDescriptor_descriptor,
            new java.lang.String[] {
              "Type", "Pattern", "NameField", "History", "Plural", "Singular", "Style",
            });
    internal_static_google_api_ResourceReference_descriptor =
        getDescriptor().getMessageTypes().get(1);
    internal_static_google_api_ResourceReference_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_ResourceReference_descriptor,
            new java.lang.String[] {
              "Type", "ChildType",
            });
    resourceReference.internalInit(descriptor.getExtensions().get(0));
    resourceDefinition.internalInit(descriptor.getExtensions().get(1));
    resource.internalInit(descriptor.getExtensions().get(2));
    com.google.protobuf.DescriptorProtos.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
