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
// source: schema/google/showcase/v1beta1/identity.proto

// Protobuf Java Version: 3.25.5
package com.google.showcase.v1beta1;

public final class IdentityOuterClass {
  private IdentityOuterClass() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_showcase_v1beta1_User_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_User_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_showcase_v1beta1_CreateUserRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_CreateUserRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_showcase_v1beta1_GetUserRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_GetUserRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_showcase_v1beta1_UpdateUserRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_UpdateUserRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_showcase_v1beta1_DeleteUserRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_DeleteUserRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_showcase_v1beta1_ListUsersRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_ListUsersRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_showcase_v1beta1_ListUsersResponse_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_showcase_v1beta1_ListUsersResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n-schema/google/showcase/v1beta1/identit"
          + "y.proto\022\027google.showcase.v1beta1\032\034google"
          + "/api/annotations.proto\032\027google/api/clien"
          + "t.proto\032\037google/api/field_behavior.proto"
          + "\032\031google/api/resource.proto\032\033google/prot"
          + "obuf/empty.proto\032 google/protobuf/field_"
          + "mask.proto\032\037google/protobuf/timestamp.pr"
          + "oto\"\204\003\n\004User\022\014\n\004name\030\001 \001(\t\022\031\n\014display_na"
          + "me\030\002 \001(\tB\003\340A\002\022\022\n\005email\030\003 \001(\tB\003\340A\002\0224\n\013cre"
          + "ate_time\030\004 \001(\0132\032.google.protobuf.Timesta"
          + "mpB\003\340A\003\0224\n\013update_time\030\005 \001(\0132\032.google.pr"
          + "otobuf.TimestampB\003\340A\003\022\020\n\003age\030\006 \001(\005H\000\210\001\001\022"
          + "\030\n\013height_feet\030\007 \001(\001H\001\210\001\001\022\025\n\010nickname\030\010 "
          + "\001(\tH\002\210\001\001\022!\n\024enable_notifications\030\t \001(\010H\003"
          + "\210\001\001:/\352A,\n\034showcase.googleapis.com/User\022\014"
          + "users/{user}B\006\n\004_ageB\016\n\014_height_feetB\013\n\t"
          + "_nicknameB\027\n\025_enable_notifications\"@\n\021Cr"
          + "eateUserRequest\022+\n\004user\030\001 \001(\0132\035.google.s"
          + "howcase.v1beta1.User\"D\n\016GetUserRequest\0222"
          + "\n\004name\030\001 \001(\tB$\340A\002\372A\036\n\034showcase.googleapi"
          + "s.com/User\"q\n\021UpdateUserRequest\022+\n\004user\030"
          + "\001 \001(\0132\035.google.showcase.v1beta1.User\022/\n\013"
          + "update_mask\030\002 \001(\0132\032.google.protobuf.Fiel"
          + "dMask\"G\n\021DeleteUserRequest\0222\n\004name\030\001 \001(\t"
          + "B$\340A\002\372A\036\n\034showcase.googleapis.com/User\"9"
          + "\n\020ListUsersRequest\022\021\n\tpage_size\030\001 \001(\005\022\022\n"
          + "\npage_token\030\002 \001(\t\"Z\n\021ListUsersResponse\022,"
          + "\n\005users\030\001 \003(\0132\035.google.showcase.v1beta1."
          + "User\022\027\n\017next_page_token\030\002 \001(\t2\212\006\n\010Identi"
          + "ty\022\363\001\n\nCreateUser\022*.google.showcase.v1be"
          + "ta1.CreateUserRequest\032\035.google.showcase."
          + "v1beta1.User\"\231\001\332A\034user.display_name,user"
          + ".email\332A^user.display_name,user.email,us"
          + "er.age,user.nickname,user.enable_notific"
          + "ations,user.height_feet\202\323\344\223\002\023\"\016/v1beta1/"
          + "users:\001*\022y\n\007GetUser\022\'.google.showcase.v1"
          + "beta1.GetUserRequest\032\035.google.showcase.v"
          + "1beta1.User\"&\332A\004name\202\323\344\223\002\031\022\027/v1beta1/{na"
          + "me=users/*}\022\203\001\n\nUpdateUser\022*.google.show"
          + "case.v1beta1.UpdateUserRequest\032\035.google."
          + "showcase.v1beta1.User\"*\202\323\344\223\002$2\034/v1beta1/"
          + "{user.name=users/*}:\004user\022x\n\nDeleteUser\022"
          + "*.google.showcase.v1beta1.DeleteUserRequ"
          + "est\032\026.google.protobuf.Empty\"&\332A\004name\202\323\344\223"
          + "\002\031*\027/v1beta1/{name=users/*}\022z\n\tListUsers"
          + "\022).google.showcase.v1beta1.ListUsersRequ"
          + "est\032*.google.showcase.v1beta1.ListUsersR"
          + "esponse\"\026\202\323\344\223\002\020\022\016/v1beta1/users\032\021\312A\016loca"
          + "lhost:7469Bq\n\033com.google.showcase.v1beta"
          + "1P\001Z4github.com/googleapis/gapic-showcas"
          + "e/server/genproto\352\002\031Google::Showcase::V1"
          + "beta1b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.api.AnnotationsProto.getDescriptor(),
              com.google.api.ClientProto.getDescriptor(),
              com.google.api.FieldBehaviorProto.getDescriptor(),
              com.google.api.ResourceProto.getDescriptor(),
              com.google.protobuf.EmptyProto.getDescriptor(),
              com.google.protobuf.FieldMaskProto.getDescriptor(),
              com.google.protobuf.TimestampProto.getDescriptor(),
            });
    internal_static_google_showcase_v1beta1_User_descriptor =
        getDescriptor().getMessageTypes().get(0);
    internal_static_google_showcase_v1beta1_User_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_showcase_v1beta1_User_descriptor,
            new java.lang.String[] {
              "Name",
              "DisplayName",
              "Email",
              "CreateTime",
              "UpdateTime",
              "Age",
              "HeightFeet",
              "Nickname",
              "EnableNotifications",
            });
    internal_static_google_showcase_v1beta1_CreateUserRequest_descriptor =
        getDescriptor().getMessageTypes().get(1);
    internal_static_google_showcase_v1beta1_CreateUserRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_showcase_v1beta1_CreateUserRequest_descriptor,
            new java.lang.String[] {
              "User",
            });
    internal_static_google_showcase_v1beta1_GetUserRequest_descriptor =
        getDescriptor().getMessageTypes().get(2);
    internal_static_google_showcase_v1beta1_GetUserRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_showcase_v1beta1_GetUserRequest_descriptor,
            new java.lang.String[] {
              "Name",
            });
    internal_static_google_showcase_v1beta1_UpdateUserRequest_descriptor =
        getDescriptor().getMessageTypes().get(3);
    internal_static_google_showcase_v1beta1_UpdateUserRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_showcase_v1beta1_UpdateUserRequest_descriptor,
            new java.lang.String[] {
              "User", "UpdateMask",
            });
    internal_static_google_showcase_v1beta1_DeleteUserRequest_descriptor =
        getDescriptor().getMessageTypes().get(4);
    internal_static_google_showcase_v1beta1_DeleteUserRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_showcase_v1beta1_DeleteUserRequest_descriptor,
            new java.lang.String[] {
              "Name",
            });
    internal_static_google_showcase_v1beta1_ListUsersRequest_descriptor =
        getDescriptor().getMessageTypes().get(5);
    internal_static_google_showcase_v1beta1_ListUsersRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_showcase_v1beta1_ListUsersRequest_descriptor,
            new java.lang.String[] {
              "PageSize", "PageToken",
            });
    internal_static_google_showcase_v1beta1_ListUsersResponse_descriptor =
        getDescriptor().getMessageTypes().get(6);
    internal_static_google_showcase_v1beta1_ListUsersResponse_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_showcase_v1beta1_ListUsersResponse_descriptor,
            new java.lang.String[] {
              "Users", "NextPageToken",
            });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.google.api.ClientProto.defaultHost);
    registry.add(com.google.api.FieldBehaviorProto.fieldBehavior);
    registry.add(com.google.api.AnnotationsProto.http);
    registry.add(com.google.api.ClientProto.methodSignature);
    registry.add(com.google.api.ResourceProto.resource);
    registry.add(com.google.api.ResourceProto.resourceReference);
    com.google.protobuf.Descriptors.FileDescriptor.internalUpdateFileDescriptor(
        descriptor, registry);
    com.google.api.AnnotationsProto.getDescriptor();
    com.google.api.ClientProto.getDescriptor();
    com.google.api.FieldBehaviorProto.getDescriptor();
    com.google.api.ResourceProto.getDescriptor();
    com.google.protobuf.EmptyProto.getDescriptor();
    com.google.protobuf.FieldMaskProto.getDescriptor();
    com.google.protobuf.TimestampProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
