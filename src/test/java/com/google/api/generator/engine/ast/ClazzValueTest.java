// Copyright 2022 Google LLC
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
import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

public class ClazzValueTest {

  @Test
  public void createClazzValue_basic() {
    TypeNode clazz = TypeNode.withReference(ConcreteReference.withClazz(List.class));

    assertValidValue(ConcreteReference.withClazz(List.class), "List.class");
    assertValidValue(
        VaporReference.builder()
            .setName("ConditionalOnProperty")
            .setPakkage("org.springframework.boot.autoconfigure.condition")
            .build(),
        "ConditionalOnProperty.class");
  }

  private static void assertValidValue(Reference reference, String value) {
    TypeNode type = TypeNode.withReference(reference);
    ClazzValue clazzValue = ClazzValue.builder().setType(type).build();
    assertEquals(value, clazzValue.value());
    assertThat(clazzValue.type()).isEqualTo(type);
  }
}
