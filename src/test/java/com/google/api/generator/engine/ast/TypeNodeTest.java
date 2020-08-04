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

package com.google.api.generator.engine.ast;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import com.google.api.generator.engine.ast.TypeNode.TypeKind;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class TypeNodeTest {
  private static final TypeNode INT_ARRAY =
      TypeNode.builder().setTypeKind(TypeKind.INT).setIsArray(true).build();
  private static final TypeNode INTEGER_ARRAY =
      TypeNode.builder()
          .setIsArray(true)
          .setReference(ConcreteReference.withClazz(Integer.class))
          .setTypeKind(TypeKind.OBJECT)
          .build();
  private static final TypeNode BOOLEAN_ARRAY =
      TypeNode.builder().setTypeKind(TypeKind.BOOLEAN).setIsArray(true).build();

  @Test
  public void stricEquals_basic() {
    assertFalse(TypeNode.INT.strictEquals(TypeNode.BOOLEAN));
    assertFalse(TypeNode.CHAR.strictEquals(TypeNode.NULL));
    assertFalse(TypeNode.INT.strictEquals(INT_ARRAY));
    assertTrue(TypeNode.BOOLEAN.strictEquals(TypeNode.BOOLEAN));
  }

  @Test
  public void stricEquals_referenceType() {
    TypeNode list = TypeNode.withReference(ConcreteReference.withClazz(List.class));
    TypeNode intList =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(List.class)
                .setGenerics(Arrays.asList(ConcreteReference.withClazz(Integer.class)))
                .build());
    TypeNode charList =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(List.class)
                .setGenerics(Arrays.asList(ConcreteReference.withClazz(Character.class)))
                .build());
    assertFalse(intList.strictEquals(list));
    assertFalse(intList.strictEquals(charList));
  }

  @Test
  public void isBoxedTypeEquals_basic() {
    assertTrue(TypeNode.INT.isBoxedTypeEquals(TypeNode.INT_OBJECT));
    assertTrue(TypeNode.DOUBLE_OBJECT.isBoxedTypeEquals(TypeNode.DOUBLE));
    assertFalse(TypeNode.BOOLEAN_OBJECT.isBoxedTypeEquals(TypeNode.SHORT));
    assertFalse(TypeNode.DOUBLE_OBJECT.isBoxedTypeEquals(TypeNode.BOOLEAN_OBJECT));
    assertFalse(TypeNode.DOUBLE.isBoxedTypeEquals(TypeNode.FLOAT));
  }

  @Test
  public void isBoxedTypeEquals_arrayType() {
    assertFalse(TypeNode.INT.isBoxedTypeEquals(INT_ARRAY));
    assertFalse(INTEGER_ARRAY.isBoxedTypeEquals(INT_ARRAY));
    assertFalse(BOOLEAN_ARRAY.isBoxedTypeEquals(INT_ARRAY));
  }

  @Test
  public void equals_basic() {
    assertTrue(TypeNode.INT.equals(TypeNode.INT_OBJECT));
    assertTrue(TypeNode.DOUBLE_OBJECT.equals(TypeNode.DOUBLE));
    assertTrue(TypeNode.BOOLEAN.equals(TypeNode.BOOLEAN));
    assertTrue(BOOLEAN_ARRAY.equals(BOOLEAN_ARRAY));
    assertFalse(TypeNode.DOUBLE.equals(TypeKind.DOUBLE));
    assertFalse(TypeNode.INT.equals(TypeNode.BOOLEAN));
    assertFalse(TypeNode.CHAR.equals(TypeNode.NULL));
    assertFalse(INTEGER_ARRAY.equals(INT_ARRAY));
  }
}
