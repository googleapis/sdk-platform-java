// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/messaging.proto

// Protobuf Java Version: 3.25.4
package com.google.showcase.v1beta1;

public interface UpdateBlurbRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.showcase.v1beta1.UpdateBlurbRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The blurb to update.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
   * @return Whether the blurb field is set.
   */
  boolean hasBlurb();
  /**
   * <pre>
   * The blurb to update.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
   * @return The blurb.
   */
  com.google.showcase.v1beta1.Blurb getBlurb();
  /**
   * <pre>
   * The blurb to update.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Blurb blurb = 1;</code>
   */
  com.google.showcase.v1beta1.BlurbOrBuilder getBlurbOrBuilder();

  /**
   * <pre>
   * The field mask to determine wich fields are to be updated. If empty, the
   * server will assume all fields are to be updated.
   * </pre>
   *
   * <code>.google.protobuf.FieldMask update_mask = 2;</code>
   * @return Whether the updateMask field is set.
   */
  boolean hasUpdateMask();
  /**
   * <pre>
   * The field mask to determine wich fields are to be updated. If empty, the
   * server will assume all fields are to be updated.
   * </pre>
   *
   * <code>.google.protobuf.FieldMask update_mask = 2;</code>
   * @return The updateMask.
   */
  com.google.protobuf.FieldMask getUpdateMask();
  /**
   * <pre>
   * The field mask to determine wich fields are to be updated. If empty, the
   * server will assume all fields are to be updated.
   * </pre>
   *
   * <code>.google.protobuf.FieldMask update_mask = 2;</code>
   */
  com.google.protobuf.FieldMaskOrBuilder getUpdateMaskOrBuilder();
}
