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
  /** ========= Multiply And Assignment Operators: VariableExpr is numeric types ============== */
  @Test
  public void validMultiplyAndAssignmentOperationExpr_numericMatched() {
    // No need swap test case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_numericUnmatched() {
    // No need swap test case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    ValueExpr rhsExpr = createValueExpr(TypeNode.INT, "5");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_numericMatchedBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_integerMatchedBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_numericUnmatchedBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_integerBoxedWithShortType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_numericWithFloatType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_integerBoxedWithFloatType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_doubleWithIntegerBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_integerBoxedWithDoubleType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_doubleWithLongBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_longBoxedWithDoubleType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_longWithIntegerBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_integerBoxedWithLongType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_integerWithFloatBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_floatBoxedWithIntegerType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_validFloatWithLongBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_longBoxedWithFloatType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_numericWithBooleanBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_booleanBoxedWithNumericType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_numericWithBooleanType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_numericWithBooleanType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_numericWithReferenceType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_feferencedWithNumericType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_NumericWithNewObject() {
    // No Need swap case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ==== Multiply And Assignment Operators: LHS data type is boolean and its boxed type ===== */
  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_booleanWithNumericType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_numericWithBooleanType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_booleanBoxedWithNumericType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_numericWithBooleanBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "x");
    ValueExpr rhsExpr = createValueExpr(TypeNode.INT, "5");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Integer Box Type ============ */
  // RHS should be int, char, short, byte or these types' boxed types.
  @Test
  public void validMultiplyAndAssignmentOperationExpr_integerMatchedBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_numericMatchedBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    ValueExpr rhsExpr = createValueExpr(TypeNode.INT, "5");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_integerBoxedWithShortType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_numericUnmatchedBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.SHORT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_integerBoxedWithShortBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_shortBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.SHORT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_integerBoxedWithCharacterBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_shortBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.CHAR_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_integerBoxedWithByteBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_byteBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BYTE_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_integerBoxedWithFloatType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_numericWithFloatType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_integerBoxedWithFloatBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_floatBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_integerBoxedWithDoubleType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_doubleWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_integerBoxedWithDoubleBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_doubleBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_integerBoxedWithLongType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_longWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_integerBoxedWithLongBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_longBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ==== Multiply And Assignment Operators: LHS data type is Float boxed type ====== */
  // RHS could be numeric or numeric boxed type, beside double and its boxed type.
  @Test
  public void validMultiplyAndAssignmentOperationExpr_floatBoxedWithIntegerType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_integerWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    ValueExpr rhsExpr = createValueExpr(TypeNode.INT, "5");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_floatBoxedWithIntegerBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_integerBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_floatBoxedWithCharBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_charBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.CHAR_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_floatBoxedWithByteBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_byteBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BYTE_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_floatBoxedWithLongBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_longBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_floatBoxedWithDoubleBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_doubleBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_floatBoxedWithObjectType() {
    // No need swap case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_floatBoxedWithNullType() {
    // No need swap case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_floatBoxedWithReferenceType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_referenceWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ==== Multiply And Assignment Operators: LHS data type is Short/Char/Byte Boxed Type ====== */
  // RHS has no valid type.
  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_byteBoxedWithIntegerBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_integerBoxedWithByteBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BYTE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_shortBoxedWithIntegerBoxedType() {
    // Swap test case in
    // "validMultiplyAndAssignmentOperationExpr_validCharacterBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.SHORT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_characterBoxedWithIntegerBoxedType() {
    // Swap test case in
    // "validMultiplyAndAssignmentOperationExpr_integerBoxedWithCharacterBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.CHAR_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_charBoxedWithFloatBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_integerBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.CHAR_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_byteBoxedWithFloatBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_floatBoxedWithByteBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BYTE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Double Boxed Type ============ */
  // RHS could be any numeric type or numeric boxed type.
  @Test
  public void validMultiplyAndAssignmentOperationExpr_doubleBoxedWithIntegerBoxedType() {
    // Swap test case in
    // "invalidMultiplyAndAssignmentOperationExpr_integerBoxedWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_doubleBoxedWithFloatBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_floatBoxedWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void validMultiplyAndAssignmentOperationExpr_doubleBoxedWithLongBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_longBoxedWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_doubleBoxedWithReferenceType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_referenceWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    ValueExpr valueExpr = ValueExpr.withValue(StringObjectValue.withValue("abc"));
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, valueExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_doubleBoxedWithNullType() {
    // No need swap case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    ValueExpr valueExprExpr = ValueExpr.withValue(NullObjectValue.create());
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, valueExprExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_doubleBoxedWithOjectType() {
    // No need swap test.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Long boxed type ============ */
  @Test
  public void validMultiplyAndAssignmentOperationExpr_longBoxedWithIntegerBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_integerBoxedWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_longBoxedWithFloatBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_floatBoxedWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_longBoxedWithFloatType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_floatWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_longBoxedWithDoubleBoxedType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_doubleBoxedWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_longBoxedWithDoubleType() {
    // Swap test case in "validMultiplyAndAssignmentOperationExpr_doubleWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_longBoxedWithNullType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_nullWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_longBoxedWithObjectType() {
    // No need swap case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_longBoxedWithReferenceType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_referenceWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Reference Type ============ */
  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_referencedWithNumericType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_numericWithReferenceType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_referenceWithFloatBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_floatBoxedWithReferenceType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_referenceWithLongBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_longBoxedWithReferenceType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_referenceWithDoubleBoxedType() {
    // Swap test case in "invalidMultiplyAndAssignmentOperationExpr_doubleBoxedWithReferenceType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** =========== Multiply And Assignment Operators: Variable is declaration ================ */
  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_variableExprIsDecl() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr lhsExpr = VariableExpr.builder().setVariable(variable).setIsDecl(true).build();
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_valueExprIsDecl() {
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr rhsExpr = VariableExpr.builder().setVariable(variable).setIsDecl(true).build();
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_variableExprAndValueExprIsDecl() {
    Variable lVariable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr lhsExpr = VariableExpr.builder().setVariable(lVariable).setIsDecl(true).build();
    Variable rVariable = Variable.builder().setName("y").setType(TypeNode.INT).build();
    VariableExpr rhsExpr = VariableExpr.builder().setVariable(rVariable).setIsDecl(true).build();
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======================= Multiply And Assignment Operators: Void type ===================== */
  @Test
  public void invalidMultiplyAndAssignmentOperationExpr_voidType() {
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
