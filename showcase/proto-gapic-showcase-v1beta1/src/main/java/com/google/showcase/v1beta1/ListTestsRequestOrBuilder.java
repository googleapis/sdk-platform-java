// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/testing.proto

package com.google.showcase.v1beta1;

public interface ListTestsRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.ListTestsRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The session.
   * </pre>
   *
   * <code>string parent = 1 [(.google.api.resource_reference) = { ... }</code>
   * @return The parent.
   */
  java.lang.String getParent();
  /**
   * <pre>
   * The session.
   * </pre>
   *
   * <code>string parent = 1 [(.google.api.resource_reference) = { ... }</code>
   * @return The bytes for parent.
   */
  com.google.protobuf.ByteString
      getParentBytes();

  /**
   * <pre>
   * The maximum number of tests to return per page.
   * </pre>
   *
   * <code>int32 page_size = 2;</code>
   * @return The pageSize.
   */
  int getPageSize();

  /**
   * <pre>
   * The page token, for retrieving subsequent pages.
   * </pre>
   *
   * <code>string page_token = 3;</code>
   * @return The pageToken.
   */
  java.lang.String getPageToken();
  /**
   * <pre>
   * The page token, for retrieving subsequent pages.
   * </pre>
   *
   * <code>string page_token = 3;</code>
   * @return The bytes for pageToken.
   */
  com.google.protobuf.ByteString
      getPageTokenBytes();
}
