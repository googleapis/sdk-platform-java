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

import com.google.common.base.Function;
import java.util.Arrays;
import org.junit.Test;

public class AnonymousClassExprTest {
  @Test
  public void validAnonymousClass_basic() {
    ConcreteReference ref = ConcreteReference.withClazz(Runnable.class);
    TypeNode type = TypeNode.withReference(ref);
    AssignmentExpr assignmentExpr = createAssignmentExpr("foobar", "false", TypeNode.BOOLEAN);
    ExprStatement statement = ExprStatement.withExpr(assignmentExpr);
    MethodDefinition method =
        MethodDefinition.builder()
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .setName("run")
            .setBody(Arrays.asList(statement))
            .build();

    AnonymousClassExpr anonymousClassExpr =
        AnonymousClassExpr.builder().setType(type).setMethods(Arrays.asList(method)).build();
    assertTrue(TypeNode.isReferenceType(anonymousClassExpr.type()));
  }

  @Test
  public void validAnonymousClass_genericAndVariableExpr() {
    ConcreteReference ref =
        ConcreteReference.builder()
            .setClazz(Function.class)
            .setGenerics(
                Arrays.asList(
                    ConcreteReference.withClazz(String.class),
                    ConcreteReference.withClazz(String.class)))
            .build();
    TypeNode type = TypeNode.withReference(ref);
    // String x;
    VariableExpr variableExpr = createVariableDeclExpr("x", TypeNode.STRING);
    ExprStatement statement = ExprStatement.withExpr(variableExpr);
    // static final String s = "constant";
    VariableExpr variableExpr_staticFinal =
        VariableExpr.builder()
            .setVariable(Variable.builder().setName("s").setType(TypeNode.STRING).build())
            .setIsDecl(true)
            .setIsStatic(true)
            .setIsFinal(true)
            .build();
    AssignmentExpr assignmentExpr =
        AssignmentExpr.builder()
            .setVariableExpr(variableExpr_staticFinal)
            .setValueExpr(
                ValueExpr.builder().setValue(StringObjectValue.withValue("constant")).build())
            .build();
    ExprStatement statement_staticFinal = ExprStatement.withExpr(assignmentExpr);
    VariableExpr arg = createVariableDeclExpr("arg", TypeNode.STRING);
    VariableExpr returnExpr =
        VariableExpr.builder()
            .setVariable(Variable.builder().setName("arg").setType(TypeNode.STRING).build())
            .build();
    MethodDefinition method =
        MethodDefinition.builder()
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.STRING)
            .setArguments(Arrays.asList(arg))
            .setName("apply")
            .setBody(Arrays.asList(statement, statement_staticFinal))
            .setReturnExpr(returnExpr)
            .build();
    AnonymousClassExpr anonymousClassExpr =
        AnonymousClassExpr.builder()
            .setType(type)
            .setStatements(Arrays.asList(statement))
            .setMethods(Arrays.asList(method))
            .build();
    assertTrue(TypeNode.isReferenceType(anonymousClassExpr.type()));
  }

  @Test
  public void invalidAnonymousClass_noReference() {
    assertThrows(
        IllegalStateException.class,
        () -> {
          AnonymousClassExpr anonymousClassExpr =
              AnonymousClassExpr.builder().setType(TypeNode.INT).build();
        });
  }

  @Test
  public void invalidAnonymousClass_staticMethod() {
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
  public void invalidAnonymousClass_staticVariableExpr() {
    ConcreteReference ref = ConcreteReference.withClazz(Runnable.class);
    TypeNode type = TypeNode.withReference(ref);
    Variable variable = createVariable("s", TypeNode.STRING);
    // static string s;
    VariableExpr variableExpr =
        VariableExpr.builder().setIsDecl(true).setIsStatic(true).setVariable(variable).build();
    ExprStatement exprStatement = ExprStatement.withExpr(variableExpr);
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
