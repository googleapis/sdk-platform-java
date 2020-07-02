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

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import org.junit.Test;

public class AnonymousClassExprTest {
  @Test
  public void basicAnonymousClass() {
    ConcreteReference ref = ConcreteReference.withClazz(Runnable.class);
    TypeNode type = TypeNode.withReference(ref);
    AnonymousClassExpr anonymousClassExpr = AnonymousClassExpr.builder().setType(type).build();
    assertTrue(TypeNode.isReferenceType(anonymousClassExpr.type()));
  }

  @Test
  public void InvalidAnonymousClass_noReference() {
    assertThrows(
        IllegalStateException.class,
        () -> {
          AnonymousClassExpr anonymousClassExpr =
              AnonymousClassExpr.builder().setType(TypeNode.INT).build();
        });
  }

  @Test
  public void InvalidAnonymousClass_staticMethod() {
    ConcreteReference ref = ConcreteReference.withClazz(Runnable.class);
    TypeNode type = TypeNode.withReference(ref);
    MethodDefinition method =
        MethodDefinition.builder()
            .setName("run")
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .setIsStatic(true)
            .setBody(
                Arrays.asList(ExprStatement.withExpr(createAssignmentExpr("x", "3", TypeNode.INT))))
            .build();

    assertThrows(
        IllegalStateException.class,
        () -> {
          AnonymousClassExpr anonymousClassExpr =
              AnonymousClassExpr.builder().setType(type).setMethods(Arrays.asList(method)).build();
        });
  }

  @Test
  public void InvalidAnonymousClass_finalVariableExpr() {
    ConcreteReference ref = ConcreteReference.withClazz(Runnable.class);
    TypeNode type = TypeNode.withReference(ref);
    Variable variable = createVariable("s", TypeNode.STRING);
    VariableExpr variableExpr =
        VariableExpr.builder()
            .setScope(ScopeNode.PRIVATE)
            .setIsDecl(true)
            .setVariable(variable)
            .build();
    ValueExpr valueExpr = ValueExpr.builder().setValue(StringObjectValue.withValue("foo")).build();
    AssignmentExpr assignmentExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();
    ExprStatement exprStatement = ExprStatement.withExpr(assignmentExpr);

    assertThrows(
        IllegalStateException.class,
        () -> {
          AnonymousClassExpr anonymousClassExpr =
              AnonymousClassExpr.builder()
                  .setType(type)
                  .setStatements(Arrays.asList(exprStatement))
                  .build();
        });
  }

  private static AssignmentExpr createAssignmentExpr(
      String variableName, String value, TypeNode type) {
    VariableExpr variableExpr = createVariableDeclExpr(variableName, type);
    Value val = PrimitiveValue.builder().setType(type).setValue(value).build();
    Expr valueExpr = ValueExpr.builder().setValue(val).build();
    return AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();
  }

  private static Variable createVariable(String variableName, TypeNode type) {
    return Variable.builder().setName(variableName).setType(type).build();
  }

  private static VariableExpr createVariableDeclExpr(String variableName, TypeNode type) {
    return createVariableExpr(variableName, type, true);
  }

  private static VariableExpr createVariableExpr(
      String variableName, TypeNode type, boolean isDecl) {
    return VariableExpr.builder()
        .setVariable(createVariable(variableName, type))
        .setIsDecl(isDecl)
        .build();
  }
}
