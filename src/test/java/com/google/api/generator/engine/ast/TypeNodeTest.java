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
  @Test
  public void equals_basic() {
    assertFalse(TypeNode.DOUBLE.equals(TypeKind.DOUBLE));
    assertFalse(TypeNode.INT.equals(TypeNode.BOOLEAN));
    assertFalse(TypeNode.CHAR.equals(TypeNode.NULL));
  }

  @Test
  public void equals_primitiveBoxedType() {
    assertTrue(TypeNode.INT.equals(TypeNode.INT_OBJECT));
    assertTrue(TypeNode.DOUBLE_OBJECT.equals(TypeNode.DOUBLE));
    assertFalse(TypeNode.BOOLEAN_OBJECT.equals(TypeNode.SHORT));
    assertFalse(TypeNode.DOUBLE_OBJECT.equals(TypeNode.BOOLEAN_OBJECT));
    assertFalse(TypeNode.DOUBLE.equals(TypeNode.FLOAT));
  }

  @Test
  public void equals_arrayType() {
    TypeNode intArray = TypeNode.builder().setTypeKind(TypeKind.INT).setIsArray(true).build();
    TypeNode booleanArray =
        TypeNode.builder().setTypeKind(TypeKind.BOOLEAN).setIsArray(true).build();
    assertFalse(TypeNode.INT.equals(intArray));
    assertFalse(booleanArray.equals(intArray));
  }

  @Test
  public void equals_referenceType() {
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
    assertFalse(intList.equals(list));
    assertFalse(intList.equals(charList));
  }
}
