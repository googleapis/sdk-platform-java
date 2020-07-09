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

import java.awt.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import org.junit.Test;

public class NewObjectValueTest {
  @Test
  public void newObjectValue_basic() {
    ConcreteReference reference =
        ConcreteReference.builder()
            .setClazz(List.class)
            .setGenerics(Collections.emptyList())
            .build();
    TypeNode type = TypeNode.withReference(reference);
    NewObjectValue newObjectValue = NewObjectValue.genericBuilder().setType(type).build();
    assertEquals(newObjectValue.type(), type);
    assertEquals(newObjectValue.value(), "new List<>");
  }

  @Test
  public void newObjectValue_generics() {
    ConcreteReference reference =
        ConcreteReference.builder()
            .setClazz(List.class)
            .setGenerics(Arrays.asList(ConcreteReference.withClazz(String.class)))
            .build();
    TypeNode type = TypeNode.withReference(reference);
    NewObjectValue newObjectValue = NewObjectValue.genericBuilder().setType(type).build();
    assertEquals(newObjectValue.type(), type);
    assertEquals(newObjectValue.value(), "new List<String>");
  }

  @Test
  public void newObjectValue_withArgs() {
    ConcreteReference reference = ConcreteReference.withClazz(Integer.class);
    TypeNode type = TypeNode.withReference(reference);
    ValueExpr valueExpr =
        ValueExpr.builder()
            .setValue(PrimitiveValue.builder().setType(TypeNode.INT).setValue("123").build())
            .build();
    NewObjectValue newObjectValue =
        NewObjectValue.builder().setType(type).setArguments(Arrays.asList(valueExpr)).build();
    assertEquals(newObjectValue.type(), type);
    assertEquals(newObjectValue.value(), "new Integer");
  }

  @Test
  public void newObjectValue_conflictGenericSetting() {
    ConcreteReference reference =
        ConcreteReference.builder()
            .setClazz(LinkedList.class)
            .setGenerics(Arrays.asList(ConcreteReference.withClazz(Object.class)))
            .build();
    TypeNode type = TypeNode.withReference(reference);
    assertThrows(
        IllegalStateException.class,
        () -> {
          NewObjectValue newObjectValue = NewObjectValue.builder().setType(type).build();
        });
  }
}
