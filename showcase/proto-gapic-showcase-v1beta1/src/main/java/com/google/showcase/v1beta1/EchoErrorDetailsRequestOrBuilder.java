// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/echo.proto

// Protobuf Java Version: 3.25.4
package com.google.showcase.v1beta1;

public interface EchoErrorDetailsRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.EchoErrorDetailsRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Content to return in a singular `*.error.details` field of type
   * `google.protobuf.Any`
   * </pre>
   *
   * <code>string single_detail_text = 1;</code>
   * @return The singleDetailText.
   */
  java.lang.String getSingleDetailText();
  /**
   * <pre>
   * Content to return in a singular `*.error.details` field of type
   * `google.protobuf.Any`
   * </pre>
   *
   * <code>string single_detail_text = 1;</code>
   * @return The bytes for singleDetailText.
   */
  com.google.protobuf.ByteString
      getSingleDetailTextBytes();

  /**
   * <pre>
   * Content to return in a repeated `*.error.details` field of type
   * `google.protobuf.Any`
   * </pre>
   *
   * <code>repeated string multi_detail_text = 2;</code>
   * @return A list containing the multiDetailText.
   */
  java.util.List<java.lang.String>
      getMultiDetailTextList();
  /**
   * <pre>
   * Content to return in a repeated `*.error.details` field of type
   * `google.protobuf.Any`
   * </pre>
   *
   * <code>repeated string multi_detail_text = 2;</code>
   * @return The count of multiDetailText.
   */
  int getMultiDetailTextCount();
  /**
   * <pre>
   * Content to return in a repeated `*.error.details` field of type
   * `google.protobuf.Any`
   * </pre>
   *
   * <code>repeated string multi_detail_text = 2;</code>
   * @param index The index of the element to return.
   * @return The multiDetailText at the given index.
   */
  java.lang.String getMultiDetailText(int index);
  /**
   * <pre>
   * Content to return in a repeated `*.error.details` field of type
   * `google.protobuf.Any`
   * </pre>
   *
   * <code>repeated string multi_detail_text = 2;</code>
   * @param index The index of the value to return.
   * @return The bytes of the multiDetailText at the given index.
   */
  com.google.protobuf.ByteString
      getMultiDetailTextBytes(int index);
}
