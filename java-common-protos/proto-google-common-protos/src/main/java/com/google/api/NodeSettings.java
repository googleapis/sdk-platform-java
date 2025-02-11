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
// source: google/api/client.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

/**
 *
 *
 * <pre>
 * Settings for Node client libraries.
 * </pre>
 *
 * Protobuf type {@code google.api.NodeSettings}
 */
public final class NodeSettings
    extends com.google.protobuf.GeneratedMessageLite<NodeSettings, NodeSettings.Builder>
    implements
    // @@protoc_insertion_point(message_implements:google.api.NodeSettings)
    NodeSettingsOrBuilder {
  private NodeSettings() {}

  private int bitField0_;
  public static final int COMMON_FIELD_NUMBER = 1;
  private com.google.api.CommonLanguageSettings common_;
  /**
   *
   *
   * <pre>
   * Some settings.
   * </pre>
   *
   * <code>.google.api.CommonLanguageSettings common = 1;</code>
   */
  @java.lang.Override
  public boolean hasCommon() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   *
   *
   * <pre>
   * Some settings.
   * </pre>
   *
   * <code>.google.api.CommonLanguageSettings common = 1;</code>
   */
  @java.lang.Override
  public com.google.api.CommonLanguageSettings getCommon() {
    return common_ == null ? com.google.api.CommonLanguageSettings.getDefaultInstance() : common_;
  }
  /**
   *
   *
   * <pre>
   * Some settings.
   * </pre>
   *
   * <code>.google.api.CommonLanguageSettings common = 1;</code>
   */
  private void setCommon(com.google.api.CommonLanguageSettings value) {
    value.getClass();
    common_ = value;
    bitField0_ |= 0x00000001;
  }
  /**
   *
   *
   * <pre>
   * Some settings.
   * </pre>
   *
   * <code>.google.api.CommonLanguageSettings common = 1;</code>
   */
  @java.lang.SuppressWarnings({"ReferenceEquality"})
  private void mergeCommon(com.google.api.CommonLanguageSettings value) {
    value.getClass();
    if (common_ != null && common_ != com.google.api.CommonLanguageSettings.getDefaultInstance()) {
      common_ =
          com.google.api.CommonLanguageSettings.newBuilder(common_).mergeFrom(value).buildPartial();
    } else {
      common_ = value;
    }
    bitField0_ |= 0x00000001;
  }
  /**
   *
   *
   * <pre>
   * Some settings.
   * </pre>
   *
   * <code>.google.api.CommonLanguageSettings common = 1;</code>
   */
  private void clearCommon() {
    common_ = null;
    bitField0_ = (bitField0_ & ~0x00000001);
  }

  public static com.google.api.NodeSettings parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.NodeSettings parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.NodeSettings parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.NodeSettings parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.NodeSettings parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.NodeSettings parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.NodeSettings parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.NodeSettings parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.NodeSettings parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.NodeSettings parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.NodeSettings parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.NodeSettings parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }

  public static Builder newBuilder(com.google.api.NodeSettings prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   *
   *
   * <pre>
   * Settings for Node client libraries.
   * </pre>
   *
   * Protobuf type {@code google.api.NodeSettings}
   */
  public static final class Builder
      extends com.google.protobuf.GeneratedMessageLite.Builder<com.google.api.NodeSettings, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.NodeSettings)
      com.google.api.NodeSettingsOrBuilder {
    // Construct using com.google.api.NodeSettings.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }

    /**
     *
     *
     * <pre>
     * Some settings.
     * </pre>
     *
     * <code>.google.api.CommonLanguageSettings common = 1;</code>
     */
    @java.lang.Override
    public boolean hasCommon() {
      return instance.hasCommon();
    }
    /**
     *
     *
     * <pre>
     * Some settings.
     * </pre>
     *
     * <code>.google.api.CommonLanguageSettings common = 1;</code>
     */
    @java.lang.Override
    public com.google.api.CommonLanguageSettings getCommon() {
      return instance.getCommon();
    }
    /**
     *
     *
     * <pre>
     * Some settings.
     * </pre>
     *
     * <code>.google.api.CommonLanguageSettings common = 1;</code>
     */
    public Builder setCommon(com.google.api.CommonLanguageSettings value) {
      copyOnWrite();
      instance.setCommon(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Some settings.
     * </pre>
     *
     * <code>.google.api.CommonLanguageSettings common = 1;</code>
     */
    public Builder setCommon(com.google.api.CommonLanguageSettings.Builder builderForValue) {
      copyOnWrite();
      instance.setCommon(builderForValue.build());
      return this;
    }
    /**
     *
     *
     * <pre>
     * Some settings.
     * </pre>
     *
     * <code>.google.api.CommonLanguageSettings common = 1;</code>
     */
    public Builder mergeCommon(com.google.api.CommonLanguageSettings value) {
      copyOnWrite();
      instance.mergeCommon(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Some settings.
     * </pre>
     *
     * <code>.google.api.CommonLanguageSettings common = 1;</code>
     */
    public Builder clearCommon() {
      copyOnWrite();
      instance.clearCommon();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.api.NodeSettings)
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
          return new com.google.api.NodeSettings();
        }
      case NEW_BUILDER:
        {
          return new Builder();
        }
      case BUILD_MESSAGE_INFO:
        {
          java.lang.Object[] objects =
              new java.lang.Object[] {
                "bitField0_", "common_",
              };
          java.lang.String info =
              "\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u1009\u0000";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
      case GET_DEFAULT_INSTANCE:
        {
          return DEFAULT_INSTANCE;
        }
      case GET_PARSER:
        {
          com.google.protobuf.Parser<com.google.api.NodeSettings> parser = PARSER;
          if (parser == null) {
            synchronized (com.google.api.NodeSettings.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<com.google.api.NodeSettings>(DEFAULT_INSTANCE);
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

  // @@protoc_insertion_point(class_scope:google.api.NodeSettings)
  private static final com.google.api.NodeSettings DEFAULT_INSTANCE;

  static {
    NodeSettings defaultInstance = new NodeSettings();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        NodeSettings.class, defaultInstance);
  }

  public static com.google.api.NodeSettings getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<NodeSettings> PARSER;

  public static com.google.protobuf.Parser<NodeSettings> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
