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
import java.util.HashMap;
import org.junit.Test;

public class VariableExprTest {
  @Test
  public void validVariableExpr_basic() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    assertThat(variableExpr.variable()).isEqualTo(variable);
    assertThat(variableExpr.type()).isEqualTo(TypeNode.INT);
    assertThat(variableExpr.isDecl()).isFalse();
    assertThat(variableExpr.isFinal()).isFalse();
    assertThat(variableExpr.isStatic()).isFalse();
    assertThat(variableExpr.scope()).isEqualTo(ScopeNode.LOCAL);
  }

  @Test
  public void validVariableExpr_staticReference() {
    VariableExpr.builder()
        .setVariable(Variable.builder().setType(TypeNode.INT).setName("MAX_VALUE").build())
        .setStaticReferenceType(TypeNode.INT_OBJECT)
        .build();
  }

  @Test
  public void validVariableExpr_withFields() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING).build();
    VariableExpr variableExpr =
        VariableExpr.builder()
            .setVariable(variable)
            .setIsFinal(true)
            .setIsStatic(true)
            .setScope(ScopeNode.PRIVATE)
            .build();
    assertThat(variableExpr.variable()).isEqualTo(variable);
    assertThat(variableExpr.type()).isEqualTo(TypeNode.STRING);
    assertThat(variableExpr.isDecl()).isFalse();
    assertThat(variableExpr.isFinal()).isTrue();
    assertThat(variableExpr.isStatic()).isTrue();
    assertThat(variableExpr.scope()).isEqualTo(ScopeNode.PRIVATE);
  }

  @Test
  public void validVariableExpr_declaration() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.BOOLEAN).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();
    assertThat(variableExpr.variable()).isEqualTo(variable);
    assertThat(variableExpr.type()).isEqualTo(TypeNode.VOID);
    assertThat(variableExpr.isDecl()).isTrue();
  }

  @Test
  public void validVariableExpr_volatileDeclaration() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.BOOLEAN).build();
    VariableExpr variableExpr =
        VariableExpr.builder()
            .setVariable(variable)
            .setIsDecl(true)
            .setIsVolatile(true)
            .setIsStatic(true)
            .setIsFinal(true)
            .build();
    assertThat(variableExpr.variable()).isEqualTo(variable);
    assertThat(variableExpr.type()).isEqualTo(TypeNode.VOID);
    assertThat(variableExpr.isDecl()).isTrue();
    assertThat(variableExpr.isStatic()).isTrue();
    assertThat(variableExpr.isFinal()).isTrue();
    assertThat(variableExpr.isVolatile()).isTrue();
  }

  @Test
  public void validVariableExpr_reference() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING_ARRAY).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();

    Variable subVariable = Variable.builder().setName("length").setType(TypeNode.INT).build();
    VariableExpr.builder().setVariable(subVariable).setExprReferenceExpr(variableExpr).build();
    // No exception thrown, we're good.
  }

  @Test
  public void validVariableExpr_referenceWithModifiersSet() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING_ARRAY).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();

    Variable subVariable = Variable.builder().setName("length").setType(TypeNode.INT).build();
    VariableExpr.builder()
        .setVariable(subVariable)
        .setExprReferenceExpr(variableExpr)
        .setIsStatic(true)
        .setIsFinal(true)
        .setScope(ScopeNode.PUBLIC)
        .build();
    // No exception thrown, we're good.
  }

  @Test
  public void validVariableExpr_templatedArgInMethod() {
    Variable variable =
        Variable.builder()
            .setName("x")
            .setType(TypeNode.withReference(ConcreteReference.withClazz(HashMap.class)))
            .build();
    VariableExpr.builder()
        .setVariable(variable)
        .setTemplateObjects(Arrays.asList("RequestT", "ResponseT"))
        .build();
    // No exception thrown, we're good.
  }

  @Test
  public void validVariableExpr_templatedArgNameAndTypeInMethod() {
    Variable variable =
        Variable.builder()
            .setName("x")
            .setType(TypeNode.withReference(ConcreteReference.withClazz(HashMap.class)))
            .build();
    VariableExpr varExpr =
        VariableExpr.builder()
            .setVariable(variable)
            .setTemplateObjects(Arrays.asList("RequestT", TypeNode.STRING))
            .build();
    assertThat(varExpr.templateNodes())
        .containsExactly(IdentifierNode.withName("RequestT"), TypeNode.STRING);
  }

  @Test
  public void invalidVariableExpr_templatedArgInMethodHasNonStringNonTypeNodeObject() {
    Variable variable =
        Variable.builder()
            .setName("x")
            .setType(TypeNode.withReference(ConcreteReference.withClazz(HashMap.class)))
            .build();
    assertThrows(
        IllegalStateException.class,
        () ->
            VariableExpr.builder()
                .setVariable(variable)
                .setTemplateObjects(Arrays.asList("RequestT", new Integer(123), TypeNode.STRING))
                .build());
  }

  @Test
  public void invalidVariableExpr_badTemplateName() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING_ARRAY).build();
    assertThrows(
        IdentifierNode.InvalidIdentifierException.class,
        () ->
            VariableExpr.builder()
                .setVariable(variable)
                .setTemplateObjects(Arrays.asList("RequestT", "123T"))
                .build());
  }

  @Test
  public void invalidVariableExpr_referencePrimitiveType() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();

    Variable subVariable = Variable.builder().setName("length").setType(TypeNode.INT).build();
    assertThrows(
        IllegalStateException.class,
        () ->
            VariableExpr.builder()
                .setVariable(subVariable)
                .setExprReferenceExpr(variableExpr)
                .build());
  }

  @Test
  public void invalidVariableExpr_referenceAndDecl() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING_ARRAY).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();

    Variable subVariable = Variable.builder().setName("length").setType(TypeNode.INT).build();
    assertThrows(
        IllegalStateException.class,
        () ->
            VariableExpr.builder()
                .setVariable(subVariable)
                .setIsDecl(true)
                .setExprReferenceExpr(variableExpr)
                .build());
  }

  @Test
  public void invalidVariableExpr_exprAndStaticReference() {
    Variable refVariable = Variable.builder().setName("x").setType(TypeNode.STRING_ARRAY).build();
    assertThrows(
        IllegalStateException.class,
        () ->
            VariableExpr.builder()
                .setVariable(Variable.builder().setType(TypeNode.INT).setName("MAX_VALUE").build())
                .setExprReferenceExpr(VariableExpr.withVariable(refVariable))
                .setStaticReferenceType(TypeNode.INT_OBJECT)
                .build());
  }

  @Test
  public void invalidVariableExpr_primitiveStaticReference() {
    assertThrows(
        IllegalStateException.class,
        () ->
            VariableExpr.builder()
                .setVariable(Variable.builder().setType(TypeNode.INT).setName("MAX_VALUE").build())
                .setStaticReferenceType(TypeNode.INT)
                .build());
  }
}
