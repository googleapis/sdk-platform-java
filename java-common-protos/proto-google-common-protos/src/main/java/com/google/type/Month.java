/*
 * Copyright 2023 Google LLC
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
// source: google/type/month.proto

// Protobuf Java Version: 3.25.2
package com.google.type;

/**
 *
 *
 * <pre>
 * Represents a month in the Gregorian calendar.
 * </pre>
 *
 * Protobuf enum {@code google.type.Month}
 */
public enum Month implements com.google.protobuf.ProtocolMessageEnum {
  /**
   *
   *
   * <pre>
   * The unspecified month.
   * </pre>
   *
   * <code>MONTH_UNSPECIFIED = 0;</code>
   */
  MONTH_UNSPECIFIED(0),
  /**
   *
   *
   * <pre>
   * The month of January.
   * </pre>
   *
   * <code>JANUARY = 1;</code>
   */
  JANUARY(1),
  /**
   *
   *
   * <pre>
   * The month of February.
   * </pre>
   *
   * <code>FEBRUARY = 2;</code>
   */
  FEBRUARY(2),
  /**
   *
   *
   * <pre>
   * The month of March.
   * </pre>
   *
   * <code>MARCH = 3;</code>
   */
  MARCH(3),
  /**
   *
   *
   * <pre>
   * The month of April.
   * </pre>
   *
   * <code>APRIL = 4;</code>
   */
  APRIL(4),
  /**
   *
   *
   * <pre>
   * The month of May.
   * </pre>
   *
   * <code>MAY = 5;</code>
   */
  MAY(5),
  /**
   *
   *
   * <pre>
   * The month of June.
   * </pre>
   *
   * <code>JUNE = 6;</code>
   */
  JUNE(6),
  /**
   *
   *
   * <pre>
   * The month of July.
   * </pre>
   *
   * <code>JULY = 7;</code>
   */
  JULY(7),
  /**
   *
   *
   * <pre>
   * The month of August.
   * </pre>
   *
   * <code>AUGUST = 8;</code>
   */
  AUGUST(8),
  /**
   *
   *
   * <pre>
   * The month of September.
   * </pre>
   *
   * <code>SEPTEMBER = 9;</code>
   */
  SEPTEMBER(9),
  /**
   *
   *
   * <pre>
   * The month of October.
   * </pre>
   *
   * <code>OCTOBER = 10;</code>
   */
  OCTOBER(10),
  /**
   *
   *
   * <pre>
   * The month of November.
   * </pre>
   *
   * <code>NOVEMBER = 11;</code>
   */
  NOVEMBER(11),
  /**
   *
   *
   * <pre>
   * The month of December.
   * </pre>
   *
   * <code>DECEMBER = 12;</code>
   */
  DECEMBER(12),
  UNRECOGNIZED(-1),
  ;

  /**
   *
   *
   * <pre>
   * The unspecified month.
   * </pre>
   *
   * <code>MONTH_UNSPECIFIED = 0;</code>
   */
  public static final int MONTH_UNSPECIFIED_VALUE = 0;
  /**
   *
   *
   * <pre>
   * The month of January.
   * </pre>
   *
   * <code>JANUARY = 1;</code>
   */
  public static final int JANUARY_VALUE = 1;
  /**
   *
   *
   * <pre>
   * The month of February.
   * </pre>
   *
   * <code>FEBRUARY = 2;</code>
   */
  public static final int FEBRUARY_VALUE = 2;
  /**
   *
   *
   * <pre>
   * The month of March.
   * </pre>
   *
   * <code>MARCH = 3;</code>
   */
  public static final int MARCH_VALUE = 3;
  /**
   *
   *
   * <pre>
   * The month of April.
   * </pre>
   *
   * <code>APRIL = 4;</code>
   */
  public static final int APRIL_VALUE = 4;
  /**
   *
   *
   * <pre>
   * The month of May.
   * </pre>
   *
   * <code>MAY = 5;</code>
   */
  public static final int MAY_VALUE = 5;
  /**
   *
   *
   * <pre>
   * The month of June.
   * </pre>
   *
   * <code>JUNE = 6;</code>
   */
  public static final int JUNE_VALUE = 6;
  /**
   *
   *
   * <pre>
   * The month of July.
   * </pre>
   *
   * <code>JULY = 7;</code>
   */
  public static final int JULY_VALUE = 7;
  /**
   *
   *
   * <pre>
   * The month of August.
   * </pre>
   *
   * <code>AUGUST = 8;</code>
   */
  public static final int AUGUST_VALUE = 8;
  /**
   *
   *
   * <pre>
   * The month of September.
   * </pre>
   *
   * <code>SEPTEMBER = 9;</code>
   */
  public static final int SEPTEMBER_VALUE = 9;
  /**
   *
   *
   * <pre>
   * The month of October.
   * </pre>
   *
   * <code>OCTOBER = 10;</code>
   */
  public static final int OCTOBER_VALUE = 10;
  /**
   *
   *
   * <pre>
   * The month of November.
   * </pre>
   *
   * <code>NOVEMBER = 11;</code>
   */
  public static final int NOVEMBER_VALUE = 11;
  /**
   *
   *
   * <pre>
   * The month of December.
   * </pre>
   *
   * <code>DECEMBER = 12;</code>
   */
  public static final int DECEMBER_VALUE = 12;

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
  public static Month valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static Month forNumber(int value) {
    switch (value) {
      case 0:
        return MONTH_UNSPECIFIED;
      case 1:
        return JANUARY;
      case 2:
        return FEBRUARY;
      case 3:
        return MARCH;
      case 4:
        return APRIL;
      case 5:
        return MAY;
      case 6:
        return JUNE;
      case 7:
        return JULY;
      case 8:
        return AUGUST;
      case 9:
        return SEPTEMBER;
      case 10:
        return OCTOBER;
      case 11:
        return NOVEMBER;
      case 12:
        return DECEMBER;
      default:
        return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<Month> internalGetValueMap() {
    return internalValueMap;
  }

  private static final com.google.protobuf.Internal.EnumLiteMap<Month> internalValueMap =
      new com.google.protobuf.Internal.EnumLiteMap<Month>() {
        public Month findValueByNumber(int number) {
          return Month.forNumber(number);
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
    return com.google.type.MonthProto.getDescriptor().getEnumTypes().get(0);
  }

  private static final Month[] VALUES = values();

  public static Month valueOf(com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException("EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private Month(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:google.type.Month)
}
