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
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableDeclExpr;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.JavaDocComment;
import com.google.api.generator.engine.ast.JavaDocComment.ParamPair;
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

  @Test  
  public void writeNormalBlockComment() {
    String content = "this is a test comment";
    BlockComment blockComment = BlockComment.builder().setComment(content).build();
    String expected = "/** this is a test comment */\n";
    String formattedComment = blockComment.accept(writerVisitor);
    assertThat(formattedComment).isEqualTo(expected);
  }

  @Test
  public void writeNormalLineComment() {
    String content = "this is a test comment";
    LineComment lineComment = LineComment.builder().setComment(content).build();
    String expected = "// this is a test comment\n";
    String formattedComment = lineComment.accept(writerVisitor);
    assertThat(formattedComment).isEqualTo(expected);
  }

  @Test
  public void writeLongLineComment() {
    String content =
        "this is a long test comment with so many words, hello world, hello again, hello for 3 times, blah, blah!";
    LineComment lineComment = LineComment.builder().setComment(content).build();
    String expected =
        "// this is a long test comment with so many words, hello world, hello again, hello for 3 times,\n// blah, blah!\n";
    String formattedComment = lineComment.accept(writerVisitor);
    assertThat(formattedComment).isEqualTo(expected);
  }

  @Test
  public void writeNormalJavaDocComment() {
    String content = "this is a test comment";
    String deprecatedText = "Use the {@link ArchivedBookName} class instead.";
    ParamPair p = new ParamPair("shelfName", "The name of the shelf where books are published to.");
    String sampleCode =
        "try (LibraryClient libraryClient = LibraryClient.create()) {\n Shelf shelf = Shelf.newBuilder().build();\nShelf response = libraryClient.createShelf(shelf);\n}";
    String throwText = "com.google.api.gax.rpc.ApiException if the remote call fails";
    JavaDocComment javaDocComment =
        JavaDocComment.builder()
            .addComment(content)
            .setSampleCode(sampleCode)
            .addParam(p)
            .setDeprecated(deprecatedText)
            .setThrowsText(throwText)
            .build();
    String expected =
        "/**\n"
            + "* this is a test comment\n"
            + "* @param shelfName The name of the shelf where books are published to.\n"
            + "* Sample code:\n"
            + "* <pre><code>\n"
            + "* try (LibraryClient libraryClient = LibraryClient.create()) {\n"
            + "*  Shelf shelf = Shelf.newBuilder().build();\n"
            + "* Shelf response = libraryClient.createShelf(shelf);\n"
            + "* }\n"
            + "* </code></pre>\n"
            + "* @deprecated Optional[Use the {@link ArchivedBookName} class instead.]\n"
            + "* @throws Optional[com.google.api.gax.rpc.ApiException if the remote call fails]\n"
            + "*/\n";
    String formattedComment = javaDocComment.accept(writerVisitor);
    assertThat(formattedComment).isEqualTo(expected);
  }
}
