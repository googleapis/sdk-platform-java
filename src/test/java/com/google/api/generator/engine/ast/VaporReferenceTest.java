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

import org.junit.Test;

public class VaporReferenceTest {
  @Test
  public void basic() {
    String pkg = "com.google.example.examples.library.v1";
    String name = "Babbage";
    Reference ref = VaporReference.builder().setName(name).setPakkage(pkg).build();
    assertEquals(ref.name(), name);
    assertEquals(ref.fullName(), String.format("%s.%s", pkg, name));
    assertFalse(ref.hasEnclosingClass());
    assertTrue(ref.isFromPackage(pkg));
    assertFalse(ref.isFromPackage("com.google.example.library"));
  }

  @Test
  public void concreteHierarchiesNotHandled() {
    String pkg = "java.io";
    String name = "IOException";
    Reference ref = VaporReference.builder().setName(name).setPakkage(pkg).build();

    Reference exceptionRef = ConcreteReference.withClazz(Exception.class);
    assertFalse(ref.isAssignableFrom(exceptionRef));
    assertFalse(exceptionRef.isAssignableFrom(ref));
    assertFalse(exceptionRef.isSupertypeOrEquals(ref));
  }

  @Test
  public void enclosingClass() {
    String pkg = "java.util";
    String enclosingName = "Map";
    String name = "Entry";

    Reference ref =
        VaporReference.builder()
            .setName(name)
            .setPakkage(pkg)
            .setEnclosingClassName(enclosingName)
            .build();
    assertTrue(ref.hasEnclosingClass());
    assertEquals(ref.fullName(), String.format("%s.%s.%s", pkg, enclosingName, name));
  }
}
