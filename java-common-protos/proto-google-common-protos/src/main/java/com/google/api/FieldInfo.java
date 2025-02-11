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
// source: google/api/field_info.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

/**
 *
 *
 * <pre>
 * Rich semantic information of an API field beyond basic typing.
 * </pre>
 *
 * Protobuf type {@code google.api.FieldInfo}
 */
public final class FieldInfo
    extends com.google.protobuf.GeneratedMessageLite<FieldInfo, FieldInfo.Builder>
    implements
    // @@protoc_insertion_point(message_implements:google.api.FieldInfo)
    FieldInfoOrBuilder {
  private FieldInfo() {
    referencedTypes_ = emptyProtobufList();
  }
  /**
   *
   *
   * <pre>
   * The standard format of a field value. The supported formats are all backed
   * by either an RFC defined by the IETF or a Google-defined AIP.
   * </pre>
   *
   * Protobuf enum {@code google.api.FieldInfo.Format}
   */
  public enum Format implements com.google.protobuf.Internal.EnumLite {
    /**
     *
     *
     * <pre>
     * Default, unspecified value.
     * </pre>
     *
     * <code>FORMAT_UNSPECIFIED = 0;</code>
     */
    FORMAT_UNSPECIFIED(0),
    /**
     *
     *
     * <pre>
     * Universally Unique Identifier, version 4, value as defined by
     * https://datatracker.ietf.org/doc/html/rfc4122. The value may be
     * normalized to entirely lowercase letters. For example, the value
     * `F47AC10B-58CC-0372-8567-0E02B2C3D479` would be normalized to
     * `f47ac10b-58cc-0372-8567-0e02b2c3d479`.
     * </pre>
     *
     * <code>UUID4 = 1;</code>
     */
    UUID4(1),
    /**
     *
     *
     * <pre>
     * Internet Protocol v4 value as defined by [RFC
     * 791](https://datatracker.ietf.org/doc/html/rfc791). The value may be
     * condensed, with leading zeros in each octet stripped. For example,
     * `001.022.233.040` would be condensed to `1.22.233.40`.
     * </pre>
     *
     * <code>IPV4 = 2;</code>
     */
    IPV4(2),
    /**
     *
     *
     * <pre>
     * Internet Protocol v6 value as defined by [RFC
     * 2460](https://datatracker.ietf.org/doc/html/rfc2460). The value may be
     * normalized to entirely lowercase letters with zeros compressed, following
     * [RFC 5952](https://datatracker.ietf.org/doc/html/rfc5952). For example,
     * the value `2001:0DB8:0::0` would be normalized to `2001:db8::`.
     * </pre>
     *
     * <code>IPV6 = 3;</code>
     */
    IPV6(3),
    /**
     *
     *
     * <pre>
     * An IP address in either v4 or v6 format as described by the individual
     * values defined herein. See the comments on the IPV4 and IPV6 types for
     * allowed normalizations of each.
     * </pre>
     *
     * <code>IPV4_OR_IPV6 = 4;</code>
     */
    IPV4_OR_IPV6(4),
    UNRECOGNIZED(-1),
    ;

    /**
     *
     *
     * <pre>
     * Default, unspecified value.
     * </pre>
     *
     * <code>FORMAT_UNSPECIFIED = 0;</code>
     */
    public static final int FORMAT_UNSPECIFIED_VALUE = 0;
    /**
     *
     *
     * <pre>
     * Universally Unique Identifier, version 4, value as defined by
     * https://datatracker.ietf.org/doc/html/rfc4122. The value may be
     * normalized to entirely lowercase letters. For example, the value
     * `F47AC10B-58CC-0372-8567-0E02B2C3D479` would be normalized to
     * `f47ac10b-58cc-0372-8567-0e02b2c3d479`.
     * </pre>
     *
     * <code>UUID4 = 1;</code>
     */
    public static final int UUID4_VALUE = 1;
    /**
     *
     *
     * <pre>
     * Internet Protocol v4 value as defined by [RFC
     * 791](https://datatracker.ietf.org/doc/html/rfc791). The value may be
     * condensed, with leading zeros in each octet stripped. For example,
     * `001.022.233.040` would be condensed to `1.22.233.40`.
     * </pre>
     *
     * <code>IPV4 = 2;</code>
     */
    public static final int IPV4_VALUE = 2;
    /**
     *
     *
     * <pre>
     * Internet Protocol v6 value as defined by [RFC
     * 2460](https://datatracker.ietf.org/doc/html/rfc2460). The value may be
     * normalized to entirely lowercase letters with zeros compressed, following
     * [RFC 5952](https://datatracker.ietf.org/doc/html/rfc5952). For example,
     * the value `2001:0DB8:0::0` would be normalized to `2001:db8::`.
     * </pre>
     *
     * <code>IPV6 = 3;</code>
     */
    public static final int IPV6_VALUE = 3;
    /**
     *
     *
     * <pre>
     * An IP address in either v4 or v6 format as described by the individual
     * values defined herein. See the comments on the IPV4 and IPV6 types for
     * allowed normalizations of each.
     * </pre>
     *
     * <code>IPV4_OR_IPV6 = 4;</code>
     */
    public static final int IPV4_OR_IPV6_VALUE = 4;

    @java.lang.Override
    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The number of the enum to look for.
     * @return The enum associated with the given number.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static Format valueOf(int value) {
      return forNumber(value);
    }

    public static Format forNumber(int value) {
      switch (value) {
        case 0:
          return FORMAT_UNSPECIFIED;
        case 1:
          return UUID4;
        case 2:
          return IPV4;
        case 3:
          return IPV6;
        case 4:
          return IPV4_OR_IPV6;
        default:
          return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<Format> internalGetValueMap() {
      return internalValueMap;
    }

    private static final com.google.protobuf.Internal.EnumLiteMap<Format> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<Format>() {
          @java.lang.Override
          public Format findValueByNumber(int number) {
            return Format.forNumber(number);
          }
        };

    public static com.google.protobuf.Internal.EnumVerifier internalGetVerifier() {
      return FormatVerifier.INSTANCE;
    }

    private static final class FormatVerifier implements com.google.protobuf.Internal.EnumVerifier {
      static final com.google.protobuf.Internal.EnumVerifier INSTANCE = new FormatVerifier();

      @java.lang.Override
      public boolean isInRange(int number) {
        return Format.forNumber(number) != null;
      }
    };

    private final int value;

    private Format(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:google.api.FieldInfo.Format)
  }

  public static final int FORMAT_FIELD_NUMBER = 1;
  private int format_;
  /**
   *
   *
   * <pre>
   * The standard format of a field value. This does not explicitly configure
   * any API consumer, just documents the API's format for the field it is
   * applied to.
   * </pre>
   *
   * <code>.google.api.FieldInfo.Format format = 1;</code>
   *
   * @return The enum numeric value on the wire for format.
   */
  @java.lang.Override
  public int getFormatValue() {
    return format_;
  }
  /**
   *
   *
   * <pre>
   * The standard format of a field value. This does not explicitly configure
   * any API consumer, just documents the API's format for the field it is
   * applied to.
   * </pre>
   *
   * <code>.google.api.FieldInfo.Format format = 1;</code>
   *
   * @return The format.
   */
  @java.lang.Override
  public com.google.api.FieldInfo.Format getFormat() {
    com.google.api.FieldInfo.Format result = com.google.api.FieldInfo.Format.forNumber(format_);
    return result == null ? com.google.api.FieldInfo.Format.UNRECOGNIZED : result;
  }
  /**
   *
   *
   * <pre>
   * The standard format of a field value. This does not explicitly configure
   * any API consumer, just documents the API's format for the field it is
   * applied to.
   * </pre>
   *
   * <code>.google.api.FieldInfo.Format format = 1;</code>
   *
   * @param value The enum numeric value on the wire for format to set.
   */
  private void setFormatValue(int value) {
    format_ = value;
  }
  /**
   *
   *
   * <pre>
   * The standard format of a field value. This does not explicitly configure
   * any API consumer, just documents the API's format for the field it is
   * applied to.
   * </pre>
   *
   * <code>.google.api.FieldInfo.Format format = 1;</code>
   *
   * @param value The format to set.
   */
  private void setFormat(com.google.api.FieldInfo.Format value) {
    format_ = value.getNumber();
  }
  /**
   *
   *
   * <pre>
   * The standard format of a field value. This does not explicitly configure
   * any API consumer, just documents the API's format for the field it is
   * applied to.
   * </pre>
   *
   * <code>.google.api.FieldInfo.Format format = 1;</code>
   */
  private void clearFormat() {

    format_ = 0;
  }

  public static final int REFERENCED_TYPES_FIELD_NUMBER = 2;
  private com.google.protobuf.Internal.ProtobufList<com.google.api.TypeReference> referencedTypes_;
  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  @java.lang.Override
  public java.util.List<com.google.api.TypeReference> getReferencedTypesList() {
    return referencedTypes_;
  }
  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  public java.util.List<? extends com.google.api.TypeReferenceOrBuilder>
      getReferencedTypesOrBuilderList() {
    return referencedTypes_;
  }
  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  @java.lang.Override
  public int getReferencedTypesCount() {
    return referencedTypes_.size();
  }
  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  @java.lang.Override
  public com.google.api.TypeReference getReferencedTypes(int index) {
    return referencedTypes_.get(index);
  }
  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  public com.google.api.TypeReferenceOrBuilder getReferencedTypesOrBuilder(int index) {
    return referencedTypes_.get(index);
  }

  private void ensureReferencedTypesIsMutable() {
    com.google.protobuf.Internal.ProtobufList<com.google.api.TypeReference> tmp = referencedTypes_;
    if (!tmp.isModifiable()) {
      referencedTypes_ = com.google.protobuf.GeneratedMessageLite.mutableCopy(tmp);
    }
  }

  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  private void setReferencedTypes(int index, com.google.api.TypeReference value) {
    value.getClass();
    ensureReferencedTypesIsMutable();
    referencedTypes_.set(index, value);
  }
  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  private void addReferencedTypes(com.google.api.TypeReference value) {
    value.getClass();
    ensureReferencedTypesIsMutable();
    referencedTypes_.add(value);
  }
  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  private void addReferencedTypes(int index, com.google.api.TypeReference value) {
    value.getClass();
    ensureReferencedTypesIsMutable();
    referencedTypes_.add(index, value);
  }
  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  private void addAllReferencedTypes(
      java.lang.Iterable<? extends com.google.api.TypeReference> values) {
    ensureReferencedTypesIsMutable();
    com.google.protobuf.AbstractMessageLite.addAll(values, referencedTypes_);
  }
  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  private void clearReferencedTypes() {
    referencedTypes_ = emptyProtobufList();
  }
  /**
   *
   *
   * <pre>
   * The type(s) that the annotated, generic field may represent.
   *
   * Currently, this must only be used on fields of type `google.protobuf.Any`.
   * Supporting other generic types may be considered in the future.
   * </pre>
   *
   * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
   */
  private void removeReferencedTypes(int index) {
    ensureReferencedTypesIsMutable();
    referencedTypes_.remove(index);
  }

  public static com.google.api.FieldInfo parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.FieldInfo parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.FieldInfo parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.FieldInfo parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.FieldInfo parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.FieldInfo parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.FieldInfo parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.FieldInfo parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.FieldInfo parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.FieldInfo parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.FieldInfo parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.FieldInfo parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }

  public static Builder newBuilder(com.google.api.FieldInfo prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   *
   *
   * <pre>
   * Rich semantic information of an API field beyond basic typing.
   * </pre>
   *
   * Protobuf type {@code google.api.FieldInfo}
   */
  public static final class Builder
      extends com.google.protobuf.GeneratedMessageLite.Builder<com.google.api.FieldInfo, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.FieldInfo)
      com.google.api.FieldInfoOrBuilder {
    // Construct using com.google.api.FieldInfo.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }

    /**
     *
     *
     * <pre>
     * The standard format of a field value. This does not explicitly configure
     * any API consumer, just documents the API's format for the field it is
     * applied to.
     * </pre>
     *
     * <code>.google.api.FieldInfo.Format format = 1;</code>
     *
     * @return The enum numeric value on the wire for format.
     */
    @java.lang.Override
    public int getFormatValue() {
      return instance.getFormatValue();
    }
    /**
     *
     *
     * <pre>
     * The standard format of a field value. This does not explicitly configure
     * any API consumer, just documents the API's format for the field it is
     * applied to.
     * </pre>
     *
     * <code>.google.api.FieldInfo.Format format = 1;</code>
     *
     * @param value The format to set.
     * @return This builder for chaining.
     */
    public Builder setFormatValue(int value) {
      copyOnWrite();
      instance.setFormatValue(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The standard format of a field value. This does not explicitly configure
     * any API consumer, just documents the API's format for the field it is
     * applied to.
     * </pre>
     *
     * <code>.google.api.FieldInfo.Format format = 1;</code>
     *
     * @return The format.
     */
    @java.lang.Override
    public com.google.api.FieldInfo.Format getFormat() {
      return instance.getFormat();
    }
    /**
     *
     *
     * <pre>
     * The standard format of a field value. This does not explicitly configure
     * any API consumer, just documents the API's format for the field it is
     * applied to.
     * </pre>
     *
     * <code>.google.api.FieldInfo.Format format = 1;</code>
     *
     * @param value The enum numeric value on the wire for format to set.
     * @return This builder for chaining.
     */
    public Builder setFormat(com.google.api.FieldInfo.Format value) {
      copyOnWrite();
      instance.setFormat(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The standard format of a field value. This does not explicitly configure
     * any API consumer, just documents the API's format for the field it is
     * applied to.
     * </pre>
     *
     * <code>.google.api.FieldInfo.Format format = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearFormat() {
      copyOnWrite();
      instance.clearFormat();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The type(s) that the annotated, generic field may represent.
     *
     * Currently, this must only be used on fields of type `google.protobuf.Any`.
     * Supporting other generic types may be considered in the future.
     * </pre>
     *
     * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
     */
    @java.lang.Override
    public java.util.List<com.google.api.TypeReference> getReferencedTypesList() {
      return java.util.Collections.unmodifiableList(instance.getReferencedTypesList());
    }
    /**
     *
     *
     * <pre>
     * The type(s) that the annotated, generic field may represent.
     *
     * Currently, this must only be used on fields of type `google.protobuf.Any`.
     * Supporting other generic types may be considered in the future.
     * </pre>
     *
     * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
     */
    @java.lang.Override
    public int getReferencedTypesCount() {
      return instance.getReferencedTypesCount();
    }
    /**
     *
     *
     * <pre>
     * The type(s) that the annotated, generic field may represent.
     *
     * Currently, this must only be used on fields of type `google.protobuf.Any`.
     * Supporting other generic types may be considered in the future.
     * </pre>
     *
     * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
     */

    @java.lang.Override
    public com.google.api.TypeReference getReferencedTypes(int index) {
      return instance.getReferencedTypes(index);
    }
    /**
     *
     *
     * <pre>
     * The type(s) that the annotated, generic field may represent.
     *
     * Currently, this must only be used on fields of type `google.protobuf.Any`.
     * Supporting other generic types may be considered in the future.
     * </pre>
     *
     * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
     */
    public Builder setReferencedTypes(int index, com.google.api.TypeReference value) {
      copyOnWrite();
      instance.setReferencedTypes(index, value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The type(s) that the annotated, generic field may represent.
     *
     * Currently, this must only be used on fields of type `google.protobuf.Any`.
     * Supporting other generic types may be considered in the future.
     * </pre>
     *
     * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
     */
    public Builder setReferencedTypes(
        int index, com.google.api.TypeReference.Builder builderForValue) {
      copyOnWrite();
      instance.setReferencedTypes(index, builderForValue.build());
      return this;
    }
    /**
     *
     *
     * <pre>
     * The type(s) that the annotated, generic field may represent.
     *
     * Currently, this must only be used on fields of type `google.protobuf.Any`.
     * Supporting other generic types may be considered in the future.
     * </pre>
     *
     * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
     */
    public Builder addReferencedTypes(com.google.api.TypeReference value) {
      copyOnWrite();
      instance.addReferencedTypes(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The type(s) that the annotated, generic field may represent.
     *
     * Currently, this must only be used on fields of type `google.protobuf.Any`.
     * Supporting other generic types may be considered in the future.
     * </pre>
     *
     * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
     */
    public Builder addReferencedTypes(int index, com.google.api.TypeReference value) {
      copyOnWrite();
      instance.addReferencedTypes(index, value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The type(s) that the annotated, generic field may represent.
     *
     * Currently, this must only be used on fields of type `google.protobuf.Any`.
     * Supporting other generic types may be considered in the future.
     * </pre>
     *
     * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
     */
    public Builder addReferencedTypes(com.google.api.TypeReference.Builder builderForValue) {
      copyOnWrite();
      instance.addReferencedTypes(builderForValue.build());
      return this;
    }
    /**
     *
     *
     * <pre>
     * The type(s) that the annotated, generic field may represent.
     *
     * Currently, this must only be used on fields of type `google.protobuf.Any`.
     * Supporting other generic types may be considered in the future.
     * </pre>
     *
     * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
     */
    public Builder addReferencedTypes(
        int index, com.google.api.TypeReference.Builder builderForValue) {
      copyOnWrite();
      instance.addReferencedTypes(index, builderForValue.build());
      return this;
    }
    /**
     *
     *
     * <pre>
     * The type(s) that the annotated, generic field may represent.
     *
     * Currently, this must only be used on fields of type `google.protobuf.Any`.
     * Supporting other generic types may be considered in the future.
     * </pre>
     *
     * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
     */
    public Builder addAllReferencedTypes(
        java.lang.Iterable<? extends com.google.api.TypeReference> values) {
      copyOnWrite();
      instance.addAllReferencedTypes(values);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The type(s) that the annotated, generic field may represent.
     *
     * Currently, this must only be used on fields of type `google.protobuf.Any`.
     * Supporting other generic types may be considered in the future.
     * </pre>
     *
     * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
     */
    public Builder clearReferencedTypes() {
      copyOnWrite();
      instance.clearReferencedTypes();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The type(s) that the annotated, generic field may represent.
     *
     * Currently, this must only be used on fields of type `google.protobuf.Any`.
     * Supporting other generic types may be considered in the future.
     * </pre>
     *
     * <code>repeated .google.api.TypeReference referenced_types = 2;</code>
     */
    public Builder removeReferencedTypes(int index) {
      copyOnWrite();
      instance.removeReferencedTypes(index);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.api.FieldInfo)
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
          return new com.google.api.FieldInfo();
        }
      case NEW_BUILDER:
        {
          return new Builder();
        }
      case BUILD_MESSAGE_INFO:
        {
          java.lang.Object[] objects =
              new java.lang.Object[] {
                "format_", "referencedTypes_", com.google.api.TypeReference.class,
              };
          java.lang.String info =
              "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0001\u0000\u0001\f\u0002\u001b"
                  + "";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
      case GET_DEFAULT_INSTANCE:
        {
          return DEFAULT_INSTANCE;
        }
      case GET_PARSER:
        {
          com.google.protobuf.Parser<com.google.api.FieldInfo> parser = PARSER;
          if (parser == null) {
            synchronized (com.google.api.FieldInfo.class) {
              parser = PARSER;
              if (parser == null) {
                parser = new DefaultInstanceBasedParser<com.google.api.FieldInfo>(DEFAULT_INSTANCE);
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

  // @@protoc_insertion_point(class_scope:google.api.FieldInfo)
  private static final com.google.api.FieldInfo DEFAULT_INSTANCE;

  static {
    FieldInfo defaultInstance = new FieldInfo();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        FieldInfo.class, defaultInstance);
  }

  public static com.google.api.FieldInfo getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<FieldInfo> PARSER;

  public static com.google.protobuf.Parser<FieldInfo> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
