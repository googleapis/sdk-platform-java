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

import com.google.api.generator.engine.ast.AssignmentExpr;
import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.IdentifierNode;
import com.google.api.generator.engine.ast.PrimitiveValue;
import com.google.api.generator.engine.ast.ScopeNode;
import com.google.api.generator.engine.ast.TypeNode;
import com.google.api.generator.engine.ast.Value;
import com.google.api.generator.engine.ast.ValueExpr;
import com.google.api.generator.engine.ast.Variable;
import com.google.api.generator.engine.ast.VariableExpr;
import com.google.api.generator.engine.ast.LineComment;
import com.google.api.generator.engine.ast.BlockComment;
import com.google.api.generator.engine.ast.JavaDocComment;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

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
  public void writeNormalBlockComment() throws Exception {
    String content = "this is a test comment";
    BlockComment blockComment = BlockComment.builder().setComment(content).build();
    String expected = "/** this is a test comment */\n";
    String formattedComment = blockComment.accept(writerVisitor);
    assertThat(formattedComment).isEqualTo(expected);
  }

  @Test
  public void writeNormalLineComment()throws Exception {
    String content = "this is a test comment";
    LineComment lineComment = LineComment.builder().setComment(content).build();
    String expected = "// this is a test comment\n";
    String formattedComment = lineComment.accept(writerVisitor);
    assertThat(formattedComment).isEqualTo(expected);
  }

  @Test
  public void writeLongLineComment() throws Exception {
    String content =
        "this is a long test comment with so many words, hello world, hello again, hello for 3 times, blah, blah!";
    LineComment lineComment = LineComment.builder().setComment(content).build();
    String expected =
        "// this is a long test comment with so many words, hello world, hello again, hello for 3 times,\n// blah, blah!\n";
    String formattedComment = lineComment.accept(writerVisitor);
    assertThat(formattedComment).isEqualTo(expected);
  }

  @Test
  public void writeNormalJavaDocComment() throws Exception {
    String content = "this is a test comment";
    String deprecatedText = "Use the {@link ArchivedBookName} class instead.";
    String paramName = "shelfName";
    String paramDescription =  "The name of the shelf where books are published to.";
    String htmlP1 = "This class provides the ability to make remote calls to the backing service through method calls that map to API methods. Sample code to get started:";
    String htmlP2 = "The surface of this class includes several types of Java methods for each of the API's methods:";
    String sampleCode =
        "try (LibraryClient libraryClient = LibraryClient.create()) {\n Shelf shelf = Shelf.newBuilder().build();\nShelf response = libraryClient.createShelf(shelf);\n}";
    List<String> htmlList = Arrays.asList("A flattened method.", " A request object method.", "A callable method.");
    String throwText = "com.google.api.gax.rpc.ApiException if the remote call fails.";
    JavaDocComment javaDocComment =
        JavaDocComment.builder()
            .addComment(content)
            .addHtmlP(htmlP1)
            .addSampleCode(sampleCode)
            .addHtmlP(htmlP2)
            .addHtmlOl(htmlList)
            .addSampleCode(sampleCode)
            .addParam(paramName, paramDescription)
            .setDeprecated(deprecatedText)
            .setThrowsText(throwText)
            .build();
    String expected =
        "/**\n"
            + "* this is a test comment\n"
            + "* <p> This class provides the ability to make remote calls to the backing service through method calls that map to API methods. Sample code to get started:\n"
            + "* Sample code:\n"
            + "* <pre><code>\n"
            + "* try (LibraryClient libraryClient = LibraryClient.create()) {\n"
            + "*  Shelf shelf = Shelf.newBuilder().build();\n"
            + "* Shelf response = libraryClient.createShelf(shelf);\n"
            + "* }\n"
            + "* </code></pre>\n"
            + "* <p> The surface of this class includes several types of Java methods for each of the API's methods:\n"
            + "* <ol>\n"
            + "* <li>A flattened method.\n"
            + "* <li> A request object method.\n"
            + "* <li>A callable method.\n"
            + "* </ol>\n"
            + "* Sample code:\n"
            + "* <pre><code>\n"
            + "* try (LibraryClient libraryClient = LibraryClient.create()) {\n"
            + "*  Shelf shelf = Shelf.newBuilder().build();\n"
            + "* Shelf response = libraryClient.createShelf(shelf);\n"
            + "* }\n"
            + "* </code></pre>\n"
            + "* @param shelfName The name of the shelf where books are published to.\n"
            + "* @deprecated Use the {@link ArchivedBookName} class instead.\n"
            + "* @throws com.google.api.gax.rpc.ApiException if the remote call fails.\n"
            + "*/\n";
    String formattedComment = javaDocComment.accept(writerVisitor);

    assertThat(formattedComment).isEqualTo(expected);
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
}
