// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.generator.gapic.protoparser;

import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Descriptors.FileDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

public class TypeParser {
  // TODO(miraleung): Add a test.
  private static Reference REFERENCE_BYTE_STRING = ConcreteReference.withClazz(ByteString.class);
  private static TypeNode TYPE_NODE_BYTE_STRING = TypeNode.withReference(REFERENCE_BYTE_STRING);

  private static Map<JavaType, TypeNode> SIMPLE_TYPE_MAP =
      ImmutableMap.<JavaType, TypeNode>builder()
          .put(JavaType.INT, TypeNode.INT)
          .put(JavaType.LONG, TypeNode.LONG)
          .put(JavaType.FLOAT, TypeNode.FLOAT)
          .put(JavaType.DOUBLE, TypeNode.DOUBLE)
          .put(JavaType.STRING, TypeNode.STRING)
          .put(JavaType.BOOLEAN, TypeNode.BOOLEAN)
          .put(JavaType.BYTE_STRING, TYPE_NODE_BYTE_STRING)
          .build();

  private static Map<JavaType, Reference> SIMPLE_REFERENCE_MAP =
      ImmutableMap.<JavaType, Reference>builder()
          .put(JavaType.INT, ConcreteReference.withClazz(Integer.class))
          .put(JavaType.LONG, ConcreteReference.withClazz(Long.class))
          .put(JavaType.FLOAT, ConcreteReference.withClazz(Float.class))
          .put(JavaType.DOUBLE, ConcreteReference.withClazz(Double.class))
          .put(JavaType.STRING, ConcreteReference.withClazz(String.class))
          .put(JavaType.BOOLEAN, ConcreteReference.withClazz(Boolean.class))
          .put(JavaType.BYTE_STRING, REFERENCE_BYTE_STRING)
          .build();

  public static TypeNode parseType(@Nonnull FieldDescriptor field) {
    if (field.isRepeated()) {
      return createListType(field);
    }

    if (field.isMapField()) {
      return createMapType(field);
    }

    // Parse basic type.
    JavaType protoFieldType = field.getJavaType();
    boolean isEnum = protoFieldType.equals(JavaType.ENUM);
    boolean isMessage = protoFieldType.equals(JavaType.MESSAGE);
    if (!isEnum && !isMessage) {
      // Primitive types.
      return SIMPLE_TYPE_MAP.get(protoFieldType);
    }

    return TypeNode.withReference(parseFieldReference(field));
  }

  public static TypeNode parseType(@Nonnull Descriptor messageDescriptor) {
    return TypeNode.withReference(parseMessageReference(messageDescriptor));
  }

  public static String getPackage(FileDescriptor fileDescriptor) {
    String pakkage = fileDescriptor.getOptions().getJavaPackage();
    if (Strings.isNullOrEmpty(pakkage)) {
      pakkage = fileDescriptor.getPackage();
    }
    Preconditions.checkNotNull(
        pakkage, String.format("Java package in file %s was null", fileDescriptor.getName()));
    return pakkage;
  }

  @VisibleForTesting
  static Reference parseFieldReference(FieldDescriptor field) {
    JavaType protoFieldType = field.getJavaType();
    boolean isEnum = protoFieldType.equals(JavaType.ENUM);
    boolean isMessage = protoFieldType.equals(JavaType.MESSAGE);
    if (!isEnum && !isMessage) {
      // Boxed primitive types.
      return SIMPLE_REFERENCE_MAP.get(protoFieldType);
    }

    // Handles enum or messages.
    if (isMessage) {
      return parseMessageReference(field.getMessageType());
    }
    return parseEnumReference(field.getEnumType());
  }

  @VisibleForTesting
  static Reference parseMessageReference(@Nonnull Descriptor messageDescriptor) {
    // TODO(miraleung): Handle deeper levels of nesting.
    String pakkage = getPackage(messageDescriptor.getFile());
    VaporReference.Builder messageReferenceBuilder =
        VaporReference.builder().setName(messageDescriptor.getName()).setPakkage(pakkage);

    boolean isNestedType = messageDescriptor.getContainingType() != null;
    if (isNestedType) {
      String enclosingClassName = messageDescriptor.getContainingType().getName();
      messageReferenceBuilder.setEnclosingClassName(enclosingClassName);
    }
    Reference messageReference = messageReferenceBuilder.build();
    String protoPackage = messageDescriptor.getFile().getPackage();
    Preconditions.checkState(
        messageReference
            .fullName()
            .replace(pakkage, protoPackage)
            .equals(messageDescriptor.getFullName()),
        String.format(
            "Parsed message name %s does not match actual name %s",
            messageReference.fullName().replace(pakkage, ""),
            messageDescriptor.getFullName().replace(protoPackage, "")));
    return messageReference;
  }

  @VisibleForTesting
  static Reference parseEnumReference(@Nonnull EnumDescriptor enumDescriptor) {
    // This is similar to parseMessageReference, but we make it a separate method because
    // EnumDescriptor and Descriptor are sibling types.
    // TODO(miraleung): Handle deeper levels of nesting.
    String pakkage = getPackage(enumDescriptor.getFile());
    VaporReference.Builder enumReferenceBuilder =
        VaporReference.builder().setName(enumDescriptor.getName()).setPakkage(pakkage);

    boolean isNestedType = enumDescriptor.getContainingType() != null;
    if (isNestedType) {
      String enclosingClassName = enumDescriptor.getContainingType().getName();
      enumReferenceBuilder.setEnclosingClassName(enclosingClassName);
    }
    Reference enumReference = enumReferenceBuilder.build();
    String protoPackage = enumDescriptor.getFile().getPackage();
    Preconditions.checkState(
        enumReference
            .fullName()
            .replace(pakkage, protoPackage)
            .equals(enumDescriptor.getFullName()),
        String.format(
            "Parsed enum name %s does not match actual name %s",
            enumReference.fullName().replace(pakkage, ""),
            enumDescriptor.getFullName().replace(protoPackage, "")));
    return enumReference;
  }

  @VisibleForTesting
  static TypeNode createListType(FieldDescriptor field) {
    JavaType protoFieldType = field.getJavaType();

    Reference listReference =
        ConcreteReference.builder()
            .setClazz(List.class)
            .setGenerics(Arrays.asList(parseFieldReference(field)))
            .build();
    return TypeNode.withReference(listReference);
  }

  @VisibleForTesting
  static TypeNode createMapType(FieldDescriptor field) {
    Preconditions.checkState(
        field.isMapField(), "createMapType can only be called on map-type fields");
    JavaType protoJavaType = field.getJavaType();

    Descriptor type = field.getMessageType();
    FieldDescriptor keyField = type.findFieldByName("key");
    FieldDescriptor valueField = type.findFieldByName("value");

    Reference keyReference = parseFieldReference(keyField);
    Reference valueReference = parseFieldReference(valueField);

    Reference mapReference =
        ConcreteReference.builder()
            .setClazz(Map.class)
            .setGenerics(Arrays.asList(keyReference, valueReference))
            .build();
    return TypeNode.withReference(mapReference);
  }
}
