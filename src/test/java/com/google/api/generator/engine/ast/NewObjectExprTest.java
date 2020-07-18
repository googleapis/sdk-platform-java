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
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

public class NewObjectExprTest {
  @Test
  public void validNewObjectValue_generics() {
    // isGeneric() true, generics() is not empty.
    // constructing `"new List<String>()"`, no exception should be thrown.
    ConcreteReference ref =
        ConcreteReference.builder()
            .setClazz(List.class)
            .setGenerics(Arrays.asList(ConcreteReference.withClazz(String.class)))
            .build();
    TypeNode type = TypeNode.withReference(ref);
    NewObjectExpr newObjectExpr = NewObjectExpr.builder().setIsGeneric(true).setType(type).build();
    assertEquals(newObjectExpr.type(), type);
  }

  @Test
  public void validNewObjectExpr_conflictGenericSetting() {
    // isGeneric() is false, but generics() is not empty
    // it’s still valid, we will set isGeneric() as true for the users.
    // constructing `"new List<String>()"`, no exception should be thrown.
    ConcreteReference ref =
        ConcreteReference.builder()
            .setClazz(List.class)
            .setGenerics(Arrays.asList(ConcreteReference.withClazz(String.class)))
            .build();
    TypeNode type = TypeNode.withReference(ref);
    NewObjectExpr newObjectExpr = NewObjectExpr.builder().setType(type).build();
    assertEquals(newObjectExpr.type(), type);
    assertEquals(newObjectExpr.type().reference(), ref);
    assertEquals(newObjectExpr.isGeneric(), true);
  }

  @Test
  public void validNewObjectExpr_notGenericWithArgs() {
    // isGeneric() is false, and generics() is empty
    // constructing “new Integer(123) “ no exception should be thrown
    ConcreteReference ref = ConcreteReference.builder().setClazz(Integer.class).build();
    TypeNode type = TypeNode.withReference(ref);
    PrimitiveValue value = PrimitiveValue.builder().setType(TypeNode.INT).setValue("123").build();
    ValueExpr valueExpr = ValueExpr.builder().setValue(value).build();
    NewObjectExpr newObjectExpr =
        NewObjectExpr.builder().setType(type).setArguments(Arrays.asList(valueExpr)).build();
    assertEquals(newObjectExpr.type(), type);
    assertEquals(newObjectExpr.type().reference(), ref);
    assertEquals(newObjectExpr.isGeneric(), false);
  }

  @Test
  public void validNewObjectExpr_emptyGeneric() {
    // isGeneric() is true, but generics() is empty
    // constructing “new LinedList<>()” no exception should be thrown
    ConcreteReference ref = ConcreteReference.builder().setClazz(LinkedList.class).build();
    TypeNode type = TypeNode.withReference(ref);
    NewObjectExpr newObjectExpr = NewObjectExpr.builder().setType(type).setIsGeneric(true).build();
    assertEquals(newObjectExpr.type(), type);
    assertEquals(newObjectExpr.type().reference(), ref);
    assertEquals(newObjectExpr.isGeneric(), true);
  }

  @Test
  public void invalidNewObjectExpr_noReference() {
    // New object expressions should be reference types.
    assertThrows(
        IllegalStateException.class,
        () -> {
          NewObjectExpr.builder().setIsGeneric(false).setType(TypeNode.INT).build();
        });
  }
}
