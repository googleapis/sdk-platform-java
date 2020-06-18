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

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.AstNodeVisitor;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.IdentifierNode;
import com.google.api.generator.engine.ast.IfStatement;
import com.google.api.generator.engine.ast.MethodInvocationExpr;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.TypeNode.TypeKind;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import java.util.List;
import java.util.Map;

public class JavaWriterVisitor implements AstNodeVisitor {
  private static final String SPACE = " ";
  private static final String NEWLINE = "\n";

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
        buffer.append("static");
        space();
      }

      if (variableExpr.isFinal()) {
        buffer.append("final");
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
    buffer.append(" = ");
    assignmentExpr.valueExpr().accept(this);
  }

  @Override
  public void visit(MethodInvocationExpr methodInvocationExpr) {
    // Expression or static reference.
    if (methodInvocationExpr.exprReferenceExpr() != null) {
      methodInvocationExpr.exprReferenceExpr().accept(this);
      buffer.append(".");
    } else if (methodInvocationExpr.staticReferenceIdentifier() != null) {
      methodInvocationExpr.staticReferenceIdentifier().accept(this);
      buffer.append(".");
    }

    if (methodInvocationExpr.isGeneric()) {
      buffer.append("<");
      int numGenerics = methodInvocationExpr.generics().size();
      for (int i = 0; i < numGenerics; i++) {
        buffer.append(methodInvocationExpr.generics().get(i).name());
        if (i < numGenerics - 1) {
          buffer.append(", ");
        }
      }
      buffer.append(">");
    }

    methodInvocationExpr.methodIdentifier().accept(this);
    buffer.append("(");
    int numArguments = methodInvocationExpr.arguments().size();
    for (int i = 0; i < numArguments; i++) {
      Expr argExpr = methodInvocationExpr.arguments().get(i);
      argExpr.accept(this);
      if (i < numArguments - 1) {
        buffer.append(", ");
      }
    }
    buffer.append(")");
  }

  /** =============================== STATEMENTS =============================== */
  @Override
  public void visit(ExprStatement exprStatement) {
    exprStatement.expression().accept(this);
    buffer.append(";");
    newline();
  }

  @Override
  public void visit(IfStatement ifStatement) {
    buffer.append("if (");
    ifStatement.conditionExpr().accept(this);
    buffer.append(") {");
    newline();
    for (Statement statement : ifStatement.body()) {
      statement.accept(this);
    }
    buffer.append("} ");
    if (!ifStatement.elseIfs().isEmpty()) {
      for (Map.Entry<Expr, List<Statement>> elseIfEntry : ifStatement.elseIfs().entrySet()) {
        Expr elseIfConditionExpr = elseIfEntry.getKey();
        List<Statement> elseIfBody = elseIfEntry.getValue();
        buffer.append("else if (");
        elseIfConditionExpr.accept(this);
        buffer.append(") {");
        newline();
        for (Statement statement : elseIfBody) {
          statement.accept(this);
        }
        buffer.append("} ");
      }
    }
    if (!ifStatement.elseBody().isEmpty()) {
      buffer.append("else {");
      newline();
      for (Statement statement : ifStatement.elseBody()) {
        statement.accept(this);
      }
      buffer.append("} ");
    }
    newline();
  }

  /** =============================== PRIVATE HELPERS =============================== */
  private void space() {
    buffer.append(SPACE);
  }

  private void newline() {
    buffer.append(NEWLINE);
  }
}
