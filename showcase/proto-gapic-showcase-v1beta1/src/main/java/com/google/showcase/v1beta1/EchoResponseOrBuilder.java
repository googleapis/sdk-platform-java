// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/echo.proto

// Protobuf Java Version: 3.25.5
package com.google.showcase.v1beta1;

public interface EchoResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.EchoResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The content specified in the request.
   * </pre>
   *
   * <code>string content = 1;</code>
   * @return The content.
   */
  java.lang.String getContent();
  /**
   * <pre>
   * The content specified in the request.
   * </pre>
   *
   * <code>string content = 1;</code>
   * @return The bytes for content.
   */
  com.google.protobuf.ByteString
      getContentBytes();

  /**
   * <pre>
   * The severity specified in the request.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Severity severity = 2;</code>
   * @return The enum numeric value on the wire for severity.
   */
  int getSeverityValue();
  /**
   * <pre>
   * The severity specified in the request.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Severity severity = 2;</code>
   * @return The severity.
   */
  com.google.showcase.v1beta1.Severity getSeverity();
}
