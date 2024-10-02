// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/compliance.proto

// Protobuf Java Version: 3.25.5
package com.google.showcase.v1beta1;

public interface ComplianceGroupOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.ComplianceGroup)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string name = 1;</code>
   * @return The name.
   */
  java.lang.String getName();
  /**
   * <code>string name = 1;</code>
   * @return The bytes for name.
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <code>repeated string rpcs = 2;</code>
   * @return A list containing the rpcs.
   */
  java.util.List<java.lang.String>
      getRpcsList();
  /**
   * <code>repeated string rpcs = 2;</code>
   * @return The count of rpcs.
   */
  int getRpcsCount();
  /**
   * <code>repeated string rpcs = 2;</code>
   * @param index The index of the element to return.
   * @return The rpcs at the given index.
   */
  java.lang.String getRpcs(int index);
  /**
   * <code>repeated string rpcs = 2;</code>
   * @param index The index of the value to return.
   * @return The bytes of the rpcs at the given index.
   */
  com.google.protobuf.ByteString
      getRpcsBytes(int index);

  /**
   * <code>repeated .google.showcase.v1beta1.RepeatRequest requests = 3;</code>
   */
  java.util.List<com.google.showcase.v1beta1.RepeatRequest> 
      getRequestsList();
  /**
   * <code>repeated .google.showcase.v1beta1.RepeatRequest requests = 3;</code>
   */
  com.google.showcase.v1beta1.RepeatRequest getRequests(int index);
  /**
   * <code>repeated .google.showcase.v1beta1.RepeatRequest requests = 3;</code>
   */
  int getRequestsCount();
  /**
   * <code>repeated .google.showcase.v1beta1.RepeatRequest requests = 3;</code>
   */
  java.util.List<? extends com.google.showcase.v1beta1.RepeatRequestOrBuilder> 
      getRequestsOrBuilderList();
  /**
   * <code>repeated .google.showcase.v1beta1.RepeatRequest requests = 3;</code>
   */
  com.google.showcase.v1beta1.RepeatRequestOrBuilder getRequestsOrBuilder(
      int index);
}
