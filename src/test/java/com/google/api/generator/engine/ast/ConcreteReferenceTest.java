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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class ConcreteReferenceTest {
  @Test
  public void basicConcreteReference() {
    Reference reference = ConcreteReference.builder().setClazz(Integer.class).build();
    assertEquals(reference.name(), Integer.class.getSimpleName());
    assertFalse(reference.isStaticImport());
  }

  @Test
  public void basicConcreteReference_setIsStaticImport() {
    Reference reference =
        ConcreteReference.builder().setClazz(Integer.class).setIsStaticImport(true).build();
    assertEquals(reference.name(), Integer.class.getSimpleName());
    assertFalse(reference.isStaticImport());
  }

  @Test
  public void basicConcreteReference_nested() {
    Reference reference = ConcreteReference.builder().setClazz(Map.Entry.class).build();
    assertEquals(reference.name(), "Map.Entry");
    assertFalse(reference.isStaticImport());
  }

  @Test
  public void basicConcreteReference_nestedAndStaticImport() {
    Reference reference =
        ConcreteReference.builder().setClazz(Map.Entry.class).setIsStaticImport(true).build();
    assertEquals(reference.name(), Map.Entry.class.getSimpleName());
    assertTrue(reference.isStaticImport());
  }

  @Test
  public void parameterizedConcreteReference() {
    Reference reference =
        ConcreteReference.builder()
            .setClazz(HashMap.class)
            .setGenerics(
                Arrays.asList(
                    ConcreteReference.withClazz(String.class),
                    ConcreteReference.withClazz(Integer.class)))
            .build();
    assertEquals(reference.name(), "HashMap<String, Integer>");
    assertEquals(reference.fullName(), "java.util.HashMap");
  }

  @Test
  public void nestedParameterizedConcreteReference() {
    Reference mapReference =
        ConcreteReference.builder()
            .setClazz(HashMap.class)
            .setGenerics(
                Arrays.asList(
                    ConcreteReference.withClazz(String.class),
                    ConcreteReference.withClazz(Integer.class)))
            .build();
    Reference outerMapReference =
        ConcreteReference.builder()
            .setClazz(HashMap.class)
            .setGenerics(Arrays.asList(mapReference, mapReference))
            .build();
    Reference listReference =
        ConcreteReference.builder()
            .setClazz(List.class)
            .setGenerics(Arrays.asList(outerMapReference))
            .build();
    assertEquals(
        listReference.name(), "List<HashMap<HashMap<String, Integer>, HashMap<String, Integer>>>");
    assertEquals(listReference.fullName(), "java.util.List");
  }

  @Test
  public void isSupertype_basic() {
    assertTrue(TypeNode.STRING.isSupertypeOrEquals(TypeNode.STRING));
    assertFalse(TypeNode.INT.isSupertypeOrEquals(TypeNode.STRING));
    assertFalse(TypeNode.STRING.isSupertypeOrEquals(TypeNode.INT));
    assertFalse(TypeNode.INT.isSupertypeOrEquals(TypeNode.INT));

    TypeNode mapType = TypeNode.withReference(ConcreteReference.withClazz(Map.class));
    TypeNode hashMapType = TypeNode.withReference(ConcreteReference.withClazz(HashMap.class));
    assertTrue(mapType.isSupertypeOrEquals(hashMapType));
    assertFalse(hashMapType.isSupertypeOrEquals(mapType));
  }

  @Test
  public void isSupertype_nestedGenerics() {
    Reference stringRef = ConcreteReference.withClazz(String.class);
    TypeNode typeOne =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(Map.class)
                .setGenerics(
                    Arrays.asList(
                        stringRef,
                        ConcreteReference.builder()
                            .setClazz(List.class)
                            .setGenerics(Arrays.asList(ConcreteReference.withClazz(Expr.class)))
                            .build()))
                .build());
    TypeNode typeTwo =
        TypeNode.withReference(
            ConcreteReference.builder()
                .setClazz(HashMap.class)
                .setGenerics(
                    Arrays.asList(
                        stringRef,
                        ConcreteReference.builder()
                            .setClazz(ArrayList.class)
                            .setGenerics(
                                Arrays.asList(ConcreteReference.withClazz(ValueExpr.class)))
                            .build()))
                .build());
    assertTrue(typeOne.isSupertypeOrEquals(typeOne));
    assertTrue(typeOne.isSupertypeOrEquals(typeTwo));
    assertFalse(typeTwo.isSupertypeOrEquals(typeOne));
  }

  @Test
  public void wildcards() {
    assertEquals("?", ConcreteReference.wildcard().name());
    assertEquals(
        "? extends String",
        ConcreteReference.wildcardWithUpperBound(TypeNode.STRING.reference()).name());
  }
}
