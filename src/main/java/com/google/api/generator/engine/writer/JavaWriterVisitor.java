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

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.AstNodeVisitor;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.IdentifierNode;
import com.google.api.generator.engine.ast.JavaDocComment;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.ReferenceTypeNode;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.TypeNode.TypeKind;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.ast.JavaDocComment.COMMENT_TYPE;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class JavaWriterVisitor implements AstNodeVisitor {
  private static final String SPACE = " ";

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
      // TODO(miraleung): Clean this up.
      visit(type.reference());
      return;
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
  public void visit(ReferenceTypeNode reference) {
    throw new RuntimeException("Not yet implemented for reference types");
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

  private void space() {
    buffer.append(SPACE);
  }

  /** =============================== COMMENT =============================== */
  public String visit(LineComment lineComment) throws Exception{
      // Split comments by new line and add `//` to each line.
      JavaFormatter formatter = JavaFormatter.getInstance();
      return formatter.format(String.format("// %s", String.join("\n//", lineComment.comment().split("\\r?\\n"))));
  }

  public String visit(BlockComment blockComment) throws Exception{
    // Split comments by new line and embrace the comment block with `/** */`.
    String sourceString = blockComment.comment();
    JavaFormatter formatter = JavaFormatter.getInstance();
    return formatter.format("/** " + sourceString + " */");
  }

  public String visit(JavaDocComment javaDocComment) throws Exception{
    StringBuilder formattedComment = new StringBuilder("/**\n");
    List<String> commentList = new ArrayList<>(javaDocComment.comments());
    Map<String, String> paramList = new HashMap<>(javaDocComment.params());
    List<String> sampleCodeList = new ArrayList<>(javaDocComment.sampleCode());
    List<String> html_p = new ArrayList<>(javaDocComment.html_p());
    List<List<String>> html_ol = new ArrayList<>(javaDocComment.html_ol());
    List<List<String>> html_ul = new ArrayList<>(javaDocComment.html_ol());
    List<COMMENT_TYPE> orderList = new ArrayList<>(JavaDocComment.commentList);

    for(COMMENT_TYPE type : orderList){
      switch(type) {
        case COMMENT:
          String comment = commentList.get(0);
          commentList.remove(0);
          formattedComment.append("* " + comment + "\n");
          break;
        case SAMPLE_CODE:
          String sample = sampleCodeList.get(0);
          sampleCodeList.remove(0);
          formattedComment.append("* Sample code:\n* <pre><code>\n");
          String[] sampleLines = sample.split("\\r?\\n");
          for (int i = 0; i < sampleLines.length; i++) {
            sampleLines[i] = "* " + sampleLines[i];
          }
          formattedComment.append(String.join("\n", sampleLines) + "\n* </code></pre>\n");
          break;
        case HTML_P:
          String p = html_p.get(0);
          html_p.remove(0);
          formattedComment.append("* <p> " + p + "\n");
          break;
        case HTML_OL:
          List<String> ol_items = html_ol.get(0);
          html_ol.remove(0);
          formattedComment.append("* <ol>\n");
          for (int i = 0; i < ol_items.size(); i++) {
            ol_items.set(i,  "* <li>" + ol_items.get(i) + "\n");
          }
          formattedComment.append(String.join("",ol_items) + "* </ol>\n");
          break;
        case HTML_UL:
          List<String> ul_items = html_ul.get(0);
          html_ul.remove(0);
          formattedComment.append("* <ul>\n");
          for (int i = 0; i < ul_items.size(); i++) {
            ul_items.set(i,  "* <li>" + ul_items.get(i) + "\n");
          }
          formattedComment.append(String.join("",ul_items) + "* </ul>\n");
          break;
      }
    }
    for (String name : paramList.keySet()) {
      formattedComment.append("* @param " + name + " " + paramList.get(name) + "\n");
    }
    if (javaDocComment.deprecated().isPresent()) {
      formattedComment.append("* @deprecated " + javaDocComment.deprecated() + "\n");
    }
    if (javaDocComment.throwsText().isPresent()) {
      formattedComment.append("* @throws " + javaDocComment.throwsText() + "\n");
    }
    JavaFormatter formatter = JavaFormatter.getInstance();
    return formatter.format(formattedComment.append("*/").toString());
  }

}
