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
// source: google/iam/v1/iam_policy.proto

// Protobuf Java Version: 3.25.5
package com.google.iam.v1;

public interface TestIamPermissionsRequestOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.iam.v1.TestIamPermissionsRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * REQUIRED: The resource for which the policy detail is being requested.
   * See the operation documentation for the appropriate value for this field.
   * </pre>
   *
   * <code>
   * string resource = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @return The resource.
   */
  java.lang.String getResource();

  /**
   *
   *
   * <pre>
   * REQUIRED: The resource for which the policy detail is being requested.
   * See the operation documentation for the appropriate value for this field.
   * </pre>
   *
   * <code>
   * string resource = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @return The bytes for resource.
   */
  com.google.protobuf.ByteString getResourceBytes();

  /**
   *
   *
   * <pre>
   * The set of permissions to check for the `resource`. Permissions with
   * wildcards (such as '*' or 'storage.*') are not allowed. For more
   * information see
   * [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions).
   * </pre>
   *
   * <code>repeated string permissions = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return A list containing the permissions.
   */
  java.util.List<java.lang.String> getPermissionsList();

  /**
   *
   *
   * <pre>
   * The set of permissions to check for the `resource`. Permissions with
   * wildcards (such as '*' or 'storage.*') are not allowed. For more
   * information see
   * [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions).
   * </pre>
   *
   * <code>repeated string permissions = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @return The count of permissions.
   */
  int getPermissionsCount();

  /**
   *
   *
   * <pre>
   * The set of permissions to check for the `resource`. Permissions with
   * wildcards (such as '*' or 'storage.*') are not allowed. For more
   * information see
   * [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions).
   * </pre>
   *
   * <code>repeated string permissions = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @param index The index of the element to return.
   * @return The permissions at the given index.
   */
  java.lang.String getPermissions(int index);

  /**
   *
   *
   * <pre>
   * The set of permissions to check for the `resource`. Permissions with
   * wildcards (such as '*' or 'storage.*') are not allowed. For more
   * information see
   * [IAM Overview](https://cloud.google.com/iam/docs/overview#permissions).
   * </pre>
   *
   * <code>repeated string permissions = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the permissions at the given index.
   */
  com.google.protobuf.ByteString getPermissionsBytes(int index);
}
