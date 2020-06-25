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
import static org.junit.Assert.assertThrows;

import com.google.api.generator.engine.ast.TypeNode.TypeKind;
import org.junit.Test;

public class VariableTest {
  @Test
  public void createVariable_basic() {
    assertValidVariable(TypeKind.INT, "intVar");
    assertValidVariable(TypeKind.BOOLEAN, "boolVar");
    assertValidVariable(TypeKind.DOUBLE, "doubleVar");
    assertValidVariable(TypeKind.LONG, "longVar");
    assertValidVariable(TypeKind.FLOAT, "floatVar");
    assertValidVariable(TypeKind.OBJECT, "objVar");
  }

  @Test
  public void createVariable_setIdentifier() {
    IdentifierNode identifierNode = IdentifierNode.builder().setName("x").build();
    Variable variable =
        Variable.builder()
            .setType(TypeNode.STRING)
            .setIdentifier(identifierNode)
            .setName("y")
            .build();
    assertThat(variable.name()).isEqualTo("y");
    assertThat(variable.type()).isEqualTo(TypeNode.STRING);
  }

  @Test
  public void createVariable_invalidType() {
    assertInvalidVariable(TypeNode.VOID);
    assertInvalidVariable(TypeNode.NULL);
  }

  private static void assertValidVariable(TypeKind typeKind, String name) {
    TypeNode type = TypeNode.builder().setTypeKind(typeKind).build();
    Variable variable = Variable.builder().setType(type).setName(name).build();
    assertThat(variable.name()).isEqualTo(name);
    assertThat(variable.type()).isEqualTo(type);
  }

  private void assertInvalidVariable(TypeNode typeNode) {
    assertThrows(
        IllegalStateException.class,
        () -> {
          Variable.builder().setType(typeNode).setName("name").build();
        });
  }
}
