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
// source: google/rpc/context/audit_context.proto

// Protobuf Java Version: 3.25.4
package com.google.rpc.context;

public interface AuditContextOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.rpc.context.AuditContext)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * Serialized audit log.
   * </pre>
   *
   * <code>bytes audit_log = 1;</code>
   *
   * @return The auditLog.
   */
  com.google.protobuf.ByteString getAuditLog();

  /**
   *
   *
   * <pre>
   * An API request message that is scrubbed based on the method annotation.
   * This field should only be filled if audit_log field is present.
   * Service Control will use this to assemble a complete log for Cloud Audit
   * Logs and Google internal audit logs.
   * </pre>
   *
   * <code>.google.protobuf.Struct scrubbed_request = 2;</code>
   *
   * @return Whether the scrubbedRequest field is set.
   */
  boolean hasScrubbedRequest();
  /**
   *
   *
   * <pre>
   * An API request message that is scrubbed based on the method annotation.
   * This field should only be filled if audit_log field is present.
   * Service Control will use this to assemble a complete log for Cloud Audit
   * Logs and Google internal audit logs.
   * </pre>
   *
   * <code>.google.protobuf.Struct scrubbed_request = 2;</code>
   *
   * @return The scrubbedRequest.
   */
  com.google.protobuf.Struct getScrubbedRequest();
  /**
   *
   *
   * <pre>
   * An API request message that is scrubbed based on the method annotation.
   * This field should only be filled if audit_log field is present.
   * Service Control will use this to assemble a complete log for Cloud Audit
   * Logs and Google internal audit logs.
   * </pre>
   *
   * <code>.google.protobuf.Struct scrubbed_request = 2;</code>
   */
  com.google.protobuf.StructOrBuilder getScrubbedRequestOrBuilder();

  /**
   *
   *
   * <pre>
   * An API response message that is scrubbed based on the method annotation.
   * This field should only be filled if audit_log field is present.
   * Service Control will use this to assemble a complete log for Cloud Audit
   * Logs and Google internal audit logs.
   * </pre>
   *
   * <code>.google.protobuf.Struct scrubbed_response = 3;</code>
   *
   * @return Whether the scrubbedResponse field is set.
   */
  boolean hasScrubbedResponse();
  /**
   *
   *
   * <pre>
   * An API response message that is scrubbed based on the method annotation.
   * This field should only be filled if audit_log field is present.
   * Service Control will use this to assemble a complete log for Cloud Audit
   * Logs and Google internal audit logs.
   * </pre>
   *
   * <code>.google.protobuf.Struct scrubbed_response = 3;</code>
   *
   * @return The scrubbedResponse.
   */
  com.google.protobuf.Struct getScrubbedResponse();
  /**
   *
   *
   * <pre>
   * An API response message that is scrubbed based on the method annotation.
   * This field should only be filled if audit_log field is present.
   * Service Control will use this to assemble a complete log for Cloud Audit
   * Logs and Google internal audit logs.
   * </pre>
   *
   * <code>.google.protobuf.Struct scrubbed_response = 3;</code>
   */
  com.google.protobuf.StructOrBuilder getScrubbedResponseOrBuilder();

  /**
   *
   *
   * <pre>
   * Number of scrubbed response items.
   * </pre>
   *
   * <code>int32 scrubbed_response_item_count = 4;</code>
   *
   * @return The scrubbedResponseItemCount.
   */
  int getScrubbedResponseItemCount();

  /**
   *
   *
   * <pre>
   * Audit resource name which is scrubbed.
   * </pre>
   *
   * <code>string target_resource = 5;</code>
   *
   * @return The targetResource.
   */
  java.lang.String getTargetResource();
  /**
   *
   *
   * <pre>
   * Audit resource name which is scrubbed.
   * </pre>
   *
   * <code>string target_resource = 5;</code>
   *
   * @return The bytes for targetResource.
   */
  com.google.protobuf.ByteString getTargetResourceBytes();
}
