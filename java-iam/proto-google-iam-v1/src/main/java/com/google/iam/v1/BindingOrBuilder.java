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
// source: google/iam/v1/policy.proto

// Protobuf Java Version: 3.25.3
package com.google.iam.v1;

public interface BindingOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.iam.v1.Binding)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Role that is assigned to the list of `members`, or principals.
   * For example, `roles/viewer`, `roles/editor`, or `roles/owner`.
   * </pre>
   *
   * <code>string role = 1;</code>
   *
   * @return The role.
   */
  java.lang.String getRole();
  /**
   *
   *
   * <pre>
   * Role that is assigned to the list of `members`, or principals.
   * For example, `roles/viewer`, `roles/editor`, or `roles/owner`.
   * </pre>
   *
   * <code>string role = 1;</code>
   *
   * @return The bytes for role.
   */
  com.google.protobuf.ByteString getRoleBytes();

  /**
   *
   *
   * <pre>
   * Specifies the principals requesting access for a Google Cloud resource.
   * `members` can have the following values:
   *
   * * `allUsers`: A special identifier that represents anyone who is
   *    on the internet; with or without a Google account.
   *
   * * `allAuthenticatedUsers`: A special identifier that represents anyone
   *    who is authenticated with a Google account or a service account.
   *
   * * `user:{emailid}`: An email address that represents a specific Google
   *    account. For example, `alice&#64;example.com` .
   *
   *
   * * `serviceAccount:{emailid}`: An email address that represents a service
   *    account. For example, `my-other-app&#64;appspot.gserviceaccount.com`.
   *
   * * `group:{emailid}`: An email address that represents a Google group.
   *    For example, `admins&#64;example.com`.
   *
   * * `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique
   *    identifier) representing a user that has been recently deleted. For
   *    example, `alice&#64;example.com?uid=123456789012345678901`. If the user is
   *    recovered, this value reverts to `user:{emailid}` and the recovered user
   *    retains the role in the binding.
   *
   * * `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus
   *    unique identifier) representing a service account that has been recently
   *    deleted. For example,
   *    `my-other-app&#64;appspot.gserviceaccount.com?uid=123456789012345678901`.
   *    If the service account is undeleted, this value reverts to
   *    `serviceAccount:{emailid}` and the undeleted service account retains the
   *    role in the binding.
   *
   * * `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique
   *    identifier) representing a Google group that has been recently
   *    deleted. For example, `admins&#64;example.com?uid=123456789012345678901`. If
   *    the group is recovered, this value reverts to `group:{emailid}` and the
   *    recovered group retains the role in the binding.
   *
   *
   * * `domain:{domain}`: The G Suite domain (primary) that represents all the
   *    users of that domain. For example, `google.com` or `example.com`.
   * </pre>
   *
   * <code>repeated string members = 2;</code>
   *
   * @return A list containing the members.
   */
  java.util.List<java.lang.String> getMembersList();
  /**
   *
   *
   * <pre>
   * Specifies the principals requesting access for a Google Cloud resource.
   * `members` can have the following values:
   *
   * * `allUsers`: A special identifier that represents anyone who is
   *    on the internet; with or without a Google account.
   *
   * * `allAuthenticatedUsers`: A special identifier that represents anyone
   *    who is authenticated with a Google account or a service account.
   *
   * * `user:{emailid}`: An email address that represents a specific Google
   *    account. For example, `alice&#64;example.com` .
   *
   *
   * * `serviceAccount:{emailid}`: An email address that represents a service
   *    account. For example, `my-other-app&#64;appspot.gserviceaccount.com`.
   *
   * * `group:{emailid}`: An email address that represents a Google group.
   *    For example, `admins&#64;example.com`.
   *
   * * `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique
   *    identifier) representing a user that has been recently deleted. For
   *    example, `alice&#64;example.com?uid=123456789012345678901`. If the user is
   *    recovered, this value reverts to `user:{emailid}` and the recovered user
   *    retains the role in the binding.
   *
   * * `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus
   *    unique identifier) representing a service account that has been recently
   *    deleted. For example,
   *    `my-other-app&#64;appspot.gserviceaccount.com?uid=123456789012345678901`.
   *    If the service account is undeleted, this value reverts to
   *    `serviceAccount:{emailid}` and the undeleted service account retains the
   *    role in the binding.
   *
   * * `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique
   *    identifier) representing a Google group that has been recently
   *    deleted. For example, `admins&#64;example.com?uid=123456789012345678901`. If
   *    the group is recovered, this value reverts to `group:{emailid}` and the
   *    recovered group retains the role in the binding.
   *
   *
   * * `domain:{domain}`: The G Suite domain (primary) that represents all the
   *    users of that domain. For example, `google.com` or `example.com`.
   * </pre>
   *
   * <code>repeated string members = 2;</code>
   *
   * @return The count of members.
   */
  int getMembersCount();
  /**
   *
   *
   * <pre>
   * Specifies the principals requesting access for a Google Cloud resource.
   * `members` can have the following values:
   *
   * * `allUsers`: A special identifier that represents anyone who is
   *    on the internet; with or without a Google account.
   *
   * * `allAuthenticatedUsers`: A special identifier that represents anyone
   *    who is authenticated with a Google account or a service account.
   *
   * * `user:{emailid}`: An email address that represents a specific Google
   *    account. For example, `alice&#64;example.com` .
   *
   *
   * * `serviceAccount:{emailid}`: An email address that represents a service
   *    account. For example, `my-other-app&#64;appspot.gserviceaccount.com`.
   *
   * * `group:{emailid}`: An email address that represents a Google group.
   *    For example, `admins&#64;example.com`.
   *
   * * `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique
   *    identifier) representing a user that has been recently deleted. For
   *    example, `alice&#64;example.com?uid=123456789012345678901`. If the user is
   *    recovered, this value reverts to `user:{emailid}` and the recovered user
   *    retains the role in the binding.
   *
   * * `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus
   *    unique identifier) representing a service account that has been recently
   *    deleted. For example,
   *    `my-other-app&#64;appspot.gserviceaccount.com?uid=123456789012345678901`.
   *    If the service account is undeleted, this value reverts to
   *    `serviceAccount:{emailid}` and the undeleted service account retains the
   *    role in the binding.
   *
   * * `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique
   *    identifier) representing a Google group that has been recently
   *    deleted. For example, `admins&#64;example.com?uid=123456789012345678901`. If
   *    the group is recovered, this value reverts to `group:{emailid}` and the
   *    recovered group retains the role in the binding.
   *
   *
   * * `domain:{domain}`: The G Suite domain (primary) that represents all the
   *    users of that domain. For example, `google.com` or `example.com`.
   * </pre>
   *
   * <code>repeated string members = 2;</code>
   *
   * @param index The index of the element to return.
   * @return The members at the given index.
   */
  java.lang.String getMembers(int index);
  /**
   *
   *
   * <pre>
   * Specifies the principals requesting access for a Google Cloud resource.
   * `members` can have the following values:
   *
   * * `allUsers`: A special identifier that represents anyone who is
   *    on the internet; with or without a Google account.
   *
   * * `allAuthenticatedUsers`: A special identifier that represents anyone
   *    who is authenticated with a Google account or a service account.
   *
   * * `user:{emailid}`: An email address that represents a specific Google
   *    account. For example, `alice&#64;example.com` .
   *
   *
   * * `serviceAccount:{emailid}`: An email address that represents a service
   *    account. For example, `my-other-app&#64;appspot.gserviceaccount.com`.
   *
   * * `group:{emailid}`: An email address that represents a Google group.
   *    For example, `admins&#64;example.com`.
   *
   * * `deleted:user:{emailid}?uid={uniqueid}`: An email address (plus unique
   *    identifier) representing a user that has been recently deleted. For
   *    example, `alice&#64;example.com?uid=123456789012345678901`. If the user is
   *    recovered, this value reverts to `user:{emailid}` and the recovered user
   *    retains the role in the binding.
   *
   * * `deleted:serviceAccount:{emailid}?uid={uniqueid}`: An email address (plus
   *    unique identifier) representing a service account that has been recently
   *    deleted. For example,
   *    `my-other-app&#64;appspot.gserviceaccount.com?uid=123456789012345678901`.
   *    If the service account is undeleted, this value reverts to
   *    `serviceAccount:{emailid}` and the undeleted service account retains the
   *    role in the binding.
   *
   * * `deleted:group:{emailid}?uid={uniqueid}`: An email address (plus unique
   *    identifier) representing a Google group that has been recently
   *    deleted. For example, `admins&#64;example.com?uid=123456789012345678901`. If
   *    the group is recovered, this value reverts to `group:{emailid}` and the
   *    recovered group retains the role in the binding.
   *
   *
   * * `domain:{domain}`: The G Suite domain (primary) that represents all the
   *    users of that domain. For example, `google.com` or `example.com`.
   * </pre>
   *
   * <code>repeated string members = 2;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the members at the given index.
   */
  com.google.protobuf.ByteString getMembersBytes(int index);

  /**
   *
   *
   * <pre>
   * The condition that is associated with this binding.
   *
   * If the condition evaluates to `true`, then this binding applies to the
   * current request.
   *
   * If the condition evaluates to `false`, then this binding does not apply to
   * the current request. However, a different role binding might grant the same
   * role to one or more of the principals in this binding.
   *
   * To learn which resources support conditions in their IAM policies, see the
   * [IAM
   * documentation](https://cloud.google.com/iam/help/conditions/resource-policies).
   * </pre>
   *
   * <code>.google.type.Expr condition = 3;</code>
   *
   * @return Whether the condition field is set.
   */
  boolean hasCondition();
  /**
   *
   *
   * <pre>
   * The condition that is associated with this binding.
   *
   * If the condition evaluates to `true`, then this binding applies to the
   * current request.
   *
   * If the condition evaluates to `false`, then this binding does not apply to
   * the current request. However, a different role binding might grant the same
   * role to one or more of the principals in this binding.
   *
   * To learn which resources support conditions in their IAM policies, see the
   * [IAM
   * documentation](https://cloud.google.com/iam/help/conditions/resource-policies).
   * </pre>
   *
   * <code>.google.type.Expr condition = 3;</code>
   *
   * @return The condition.
   */
  com.google.type.Expr getCondition();
  /**
   *
   *
   * <pre>
   * The condition that is associated with this binding.
   *
   * If the condition evaluates to `true`, then this binding applies to the
   * current request.
   *
   * If the condition evaluates to `false`, then this binding does not apply to
   * the current request. However, a different role binding might grant the same
   * role to one or more of the principals in this binding.
   *
   * To learn which resources support conditions in their IAM policies, see the
   * [IAM
   * documentation](https://cloud.google.com/iam/help/conditions/resource-policies).
   * </pre>
   *
   * <code>.google.type.Expr condition = 3;</code>
   */
  com.google.type.ExprOrBuilder getConditionOrBuilder();
}
