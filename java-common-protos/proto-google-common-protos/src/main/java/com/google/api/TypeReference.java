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
// source: google/api/field_info.proto

// Protobuf Java Version: 3.25.4
package com.google.api;

/**
 *
 *
 * <pre>
 * A reference to a message type, for use in [FieldInfo][google.api.FieldInfo].
 * </pre>
 *
 * Protobuf type {@code google.api.TypeReference}
 */
public final class TypeReference extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.api.TypeReference)
    TypeReferenceOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use TypeReference.newBuilder() to construct.
  private TypeReference(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private TypeReference() {
    typeName_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new TypeReference();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.api.FieldInfoProto.internal_static_google_api_TypeReference_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.api.FieldInfoProto.internal_static_google_api_TypeReference_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.api.TypeReference.class, com.google.api.TypeReference.Builder.class);
  }

  public static final int TYPE_NAME_FIELD_NUMBER = 1;

  @SuppressWarnings("serial")
  private volatile java.lang.Object typeName_ = "";
  /**
   *
   *
   * <pre>
   * The name of the type that the annotated, generic field may represent.
   * If the type is in the same protobuf package, the value can be the simple
   * message name e.g., `"MyMessage"`. Otherwise, the value must be the
   * fully-qualified message name e.g., `"google.library.v1.Book"`.
   *
   * If the type(s) are unknown to the service (e.g. the field accepts generic
   * user input), use the wildcard `"*"` to denote this behavior.
   *
   * See [AIP-202](https://google.aip.dev/202#type-references) for more details.
   * </pre>
   *
   * <code>string type_name = 1;</code>
   *
   * @return The typeName.
   */
  @java.lang.Override
  public java.lang.String getTypeName() {
    java.lang.Object ref = typeName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      typeName_ = s;
      return s;
    }
  }
  /**
   *
   *
   * <pre>
   * The name of the type that the annotated, generic field may represent.
   * If the type is in the same protobuf package, the value can be the simple
   * message name e.g., `"MyMessage"`. Otherwise, the value must be the
   * fully-qualified message name e.g., `"google.library.v1.Book"`.
   *
   * If the type(s) are unknown to the service (e.g. the field accepts generic
   * user input), use the wildcard `"*"` to denote this behavior.
   *
   * See [AIP-202](https://google.aip.dev/202#type-references) for more details.
   * </pre>
   *
   * <code>string type_name = 1;</code>
   *
   * @return The bytes for typeName.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getTypeNameBytes() {
    java.lang.Object ref = typeName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      typeName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(typeName_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, typeName_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(typeName_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, typeName_);
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
    if (!(obj instanceof com.google.api.TypeReference)) {
      return super.equals(obj);
    }
    com.google.api.TypeReference other = (com.google.api.TypeReference) obj;

    if (!getTypeName().equals(other.getTypeName())) return false;
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
    hash = (37 * hash) + TYPE_NAME_FIELD_NUMBER;
    hash = (53 * hash) + getTypeName().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.api.TypeReference parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.TypeReference parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.TypeReference parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.TypeReference parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.TypeReference parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.TypeReference parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.TypeReference parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.api.TypeReference parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.api.TypeReference parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.api.TypeReference parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.api.TypeReference parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.api.TypeReference parseFrom(
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

  public static Builder newBuilder(com.google.api.TypeReference prototype) {
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
   * A reference to a message type, for use in [FieldInfo][google.api.FieldInfo].
   * </pre>
   *
   * Protobuf type {@code google.api.TypeReference}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.TypeReference)
      com.google.api.TypeReferenceOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.api.FieldInfoProto.internal_static_google_api_TypeReference_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.api.FieldInfoProto
          .internal_static_google_api_TypeReference_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.api.TypeReference.class, com.google.api.TypeReference.Builder.class);
    }

    // Construct using com.google.api.TypeReference.newBuilder()
    private Builder() {}

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      typeName_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.api.FieldInfoProto.internal_static_google_api_TypeReference_descriptor;
    }

    @java.lang.Override
    public com.google.api.TypeReference getDefaultInstanceForType() {
      return com.google.api.TypeReference.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.api.TypeReference build() {
      com.google.api.TypeReference result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.api.TypeReference buildPartial() {
      com.google.api.TypeReference result = new com.google.api.TypeReference(this);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.api.TypeReference result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.typeName_ = typeName_;
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
      if (other instanceof com.google.api.TypeReference) {
        return mergeFrom((com.google.api.TypeReference) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.api.TypeReference other) {
      if (other == com.google.api.TypeReference.getDefaultInstance()) return this;
      if (!other.getTypeName().isEmpty()) {
        typeName_ = other.typeName_;
        bitField0_ |= 0x00000001;
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
                typeName_ = input.readStringRequireUtf8();
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

    private java.lang.Object typeName_ = "";
    /**
     *
     *
     * <pre>
     * The name of the type that the annotated, generic field may represent.
     * If the type is in the same protobuf package, the value can be the simple
     * message name e.g., `"MyMessage"`. Otherwise, the value must be the
     * fully-qualified message name e.g., `"google.library.v1.Book"`.
     *
     * If the type(s) are unknown to the service (e.g. the field accepts generic
     * user input), use the wildcard `"*"` to denote this behavior.
     *
     * See [AIP-202](https://google.aip.dev/202#type-references) for more details.
     * </pre>
     *
     * <code>string type_name = 1;</code>
     *
     * @return The typeName.
     */
    public java.lang.String getTypeName() {
      java.lang.Object ref = typeName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        typeName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     *
     *
     * <pre>
     * The name of the type that the annotated, generic field may represent.
     * If the type is in the same protobuf package, the value can be the simple
     * message name e.g., `"MyMessage"`. Otherwise, the value must be the
     * fully-qualified message name e.g., `"google.library.v1.Book"`.
     *
     * If the type(s) are unknown to the service (e.g. the field accepts generic
     * user input), use the wildcard `"*"` to denote this behavior.
     *
     * See [AIP-202](https://google.aip.dev/202#type-references) for more details.
     * </pre>
     *
     * <code>string type_name = 1;</code>
     *
     * @return The bytes for typeName.
     */
    public com.google.protobuf.ByteString getTypeNameBytes() {
      java.lang.Object ref = typeName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        typeName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     *
     *
     * <pre>
     * The name of the type that the annotated, generic field may represent.
     * If the type is in the same protobuf package, the value can be the simple
     * message name e.g., `"MyMessage"`. Otherwise, the value must be the
     * fully-qualified message name e.g., `"google.library.v1.Book"`.
     *
     * If the type(s) are unknown to the service (e.g. the field accepts generic
     * user input), use the wildcard `"*"` to denote this behavior.
     *
     * See [AIP-202](https://google.aip.dev/202#type-references) for more details.
     * </pre>
     *
     * <code>string type_name = 1;</code>
     *
     * @param value The typeName to set.
     * @return This builder for chaining.
     */
    public Builder setTypeName(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      typeName_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The name of the type that the annotated, generic field may represent.
     * If the type is in the same protobuf package, the value can be the simple
     * message name e.g., `"MyMessage"`. Otherwise, the value must be the
     * fully-qualified message name e.g., `"google.library.v1.Book"`.
     *
     * If the type(s) are unknown to the service (e.g. the field accepts generic
     * user input), use the wildcard `"*"` to denote this behavior.
     *
     * See [AIP-202](https://google.aip.dev/202#type-references) for more details.
     * </pre>
     *
     * <code>string type_name = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearTypeName() {
      typeName_ = getDefaultInstance().getTypeName();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The name of the type that the annotated, generic field may represent.
     * If the type is in the same protobuf package, the value can be the simple
     * message name e.g., `"MyMessage"`. Otherwise, the value must be the
     * fully-qualified message name e.g., `"google.library.v1.Book"`.
     *
     * If the type(s) are unknown to the service (e.g. the field accepts generic
     * user input), use the wildcard `"*"` to denote this behavior.
     *
     * See [AIP-202](https://google.aip.dev/202#type-references) for more details.
     * </pre>
     *
     * <code>string type_name = 1;</code>
     *
     * @param value The bytes for typeName to set.
     * @return This builder for chaining.
     */
    public Builder setTypeNameBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);
      typeName_ = value;
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

    // @@protoc_insertion_point(builder_scope:google.api.TypeReference)
  }

  // @@protoc_insertion_point(class_scope:google.api.TypeReference)
  private static final com.google.api.TypeReference DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.api.TypeReference();
  }

  public static com.google.api.TypeReference getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<TypeReference> PARSER =
      new com.google.protobuf.AbstractParser<TypeReference>() {
        @java.lang.Override
        public TypeReference parsePartialFrom(
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

  public static com.google.protobuf.Parser<TypeReference> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<TypeReference> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.api.TypeReference getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}