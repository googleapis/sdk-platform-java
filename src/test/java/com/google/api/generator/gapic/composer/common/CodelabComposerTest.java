package com.google.api.generator.gapic.composer.common;

import static org.junit.Assert.*;

import com.google.api.generator.engine.ast.MethodDefinition;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import org.junit.Test;

public class CodelabComposerTest {
  JavaWriterVisitor visitor = new JavaWriterVisitor();

  @Test
  public void createdALengthMethod() {
    MethodDefinition methodDefinition = CodelabComposer.createdALengthMethod();

    methodDefinition.accept(visitor);
    String actual = visitor.write();
    String expected =
        "public static int length(String word) {\n"
            + "return word.length();\n"
            + "}";
    assertEquals(actual.trim(), expected);
  }

  @Test
  public void createdAFunMethod() {
    MethodDefinition methodDefinition = CodelabComposer.createdAFunMethod();

    methodDefinition.accept(visitor);
    String actual = visitor.write();
    String expected =
        "protected static final boolean isFun(String word) {\n"
        + "return word.equals(\"fun\");\n"
        + "}";
    assertEquals(actual.trim(), expected);
  }

  @Test
  public void createdARobustFunMethod() {
    MethodDefinition methodDefinition = CodelabComposer.createdARobustFunMethod();

    methodDefinition.accept(visitor);
    String actual = visitor.write();
    String expected =
        "protected static final boolean isFun(String word) {\n"
            + "if (word == null) {\n"
            + "return false;\n"
            + "}\n"
            + "return word.equals(\"fun\");\n"
            + "}";
    assertEquals(actual.trim(), expected);
  }

}