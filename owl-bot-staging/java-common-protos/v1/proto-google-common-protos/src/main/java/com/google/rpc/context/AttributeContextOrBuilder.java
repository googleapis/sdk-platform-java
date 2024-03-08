// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/rpc/context/attribute_context.proto

// Protobuf Java Version: 3.25.2
package com.google.rpc.context;

public interface AttributeContextOrBuilder extends
    // @@protoc_insertion_point(interface_extends:google.rpc.context.AttributeContext)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * The origin of a network activity. In a multi hop network activity,
   * the origin represents the sender of the first hop. For the first hop,
   * the `source` and the `origin` must have the same content.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Peer origin = 7;</code>
   * @return Whether the origin field is set.
   */
  boolean hasOrigin();
  /**
   * <pre>
   * The origin of a network activity. In a multi hop network activity,
   * the origin represents the sender of the first hop. For the first hop,
   * the `source` and the `origin` must have the same content.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Peer origin = 7;</code>
   * @return The origin.
   */
  com.google.rpc.context.AttributeContext.Peer getOrigin();
  /**
   * <pre>
   * The origin of a network activity. In a multi hop network activity,
   * the origin represents the sender of the first hop. For the first hop,
   * the `source` and the `origin` must have the same content.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Peer origin = 7;</code>
   */
  com.google.rpc.context.AttributeContext.PeerOrBuilder getOriginOrBuilder();

  /**
   * <pre>
   * The source of a network activity, such as starting a TCP connection.
   * In a multi hop network activity, the source represents the sender of the
   * last hop.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Peer source = 1;</code>
   * @return Whether the source field is set.
   */
  boolean hasSource();
  /**
   * <pre>
   * The source of a network activity, such as starting a TCP connection.
   * In a multi hop network activity, the source represents the sender of the
   * last hop.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Peer source = 1;</code>
   * @return The source.
   */
  com.google.rpc.context.AttributeContext.Peer getSource();
  /**
   * <pre>
   * The source of a network activity, such as starting a TCP connection.
   * In a multi hop network activity, the source represents the sender of the
   * last hop.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Peer source = 1;</code>
   */
  com.google.rpc.context.AttributeContext.PeerOrBuilder getSourceOrBuilder();

  /**
   * <pre>
   * The destination of a network activity, such as accepting a TCP connection.
   * In a multi hop network activity, the destination represents the receiver of
   * the last hop.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Peer destination = 2;</code>
   * @return Whether the destination field is set.
   */
  boolean hasDestination();
  /**
   * <pre>
   * The destination of a network activity, such as accepting a TCP connection.
   * In a multi hop network activity, the destination represents the receiver of
   * the last hop.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Peer destination = 2;</code>
   * @return The destination.
   */
  com.google.rpc.context.AttributeContext.Peer getDestination();
  /**
   * <pre>
   * The destination of a network activity, such as accepting a TCP connection.
   * In a multi hop network activity, the destination represents the receiver of
   * the last hop.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Peer destination = 2;</code>
   */
  com.google.rpc.context.AttributeContext.PeerOrBuilder getDestinationOrBuilder();

  /**
   * <pre>
   * Represents a network request, such as an HTTP request.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Request request = 3;</code>
   * @return Whether the request field is set.
   */
  boolean hasRequest();
  /**
   * <pre>
   * Represents a network request, such as an HTTP request.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Request request = 3;</code>
   * @return The request.
   */
  com.google.rpc.context.AttributeContext.Request getRequest();
  /**
   * <pre>
   * Represents a network request, such as an HTTP request.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Request request = 3;</code>
   */
  com.google.rpc.context.AttributeContext.RequestOrBuilder getRequestOrBuilder();

  /**
   * <pre>
   * Represents a network response, such as an HTTP response.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Response response = 4;</code>
   * @return Whether the response field is set.
   */
  boolean hasResponse();
  /**
   * <pre>
   * Represents a network response, such as an HTTP response.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Response response = 4;</code>
   * @return The response.
   */
  com.google.rpc.context.AttributeContext.Response getResponse();
  /**
   * <pre>
   * Represents a network response, such as an HTTP response.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Response response = 4;</code>
   */
  com.google.rpc.context.AttributeContext.ResponseOrBuilder getResponseOrBuilder();

  /**
   * <pre>
   * Represents a target resource that is involved with a network activity.
   * If multiple resources are involved with an activity, this must be the
   * primary one.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Resource resource = 5;</code>
   * @return Whether the resource field is set.
   */
  boolean hasResource();
  /**
   * <pre>
   * Represents a target resource that is involved with a network activity.
   * If multiple resources are involved with an activity, this must be the
   * primary one.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Resource resource = 5;</code>
   * @return The resource.
   */
  com.google.rpc.context.AttributeContext.Resource getResource();
  /**
   * <pre>
   * Represents a target resource that is involved with a network activity.
   * If multiple resources are involved with an activity, this must be the
   * primary one.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Resource resource = 5;</code>
   */
  com.google.rpc.context.AttributeContext.ResourceOrBuilder getResourceOrBuilder();

  /**
   * <pre>
   * Represents an API operation that is involved to a network activity.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Api api = 6;</code>
   * @return Whether the api field is set.
   */
  boolean hasApi();
  /**
   * <pre>
   * Represents an API operation that is involved to a network activity.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Api api = 6;</code>
   * @return The api.
   */
  com.google.rpc.context.AttributeContext.Api getApi();
  /**
   * <pre>
   * Represents an API operation that is involved to a network activity.
   * </pre>
   *
   * <code>.google.rpc.context.AttributeContext.Api api = 6;</code>
   */
  com.google.rpc.context.AttributeContext.ApiOrBuilder getApiOrBuilder();

  /**
   * <pre>
   * Supports extensions for advanced use cases, such as logs and metrics.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 8;</code>
   */
  java.util.List<com.google.protobuf.Any> 
      getExtensionsList();
  /**
   * <pre>
   * Supports extensions for advanced use cases, such as logs and metrics.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 8;</code>
   */
  com.google.protobuf.Any getExtensions(int index);
  /**
   * <pre>
   * Supports extensions for advanced use cases, such as logs and metrics.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 8;</code>
   */
  int getExtensionsCount();
  /**
   * <pre>
   * Supports extensions for advanced use cases, such as logs and metrics.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 8;</code>
   */
  java.util.List<? extends com.google.protobuf.AnyOrBuilder> 
      getExtensionsOrBuilderList();
  /**
   * <pre>
   * Supports extensions for advanced use cases, such as logs and metrics.
   * </pre>
   *
   * <code>repeated .google.protobuf.Any extensions = 8;</code>
   */
  com.google.protobuf.AnyOrBuilder getExtensionsOrBuilder(
      int index);
}