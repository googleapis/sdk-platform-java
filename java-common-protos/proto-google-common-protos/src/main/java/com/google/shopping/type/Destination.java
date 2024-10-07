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
// source: google/shopping/type/types.proto

// Protobuf Java Version: 3.25.5
package com.google.shopping.type;

/**
 *
 *
 * <pre>
 * Destinations available for a product.
 *
 * Destinations are used in Merchant Center to allow you to control where the
 * products from your data feed should be displayed.
 * </pre>
 *
 * Protobuf type {@code google.shopping.type.Destination}
 */
public final class Destination extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.shopping.type.Destination)
    DestinationOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use Destination.newBuilder() to construct.
  private Destination(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private Destination() {}

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new Destination();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.shopping.type.TypesProto
        .internal_static_google_shopping_type_Destination_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.shopping.type.TypesProto
        .internal_static_google_shopping_type_Destination_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.shopping.type.Destination.class,
            com.google.shopping.type.Destination.Builder.class);
  }

  /**
   *
   *
   * <pre>
   * Destination values.
   * </pre>
   *
   * Protobuf enum {@code google.shopping.type.Destination.DestinationEnum}
   */
  public enum DestinationEnum implements com.google.protobuf.ProtocolMessageEnum {
    /**
     *
     *
     * <pre>
     * Not specified.
     * </pre>
     *
     * <code>DESTINATION_ENUM_UNSPECIFIED = 0;</code>
     */
    DESTINATION_ENUM_UNSPECIFIED(0),
    /**
     *
     *
     * <pre>
     * [Shopping ads](https://support.google.com/google-ads/answer/2454022).
     * </pre>
     *
     * <code>SHOPPING_ADS = 1;</code>
     */
    SHOPPING_ADS(1),
    /**
     *
     *
     * <pre>
     * [Display ads](https://support.google.com/merchants/answer/6069387).
     * </pre>
     *
     * <code>DISPLAY_ADS = 2;</code>
     */
    DISPLAY_ADS(2),
    /**
     *
     *
     * <pre>
     * [Local inventory
     * ads](https://support.google.com/merchants/answer/3057972).
     * </pre>
     *
     * <code>LOCAL_INVENTORY_ADS = 3;</code>
     */
    LOCAL_INVENTORY_ADS(3),
    /**
     *
     *
     * <pre>
     * [Free listings](https://support.google.com/merchants/answer/9199328).
     * </pre>
     *
     * <code>FREE_LISTINGS = 4;</code>
     */
    FREE_LISTINGS(4),
    /**
     *
     *
     * <pre>
     * [Free local product
     * listings](https://support.google.com/merchants/answer/9825611).
     * </pre>
     *
     * <code>FREE_LOCAL_LISTINGS = 5;</code>
     */
    FREE_LOCAL_LISTINGS(5),
    /**
     *
     *
     * <pre>
     * [YouTube Shopping](https://support.google.com/merchants/answer/12362804).
     * </pre>
     *
     * <code>YOUTUBE_SHOPPING = 6;</code>
     */
    YOUTUBE_SHOPPING(6),
    UNRECOGNIZED(-1),
    ;

    /**
     *
     *
     * <pre>
     * Not specified.
     * </pre>
     *
     * <code>DESTINATION_ENUM_UNSPECIFIED = 0;</code>
     */
    public static final int DESTINATION_ENUM_UNSPECIFIED_VALUE = 0;
    /**
     *
     *
     * <pre>
     * [Shopping ads](https://support.google.com/google-ads/answer/2454022).
     * </pre>
     *
     * <code>SHOPPING_ADS = 1;</code>
     */
    public static final int SHOPPING_ADS_VALUE = 1;
    /**
     *
     *
     * <pre>
     * [Display ads](https://support.google.com/merchants/answer/6069387).
     * </pre>
     *
     * <code>DISPLAY_ADS = 2;</code>
     */
    public static final int DISPLAY_ADS_VALUE = 2;
    /**
     *
     *
     * <pre>
     * [Local inventory
     * ads](https://support.google.com/merchants/answer/3057972).
     * </pre>
     *
     * <code>LOCAL_INVENTORY_ADS = 3;</code>
     */
    public static final int LOCAL_INVENTORY_ADS_VALUE = 3;
    /**
     *
     *
     * <pre>
     * [Free listings](https://support.google.com/merchants/answer/9199328).
     * </pre>
     *
     * <code>FREE_LISTINGS = 4;</code>
     */
    public static final int FREE_LISTINGS_VALUE = 4;
    /**
     *
     *
     * <pre>
     * [Free local product
     * listings](https://support.google.com/merchants/answer/9825611).
     * </pre>
     *
     * <code>FREE_LOCAL_LISTINGS = 5;</code>
     */
    public static final int FREE_LOCAL_LISTINGS_VALUE = 5;
    /**
     *
     *
     * <pre>
     * [YouTube Shopping](https://support.google.com/merchants/answer/12362804).
     * </pre>
     *
     * <code>YOUTUBE_SHOPPING = 6;</code>
     */
    public static final int YOUTUBE_SHOPPING_VALUE = 6;

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
    public static DestinationEnum valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static DestinationEnum forNumber(int value) {
      switch (value) {
        case 0:
          return DESTINATION_ENUM_UNSPECIFIED;
        case 1:
          return SHOPPING_ADS;
        case 2:
          return DISPLAY_ADS;
        case 3:
          return LOCAL_INVENTORY_ADS;
        case 4:
          return FREE_LISTINGS;
        case 5:
          return FREE_LOCAL_LISTINGS;
        case 6:
          return YOUTUBE_SHOPPING;
        default:
          return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<DestinationEnum> internalGetValueMap() {
      return internalValueMap;
    }

    private static final com.google.protobuf.Internal.EnumLiteMap<DestinationEnum>
        internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<DestinationEnum>() {
              public DestinationEnum findValueByNumber(int number) {
                return DestinationEnum.forNumber(number);
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
      return com.google.shopping.type.Destination.getDescriptor().getEnumTypes().get(0);
    }

    private static final DestinationEnum[] VALUES = values();

    public static DestinationEnum valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException("EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private DestinationEnum(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:google.shopping.type.Destination.DestinationEnum)
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
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof com.google.shopping.type.Destination)) {
      return super.equals(obj);
    }
    com.google.shopping.type.Destination other = (com.google.shopping.type.Destination) obj;

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
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.shopping.type.Destination parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.shopping.type.Destination parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.shopping.type.Destination parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.shopping.type.Destination parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.shopping.type.Destination parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.shopping.type.Destination parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.shopping.type.Destination parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.shopping.type.Destination parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.shopping.type.Destination parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.shopping.type.Destination parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.shopping.type.Destination parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.shopping.type.Destination parseFrom(
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

  public static Builder newBuilder(com.google.shopping.type.Destination prototype) {
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
   * Destinations available for a product.
   *
   * Destinations are used in Merchant Center to allow you to control where the
   * products from your data feed should be displayed.
   * </pre>
   *
   * Protobuf type {@code google.shopping.type.Destination}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.shopping.type.Destination)
      com.google.shopping.type.DestinationOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.shopping.type.TypesProto
          .internal_static_google_shopping_type_Destination_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.shopping.type.TypesProto
          .internal_static_google_shopping_type_Destination_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.shopping.type.Destination.class,
              com.google.shopping.type.Destination.Builder.class);
    }

    // Construct using com.google.shopping.type.Destination.newBuilder()
    private Builder() {}

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.shopping.type.TypesProto
          .internal_static_google_shopping_type_Destination_descriptor;
    }

    @java.lang.Override
    public com.google.shopping.type.Destination getDefaultInstanceForType() {
      return com.google.shopping.type.Destination.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.shopping.type.Destination build() {
      com.google.shopping.type.Destination result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.shopping.type.Destination buildPartial() {
      com.google.shopping.type.Destination result = new com.google.shopping.type.Destination(this);
      onBuilt();
      return result;
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
      if (other instanceof com.google.shopping.type.Destination) {
        return mergeFrom((com.google.shopping.type.Destination) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.shopping.type.Destination other) {
      if (other == com.google.shopping.type.Destination.getDefaultInstance()) return this;
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

    @java.lang.Override
    public final Builder setUnknownFields(final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }

    // @@protoc_insertion_point(builder_scope:google.shopping.type.Destination)
  }

  // @@protoc_insertion_point(class_scope:google.shopping.type.Destination)
  private static final com.google.shopping.type.Destination DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.shopping.type.Destination();
  }

  public static com.google.shopping.type.Destination getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Destination> PARSER =
      new com.google.protobuf.AbstractParser<Destination>() {
        @java.lang.Override
        public Destination parsePartialFrom(
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

  public static com.google.protobuf.Parser<Destination> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Destination> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.shopping.type.Destination getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
