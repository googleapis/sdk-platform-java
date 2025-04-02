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

// Protobuf Java Version: 3.25.5
package com.google.iam.v2beta;

public interface CreatePolicyRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.iam.v2beta.CreatePolicyRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Required. The resource that the policy is attached to, along with the kind of policy
   * to create. Format: `policies/{attachment_point}/denypolicies`
   *
   *
   * The attachment point is identified by its URL-encoded full resource name,
   * which means that the forward-slash character, `/`, must be written as
   * `%2F`. For example,
   * `policies/cloudresourcemanager.googleapis.com%2Fprojects%2Fmy-project/denypolicies`.
   *
   * For organizations and folders, use the numeric ID in the full resource
   * name. For projects, you can use the alphanumeric or the numeric ID.
   * </pre>
   *
   * <code>string parent = 1 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return The parent.
   */
  java.lang.String getParent();

  /**
   *
   *
   * <pre>
   * Required. The resource that the policy is attached to, along with the kind of policy
   * to create. Format: `policies/{attachment_point}/denypolicies`
   *
   *
   * The attachment point is identified by its URL-encoded full resource name,
   * which means that the forward-slash character, `/`, must be written as
   * `%2F`. For example,
   * `policies/cloudresourcemanager.googleapis.com%2Fprojects%2Fmy-project/denypolicies`.
   *
   * For organizations and folders, use the numeric ID in the full resource
   * name. For projects, you can use the alphanumeric or the numeric ID.
   * </pre>
   *
   * <code>string parent = 1 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return The bytes for parent.
   */
  com.google.protobuf.ByteString getParentBytes();

  /**
   *
   *
   * <pre>
   * Required. The policy to create.
   * </pre>
   *
   * <code>.google.iam.v2beta.Policy policy = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return Whether the policy field is set.
   */
  boolean hasPolicy();

  /**
   *
   *
   * <pre>
   * Required. The policy to create.
   * </pre>
   *
   * <code>.google.iam.v2beta.Policy policy = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return The policy.
   */
  com.google.iam.v2beta.Policy getPolicy();

  /**
   *
   *
   * <pre>
   * Required. The policy to create.
   * </pre>
   *
   * <code>.google.iam.v2beta.Policy policy = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   */
  com.google.iam.v2beta.PolicyOrBuilder getPolicyOrBuilder();

  /**
   *
   *
   * <pre>
   * The ID to use for this policy, which will become the final component of
   * the policy's resource name. The ID must contain 3 to 63 characters. It can
   * contain lowercase letters and numbers, as well as dashes (`-`) and periods
   * (`.`). The first character must be a lowercase letter.
   * </pre>
   *
   * <code>string policy_id = 3;</code>
   *
   * @return The policyId.
   */
  java.lang.String getPolicyId();

  /**
   *
   *
   * <pre>
   * The ID to use for this policy, which will become the final component of
   * the policy's resource name. The ID must contain 3 to 63 characters. It can
   * contain lowercase letters and numbers, as well as dashes (`-`) and periods
   * (`.`). The first character must be a lowercase letter.
   * </pre>
   *
   * <code>string policy_id = 3;</code>
   *
   * @return The bytes for policyId.
   */
  com.google.protobuf.ByteString getPolicyIdBytes();
}
