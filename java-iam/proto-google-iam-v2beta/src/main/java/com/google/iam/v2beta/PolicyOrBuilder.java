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
// source: google/iam/v2beta/policy.proto

// Protobuf Java Version: 3.25.8
package com.google.iam.v2beta;

public interface PolicyOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.iam.v2beta.Policy)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Immutable. The resource name of the `Policy`, which must be unique. Format:
   * `policies/{attachment_point}/denypolicies/{policy_id}`
   *
   *
   * The attachment point is identified by its URL-encoded full resource name,
   * which means that the forward-slash character, `/`, must be written as
   * `%2F`. For example,
   * `policies/cloudresourcemanager.googleapis.com%2Fprojects%2Fmy-project/denypolicies/my-deny-policy`.
   *
   * For organizations and folders, use the numeric ID in the full resource
   * name. For projects, requests can use the alphanumeric or the numeric ID.
   * Responses always contain the numeric ID.
   * </pre>
   *
   * <code>string name = 1 [(.google.api.field_behavior) = IMMUTABLE];</code>
   *
   * @return The name.
   */
  java.lang.String getName();

  /**
   *
   *
   * <pre>
   * Immutable. The resource name of the `Policy`, which must be unique. Format:
   * `policies/{attachment_point}/denypolicies/{policy_id}`
   *
   *
   * The attachment point is identified by its URL-encoded full resource name,
   * which means that the forward-slash character, `/`, must be written as
   * `%2F`. For example,
   * `policies/cloudresourcemanager.googleapis.com%2Fprojects%2Fmy-project/denypolicies/my-deny-policy`.
   *
   * For organizations and folders, use the numeric ID in the full resource
   * name. For projects, requests can use the alphanumeric or the numeric ID.
   * Responses always contain the numeric ID.
   * </pre>
   *
   * <code>string name = 1 [(.google.api.field_behavior) = IMMUTABLE];</code>
   *
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString getNameBytes();

  /**
   *
   *
   * <pre>
   * Immutable. The globally unique ID of the `Policy`. Assigned automatically when the
   * `Policy` is created.
   * </pre>
   *
   * <code>string uid = 2 [(.google.api.field_behavior) = IMMUTABLE];</code>
   *
   * @return The uid.
   */
  java.lang.String getUid();

  /**
   *
   *
   * <pre>
   * Immutable. The globally unique ID of the `Policy`. Assigned automatically when the
   * `Policy` is created.
   * </pre>
   *
   * <code>string uid = 2 [(.google.api.field_behavior) = IMMUTABLE];</code>
   *
   * @return The bytes for uid.
   */
  com.google.protobuf.ByteString getUidBytes();

  /**
   *
   *
   * <pre>
   * Output only. The kind of the `Policy`. Always contains the value `DenyPolicy`.
   * </pre>
   *
   * <code>string kind = 3 [(.google.api.field_behavior) = OUTPUT_ONLY];</code>
   *
   * @return The kind.
   */
  java.lang.String getKind();

  /**
   *
   *
   * <pre>
   * Output only. The kind of the `Policy`. Always contains the value `DenyPolicy`.
   * </pre>
   *
   * <code>string kind = 3 [(.google.api.field_behavior) = OUTPUT_ONLY];</code>
   *
   * @return The bytes for kind.
   */
  com.google.protobuf.ByteString getKindBytes();

  /**
   *
   *
   * <pre>
   * A user-specified description of the `Policy`. This value can be up to 63
   * characters.
   * </pre>
   *
   * <code>string display_name = 4;</code>
   *
   * @return The displayName.
   */
  java.lang.String getDisplayName();

  /**
   *
   *
   * <pre>
   * A user-specified description of the `Policy`. This value can be up to 63
   * characters.
   * </pre>
   *
   * <code>string display_name = 4;</code>
   *
   * @return The bytes for displayName.
   */
  com.google.protobuf.ByteString getDisplayNameBytes();

  /**
   *
   *
   * <pre>
   * A key-value map to store arbitrary metadata for the `Policy`. Keys
   * can be up to 63 characters. Values can be up to 255 characters.
   * </pre>
   *
   * <code>map&lt;string, string&gt; annotations = 5;</code>
   */
  int getAnnotationsCount();

  /**
   *
   *
   * <pre>
   * A key-value map to store arbitrary metadata for the `Policy`. Keys
   * can be up to 63 characters. Values can be up to 255 characters.
   * </pre>
   *
   * <code>map&lt;string, string&gt; annotations = 5;</code>
   */
  boolean containsAnnotations(java.lang.String key);

  /** Use {@link #getAnnotationsMap()} instead. */
  @java.lang.Deprecated
  java.util.Map<java.lang.String, java.lang.String> getAnnotations();

  /**
   *
   *
   * <pre>
   * A key-value map to store arbitrary metadata for the `Policy`. Keys
   * can be up to 63 characters. Values can be up to 255 characters.
   * </pre>
   *
   * <code>map&lt;string, string&gt; annotations = 5;</code>
   */
  java.util.Map<java.lang.String, java.lang.String> getAnnotationsMap();

  /**
   *
   *
   * <pre>
   * A key-value map to store arbitrary metadata for the `Policy`. Keys
   * can be up to 63 characters. Values can be up to 255 characters.
   * </pre>
   *
   * <code>map&lt;string, string&gt; annotations = 5;</code>
   */
  /* nullable */
  java.lang.String getAnnotationsOrDefault(
      java.lang.String key,
      /* nullable */
      java.lang.String defaultValue);

  /**
   *
   *
   * <pre>
   * A key-value map to store arbitrary metadata for the `Policy`. Keys
   * can be up to 63 characters. Values can be up to 255 characters.
   * </pre>
   *
   * <code>map&lt;string, string&gt; annotations = 5;</code>
   */
  java.lang.String getAnnotationsOrThrow(java.lang.String key);

  /**
   *
   *
   * <pre>
   * An opaque tag that identifies the current version of the `Policy`. IAM uses
   * this value to help manage concurrent updates, so they do not cause one
   * update to be overwritten by another.
   *
   * If this field is present in a [CreatePolicy][] request, the value is
   * ignored.
   * </pre>
   *
   * <code>string etag = 6;</code>
   *
   * @return The etag.
   */
  java.lang.String getEtag();

  /**
   *
   *
   * <pre>
   * An opaque tag that identifies the current version of the `Policy`. IAM uses
   * this value to help manage concurrent updates, so they do not cause one
   * update to be overwritten by another.
   *
   * If this field is present in a [CreatePolicy][] request, the value is
   * ignored.
   * </pre>
   *
   * <code>string etag = 6;</code>
   *
   * @return The bytes for etag.
   */
  com.google.protobuf.ByteString getEtagBytes();

  /**
   *
   *
   * <pre>
   * Output only. The time when the `Policy` was created.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp create_time = 7 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return Whether the createTime field is set.
   */
  boolean hasCreateTime();

  /**
   *
   *
   * <pre>
   * Output only. The time when the `Policy` was created.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp create_time = 7 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return The createTime.
   */
  com.google.protobuf.Timestamp getCreateTime();

  /**
   *
   *
   * <pre>
   * Output only. The time when the `Policy` was created.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp create_time = 7 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   */
  com.google.protobuf.TimestampOrBuilder getCreateTimeOrBuilder();

  /**
   *
   *
   * <pre>
   * Output only. The time when the `Policy` was last updated.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp update_time = 8 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return Whether the updateTime field is set.
   */
  boolean hasUpdateTime();

  /**
   *
   *
   * <pre>
   * Output only. The time when the `Policy` was last updated.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp update_time = 8 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return The updateTime.
   */
  com.google.protobuf.Timestamp getUpdateTime();

  /**
   *
   *
   * <pre>
   * Output only. The time when the `Policy` was last updated.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp update_time = 8 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   */
  com.google.protobuf.TimestampOrBuilder getUpdateTimeOrBuilder();

  /**
   *
   *
   * <pre>
   * Output only. The time when the `Policy` was deleted. Empty if the policy is not deleted.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp delete_time = 9 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return Whether the deleteTime field is set.
   */
  boolean hasDeleteTime();

  /**
   *
   *
   * <pre>
   * Output only. The time when the `Policy` was deleted. Empty if the policy is not deleted.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp delete_time = 9 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   *
   * @return The deleteTime.
   */
  com.google.protobuf.Timestamp getDeleteTime();

  /**
   *
   *
   * <pre>
   * Output only. The time when the `Policy` was deleted. Empty if the policy is not deleted.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp delete_time = 9 [(.google.api.field_behavior) = OUTPUT_ONLY];
   * </code>
   */
  com.google.protobuf.TimestampOrBuilder getDeleteTimeOrBuilder();

  /**
   *
   *
   * <pre>
   * A list of rules that specify the behavior of the `Policy`. All of the rules
   * should be of the `kind` specified in the `Policy`.
   * </pre>
   *
   * <code>repeated .google.iam.v2beta.PolicyRule rules = 10;</code>
   */
  java.util.List<com.google.iam.v2beta.PolicyRule> getRulesList();

  /**
   *
   *
   * <pre>
   * A list of rules that specify the behavior of the `Policy`. All of the rules
   * should be of the `kind` specified in the `Policy`.
   * </pre>
   *
   * <code>repeated .google.iam.v2beta.PolicyRule rules = 10;</code>
   */
  com.google.iam.v2beta.PolicyRule getRules(int index);

  /**
   *
   *
   * <pre>
   * A list of rules that specify the behavior of the `Policy`. All of the rules
   * should be of the `kind` specified in the `Policy`.
   * </pre>
   *
   * <code>repeated .google.iam.v2beta.PolicyRule rules = 10;</code>
   */
  int getRulesCount();

  /**
   *
   *
   * <pre>
   * A list of rules that specify the behavior of the `Policy`. All of the rules
   * should be of the `kind` specified in the `Policy`.
   * </pre>
   *
   * <code>repeated .google.iam.v2beta.PolicyRule rules = 10;</code>
   */
  java.util.List<? extends com.google.iam.v2beta.PolicyRuleOrBuilder> getRulesOrBuilderList();

  /**
   *
   *
   * <pre>
   * A list of rules that specify the behavior of the `Policy`. All of the rules
   * should be of the `kind` specified in the `Policy`.
   * </pre>
   *
   * <code>repeated .google.iam.v2beta.PolicyRule rules = 10;</code>
   */
  com.google.iam.v2beta.PolicyRuleOrBuilder getRulesOrBuilder(int index);
}
