package com.google.api.generator.engine.ast;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class AssignmentOperationExprTest {
  /** ========================= Multiply And Assignment Operators =============================== */
  /** =========== Multiply And Assignment Operators: LHS data type is numeric ================= */
  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericMatched() {
    // int a = 1;
    // double b = 2;
    // char c = 'e';
    // float f = 5.99f;
    // short s = 5000;
    // long l = 15000000000L;
    // boolean bool = false;
    // byte bbb = '1';
    // Byte bbbo = new Byte(bbb);
    // Integer ao = new Integer(4);
    // Float fo = new Float(f);
    // Character co = new Character('c');
    // Double doo = new Double(122.3);
    // Boolean bo = new Boolean(false);
    // Long lo = new Long(4l);
    // Short so = new Short(s);
    // int[] ar = new int[3];
    // String word = "avc";
    // a *= b;
    // a *= c;
    // a *= f;
    // a *= s;
    // a *= fo;
    // a *= co;
    // a *= doo;
    // a *= ao;
    // a *= bbb;
    // a *= bbbo;
    // // a *= bool; invalid
    // l *= a;
    // l *= b;
    // l *= c;
    // l *= f;
    // l *= s;
    // l *= ao;
    // l *= co;
    // l *= fo;
    // l *= doo;
    // s *= a;
    // s *= l;
    // s *= f;
    // s *= c;
    // s *= b;
    // ao *= a;
    // ao *= s;
    // ao *= f;
    // ao *= b;
    // ao *= c;
    // ao *= l;
    // ao *= bbb;
    // ao *= co;
    // ao *= fo;
    // ao *= doo;
    // ao *= lo;
    // ao *= bbbo;
    // doo *= a;
    // doo *= b;
    // doo *= c;
    // doo *= f;
    // doo *= s;
    // doo *= l;
    // doo *= bbb;
    // doo *= ao;
    // doo *= fo;
    // doo *= co;
    // doo *= bbbo;
    // co *= a;
    // co *= b;
    // co *= f;
    // co *= s;
    // co *= bbb;
    // co *= l;
    // co *= c;
    // co *= ao;
    // co *= bo;
    // co *= fo;
    // lo *= ao;
    // lo *= a;
    // lo *= l;
    // lo *= c;
    // lo *= s;
    // lo *= bbb;
    // lo *= doo;
    // lo *= fo;
    // lo *= f;
    // lo *= b;
    // lo *= co;
    // lo *= bbbo;
    // lo *= lo;
    // lo *= co;
    // lo *= so;
    // so *= a;
    // so *= b;
    // so *= f;
    // so *= l;
    // so *= so;
    // so *= s;
    // so *= bbb;
    // so *= ao;
    // so *= fo;
    // so *= doo;
    // so *= co;
    // so *= ao;
    // bbbo *= a;
    // bbbo *= f;
    // bbbo *= b;
    // bbbo *= c;
    // bbbo *= s;
    // bbbo *= l;
    // bbbo *= ao;
    // bbbo *= fo;
    // bbbo *= doo;
    // fo *= ao;
    // fo *= fo;
    // fo *= co;
    // fo *= a;
    // fo *= a;
    // fo *= fo;
    // fo *= c;
    // fo *= co;
    // fo *= bbbo;
    // fo *= b;
    // fo *= lo;
    // fo *= doo;
    // fo *= lo;
    // fo *= co;
    // fo *= so;
    // fo *= a;
    // doo *= a;
    // doo *= b;
    // doo *= f;
    // doo *= s;
    // doo *= c;
    // doo *= l;
    // doo *= bbb;
    // doo *= ao;
    // doo *= fo;
    // doo *= so;
    // doo *= bbbo;
    // boolean wpeo = (fo *= co) instanceof Float;
    // No need swap test case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericUnmatched() {
    // No need swap test case.
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericMatchedBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validIntegerMatchedBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericUnmatchedBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validIntegerBoxedWithShortType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validNumericWithFloatType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithFloatType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validDoubleWithIntegerBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithDoubleType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validDoubleWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidLongBoxedWithDoubleType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validLongWithIntegerBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithLongType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validIntegerWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validFloatBoxedWithIntegerType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validFloatWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidLongBoxedWithFloatType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidBooleanBoxedWithNumericType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNumericWithReferenceType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidReferencedWithNumericType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNumericWithNewObject() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNewObjectWithNumeric".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ==== Multiply And Assignment Operators: LHS data type is boolean and its boxed type ===== */
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidBooleanWithNumericType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidBooleanBoxedWithNumericType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithBooleanBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Integer Box Type ============ */
  // RHS should be int, char, short, byte or these types' boxed types.
  @Test
  public void multiplyAndAssignmentOperationExpr_validIntegerMatchedBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validNumericMatchedBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validIntegerBoxedWithShortType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validNumericUnmatchedBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.SHORT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validIntegerBoxedWithShortBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validShortBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.SHORT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validIntegerBoxedWithCharacterBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validShortBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.CHAR_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validIntegerBoxedWithByteBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validByteBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BYTE_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithFloatType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validNumericWithFloatType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validFloatBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithDoubleType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validDoubleWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithDoubleBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validDoubleBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithLongType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validLongWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validLongBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ==== Multiply And Assignment Operators: LHS data type is Float boxed type ====== */
  // RHS could be numeric or numeric boxed type, beside double and its boxed type.
  @Test
  public void multiplyAndAssignmentOperationExpr_validFloatBoxedWithIntegerType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validIntegerWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validFloatBoxedWithIntegerBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validFloatBoxedWithCharBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidCharBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.CHAR_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validFloatBoxedWithByteBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidByteBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BYTE_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validFloatBoxedWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidLongBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithDoubleBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validDoubleBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithObjectType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithObjectType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithNullType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNullWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithReferenceType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidReferenceWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
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
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidShortBoxedWithIntegerBoxedType() {
    // Swap test case in
    // "multiplyAndAssignmentOperationExpr_validCharacterBoxedWithIntegerBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.SHORT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validCharacterBoxedWithIntegerBoxedType() {
    // Swap test case in
    // "multiplyAndAssignmentOperationExpr_validIntegerBoxedWithCharacterBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.CHAR_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidCharBoxedWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithFloatBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.CHAR_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidByteBoxedWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validFloatBoxedWithByteBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BYTE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Double Boxed Type ============ */
  // RHS could be any numeric type or numeric boxed type.
  @Test
  public void multiplyAndAssignmentOperationExpr_validDoubleBoxedWithIntegerBoxedType() {
    // Swap test case in
    // "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validDoubleBoxedWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_validDoubleBoxedWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidLongBoxedWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidDoubleBoxedWithReferenceType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidReferenceWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidDoubleBoxedWithNullType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNullWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidDoubleBoxedWithOjectType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidObjectWithDoubleBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Long boxed type ============ */
  @Test
  public void multiplyAndAssignmentOperationExpr_validLongBoxedWithIntegerBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidIntegerBoxedWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validFloatBoxedWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithFloatType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validFloatWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithDoubleBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validDoubleBoxedWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithDoubleType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validDoubleWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithNullType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNullWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithObjectType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidObjectWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidLongBoxedWithReferenceType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidReferenceWithLongBoxedType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Reference Type ============ */
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidReferencedWithNumericType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithReferenceType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidReferenceWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithReferenceType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidReferenceWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidLongBoxedWithReferenceType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidReferenceWithDoubleBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidDoubleBoxedWithReferenceType".
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Object Type ============ */
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNewObjectWithNumeric() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithNewObject".
    NewObjectExpr lhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "x");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidObjectWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_validDoubleBoxedWithFloatBoxedType".
    NewObjectExpr lhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidObjectWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidLongBoxedWithObjectType".
    NewObjectExpr lhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidObjectWithDoubleBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidDoubleBoxedWithOjectType".
    NewObjectExpr lhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: LHS data type is Null Type ============ */
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNullWithNumericType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidNumericWithNullType".
    ValueExpr lhsExpr = ValueExpr.withValue(NullObjectValue.create());
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNullWithLongBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidLongBoxedWithNullType".
    ValueExpr lhsExpr = ValueExpr.withValue(NullObjectValue.create());
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG_OBJECT, "x");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNullWithFloatBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidFloatBoxedWithNullType".
    ValueExpr lhsExpr = ValueExpr.withValue(NullObjectValue.create());
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidNullWithDoubleBoxedType() {
    // Swap test case in "multiplyAndAssignmentOperationExpr_invalidDoubleBoxedWithNullType".
    ValueExpr lhsExpr = ValueExpr.withValue(NullObjectValue.create());
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "x");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  /** ======== Multiply And Assignment Operators: Void type ============ */
  @Test
  public void multiplyAndAssignmentOperationExpr_invalidVoidType() {
    // No need swap case.
    MethodInvocationExpr lhsExpr =
        MethodInvocationExpr.builder().setMethodName("x").setReturnType(TypeNode.VOID).build();
    MethodInvocationExpr rhsExpr =
        MethodInvocationExpr.builder().setMethodName("y").setReturnType(TypeNode.VOID).build();
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void multiplyAndAssignmentOperationExpr_invalidWithOneVoidType() {
    // No need swap case.
    MethodInvocationExpr lhsExpr =
        MethodInvocationExpr.builder().setMethodName("x").setReturnType(TypeNode.VOID).build();
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    assertThrows(
        IllegalStateException.class,
        () -> AssignmentOperationExpr.multiplyAndAssignmentWithExprs(lhsExpr, rhsExpr));
  }

  // TODO(summerji): Complete the type-checking for ^= and unit test.
  /** ======== Bitwise Exclusive Or And Assignment Operators: ============ */
  private VariableExpr createVariableExpr(TypeNode type, String name) {
    Variable variable = Variable.builder().setName(name).setType(type).build();
    VariableExpr variableExpr = VariableExpr.withVariable(variable);
    return variableExpr;
  }
}
