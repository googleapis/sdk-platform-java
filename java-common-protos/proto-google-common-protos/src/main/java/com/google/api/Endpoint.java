/*
 * Copyright 2025 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/api/endpoint.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

/**
 *
 *
 * <pre>
 * `Endpoint` describes a network address of a service that serves a set of
 * APIs. It is commonly known as a service endpoint. A service may expose
 * any number of service endpoints, and all service endpoints share the same
 * service definition, such as quota limits and monitoring metrics.
 *
 * Example:
 *
 *     type: google.api.Service
 *     name: library-example.googleapis.com
 *     endpoints:
 *       # Declares network address `https://library-example.googleapis.com`
 *       # for service `library-example.googleapis.com`. The `https` scheme
 *       # is implicit for all service endpoints. Other schemes may be
 *       # supported in the future.
 *     - name: library-example.googleapis.com
 *       allow_cors: false
 *     - name: content-staging-library-example.googleapis.com
 *       # Allows HTTP OPTIONS calls to be passed to the API frontend, for it
 *       # to decide whether the subsequent cross-origin request is allowed
 *       # to proceed.
 *       allow_cors: true
 * </pre>
 *
 * Protobuf type {@code google.api.Endpoint}
 */
public final class Endpoint
    extends com.google.protobuf.GeneratedMessageLite<Endpoint, Endpoint.Builder>
    implements
    // @@protoc_insertion_point(message_implements:google.api.Endpoint)
    EndpointOrBuilder {
  private Endpoint() {
    name_ = "";
    aliases_ = com.google.protobuf.GeneratedMessageLite.emptyProtobufList();
    target_ = "";
  }

  public static final int NAME_FIELD_NUMBER = 1;
  private java.lang.String name_;
  /**
   *
   *
   * <pre>
   * The canonical name of this endpoint.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The name.
   */
  @java.lang.Override
  public java.lang.String getName() {
    return name_;
  }
  /**
   *
   *
   * <pre>
   * The canonical name of this endpoint.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The bytes for name.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getNameBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(name_);
  }
  /**
   *
   *
   * <pre>
   * The canonical name of this endpoint.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @param value The name to set.
   */
  private void setName(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();

    name_ = value;
  }
  /**
   *
   *
   * <pre>
   * The canonical name of this endpoint.
   * </pre>
   *
   * <code>string name = 1;</code>
   */
  private void clearName() {

    name_ = getDefaultInstance().getName();
  }
  /**
   *
   *
   * <pre>
   * The canonical name of this endpoint.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @param value The bytes for name to set.
   */
  private void setNameBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    name_ = value.toStringUtf8();
  }

  public static final int ALIASES_FIELD_NUMBER = 2;
  private com.google.protobuf.Internal.ProtobufList<java.lang.String> aliases_;
  /**
   *
   *
   * <pre>
   * Aliases for this endpoint, these will be served by the same UrlMap as the
   * parent endpoint, and will be provisioned in the GCP stack for the Regional
   * Endpoints.
   * </pre>
   *
   * <code>repeated string aliases = 2;</code>
   *
   * @return A list containing the aliases.
   */
  @java.lang.Override
  public java.util.List<java.lang.String> getAliasesList() {
    return aliases_;
  }
  /**
   *
   *
   * <pre>
   * Aliases for this endpoint, these will be served by the same UrlMap as the
   * parent endpoint, and will be provisioned in the GCP stack for the Regional
   * Endpoints.
   * </pre>
   *
   * <code>repeated string aliases = 2;</code>
   *
   * @return The count of aliases.
   */
  @java.lang.Override
  public int getAliasesCount() {
    return aliases_.size();
  }
  /**
   *
   *
   * <pre>
   * Aliases for this endpoint, these will be served by the same UrlMap as the
   * parent endpoint, and will be provisioned in the GCP stack for the Regional
   * Endpoints.
   * </pre>
   *
   * <code>repeated string aliases = 2;</code>
   *
   * @param index The index of the element to return.
   * @return The aliases at the given index.
   */
  @java.lang.Override
  public java.lang.String getAliases(int index) {
    return aliases_.get(index);
  }
  /**
   *
   *
   * <pre>
   * Aliases for this endpoint, these will be served by the same UrlMap as the
   * parent endpoint, and will be provisioned in the GCP stack for the Regional
   * Endpoints.
   * </pre>
   *
   * <code>repeated string aliases = 2;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the aliases at the given index.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getAliasesBytes(int index) {
    return com.google.protobuf.ByteString.copyFromUtf8(aliases_.get(index));
  }

  private void ensureAliasesIsMutable() {
    com.google.protobuf.Internal.ProtobufList<java.lang.String> tmp = aliases_;
    if (!tmp.isModifiable()) {
      aliases_ = com.google.protobuf.GeneratedMessageLite.mutableCopy(tmp);
    }
  }
  /**
   *
   *
   * <pre>
   * Aliases for this endpoint, these will be served by the same UrlMap as the
   * parent endpoint, and will be provisioned in the GCP stack for the Regional
   * Endpoints.
   * </pre>
   *
   * <code>repeated string aliases = 2;</code>
   *
   * @param index The index to set the value at.
   * @param value The aliases to set.
   */
  private void setAliases(int index, java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();
    ensureAliasesIsMutable();
    aliases_.set(index, value);
  }
  /**
   *
   *
   * <pre>
   * Aliases for this endpoint, these will be served by the same UrlMap as the
   * parent endpoint, and will be provisioned in the GCP stack for the Regional
   * Endpoints.
   * </pre>
   *
   * <code>repeated string aliases = 2;</code>
   *
   * @param value The aliases to add.
   */
  private void addAliases(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();
    ensureAliasesIsMutable();
    aliases_.add(value);
  }
  /**
   *
   *
   * <pre>
   * Aliases for this endpoint, these will be served by the same UrlMap as the
   * parent endpoint, and will be provisioned in the GCP stack for the Regional
   * Endpoints.
   * </pre>
   *
   * <code>repeated string aliases = 2;</code>
   *
   * @param values The aliases to add.
   */
  private void addAllAliases(java.lang.Iterable<java.lang.String> values) {
    ensureAliasesIsMutable();
    com.google.protobuf.AbstractMessageLite.addAll(values, aliases_);
  }
  /**
   *
   *
   * <pre>
   * Aliases for this endpoint, these will be served by the same UrlMap as the
   * parent endpoint, and will be provisioned in the GCP stack for the Regional
   * Endpoints.
   * </pre>
   *
   * <code>repeated string aliases = 2;</code>
   */
  private void clearAliases() {
    aliases_ = com.google.protobuf.GeneratedMessageLite.emptyProtobufList();
  }
  /**
   *
   *
   * <pre>
   * Aliases for this endpoint, these will be served by the same UrlMap as the
   * parent endpoint, and will be provisioned in the GCP stack for the Regional
   * Endpoints.
   * </pre>
   *
   * <code>repeated string aliases = 2;</code>
   *
   * @param value The bytes of the aliases to add.
   */
  private void addAliasesBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    ensureAliasesIsMutable();
    aliases_.add(value.toStringUtf8());
  }

  public static final int TARGET_FIELD_NUMBER = 101;
  private java.lang.String target_;
  /**
   *
   *
   * <pre>
   * The specification of an Internet routable address of API frontend that will
   * handle requests to this [API
   * Endpoint](https://cloud.google.com/apis/design/glossary). It should be
   * either a valid IPv4 address or a fully-qualified domain name. For example,
   * "8.8.8.8" or "myservice.appspot.com".
   * </pre>
   *
   * <code>string target = 101;</code>
   *
   * @return The target.
   */
  @java.lang.Override
  public java.lang.String getTarget() {
    return target_;
  }
  /**
   *
   *
   * <pre>
   * The specification of an Internet routable address of API frontend that will
   * handle requests to this [API
   * Endpoint](https://cloud.google.com/apis/design/glossary). It should be
   * either a valid IPv4 address or a fully-qualified domain name. For example,
   * "8.8.8.8" or "myservice.appspot.com".
   * </pre>
   *
   * <code>string target = 101;</code>
   *
   * @return The bytes for target.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getTargetBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(target_);
  }
  /**
   *
   *
   * <pre>
   * The specification of an Internet routable address of API frontend that will
   * handle requests to this [API
   * Endpoint](https://cloud.google.com/apis/design/glossary). It should be
   * either a valid IPv4 address or a fully-qualified domain name. For example,
   * "8.8.8.8" or "myservice.appspot.com".
   * </pre>
   *
   * <code>string target = 101;</code>
   *
   * @param value The target to set.
   */
  private void setTarget(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();

    target_ = value;
  }
  /**
   *
   *
   * <pre>
   * The specification of an Internet routable address of API frontend that will
   * handle requests to this [API
   * Endpoint](https://cloud.google.com/apis/design/glossary). It should be
   * either a valid IPv4 address or a fully-qualified domain name. For example,
   * "8.8.8.8" or "myservice.appspot.com".
   * </pre>
   *
   * <code>string target = 101;</code>
   */
  private void clearTarget() {

    target_ = getDefaultInstance().getTarget();
  }
  /**
   *
   *
   * <pre>
   * The specification of an Internet routable address of API frontend that will
   * handle requests to this [API
   * Endpoint](https://cloud.google.com/apis/design/glossary). It should be
   * either a valid IPv4 address or a fully-qualified domain name. For example,
   * "8.8.8.8" or "myservice.appspot.com".
   * </pre>
   *
   * <code>string target = 101;</code>
   *
   * @param value The bytes for target to set.
   */
  private void setTargetBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    target_ = value.toStringUtf8();
  }

  public static final int ALLOW_CORS_FIELD_NUMBER = 5;
  private boolean allowCors_;
  /**
   *
   *
   * <pre>
   * Allowing
   * [CORS](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing), aka
   * cross-domain traffic, would allow the backends served from this endpoint to
   * receive and respond to HTTP OPTIONS requests. The response will be used by
   * the browser to determine whether the subsequent cross-origin request is
   * allowed to proceed.
   * </pre>
   *
   * <code>bool allow_cors = 5;</code>
   *
   * @return The allowCors.
   */
  @java.lang.Override
  public boolean getAllowCors() {
    return allowCors_;
  }
  /**
   *
   *
   * <pre>
   * Allowing
   * [CORS](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing), aka
   * cross-domain traffic, would allow the backends served from this endpoint to
   * receive and respond to HTTP OPTIONS requests. The response will be used by
   * the browser to determine whether the subsequent cross-origin request is
   * allowed to proceed.
   * </pre>
   *
   * <code>bool allow_cors = 5;</code>
   *
   * @param value The allowCors to set.
   */
  private void setAllowCors(boolean value) {

    allowCors_ = value;
  }
  /**
   *
   *
   * <pre>
   * Allowing
   * [CORS](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing), aka
   * cross-domain traffic, would allow the backends served from this endpoint to
   * receive and respond to HTTP OPTIONS requests. The response will be used by
   * the browser to determine whether the subsequent cross-origin request is
   * allowed to proceed.
   * </pre>
   *
   * <code>bool allow_cors = 5;</code>
   */
  private void clearAllowCors() {

    allowCors_ = false;
  }

  public static com.google.api.Endpoint parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.Endpoint parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.Endpoint parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.Endpoint parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.Endpoint parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.Endpoint parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.Endpoint parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.Endpoint parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.Endpoint parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.Endpoint parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.Endpoint parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.Endpoint parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }

  public static Builder newBuilder(com.google.api.Endpoint prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   *
   *
   * <pre>
   * `Endpoint` describes a network address of a service that serves a set of
   * APIs. It is commonly known as a service endpoint. A service may expose
   * any number of service endpoints, and all service endpoints share the same
   * service definition, such as quota limits and monitoring metrics.
   *
   * Example:
   *
   *     type: google.api.Service
   *     name: library-example.googleapis.com
   *     endpoints:
   *       # Declares network address `https://library-example.googleapis.com`
   *       # for service `library-example.googleapis.com`. The `https` scheme
   *       # is implicit for all service endpoints. Other schemes may be
   *       # supported in the future.
   *     - name: library-example.googleapis.com
   *       allow_cors: false
   *     - name: content-staging-library-example.googleapis.com
   *       # Allows HTTP OPTIONS calls to be passed to the API frontend, for it
   *       # to decide whether the subsequent cross-origin request is allowed
   *       # to proceed.
   *       allow_cors: true
   * </pre>
   *
   * Protobuf type {@code google.api.Endpoint}
   */
  public static final class Builder
      extends com.google.protobuf.GeneratedMessageLite.Builder<com.google.api.Endpoint, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.Endpoint)
      com.google.api.EndpointOrBuilder {
    // Construct using com.google.api.Endpoint.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }

    /**
     *
     *
     * <pre>
     * The canonical name of this endpoint.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return The name.
     */
    @java.lang.Override
    public java.lang.String getName() {
      return instance.getName();
    }
    /**
     *
     *
     * <pre>
     * The canonical name of this endpoint.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return The bytes for name.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getNameBytes() {
      return instance.getNameBytes();
    }
    /**
     *
     *
     * <pre>
     * The canonical name of this endpoint.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @param value The name to set.
     * @return This builder for chaining.
     */
    public Builder setName(java.lang.String value) {
      copyOnWrite();
      instance.setName(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The canonical name of this endpoint.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearName() {
      copyOnWrite();
      instance.clearName();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The canonical name of this endpoint.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @param value The bytes for name to set.
     * @return This builder for chaining.
     */
    public Builder setNameBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setNameBytes(value);
      return this;
    }

    /**
     *
     *
     * <pre>
     * Aliases for this endpoint, these will be served by the same UrlMap as the
     * parent endpoint, and will be provisioned in the GCP stack for the Regional
     * Endpoints.
     * </pre>
     *
     * <code>repeated string aliases = 2;</code>
     *
     * @return A list containing the aliases.
     */
    @java.lang.Override
    public java.util.List<java.lang.String> getAliasesList() {
      return java.util.Collections.unmodifiableList(instance.getAliasesList());
    }
    /**
     *
     *
     * <pre>
     * Aliases for this endpoint, these will be served by the same UrlMap as the
     * parent endpoint, and will be provisioned in the GCP stack for the Regional
     * Endpoints.
     * </pre>
     *
     * <code>repeated string aliases = 2;</code>
     *
     * @return The count of aliases.
     */
    @java.lang.Override
    public int getAliasesCount() {
      return instance.getAliasesCount();
    }
    /**
     *
     *
     * <pre>
     * Aliases for this endpoint, these will be served by the same UrlMap as the
     * parent endpoint, and will be provisioned in the GCP stack for the Regional
     * Endpoints.
     * </pre>
     *
     * <code>repeated string aliases = 2;</code>
     *
     * @param index The index of the element to return.
     * @return The aliases at the given index.
     */
    @java.lang.Override
    public java.lang.String getAliases(int index) {
      return instance.getAliases(index);
    }
    /**
     *
     *
     * <pre>
     * Aliases for this endpoint, these will be served by the same UrlMap as the
     * parent endpoint, and will be provisioned in the GCP stack for the Regional
     * Endpoints.
     * </pre>
     *
     * <code>repeated string aliases = 2;</code>
     *
     * @param index The index of the value to return.
     * @return The bytes of the aliases at the given index.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getAliasesBytes(int index) {
      return instance.getAliasesBytes(index);
    }
    /**
     *
     *
     * <pre>
     * Aliases for this endpoint, these will be served by the same UrlMap as the
     * parent endpoint, and will be provisioned in the GCP stack for the Regional
     * Endpoints.
     * </pre>
     *
     * <code>repeated string aliases = 2;</code>
     *
     * @param index The index to set the value at.
     * @param value The aliases to set.
     * @return This builder for chaining.
     */
    public Builder setAliases(int index, java.lang.String value) {
      copyOnWrite();
      instance.setAliases(index, value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Aliases for this endpoint, these will be served by the same UrlMap as the
     * parent endpoint, and will be provisioned in the GCP stack for the Regional
     * Endpoints.
     * </pre>
     *
     * <code>repeated string aliases = 2;</code>
     *
     * @param value The aliases to add.
     * @return This builder for chaining.
     */
    public Builder addAliases(java.lang.String value) {
      copyOnWrite();
      instance.addAliases(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Aliases for this endpoint, these will be served by the same UrlMap as the
     * parent endpoint, and will be provisioned in the GCP stack for the Regional
     * Endpoints.
     * </pre>
     *
     * <code>repeated string aliases = 2;</code>
     *
     * @param values The aliases to add.
     * @return This builder for chaining.
     */
    public Builder addAllAliases(java.lang.Iterable<java.lang.String> values) {
      copyOnWrite();
      instance.addAllAliases(values);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Aliases for this endpoint, these will be served by the same UrlMap as the
     * parent endpoint, and will be provisioned in the GCP stack for the Regional
     * Endpoints.
     * </pre>
     *
     * <code>repeated string aliases = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearAliases() {
      copyOnWrite();
      instance.clearAliases();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Aliases for this endpoint, these will be served by the same UrlMap as the
     * parent endpoint, and will be provisioned in the GCP stack for the Regional
     * Endpoints.
     * </pre>
     *
     * <code>repeated string aliases = 2;</code>
     *
     * @param value The bytes of the aliases to add.
     * @return This builder for chaining.
     */
    public Builder addAliasesBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.addAliasesBytes(value);
      return this;
    }

    /**
     *
     *
     * <pre>
     * The specification of an Internet routable address of API frontend that will
     * handle requests to this [API
     * Endpoint](https://cloud.google.com/apis/design/glossary). It should be
     * either a valid IPv4 address or a fully-qualified domain name. For example,
     * "8.8.8.8" or "myservice.appspot.com".
     * </pre>
     *
     * <code>string target = 101;</code>
     *
     * @return The target.
     */
    @java.lang.Override
    public java.lang.String getTarget() {
      return instance.getTarget();
    }
    /**
     *
     *
     * <pre>
     * The specification of an Internet routable address of API frontend that will
     * handle requests to this [API
     * Endpoint](https://cloud.google.com/apis/design/glossary). It should be
     * either a valid IPv4 address or a fully-qualified domain name. For example,
     * "8.8.8.8" or "myservice.appspot.com".
     * </pre>
     *
     * <code>string target = 101;</code>
     *
     * @return The bytes for target.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getTargetBytes() {
      return instance.getTargetBytes();
    }
    /**
     *
     *
     * <pre>
     * The specification of an Internet routable address of API frontend that will
     * handle requests to this [API
     * Endpoint](https://cloud.google.com/apis/design/glossary). It should be
     * either a valid IPv4 address or a fully-qualified domain name. For example,
     * "8.8.8.8" or "myservice.appspot.com".
     * </pre>
     *
     * <code>string target = 101;</code>
     *
     * @param value The target to set.
     * @return This builder for chaining.
     */
    public Builder setTarget(java.lang.String value) {
      copyOnWrite();
      instance.setTarget(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The specification of an Internet routable address of API frontend that will
     * handle requests to this [API
     * Endpoint](https://cloud.google.com/apis/design/glossary). It should be
     * either a valid IPv4 address or a fully-qualified domain name. For example,
     * "8.8.8.8" or "myservice.appspot.com".
     * </pre>
     *
     * <code>string target = 101;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTarget() {
      copyOnWrite();
      instance.clearTarget();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The specification of an Internet routable address of API frontend that will
     * handle requests to this [API
     * Endpoint](https://cloud.google.com/apis/design/glossary). It should be
     * either a valid IPv4 address or a fully-qualified domain name. For example,
     * "8.8.8.8" or "myservice.appspot.com".
     * </pre>
     *
     * <code>string target = 101;</code>
     *
     * @param value The bytes for target to set.
     * @return This builder for chaining.
     */
    public Builder setTargetBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setTargetBytes(value);
      return this;
    }

    /**
     *
     *
     * <pre>
     * Allowing
     * [CORS](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing), aka
     * cross-domain traffic, would allow the backends served from this endpoint to
     * receive and respond to HTTP OPTIONS requests. The response will be used by
     * the browser to determine whether the subsequent cross-origin request is
     * allowed to proceed.
     * </pre>
     *
     * <code>bool allow_cors = 5;</code>
     *
     * @return The allowCors.
     */
    @java.lang.Override
    public boolean getAllowCors() {
      return instance.getAllowCors();
    }
    /**
     *
     *
     * <pre>
     * Allowing
     * [CORS](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing), aka
     * cross-domain traffic, would allow the backends served from this endpoint to
     * receive and respond to HTTP OPTIONS requests. The response will be used by
     * the browser to determine whether the subsequent cross-origin request is
     * allowed to proceed.
     * </pre>
     *
     * <code>bool allow_cors = 5;</code>
     *
     * @param value The allowCors to set.
     * @return This builder for chaining.
     */
    public Builder setAllowCors(boolean value) {
      copyOnWrite();
      instance.setAllowCors(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Allowing
     * [CORS](https://en.wikipedia.org/wiki/Cross-origin_resource_sharing), aka
     * cross-domain traffic, would allow the backends served from this endpoint to
     * receive and respond to HTTP OPTIONS requests. The response will be used by
     * the browser to determine whether the subsequent cross-origin request is
     * allowed to proceed.
     * </pre>
     *
     * <code>bool allow_cors = 5;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearAllowCors() {
      copyOnWrite();
      instance.clearAllowCors();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.api.Endpoint)
  }

  @java.lang.Override
  @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
  protected final java.lang.Object dynamicMethod(
      com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
      java.lang.Object arg0,
      java.lang.Object arg1) {
    switch (method) {
      case NEW_MUTABLE_INSTANCE:
        {
          return new com.google.api.Endpoint();
        }
      case NEW_BUILDER:
        {
          return new Builder();
        }
      case BUILD_MESSAGE_INFO:
        {
          java.lang.Object[] objects =
              new java.lang.Object[] {
                "name_", "aliases_", "allowCors_", "target_",
              };
          java.lang.String info =
              "\u0000\u0004\u0000\u0000\u0001e\u0004\u0000\u0001\u0000\u0001\u0208\u0002\u021a\u0005"
                  + "\u0007e\u0208";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
      case GET_DEFAULT_INSTANCE:
        {
          return DEFAULT_INSTANCE;
        }
      case GET_PARSER:
        {
          com.google.protobuf.Parser<com.google.api.Endpoint> parser = PARSER;
          if (parser == null) {
            synchronized (com.google.api.Endpoint.class) {
              parser = PARSER;
              if (parser == null) {
                parser = new DefaultInstanceBasedParser<com.google.api.Endpoint>(DEFAULT_INSTANCE);
                PARSER = parser;
              }
            }
          }
          return parser;
        }
      case GET_MEMOIZED_IS_INITIALIZED:
        {
          return (byte) 1;
        }
      case SET_MEMOIZED_IS_INITIALIZED:
        {
          return null;
        }
    }
    throw new UnsupportedOperationException();
  }

  // @@protoc_insertion_point(class_scope:google.api.Endpoint)
  private static final com.google.api.Endpoint DEFAULT_INSTANCE;

  static {
    Endpoint defaultInstance = new Endpoint();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        Endpoint.class, defaultInstance);
  }

  public static com.google.api.Endpoint getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<Endpoint> PARSER;

  public static com.google.protobuf.Parser<Endpoint> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
