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
// source: google/type/quaternion.proto

// Protobuf Java Version: 3.25.5
package com.google.type;

/**
 *
 *
 * <pre>
 * A quaternion is defined as the quotient of two directed lines in a
 * three-dimensional space or equivalently as the quotient of two Euclidean
 * vectors (https://en.wikipedia.org/wiki/Quaternion).
 *
 * Quaternions are often used in calculations involving three-dimensional
 * rotations (https://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation),
 * as they provide greater mathematical robustness by avoiding the gimbal lock
 * problems that can be encountered when using Euler angles
 * (https://en.wikipedia.org/wiki/Gimbal_lock).
 *
 * Quaternions are generally represented in this form:
 *
 *     w + xi + yj + zk
 *
 * where x, y, z, and w are real numbers, and i, j, and k are three imaginary
 * numbers.
 *
 * Our naming choice `(x, y, z, w)` comes from the desire to avoid confusion for
 * those interested in the geometric properties of the quaternion in the 3D
 * Cartesian space. Other texts often use alternative names or subscripts, such
 * as `(a, b, c, d)`, `(1, i, j, k)`, or `(0, 1, 2, 3)`, which are perhaps
 * better suited for mathematical interpretations.
 *
 * To avoid any confusion, as well as to maintain compatibility with a large
 * number of software libraries, the quaternions represented using the protocol
 * buffer below *must* follow the Hamilton convention, which defines `ij = k`
 * (i.e. a right-handed algebra), and therefore:
 *
 *     i^2 = j^2 = k^2 = ijk = −1
 *     ij = −ji = k
 *     jk = −kj = i
 *     ki = −ik = j
 *
 * Please DO NOT use this to represent quaternions that follow the JPL
 * convention, or any of the other quaternion flavors out there.
 *
 * Definitions:
 *
 *   - Quaternion norm (or magnitude): `sqrt(x^2 + y^2 + z^2 + w^2)`.
 *   - Unit (or normalized) quaternion: a quaternion whose norm is 1.
 *   - Pure quaternion: a quaternion whose scalar component (`w`) is 0.
 *   - Rotation quaternion: a unit quaternion used to represent rotation.
 *   - Orientation quaternion: a unit quaternion used to represent orientation.
 *
 * A quaternion can be normalized by dividing it by its norm. The resulting
 * quaternion maintains the same direction, but has a norm of 1, i.e. it moves
 * on the unit sphere. This is generally necessary for rotation and orientation
 * quaternions, to avoid rounding errors:
 * https://en.wikipedia.org/wiki/Rotation_formalisms_in_three_dimensions
 *
 * Note that `(x, y, z, w)` and `(-x, -y, -z, -w)` represent the same rotation,
 * but normalization would be even more useful, e.g. for comparison purposes, if
 * it would produce a unique representation. It is thus recommended that `w` be
 * kept positive, which can be achieved by changing all the signs when `w` is
 * negative.
 * </pre>
 *
 * Protobuf type {@code google.type.Quaternion}
 */
