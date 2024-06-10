/*
 * Copyright 2024 Google LLC
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
// NO CHECKED-IN PROTOBUF GENCODE
// source: google/api/http.proto
// Protobuf Java Version: 4.27.1

package com.google.api;

/**
 *
 *
 * <pre>
 * Defines the HTTP configuration for an API service. It contains a list of
 * [HttpRule][google.api.HttpRule], each specifying the mapping of an RPC method
 * to one or more HTTP REST API methods.
 * </pre>
 *
 * Protobuf type {@code google.api.Http}
 */
public final class Http extends com.google.protobuf.GeneratedMessage
    implements
    // @@protoc_insertion_point(message_implements:google.api.Http)
    HttpOrBuilder {
  private static final long serialVersionUID = 0L;

  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 27,
        /* patch= */ 1,
        /* suffix= */ "",
        Http.class.getName());
  }
  // Use Http.newBuilder() to construct.
  private Http(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }

  private Http() {
    rules_ = java.util.Collections.emptyList();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.api.HttpProto.internal_static_google_api_Http_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.api.HttpProto.internal_static_google_api_Http_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.api.Http.class, com.google.api.Http.Builder.class);
  }

  public static final int RULES_FIELD_NUMBER = 1;

  @SuppressWarnings("serial")
  private java.util.List<com.google.api.HttpRule> rules_;
  /**
   *
   *
   * <pre>
   * A list of HTTP configuration rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.HttpRule rules = 1;</code>
   */
  @java.lang.Override
  public java.util.List<com.google.api.HttpRule> getRulesList() {
    return rules_;
  }
  /**
   *
   *
   * <pre>
   * A list of HTTP configuration rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.HttpRule rules = 1;</code>
   */
  @java.lang.Override
  public java.util.List<? extends com.google.api.HttpRuleOrBuilder> getRulesOrBuilderList() {
    return rules_;
  }
  /**
   *
   *
   * <pre>
   * A list of HTTP configuration rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.HttpRule rules = 1;</code>
   */
  @java.lang.Override
  public int getRulesCount() {
    return rules_.size();
  }
  /**
   *
   *
   * <pre>
   * A list of HTTP configuration rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.HttpRule rules = 1;</code>
   */
  @java.lang.Override
  public com.google.api.HttpRule getRules(int index) {
    return rules_.get(index);
  }
  /**
   *
   *
   * <pre>
   * A list of HTTP configuration rules that apply to individual API methods.
   *
   * **NOTE:** All service configuration rules follow "last one wins" order.
   * </pre>
   *
   * <code>repeated .google.api.HttpRule rules = 1;</code>
   */
  @java.lang.Override
  public com.google.api.HttpRuleOrBuilder getRulesOrBuilder(int index) {
    return rules_.get(index);
  }

  public static final int FULLY_DECODE_RESERVED_EXPANSION_FIELD_NUMBER = 2;
  private boolean fullyDecodeReservedExpansion_ = false;
  /**
   *
   *
   * <pre>
   * When set to true, URL path parameters will be fully URI-decoded except in
   * cases of single segment matches in reserved expansion, where "%2F" will be
   * left encoded.
   *
   * The default behavior is to not decode RFC 6570 reserved characters in multi
   * segment matches.
   * </pre>
   *
   * <code>bool fully_decode_reserved_expansion = 2;</code>
   *
   * @return The fullyDecodeReservedExpansion.
   */
  @java.lang.Override
  public boolean getFullyDecodeReservedExpansion() {
    return fullyDecodeReservedExpansion_;
  }

  private byte memoizedIsInitialized = -1;

  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
    for (int i = 0; i < rules_.size(); i++) {
      output.writeMessage(1, rules_.get(i));
    }
    if (fullyDecodeReservedExpansion_ != false) {
      output.writeBool(2, fullyDecodeReservedExpansion_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < rules_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, rules_.get(i));
    }
    if (fullyDecodeReservedExpansion_ != false) {
      size +=
          com.google.protobuf.CodedOutputStream.computeBoolSize(2, fullyDecodeReservedExpansion_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof com.google.api.Http)) {
      return super.equals(obj);
    }
    com.google.api.Http other = (com.google.api.Http) obj;

    if (!getRulesList().equals(other.getRulesList())) return false;
    if (getFullyDecodeReservedExpansion() != other.getFullyDecodeReservedExpansion()) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getRulesCount() > 0) {
      hash = (37 * hash) + RULES_FIELD_NUMBER;
      hash = (53 * hash) + getRulesList().hashCode();
    }
    hash = (37 * hash) + FULLY_DECODE_RESERVED_EXPANSION_FIELD_NUMBER;
    hash =
        (53 * hash) + com.google.protobuf.Internal.hashBoolean(getFullyDecodeReservedExpansion());
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.api.Http parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.Http parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.Http parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.Http parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.Http parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.Http parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.Http parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage.parseWithIOException(PARSER, input);
  }

  public static com.google.api.Http parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.api.Http parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.api.Http parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.api.Http parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage.parseWithIOException(PARSER, input);
  }

  public static com.google.api.Http parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() {
    return newBuilder();
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(com.google.api.Http prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   *
   *
   * <pre>
   * Defines the HTTP configuration for an API service. It contains a list of
   * [HttpRule][google.api.HttpRule], each specifying the mapping of an RPC method
   * to one or more HTTP REST API methods.
   * </pre>
   *
   * Protobuf type {@code google.api.Http}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.Http)
      com.google.api.HttpOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.api.HttpProto.internal_static_google_api_Http_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.api.HttpProto.internal_static_google_api_Http_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.api.Http.class, com.google.api.Http.Builder.class);
    }

    // Construct using com.google.api.Http.newBuilder()
    private Builder() {}

    private Builder(com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      if (rulesBuilder_ == null) {
        rules_ = java.util.Collections.emptyList();
      } else {
        rules_ = null;
        rulesBuilder_.clear();
      }
      bitField0_ = (bitField0_ & ~0x00000001);
      fullyDecodeReservedExpansion_ = false;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.api.HttpProto.internal_static_google_api_Http_descriptor;
    }

    @java.lang.Override
    public com.google.api.Http getDefaultInstanceForType() {
      return com.google.api.Http.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.api.Http build() {
      com.google.api.Http result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.api.Http buildPartial() {
      com.google.api.Http result = new com.google.api.Http(this);
      buildPartialRepeatedFields(result);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartialRepeatedFields(com.google.api.Http result) {
      if (rulesBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          rules_ = java.util.Collections.unmodifiableList(rules_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.rules_ = rules_;
      } else {
        result.rules_ = rulesBuilder_.build();
      }
    }

    private void buildPartial0(com.google.api.Http result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.fullyDecodeReservedExpansion_ = fullyDecodeReservedExpansion_;
      }
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.google.api.Http) {
        return mergeFrom((com.google.api.Http) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.api.Http other) {
      if (other == com.google.api.Http.getDefaultInstance()) return this;
      if (rulesBuilder_ == null) {
        if (!other.rules_.isEmpty()) {
          if (rules_.isEmpty()) {
            rules_ = other.rules_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureRulesIsMutable();
            rules_.addAll(other.rules_);
          }
          onChanged();
        }
      } else {
        if (!other.rules_.isEmpty()) {
          if (rulesBuilder_.isEmpty()) {
            rulesBuilder_.dispose();
            rulesBuilder_ = null;
            rules_ = other.rules_;
            bitField0_ = (bitField0_ & ~0x00000001);
            rulesBuilder_ =
                com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders
                    ? getRulesFieldBuilder()
                    : null;
          } else {
            rulesBuilder_.addAllMessages(other.rules_);
          }
        }
      }
      if (other.getFullyDecodeReservedExpansion() != false) {
        setFullyDecodeReservedExpansion(other.getFullyDecodeReservedExpansion());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10:
              {
                com.google.api.HttpRule m =
                    input.readMessage(com.google.api.HttpRule.parser(), extensionRegistry);
                if (rulesBuilder_ == null) {
                  ensureRulesIsMutable();
                  rules_.add(m);
                } else {
                  rulesBuilder_.addMessage(m);
                }
                break;
              } // case 10
            case 16:
              {
                fullyDecodeReservedExpansion_ = input.readBool();
                bitField0_ |= 0x00000002;
                break;
              } // case 16
            default:
              {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }

    private int bitField0_;

    private java.util.List<com.google.api.HttpRule> rules_ = java.util.Collections.emptyList();

    private void ensureRulesIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        rules_ = new java.util.ArrayList<com.google.api.HttpRule>(rules_);
        bitField0_ |= 0x00000001;
      }
    }

    private com.google.protobuf.RepeatedFieldBuilder<
            com.google.api.HttpRule,
            com.google.api.HttpRule.Builder,
            com.google.api.HttpRuleOrBuilder>
        rulesBuilder_;

    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public java.util.List<com.google.api.HttpRule> getRulesList() {
      if (rulesBuilder_ == null) {
        return java.util.Collections.unmodifiableList(rules_);
      } else {
        return rulesBuilder_.getMessageList();
      }
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public int getRulesCount() {
      if (rulesBuilder_ == null) {
        return rules_.size();
      } else {
        return rulesBuilder_.getCount();
      }
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public com.google.api.HttpRule getRules(int index) {
      if (rulesBuilder_ == null) {
        return rules_.get(index);
      } else {
        return rulesBuilder_.getMessage(index);
      }
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder setRules(int index, com.google.api.HttpRule value) {
      if (rulesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRulesIsMutable();
        rules_.set(index, value);
        onChanged();
      } else {
        rulesBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder setRules(int index, com.google.api.HttpRule.Builder builderForValue) {
      if (rulesBuilder_ == null) {
        ensureRulesIsMutable();
        rules_.set(index, builderForValue.build());
        onChanged();
      } else {
        rulesBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder addRules(com.google.api.HttpRule value) {
      if (rulesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRulesIsMutable();
        rules_.add(value);
        onChanged();
      } else {
        rulesBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder addRules(int index, com.google.api.HttpRule value) {
      if (rulesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureRulesIsMutable();
        rules_.add(index, value);
        onChanged();
      } else {
        rulesBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder addRules(com.google.api.HttpRule.Builder builderForValue) {
      if (rulesBuilder_ == null) {
        ensureRulesIsMutable();
        rules_.add(builderForValue.build());
        onChanged();
      } else {
        rulesBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder addRules(int index, com.google.api.HttpRule.Builder builderForValue) {
      if (rulesBuilder_ == null) {
        ensureRulesIsMutable();
        rules_.add(index, builderForValue.build());
        onChanged();
      } else {
        rulesBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder addAllRules(java.lang.Iterable<? extends com.google.api.HttpRule> values) {
      if (rulesBuilder_ == null) {
        ensureRulesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(values, rules_);
        onChanged();
      } else {
        rulesBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder clearRules() {
      if (rulesBuilder_ == null) {
        rules_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        rulesBuilder_.clear();
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public Builder removeRules(int index) {
      if (rulesBuilder_ == null) {
        ensureRulesIsMutable();
        rules_.remove(index);
        onChanged();
      } else {
        rulesBuilder_.remove(index);
      }
      return this;
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public com.google.api.HttpRule.Builder getRulesBuilder(int index) {
      return getRulesFieldBuilder().getBuilder(index);
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public com.google.api.HttpRuleOrBuilder getRulesOrBuilder(int index) {
      if (rulesBuilder_ == null) {
        return rules_.get(index);
      } else {
        return rulesBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public java.util.List<? extends com.google.api.HttpRuleOrBuilder> getRulesOrBuilderList() {
      if (rulesBuilder_ != null) {
        return rulesBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(rules_);
      }
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public com.google.api.HttpRule.Builder addRulesBuilder() {
      return getRulesFieldBuilder().addBuilder(com.google.api.HttpRule.getDefaultInstance());
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public com.google.api.HttpRule.Builder addRulesBuilder(int index) {
      return getRulesFieldBuilder().addBuilder(index, com.google.api.HttpRule.getDefaultInstance());
    }
    /**
     *
     *
     * <pre>
     * A list of HTTP configuration rules that apply to individual API methods.
     *
     * **NOTE:** All service configuration rules follow "last one wins" order.
     * </pre>
     *
     * <code>repeated .google.api.HttpRule rules = 1;</code>
     */
    public java.util.List<com.google.api.HttpRule.Builder> getRulesBuilderList() {
      return getRulesFieldBuilder().getBuilderList();
    }

    private com.google.protobuf.RepeatedFieldBuilder<
            com.google.api.HttpRule,
            com.google.api.HttpRule.Builder,
            com.google.api.HttpRuleOrBuilder>
        getRulesFieldBuilder() {
      if (rulesBuilder_ == null) {
        rulesBuilder_ =
            new com.google.protobuf.RepeatedFieldBuilder<
                com.google.api.HttpRule,
                com.google.api.HttpRule.Builder,
                com.google.api.HttpRuleOrBuilder>(
                rules_, ((bitField0_ & 0x00000001) != 0), getParentForChildren(), isClean());
        rules_ = null;
      }
      return rulesBuilder_;
    }

    private boolean fullyDecodeReservedExpansion_;
    /**
     *
     *
     * <pre>
     * When set to true, URL path parameters will be fully URI-decoded except in
     * cases of single segment matches in reserved expansion, where "%2F" will be
     * left encoded.
     *
     * The default behavior is to not decode RFC 6570 reserved characters in multi
     * segment matches.
     * </pre>
     *
     * <code>bool fully_decode_reserved_expansion = 2;</code>
     *
     * @return The fullyDecodeReservedExpansion.
     */
    @java.lang.Override
    public boolean getFullyDecodeReservedExpansion() {
      return fullyDecodeReservedExpansion_;
    }
    /**
     *
     *
     * <pre>
     * When set to true, URL path parameters will be fully URI-decoded except in
     * cases of single segment matches in reserved expansion, where "%2F" will be
     * left encoded.
     *
     * The default behavior is to not decode RFC 6570 reserved characters in multi
     * segment matches.
     * </pre>
     *
     * <code>bool fully_decode_reserved_expansion = 2;</code>
     *
     * @param value The fullyDecodeReservedExpansion to set.
     * @return This builder for chaining.
     */
    public Builder setFullyDecodeReservedExpansion(boolean value) {

      fullyDecodeReservedExpansion_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * When set to true, URL path parameters will be fully URI-decoded except in
     * cases of single segment matches in reserved expansion, where "%2F" will be
     * left encoded.
     *
     * The default behavior is to not decode RFC 6570 reserved characters in multi
     * segment matches.
     * </pre>
     *
     * <code>bool fully_decode_reserved_expansion = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearFullyDecodeReservedExpansion() {
      bitField0_ = (bitField0_ & ~0x00000002);
      fullyDecodeReservedExpansion_ = false;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.api.Http)
  }

  // @@protoc_insertion_point(class_scope:google.api.Http)
  private static final com.google.api.Http DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.api.Http();
  }

  public static com.google.api.Http getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Http> PARSER =
      new com.google.protobuf.AbstractParser<Http>() {
        @java.lang.Override
        public Http parsePartialFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
          Builder builder = newBuilder();
          try {
            builder.mergeFrom(input, extensionRegistry);
          } catch (com.google.protobuf.InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(builder.buildPartial());
          } catch (com.google.protobuf.UninitializedMessageException e) {
            throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
          } catch (java.io.IOException e) {
            throw new com.google.protobuf.InvalidProtocolBufferException(e)
                .setUnfinishedMessage(builder.buildPartial());
          }
          return builder.buildPartial();
        }
      };

  public static com.google.protobuf.Parser<Http> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Http> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.api.Http getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
