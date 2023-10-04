// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema/google/showcase/v1beta1/testing.proto

package com.google.showcase.v1beta1;

/**
 * <pre>
 * A TestRun is the result of running a Test.
 * </pre>
 *
 * Protobuf type {@code google.showcase.v1beta1.TestRun}
 */
public final class TestRun extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:google.showcase.v1beta1.TestRun)
    TestRunOrBuilder {
private static final long serialVersionUID = 0L;
  // Use TestRun.newBuilder() to construct.
  private TestRun(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private TestRun() {
    test_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new TestRun();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.google.showcase.v1beta1.TestingOuterClass.internal_static_google_showcase_v1beta1_TestRun_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.showcase.v1beta1.TestingOuterClass.internal_static_google_showcase_v1beta1_TestRun_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.showcase.v1beta1.TestRun.class, com.google.showcase.v1beta1.TestRun.Builder.class);
  }

  private int bitField0_;
  public static final int TEST_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object test_ = "";
  /**
   * <pre>
   * The name of the test.
   * The tests/&#42; portion of the names are hard-coded, and do not change
   * from session to session.
   * </pre>
   *
   * <code>string test = 1 [(.google.api.resource_reference) = { ... }</code>
   * @return The test.
   */
  @java.lang.Override
  public java.lang.String getTest() {
    java.lang.Object ref = test_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      test_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * The name of the test.
   * The tests/&#42; portion of the names are hard-coded, and do not change
   * from session to session.
   * </pre>
   *
   * <code>string test = 1 [(.google.api.resource_reference) = { ... }</code>
   * @return The bytes for test.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getTestBytes() {
    java.lang.Object ref = test_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      test_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ISSUE_FIELD_NUMBER = 2;
  private com.google.showcase.v1beta1.Issue issue_;
  /**
   * <pre>
   * An issue found with the test run. If empty, this test run was successful.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Issue issue = 2;</code>
   * @return Whether the issue field is set.
   */
  @java.lang.Override
  public boolean hasIssue() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <pre>
   * An issue found with the test run. If empty, this test run was successful.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Issue issue = 2;</code>
   * @return The issue.
   */
  @java.lang.Override
  public com.google.showcase.v1beta1.Issue getIssue() {
    return issue_ == null ? com.google.showcase.v1beta1.Issue.getDefaultInstance() : issue_;
  }
  /**
   * <pre>
   * An issue found with the test run. If empty, this test run was successful.
   * </pre>
   *
   * <code>.google.showcase.v1beta1.Issue issue = 2;</code>
   */
  @java.lang.Override
  public com.google.showcase.v1beta1.IssueOrBuilder getIssueOrBuilder() {
    return issue_ == null ? com.google.showcase.v1beta1.Issue.getDefaultInstance() : issue_;
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
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(test_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, test_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(2, getIssue());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(test_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, test_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getIssue());
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
    if (!(obj instanceof com.google.showcase.v1beta1.TestRun)) {
      return super.equals(obj);
    }
    com.google.showcase.v1beta1.TestRun other = (com.google.showcase.v1beta1.TestRun) obj;

    if (!getTest()
        .equals(other.getTest())) return false;
    if (hasIssue() != other.hasIssue()) return false;
    if (hasIssue()) {
      if (!getIssue()
          .equals(other.getIssue())) return false;
    }
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
    hash = (37 * hash) + TEST_FIELD_NUMBER;
    hash = (53 * hash) + getTest().hashCode();
    if (hasIssue()) {
      hash = (37 * hash) + ISSUE_FIELD_NUMBER;
      hash = (53 * hash) + getIssue().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.showcase.v1beta1.TestRun parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.TestRun parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.TestRun parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.TestRun parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.TestRun parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.showcase.v1beta1.TestRun parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.TestRun parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.showcase.v1beta1.TestRun parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static com.google.showcase.v1beta1.TestRun parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.showcase.v1beta1.TestRun parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.showcase.v1beta1.TestRun parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.showcase.v1beta1.TestRun parseFrom(
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
  public static Builder newBuilder(com.google.showcase.v1beta1.TestRun prototype) {
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
   * A TestRun is the result of running a Test.
   * </pre>
   *
   * Protobuf type {@code google.showcase.v1beta1.TestRun}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:google.showcase.v1beta1.TestRun)
      com.google.showcase.v1beta1.TestRunOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.google.showcase.v1beta1.TestingOuterClass.internal_static_google_showcase_v1beta1_TestRun_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.showcase.v1beta1.TestingOuterClass.internal_static_google_showcase_v1beta1_TestRun_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.showcase.v1beta1.TestRun.class, com.google.showcase.v1beta1.TestRun.Builder.class);
    }

    // Construct using com.google.showcase.v1beta1.TestRun.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getIssueFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      test_ = "";
      issue_ = null;
      if (issueBuilder_ != null) {
        issueBuilder_.dispose();
        issueBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.google.showcase.v1beta1.TestingOuterClass.internal_static_google_showcase_v1beta1_TestRun_descriptor;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.TestRun getDefaultInstanceForType() {
      return com.google.showcase.v1beta1.TestRun.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.TestRun build() {
      com.google.showcase.v1beta1.TestRun result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.showcase.v1beta1.TestRun buildPartial() {
      com.google.showcase.v1beta1.TestRun result = new com.google.showcase.v1beta1.TestRun(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.showcase.v1beta1.TestRun result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.test_ = test_;
      }
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.issue_ = issueBuilder_ == null
            ? issue_
            : issueBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      result.bitField0_ |= to_bitField0_;
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
      if (other instanceof com.google.showcase.v1beta1.TestRun) {
        return mergeFrom((com.google.showcase.v1beta1.TestRun)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.showcase.v1beta1.TestRun other) {
      if (other == com.google.showcase.v1beta1.TestRun.getDefaultInstance()) return this;
      if (!other.getTest().isEmpty()) {
        test_ = other.test_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (other.hasIssue()) {
        mergeIssue(other.getIssue());
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
            case 10: {
              test_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              input.readMessage(
                  getIssueFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000002;
              break;
            } // case 18
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

    private java.lang.Object test_ = "";
    /**
     * <pre>
     * The name of the test.
     * The tests/&#42; portion of the names are hard-coded, and do not change
     * from session to session.
     * </pre>
     *
     * <code>string test = 1 [(.google.api.resource_reference) = { ... }</code>
     * @return The test.
     */
    public java.lang.String getTest() {
      java.lang.Object ref = test_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        test_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * The name of the test.
     * The tests/&#42; portion of the names are hard-coded, and do not change
     * from session to session.
     * </pre>
     *
     * <code>string test = 1 [(.google.api.resource_reference) = { ... }</code>
     * @return The bytes for test.
     */
    public com.google.protobuf.ByteString
        getTestBytes() {
      java.lang.Object ref = test_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        test_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * The name of the test.
     * The tests/&#42; portion of the names are hard-coded, and do not change
     * from session to session.
     * </pre>
     *
     * <code>string test = 1 [(.google.api.resource_reference) = { ... }</code>
     * @param value The test to set.
     * @return This builder for chaining.
     */
    public Builder setTest(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      test_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The name of the test.
     * The tests/&#42; portion of the names are hard-coded, and do not change
     * from session to session.
     * </pre>
     *
     * <code>string test = 1 [(.google.api.resource_reference) = { ... }</code>
     * @return This builder for chaining.
     */
    public Builder clearTest() {
      test_ = getDefaultInstance().getTest();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * The name of the test.
     * The tests/&#42; portion of the names are hard-coded, and do not change
     * from session to session.
     * </pre>
     *
     * <code>string test = 1 [(.google.api.resource_reference) = { ... }</code>
     * @param value The bytes for test to set.
     * @return This builder for chaining.
     */
    public Builder setTestBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      test_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private com.google.showcase.v1beta1.Issue issue_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.showcase.v1beta1.Issue, com.google.showcase.v1beta1.Issue.Builder, com.google.showcase.v1beta1.IssueOrBuilder> issueBuilder_;
    /**
     * <pre>
     * An issue found with the test run. If empty, this test run was successful.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue issue = 2;</code>
     * @return Whether the issue field is set.
     */
    public boolean hasIssue() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <pre>
     * An issue found with the test run. If empty, this test run was successful.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue issue = 2;</code>
     * @return The issue.
     */
    public com.google.showcase.v1beta1.Issue getIssue() {
      if (issueBuilder_ == null) {
        return issue_ == null ? com.google.showcase.v1beta1.Issue.getDefaultInstance() : issue_;
      } else {
        return issueBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * An issue found with the test run. If empty, this test run was successful.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue issue = 2;</code>
     */
    public Builder setIssue(com.google.showcase.v1beta1.Issue value) {
      if (issueBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        issue_ = value;
      } else {
        issueBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * An issue found with the test run. If empty, this test run was successful.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue issue = 2;</code>
     */
    public Builder setIssue(
        com.google.showcase.v1beta1.Issue.Builder builderForValue) {
      if (issueBuilder_ == null) {
        issue_ = builderForValue.build();
      } else {
        issueBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * An issue found with the test run. If empty, this test run was successful.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue issue = 2;</code>
     */
    public Builder mergeIssue(com.google.showcase.v1beta1.Issue value) {
      if (issueBuilder_ == null) {
        if (((bitField0_ & 0x00000002) != 0) &&
          issue_ != null &&
          issue_ != com.google.showcase.v1beta1.Issue.getDefaultInstance()) {
          getIssueBuilder().mergeFrom(value);
        } else {
          issue_ = value;
        }
      } else {
        issueBuilder_.mergeFrom(value);
      }
      if (issue_ != null) {
        bitField0_ |= 0x00000002;
        onChanged();
      }
      return this;
    }
    /**
     * <pre>
     * An issue found with the test run. If empty, this test run was successful.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue issue = 2;</code>
     */
    public Builder clearIssue() {
      bitField0_ = (bitField0_ & ~0x00000002);
      issue_ = null;
      if (issueBuilder_ != null) {
        issueBuilder_.dispose();
        issueBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <pre>
     * An issue found with the test run. If empty, this test run was successful.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue issue = 2;</code>
     */
    public com.google.showcase.v1beta1.Issue.Builder getIssueBuilder() {
      bitField0_ |= 0x00000002;
      onChanged();
      return getIssueFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * An issue found with the test run. If empty, this test run was successful.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue issue = 2;</code>
     */
    public com.google.showcase.v1beta1.IssueOrBuilder getIssueOrBuilder() {
      if (issueBuilder_ != null) {
        return issueBuilder_.getMessageOrBuilder();
      } else {
        return issue_ == null ?
            com.google.showcase.v1beta1.Issue.getDefaultInstance() : issue_;
      }
    }
    /**
     * <pre>
     * An issue found with the test run. If empty, this test run was successful.
     * </pre>
     *
     * <code>.google.showcase.v1beta1.Issue issue = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.showcase.v1beta1.Issue, com.google.showcase.v1beta1.Issue.Builder, com.google.showcase.v1beta1.IssueOrBuilder> 
        getIssueFieldBuilder() {
      if (issueBuilder_ == null) {
        issueBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.showcase.v1beta1.Issue, com.google.showcase.v1beta1.Issue.Builder, com.google.showcase.v1beta1.IssueOrBuilder>(
                getIssue(),
                getParentForChildren(),
                isClean());
        issue_ = null;
      }
      return issueBuilder_;
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


    // @@protoc_insertion_point(builder_scope:google.showcase.v1beta1.TestRun)
  }

  // @@protoc_insertion_point(class_scope:google.showcase.v1beta1.TestRun)
  private static final com.google.showcase.v1beta1.TestRun DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.google.showcase.v1beta1.TestRun();
  }

  public static com.google.showcase.v1beta1.TestRun getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<TestRun>
      PARSER = new com.google.protobuf.AbstractParser<TestRun>() {
    @java.lang.Override
    public TestRun parsePartialFrom(
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

  public static com.google.protobuf.Parser<TestRun> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<TestRun> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.showcase.v1beta1.TestRun getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