public final class Quaternion extends com.google.protobuf.GeneratedMessageV3
    implements
    // @@protoc_insertion_point(message_implements:google.type.Quaternion)
    QuaternionOrBuilder {
  private static final long serialVersionUID = 0L;

  // Use Quaternion.newBuilder() to construct.
  private Quaternion(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }

  private Quaternion() {}

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
    return new Quaternion();
  }

  public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
    return com.google.type.QuaternionProto.internal_static_google_type_Quaternion_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.type.QuaternionProto.internal_static_google_type_Quaternion_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.type.Quaternion.class, com.google.type.Quaternion.Builder.class);
  }

  public static final int X_FIELD_NUMBER = 1;
  private double x_ = 0D;

  /**
   *
   *
   * <pre>
   * The x component.
   * </pre>
   *
   * <code>double x = 1;</code>
   *
   * @return The x.
   */
  @java.lang.Override
  public double getX() {
    return x_;
  }

  public static final int Y_FIELD_NUMBER = 2;
  private double y_ = 0D;

  /**
   *
   *
   * <pre>
   * The y component.
   * </pre>
   *
   * <code>double y = 2;</code>
   *
   * @return The y.
   */
  @java.lang.Override
  public double getY() {
    return y_;
  }

  public static final int Z_FIELD_NUMBER = 3;
  private double z_ = 0D;

  /**
   *
   *
   * <pre>
   * The z component.
   * </pre>
   *
   * <code>double z = 3;</code>
   *
   * @return The z.
   */
  @java.lang.Override
  public double getZ() {
    return z_;
  }

  public static final int W_FIELD_NUMBER = 4;
  private double w_ = 0D;

  /**
   *
   *
   * <pre>
   * The scalar component.
   * </pre>
   *
   * <code>double w = 4;</code>
   *
   * @return The w.
   */
  @java.lang.Override
  public double getW() {
    return w_;
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
    if (java.lang.Double.doubleToRawLongBits(x_) != 0) {
      output.writeDouble(1, x_);
    }
    if (java.lang.Double.doubleToRawLongBits(y_) != 0) {
      output.writeDouble(2, y_);
    }
    if (java.lang.Double.doubleToRawLongBits(z_) != 0) {
      output.writeDouble(3, z_);
    }
    if (java.lang.Double.doubleToRawLongBits(w_) != 0) {
      output.writeDouble(4, w_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (java.lang.Double.doubleToRawLongBits(x_) != 0) {
      size += com.google.protobuf.CodedOutputStream.computeDoubleSize(1, x_);
    }
    if (java.lang.Double.doubleToRawLongBits(y_) != 0) {
      size += com.google.protobuf.CodedOutputStream.computeDoubleSize(2, y_);
    }
    if (java.lang.Double.doubleToRawLongBits(z_) != 0) {
      size += com.google.protobuf.CodedOutputStream.computeDoubleSize(3, z_);
    }
    if (java.lang.Double.doubleToRawLongBits(w_) != 0) {
      size += com.google.protobuf.CodedOutputStream.computeDoubleSize(4, w_);
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
    if (!(obj instanceof com.google.type.Quaternion)) {
      return super.equals(obj);
    }
    com.google.type.Quaternion other = (com.google.type.Quaternion) obj;

    if (java.lang.Double.doubleToLongBits(getX())
        != java.lang.Double.doubleToLongBits(other.getX())) return false;
    if (java.lang.Double.doubleToLongBits(getY())
        != java.lang.Double.doubleToLongBits(other.getY())) return false;
    if (java.lang.Double.doubleToLongBits(getZ())
        != java.lang.Double.doubleToLongBits(other.getZ())) return false;
    if (java.lang.Double.doubleToLongBits(getW())
        != java.lang.Double.doubleToLongBits(other.getW())) return false;
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
    hash = (37 * hash) + X_FIELD_NUMBER;
    hash =
        (53 * hash)
            + com.google.protobuf.Internal.hashLong(java.lang.Double.doubleToLongBits(getX()));
    hash = (37 * hash) + Y_FIELD_NUMBER;
    hash =
        (53 * hash)
            + com.google.protobuf.Internal.hashLong(java.lang.Double.doubleToLongBits(getY()));
    hash = (37 * hash) + Z_FIELD_NUMBER;
    hash =
        (53 * hash)
            + com.google.protobuf.Internal.hashLong(java.lang.Double.doubleToLongBits(getZ()));
    hash = (37 * hash) + W_FIELD_NUMBER;
    hash =
        (53 * hash)
            + com.google.protobuf.Internal.hashLong(java.lang.Double.doubleToLongBits(getW()));
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.type.Quaternion parseFrom(java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.type.Quaternion parseFrom(
      java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.type.Quaternion parseFrom(com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.type.Quaternion parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.type.Quaternion parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }

  public static com.google.type.Quaternion parseFrom(
      byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }

  public static com.google.type.Quaternion parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.type.Quaternion parseFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.type.Quaternion parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
  }

  public static com.google.type.Quaternion parseDelimitedFrom(
      java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
        PARSER, input, extensionRegistry);
  }

  public static com.google.type.Quaternion parseFrom(com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
  }

  public static com.google.type.Quaternion parseFrom(
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

  public static Builder newBuilder(com.google.type.Quaternion prototype) {
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
   * A quaternion is defined as the quotient of two directed lines in a
   * three-dimensional space or equivalently as the quotient of two Euclidean
   * vectors (https://en.wikipedia.org/wiki/Quaternion).
   *
   * Quaternions are often used in calculations involving three-dimensional
   * rotations (https://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation),
   * as they provide greater mathematical robustness by avoiding the gimbal lock
   * problems that can be encountered when using Euler angles
   * (https://en.wikipedia.org/wiki/Gimbal_lock).
   *
   * Quaternions are generally represented in this form:
   *
   *     w + xi + yj + zk
   *
   * where x, y, z, and w are real numbers, and i, j, and k are three imaginary
   * numbers.
   *
   * Our naming choice `(x, y, z, w)` comes from the desire to avoid confusion for
   * those interested in the geometric properties of the quaternion in the 3D
   * Cartesian space. Other texts often use alternative names or subscripts, such
   * as `(a, b, c, d)`, `(1, i, j, k)`, or `(0, 1, 2, 3)`, which are perhaps
   * better suited for mathematical interpretations.
   *
   * To avoid any confusion, as well as to maintain compatibility with a large
   * number of software libraries, the quaternions represented using the protocol
   * buffer below *must* follow the Hamilton convention, which defines `ij = k`
   * (i.e. a right-handed algebra), and therefore:
   *
   *     i^2 = j^2 = k^2 = ijk = −1
   *     ij = −ji = k
   *     jk = −kj = i
   *     ki = −ik = j
   *
   * Please DO NOT use this to represent quaternions that follow the JPL
   * convention, or any of the other quaternion flavors out there.
   *
   * Definitions:
   *
   *   - Quaternion norm (or magnitude): `sqrt(x^2 + y^2 + z^2 + w^2)`.
   *   - Unit (or normalized) quaternion: a quaternion whose norm is 1.
   *   - Pure quaternion: a quaternion whose scalar component (`w`) is 0.
   *   - Rotation quaternion: a unit quaternion used to represent rotation.
   *   - Orientation quaternion: a unit quaternion used to represent orientation.
   *
   * A quaternion can be normalized by dividing it by its norm. The resulting
   * quaternion maintains the same direction, but has a norm of 1, i.e. it moves
   * on the unit sphere. This is generally necessary for rotation and orientation
   * quaternions, to avoid rounding errors:
   * https://en.wikipedia.org/wiki/Rotation_formalisms_in_three_dimensions
   *
   * Note that `(x, y, z, w)` and `(-x, -y, -z, -w)` represent the same rotation,
   * but normalization would be even more useful, e.g. for comparison purposes, if
   * it would produce a unique representation. It is thus recommended that `w` be
   * kept positive, which can be achieved by changing all the signs when `w` is
   * negative.
   * </pre>
   *
   * Protobuf type {@code google.type.Quaternion}
   */
  public static final class Builder extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
      implements
      // @@protoc_insertion_point(builder_implements:google.type.Quaternion)
      com.google.type.QuaternionOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
      return com.google.type.QuaternionProto.internal_static_google_type_Quaternion_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.type.QuaternionProto
          .internal_static_google_type_Quaternion_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.type.Quaternion.class, com.google.type.Quaternion.Builder.class);
    }

    // Construct using com.google.type.Quaternion.newBuilder()
    private Builder() {}

    private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
    }

    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      x_ = 0D;
      y_ = 0D;
      z_ = 0D;
      w_ = 0D;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
      return com.google.type.QuaternionProto.internal_static_google_type_Quaternion_descriptor;
    }

    @java.lang.Override
    public com.google.type.Quaternion getDefaultInstanceForType() {
      return com.google.type.Quaternion.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.type.Quaternion build() {
      com.google.type.Quaternion result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.type.Quaternion buildPartial() {
      com.google.type.Quaternion result = new com.google.type.Quaternion(this);
      if (bitField0_ != 0) {
        buildPartial0(result);
      }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.google.type.Quaternion result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.x_ = x_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.y_ = y_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.z_ = z_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.w_ = w_;
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
      if (other instanceof com.google.type.Quaternion) {
        return mergeFrom((com.google.type.Quaternion) other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.type.Quaternion other) {
      if (other == com.google.type.Quaternion.getDefaultInstance()) return this;
      if (other.getX() != 0D) {
        setX(other.getX());
      }
      if (other.getY() != 0D) {
        setY(other.getY());
      }
      if (other.getZ() != 0D) {
        setZ(other.getZ());
      }
      if (other.getW() != 0D) {
        setW(other.getW());
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
            case 9:
              {
                x_ = input.readDouble();
                bitField0_ |= 0x00000001;
                break;
              } // case 9
            case 17:
              {
                y_ = input.readDouble();
                bitField0_ |= 0x00000002;
                break;
              } // case 17
            case 25:
              {
                z_ = input.readDouble();
                bitField0_ |= 0x00000004;
                break;
              } // case 25
            case 33:
              {
                w_ = input.readDouble();
                bitField0_ |= 0x00000008;
                break;
              } // case 33
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

    private double x_;

    /**
     *
     *
     * <pre>
     * The x component.
     * </pre>
     *
     * <code>double x = 1;</code>
     *
     * @return The x.
     */
    @java.lang.Override
    public double getX() {
      return x_;
    }

    /**
     *
     *
     * <pre>
     * The x component.
     * </pre>
     *
     * <code>double x = 1;</code>
     *
     * @param value The x to set.
     * @return This builder for chaining.
     */
    public Builder setX(double value) {

      x_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The x component.
     * </pre>
     *
     * <code>double x = 1;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearX() {
      bitField0_ = (bitField0_ & ~0x00000001);
      x_ = 0D;
      onChanged();
      return this;
    }

    private double y_;

    /**
     *
     *
     * <pre>
     * The y component.
     * </pre>
     *
     * <code>double y = 2;</code>
     *
     * @return The y.
     */
    @java.lang.Override
    public double getY() {
      return y_;
    }

    /**
     *
     *
     * <pre>
     * The y component.
     * </pre>
     *
     * <code>double y = 2;</code>
     *
     * @param value The y to set.
     * @return This builder for chaining.
     */
    public Builder setY(double value) {

      y_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The y component.
     * </pre>
     *
     * <code>double y = 2;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearY() {
      bitField0_ = (bitField0_ & ~0x00000002);
      y_ = 0D;
      onChanged();
      return this;
    }

    private double z_;

    /**
     *
     *
     * <pre>
     * The z component.
     * </pre>
     *
     * <code>double z = 3;</code>
     *
     * @return The z.
     */
    @java.lang.Override
    public double getZ() {
      return z_;
    }

    /**
     *
     *
     * <pre>
     * The z component.
     * </pre>
     *
     * <code>double z = 3;</code>
     *
     * @param value The z to set.
     * @return This builder for chaining.
     */
    public Builder setZ(double value) {

      z_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The z component.
     * </pre>
     *
     * <code>double z = 3;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearZ() {
      bitField0_ = (bitField0_ & ~0x00000004);
      z_ = 0D;
      onChanged();
      return this;
    }

    private double w_;

    /**
     *
     *
     * <pre>
     * The scalar component.
     * </pre>
     *
     * <code>double w = 4;</code>
     *
     * @return The w.
     */
    @java.lang.Override
    public double getW() {
      return w_;
    }

    /**
     *
     *
     * <pre>
     * The scalar component.
     * </pre>
     *
     * <code>double w = 4;</code>
     *
     * @param value The w to set.
     * @return This builder for chaining.
     */
    public Builder setW(double value) {

      w_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }

    /**
     *
     *
     * <pre>
     * The scalar component.
     * </pre>
     *
     * <code>double w = 4;</code>
     *
     * @return This builder for chaining.
     */
    public Builder clearW() {
      bitField0_ = (bitField0_ & ~0x00000008);
      w_ = 0D;
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

    // @@protoc_insertion_point(builder_scope:google.type.Quaternion)
  }

  // @@protoc_insertion_point(class_scope:google.type.Quaternion)
  private static final com.google.type.Quaternion DEFAULT_INSTANCE;

  static {
    DEFAULT_INSTANCE = new com.google.type.Quaternion();
  }

  public static com.google.type.Quaternion getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Quaternion> PARSER =
      new com.google.protobuf.AbstractParser<Quaternion>() {
        @java.lang.Override
        public Quaternion parsePartialFrom(
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

  public static com.google.protobuf.Parser<Quaternion> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Quaternion> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.type.Quaternion getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }
}
