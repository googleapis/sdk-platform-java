// Copyright 2021 Google LLC
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

package com.google.api.generator.gapic.model;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.junit.Test;

public class MessageTest {

  public static final String MESSAGE_NAME = "TestMessage";
  public static final Message.Builder TEST_MESSAGE_BUILDER =
      Message.builder()
          .setName(MESSAGE_NAME)
          .setFullProtoName("com.google.test.TestMessage")
          .setType(TypeNode.OBJECT);

  @Test
  public void validateField_shouldThrowExceptionIfFieldNameIsEmpty() {
    Message message = TEST_MESSAGE_BUILDER.build();
    IllegalStateException illegalStateException =
        assertThrows(
            IllegalStateException.class, () -> message.validateField("", ImmutableMap.of()));
    assertThat(illegalStateException.getMessage())
        .isEqualTo(String.format("Null or empty field name found for message %s", MESSAGE_NAME));
  }

  @Test
  public void validateField_shouldThrowExceptionIfFieldDoesNotExist() {
    Message message = TEST_MESSAGE_BUILDER.build();
    String fieldName = "doesNotExist";
    IllegalStateException illegalStateException =
        assertThrows(
            IllegalStateException.class, () -> message.validateField(fieldName, ImmutableMap.of()));
    assertThat(illegalStateException.getMessage())
        .isEqualTo(
            String.format(
                "Expected message %s to contain field %s but none found", MESSAGE_NAME, fieldName));
  }

  @Test
  public void validateField_shouldThrowExceptionIfMessageDoesNotExist() {
    String subFieldName = "table";
    String fieldTypeName = "doesNotMatter";
    Field subField =
        Field.builder()
            .setName(subFieldName)
            .setType(
                TypeNode.withReference(
                    VaporReference.builder()
                        .setPakkage("com.google")
                        .setName(fieldTypeName)
                        .build()))
            .build();
    Message message =
        TEST_MESSAGE_BUILDER.setFieldMap(ImmutableMap.of(subFieldName, subField)).build();
    String fieldName = subFieldName + "." + "size";
    NullPointerException nullPointerException =
        assertThrows(
            NullPointerException.class, () -> message.validateField(fieldName, ImmutableMap.of()));
    assertThat(nullPointerException.getMessage())
        .isEqualTo(
            String.format(
                "No containing message found for field %s with type %s",
                subFieldName, fieldTypeName));
  }

  @Test
  public void validateField_shouldNotThrowExceptionIfFieldExist() {
    String subFieldName = "table";
    String fieldTypeName = "TableFieldType";
    VaporReference fieldType =
        VaporReference.builder().setPakkage("com.google").setName(fieldTypeName).build();
    Field subField =
        Field.builder().setName(subFieldName).setType(TypeNode.withReference(fieldType)).build();
    String subFieldName2 = "size";
    String fieldName = subFieldName + "." + subFieldName2;
    Message subMessage =
        Message.builder()
            .setName(fieldTypeName)
            .setFullProtoName("com.google." + fieldTypeName)
            .setType(TypeNode.OBJECT)
            .setFieldMap(
                ImmutableMap.of(
                    subFieldName2,
                    Field.builder().setType(TypeNode.STRING).setName(subFieldName2).build()))
            .build();
    Map<String, Message> messageTypes = ImmutableMap.of(fieldType.fullName(), subMessage);
    Message message =
        TEST_MESSAGE_BUILDER.setFieldMap(ImmutableMap.of(subFieldName, subField)).build();
    message.validateField(fieldName, messageTypes);
  }
}
