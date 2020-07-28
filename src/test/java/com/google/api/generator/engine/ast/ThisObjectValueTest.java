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

import static org.junit.Assert.assertThrows;

import org.junit.Assert;
import org.junit.Test;

public class ThisObjectValueTest {
  @Test
  public void testThisObjectValueTest() {
    VaporReference ref =
        VaporReference.builder()
            .setName("Student")
            .setPakkage("com.google.example.examples.v1")
            .build();
    TypeNode typeNode = TypeNode.withReference(ref);
    ThisObjectValue thisObjectValue = ThisObjectValue.withType(TypeNode.withReference(ref));

    Assert.assertEquals(thisObjectValue.value(), "this");
    Assert.assertEquals(thisObjectValue.type(), typeNode);
  }

  @Test
  public void testThisObjectValueTest_invalid() {
    ClassDefinition classDefinition =
        ClassDefinition.builder()
            .setPackageString("com.google.example.library.v1.stub")
            .setName("LibraryServiceStub")
            .setScope(ScopeNode.PUBLIC)
            .build();
    ConcreteReference ref = ConcreteReference.withClazz(classDefinition.getClass());
    TypeNode typeNode = TypeNode.withReference(ref);
    ThisObjectValue thisObjectValue = ThisObjectValue.withType(TypeNode.withReference(ref));
    Assert.assertEquals(thisObjectValue.value(), "this");
    Assert.assertEquals(thisObjectValue.type(), typeNode);
  }

  @Test
  public void testThisObjectValueTest_invalid_type() {
    ConcreteReference ref = ConcreteReference.withClazz(Integer.class);
    assertThrows(
        IllegalStateException.class,
        () -> {
          ThisObjectValue.withType(TypeNode.withReference(ref));
        });
    assertThrows(
        IllegalStateException.class,
        () -> {
          ThisObjectValue.withType(TypeNode.BOOLEAN);
        });
  }
}
