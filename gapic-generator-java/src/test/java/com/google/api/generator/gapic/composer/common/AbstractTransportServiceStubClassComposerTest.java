// Copyright 2024 Google LLC
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

package com.google.api.generator.gapic.composer.common;

import static org.junit.Assert.assertEquals;

import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.api.generator.gapic.model.Method;
import com.google.api.generator.test.utils.LineFormatter;
import org.junit.Before;
import org.junit.Test;

public class AbstractTransportServiceStubClassComposerTest {
  private JavaWriterVisitor writerVisitor;

  @Before
  public void setUp() {
    writerVisitor = new JavaWriterVisitor();
  }

  @Test
  public void createAutoPopulatedRequestStatement_sampleField() {
    Method METHOD =
        Method.builder()
            .setName("TestMethod")
            .setInputType(TypeNode.STRING)
            .setOutputType(TypeNode.STRING)
            .build();

    VariableExpr requestVarExpr =
        VariableExpr.withVariable(
            Variable.builder().setType(METHOD.inputType()).setName("request").build());

    Statement autoPopulatedFieldStatement =
        AbstractTransportServiceStubClassComposer.createAutoPopulatedRequestStatement(
            METHOD, requestVarExpr, "sampleField");

    autoPopulatedFieldStatement.accept(writerVisitor);
    String expected =
        LineFormatter.lines(
            "if (request.getSampleField() == null || request.getSampleField().isEmpty()) {\n",
            "request = request.toBuilder().setSampleField(UUID.randomUUID().toString()).build();\n",
            "}\n");
    assertEquals(expected, writerVisitor.write());
  }
}
