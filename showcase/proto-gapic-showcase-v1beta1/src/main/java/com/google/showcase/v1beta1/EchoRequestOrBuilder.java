// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/echo.proto

// Protobuf Java Version: 3.25.1
package com.google.showcase.v1beta1;

public interface EchoRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.EchoRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The content to be echoed by the server.
   * </pre>
   *
   * <code>string content = 1;</code>
   * @return Whether the content field is set.
   */
  boolean hasContent();
  /**
   * <pre>
   * The content to be echoed by the server.
   * </pre>
   *
   * <code>string content = 1;</code>
   * @return The content.
   */
  java.lang.String getContent();
  /**
   * <pre>
   * The content to be echoed by the server.
   * </pre>
   *
   * <code>string content = 1;</code>
   * @return The bytes for content.
   */
  com.google.protobuf.ByteString
      getContentBytes();

  /**
   * <pre>
   * The error to be thrown by the server.
   * </pre>
   *
   * <code>.google.rpc.Status error = 2;</code>
   * @return Whether the error field is set.
   */
  boolean hasError();
  /**
   * <pre>
   * The error to be thrown by the server.
   * </pre>
   *
   * <code>.google.rpc.Status error = 2;</code>
   * @return The error.
   */
  com.google.rpc.Status getError();
  /**
   * <pre>
   * The error to be thrown by the server.
   * </pre>
   *
   * <code>.google.rpc.Status error = 2;</code>
   */
  com.google.rpc.StatusOrBuilder getErrorOrBuilder();

  /**
   * <pre>
   * The severity to be echoed by the server.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Severity severity = 3;</code>
   * @return The enum numeric value on the wire for severity.
   */
  int getSeverityValue();
  /**
   * <pre>
   * The severity to be echoed by the server.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Severity severity = 3;</code>
   * @return The severity.
   */
  com.google.showcase.v1beta1.Severity getSeverity();

  /**
   * <pre>
   * Optional. This field can be set to test the routing annotation on the Echo method.
   * </pre>
   *
   * <code>string header = 4;</code>
   * @return The header.
   */
  java.lang.String getHeader();
  /**
   * <pre>
   * Optional. This field can be set to test the routing annotation on the Echo method.
   * </pre>
   *
   * <code>string header = 4;</code>
   * @return The bytes for header.
   */
  com.google.protobuf.ByteString
      getHeaderBytes();

  /**
   * <pre>
   * Optional. This field can be set to test the routing annotation on the Echo method.
   * </pre>
   *
   * <code>string other_header = 5;</code>
   * @return The otherHeader.
   */
  java.lang.String getOtherHeader();
  /**
   * <pre>
   * Optional. This field can be set to test the routing annotation on the Echo method.
   * </pre>
   *
   * <code>string other_header = 5;</code>
   * @return The bytes for otherHeader.
   */
  com.google.protobuf.ByteString
      getOtherHeaderBytes();

  com.google.showcase.v1beta1.EchoRequest.ResponseCase getResponseCase();
}
