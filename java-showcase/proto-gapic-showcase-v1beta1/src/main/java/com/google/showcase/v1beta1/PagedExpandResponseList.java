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
// source: schema/google/showcase/v1beta1/echo.proto

// Protobuf Java Version: 3.25.5
package com.google.showcase.v1beta1;

/**
 *
 *
 * <pre>
 * A list of words.
 * </pre>
 *
 * Protobuf type {@code google.showcase.v1beta1.PagedExpandResponseList}
 */
public final class PagedExpandResponseList extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.showcase.v1beta1.PagedExpandResponseList)
    PagedExpandResponseListOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use PagedExpandResponseList.newBuilder() to construct.
  private PagedExpandResponseList(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private PagedExpandResponseList() {
    words_ = com.google.protobuf.LazyStringArrayList.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new PagedExpandResponseList();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.showcase.v1beta1.EchoOuterClass
        .internal_static_google_showcase_v1beta1_PagedExpandResponseList_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.showcase.v1beta1.EchoOuterClass
        .internal_static_google_showcase_v1beta1_PagedExpandResponseList_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.showcase.v1beta1.PagedExpandResponseList.class,
            com.google.showcase.v1beta1.PagedExpandResponseList.Builder.class);
  }

  public static final int WORDS_FIELD_NUMBER = 1;

  @SuppressWarnings("serial")
  private com.google.protobuf.LazyStringArrayList words_ =
      com.google.protobuf.LazyStringArrayList.emptyList();
  /**
   * <code>repeated string words = 1;</code>
   *
   * @return A list containing the words.
   */
  public com.google.protobuf.ProtocolStringList getWordsList() {
    return words_;
  }
  /**
   * <code>repeated string words = 1;</code>
   *
   * @return The count of words.
   */
  public int getWordsCount() {
    return words_.size();
  }
  /**
   * <code>repeated string words = 1;</code>
   *
   * @param index The index of the element to return.
   * @return The words at the given index.
   */
  public java.lang.String getWords(int index) {
    return words_.get(index);
  }
  /**
   * <code>repeated string words = 1;</code>
   *
   * @param index The index of the value to return.
   * @return The bytes of the words at the given index.
   */
  public com.google.protobuf.ByteString getWordsBytes(int index) {
    return words_.getByteString(index);
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
    for (int i = 0; i < words_.size(); i++) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, words_.getRaw(i));
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
      for (int i = 0; i < words_.size(); i++) {
        dataSize += computeStringSizeNoTag(words_.getRaw(i));
      }
      size += dataSize;
      size += 1 * getWordsList().size();
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
    if (!(obj instanceof com.google.showcase.v1beta1.PagedExpandResponseList)) {
      return super.equals(obj);
    }
    com.google.showcase.v1beta1.PagedExpandResponseList other =
        (com.google.showcase.v1beta1.PagedExpandResponseList) obj;

    if (!getWordsList().equals(other.getWordsList())) return false;
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
    if (getWordsCount() > 0) {
      hash = (37 * hash) + WORDS_FIELD_NUMBER;
      hash = (53 * hash) + getWordsList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.showcase.v1beta1.PagedExpandResponseList parseFrom(
      java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.showcase.v1beta1.PagedExpandResponseList parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.PagedExpandResponseList parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.showcase.v1beta1.PagedExpandResponseList parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.PagedExpandResponseList parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.showcase.v1beta1.PagedExpandResponseList parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.PagedExpandResponseList parseFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.PagedExpandResponseList parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.PagedExpandResponseList parseDelimitedFrom(
      java.io.InputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.PagedExpandResponseList parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.PagedExpandResponseList parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.PagedExpandResponseList parseFrom(
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

  public static Builder newBuilder(com.google.showcase.v1beta1.PagedExpandResponseList prototype) {
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
   * A list of words.
   * </pre>
   *
   * Protobuf type {@code google.showcase.v1beta1.PagedExpandResponseList}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.showcase.v1beta1.PagedExpandResponseList)
      com.google.showcase.v1beta1.PagedExpandResponseListOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.showcase.v1beta1.EchoOuterClass
          .internal_static_google_showcase_v1beta1_PagedExpandResponseList_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.showcase.v1beta1.EchoOuterClass
          .internal_static_google_showcase_v1beta1_PagedExpandResponseList_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.showcase.v1beta1.PagedExpandResponseList.class,
              com.google.showcase.v1beta1.PagedExpandResponseList.Builder.class);
    }

    // Construct using com.google.showcase.v1beta1.PagedExpandResponseList.newBuilder()
    private Builder() {}

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      words_ = com.google.protobuf.LazyStringArrayList.emptyList();
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.showcase.v1beta1.EchoOuterClass
          .internal_static_google_showcase_v1beta1_PagedExpandResponseList_descriptor;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.PagedExpandResponseList getDefaultInstanceForType() {
      return com.google.showcase.v1beta1.PagedExpandResponseList.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.PagedExpandResponseList build() {
      com.google.showcase.v1beta1.PagedExpandResponseList result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.PagedExpandResponseList buildPartial() {
      com.google.showcase.v1beta1.PagedExpandResponseList result =
          new com.google.showcase.v1beta1.PagedExpandResponseList(this);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.showcase.v1beta1.PagedExpandResponseList result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        words_.makeImmutable();
        result.words_ = words_;
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
      if (other instanceof com.google.showcase.v1beta1.PagedExpandResponseList) {
        return mergeFrom((com.google.showcase.v1beta1.PagedExpandResponseList) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.showcase.v1beta1.PagedExpandResponseList other) {
      if (other == com.google.showcase.v1beta1.PagedExpandResponseList.getDefaultInstance())
        return this;
      if (!other.words_.isEmpty()) {
        if (words_.isEmpty()) {
          words_ = other.words_;
          bitField0_ |= 0x00000001;
        } else {
          ensureWordsIsMutable();
          words_.addAll(other.words_);
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
                ensureWordsIsMutable();
                words_.add(s);
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

    private com.google.protobuf.LazyStringArrayList words_ =
        com.google.protobuf.LazyStringArrayList.emptyList();

    private void ensureWordsIsMutable() {
      if (!words_.isModifiable()) {
        words_ = new com.google.protobuf.LazyStringArrayList(words_);
      }
      bitField0_ |= 0x00000001;
    }
    /**
     * <code>repeated string words = 1;</code>
     *
     * @return A list containing the words.
     */
    public com.google.protobuf.ProtocolStringList getWordsList() {
      words_.makeImmutable();
      return words_;
    }
    /**
     * <code>repeated string words = 1;</code>
     *
     * @return The count of words.
     */
    public int getWordsCount() {
      return words_.size();
    }
    /**
     * <code>repeated string words = 1;</code>
     *
     * @param index The index of the element to return.
     * @return The words at the given index.
     */
    public java.lang.String getWords(int index) {
      return words_.get(index);
    }
    /**
     * <code>repeated string words = 1;</code>
     *
     * @param index The index of the value to return.
     * @return The bytes of the words at the given index.
     */
    public com.google.protobuf.ByteString getWordsBytes(int index) {
      return words_.getByteString(index);
    }
    /**
     * <code>repeated string words = 1;</code>
     *
     * @param index The index to set the value at.
     * @param value The words to set.
     * @return This builder for chaining.
     */
    public Builder setWords(int index, java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureWordsIsMutable();
      words_.set(index, value);
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>repeated string words = 1;</code>
     *
     * @param value The words to add.
     * @return This builder for chaining.
     */
    public Builder addWords(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureWordsIsMutable();
      words_.add(value);
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>repeated string words = 1;</code>
     *
     * @param values The words to add.
     * @return This builder for chaining.
     */
    public Builder addAllWords(java.lang.Iterable<java.lang.String> values) {
      ensureWordsIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(values, words_);
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>repeated string words = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearWords() {
      words_ = com.google.protobuf.LazyStringArrayList.emptyList();
      bitField0_ = (bitField0_ & ~0x00000001);
      ;
      onChanged();
      return this;
    }
    /**
     * <code>repeated string words = 1;</code>
     *
     * @param value The bytes of the words to add.
     * @return This builder for chaining.
     */
    public Builder addWordsBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);
      ensureWordsIsMutable();
      words_.add(value);
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

    // @@protoc_insertion_point(builder_scope:google.showcase.v1beta1.PagedExpandResponseList)
  }

  // @@protoc_insertion_point(class_scope:google.showcase.v1beta1.PagedExpandResponseList)
  private static final com.google.showcase.v1beta1.PagedExpandResponseList DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.showcase.v1beta1.PagedExpandResponseList();
  }

  public static com.google.showcase.v1beta1.PagedExpandResponseList getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PagedExpandResponseList> PARSER =
      new com.google.protobuf.AbstractParser<PagedExpandResponseList>() {
        @java.lang.Override
        public PagedExpandResponseList parsePartialFrom(
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

  public static com.google.protobuf.Parser<PagedExpandResponseList> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PagedExpandResponseList> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.showcase.v1beta1.PagedExpandResponseList getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
