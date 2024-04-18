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
// source: google/cloud/audit/audit_log.proto

// Protobuf Java Version: 3.25.3
package com.google.cloud.audit;

public interface PolicyViolationInfoOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.cloud.audit.PolicyViolationInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Indicates the orgpolicy violations for this resource.
   * </pre>
   *
   * <code>.google.cloud.audit.OrgPolicyViolationInfo org_policy_violation_info = 1;</code>
   *
   * @return Whether the orgPolicyViolationInfo field is set.
   */
  boolean hasOrgPolicyViolationInfo();
  /**
   *
   *
   * <pre>
   * Indicates the orgpolicy violations for this resource.
   * </pre>
   *
   * <code>.google.cloud.audit.OrgPolicyViolationInfo org_policy_violation_info = 1;</code>
   *
   * @return The orgPolicyViolationInfo.
   */
  com.google.cloud.audit.OrgPolicyViolationInfo getOrgPolicyViolationInfo();
  /**
   *
   *
   * <pre>
   * Indicates the orgpolicy violations for this resource.
   * </pre>
   *
   * <code>.google.cloud.audit.OrgPolicyViolationInfo org_policy_violation_info = 1;</code>
   */
  com.google.cloud.audit.OrgPolicyViolationInfoOrBuilder getOrgPolicyViolationInfoOrBuilder();
}
