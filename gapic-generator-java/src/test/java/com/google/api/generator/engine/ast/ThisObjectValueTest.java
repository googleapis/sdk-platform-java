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

import org.junit.Test;

public class ThisObjectValueTest {
  @Test
  public void validThisObjectValue_basic() {
    VaporReference ref =
        VaporReference.builder()
            .setName("Student")
            .setPakkage("com.google.example.examples.v1")
            .build();
    TypeNode typeNode = TypeNode.withReference(ref);
    ThisObjectValue.withType(typeNode);
    // No exception thrown, we're good.
  }

  @Test
  public void invalidThisObjectValue_nonReferenceType() {
    assertThrows(
        IllegalStateException.class,
        () -> {
          ThisObjectValue.withType(TypeNode.DOUBLE);
        });
  }

  @Test
  public void invalidThisObjectValue_nullType() {
    assertThrows(
        IllegalStateException.class,
        () -> {
          ThisObjectValue.withType(TypeNode.NULL);
        });
  }
}
