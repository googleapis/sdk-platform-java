// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/messaging.proto

package com.google.showcase.v1beta1;

public interface StreamBlurbsRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.StreamBlurbsRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The resource name of a chat room or user profile whose blurbs to stream.
   * </pre>
   *
   * <code>string name = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <pre>
   * The resource name of a chat room or user profile whose blurbs to stream.
   * </pre>
   *
   * <code>string name = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   * The time at which this stream will close.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp expire_time = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   * @return Whether the expireTime field is set.
   */
  boolean hasExpireTime();
  /**
   * <pre>
   * The time at which this stream will close.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp expire_time = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   * @return The expireTime.
   */
  com.google.protobuf.Timestamp getExpireTime();
  /**
   * <pre>
   * The time at which this stream will close.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp expire_time = 2 [(.google.api.field_behavior) = REQUIRED];</code>
   */
  com.google.protobuf.TimestampOrBuilder getExpireTimeOrBuilder();
}
