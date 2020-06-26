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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTMLWriter;

import com.google.api.generator.engine.ast.AnnotationNode;
import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.AstNodeVisitor;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.ForStatement;
import com.google.api.generator.engine.ast.IdentifierNode;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.JavaDocComment;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TryCatchStatement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.TypeNode.TypeKind;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.ast.WhileStatement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JavaWriterVisitor implements AstNodeVisitor {
  private static final String SPACE = " ";
  private static final String NEWLINE = "\n";

  private static final String AT = "@";

  private static final String COLON = ":";
  private static final String COMMA = ",";
  private static final String DOT = ".";
  private static final String EQUALS = "=";
  private static final String LEFT_ANGLE = "<";
  private static final String LEFT_BRACE = "{";
  private static final String LEFT_PAREN = "(";
  private static final String RIGHT_ANGLE = ">";
  private static final String RIGHT_BRACE = "}";
  private static final String RIGHT_PAREN = ")";
  private static final String SEMICOLON = ";";

  private static final String ABSTRACT = "abstract";
  private static final String CATCH = "catch";
  private static final String CLASS = "class";
  private static final String ELSE = "else";
  private static final String EXTENDS = "extends";
  private static final String FINAL = "final";
  private static final String FOR = "for";
  private static final String IF = "if";
  private static final String IMPLEMENTS = "implements";
  private static final String RETURN = "return";
  private static final String STATIC = "static";
  private static final String THROWS = "throws";
  private static final String TRY = "try";
  private static final String WHILE = "while";

  private final StringBuffer buffer = new StringBuffer();
  public JavaWriterVisitor() {}

  public void clear() {
    buffer.setLength(0);
  }

  public String write() {
    return buffer.toString();
  }

  @Override
  public void visit(IdentifierNode identifier) {
    buffer.append(identifier.name());
  }

  @Override
  public void visit(TypeNode type) {
    TypeKind typeKind = type.typeKind();
    StringBuilder generatedCodeBuilder = new StringBuilder();
    if (type.isPrimitiveType()) {
      generatedCodeBuilder.append(typeKind.toString().toLowerCase());
    } else {
      // A null pointer exception will be thrown if reference is null, which is WAI.
      generatedCodeBuilder.append(type.reference().name());
    }

    if (type.isArray()) {
      generatedCodeBuilder.append("[]");
    }

    buffer.append(generatedCodeBuilder.toString());
  }

  @Override
  public void visit(ScopeNode scope) {
    buffer.append(scope.toString());
  }

  @Override
  public void visit(AnnotationNode annotation) {
    buffer.append(AT);
    annotation.type().accept(this);
    if (annotation.description() != null && !annotation.description().isEmpty()) {
      buffer.append(String.format("(\"%s\")", annotation.description()));
    }
    newline();
  }

  /** =============================== EXPRESSIONS =============================== */
  @Override
  public void visit(ValueExpr valueExpr) {
    buffer.append(valueExpr.value().toString());
  }

  @Override
  public void visit(VariableExpr variableExpr) {
    Variable variable = variableExpr.variable();
    TypeNode type = variable.type();
    ScopeNode scope = variableExpr.scope();

    if (variableExpr.isDecl()) {
      if (!scope.equals(ScopeNode.LOCAL)) {
        scope.accept(this);
        space();
      }

      if (variableExpr.isStatic()) {
        buffer.append(STATIC);
        space();
      }

      if (variableExpr.isFinal()) {
        buffer.append(FINAL);
        space();
      }

      type.accept(this);
      space();
    }

    variable.identifier().accept(this);
  }

  @Override
  public void visit(AssignmentExpr assignmentExpr) {
    assignmentExpr.variableExpr().accept(this);
    space();
    buffer.append(EQUALS);
    space();
    assignmentExpr.valueExpr().accept(this);
  }

  @Override
  public void visit(MethodInvocationExpr methodInvocationExpr) {
    // Expression or static reference.
    if (methodInvocationExpr.exprReferenceExpr() != null) {
      methodInvocationExpr.exprReferenceExpr().accept(this);
      buffer.append(DOT);
    } else if (methodInvocationExpr.staticReferenceIdentifier() != null) {
      methodInvocationExpr.staticReferenceIdentifier().accept(this);
      buffer.append(DOT);
    }

    if (methodInvocationExpr.isGeneric()) {
      buffer.append(LEFT_ANGLE);
      int numGenerics = methodInvocationExpr.generics().size();
      for (int i = 0; i < numGenerics; i++) {
        buffer.append(methodInvocationExpr.generics().get(i).name());
        if (i < numGenerics - 1) {
          buffer.append(COMMA);
          space();
        }
      }
      buffer.append(RIGHT_ANGLE);
    }

    methodInvocationExpr.methodIdentifier().accept(this);
    leftParen();
    int numArguments = methodInvocationExpr.arguments().size();
    for (int i = 0; i < numArguments; i++) {
      Expr argExpr = methodInvocationExpr.arguments().get(i);
      argExpr.accept(this);
      if (i < numArguments - 1) {
        buffer.append(COMMA);
        space();
      }
    }
    rightParen();
  }

  /** =============================== STATEMENTS =============================== */
  @Override
  public void visit(ExprStatement exprStatement) {
    exprStatement.expression().accept(this);
    semicolon();
    newline();
  }

  @Override
  public void visit(WhileStatement whileStatement) {
    buffer.append(WHILE);
    space();
    leftParen();
    whileStatement.conditionExpr().accept(this);
    rightParen();
    space();
    leftBrace();
    newline();
    statements(whileStatement.body());
    rightBrace();
    newline();
  }

  @Override
  public void visit(IfStatement ifStatement) {
    buffer.append(IF);
    space();
    leftParen();

    ifStatement.conditionExpr().accept(this);
    rightParen();
    space();
    leftBrace();
    newline();

    statements(ifStatement.body());
    buffer.append(RIGHT_BRACE);
    if (!ifStatement.elseIfs().isEmpty()) {
      for (Map.Entry<Expr, List<Statement>> elseIfEntry : ifStatement.elseIfs().entrySet()) {
        Expr elseIfConditionExpr = elseIfEntry.getKey();
        List<Statement> elseIfBody = elseIfEntry.getValue();
        space();
        buffer.append(ELSE);
        space();
        buffer.append(IF);
        space();
        leftParen();
        elseIfConditionExpr.accept(this);
        rightParen();
        space();
        leftBrace();
        newline();
        statements(elseIfBody);
        rightBrace();
      }
    }
    if (!ifStatement.elseBody().isEmpty()) {
      space();
      buffer.append(ELSE);
      space();
      leftBrace();
      newline();
      statements(ifStatement.elseBody());
      rightBrace();
    }
    newline();
  }

  @Override
  public void visit(ForStatement forStatement) {
    buffer.append(FOR);
    space();
    leftParen();
    forStatement.localVariableExpr().accept(this);
    space();
    buffer.append(COLON);
    space();
    forStatement.collectionExpr().accept(this);
    rightParen();
    space();
    leftBrace();
    newline();
    statements(forStatement.body());
    rightBrace();
    newline();
  }

  @Override
  public void visit(TryCatchStatement tryCatchStatement) {
    buffer.append(TRY);
    space();
    if (tryCatchStatement.tryResourceExpr() != null) {
      leftParen();
      tryCatchStatement.tryResourceExpr().accept(this);
      rightParen();
      space();
    }
    leftBrace();
    newline();

    statements(tryCatchStatement.tryBody());
    rightBrace();

    if (tryCatchStatement.catchVariableExpr() != null) {
      space();
      buffer.append(CATCH);
      space();
      leftParen();
      tryCatchStatement.catchVariableExpr().accept(this);
      rightParen();
      space();
      leftBrace();
      newline();
      statements(tryCatchStatement.catchBody());
      rightBrace();
    }
    newline();
  }

  /** =============================== COMMENT =============================== */
  public String visit(LineComment lineComment){
      // Split comments by new line and add `//` to each line.
      return JavaFormatter.getInstance().format(String.format("// %s", String.join("\n//", lineComment.comment().split("\\r?\\n"))));
  }

  public String visit(BlockComment blockComment){
    // Split comments by new line and embrace the comment block with `/** */`.
    String sourceString = blockComment.comment();
    return JavaFormatter.getInstance().format(String.format("%s%s%s", "/** ", sourceString, " */"));
  }

  public String visit(JavaDocComment javaDocComment){
    String comment = javaDocComment.comment();
    return JavaFormatter.getInstance().format(comment);
  }

  /** =============================== OTHER =============================== */
  @Override
  public void visit(MethodDefinition methodDefinition) {
    // Annotations, if any.
    annotations(methodDefinition.annotations());

    // Method scope.
    methodDefinition.scope().accept(this);
    space();

    // Modifiers.

    if (methodDefinition.isAbstract()) {
      buffer.append(ABSTRACT);
      space();
    }
    if (methodDefinition.isStatic()) {
      buffer.append(STATIC);
      space();
    }
    if (methodDefinition.isFinal()) {
      buffer.append(FINAL);
      space();
    }
    methodDefinition.returnType().accept(this);
    space();

    // Method name.
    methodDefinition.methodIdentifier().accept(this);
    leftParen();

    // Arguments, if any.
    int numArguments = methodDefinition.arguments().size();
    for (int i = 0; i < numArguments; i++) {
      methodDefinition.arguments().get(i).accept(this);
      if (i < numArguments - 1) {
        buffer.append(COMMA);
        space();
      }
    }
    rightParen();

    // Thrown exceptions.
    if (!methodDefinition.throwsExceptions().isEmpty()) {
      space();
      buffer.append(THROWS);
      space();

      int numExceptionsThrown = methodDefinition.throwsExceptions().size();
      Iterator<TypeNode> exceptionIter = methodDefinition.throwsExceptions().iterator();
      while (exceptionIter.hasNext()) {
        TypeNode exceptionType = exceptionIter.next();
        exceptionType.accept(this);
        if (exceptionIter.hasNext()) {
          buffer.append(COMMA);
          space();
        }
      }
    }

    if (methodDefinition.isAbstract() && methodDefinition.body().isEmpty()) {
      semicolon();
      newline();
      return;
    }

    // Method body.
    space();
    leftBrace();
    newline();
    statements(methodDefinition.body());
    if (methodDefinition.returnExpr() != null) {
      buffer.append(RETURN);
      space();
      methodDefinition.returnExpr().accept(this);
      semicolon();
      newline();
    }

    rightBrace();
    newline();
  }

  @Override
  public void visit(ClassDefinition classDefinition) {
    if (!classDefinition.isNested()) {
      buffer.append(String.format("package %s;", classDefinition.packageString()));
      newline();
      newline();
    }

    ImportWriterVisitor importWriterVisitor =
        new ImportWriterVisitor(classDefinition.packageString());
    classDefinition.accept(importWriterVisitor);
    buffer.append(importWriterVisitor.write());
 
    // Annotations, if any.
    annotations(classDefinition.annotations());

    // Comments, if any.
    // TODO(xiaozhenliu): Uncomment / update the lines below.
    // statements(classDefinition.comments());

    classDefinition.scope().accept(this);
    space();

    // Modifiers.
    if (classDefinition.isStatic()) {
      buffer.append(STATIC);
      space();
    }
    if (classDefinition.isFinal()) {
      buffer.append(FINAL);
      space();
    }
    if (classDefinition.isAbstract()) {
      buffer.append(ABSTRACT);
      space();
    }

    // Name, extends, implements.
    buffer.append(CLASS);
    space();
    classDefinition.classIdentifier().accept(this);
    space();
    if (classDefinition.extendsType() != null) {
      buffer.append(EXTENDS);
      space();
      classDefinition.extendsType().accept(this);
      space();
    }

    if (!classDefinition.implementsTypes().isEmpty()) {
      buffer.append(IMPLEMENTS);
      space();

      int numImplementsTypes = classDefinition.implementsTypes().size();
      for (int i = 0; i < numImplementsTypes; i++) {
        classDefinition.implementsTypes().get(i).accept(this);
        if (i < numImplementsTypes - 1) {
          buffer.append(COMMA);
        }
        space();
      }
    }

    // Class body.
    leftBrace();
    newline();

    statements(classDefinition.statements());
    methods(classDefinition.methods());
    classes(classDefinition.nestedClasses());

    rightBrace();
  }

  /** =============================== PRIVATE HELPERS =============================== */
  private void annotations(List<AnnotationNode> annotations) {
    for (AnnotationNode annotation : annotations) {
      annotation.accept(this);
    }
  }

  private void statements(List<Statement> statements) {
    for (Statement statement : statements) {
      statement.accept(this);
    }
  }

  private void methods(List<MethodDefinition> methods) {
    for (MethodDefinition method : methods) {
      method.accept(this);
    }
  }

  private void classes(List<ClassDefinition> classes) {
    if (!classes.isEmpty()) {
      newline();
    }
    for (ClassDefinition classDef : classes) {
      classDef.accept(this);
      newline();
    }
  }

  private void space() {
    buffer.append(SPACE);
  }

  private void newline() {
    buffer.append(NEWLINE);
  }

  private void leftParen() {
    buffer.append(LEFT_PAREN);
  }

  private void rightParen() {
    buffer.append(RIGHT_PAREN);
  }

  private void leftBrace() {
    buffer.append(LEFT_BRACE);
  }

  private void rightBrace() {
    buffer.append(RIGHT_BRACE);
  }

  private void semicolon() {
    buffer.append(SEMICOLON);
  }
}
