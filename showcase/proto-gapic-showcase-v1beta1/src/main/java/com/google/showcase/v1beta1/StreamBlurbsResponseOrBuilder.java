// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/messaging.proto

// Protobuf Java Version: 3.25.1
package com.google.showcase.v1beta1;

public interface StreamBlurbsResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.StreamBlurbsResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The blurb that was either created, updated, or deleted.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
   * @return Whether the blurb field is set.
   */
  boolean hasBlurb();
  /**
   * <pre>
   * The blurb that was either created, updated, or deleted.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
   * @return The blurb.
   */
  com.google.showcase.v1beta1.Blurb getBlurb();
  /**
   * <pre>
   * The blurb that was either created, updated, or deleted.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
   */
  com.google.showcase.v1beta1.BlurbOrBuilder getBlurbOrBuilder();

  /**
   * <pre>
   * The action that triggered the blurb to be returned.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.StreamBlurbsResponse.Action action = 2;</code>
   * @return The enum numeric value on the wire for action.
   */
  int getActionValue();
  /**
   * <pre>
   * The action that triggered the blurb to be returned.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.StreamBlurbsResponse.Action action = 2;</code>
   * @return The action.
   */
  com.google.showcase.v1beta1.StreamBlurbsResponse.Action getAction();
}
