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
// source: google/apps/card/v1/card.proto

// Protobuf Java Version: 3.25.8
package com.google.apps.card.v1;

/**
 *
 *
 * <pre>
 * Represents the crop style applied to an image.
 *
 * [Google Workspace Add-ons and
 * Chat apps](https://developers.google.com/workspace/extend):
 *
 * For example, here's how to apply a 16:9 aspect ratio:
 *
 * ```
 * cropStyle {
 *  "type": "RECTANGLE_CUSTOM",
 *  "aspectRatio": 16/9
 * }
 * ```
 * </pre>
 *
 * Protobuf type {@code google.apps.card.v1.ImageCropStyle}
 */
public final class ImageCropStyle extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.apps.card.v1.ImageCropStyle)
    ImageCropStyleOrBuilder {
  private static final long serialVersionUID = 0L;

  // Use ImageCropStyle.newBuilder() to construct.
  private ImageCropStyle(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private ImageCropStyle() {
    type_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new ImageCropStyle();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.apps.card.v1.CardProto
        .internal_static_google_apps_card_v1_ImageCropStyle_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.apps.card.v1.CardProto
        .internal_static_google_apps_card_v1_ImageCropStyle_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.apps.card.v1.ImageCropStyle.class,
            com.google.apps.card.v1.ImageCropStyle.Builder.class);
  }

  /**
   *
   *
   * <pre>
   * Represents the crop style applied to an image.
   *
   * [Google Workspace Add-ons
   * and Chat apps](https://developers.google.com/workspace/extend):
   * </pre>
   *
   * Protobuf enum {@code google.apps.card.v1.ImageCropStyle.ImageCropType}
   */
  public enum ImageCropType implements com.google.protobuf.ProtocolMessageEnum {
    /**
     *
     *
     * <pre>
     * Don't use. Unspecified.
     * </pre>
     *
     * <code>IMAGE_CROP_TYPE_UNSPECIFIED = 0;</code>
     */
    IMAGE_CROP_TYPE_UNSPECIFIED(0),
    /**
     *
     *
     * <pre>
     * Default value. Applies a square crop.
     * </pre>
     *
     * <code>SQUARE = 1;</code>
     */
    SQUARE(1),
    /**
     *
     *
     * <pre>
     * Applies a circular crop.
     * </pre>
     *
     * <code>CIRCLE = 2;</code>
     */
    CIRCLE(2),
    /**
     *
     *
     * <pre>
     * Applies a rectangular crop with a custom aspect ratio. Set the custom
     * aspect ratio with `aspectRatio`.
     * </pre>
     *
     * <code>RECTANGLE_CUSTOM = 3;</code>
     */
    RECTANGLE_CUSTOM(3),
    /**
     *
     *
     * <pre>
     * Applies a rectangular crop with a 4:3 aspect ratio.
     * </pre>
     *
     * <code>RECTANGLE_4_3 = 4;</code>
     */
    RECTANGLE_4_3(4),
    UNRECOGNIZED(-1),
    ;

    /**
     *
     *
     * <pre>
     * Don't use. Unspecified.
     * </pre>
     *
     * <code>IMAGE_CROP_TYPE_UNSPECIFIED = 0;</code>
     */
    public static final int IMAGE_CROP_TYPE_UNSPECIFIED_VALUE = 0;

    /**
     *
     *
     * <pre>
     * Default value. Applies a square crop.
     * </pre>
     *
     * <code>SQUARE = 1;</code>
     */
    public static final int SQUARE_VALUE = 1;

    /**
     *
     *
     * <pre>
     * Applies a circular crop.
     * </pre>
     *
     * <code>CIRCLE = 2;</code>
     */
    public static final int CIRCLE_VALUE = 2;

    /**
     *
     *
     * <pre>
     * Applies a rectangular crop with a custom aspect ratio. Set the custom
     * aspect ratio with `aspectRatio`.
     * </pre>
     *
     * <code>RECTANGLE_CUSTOM = 3;</code>
     */
    public static final int RECTANGLE_CUSTOM_VALUE = 3;

    /**
     *
     *
     * <pre>
     * Applies a rectangular crop with a 4:3 aspect ratio.
     * </pre>
     *
     * <code>RECTANGLE_4_3 = 4;</code>
     */
    public static final int RECTANGLE_4_3_VALUE = 4;

    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static ImageCropType valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static ImageCropType forNumber(int value) {
      switch (value) {
        case 0:
          return IMAGE_CROP_TYPE_UNSPECIFIED;
        case 1:
          return SQUARE;
        case 2:
          return CIRCLE;
        case 3:
          return RECTANGLE_CUSTOM;
        case 4:
          return RECTANGLE_4_3;
        default:
          return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<ImageCropType> internalGetValueMap() {
      return internalValueMap;
    }

    private static final com.google.protobuf.Internal.EnumLiteMap<ImageCropType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<ImageCropType>() {
          public ImageCropType findValueByNumber(int number) {
            return ImageCropType.forNumber(number);
          }
        };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor getValueDescriptor() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalStateException(
            "Can't get the descriptor of an unrecognized enum value.");
      }
      return getDescriptor().getValues().get(ordinal());
    }

    public final com.google.protobuf.Descriptors.EnumDescriptor getDescriptorForType() {
      return getDescriptor();
    }

    public static final com.google.protobuf.Descriptors.EnumDescriptor getDescriptor() {
      return com.google.apps.card.v1.ImageCropStyle.getDescriptor().getEnumTypes().get(0);
    }

    private static final ImageCropType[] VALUES = values();

    public static ImageCropType valueOf(com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException("EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private ImageCropType(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:google.apps.card.v1.ImageCropStyle.ImageCropType)
  }

  public static final int TYPE_FIELD_NUMBER = 1;
  private int type_ = 0;

  /**
   *
   *
   * <pre>
   * The crop type.
   * </pre>
   *
   * <code>.google.apps.card.v1.ImageCropStyle.ImageCropType type = 1;</code>
   *
   * @return The enum numeric value on the wire for type.
   */
  @java.lang.Override
  public int getTypeValue() {
    return type_;
  }

  /**
   *
   *
   * <pre>
   * The crop type.
   * </pre>
   *
   * <code>.google.apps.card.v1.ImageCropStyle.ImageCropType type = 1;</code>
   *
   * @return The type.
   */
  @java.lang.Override
  public com.google.apps.card.v1.ImageCropStyle.ImageCropType getType() {
    com.google.apps.card.v1.ImageCropStyle.ImageCropType result =
        com.google.apps.card.v1.ImageCropStyle.ImageCropType.forNumber(type_);
    return result == null
        ? com.google.apps.card.v1.ImageCropStyle.ImageCropType.UNRECOGNIZED
        : result;
  }

  public static final int ASPECT_RATIO_FIELD_NUMBER = 2;
  private double aspectRatio_ = 0D;

  /**
   *
   *
   * <pre>
   * The aspect ratio to use if the crop type is `RECTANGLE_CUSTOM`.
   *
   * For example, here's how to apply a 16:9 aspect ratio:
   *
   * ```
   * cropStyle {
   *  "type": "RECTANGLE_CUSTOM",
   *  "aspectRatio": 16/9
   * }
   * ```
   * </pre>
   *
   * <code>double aspect_ratio = 2;</code>
   *
   * @return The aspectRatio.
   */
  @java.lang.Override
  public double getAspectRatio() {
    return aspectRatio_;
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
    if (type_
        != com.google.apps.card.v1.ImageCropStyle.ImageCropType.IMAGE_CROP_TYPE_UNSPECIFIED
            .getNumber()) {
      output.writeEnum(1, type_);
    }
    if (java.lang.Double.doubleToRawLongBits(aspectRatio_) != 0) {
      output.writeDouble(2, aspectRatio_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (type_
        != com.google.apps.card.v1.ImageCropStyle.ImageCropType.IMAGE_CROP_TYPE_UNSPECIFIED
            .getNumber()) {
      size += com.google.protobuf.CodedOutputStream.computeEnumSize(1, type_);
    }
    if (java.lang.Double.doubleToRawLongBits(aspectRatio_) != 0) {
      size += com.google.protobuf.CodedOutputStream.computeDoubleSize(2, aspectRatio_);
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
    if (!(obj instanceof com.google.apps.card.v1.ImageCropStyle)) {
      return super.equals(obj);
    }
    com.google.apps.card.v1.ImageCropStyle other = (com.google.apps.card.v1.ImageCropStyle) obj;

    if (type_ != other.type_) return false;
    if (java.lang.Double.doubleToLongBits(getAspectRatio())
        != java.lang.Double.doubleToLongBits(other.getAspectRatio())) return false;
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
    hash = (37 * hash) + TYPE_FIELD_NUMBER;
    hash = (53 * hash) + type_;
    hash = (37 * hash) + ASPECT_RATIO_FIELD_NUMBER;
    hash =
        (53 * hash)
            + com.google.protobuf.Internal.hashLong(
                java.lang.Double.doubleToLongBits(getAspectRatio()));
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.apps.card.v1.ImageCropStyle parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.apps.card.v1.ImageCropStyle parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.apps.card.v1.ImageCropStyle parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.apps.card.v1.ImageCropStyle parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.apps.card.v1.ImageCropStyle parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.apps.card.v1.ImageCropStyle parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.apps.card.v1.ImageCropStyle parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.apps.card.v1.ImageCropStyle parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.apps.card.v1.ImageCropStyle parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.apps.card.v1.ImageCropStyle parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.apps.card.v1.ImageCropStyle parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.apps.card.v1.ImageCropStyle parseFrom(
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

  public static Builder newBuilder(com.google.apps.card.v1.ImageCropStyle prototype) {
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
   * Represents the crop style applied to an image.
   *
   * [Google Workspace Add-ons and
   * Chat apps](https://developers.google.com/workspace/extend):
   *
   * For example, here's how to apply a 16:9 aspect ratio:
   *
   * ```
   * cropStyle {
   *  "type": "RECTANGLE_CUSTOM",
   *  "aspectRatio": 16/9
   * }
   * ```
   * </pre>
   *
   * Protobuf type {@code google.apps.card.v1.ImageCropStyle}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.apps.card.v1.ImageCropStyle)
      com.google.apps.card.v1.ImageCropStyleOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.apps.card.v1.CardProto
          .internal_static_google_apps_card_v1_ImageCropStyle_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.apps.card.v1.CardProto
          .internal_static_google_apps_card_v1_ImageCropStyle_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.apps.card.v1.ImageCropStyle.class,
              com.google.apps.card.v1.ImageCropStyle.Builder.class);
    }

    // Construct using com.google.apps.card.v1.ImageCropStyle.newBuilder()
    private Builder() {}

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      type_ = 0;
      aspectRatio_ = 0D;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.apps.card.v1.CardProto
          .internal_static_google_apps_card_v1_ImageCropStyle_descriptor;
    }

    @java.lang.Override
    public com.google.apps.card.v1.ImageCropStyle getDefaultInstanceForType() {
      return com.google.apps.card.v1.ImageCropStyle.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.apps.card.v1.ImageCropStyle build() {
      com.google.apps.card.v1.ImageCropStyle result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.apps.card.v1.ImageCropStyle buildPartial() {
      com.google.apps.card.v1.ImageCropStyle result =
          new com.google.apps.card.v1.ImageCropStyle(this);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.apps.card.v1.ImageCropStyle result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.type_ = type_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.aspectRatio_ = aspectRatio_;
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
      if (other instanceof com.google.apps.card.v1.ImageCropStyle) {
        return mergeFrom((com.google.apps.card.v1.ImageCropStyle) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.apps.card.v1.ImageCropStyle other) {
      if (other == com.google.apps.card.v1.ImageCropStyle.getDefaultInstance()) return this;
      if (other.type_ != 0) {
        setTypeValue(other.getTypeValue());
      }
      if (other.getAspectRatio() != 0D) {
        setAspectRatio(other.getAspectRatio());
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
            case 8:
              {
                type_ = input.readEnum();
                bitField0_ |= 0x00000001;
                break;
              } // case 8
            case 17:
              {
                aspectRatio_ = input.readDouble();
                bitField0_ |= 0x00000002;
                break;
              } // case 17
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

    private int type_ = 0;

    /**
     *
     *
     * <pre>
     * The crop type.
     * </pre>
     *
     * <code>.google.apps.card.v1.ImageCropStyle.ImageCropType type = 1;</code>
     *
     * @return The enum numeric value on the wire for type.
     */
    @java.lang.Override
    public int getTypeValue() {
      return type_;
    }

    /**
     *
     *
     * <pre>
     * The crop type.
     * </pre>
     *
     * <code>.google.apps.card.v1.ImageCropStyle.ImageCropType type = 1;</code>
     *
     * @param value The enum numeric value on the wire for type to set.
     * @return This builder for chaining.
     */
    public Builder setTypeValue(int value) {
      type_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The crop type.
     * </pre>
     *
     * <code>.google.apps.card.v1.ImageCropStyle.ImageCropType type = 1;</code>
     *
     * @return The type.
     */
    @java.lang.Override
    public com.google.apps.card.v1.ImageCropStyle.ImageCropType getType() {
      com.google.apps.card.v1.ImageCropStyle.ImageCropType result =
          com.google.apps.card.v1.ImageCropStyle.ImageCropType.forNumber(type_);
      return result == null
          ? com.google.apps.card.v1.ImageCropStyle.ImageCropType.UNRECOGNIZED
          : result;
    }

    /**
     *
     *
     * <pre>
     * The crop type.
     * </pre>
     *
     * <code>.google.apps.card.v1.ImageCropStyle.ImageCropType type = 1;</code>
     *
     * @param value The type to set.
     * @return This builder for chaining.
     */
    public Builder setType(com.google.apps.card.v1.ImageCropStyle.ImageCropType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000001;
      type_ = value.getNumber();
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The crop type.
     * </pre>
     *
     * <code>.google.apps.card.v1.ImageCropStyle.ImageCropType type = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearType() {
      bitField0_ = (bitField0_ & ~0x00000001);
      type_ = 0;
      onChanged();
      return this;
    }

    private double aspectRatio_;

    /**
     *
     *
     * <pre>
     * The aspect ratio to use if the crop type is `RECTANGLE_CUSTOM`.
     *
     * For example, here's how to apply a 16:9 aspect ratio:
     *
     * ```
     * cropStyle {
     *  "type": "RECTANGLE_CUSTOM",
     *  "aspectRatio": 16/9
     * }
     * ```
     * </pre>
     *
     * <code>double aspect_ratio = 2;</code>
     *
     * @return The aspectRatio.
     */
    @java.lang.Override
    public double getAspectRatio() {
      return aspectRatio_;
    }

    /**
     *
     *
     * <pre>
     * The aspect ratio to use if the crop type is `RECTANGLE_CUSTOM`.
     *
     * For example, here's how to apply a 16:9 aspect ratio:
     *
     * ```
     * cropStyle {
     *  "type": "RECTANGLE_CUSTOM",
     *  "aspectRatio": 16/9
     * }
     * ```
     * </pre>
     *
     * <code>double aspect_ratio = 2;</code>
     *
     * @param value The aspectRatio to set.
     * @return This builder for chaining.
     */
    public Builder setAspectRatio(double value) {

      aspectRatio_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The aspect ratio to use if the crop type is `RECTANGLE_CUSTOM`.
     *
     * For example, here's how to apply a 16:9 aspect ratio:
     *
     * ```
     * cropStyle {
     *  "type": "RECTANGLE_CUSTOM",
     *  "aspectRatio": 16/9
     * }
     * ```
     * </pre>
     *
     * <code>double aspect_ratio = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearAspectRatio() {
      bitField0_ = (bitField0_ & ~0x00000002);
      aspectRatio_ = 0D;
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

    // @@protoc_insertion_point(builder_scope:google.apps.card.v1.ImageCropStyle)
  }

  // @@protoc_insertion_point(class_scope:google.apps.card.v1.ImageCropStyle)
  private static final com.google.apps.card.v1.ImageCropStyle DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.apps.card.v1.ImageCropStyle();
  }

  public static com.google.apps.card.v1.ImageCropStyle getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ImageCropStyle> PARSER =
      new com.google.protobuf.AbstractParser<ImageCropStyle>() {
        @java.lang.Override
        public ImageCropStyle parsePartialFrom(
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

  public static com.google.protobuf.Parser<ImageCropStyle> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ImageCropStyle> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.apps.card.v1.ImageCropStyle getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
