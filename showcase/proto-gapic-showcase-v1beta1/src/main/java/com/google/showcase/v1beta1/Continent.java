// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/compliance.proto

// Protobuf Java Version: 3.25.0
package com.google.showcase.v1beta1;

/**
 * Protobuf enum {@code google.showcase.v1beta1.Continent}
 */
public enum Continent
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>CONTINENT_UNSPECIFIED = 0;</code>
   */
  CONTINENT_UNSPECIFIED(0),
  /**
   * <code>AFRICA = 1;</code>
   */
  AFRICA(1),
  /**
   * <code>AMERICA = 2;</code>
   */
  AMERICA(2),
  /**
   * <code>ANTARTICA = 3;</code>
   */
  ANTARTICA(3),
  /**
   * <code>AUSTRALIA = 4;</code>
   */
  AUSTRALIA(4),
  /**
   * <code>EUROPE = 5;</code>
   */
  EUROPE(5),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>CONTINENT_UNSPECIFIED = 0;</code>
   */
  public static final int CONTINENT_UNSPECIFIED_VALUE = 0;
  /**
   * <code>AFRICA = 1;</code>
   */
  public static final int AFRICA_VALUE = 1;
  /**
   * <code>AMERICA = 2;</code>
   */
  public static final int AMERICA_VALUE = 2;
  /**
   * <code>ANTARTICA = 3;</code>
   */
  public static final int ANTARTICA_VALUE = 3;
  /**
   * <code>AUSTRALIA = 4;</code>
   */
  public static final int AUSTRALIA_VALUE = 4;
  /**
   * <code>EUROPE = 5;</code>
   */
  public static final int EUROPE_VALUE = 5;


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
  public static Continent valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static Continent forNumber(int value) {
    switch (value) {
      case 0: return CONTINENT_UNSPECIFIED;
      case 1: return AFRICA;
      case 2: return AMERICA;
      case 3: return ANTARTICA;
      case 4: return AUSTRALIA;
      case 5: return EUROPE;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<Continent>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      Continent> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<Continent>() {
          public Continent findValueByNumber(int number) {
            return Continent.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return com.google.showcase.v1beta1.ComplianceOuterClass.getDescriptor().getEnumTypes().get(0);
  }

  private static final Continent[] VALUES = values();

  public static Continent valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private Continent(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:google.showcase.v1beta1.Continent)
}

