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

import static org.junit.Assert.assertThrows;

import com.google.api.generator.util.TestUtils;
import org.junit.Test;

public class ArrayExprTest {

  @Test
  public void validAnonymousArray_sametype() {
    ArrayExpr.Builder exprBuilder =
        ArrayExpr.builder()
            .addExpr(TestUtils.generateStringValueExpr("test1"))
            .addExpr(TestUtils.generateStringValueExpr("test2"))
            .addExpr(TestUtils.generateStringValueExpr("test3"));

    assertThrows(
        IllegalStateException.class,
        () ->
            exprBuilder.addExpr(
                ValueExpr.withValue(
                    PrimitiveValue.builder().setValue("1").setType(TypeNode.INT).build())));
  }

  @Test
  public void validAnonymousArray_emptythrows() {
    ArrayExpr.Builder exprBuilder = ArrayExpr.builder();
    assertThrows(IllegalStateException.class, () -> exprBuilder.build());
  }
}
