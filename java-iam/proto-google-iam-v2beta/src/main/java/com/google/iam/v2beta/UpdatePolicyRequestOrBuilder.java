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
// source: google/iam/v2beta/policy.proto

// Protobuf Java Version: 3.25.3
package com.google.iam.v2beta;

public interface UpdatePolicyRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.iam.v2beta.UpdatePolicyRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Required. The policy to update.
   *
   * To prevent conflicting updates, the `etag` value must match the value that
   * is stored in IAM. If the `etag` values do not match, the request fails with
   * a `409` error code and `ABORTED` status.
   * </pre>
   *
   * <code>.google.iam.v2beta.Policy policy = 1 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return Whether the policy field is set.
   */
  boolean hasPolicy();
  /**
   *
   *
   * <pre>
   * Required. The policy to update.
   *
   * To prevent conflicting updates, the `etag` value must match the value that
   * is stored in IAM. If the `etag` values do not match, the request fails with
   * a `409` error code and `ABORTED` status.
   * </pre>
   *
   * <code>.google.iam.v2beta.Policy policy = 1 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return The policy.
   */
  com.google.iam.v2beta.Policy getPolicy();
  /**
   *
   *
   * <pre>
   * Required. The policy to update.
   *
   * To prevent conflicting updates, the `etag` value must match the value that
   * is stored in IAM. If the `etag` values do not match, the request fails with
   * a `409` error code and `ABORTED` status.
   * </pre>
   *
   * <code>.google.iam.v2beta.Policy policy = 1 [(.google.api.field_behavior) = REQUIRED];</code>
   */
  com.google.iam.v2beta.PolicyOrBuilder getPolicyOrBuilder();
}