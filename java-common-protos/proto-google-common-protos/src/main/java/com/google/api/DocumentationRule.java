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
// source: google/api/documentation.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

/**
 *
 *
 * <pre>
 * A documentation rule provides information about individual API elements.
 * </pre>
 *
 * Protobuf type {@code google.api.DocumentationRule}
 */
public final class DocumentationRule
    extends com.google.protobuf.GeneratedMessageLite<DocumentationRule, DocumentationRule.Builder>
    implements
    // @@protoc_insertion_point(message_implements:google.api.DocumentationRule)
    DocumentationRuleOrBuilder {
  private DocumentationRule() {
    selector_ = "";
    description_ = "";
    deprecationDescription_ = "";
  }

  public static final int SELECTOR_FIELD_NUMBER = 1;
  private java.lang.String selector_;
  /**
   *
   *
   * <pre>
   * The selector is a comma-separated list of patterns for any element such as
   * a method, a field, an enum value. Each pattern is a qualified name of the
   * element which may end in "*", indicating a wildcard. Wildcards are only
   * allowed at the end and for a whole component of the qualified name,
   * i.e. "foo.*" is ok, but not "foo.b*" or "foo.*.bar". A wildcard will match
   * one or more components. To specify a default for all applicable elements,
   * the whole pattern "*" is used.
   * </pre>
   *
   * <code>string selector = 1;</code>
   *
   * @return The selector.
   */
  @java.lang.Override
  public java.lang.String getSelector() {
    return selector_;
  }
  /**
   *
   *
   * <pre>
   * The selector is a comma-separated list of patterns for any element such as
   * a method, a field, an enum value. Each pattern is a qualified name of the
   * element which may end in "*", indicating a wildcard. Wildcards are only
   * allowed at the end and for a whole component of the qualified name,
   * i.e. "foo.*" is ok, but not "foo.b*" or "foo.*.bar". A wildcard will match
   * one or more components. To specify a default for all applicable elements,
   * the whole pattern "*" is used.
   * </pre>
   *
   * <code>string selector = 1;</code>
   *
   * @return The bytes for selector.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getSelectorBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(selector_);
  }
  /**
   *
   *
   * <pre>
   * The selector is a comma-separated list of patterns for any element such as
   * a method, a field, an enum value. Each pattern is a qualified name of the
   * element which may end in "*", indicating a wildcard. Wildcards are only
   * allowed at the end and for a whole component of the qualified name,
   * i.e. "foo.*" is ok, but not "foo.b*" or "foo.*.bar". A wildcard will match
   * one or more components. To specify a default for all applicable elements,
   * the whole pattern "*" is used.
   * </pre>
   *
   * <code>string selector = 1;</code>
   *
   * @param value The selector to set.
   */
  private void setSelector(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();

    selector_ = value;
  }
  /**
   *
   *
   * <pre>
   * The selector is a comma-separated list of patterns for any element such as
   * a method, a field, an enum value. Each pattern is a qualified name of the
   * element which may end in "*", indicating a wildcard. Wildcards are only
   * allowed at the end and for a whole component of the qualified name,
   * i.e. "foo.*" is ok, but not "foo.b*" or "foo.*.bar". A wildcard will match
   * one or more components. To specify a default for all applicable elements,
   * the whole pattern "*" is used.
   * </pre>
   *
   * <code>string selector = 1;</code>
   */
  private void clearSelector() {

    selector_ = getDefaultInstance().getSelector();
  }
  /**
   *
   *
   * <pre>
   * The selector is a comma-separated list of patterns for any element such as
   * a method, a field, an enum value. Each pattern is a qualified name of the
   * element which may end in "*", indicating a wildcard. Wildcards are only
   * allowed at the end and for a whole component of the qualified name,
   * i.e. "foo.*" is ok, but not "foo.b*" or "foo.*.bar". A wildcard will match
   * one or more components. To specify a default for all applicable elements,
   * the whole pattern "*" is used.
   * </pre>
   *
   * <code>string selector = 1;</code>
   *
   * @param value The bytes for selector to set.
   */
  private void setSelectorBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    selector_ = value.toStringUtf8();
  }

  public static final int DESCRIPTION_FIELD_NUMBER = 2;
  private java.lang.String description_;
  /**
   *
   *
   * <pre>
   * Description of the selected proto element (e.g. a message, a method, a
   * 'service' definition, or a field). Defaults to leading &amp; trailing comments
   * taken from the proto source definition of the proto element.
   * </pre>
   *
   * <code>string description = 2;</code>
   *
   * @return The description.
   */
  @java.lang.Override
  public java.lang.String getDescription() {
    return description_;
  }
  /**
   *
   *
   * <pre>
   * Description of the selected proto element (e.g. a message, a method, a
   * 'service' definition, or a field). Defaults to leading &amp; trailing comments
   * taken from the proto source definition of the proto element.
   * </pre>
   *
   * <code>string description = 2;</code>
   *
   * @return The bytes for description.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getDescriptionBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(description_);
  }
  /**
   *
   *
   * <pre>
   * Description of the selected proto element (e.g. a message, a method, a
   * 'service' definition, or a field). Defaults to leading &amp; trailing comments
   * taken from the proto source definition of the proto element.
   * </pre>
   *
   * <code>string description = 2;</code>
   *
   * @param value The description to set.
   */
  private void setDescription(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();

    description_ = value;
  }
  /**
   *
   *
   * <pre>
   * Description of the selected proto element (e.g. a message, a method, a
   * 'service' definition, or a field). Defaults to leading &amp; trailing comments
   * taken from the proto source definition of the proto element.
   * </pre>
   *
   * <code>string description = 2;</code>
   */
  private void clearDescription() {

    description_ = getDefaultInstance().getDescription();
  }
  /**
   *
   *
   * <pre>
   * Description of the selected proto element (e.g. a message, a method, a
   * 'service' definition, or a field). Defaults to leading &amp; trailing comments
   * taken from the proto source definition of the proto element.
   * </pre>
   *
   * <code>string description = 2;</code>
   *
   * @param value The bytes for description to set.
   */
  private void setDescriptionBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    description_ = value.toStringUtf8();
  }

  public static final int DEPRECATION_DESCRIPTION_FIELD_NUMBER = 3;
  private java.lang.String deprecationDescription_;
  /**
   *
   *
   * <pre>
   * Deprecation description of the selected element(s). It can be provided if
   * an element is marked as `deprecated`.
   * </pre>
   *
   * <code>string deprecation_description = 3;</code>
   *
   * @return The deprecationDescription.
   */
  @java.lang.Override
  public java.lang.String getDeprecationDescription() {
    return deprecationDescription_;
  }
  /**
   *
   *
   * <pre>
   * Deprecation description of the selected element(s). It can be provided if
   * an element is marked as `deprecated`.
   * </pre>
   *
   * <code>string deprecation_description = 3;</code>
   *
   * @return The bytes for deprecationDescription.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getDeprecationDescriptionBytes() {
    return com.google.protobuf.ByteString.copyFromUtf8(deprecationDescription_);
  }
  /**
   *
   *
   * <pre>
   * Deprecation description of the selected element(s). It can be provided if
   * an element is marked as `deprecated`.
   * </pre>
   *
   * <code>string deprecation_description = 3;</code>
   *
   * @param value The deprecationDescription to set.
   */
  private void setDeprecationDescription(java.lang.String value) {
    java.lang.Class<?> valueClass = value.getClass();

    deprecationDescription_ = value;
  }
  /**
   *
   *
   * <pre>
   * Deprecation description of the selected element(s). It can be provided if
   * an element is marked as `deprecated`.
   * </pre>
   *
   * <code>string deprecation_description = 3;</code>
   */
  private void clearDeprecationDescription() {

    deprecationDescription_ = getDefaultInstance().getDeprecationDescription();
  }
  /**
   *
   *
   * <pre>
   * Deprecation description of the selected element(s). It can be provided if
   * an element is marked as `deprecated`.
   * </pre>
   *
   * <code>string deprecation_description = 3;</code>
   *
   * @param value The bytes for deprecationDescription to set.
   */
  private void setDeprecationDescriptionBytes(com.google.protobuf.ByteString value) {
    checkByteStringIsUtf8(value);
    deprecationDescription_ = value.toStringUtf8();
  }

  public static com.google.api.DocumentationRule parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.DocumentationRule parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.DocumentationRule parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.DocumentationRule parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.DocumentationRule parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
  }

  public static com.google.api.DocumentationRule parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, data, extensionRegistry);
  }

  public static com.google.api.DocumentationRule parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.DocumentationRule parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.DocumentationRule parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.DocumentationRule parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static com.google.api.DocumentationRule parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
  }

  public static com.google.api.DocumentationRule parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageLite.parseFrom(
        DEFAULT_INSTANCE, input, extensionRegistry);
  }

  public static Builder newBuilder() {
    return (Builder) DEFAULT_INSTANCE.createBuilder();
  }

  public static Builder newBuilder(com.google.api.DocumentationRule prototype) {
    return DEFAULT_INSTANCE.createBuilder(prototype);
  }

  /**
   *
   *
   * <pre>
   * A documentation rule provides information about individual API elements.
   * </pre>
   *
   * Protobuf type {@code google.api.DocumentationRule}
   */
  public static final class Builder
      extends com.google.protobuf.GeneratedMessageLite.Builder<
          com.google.api.DocumentationRule, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.DocumentationRule)
      com.google.api.DocumentationRuleOrBuilder {
    // Construct using com.google.api.DocumentationRule.newBuilder()
    private Builder() {
      super(DEFAULT_INSTANCE);
    }

    /**
     *
     *
     * <pre>
     * The selector is a comma-separated list of patterns for any element such as
     * a method, a field, an enum value. Each pattern is a qualified name of the
     * element which may end in "*", indicating a wildcard. Wildcards are only
     * allowed at the end and for a whole component of the qualified name,
     * i.e. "foo.*" is ok, but not "foo.b*" or "foo.*.bar". A wildcard will match
     * one or more components. To specify a default for all applicable elements,
     * the whole pattern "*" is used.
     * </pre>
     *
     * <code>string selector = 1;</code>
     *
     * @return The selector.
     */
    @java.lang.Override
    public java.lang.String getSelector() {
      return instance.getSelector();
    }
    /**
     *
     *
     * <pre>
     * The selector is a comma-separated list of patterns for any element such as
     * a method, a field, an enum value. Each pattern is a qualified name of the
     * element which may end in "*", indicating a wildcard. Wildcards are only
     * allowed at the end and for a whole component of the qualified name,
     * i.e. "foo.*" is ok, but not "foo.b*" or "foo.*.bar". A wildcard will match
     * one or more components. To specify a default for all applicable elements,
     * the whole pattern "*" is used.
     * </pre>
     *
     * <code>string selector = 1;</code>
     *
     * @return The bytes for selector.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getSelectorBytes() {
      return instance.getSelectorBytes();
    }
    /**
     *
     *
     * <pre>
     * The selector is a comma-separated list of patterns for any element such as
     * a method, a field, an enum value. Each pattern is a qualified name of the
     * element which may end in "*", indicating a wildcard. Wildcards are only
     * allowed at the end and for a whole component of the qualified name,
     * i.e. "foo.*" is ok, but not "foo.b*" or "foo.*.bar". A wildcard will match
     * one or more components. To specify a default for all applicable elements,
     * the whole pattern "*" is used.
     * </pre>
     *
     * <code>string selector = 1;</code>
     *
     * @param value The selector to set.
     * @return This builder for chaining.
     */
    public Builder setSelector(java.lang.String value) {
      copyOnWrite();
      instance.setSelector(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * The selector is a comma-separated list of patterns for any element such as
     * a method, a field, an enum value. Each pattern is a qualified name of the
     * element which may end in "*", indicating a wildcard. Wildcards are only
     * allowed at the end and for a whole component of the qualified name,
     * i.e. "foo.*" is ok, but not "foo.b*" or "foo.*.bar". A wildcard will match
     * one or more components. To specify a default for all applicable elements,
     * the whole pattern "*" is used.
     * </pre>
     *
     * <code>string selector = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearSelector() {
      copyOnWrite();
      instance.clearSelector();
      return this;
    }
    /**
     *
     *
     * <pre>
     * The selector is a comma-separated list of patterns for any element such as
     * a method, a field, an enum value. Each pattern is a qualified name of the
     * element which may end in "*", indicating a wildcard. Wildcards are only
     * allowed at the end and for a whole component of the qualified name,
     * i.e. "foo.*" is ok, but not "foo.b*" or "foo.*.bar". A wildcard will match
     * one or more components. To specify a default for all applicable elements,
     * the whole pattern "*" is used.
     * </pre>
     *
     * <code>string selector = 1;</code>
     *
     * @param value The bytes for selector to set.
     * @return This builder for chaining.
     */
    public Builder setSelectorBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setSelectorBytes(value);
      return this;
    }

    /**
     *
     *
     * <pre>
     * Description of the selected proto element (e.g. a message, a method, a
     * 'service' definition, or a field). Defaults to leading &amp; trailing comments
     * taken from the proto source definition of the proto element.
     * </pre>
     *
     * <code>string description = 2;</code>
     *
     * @return The description.
     */
    @java.lang.Override
    public java.lang.String getDescription() {
      return instance.getDescription();
    }
    /**
     *
     *
     * <pre>
     * Description of the selected proto element (e.g. a message, a method, a
     * 'service' definition, or a field). Defaults to leading &amp; trailing comments
     * taken from the proto source definition of the proto element.
     * </pre>
     *
     * <code>string description = 2;</code>
     *
     * @return The bytes for description.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getDescriptionBytes() {
      return instance.getDescriptionBytes();
    }
    /**
     *
     *
     * <pre>
     * Description of the selected proto element (e.g. a message, a method, a
     * 'service' definition, or a field). Defaults to leading &amp; trailing comments
     * taken from the proto source definition of the proto element.
     * </pre>
     *
     * <code>string description = 2;</code>
     *
     * @param value The description to set.
     * @return This builder for chaining.
     */
    public Builder setDescription(java.lang.String value) {
      copyOnWrite();
      instance.setDescription(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Description of the selected proto element (e.g. a message, a method, a
     * 'service' definition, or a field). Defaults to leading &amp; trailing comments
     * taken from the proto source definition of the proto element.
     * </pre>
     *
     * <code>string description = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDescription() {
      copyOnWrite();
      instance.clearDescription();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Description of the selected proto element (e.g. a message, a method, a
     * 'service' definition, or a field). Defaults to leading &amp; trailing comments
     * taken from the proto source definition of the proto element.
     * </pre>
     *
     * <code>string description = 2;</code>
     *
     * @param value The bytes for description to set.
     * @return This builder for chaining.
     */
    public Builder setDescriptionBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setDescriptionBytes(value);
      return this;
    }

    /**
     *
     *
     * <pre>
     * Deprecation description of the selected element(s). It can be provided if
     * an element is marked as `deprecated`.
     * </pre>
     *
     * <code>string deprecation_description = 3;</code>
     *
     * @return The deprecationDescription.
     */
    @java.lang.Override
    public java.lang.String getDeprecationDescription() {
      return instance.getDeprecationDescription();
    }
    /**
     *
     *
     * <pre>
     * Deprecation description of the selected element(s). It can be provided if
     * an element is marked as `deprecated`.
     * </pre>
     *
     * <code>string deprecation_description = 3;</code>
     *
     * @return The bytes for deprecationDescription.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getDeprecationDescriptionBytes() {
      return instance.getDeprecationDescriptionBytes();
    }
    /**
     *
     *
     * <pre>
     * Deprecation description of the selected element(s). It can be provided if
     * an element is marked as `deprecated`.
     * </pre>
     *
     * <code>string deprecation_description = 3;</code>
     *
     * @param value The deprecationDescription to set.
     * @return This builder for chaining.
     */
    public Builder setDeprecationDescription(java.lang.String value) {
      copyOnWrite();
      instance.setDeprecationDescription(value);
      return this;
    }
    /**
     *
     *
     * <pre>
     * Deprecation description of the selected element(s). It can be provided if
     * an element is marked as `deprecated`.
     * </pre>
     *
     * <code>string deprecation_description = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearDeprecationDescription() {
      copyOnWrite();
      instance.clearDeprecationDescription();
      return this;
    }
    /**
     *
     *
     * <pre>
     * Deprecation description of the selected element(s). It can be provided if
     * an element is marked as `deprecated`.
     * </pre>
     *
     * <code>string deprecation_description = 3;</code>
     *
     * @param value The bytes for deprecationDescription to set.
     * @return This builder for chaining.
     */
    public Builder setDeprecationDescriptionBytes(com.google.protobuf.ByteString value) {
      copyOnWrite();
      instance.setDeprecationDescriptionBytes(value);
      return this;
    }

    // @@protoc_insertion_point(builder_scope:google.api.DocumentationRule)
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
          return new com.google.api.DocumentationRule();
        }
      case NEW_BUILDER:
        {
          return new Builder();
        }
      case BUILD_MESSAGE_INFO:
        {
          java.lang.Object[] objects =
              new java.lang.Object[] {
                "selector_", "description_", "deprecationDescription_",
              };
          java.lang.String info =
              "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0208\u0002\u0208"
                  + "\u0003\u0208";
          return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
      case GET_DEFAULT_INSTANCE:
        {
          return DEFAULT_INSTANCE;
        }
      case GET_PARSER:
        {
          com.google.protobuf.Parser<com.google.api.DocumentationRule> parser = PARSER;
          if (parser == null) {
            synchronized (com.google.api.DocumentationRule.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<com.google.api.DocumentationRule>(
                        DEFAULT_INSTANCE);
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

  // @@protoc_insertion_point(class_scope:google.api.DocumentationRule)
  private static final com.google.api.DocumentationRule DEFAULT_INSTANCE;

  static {
    DocumentationRule defaultInstance = new DocumentationRule();
    // New instances are implicitly immutable so no need to make
    // immutable.
    DEFAULT_INSTANCE = defaultInstance;
    com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        DocumentationRule.class, defaultInstance);
  }

  public static com.google.api.DocumentationRule getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static volatile com.google.protobuf.Parser<DocumentationRule> PARSER;

  public static com.google.protobuf.Parser<DocumentationRule> parser() {
    return DEFAULT_INSTANCE.getParserForType();
  }
}
