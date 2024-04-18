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

public interface AuditConfigOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.iam.v1.AuditConfig)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Specifies a service that will be enabled for audit logging.
   * For example, `storage.googleapis.com`, `cloudsql.googleapis.com`.
   * `allServices` is a special value that covers all services.
   * </pre>
   *
   * <code>string service = 1;</code>
   *
   * @return The service.
   */
  java.lang.String getService();
  /**
   *
   *
   * <pre>
   * Specifies a service that will be enabled for audit logging.
   * For example, `storage.googleapis.com`, `cloudsql.googleapis.com`.
   * `allServices` is a special value that covers all services.
   * </pre>
   *
   * <code>string service = 1;</code>
   *
   * @return The bytes for service.
   */
  com.google.protobuf.ByteString getServiceBytes();

  /**
   *
   *
   * <pre>
   * The configuration for logging of each type of permission.
   * </pre>
   *
   * <code>repeated .google.iam.v1.AuditLogConfig audit_log_configs = 3;</code>
   */
  java.util.List<com.google.iam.v1.AuditLogConfig> getAuditLogConfigsList();
  /**
   *
   *
   * <pre>
   * The configuration for logging of each type of permission.
   * </pre>
   *
   * <code>repeated .google.iam.v1.AuditLogConfig audit_log_configs = 3;</code>
   */
  com.google.iam.v1.AuditLogConfig getAuditLogConfigs(int index);
  /**
   *
   *
   * <pre>
   * The configuration for logging of each type of permission.
   * </pre>
   *
   * <code>repeated .google.iam.v1.AuditLogConfig audit_log_configs = 3;</code>
   */
  int getAuditLogConfigsCount();
  /**
   *
   *
   * <pre>
   * The configuration for logging of each type of permission.
   * </pre>
   *
   * <code>repeated .google.iam.v1.AuditLogConfig audit_log_configs = 3;</code>
   */
  java.util.List<? extends com.google.iam.v1.AuditLogConfigOrBuilder>
      getAuditLogConfigsOrBuilderList();
  /**
   *
   *
   * <pre>
   * The configuration for logging of each type of permission.
   * </pre>
   *
   * <code>repeated .google.iam.v1.AuditLogConfig audit_log_configs = 3;</code>
   */
  com.google.iam.v1.AuditLogConfigOrBuilder getAuditLogConfigsOrBuilder(int index);
}