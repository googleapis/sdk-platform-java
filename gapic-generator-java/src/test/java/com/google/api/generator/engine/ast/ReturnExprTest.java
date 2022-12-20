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

public class ReturnExprTest {
  @Test
  public void validReturnExpr_basic() {
    ReturnExpr.withExpr(ValueExpr.withValue(StringObjectValue.withValue("asdf")));
    // No exception thrown, we're good.
  }

  @Test
  public void invalidReturnExpr_nestedReturnExpr() {
    ReturnExpr returnExpr =
        ReturnExpr.withExpr(ValueExpr.withValue(StringObjectValue.withValue("asdf")));
    assertThrows(IllegalStateException.class, () -> ReturnExpr.withExpr(returnExpr));
  }
}
