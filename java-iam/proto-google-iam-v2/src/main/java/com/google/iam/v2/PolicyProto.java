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
// source: google/iam/v2/policy.proto

// Protobuf Java Version: 3.25.5
package com.google.iam.v2;

public final class PolicyProto {
  private PolicyProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v2_Policy_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2_Policy_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v2_Policy_AnnotationsEntry_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2_Policy_AnnotationsEntry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v2_PolicyRule_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2_PolicyRule_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v2_ListPoliciesRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2_ListPoliciesRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v2_ListPoliciesResponse_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2_ListPoliciesResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v2_GetPolicyRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2_GetPolicyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v2_CreatePolicyRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2_CreatePolicyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v2_UpdatePolicyRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2_UpdatePolicyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v2_DeletePolicyRequest_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2_DeletePolicyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_iam_v2_PolicyOperationMetadata_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2_PolicyOperationMetadata_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n"
          + "\032google/iam/v2/policy.proto\022\r"
          + "google.iam.v2\032\034google/api/annotations.proto\032\027googl"
          + "e/api/client.proto\032\037google/api/field_beh"
          + "avior.proto\032\030google/iam/v2/deny.proto\032#g"
          + "oogle/longrunning/operations.proto\032\037google/protobuf/timestamp.proto\"\302\003\n"
          + "\006Policy\022\021\n"
          + "\004name\030\001 \001(\tB\003\340A\005\022\020\n"
          + "\003uid\030\002 \001(\tB\003\340A\005\022\021\n"
          + "\004kind\030\003 \001(\tB\003\340A\003\022\024\n"
          + "\014display_name\030\004 \001(\t\022;\n"
          + "\013annotations\030\005 \003(\0132&.google.iam.v2.Policy.AnnotationsEntry\022\014\n"
          + "\004etag\030\006 \001(\t\0224\n"
          + "\013create_time\030\007 \001(\0132\032.google.protobuf.TimestampB\003\340A\003\0224\n"
          + "\013update_time\030\010 \001(\0132\032.google.protobuf.TimestampB\003\340A\003\0224\n"
          + "\013delete_time\030\t \001(\0132\032.google.protobuf.TimestampB\003\340A\003\022(\n"
          + "\005rules\030\n"
          + " \003(\0132\031.google.iam.v2.PolicyRule\022\037\n"
          + "\022managing_authority\030\013 \001(\tB\003\340A\005\0322\n"
          + "\020AnnotationsEntry\022\013\n"
          + "\003key\030\001 \001(\t\022\r\n"
          + "\005value\030\002 \001(\t:\0028\001\"W\n\n"
          + "PolicyRule\022,\n"
          + "\tdeny_rule\030\002 \001(\0132\027.google.iam.v2.DenyRuleH\000\022\023\n"
          + "\013description\030\001 \001(\tB\006\n"
          + "\004kind\"Q\n"
          + "\023ListPoliciesRequest\022\023\n"
          + "\006parent\030\001 \001(\tB\003\340A\002\022\021\n"
          + "\tpage_size\030\002 \001(\005\022\022\n\n"
          + "page_token\030\003 \001(\t\"X\n"
          + "\024ListPoliciesResponse\022\'\n"
          + "\010policies\030\001 \003(\0132\025.google.iam.v2.Policy\022\027\n"
          + "\017next_page_token\030\002 \001(\t\"%\n"
          + "\020GetPolicyRequest\022\021\n"
          + "\004name\030\001 \001(\tB\003\340A\002\"i\n"
          + "\023CreatePolicyRequest\022\023\n"
          + "\006parent\030\001 \001(\tB\003\340A\002\022*\n"
          + "\006policy\030\002 \001(\0132\025.google.iam.v2.PolicyB\003\340A\002\022\021\n"
          + "\tpolicy_id\030\003 \001(\t\"A\n"
          + "\023UpdatePolicyRequest\022*\n"
          + "\006policy\030\001 \001(\0132\025.google.iam.v2.PolicyB\003\340A\002\";\n"
          + "\023DeletePolicyRequest\022\021\n"
          + "\004name\030\001 \001(\tB\003\340A\002\022\021\n"
          + "\004etag\030\002 \001(\tB\003\340A\001\"J\n"
          + "\027PolicyOperationMetadata\022/\n"
          + "\013create_time\030\001 \001(\0132\032.google.protobuf.Timestamp2\320\006\n"
          + "\010Policies\022\203\001\n"
          + "\014ListPolicies\022\".google.iam.v2.ListPoliciesRequest\032#"
          + ".google.iam.v2.ListPoliciesResponse\"*\332A\006"
          + "parent\202\323\344\223\002\033\022\031/v2/{parent=policies/*/*}\022m\n"
          + "\tGetPolicy\022\037.google.iam.v2.GetPolicyRe"
          + "quest\032\025.google.iam.v2.Policy\"(\332A\004name\202\323\344\223\002\033\022\031/v2/{name=policies/*/*/*}\022\272\001\n"
          + "\014Creat"
          + "ePolicy\022\".google.iam.v2.CreatePolicyRequest\032\035.google.longrunning.Operation\"g\312A!\n"
          + "\006Policy\022\027PolicyOperationMetadata\332A\027paren"
          + "t,policy,policy_id\202\323\344\223\002#\"\031/v2/{parent=policies/*/*}:\006policy\022\247\001\n"
          + "\014UpdatePolicy\022\".g"
          + "oogle.iam.v2.UpdatePolicyRequest\032\035.google.longrunning.Operation\"T\312A!\n"
          + "\006Policy\022\027PolicyOperationMetadata\202\323\344\223\002*\032"
          + " /v2/{policy.name=policies/*/*/*}:\006policy\022\237\001\n"
          + "\014Delete"
          + "Policy\022\".google.iam.v2.DeletePolicyRequest\032\035.google.longrunning.Operation\"L\312A!\n"
          + "\006Policy\022\027PolicyOperationMetadata\332A\004name\202\323"
          + "\344\223\002\033*\031/v2/{name=policies/*/*/*}\032F\312A\022iam."
          + "googleapis.com\322A.https://www.googleapis.com/auth/cloud-platformBy\n"
          + "\021com.google.iam.v2B\013PolicyProtoP\001Z)cloud.google.com/go"
          + "/iam/apiv2/iampb;iampb\252\002\023Google.Cloud.Ia"
          + "m.V2\312\002\023Google\\Cloud\\Iam\\V2b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.api.AnnotationsProto.getDescriptor(),
              com.google.api.ClientProto.getDescriptor(),
              com.google.api.FieldBehaviorProto.getDescriptor(),
              com.google.iam.v2.DenyRuleProto.getDescriptor(),
              com.google.longrunning.OperationsProto.getDescriptor(),
              com.google.protobuf.TimestampProto.getDescriptor(),
            });
    internal_static_google_iam_v2_Policy_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_google_iam_v2_Policy_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v2_Policy_descriptor,
            new java.lang.String[] {
              "Name",
              "Uid",
              "Kind",
              "DisplayName",
              "Annotations",
              "Etag",
              "CreateTime",
              "UpdateTime",
              "DeleteTime",
              "Rules",
              "ManagingAuthority",
            });
    internal_static_google_iam_v2_Policy_AnnotationsEntry_descriptor =
        internal_static_google_iam_v2_Policy_descriptor.getNestedTypes().get(0);
    internal_static_google_iam_v2_Policy_AnnotationsEntry_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v2_Policy_AnnotationsEntry_descriptor,
            new java.lang.String[] {
              "Key", "Value",
            });
    internal_static_google_iam_v2_PolicyRule_descriptor = getDescriptor().getMessageTypes().get(1);
    internal_static_google_iam_v2_PolicyRule_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v2_PolicyRule_descriptor,
            new java.lang.String[] {
              "DenyRule", "Description", "Kind",
            });
    internal_static_google_iam_v2_ListPoliciesRequest_descriptor =
        getDescriptor().getMessageTypes().get(2);
    internal_static_google_iam_v2_ListPoliciesRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v2_ListPoliciesRequest_descriptor,
            new java.lang.String[] {
              "Parent", "PageSize", "PageToken",
            });
    internal_static_google_iam_v2_ListPoliciesResponse_descriptor =
        getDescriptor().getMessageTypes().get(3);
    internal_static_google_iam_v2_ListPoliciesResponse_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v2_ListPoliciesResponse_descriptor,
            new java.lang.String[] {
              "Policies", "NextPageToken",
            });
    internal_static_google_iam_v2_GetPolicyRequest_descriptor =
        getDescriptor().getMessageTypes().get(4);
    internal_static_google_iam_v2_GetPolicyRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v2_GetPolicyRequest_descriptor,
            new java.lang.String[] {
              "Name",
            });
    internal_static_google_iam_v2_CreatePolicyRequest_descriptor =
        getDescriptor().getMessageTypes().get(5);
    internal_static_google_iam_v2_CreatePolicyRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v2_CreatePolicyRequest_descriptor,
            new java.lang.String[] {
              "Parent", "Policy", "PolicyId",
            });
    internal_static_google_iam_v2_UpdatePolicyRequest_descriptor =
        getDescriptor().getMessageTypes().get(6);
    internal_static_google_iam_v2_UpdatePolicyRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v2_UpdatePolicyRequest_descriptor,
            new java.lang.String[] {
              "Policy",
            });
    internal_static_google_iam_v2_DeletePolicyRequest_descriptor =
        getDescriptor().getMessageTypes().get(7);
    internal_static_google_iam_v2_DeletePolicyRequest_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v2_DeletePolicyRequest_descriptor,
            new java.lang.String[] {
              "Name", "Etag",
            });
    internal_static_google_iam_v2_PolicyOperationMetadata_descriptor =
        getDescriptor().getMessageTypes().get(8);
    internal_static_google_iam_v2_PolicyOperationMetadata_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_iam_v2_PolicyOperationMetadata_descriptor,
            new java.lang.String[] {
              "CreateTime",
            });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.google.api.ClientProto.defaultHost);
    registry.add(com.google.api.FieldBehaviorProto.fieldBehavior);
    registry.add(com.google.api.AnnotationsProto.http);
    registry.add(com.google.api.ClientProto.methodSignature);
    registry.add(com.google.api.ClientProto.oauthScopes);
    registry.add(com.google.longrunning.OperationsProto.operationInfo);
    com.google.protobuf.Descriptors.FileDescriptor.internalUpdateFileDescriptor(
        descriptor, registry);
    com.google.api.AnnotationsProto.getDescriptor();
    com.google.api.ClientProto.getDescriptor();
    com.google.api.FieldBehaviorProto.getDescriptor();
    com.google.iam.v2.DenyRuleProto.getDescriptor();
    com.google.longrunning.OperationsProto.getDescriptor();
    com.google.protobuf.TimestampProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
