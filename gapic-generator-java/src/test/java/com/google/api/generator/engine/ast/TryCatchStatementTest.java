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

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import org.junit.Test;

public class TryCatchStatementTest {

  @Test
  public void validTryCatchStatement_simple() {
    Reference exceptionReference = ConcreteReference.withClazz(IllegalArgumentException.class);
    TypeNode type = TypeNode.withReference(exceptionReference);
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(createVariable("e", type)).setIsDecl(true).build();

    TryCatchStatement tryCatch =
        TryCatchStatement.builder()
            .setTryBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr())))
            .setCatchVariableExpr(variableExpr)
            .build();
    assertThat(tryCatch.catchVariableExpr()).isEqualTo(variableExpr);
  }

  @Test
  public void validTryCatchStatement_withResources() {
    Reference exceptionReference = ConcreteReference.withClazz(IllegalArgumentException.class);
    TypeNode type = TypeNode.withReference(exceptionReference);
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(createVariable("e", type)).setIsDecl(true).build();
    AssignmentExpr assignmentExpr = createAssignmentExpr();

    TryCatchStatement tryCatch =
        TryCatchStatement.builder()
            .setTryResourceExpr(assignmentExpr)
            .setTryBody(Arrays.asList(ExprStatement.withExpr(assignmentExpr)))
            .setCatchVariableExpr(variableExpr)
            .build();
    assertThat(tryCatch.catchVariableExpr()).isEqualTo(variableExpr);
    assertThat(tryCatch.tryResourceExpr()).isEqualTo(assignmentExpr);
  }

  @Test
  public void validTryCatchStatement_sampleCode() {
    Reference exceptionReference = ConcreteReference.withClazz(IllegalArgumentException.class);
    TypeNode type = TypeNode.withReference(exceptionReference);
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(createVariable("e", type)).setIsDecl(true).build();

    TryCatchStatement tryCatch =
        TryCatchStatement.builder()
            .setTryBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr())))
            .setIsSampleCode(true)
            .build();
    assertThat(tryCatch.catchVariableExpr()).isNull();
  }

  @Test
  public void invalidTryCatchStatement_missingCatchVariable() {
    Reference exceptionReference = ConcreteReference.withClazz(IllegalArgumentException.class);
    TypeNode type = TypeNode.withReference(exceptionReference);
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(createVariable("e", type)).setIsDecl(true).build();

    assertThrows(
        NullPointerException.class,
        () -> {
          TryCatchStatement.builder()
              .setTryBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr())))
              .build();
        });
  }

  @Test
  public void invalidTryCatchStatement_catchVariableNotDecl() {
    Reference exceptionReference = ConcreteReference.withClazz(IllegalArgumentException.class);
    TypeNode type = TypeNode.withReference(exceptionReference);
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(createVariable("e", type)).build();

    assertThrows(
        IllegalStateException.class,
        () -> {
          TryCatchStatement tryCatch =
              TryCatchStatement.builder()
                  .setTryBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr())))
                  .setCatchVariableExpr(variableExpr)
                  .build();
        });
  }

  @Test
  public void invalidTryCatchStatement_nonExceptionReference() {
    Reference exceptionReference = ConcreteReference.withClazz(Integer.class);
    TypeNode type = TypeNode.withReference(exceptionReference);
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(createVariable("e", type)).build();

    assertThrows(
        IllegalStateException.class,
        () -> {
          TryCatchStatement tryCatch =
              TryCatchStatement.builder()
                  .setTryBody(Arrays.asList(ExprStatement.withExpr(createAssignmentExpr())))
                  .setCatchVariableExpr(variableExpr)
                  .build();
        });
  }

  private static AssignmentExpr createAssignmentExpr() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Variable anotherVariable = Variable.builder().setName("y").setType(TypeNode.INT).build();
    Expr valueExpr = VariableExpr.builder().setVariable(anotherVariable).build();

    return AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();
  }

  private static Variable createVariable(String variableName, TypeNode type) {
    return Variable.builder().setName(variableName).setType(type).build();
  }
}
