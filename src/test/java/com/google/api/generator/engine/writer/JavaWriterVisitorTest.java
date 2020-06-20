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
import static junit.framework.Assert.assertEquals;

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.ForStatement;
import com.google.api.generator.engine.ast.IdentifierNode;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Value;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    TypeNode byteArrayType =
        TypeNode.builder().setTypeKind(TypeNode.TypeKind.BYTE).setIsArray(true).build();
    assertThat(byteArrayType).isNotNull();
    byteArrayType.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("byte[]");
  }

  /** =============================== EXPRESSIONS =============================== */
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
  public void writeVariableExpr_nonDeclIgnoresModifiers() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable =
        Variable.builder().setIdentifier(identifier).setType(TypeNode.BOOLEAN).build();
    VariableExpr expr =
        VariableExpr.builder()
            .setVariable(variable)
            .setScope(ScopeNode.PUBLIC)
            .setIsStatic(true)
            .setIsFinal(true)
            .build();

    expr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("x");
  }

  @Test
  public void writeVariableExpr_basicLocalDecl() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable = Variable.builder().setIdentifier(identifier).setType(TypeNode.INT).build();
    VariableExpr expr = VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    expr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("int x");
  }

  @Test
  public void writeVariableExpr_localFinalDecl() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable =
        Variable.builder().setIdentifier(identifier).setType(TypeNode.BOOLEAN).build();

    VariableExpr expr =
        VariableExpr.builder().setVariable(variable).setIsFinal(true).setIsDecl(true).build();

    expr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("final boolean x");
  }

  @Test
  public void writeVariableExpr_scopedDecl() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable = Variable.builder().setIdentifier(identifier).setType(TypeNode.INT).build();
    VariableExpr expr =
        VariableExpr.builder()
            .setVariable(variable)
            .setScope(ScopeNode.PRIVATE)
            .setIsDecl(true)
            .build();

    expr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("private int x");
  }

  @Test
  public void writeVariableExpr_scopedStaticFinalDecl() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable =
        Variable.builder().setIdentifier(identifier).setType(TypeNode.BOOLEAN).build();
    VariableExpr expr =
        VariableExpr.builder()
            .setVariable(variable)
            .setIsDecl(true)
            .setScope(ScopeNode.PUBLIC)
            .setIsStatic(true)
            .setIsFinal(true)
            .build();

    expr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("public static final boolean x");
  }

  @Test
  public void writeAssignmentExpr_basicValue() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable = Variable.builder().setIdentifier(identifier).setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Value value = PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build();
    Expr valueExpr = ValueExpr.builder().setValue(value).build();

    AssignmentExpr assignExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();

    assignExpr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("int x = 3");
  }

  @Test
  public void writeAssignmentExpr_varToVar() {
    IdentifierNode identifier = IdentifierNode.builder().setName("foobar").build();
    Variable variable = Variable.builder().setIdentifier(identifier).setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder()
            .setVariable(variable)
            .setIsStatic(true)
            .setIsFinal(true)
            .setScope(ScopeNode.PRIVATE)
            .setIsDecl(true)
            .build();

    IdentifierNode anotherIdentifier = IdentifierNode.builder().setName("y").build();
    Variable anotherVariable =
        Variable.builder().setIdentifier(anotherIdentifier).setType(TypeNode.INT).build();
    Expr valueExpr = VariableExpr.builder().setVariable(anotherVariable).build();

    AssignmentExpr assignExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();

    assignExpr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("private static final int foobar = y");
  }

  @Test
  public void writeMethodInvocationExpr_basic() {
    MethodInvocationExpr methodExpr =
        MethodInvocationExpr.builder().setMethodName("foobar").build();

    methodExpr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("foobar()");
  }

  @Test
  public void writeMethodInvocationExpr_staticRef() {
    MethodInvocationExpr methodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("foobar")
            .setStaticReferenceName("SomeClass")
            .build();

    methodExpr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("SomeClass.foobar()");
  }

  @Test
  public void writeMethodInvocationExpr_genericWithArgs() {
    Reference mapReference =
        Reference.builder()
            .setClazz(HashMap.class)
            .setGenerics(
                Arrays.asList(
                    Reference.withClazz(String.class), Reference.withClazz(Integer.class)))
            .build();
    Reference outerMapReference =
        Reference.builder()
            .setClazz(HashMap.class)
            .setGenerics(Arrays.asList(mapReference, mapReference))
            .build();

    IdentifierNode identifier = IdentifierNode.builder().setName("anArg").build();
    Variable variable = Variable.builder().setType(TypeNode.INT).setIdentifier(identifier).build();
    VariableExpr varExpr = VariableExpr.builder().setVariable(variable).build();

    MethodInvocationExpr methodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("foobar")
            .setGenerics(
                Arrays.asList(
                    Reference.withClazz(String.class),
                    Reference.withClazz(Double.class),
                    outerMapReference))
            .setArguments(Arrays.asList(varExpr, varExpr, varExpr))
            .setExprReferenceExpr(varExpr)
            .setReturnType(TypeNode.STRING)
            .build();

    IdentifierNode lhsVarIdentifier = IdentifierNode.builder().setName("someStr").build();
    Variable lhsVariable =
        Variable.builder().setType(TypeNode.STRING).setIdentifier(lhsVarIdentifier).build();
    VariableExpr lhsVarExpr =
        VariableExpr.builder().setVariable(lhsVariable).setIsDecl(true).setIsFinal(true).build();

    AssignmentExpr assignExpr =
        AssignmentExpr.builder().setVariableExpr(lhsVarExpr).setValueExpr(methodExpr).build();

    assignExpr.accept(writerVisitor);
    assertThat(writerVisitor.write())
        .isEqualTo(
            "final String someStr = anArg.<String, Double, HashMap<HashMap<String, Integer>,"
                + " HashMap<String, Integer>>>foobar(anArg, anArg, anArg)");
  }

  @Test
  public void writeMethodInvocationExpr_chained() {
    IdentifierNode identifier = IdentifierNode.builder().setName("libraryClient").build();
    Variable variable = Variable.builder().setType(TypeNode.INT).setIdentifier(identifier).build();
    VariableExpr varExpr = VariableExpr.builder().setVariable(variable).build();

    MethodInvocationExpr firstMethodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("streamBooksCallable")
            .setExprReferenceExpr(varExpr)
            .build();
    MethodInvocationExpr secondMethodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("doAnotherThing")
            .setExprReferenceExpr(firstMethodExpr)
            .build();
    MethodInvocationExpr methodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("call")
            .setExprReferenceExpr(secondMethodExpr)
            .build();

    methodExpr.accept(writerVisitor);
    assertThat(writerVisitor.write())
        .isEqualTo("libraryClient.streamBooksCallable().doAnotherThing().call()");
  }

  /** =============================== STATEMENTS =============================== */
  @Test
  public void writeExprStatement() {
    MethodInvocationExpr methodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("foobar")
            .setStaticReferenceName("SomeClass")
            .build();
    ExprStatement exprStatement = ExprStatement.withExpr(methodExpr);

    exprStatement.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("SomeClass.foobar();\n");
  }

  @Test
  public void writeIfStatement_simple() {
    AssignmentExpr assignExpr = createAssignmentExpr("x", "3", TypeNode.INT);
    Statement assignExprStatement = ExprStatement.withExpr(assignExpr);
    List<Statement> ifBody = Arrays.asList(assignExprStatement, assignExprStatement);
    VariableExpr condExpr = createVariableExpr("condition", TypeNode.BOOLEAN);

    IfStatement ifStatement =
        IfStatement.builder().setConditionExpr(condExpr).setBody(ifBody).build();

    ifStatement.accept(writerVisitor);
    assertThat(writerVisitor.write())
        .isEqualTo(
            String.format(
                "%s%s%s%s", "if (condition) {\n", "int x = 3;\n", "int x = 3;\n", "} \n"));
  }

  @Test
  public void writeIfStatement_withElse() {
    AssignmentExpr assignExpr = createAssignmentExpr("x", "3", TypeNode.INT);
    Statement assignExprStatement = ExprStatement.withExpr(assignExpr);
    List<Statement> ifBody = Arrays.asList(assignExprStatement, assignExprStatement);
    VariableExpr condExpr = createVariableExpr("condition", TypeNode.BOOLEAN);

    IfStatement ifStatement =
        IfStatement.builder()
            .setConditionExpr(condExpr)
            .setBody(ifBody)
            .setElseBody(ifBody)
            .build();

    ifStatement.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            "%s%s%s" + "%s%s%s%s",
            "if (condition) {\n",
            "int x = 3;\n",
            "int x = 3;\n",
            "} else {\n",
            "int x = 3;\n",
            "int x = 3;\n",
            "} \n"));
  }

  @Test
  public void writeIfStatement_elseIfs() {
    List<Statement> ifBody =
        Arrays.asList(
            ExprStatement.withExpr(createAssignmentExpr("x", "3", TypeNode.INT)),
            ExprStatement.withExpr(createAssignmentExpr("fooBar", "true", TypeNode.BOOLEAN)));

    VariableExpr condExprOne = createVariableExpr("condition", TypeNode.BOOLEAN);
    VariableExpr condExprTwo = createVariableExpr("fooBarCheck", TypeNode.BOOLEAN);
    VariableExpr condExprThree = createVariableExpr("anotherCondition", TypeNode.BOOLEAN);
    VariableExpr condExprFour = createVariableExpr("lookAtMe", TypeNode.BOOLEAN);

    IfStatement ifStatement =
        IfStatement.builder()
            .setConditionExpr(condExprOne)
            .setBody(ifBody)
            .addElseIf(condExprTwo, ifBody)
            .addElseIf(condExprThree, ifBody)
            .addElseIf(condExprFour, ifBody)
            .build();

    ifStatement.accept(writerVisitor);
    String ifFormatStr = "%s%s%s";
    String expected =
        String.format(
            new String(new char[4]).replace("\0", ifFormatStr) + "%s",
            "if (condition) {\n",
            "int x = 3;\n",
            "boolean fooBar = true;\n",
            "} else if (fooBarCheck) {\n",
            "int x = 3;\n",
            "boolean fooBar = true;\n",
            "} else if (anotherCondition) {\n",
            "int x = 3;\n",
            "boolean fooBar = true;\n",
            "} else if (lookAtMe) {\n",
            "int x = 3;\n",
            "boolean fooBar = true;\n",
            "} \n");

    assertEquals(writerVisitor.write(), expected);
  }

  @Test
  public void writeIfStatement_nested() {
    List<Statement> ifBody =
        Arrays.asList(
            ExprStatement.withExpr(createAssignmentExpr("x", "3", TypeNode.INT)),
            ExprStatement.withExpr(createAssignmentExpr("fooBar", "true", TypeNode.BOOLEAN)));

    VariableExpr condExprOne = createVariableExpr("condition", TypeNode.BOOLEAN);
    VariableExpr condExprTwo = createVariableExpr("fooBarCheck", TypeNode.BOOLEAN);
    VariableExpr condExprThree = createVariableExpr("anotherCondition", TypeNode.BOOLEAN);
    VariableExpr condExprFour = createVariableExpr("lookAtMe", TypeNode.BOOLEAN);

    IfStatement nestedTwoIfStatement =
        IfStatement.builder()
            .setConditionExpr(condExprThree)
            .setBody(ifBody)
            .setElseBody(ifBody)
            .build();
    IfStatement nestedOneIfStatement =
        IfStatement.builder()
            .setConditionExpr(condExprTwo)
            .setBody(
                Arrays.asList(
                    ExprStatement.withExpr(createAssignmentExpr("anInt", "10", TypeNode.INT)),
                    nestedTwoIfStatement))
            .build();
    IfStatement nestedZeroIfStatement =
        IfStatement.builder()
            .setConditionExpr(condExprOne)
            .setBody(Arrays.asList(nestedOneIfStatement))
            .addElseIf(condExprFour, ifBody)
            .build();

    IfStatement ifStatement =
        IfStatement.builder()
            .setConditionExpr(condExprOne)
            .setBody(Arrays.asList(nestedZeroIfStatement))
            .build();

    ifStatement.accept(writerVisitor);

    int numLines = 17;
    String expected =
        String.format(
            new String(new char[numLines]).replace("\0", "%s"),
            "if (condition) {\n",
            "if (condition) {\n",
            "if (fooBarCheck) {\n",
            "int anInt = 10;\n",
            "if (anotherCondition) {\n",
            "int x = 3;\n",
            "boolean fooBar = true;\n",
            "} else {\n",
            "int x = 3;\n",
            "boolean fooBar = true;\n",
            "} \n",
            "} \n",
            "} else if (lookAtMe) {\n",
            "int x = 3;\n",
            "boolean fooBar = true;\n",
            "} \n",
            "} \n");
    assertEquals(writerVisitor.write(), expected);
  }

  @Test
  public void writeForStatement() {
    AssignmentExpr assignExpr = createAssignmentExpr("x", "3", TypeNode.INT);
    Statement assignExprStatement = ExprStatement.withExpr(assignExpr);
    List<Statement> body = Arrays.asList(assignExprStatement, assignExprStatement);

    VariableExpr varDeclExpr = createVariableDeclExpr("str", TypeNode.STRING);
    Expr collectionExpr = MethodInvocationExpr.builder().setMethodName("getSomeStrings").build();

    ForStatement forStatement =
        ForStatement.builder()
            .setLocalVariableExpr(varDeclExpr)
            .setCollectionExpr(collectionExpr)
            .setBody(body)
            .build();

    forStatement.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            "%s%s%s%s",
            "for (String str : getSomeStrings()) {\n", "int x = 3;\n", "int x = 3;\n", "} \n"));
  }

  private static AssignmentExpr createAssignmentExpr(
      String variableName, String value, TypeNode type) {
    VariableExpr variableExpr = createVariableDeclExpr(variableName, type);
    Value val = PrimitiveValue.builder().setType(type).setValue(value).build();
    Expr valueExpr = ValueExpr.builder().setValue(val).build();
    return AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();
  }

  private static VariableExpr createVariableExpr(String variableName, TypeNode type) {
    return createVariableExpr(variableName, type, false);
  }

  private static VariableExpr createVariableDeclExpr(String variableName, TypeNode type) {
    return createVariableExpr(variableName, type, true);
  }

  private static VariableExpr createVariableExpr(
      String variableName, TypeNode type, boolean isDecl) {
    IdentifierNode identifier = IdentifierNode.builder().setName(variableName).build();
    Variable variable = Variable.builder().setIdentifier(identifier).setType(type).build();
    return VariableExpr.builder().setVariable(variable).setIsDecl(isDecl).build();
  }
}
