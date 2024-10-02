// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/compliance.proto

// Protobuf Java Version: 3.25.5
package com.google.showcase.v1beta1;

public interface EnumResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.EnumResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The original request for a known or unknown enum from the server.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
   * @return Whether the request field is set.
   */
  boolean hasRequest();
  /**
   * <pre>
   * The original request for a known or unknown enum from the server.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
   * @return The request.
   */
  com.google.showcase.v1beta1.EnumRequest getRequest();
  /**
   * <pre>
   * The original request for a known or unknown enum from the server.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.EnumRequest request = 1;</code>
   */
  com.google.showcase.v1beta1.EnumRequestOrBuilder getRequestOrBuilder();

  /**
   * <pre>
   * The actual enum the server provided.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Continent continent = 2;</code>
   * @return The enum numeric value on the wire for continent.
   */
  int getContinentValue();
  /**
   * <pre>
   * The actual enum the server provided.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Continent continent = 2;</code>
   * @return The continent.
   */
  com.google.showcase.v1beta1.Continent getContinent();
}
