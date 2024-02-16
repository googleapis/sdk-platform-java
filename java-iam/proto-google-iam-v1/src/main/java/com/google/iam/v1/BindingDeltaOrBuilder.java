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

// Protobuf Java Version: 3.25.2
package com.google.iam.v1;

public interface BindingDeltaOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.iam.v1.BindingDelta)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The action that was performed on a Binding.
   * Required
   * </pre>
   *
   * <code>.google.iam.v1.BindingDelta.Action action = 1;</code>
   *
   * @return The enum numeric value on the wire for action.
   */
  int getActionValue();
  /**
   *
   *
   * <pre>
   * The action that was performed on a Binding.
   * Required
   * </pre>
   *
   * <code>.google.iam.v1.BindingDelta.Action action = 1;</code>
   *
   * @return The action.
   */
  com.google.iam.v1.BindingDelta.Action getAction();

  /**
   *
   *
   * <pre>
   * Role that is assigned to `members`.
   * For example, `roles/viewer`, `roles/editor`, or `roles/owner`.
   * Required
   * </pre>
   *
   * <code>string role = 2;</code>
   *
   * @return The role.
   */
  java.lang.String getRole();
  /**
   *
   *
   * <pre>
   * Role that is assigned to `members`.
   * For example, `roles/viewer`, `roles/editor`, or `roles/owner`.
   * Required
   * </pre>
   *
   * <code>string role = 2;</code>
   *
   * @return The bytes for role.
   */
  com.google.protobuf.ByteString getRoleBytes();

  /**
   *
   *
   * <pre>
   * A single identity requesting access for a Google Cloud resource.
   * Follows the same format of Binding.members.
   * Required
   * </pre>
   *
   * <code>string member = 3;</code>
   *
   * @return The member.
   */
  java.lang.String getMember();
  /**
   *
   *
   * <pre>
   * A single identity requesting access for a Google Cloud resource.
   * Follows the same format of Binding.members.
   * Required
   * </pre>
   *
   * <code>string member = 3;</code>
   *
   * @return The bytes for member.
   */
  com.google.protobuf.ByteString getMemberBytes();

  /**
   *
   *
   * <pre>
   * The condition that is associated with this binding.
   * </pre>
   *
   * <code>.google.type.Expr condition = 4;</code>
   *
   * @return Whether the condition field is set.
   */
  boolean hasCondition();
  /**
   *
   *
   * <pre>
   * The condition that is associated with this binding.
   * </pre>
   *
   * <code>.google.type.Expr condition = 4;</code>
   *
   * @return The condition.
   */
  com.google.type.Expr getCondition();
  /**
   *
   *
   * <pre>
   * The condition that is associated with this binding.
   * </pre>
   *
   * <code>.google.type.Expr condition = 4;</code>
   */
  com.google.type.ExprOrBuilder getConditionOrBuilder();
}
