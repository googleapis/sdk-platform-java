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
// source: google/iam/v1/policy.proto

// Protobuf Java Version: 3.25.5
package com.google.iam.v1;

public interface PolicyDeltaOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.iam.v1.PolicyDelta)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The delta for Bindings between two policies.
   * </pre>
   *
   * <code>repeated .google.iam.v1.BindingDelta binding_deltas = 1;</code>
   */
  java.util.List<com.google.iam.v1.BindingDelta> getBindingDeltasList();

  /**
   *
   *
   * <pre>
   * The delta for Bindings between two policies.
   * </pre>
   *
   * <code>repeated .google.iam.v1.BindingDelta binding_deltas = 1;</code>
   */
  com.google.iam.v1.BindingDelta getBindingDeltas(int index);

  /**
   *
   *
   * <pre>
   * The delta for Bindings between two policies.
   * </pre>
   *
   * <code>repeated .google.iam.v1.BindingDelta binding_deltas = 1;</code>
   */
  int getBindingDeltasCount();

  /**
   *
   *
   * <pre>
   * The delta for Bindings between two policies.
   * </pre>
   *
   * <code>repeated .google.iam.v1.BindingDelta binding_deltas = 1;</code>
   */
  java.util.List<? extends com.google.iam.v1.BindingDeltaOrBuilder> getBindingDeltasOrBuilderList();

  /**
   *
   *
   * <pre>
   * The delta for Bindings between two policies.
   * </pre>
   *
   * <code>repeated .google.iam.v1.BindingDelta binding_deltas = 1;</code>
   */
  com.google.iam.v1.BindingDeltaOrBuilder getBindingDeltasOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * The delta for AuditConfigs between two policies.
   * </pre>
   *
   * <code>repeated .google.iam.v1.AuditConfigDelta audit_config_deltas = 2;</code>
   */
  java.util.List<com.google.iam.v1.AuditConfigDelta> getAuditConfigDeltasList();

  /**
   *
   *
   * <pre>
   * The delta for AuditConfigs between two policies.
   * </pre>
   *
   * <code>repeated .google.iam.v1.AuditConfigDelta audit_config_deltas = 2;</code>
   */
  com.google.iam.v1.AuditConfigDelta getAuditConfigDeltas(int index);

  /**
   *
   *
   * <pre>
   * The delta for AuditConfigs between two policies.
   * </pre>
   *
   * <code>repeated .google.iam.v1.AuditConfigDelta audit_config_deltas = 2;</code>
   */
  int getAuditConfigDeltasCount();

  /**
   *
   *
   * <pre>
   * The delta for AuditConfigs between two policies.
   * </pre>
   *
   * <code>repeated .google.iam.v1.AuditConfigDelta audit_config_deltas = 2;</code>
   */
  java.util.List<? extends com.google.iam.v1.AuditConfigDeltaOrBuilder>
      getAuditConfigDeltasOrBuilderList();

  /**
   *
   *
   * <pre>
   * The delta for AuditConfigs between two policies.
   * </pre>
   *
   * <code>repeated .google.iam.v1.AuditConfigDelta audit_config_deltas = 2;</code>
   */
  com.google.iam.v1.AuditConfigDeltaOrBuilder getAuditConfigDeltasOrBuilder(int index);
}
