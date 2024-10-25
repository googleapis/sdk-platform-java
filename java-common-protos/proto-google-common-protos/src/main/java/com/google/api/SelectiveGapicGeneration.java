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
// source: google/api/client.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

/**
 *
 *
 * <pre>
 * This message is used to configure the generation of a subset of the RPCs in
 * a service for client libraries.
 * </pre>
 *
 * Protobuf type {@code google.api.SelectiveGapicGeneration}
 */
public final class SelectiveGapicGeneration extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.api.SelectiveGapicGeneration)
    SelectiveGapicGenerationOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use SelectiveGapicGeneration.newBuilder() to construct.
  private SelectiveGapicGeneration(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private SelectiveGapicGeneration() {
    methods_ = com.google.protobuf.LazyStringArrayList.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new SelectiveGapicGeneration();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.api.ClientProto
        .internal_static_google_api_SelectiveGapicGeneration_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.api.ClientProto
        .internal_static_google_api_SelectiveGapicGeneration_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.api.SelectiveGapicGeneration.class,
            com.google.api.SelectiveGapicGeneration.Builder.class);
  }

  public static final int METHODS_FIELD_NUMBER = 1;

  @SuppressWarnings("serial")
  private com.google.protobuf.LazyStringArrayList methods_ =
      com.google.protobuf.LazyStringArrayList.emptyList();
  /**
   *
   *
   * <pre>
   * An allowlist of the fully qualified names of RPCs that should be included
   * on public client surfaces.
   * </pre>
   *
   * <code>repeated string methods = 1;</code>
   *
   * @return A list containing the methods.
   */
  public com.google.protobuf.ProtocolStringList getMethodsList() {
    return methods_;
  }
  /**
   *
   *
   * <pre>
   * An allowlist of the fully qualified names of RPCs that should be included
   * on public client surfaces.
   * </pre>
   *
   * <code>repeated string methods = 1;</code>
   *
   * @return The count of methods.
   */
  public int getMethodsCount() {
    return methods_.size();
  }
  /**
   *
   *
   * <pre>
   * An allowlist of the fully qualified names of RPCs that should be included
   * on public client surfaces.
   * </pre>
   *
   * <code>repeated string methods = 1;</code>
   *
   * @param index The index of the element to return.
   * @return The methods at the given index.
   */
  public java.lang.String getMethods(int index) {
    return methods_.get(index);
  }
  /**
   *
   *
   * <pre>
   * An allowlist of the fully qualified names of RPCs that should be included
   * on public client surfaces.
   * </pre>
   *
   * <code>repeated string methods = 1;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the methods at the given index.
   */
  public com.google.protobuf.ByteString getMethodsBytes(int index) {
    return methods_.getByteString(index);
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
    for (int i = 0; i < methods_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, methods_.getRaw(i));
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    {
      int dataSize = 0;
      for (int i = 0; i < methods_.size(); i++) {
        dataSize += computeStringSizeNoTag(methods_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getMethodsList().size();
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
    if (!(obj instanceof com.google.api.SelectiveGapicGeneration)) {
      return super.equals(obj);
    }
    com.google.api.SelectiveGapicGeneration other = (com.google.api.SelectiveGapicGeneration) obj;

    if (!getMethodsList().equals(other.getMethodsList())) return false;
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
    if (getMethodsCount() > 0) {
      hash = (37 * hash) + METHODS_FIELD_NUMBER;
      hash = (53 * hash) + getMethodsList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.api.SelectiveGapicGeneration parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.SelectiveGapicGeneration parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.SelectiveGapicGeneration parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.SelectiveGapicGeneration parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.SelectiveGapicGeneration parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.SelectiveGapicGeneration parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.SelectiveGapicGeneration parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.api.SelectiveGapicGeneration parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.api.SelectiveGapicGeneration parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.api.SelectiveGapicGeneration parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.api.SelectiveGapicGeneration parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.api.SelectiveGapicGeneration parseFrom(
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

  public static Builder newBuilder(com.google.api.SelectiveGapicGeneration prototype) {
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
   * This message is used to configure the generation of a subset of the RPCs in
   * a service for client libraries.
   * </pre>
   *
   * Protobuf type {@code google.api.SelectiveGapicGeneration}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.SelectiveGapicGeneration)
      com.google.api.SelectiveGapicGenerationOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.api.ClientProto
          .internal_static_google_api_SelectiveGapicGeneration_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.api.ClientProto
          .internal_static_google_api_SelectiveGapicGeneration_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.api.SelectiveGapicGeneration.class,
              com.google.api.SelectiveGapicGeneration.Builder.class);
    }

    // Construct using com.google.api.SelectiveGapicGeneration.newBuilder()
    private Builder() {}

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      methods_ = com.google.protobuf.LazyStringArrayList.emptyList();
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.api.ClientProto
          .internal_static_google_api_SelectiveGapicGeneration_descriptor;
    }

    @java.lang.Override
    public com.google.api.SelectiveGapicGeneration getDefaultInstanceForType() {
      return com.google.api.SelectiveGapicGeneration.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.api.SelectiveGapicGeneration build() {
      com.google.api.SelectiveGapicGeneration result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.api.SelectiveGapicGeneration buildPartial() {
      com.google.api.SelectiveGapicGeneration result =
          new com.google.api.SelectiveGapicGeneration(this);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.api.SelectiveGapicGeneration result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        methods_.makeImmutable();
        result.methods_ = methods_;
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
      if (other instanceof com.google.api.SelectiveGapicGeneration) {
        return mergeFrom((com.google.api.SelectiveGapicGeneration) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.api.SelectiveGapicGeneration other) {
      if (other == com.google.api.SelectiveGapicGeneration.getDefaultInstance()) return this;
      if (!other.methods_.isEmpty()) {
        if (methods_.isEmpty()) {
          methods_ = other.methods_;
          bitField0_ |= 0x00000001;
        } else {
          ensureMethodsIsMutable();
          methods_.addAll(other.methods_);
        }
        onChanged();
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
                java.lang.String s = input.readStringRequireUtf8();
                ensureMethodsIsMutable();
                methods_.add(s);
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

    private com.google.protobuf.LazyStringArrayList methods_ =
        com.google.protobuf.LazyStringArrayList.emptyList();

    private void ensureMethodsIsMutable() {
      if (!methods_.isModifiable()) {
        methods_ = new com.google.protobuf.LazyStringArrayList(methods_);
      }
      bitField0_ |= 0x00000001;
    }
    /**
     *
     *
     * <pre>
     * An allowlist of the fully qualified names of RPCs that should be included
     * on public client surfaces.
     * </pre>
     *
     * <code>repeated string methods = 1;</code>
     *
     * @return A list containing the methods.
     */
    public com.google.protobuf.ProtocolStringList getMethodsList() {
      methods_.makeImmutable();
      return methods_;
    }
    /**
     *
     *
     * <pre>
     * An allowlist of the fully qualified names of RPCs that should be included
     * on public client surfaces.
     * </pre>
     *
     * <code>repeated string methods = 1;</code>
     *
     * @return The count of methods.
     */
    public int getMethodsCount() {
      return methods_.size();
    }
    /**
     *
     *
     * <pre>
     * An allowlist of the fully qualified names of RPCs that should be included
     * on public client surfaces.
     * </pre>
     *
     * <code>repeated string methods = 1;</code>
     *
     * @param index The index of the element to return.
     * @return The methods at the given index.
     */
    public java.lang.String getMethods(int index) {
      return methods_.get(index);
    }
    /**
     *
     *
     * <pre>
     * An allowlist of the fully qualified names of RPCs that should be included
     * on public client surfaces.
     * </pre>
     *
     * <code>repeated string methods = 1;</code>
     *
     * @param index The index of the value to return.
     * @return The bytes of the methods at the given index.
     */
    public com.google.protobuf.ByteString getMethodsBytes(int index) {
      return methods_.getByteString(index);
    }
    /**
     *
     *
     * <pre>
     * An allowlist of the fully qualified names of RPCs that should be included
     * on public client surfaces.
     * </pre>
     *
     * <code>repeated string methods = 1;</code>
     *
     * @param index The index to set the value at.
     * @param value The methods to set.
     * @return This builder for chaining.
     */
    public Builder setMethods(int index, java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureMethodsIsMutable();
      methods_.set(index, value);
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * An allowlist of the fully qualified names of RPCs that should be included
     * on public client surfaces.
     * </pre>
     *
     * <code>repeated string methods = 1;</code>
     *
     * @param value The methods to add.
     * @return This builder for chaining.
     */
    public Builder addMethods(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureMethodsIsMutable();
      methods_.add(value);
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * An allowlist of the fully qualified names of RPCs that should be included
     * on public client surfaces.
     * </pre>
     *
     * <code>repeated string methods = 1;</code>
     *
     * @param values The methods to add.
     * @return This builder for chaining.
     */
    public Builder addAllMethods(java.lang.Iterable<java.lang.String> values) {
      ensureMethodsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(values, methods_);
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * An allowlist of the fully qualified names of RPCs that should be included
     * on public client surfaces.
     * </pre>
     *
     * <code>repeated string methods = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearMethods() {
      methods_ = com.google.protobuf.LazyStringArrayList.emptyList();
      bitField0_ = (bitField0_ & ~0x00000001);
      ;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * An allowlist of the fully qualified names of RPCs that should be included
     * on public client surfaces.
     * </pre>
     *
     * <code>repeated string methods = 1;</code>
     *
     * @param value The bytes of the methods to add.
     * @return This builder for chaining.
     */
    public Builder addMethodsBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);
      ensureMethodsIsMutable();
      methods_.add(value);
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
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

    // @@protoc_insertion_point(builder_scope:google.api.SelectiveGapicGeneration)
  }

  // @@protoc_insertion_point(class_scope:google.api.SelectiveGapicGeneration)
  private static final com.google.api.SelectiveGapicGeneration DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.api.SelectiveGapicGeneration();
  }

  public static com.google.api.SelectiveGapicGeneration getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<SelectiveGapicGeneration> PARSER =
      new com.google.protobuf.AbstractParser<SelectiveGapicGeneration>() {
        @java.lang.Override
        public SelectiveGapicGeneration parsePartialFrom(
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

  public static com.google.protobuf.Parser<SelectiveGapicGeneration> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<SelectiveGapicGeneration> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.api.SelectiveGapicGeneration getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
