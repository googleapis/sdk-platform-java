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
// source: google/type/interval.proto

// Protobuf Java Version: 3.25.5
package com.google.type;

/**
 *
 *
 * <pre>
 * Represents a time interval, encoded as a Timestamp start (inclusive) and a
 * Timestamp end (exclusive).
 *
 * The start must be less than or equal to the end.
 * When the start equals the end, the interval is empty (matches no time).
 * When both start and end are unspecified, the interval matches any time.
 * </pre>
 *
 * Protobuf type {@code google.type.Interval}
 */
public final class Interval
    extends com.google.protobuf.GeneratedMessageLite<Interval, Interval.Builder>
    implements
    // @@protoc_insertion_point(message_implements:google.type.Interval)
    IntervalOrBuilder {
  private Interval() {}

  private int bitField0_;
  public static final int START_TIME_FIELD_NUMBER = 1;
  private com.google.protobuf.Timestamp startTime_;
  /**
   *
   *
   * <pre>
   * Optional. Inclusive start of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be the same
   * or after the start.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp start_time = 1;</code>
   */
  @java.lang.Override
  public boolean hasStartTime() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   *
   *
   * <pre>
   * Optional. Inclusive start of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be the same
   * or after the start.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp start_time = 1;</code>
   */
  @java.lang.Override
  public com.google.protobuf.Timestamp getStartTime() {
    return startTime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : startTime_;
  }
  /**
   *
   *
   * <pre>
   * Optional. Inclusive start of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be the same
   * or after the start.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp start_time = 1;</code>
   */
  private void setStartTime(com.google.protobuf.Timestamp value) {
    value.getClass();
    startTime_ = value;
    bitField0_ |= 0x00000001;
  }
  /**
   *
   *
   * <pre>
   * Optional. Inclusive start of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be the same
   * or after the start.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp start_time = 1;</code>
   */
  @java.lang.SuppressWarnings({"ReferenceEquality"})
  private void mergeStartTime(com.google.protobuf.Timestamp value) {
    value.getClass();
    if (startTime_ != null && startTime_ != com.google.protobuf.Timestamp.getDefaultInstance()) {
      startTime_ =
          com.google.protobuf.Timestamp.newBuilder(startTime_).mergeFrom(value).buildPartial();
    } else {
      startTime_ = value;
    }
    bitField0_ |= 0x00000001;
  }
  /**
   *
   *
   * <pre>
   * Optional. Inclusive start of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be the same
   * or after the start.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp start_time = 1;</code>
   */
  private void clearStartTime() {
    startTime_ = null;
    bitField0_ = (bitField0_ & ~0x00000001);
  }

  public static final int END_TIME_FIELD_NUMBER = 2;
  private com.google.protobuf.Timestamp endTime_;
  /**
   *
   *
   * <pre>
   * Optional. Exclusive end of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be before the
   * end.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp end_time = 2;</code>
   */
  @java.lang.Override
  public boolean hasEndTime() {
    return ((bitField0_ & 0x00000002) != 0);
  }
  /**
   *
   *
   * <pre>
   * Optional. Exclusive end of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be before the
   * end.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp end_time = 2;</code>
   */
  @java.lang.Override
  public com.google.protobuf.Timestamp getEndTime() {
    return endTime_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : endTime_;
  }
  /**
   *
   *
   * <pre>
   * Optional. Exclusive end of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be before the
   * end.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp end_time = 2;</code>
   */
  private void setEndTime(com.google.protobuf.Timestamp value) {
    value.getClass();
    endTime_ = value;
    bitField0_ |= 0x00000002;
  }
  /**
   *
   *
   * <pre>
   * Optional. Exclusive end of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be before the
   * end.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp end_time = 2;</code>
   */
  @java.lang.SuppressWarnings({"ReferenceEquality"})
  private void mergeEndTime(com.google.protobuf.Timestamp value) {
    value.getClass();
    if (endTime_ != null && endTime_ != com.google.protobuf.Timestamp.getDefaultInstance()) {
      endTime_ = com.google.protobuf.Timestamp.newBuilder(endTime_).mergeFrom(value).buildPartial();
    } else {
      endTime_ = value;
    }
    bitField0_ |= 0x00000002;
  }
  /**
   *
   *
   * <pre>
   * Optional. Exclusive end of the interval.
   *
   * If specified, a Timestamp matching this interval will have to be before the
   * end.
   * </pre>
   *
   * <code>.google.protobuf.Timestamp end_time = 2;</code>
   */
  private void clearEndTime() {
    endTime_ = null;
    bitField0_ = (bitField0_ & ~0x00000002);
  }

  public static com.google.type.Interval parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.type.Interval parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.type.Interval parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.type.Interval parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.type.Interval parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.type.Interval parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.type.Interval parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.type.Interval parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.type.Interval parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.type.Interval parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.type.Interval parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.type.Interval parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }

  public static Builder newBuilder(com.google.type.Interval prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   *
   *
   * <pre>
   * Represents a time interval, encoded as a Timestamp start (inclusive) and a
   * Timestamp end (exclusive).
   *
   * The start must be less than or equal to the end.
   * When the start equals the end, the interval is empty (matches no time).
   * When both start and end are unspecified, the interval matches any time.
   * </pre>
   *
   * Protobuf type {@code google.type.Interval}
   */
  public static final class Builder
      extends com.google.protobuf.GeneratedMessageLite.Builder<com.google.type.Interval, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.type.Interval)
      com.google.type.IntervalOrBuilder {
    // Construct using com.google.type.Interval.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }

    /**
     *
     *
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     */
    @java.lang.Override
    public boolean hasStartTime() {
      return instance.hasStartTime();
    }
    /**
     *
     *
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     */
    @java.lang.Override
    public com.google.protobuf.Timestamp getStartTime() {
      return instance.getStartTime();
    }
    /**
     *
     *
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     */
    public Builder setStartTime(com.google.protobuf.Timestamp value) {
      copyOnWrite();
      instance.setStartTime(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     */
    public Builder setStartTime(com.google.protobuf.Timestamp.Builder builderForValue) {
      copyOnWrite();
      instance.setStartTime(builderForValue.build());
      return this;
    }
    /**
     *
     *
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     */
    public Builder mergeStartTime(com.google.protobuf.Timestamp value) {
      copyOnWrite();
      instance.mergeStartTime(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Optional. Inclusive start of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be the same
     * or after the start.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp start_time = 1;</code>
     */
    public Builder clearStartTime() {
      copyOnWrite();
      instance.clearStartTime();
      return this;
    }

    /**
     *
     *
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     */
    @java.lang.Override
    public boolean hasEndTime() {
      return instance.hasEndTime();
    }
    /**
     *
     *
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     */
    @java.lang.Override
    public com.google.protobuf.Timestamp getEndTime() {
      return instance.getEndTime();
    }
    /**
     *
     *
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     */
    public Builder setEndTime(com.google.protobuf.Timestamp value) {
      copyOnWrite();
      instance.setEndTime(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     */
    public Builder setEndTime(com.google.protobuf.Timestamp.Builder builderForValue) {
      copyOnWrite();
      instance.setEndTime(builderForValue.build());
      return this;
    }
    /**
     *
     *
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     */
    public Builder mergeEndTime(com.google.protobuf.Timestamp value) {
      copyOnWrite();
      instance.mergeEndTime(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Optional. Exclusive end of the interval.
     *
     * If specified, a Timestamp matching this interval will have to be before the
     * end.
     * </pre>
     *
     * <code>.google.protobuf.Timestamp end_time = 2;</code>
     */
    public Builder clearEndTime() {
      copyOnWrite();
      instance.clearEndTime();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.type.Interval)
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
          return new com.google.type.Interval();
        }
      case NEW_BUILDER:
        {
          return new Builder();
        }
      case BUILD_MESSAGE_INFO:
        {
          java.lang.Object[] objects =
              new java.lang.Object[] {
                "bitField0_", "startTime_", "endTime_",
              };
          java.lang.String info =
              "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u1009\u0000\u0002"
                  + "\u1009\u0001";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
      case GET_DEFAULT_INSTANCE:
        {
          return DEFAULT_INSTANCE;
        }
      case GET_PARSER:
        {
          com.google.protobuf.Parser<com.google.type.Interval> parser = PARSER;
          if (parser == null) {
            synchronized (com.google.type.Interval.class) {
              parser = PARSER;
              if (parser == null) {
                parser = new DefaultInstanceBasedParser<com.google.type.Interval>(DEFAULT_INSTANCE);
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

  // @@protoc_insertion_point(class_scope:google.type.Interval)
  private static final com.google.type.Interval DEFAULT_INSTANCE;

  static {
    Interval defaultInstance = new Interval();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        Interval.class, defaultInstance);
  }

  public static com.google.type.Interval getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<Interval> PARSER;

  public static com.google.protobuf.Parser<Interval> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
