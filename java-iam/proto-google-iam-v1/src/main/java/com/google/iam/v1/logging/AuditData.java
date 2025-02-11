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
// source: google/iam/v1/logging/audit_data.proto

// Protobuf Java Version: 3.25.5
package com.google.iam.v1.logging;

/**
 *
 *
 * <pre>
 * Audit log information specific to Cloud IAM. This message is serialized
 * as an `Any` type in the `ServiceData` message of an
 * `AuditLog` message.
 * </pre>
 *
 * Protobuf type {@code google.iam.v1.logging.AuditData}
 */
public final class AuditData
    extends com.google.protobuf.GeneratedMessageLite<AuditData, AuditData.Builder>
    implements
    // @@protoc_insertion_point(message_implements:google.iam.v1.logging.AuditData)
    AuditDataOrBuilder {
  private AuditData() {}

  private int bitField0_;
  public static final int POLICY_DELTA_FIELD_NUMBER = 2;
  private com.google.iam.v1.PolicyDelta policyDelta_;
  /**
   *
   *
   * <pre>
   * Policy delta between the original policy and the newly set policy.
   * </pre>
   *
   * <code>.google.iam.v1.PolicyDelta policy_delta = 2;</code>
   */
  @java.lang.Override
  public boolean hasPolicyDelta() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   *
   *
   * <pre>
   * Policy delta between the original policy and the newly set policy.
   * </pre>
   *
   * <code>.google.iam.v1.PolicyDelta policy_delta = 2;</code>
   */
  @java.lang.Override
  public com.google.iam.v1.PolicyDelta getPolicyDelta() {
    return policyDelta_ == null ? com.google.iam.v1.PolicyDelta.getDefaultInstance() : policyDelta_;
  }
  /**
   *
   *
   * <pre>
   * Policy delta between the original policy and the newly set policy.
   * </pre>
   *
   * <code>.google.iam.v1.PolicyDelta policy_delta = 2;</code>
   */
  private void setPolicyDelta(com.google.iam.v1.PolicyDelta value) {
    value.getClass();
    policyDelta_ = value;
    bitField0_ |= 0x00000001;
  }
  /**
   *
   *
   * <pre>
   * Policy delta between the original policy and the newly set policy.
   * </pre>
   *
   * <code>.google.iam.v1.PolicyDelta policy_delta = 2;</code>
   */
  @java.lang.SuppressWarnings({"ReferenceEquality"})
  private void mergePolicyDelta(com.google.iam.v1.PolicyDelta value) {
    value.getClass();
    if (policyDelta_ != null
        && policyDelta_ != com.google.iam.v1.PolicyDelta.getDefaultInstance()) {
      policyDelta_ =
          com.google.iam.v1.PolicyDelta.newBuilder(policyDelta_).mergeFrom(value).buildPartial();
    } else {
      policyDelta_ = value;
    }
    bitField0_ |= 0x00000001;
  }
  /**
   *
   *
   * <pre>
   * Policy delta between the original policy and the newly set policy.
   * </pre>
   *
   * <code>.google.iam.v1.PolicyDelta policy_delta = 2;</code>
   */
  private void clearPolicyDelta() {
    policyDelta_ = null;
    bitField0_ = (bitField0_ & ~0x00000001);
  }

  public static com.google.iam.v1.logging.AuditData parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.iam.v1.logging.AuditData parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.iam.v1.logging.AuditData parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.iam.v1.logging.AuditData parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.iam.v1.logging.AuditData parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.iam.v1.logging.AuditData parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.iam.v1.logging.AuditData parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.iam.v1.logging.AuditData parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.iam.v1.logging.AuditData parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.iam.v1.logging.AuditData parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.iam.v1.logging.AuditData parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.iam.v1.logging.AuditData parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }

  public static Builder newBuilder(com.google.iam.v1.logging.AuditData prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   *
   *
   * <pre>
   * Audit log information specific to Cloud IAM. This message is serialized
   * as an `Any` type in the `ServiceData` message of an
   * `AuditLog` message.
   * </pre>
   *
   * Protobuf type {@code google.iam.v1.logging.AuditData}
   */
  public static final class Builder
      extends com.google.protobuf.GeneratedMessageLite.Builder<
          com.google.iam.v1.logging.AuditData, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.iam.v1.logging.AuditData)
      com.google.iam.v1.logging.AuditDataOrBuilder {
    // Construct using com.google.iam.v1.logging.AuditData.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }

    /**
     *
     *
     * <pre>
     * Policy delta between the original policy and the newly set policy.
     * </pre>
     *
     * <code>.google.iam.v1.PolicyDelta policy_delta = 2;</code>
     */
    @java.lang.Override
    public boolean hasPolicyDelta() {
      return instance.hasPolicyDelta();
    }
    /**
     *
     *
     * <pre>
     * Policy delta between the original policy and the newly set policy.
     * </pre>
     *
     * <code>.google.iam.v1.PolicyDelta policy_delta = 2;</code>
     */
    @java.lang.Override
    public com.google.iam.v1.PolicyDelta getPolicyDelta() {
      return instance.getPolicyDelta();
    }
    /**
     *
     *
     * <pre>
     * Policy delta between the original policy and the newly set policy.
     * </pre>
     *
     * <code>.google.iam.v1.PolicyDelta policy_delta = 2;</code>
     */
    public Builder setPolicyDelta(com.google.iam.v1.PolicyDelta value) {
      copyOnWrite();
      instance.setPolicyDelta(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Policy delta between the original policy and the newly set policy.
     * </pre>
     *
     * <code>.google.iam.v1.PolicyDelta policy_delta = 2;</code>
     */
    public Builder setPolicyDelta(com.google.iam.v1.PolicyDelta.Builder builderForValue) {
      copyOnWrite();
      instance.setPolicyDelta(builderForValue.build());
      return this;
    }
    /**
     *
     *
     * <pre>
     * Policy delta between the original policy and the newly set policy.
     * </pre>
     *
     * <code>.google.iam.v1.PolicyDelta policy_delta = 2;</code>
     */
    public Builder mergePolicyDelta(com.google.iam.v1.PolicyDelta value) {
      copyOnWrite();
      instance.mergePolicyDelta(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Policy delta between the original policy and the newly set policy.
     * </pre>
     *
     * <code>.google.iam.v1.PolicyDelta policy_delta = 2;</code>
     */
    public Builder clearPolicyDelta() {
      copyOnWrite();
      instance.clearPolicyDelta();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.iam.v1.logging.AuditData)
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
          return new com.google.iam.v1.logging.AuditData();
        }
      case NEW_BUILDER:
        {
          return new Builder();
        }
      case BUILD_MESSAGE_INFO:
        {
          java.lang.Object[] objects =
              new java.lang.Object[] {
                "bitField0_", "policyDelta_",
              };
          java.lang.String info =
              "\u0000\u0001\u0000\u0001\u0002\u0002\u0001\u0000\u0000\u0000\u0002\u1009\u0000";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
      case GET_DEFAULT_INSTANCE:
        {
          return DEFAULT_INSTANCE;
        }
      case GET_PARSER:
        {
          com.google.protobuf.Parser<com.google.iam.v1.logging.AuditData> parser = PARSER;
          if (parser == null) {
            synchronized (com.google.iam.v1.logging.AuditData.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<com.google.iam.v1.logging.AuditData>(
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

  // @@protoc_insertion_point(class_scope:google.iam.v1.logging.AuditData)
  private static final com.google.iam.v1.logging.AuditData DEFAULT_INSTANCE;

  static {
    AuditData defaultInstance = new AuditData();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        AuditData.class, defaultInstance);
  }

  public static com.google.iam.v1.logging.AuditData getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<AuditData> PARSER;

  public static com.google.protobuf.Parser<AuditData> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
