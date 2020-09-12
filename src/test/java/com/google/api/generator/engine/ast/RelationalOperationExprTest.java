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

import com.google.api.generator.engine.ast.TypeNode.TypeKind;
import org.junit.Test;

public class RelationalOperationExprTest {
  /** ==================== Equality Operators: LHS data type is numeric ======================= */
  @Test
  public void equalToOperationExpr_validBasic() {
    // LHS: numeric type, RHS: matched numeric type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validNumericTYpe() {
    // LHS: numeric type, RHS: unmatched numeric type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.CHAR, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.LONG, "y");
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void notEqualToOperationExpr_validMatchedNumericBoxTYpe() {
    // LHS: numeric type, RHS: matched numeric Boxed type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "y");
    RelationalOperationExpr.notEqualToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void notEqualToOperationExpr_validNumericBoxTYpe() {
    // LHS: numeric type, RHS: unmatched numeric Boxed type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE_OBJECT, "y");
    RelationalOperationExpr.notEqualToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_invalidNumericBooleanBoxedType() {
    // LHS: numeric type, RHS: boolean boxed Type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void notEqualToOperationExpr_invalidNumericStringType() {
    // LHS: numeric type, RHS: referenced type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.DOUBLE, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.notEqualToWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void equalToOperationExpr_invalidNumericBooleanType() {
    // LHS: numeric type, RHS: boolean boxed Type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.LONG, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN, "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr));
  }

  /** =================== Equality Operators: LHS data type is Boxed Type ====================== */
  @Test
  public void equalToOperationExpr_validBoxedWithMatchedBoxedType() {
    // LHS: Boxed type, RHS: Matched Boxed
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT_OBJECT, "y");
    RelationalOperationExpr.equalToWithExprs(rhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validBoxedWithMatchedUnBoxedType() {
    // LHS: Boxed type, RHS: Unmatched Boxed
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.FLOAT, "y");
    RelationalOperationExpr.equalToWithExprs(rhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validBoxedWithNullType() {
    // LHS: Boxed type, RHS: Null
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    RelationalOperationExpr.equalToWithExprs(rhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validBoxedWithUnmatchedUnBoxedType() {
    // LHS: Numeric boxed type, RHS: other numeric type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE, "y");
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validBoxedWithNewObjectType() {
    // LHS: Numeric boxed type, RHS: new object
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_invalidBoxedWithBooleanBoxedType() {
    // LHS: Numeric boxed type, RHS: Boolean Boxed type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.FLOAT_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr));
  }

  /** ==================== Equality Operators: LHS data type is boolean ======================= */
  @Test
  public void equalToOperationExpr_validRHSBooleanBoxedType() {
    // LHS: boolean type, RHS: boolean boxed Type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "y");
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validBooleanType() {
    // LHS: boolean type, RHS: boolean Type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN, "y");
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validLHSBooleanBoxedType() {
    // LHS: boolean boxed type, RHS: boolean Type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN, "y");
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void notEqualToOperationExpr_validBooleanBoxedToNullType() {
    // LHS: boolean boxed type, RHS: null
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "x");
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void notEqualToOperationExpr_invalidBooleanToOtherBoxedType() {
    // LHS: boolean type, RHS: char boxed type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.CHAR_OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.notEqualToWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void notEqualToOperationExpr_invalidBooleanToReferenceType() {
    // LHS: boolean type, RHS: new Object
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.notEqualToWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void equalToOperationExpr_invalidBooleanWithNullType() {
    // LHS: boolean type, RHS: null type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN, "x");
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void equalToOperationExpr_invalidBooleanWithObjectType() {
    // LHS: boolean type, RHS: object type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.BOOLEAN, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.OBJECT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr));
  }

  /** ===================== Equality Operators: LHS data type is Array ======================== */
  @Test
  public void equalToOperationExpr_validArrayWithMatchedType() {
    // LHS: Array with numeric type, RHS: Array with matched numeric type
    VariableExpr lhsExpr =
        createVariableExpr(
            TypeNode.builder().setIsArray(true).setTypeKind(TypeKind.INT).build(), "x");
    VariableExpr rhsExpr =
        createVariableExpr(
            TypeNode.builder().setIsArray(true).setTypeKind(TypeKind.INT).build(), "y");
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validArrayWithNullType() {
    // LHS: Array with numeric type, RHS: Array with matched numeric type
    VariableExpr lhsExpr =
        createVariableExpr(
            TypeNode.builder().setIsArray(true).setTypeKind(TypeKind.INT).build(), "x");
    NullObjectValue nullObjectValue = NullObjectValue.create();
    ValueExpr rhsExpr = ValueExpr.withValue(nullObjectValue);
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void notEqualToOperationExpr_invalidArrayWithUnmatchedType() {
    // LHS: Array with numeric type, RHS: Array with unmatched numeric type
    VariableExpr lhsExpr =
        createVariableExpr(
            TypeNode.builder().setIsArray(true).setTypeKind(TypeKind.INT).build(), "x");
    VariableExpr rhsExpr =
        createVariableExpr(
            TypeNode.builder().setIsArray(true).setTypeKind(TypeKind.CHAR).build(), "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.notEqualToWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void equalToOperationExpr_invalidArrayWithNotArrayType() {
    // LHS: Array with numeric type, RHS: not Array
    VariableExpr lhsExpr =
        createVariableExpr(
            TypeNode.builder().setIsArray(true).setTypeKind(TypeKind.INT).build(), "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.INT, "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void equalToOperationExpr_invalidRHSArrayType() {
    // LHS: not arrary, RHS: Array
    VariableExpr lhsExpr = createVariableExpr(TypeNode.INT, "x");
    VariableExpr rhsExpr =
        createVariableExpr(
            TypeNode.builder().setIsArray(true).setTypeKind(TypeKind.INT).build(), "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr));
  }

  /** ================== Equality Operators: LHS data type is reference type =================== */
  @Test
  public void equalToOperationExpr_validReferenceWithMatchedType() {
    // LHS: String type, RHS: matched String type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.STRING, "y");
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validReferenceWithNullType() {
    // LHS: String type, RHS: null
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validReferenceWithObjectType() {
    // LHS: String type, RHS: new object type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    NewObjectExpr rhsExpr = NewObjectExpr.withType(TypeNode.OBJECT);
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_invalidReferenceWithUnmatchedReferenceType() {
    // LHS: String type, RHS: unmatched reference type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    TypeNode someType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("SomeClass")
                .setPakkage("com.google.api.some.pakkage")
                .build());
    VariableExpr rhsExpr = createVariableExpr(someType, "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void equalToOperationExpr_invalidReferenceWithNumericType() {
    // LHS: String type, RHS: unmatched reference type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.STRING, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE, "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr));
  }

  /** ================== Equality Operators: LHS data type is Object or null =================== */
  @Test
  public void equalToOperationExpr_validObjectWithAnyObjectType() {
    // LHS: object type, RHS: any reference type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.OBJECT, "x");
    TypeNode someType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("SomeClass")
                .setPakkage("com.google.api.some.pakkage")
                .build());
    VariableExpr rhsExpr = createVariableExpr(someType, "y");
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validNullWithAnyObjectType() {
    // LHS: Null type, RHS: any reference type
    ValueExpr lhsExpr = ValueExpr.withValue(NullObjectValue.create());
    TypeNode someType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("SomeClass")
                .setPakkage("com.google.api.some.pakkage")
                .build());
    VariableExpr rhsExpr = createVariableExpr(someType, "y");
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validObjectWithNullType() {
    // LHS: Object, RHS: Null
    VariableExpr lhsExpr = createVariableExpr(TypeNode.OBJECT, "x");
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validNullWithNullType() {
    // LHS: Null, RHS: Null
    ValueExpr lhsExpr = ValueExpr.withValue(NullObjectValue.create());
    ValueExpr rhsExpr = ValueExpr.withValue(NullObjectValue.create());
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validObjectWithBoxedType() {
    // LHS: Object type, RHS: any Boxed type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "y");
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_validNullWithBoxedType() {
    // LHS: Object type, RHS: any Boxed type
    ValueExpr lhsExpr = ValueExpr.withValue(NullObjectValue.create());
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN_OBJECT, "y");
    RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr);
    // No exception thrown, so we succeeded.
  }

  @Test
  public void equalToOperationExpr_invalidObjectWithNumericType() {
    // LHS: Object type, RHS: any Numeric type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.DOUBLE, "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr));
  }

  @Test
  public void equalToOperationExpr_invalidObjectWithBooleanType() {
    // LHS: Object type, RHS: boolean type
    VariableExpr lhsExpr = createVariableExpr(TypeNode.OBJECT, "x");
    VariableExpr rhsExpr = createVariableExpr(TypeNode.BOOLEAN, "y");
    assertThrows(
        IllegalStateException.class,
        () -> RelationalOperationExpr.equalToWithExprs(lhsExpr, rhsExpr));
  }

  private VariableExpr createVariableExpr(TypeNode type, String name) {
    Variable variable = Variable.builder().setName(name).setType(type).build();
    VariableExpr variableExpr = VariableExpr.withVariable(variable);
    return variableExpr;
  }
}
