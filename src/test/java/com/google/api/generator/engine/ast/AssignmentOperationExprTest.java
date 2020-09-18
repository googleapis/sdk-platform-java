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

public class AssignmentOperationExprTest {
  /** =========== Multiply And Assignment Operators: Variable is declaration ================ */
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidVariableExprIsDecl() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr lhsExpr = VariableExpr.builder().setVariable(variable).setIsDecl(true).build();
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidValueExprIsDecl() {
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr rhsExpr = VariableExpr.builder().setVariable(variable).setIsDecl(true).build();
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidVariableExprAndValueExprIsDecl() {
    Variable lVariable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr lhsExpr = VariableExpr.builder().setVariable(lVariable).setIsDecl(true).build();
    Variable rVariable = Variable.builder().setName("y").setType(TypeNode.INT).build();
    VariableExpr rhsExpr = VariableExpr.builder().setVariable(rVariable).setIsDecl(true).build();
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ========= Multiply And Assignment Operators: VariableExpr is numeric types ============== */
  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericMatched() {
    // No need swap test case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericUnmatched() {
    // No need swap test case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    ValueExpr rhsExpr = createValueExpr(TypeNode.INT, "5");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericMatchedBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validIntegerMatchedBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericUnmatchedBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validIntegerBoxedWithShortType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericWithFloatType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithFloatType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validDoubleWithIntegerBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithDoubleType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validDoubleWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidLongBoxedWithDoubleType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validLongWithIntegerBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithLongType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validIntegerWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validFloatBoxedWithIntegerType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validFloatWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidLongBoxedWithFloatType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidBooleanBoxedWithNumericType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNumericWithReferenceType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidReferencedWithNumericType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNumericWithNewObject() {
    // No Need swap case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ==== Multiply And Assignment Operators: LHS data type is boolean and its boxed type ===== */
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidBooleanWithNumericType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidBooleanBoxedWithNumericType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "x");
    ValueExpr rhsExpr = createValueExpr(TypeNode.INT, "5");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Integer Box Type ============ */
  // RHS should be int, char, short, byte or these types' boxed types.
  @Test
  public void multiplyAndAssignmentOperationExpr_validIntegerMatchedBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validNumericMatchedBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    ValueExpr rhsExpr = createValueExpr(TypeNode.INT, "5");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validIntegerBoxedWithShortType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validNumericUnmatchedBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.SHORT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validIntegerBoxedWithShortBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validShortBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.SHORT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validIntegerBoxedWithCharacterBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validShortBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.CHAR_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validIntegerBoxedWithByteBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validByteBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BYTE_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithFloatType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validNumericWithFloatType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validFloatBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithDoubleType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validDoubleWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithDoubleBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validDoubleBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithLongType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validLongWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validLongBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ==== Multiply And Assignment Operators: LHS data type is Float boxed type ====== */
  // RHS could be numeric or numeric boxed type, beside double and its boxed type.
  @Test
  public void multiplyAndAssignmentOperationExpr_validFloatBoxedWithIntegerType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validIntegerWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    ValueExpr rhsExpr = createValueExpr(TypeNode.INT, "5");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validFloatBoxedWithIntegerBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validFloatBoxedWithCharBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidCharBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.CHAR_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validFloatBoxedWithByteBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidByteBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BYTE_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validFloatBoxedWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidLongBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithDoubleBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validDoubleBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithObjectType() {
    // No need swap case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithNullType() {
    // No need swap case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithReferenceType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidReferenceWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ==== Multiply And Assignment Operators: LHS data type is Short/Char/Byte Boxed Type ====== */
  // RHS has no valid type.
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidByteBoxedWithIntegerBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validIntegerBoxedWithByteBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BYTE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidShortBoxedWithIntegerBoxedType() {
    // Swap test case in
    // "multiplyAndAssignmentOperationExpr_validCharacterBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.SHORT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validCharacterBoxedWithIntegerBoxedType() {
    // Swap test case in
    // "multiplyAndAssignmentOperationExpr_validIntegerBoxedWithCharacterBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.CHAR_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidCharBoxedWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.CHAR_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidByteBoxedWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validFloatBoxedWithByteBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BYTE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Double Boxed Type ============ */
  // RHS could be any numeric type or numeric boxed type.
  @Test
  public void multiplyAndAssignmentOperationExpr_validDoubleBoxedWithIntegerBoxedType() {
    // Swap test case in
    // "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validDoubleBoxedWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validDoubleBoxedWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidLongBoxedWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidDoubleBoxedWithReferenceType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidReferenceWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    ValueExpr valueExpr = ValueExpr.withValue(StringObjectValue.withValue("abc"));
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, valueExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidDoubleBoxedWithNullType() {
    // No need swap case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    ValueExpr valueExprExpr = ValueExpr.withValue(NullObjectValue.create());
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, valueExprExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidDoubleBoxedWithOjectType() {
    // No need swap test.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Long boxed type ============ */
  @Test
  public void multiplyAndAssignmentOperationExpr_validLongBoxedWithIntegerBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validFloatBoxedWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithFloatType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validFloatWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithDoubleBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validDoubleBoxedWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithDoubleType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validDoubleWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithNullType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNullWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithObjectType() {
    // No need swap case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithReferenceType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidReferenceWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Reference Type ============ */
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidReferencedWithNumericType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithReferenceType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidReferenceWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithReferenceType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidReferenceWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidLongBoxedWithReferenceType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidReferenceWithDoubleBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidDoubleBoxedWithReferenceType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======================= Multiply And Assignment Operators: Void type ===================== */
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidWithOneVoidType() {
    // No need swap case.
    VariableExpr lhsExprExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    MethodInvocationExpr rhsExpr =
        MethodInvocationExpr.builder().setMethodName("x").setReturnType(TypeNode.VOID).build();
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExprExpr, rhsExpr));
  }

  // TODO(summerji): Complete the type-checking for ^= and unit test.
  /** ================== Bitwise Exclusive Or And Assignment Operators: ======================== */
  // createVariableExpr is help function to create a variable expr.
  private VariableExpr createVariableExpr(TypeNode type, String name) {
    Variable variable = Variable.builder().setName(name).setType(type).build();
    VariableExpr lhsExpr = VariableExpr.withVariable(variable);
    return lhsExpr;
  }

  // createValueExpr is help function to create a value expr.
  private ValueExpr createValueExpr(TypeNode type, String value) {
    PrimitiveValue primitiveValue = PrimitiveValue.builder().setType(type).setValue(value).build();
    ValueExpr valueExpr = ValueExpr.withValue(primitiveValue);
    return valueExpr;
  }
}
