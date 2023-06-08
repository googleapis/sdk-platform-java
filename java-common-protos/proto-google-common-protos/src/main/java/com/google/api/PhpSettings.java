/*
 * Copyright 2020 Google LLC
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

package com.google.api;

/**
 *
 *
 * <pre>
 * Settings for Php client libraries.
 * </pre>
 *
 * Protobuf type {@code google.api.PhpSettings}
 */
public final class PhpSettings extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.api.PhpSettings)
    PhpSettingsOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use PhpSettings.newBuilder() to construct.
  private PhpSettings(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private PhpSettings() {}

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new PhpSettings();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.api.ClientProto.internal_static_google_api_PhpSettings_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.api.ClientProto.internal_static_google_api_PhpSettings_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.api.PhpSettings.class, com.google.api.PhpSettings.Builder.class);
  }

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
   *
   * @return Whether the common field is set.
   */
  @java.lang.Override
  public boolean hasCommon() {
    return common_ != null;
  }
  /**
   *
   *
   * <pre>
   * Some settings.
   * </pre>
   *
   * <code>.google.api.CommonLanguageSettings common = 1;</code>
   *
   * @return The common.
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
  @java.lang.Override
  public com.google.api.CommonLanguageSettingsOrBuilder getCommonOrBuilder() {
    return common_ == null ? com.google.api.CommonLanguageSettings.getDefaultInstance() : common_;
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
    if (common_ != null) {
      output.writeMessage(1, getCommon());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (common_ != null) {
      size += com.google.protobuf.CodedOutputStream.computeMessageSize(1, getCommon());
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
    if (!(obj instanceof com.google.api.PhpSettings)) {
      return super.equals(obj);
    }
    com.google.api.PhpSettings other = (com.google.api.PhpSettings) obj;

    if (hasCommon() != other.hasCommon()) return false;
    if (hasCommon()) {
      if (!getCommon().equals(other.getCommon())) return false;
    }
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
    if (hasCommon()) {
      hash = (37 * hash) + COMMON_FIELD_NUMBER;
      hash = (53 * hash) + getCommon().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.api.PhpSettings parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.PhpSettings parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.PhpSettings parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.PhpSettings parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.PhpSettings parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.PhpSettings parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.PhpSettings parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.api.PhpSettings parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.api.PhpSettings parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.api.PhpSettings parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.api.PhpSettings parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.api.PhpSettings parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() {
    return newBuilder();
  }

  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }

  public static Builder newBuilder(com.google.api.PhpSettings prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }

  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   *
   *
   * <pre>
   * Settings for Php client libraries.
   * </pre>
   *
   * Protobuf type {@code google.api.PhpSettings}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.PhpSettings)
      com.google.api.PhpSettingsOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.api.ClientProto.internal_static_google_api_PhpSettings_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.api.ClientProto.internal_static_google_api_PhpSettings_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.api.PhpSettings.class, com.google.api.PhpSettings.Builder.class);
    }

    // Construct using com.google.api.PhpSettings.newBuilder()
    private Builder() {}

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      common_ = null;
      if (commonBuilder_ != null) {
        commonBuilder_.dispose();
        commonBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.api.ClientProto.internal_static_google_api_PhpSettings_descriptor;
    }

    @java.lang.Override
    public com.google.api.PhpSettings getDefaultInstanceForType() {
      return com.google.api.PhpSettings.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.api.PhpSettings build() {
      com.google.api.PhpSettings result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.api.PhpSettings buildPartial() {
      com.google.api.PhpSettings result = new com.google.api.PhpSettings(this);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.api.PhpSettings result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.common_ = commonBuilder_ == null ? common_ : commonBuilder_.build();
      }
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }

    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
      return super.setField(field, value);
    }

    @java.lang.Override
    public Builder clearField(com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }

    @java.lang.Override
    public Builder clearOneof(com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }

    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field, int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }

    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.google.api.PhpSettings) {
        return mergeFrom((com.google.api.PhpSettings) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.api.PhpSettings other) {
      if (other == com.google.api.PhpSettings.getDefaultInstance()) return this;
      if (other.hasCommon()) {
        mergeCommon(other.getCommon());
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
                input.readMessage(getCommonFieldBuilder().getBuilder(), extensionRegistry);
                bitField0_ |= 0x00000001;
                break;
              } // case 10
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

    private com.google.api.CommonLanguageSettings common_;
    private com.google.protobuf.SingleFieldBuilderV3<
            com.google.api.CommonLanguageSettings,
            com.google.api.CommonLanguageSettings.Builder,
            com.google.api.CommonLanguageSettingsOrBuilder>
        commonBuilder_;
    /**
     *
     *
     * <pre>
     * Some settings.
     * </pre>
     *
     * <code>.google.api.CommonLanguageSettings common = 1;</code>
     *
     * @return Whether the common field is set.
     */
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
     *
     * @return The common.
     */
    public com.google.api.CommonLanguageSettings getCommon() {
      if (commonBuilder_ == null) {
        return common_ == null
            ? com.google.api.CommonLanguageSettings.getDefaultInstance()
            : common_;
      } else {
        return commonBuilder_.getMessage();
      }
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
      if (commonBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        common_ = value;
      } else {
        commonBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
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
      if (commonBuilder_ == null) {
        common_ = builderForValue.build();
      } else {
        commonBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
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
      if (commonBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)
            && common_ != null
            && common_ != com.google.api.CommonLanguageSettings.getDefaultInstance()) {
          getCommonBuilder().mergeFrom(value);
        } else {
          common_ = value;
        }
      } else {
        commonBuilder_.mergeFrom(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
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
      bitField0_ = (bitField0_ & ~0x00000001);
      common_ = null;
      if (commonBuilder_ != null) {
        commonBuilder_.dispose();
        commonBuilder_ = null;
      }
      onChanged();
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
    public com.google.api.CommonLanguageSettings.Builder getCommonBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getCommonFieldBuilder().getBuilder();
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
    public com.google.api.CommonLanguageSettingsOrBuilder getCommonOrBuilder() {
      if (commonBuilder_ != null) {
        return commonBuilder_.getMessageOrBuilder();
      } else {
        return common_ == null
            ? com.google.api.CommonLanguageSettings.getDefaultInstance()
            : common_;
      }
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
    private com.google.protobuf.SingleFieldBuilderV3<
            com.google.api.CommonLanguageSettings,
            com.google.api.CommonLanguageSettings.Builder,
            com.google.api.CommonLanguageSettingsOrBuilder>
        getCommonFieldBuilder() {
      if (commonBuilder_ == null) {
        commonBuilder_ =
            new com.google.protobuf.SingleFieldBuilderV3<
                com.google.api.CommonLanguageSettings,
                com.google.api.CommonLanguageSettings.Builder,
                com.google.api.CommonLanguageSettingsOrBuilder>(
                getCommon(), getParentForChildren(), isClean());
        common_ = null;
      }
      return commonBuilder_;
    }

    @java.lang.Override
    public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }

    // @@protoc_insertion_point(builder_scope:google.api.PhpSettings)
  }

  // @@protoc_insertion_point(class_scope:google.api.PhpSettings)
  private static final com.google.api.PhpSettings DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.api.PhpSettings();
  }

  public static com.google.api.PhpSettings getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PhpSettings> PARSER =
      new com.google.protobuf.AbstractParser<PhpSettings>() {
        @java.lang.Override
        public PhpSettings parsePartialFrom(
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

  public static com.google.protobuf.Parser<PhpSettings> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PhpSettings> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.api.PhpSettings getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
