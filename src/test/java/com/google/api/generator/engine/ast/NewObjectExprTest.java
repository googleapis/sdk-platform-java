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

public class NewObjectExprTest {
    @Test
    public void newObjectValue_generics() {
      ConcreteReference ref = ConcreteReference.builder().setClazz(List.class).setGenerics(ConcreteReference.withClazz(String.class)).build();
      TypeNode type = TypeNode.withReference(reference);
      NewObjectExpr newObjectExpr = NewObjectExpr.genericBuilder().setType(type).build();
      assertEquals(newObjectExpr.type(), type);
      // constructing `"new List<String>()"`, no exception should be thrown.
    }

    @Test
    public void invalidNewObjectExpr_noReference() {
    // New object expressions should be reference types.
    assertThrows(
       IllegalStateException.class,
       () -> {
           NewObjectExpr.builder().setType(TypeNode.INT).build();
       });
    }

    @Test
    public void validNewObjectValue_conflictGenericSetting() {
      // if the generics() is set, but user calls builder() to build the object,
      // instead of calling genericBuilder, we should set isGeneric for users.
      ConcreteReference ref =
          ConcreteReference.builder()
              .setClazz(LinkedList.class)
              .setGenerics(Arrays.asList(ConcreteReference.withClazz(Object.class)))
              .build();
      TypeNode type = TypeNode.withReference(ref);
      NewObjectValue newObjectValue = NewObjectValue.builder().setType(type).build();
      assertEquals(newObjectExpr.type(), type);
      assertEquals(newObjectExpr.isGeneric(), true);
      assertEquals(newObjectExpr.type().reference(), ref);
    }
}
