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
// source: google/api/auth.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

public final class AuthProto {
  private AuthProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_Authentication_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_Authentication_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_AuthenticationRule_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_AuthenticationRule_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_JwtLocation_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_JwtLocation_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_AuthProvider_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_AuthProvider_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_OAuthRequirements_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_OAuthRequirements_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_api_AuthRequirement_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_api_AuthRequirement_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n\025google/api/auth.proto\022\ngoogle.api\"l\n\016A"
          + "uthentication\022-\n\005rules\030\003 \003(\0132\036.google.ap"
          + "i.AuthenticationRule\022+\n\tproviders\030\004 \003(\0132"
          + "\030.google.api.AuthProvider\"\251\001\n\022Authentica"
          + "tionRule\022\020\n\010selector\030\001 \001(\t\022,\n\005oauth\030\002 \001("
          + "\0132\035.google.api.OAuthRequirements\022 \n\030allo"
          + "w_without_credential\030\005 \001(\010\0221\n\014requiremen"
          + "ts\030\007 \003(\0132\033.google.api.AuthRequirement\"^\n"
          + "\013JwtLocation\022\020\n\006header\030\001 \001(\tH\000\022\017\n\005query\030"
          + "\002 \001(\tH\000\022\020\n\006cookie\030\004 \001(\tH\000\022\024\n\014value_prefi"
          + "x\030\003 \001(\tB\004\n\002in\"\232\001\n\014AuthProvider\022\n\n\002id\030\001 \001"
          + "(\t\022\016\n\006issuer\030\002 \001(\t\022\020\n\010jwks_uri\030\003 \001(\t\022\021\n\t"
          + "audiences\030\004 \001(\t\022\031\n\021authorization_url\030\005 \001"
          + "(\t\022.\n\rjwt_locations\030\006 \003(\0132\027.google.api.J"
          + "wtLocation\"-\n\021OAuthRequirements\022\030\n\020canon"
          + "ical_scopes\030\001 \001(\t\"9\n\017AuthRequirement\022\023\n\013"
          + "provider_id\030\001 \001(\t\022\021\n\taudiences\030\002 \001(\tBk\n\016"
          + "com.google.apiB\tAuthProtoP\001ZEgoogle.gola"
          + "ng.org/genproto/googleapis/api/serviceco"
          + "nfig;serviceconfig\242\002\004GAPIb\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData, new com.google.protobuf.Descriptors.FileDescriptor[] {});
    internal_static_google_api_Authentication_descriptor = getDescriptor().getMessageTypes().get(0);
    internal_static_google_api_Authentication_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_Authentication_descriptor,
            new java.lang.String[] {
              "Rules", "Providers",
            });
    internal_static_google_api_AuthenticationRule_descriptor =
        getDescriptor().getMessageTypes().get(1);
    internal_static_google_api_AuthenticationRule_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_AuthenticationRule_descriptor,
            new java.lang.String[] {
              "Selector", "Oauth", "AllowWithoutCredential", "Requirements",
            });
    internal_static_google_api_JwtLocation_descriptor = getDescriptor().getMessageTypes().get(2);
    internal_static_google_api_JwtLocation_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_JwtLocation_descriptor,
            new java.lang.String[] {
              "Header", "Query", "Cookie", "ValuePrefix", "In",
            });
    internal_static_google_api_AuthProvider_descriptor = getDescriptor().getMessageTypes().get(3);
    internal_static_google_api_AuthProvider_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_AuthProvider_descriptor,
            new java.lang.String[] {
              "Id", "Issuer", "JwksUri", "Audiences", "AuthorizationUrl", "JwtLocations",
            });
    internal_static_google_api_OAuthRequirements_descriptor =
        getDescriptor().getMessageTypes().get(4);
    internal_static_google_api_OAuthRequirements_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_OAuthRequirements_descriptor,
            new java.lang.String[] {
              "CanonicalScopes",
            });
    internal_static_google_api_AuthRequirement_descriptor =
        getDescriptor().getMessageTypes().get(5);
    internal_static_google_api_AuthRequirement_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_api_AuthRequirement_descriptor,
            new java.lang.String[] {
              "ProviderId", "Audiences",
            });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
