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

public class ThrowExprTest {
  @Test
  public void createThrowExpr_basic() {
    TypeNode npeType =
        TypeNode.withReference(ConcreteReference.withClazz(NullPointerException.class));
    ThrowExpr.builder().setType(npeType).build();
    // No exception thrown, we're good.

  }

  @Test
  public void createThrowExpr_basicWithMessage() {
    TypeNode npeType =
        TypeNode.withReference(ConcreteReference.withClazz(NullPointerException.class));
    ThrowExpr.builder().setType(npeType).setMessage("Some message").build();
    // No exception thrown, we're good.

  }

  @Test
  public void createThrowExpr_badExceptionType() {
    TypeNode nonExceptionType = TypeNode.STRING;
    assertThrows(
        IllegalStateException.class, () -> ThrowExpr.builder().setType(nonExceptionType).build());
  }
}
