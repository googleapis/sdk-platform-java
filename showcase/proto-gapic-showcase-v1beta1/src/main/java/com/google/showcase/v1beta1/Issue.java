// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/testing.proto

// Protobuf Java Version: 3.25.1
package com.google.showcase.v1beta1;

/**
 * <pre>
 * An issue found in the test.
 * </pre>
 *
 * Protobuf type {@code google.showcase.v1beta1.Issue}
 */
public final class Issue extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:google.showcase.v1beta1.Issue)
    IssueOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Issue.newBuilder() to construct.
  private Issue(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Issue() {
    type_ = 0;
    severity_ = 0;
    description_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Issue();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.google.showcase.v1beta1.TestingOuterClass.internal_static_google_showcase_v1beta1_Issue_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.showcase.v1beta1.TestingOuterClass.internal_static_google_showcase_v1beta1_Issue_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.showcase.v1beta1.Issue.class, com.google.showcase.v1beta1.Issue.Builder.class);
  }

  /**
   * <pre>
   * The different potential types of issues.
   * </pre>
   *
   * Protobuf enum {@code google.showcase.v1beta1.Issue.Type}
   */
  public enum Type
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>TYPE_UNSPECIFIED = 0;</code>
     */
    TYPE_UNSPECIFIED(0),
    /**
     * <pre>
     * The test was never instrumented.
     * </pre>
     *
     * <code>SKIPPED = 1;</code>
     */
    SKIPPED(1),
    /**
     * <pre>
     * The test was started but never confirmed.
     * </pre>
     *
     * <code>PENDING = 2;</code>
     */
    PENDING(2),
    /**
     * <pre>
     * The test was instrumented, but Showcase got an unexpected
     * value when the generator tried to confirm success.
     * </pre>
     *
     * <code>INCORRECT_CONFIRMATION = 3;</code>
     */
    INCORRECT_CONFIRMATION(3),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>TYPE_UNSPECIFIED = 0;</code>
     */
    public static final int TYPE_UNSPECIFIED_VALUE = 0;
    /**
     * <pre>
     * The test was never instrumented.
     * </pre>
     *
     * <code>SKIPPED = 1;</code>
     */
    public static final int SKIPPED_VALUE = 1;
    /**
     * <pre>
     * The test was started but never confirmed.
     * </pre>
     *
     * <code>PENDING = 2;</code>
     */
    public static final int PENDING_VALUE = 2;
    /**
     * <pre>
     * The test was instrumented, but Showcase got an unexpected
     * value when the generator tried to confirm success.
     * </pre>
     *
     * <code>INCORRECT_CONFIRMATION = 3;</code>
     */
    public static final int INCORRECT_CONFIRMATION_VALUE = 3;


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
    public static Type valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static Type forNumber(int value) {
      switch (value) {
        case 0: return TYPE_UNSPECIFIED;
        case 1: return SKIPPED;
        case 2: return PENDING;
        case 3: return INCORRECT_CONFIRMATION;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<Type>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        Type> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<Type>() {
            public Type findValueByNumber(int number) {
              return Type.forNumber(number);
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
      return com.google.showcase.v1beta1.Issue.getDescriptor().getEnumTypes().get(0);
    }

    private static final Type[] VALUES = values();

    public static Type valueOf(
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

    private Type(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:google.showcase.v1beta1.Issue.Type)
  }

  /**
   * <pre>
   * Severity levels.
   * </pre>
   *
   * Protobuf enum {@code google.showcase.v1beta1.Issue.Severity}
   */
  public enum Severity
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>SEVERITY_UNSPECIFIED = 0;</code>
     */
    SEVERITY_UNSPECIFIED(0),
    /**
     * <pre>
     * Errors.
     * </pre>
     *
     * <code>ERROR = 1;</code>
     */
    ERROR(1),
    /**
     * <pre>
     * Warnings.
     * </pre>
     *
     * <code>WARNING = 2;</code>
     */
    WARNING(2),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>SEVERITY_UNSPECIFIED = 0;</code>
     */
    public static final int SEVERITY_UNSPECIFIED_VALUE = 0;
    /**
     * <pre>
     * Errors.
     * </pre>
     *
     * <code>ERROR = 1;</code>
     */
    public static final int ERROR_VALUE = 1;
    /**
     * <pre>
     * Warnings.
     * </pre>
     *
     * <code>WARNING = 2;</code>
     */
    public static final int WARNING_VALUE = 2;


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
    public static Severity valueOf(int value) {
      return forNumber(value);
    }

    /**
     * @param value The numeric wire value of the corresponding enum entry.
     * @return The enum associated with the given numeric wire value.
     */
    public static Severity forNumber(int value) {
      switch (value) {
        case 0: return SEVERITY_UNSPECIFIED;
        case 1: return ERROR;
        case 2: return WARNING;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<Severity>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        Severity> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<Severity>() {
            public Severity findValueByNumber(int number) {
              return Severity.forNumber(number);
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
      return com.google.showcase.v1beta1.Issue.getDescriptor().getEnumTypes().get(1);
    }

    private static final Severity[] VALUES = values();

    public static Severity valueOf(
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

    private Severity(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:google.showcase.v1beta1.Issue.Severity)
  }

  public static final int TYPE_FIELD_NUMBER = 1;
  private int type_ = 0;
  /**
   * <pre>
   * The type of the issue.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Issue.Type type = 1;</code>
   * @return The enum numeric value on the wire for type.
   */
  @java.lang.Override public int getTypeValue() {
    return type_;
  }
  /**
   * <pre>
   * The type of the issue.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Issue.Type type = 1;</code>
   * @return The type.
   */
  @java.lang.Override public com.google.showcase.v1beta1.Issue.Type getType() {
    com.google.showcase.v1beta1.Issue.Type result = com.google.showcase.v1beta1.Issue.Type.forNumber(type_);
    return result == null ? com.google.showcase.v1beta1.Issue.Type.UNRECOGNIZED : result;
  }

  public static final int SEVERITY_FIELD_NUMBER = 2;
  private int severity_ = 0;
  /**
   * <pre>
   * The severity of the issue.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Issue.Severity severity = 2;</code>
   * @return The enum numeric value on the wire for severity.
   */
  @java.lang.Override public int getSeverityValue() {
    return severity_;
  }
  /**
   * <pre>
   * The severity of the issue.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Issue.Severity severity = 2;</code>
   * @return The severity.
   */
  @java.lang.Override public com.google.showcase.v1beta1.Issue.Severity getSeverity() {
    com.google.showcase.v1beta1.Issue.Severity result = com.google.showcase.v1beta1.Issue.Severity.forNumber(severity_);
    return result == null ? com.google.showcase.v1beta1.Issue.Severity.UNRECOGNIZED : result;
  }

  public static final int DESCRIPTION_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private volatile java.lang.Object description_ = "";
  /**
   * <pre>
   * A description of the issue.
   * </pre>
   *
   * <code>string description = 3;</code>
   * @return The description.
   */
  @java.lang.Override
  public java.lang.String getDescription() {
    java.lang.Object ref = description_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      description_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * A description of the issue.
   * </pre>
   *
   * <code>string description = 3;</code>
   * @return The bytes for description.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getDescriptionBytes() {
    java.lang.Object ref = description_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      description_ = b;
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
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (type_ != com.google.showcase.v1beta1.Issue.Type.TYPE_UNSPECIFIED.getNumber()) {
      output.writeEnum(1, type_);
    }
    if (severity_ != com.google.showcase.v1beta1.Issue.Severity.SEVERITY_UNSPECIFIED.getNumber()) {
      output.writeEnum(2, severity_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(description_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, description_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (type_ != com.google.showcase.v1beta1.Issue.Type.TYPE_UNSPECIFIED.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, type_);
    }
    if (severity_ != com.google.showcase.v1beta1.Issue.Severity.SEVERITY_UNSPECIFIED.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(2, severity_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(description_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, description_);
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
    if (!(obj instanceof com.google.showcase.v1beta1.Issue)) {
      return super.equals(obj);
    }
    com.google.showcase.v1beta1.Issue other = (com.google.showcase.v1beta1.Issue) obj;

    if (type_ != other.type_) return false;
    if (severity_ != other.severity_) return false;
    if (!getDescription()
        .equals(other.getDescription())) return false;
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
    hash = (37 * hash) + SEVERITY_FIELD_NUMBER;
    hash = (53 * hash) + severity_;
    hash = (37 * hash) + DESCRIPTION_FIELD_NUMBER;
    hash = (53 * hash) + getDescription().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.showcase.v1beta1.Issue parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.Issue parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.Issue parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.Issue parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.Issue parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.Issue parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.Issue parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.showcase.v1beta1.Issue parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.Issue parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.Issue parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.Issue parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.showcase.v1beta1.Issue parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.google.showcase.v1beta1.Issue prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * An issue found in the test.
   * </pre>
   *
   * Protobuf type {@code google.showcase.v1beta1.Issue}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:google.showcase.v1beta1.Issue)
      com.google.showcase.v1beta1.IssueOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.google.showcase.v1beta1.TestingOuterClass.internal_static_google_showcase_v1beta1_Issue_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.showcase.v1beta1.TestingOuterClass.internal_static_google_showcase_v1beta1_Issue_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.showcase.v1beta1.Issue.class, com.google.showcase.v1beta1.Issue.Builder.class);
    }

    // Construct using com.google.showcase.v1beta1.Issue.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      type_ = 0;
      severity_ = 0;
      description_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.google.showcase.v1beta1.TestingOuterClass.internal_static_google_showcase_v1beta1_Issue_descriptor;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.Issue getDefaultInstanceForType() {
      return com.google.showcase.v1beta1.Issue.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.Issue build() {
      com.google.showcase.v1beta1.Issue result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.Issue buildPartial() {
      com.google.showcase.v1beta1.Issue result = new com.google.showcase.v1beta1.Issue(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.showcase.v1beta1.Issue result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.type_ = type_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.severity_ = severity_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.description_ = description_;
      }
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.google.showcase.v1beta1.Issue) {
        return mergeFrom((com.google.showcase.v1beta1.Issue)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.showcase.v1beta1.Issue other) {
      if (other == com.google.showcase.v1beta1.Issue.getDefaultInstance()) return this;
      if (other.type_ != 0) {
        setTypeValue(other.getTypeValue());
      }
      if (other.severity_ != 0) {
        setSeverityValue(other.getSeverityValue());
      }
      if (!other.getDescription().isEmpty()) {
        description_ = other.description_;
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
            case 8: {
              type_ = input.readEnum();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 16: {
              severity_ = input.readEnum();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 26: {
              description_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            default: {
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
     * <pre>
     * The type of the issue.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue.Type type = 1;</code>
     * @return The enum numeric value on the wire for type.
     */
    @java.lang.Override public int getTypeValue() {
      return type_;
    }
    /**
     * <pre>
     * The type of the issue.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue.Type type = 1;</code>
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
     * <pre>
     * The type of the issue.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue.Type type = 1;</code>
     * @return The type.
     */
    @java.lang.Override
    public com.google.showcase.v1beta1.Issue.Type getType() {
      com.google.showcase.v1beta1.Issue.Type result = com.google.showcase.v1beta1.Issue.Type.forNumber(type_);
      return result == null ? com.google.showcase.v1beta1.Issue.Type.UNRECOGNIZED : result;
    }
    /**
     * <pre>
     * The type of the issue.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue.Type type = 1;</code>
     * @param value The type to set.
     * @return This builder for chaining.
     */
    public Builder setType(com.google.showcase.v1beta1.Issue.Type value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000001;
      type_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The type of the issue.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue.Type type = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearType() {
      bitField0_ = (bitField0_ & ~0x00000001);
      type_ = 0;
      onChanged();
      return this;
    }

    private int severity_ = 0;
    /**
     * <pre>
     * The severity of the issue.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue.Severity severity = 2;</code>
     * @return The enum numeric value on the wire for severity.
     */
    @java.lang.Override public int getSeverityValue() {
      return severity_;
    }
    /**
     * <pre>
     * The severity of the issue.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue.Severity severity = 2;</code>
     * @param value The enum numeric value on the wire for severity to set.
     * @return This builder for chaining.
     */
    public Builder setSeverityValue(int value) {
      severity_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The severity of the issue.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue.Severity severity = 2;</code>
     * @return The severity.
     */
    @java.lang.Override
    public com.google.showcase.v1beta1.Issue.Severity getSeverity() {
      com.google.showcase.v1beta1.Issue.Severity result = com.google.showcase.v1beta1.Issue.Severity.forNumber(severity_);
      return result == null ? com.google.showcase.v1beta1.Issue.Severity.UNRECOGNIZED : result;
    }
    /**
     * <pre>
     * The severity of the issue.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue.Severity severity = 2;</code>
     * @param value The severity to set.
     * @return This builder for chaining.
     */
    public Builder setSeverity(com.google.showcase.v1beta1.Issue.Severity value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000002;
      severity_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The severity of the issue.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue.Severity severity = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearSeverity() {
      bitField0_ = (bitField0_ & ~0x00000002);
      severity_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object description_ = "";
    /**
     * <pre>
     * A description of the issue.
     * </pre>
     *
     * <code>string description = 3;</code>
     * @return The description.
     */
    public java.lang.String getDescription() {
      java.lang.Object ref = description_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        description_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * A description of the issue.
     * </pre>
     *
     * <code>string description = 3;</code>
     * @return The bytes for description.
     */
    public com.google.protobuf.ByteString
        getDescriptionBytes() {
      java.lang.Object ref = description_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        description_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * A description of the issue.
     * </pre>
     *
     * <code>string description = 3;</code>
     * @param value The description to set.
     * @return This builder for chaining.
     */
    public Builder setDescription(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      description_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * A description of the issue.
     * </pre>
     *
     * <code>string description = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearDescription() {
      description_ = getDefaultInstance().getDescription();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * A description of the issue.
     * </pre>
     *
     * <code>string description = 3;</code>
     * @param value The bytes for description to set.
     * @return This builder for chaining.
     */
    public Builder setDescriptionBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      description_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:google.showcase.v1beta1.Issue)
  }

  // @@protoc_insertion_point(class_scope:google.showcase.v1beta1.Issue)
  private static final com.google.showcase.v1beta1.Issue DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.google.showcase.v1beta1.Issue();
  }

  public static com.google.showcase.v1beta1.Issue getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Issue>
      PARSER = new com.google.protobuf.AbstractParser<Issue>() {
    @java.lang.Override
    public Issue parsePartialFrom(
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

  public static com.google.protobuf.Parser<Issue> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Issue> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.showcase.v1beta1.Issue getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

