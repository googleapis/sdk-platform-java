// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/echo.proto

package com.google.showcase.v1beta1;

public interface ExpandRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.ExpandRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The content that will be split into words and returned on the stream.
   * </pre>
   *
   * <code>string content = 1;</code>
   * @return The content.
   */
  java.lang.String getContent();
  /**
   * <pre>
   * The content that will be split into words and returned on the stream.
   * </pre>
   *
   * <code>string content = 1;</code>
   * @return The bytes for content.
   */
  com.google.protobuf.ByteString
      getContentBytes();

  /**
   * <pre>
   * The error that is thrown after all words are sent on the stream.
   * </pre>
   *
   * <code>.google.rpc.Status error = 2;</code>
   * @return Whether the error field is set.
   */
  boolean hasError();
  /**
   * <pre>
   * The error that is thrown after all words are sent on the stream.
   * </pre>
   *
   * <code>.google.rpc.Status error = 2;</code>
   * @return The error.
   */
  com.google.rpc.Status getError();
  /**
   * <pre>
   * The error that is thrown after all words are sent on the stream.
   * </pre>
   *
   * <code>.google.rpc.Status error = 2;</code>
   */
  com.google.rpc.StatusOrBuilder getErrorOrBuilder();
}
