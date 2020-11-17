package com.google.api.generator.gapic.composer.samplecode;

import com.google.api.generator.engine.ast.Expr;
import com.google.api.generator.engine.ast.ExprStatement;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.engine.writer.JavaWriterVisitor;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;

public class SampleCodeWriter {

  public static String writeSampleCode(List<Statement> statements) {
    JavaWriterVisitor visitor = new JavaWriterVisitor();
    for (Statement statement : statements) {
      statement.accept(visitor);
    }
    return SampleCodeJavaFormatter.format(visitor.write());
  }
}
