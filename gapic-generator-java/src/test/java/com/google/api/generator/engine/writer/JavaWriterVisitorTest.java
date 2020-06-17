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

package com.google.api.generator.engine.writer;

import static com.google.common.truth.Truth.assertThat;

import com.google.api.generator.engine.ast.IdentifierNode;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Value;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableDeclExpr;
import com.google.api.generator.engine.ast.VariableExpr;
import org.junit.Before;
import org.junit.Test;

public class JavaWriterVisitorTest {
  private JavaWriterVisitor writerVisitor;

  @Before
  public void setUp() {
    writerVisitor = new JavaWriterVisitor();
  }

  @Test
  public void writeIdentifier() {

    String idName = "foobar";
    IdentifierNode.builder().setName(idName).build().accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo(idName);
  }

  @Test
  public void writePrimitiveType() {
    TypeNode intType = TypeNode.INT;
    assertThat(intType).isNotNull();
    intType.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("int");
  }

  @Test
  public void writePrimitiveArrayType() {
    TypeNode byteArrayType = TypeNode.createByteArrayType();
    assertThat(byteArrayType).isNotNull();
    byteArrayType.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("byte[]");
  }

  @Test
  public void writeValueExpr() {
    Value value = PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build();
    ValueExpr valueExpr = ValueExpr.builder().setValue(value).build();
    valueExpr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("3");
  }

  @Test
  public void writeVariableExpr() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable = Variable.builder().setIdentifier(identifier).setType(TypeNode.INT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();

    variableExpr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("x");
  }

  @Test
  public void writeVariableDeclExpr_basicLocalDecl() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable = Variable.builder().setIdentifier(identifier).setType(TypeNode.INT).build();
    VariableDeclExpr expr = VariableDeclExpr.builder().setVariable(variable).build();

    expr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("int x");
  }

  @Test
  public void writeVariableDeclExpr_localFinalDecl() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable =
        Variable.builder().setIdentifier(identifier).setType(TypeNode.BOOLEAN).build();

    VariableDeclExpr expr =
        VariableDeclExpr.builder().setVariable(variable).setIsFinal(true).build();

    expr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("final boolean x");
  }

  @Test
  public void writeVariableDeclExpr_scopedDecl() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable = Variable.builder().setIdentifier(identifier).setType(TypeNode.INT).build();
    VariableDeclExpr expr =
        VariableDeclExpr.builder().setVariable(variable).setScope(ScopeNode.PRIVATE).build();

    expr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("private int x");
  }

  @Test
  public void writeVariableDeclExpr_scopedStaticFinalDecl() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable =
        Variable.builder().setIdentifier(identifier).setType(TypeNode.BOOLEAN).build();
    VariableDeclExpr expr =
        VariableDeclExpr.builder()
            .setVariable(variable)
            .setScope(ScopeNode.PUBLIC)
            .setIsStatic(true)
            .setIsFinal(true)
            .build();

    expr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("public static final boolean x");
  }
}
