// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/iam/v2beta/policy.proto

package com.google.iam.v2beta;

public final class PolicyProto {
  private PolicyProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_iam_v2beta_Policy_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2beta_Policy_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_iam_v2beta_Policy_AnnotationsEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2beta_Policy_AnnotationsEntry_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_iam_v2beta_PolicyRule_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2beta_PolicyRule_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_iam_v2beta_ListPoliciesRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2beta_ListPoliciesRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_iam_v2beta_ListPoliciesResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2beta_ListPoliciesResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_iam_v2beta_GetPolicyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2beta_GetPolicyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_iam_v2beta_CreatePolicyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2beta_CreatePolicyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_iam_v2beta_UpdatePolicyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2beta_UpdatePolicyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_iam_v2beta_DeletePolicyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2beta_DeletePolicyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_google_iam_v2beta_PolicyOperationMetadata_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_iam_v2beta_PolicyOperationMetadata_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\036google/iam/v2beta/policy.proto\022\021google" +
      ".iam.v2beta\032\034google/api/annotations.prot" +
      "o\032\027google/api/client.proto\032\037google/api/f" +
      "ield_behavior.proto\032\034google/iam/v2beta/d" +
      "eny.proto\032#google/longrunning/operations" +
      ".proto\032\037google/protobuf/timestamp.proto\"" +
      "\257\003\n\006Policy\022\022\n\004name\030\001 \001(\tB\004\342A\001\005\022\021\n\003uid\030\002 " +
      "\001(\tB\004\342A\001\005\022\022\n\004kind\030\003 \001(\tB\004\342A\001\003\022\024\n\014display" +
      "_name\030\004 \001(\t\022?\n\013annotations\030\005 \003(\0132*.googl" +
      "e.iam.v2beta.Policy.AnnotationsEntry\022\014\n\004" +
      "etag\030\006 \001(\t\0225\n\013create_time\030\007 \001(\0132\032.google" +
      ".protobuf.TimestampB\004\342A\001\003\0225\n\013update_time" +
      "\030\010 \001(\0132\032.google.protobuf.TimestampB\004\342A\001\003" +
      "\0225\n\013delete_time\030\t \001(\0132\032.google.protobuf." +
      "TimestampB\004\342A\001\003\022,\n\005rules\030\n \003(\0132\035.google." +
      "iam.v2beta.PolicyRule\0322\n\020AnnotationsEntr" +
      "y\022\013\n\003key\030\001 \001(\t\022\r\n\005value\030\002 \001(\t:\0028\001\"[\n\nPol" +
      "icyRule\0220\n\tdeny_rule\030\002 \001(\0132\033.google.iam." +
      "v2beta.DenyRuleH\000\022\023\n\013description\030\001 \001(\tB\006" +
      "\n\004kind\"R\n\023ListPoliciesRequest\022\024\n\006parent\030" +
      "\001 \001(\tB\004\342A\001\002\022\021\n\tpage_size\030\002 \001(\005\022\022\n\npage_t" +
      "oken\030\003 \001(\t\"\\\n\024ListPoliciesResponse\022+\n\010po" +
      "licies\030\001 \003(\0132\031.google.iam.v2beta.Policy\022" +
      "\027\n\017next_page_token\030\002 \001(\t\"&\n\020GetPolicyReq" +
      "uest\022\022\n\004name\030\001 \001(\tB\004\342A\001\002\"o\n\023CreatePolicy" +
      "Request\022\024\n\006parent\030\001 \001(\tB\004\342A\001\002\022/\n\006policy\030" +
      "\002 \001(\0132\031.google.iam.v2beta.PolicyB\004\342A\001\002\022\021" +
      "\n\tpolicy_id\030\003 \001(\t\"F\n\023UpdatePolicyRequest" +
      "\022/\n\006policy\030\001 \001(\0132\031.google.iam.v2beta.Pol" +
      "icyB\004\342A\001\002\"=\n\023DeletePolicyRequest\022\022\n\004name" +
      "\030\001 \001(\tB\004\342A\001\002\022\022\n\004etag\030\002 \001(\tB\004\342A\001\001\"J\n\027Poli" +
      "cyOperationMetadata\022/\n\013create_time\030\001 \001(\013" +
      "2\032.google.protobuf.Timestamp2\200\007\n\010Policie" +
      "s\022\217\001\n\014ListPolicies\022&.google.iam.v2beta.L" +
      "istPoliciesRequest\032\'.google.iam.v2beta.L" +
      "istPoliciesResponse\".\332A\006parent\202\323\344\223\002\037\022\035/v" +
      "2beta/{parent=policies/*/*}\022y\n\tGetPolicy" +
      "\022#.google.iam.v2beta.GetPolicyRequest\032\031." +
      "google.iam.v2beta.Policy\",\332A\004name\202\323\344\223\002\037\022" +
      "\035/v2beta/{name=policies/*/*/*}\022\302\001\n\014Creat" +
      "ePolicy\022&.google.iam.v2beta.CreatePolicy" +
      "Request\032\035.google.longrunning.Operation\"k" +
      "\312A!\n\006Policy\022\027PolicyOperationMetadata\332A\027p" +
      "arent,policy,policy_id\202\323\344\223\002\'\"\035/v2beta/{p" +
      "arent=policies/*/*}:\006policy\022\257\001\n\014UpdatePo" +
      "licy\022&.google.iam.v2beta.UpdatePolicyReq" +
      "uest\032\035.google.longrunning.Operation\"X\312A!" +
      "\n\006Policy\022\027PolicyOperationMetadata\202\323\344\223\002.\032" +
      "$/v2beta/{policy.name=policies/*/*/*}:\006p" +
      "olicy\022\247\001\n\014DeletePolicy\022&.google.iam.v2be" +
      "ta.DeletePolicyRequest\032\035.google.longrunn" +
      "ing.Operation\"P\312A!\n\006Policy\022\027PolicyOperat" +
      "ionMetadata\332A\004name\202\323\344\223\002\037*\035/v2beta/{name=" +
      "policies/*/*/*}\032F\312A\022iam.googleapis.com\322A" +
      ".https://www.googleapis.com/auth/cloud-p" +
      "latformB\211\001\n\025com.google.iam.v2betaB\013Polic" +
      "yProtoP\001Z-cloud.google.com/go/iam/apiv2b" +
      "eta/iampb;iampb\252\002\027Google.Cloud.Iam.V2Bet" +
      "a\312\002\027Google\\Cloud\\Iam\\V2betab\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.api.AnnotationsProto.getDescriptor(),
          com.google.api.ClientProto.getDescriptor(),
          com.google.api.FieldBehaviorProto.getDescriptor(),
          com.google.iam.v2beta.DenyRuleProto.getDescriptor(),
          com.google.longrunning.OperationsProto.getDescriptor(),
          com.google.protobuf.TimestampProto.getDescriptor(),
        });
    internal_static_google_iam_v2beta_Policy_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_google_iam_v2beta_Policy_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_iam_v2beta_Policy_descriptor,
        new java.lang.String[] { "Name", "Uid", "Kind", "DisplayName", "Annotations", "Etag", "CreateTime", "UpdateTime", "DeleteTime", "Rules", });
    internal_static_google_iam_v2beta_Policy_AnnotationsEntry_descriptor =
      internal_static_google_iam_v2beta_Policy_descriptor.getNestedTypes().get(0);
    internal_static_google_iam_v2beta_Policy_AnnotationsEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_iam_v2beta_Policy_AnnotationsEntry_descriptor,
        new java.lang.String[] { "Key", "Value", });
    internal_static_google_iam_v2beta_PolicyRule_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_google_iam_v2beta_PolicyRule_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_iam_v2beta_PolicyRule_descriptor,
        new java.lang.String[] { "DenyRule", "Description", "Kind", });
    internal_static_google_iam_v2beta_ListPoliciesRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_google_iam_v2beta_ListPoliciesRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_iam_v2beta_ListPoliciesRequest_descriptor,
        new java.lang.String[] { "Parent", "PageSize", "PageToken", });
    internal_static_google_iam_v2beta_ListPoliciesResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_google_iam_v2beta_ListPoliciesResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_iam_v2beta_ListPoliciesResponse_descriptor,
        new java.lang.String[] { "Policies", "NextPageToken", });
    internal_static_google_iam_v2beta_GetPolicyRequest_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_google_iam_v2beta_GetPolicyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_iam_v2beta_GetPolicyRequest_descriptor,
        new java.lang.String[] { "Name", });
    internal_static_google_iam_v2beta_CreatePolicyRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_google_iam_v2beta_CreatePolicyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_iam_v2beta_CreatePolicyRequest_descriptor,
        new java.lang.String[] { "Parent", "Policy", "PolicyId", });
    internal_static_google_iam_v2beta_UpdatePolicyRequest_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_google_iam_v2beta_UpdatePolicyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_iam_v2beta_UpdatePolicyRequest_descriptor,
        new java.lang.String[] { "Policy", });
    internal_static_google_iam_v2beta_DeletePolicyRequest_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_google_iam_v2beta_DeletePolicyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_iam_v2beta_DeletePolicyRequest_descriptor,
        new java.lang.String[] { "Name", "Etag", });
    internal_static_google_iam_v2beta_PolicyOperationMetadata_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_google_iam_v2beta_PolicyOperationMetadata_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_google_iam_v2beta_PolicyOperationMetadata_descriptor,
        new java.lang.String[] { "CreateTime", });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.google.api.ClientProto.defaultHost);
    registry.add(com.google.api.FieldBehaviorProto.fieldBehavior);
    registry.add(com.google.api.AnnotationsProto.http);
    registry.add(com.google.api.ClientProto.methodSignature);
    registry.add(com.google.api.ClientProto.oauthScopes);
    registry.add(com.google.longrunning.OperationsProto.operationInfo);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
    com.google.api.AnnotationsProto.getDescriptor();
    com.google.api.ClientProto.getDescriptor();
    com.google.api.FieldBehaviorProto.getDescriptor();
    com.google.iam.v2beta.DenyRuleProto.getDescriptor();
    com.google.longrunning.OperationsProto.getDescriptor();
    com.google.protobuf.TimestampProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
