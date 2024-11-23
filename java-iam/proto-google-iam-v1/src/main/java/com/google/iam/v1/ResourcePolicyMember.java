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
// source: google/iam/v1/resource_policy_member.proto

// Protobuf Java Version: 3.25.5
package com.google.iam.v1;

/**
 *
 *
 * <pre>
 * Output-only policy member strings of a Google Cloud resource's built-in
 * identity.
 * </pre>
 *
 * Protobuf type {@code google.iam.v1.ResourcePolicyMember}
 */
public final class ResourcePolicyMember extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.iam.v1.ResourcePolicyMember)
    ResourcePolicyMemberOrBuilder {
  private static final long serialVersionUID = 0L;
  // Use ResourcePolicyMember.newBuilder() to construct.
  private ResourcePolicyMember(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private ResourcePolicyMember() {
    iamPolicyNamePrincipal_ = "";
    iamPolicyUidPrincipal_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new ResourcePolicyMember();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.iam.v1.ResourcePolicyMemberProto
        .internal_static_google_iam_v1_ResourcePolicyMember_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.iam.v1.ResourcePolicyMemberProto
        .internal_static_google_iam_v1_ResourcePolicyMember_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.iam.v1.ResourcePolicyMember.class,
            com.google.iam.v1.ResourcePolicyMember.Builder.class);
  }

  public static final int IAM_POLICY_NAME_PRINCIPAL_FIELD_NUMBER = 1;

  @SuppressWarnings("serial")
  private volatile java.lang.Object iamPolicyNamePrincipal_ = "";
  /**
   *
   *
   * <pre>
   * IAM policy binding member referring to a Google Cloud resource by
   * user-assigned name (https://google.aip.dev/122). If a resource is deleted
   * and recreated with the same name, the binding will be applicable to the new
   * resource.
   *
   * Example:
   * `principal://parametermanager.googleapis.com/projects/12345/name/locations/us-central1-a/parameters/my-parameter`
   * </pre>
   *
   * <code>string iam_policy_name_principal = 1 [(.google.api.field_behavior) = OUTPUT_ONLY];</code>
   *
   * @return The iamPolicyNamePrincipal.
   */
  @java.lang.Override
  public java.lang.String getIamPolicyNamePrincipal() {
    java.lang.Object ref = iamPolicyNamePrincipal_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      iamPolicyNamePrincipal_ = s;
      return s;
    }
  }
  /**
   *
   *
   * <pre>
   * IAM policy binding member referring to a Google Cloud resource by
   * user-assigned name (https://google.aip.dev/122). If a resource is deleted
   * and recreated with the same name, the binding will be applicable to the new
   * resource.
   *
   * Example:
   * `principal://parametermanager.googleapis.com/projects/12345/name/locations/us-central1-a/parameters/my-parameter`
   * </pre>
   *
   * <code>string iam_policy_name_principal = 1 [(.google.api.field_behavior) = OUTPUT_ONLY];</code>
   *
   * @return The bytes for iamPolicyNamePrincipal.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getIamPolicyNamePrincipalBytes() {
    java.lang.Object ref = iamPolicyNamePrincipal_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      iamPolicyNamePrincipal_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int IAM_POLICY_UID_PRINCIPAL_FIELD_NUMBER = 2;

  @SuppressWarnings("serial")
  private volatile java.lang.Object iamPolicyUidPrincipal_ = "";
  /**
   *
   *
   * <pre>
   * IAM policy binding member referring to a Google Cloud resource by
   * system-assigned unique identifier (https://google.aip.dev/148#uid). If a
   * resource is deleted and recreated with the same name, the binding will not
   * be applicable to the new resource
   *
   * Example:
   * `principal://parametermanager.googleapis.com/projects/12345/uid/locations/us-central1-a/parameters/a918fed5`
   * </pre>
   *
   * <code>string iam_policy_uid_principal = 2 [(.google.api.field_behavior) = OUTPUT_ONLY];</code>
   *
   * @return The iamPolicyUidPrincipal.
   */
  @java.lang.Override
  public java.lang.String getIamPolicyUidPrincipal() {
    java.lang.Object ref = iamPolicyUidPrincipal_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      iamPolicyUidPrincipal_ = s;
      return s;
    }
  }
  /**
   *
   *
   * <pre>
   * IAM policy binding member referring to a Google Cloud resource by
   * system-assigned unique identifier (https://google.aip.dev/148#uid). If a
   * resource is deleted and recreated with the same name, the binding will not
   * be applicable to the new resource
   *
   * Example:
   * `principal://parametermanager.googleapis.com/projects/12345/uid/locations/us-central1-a/parameters/a918fed5`
   * </pre>
   *
   * <code>string iam_policy_uid_principal = 2 [(.google.api.field_behavior) = OUTPUT_ONLY];</code>
   *
   * @return The bytes for iamPolicyUidPrincipal.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getIamPolicyUidPrincipalBytes() {
    java.lang.Object ref = iamPolicyUidPrincipal_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
      iamPolicyUidPrincipal_ = b;
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
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(iamPolicyNamePrincipal_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, iamPolicyNamePrincipal_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(iamPolicyUidPrincipal_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, iamPolicyUidPrincipal_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(iamPolicyNamePrincipal_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, iamPolicyNamePrincipal_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(iamPolicyUidPrincipal_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, iamPolicyUidPrincipal_);
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
    if (!(obj instanceof com.google.iam.v1.ResourcePolicyMember)) {
      return super.equals(obj);
    }
    com.google.iam.v1.ResourcePolicyMember other = (com.google.iam.v1.ResourcePolicyMember) obj;

    if (!getIamPolicyNamePrincipal().equals(other.getIamPolicyNamePrincipal())) return false;
    if (!getIamPolicyUidPrincipal().equals(other.getIamPolicyUidPrincipal())) return false;
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
    hash = (37 * hash) + IAM_POLICY_NAME_PRINCIPAL_FIELD_NUMBER;
    hash = (53 * hash) + getIamPolicyNamePrincipal().hashCode();
    hash = (37 * hash) + IAM_POLICY_UID_PRINCIPAL_FIELD_NUMBER;
    hash = (53 * hash) + getIamPolicyUidPrincipal().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.iam.v1.ResourcePolicyMember parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.iam.v1.ResourcePolicyMember parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.iam.v1.ResourcePolicyMember parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.iam.v1.ResourcePolicyMember parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.iam.v1.ResourcePolicyMember parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.iam.v1.ResourcePolicyMember parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.iam.v1.ResourcePolicyMember parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.iam.v1.ResourcePolicyMember parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.iam.v1.ResourcePolicyMember parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.iam.v1.ResourcePolicyMember parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.iam.v1.ResourcePolicyMember parseFrom(
      com.google.protobuf.CodedInputStream input) throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.iam.v1.ResourcePolicyMember parseFrom(
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

  public static Builder newBuilder(com.google.iam.v1.ResourcePolicyMember prototype) {
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
   * Output-only policy member strings of a Google Cloud resource's built-in
   * identity.
   * </pre>
   *
   * Protobuf type {@code google.iam.v1.ResourcePolicyMember}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.iam.v1.ResourcePolicyMember)
      com.google.iam.v1.ResourcePolicyMemberOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.iam.v1.ResourcePolicyMemberProto
          .internal_static_google_iam_v1_ResourcePolicyMember_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.iam.v1.ResourcePolicyMemberProto
          .internal_static_google_iam_v1_ResourcePolicyMember_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.iam.v1.ResourcePolicyMember.class,
              com.google.iam.v1.ResourcePolicyMember.Builder.class);
    }

    // Construct using com.google.iam.v1.ResourcePolicyMember.newBuilder()
    private Builder() {}

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      iamPolicyNamePrincipal_ = "";
      iamPolicyUidPrincipal_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.iam.v1.ResourcePolicyMemberProto
          .internal_static_google_iam_v1_ResourcePolicyMember_descriptor;
    }

    @java.lang.Override
    public com.google.iam.v1.ResourcePolicyMember getDefaultInstanceForType() {
      return com.google.iam.v1.ResourcePolicyMember.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.iam.v1.ResourcePolicyMember build() {
      com.google.iam.v1.ResourcePolicyMember result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.iam.v1.ResourcePolicyMember buildPartial() {
      com.google.iam.v1.ResourcePolicyMember result =
          new com.google.iam.v1.ResourcePolicyMember(this);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.iam.v1.ResourcePolicyMember result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.iamPolicyNamePrincipal_ = iamPolicyNamePrincipal_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.iamPolicyUidPrincipal_ = iamPolicyUidPrincipal_;
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
      if (other instanceof com.google.iam.v1.ResourcePolicyMember) {
        return mergeFrom((com.google.iam.v1.ResourcePolicyMember) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.iam.v1.ResourcePolicyMember other) {
      if (other == com.google.iam.v1.ResourcePolicyMember.getDefaultInstance()) return this;
      if (!other.getIamPolicyNamePrincipal().isEmpty()) {
        iamPolicyNamePrincipal_ = other.iamPolicyNamePrincipal_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (!other.getIamPolicyUidPrincipal().isEmpty()) {
        iamPolicyUidPrincipal_ = other.iamPolicyUidPrincipal_;
        bitField0_ |= 0x00000002;
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
                iamPolicyNamePrincipal_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000001;
                break;
              } // case 10
            case 18:
              {
                iamPolicyUidPrincipal_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000002;
                break;
              } // case 18
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

    private java.lang.Object iamPolicyNamePrincipal_ = "";
    /**
     *
     *
     * <pre>
     * IAM policy binding member referring to a Google Cloud resource by
     * user-assigned name (https://google.aip.dev/122). If a resource is deleted
     * and recreated with the same name, the binding will be applicable to the new
     * resource.
     *
     * Example:
     * `principal://parametermanager.googleapis.com/projects/12345/name/locations/us-central1-a/parameters/my-parameter`
     * </pre>
     *
     * <code>string iam_policy_name_principal = 1 [(.google.api.field_behavior) = OUTPUT_ONLY];
     * </code>
     *
     * @return The iamPolicyNamePrincipal.
     */
    public java.lang.String getIamPolicyNamePrincipal() {
      java.lang.Object ref = iamPolicyNamePrincipal_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        iamPolicyNamePrincipal_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     *
     *
     * <pre>
     * IAM policy binding member referring to a Google Cloud resource by
     * user-assigned name (https://google.aip.dev/122). If a resource is deleted
     * and recreated with the same name, the binding will be applicable to the new
     * resource.
     *
     * Example:
     * `principal://parametermanager.googleapis.com/projects/12345/name/locations/us-central1-a/parameters/my-parameter`
     * </pre>
     *
     * <code>string iam_policy_name_principal = 1 [(.google.api.field_behavior) = OUTPUT_ONLY];
     * </code>
     *
     * @return The bytes for iamPolicyNamePrincipal.
     */
    public com.google.protobuf.ByteString getIamPolicyNamePrincipalBytes() {
      java.lang.Object ref = iamPolicyNamePrincipal_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        iamPolicyNamePrincipal_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     *
     *
     * <pre>
     * IAM policy binding member referring to a Google Cloud resource by
     * user-assigned name (https://google.aip.dev/122). If a resource is deleted
     * and recreated with the same name, the binding will be applicable to the new
     * resource.
     *
     * Example:
     * `principal://parametermanager.googleapis.com/projects/12345/name/locations/us-central1-a/parameters/my-parameter`
     * </pre>
     *
     * <code>string iam_policy_name_principal = 1 [(.google.api.field_behavior) = OUTPUT_ONLY];
     * </code>
     *
     * @param value The iamPolicyNamePrincipal to set.
     * @return This builder for chaining.
     */
    public Builder setIamPolicyNamePrincipal(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      iamPolicyNamePrincipal_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * IAM policy binding member referring to a Google Cloud resource by
     * user-assigned name (https://google.aip.dev/122). If a resource is deleted
     * and recreated with the same name, the binding will be applicable to the new
     * resource.
     *
     * Example:
     * `principal://parametermanager.googleapis.com/projects/12345/name/locations/us-central1-a/parameters/my-parameter`
     * </pre>
     *
     * <code>string iam_policy_name_principal = 1 [(.google.api.field_behavior) = OUTPUT_ONLY];
     * </code>
     *
     * @return This builder for chaining.
     */
    public Builder clearIamPolicyNamePrincipal() {
      iamPolicyNamePrincipal_ = getDefaultInstance().getIamPolicyNamePrincipal();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * IAM policy binding member referring to a Google Cloud resource by
     * user-assigned name (https://google.aip.dev/122). If a resource is deleted
     * and recreated with the same name, the binding will be applicable to the new
     * resource.
     *
     * Example:
     * `principal://parametermanager.googleapis.com/projects/12345/name/locations/us-central1-a/parameters/my-parameter`
     * </pre>
     *
     * <code>string iam_policy_name_principal = 1 [(.google.api.field_behavior) = OUTPUT_ONLY];
     * </code>
     *
     * @param value The bytes for iamPolicyNamePrincipal to set.
     * @return This builder for chaining.
     */
    public Builder setIamPolicyNamePrincipalBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);
      iamPolicyNamePrincipal_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private java.lang.Object iamPolicyUidPrincipal_ = "";
    /**
     *
     *
     * <pre>
     * IAM policy binding member referring to a Google Cloud resource by
     * system-assigned unique identifier (https://google.aip.dev/148#uid). If a
     * resource is deleted and recreated with the same name, the binding will not
     * be applicable to the new resource
     *
     * Example:
     * `principal://parametermanager.googleapis.com/projects/12345/uid/locations/us-central1-a/parameters/a918fed5`
     * </pre>
     *
     * <code>string iam_policy_uid_principal = 2 [(.google.api.field_behavior) = OUTPUT_ONLY];
     * </code>
     *
     * @return The iamPolicyUidPrincipal.
     */
    public java.lang.String getIamPolicyUidPrincipal() {
      java.lang.Object ref = iamPolicyUidPrincipal_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs = (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        iamPolicyUidPrincipal_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     *
     *
     * <pre>
     * IAM policy binding member referring to a Google Cloud resource by
     * system-assigned unique identifier (https://google.aip.dev/148#uid). If a
     * resource is deleted and recreated with the same name, the binding will not
     * be applicable to the new resource
     *
     * Example:
     * `principal://parametermanager.googleapis.com/projects/12345/uid/locations/us-central1-a/parameters/a918fed5`
     * </pre>
     *
     * <code>string iam_policy_uid_principal = 2 [(.google.api.field_behavior) = OUTPUT_ONLY];
     * </code>
     *
     * @return The bytes for iamPolicyUidPrincipal.
     */
    public com.google.protobuf.ByteString getIamPolicyUidPrincipalBytes() {
      java.lang.Object ref = iamPolicyUidPrincipal_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8((java.lang.String) ref);
        iamPolicyUidPrincipal_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     *
     *
     * <pre>
     * IAM policy binding member referring to a Google Cloud resource by
     * system-assigned unique identifier (https://google.aip.dev/148#uid). If a
     * resource is deleted and recreated with the same name, the binding will not
     * be applicable to the new resource
     *
     * Example:
     * `principal://parametermanager.googleapis.com/projects/12345/uid/locations/us-central1-a/parameters/a918fed5`
     * </pre>
     *
     * <code>string iam_policy_uid_principal = 2 [(.google.api.field_behavior) = OUTPUT_ONLY];
     * </code>
     *
     * @param value The iamPolicyUidPrincipal to set.
     * @return This builder for chaining.
     */
    public Builder setIamPolicyUidPrincipal(java.lang.String value) {
      if (value == null) {
        throw new NullPointerException();
      }
      iamPolicyUidPrincipal_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * IAM policy binding member referring to a Google Cloud resource by
     * system-assigned unique identifier (https://google.aip.dev/148#uid). If a
     * resource is deleted and recreated with the same name, the binding will not
     * be applicable to the new resource
     *
     * Example:
     * `principal://parametermanager.googleapis.com/projects/12345/uid/locations/us-central1-a/parameters/a918fed5`
     * </pre>
     *
     * <code>string iam_policy_uid_principal = 2 [(.google.api.field_behavior) = OUTPUT_ONLY];
     * </code>
     *
     * @return This builder for chaining.
     */
    public Builder clearIamPolicyUidPrincipal() {
      iamPolicyUidPrincipal_ = getDefaultInstance().getIamPolicyUidPrincipal();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     *
     *
     * <pre>
     * IAM policy binding member referring to a Google Cloud resource by
     * system-assigned unique identifier (https://google.aip.dev/148#uid). If a
     * resource is deleted and recreated with the same name, the binding will not
     * be applicable to the new resource
     *
     * Example:
     * `principal://parametermanager.googleapis.com/projects/12345/uid/locations/us-central1-a/parameters/a918fed5`
     * </pre>
     *
     * <code>string iam_policy_uid_principal = 2 [(.google.api.field_behavior) = OUTPUT_ONLY];
     * </code>
     *
     * @param value The bytes for iamPolicyUidPrincipal to set.
     * @return This builder for chaining.
     */
    public Builder setIamPolicyUidPrincipalBytes(com.google.protobuf.ByteString value) {
      if (value == null) {
        throw new NullPointerException();
      }
      checkByteStringIsUtf8(value);
      iamPolicyUidPrincipal_ = value;
      bitField0_ |= 0x00000002;
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

    // @@protoc_insertion_point(builder_scope:google.iam.v1.ResourcePolicyMember)
  }

  // @@protoc_insertion_point(class_scope:google.iam.v1.ResourcePolicyMember)
  private static final com.google.iam.v1.ResourcePolicyMember DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.iam.v1.ResourcePolicyMember();
  }

  public static com.google.iam.v1.ResourcePolicyMember getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ResourcePolicyMember> PARSER =
      new com.google.protobuf.AbstractParser<ResourcePolicyMember>() {
        @java.lang.Override
        public ResourcePolicyMember parsePartialFrom(
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

  public static com.google.protobuf.Parser<ResourcePolicyMember> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ResourcePolicyMember> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.iam.v1.ResourcePolicyMember getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
