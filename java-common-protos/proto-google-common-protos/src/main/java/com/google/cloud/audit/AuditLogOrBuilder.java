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
// source: google/cloud/audit/audit_log.proto

// Protobuf Java Version: 3.25.5
package com.google.cloud.audit;

public interface AuditLogOrBuilder
    extends
    // @@protoc_insertion_point(interface_extends:google.cloud.audit.AuditLog)
    com.google.protobuf.MessageOrBuilder {

  /**
   *
   *
   * <pre>
   * The name of the API service performing the operation. For example,
   * `"compute.googleapis.com"`.
   * </pre>
   *
   * <code>string service_name = 7;</code>
   *
   * @return The serviceName.
   */
  java.lang.String getServiceName();

  /**
   *
   *
   * <pre>
   * The name of the API service performing the operation. For example,
   * `"compute.googleapis.com"`.
   * </pre>
   *
   * <code>string service_name = 7;</code>
   *
   * @return The bytes for serviceName.
   */
  com.google.protobuf.ByteString getServiceNameBytes();

  /**
   *
   *
   * <pre>
   * The name of the service method or operation.
   * For API calls, this should be the name of the API method.
   * For example,
   *
   *     "google.cloud.bigquery.v2.TableService.InsertTable"
   *     "google.logging.v2.ConfigServiceV2.CreateSink"
   * </pre>
   *
   * <code>string method_name = 8;</code>
   *
   * @return The methodName.
   */
  java.lang.String getMethodName();

  /**
   *
   *
   * <pre>
   * The name of the service method or operation.
   * For API calls, this should be the name of the API method.
   * For example,
   *
   *     "google.cloud.bigquery.v2.TableService.InsertTable"
   *     "google.logging.v2.ConfigServiceV2.CreateSink"
   * </pre>
   *
   * <code>string method_name = 8;</code>
   *
   * @return The bytes for methodName.
   */
  com.google.protobuf.ByteString getMethodNameBytes();

  /**
   *
   *
   * <pre>
   * The resource or collection that is the target of the operation.
   * The name is a scheme-less URI, not including the API service name.
   * For example:
   *
   *     "projects/PROJECT_ID/zones/us-central1-a/instances"
   *     "projects/PROJECT_ID/datasets/DATASET_ID"
   * </pre>
   *
   * <code>string resource_name = 11;</code>
   *
   * @return The resourceName.
   */
  java.lang.String getResourceName();

  /**
   *
   *
   * <pre>
   * The resource or collection that is the target of the operation.
   * The name is a scheme-less URI, not including the API service name.
   * For example:
   *
   *     "projects/PROJECT_ID/zones/us-central1-a/instances"
   *     "projects/PROJECT_ID/datasets/DATASET_ID"
   * </pre>
   *
   * <code>string resource_name = 11;</code>
   *
   * @return The bytes for resourceName.
   */
  com.google.protobuf.ByteString getResourceNameBytes();

  /**
   *
   *
   * <pre>
   * The resource location information.
   * </pre>
   *
   * <code>.google.cloud.audit.ResourceLocation resource_location = 20;</code>
   *
   * @return Whether the resourceLocation field is set.
   */
  boolean hasResourceLocation();

  /**
   *
   *
   * <pre>
   * The resource location information.
   * </pre>
   *
   * <code>.google.cloud.audit.ResourceLocation resource_location = 20;</code>
   *
   * @return The resourceLocation.
   */
  com.google.cloud.audit.ResourceLocation getResourceLocation();

  /**
   *
   *
   * <pre>
   * The resource location information.
   * </pre>
   *
   * <code>.google.cloud.audit.ResourceLocation resource_location = 20;</code>
   */
  com.google.cloud.audit.ResourceLocationOrBuilder getResourceLocationOrBuilder();

  /**
   *
   *
   * <pre>
   * The resource's original state before mutation. Present only for
   * operations which have successfully modified the targeted resource(s).
   * In general, this field should contain all changed fields, except those
   * that are already been included in `request`, `response`, `metadata` or
   * `service_data` fields.
   * When the JSON object represented here has a proto equivalent,
   * the proto name will be indicated in the `&#64;type` property.
   * </pre>
   *
   * <code>.google.protobuf.Struct resource_original_state = 19;</code>
   *
   * @return Whether the resourceOriginalState field is set.
   */
  boolean hasResourceOriginalState();

  /**
   *
   *
   * <pre>
   * The resource's original state before mutation. Present only for
   * operations which have successfully modified the targeted resource(s).
   * In general, this field should contain all changed fields, except those
   * that are already been included in `request`, `response`, `metadata` or
   * `service_data` fields.
   * When the JSON object represented here has a proto equivalent,
   * the proto name will be indicated in the `&#64;type` property.
   * </pre>
   *
   * <code>.google.protobuf.Struct resource_original_state = 19;</code>
   *
   * @return The resourceOriginalState.
   */
  com.google.protobuf.Struct getResourceOriginalState();

  /**
   *
   *
   * <pre>
   * The resource's original state before mutation. Present only for
   * operations which have successfully modified the targeted resource(s).
   * In general, this field should contain all changed fields, except those
   * that are already been included in `request`, `response`, `metadata` or
   * `service_data` fields.
   * When the JSON object represented here has a proto equivalent,
   * the proto name will be indicated in the `&#64;type` property.
   * </pre>
   *
   * <code>.google.protobuf.Struct resource_original_state = 19;</code>
   */
  com.google.protobuf.StructOrBuilder getResourceOriginalStateOrBuilder();

  /**
   *
   *
   * <pre>
   * The number of items returned from a List or Query API method,
   * if applicable.
   * </pre>
   *
   * <code>int64 num_response_items = 12;</code>
   *
   * @return The numResponseItems.
   */
  long getNumResponseItems();

  /**
   *
   *
   * <pre>
   * The status of the overall operation.
   * </pre>
   *
   * <code>.google.rpc.Status status = 2;</code>
   *
   * @return Whether the status field is set.
   */
  boolean hasStatus();

  /**
   *
   *
   * <pre>
   * The status of the overall operation.
   * </pre>
   *
   * <code>.google.rpc.Status status = 2;</code>
   *
   * @return The status.
   */
  com.google.rpc.Status getStatus();

  /**
   *
   *
   * <pre>
   * The status of the overall operation.
   * </pre>
   *
   * <code>.google.rpc.Status status = 2;</code>
   */
  com.google.rpc.StatusOrBuilder getStatusOrBuilder();

  /**
   *
   *
   * <pre>
   * Authentication information.
   * </pre>
   *
   * <code>.google.cloud.audit.AuthenticationInfo authentication_info = 3;</code>
   *
   * @return Whether the authenticationInfo field is set.
   */
  boolean hasAuthenticationInfo();

  /**
   *
   *
   * <pre>
   * Authentication information.
   * </pre>
   *
   * <code>.google.cloud.audit.AuthenticationInfo authentication_info = 3;</code>
   *
   * @return The authenticationInfo.
   */
  com.google.cloud.audit.AuthenticationInfo getAuthenticationInfo();

  /**
   *
   *
   * <pre>
   * Authentication information.
   * </pre>
   *
   * <code>.google.cloud.audit.AuthenticationInfo authentication_info = 3;</code>
   */
  com.google.cloud.audit.AuthenticationInfoOrBuilder getAuthenticationInfoOrBuilder();

  /**
   *
   *
   * <pre>
   * Authorization information. If there are multiple
   * resources or permissions involved, then there is
   * one AuthorizationInfo element for each {resource, permission} tuple.
   * </pre>
   *
   * <code>repeated .google.cloud.audit.AuthorizationInfo authorization_info = 9;</code>
   */
  java.util.List<com.google.cloud.audit.AuthorizationInfo> getAuthorizationInfoList();

  /**
   *
   *
   * <pre>
   * Authorization information. If there are multiple
   * resources or permissions involved, then there is
   * one AuthorizationInfo element for each {resource, permission} tuple.
   * </pre>
   *
   * <code>repeated .google.cloud.audit.AuthorizationInfo authorization_info = 9;</code>
   */
  com.google.cloud.audit.AuthorizationInfo getAuthorizationInfo(int index);

  /**
   *
   *
   * <pre>
   * Authorization information. If there are multiple
   * resources or permissions involved, then there is
   * one AuthorizationInfo element for each {resource, permission} tuple.
   * </pre>
   *
   * <code>repeated .google.cloud.audit.AuthorizationInfo authorization_info = 9;</code>
   */
  int getAuthorizationInfoCount();

  /**
   *
   *
   * <pre>
   * Authorization information. If there are multiple
   * resources or permissions involved, then there is
   * one AuthorizationInfo element for each {resource, permission} tuple.
   * </pre>
   *
   * <code>repeated .google.cloud.audit.AuthorizationInfo authorization_info = 9;</code>
   */
  java.util.List<? extends com.google.cloud.audit.AuthorizationInfoOrBuilder>
      getAuthorizationInfoOrBuilderList();

  /**
   *
   *
   * <pre>
   * Authorization information. If there are multiple
   * resources or permissions involved, then there is
   * one AuthorizationInfo element for each {resource, permission} tuple.
   * </pre>
   *
   * <code>repeated .google.cloud.audit.AuthorizationInfo authorization_info = 9;</code>
   */
  com.google.cloud.audit.AuthorizationInfoOrBuilder getAuthorizationInfoOrBuilder(int index);

  /**
   *
   *
   * <pre>
   * Indicates the policy violations for this request. If the request
   * is denied by the policy, violation information will be logged
   * here.
   * </pre>
   *
   * <code>.google.cloud.audit.PolicyViolationInfo policy_violation_info = 25;</code>
   *
   * @return Whether the policyViolationInfo field is set.
   */
  boolean hasPolicyViolationInfo();

  /**
   *
   *
   * <pre>
   * Indicates the policy violations for this request. If the request
   * is denied by the policy, violation information will be logged
   * here.
   * </pre>
   *
   * <code>.google.cloud.audit.PolicyViolationInfo policy_violation_info = 25;</code>
   *
   * @return The policyViolationInfo.
   */
  com.google.cloud.audit.PolicyViolationInfo getPolicyViolationInfo();

  /**
   *
   *
   * <pre>
   * Indicates the policy violations for this request. If the request
   * is denied by the policy, violation information will be logged
   * here.
   * </pre>
   *
   * <code>.google.cloud.audit.PolicyViolationInfo policy_violation_info = 25;</code>
   */
  com.google.cloud.audit.PolicyViolationInfoOrBuilder getPolicyViolationInfoOrBuilder();

  /**
   *
   *
   * <pre>
   * Metadata about the operation.
   * </pre>
   *
   * <code>.google.cloud.audit.RequestMetadata request_metadata = 4;</code>
   *
   * @return Whether the requestMetadata field is set.
   */
  boolean hasRequestMetadata();

  /**
   *
   *
   * <pre>
   * Metadata about the operation.
   * </pre>
   *
   * <code>.google.cloud.audit.RequestMetadata request_metadata = 4;</code>
   *
   * @return The requestMetadata.
   */
  com.google.cloud.audit.RequestMetadata getRequestMetadata();

  /**
   *
   *
   * <pre>
   * Metadata about the operation.
   * </pre>
   *
   * <code>.google.cloud.audit.RequestMetadata request_metadata = 4;</code>
   */
  com.google.cloud.audit.RequestMetadataOrBuilder getRequestMetadataOrBuilder();

  /**
   *
   *
   * <pre>
   * The operation request. This may not include all request parameters,
   * such as those that are too large, privacy-sensitive, or duplicated
   * elsewhere in the log record.
   * It should never include user-generated data, such as file contents.
   * When the JSON object represented here has a proto equivalent, the proto
   * name will be indicated in the `&#64;type` property.
   * </pre>
   *
   * <code>.google.protobuf.Struct request = 16;</code>
   *
   * @return Whether the request field is set.
   */
  boolean hasRequest();

  /**
   *
   *
   * <pre>
   * The operation request. This may not include all request parameters,
   * such as those that are too large, privacy-sensitive, or duplicated
   * elsewhere in the log record.
   * It should never include user-generated data, such as file contents.
   * When the JSON object represented here has a proto equivalent, the proto
   * name will be indicated in the `&#64;type` property.
   * </pre>
   *
   * <code>.google.protobuf.Struct request = 16;</code>
   *
   * @return The request.
   */
  com.google.protobuf.Struct getRequest();

  /**
   *
   *
   * <pre>
   * The operation request. This may not include all request parameters,
   * such as those that are too large, privacy-sensitive, or duplicated
   * elsewhere in the log record.
   * It should never include user-generated data, such as file contents.
   * When the JSON object represented here has a proto equivalent, the proto
   * name will be indicated in the `&#64;type` property.
   * </pre>
   *
   * <code>.google.protobuf.Struct request = 16;</code>
   */
  com.google.protobuf.StructOrBuilder getRequestOrBuilder();

  /**
   *
   *
   * <pre>
   * The operation response. This may not include all response elements,
   * such as those that are too large, privacy-sensitive, or duplicated
   * elsewhere in the log record.
   * It should never include user-generated data, such as file contents.
   * When the JSON object represented here has a proto equivalent, the proto
   * name will be indicated in the `&#64;type` property.
   * </pre>
   *
   * <code>.google.protobuf.Struct response = 17;</code>
   *
   * @return Whether the response field is set.
   */
  boolean hasResponse();

  /**
   *
   *
   * <pre>
   * The operation response. This may not include all response elements,
   * such as those that are too large, privacy-sensitive, or duplicated
   * elsewhere in the log record.
   * It should never include user-generated data, such as file contents.
   * When the JSON object represented here has a proto equivalent, the proto
   * name will be indicated in the `&#64;type` property.
   * </pre>
   *
   * <code>.google.protobuf.Struct response = 17;</code>
   *
   * @return The response.
   */
  com.google.protobuf.Struct getResponse();

  /**
   *
   *
   * <pre>
   * The operation response. This may not include all response elements,
   * such as those that are too large, privacy-sensitive, or duplicated
   * elsewhere in the log record.
   * It should never include user-generated data, such as file contents.
   * When the JSON object represented here has a proto equivalent, the proto
   * name will be indicated in the `&#64;type` property.
   * </pre>
   *
   * <code>.google.protobuf.Struct response = 17;</code>
   */
  com.google.protobuf.StructOrBuilder getResponseOrBuilder();

  /**
   *
   *
   * <pre>
   * Other service-specific data about the request, response, and other
   * information associated with the current audited event.
   * </pre>
   *
   * <code>.google.protobuf.Struct metadata = 18;</code>
   *
   * @return Whether the metadata field is set.
   */
  boolean hasMetadata();

  /**
   *
   *
   * <pre>
   * Other service-specific data about the request, response, and other
   * information associated with the current audited event.
   * </pre>
   *
   * <code>.google.protobuf.Struct metadata = 18;</code>
   *
   * @return The metadata.
   */
  com.google.protobuf.Struct getMetadata();

  /**
   *
   *
   * <pre>
   * Other service-specific data about the request, response, and other
   * information associated with the current audited event.
   * </pre>
   *
   * <code>.google.protobuf.Struct metadata = 18;</code>
   */
  com.google.protobuf.StructOrBuilder getMetadataOrBuilder();

  /**
   *
   *
   * <pre>
   * Deprecated. Use the `metadata` field instead.
   * Other service-specific data about the request, response, and other
   * activities.
   * </pre>
   *
   * <code>.google.protobuf.Any service_data = 15 [deprecated = true];</code>
   *
   * @deprecated google.cloud.audit.AuditLog.service_data is deprecated. See
   *     google/cloud/audit/audit_log.proto;l=110
   * @return Whether the serviceData field is set.
   */
  @java.lang.Deprecated
  boolean hasServiceData();

  /**
   *
   *
   * <pre>
   * Deprecated. Use the `metadata` field instead.
   * Other service-specific data about the request, response, and other
   * activities.
   * </pre>
   *
   * <code>.google.protobuf.Any service_data = 15 [deprecated = true];</code>
   *
   * @deprecated google.cloud.audit.AuditLog.service_data is deprecated. See
   *     google/cloud/audit/audit_log.proto;l=110
   * @return The serviceData.
   */
  @java.lang.Deprecated
  com.google.protobuf.Any getServiceData();

  /**
   *
   *
   * <pre>
   * Deprecated. Use the `metadata` field instead.
   * Other service-specific data about the request, response, and other
   * activities.
   * </pre>
   *
   * <code>.google.protobuf.Any service_data = 15 [deprecated = true];</code>
   */
  @java.lang.Deprecated
  com.google.protobuf.AnyOrBuilder getServiceDataOrBuilder();
}
