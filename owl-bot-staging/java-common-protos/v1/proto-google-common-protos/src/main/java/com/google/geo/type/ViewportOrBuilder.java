// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/geo/type/viewport.proto

// Protobuf Java Version: 3.25.2
package com.google.geo.type;

public interface ViewportOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.geo.type.Viewport)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * Required. The low point of the viewport.
   * </pre>
   *
   * <code>.google.type.LatLng low = 1;</code>
   * @return Whether the low field is set.
   */
  boolean hasLow();
  /**
   * <pre>
   * Required. The low point of the viewport.
   * </pre>
   *
   * <code>.google.type.LatLng low = 1;</code>
   * @return The low.
   */
  com.google.type.LatLng getLow();
  /**
   * <pre>
   * Required. The low point of the viewport.
   * </pre>
   *
   * <code>.google.type.LatLng low = 1;</code>
   */
  com.google.type.LatLngOrBuilder getLowOrBuilder();

  /**
   * <pre>
   * Required. The high point of the viewport.
   * </pre>
   *
   * <code>.google.type.LatLng high = 2;</code>
   * @return Whether the high field is set.
   */
  boolean hasHigh();
  /**
   * <pre>
   * Required. The high point of the viewport.
   * </pre>
   *
   * <code>.google.type.LatLng high = 2;</code>
   * @return The high.
   */
  com.google.type.LatLng getHigh();
  /**
   * <pre>
   * Required. The high point of the viewport.
   * </pre>
   *
   * <code>.google.type.LatLng high = 2;</code>
   */
  com.google.type.LatLngOrBuilder getHighOrBuilder();
}