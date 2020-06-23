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

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class MethodDefinitionTest {

  @Test
  public void validMethodDefinition_basic() {
    MethodDefinition.builder()
        .setName("close")
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr())))
        .build();
    // No exception thrown, we're good.
  }

  @Test
  public void validMethodDefinition_withArgumentsAndReturnExpr() {
    ValueExpr returnExpr =
        ValueExpr.builder()
            .setValue(PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build())
            .build();
    List<VariableExpr> arguments =
        Arrays.asList(
            VariableExpr.builder()
                .setVariable(createVariable("x", TypeNode.INT))
                .setIsDecl(true)
                .build(),
            VariableExpr.builder()
                .setVariable(createVariable("y", TypeNode.INT))
                .setIsDecl(true)
                .build());
    MethodDefinition.builder()
        .setName("close")
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.INT)
        .setArguments(arguments)
        .setReturnExpr(returnExpr)
        .setBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr())))
        .build();
    // No exception thrown, we're good.
  }

  @Test
  public void validMethodDefinition_nonRuntimeException() {
    MethodDefinition.builder()
        .setName("close")
        .setScope(ScopeNode.PUBLIC)
        .setReturnType(TypeNode.VOID)
        .setBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr())))
        .setThrowsExceptions(Arrays.asList(TypeNode.withExceptionClazz(InterruptedException.class)))
        .build();
    // No exception thrown, we're good.
  }

  @Test
  public void invalidMethodDefinition_runtimeException() {
    assertThrows(
        IllegalStateException.class,
        () -> {
          MethodDefinition.builder()
              .setName("close")
              .setScope(ScopeNode.PUBLIC)
              .setReturnType(TypeNode.VOID)
              .setBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr())))
              .setThrowsExceptions(
                  Arrays.asList(TypeNode.withExceptionClazz(IllegalArgumentException.class)))
              .build();
        });
  }

  @Test
  public void invalidMethodDefinition_invalidException() {
    assertThrows(
        IllegalStateException.class,
        () -> {
          MethodDefinition.builder()
              .setName("close")
              .setScope(ScopeNode.PUBLIC)
              .setReturnType(TypeNode.VOID)
              .setBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr())))
              .setThrowsExceptions(Arrays.asList(TypeNode.STRING))
              .build();
        });
  }

  @Test
  public void invalidMethodDefinition_missingNonVoidReturnType() {
    assertThrows(
        IllegalStateException.class,
        () -> {
          MethodDefinition.builder()
              .setName("close")
              .setScope(ScopeNode.PUBLIC)
              .setReturnType(TypeNode.INT)
              .setBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr())))
              .build();
        });
  }

  @Test
  public void invalidMethodDefinition_mismatchedReturnType() {
    ValueExpr booleanValueExpr =
        ValueExpr.builder()
            .setValue(PrimitiveValue.builder().setType(TypeNode.BOOLEAN).setValue("false").build())
            .build();
    assertThrows(
        IllegalStateException.class,
        () -> {
          MethodDefinition.builder()
              .setName("close")
              .setScope(ScopeNode.PUBLIC)
              .setReturnType(TypeNode.INT)
              .setReturnExpr(booleanValueExpr)
              .setBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr())))
              .build();
        });
  }

  @Test
  public void invalidMethodDefinition_nonDeclArguments() {
    ValueExpr returnValueExpr =
        ValueExpr.builder()
            .setValue(PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build())
            .build();
    List<VariableExpr> arguments =
        Arrays.asList(
            VariableExpr.builder().setVariable(createVariable("x", TypeNode.INT)).build(),
            VariableExpr.builder().setVariable(createVariable("y", TypeNode.INT)).build());
    assertThrows(
        IllegalStateException.class,
        () -> {
          MethodDefinition.builder()
              .setName("close")
              .setScope(ScopeNode.PUBLIC)
              .setReturnType(TypeNode.INT)
              .setArguments(arguments)
              .setReturnExpr(returnValueExpr)
              .setBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr())))
              .build();
        });
  }

  private static Variable createVariable(String variableName, TypeNode type) {
    return Variable.builder().setName(variableName).setType(type).build();
  }

  private static AssignmentExpr createAssignmentExpr() {
    Variable variable = createVariable("x", TypeNode.INT);
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Variable anotherVariable = createVariable("y", TypeNode.INT);
    Expr valueExpr = VariableExpr.builder().setVariable(anotherVariable).build();

    return AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();
  }
}
