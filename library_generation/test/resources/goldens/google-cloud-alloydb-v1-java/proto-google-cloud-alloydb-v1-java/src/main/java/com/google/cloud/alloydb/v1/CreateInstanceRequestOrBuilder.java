// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/cloud/alloydb/v1/service.proto

package com.google.cloud.alloydb.v1;

public interface CreateInstanceRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.cloud.alloydb.v1.CreateInstanceRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Required. The name of the parent resource. For the required format, see the
   * comment on the Instance.name field.
   * </pre>
   *
   * <code>string parent = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }</code>
   * @return The parent.
   */
  java.lang.String getParent();
  /**
   * <pre>
   * Required. The name of the parent resource. For the required format, see the
   * comment on the Instance.name field.
   * </pre>
   *
   * <code>string parent = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }</code>
   * @return The bytes for parent.
   */
  com.google.protobuf.ByteString
      getParentBytes();

  /**
   * <pre>
   * Required. ID of the requesting object.
   * </pre>
   *
   * <code>string instance_id = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   * @return The instanceId.
   */
  java.lang.String getInstanceId();
  /**
   * <pre>
   * Required. ID of the requesting object.
   * </pre>
   *
   * <code>string instance_id = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   * @return The bytes for instanceId.
   */
  com.google.protobuf.ByteString
      getInstanceIdBytes();

  /**
   * <pre>
   * Required. The resource being created
   * </pre>
   *
   * <code>.google.cloud.alloydb.v1.Instance instance = 3 [(.google.api.field_behavior) = REQUIRED];</code>
   * @return Whether the instance field is set.
   */
  boolean hasInstance();
  /**
   * <pre>
   * Required. The resource being created
   * </pre>
   *
   * <code>.google.cloud.alloydb.v1.Instance instance = 3 [(.google.api.field_behavior) = REQUIRED];</code>
   * @return The instance.
   */
  com.google.cloud.alloydb.v1.Instance getInstance();
  /**
   * <pre>
   * Required. The resource being created
   * </pre>
   *
   * <code>.google.cloud.alloydb.v1.Instance instance = 3 [(.google.api.field_behavior) = REQUIRED];</code>
   */
  com.google.cloud.alloydb.v1.InstanceOrBuilder getInstanceOrBuilder();

  /**
   * <pre>
   * Optional. An optional request ID to identify requests. Specify a unique
   * request ID so that if you must retry your request, the server will know to
   * ignore the request if it has already been completed. The server will
   * guarantee that for at least 60 minutes since the first request.
   *
   * For example, consider a situation where you make an initial request and
   * the request times out. If you make the request again with the same request
   * ID, the server can check if original operation with the same request ID
   * was received, and if so, will ignore the second request. This prevents
   * clients from accidentally creating duplicate commitments.
   *
   * The request ID must be a valid UUID with the exception that zero UUID is
   * not supported (00000000-0000-0000-0000-000000000000).
   * </pre>
   *
   * <code>string request_id = 4 [(.google.api.field_behavior) = OPTIONAL];</code>
   * @return The requestId.
   */
  java.lang.String getRequestId();
  /**
   * <pre>
   * Optional. An optional request ID to identify requests. Specify a unique
   * request ID so that if you must retry your request, the server will know to
   * ignore the request if it has already been completed. The server will
   * guarantee that for at least 60 minutes since the first request.
   *
   * For example, consider a situation where you make an initial request and
   * the request times out. If you make the request again with the same request
   * ID, the server can check if original operation with the same request ID
   * was received, and if so, will ignore the second request. This prevents
   * clients from accidentally creating duplicate commitments.
   *
   * The request ID must be a valid UUID with the exception that zero UUID is
   * not supported (00000000-0000-0000-0000-000000000000).
   * </pre>
   *
   * <code>string request_id = 4 [(.google.api.field_behavior) = OPTIONAL];</code>
   * @return The bytes for requestId.
   */
  com.google.protobuf.ByteString
      getRequestIdBytes();

  /**
   * <pre>
   * Optional. If set, performs request validation (e.g. permission checks and
   * any other type of validation), but do not actually execute the create
   * request.
   * </pre>
   *
   * <code>bool validate_only = 5 [(.google.api.field_behavior) = OPTIONAL];</code>
   * @return The validateOnly.
   */
  boolean getValidateOnly();
}
