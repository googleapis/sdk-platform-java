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
// source: google/iam/v1/iam_policy.proto

// Protobuf Java Version: 3.25.5
package com.google.iam.v1;

/**
 *
 *
 * <pre>
 * Request message for `GetIamPolicy` method.
 * </pre>
 *
 * Protobuf type {@code google.iam.v1.GetIamPolicyRequest}
 */
public final class GetIamPolicyRequest
    extends com.google.protobuf.GeneratedMessageLite<
        GetIamPolicyRequest, GetIamPolicyRequest.Builder>
    implements
    // @@protoc_insertion_point(message_implements:google.iam.v1.GetIamPolicyRequest)
    GetIamPolicyRequestOrBuilder {
  private GetIamPolicyRequest() {
    resource_ = "";
  }

  private int bitField0_;
  public static final int RESOURCE_FIELD_NUMBER = 1;
  private java.lang.String resource_;
  /**
   *
   *
   * <pre>
   * REQUIRED: The resource for which the policy is being requested.
   * See the operation documentation for the appropriate value for this field.
   * </pre>
   *
   * <code>
   * string resource = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @return The resource.
   */
  @java.lang.Override
  public java.lang.String getResource() {
    return resource_;
  }
  /**
   *
   *
   * <pre>
   * REQUIRED: The resource for which the policy is being requested.
   * See the operation documentation for the appropriate value for this field.
   * </pre>
   *
   * <code>
   * string resource = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @return The bytes for resource.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getResourceBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(resource_);
  }
  /**
   *
   *
   * <pre>
   * REQUIRED: The resource for which the policy is being requested.
   * See the operation documentation for the appropriate value for this field.
   * </pre>
   *
   * <code>
   * string resource = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @param value The resource to set.
   */
  private void setResource(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();

    resource_ = value;
  }
  /**
   *
   *
   * <pre>
   * REQUIRED: The resource for which the policy is being requested.
   * See the operation documentation for the appropriate value for this field.
   * </pre>
   *
   * <code>
   * string resource = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   */
  private void clearResource() {

    resource_ = getDefaultInstance().getResource();
  }
  /**
   *
   *
   * <pre>
   * REQUIRED: The resource for which the policy is being requested.
   * See the operation documentation for the appropriate value for this field.
   * </pre>
   *
   * <code>
   * string resource = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
   * </code>
   *
   * @param value The bytes for resource to set.
   */
  private void setResourceBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    resource_ = value.toStringUtf8();
  }

  public static final int OPTIONS_FIELD_NUMBER = 2;
  private com.google.iam.v1.GetPolicyOptions options_;
  /**
   *
   *
   * <pre>
   * OPTIONAL: A `GetPolicyOptions` object for specifying options to
   * `GetIamPolicy`.
   * </pre>
   *
   * <code>.google.iam.v1.GetPolicyOptions options = 2;</code>
   */
  @java.lang.Override
  public boolean hasOptions() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   *
   *
   * <pre>
   * OPTIONAL: A `GetPolicyOptions` object for specifying options to
   * `GetIamPolicy`.
   * </pre>
   *
   * <code>.google.iam.v1.GetPolicyOptions options = 2;</code>
   */
  @java.lang.Override
  public com.google.iam.v1.GetPolicyOptions getOptions() {
    return options_ == null ? com.google.iam.v1.GetPolicyOptions.getDefaultInstance() : options_;
  }
  /**
   *
   *
   * <pre>
   * OPTIONAL: A `GetPolicyOptions` object for specifying options to
   * `GetIamPolicy`.
   * </pre>
   *
   * <code>.google.iam.v1.GetPolicyOptions options = 2;</code>
   */
  private void setOptions(com.google.iam.v1.GetPolicyOptions value) {
    value.getClass();
    options_ = value;
    bitField0_ |= 0x00000001;
  }
  /**
   *
   *
   * <pre>
   * OPTIONAL: A `GetPolicyOptions` object for specifying options to
   * `GetIamPolicy`.
   * </pre>
   *
   * <code>.google.iam.v1.GetPolicyOptions options = 2;</code>
   */
  @java.lang.SuppressWarnings({"ReferenceEquality"})
  private void mergeOptions(com.google.iam.v1.GetPolicyOptions value) {
    value.getClass();
    if (options_ != null && options_ != com.google.iam.v1.GetPolicyOptions.getDefaultInstance()) {
      options_ =
          com.google.iam.v1.GetPolicyOptions.newBuilder(options_).mergeFrom(value).buildPartial();
    } else {
      options_ = value;
    }
    bitField0_ |= 0x00000001;
  }
  /**
   *
   *
   * <pre>
   * OPTIONAL: A `GetPolicyOptions` object for specifying options to
   * `GetIamPolicy`.
   * </pre>
   *
   * <code>.google.iam.v1.GetPolicyOptions options = 2;</code>
   */
  private void clearOptions() {
    options_ = null;
    bitField0_ = (bitField0_ & ~0x00000001);
  }

  public static com.google.iam.v1.GetIamPolicyRequest parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.iam.v1.GetIamPolicyRequest parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.iam.v1.GetIamPolicyRequest parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.iam.v1.GetIamPolicyRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.iam.v1.GetIamPolicyRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.iam.v1.GetIamPolicyRequest parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.iam.v1.GetIamPolicyRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.iam.v1.GetIamPolicyRequest parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.iam.v1.GetIamPolicyRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.iam.v1.GetIamPolicyRequest parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.iam.v1.GetIamPolicyRequest parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.iam.v1.GetIamPolicyRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }

  public static Builder newBuilder(com.google.iam.v1.GetIamPolicyRequest prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   *
   *
   * <pre>
   * Request message for `GetIamPolicy` method.
   * </pre>
   *
   * Protobuf type {@code google.iam.v1.GetIamPolicyRequest}
   */
  public static final class Builder
      extends com.google.protobuf.GeneratedMessageLite.Builder<
          com.google.iam.v1.GetIamPolicyRequest, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.iam.v1.GetIamPolicyRequest)
      com.google.iam.v1.GetIamPolicyRequestOrBuilder {
    // Construct using com.google.iam.v1.GetIamPolicyRequest.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }

    /**
     *
     *
     * <pre>
     * REQUIRED: The resource for which the policy is being requested.
     * See the operation documentation for the appropriate value for this field.
     * </pre>
     *
     * <code>
     * string resource = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
     * </code>
     *
     * @return The resource.
     */
    @java.lang.Override
    public java.lang.String getResource() {
      return instance.getResource();
    }
    /**
     *
     *
     * <pre>
     * REQUIRED: The resource for which the policy is being requested.
     * See the operation documentation for the appropriate value for this field.
     * </pre>
     *
     * <code>
     * string resource = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
     * </code>
     *
     * @return The bytes for resource.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getResourceBytes() {
      return instance.getResourceBytes();
    }
    /**
     *
     *
     * <pre>
     * REQUIRED: The resource for which the policy is being requested.
     * See the operation documentation for the appropriate value for this field.
     * </pre>
     *
     * <code>
     * string resource = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
     * </code>
     *
     * @param value The resource to set.
     * @return This builder for chaining.
     */
    public Builder setResource(java.lang.String value) {
      copyOnWrite();
      instance.setResource(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * REQUIRED: The resource for which the policy is being requested.
     * See the operation documentation for the appropriate value for this field.
     * </pre>
     *
     * <code>
     * string resource = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
     * </code>
     *
     * @return This builder for chaining.
     */
    public Builder clearResource() {
      copyOnWrite();
      instance.clearResource();
      return this;
    }
    /**
     *
     *
     * <pre>
     * REQUIRED: The resource for which the policy is being requested.
     * See the operation documentation for the appropriate value for this field.
     * </pre>
     *
     * <code>
     * string resource = 1 [(.google.api.field_behavior) = REQUIRED, (.google.api.resource_reference) = { ... }
     * </code>
     *
     * @param value The bytes for resource to set.
     * @return This builder for chaining.
     */
    public Builder setResourceBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setResourceBytes(value);
      return this;
    }

    /**
     *
     *
     * <pre>
     * OPTIONAL: A `GetPolicyOptions` object for specifying options to
     * `GetIamPolicy`.
     * </pre>
     *
     * <code>.google.iam.v1.GetPolicyOptions options = 2;</code>
     */
    @java.lang.Override
    public boolean hasOptions() {
      return instance.hasOptions();
    }
    /**
     *
     *
     * <pre>
     * OPTIONAL: A `GetPolicyOptions` object for specifying options to
     * `GetIamPolicy`.
     * </pre>
     *
     * <code>.google.iam.v1.GetPolicyOptions options = 2;</code>
     */
    @java.lang.Override
    public com.google.iam.v1.GetPolicyOptions getOptions() {
      return instance.getOptions();
    }
    /**
     *
     *
     * <pre>
     * OPTIONAL: A `GetPolicyOptions` object for specifying options to
     * `GetIamPolicy`.
     * </pre>
     *
     * <code>.google.iam.v1.GetPolicyOptions options = 2;</code>
     */
    public Builder setOptions(com.google.iam.v1.GetPolicyOptions value) {
      copyOnWrite();
      instance.setOptions(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * OPTIONAL: A `GetPolicyOptions` object for specifying options to
     * `GetIamPolicy`.
     * </pre>
     *
     * <code>.google.iam.v1.GetPolicyOptions options = 2;</code>
     */
    public Builder setOptions(com.google.iam.v1.GetPolicyOptions.Builder builderForValue) {
      copyOnWrite();
      instance.setOptions(builderForValue.build());
      return this;
    }
    /**
     *
     *
     * <pre>
     * OPTIONAL: A `GetPolicyOptions` object for specifying options to
     * `GetIamPolicy`.
     * </pre>
     *
     * <code>.google.iam.v1.GetPolicyOptions options = 2;</code>
     */
    public Builder mergeOptions(com.google.iam.v1.GetPolicyOptions value) {
      copyOnWrite();
      instance.mergeOptions(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * OPTIONAL: A `GetPolicyOptions` object for specifying options to
     * `GetIamPolicy`.
     * </pre>
     *
     * <code>.google.iam.v1.GetPolicyOptions options = 2;</code>
     */
    public Builder clearOptions() {
      copyOnWrite();
      instance.clearOptions();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.iam.v1.GetIamPolicyRequest)
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
          return new com.google.iam.v1.GetIamPolicyRequest();
        }
      case NEW_BUILDER:
        {
          return new Builder();
        }
      case BUILD_MESSAGE_INFO:
        {
          java.lang.Object[] objects =
              new java.lang.Object[] {
                "bitField0_", "resource_", "options_",
              };
          java.lang.String info =
              "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0208\u0002\u1009"
                  + "\u0000";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
      case GET_DEFAULT_INSTANCE:
        {
          return DEFAULT_INSTANCE;
        }
      case GET_PARSER:
        {
          com.google.protobuf.Parser<com.google.iam.v1.GetIamPolicyRequest> parser = PARSER;
          if (parser == null) {
            synchronized (com.google.iam.v1.GetIamPolicyRequest.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<com.google.iam.v1.GetIamPolicyRequest>(
                        DEFAULT_INSTANCE);
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

  // @@protoc_insertion_point(class_scope:google.iam.v1.GetIamPolicyRequest)
  private static final com.google.iam.v1.GetIamPolicyRequest DEFAULT_INSTANCE;

  static {
    GetIamPolicyRequest defaultInstance = new GetIamPolicyRequest();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        GetIamPolicyRequest.class, defaultInstance);
  }

  public static com.google.iam.v1.GetIamPolicyRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<GetIamPolicyRequest> PARSER;

  public static com.google.protobuf.Parser<GetIamPolicyRequest> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
