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

import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AnonymousClassExpr;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.BlockStatement;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.ConcreteReference;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.ForStatement;
import com.google.api.generator.engine.ast.IdentifierNode;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.InstanceofExpr;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.NullObjectValue;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.Reference;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.StringObjectValue;
import com.google.api.generator.engine.ast.TernaryExpr;
import com.google.api.generator.engine.ast.ThrowExpr;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Value;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.VaporReference;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.ast.WhileStatement;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
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
    assertEquals(writerVisitor.write(), idName);
  }

  @Test
  public void writePrimitiveType() {
    TypeNode intType = TypeNode.INT;
    assertThat(intType).isNotNull();
    intType.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "int");
  }

  @Test
  public void writePrimitiveArrayType() {
    TypeNode byteArrayType =
        TypeNode.builder().setTypeKind(TypeNode.TypeKind.BYTE).setIsArray(true).build();
    assertThat(byteArrayType).isNotNull();
    byteArrayType.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "byte[]");
  }

  @Test
  public void writeAnnotation_simple() {
    AnnotationNode annotation = AnnotationNode.OVERRIDE;
    annotation.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "@Override\n");
  }

  @Test
  public void writeAnnotation_withDescription() {
    AnnotationNode annotation = AnnotationNode.withSuppressWarnings("all");
    annotation.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "@SuppressWarnings(\"all\")\n");
  }

  /** =============================== EXPRESSIONS =============================== */
  @Test
  public void writeValueExpr() {
    Value value = PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build();
    ValueExpr valueExpr = ValueExpr.builder().setValue(value).build();
    valueExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "3");
  }

  @Test
  public void writeVariableExpr() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();

    variableExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "x");
  }

  @Test
  public void writeVariableExpr_nonDeclIgnoresModifiers() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.BOOLEAN).build();
    VariableExpr expr =
        VariableExpr.builder()
            .setVariable(variable)
            .setScope(ScopeNode.PUBLIC)
            .setIsStatic(true)
            .setIsFinal(true)
            .build();

    expr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "x");
  }

  @Test
  public void writeVariableExpr_basicLocalDecl() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr expr = VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    expr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "int x");
  }

  @Test
  public void writeVariableExpr_localFinalDecl() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.BOOLEAN).build();

    VariableExpr expr =
        VariableExpr.builder().setVariable(variable).setIsFinal(true).setIsDecl(true).build();

    expr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "final boolean x");
  }

  @Test
  public void writeVariableExpr_scopedDecl() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr expr =
        VariableExpr.builder()
            .setVariable(variable)
            .setScope(ScopeNode.PRIVATE)
            .setIsDecl(true)
            .build();

    expr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "private int x");
  }

  @Test
  public void writeVariableExpr_scopedStaticFinalDecl() {
    IdentifierNode identifier = IdentifierNode.builder().setName("x").build();
    Variable variable = Variable.builder().setName("x").setType(TypeNode.BOOLEAN).build();
    VariableExpr expr =
        VariableExpr.builder()
            .setVariable(variable)
            .setIsDecl(true)
            .setScope(ScopeNode.PUBLIC)
            .setIsStatic(true)
            .setIsFinal(true)
            .build();

    expr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "public static final boolean x");
  }

  @Test
  public void writeTernaryExpr_basic() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();

    Variable conditionVariable =
        Variable.builder().setName("condition").setType(TypeNode.BOOLEAN).build();
    VariableExpr conditionExpr = VariableExpr.builder().setVariable(conditionVariable).build();

    Value value1 = PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build();
    Expr thenExpr = ValueExpr.builder().setValue(value1).build();
    Value value2 = PrimitiveValue.builder().setType(TypeNode.INT).setValue("4").build();
    Expr elseExpr = ValueExpr.builder().setValue(value2).build();

    TernaryExpr ternaryExpr =
        TernaryExpr.builder()
            .setConditionExpr(conditionExpr)
            .setThenExpr(thenExpr)
            .setElseExpr(elseExpr)
            .build();
    ternaryExpr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("condition ? 3 : 4");
  }

  @Test
  public void writeAssignmentExpr_basicValue() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Value value = PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build();
    Expr valueExpr = ValueExpr.builder().setValue(value).build();

    AssignmentExpr assignExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();

    assignExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "int x = 3");
  }

  @Test
  public void writeAssignmentExpr_varToVar() {
    Variable variable = Variable.builder().setName("foobar").setType(TypeNode.INT).build();
    VariableExpr variableExpr =
        VariableExpr.builder()
            .setVariable(variable)
            .setIsStatic(true)
            .setIsFinal(true)
            .setScope(ScopeNode.PRIVATE)
            .setIsDecl(true)
            .build();

    Variable anotherVariable = Variable.builder().setName("y").setType(TypeNode.INT).build();
    Expr valueExpr = VariableExpr.builder().setVariable(anotherVariable).build();

    AssignmentExpr assignExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();

    assignExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "private static final int foobar = y");
  }

  @Test
  public void writeAssignmentExpr_nullObjectValueReferenceType() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Value value = NullObjectValue.create();
    Expr valueExpr = ValueExpr.builder().setValue(value).build();

    AssignmentExpr assignExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();

    assignExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "String x = null");
  }

  @Test
  public void writeStringObjectValue_basic() {
    Value value = StringObjectValue.withValue("test");
    Expr valueExpr = ValueExpr.builder().setValue(value).build();
    valueExpr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("\"test\"");
  }

  @Test
  public void writeAssignmentExpr_stringObjectValue() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING).build();
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(variable).setIsDecl(true).build();

    Value value = StringObjectValue.withValue("Hi! World. \n");
    Expr valueExpr = ValueExpr.builder().setValue(value).build();
    AssignmentExpr assignExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();

    assignExpr.accept(writerVisitor);
    assertThat(writerVisitor.write()).isEqualTo("String x = \"Hi! World. \\n\"");
  }

  @Test
  public void writeMethodInvocationExpr_basic() {
    MethodInvocationExpr methodExpr =
        MethodInvocationExpr.builder().setMethodName("foobar").build();

    methodExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "foobar()");
  }

  @Test
  public void writeMethodInvocationExpr_staticRef() {
    MethodInvocationExpr methodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("foobar")
            .setStaticReferenceName("SomeClass")
            .build();

    methodExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "SomeClass.foobar()");
  }

  @Test
  public void writeMethodInvocationExpr_genericWithArgs() {
    Reference mapReference =
        ConcreteReference.builder()
            .setClazz(HashMap.class)
            .setGenerics(
                Arrays.asList(
                    ConcreteReference.withClazz(String.class),
                    ConcreteReference.withClazz(Integer.class)))
            .build();
    Reference outerMapReference =
        ConcreteReference.builder()
            .setClazz(HashMap.class)
            .setGenerics(Arrays.asList(mapReference, mapReference))
            .build();

    Variable variable = Variable.builder().setType(TypeNode.INT).setName("anArg").build();
    VariableExpr varExpr = VariableExpr.builder().setVariable(variable).build();

    MethodInvocationExpr methodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("foobar")
            .setGenerics(
                Arrays.asList(
                    ConcreteReference.withClazz(String.class),
                    ConcreteReference.withClazz(Double.class),
                    outerMapReference))
            .setArguments(Arrays.asList(varExpr, varExpr, varExpr))
            .setExprReferenceExpr(varExpr)
            .setReturnType(TypeNode.STRING)
            .build();

    Variable lhsVariable = Variable.builder().setType(TypeNode.STRING).setName("someStr").build();
    VariableExpr lhsVarExpr =
        VariableExpr.builder().setVariable(lhsVariable).setIsDecl(true).setIsFinal(true).build();

    AssignmentExpr assignExpr =
        AssignmentExpr.builder().setVariableExpr(lhsVarExpr).setValueExpr(methodExpr).build();

    assignExpr.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        "final String someStr = anArg.<String, Double, HashMap<HashMap<String, Integer>,"
            + " HashMap<String, Integer>>>foobar(anArg, anArg, anArg)");
  }

  @Test
  public void writeMethodInvocationExpr_chained() {
    Variable variable = Variable.builder().setType(TypeNode.INT).setName("libraryClient").build();
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
    assertEquals(
        writerVisitor.write(), "libraryClient.streamBooksCallable().doAnotherThing().call()");
  }

  @Test
  public void writeAnonymousClassExpr_basic() {
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
      anonymousClassExpr.accept(writerVisitor);
      System.out.println(writerVisitor.write());
      assertEquals(writerVisitor.write(), "new Runnable() {\npublic void run() {\nboolean foobar = false;\n}\n}");
    }

  @Test
  public void writeAnonymousClassExpr_withStatementsMethods() {
    ConcreteReference ref = ConcreteReference.withClazz(Runnable.class);
    TypeNode type = TypeNode.withReference(ref);

    Variable variable = createVariable("s", TypeNode.STRING);
    VariableExpr variableExpr =
        VariableExpr.builder()
            .setScope(ScopeNode.PRIVATE)
            .setIsDecl(true)
            .setIsFinal(true)
            .setIsStatic(true)
            .setVariable(variable)
            .build();
    ValueExpr valueExpr = ValueExpr.builder().setValue(StringObjectValue.withValue("foo")).build();
    AssignmentExpr assignmentExpr =
        AssignmentExpr.builder().setVariableExpr(variableExpr).setValueExpr(valueExpr).build();
    ExprStatement exprStatement = ExprStatement.withExpr(assignmentExpr);

    MethodDefinition methodDefinition =
        MethodDefinition.builder()
            .setName("run")
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .setBody(
                Arrays.asList(ExprStatement.withExpr(createAssignmentExpr("x", "3", TypeNode.INT))))
            .build();
    AnonymousClassExpr anonymousClassExpr =
        AnonymousClassExpr.builder()
            .setType(type)
            .setStatements(Arrays.asList(exprStatement))
            .setMethods(Arrays.asList(methodDefinition))
            .build();
    anonymousClassExpr.accept(writerVisitor);
    String expected =
        "new Runnable() {\nprivate static final String s = \"foo\";\npublic void run() {\nint x = 3;\n}\n}";
    assertEquals(writerVisitor.write(), expected);
  }
  
  @Test
  public void writeThrowExpr_basic() {
    TypeNode npeType =
        TypeNode.withReference(ConcreteReference.withClazz(NullPointerException.class));
    ThrowExpr throwExpr = ThrowExpr.builder().setType(npeType).build();
    throwExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "throw new NullPointerException()");
  }

  @Test
  public void writeThrowExpr_basicWithMessage() {
    TypeNode npeType =
        TypeNode.withReference(ConcreteReference.withClazz(NullPointerException.class));
    String message = "Some message asdf";
    ThrowExpr throwExpr = ThrowExpr.builder().setType(npeType).setMessage(message).build();
    throwExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "throw new NullPointerException(\"Some message asdf\")");
  }

  @Test
  public void writeInstanceofExpr() {
    Variable variable = Variable.builder().setName("x").setType(TypeNode.STRING).build();
    VariableExpr variableExpr = VariableExpr.builder().setVariable(variable).build();
    InstanceofExpr instanceofExpr =
        InstanceofExpr.builder().setCheckType(TypeNode.STRING).setExpr(variableExpr).build();
    instanceofExpr.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "x instanceof String");
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
    assertEquals(writerVisitor.write(), "SomeClass.foobar();\n");
  }

  @Test
  public void writeBlockStatement_empty() {
    BlockStatement blockStatement = BlockStatement.builder().build();
    blockStatement.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "{\n}\n");
  }

  @Test
  public void writeBlockStatement_simple() {
    MethodInvocationExpr methodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("foobar")
            .setStaticReferenceName("SomeClass")
            .build();
    BlockStatement blockStatement =
        BlockStatement.builder().setBody(Arrays.asList(ExprStatement.withExpr(methodExpr))).build();

    blockStatement.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "{\nSomeClass.foobar();\n}\n");
  }

  @Test
  public void writeBlockStatement_static() {
    MethodInvocationExpr methodExpr =
        MethodInvocationExpr.builder()
            .setMethodName("foobar")
            .setStaticReferenceName("SomeClass")
            .build();
    BlockStatement blockStatement =
        BlockStatement.builder()
            .setIsStatic(true)
            .setBody(
                Arrays.asList(
                    ExprStatement.withExpr(methodExpr), ExprStatement.withExpr(methodExpr)))
            .build();

    blockStatement.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "static {\nSomeClass.foobar();\nSomeClass.foobar();\n}\n");
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
    assertEquals(
        writerVisitor.write(),
        String.format("%s%s%s%s", "if (condition) {\n", "int x = 3;\n", "int x = 3;\n", "}\n"));
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
            "}\n"));
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
    String expected =
        String.format(
            createLines(13),
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
            "}\n");

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

    String expected =
        String.format(
            createLines(17),
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
            "}\n",
            "}\n",
            "} else if (lookAtMe) {\n",
            "int x = 3;\n",
            "boolean fooBar = true;\n",
            "}\n",
            "}\n");
    assertEquals(writerVisitor.write(), expected);
  }

  @Test
  public void writeWhileStatement_simple() {
    AssignmentExpr assignExpr = createAssignmentExpr("x", "3", TypeNode.INT);
    Statement assignExprStatement = ExprStatement.withExpr(assignExpr);
    List<Statement> whileBody = Arrays.asList(assignExprStatement, assignExprStatement);
    VariableExpr condExpr = createVariableExpr("condition", TypeNode.BOOLEAN);

    WhileStatement whileStatement =
        WhileStatement.builder().setConditionExpr(condExpr).setBody(whileBody).build();

    whileStatement.accept(writerVisitor);
    assertThat(writerVisitor.write())
        .isEqualTo(
            String.format(
                "%s%s%s%s", "while (condition) {\n", "int x = 3;\n", "int x = 3;\n", "}\n"));
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
            "for (String str : getSomeStrings()) {\n", "int x = 3;\n", "int x = 3;\n", "}\n"));
  }

  @Test
  public void writeTryCatchStatement_simple() {
    Reference exceptionReference = ConcreteReference.withClazz(IllegalArgumentException.class);
    TypeNode type = TypeNode.withReference(exceptionReference);
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(createVariable("e", type)).setIsDecl(true).build();

    TryCatchStatement tryCatch =
        TryCatchStatement.builder()
            .setTryBody(
                Arrays.asList(ExprStatement.withExpr(createAssignmentExpr("x", "3", TypeNode.INT))))
            .setCatchVariableExpr(variableExpr)
            .build();

    tryCatch.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            "%s%s%s%s",
            "try {\n", "int x = 3;\n", "} catch (IllegalArgumentException e) {\n", "}\n"));
  }

  @Test
  public void writeTryCatchStatement_withResources() {
    Reference exceptionReference = ConcreteReference.withClazz(IllegalArgumentException.class);
    TypeNode type = TypeNode.withReference(exceptionReference);
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(createVariable("e", type)).setIsDecl(true).build();

    TryCatchStatement tryCatch =
        TryCatchStatement.builder()
            .setTryResourceExpr(createAssignmentExpr("aBool", "false", TypeNode.BOOLEAN))
            .setTryBody(
                Arrays.asList(ExprStatement.withExpr(createAssignmentExpr("y", "4", TypeNode.INT))))
            .setCatchVariableExpr(variableExpr)
            .setCatchBody(
                Arrays.asList(
                    ExprStatement.withExpr(createAssignmentExpr("foobar", "123", TypeNode.INT))))
            .build();

    tryCatch.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            "%s%s%s%s%s",
            "try (boolean aBool = false) {\n",
            "int y = 4;\n",
            "} catch (IllegalArgumentException e) {\n",
            "int foobar = 123;\n",
            "}\n"));
  }

  @Test
  public void writeTryCatchStatement_sampleCodeNoCatch() {
    Reference exceptionReference = ConcreteReference.withClazz(IllegalArgumentException.class);
    TypeNode type = TypeNode.withReference(exceptionReference);
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(createVariable("e", type)).setIsDecl(true).build();

    TryCatchStatement tryCatch =
        TryCatchStatement.builder()
            .setTryBody(
                Arrays.asList(ExprStatement.withExpr(createAssignmentExpr("x", "3", TypeNode.INT))))
            .setIsSampleCode(true)
            .build();

    tryCatch.accept(writerVisitor);
    assertEquals(writerVisitor.write(), String.format("%s%s%s", "try {\n", "int x = 3;\n", "}\n"));
  }

  @Test
  public void writeTryCatchStatement_sampleCodeWithCatch() {
    Reference exceptionReference = ConcreteReference.withClazz(IllegalArgumentException.class);
    TypeNode type = TypeNode.withReference(exceptionReference);
    VariableExpr variableExpr =
        VariableExpr.builder().setVariable(createVariable("e", type)).setIsDecl(true).build();

    TryCatchStatement tryCatch =
        TryCatchStatement.builder()
            .setIsSampleCode(true)
            .setTryResourceExpr(createAssignmentExpr("aBool", "false", TypeNode.BOOLEAN))
            .setTryBody(
                Arrays.asList(ExprStatement.withExpr(createAssignmentExpr("y", "4", TypeNode.INT))))
            .setCatchVariableExpr(variableExpr)
            .setCatchBody(
                Arrays.asList(
                    ExprStatement.withExpr(createAssignmentExpr("foobar", "123", TypeNode.INT))))
            .build();

    tryCatch.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            "%s%s%s%s%s",
            "try (boolean aBool = false) {\n",
            "int y = 4;\n",
            "} catch (IllegalArgumentException e) {\n",
            "int foobar = 123;\n",
            "}\n"));
  }

  @Test
  public void writeMethodDefinition_basic() {
    MethodDefinition methodDefinition =
        MethodDefinition.builder()
            .setName("close")
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .setBody(
                Arrays.asList(ExprStatement.withExpr(createAssignmentExpr("x", "3", TypeNode.INT))))
            .build();

    methodDefinition.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format("%s%s%s", "public void close() {\n", "int x = 3;\n", "}\n"));
  }

  @Test
  public void writeMethodDefinition_constructor() {
    TypeNode returnType =
        TypeNode.withReference(
            VaporReference.builder()
                .setName("LibrarySettings")
                .setPakkage("com.google.example.library.v1")
                .build());
    MethodDefinition methodDefinition =
        MethodDefinition.constructorBuilder()
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(returnType)
            .build();

    methodDefinition.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "public LibrarySettings() {\n}\n");
  }

  @Test
  public void writeMethodDefinition_basicEmptyBody() {
    MethodDefinition methodDefinition =
        MethodDefinition.builder()
            .setName("close")
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .build();

    methodDefinition.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "public void close() {\n}\n");
  }

  @Test
  public void writeMethodDefinition_basicAbstract() {
    MethodDefinition methodDefinition =
        MethodDefinition.builder()
            .setName("close")
            .setIsAbstract(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .setBody(
                Arrays.asList(ExprStatement.withExpr(createAssignmentExpr("x", "3", TypeNode.INT))))
            .build();

    methodDefinition.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format("%s%s%s", "public abstract void close() {\n", "int x = 3;\n", "}\n"));
  }

  @Test
  public void writeMethodDefinition_basicAbstractEmptyBody() {
    MethodDefinition methodDefinition =
        MethodDefinition.builder()
            .setName("close")
            .setIsAbstract(true)
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .build();

    methodDefinition.accept(writerVisitor);
    assertEquals(writerVisitor.write(), "public abstract void close();\n");
  }

  @Test
  public void writeMethodDefinition_withArgumentsAndReturnExpr() {
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
    MethodDefinition methodDefinition =
        MethodDefinition.builder()
            .setName("close")
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.INT)
            .setArguments(arguments)
            .setReturnExpr(returnExpr)
            .setBody(
                Arrays.asList(
                    ExprStatement.withExpr(
                        createAssignmentExpr("foobar", "false", TypeNode.BOOLEAN))))
            .build();

    methodDefinition.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            "%s%s%s%s",
            "public int close(int x, int y) {\n",
            "boolean foobar = false;\n",
            "return 3;\n",
            "}\n"));
  }

  @Test
  public void writeMethodDefinition_withAnnotationsAndThrows() {
    ValueExpr returnExpr =
        ValueExpr.builder()
            .setValue(PrimitiveValue.builder().setType(TypeNode.INT).setValue("3").build())
            .build();
    List<VariableExpr> arguments =
        Arrays.asList(
            VariableExpr.builder()
                .setVariable(createVariable("valOne", TypeNode.STRING))
                .setIsDecl(true)
                .build(),
            VariableExpr.builder()
                .setVariable(createVariable("valTwo", TypeNode.BOOLEAN))
                .setIsDecl(true)
                .build());
    MethodDefinition methodDefinition =
        MethodDefinition.builder()
            .setName("close")
            .setIsOverride(true)
            .setIsFinal(true)
            .setIsStatic(true)
            .setScope(ScopeNode.PROTECTED)
            .setReturnType(TypeNode.INT)
            .setThrowsExceptions(
                Arrays.asList(
                    TypeNode.withExceptionClazz(IOException.class),
                    TypeNode.withExceptionClazz(TimeoutException.class),
                    TypeNode.withExceptionClazz(InterruptedException.class)))
            .setArguments(arguments)
            .setReturnExpr(returnExpr)
            .setAnnotations(
                Arrays.asList(
                    AnnotationNode.withSuppressWarnings("all"), AnnotationNode.DEPRECATED))
            .setBody(
                Arrays.asList(
                    createForStatement(),
                    ExprStatement.withExpr(
                        createAssignmentExpr("foobar", "false", TypeNode.BOOLEAN))))
            .build();

    methodDefinition.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(10),
            "@SuppressWarnings(\"all\")\n",
            "@Deprecated\n",
            "@Override\n",
            "protected static final int close(String valOne, boolean valTwo) throws"
                + " IOException, TimeoutException, InterruptedException {\n",
            "for (String str : getSomeStrings()) {\n",
            "boolean aBool = false;\n",
            "}\n",
            "boolean foobar = false;\n",
            "return 3;\n",
            "}\n"));
  }

  @Test
  public void writeClassDefinition_basic() {
    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString("com.google.example.library.v1.stub")
            .setName("LibraryServiceStub")
            .setScope(ScopeNode.PUBLIC)
            .build();

    classDef.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(4),
            "package com.google.example.library.v1.stub;\n",
            "\n",
            "public class LibraryServiceStub {\n",
            "}"));
  }

  @Test
  public void writeClassDefinition_withAnnotationsExtendsAndImplements() {
    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString("com.google.example.library.v1.stub")
            .setName("LibraryServiceStub")
            .setScope(ScopeNode.PUBLIC)
            .setIsFinal(true)
            .setAnnotations(
                Arrays.asList(
                    AnnotationNode.DEPRECATED, AnnotationNode.withSuppressWarnings("all")))
            .setExtendsType(TypeNode.STRING)
            .setImplementsTypes(
                Arrays.asList(
                    TypeNode.withReference(ConcreteReference.withClazz(Appendable.class)),
                    TypeNode.withReference(ConcreteReference.withClazz(Cloneable.class)),
                    TypeNode.withReference(ConcreteReference.withClazz(Readable.class))))
            .build();

    classDef.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(6),
            "package com.google.example.library.v1.stub;\n",
            "\n",
            "@Deprecated\n",
            "@SuppressWarnings(\"all\")\n",
            "public final class LibraryServiceStub extends String implements Appendable,"
                + " Cloneable, Readable {\n",
            "}"));
  }

  @Test
  public void writeClassDefinition_statementsAndMethods() {
    List<Reference> subGenerics =
        Arrays.asList(
            ConcreteReference.withClazz(String.class),
            ConcreteReference.withClazz(MethodDefinition.class));
    Reference mapEntryReference =
        ConcreteReference.builder().setClazz(Map.Entry.class).setGenerics(subGenerics).build();
    List<Reference> generics =
        Arrays.asList(ConcreteReference.withClazz(ClassDefinition.class), mapEntryReference);
    Reference mapReference =
        ConcreteReference.builder().setClazz(Map.class).setGenerics(generics).build();

    List<Statement> statements =
        Arrays.asList(
            ExprStatement.withExpr(
                VariableExpr.builder()
                    .setVariable(
                        createVariable(
                            "x",
                            TypeNode.withReference(
                                ConcreteReference.withClazz(AssignmentExpr.class))))
                    .setIsDecl(true)
                    .setScope(ScopeNode.PRIVATE)
                    .build()),
            ExprStatement.withExpr(
                VariableExpr.builder()
                    .setVariable(createVariable("y", TypeNode.withReference(mapReference)))
                    .setIsDecl(true)
                    .setScope(ScopeNode.PROTECTED)
                    .build()));

    MethodDefinition methodOne =
        MethodDefinition.builder()
            .setName("open")
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.BOOLEAN)
            .setReturnExpr(
                ValueExpr.builder()
                    .setValue(
                        PrimitiveValue.builder().setType(TypeNode.BOOLEAN).setValue("true").build())
                    .build())
            .build();

    MethodDefinition methodTwo =
        MethodDefinition.builder()
            .setName("close")
            .setScope(ScopeNode.PUBLIC)
            .setReturnType(TypeNode.VOID)
            .setBody(
                Arrays.asList(
                    ExprStatement.withExpr(
                        createAssignmentExpr("foobar", "false", TypeNode.BOOLEAN))))
            .build();

    List<MethodDefinition> methods = Arrays.asList(methodOne, methodTwo);

    ClassDefinition nestedClassDef =
        ClassDefinition.builder()
            .setName("IAmANestedClass")
            .setIsNested(true)
            .setScope(ScopeNode.PRIVATE)
            .setIsStatic(true)
            .setMethods(Arrays.asList(methodOne))
            .build();

    ClassDefinition classDef =
        ClassDefinition.builder()
            .setPackageString("com.google.example.library.v1.stub")
            .setName("LibraryServiceStub")
            .setScope(ScopeNode.PUBLIC)
            .setStatements(statements)
            .setMethods(methods)
            .setNestedClasses(Arrays.asList(nestedClassDef))
            .build();

    classDef.accept(writerVisitor);
    assertEquals(
        writerVisitor.write(),
        String.format(
            createLines(23),
            "package com.google.example.library.v1.stub;\n",
            "\n",
            "import com.google.api.generator.engine.ast.AssignmentExpr;\n",
            "import com.google.api.generator.engine.ast.ClassDefinition;\n",
            "import com.google.api.generator.engine.ast.MethodDefinition;\n",
            "import java.util.Map;\n",
            "\n",
            "public class LibraryServiceStub {\n",
            "private AssignmentExpr x;\n",
            "protected Map<ClassDefinition, Map.Entry<String, MethodDefinition>> y;\n",
            "public boolean open() {\n",
            "return true;\n",
            "}\n",
            "public void close() {\n",
            "boolean foobar = false;\n",
            "}\n",
            "\n",
            "private static class IAmANestedClass {\n",
            "public boolean open() {\n",
            "return true;\n",
            "}\n",
            "}\n",
            "}"));
  }

  private static String createLines(int numLines) {
    return new String(new char[numLines]).replace("\0", "%s");
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
    return VariableExpr.builder()
        .setVariable(createVariable(variableName, type))
        .setIsDecl(isDecl)
        .build();
  }

  private static Variable createVariable(String variableName, TypeNode type) {
    return Variable.builder().setName(variableName).setType(type).build();
  }

  private static ForStatement createForStatement() {
    Expr collectionExpr = MethodInvocationExpr.builder().setMethodName("getSomeStrings").build();
    ExprStatement assignExprStatement =
        ExprStatement.withExpr(createAssignmentExpr("aBool", "false", TypeNode.BOOLEAN));
    List<Statement> body = Arrays.asList(assignExprStatement);
    return ForStatement.builder()
        .setLocalVariableExpr(createVariableDeclExpr("str", TypeNode.STRING))
        .setCollectionExpr(collectionExpr)
        .setBody(body)
        .build();
  }
}
