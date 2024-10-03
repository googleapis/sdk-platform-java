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
// source: google/api/system_parameter.proto

// Protobuf Java Version: 3.25.5
package com.google.api;

/**
 *
 *
 * <pre>
 * Define a parameter's name and location. The parameter may be passed as either
 * an HTTP header or a URL query parameter, and if both are passed the behavior
 * is implementation-dependent.
 * </pre>
 *
 * Protobuf type {@code google.api.SystemParameter}
 */
public final class SystemParameter extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.api.SystemParameter)
    SystemParameterOrBuilder {
  private static final long serialVersionUID = 0L;

  // Use SystemParameter.newBuilder() to construct.
  private SystemParameter(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private SystemParameter() {
    name_ = "";
    httpHeader_ = "";
    urlQueryParameter_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new SystemParameter();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.api.SystemParameterProto
        .internal_static_google_api_SystemParameter_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.api.SystemParameterProto
        .internal_static_google_api_SystemParameter_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.api.SystemParameter.class, com.google.api.SystemParameter.Builder.class);
  }

  public static final int NAME_FIELD_NUMBER = 1;

  @SuppressWarnings("serial")
  private volatile java.lang.Object name_ = "";

  /**
   *
   *
   * <pre>
   * Define the name of the parameter, such as "api_key" . It is case sensitive.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The name.
   */
  @java.lang.Override
  public java.lang.String getName() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      name_ = s;
      return s;
    }
  }

  /**
   *
   *
   * <pre>
   * Define the name of the parameter, such as "api_key" . It is case sensitive.
   * </pre>
   *
   * <code>string name = 1;</code>
   *
   * @return The bytes for name.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getNameBytes() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      name_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int HTTP_HEADER_FIELD_NUMBER = 2;

  @SuppressWarnings("serial")
  private volatile java.lang.Object httpHeader_ = "";

  /**
   *
   *
   * <pre>
   * Define the HTTP header name to use for the parameter. It is case
   * insensitive.
   * </pre>
   *
   * <code>string http_header = 2;</code>
   *
   * @return The httpHeader.
   */
  @java.lang.Override
  public java.lang.String getHttpHeader() {
    java.lang.Object ref = httpHeader_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      httpHeader_ = s;
      return s;
    }
  }

  /**
   *
   *
   * <pre>
   * Define the HTTP header name to use for the parameter. It is case
   * insensitive.
   * </pre>
   *
   * <code>string http_header = 2;</code>
   *
   * @return The bytes for httpHeader.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getHttpHeaderBytes() {
    java.lang.Object ref = httpHeader_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      httpHeader_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int URL_QUERY_PARAMETER_FIELD_NUMBER = 3;

  @SuppressWarnings("serial")
  private volatile java.lang.Object urlQueryParameter_ = "";

  /**
   *
   *
   * <pre>
   * Define the URL query parameter name to use for the parameter. It is case
   * sensitive.
   * </pre>
   *
   * <code>string url_query_parameter = 3;</code>
   *
   * @return The urlQueryParameter.
   */
  @java.lang.Override
  public java.lang.String getUrlQueryParameter() {
    java.lang.Object ref = urlQueryParameter_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      urlQueryParameter_ = s;
      return s;
    }
  }

  /**
   *
   *
   * <pre>
   * Define the URL query parameter name to use for the parameter. It is case
   * sensitive.
   * </pre>
   *
   * <code>string url_query_parameter = 3;</code>
   *
   * @return The bytes for urlQueryParameter.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getUrlQueryParameterBytes() {
    java.lang.Object ref = urlQueryParameter_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      urlQueryParameter_ = b;
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
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(name_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(httpHeader_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, httpHeader_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(urlQueryParameter_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, urlQueryParameter_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(name_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(httpHeader_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, httpHeader_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(urlQueryParameter_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, urlQueryParameter_);
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
    if (!(obj instanceof com.google.api.SystemParameter)) {
      return super.equals(obj);
    }
    com.google.api.SystemParameter other = (com.google.api.SystemParameter) obj;

    if (!getName().equals(other.getName())) return false;
    if (!getHttpHeader().equals(other.getHttpHeader())) return false;
    if (!getUrlQueryParameter().equals(other.getUrlQueryParameter())) return false;
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
    hash = (37 * hash) + NAME_FIELD_NUMBER;
    hash = (53 * hash) + getName().hashCode();
    hash = (37 * hash) + HTTP_HEADER_FIELD_NUMBER;
    hash = (53 * hash) + getHttpHeader().hashCode();
    hash = (37 * hash) + URL_QUERY_PARAMETER_FIELD_NUMBER;
    hash = (53 * hash) + getUrlQueryParameter().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.api.SystemParameter parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.SystemParameter parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.SystemParameter parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.SystemParameter parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.SystemParameter parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.api.SystemParameter parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.api.SystemParameter parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.api.SystemParameter parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.api.SystemParameter parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.api.SystemParameter parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.api.SystemParameter parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.api.SystemParameter parseFrom(
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

  public static Builder newBuilder(com.google.api.SystemParameter prototype) {
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
   * Define a parameter's name and location. The parameter may be passed as either
   * an HTTP header or a URL query parameter, and if both are passed the behavior
   * is implementation-dependent.
   * </pre>
   *
   * Protobuf type {@code google.api.SystemParameter}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.api.SystemParameter)
      com.google.api.SystemParameterOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.api.SystemParameterProto
          .internal_static_google_api_SystemParameter_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.api.SystemParameterProto
          .internal_static_google_api_SystemParameter_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.api.SystemParameter.class, com.google.api.SystemParameter.Builder.class);
    }

    // Construct using com.google.api.SystemParameter.newBuilder()
    private Builder() {}

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      name_ = "";
      httpHeader_ = "";
      urlQueryParameter_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.api.SystemParameterProto
          .internal_static_google_api_SystemParameter_descriptor;
    }

    @java.lang.Override
    public com.google.api.SystemParameter getDefaultInstanceForType() {
      return com.google.api.SystemParameter.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.api.SystemParameter build() {
      com.google.api.SystemParameter result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.api.SystemParameter buildPartial() {
      com.google.api.SystemParameter result = new com.google.api.SystemParameter(this);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.api.SystemParameter result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.name_ = name_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.httpHeader_ = httpHeader_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.urlQueryParameter_ = urlQueryParameter_;
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
      if (other instanceof com.google.api.SystemParameter) {
        return mergeFrom((com.google.api.SystemParameter) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.api.SystemParameter other) {
      if (other == com.google.api.SystemParameter.getDefaultInstance()) return this;
      if (!other.getName().isEmpty()) {
        name_ = other.name_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (!other.getHttpHeader().isEmpty()) {
        httpHeader_ = other.httpHeader_;
        bitField0_ |= 0x00000002;
        onChanged();
      }
      if (!other.getUrlQueryParameter().isEmpty()) {
        urlQueryParameter_ = other.urlQueryParameter_;
        bitField0_ |= 0x00000004;
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
                name_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000001;
                break;
              } // case 10
            case 18:
              {
                httpHeader_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000002;
                break;
              } // case 18
            case 26:
              {
                urlQueryParameter_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000004;
                break;
              } // case 26
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

    private java.lang.Object name_ = "";

    /**
     *
     *
     * <pre>
     * Define the name of the parameter, such as "api_key" . It is case sensitive.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return The name.
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }

    /**
     *
     *
     * <pre>
     * Define the name of the parameter, such as "api_key" . It is case sensitive.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return The bytes for name.
     */
    public com.google.protobuf.ByteString getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    /**
     *
     *
     * <pre>
     * Define the name of the parameter, such as "api_key" . It is case sensitive.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @param value The name to set.
     * @return This builder for chaining.
     */
    public Builder setName(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      name_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * Define the name of the parameter, such as "api_key" . It is case sensitive.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearName() {
      name_ = getDefaultInstance().getName();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * Define the name of the parameter, such as "api_key" . It is case sensitive.
     * </pre>
     *
     * <code>string name = 1;</code>
     *
     * @param value The bytes for name to set.
     * @return This builder for chaining.
     */
    public Builder setNameBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);
      name_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private java.lang.Object httpHeader_ = "";

    /**
     *
     *
     * <pre>
     * Define the HTTP header name to use for the parameter. It is case
     * insensitive.
     * </pre>
     *
     * <code>string http_header = 2;</code>
     *
     * @return The httpHeader.
     */
    public java.lang.String getHttpHeader() {
      java.lang.Object ref = httpHeader_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        httpHeader_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }

    /**
     *
     *
     * <pre>
     * Define the HTTP header name to use for the parameter. It is case
     * insensitive.
     * </pre>
     *
     * <code>string http_header = 2;</code>
     *
     * @return The bytes for httpHeader.
     */
    public com.google.protobuf.ByteString getHttpHeaderBytes() {
      java.lang.Object ref = httpHeader_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        httpHeader_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    /**
     *
     *
     * <pre>
     * Define the HTTP header name to use for the parameter. It is case
     * insensitive.
     * </pre>
     *
     * <code>string http_header = 2;</code>
     *
     * @param value The httpHeader to set.
     * @return This builder for chaining.
     */
    public Builder setHttpHeader(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      httpHeader_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * Define the HTTP header name to use for the parameter. It is case
     * insensitive.
     * </pre>
     *
     * <code>string http_header = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearHttpHeader() {
      httpHeader_ = getDefaultInstance().getHttpHeader();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * Define the HTTP header name to use for the parameter. It is case
     * insensitive.
     * </pre>
     *
     * <code>string http_header = 2;</code>
     *
     * @param value The bytes for httpHeader to set.
     * @return This builder for chaining.
     */
    public Builder setHttpHeaderBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);
      httpHeader_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    private java.lang.Object urlQueryParameter_ = "";

    /**
     *
     *
     * <pre>
     * Define the URL query parameter name to use for the parameter. It is case
     * sensitive.
     * </pre>
     *
     * <code>string url_query_parameter = 3;</code>
     *
     * @return The urlQueryParameter.
     */
    public java.lang.String getUrlQueryParameter() {
      java.lang.Object ref = urlQueryParameter_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        urlQueryParameter_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }

    /**
     *
     *
     * <pre>
     * Define the URL query parameter name to use for the parameter. It is case
     * sensitive.
     * </pre>
     *
     * <code>string url_query_parameter = 3;</code>
     *
     * @return The bytes for urlQueryParameter.
     */
    public com.google.protobuf.ByteString getUrlQueryParameterBytes() {
      java.lang.Object ref = urlQueryParameter_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        urlQueryParameter_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    /**
     *
     *
     * <pre>
     * Define the URL query parameter name to use for the parameter. It is case
     * sensitive.
     * </pre>
     *
     * <code>string url_query_parameter = 3;</code>
     *
     * @param value The urlQueryParameter to set.
     * @return This builder for chaining.
     */
    public Builder setUrlQueryParameter(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      urlQueryParameter_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * Define the URL query parameter name to use for the parameter. It is case
     * sensitive.
     * </pre>
     *
     * <code>string url_query_parameter = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearUrlQueryParameter() {
      urlQueryParameter_ = getDefaultInstance().getUrlQueryParameter();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * Define the URL query parameter name to use for the parameter. It is case
     * sensitive.
     * </pre>
     *
     * <code>string url_query_parameter = 3;</code>
     *
     * @param value The bytes for urlQueryParameter to set.
     * @return This builder for chaining.
     */
    public Builder setUrlQueryParameterBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);
      urlQueryParameter_ = value;
      bitField0_ |= 0x00000004;
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

    // @@protoc_insertion_point(builder_scope:google.api.SystemParameter)
  }

  // @@protoc_insertion_point(class_scope:google.api.SystemParameter)
  private static final com.google.api.SystemParameter DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.api.SystemParameter();
  }

  public static com.google.api.SystemParameter getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<SystemParameter> PARSER =
      new com.google.protobuf.AbstractParser<SystemParameter>() {
        @java.lang.Override
        public SystemParameter parsePartialFrom(
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

  public static com.google.protobuf.Parser<SystemParameter> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<SystemParameter> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.api.SystemParameter getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
