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

import static com.google.common.truth.Truth.assertThat;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class ReferenceTest {
  @Test
  public void basicReference() {
    Reference reference = Reference.builder().setClazz(Integer.class).build();
    assertEquals(reference.name(), Integer.class.getSimpleName());
  }

  @Test
  public void parameterizedReference() {
    Reference reference =
        Reference.builder()
            .setClazz(HashMap.class)
            .setGenerics(
                Arrays.asList(
                    Reference.withClazz(String.class), Reference.withClazz(Integer.class)))
            .build();
    assertEquals(reference.name(), "HashMap<String, Integer>");
  }

  @Test
  public void nestedParameterizedReference() {
    Reference mapReference =
        Reference.builder()
            .setClazz(HashMap.class)
            .setGenerics(
                Arrays.asList(
                    Reference.withClazz(String.class), Reference.withClazz(Integer.class)))
            .build();
    Reference outerMapReference =
        Reference.builder()
            .setClazz(HashMap.class)
            .setGenerics(Arrays.asList(mapReference, mapReference))
            .build();
    Reference listReference =
        Reference.builder()
            .setClazz(List.class)
            .setGenerics(Arrays.asList(outerMapReference))
            .build();
    assertEquals(
        listReference.name(), "List<HashMap<HashMap<String, Integer>, HashMap<String, Integer>>>");
  }

  @Test
  public void isSupertype_basic() {
    assertTrue(TypeNode.STRING.isSupertypeOrEquals(TypeNode.STRING));
    assertFalse(TypeNode.INT.isSupertypeOrEquals(TypeNode.STRING));
    assertFalse(TypeNode.STRING.isSupertypeOrEquals(TypeNode.INT));
    assertFalse(TypeNode.INT.isSupertypeOrEquals(TypeNode.INT));

    TypeNode mapType = TypeNode.withReference(Reference.withClazz(Map.class));
    TypeNode hashMapType = TypeNode.withReference(Reference.withClazz(HashMap.class));
    assertTrue(mapType.isSupertypeOrEquals(hashMapType));
    assertFalse(hashMapType.isSupertypeOrEquals(mapType));
  }

  @Test
  public void isSupertype_nestedGenerics() {
    Reference stringRef = Reference.withClazz(String.class);
    TypeNode typeOne =
        TypeNode.withReference(
            Reference.builder()
                .setClazz(Map.class)
                .setGenerics(
                    Arrays.asList(
                        stringRef,
                        Reference.builder()
                            .setClazz(List.class)
                            .setGenerics(Arrays.asList(Reference.withClazz(Expr.class)))
                            .build()))
                .build());
    TypeNode typeTwo =
        TypeNode.withReference(
            Reference.builder()
                .setClazz(HashMap.class)
                .setGenerics(
                    Arrays.asList(
                        stringRef,
                        Reference.builder()
                            .setClazz(ArrayList.class)
                            .setGenerics(Arrays.asList(Reference.withClazz(ValueExpr.class)))
                            .build()))
                .build());
    assertTrue(typeOne.isSupertypeOrEquals(typeOne));
    assertTrue(typeOne.isSupertypeOrEquals(typeTwo));
    assertFalse(typeTwo.isSupertypeOrEquals(typeOne));
  }
}
